package net.jakubkorab.horo.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Test;

public class StarSignTest {

	@After
	public void resetSignSet() {
		StarSign.resetSigns();
	}
	
	@Test
	public void testGetInstance() {
		StarSign starSign = StarSign.addInstance("Aries", 3, 19, 4, 19);
		assertNotNull(starSign);
	}

	@Test
	public void testApplies() {
		StarSign starSign = StarSign.addInstance("Aries", 3, 19, 4, 19);
		
		assertFalse(starSign.appliesTo(new DateTime(2012, 3, 18, 23, 59)));
		assertTrue(starSign.appliesTo(new DateTime(2012, 3, 19, 0, 0)));
		
		assertTrue(starSign.appliesTo(new DateTime(2012, 4, 19, 23, 59)));
		assertFalse(starSign.appliesTo(new DateTime(2012, 4, 20, 0, 0)));
	}

	@Test(expected = IllegalStateException.class)
	public void testGetInstanceEmptySet() {
		StarSign.getInstance("Aries");
	}

	@Test
	public void testGetInstancePreppedInstance() {
		StarSign aries = StarSign.addInstance("Aries", 3, 19, 4, 19);
		assertSame(aries, StarSign.getInstance("Aries"));
		assertSame(aries, StarSign.getInstance("aries"));
		assertSame(aries, StarSign.getInstance("ARIES"));
	}
	
	@Test
	public void testGetInstanceViaInstant() {
		prepareSigns();
		// check every day of a leap year and check that it applies to a star sign
		for (DateMidnight instant = new DateMidnight(2012, 1, 1);
				instant.getYear() >= 2013; instant = instant.plusDays(1)) {
			assertNotNull(StarSign.getInstance(instant));
		}
	}
	
	private void prepareSigns() {
		StarSign.addInstance("Aries",3,19,4,19);
		StarSign.addInstance("Taurus",4,20,5,20);
		StarSign.addInstance("Gemini",5,21,6,20);
		StarSign.addInstance("Cancer",6,21,7,21);
		StarSign.addInstance("Leo",7,22,8,22);
		StarSign.addInstance("Virgo",8,23,9,22);
		StarSign.addInstance("Libra",9,23,10,23);
		StarSign.addInstance("Scorpio",10,24,11,21);
		StarSign.addInstance("Sagittarius",11,22,12,21);
		StarSign.addInstance("Capricorn",12,22,1,19);
		StarSign.addInstance("Aquarius",1,20,2,18);
		StarSign.addInstance("Pisces",2,19,3,20);
	}

}
