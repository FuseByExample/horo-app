package net.jakubkorab.horo.db;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

public class DataSourceChecker {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    private JdbcTemplate jdbcTemplate;
    private String status = "Unavailable";

    public void setDataSource(DataSource dataSource) {
        Validate.notNull(dataSource, "dataSource is null");
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @PostConstruct
    public void check() {
        Validate.notNull(jdbcTemplate, "jdbcTemplate is null");
        Validate.isTrue(jdbcTemplate.queryForInt("select 1") == 1, "unable to run check statement");
        log.info("DataSource looks OK");
        status = "Available";
    }

    public String getStatus() {
        return status;
    }
}