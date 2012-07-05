package net.jakubkorab.horo.db;

import static junit.framework.Assert.*;
import net.jakubkorab.horo.model.Horoscope;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;
import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-context-test.xml", "/META-INF/spring/spring-context.xml"})
public class MapperTest {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
    }

    private JdbcTemplate jdbcTemplate;

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
    public void checkMappers() throws IOException {
        assertNotNull(sqlSessionTemplate);
        List<Horoscope> horoscopes = (List<Horoscope>) sqlSessionTemplate.selectList("selectAllHoroscopes");
        assertFalse(horoscopes.isEmpty());
        for (Horoscope horoscope : horoscopes) {
            log.info(horoscope.toString());
        }
    }


}

