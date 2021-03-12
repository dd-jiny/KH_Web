<%@page import="com.board.dao.MyBoardDao"%>
<%@page import="com.board.dto.MyBoardDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	request.setCharacterEncoding("UTF-8");
%>
<%
	response.setContentType("text/html; charset=UTF-8");
%>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
	//request.getParameter : 요청하면 키에 맞는 밸류값을 리턴해준다. 근데 문자열로 받아와서  int로 형변환해준다! 
	int seq = Integer.parseInt(request.getParameter("seq"));

	MyBoardDao dao = new MyBoardDao();
	MyBoardDto dto = dao.selectOne(seq);
%>

<h1>글 수정하기</h1>
	<form action="./myupdate_res.jsp" method="post">
	<input type="hidden" value="<%=dto.getSeq()%>" name="seq">
		<table border="1">
			<tr>
		 <th>작성자</th>
		 <td><%=dto.getWriter() %></td>
		</tr>
		<tr>
		 <th>제목</th>
		 <td><input type="text" name="title" value="<%=dto.getTitle()%>"></td>
		</tr>
		<tr>
		 <th>내용</th>
		 <td><textarea rows="10" cols="50" name="content"><%=dto.getContent()%></textarea></td>
		 
		</tr>
		
		
		<tr align="right">
			<td colspan="2">
				<input type="submit" value="확인">
				<input type="button" value="취소" onclick="location.href='myselect.jsp?seq=<%=dto.getSeq()%>'">
								
			</td>
		</tr>
		
		</table>
	</form>


</body>
</html>