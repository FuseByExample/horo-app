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

import com.fusesource.examples.horo.model.Horoscope;
import com.fusesource.examples.horo.web.vo.HoroscopeVO;
import com.fusesource.examples.horo.web.vo.HoroscopesResponseVO;
import org.joda.time.DateTimeZone;

import java.util.ArrayList;
import java.util.List;

public class HoroscopeTranslator {
    public HoroscopesResponseVO toHoroscopesResponseVO(List<Horoscope> horoscopes) {
        HoroscopesResponseVO responseVO = new HoroscopesResponseVO();
        List<HoroscopeVO> horoscopeVOs = new ArrayList<HoroscopeVO>();
        responseVO.setHoroscopes(horoscopeVOs);
        if (horoscopes != null) {
            for (Horoscope horoscope : horoscopes) {
                HoroscopeVO horoscopeVO = new HoroscopeVO();
                horoscopeVO.setHoroscopeId(horoscope.getId());
                horoscopeVO.setEntry(horoscope.getEntry());
                horoscopeVO.setDate(horoscope.getPredictsFor().withZone(DateTimeZone.UTC).toDate());
                horoscopeVO.setFeedId(0L); // TODO load feed id into model
                horoscopeVO.setStarSign(horoscope.getStarSign().toString());
                horoscopeVOs.add(horoscopeVO);
            }
        }
        return responseVO;
    }
}
