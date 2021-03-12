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
	int seq = Integer.parseInt(request.getParameter("seq"));
	//String writer = request.getParameter("writer");
	String title = request.getParameter("title");
	String content = request.getParameter("content");
	
	MyBoardDao dao = new MyBoardDao();
	MyBoardDto dto = dao.selectOne(seq);
	
	dto.setTitle(title);
	dto.setContent(content);
	
	int res = dao.update(dto);
	
	if(res>0){
%>
<script type="text/javascript">
confirm("수정하시겠습니까?");
alert("수정 성공!!")
location.href="myselect.jsp?seq=<%=dto.getSeq()%>";

</script>
<%
	}else{
%>

<script type="text/javascript">
alert("수정 실패")
location.href='mylist.jsp';

</script>

<%
	}
%>
</body>
</html>