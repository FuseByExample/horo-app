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
