package de.projectnash.frontend;

	import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.projectnash.application.SessionLogic;
import de.projectnash.application.UserLogic;
import de.projectnash.entities.User;
	 
	/**
	 * Servlet implementation class Certificate Servlet
	 */
	@WebServlet("/CertificateServlet")
	public class CertificateServlet extends HttpServlet {
	    	 
		private static final long serialVersionUID = 4192643567772796818L;
		
		protected void doPost(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException {
			
			
			
//			boolean validSession = SessionLogic.checkSession(request, response);
//			System.out.println("Session is: " + validSession);
//			if(validSession){
//	
//			}else {
//				RequestDispatcher rd = request.getRequestDispatcher("/intern/pages/index.jsp");
//				rd.forward(request, response);
//			}
		

	        request.getRequestDispatcher("/intern/pages/beantragen.jsp").forward(request, response);

			
			
	    }
	 
	}
