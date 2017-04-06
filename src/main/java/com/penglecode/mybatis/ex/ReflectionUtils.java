package com.penglecode.mybatis.ex;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
/**
 * 反射工具类
 * 
 * @author	  	pengpeng
 * @date	  	2014年7月10日 下午10:10:24
 * @version  	1.0
 */
class ReflectionUtils {

	/**
	 * 获取字段值
	 * @param target
	 * @param field
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getFieldValue(Object target, Field field) {
		try {
			return (T) field.get(target);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 根据字段名从某个class及其父类中查找字段
	 * @param clazz
	 * @param name
	 * @return
	 */
	public static Field findField(Class<?> clazz, String name) {
        Class<?> searchType = clazz;
        while (!Object.class.equals(searchType) && searchType != null) {
            Field[] fields = searchType.getDeclaredFields();
            for (Field field : fields) {
                if ((name == null || name.equals(field.getName()))) {
                    return field;
                }
            }
            searchType = searchType.getSuperclass();
        }
        return null;
    }
	
	/**
     * 修改final字段的值
     *
     * @param targetClass
     * @param fieldName
     * @param newValue
     * @throws Exception
     */
    public static void setFinalFieldValue(Object target, String fieldName, Object newValue) {
        try {
        	setFinalFieldValue(target, findField(target.getClass(), fieldName), newValue);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }

    /**
     * 修改final字段的值
     *
     * @param target
     * @param field
     * @param newValue
     * @throws Exception
     */
    public static void setFinalFieldValue(Object target, Field field, Object newValue) {
        try {
        	field.setAccessible(true);
			final Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
			field.set(target, newValue);
			//设置完后再将修饰符改回去
			modifiersField.setInt(field, field.getModifiers() | Modifier.FINAL);
			modifiersField.setAccessible(false);
			field.setAccessible(false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
	
}
