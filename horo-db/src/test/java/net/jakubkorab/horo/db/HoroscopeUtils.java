package net.jakubkorab.horo.db;

import net.jakubkorab.horo.model.Horoscope;
import org.apache.commons.lang.Validate;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author jakub
 */
public class HoroscopeUtils {
    private JdbcTemplate jdbcTemplate;

    public HoroscopeUtils(DataSource dataSource) {
        Validate.notNull(dataSource, "dataSource is null");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void deleteHoroscope(Horoscope horoscope) {
        jdbcTemplate.update("delete from horoscopes where entry = ?", horoscope.getEntry());
    }

    public int getHoroscopeCount() {
        return jdbcTemplate.queryForInt("select count(*) from horoscopes");
    }
}
