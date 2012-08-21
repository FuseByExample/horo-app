package com.fusesource.examples.horo.model;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.*;

public class StarSignTest {

    @Test
    public void testApplies() {
        StarSign starSign = StarSign.getInstance("Aries");

        assertFalse(starSign.appliesTo(new DateTime(2012, 3, 18, 23, 59)));
        assertTrue(starSign.appliesTo(new DateTime(2012, 3, 19, 0, 0)));

        assertTrue(starSign.appliesTo(new DateTime(2012, 4, 19, 23, 59)));
        assertFalse(starSign.appliesTo(new DateTime(2012, 4, 20, 0, 0)));
    }

    @Test
    public void testGetInstance() {
        assertSame(StarSign.Aries, StarSign.getInstance("Aries"));
        assertSame(StarSign.Aries, StarSign.getInstance("aries"));
        assertSame(StarSign.Aries, StarSign.getInstance("ARIES"));
    }

    @Test
    public void testGetInstanceViaInstant() {
        // check every day of a leap year and check that it applies to a star sign
        for (DateMidnight instant = new DateMidnight(2012, 1, 1);
             instant.getYear() >= 2013; instant = instant.plusDays(1)) {
            assertNotNull(StarSign.getInstance(instant));
        }
    }

}
