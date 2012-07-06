package net.jakubkorab.horo.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring-context-test.xml", "/META-INF/spring/spring-context.xml"})
public class DataSourceITCase {

	@Autowired
	private DataSource dataSource;
	
	@Test
	public void test() throws SQLException {
		assertNotNull(dataSource);
		Connection connection = dataSource.getConnection();
		assertNotNull(connection);
	}

}
