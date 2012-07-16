package net.jakubkorab.horo.db.camelMybatisDebug;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * @author jakub
 */
public class NonCommittingSqlSession implements SqlSession {

    private static Logger log = LoggerFactory.getLogger(NonCommittingSqlSession.class);
    private final SqlSession delegate;

    NonCommittingSqlSession(SqlSession delegate) {
        this.delegate = delegate;
    }

    @Override
    public Object selectOne(String s) {
        return delegate.selectOne(s);
    }

    @Override
    public Object selectOne(String s, Object o) {
        return delegate.selectOne(s, o);
    }

    @Override
    public List selectList(String s) {
        return delegate.selectList(s);
    }

    @Override
    public List selectList(String s, Object o) {
        return delegate.selectList(s, o);
    }

    @Override
    public List selectList(String s, Object o, RowBounds rowBounds) {
        return delegate.selectList(s, o, rowBounds);
    }

    @Override
    public Map selectMap(String s, String s1) {
        return delegate.selectMap(s, s1);
    }

    @Override
    public Map selectMap(String s, Object o, String s1) {
        return delegate.selectMap(s, o, s1);
    }

    @Override
    public Map selectMap(String s, Object o, String s1, RowBounds rowBounds) {
        return delegate.selectMap(s, o, s1, rowBounds);
    }

    @Override
    public void select(String s, Object o, ResultHandler resultHandler) {
        delegate.select(s, o, resultHandler);
    }

    @Override
    public void select(String s, ResultHandler resultHandler) {
        delegate.select(s, resultHandler);
    }

    @Override
    public void select(String s, Object o, RowBounds rowBounds, ResultHandler resultHandler) {
        delegate.select(s, o, rowBounds, resultHandler);
    }

    @Override
    public int insert(String s) {
        return delegate.insert(s);
    }

    @Override
    public int insert(String s, Object o) {
        return delegate.insert(s, o);
    }

    @Override
    public int update(String s) {
        return delegate.update(s);
    }

    @Override
    public int update(String s, Object o) {
        return delegate.update(s, o);
    }

    @Override
    public int delete(String s) {
        return delegate.delete(s);
    }

    @Override
    public int delete(String s, Object o) {
        return delegate.delete(s, o);
    }

    @Override
    public void commit() {
        log.warn("commit called - ignoring");
    }

    @Override
    public void commit(boolean b) {
        log.warn("commit called - ignoring");
    }

    @Override
    public void rollback() {
        log.warn("rollback called - ignoring");
    }

    @Override
    public void rollback(boolean b) {
        log.warn("rollback called - ignoring");
    }

    @Override
    public void close() {
        delegate.close();
    }

    @Override
    public void clearCache() {
        delegate.clearCache();
    }

    @Override
    public Configuration getConfiguration() {
        return delegate.getConfiguration();
    }

    @Override
    public <T> T getMapper(Class<T> tClass) {
        return delegate.getMapper(tClass);
    }

    @Override
    public Connection getConnection() {
        return delegate.getConnection();
    }
}
