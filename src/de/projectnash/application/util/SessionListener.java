package de.projectnash.application.util;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import de.projectnash.application.SessionLogic;

/**
 * This class provides a {@link HttpSessionListener} that removes every session if any destruction action is called on it.
 * @author Silvio D'Alessandro
 *
 */
public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {	
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		SessionLogic.removeSession(sessionEvent.getSession().getId());		
	}
}
