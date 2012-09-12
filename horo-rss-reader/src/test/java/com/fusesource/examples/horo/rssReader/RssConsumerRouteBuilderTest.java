/*
 * Copyright 2012 FuseSource
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fusesource.examples.horo.rssReader;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import com.fusesource.test.ResourceHelper;
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
        builder.setRepositoryBuilder(new IdempotentRepositoryBuilder());

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
    public void testAstrologyComSimpleIdempotence() throws InterruptedException {
        MockEndpoint mock = getMockEndpoint("mock:out");
        mock.setExpectedMessageCount(12);

        SyndFeed body = getResourceAsSyndFeed("/com/astrology/2012-06-25.xml");
        template.sendBody("direct:in", body);
        template.sendBody("direct:in", body);

        mock.setResultWaitTime(4000);
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
