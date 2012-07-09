package net.jakubkorab.horo.rssReader;

import net.jakubkorab.horo.model.Feed;
import net.jakubkorab.horo.model.Horoscope;
import net.jakubkorab.horo.model.StarSign;
import org.apache.camel.Body;
import org.apache.camel.Header;
import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        // TODO applies timezone offset
        DateTime dateTime = new DateTime(format.parse(date)).toDateMidnight().toDateTime();
        horoscope.setPredictsFor(dateTime);
        return horoscope;
    }
}
