package com.penglecode.mybatis.ex;

import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.executor.CachingExecutor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.transaction.Transaction;
/**
 * 针对DynamicExecutor扩展的Configuration
 * 
 * @author	  	pengpeng
 * @date	  	2014年7月6日 下午10:23:47
 * @version  	1.0
 */
public class ExConfiguration extends Configuration {

	protected final MapperRegistry mapperRegistry = new MapperRegistry(this);
	
	public Executor newExecutor(Transaction transaction, ExecutorType executorType) {
		Executor executor = new DynamicExecutor(this, transaction, executorType);
		if (cacheEnabled) {
			executor = new CachingExecutor(executor);
		}
		executor = (Executor) interceptorChain.pluginAll(executor);
		return executor;
	}
	
}
