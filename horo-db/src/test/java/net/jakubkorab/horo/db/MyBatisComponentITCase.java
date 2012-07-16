package net.jakubkorab.horo.db;

import static org.junit.Assert.*;

import net.jakubkorab.horo.model.Feed;
import net.jakubkorab.horo.model.Horoscope;
import net.jakubkorab.horo.model.StarSign;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.joda.time.DateMidnight;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author jakub
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-context-test.xml",
        "/spring-context-db-postgres-test.xml",
        "/META-INF/spring/spring-context.xml",
        "insert-route-context.xml"})
public class MyBatisComponentITCase {

    private JdbcTemplate jdbcTemplate;
    private ProducerTemplate producerTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    public void setCamelContext(CamelContext camelContext) throws Exception {
        producerTemplate = camelContext.createProducerTemplate();
        producerTemplate.start();
    }

    /**
     * This test is just here to check that the database user can connect successfully, without
     * mybatis getting in the way.
     */
    @Test
    public void checkInsert() throws Exception {
        Feed feed = new Feed();
        feed.setName("com.astrology");

        Horoscope horoscope = new Horoscope();
        horoscope.setFeed(feed);
        horoscope.setStarSign(StarSign.Aquarius);
        horoscope.setPredictsFor(new DateMidnight(2001, 1, 1).toDateTime());
        horoscope.setEntry("You will meet a tall, dark stranger");

        assertNotNull(producerTemplate);

        int initialCount = getHoroscopeCount();

        producerTemplate.sendBody("direct:insert", horoscope);

        Thread.sleep(1000);

        try {
            assertTrue("horoscope not inserted", initialCount < getHoroscopeCount());
        } finally {
            deleteHoroscope(horoscope);
        }
    }

    private void deleteHoroscope(Horoscope horoscope) {
        // jdbcTemplate.update("delete from horoscopes where entry = ?", horoscope.getEntry());
    }

    private int getHoroscopeCount() {
        return jdbcTemplate.queryForInt("select count(*) from horoscopes");
    }
}
