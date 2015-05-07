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

import de.projectnash.databackend.SessionPersistenceService;
import de.projectnash.databackend.UserPersistenceService;
import de.projectnash.entities.Session;
import de.projectnash.entities.User;
	 
	/**
	 * Servlet implementation class LoginServlet
	 */
	@WebServlet("/LoginServlet")
	public class LoginServlet extends HttpServlet {
	    	 

		private static final long serialVersionUID = 4192643567772796818L;
		
		private static final String MAIL_ADDRESS = "emailAddress";

		protected void doPost(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException {
	 
	        /** get request parameters for userID and password */
	        String emailAddress = request.getParameter(MAIL_ADDRESS);
	        String password = request.getParameter("password");
	        
	        /** try to load user from database */
	        User loadedUser = UserPersistenceService.loadUser(emailAddress);
	        
	        /** check if a user was recevied and password is correct */
	        if(loadedUser != null&&loadedUser.getPassword().equals(password)){

	        	HttpSession httpSession = request.getSession();
	        	httpSession.setAttribute(MAIL_ADDRESS, loadedUser.getEmailAddress());
	        	
	        	/** setting session to expiry in 30 mins */
	        	httpSession.setMaxInactiveInterval(30*60);
	        	httpSession.getId();
	        	
	        	Session session = new Session(loadedUser, httpSession.getId());
	        	SessionPersistenceService.storeSession(session);
	        	
	        	Cookie userName = new Cookie(MAIL_ADDRESS, emailAddress);
	        	userName.setMaxAge(30*60);
 	            response.addCookie(userName);
 	            response.sendRedirect("intern/index.html");
	 	    
	       } else {
	    	   /** user not present or password wrong */
	    	    RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
	            PrintWriter out= response.getWriter();
	            out.println("<font color=red>Your E-Mail-Address or password is incorrect.</font>");
	            rd.include(request, response);
	       }
	    }
	 
	}
