
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.mvc.biz.MVCBoardBiz" %>
<%@page import="com.mvc.biz.MVCBoardBizImpl" %>
<%@page import="com.mvc.dto.MVCBoardDto" %>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8");%>
<%@page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
/*											
getParameter() : String 타입을 리턴 ,또한 웹브라우저에서 전송받은 request 영역의 값을 읽어오고
getAttribute() : object 타입을 리턴하기 때문에 주로 빈 객체나 다른 클래스를 받아올때 사용 
				 setAttribute() 속성을 통한 설정이 없으면 무조건 null을 리턴
*/
	String command = request.getParameter("command");
	System.out.printf("[%s]\n", command);
	
	MVCBoardBiz biz = new MVCBoardBizImpl();
	
	//요청한 명령을 확인한다. 
	if(command.equals("list")) {
		//1. 보내준 값이 있으면, 받는다. 	
		
		//2. db에 전달할 값이 있으면 전달하고, 
		//없으면 없는대로 호출해서 리턴 받는다.
		List<MVCBoardDto> list = biz.selectList();
		
		//3. 화면에 전달할 값이 있으면, request 객체에 담아준다. 
		request.setAttribute("list", list);
		
		//4. 보낸다
		pageContext.forward("mylist.jsp");
	} else if (command.equals("insertform")) {
		//1. 보내준 값이 있으면 , 받는다.
		//2.db.에 전달할 값이 있으면 전달하고,
		//없으면 없는대로 호출해서 리턴 받는다.
		//3.화면에 전달할 값이 있으면, request 객체에 담아준다. 
		//request.setAttribute("list", "a");
		//4. 보낸다.
		response.sendRedirect("myinsert.jsp");
		/*
			pageContext.forward() : 페이지 위임 (request, response 객체가 그대로 전달) 나대신가세요
			response.sendRedirect() : 페이지 이동 (새로운 request, response 객체) 니가가세요
		*/
	} else if (command.equals("insertres")) {
		// 1. 보내준 값이 있으면, 받는다.
		String writer = request.getParameter("writer");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		//2 . db에 전달할 값이 있으면 전달하고,
		//없으면 없는대로 호출해서 리턴 받는다. 
		MVCBoardDto dto = new MVCBoardDto(0, writer, title, content, null);
		//파라미터 세개짜리 생성자...
		int res = biz.insert(dto);
		
		//3. 화면에 전달할 값이 있으면, request 객체에 담아준다. 
		//전달할 값이 없다.
		
		//4. 보낸다.
		if (res > 0) {
%>
		<script type="text/javascript">
			alert("글 작성 성공");
			location.href='mycontroller.jsp?command=list';
		</script>
<%
		}else {
%>
	<script type="text/javascript">
		alert("글 작성 실패");
		location.href='mycontroller.jsp?command=insertform';
	</script>
<% 
		}
	} else if (command.equals("selectone")) {
		
		//1. 보내준 값이 있으면 받는다.
		int seq = Integer.parseInt(request.getParameter("seq"));
		//2. db에 전달할 값이 있으면 전달하고
		//없으면 없는대로 호출한다.
		MVCBoardDto dto = biz.selectOne(seq);
		//3.화면에 전달할 값이 있으면 request객체에 담아준다.
		request.setAttribute("dto", dto);
		//4. 보낸다
		pageContext.forward("myselect.jsp");
		
	} else if (command.equals("updateform")) {
		//1. 보내준값이 있으면 받는다.
		int seq = Integer.parseInt(request.getParameter("seq"));
		//2. db에 전달할 값이 있으면 전달하고
		//없으면 없는대로 호출한다.
		MVCBoardDto dto = biz.selectOne(seq);
		//3.화면에 전달할 값이 있으면 request 객체에 담겨준다. 
		request.setAttribute("dto", dto);
		//4.보낸다
		pageContext.forward("myupdate.jsp");
		
	} else if (command.equals("updateres")) {
		//1. 보내준 값이 있으면 받는다.
		int seq = Integer.parseInt(request.getParameter("seq"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		//2.db에 전달할 값이 있으면 전달하고
		//없으면 없는대로 메소드 호출
		
		MVCBoardDto dto = new MVCBoardDto();
		dto.setSeq(seq);
		dto.setTitle(title);
		dto.setContent(content);
		int res = biz.update(dto);
		
		//3 화면에 전달할 값이 있으면 request 객체에 담겨준다. 
		
		//4 보낸다.
		if (res > 0) {
%>
		<script type="text/javascript">
			alert("수정 성공");
			location.href="mycontroller.jsp?command=selectone&seq=<%=seq%>";
		</script>
<%
		} else {
%>
		<script>
			alert("수정 실패");
			history.back();
		</script>
<% 
		}
		} else if (command.equals("delete")) {
			//1
			int seq = Integer.parseInt(request.getParameter("seq"));
			//2
			int res = biz.delete(seq);
			//3
			//4
			if (res > 0) {
%>
			<script type="text/javascript">
				alert("삭제 성공");
				location.href="mycontroller.jsp?command=list";
			</script>
<%
			} else {
				
%>
			<script type="text/javascript">
				alert("삭제 실패");
				location.href="mycontroller.jsp?command=selectone&seq=<%=seq%>";
			</script>
<% 
	}
	}
%>

</body>
</html>


















