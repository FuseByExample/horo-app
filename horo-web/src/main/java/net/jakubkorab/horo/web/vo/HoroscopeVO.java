package net.jakubkorab.horo.web.vo;

import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@XmlType(name = "horoscope")
public class HoroscopeVO {
    private Long horoscopeId;
    private Long feedId;
    private String starSign;
    private String entry;
    private Date date;

    public Long getHoroscopeId() {
        return horoscopeId;
    }

    public void setHoroscopeId(Long horoscopeId) {
        this.horoscopeId = horoscopeId;
    }

    public Long getFeedId() {
        return feedId;
    }

    public void setFeedId(Long feedId) {
        this.feedId = feedId;
    }

    public String getStarSign() {
        return starSign;
    }

    public void setStarSign(String starSign) {
        this.starSign = starSign;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
