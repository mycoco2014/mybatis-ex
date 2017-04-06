package com.penglecode.mybatis.ex.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.penglecode.mybatis.ex.BatchResultUtils;
import com.penglecode.mybatis.ex.BatchTemplate;
import com.penglecode.mybatis.ex.SqlSessionCallback;
import com.penglecode.mybatis.ex.test.bean.Product;

import static com.penglecode.mybatis.ex.test.util.MapperUtils.*;

public class ProductTest extends BaseTestCase {

	/**
	 * 不使用Batch
	 * @throws Exception
	 */
	@Test
	public void testNormalInsertProduct() throws Exception {
		SqlSession sqlSession = null;
		final Random random = new Random();
		try {
			sqlSession = getSqlSessionFactory().openSession(ExecutorType.SIMPLE, false);
			for(int i = 1; i <= 10; i++){
				double rd = random.nextDouble();
				Product product = new Product((long)i, "iPhone 5s 土豪金-" + i, 10000 * rd, 50 * rd, "1", "iPhone 5s 土豪金-" + i, "0", "2014-07-10 14:22:45", null);
				int affectedRow = sqlSession.insert(getMapperKey(Product.class, "insertProduct"), product);
				System.out.println(">>> inserted rows " + affectedRow);
			}
			sqlSession.commit();
		} catch(Exception e) {
			sqlSession.rollback();
			e.printStackTrace();
		} finally {
			if(sqlSession != null){
				sqlSession.close();
			}
		}
	}
	
	/**
	 * batch和非batch两种混用
	 * @throws Exception
	 */
	@Test
	public void testBatchInsertProduct1() throws Exception {
		SqlSession sqlSession = null;
		final Random random = new Random();
		try {
			sqlSession = getSqlSessionFactory().openSession(ExecutorType.SIMPLE, false);
			//1、非batch方式执行insert
			for(int i = 1; i <= 5; i++){
				double rd = random.nextDouble();
				Product product = new Product((long)i, "iPhone 5s 土豪金-" + i, 10000 * rd, 50 * rd, "1", "iPhone 5s 土豪金-" + i, "0", "2014-07-10 14:22:45", null);
				int affectedRow = sqlSession.insert(getMapperKey(Product.class, "insertProduct"), product);
				System.out.println(">>> inserted rows " + affectedRow);
			}
			//2、以batch方式批量执行insert
			BatchTemplate batchTemplate = new BatchTemplate(sqlSession);
			List<Integer> batchResultList = batchTemplate.execute(new SqlSessionCallback<List<Integer>>(){
				public List<Integer> doInSqlSession(SqlSession sqlSession) {
					List<Integer> list = new ArrayList<Integer>();
					for(int i = 6; i <= 10; i++){
						double rd = random.nextDouble();
						Product product = new Product((long)i, "iPhone 5s 土豪金-" + i, 10000 * rd, 50 * rd, "1", "iPhone 5s 土豪金-" + i, "0", "2014-07-10 14:22:45", null);
						sqlSession.insert(getMapperKey(Product.class, "insertProduct"), product);
					}
					BatchResultUtils.extractBatchResult(list, sqlSession.flushStatements());
					return list;
				}
			});
			System.out.println(">>> batch inserted rows " + batchResultList.size());
			sqlSession.commit();
		} catch(Exception e) {
			sqlSession.rollback();
			e.printStackTrace();
		} finally {
			if(sqlSession != null){
				sqlSession.close();
			}
		}
	}
	
	/**
	 * 测试batch的性能
	 * 插入10w条数据,本机数据库大概需要27秒左右
	 * 插入10w条数据,本机数据库大概需要5秒左右(开启MySQL jdbc驱动的batch模式,即添加连接参数rewriteBatchedStatements=true)
	 * @throws Exception
	 */
	@Test
	public void testBatchInsertPerformance() throws Exception {
		long beginMillis = System.currentTimeMillis();
		SqlSession sqlSession = null;
		final Random random = new Random();
		try {
			sqlSession = getSqlSessionFactory().openSession(ExecutorType.SIMPLE, false);
			BatchTemplate batchTemplate = new BatchTemplate(sqlSession);
			List<Integer> batchResultList = batchTemplate.execute(new SqlSessionCallback<List<Integer>>(){
				public List<Integer> doInSqlSession(SqlSession sqlSession) {
					List<Integer> list = new ArrayList<Integer>();
					for(int i = 1; i <= 100000; i++){
						double rd = random.nextDouble();
						Product product = new Product((long)i, "iPhone 5s 土豪金-" + i, 10000 * rd, 50 * rd, "1", "iPhone 5s 土豪金-" + i, "0", "2014-07-10 14:22:45", null);
						sqlSession.insert(getMapperKey(Product.class, "insertProduct"), product);
						if(i % 2500 == 0){
							BatchResultUtils.extractBatchResult(list, sqlSession.flushStatements());
						}
					}
					BatchResultUtils.extractBatchResult(list, sqlSession.flushStatements());
					return list;
				}
			});
			System.out.println(">>> batch inserted rows " + batchResultList.size());
			sqlSession.commit();
			long endMillis = System.currentTimeMillis();
			System.out.println(">>> Total cost " + (endMillis - beginMillis) + " ms");
		} catch(Exception e) {
			sqlSession.rollback();
			e.printStackTrace();
		} finally {
			if(sqlSession != null){
				sqlSession.close();
			}
		}
	}
	
}
