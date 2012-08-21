package com.fusesource.examples.horo.db;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * This test class requires a visual test. Should see log statement:
 * SpringManagedTransaction       DEBUG JDBC Connection [....] will not be managed by Spring
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/test-context-props.xml",
        "/test-context-h2.xml",
        "/META-INF/spring/spring-context-mybatis.xml",
        "InsertRouteNonTransactedITCase-context.xml"})
public class InsertRouteNonTransactedITCase extends InsertRouteITBase {
}
