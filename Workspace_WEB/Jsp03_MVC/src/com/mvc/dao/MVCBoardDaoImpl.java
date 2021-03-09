package com.mvc.dao;
import static com.mvc.db.JDBCTemplate.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mvc.dto.MVCBoardDto;

public class MVCBoardDaoImpl implements MVCBoardDao {

	@Override
	public List<MVCBoardDto> selectList() {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<MVCBoardDto> list = new ArrayList<MVCBoardDto>();
		
		try {
			pstm = con.prepareStatement(SELECT_LIST_SQL);
			System.out.println(" 3 . query 준비 : " + SELECT_LIST_SQL);
			
			rs = pstm.executeQuery();
			System.out.println(" 4 . query 실행 및 리턴");
			while(rs.next()) {
				MVCBoardDto dto = new MVCBoardDto();
					dto.setSeq(rs.getInt("SEQ"));
					dto.setWriter(rs.getString("WRITER"));
					dto.setTitle(rs.getString("TITLE"));
					dto.setContent(rs.getString("CONTENT"));
					dto.setRegdate(rs.getDate("REGDATE"));
					
					list.add(dto);
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstm);
			close(con);
			
			System.out.println(" 5 . db 종료");
		}
		
		return list;
	}

	@Override
	public MVCBoardDto selectOne(int seq) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		MVCBoardDto dto = null;
		
		try {
			pstm = con.prepareStatement(SELECT_ONE_SQL);
			pstm.setInt(1, seq);
			System.out.println(" 3. query 준비 : " + SELECT_ONE_SQL);
			
			rs = pstm.executeQuery();
			System.out.println(" 4 . query 실행 및 리턴");
			while (rs.next()) {
				dto = new MVCBoardDto();
				dto.setSeq(rs.getInt("SEQ"));
				dto.setWriter(rs.getString("WRITER"));
				dto.setTitle(rs.getString("TITLE"));
				dto.setContent(rs.getString("CONTENT"));
				dto.setRegdate(rs.getDate("REGDATE"));
				
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
			close(rs);
			close(pstm);
			close(con);
			System.out.println(" 5. db 종료 ");
		}
		
		return dto;
	}

	@Override
	public int insert(MVCBoardDto dto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		
		int res = 0;
		
		try {
			pstm = con.prepareStatement(INSERT_SQL);
			pstm.setString(1, dto.getWriter());
			pstm.setString(2, dto.getTitle());
			pstm.setString(3, dto.getContent());
			System.out.println("3. query 준비 : " + INSERT_SQL);
			
			res = pstm.executeUpdate();
			System.out.println(" 4. query 실행 및 리턴 ");
			
			if(res > 0) {
				commit(con);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
			close(pstm);
			close(con);
			System.out.println("DB 종료");
		}
		
		return res;
	}

	@Override
	public int update(MVCBoardDto dto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		
		int res = 0;
		
		try {
			pstm = con.prepareStatement(UPDATE_SQL);
			pstm.setString(1, dto.getTitle());
			pstm.setString(2, dto.getContent());
			pstm.setInt(3, dto.getSeq());
			
			System.out.println(" 3. query 준비 : " + UPDATE_SQL);
			
			res = pstm.executeUpdate();
			if(res > 0) {
				commit(con);
			}
			System.out.println(" 4. query 실행 및 리턴 ");
		} catch (SQLException e) {
		
			e.printStackTrace();
		} finally {
			close(pstm);
			close(con);
			System.out.println(" 5. db 종료");
		}
		return res;
	}

	@Override
	public int delete(int seq) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res = 0;
		
		try {
			pstm = con.prepareStatement(DELETE_SQL);
			pstm.setInt(1, seq);
			System.out.println(" 3. query 준비 : " + DELETE_SQL);
			
			//4
			res = pstm.executeUpdate();
			System.out.println(" 4. query 실행 및 리턴 ");
			
			if(res > 0) {
				commit(con);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			close(pstm);
			close(con);
			System.out.println(" 5. db 종료 ");
		}
		return res;
	}

	

}
