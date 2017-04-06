package com.penglecode.mybatis.ex;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.ExecutorType;
/**
 * Executor同步管理器
 * 实现原理：如果当前线程绑定了ExecutorType.BATCH,并且当前SqlSession使用的是DynamicExecutor的话,则DynamicExecutor实际用的是executors中的BatchExecutor,
 * 			 如果当前线程未绑定ExecutorType.BATCH,并且当前SqlSession使用的是DynamicExecutor的话,则DynamicExecutor实际用的是executors中的哪种类型的Executor将由DynamicExecutor.defaultExecutorType决定
 * 
 * @author	  	pengpeng
 * @date	  	2014年7月7日 下午10:59:23
 * @version  	1.0
 */
public abstract class ExecutorSynchronizationManager {

	private static final Log logger = LogFactory.getLog(ExecutorSynchronizationManager.class);
	
	/**
	 * currentExecutorType只会将ExecutorType.BATCH与当前线程绑定,
	 * 如果当前线程上没有绑定ExecutorType且当前SqlSession使用的是DynamicExecutor的话,则使用DynamicExecutor.defaultExecutorType
	 */
	private static final ThreadLocal<ExecutorType> currentExecutorType = new ThreadLocal<ExecutorType>();
	
	public static ExecutorType getCurrentExecutorType() {
		return currentExecutorType.get();
	}
	
	public static void beginBatchExecutorMode() {
		currentExecutorType.set(ExecutorType.BATCH);
		if(logger.isDebugEnabled()){
			logger.debug("Begin JDBC Batch execute mode for current SqlSession in current thread!");
		}
	}
	
	public static void endBatchExecutorMode() {
		currentExecutorType.remove();
		if(logger.isDebugEnabled()){
			logger.debug("End JDBC Batch execute mode for current SqlSession in current thread!");
		}
	}
	
}
