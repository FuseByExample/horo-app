package net.jakubkorab.horo.web;

import net.jakubkorab.horo.model.Feed;
import net.jakubkorab.horo.model.Horoscope;
import net.jakubkorab.horo.model.StarSign;
import net.jakubkorab.horo.web.vo.HoroscopesResponseVO;
import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jakub
 */
@Path("/horoscopes/")
@Produces({"application/xml", "application/json"})
public class HoroscopeService {

    private static Logger LOG = LoggerFactory.getLogger(HoroscopeService.class);

    public HoroscopeService() {
        LOG.info("Creating HoroscopeService");
    }

    @GET
    @Path("/signs/{starSign}")
    public HoroscopesResponseVO getHoroscopes(@PathParam("starSign") String requestedSign) {
        LOG.info("getHoroscopes({})", requestedSign);
        Validate.notEmpty(requestedSign, "requestedSign is empty");
        StarSign starSign = StarSign.getInstance(requestedSign);
        Validate.notNull(starSign, "could not resolve " + requestedSign + " to a valid sign");

        List<Horoscope> horoscopes = getHoroscopes(starSign);
        return new HoroscopeTranslator().toHoroscopesResponseVO(horoscopes);
    }

    private List<Horoscope> getHoroscopes(StarSign starSign) {
        Horoscope horoscope = new Horoscope();
        horoscope.setEntry("Your future has cake");
        horoscope.setPredictsFor(DateTime.now());
        horoscope.setStarSign(starSign);
        Feed feed = new Feed();
        feed.setName("com.astrology");
        horoscope.setFeed(feed);

        List<Horoscope> horoscopes = new ArrayList<Horoscope>();
        horoscopes.add(horoscope);
        // and again
        horoscopes.add(horoscope);
        return horoscopes;
    }
}
