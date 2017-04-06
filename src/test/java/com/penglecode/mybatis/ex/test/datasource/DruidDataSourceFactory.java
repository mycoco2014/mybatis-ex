package com.penglecode.mybatis.ex.test.datasource;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.DataSourceFactory;

import com.alibaba.druid.pool.DruidDataSource;

public class DruidDataSourceFactory implements DataSourceFactory {

	private final DruidDataSource dataSource;
	
	public DruidDataSourceFactory() {
		super();
		this.dataSource = new DruidDataSource();
	}

	public void setProperties(Properties props) {
		dataSource.setDriverClassName(props.getProperty("driverClassName"));
		dataSource.setUrl(props.getProperty("url"));
		dataSource.setUsername(props.getProperty("username"));
		dataSource.setPassword(props.getProperty("password"));
        
		dataSource.setInitialSize(Integer.parseInt(props.getProperty("initialSize","5")));
		dataSource.setMaxActive(Integer.parseInt(props.getProperty("maxActive","20")));
        dataSource.setMinIdle(Integer.parseInt(props.getProperty("minIdle","5")));
        dataSource.setMaxWait(Long.parseLong(props.getProperty("maxWait","3000")));
        dataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(props.getProperty("timeBetweenEvictionRunsMillis", "30000")));
        dataSource.setDefaultAutoCommit(Boolean.parseBoolean(props.getProperty("defaultAutoCommit","false")));
        dataSource.setConnectionProperties(props.getProperty("connectionProperties", "rewriteBatchedStatements=true"));
	}

	public DataSource getDataSource() {
		return this.dataSource;
	}

}
