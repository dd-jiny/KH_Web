<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@page import="com.board.dao.MyBoardDao"%>
<%@page import="com.board.dto.MyBoardDto"%>

<% request.setCharacterEncoding("UTF-8");%>
<% response.setContentType("text/html; charset=UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	int seq = Integer.parseInt(request.getParameter("seq"));
	//html 형식은 string 이므로 주소창의 값을 넣어주기 위해 int로 형변환 

	String title = request.getParameter("title") ;
	String content = request.getParameter("content") ;
	
	MyBoardDto dto = new MyBoardDto(seq, null, title, content, null);
	MyBoardDao dao = new MyBoardDao();
	
	int res = dao.update(dto);
	if (res > 0) {
		
%>
	<script type="text/javascript">
		alert("수정 성공");
		location.href="myselect.jsp?seq=<%=seq%>";
	</script>
<%
	} else {
%>
	<script type="text/javascript">
		alert("수정 실패");
		location.href="myupdate.jsp?seq=<%=seq%>";
	</script>
<%
	}
%>
</body>
</html>


















