package net.jakubkorab.horo.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-context-test.xml", "/META-INF/spring/spring-context.xml"})
public class DataSourceITCase {

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
    public void checkSelect() throws IOException {
        assertTrue(jdbcTemplate.queryForLong("select count(*) from horoscopes") > 0);
    }
}
