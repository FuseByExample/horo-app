package net.jakubkorab.horo.rssReader;

import net.jakubkorab.test.http.HttpServerInterceptor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class RssConsumerRouteBuilderITCase extends CamelTestSupport {
    public static final String CONTEXT_PATH = "/com-astrology-horoscope.rss";
    public static final String RSS_COMPONENT_OPTIONS = "splitEntries=false";

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Rule
    public HttpServerInterceptor httpServer = new HttpServerInterceptor(this.getClass())
            .respondsTo(CONTEXT_PATH, "/com/astrology/2012-06-25.xml");

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        RssConsumerRouteBuilder builder = new RssConsumerRouteBuilder();
        builder.setSourceName("com.astrology");
        builder.setSourceUri("rss://http://localhost:" + httpServer.getPort() + CONTEXT_PATH
                + "?" + RSS_COMPONENT_OPTIONS);
        builder.setTargetUri("mock:out");
        builder.setRepositoryBuilder(new IdempotentRepositoryBuilder());
        return builder;
    }

    @Test
    public void testAstrologyComSimple() throws InterruptedException, IOException {
        // log.info("httpServer running on port {}", httpServer.getPort());

        MockEndpoint mock = getMockEndpoint("mock:out");
        mock.setExpectedMessageCount(12);

        mock.setResultWaitTime(5000);
        mock.assertIsSatisfied();
        log.info(mock.getReceivedExchanges().get(0).getIn().getBody()
                .toString());
    }
}
