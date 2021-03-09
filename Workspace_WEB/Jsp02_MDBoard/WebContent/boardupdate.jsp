<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset+UTF-8"); %>

<%@page import="com.muldel.biz.MDBoardBiz" %>
<%@page import="com.muldel.biz.MDBoardBizImpl" %>
<%@page import="com.muldel.dto.MDBoardDto" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
	int seq = Integer.parseInt(request.getParameter("seq"));

	MDBoardBiz biz = new MDBoardBizImpl();
	MDBoardDto dto = biz.selectOne(seq);
%>

	<%@ include file="./form/header.jsp" %>

	<h1>update</h1>
	
	<form action="boardupdate_res.jsp" method="post">
	<input type="hidden" name="seq" value="<%=dto.getSeq() %>" />
		<table border="1">
			<tr>
				<th>작성자</th>
				<td><%=dto.getWriter() %></td>
			</tr>
			<tr>
				<th>제목</th>
				<td><input type="text" name= "title" value="<%=dto.getTitle() %>"/></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea rows="10" cols="60" name="content"><%=dto.getContent() %></textarea></td>
			</tr>
			<tr>
				<td colspan="2" align="right">
					<input type="submit" value="작성" />
					<input type="button" value="취소" onclick=""/>
				</td>
			</tr>
		</table>
	</form>
	
	<%@ include file="./form/footer.jsp" %>

</body>
</html>






































