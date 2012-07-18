package net.jakubkorab.horo.rssReader;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import net.jakubkorab.test.ResourceHelper;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RssConsumerRouteBuilderTest extends CamelTestSupport {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private final ResourceHelper testResources = new ResourceHelper(this.getClass());

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        RssConsumerRouteBuilder builder = new RssConsumerRouteBuilder();
        builder.setSourceName("com.astrology");
        builder.setSourceUri("direct:in");
        builder.setTargetUri("mock:out");

        SpringTransactionPolicy transactionPolicy = new SpringTransactionPolicy();

        return builder;
    }

    @Test
    public void testAstrologyComSimple() throws InterruptedException {
        MockEndpoint mock = getMockEndpoint("mock:out");
        mock.setExpectedMessageCount(12);

        SyndFeed body = getResourceAsSyndFeed("/com/astrology/2012-06-25.xml");
        template.sendBody("direct:in", body);

        mock.setResultWaitTime(3000);
        mock.assertIsSatisfied();
        log.info(mock.getReceivedExchanges().get(0).getIn().getBody()
                .toString());
    }

    @Test
    public void testAstrologyComExtended() throws InterruptedException {
        MockEndpoint mock = getMockEndpoint("mock:out");
        mock.setExpectedMessageCount(12);

        SyndFeed body = getResourceAsSyndFeed("/com/astrology/extended/2012-06-25.xml");
        template.sendBody("direct:in", body);

        mock.setResultWaitTime(3000);
        mock.assertIsSatisfied();
        log.info(mock.getReceivedExchanges().get(0).getIn().getBody()
                .toString());
    }

    private SyndFeed getResourceAsSyndFeed(String resource) {
        try {
            XmlReader xmlReader = new XmlReader(this.getClass().getResource(resource));
            return new SyndFeedInput().build(xmlReader);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
