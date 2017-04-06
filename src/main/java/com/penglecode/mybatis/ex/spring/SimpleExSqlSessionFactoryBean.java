package com.penglecode.mybatis.ex.spring;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.util.Assert;

import com.penglecode.mybatis.ex.ExSqlSessionFactoryBuilder;
import com.penglecode.mybatis.ex.ExXMLConfigBuilder;
/**
 * 一个简单的SqlSessionFactory工厂bean,默认使用{@code ExSqlSessionFactoryBuilder}来创建{@code DefaultExSqlSessionFactory}}
 * 使用示例：
 * 		<bean id="sqlSessionFactory" class="com.penglecode.mybatis.ex.spring.SimpleExSqlSessionFactoryBean">
 * 			<!-- 其中configLocation和dataSource必须指定 -->
 *	        <property name="configLocation" value="classpath:/mybatis/mybatis-config.xml"/>
 *	        <property name="dataSource" ref="dataSource"/>
 *	    </bean>
 * 
 * 
 * @author	  	pengpeng
 * @date	  	2014年7月12日 下午4:41:52
 * @version  	1.0
 */
public class SimpleExSqlSessionFactoryBean implements FactoryBean<SqlSessionFactory>, InitializingBean, ApplicationListener<ApplicationEvent> {

	protected Resource configLocation;
	
	protected DataSource dataSource;
	
	protected TransactionFactory transactionFactory;
	
	protected SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new ExSqlSessionFactoryBuilder();
	
	protected SqlSessionFactory sqlSessionFactory;

	protected String environment = SimpleExSqlSessionFactoryBean.class.getSimpleName(); // EnvironmentAware requires spring 3.1

	protected DatabaseIdProvider databaseIdProvider;
	
	protected boolean failFast;
	
	public void setConfigLocation(Resource configLocation) {
		this.configLocation = configLocation;
	}

	public void setDataSource(DataSource dataSource) {
		if (dataSource instanceof TransactionAwareDataSourceProxy) {
			// If we got a TransactionAwareDataSourceProxy, we need to perform
			// transactions for its underlying target DataSource, else data
			// access code won't see properly exposed transactions (i.e.
			// transactions for the target DataSource).
			this.dataSource = ((TransactionAwareDataSourceProxy) dataSource).getTargetDataSource();
		} else {
			this.dataSource = dataSource;
		}
	}
	
	/**
	 * Set the MyBatis TransactionFactory to use. Default is {@code SpringManagedTransactionFactory}
	 * @param transactionFactory
	 */
	public void setTransactionFactory(TransactionFactory transactionFactory) {
		this.transactionFactory = transactionFactory;
	}

	/**
	 * Default is {@code ExSqlSessionFactoryBuilder}
	 * @param sqlSessionFactoryBuilder
	 */
	public void setSqlSessionFactoryBuilder(SqlSessionFactoryBuilder sqlSessionFactoryBuilder) {
		this.sqlSessionFactoryBuilder = sqlSessionFactoryBuilder;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public DatabaseIdProvider getDatabaseIdProvider() {
		return databaseIdProvider;
	}

	public void setDatabaseIdProvider(DatabaseIdProvider databaseIdProvider) {
		this.databaseIdProvider = databaseIdProvider;
	}

	public void setFailFast(boolean failFast) {
		this.failFast = failFast;
	}

	public SqlSessionFactory getObject() throws Exception {
		if (this.sqlSessionFactory == null) {
			afterPropertiesSet();
		}
		return this.sqlSessionFactory;
	}

	public Class<? extends SqlSessionFactory> getObjectType() {
		return this.sqlSessionFactory == null ? SqlSessionFactory.class : this.sqlSessionFactory.getClass();
	}

	public boolean isSingleton() {
		return true;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(configLocation, "Property 'configLocation' is required");
		Assert.notNull(dataSource, "Property 'dataSource' is required");
		Assert.notNull(sqlSessionFactoryBuilder, "Property 'sqlSessionFactoryBuilder' is required");
	    this.sqlSessionFactory = buildSqlSessionFactory();
	}
	
	public void onApplicationEvent(ApplicationEvent event) {
		if (failFast && event instanceof ContextRefreshedEvent) {
			// fail-fast -> check all statements are completed
			this.sqlSessionFactory.getConfiguration().getMappedStatementNames();
		}
	}
	
	protected SqlSessionFactory buildSqlSessionFactory() throws IOException {
		ExXMLConfigBuilder parser = new ExXMLConfigBuilder(this.configLocation.getInputStream(), environment, null);
		Configuration configuration = parser.parse();
		
		if (this.transactionFactory == null) {
			this.transactionFactory = new SpringManagedTransactionFactory();
		}

		Environment environment = new Environment(this.environment, this.transactionFactory, this.dataSource);
		configuration.setEnvironment(environment);
		
		if (this.databaseIdProvider != null) {
			try {
				configuration.setDatabaseId(this.databaseIdProvider.getDatabaseId(this.dataSource));
			} catch (SQLException e) {
				throw new NestedIOException("Failed getting a databaseId", e);
			}
		}
		return this.sqlSessionFactoryBuilder.build(configuration);
	}
	
}
