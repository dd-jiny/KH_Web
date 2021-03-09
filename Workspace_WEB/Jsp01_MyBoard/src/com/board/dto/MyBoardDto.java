package com.board.dto;

import java.util.Date;
//Dto 먼저 만들러 가기 
//Data Transfer Object : 값 전달 객체 (Vo - Value Object)
//DB Table의 row 한 개 값을 저장할 수 있음. 
public class MyBoardDto {
	
	private int seq;                //-> primary key 적용
	private String writer;
	private String title;
	private String content;
	private Date regdate;
	//Java.sql 이 아니라 왜 java.util을 사용하였을까나

	public MyBoardDto() {
		
	}  //기본 생성자
	
	public MyBoardDto(int seq, String writer, String title, String content, Date regdate) {
		
		this.seq = seq;
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.regdate = regdate;
		
		//필드 초기화 생성자 
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	
	
}