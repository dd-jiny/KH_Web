
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% request.setCharacterEncoding("UTF-8"); %>

<%@page import="com.login.dto.MYMemberDto" %>
<%@page import="com.login.biz.MYMemberBiz" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
	String command = request.getParameter("command");
	System.out.println("[" + command + "]");
	
	MYMemberBiz biz = new MYMemberBiz();
	
	if(command.equals("login")) {
		
		String myid = request.getParameter("myid");
		String mypw = request.getParameter("mypw");
		
		MYMemberDto dto = biz.login(myid, mypw);
		
		if (dto != null) {
			//side effect ... 이거 다시한번 이해해보자
			// session scope 에 객체 담기
			session.setAttribute("dto", dto);
			//만료되는 시간 설정 (default: 30분)  ex) 세션이 만료되었습니다...
			//음수일 경우,,,?
			session.setMaxInactiveInterval(10 * 60);
			
			if(dto.getMyrole().equals("ADMIN")) {
				response.sendRedirect("adminmain.jsp");
				
			} else if(dto.getMyrole().equals("USER")) {
				response.sendRedirect("usermain.jsp");
			}
			
			
		} else {
%>
		<script type="text/javascript">
		alert("로그인 실패");
		location.href="index.html";
		</script>
<% 
		}
	} else if (command.equals("logout")) {
		// sessin scope 에서 값 삭제 (만료)
		session.invalidate();
		response.sendRedirect("index.html");
		
	} else if (command.equals("listall")) {
		//1. 보내준것이 있으면 받는다.
		//2. db에 전달할 것이 있으면 전달한다.
		//없으면 리턴한다
		List<MYMemberDto> list = biz.selectAllUser();
		request.setAttribute("list", list);
		pageContext.forward("userlistall.jsp");
		
	} else if (command.equals("listen")) {
		//1.
		//2.
		List<MYMemberDto> list = biz.selectEnabledUser();
		request.setAttribute("list", list);
		pageContext.forward("userlisten.jsp");
		//3.
		//4.
	} else if(command.equals("updateroleform")){
		//1
		int myno = Integer.parseInt(request.getParameter("myno"));
		//2
		MYMemberDto dto = biz.selectUser(myno);
		//3
		request.setAttribute("dto", dto);
		//4
		pageContext.forward("updaterole.jsp");
	} else if(command.equals("updaterole")) {
		int myno = Integer.parseInt(request.getParameter("myno"));
		String myrole = request.getParameter("myrole");
		
		//2
		int res = biz.updateRole(myno, myrole);
		
		if(res > 0) {
%>
	<script type="text/javascript">
		alert("등급 변경 성공");
		location.href="logincontroller.jsp?command=listen";
	</script>
<%
		} else {
%>
	<script type="text/javascript">
		alert("등급 변경 실패");
		location.href="logincontroller.jsp?command=updateroleform&myno=<%=myno%>";
	</script>
<% 
		}
	} else if (command.equals("registform")) {
		response.sendRedirect("regist.jsp");
	} else if (command.equals("idchk")){
		String myid = request.getParameter("myid");
		MYMemberDto dto = biz.idCheck(myid);
		boolean idnotused = true;
		
		if(dto.getMyid() != null){
			idnotused = false;
		}
		
		response.sendRedirect("idchk.jsp?idnotused="+idnotused);
	} else if (command.equals("insertuser")) {
		//1.보내준 값이 있으면 받는다.
		String myid = request.getParameter("myid");
		String mypw = request.getParameter("mypw");
		String myname = request.getParameter("myname");
		String myaddr = request.getParameter("myaddr");
		String myphone = request.getParameter("myphone");
		String myemail = request.getParameter("myemail");
		//2.db에 전달할 값이 있으면 전달하고
		MYMemberDto dto = new MYMemberDto(0, myid, mypw, myname, myaddr, myphone, myemail, null, null);
		//없으면 없는대로 호출해서 리턴받는다. 
		int res = biz.insertUser(dto);
		//3. 화면에 전달할 값이 있으면, request객체에 담아준다.
		//4.보낸다
		if (res > 0) {
%>
		<script type="text/javascript">
			alert("가입 성공");
			location.href='index.html';
		</script>
<%
		}else {
%>
		<script type="text/javascript">
			alert("가입 실패");
			location.href='logincontroller.jsp?command=regist';
		</script>
<%
		}
	}
	
%>







	<h1 style="color: red;"> 잘못왔다....</h1>
</body>
</html>
































