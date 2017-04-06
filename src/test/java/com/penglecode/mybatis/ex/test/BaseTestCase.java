package com.penglecode.mybatis.ex.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.penglecode.mybatis.ex.ExSqlSessionFactoryBuilder;
/**
 * 纯Mybatis测试基类,不与Spring整合的
 * 
 * @author	  	pengpeng
 * @date	  	2014年7月9日 上午9:58:26
 * @version  	1.0
 */
public abstract class BaseTestCase {

	private final Logger logger = LoggerFactory.getLogger(BaseTestCase.class);

	private SqlSessionFactory sqlSessionFactory;

	public BaseTestCase(){
		try {
			logger.debug(">>> Load mybatis configuration!");
			String resource = "mybatis/mybatis-config.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new ExSqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			logger.error("Load mybatis configuration failed!", e);
		}
	}

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}
	
}
