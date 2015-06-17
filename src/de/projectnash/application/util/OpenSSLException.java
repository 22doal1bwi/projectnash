package de.projectnash.application.util;

/**
 * This class provides a self named exception type.
 * 
 * @author Marius Boepple
 *
 */
public class OpenSSLException extends Exception {

	private static final long serialVersionUID = 3166092358585680625L;

	public OpenSSLException(String message) {
		super(message);
	}
}
