package net.jakubkorab.horo.rssReader;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.processor.idempotent.jdbc.JdbcMessageIdRepository;
import org.apache.camel.spi.IdempotentRepository;
import org.apache.camel.spi.TransactedPolicy;
import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.apache.commons.lang.Validate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * Template {@link RouteBuilder} for routes that consume RSS feeds and process these further (persist these to a database).
 */
public class RssConsumerRouteBuilder extends RouteBuilder {

    private String sourceName;
    private String sourceUri;
    private String targetUri;

    private TransactedPolicy transactedPolicy;
    private IdempotentRepositoryBuilder repositoryBuilder;

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public void setSourceUri(String sourceUri) {
        this.sourceUri = sourceUri;
    }

    public void setTargetUri(String targetUri) {
        this.targetUri = targetUri;
    }

    public void setRepositoryBuilder(IdempotentRepositoryBuilder repositoryBuilder) {
        this.repositoryBuilder = repositoryBuilder;
    }

    public void setTransactedPolicy(TransactedPolicy transactedPolicy) {
        Validate.notNull(transactedPolicy, "transactedPolicy is null");
        this.transactedPolicy = transactedPolicy;
    }

    @PostConstruct
    public void checkMandatoryProperties() {
        Validate.notEmpty(sourceName);
        Validate.notEmpty(sourceUri);
        Validate.notEmpty(targetUri);
        Validate.notNull(repositoryBuilder);
    }

    @Override
    public void configure() throws Exception {

        // consume an RSS feed with multiple horoscopes per item
        String sedaUri = "seda:" + sourceName;
        from(sourceUri).id(sourceName + ".rss")
                .removeHeader("CamelRssFeed") // redundant header that slows down processing
                .log("Consuming from " + sourceName)
                .split(simple("${body.entries}"))
                .to(sedaUri);

        // consume each item individually in its own transaction
        ProcessorDefinition definition = from(sedaUri).id(sourceName + ".seda");
        if (transactedPolicy != null) {
            // none defined during unit test
            definition = definition.policy(transactedPolicy);
        }

        // ensure that no item is consumed (stored to the database) more than once
        IdempotentRepository idempotentRepository = repositoryBuilder.getRepository(sourceName);

        definition.setHeader("feedName", constant(sourceName))
                .setHeader("title", simple("${body.title}"))
                .setHeader("sign", bean(StarSignParser.class, "parse"))
                .setHeader("date", simple("${body.publishedDate}"))
                .idempotentConsumer(simple("${header.sign} ${header.date}"), idempotentRepository)
                .transform(simple("${body.description.value}"))
                .convertBodyTo(String.class)
                .unmarshal().tidyMarkup()
                // get the first paragraph
                .transform(xpath("//p[1]/text()").stringResult())
                .bean(HoroscopeBuilder.class)
                .to("log:items?showHeaders=true")
                .to(targetUri);
    }

}
