<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@page import="com.muldel.dao.MDBoardDao"%>
<%@page import="com.muldel.dao.MDBoardDaoImpl" %>
<%@page import="com.muldel.dto.MDBoardDto" %>
<%@page import="com.muldel.biz.MDBoardBiz" %>
<%@page import="com.muldel.biz.MDBoardBizImpl" %>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<%
//biz 객체 선언하여 db와 연결 
	int seq = Integer.parseInt(request.getParameter("seq"));

	//MDBoardDao dao = new MDBoardDaoImpl(); dao말고 biz로 해야하는 이유
	MDBoardBiz biz = new MDBoardBizImpl();
	MDBoardDto dto = biz.selectOne(seq);

%>
<body>

	<%@ include file="./form/header.jsp" %>
	
	<h1>selectOne</h1>
	
	<table border="1">
		<tr>
			<th>작성자</th>
			<td><%=dto.getWriter() %></td>
		</tr>
		
		<tr>
			<th>제목</th>
			<td><%=dto.getTitle() %></td>
		</tr>
		
		<tr>
			<th>내용</th>
			<td><textarea rows="10" cols="60" readonly="readonly"><%=dto.getContent() %></textarea></td>
		</tr>
		
		<tr>
			<td colspan="2" align="right">
				<input type="button" value="목록" onclick="location.href='./boardlist.jsp'" />
				<input type="button" value="수정" onclick="location.href='boardupdate.jsp?seq=<%=dto.getSeq() %>'" />
				<input type="button" value="삭제" onclick="location.href='boarddelete.jsp?seq=<%=dto.getSeq() %>'" />
			</td>
		</tr>
	</table>
	
	<%@ include file="./form/footer.jsp" %>


</body>
</html>