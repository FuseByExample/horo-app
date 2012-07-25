package net.jakubkorab.horo.web;

import net.jakubkorab.horo.model.Horoscope;
import net.jakubkorab.horo.web.vo.HoroscopeVO;
import net.jakubkorab.horo.web.vo.HoroscopesResponseVO;
import org.joda.time.DateTimeZone;

import java.util.ArrayList;
import java.util.List;

// TODO test
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
