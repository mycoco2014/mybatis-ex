package com.penglecode.mybatis.ex.test.util;

public class MapperUtils {

	public static String getMapperKey(Class<?> mapperClass, String statementKey) {
		return mapperClass.getName() + "Mapper." + statementKey;
	}
	
}
