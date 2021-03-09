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

	<h1>ANSWER</h1>

	<form action="answer.do" method="post">
		<input type="hidden" name="command" value="answerwrite"/>
		<input type="hidden" name="parentBoardNo" value="${dto.boardno }"/>
		<table border="1">
			<tr>
				<th>제목</th>
				<td><input type="text" name="title" value="RE:${dto.title }"/></td>
			</tr>
			<tr>
				<th>작성자</th>
				<td><input type="text" name="writer" /></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea rows="10" cols="60" name="content">${dto.content }
---------------------------------------
				
				</textarea></td>
			</tr>
			<tr>
				<td colspan="2" align="right">
					<input type="submit" value="답변" />
					<input type="button" value="취소" onclick="location.href='answer.do?command=select&boardno=${dto.boardno}'"/>
				</td>
			</tr>
		
		</table>
	</form>



	

</body>
</html>