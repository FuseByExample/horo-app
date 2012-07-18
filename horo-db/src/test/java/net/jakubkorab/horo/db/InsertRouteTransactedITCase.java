package net.jakubkorab.horo.db;

import net.jakubkorab.horo.model.Horoscope;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * This test class requires a visual test. Should see log statement:
 * SpringManagedTransaction       DEBUG JDBC Connection [....] will be managed by Spring
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/test-context-props.xml",
        "/test-context-h2.xml",
        "/META-INF/spring/spring-context-mybatis.xml",
        "InsertRouteTransactedITCase-context.xml"})
public class InsertRouteTransactedITCase extends InsertRouteITBase {
}
