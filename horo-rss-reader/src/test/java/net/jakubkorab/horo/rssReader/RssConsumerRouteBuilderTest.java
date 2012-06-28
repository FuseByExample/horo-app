package net.jakubkorab.horo.rssReader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.Validate;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RssConsumerRouteBuilderTest extends CamelTestSupport {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		RssConsumerRouteBuilder builder = new RssConsumerRouteBuilder();
		builder.setSourceName("test");
		builder.setSourceUri("direct:in");
		builder.setTargetUri("mock:out");
		return builder;
	}

	@Test
	public void testAstrologyComSimple() throws InterruptedException, IOException {
		MockEndpoint mock = getMockEndpoint("mock:out");
		mock.setExpectedMessageCount(12);

		String body = loadFileAsString("src/test/resources/astrology.com/2012-06-25.xml");
		template.sendBody("direct:in", body);

		mock.setResultWaitTime(1000);
		mock.assertIsSatisfied();
		log.info(mock.getReceivedExchanges().get(0).getIn().getBody()
				.toString());
	}

	@Test
	public void testAstrologyComExtended() throws InterruptedException, IOException {
		MockEndpoint mock = getMockEndpoint("mock:out");
		mock.setExpectedMessageCount(12);

		String body = loadFileAsString("src/test/resources/astrology.com/2012-06-25-extended.xml");
		template.sendBody("direct:in", body);

		mock.setResultWaitTime(1000);
		mock.assertIsSatisfied();
		log.info(mock.getReceivedExchanges().get(0).getIn().getBody()
				.toString());
	}

	private String loadFileAsString(String fileName) throws IOException {
		Validate.notEmpty(fileName);
		FileInputStream in = null;
		try {
			in = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Could not find " + fileName, e);
		}
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(in));
		StringBuilder out = new StringBuilder();
		while (true) {
			String line = bufferedReader.readLine();
			if (line == null) {
				break;
			} else {
				out.append(line + SystemUtils.LINE_SEPARATOR);
			}
		}
		return out.toString();
	}
}
