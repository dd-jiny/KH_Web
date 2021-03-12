<%@page import="com.board.dao.MyBoardDao"%>
<%@page import="com.board.dto.MyBoardDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
	int seq = Integer.parseInt(request.getParameter("seq"));

	MyBoardDao dao = new MyBoardDao();
	MyBoardDto dto = dao.selectOne(seq);
	
	dto.setSeq(seq);
	
	int res = dao.delete(dto);
	if(res>0){
%>
<script type="text/javascript">
confirm("정말 삭제할까요?");
alert("삭제 성공!!")
location.href="mylist.jsp";

</script>

<%
	}else{
%>

<script type="text/javascript">
alert("삭제 실패");
location.href="mylist.jsp";
</script>

<%
	}
%>
</body>
</html>