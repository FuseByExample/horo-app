package net.jakubkorab.horo.db;

import net.jakubkorab.horo.model.Feed;
import net.jakubkorab.horo.model.Horoscope;
import net.jakubkorab.horo.model.StarSign;
import org.apache.ibatis.session.SqlSessionFactory;
import org.joda.time.DateMidnight;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

import static junit.framework.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-context-test.xml", "/META-INF/spring/spring-context.xml"})
public class HoroscopeMapperITCase {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private SqlSessionTemplate sqlSessionTemplate;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * This test is just here to check that the database user can connect successfully, without
     * mybatis getting in the way.
     */
    @Test
    public void checkPermissions() throws IOException {
        assertTrue(jdbcTemplate.queryForLong("select count(*) from horoscopes") > 0);
    }

    @Test
    public void testSelectAllHoroscopes() {
        assertNotNull(sqlSessionTemplate);
        List<Horoscope> horoscopes = (List<Horoscope>) sqlSessionTemplate.selectList("horoscope.selectAll");
        assertFalse(horoscopes.isEmpty());
        for (Horoscope horoscope : horoscopes) {
            log.info(horoscope.toString());
        }
    }

    @Test
    public void testInsertHoroscope() {
        assertNotNull(sqlSessionTemplate);

        Feed feed = new Feed();
        feed.setName("com.astrology");

        Horoscope horoscope = new Horoscope();
        horoscope.setFeed(feed);
        horoscope.setStarSign(StarSign.Aquarius);
        horoscope.setPredictsFor(new DateMidnight(2001, 1, 1).toDateTime());
        horoscope.setEntry("You will meet a tall, dark stranger");

        // ensure that the db is in a good state
        sqlSessionTemplate.delete("horoscope.delete", horoscope);
        try {
            assertEquals((Integer) 0, getInstanceCount(horoscope));
            sqlSessionTemplate.insert("horoscope.insert", horoscope);
            assertEquals((Integer) 1, getInstanceCount(horoscope));
        } finally {
            // clean up
            sqlSessionTemplate.delete("horoscope.delete", horoscope);
        }
    }

    private Integer getInstanceCount(Horoscope horoscope) {
        return (Integer) sqlSessionTemplate.selectOne("horoscope.count", horoscope);
    }

}

