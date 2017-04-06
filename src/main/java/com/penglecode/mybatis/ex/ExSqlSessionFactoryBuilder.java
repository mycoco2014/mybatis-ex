package com.penglecode.mybatis.ex;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

import org.apache.ibatis.exceptions.ExceptionFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.penglecode.mybatis.ex.spring.SimpleExSqlSessionFactoryBean;
/**
 * 扩展的SqlSessionFactory builder类,用于构建DefaultExSqlSessionFactory,使用:
 * 
 * @see SimpleExSqlSessionFactoryBean
 * @author	  	pengpeng
 * @date	  	2014年7月7日 下午11:00:18
 * @version  	1.0
 */
public class ExSqlSessionFactoryBuilder extends SqlSessionFactoryBuilder {

	public SqlSessionFactory build(Reader reader) {
		return super.build(reader);
	}

	public SqlSessionFactory build(Reader reader, String environment) {
		return super.build(reader, environment);
	}

	public SqlSessionFactory build(Reader reader, Properties properties) {
		return super.build(reader, properties);
	}

	public SqlSessionFactory build(Reader reader, String environment, Properties properties) {
		try {
			ExXMLConfigBuilder parser = new ExXMLConfigBuilder(reader, environment, properties);
			return build(parser.parse());
		} catch (Exception e) {
			throw ExceptionFactory.wrapException("Error building SqlSession.", e);
		} finally {
			ErrorContext.instance().reset();
			try {
				reader.close();
			} catch (IOException e) {
				// Intentionally ignore. Prefer previous error.
			}
		}
	}

	public SqlSessionFactory build(InputStream inputStream) {
		return super.build(inputStream);
	}

	public SqlSessionFactory build(InputStream inputStream, String environment) {
		return super.build(inputStream, environment);
	}

	public SqlSessionFactory build(InputStream inputStream,
			Properties properties) {
		return super.build(inputStream, properties);
	}

	public SqlSessionFactory build(InputStream inputStream, String environment, Properties properties) {
		try {
			ExXMLConfigBuilder parser = new ExXMLConfigBuilder(inputStream, environment, properties);
			return build(parser.parse());
		} catch (Exception e) {
			throw ExceptionFactory.wrapException("Error building SqlSession.", e);
		} finally {
			ErrorContext.instance().reset();
			try {
				inputStream.close();
			} catch (IOException e) {
				// Intentionally ignore. Prefer previous error.
			}
		}
	}

	public SqlSessionFactory build(Configuration configuration) {
		if(configuration instanceof ExConfiguration){
			return new DefaultExSqlSessionFactory(configuration);
		}
		throw new IllegalArgumentException("Parameter 'configuration' must be type of " + ExConfiguration.class);
	}

}
