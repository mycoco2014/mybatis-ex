package com.penglecode.mybatis.ex;

import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.session.Configuration;
/**
 * 扩展的XMLConfigBuilder,用于创建ExConfiguration
 * 
 * @author	  	pengpeng
 * @date	  	2014年7月7日 下午9:58:17
 * @version  	1.0
 */
public class ExXMLConfigBuilder extends XMLConfigBuilder {

	public ExXMLConfigBuilder(InputStream inputStream, String environment, Properties props) {
		super(inputStream, environment, props);
		setConfiguration();
	}

	public ExXMLConfigBuilder(InputStream inputStream, String environment) {
		super(inputStream, environment);
		setConfiguration();
	}

	public ExXMLConfigBuilder(InputStream inputStream) {
		super(inputStream);
		setConfiguration();
	}

	public ExXMLConfigBuilder(Reader reader, String environment, Properties props) {
		super(reader, environment, props);
		setConfiguration();
	}

	public ExXMLConfigBuilder(Reader reader, String environment) {
		super(reader, environment);
		setConfiguration();
	}

	public ExXMLConfigBuilder(Reader reader) {
		super(reader);
		setConfiguration();
	}
	
	
	protected void setConfiguration() {
		Configuration configuration = new ExConfiguration();
		ReflectionUtils.setFinalFieldValue(this, "configuration", configuration);
		ReflectionUtils.setFinalFieldValue(this, "typeAliasRegistry", configuration.getTypeAliasRegistry());
		ReflectionUtils.setFinalFieldValue(this, "typeHandlerRegistry", configuration.getTypeHandlerRegistry());
	}
	
}
