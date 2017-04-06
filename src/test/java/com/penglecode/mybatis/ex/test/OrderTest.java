package com.penglecode.mybatis.ex.test;

import static com.penglecode.mybatis.ex.test.util.MapperUtils.getMapperKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.ibatis.session.ExecutorType;
import org.junit.Test;

import com.penglecode.mybatis.ex.ExSqlSession;
import com.penglecode.mybatis.ex.test.bean.ChildOrder;
import com.penglecode.mybatis.ex.test.bean.MainOrder;

public class OrderTest extends BaseTestCase {

	/**
	 * @throws Exception
	 */
	@Test
	public void testInsertOrder() throws Exception {
		MainOrder mainOrder = new MainOrder();
		mainOrder.setOrderId(100001L);
		mainOrder.setUserId(1L);
		mainOrder.setTotalMoney(210.0);
		mainOrder.setTotalFreight(10.0);
		mainOrder.setDiscount(1.0);
		mainOrder.setOrderTime("2014-07-11 14:55:35");
		mainOrder.setStatus("0");
		List<ChildOrder> childOrderList = new ArrayList<ChildOrder>();
		for(int i = 1; i <= 5; i++){
			ChildOrder childOrder = new ChildOrder();
			childOrder.setOrderId(Long.valueOf(mainOrder.getOrderId().toString() + i));
			childOrder.setMainOrderId(mainOrder.getOrderId());
			childOrder.setProductId(10001L);
			childOrder.setProductName("iPhone 5S 硅胶皮套");
			childOrder.setBuyNum(1);
			childOrder.setUnitPrice(20.0);
			childOrder.setFreight(2.0);
			childOrder.setOrderTime(mainOrder.getOrderTime());
			childOrder.setSubTotal(22.0);
			childOrderList.add(childOrder);
		}
		ExSqlSession sqlSession = null;
		try {
			sqlSession = (ExSqlSession) getSqlSessionFactory().openSession(ExecutorType.SIMPLE, false);
			int affectedRow = sqlSession.insert(getMapperKey(MainOrder.class, "insertMainOrder"), mainOrder);
			System.out.println(">>> inserted rows " + affectedRow);
			int[] affectedRows = sqlSession.batchInsert(getMapperKey(ChildOrder.class, "insertChildOrder"), childOrderList);
			System.out.println(">>> inserted rows " + affectedRows.length);
			sqlSession.commit();
		} catch (Exception e) {
			sqlSession.rollback();
			e.printStackTrace();
		} finally {
			if(sqlSession != null){
				sqlSession.close();
			}
		}
	}
	
	/**
	 * @throws Exception
	 */
	@Test
	public void testInsertOrder1() throws Exception {
		final Random random = new Random();
		List<MainOrder> mainOrderList = new ArrayList<MainOrder>();
		List<ChildOrder> childOrderList = new ArrayList<ChildOrder>();
		for(long i = 1; i <= 100000; i++){
			MainOrder mainOrder = new MainOrder();
			mainOrder.setOrderId(System.currentTimeMillis() + i);
			mainOrder.setUserId(1L);
			mainOrder.setTotalMoney(210.0);
			mainOrder.setTotalFreight(10.0);
			mainOrder.setDiscount(1.0);
			mainOrder.setOrderTime("2014-07-22 14:55:35");
			mainOrder.setStatus("0");
			
			for(int j = 1; j <= 5; j++){
				ChildOrder childOrder = new ChildOrder();
				childOrder.setOrderId(Long.valueOf(mainOrder.getOrderId().toString() + j));
				childOrder.setMainOrderId(mainOrder.getOrderId());
				childOrder.setProductId((long)random.nextInt(100000));
				childOrder.setProductName("iPhone 5S 硅胶皮套" + childOrder.getProductId());
				childOrder.setBuyNum(1);
				childOrder.setUnitPrice(20.0);
				childOrder.setFreight(2.0);
				childOrder.setOrderTime(mainOrder.getOrderTime());
				childOrder.setSubTotal(22.0);
				childOrderList.add(childOrder);
			}
			mainOrderList.add(mainOrder);
		}
		ExSqlSession sqlSession = null;
		try {
			sqlSession = (ExSqlSession) getSqlSessionFactory().openSession(ExecutorType.SIMPLE, false);
			int[] affectedRows = sqlSession.batchInsert(getMapperKey(MainOrder.class, "insertMainOrder"), mainOrderList);
			System.out.println(">>> main order inserted rows " + affectedRows.length);
			affectedRows = sqlSession.batchInsert(getMapperKey(ChildOrder.class, "insertChildOrder"), childOrderList);
			System.out.println(">>> main order inserted rows " + affectedRows.length);
			sqlSession.commit();
		} catch (Exception e) {
			sqlSession.rollback();
			e.printStackTrace();
		} finally {
			if(sqlSession != null){
				sqlSession.close();
			}
		}
	}
	
}
