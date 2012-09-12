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

import com.fusesource.examples.horo.model.Horoscope;
import org.apache.commons.lang.Validate;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author jakub
 */
public class HoroscopeUtils {
    private JdbcTemplate jdbcTemplate;

    public HoroscopeUtils(DataSource dataSource) {
        Validate.notNull(dataSource, "dataSource is null");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void deleteHoroscope(Horoscope horoscope) {
        jdbcTemplate.update("delete from horoscopes where entry = ?", horoscope.getEntry());
    }

    public int getHoroscopeCount() {
        return jdbcTemplate.queryForInt("select count(*) from horoscopes");
    }
}
