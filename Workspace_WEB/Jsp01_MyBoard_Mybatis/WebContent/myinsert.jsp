<%@page import="com.board.dto.MyBoardDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%request.setCharacterEncoding("UTF-8"); %>
<%response.setContentType("text/html; charset=UTF-8"); %>

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<%-- <%
	//int seq = Integer.parseInt(request.getParameter("seq"));
	String writer = request.getParameter("writer");
	String title = request.getParameter("title");
	String content = request.getParameter("content");

%> --%>

	<h1>글쓰기</h1>
	<form action="./myinsert_res.jsp" method="post">
		<table border="1">
			<tr>
		 <th>작성자</th>
		 <td><input type="text" name="writer"></td>
		</tr>
		<tr>
		 <th>제목</th>
		 <td><input type="text" name="title"></td>
		</tr>
		<tr>
		 <th>내용</th>
		 <td><textarea rows="10" cols="50" name="content"></textarea></td>
		 
		</tr>
		
		
		<tr align="right">
			<td colspan="2">
				<input type="submit" value="확인">
				<input type="button" value="취소" onclick="">
								
			</td>
		</tr>
		
		</table>
	</form>

</body>
</html>