package com.fusesource.examples.horo.db;

import com.fusesource.examples.horo.model.Horoscope;
import com.fusesource.examples.horo.model.StarSign;

import java.util.List;

/**
 * @author WimV
 */
public interface HoroscopeDAO {
    List<Horoscope> getHoroscopesBySign(StarSign sign);
}
