package com.penglecode.mybatis.ex;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.BatchExecutor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.ReuseExecutor;
import org.apache.ibatis.executor.SimpleExecutor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;
/**
 * 动态Executor
 * 
 * 工作机制：如果当前线程绑定了ExecutorType.BATCH,则该DynamicExecutor实际用的是executors中的BatchExecutor,
 * 			 如果当前线程未绑定ExecutorType.BATCH,则该DynamicExecutor实际用的是executors中的哪种类型的Executor将由DynamicExecutor.defaultExecutorType决定
 * 
 * @author	  	pengpeng
 * @date	  	2014年7月6日 下午10:07:02
 * @version  	1.0
 */
public class DynamicExecutor implements Executor {

	protected final Map<ExecutorType, Executor> executors;
	
	/**
	 * 该DynamicExecutor拥有者(SqlSession)的默认ExecutorType(注意：该ExecutorType并不是Configuration.defaultExecutorType)
	 * 如果当前线程中没有绑定
	 */
	protected final ExecutorType defaultExecutorType;
	
	protected Transaction transaction;
	
	protected Configuration configuration;
	
	public DynamicExecutor(Configuration configuration, Transaction transaction, ExecutorType defaultExecutorType) {
		this.configuration = configuration;
		this.transaction = transaction;
		this.defaultExecutorType = defaultExecutorType;
		this.executors = new HashMap<ExecutorType, Executor>();
		this.executors.put(ExecutorType.SIMPLE, new SimpleExecutor(configuration, transaction));
		this.executors.put(ExecutorType.BATCH, new BatchExecutor(configuration, transaction));
		this.executors.put(ExecutorType.REUSE, new ReuseExecutor(configuration, transaction));
	}
	
	protected Executor determineExecutor() {
		ExecutorType executorType = ExecutorSynchronizationManager.getCurrentExecutorType();
		return executors.get(executorType == null ? defaultExecutorType : executorType);
	}
	
	public int update(MappedStatement ms, Object parameter) throws SQLException {
		return determineExecutor().update(ms, parameter);
	}

	public <E> List<E> query(MappedStatement ms, Object parameter,
			RowBounds rowBounds, ResultHandler resultHandler,
			CacheKey cacheKey, BoundSql boundSql) throws SQLException {
		return determineExecutor().query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
	}

	public <E> List<E> query(MappedStatement ms, Object parameter,
			RowBounds rowBounds, ResultHandler resultHandler)
			throws SQLException {
		return determineExecutor().query(ms, parameter, rowBounds, resultHandler);
	}

	public List<BatchResult> flushStatements() throws SQLException {
		return determineExecutor().flushStatements();
	}

	public void commit(boolean required) throws SQLException {
		determineExecutor().commit(required);
	}

	public void rollback(boolean required) throws SQLException {
		determineExecutor().rollback(required);
	}

	public CacheKey createCacheKey(MappedStatement ms, Object parameterObject,
			RowBounds rowBounds, BoundSql boundSql) {
		return determineExecutor().createCacheKey(ms, parameterObject, rowBounds, boundSql);
	}

	public boolean isCached(MappedStatement ms, CacheKey key) {
		return determineExecutor().isCached(ms, key);
	}

	public void clearLocalCache() {
		determineExecutor().clearLocalCache();
	}

	public void deferLoad(MappedStatement ms, MetaObject resultObject,
			String property, CacheKey key, Class<?> targetType) {
		determineExecutor().deferLoad(ms, resultObject, property, key, targetType);
	}

	public Transaction getTransaction() {
		return determineExecutor().getTransaction();
	}

	public void close(boolean forceRollback) {
		determineExecutor().close(forceRollback);
	}

	public boolean isClosed() {
		return determineExecutor().isClosed();
	}

	public void setExecutorWrapper(Executor executor) {
		determineExecutor().setExecutorWrapper(executor);
	}

	public <E> Cursor<E> queryCursor(MappedStatement arg0, Object arg1, RowBounds arg2) throws SQLException {
		return determineExecutor().queryCursor(arg0, arg1, arg2);
	}
	
}