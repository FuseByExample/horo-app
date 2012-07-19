package net.jakubkorab.horo.rssReader;

import org.apache.camel.processor.idempotent.MemoryIdempotentRepository;
import org.apache.camel.processor.idempotent.jdbc.JdbcMessageIdRepository;
import org.apache.camel.spi.IdempotentRepository;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * Utility class that allows the construction of {@link IdempotentRepository} objects to be created uniformly
 * on demand.
 */
public class IdempotentRepositoryBuilder {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private DataSource dataSource;
    private PlatformTransactionManager transactionManager;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public IdempotentRepository getRepository(String processorName) {
        Validate.notEmpty(processorName, "processorName is empty");

        IdempotentRepository repository;
        if (dataSource == null) {
            log.info("Initialising in-memory idempotent repository for {}", processorName);
            repository = new MemoryIdempotentRepository();
        } else {
            log.info("Initialising JDBC idempotent repository for {}", processorName);
            if (transactionManager == null) {
                repository = new JdbcMessageIdRepository(dataSource, processorName);
            } else {
                repository = new JdbcMessageIdRepository(dataSource, new TransactionTemplate(transactionManager), processorName);
            }
        }
        return repository;
    }
}
