<%@page import="java.util.List"%>
<%@page import="com.cal.dto.CalDto"%>
<%@page import="com.cal.dao.CalDao"%>
<%@page import="com.cal.common.Util"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
#calendar {
	border-collapse: collapse;
	boarder: 1px solid gray;
}

#calendar th {
	width: 80px;
	border: 1px solid gray;
}

#calendar td {
	width: 80px;
	height: 80px;
	border: 1px solid gray;
	text-align: left;
	vertical-align: top;
	position: relative;
}

a {
	text-decoration: none;
}

.list>p {
	font-size: 5px;
	margin: 1px;
	background-color: pink;
}

.preview {
	position: absolute;
	top: -30px;
	left: 10px;
	background-color: pink;
	width: 40px;
	height: 40px;
	text-align: center;
	line-height: 40px;
	border-radius: 40px 40px 40px 1px;
}
</style>

<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">

		function isTwo(n){
			return (n.length<2)?"0"+n:n;
		}



	$(function(){
		$(".countview").hover(function(){
			//handle in
			var countView = $(this);
			var year = $(".y").text().trim();
			var month = $(".m").text().trim();
			var date = countView.text().trim();
			var yyyyMMdd= year + isTwo(month)+isTwo(date);
			
			$.ajax({
				type:"post",
				url : "count.do?id=kh&yyyyMMdd="+yyyyMMdd,
				dataType: "json",
				async : false,
				success : function(msg){
					var count = msg.calcount;
					countView.after("<div class='preview'>"+count+"</div>");
					
					
				},
				error: function(){
					alert("통신 실패");
				}
			
			});
		},
		function(){
			//handle out
			$(".preview").remove();
			
		});
	
	})
	
	

</script>


</head>
<body>


	<%

	//현재 시간의 연월일시분초를 cal 객체에 대입 
	Calendar cal = Calendar.getInstance();
	

	//year 변수에 현재 년도, month변수에 현재 월 +1 하는 이유는 api에서 해당월을 0~11로 리턴해주기 때문이다
	int year = cal.get(Calendar.YEAR);
	int month = cal.get(Calendar.MONTH) +1;
	
	String paramYear = request.getParameter("year");
	String paramMonth = request.getParameter("month");
	
	
	//처은에는 값이 null이고 그 화살표?를 누르는 순간 null이 아니게 된다 
	if(paramYear != null){
		year = Integer.parseInt(paramYear);
	}
	if(paramMonth != null){
		month = Integer.parseInt(paramMonth);
	}
	
	if(month >12){
		month = 1;
		year++;
		
	}
	//month 가 0이 되는 순간 0을 12로 바꿔주고 year를 -1  해준다. 
	if(month <1){
		month = 12;
		year--;
	}
	//cal변수에 year month date를 세팅해준다
	cal.set(year,month-1,1);
	
	//dayofweek에 시작 요일을 숫자로 리턴해준다 1~7 까지 
	int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
	
	//lastDay 에 해당 월의 요일 중 최대값을 넣어준다 
	int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	
	CalDao dao = new CalDao();
	String yyyyMM = year +Util.isTwo(String.valueOf(month));
	List<CalDto> list = dao.getCalViewList("kh", yyyyMM);


%>

	<table id="calendar">
		<caption>
			<a href="calendar.jsp?year=<%=year-1%>&month=<%=month%>">◁</a> <a
				href="calendar.jsp?year=<%=year%>&month=<%=month-1%>">◀</a> <span
				class="y"><%=year %></span>년 <span class="m"><%=month %></span>월 <a
				href="calendar.jsp?year=<%=year%>&month=<%=month+1%>">▶</a> <a
				href="calendar.jsp?year=<%=year+1%>&month=<%=month%>">▷</a>

		</caption>

		<tr>
			<th>일</th>
			<th>월</th>
			<th>화</th>
			<th>수</th>
			<th>목</th>
			<th>금</th>
			<th>토</th>
		</tr>

		<tr>

			<%
		//해당 월의 요일 시작전까지 공백을 만들어준다 
		for(int i=0;i<dayOfWeek-1;i++){
			out.print("<td></td>");
			
		}
		
		//해당 월의 최대값을 담아 준다 3월의 경우 31 4월 경우 30 리턴 
		for(int i=1;i<=lastDay; i++){
		

%>
			<td>
				
				<a class="countview"
				href="cal.do?command=list&year=<%=year %>&month=<%=month %>&date=<%=i %>"
				style="color:<%=Util.fontColor(i,dayOfWeek)%>"><%=i %></a> 
				
				<a href="insert.jsp?year=<%=year%>&month=<%=month%>&date=<%=i%>&lastDay=<%=lastDay%>">
					<img alt="" src="image/pen.png" style="width: 10px; height: 10px;">
				</a>
				
				<div class="list">
					<%=Util.getCalView(i, list) %>
				</div></td>


<%
			//일주일이 끝났을때 <tr>을 끝내고 다시 시작해준다 
			if((dayOfWeek-1+i)%7 ==0){
			out.print("</tr><tr>");
			
			}
		}
		
		//7-마지막 날짜의 요일 만큼 공백을 채워준다 
		for (int i=0;i<(7-(dayOfWeek-1+lastDay)%7)%7;i++){
			out.print("<td></td>");
		}

%>





		</tr>

	</table>




</body>
</html>