package net.jakubkorab.horo.db;

import net.jakubkorab.horo.model.Feed;
import net.jakubkorab.horo.model.Horoscope;
import net.jakubkorab.horo.model.StarSign;
import org.joda.time.DateMidnight;

public class SampleBuilder {
    public static Horoscope getSampleHoroscope() {
        Feed feed = new Feed();
        feed.setName("com.astrology");

        Horoscope horoscope = new Horoscope();
        horoscope.setFeed(feed);
        horoscope.setStarSign(StarSign.Aquarius);
        horoscope.setPredictsFor(new DateMidnight(2001, 1, 1).toDateTime());
        horoscope.setEntry("You will meet a tall, dark stranger");
        return horoscope;
    }
}