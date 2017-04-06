package com.penglecode.mybatis.ex;

import org.apache.ibatis.session.SqlSession;
/**
 * Batch操作的模板类
 * 
 * 如果当前SqlSession或底层SqlSession中的executor是DynamicExecutor类型的
 * 那么在SqlSessionCallback回调方法中将以BATCH模式来执行SqlSession的各种操作
 * 
 * @author	  	pengpeng
 * @date	  	2014年7月9日 下午3:07:09
 * @version  	1.0
 */
public class BatchTemplate {

	private final SqlSession sqlSession;
	
	public BatchTemplate(SqlSession sqlSession) {
		super();
		if(sqlSession == null){
			throw new IllegalArgumentException("Parameter 'sqlSession' can not be null!");
		}
		this.sqlSession = sqlSession;
	}
	
	/**
	 * 如果当前sqlSession中的executor是DynamicExecutor类型的
	 * 那么在execute方法中将以BATCH模式来执行SqlSession的各种操作
	 * @param action
	 * @return
	 */
	public <T> T execute(SqlSessionCallback<T> action) {
		try {
			ExecutorSynchronizationManager.beginBatchExecutorMode();
			return action.doInSqlSession(sqlSession);
		} finally {
			ExecutorSynchronizationManager.endBatchExecutorMode();
		}
	}
	
}
