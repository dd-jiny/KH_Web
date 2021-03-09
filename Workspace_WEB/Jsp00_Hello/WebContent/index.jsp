<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<!-- jsp는 java server page이고, 서버에서 컴파일하다 예외가 발생할 시 특정 화면으로 넘겨주는 지시자가 따로 있습니다.
 또한 에러메시지를 화면에 바로 출력해주기에 안써도 상관은 없습니다...만, 그래도 사실 쓰는게 좋긴 합니다. -->
	
	<table border = "1">
	
	
<%
	 /* scriptlet("<%% >") : 여기가 자바 코드 영역이다. */
	
	//EMP 테이블 전체 출력
	
	//1. driver 연결
	Class.forName("oracle.jdbc.driver.OracleDriver");  //경로를 적어주기 
	
	
	
	//2. 계정 연결
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String user = "kh";
	String password = "kh";
	Connection con = null;  // con 메소드 파라미터에 값을 넣어주기 위해 만들어놓음
	
	con = DriverManager.getConnection(url, user, password); // 계정을 파라미터 안에 넣어줌 
	
	//3. query 준비
	String sql = " SELECT EMPNO, ENAME, JOB, MGR, HIREDATE, SAL, COMM, DEPTNO " + 
	 			 " FROM EMP ";
	
	Statement stmt = null; 
	ResultSet rs = null;

	stmt = con.createStatement();
	
	//4. query 실행 및 리턴
	rs = stmt.executeQuery(sql); 
	
	/* Statement는 db와 연결되어있는 connection 객체를 통해 sql문을 db에 전달하여 실행하고, 결과를 리턴받아주는 객체입니다.
	PreparedStatement는 Statement를 상속받았기 때문에, statement와 거의 똑같으나, sql문을 컴파일 할 때
	?를 사용하여 preCompile 하여 미리 준비해놓고, 실행하여 리턴받습니다. */
	
	while(rs.next()){
%>	
        <!-- HTML 영역 --> 
        <tr>
        	<td><%=rs.getInt(1) %></td>
        	<td><%=rs.getString(1) %></td>
        	<td><%=rs.getString(2) %></td>
        	<td><%=rs.getString(3) %></td>
        	<td><%=rs.getInt(4) %></td>
        	<td><%=rs.getDate(5) %></td>
        	<td><%=rs.getDouble(6) %></td>
        	<td><%=rs.getDouble(7) %></td>
        	<td><%=rs.getInt(8) %></td>
        	
        	
        </tr>



<% 	
	}
	
	//5. db종료
	rs.close();
	stmt.close();
	con.close();


%>

	</table>

</body>
</html>
















