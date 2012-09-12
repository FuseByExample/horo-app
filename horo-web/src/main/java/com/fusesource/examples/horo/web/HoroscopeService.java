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

package com.fusesource.examples.horo.web;

import com.fusesource.examples.horo.model.Feed;
import com.fusesource.examples.horo.model.Horoscope;
import com.fusesource.examples.horo.model.StarSign;
import com.fusesource.examples.horo.web.vo.HoroscopesResponseVO;
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
