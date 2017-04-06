package com.penglecode.mybatis.ex.spring;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

import com.penglecode.mybatis.ex.AbstractExSqlSession;

/**
 * {@link #SqlSessionTemplate}的代理类,增加了batch方法,一个简单的使用配置如下：
 * 
 * <bean id="sqlSessionFactory" class="com.penglecode.mybatis.ex.spring.SimpleExSqlSessionFactoryBean">
 *	    <property name="configLocation" value="classpath:/mybatis/mybatis-config.xml"/>
 *	    <property name="dataSource" ref="dataSource"/>
 * </bean>
 * 
 * <bean id="defaultSqlSessionTemplate" class="com.penglecode.mybatis.ex.spring.ExSqlSessionTemplate">
 *		<constructor-arg index="0" ref="sqlSessionFactory"/>
 *		<constructor-arg index="1" ref="SIMPLE"/>
 *	</bean>
 * 
 * @author	  	pengpeng
 * @date	  	2014年6月18日 下午6:26:20
 * @version  	1.0
 */
public class ExSqlSessionTemplate extends AbstractExSqlSession {

    private SqlSession delegate;

    public ExSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
    	this(sqlSessionFactory, sqlSessionFactory.getConfiguration().getDefaultExecutorType());
    }
    
    public ExSqlSessionTemplate(SqlSessionFactory sqlSessionFactory, ExecutorType executorType) {
    	delegate = new SqlSessionTemplate(sqlSessionFactory, executorType);
    }
	
	public void clearCache() {
		delegate.clearCache();
	}

	public void close() {
		delegate.close();
	}

	public void commit() {
		delegate.commit();
	}

	public void commit(boolean force) {
		delegate.commit(force);
	}

	public int delete(String statement) {
		return delegate.delete(statement);
	}

	public int delete(String statement, Object parameter) {
		return delegate.delete(statement, parameter);
	}

	public List<BatchResult> flushStatements() {
		return delegate.flushStatements();
	}

	public Configuration getConfiguration() {
		return delegate.getConfiguration();
	}

	public Connection getConnection() {
		return delegate.getConnection();
	}

	public <T> T getMapper(Class<T> type) {
		return delegate.getMapper(type);
	}

	public int insert(String statement) {
		return delegate.insert(statement);
	}

	public int insert(String statement, Object parameter) {
		return delegate.insert(statement, parameter);
	}

	public void rollback() {
		delegate.rollback();
	}

	public void rollback(boolean force) {
		delegate.rollback(force);
	}

	public void select(String statement, ResultHandler handler) {
		delegate.select(statement, handler);
	}

	public void select(String statement, Object parameter, ResultHandler handler) {
		delegate.select(statement, parameter, handler);
	}

	public void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler) {
		delegate.select(statement, parameter, rowBounds, handler);
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

	public <K, V> Map<K, V> selectMap(String statement, String parameter) {
		return delegate.selectMap(statement, parameter);
	}

	public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
		return delegate.selectMap(statement, parameter, mapKey);
	}

	public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds) {
		return delegate.selectMap(statement, parameter, mapKey, rowBounds);
	}

	public <T> T selectOne(String statement) {
		return delegate.selectOne(statement);
	}

	public <T> T selectOne(String statement, Object parameter) {
		return delegate.selectOne(statement, parameter);
	}

	public int update(String statement) {
		return delegate.update(statement);
	}

	public int update(String statement, Object parameter) {
		return delegate.update(statement, parameter);
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
