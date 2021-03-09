package com.board.dao;
//DTO - > JDBCTemplate -> DAO
import static com.board.db.JDBCTemplate.*; //내것처럼 쓸거야~ impotr STATIC



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.board.dto.MyBoardDto;
//DAO : Data Access Object : DB와 연결하는 객체 
public class MyBoardDao {
	
	//전체 출력
	//파라미터와 리턴타입 먼저 생각을 해보기 
	//파라미터 : 메소드 외부에서 전달되는 값을 받아서 메소드 내부에서 사용하는 변수
	//dao 에서는 파라미터는 db에 전달할 값 리턴타입 : db에서 가져온 값
	//리턴되는 값이 테이블이니 <컬렉션 혹은 배열>을 사용 순서가 있어야하니 list사용 
	public List<MyBoardDto> selectList() {
		
		// 1. diver연결
		// 2. 계정연결
		Connection con = getConnection();
		String sql = " SELECT SEQ, WRITER, TITLE, CONTENT, REGDATE "
				   + " FROM MYBOARD "
				   + " ORDER BY SEQ DESC ";
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<MyBoardDto> list = new ArrayList<MyBoardDto>(); //리턴할값을 list로 써놓음
		
		//MyBoardDto dto = new MyBoardDto(); 여기에 쓰면안되는 이유
		
		try {
			// 3. query 준비
			pstm = con.prepareStatement(sql);
			System.out.println(" 3 . query 준비");
			
			// 4. query 실행 및 리턴
			rs = pstm.executeQuery();
			while(rs.next()) {
				MyBoardDto dto = new MyBoardDto(); // try 밖으로 빼면 안되는 이유
				dto.setSeq(rs.getInt(1));
				dto.setWriter(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setRegdate(rs.getDate(5));
				
				list.add(dto);
				System.out.println("4.query 실행 및 리턴");
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
		} finally {
			// 5. db 종료
			close(rs);
			close(pstm);
			close(con);
			System.out.println(" 5 . db 종료 ");
		}
				
		return list;
	}
	//하나 출력
	//row 한줄이 리턴될거에요
	//조건 : 기본키 
	//리턴타입 MyBoardDto 파라미터 (int seq)
	//dao가 db에서 하나의 값을 가져오기 위해 where조건으로 pk를 사용 
	//가져올 값은 테이블이지만 하나의 값 row한개 
	public MyBoardDto selectOne(int seq) {
		
		//1.
		//2.
		Connection con = getConnection();
		String sql = " SELECT SEQ, WRITER, TITLE, CONTENT, REGDATE "
				   + " FROM MYBOARD "
				   + " WHERE SEQ = ? ";
		PreparedStatement pstm = null;
		ResultSet rs = null;
		MyBoardDto dto = null;
		
		
		try {
			//3.
			pstm = con.prepareStatement(sql);
			pstm.setInt(1,  seq); // 첫번째 물음표에 seq들어갈거에요
			System.out.println("3. query준비 : " + sql);
			
			rs = pstm.executeQuery();
			while (rs.next()) {
				dto = new MyBoardDto(rs.getInt("SEQ"),
									 rs.getString("WRITER"),
									 rs.getString("TITLE"),
									 rs.getString("CONTENT"),
									 rs.getDate("REGDATE"));
						
			}
			System.out.println(" 4 . query 실행 및 리턴");
			//4.
		} catch (SQLException e) {
			
		} finally {
			//5.
			close(rs);
			close(pstm);
			close(con);
			System.out.println(" 5 . db 종료");
		}
		
		//return null; 
		return dto;
	}
	
	//추가 
	//insert할 row 값을 보내주면 n개의 rows가 inserted됐다는 것이 리턴
	//INSERT INTO MYBOARD
	//VALUEW(MYBOARDSEQ.NEXTVAL, ?, ?, ?, SYSDATE); 값이 넘어가야하니 '?' -> PreparedStatement 써줘야함 
	//숫자가 나와야하니 int타입이 리턴 dao에서 db로 인서트 쿼리를 날림
	//몇개가 인서트 됐다고 int타입으로 리턴 
	public int insert(MyBoardDto dto) {
		Connection con = getConnection();
		String sql = " INSERT INTO MYBOARD "
				   + " VALUES (MYBOARDSEQ.NEXTVAL, ?, ?, ?, SYSDATE) ";
		PreparedStatement pstm = null;
		int res = 0;
		
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, dto.getWriter());
			pstm.setString(2, dto.getTitle());
			pstm.setString(3, dto.getContent());
			System.out.println("3. query 준비 : " + sql);
			
			res = pstm.executeUpdate();
			System.out.println("4. query 실행 및 리턴");
			
			if(res > 0) {
				commit(con);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//5.
			
			close(pstm);
			close(con);
			System.out.println(" 5 . db 종료");
		}
		
		return res;
	}
	
	//수정
	public int update(MyBoardDto dto) {
		Connection con = getConnection();
		String sql = " UPDATE MYBOARD "
				   + " SET TITLE = ?, CONTENT = ? "
				   + " WHERE SEQ = ? ";
		PreparedStatement pstm = null;
		int res = 0;
	try {
		pstm = con.prepareStatement(sql);
		pstm.setString(1, dto.getTitle());
		pstm.setString(2, dto.getContent());
		pstm.setInt(3, dto.getSeq());
		
		System.out.println("3. query준비 : " + sql);
		
		res = pstm.executeUpdate();
		if(res > 0) {
			commit(con);
		}
		System.out.println(" 4. query 실행 및 리턴");
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		close(pstm);
		close(con);
		System.out.println(" 5. db 종료");
	}
		return res;
	}
	//삭제
	//pk만 전달해서 삭제하자~
	//기본키만 전달해야 한줄만 삭제 가능~
	public int delete(int seq) {
		
		Connection con = getConnection();
		String sql = " DELETE FROM MYBOARD "
				   + " WHERE SEQ = ? ";
		PreparedStatement pstm = null;
		
		int res = 0;
		
		try {
			//3.
			pstm = con.prepareStatement(sql);
			pstm.setInt(1, seq);
			System.out.println(" 3. query 준비 : " + sql);
			//4.
			res = pstm.executeUpdate();
			System.out.println("4. query 실행 및 리턴");
			
			if ( res > 0) {
				commit(con);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstm);
			close(con);
			System.out.println( " 5. db 종료 ");
		}
		
		return res;
	}
}























