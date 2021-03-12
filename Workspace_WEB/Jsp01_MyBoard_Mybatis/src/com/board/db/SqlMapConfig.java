package com.board.db;

import java.io.IOException;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;



//https://mybatis.org/mybatis-3/ko/getting-started.html
public class SqlMapConfig {

	private SqlSessionFactory sqlSessionFactory; 
	
	public SqlSessionFactory getSqlSessionFactory(){
		
		//경로일때는 / 로 구분 
		String resource = "com/board/db/mybatis-config.xml";
		
		try {
			InputStream inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			
			inputStream.close();
			
			
		
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return sqlSessionFactory;
		
	
	
	
	}
	
	
	
	
	
}
