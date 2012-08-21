package net.jakubkorab.horo.web.vo;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "horoscopeResponse")
public class HoroscopesResponseVO {
    private List<HoroscopeVO> horoscopes;

    public List<HoroscopeVO> getHoroscopes() {
        return horoscopes;
    }

    public void setHoroscopes(List<HoroscopeVO> horoscopes) {
        this.horoscopes = horoscopes;
    }
}
