package com.cal.dao;

import static com.cal.db.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.management.remote.JMXConnectionNotification;

import com.cal.dto.CalDto;
import com.sun.net.httpserver.Authenticator.Result;


public class CalDao {
	public int insertCalBoard(CalDto dto) {
		
		////SELECT SEQ, TITLE, CONTENT, MDATE, REGDATE
		Connection con = getConnection();
		String sql = " INSERT INTO CALBOARD "
				+ " VALUES(CALBOARDSEQ.NEXTVAL,?,?,?,?,SYSDATE) ";

		PreparedStatement pstm = null;
		int res = 0;
		
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, dto.getId());
			pstm.setString(2, dto.getTitle());
			pstm.setString(3, dto.getContent());
			pstm.setString(4, dto.getMdate());
			System.out.println("3, query 준비 :"+sql);
			
			res = pstm.executeUpdate();
			System.out.println("4, query 실행 및 리턴");
			if(res>0) {
				commit(con);
				
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			close(pstm);
			close(con);
			System.out.println("5, db 종료");
		}
		
		
		
		
		return res;
	}
	
	public List<CalDto> getCalList(String id, String yyyyMMdd){
		
		Connection con = getConnection();
		String sql = " SELECT SEQ, ID, TITLE, CONTENT, MDATE, REGDATE "
				+ " FROM CALBOARD "
				+ " WHERE ID =? "
				+ " AND SUBSTR(MDATE,1,8) =? ";
		
		PreparedStatement pstm = null; 
		ResultSet rs = null;
		List<CalDto> list = new ArrayList<CalDto>();
		
		try {
			pstm=con.prepareStatement(sql);
			pstm.setString(1, id);
			pstm.setString(2, yyyyMMdd);
			System.out.println("3.query 준비:" +sql);
			rs = pstm.executeQuery();
			System.out.println("4.query 실행 및 리턴 ");
			while(rs.next()) {
				CalDto dto = new CalDto(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getDate(6));
				list.add(dto);
			}
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstm);
			close(con);
		}
		
		
		
		
		return list;
		
	}
	
	public List<CalDto> getCalViewList(String id,String yyyyMM){
		
		Connection con = getConnection();
		String sql=" SELECT *"
				+ "FROM "
				+" ( "
				+ "	SELECT(ROW_NUMBER() OVER(PARTITION BY SUBSTR(MDATE,1,8) ORDER BY MDATE))RN,SEQ,ID,TITLE,CONTENT,MDATE,REGDATE "
				+ "	FROM CALBOARD "
				+ "	WHERE ID=? "
				+ "	AND SUBSTR(MDATE,1,6)=?"
				+ " ) "
				+ "WHERE RN BETWEEN 1 AND 4";
		
		PreparedStatement pstm = null; 
		ResultSet rs = null; 
		List<CalDto> list= new ArrayList<CalDto>();
		
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, id);
			pstm.setString(2, yyyyMM);
			System.out.println("3.query 준비 :" +sql);
			
			rs= pstm.executeQuery();
			System.out.println("4.query 실행 및 리턴");
			while(rs.next()) {
				CalDto dto = new CalDto(rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getDate(7));
						list.add(dto);
						
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstm);
			close(con);
		}
		
		
		
		return list;
	}
	
	
	//" SELECT SEQ, ID, TITLE, CONTENT, MDATE, REGDATE "
	//+ " FROM CALBOARD "
	
	public CalDto selectCal(int seq ) {
		Connection con = getConnection();
		String sql = " SELECT ID,TITLE,CONTENT,MDATE "
				+ " FROM CALBOARD "
				+ " WHERE SEQ = ?";
		
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CalDto dto = new CalDto();
		try {
			pstm=con.prepareStatement(sql);
			pstm.setInt(1, seq);
			System.out.println("3.query 준비"+sql);
			
			rs = pstm.executeQuery();
			System.out.println("4.query 실행 및 리턴");
			while(rs.next()) {
				dto.setId(rs.getString(1));
				dto.setTitle(rs.getString(2));
				dto.setContent(rs.getString(3));
				dto.setMdate(rs.getString(4));
				
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstm);
			close(con);
			System.out.println("5.db 종료");
		}
		
		return dto;
		
	}
	
	public int getCalViewCount(String id, String yyyyMMdd) {
		
		Connection con = getConnection();
		String sql = " SELECT COUNT(*)"
				+ " FROM CALBOARD"
				+ " WHERE ID = ?"
				+ " AND SUBSTR(MDATE,1,8)=? ";
		
		
		PreparedStatement pstm = null; 
		ResultSet rs = null;
		int count = 0; 
		
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, id);
			pstm.setString(2, yyyyMMdd);
			System.out.println("3.query 준비"+sql);
			
			rs = pstm.executeQuery();
			System.out.println("4.query 실행 및 리턴");
			while(rs.next()) {
				count = rs.getInt(1);
				
				
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstm);
			close(con);
			System.out.println("5.db종료");
		}
		return count;
	}
	
}
