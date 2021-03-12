<%@page import="com.cal.dto.CalDto"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
	
	Calendar cal = Calendar.getInstance();
	int hour = cal.get(Calendar.HOUR_OF_DAY);
	int min = cal.get(Calendar.MINUTE);


	String year = request.getParameter("year");
	String month = request.getParameter("month");
	String date = request.getParameter("date");

%>




<h1>일정 보기</h1>
	
		
		<table border="1">
			<tr>
				<th>ID</th>
				<td>${dto.id }</td>
			</tr>
			<tr>
				<th>일정</th>
				<td><%=year %>년 <%=month %>월 <%=date %>일 <%=hour %>시 <%=min %>분</td>
			</tr>
			
			<tr>
				<th>제목</th>
				<td>${dto.title }</td>
			</tr>
			
			<tr>
				<th>내용</th>
				<td><textarea rows="10" cols="60" name="content" readonly="readonly">${dto.content }</textarea></td>
			</tr>
			
			<tr>
				<td colspan="2" align="right">
					<input type="button" value="일정수정" onclick="location.href='cal.do?command=update&year=<%=year %>&month=<%=month %>&date=<%=date %>'">
					<input type="button" value="목록" onclick="location.href='cal.do?command=list&year=<%=year %>&month=<%=month %>&date=<%=date %>'">
					<input type="button" value="삭제" onclick="">
				
				</td>
			</tr>
			
		
		</table>

	
	
	
	







</body>
</html>