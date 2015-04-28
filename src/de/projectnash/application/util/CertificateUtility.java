package de.projectnash.application.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import de.projectnash.entities.Certificate;

/**
 * This class provides all methods that represents standardized mechanisms in {@link Certificate} context. 
 * @author Silvio D'Alessandro
 *
 */
public class CertificateUtility {
	
	/**
	 * Runs a command and returns its output as a input stream
	 * 
	 * @param command
	 * @return InputStream
	 * @throws IOException
	 * @author alexander
	 */
	private static InputStream getCommandOutput(String... command) throws IOException{
		Process proc = Runtime.getRuntime().exec(command);
		return proc.getInputStream();		
	}
	
	/**
	 * Writes an InputStream into an OutputStream and flushes/closes it
	 * 
	 * @param in InputStream
	 * @param out OutputStream
	 * @throws IOException
	 * @author alexander
	 */
	private static void writeInputToOutput(InputStream in, OutputStream out)throws IOException{
		int b;
		while( (b = in.read()) != -1) {
		   out.write(b);
		}
		out.flush();
		out.close();
	}
	
	/**
	 * Generates a new private key
	 * 
	 * @return File instance referring to key file
	 * @throws IOException
	 * @author alexander, Silvio
	 */
	public static File generatePrivateKey() throws IOException{
		
		/** get output of key generation command */
		InputStream in = getCommandOutput(OpenSSLConstants.CMD_GENERATE_PRIVATE_KEY);
		/** prepare collection of output into a file  */
		File privkeyOut = new File("privkey_temp.pem");
		OutputStream out = new FileOutputStream(privkeyOut);
		/** write command output into file stream */
		writeInputToOutput(in, out);
		
		return privkeyOut;
	}
	
	/**
	 * Shows output of a csr file check
	 * 
	 * @param csr a certificate signing request
	 * @return output of csr check
	 * @throws IOException
	 * @author alexander, Silvio
	 */
	public static String checkCSR(File csr) throws IOException{
		
		/** get output of key generation command */
		InputStream in = getCommandOutput(OpenSSLConstants.getCsrCheckCommand(csr.getPath()));
		/** prepare collection of output into an byte array */
		OutputStream out = new ByteArrayOutputStream();
		/** write command output into byte stream */		
		writeInputToOutput(in, out);
		
		return out.toString();
	}
	
	/**
	 * Method which generates a certificate signing request (.csr file) 
	 * 
	 * @param c Country
	 * @param st State
	 * @param l Location
	 * @param o Organization
	 * @param ou OrganizationUnit
	 * @param cn CommonName
	 * @return File object referring to the created .csr file
	 * @throws IOException
	 * @author alexander, Silvio
	 */
	public static File generateCSR(String c, String st, String l, String o, String ou, String cn) throws IOException{
		
		/** get output of key generation command as input stream */
		InputStream in = getCommandOutput(OpenSSLConstants.getCsrGenerationCommand(c, st, l, o, ou, cn));
		/** prepare collection of output into a file  */
		File csrOut = new File("csr_temp.csr");
		FileOutputStream out = new FileOutputStream(csrOut);
		/** write command output into file stream */
		writeInputToOutput(in, out);
		
		return csrOut;
	}
	
	/**
	 * Test main um Klasse direkt starten zu können für Tests
	 * TODO: methode entfernen
	 */
	public static void main(String[] args) {
		
		try {
			
			File csrFile = generateCSR("DE","Baden Wuerttemberg","Stuttgart", "Nash Inc.", "CA" ,"simpleCert");
			String verifyCSR = checkCSR(csrFile);
			System.out.println(verifyCSR);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
