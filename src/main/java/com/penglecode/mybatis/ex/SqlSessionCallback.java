package com.penglecode.mybatis.ex;

import org.apache.ibatis.session.SqlSession;
/**
 * SqlSession回调类
 * @param <T>
 * @author	  	pengpeng
 * @date	  	2014年7月11日 下午1:26:02
 * @version  	1.0
 */
public interface SqlSessionCallback<T> {

	public T doInSqlSession(SqlSession sqlSession);
	
}
