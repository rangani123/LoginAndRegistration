package com.uniquedeveloper.registration;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mysql.cj.protocol.Resultset;

@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uemail=request.getParameter("username");
		String upwd = request.getParameter("password");
		
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher = null;
		if(uemail==null || uemail.equals("") ) {
			request.setAttribute("status","invalidEmail");
			dispatcher=request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
		}
		if(upwd==null || upwd.equals("") ) {
			request.setAttribute("status","invalidPassword");
			dispatcher=request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
		}
		Connection con =null;
		PreparedStatement pst=null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		con =DriverManager.getConnection("jdbc:mysql://localhost:3306/youtube?useSSL=false","root","1234");
		pst=con.prepareStatement("select * from users where uemail=? and upwd=?");
	
		pst.setString(1, uemail);
		pst.setString(2, upwd);
        ResultSet rs = pst.executeQuery();
		if(rs.next()) {
			session.setAttribute("name",rs.getString("uname"));
			dispatcher=request.getRequestDispatcher("index.jsp");
		}else {
			request.setAttribute("status","failed");
			dispatcher=request.getRequestDispatcher("login.jsp");
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


