package com.muldel.dao;
//dao 인터페이스를 통해 daoimpl 만들기 
//sql을 상수로 박아버리기 

import java.util.List;

import com.muldel.dto.MDBoardDto;

public interface MDBoardDao {
	
	String SELECT_LIST_SQL = " SELECT SEQ, WRITER, TITLE, CONTENT, REGDATE "
						   + " FROM MDBOARD "
						   + " ORDER BY SEQ DESC " ;
	String SELECT_ONE_SQL = " SELECT SEQ, WRITER, TITLE, CONTENT, REGDATE "
						  + " FROM MDBOARD "
						  + " WHERE SEQ = ? ";
	String INSERT_SQL = " INSERT INTO MDBOARD "
					  + " VALUES(MDBOARDSEQ.NEXTVAL, ?, ?, ?, SYSDATE) ";
	String UPDATE_SQL = " UPDATE MDBOARD "
				      + " SET TITLE = ?, CONTENT = ? "
				      + " WHERE SEQ = ? " ;
	String DELETE_SQL = "DELETE FROM MDBOARD WHERE SEQ = ? ";
		
	
	public List <MDBoardDto> selectList();
	public MDBoardDto selectOne(int seq);
	public int insert(MDBoardDto dto);
	public int update(MDBoardDto dto);
	public int delete(int seq);
	public int multidelete(String[] seqs);
	//multi 이기 때문에 파라미터를 String 배열로! 
	//int로 하면 안되는 이유 		

}
