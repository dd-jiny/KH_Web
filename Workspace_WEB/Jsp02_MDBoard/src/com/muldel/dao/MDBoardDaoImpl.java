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
		//extends JDBCTemplate도 사용가능 하지만 메모리 효율은 import static이 더 좋음 왠만하면 후자를 쓰자 
	@Override
	public List<MDBoardDto> selectList() {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<MDBoardDto> list = new ArrayList<MDBoardDto>();
		//MDBoardDto dto; -> 메모리에 dto 객체가 하나만 만들어져서 list의 0번지 1번지 2번지등등  모두가 하나의 dto객체만 참조해서 하나의 글만 나옴 똑같은 글 계속 나옴
		//이렇게 만들고 try 안에 MDBoardDto dto = new MDBoardDto(); -> dto = new MDBoardDto(); 이렇게 해서 돌려보기 
		try {
			//3.
			pstm = con.prepareStatement(SELECT_LIST_SQL);
			System.out.println("3. query 준비 : " + SELECT_LIST_SQL);
			
			//4.
			rs = pstm.executeQuery();
			System.out.println("4. query 실행 및 리턴");
			while (rs.next()) {  //rs.next 반복하면서 하나씩 하나씩 가지고 올거에요
				MDBoardDto dto = new MDBoardDto();  //변수의 스코프 - > 변수의 위치에 따라 생명주기가 달라진다 
					dto.setSeq(rs.getInt("SEQ"));
					dto.setWriter(rs.getString("WRITER"));
					dto.setTitle(rs.getString("TITLE"));
					dto.setContent(rs.getString("CONTENT"));
					dto.setRegdate(rs.getDate("REGDATE"));
			//set을 쓰냐 rs.get만쓰냐의 차이 -> set을 통해 값을 넣어줄거냐 그냥 생성자에 바로 값을 넣어줄거냐의 차이 
					list.add(dto);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstm);
			close(con);
			System.out.println("5. db 종료 ");
		}
		
		return list;
	}

	@Override
	public MDBoardDto selectOne(int seq) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		MDBoardDto dto = null;
		//MDBoardDto dto = null;  와 MDBoardDto dto = new MDBoardDto(); 요곳의 차이 npe hell 검색
		
		try {
			//3
			pstm = con.prepareStatement(SELECT_ONE_SQL);
			pstm.setInt(1, seq);
			System.out.println(" 3. query준비 : " + SELECT_ONE_SQL);
			
			//4.
			rs = pstm.executeQuery();
			System.out.println(" 4. query 실행 및 리턴 ");
			while (rs.next()) {
				dto = new MDBoardDto();
				dto.setSeq(rs.getInt("SEQ")); //(1) 숫자 입력가능
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
			System.out.println( " 5. db 종료 ");
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
			pstm.setString(2, dto.getTitle());
			pstm.setString(3, dto.getContent());
			System.out.println( " 3. query 준비 : " + INSERT_SQL);
			
			res = pstm.executeUpdate();
			System.out.println(" 4 . query 실행 및 리턴 ");
			
			if(res > 0) {
				commit(con);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			//5
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
			
			System.out.println(" 3. query 준비 : " + UPDATE_SQL);
			
			res = pstm.executeUpdate();
			if(res > 0) {
				commit(con);
			}
			System.out.println(" 4. query 실행 및 리턴");
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

	@Override
	public int delete(int seq) {
		
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res = 0;
		
		try {
			//3
			pstm = con.prepareStatement(DELETE_SQL);
			pstm.setInt(1, seq);
			System.out.println(" 3 . query 준비 : " + DELETE_SQL);
			
			//4
			res = pstm.executeUpdate();
			System.out.println(" 4 . query 실행 및 리턴");
			
			if ( res > 0 ) {
				commit(con);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			close(pstm);
			close(con);
			System.out.println(" 5. db 종료 ");
		}
		
		return res;
	}

	@Override
	public int multidelete(String[] seqs) {
		Connection con = getConnection();
		PreparedStatement pstm = null;
		int res = 0;
		
		int [] cnt = null;
		
		try {
			pstm = con.prepareStatement(DELETE_SQL);
			for (int i = 0; i < seqs.length; i++) {
				pstm.setString(1, seqs[i]);
				//seq값을 int로 넣어도 되고 string으로 넣어도 돼
				
				//addBatch()로 메모리에 적재해놓고 executeBatch() 메소드가 호출 될 떄, 한번에 실행!
				pstm.addBatch();
				System.out.println("3. query 준비 : " + DELETE_SQL);
			}
			//executeBatch
			//메모리에 적재되어있는 sql문들을 한번에 실행!
			//int[] 로 리턴됨!!!
			//-2 : 성공 , -3 : 실패   -> [-2,-2, -3, -2] 이런 int 배열 형대로 리턴
			cnt = pstm.executeBatch();
			System.out.println("4.query 실행 및 리턴");
			
			
			
			for (int i = 0; i < cnt.length; i++) {
				//cnt 배열 0번지 1번지 2번지의 결과 값 확인 -2면 성공 -3이면 실패 
				if ( cnt[i] == -2) {
					res++;
				}
			}
			//갯수 확인
			if(seqs.length == res) {
				commit(con);
			} 
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			close(pstm);
			close(con);
			//con, pstm이 계속 연결되어있으면 내가 글을 작성했는데 다른 사람이 작성했다고 나올 수 있다.
			System.out.println(" db 종료 ");
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
