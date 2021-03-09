<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8");%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>RESULT</h1>
	
	pageId : <%=pageContext.getAttribute("pageId") %> <br>
	requestId : <%=request.getAttribute("requestId") %> <br>
	sessionId : <%=session.getAttribute("sessionId") %> <br>
	applicationId : <%=application.getAttribute("applicationId") %> <br>
	myRequest : <%=request.getAttribute("myRequest") %> <br>
	
<!-- 	1. requestId는 왜 null일까 
2. myRequest는 왜 나올까
3. RequestDispatcher 는 뭐하는 놈일까
4. controller에서 myRequest는 뜨는데 result에서 myRequest는 왜 null일까 -->

 

</body>
</html>