<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8");%>
<%@page import="com.mvc.dto.MVCBoardDto" %>
<%@page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<%												//request.getAttribue("list") ->object 부모타입에서 작은 녀석으로 갈때에는 묵시적 형변환
												//작은거에서 큰거로 갈때는 명시적
	/*											
	getParameter() : String 타입을 리턴 ,또한 웹브라우저에서 전송받은 request 영역의 값을 읽어오고
	getAttribute() : object 타입을 리턴하기 때문에 주로 빈 객체나 다른 클래스를 받아올때 사용 
					 setAttribute() 속성을 통한 설정이 없으면 무조건 null을 리턴
	*/
	
	List<MVCBoardDto> list = (List<MVCBoardDto>) request.getAttribute("list");
%>
<body>

	<h1>List</h1>
	
	<table border="1">
		<tr>
			<th>번호</th>
			<th>작성자</th>
			<th>제목</th>
			<th>작성일</th>
		</tr>
<%
	for (MVCBoardDto dto : list) {
%>
		<tr>
			<td><%=dto.getSeq() %></td>
			<td><%=dto.getWriter() %></td>
			<td><a href="myservlet.do?command=selectone&seq=<%=dto.getSeq() %>"><%=dto.getTitle() %></a></td>
			<td><%=dto.getRegdate() %></td>
		</tr>
<%
	}
%>
		<tr>
			<td colspan="4" align="right">
				<input type="button" value="글작성" onclick="location.href='myservlet.do?command=insertform'" />
			</td>
		</tr>
	</table>
</body>
</html>





















