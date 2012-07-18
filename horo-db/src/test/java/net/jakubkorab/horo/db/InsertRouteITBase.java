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

/**
 * Camel route-based class to ensure that the mybatis component is wired correctly.
 */
public abstract class InsertRouteITBase {

    private ProducerTemplate producerTemplate;
    private HoroscopeUtils horoscopeUtils;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.horoscopeUtils = new HoroscopeUtils(dataSource);
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
        Horoscope horoscope = SampleBuilder.getSampleHoroscope();

        assertNotNull(producerTemplate);
        int initialCount = horoscopeUtils.getHoroscopeCount();
        producerTemplate.sendBody("direct:insert", horoscope);

        Thread.sleep(1000);
        try {
            assertTrue("horoscope not inserted", initialCount < horoscopeUtils.getHoroscopeCount());
        } finally {
            horoscopeUtils.deleteHoroscope(horoscope);
        }
    }

}
