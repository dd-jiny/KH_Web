package com.muldel.dao;

import static com.muldel.db.JDBCTemplate.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.muldel.dto.MDBoardDto;


public class MDBoardDaoImpl implements MDBoardDao {

	@Override
	public List<MDBoardDto> selectList() {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<MDBoardDto> list = new ArrayList<MDBoardDto>();
		
		try {
			//3.
			pstm = con.prepareStatement(SELECT_LIST_SQL);
			System.out.println("3. query 준비 : " + SELECT_LIST_SQL);
			
			//4.
			rs = pstm.executeQuery();
			System.out.println("4. query 실행 및 리턴");
			while (rs.next()) {
				MDBoardDto dto = new MDBoardDto();
				dto.setSeq(rs.getInt("SEQ"));
				dto.setWriter(rs.getString("WRITER"));
				dto.setTitle(rs.getString("TITLE"));
				dto.setContent(rs.getString("CONTENT"));
				dto.setRegdate(rs.getDate("REGDATE"));
				
				list.add(dto);
				
			}
			
			}catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(rs);
				close(pstm);
				close(con);
				System.out.println("5. db 종료");
				
			}
			
		return list;
	}

	@Override
	public MDBoardDto selectOne(int seq) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		MDBoardDto dto = null;
		
		try {
			//3.
			pstm = con.prepareStatement(SELECT_ONE_SQL);
			pstm.setInt(1, seq);
			System.out.println(" 3 . query 준비 : " + SELECT_ONE_SQL);
			
			//4.
			rs = pstm.executeQuery();
			while (rs.next()) {
				dto = new MDBoardDto();
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
		}
		
		
		return dto;
	}

	@Override
	public int insert(MDBoardDto dto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res = 0;
		
		try {
			pstm = con.prepareStatement(INSERT_SQL);
			pstm.setString(1, dto.getWriter());
			pstm.setString(2, dto.getTitle());   // 파라미터 dto로 들어오는 값
			pstm.setString(3, dto.getContent());
			System.out.println("3. query 준비 : " +  INSERT_SQL);
			
			res = pstm.executeUpdate();
			System.out.println(" 4. query 실행 및 리턴 ");
			
			if(res > 0) {
				commit(con);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//5.
			
			close(pstm);
			close(con);
			System.out.println("DB 종료");
		}
		return res;
	}

	@Override
	public int update(MDBoardDto dto) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res = 0;
		
		try {
			pstm = con.prepareStatement(UPDATE_SQL);
			pstm.setString(1, dto.getTitle());
			pstm.setString(2, dto.getContent());
			pstm.setInt(3, dto.getSeq());
			
			System.out.println("3. query 준비 : " + UPDATE_SQL);
			
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int multidelete(String[] seq) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res = 0;
		
		int[] cnt = null;
		
		try {
			pstm = con.prepareStatement(DELETE_SQL);
			for (int i = 0; i < seq.length; i++) {
				pstm.setString(1, seq[i]);
				//seq값을 int로 넣어도 되고 string으로 넣어도 돼
				//메모리에 적재해놓고 executeBatch() 메소드가 호출 될 떄, 한번에 실행!
				pstm.addBatch();
				System.out.println("3. querty 준비 : " + DELETE_SQL + " ( 삭제할 번호 : " + seq[i] + ")");
			}
			
			//메모리에 적재되어있는 sql문들을 한번에 실행!
			//int[] 로 리턴됨!!!
			
			cnt = pstm.executeBatch();
			System.out.println("4.query 실행 및 리턴");
			
			//-2 : 성공 , -3 : 실패
			
			for (int i = 0; i < cnt.length; i++) {
				if (cnt[i] == -2) {
					res++;
				}
			}
			
			//갯수 확인
			if(seq.length == res) {
				commit(con);
			}
			
		} catch (SQLException e) {
				
			e.printStackTrace();
		} finally {
			close(pstm);
			close(con);
			//con, pstm이 계속 연결되어있으면 내가 글을 작성했는데 다른 사람이 작성했다고 나올 수 있다.
			System.out.println(" 5.db 종료 ");
			}
		
		return res;
	}
	/*
	executeQuery();
	executeUpdate();   -> 실행된 쿼리 개수 반환 
	executeBatch();  -> 해당쿼리 성공 : -2, 실패 -3
	배열 0번지의 값이 성공했다면 -2, 1번지의 값이 성공했다면 -2
	*/
}





















