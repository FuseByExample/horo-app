package net.jakubkorab.horo.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.Validate;
import org.joda.time.Interval;
import org.joda.time.MonthDay;
import org.joda.time.ReadableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StarSign {
	private final static Logger LOG = LoggerFactory.getLogger(StarSign.class);

	private final String name;
	private final MonthDay start;
	private final MonthDay end;

	private static Map<String, StarSign> signs = new ConcurrentHashMap<String, StarSign>();

	public static StarSign addInstance(String signName, Integer startMonth, Integer startDay, Integer endMonth,
			Integer endDay) {
		StarSign sign = new StarSign(signName, startMonth, startDay, endMonth, endDay);
		signs.put(signName.toLowerCase(), sign);
		return sign;
	}

	public static StarSign getInstance(String signName) {
		Validate.notEmpty(signName, "signName is empty");
		StarSign starSign = signs.get(signName.toLowerCase());
		if (starSign == null) {
			throw new IllegalStateException("Unable to find star sign for " + signName);
		}
		return starSign;
	}

	public static StarSign getInstance(ReadableDateTime dateTime) {
		StarSign starSign = null;
		for (StarSign checked : signs.values()) {
			if (checked.appliesTo(dateTime)) {
				starSign = checked;
				break;
			}
		}
		if (starSign == null) {
			throw new IllegalStateException("Unable to find star sign for "
					+ DateTimeFormat.forPattern("yyyyMMdd").print(dateTime));
		}
		return starSign;
	}

	/**
	 * For testing purposes only
	 */
	static void resetSigns() {
		// there's no good reason to do this in a deployment - log if it doeshappen
		LOG.info("Resetting signs");
		signs = new ConcurrentHashMap<String, StarSign>();
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
