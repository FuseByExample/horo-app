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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static junit.framework.Assert.*;

/**
 * This test checks whether the mybatis-spring {@link SqlSessionFactory} is configured as expected.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/test-context-props.xml",
        "/test-context-h2.xml",
        "/META-INF/spring/spring-context-mybatis.xml"})
public class SqlSessionFactoryITCase {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
    }

    @Test
    public void testSelectAllHoroscopes() {
        assertNotNull(sqlSessionTemplate);
        List<Horoscope> horoscopes = sqlSessionTemplate.selectList("horoscope.selectAll");
        assertNotNull(horoscopes);
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
            int initialCount = getInstanceCount(horoscope);
            sqlSessionTemplate.insert("horoscope.insert", horoscope);
            assertEquals(initialCount + 1, (int) getInstanceCount(horoscope));
        } finally {
            // clean up
            sqlSessionTemplate.delete("horoscope.delete", horoscope);
            assertEquals((Integer) 0, getInstanceCount(horoscope));
        }
    }

    private Integer getInstanceCount(Horoscope horoscope) {
        return (Integer) sqlSessionTemplate.selectOne("horoscope.count", horoscope);
    }

}

