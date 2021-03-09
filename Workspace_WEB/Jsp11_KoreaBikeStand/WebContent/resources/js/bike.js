
$(function(){
	getJsonData();
});
//윈도우.onload와 같음 

function getJsonData(){
	
	$.getJSON("resources/json/bike.json", function(mydata){
		//여기서 콘솔을 출력시 mydata 내부에는 data.json에 들어있는 데이터를 출력할 수 있다. 
		//그래서 뷰 파일 내부에서 데이터를 갖고 왔고, ajax통신을 이용해서 서버로 데이터를 넘긴다.
		//json을 받아오는애 여기서 mydata - > 리턴하는거 
		$.ajax({
			url: "bike.do", //위치
			method: "post", //방식
			data: {"command": "getdata", "mydata" : JSON.stringify(mydata)},  /*JavaScript 값이나 객체를 JSON 문자열로 변환.*/
			dataType: "json", //json으로 데이터를 받겟다. 이를 설정하지 않을 경우 msg를 다시 파싱하는 과정을 거쳐야함.
			success: function(msg) { //성공시 파라미터로 데이터를 받을 수 있다. 
				var $tbody = $("tbody");
				
				var list = msg.result; //키를 통해 밸류값을 꺼내오는거 
				for (var i = 0; i < list.length; i++) {
					var $tr = $("<tr>");
					
					$tr.append($("<td>").append(list[i].name));
					$tr.append($("<td>").append(list[i].addr));
					$tr.append($("<td>").append(list[i].latitude));
					$tr.append($("<td>").append(list[i].longitude));
					$tr.append($("<td>").append(list[i].bike_count));
					
					$tbody.append($tr);
				}
				
				//dom을 그려주는 처리 완료 
			},
			error: function(){
				alert("통신 실패");
			}
		});
	});
}