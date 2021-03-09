package com.scope.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/ScopeController")
public class ScopeController extends HttpServlet {
   private static final long serialVersionUID = 1L;
       
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      request.setCharacterEncoding("UTF-8");
      response.setContentType("text/html; charset=UTF-8");
      
      String requestId = request.getAttribute("requestId") + "";
      
      HttpSession session = request.getSession();
      String sessionId = session.getAttribute("sessionId") + "";
      
      ServletContext application = getServletContext();
      String applicationId = application.getAttribute("applicationId") + "";
      
      System.out.println("requestId : " + requestId);
      System.out.println("sessionId : " + sessionId); 
      System.out.println("applicationId : " + applicationId);
      
      String myRequest = request.getParameter("myRequest");
      System.out.println("myRequest : " + myRequest);
      
      PrintWriter out = response.getWriter();
      String html = "<h1>응답</h1>"
            + "<table border='1'>"
               + "<tr>"
                  + "<th>request</th>"
                  //+ "<td>" + requestId +"</td>"
                  + "<td>" + myRequest + "</td>"
               + "</tr>"
               + "<tr>"
                  + "<th>session</th>"
                  + "<td>" + sessionId + "</td>"
               + "</tr>"
               + "<tr>"
                  + "<th>application</th>"
                  + "<td>" + applicationId + "</td>"
               + "</tr>"
            + "</table>";
      out.print(html);
      
      // 서블릿에서 값을 넘겨주고 해당 페이지에서 처리할 수 있도록 하는 방법
		  //request.setAttribute("myRequest", myRequest);
		  //request와 response를 전달 
		  RequestDispatcher dispatch = request.getRequestDispatcher("result.jsp");
		  dispatch.forward(request, response);
		 
      
   }

   
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      
      doGet(request, response);
   }

}
