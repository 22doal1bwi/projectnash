package de.projectnash.frontend.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.projectnash.application.SessionLogic;

/**
 * This class represents the controller in {@link Session} context.
 * 
 * @author Alexander Dobler
 *
 */
public class SessionController {
	
	/**
	 * Checks if the {@link Session} exists.
	 * 
	 * @param request a HttpServletRequest
	 * @param response a HttpServletResponse
	 * @return A {@link Integer} that represents the value depending on {@link Session} status.
	 * <p>-1: Session does not contain simplecert attributes</p>
	 * <p>0: Session is not known in database</p>
	 * <p>other: Session exists and is available in database</p>
	 * @throws IOException
	 * @throws ServletException
	 */
	public static String checkForSessionId(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		/** get session from request. */
		HttpSession session = request.getSession();
	
		/** check if session contains a simplecert attribute. */
		if (session.getAttribute("emailAddress") == null || request.getCookies() == null) {
			return "-1";
		} else {
			String ssnId = session.getId();
			
			/** check if session is available in DB if true return ssnId else 0. */
			return SessionLogic.checkSession(ssnId) ? ssnId : "0";
			}
		}
	}