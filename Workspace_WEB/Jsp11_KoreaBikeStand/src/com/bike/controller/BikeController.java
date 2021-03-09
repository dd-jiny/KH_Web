package com.bike.controller;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bike.dao.BikeDao;
import com.bike.dto.BikeDto;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet("/BikeController")
public class BikeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public BikeController() {

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String command = request.getParameter("command");
		System.out.println("["+command+"]");
		
		if(command.equals("view")) {
			response.sendRedirect("view.html");
		} else if (command.equals("getdata")) {
			BikeDao dao = new BikeDao();
			
			if(dao.delete()) {
				System.out.println("db 초기화 성공");
			} else {
				System.out.println("db 초기화 실패");
			}
			
			String data = request.getParameter("mydata");
			// 데이터 가져온다. 아직 스트링
			
			JsonElement element = JsonParser.parseString(data); //데이터 파싱 스트링을 제이슨 형태로  JsonString->JsonObject제이슨 문자열을 제이슨 객체로
			JsonObject jsonData = element.getAsJsonObject(); //파싱된 json을 jsonObject 로 가져옴
			//getAsJsonObject - > 
			
			//JsonObject jsonData = JsonParser.parseString(data).getAsJsonObject();
			//----------쓸수 있는 형태로 변환 완료 ----------------
			
			JsonElement records = jsonData.get("records");  //records라는 키를 입력해주면 밸류값이 들어간다. 제이슨 엘리먼트는 어레이다.
			JsonArray recordsArray = records.getAsJsonArray(); //디비로 보내줄 걸 담아주는 바구니 Array // 어레이객체로가져오겠다.
																//jsonData -> {"fields" : JsonElement, "records" : JsonElement}
			
			List<BikeDto> list = new ArrayList<BikeDto>(); //디비로 보내주려면 이 형식에 담아줘야되므로
			JsonArray resultArray = new JsonArray(); //담아서 프론트로 보내줄 Array  
			//리턴할 값을 담았다.
			
			for (int i = 0; i < recordsArray.size(); i++) {
				/*
				 * JsonElement tempElement = recordsArray.get(i);
				 * JsonObject tempObject = tempElement.getAsJsonObject();
				 * JsonElement nameElement = tempObject.get("자전거대여소명");
				 * String name = nameElement.getAsString(); 이것이 한줄요약
				 * 
				 */
				String name = recordsArray.get(i).getAsJsonObject().get("자전거대여소명").getAsString();
				
				String addr = null;
				if (recordsArray.get(i).getAsJsonObject().get("소재지도로명주소") != null ) {
					addr = recordsArray.get(i).getAsJsonObject().get("소재지도로명주소").getAsString();
					
				} else {
					addr = recordsArray.get(i).getAsJsonObject().get("소재지지번주소").getAsString();
					
				}
				
				double latitude = recordsArray.get(i).getAsJsonObject().get("위도").getAsDouble();
				double longitude = recordsArray.get(i).getAsJsonObject().get("경도").getAsDouble();
				
				int bike_count = recordsArray.get(i).getAsJsonObject().get("자전거보유대수").getAsInt();
				
				BikeDto dto = new BikeDto(name, addr, latitude, longitude, bike_count);
				list.add(dto);
				
				Gson gson = new Gson(); //gson이라는 애를 가지고  제이슨으로 바꿀것이다. = 자바오브젝트를 -> 제이슨문자열로~
				String jsonString = gson.toJson(dto); //자바객체dto를 제이슨 문자열로 바꾸고
				resultArray.add(JsonParser.parseString(jsonString)); //제이슨 객체로 바꾸어 배열에 저장
				
			}
			
			if(dao.insert(list)) {
				System.out.println("db저장 성공");
			} else {
				System.out.println("db 저장 실패");
			}
			
			JsonObject result = new JsonObject();
			result.add("result", resultArray);
			
			response.getWriter().append(result+"");
			
		}
	}

}

/*
 Gson은 Object mapping 방식 말고도, Json자체를 JsonObject라는 class로 만들어서
 Json내에 object, 값, 배열,null 을 가져오거나, 추가, 삭제 등을 할 수 있다.
 먼저 JsonObject에서는 Json의 요소를 구분하는 총 5가지 종류의 class가 있다.
 JsonElement, JsonObject, JsonPrimitive, JsonArray, JsonNull

1.JsonElement
위의 다섯가지 중 JsonElement를 제외한 4가지는 JsonElement를 상속한다.
즉, JsonElement는 나머지 4가지의 부모class로 추상클래스로 정의되어 있다.
기본적으로 getter를 통해서 Json의 요소를 가져오면 기본적으로 JsonElement 형태로 받아오며, 필요에 따라서 적절히 변환해서 사용해야 한다.

2.JsonObject
Json에서 중괄호로 묶여서 하나의 Object를 나타낼때, 그것을 표현하기 위한 class
당연히 key-value형태로 존재하며, 실제 구현은 LinkedTreeMap으로 구현되어 있다.
key = String , value = JsonElement 형식
 -> ({"String" : JsonElement} 형식 
3.JsonPrimitive
Json에서 특정 key의 value값을 나타내기 위한 class, 
숫자, 문자, Boolean 등 의 실제 값을 저장한다.

4.JsonArray
Json에서 [] 로 묶여서 배열을 나타내기 위한 class
index로 접근 가능하며, 실제 List와 유사하다.

5.JsonNull
null object를 표현하기 위한 class
 */














