package de.projectnash.application.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import de.projectnash.entities.Certificate;

/**
 * This class provides all methods that represents standardized mechanisms in {@link Certificate} context. 
 * @author Silvio D'Alessandro, alexander
 *
 */
public class CertificateUtility {
	
	private static enum FilePattern{
		
		KEY("key_", ".pem"),
		CSR("csr_", ".csr"),
		CRT("crt_", ".crt"),
		;
			
		final String prefix;
		final String suffix;
		private FilePattern(String prefix, String suffix) {
			this.prefix = prefix;
			this.suffix = suffix;
		}
		
	}
	
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
	 * Writes the content of a binary byte array into a (temporary) file object
	 * 
	 * @param binaryFileObject file data as byte array
	 * @param pattern pattern enum object which defines prefix/suffix of the output file
	 * @return a file object
	 * @throws IOException
	 * @author alexander
	 */
	private static File writeBytesToTempFile(byte[] binaryFileObject, FilePattern pattern) throws IOException{
		/** create a empty file matching the pattern in the default temporary directory */
		File tmp_file = File.createTempFile(pattern.prefix, pattern.suffix);
		/** write into file using fos in try with resources */
		try(FileOutputStream fos = new FileOutputStream(tmp_file)){
			fos.write(binaryFileObject);
		}
		
		return tmp_file;
	}
	
	/**
	 * Generates a new private key
	 * 
	 * @return File instance referring to key file
	 * @throws IOException
	 * @author alexander, Silvio
	 */
	public static byte[] generatePrivateKey() throws IOException{
		
		/** get output of key generation command */
		InputStream in = getCommandOutput(OpenSSLConstants.CMD_GENERATE_PRIVATE_KEY);
		/** prepare collection of output into a byte array  */
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		/** write command output into byte stream */
		writeInputToOutput(in, out);
			
		return out.toByteArray();
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
	public static byte[] generateCSR(String c, String st, String l, String o, String ou, String cn, byte[] privateKeyFile) throws IOException {
		
		File keyTempFile = writeBytesToTempFile(privateKeyFile, FilePattern.KEY);
		/** get output of key generation command as input stream */
		InputStream in = getCommandOutput(OpenSSLConstants.getCsrGenerationCommand(c, st, l, o, ou, cn, keyTempFile.getPath()));
		/** prepare collection of output into a byte array  */
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		/** write command output into byte stream */
		writeInputToOutput(in, out);
		
		return out.toByteArray();
	}
	
	public static byte[] generateCRT(byte[] csrFile) throws IOException{
		
		File csrTempFile = writeBytesToTempFile(csrFile, FilePattern.CSR);
		File rootKeyFile = new File("root_key.pem");
		File rootCertFile = new File("root_cert.pem");
		/** get output of crt generation command as input stream */
		InputStream in = getCommandOutput(OpenSSLConstants.getCrtGenerationCommand(csrTempFile.getPath(), rootKeyFile.getAbsolutePath(), rootCertFile.getAbsolutePath()));
		/** prepare collection of output into a byte array  */
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		/** write command output into byte stream */
		writeInputToOutput(in, out);
		
		return out.toByteArray();
	}
	
	/**
	 * Test main um Klasse direkt starten zu können für Tests
	 * TODO: methode entfernen
	 */
	public static void main(String[] args) {
		
		try {
			byte[] keyData = generatePrivateKey();
			byte[] csrData = generateCSR("DE","Baden Wuerttemberg","Stuttgart", "Nash Inc.", "Student", "Tobi Burger", keyData);
			byte[] crtData = generateCRT(csrData);
			writeBytesToTempFile(crtData, FilePattern.CRT);
			//String verifyCSR = checkCSR(writeBytesToTempFile(csrData, FilePattern.CSR));
			//System.out.println(verifyCSR);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
