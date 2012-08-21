package com.fusesource.examples.horo.db;

import static org.junit.Assert.*;

import com.fusesource.examples.horo.model.Horoscope;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
