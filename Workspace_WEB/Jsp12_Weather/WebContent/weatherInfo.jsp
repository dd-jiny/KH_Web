 <%
/*
jstl : JSP Standard Tag Library -> 표준 태그 모음집 
c: -> core library
x: ->xml library =>apach xalan 가져와야해 
page contentType="text/html; charset=UTF-8"
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"
<!-- 날씨정보를 통신해서 가져오는 애 ? -->
<!-- rss : 업데이트가 빈번한 웹사이트의 정보를 사용자에게 보다 쉽게 제공하기 위하여 만들어진 XML 기반의 콘텐츠 배급 포맷을 말한다. -->
<!-- pubDate -->


<c:catch var="err">
<!-- 익셉션 처리에 사용된다. 에러가나면 err변수에 담아준다. -->
	<c:set var="weatherURL" value="http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=${code}" />
	<!-- value안에 링크주소에 있는 값 저장  -->
	<!--  c:set = weatherURL을 변수 설정 후 값을 저장-->
		<c:import var="weather" url="${weatherURL}" />
		<!-- c:import : URL을 사용하여 다른 자원의 결과를 삽입한다. -->
		절대 또는 상대 URL을 검색하여 해당 내용을 페이지, 'var'의 문자열 또는 'varReader'의 판독기에 표시합니다.
		xml 형태의 문자열(문서,도큐먼트)을 weather에 삽입.
		<!-- wheatherURL 받아온놈을 weather안에 넣어주기  -->
			<x:parse var="wrss" xml="${weather}" />
			<!--  태그는 속성 또는 태그 본문을 통해 지정된 XML 데이터를 구문 분석하는 데 사용됩니다. -->
			xml contents로 바꿔줌 <xmlString -> xml>
			<!--  xml로 된 애를 wrss 로 변수 설정 -->
			<!-- weather가 xml인데 이거를 json으로 바꿔주기위해 -->
			{"pubDate":"<x:out select="$wrss/rss/channel/pubDate" />",
			"temp":"<x:out select="$wrss/rss/channel/item/description/body/data/temp" />",
			"reh":"<x:out select="$wrss/rss/channel/item/description/body/data/reh" />",
			"pop":"<x:out select="$wrss/rss/channel/item/description/body/data/pop" />",
			"x":"<x:out select="$wrss/rss/channel/item/description/header/x" />",
			"y":"<x:out select="$wrss/rss/channel/item/description/header/y" />",
			"wfKor":"<x:out select="$wrss/rss/channel/item/description/body/data/wfKor" />"}
			<!-- QQQQ: x:out 출력?? -->
			<!-- wrss안에 있는 데이터를 wrss/rss/channel/pubDate 요런 경로를 통해 필요한 데이터를 뽑아서 출려갷ㅆ다??  -->
</c:catch>

	<c:if test="${err!=null}">
		${err}
	</c:if>   
	
	
전체 ->	JsonString 형태의 문자열로 파싱 
*/

%>
	


<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>



<c:catch var="err">

	<c:set var="weatherURL" value="http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=${code}" />

		<c:import var="weather" url="${weatherURL}" />

			<x:parse var="wrss" xml="${weather}" />
	
			{"pubDate":"<x:out select="$wrss/rss/channel/pubDate" />",
			"temp":"<x:out select="$wrss/rss/channel/item/description/body/data/temp" />",
			"reh":"<x:out select="$wrss/rss/channel/item/description/body/data/reh" />",
			"pop":"<x:out select="$wrss/rss/channel/item/description/body/data/pop" />",
			"x":"<x:out select="$wrss/rss/channel/item/description/header/x" />",
			"y":"<x:out select="$wrss/rss/channel/item/description/header/y" />",
			"wfKor":"<x:out select="$wrss/rss/channel/item/description/body/data/wfKor" />"}
	
</c:catch>
	<c:if test="${err!=null}">
		${err}
	</c:if>
	
	


