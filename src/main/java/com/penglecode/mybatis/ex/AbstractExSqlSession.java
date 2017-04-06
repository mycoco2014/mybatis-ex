package com.penglecode.mybatis.ex;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.SqlSession;

/**
 * ExSqlSession的实现基类,此处实现了批量insert、update、delete的jdbc batch特性的使用
 * 
 * @author	  	pengpeng
 * @date	  	2014年7月7日 下午10:30:54
 * @version  	1.0
 */
public abstract class AbstractExSqlSession implements ExSqlSession {

	/**
	 * 大批量操作时,每2500笔批量操作后执行jdbc操作：stmt.executeBatch();提交部分操作的结果到数据库
	 */
	public static final int DEFAULT_FLUSH_BATCH_SIZE = 2500;
	
	private final BatchTemplate batchTemplate = new BatchTemplate(this);
	
	public <T> int[] batchInsert(String statementKey, List<T> paramObjList) {
		return batchInsert(statementKey, paramObjList, DEFAULT_FLUSH_BATCH_SIZE);
	}

	public <T> int[] batchInsert(final String statementKey, final List<T> paramObjList, final int flushBatchSize) {
		if(statementKey == null || statementKey.trim().equals("")){
			throw new IllegalArgumentException("parameter 'statementKey' can't be null or empty!");
		}
		if(paramObjList == null || paramObjList.isEmpty()){
			throw new IllegalArgumentException("parameter 'paramObjList' can't be null or empty!");
		}
		if(flushBatchSize <= 0){
			throw new IllegalArgumentException("parameter 'flushBatchSize' must be greater than zero!");
		}
		final List<Integer> batchResultList = new ArrayList<Integer>();
		batchTemplate.execute(new SqlSessionCallback<List<Integer>>(){
			public List<Integer> doInSqlSession(SqlSession sqlSession) {
				for (int i = 0; i < paramObjList.size(); i++) {
					sqlSession.insert(statementKey, paramObjList.get(i));
					if((i + 1) % flushBatchSize == 0){
						List<BatchResult> currentBatchResults = sqlSession.flushStatements();
						BatchResultUtils.extractBatchResult(batchResultList, currentBatchResults);
					}
				}
				List<BatchResult> currentBatchResults = sqlSession.flushStatements();
				BatchResultUtils.extractBatchResult(batchResultList, currentBatchResults);
				return batchResultList;
			}
		});
		return BatchResultUtils.toPrimitive(batchResultList);
	}

	public <T> int[] batchUpdate(String statementKey, List<T> paramObjList) {
		return batchUpdate(statementKey, paramObjList, DEFAULT_FLUSH_BATCH_SIZE);
	}

	public <T> int[] batchUpdate(final String statementKey, final List<T> paramObjList, final int flushBatchSize) {
		if(statementKey == null || statementKey.trim().equals("")){
			throw new IllegalArgumentException("parameter 'statementKey' can't be null or empty!");
		}
		if(paramObjList == null || paramObjList.isEmpty()){
			throw new IllegalArgumentException("parameter 'paramObjList' can't be null or empty!");
		}
		if(flushBatchSize <= 0){
			throw new IllegalArgumentException("parameter 'flushBatchSize' must be greater than zero!");
		}
		final List<Integer> batchResultList = new ArrayList<Integer>();
		batchTemplate.execute(new SqlSessionCallback<List<Integer>>(){
			public List<Integer> doInSqlSession(SqlSession sqlSession) {
				for (int i = 0; i < paramObjList.size(); i++) {
					sqlSession.update(statementKey, paramObjList.get(i));
					if((i + 1) % flushBatchSize == 0){
						List<BatchResult> currentBatchResults = sqlSession.flushStatements();
						BatchResultUtils.extractBatchResult(batchResultList, currentBatchResults);
					}
				}
				List<BatchResult> currentBatchResults = sqlSession.flushStatements();
				BatchResultUtils.extractBatchResult(batchResultList, currentBatchResults);
				return batchResultList;
			}
		});
		return BatchResultUtils.toPrimitive(batchResultList);
	}

	public <T> int[] batchDelete(String statementKey, List<T> paramObjList) {
		return batchDelete(statementKey, paramObjList, DEFAULT_FLUSH_BATCH_SIZE);
	}

	public <T> int[] batchDelete(final String statementKey, final List<T> paramObjList, final int flushBatchSize) {
		if(statementKey == null || statementKey.trim().equals("")){
			throw new IllegalArgumentException("parameter 'statementKey' can't be null or empty!");
		}
		if(paramObjList == null || paramObjList.isEmpty()){
			throw new IllegalArgumentException("parameter 'paramObjList' can't be null or empty!");
		}
		if(flushBatchSize <= 0){
			throw new IllegalArgumentException("parameter 'flushBatchSize' must be greater than zero!");
		}
		final List<Integer> batchResultList = new ArrayList<Integer>();
		batchTemplate.execute(new SqlSessionCallback<List<Integer>>(){
			public List<Integer> doInSqlSession(SqlSession sqlSession) {
				for (int i = 0; i < paramObjList.size(); i++) {
					sqlSession.delete(statementKey, paramObjList.get(i));
					if((i + 1) % flushBatchSize == 0){
						List<BatchResult> currentBatchResults = sqlSession.flushStatements();
						BatchResultUtils.extractBatchResult(batchResultList, currentBatchResults);
					}
				}
				List<BatchResult> currentBatchResults = sqlSession.flushStatements();
				BatchResultUtils.extractBatchResult(batchResultList, currentBatchResults);
				return batchResultList;
			}
		});
		return BatchResultUtils.toPrimitive(batchResultList);
	}
	
}
