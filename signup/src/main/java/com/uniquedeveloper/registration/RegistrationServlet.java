package com.uniquedeveloper.registration;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uname = request.getParameter("name");
		String uemail = request.getParameter("email");
		String upwd= request.getParameter("pass");
		String Reupwd= request.getParameter("re-pass");
		String umobile = request.getParameter("contact");
		 RequestDispatcher dispatcher = null;
		 Connection con =null;
			PreparedStatement pst=null;
			if(uname==null || uname.equals("") ) {
				request.setAttribute("status","invalidName");
				dispatcher=request.getRequestDispatcher("registration.jsp");
				dispatcher.forward(request, response);
			}
			if(uemail==null || uemail.equals("") ) {
				request.setAttribute("status","invalidEmail");
				dispatcher=request.getRequestDispatcher("registration.jsp");
				dispatcher.forward(request, response);
			}
			if(upwd==null || upwd.equals("") ) {
				request.setAttribute("status","invalidPassword");
				dispatcher=request.getRequestDispatcher("registration.jsp");
				dispatcher.forward(request, response);
			}
//			else if(!upwd.equals(Reupwd)) {
//				request.setAttribute("status","invalidConfirmPassword");
//				dispatcher=request.getRequestDispatcher("registration.jsp");
//				dispatcher.forward(request, response);
//			}
			if(umobile==null || umobile.equals("") ) {
				request.setAttribute("status","invalidMobilel");
				dispatcher=request.getRequestDispatcher("registration.jsp");
				dispatcher.forward(request, response);
			}else if(umobile.length()>10) {
				request.setAttribute("status","invalidMobileLength");
				dispatcher=request.getRequestDispatcher("login.registration.jsp");
				dispatcher.forward(request, response);
			}
			
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		con =DriverManager.getConnection("jdbc:mysql://localhost:3306/youtube?useSSL=false","root","1234");
		pst=con.prepareStatement("insert into users(uname,uemail,upwd,umobile)values(?,?,?,?)");
		pst.setString(1, uname);
		pst.setString(2, uemail);
		pst.setString(3, upwd);
		pst.setString(4, umobile);
		int rowsCount=pst.executeUpdate();
		dispatcher=request.getRequestDispatcher("/registration.jsp");
		if(rowsCount>0) {
			request.setAttribute("status","success");
		}else {
			request.setAttribute("status","failed");
		}
		dispatcher.forward(request, response);
		

			
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
				pst.close();
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
