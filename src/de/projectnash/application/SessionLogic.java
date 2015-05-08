package de.projectnash.application;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.projectnash.databackend.SessionPersistenceService;
import de.projectnash.entities.Session;
import de.projectnash.entities.User;

/**
 * This class provides all methods to handle the {@link Session}.
 * 
 * @author Marius Boepple
 *
 */
public class SessionLogic {
	
	public static boolean createSession(User user, String ssnId) {
		
		Session session = new Session(user, ssnId);
		SessionPersistenceService.storeSession(session);
		
		return false;
	}

	public static void removeSession(String ssnId){
		SessionPersistenceService.removeSession(SessionPersistenceService.loadSession(ssnId));
	}
	
	public static Session loadSession(String ssnId){
		return SessionPersistenceService.loadSession(ssnId);
	}
	
	public static boolean checkSession(String ssnId){
		return SessionPersistenceService.sessionExists(ssnId);
	}
	
	public static boolean checkSession(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		User user = null;
		String ssnId = null;
		HttpSession session = request.getSession();
	
		if (session.getAttribute("emailAddress") == null) {
			RequestDispatcher rd = request.getRequestDispatcher("/intern/pages/index.jsp");
			rd.forward(request, response);
		} else {
			
			Cookie[] cookies = request.getCookies();
			if (cookies == null) {
				
			} else {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("JSESSIONID")){
						ssnId = cookie.getValue();
					}
				}
				Session appSession = SessionPersistenceService.loadSession(ssnId);
				user = appSession.getUser();
				System.out.println(user.toString());
				return true;
			}
		}
		return false;
	}
}
