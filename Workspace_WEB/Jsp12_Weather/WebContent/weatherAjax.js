$(function() {
	//페이지가 다 로딩일 되었을때 실행해라
	//weatherview.html 의 날씨보기라는 버튼을 클릭하면 하단의 함수 실행됨
	//code라는 키값으로 값을 받을 수 있고
	//weather open 이라는 서블릿이 실행된다.
	$("#weaView").click(
			function() {
				var url = "weatherOpen"; //서블릿 weatherOpen
				var code = $("#address option:selected").val();
				//콘솔은 무조건 함수내에서만...스크립트 안이나, 뭐 실행하는거 안에서!
				
				//달러 내부에 html dom을 선택을 하고 
				//.val() : value갖고옴 
				console.log("code = ", code);
				
				$.ajax({
					type : "GET", //방식
					url : url + "?code=" + code, //위치
						//보내주려고 만든거라 여기에 console찍으면 안된다.				
					dataType : "text",  //리턴되는 값을 이렇게 알아듣겠다.
					 
				//---------------------------------------------------------------
				
					success : function(data) {
						console.log("data = ", data);
						var temp = $.trim(data);
						//jquery에서 공백제거를 하려면 $.trim() 함수를 써주면 된다.
						//data가 인포에서 만든거다 
						//공백의 이유 -> info 에서 생기는 엔터
						
						var obj = JSON.parse(temp); //<->JSON.stringify 제이슨 형태로 변환
						console.log("temp = ", temp);
						console.log("temp = ", JSON.parse(temp)); //그전까지는 문자열이었던 데이터를 파싱으로 제이슨형태로 바꾸면 안에 있는 데이터를 빼올수있음!
						console.log("temp = ", JSON.parse(temp).x); 
						console.log("data = ", data); // 우리가 불러들인 data, temp값을 보여주기
						
						$("#pubDate").val(obj.pubDate); //obj 안에 값들을 빼오기
						$("#temp").val(obj.temp);
						$("#x").val(obj.x);
						$("#y").val(obj.y);
						$("#reh").val(obj.reh);
						$("#pop").val(obj.pop);
						$("#wfKor").val(obj.wfKor); //val 안에 값을쓰면 obj안에 있는wfkor이라는 데이터를 #wfkor안에 넣어줌 

						var weather_condition = obj.wfKor;
						//사진을 넣기 위해 변수 설정 
						if (weather_condition == "맑음"){
							$("#weather_img").attr("src","/Jsp12_Weather/image/sun.png");
						}else if (weather_condition == "비"){
							$("#weather_img").attr("src","/Jsp12_Weather/image/rain.png");
						}else if (weather_condition == "눈"){
							$("#weather_img").attr("src","/Jsp12_Weather/image/snow.png");
						}else if (weather_condition == "흐림"){
							$("#weather_img").attr("src","/Jsp12_Weather/image/cloud.png");
						}else if (weather_condition == "구름 조금"){
							$("#weather_img").attr("src","/Jsp12_Weather/image/cloud_sun.png");
						}else{
							$("#weather_img").attr("src","/Jsp12_Weather/image/etc.png");
						}
						//setAttribute 같은놈
					},
					error : function() {
						alert("정보를 불러오는데 실패하였습니다.");
					}
				});
			});
});
