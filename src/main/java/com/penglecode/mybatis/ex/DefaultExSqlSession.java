package com.penglecode.mybatis.ex;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
/**
 * 一个SqlSession代理类,同时扩充了几个批量方法(insert、update、delete)
 * 
 * @author	  	pengpeng
 * @date	  	2014年7月7日 下午9:51:48
 * @version  	1.0
 */
public class DefaultExSqlSession extends AbstractExSqlSession {

	private final SqlSession delegate;
	
	public DefaultExSqlSession(SqlSession delegate) {
		super();
		this.delegate = delegate;
	}

	public <T> T selectOne(String statement) {
		return delegate.selectOne(statement);
	}

	public <T> T selectOne(String statement, Object parameter) {
		return delegate.selectOne(statement, parameter);
	}

	public <E> List<E> selectList(String statement) {
		return delegate.selectList(statement);
	}

	public <E> List<E> selectList(String statement, Object parameter) {
		return delegate.selectList(statement, parameter);
	}

	public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
		return delegate.selectList(statement, parameter, rowBounds);
	}

	public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
		return delegate.selectMap(statement, mapKey);
	}

	public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
		return delegate.selectMap(statement, parameter, mapKey);
	}

	public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds) {
		return delegate.selectMap(statement, parameter, mapKey, rowBounds);
	}

	public void select(String statement, Object parameter, ResultHandler handler) {
		delegate.select(statement, parameter, handler);
	}

	public void select(String statement, ResultHandler handler) {
		delegate.select(statement, handler);
	}

	public void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler) {
		delegate.select(statement, parameter, rowBounds, handler);
	}

	public int insert(String statement) {
		return delegate.insert(statement);
	}

	public int insert(String statement, Object parameter) {
		return delegate.insert(statement, parameter);
	}

	public int update(String statement) {
		return delegate.update(statement);
	}

	public int update(String statement, Object parameter) {
		return delegate.update(statement, parameter);
	}

	public int delete(String statement) {
		return delegate.delete(statement);
	}

	public int delete(String statement, Object parameter) {
		return delegate.delete(statement, parameter);
	}

	public void commit() {
		delegate.commit();
	}

	public void commit(boolean force) {
		delegate.commit(force);
	}

	public void rollback() {
		delegate.rollback();
	}

	public void rollback(boolean force) {
		delegate.rollback(force);
	}

	public List<BatchResult> flushStatements() {
		return delegate.flushStatements();
	}

	public void close() {
		delegate.close();
	}

	public void clearCache() {
		delegate.clearCache();
	}

	public Configuration getConfiguration() {
		return delegate.getConfiguration();
	}

	public <T> T getMapper(Class<T> type) {
		return delegate.getMapper(type);
	}

	public Connection getConnection() {
		return delegate.getConnection();
	}

	public SqlSession getDelegate() {
		return delegate;
	}
	
	public <T> Cursor<T> selectCursor(String arg0) {
		return delegate.selectCursor(arg0);
	}

	public <T> Cursor<T> selectCursor(String arg0, Object arg1) {
		return delegate.selectCursor(arg0, arg1);
	}

	public <T> Cursor<T> selectCursor(String arg0, Object arg1, RowBounds arg2) {
		return delegate.selectCursor(arg0, arg1, arg2);
	}

}
