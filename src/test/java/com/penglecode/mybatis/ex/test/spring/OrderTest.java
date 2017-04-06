package com.penglecode.mybatis.ex.test.spring;

import static com.penglecode.mybatis.ex.test.util.MapperUtils.getMapperKey;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.penglecode.mybatis.ex.ExSqlSession;
import com.penglecode.mybatis.ex.test.bean.ChildOrder;
import com.penglecode.mybatis.ex.test.bean.MainOrder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/applicationContext-dao.xml"})
@TransactionConfiguration(defaultRollback=false, transactionManager="jdbcTransactionManager")
public class OrderTest {

	@Resource(name="sqlSessionTemplate")
	private ExSqlSession sqlSessionTemplate;
	
	@Test
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
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
		int affectedRow = sqlSessionTemplate.insert(getMapperKey(MainOrder.class, "insertMainOrder"), mainOrder);
		System.out.println(">>> inserted rows " + affectedRow);
		int[] affectedRows = sqlSessionTemplate.batchInsert(getMapperKey(ChildOrder.class, "insertChildOrder"), childOrderList);
		System.out.println(">>> inserted rows " + affectedRows.length);
	}
	
}
