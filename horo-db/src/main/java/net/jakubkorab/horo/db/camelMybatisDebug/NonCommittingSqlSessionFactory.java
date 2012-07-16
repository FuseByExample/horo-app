package net.jakubkorab.horo.db.camelMybatisDebug;

import org.apache.ibatis.session.*;

import java.sql.Connection;

/**
 * @author jakub
 */
public class NonCommittingSqlSessionFactory implements SqlSessionFactory {


    private final SqlSessionFactory delegate;

    public NonCommittingSqlSessionFactory(SqlSessionFactory delegate) {
        this.delegate = delegate;
    }

    @Override
    public SqlSession openSession() {
        return new NonCommittingSqlSession(delegate.openSession());
    }

    @Override
    public SqlSession openSession(boolean b) {
        return new NonCommittingSqlSession(delegate.openSession(b));
    }

    @Override
    public SqlSession openSession(Connection connection) {
        return new NonCommittingSqlSession(delegate.openSession(connection));
    }

    @Override
    public SqlSession openSession(TransactionIsolationLevel transactionIsolationLevel) {
        return new NonCommittingSqlSession(delegate.openSession(transactionIsolationLevel));
    }

    @Override
    public SqlSession openSession(ExecutorType executorType) {
        return new NonCommittingSqlSession(delegate.openSession(executorType));
    }

    @Override
    public SqlSession openSession(ExecutorType executorType, boolean b) {
        return new NonCommittingSqlSession(delegate.openSession(executorType, b));
    }

    @Override
    public SqlSession openSession(ExecutorType executorType, TransactionIsolationLevel transactionIsolationLevel) {
        return new NonCommittingSqlSession(delegate.openSession(executorType, transactionIsolationLevel));
    }

    @Override
    public SqlSession openSession(ExecutorType executorType, Connection connection) {
        return new NonCommittingSqlSession(delegate.openSession(executorType, connection));
    }

    @Override
    public Configuration getConfiguration() {
        return delegate.getConfiguration();
    }
}
