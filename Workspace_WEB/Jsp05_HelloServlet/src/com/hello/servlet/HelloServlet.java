package com.hello.servlet;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//web.xml 설정을 annotation 으로 간략화 
//servlet~servlet-mapping 을 @WebServlet("/controller.do") 한줄로 간략화 ->@WebServlet
@WebServlet("/controller.do") //객체를 만들어주는 놈 
public class HelloServlet extends HttpServlet {
	/* controller.do 라는 요청이 오면 helloservlet에서 받아줄거야~ */
	private static final long serialVersionUID = 1L;
	
	private String contextParam;
	private String initParam;
	
	
    public HelloServlet() {
        System.out.println("servlet constructor");
        
    }
    
    @Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println("servlet init");
		
		contextParam = config.getServletContext().getInitParameter("name");
		initParam = config.getInitParameter("sports");
		System.out.println("context-param : " + contextParam);
		System.out.println("init-param :" + initParam);
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//do get, do post 둘다 컨트롤러 방식
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		//얘네 둘 무조건 쳐라~
		System.out.println("Get 방식으로 들어옴!");
		
		//1
		String command = request.getParameter("command");
		System.out.println("command : " + command);
		
		//2
		//3
		//4
		//다음페이지 전달하기전에 서블릿에서 페이지를 만들어 볼게요
		PrintWriter out = response.getWriter();
		out.print("<h1 style='color: red'>Hello Servlet</h1>");
		out.print("<h2>계층구조, lifecycle, url-mapping</h1>");
		out.print("<a href='home.html'>home...</a>");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	
		System.out.println("post 방식으로 들어옴!");
		//1
		String command= request.getParameter("command");
		System.out.println("command : " + command);
		//2
		//3
		//4
		PrintWriter out = response.getWriter();
		out.println("<h1 style='color: blue'>Hello Servlet</h1>");
		out.println("<h2>init - service - doGet/doPost - destroy</h2>");
		out.println("<a href='home.html'>home...</a>");
		
	}
	
	
	@Override
	public void destroy() {
		System.out.println("servlet destroy");
	}

}



/*
 * 1.servlet-class태그에 써있는 클래스로 서블릿객체를 만들고
 * 
 * 2. 어노테이션으로 어노테이션이 써있는 파일의 서블릿객체를 만든다
 */









