package com.fusesource.examples.horo.db;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
