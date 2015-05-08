package de.projectnash.frontend;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.projectnash.application.SessionLogic;

public class SessionController {
	
	/**
	 * Returns a int value depending on session status.
	 * 
	 * @param request a HttpServletRequest
	 * @param response a HttpServletResponse
	 * @return 
	 * <p>-1: Session does not contain simplecert attributes</p>
	 * <p>0: Session is not known in database</p>
	 * <p>other: Session exists and is available in database</p>
	 * @throws IOException
	 * @throws ServletException
	 */
	public static String checkForSessionId(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		/** get session from request */
		HttpSession session = request.getSession();
	
		/** check if session contains a simplecert attribute */
		if (session.getAttribute("emailAddress") == null) {
			return "-1";
		} else {
			
			/**  */
			Cookie[] cookies = request.getCookies();
			if (cookies == null) {
				/** cookies empty */
				return "-1";
			} else {
				/** get ssnId from cookie */ 
				String ssnId = null;
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("JSESSIONID")){
						ssnId = cookie.getValue();
					}
				}
			/** check if session is available in DB if true return ssnId else 0 */
			return SessionLogic.checkSession(ssnId) ? ssnId : "0";
			}
		}

	}
	
}
