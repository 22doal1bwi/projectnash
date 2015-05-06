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

		protected void doPost(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException {
	 
	        // get request parameters for userID and password
	        String emailAddress = request.getParameter("emailAddress");
	        String password = request.getParameter("password");
	        
	        User loadedUser = UserPersistenceService.loadUser(emailAddress);
	        
	        if(loadedUser!=null){
	            HttpSession httpSession = request.getSession();
	            httpSession.setAttribute("emailAddress", loadedUser.getFirstName());
	            //setting session to expiry in 30 mins
	            httpSession.setMaxInactiveInterval(30*60);
	            httpSession.getId();
	            
	            Session session = new Session(loadedUser, httpSession.getId());
	            SessionPersistenceService.storeSession(session);
	            
	            Cookie userName = new Cookie("emailAddress", emailAddress);
	            userName.setMaxAge(30*60);
	            response.addCookie(userName);
	            response.sendRedirect("LoginSuccess.jsp");
	        }else{
	            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
	            PrintWriter out= response.getWriter();
	            out.println("<font color=red>Either user name or password is wrong.</font>");
	            rd.include(request, response);
	        }
	 
	    }
	 
	}
