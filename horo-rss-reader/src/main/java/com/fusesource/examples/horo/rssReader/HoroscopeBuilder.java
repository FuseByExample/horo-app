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

import com.fusesource.examples.horo.model.Feed;
import com.fusesource.examples.horo.model.Horoscope;
import com.fusesource.examples.horo.model.StarSign;
import org.apache.camel.Body;
import org.apache.camel.Header;
import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author jakub
 */
public class HoroscopeBuilder {

    public Horoscope build(@Header("sign") StarSign starSign,
                           @Header("date") String date,
                           @Header("feedName") String feedName,
                           @Body String entry) throws ParseException {
        Validate.notNull(starSign, "starSign is null");
        Validate.notEmpty(date, "date is empty");
        Validate.notEmpty(feedName, "feedName is empty");
        Validate.notEmpty(entry, "entry is empty");

        Feed feed = new Feed();
        feed.setName(feedName);

        Horoscope horoscope = new Horoscope();
        horoscope.setStarSign(starSign);
        horoscope.setFeed(feed);
        horoscope.setEntry(entry);

        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        DateTime dateTime = new DateTime(format.parse(date)).toDateTime(DateTimeZone.UTC).toDateMidnight().toDateTime();
        horoscope.setPredictsFor(dateTime);
        return horoscope;
    }
}
