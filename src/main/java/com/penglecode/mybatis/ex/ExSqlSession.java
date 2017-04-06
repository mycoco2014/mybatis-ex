package com.penglecode.mybatis.ex;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
/**
 * 扩展的SqlSession接口,增加了批量insert、update、delete的方法
 * 
 * @author	  	pengpeng
 * @date	  	2014年7月11日 下午1:29:39
 * @version  	1.0
 */
public interface ExSqlSession extends SqlSession {

	/**
	 * 使用jdbc Batch特性批量insert语句
	 * @param <T>
	 * @param statementKey
	 * @param paramObjList
	 * @return
	 */
	public <T> int[] batchInsert(String statementKey, List<T> paramObjList);

	/**
	 * 使用jdbc Batch特性批量update语句
	 * @param <T>
	 * @param statementKey
	 * @param paramObjList
	 * @param flushBatchSize	- 即多少笔批量操作执行一次jdbc底层的executeBatch方法并返回结果
	 * @return
	 */
	public <T> int[] batchInsert(String statementKey, List<T> paramObjList, int flushBatchSize);
	
	/**
	 * 使用jdbc Batch特性批量update语句
	 * @param <T>
	 * @param statementKey
	 * @param paramObjList
	 * @return
	 */
	public <T> int[] batchUpdate(String statementKey, List<T> paramObjList);
	
	/**
	 * 使用jdbc Batch特性批量update语句
	 * @param <T>
	 * @param statementKey
	 * @param paramObjList
	 * @param flushBatchSize	- 即多少笔批量操作执行一次jdbc底层的executeBatch方法并返回结果
	 * @return
	 */
	public <T> int[] batchUpdate(String statementKey, List<T> paramObjList, int flushBatchSize);

	/**
	 * 使用jdbc Batch特性批量delete语句
	 * @param <T>
	 * @param statementKey
	 * @param paramObjList
	 * @return
	 */
	public <T> int[] batchDelete(String statementKey, List<T> paramObjList);
	
	/**
	 * 使用jdbc Batch特性批量delete语句
	 * @param <T>
	 * @param statementKey
	 * @param paramObjList
	 * @param flushBatchSize	- 即多少笔批量操作执行一次jdbc底层的executeBatch方法并返回结果
	 * @return
	 */
	public <T> int[] batchDelete(String statementKey, List<T> paramObjList, int flushBatchSize);
	
}
