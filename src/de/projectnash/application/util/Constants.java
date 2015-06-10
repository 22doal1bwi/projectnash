package de.projectnash.application.util;

/**
 * This class provides all necessary constants
 * 
 * @author Marius Boepple, Jonathan Schlotz
 *
 */
public class Constants {

	/** The bit length of the RSA key */
	public static final String KEY_BIT_LENGTH = "2048";

	/** Duration of days a certificate is valid */
	public static final String DAYS_VALID = "730";

	/** Timeframe in which a certificate can be extended before it gets invalid */
	public static final int TIMEFRAME_FOR_EXTENSION = 30;
	
	/** Timeframe in which the certificate's user will receive a reminder to extend his certificate.*/
	public static final int TIMEFRAME_FOR_REMINDER = 14;
}
