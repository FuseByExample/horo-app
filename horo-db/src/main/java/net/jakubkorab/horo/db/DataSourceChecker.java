package net.jakubkorab.horo.db;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSourceChecker {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@PostConstruct
	public void check() {
		if (dataSource == null) {
			log.info("DataSource is no good");
		} else {
			log.info("DataSource looks OK");
		}
	}
}