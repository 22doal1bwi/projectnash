package de.projectnash.application.util;

/**
 * This class provides all necessary constants for openSSL.
 * @author Silvio D'Alessandro
 *
 */
public class OpenSSLConstants {
	
	/** The bit lenght of the rsa key. */
	public static final int CMD_KEY_BIT_LENGTH = 2048;
	
	/** Duration of days a certificate is valid */
	public static final String CMD_DAYS_VALID = "730";
	
	/** The array that contains the standard command to generate a private key. */
	public static final String [] CMD_GENERATE_PRIVATE_KEY = {"openssl", "genrsa", String.valueOf(CMD_KEY_BIT_LENGTH)};
	
	/**
	 * Provides the standard command to check the csr request.
	 * @param filePath The {@link String} that represents the path of the csr file.
	 * @return An array that contains all information for the request check.
	 */
	public static final String [] getCsrCheckCommand(String filePath){
		
		String [] command = {
				"openssl",
				"req",
				"-text",
				"-verify",
				"-in", filePath
				};
		
		return command;
	}	

	/**
	 * Provides the standard command to generate a csr request.
	 * @param c The {@link String} that represents the country information.
	 * @param st The {@link String} that represents the state information. 
	 * @param l The {@link String} that represents the locality information.
	 * @param o The {@link String} that represents the organization information.
	 * @param ou The {@link String} that represents the organization name information.
	 * @param cn The {@link String} that represents the common name information.
	 * @return An array that contains all information for the csr request.
	 */
	public static final String [] getCsrGenerationCommand(String c, String st, String l, String o, String ou, String cn, String keyPath){
		
		String [] command = {
				"openssl",
				"req",
				"-new",
				"-subj",
				"/C=" + c + "/ST=" + st + "/L=" + l + "/O=" + o + "/OU=" + ou + "/CN=" + cn,
				"-key", keyPath
				};
		
		return command;
	}
	
	public static final String[] getCrtGenerationCommand(String csrPath, String rootKeyPath, String root_cert){
		
		//openssl x509 -req -in zertifikat.csr -CA ca-root.pem -CAkey ca-key.pem -CAcreateserial -days 720 -sha512
		String[] command = {
				"openssl",
				"x509",
				"-req",
				"-in", csrPath,
				"-CA", root_cert,
				"-CAkey", rootKeyPath,
				"-CAcreateserial",
				"-days", CMD_DAYS_VALID,
				"-sha512"
		};
			
		return command;
	}
	
	
	
};
