package net.jakubkorab.horo.db;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-context-test.xml", "/META-INF/spring/spring-context.xml"})
public class DataSourceCheckerTest {

	@Autowired
	private DataSource dataSource;
	
	@Test
	public void test() throws SQLException {
		assertNotNull(dataSource);
		Connection connection = dataSource.getConnection();
		assertNotNull(connection);
	}

}
