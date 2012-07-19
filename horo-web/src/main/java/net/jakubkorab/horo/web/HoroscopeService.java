package net.jakubkorab.horo.web;

import com.thoughtworks.xstream.XStream;
import net.jakubkorab.horo.model.Feed;
import net.jakubkorab.horo.model.Horoscope;
import net.jakubkorab.horo.model.StarSign;
import net.jakubkorab.horo.web.xstream.DateTimeConverter;
import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.Collections;

/**
 * @author jakub
 */
@Path("/horoscopes/")
@Produces("application/xml")
public class HoroscopeService {

    private static Logger LOG = LoggerFactory.getLogger(HoroscopeService.class);
    private final XStream xstream;

    public HoroscopeService() {
        LOG.info("Creating HoroscopeService");
        xstream = new XStream();
        xstream.registerConverter(new DateTimeConverter());
        xstream.setMode(XStream.NO_REFERENCES);
    }

    @GET
    @Path("/signs/{starSign}")
    public String getHoroscopes(@PathParam("starSign") String requestedSign) {
        LOG.info("getHoroscopes({})", requestedSign);
        Validate.notEmpty(requestedSign, "requestedSign is empty");
        StarSign starSign = StarSign.getInstance(requestedSign);
        Validate.notNull(starSign, "could not resolve " + requestedSign + " to a valid sign");
        Horoscope horoscope = new Horoscope();
        horoscope.setEntry("Your future has cake");
        horoscope.setPredictsFor(DateTime.now());
        Feed feed = new Feed();
        feed.setName("com.astrology");
        horoscope.setFeed(feed);
        return xstream.toXML(Collections.singletonList(horoscope));
    }
}
