/*
 * Copyright 2012 FuseSource
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fusesource.examples.horo.rssReader;

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
