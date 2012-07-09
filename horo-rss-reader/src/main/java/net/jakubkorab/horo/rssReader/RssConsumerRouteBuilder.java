package net.jakubkorab.horo.rssReader;

import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.lang.Validate;

import javax.annotation.PostConstruct;

/**
 * Template {@link RouteBuilder} for routes that consume RSS feeds and persist these to the database.
 * @author jkorab
 */
public class RssConsumerRouteBuilder extends RouteBuilder {
	
	private String sourceName;
	private String sourceUri;
	private String targetUri;

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public void setSourceUri(String sourceUri) {
		this.sourceUri = sourceUri;
	}

	public void setTargetUri(String targetUri) {
		this.targetUri = targetUri;
	}

	@PostConstruct
	public void checkMandatoryProperties() {
		Validate.notEmpty(sourceName);
		Validate.notEmpty(sourceUri);
		Validate.notEmpty(targetUri);
	}

	/* (non-Javadoc)
	 * @see org.apache.camel.builder.RouteBuilder#configure()
	 */
	@Override
	public void configure() throws Exception {
		from(sourceUri).id("rssConsumer-" + sourceName)
			.split(simple("${body.entries}"))
            .setHeader("feedName", constant(sourceName))
            .setHeader("title", simple("${body.title}"))
            .setHeader("sign", bean(StarSignParser.class, "parse"))
			.setHeader("date", simple("${body.publishedDate}"))
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
