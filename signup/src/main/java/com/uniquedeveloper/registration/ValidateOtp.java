package com.uniquedeveloper.registration;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class ValidateOtp
 */
@WebServlet("/ValidateOtp")
public class ValidateOtp extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int value = Integer.parseInt(request.getParameter("otp"));
            HttpSession session = request.getSession();
            int otp = (int) session.getAttribute("otp");

            RequestDispatcher dispatcher = null;

            if (value == otp) {
                request.setAttribute("email", request.getParameter("email"));
                request.setAttribute("status", "success");
                dispatcher = request.getRequestDispatcher("newPassword.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("message", "Wrong OTP");
                dispatcher = request.getRequestDispatcher("EnterOtp.jsp");
                dispatcher.forward(request, response);
            }
        } catch (NumberFormatException e) {
            // Handle the case where the user entered a non-numeric value for OTP
            request.setAttribute("message", "Invalid OTP format");
            RequestDispatcher dispatcher = request.getRequestDispatcher("EnterOtp.jsp");
            dispatcher.forward(request, response);
        }
    }
}
