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

package com.fusesource.examples.horo.model;

import org.apache.commons.lang.Validate;
import org.joda.time.Interval;
import org.joda.time.MonthDay;
import org.joda.time.ReadableDateTime;
import org.joda.time.format.DateTimeFormat;

public enum StarSign {

    Aries("Aries", 3, 19, 4, 19),
    Taurus("Taurus", 4, 20, 5, 20),
    Gemini("Gemini", 5, 21, 6, 20),
    Cancer("Cancer", 6, 21, 7, 21),
    Leo("Leo", 7, 22, 8, 22),
    Virgo("Virgo", 8, 23, 9, 22),
    Libra("Libra", 9, 23, 10, 23),
    Scorpio("Scorpio", 10, 24, 11, 21),
    Sagittarius("Sagittarius", 11, 22, 12, 21),
    Capricorn("Capricorn", 12, 22, 1, 19),
    Aquarius("Aquarius", 1, 20, 2, 18),
    Pisces("Pisces", 2, 19, 3, 20);

    private final String name;
    private final MonthDay start;
    private final MonthDay end;

    public static StarSign getInstance(String name) {
        Validate.notEmpty(name, "name is empty");
        StarSign starSign = null;
        for (StarSign temp : values()) {
            if (temp.getName().toLowerCase().equals(name.toLowerCase())) {
                starSign = temp;
                break;
            }
        }
        if (starSign == null) {
            throw new IllegalStateException("Unable to find star sign for "
                    + name);
        }
        return starSign;
    }

    public static StarSign getInstance(ReadableDateTime dateTime) {
        Validate.notNull(dateTime, "dateTime is null");
        StarSign starSign = null;
        for (StarSign temp : values()) {
            if (temp.appliesTo(dateTime)) {
                starSign = temp;
                break;
            }
        }
        if (starSign == null) {
            throw new IllegalStateException("Unable to find star sign for "
                    + DateTimeFormat.forPattern("yyyyMMdd").print(dateTime));
        }
        return starSign;
    }

    private StarSign(String signName, Integer startMonth, Integer startDay, Integer endMonth, Integer endDay) {
        Validate.notEmpty(signName, "signName is empty");
        Validate.notNull(startMonth, "startMonth is null");
        Validate.notNull(startDay, "startDay is null");
        Validate.notNull(endMonth, "endMonth is null");
        Validate.notNull(endDay, "endDay is null");

        this.name = signName;
        // year is intended to be ignored
        this.start = new MonthDay(startMonth, startDay);
        this.end = new MonthDay(endMonth, endDay);
    }

    public boolean appliesTo(ReadableDateTime dateTime) {
        Validate.notNull(dateTime);
        int year = dateTime.getYear();

        Interval signInterval = new Interval(start.toLocalDate(year).toDateMidnight(), end.toLocalDate(year)
                .toDateMidnight().plusDays(1));
        return signInterval.contains(dateTime);
    }

    public String getName() {
        return name;
    }

    public MonthDay getStart() {
        return start;
    }

    public MonthDay getEnd() {
        return end;
    }

}
