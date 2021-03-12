package com.board.dao;



import java.util.ArrayList;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.board.db.SqlMapConfig;
import com.board.dto.MyBoardDto;


public class MyBoardDao extends SqlMapConfig {
	
	public List<MyBoardDto> selectList(){
		SqlSession session = null;
		
		List<MyBoardDto> list = new ArrayList<MyBoardDto>();
		
		session = getSqlSessionFactory().openSession(true);
		//SqlSession 객체를 얻을 때 openSession(true)와 같이 호출하면 
		//INSERT, UPDATE, DELETE문 실행 시 auto commit을 수행하는 
		//SqlSession 객체를 얻을 수 있다.
		
		
		list= session.selectList("boardmapper.selectList");
		
		session.close();
		
		
		
		return list;
	}
	
	public MyBoardDto selectOne(int seq) {
		
		SqlSession session = null;
		MyBoardDto dto = new MyBoardDto();
		
		session = getSqlSessionFactory().openSession(true);
		try {
			dto = session.selectOne("boardmapper.selectOne",seq);
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {
			session.close();
		}
		return dto;
	}
	
	public int insert(MyBoardDto dto) {
		int res = 0; 
		try(SqlSession session = getSqlSessionFactory().openSession(true);){
			res = session.insert("boardmapper.insert",dto);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return res;
		
	}
	
	
	
	
	public int update(MyBoardDto dto) {
		int res = 0; 
		try(SqlSession session = getSqlSessionFactory().openSession(true);){
			res = session.insert("boardmapper.update",dto);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return res;
	}
	
	public int delete(MyBoardDto dto) {
		int res = 0; 
		try(SqlSession session = getSqlSessionFactory().openSession(true);){
			res = session.insert("boardmapper.delete",dto);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}
}
