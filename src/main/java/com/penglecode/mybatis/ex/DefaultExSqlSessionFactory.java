package com.penglecode.mybatis.ex;

import java.sql.Connection;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;

/**
 * DefaultExSqlSession的默认工厂
 * 
 * @author	  	pengpeng
 * @date	  	2014年7月7日 下午9:56:23
 * @version  	1.0
 */
public class DefaultExSqlSessionFactory extends DefaultSqlSessionFactory {

	public DefaultExSqlSessionFactory(Configuration configuration) {
		super(configuration);
	}

	public SqlSession openSession() {
		return wrap(super.openSession());
	}

	public SqlSession openSession(boolean autoCommit) {
		return wrap(super.openSession(autoCommit));
	}

	public SqlSession openSession(ExecutorType execType) {
		return wrap(super.openSession(execType));
	}

	public SqlSession openSession(TransactionIsolationLevel level) {
		return wrap(super.openSession(level));
	}

	public SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level) {
		return wrap(super.openSession(execType, level));
	}

	public SqlSession openSession(ExecutorType execType, boolean autoCommit) {
		return wrap(super.openSession(execType, autoCommit));
	}

	public SqlSession openSession(Connection connection) {
		return wrap(super.openSession(connection));
	}

	public SqlSession openSession(ExecutorType execType, Connection connection) {
		return wrap(super.openSession(execType, connection));
	}

	protected SqlSession wrap(SqlSession sqlSession){
		return new DefaultExSqlSession(sqlSession);
	}
	
}
