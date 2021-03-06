package de.projectnash.application.util;

import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import de.projectnash.entities.Certificate;
import de.projectnash.application.util.OpenSSLException;

/**
 * This class provides all methods that represents standardized mechanisms in {@link Certificate} context.
 * 
 * @author Silvio D'Alessandro, Alexander Dobler
 *
 */
public class CertificateUtility {
	
	/* File constants. */
	private static final File ROOT_CONF_FILE = new File("pki/root_conf.cnf");
	private static final File ROOT_CERT_FILE = new File("pki/root_cert.pem");
	private static final File ROOT_KEY_FILE = new File("pki/root_key.pem");
	private static final File ROOT_CRL_FILE = new File("pki/root_crl.pem");
	private static final File CERTINDEX_FILE = new File("pki/certindex");
	static int enterKey = KeyEvent.VK_ENTER;

	/**
	 * Emun Class for .pem .csr .crt
	 * 
	 * @author Alexander Dobler
	 *
	 */
	public static enum FilePattern {

		KEY("key_", ".pem"),
		CSR("csr_", ".csr"),
		CRT("crt_", ".crt"),
		P12("pkcs12_", ".p12");

		final String prefix;
		final String suffix;

		private FilePattern(String prefix, String suffix) {
			this.prefix = prefix;
			this.suffix = suffix;
		}
	}

	/**
	 * Writes an InputStream into an OutputStream and flushes/closes it
	 * 
	 * @param in InputStream
	 * @param out OutputStream
	 * @throws IOException
	 * @author Alexander Dobler
	 */
	private static void writeInputToOutput(InputStream in, OutputStream out) throws IOException {
		int b;
		while ((b = in.read()) != -1) {
			out.write(b);
		}
		out.flush();
		out.close();
	}

	/**
	 * Writes the content of a binary byte array into a (temporary) {@link File} object
	 * 
	 * @param binaryFileObject file data as byte array
	 * @param pattern pattern enum object which defines prefix/suffix of the output file
	 * @return file object
	 * @throws IOException
	 * @author Alexander Dobler
	 */
	private static File writeBytesToTempFile(byte[] binaryFileObject, FilePattern pattern) throws IOException {
		
		/** create a empty file matching the pattern in the default temporary directory. */
		File tmp_file = File.createTempFile(pattern.prefix, pattern.suffix);
		
		/** write into file using fos in try with resources. */
		try (FileOutputStream fos = new FileOutputStream(tmp_file)) {
			fos.write(binaryFileObject);
		}
		return tmp_file;
	}

	/**
	 * Generates a new private key
	 * 
	 * @return private key as byte array
	 * @throws IOException
	 * @author Alexander Dobler, Silvio D'Alessandro
	 * @throws OpenSSLException 
	 */
	public static byte[] generatePrivateKey() throws IOException, OpenSSLException {

		String[] command = {
				"openssl",
				"genrsa", Constants.KEY_BIT_LENGTH
				};

		/** execute command */
		Process proc = Runtime.getRuntime().exec(command);
		
		/** get output of key generation command */
		InputStream in = proc.getInputStream();
		
		/** prepare collection of output into a byte array */
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		/** write command output into byte stream */
		writeInputToOutput(in, out);
		
		if (out.toString().isEmpty()) throw new OpenSSLException("Problem in private key generation method!");
		
		/** destroy openssl instance */
		proc.destroy();
		
		return out.toByteArray();
	}

	/**
	 * Method which generates a certificate signing request (.csr file)
	 * 
	 * @param countryName
	 * @param state
	 * @param localityName
	 * @param organizationName
	 * @param organizationalUnit
	 * @param commonName
	 * @param emailAddress
	 * @param privateKey
	 * @return request as byte array
	 * @throws IOException
	 * @author Alexander Dobler, Silvio D'Alessandro
	 * @throws OpenSSLException 
	 */
	public static byte[] generateCSR(String countryName, String state, String localityName, String organizationName, String organizationalUnit,
			String commonName, String emailAddress, byte[] privateKey) throws IOException, OpenSSLException {

		/** get a temporary key file */
		File tmpKeyFile = writeBytesToTempFile(privateKey, FilePattern.KEY);

		String[] command = {
				"openssl",
				"req",
				"-new",
				"-subj",
				"/C=" + countryName+
				"/ST=" + state +
				"/L=" + localityName +
				"/O=" + organizationName +
				"/OU="+ organizationalUnit +
				"/CN=" + commonName +
				"/emailAddress=" + emailAddress,
				"-key",	tmpKeyFile.getAbsolutePath()
				};

		/** execute command */
		Process proc = Runtime.getRuntime().exec(command);
		
		/** get output of key generation command as input stream */
		InputStream in = proc.getInputStream();
		
		/** prepare collection of output into a byte array */
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		/** write command output into byte stream */
		writeInputToOutput(in, out);
		
		/** check if problem occurred */
		if (out.toString().isEmpty()) throw new OpenSSLException("Problem in CSR generation method!");
		
		/** destroy openssl instance */
		proc.destroy();
		
		/** delete temporary files from disk */
		System.out.println(tmpKeyFile.delete() ? "Key file deleted" : "Key file delete failed ");
		
		return out.toByteArray();
	}

	/**
	 * Shows output of a CSR {@link File} check
	 * 
	 * @param csrFilePath path of certificate signing request
	 * @return output of CSR check
	 * @throws IOException
	 * @author Alexander Dobler, Silvio D'Alessandro
	 */
	public static String checkCSR(String csrFilePath) throws IOException {

		String[] command = {
				"openssl",
				"req",
				"-text",
				"-verify",
				"-in", csrFilePath
				};

		/** execute command */
		Process proc = Runtime.getRuntime().exec(command);
		
		/** get output of key generation command */
		InputStream in = proc.getInputStream();
		
		/** prepare collection of output into an byte array */
		OutputStream out = new ByteArrayOutputStream();
		
		/** write command output into byte stream */
		writeInputToOutput(in, out);
		
		/** destroy openssl instance */
		proc.destroy();

		return out.toString();
	}

	/**
	 * Method which generates a certificate (.crt file)
	 * 
	 * @param csrFilePath
	 * @return certificate as byte array
	 * @throws IOException
	 * @author Alexander Dobler, Tobias Burger
	 * @throws OpenSSLException 
	 */
	public static byte[] generateCRT(byte[] csrData) throws IOException, OpenSSLException {

		/** get temporary csr file */
		File tmpCsrFile = writeBytesToTempFile(csrData, FilePattern.CSR);

		String[] command = {
				"openssl",
				"x509",
				"-req",
				"-in", tmpCsrFile.getAbsolutePath(),
				"-CA", ROOT_CERT_FILE.getAbsolutePath(),
				"-CAkey", ROOT_KEY_FILE.getAbsolutePath(),
				"-CAcreateserial",
				"-CAserial", "pki/crlnumber",
				"-days", Constants.DAYS_VALID,
				"-sha512"
				};

		/** execute command */
		Process proc = Runtime.getRuntime().exec(command);
		
		/** get output of crt generation command as input stream */
		InputStream in = proc.getInputStream();
		
		/** prepare collection of output into a byte array */
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		/** write command output into byte stream */
		writeInputToOutput(in, out);
		
		/** check if errors occurred during crt generation */
		if (out.toString().isEmpty()) throw new OpenSSLException("Error during CRT generation.");
		
		/** destroy openssl instance */
		proc.destroy();
		
		/** delete temporary files from disk */
		System.out.println(tmpCsrFile.delete() ? "CSR file deleted" : "CSR file delete failed ");
		
		return out.toByteArray();
	}
	
	/**
	 * Method which generates a certificate (.p12 file)
	 * 
	 * @param crtData
	 * @param privateKey
	 * @param password password which protects pkcs12
	 * @return A byte array that contains the .p12 data
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws OpenSSLException
	 * @author Alexander Dobler, Jonathan Schlotz, Silvio D'Alessandro
	 */
	public static byte[] generatePKCS12(byte[] crtData, byte[] privateKey, String password) throws IOException, InterruptedException, OpenSSLException{
		
		/** get temporary crt + key files */
		File tmpCrtFile = writeBytesToTempFile(crtData, FilePattern.CRT);
		File tmpKeyFile = writeBytesToTempFile(privateKey, FilePattern.KEY);
		
		String[] command = {
				"openssl",
				"pkcs12",
				"-export",
				"-inkey", tmpKeyFile.getAbsolutePath(),
				"-in", tmpCrtFile.getAbsolutePath(),
				"-certfile", ROOT_CERT_FILE.getAbsolutePath(),
				"-password", "pass:"+password
				};
		
		/** execute command */
		Process proc = Runtime.getRuntime().exec(command);
		proc.waitFor();
		
		/** get output of pkcs12 generation command as input stream */
		InputStream in = proc.getInputStream();
		
		/** prepare collection of output into a byte array */
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		/** write command output into byte stream */
		writeInputToOutput(in, out);
		
		/** check if error occurred during PKCS12 generation */
		if (out.toString().isEmpty()) throw new OpenSSLException("Error during PKCS12 generation.");
		
		/** destroy openssl instance */
		proc.destroy();
		
		/** delete temporary files from disk */
		System.out.println(tmpCrtFile.delete() ? "CRT file deleted" : "CRT file delete failed ");
		System.out.println(tmpKeyFile.delete() ? "Key file deleted" : "Key file delete failed ");
		
		return out.toByteArray();
	}
	
	/**
	 * 
	 * Method which shows output of CRT {@link File} depending on the kind of data you want to get
	 * 
	 * @param crtData
	 * @param kindOfData "-text" (for all data) or "-dates" (for initialization and expiration dates) or "-subject" (for user information)
	 * @return
	 * @throws IOException
	 * @author Marius Boepple, Jonathan Schlotz
	 */
	public static String getCRTdata(byte[] crtData, String kindOfData) throws IOException{
		
		/** get temporary crt file */
		File tmpCrtFile = writeBytesToTempFile(crtData, FilePattern.CRT);
		
		String[] command = {
				"openssl",
				"x509",
				"-in", tmpCrtFile.getAbsolutePath(),
				"-noout",
				kindOfData			
				};

		/** execute command */
		Process proc = Runtime.getRuntime().exec(command);
		
		/** get output of key generation command */
		InputStream in = proc.getInputStream();
		
		/** prepare collection of output into an byte array */
		OutputStream out = new ByteArrayOutputStream();
		
		/** write command output into byte stream */
		writeInputToOutput(in, out);
		
		/** destroy openssl instance */
		proc.destroy();
		
		/** delete temporary files from disk */
		System.out.println(tmpCrtFile.delete() ? "CRT file deleted" : "CRT file delete failed ");
		
		return out.toString();
	}
	
	/**
	 * 
	 * Method which revokes a certificate
	 * 
	 * @param crtData certificate data as byte array
	 * @return
	 * @throws IOException
	 * @author Alexander Dobler, Tobias Burger
	 */
	public static boolean revokeCRT(byte[] crtData) throws IOException, InterruptedException, OpenSSLException{
		
		/** check if certindex exists and create it if not */
		if(!CERTINDEX_FILE.exists()){
			CERTINDEX_FILE.createNewFile();
		}
		
		File tmpCrtFile = writeBytesToTempFile(crtData, FilePattern.CRT);
		
		String[] command = {
				"openssl",
				"ca",
				"-config", ROOT_CONF_FILE.getAbsolutePath(),
				"-keyfile", ROOT_KEY_FILE.getAbsolutePath(),
				"-cert", ROOT_CERT_FILE.getAbsolutePath(),
				"-revoke", tmpCrtFile.getAbsolutePath()
				};
		
		/** execute command */
		Process proc = Runtime.getRuntime().exec(command);
		proc.waitFor();
				
		/** destroy openssl instance */
		proc.destroy();
		
		/** update the signed crl list */
		updateCRL();
		
		/** delete temporary files from disk */
		System.out.println(tmpCrtFile.delete() ? "CRT file deleted" : "CRT file delete failed ");
		
		return true;
	}
	
	/**
	 * Create/Update CRL file
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 * @author Alexander Dobler, Tobias Burger
	 */
	private static void updateCRL() throws IOException, InterruptedException{
					
		if(!ROOT_CRL_FILE.exists()){
			ROOT_CRL_FILE.createNewFile();
		}
		
		String[] command = {
				"openssl",
				"ca",
				"-config", ROOT_CONF_FILE.getAbsolutePath(),
				"-keyfile", ROOT_KEY_FILE.getAbsolutePath(),
				"-cert", ROOT_CERT_FILE.getAbsolutePath(),
				"-gencrl",
				"-out", ROOT_CRL_FILE.getAbsolutePath()				
				};
		
		/** execute command, wait for end of execution and destroy the process*/
		Process proc = Runtime.getRuntime().exec(command);
		proc.waitFor();
		proc.destroy();
	}
	
	/**
	 * Extracts the private key data.
	 * 
	 * @param p12Data
	 * @return
	 * @throws IOException
	 * @throws OpenSSLException
	 * @throws InterruptedException
	 */
	public static byte[] extractPrivateKey(byte[] p12Data, String password) throws IOException, OpenSSLException, InterruptedException{
		
		File tmpP12File = writeBytesToTempFile(p12Data, FilePattern.P12);
		
		String[] command = {
				"openssl",
				"pkcs12",				
				"-in", tmpP12File.getAbsolutePath(),
				"-nodes",
				"-nocerts",
				"-password", "pass:"+password
				};
		
		/** execute command */
		Process proc = Runtime.getRuntime().exec(command);
		proc.waitFor();
		
		/** get output of generation command as input stream */
		InputStream in = proc.getInputStream();
		
		/** prepare collection of output into a byte array */
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		/** write command output into byte stream */
		writeInputToOutput(in, out);
		
		if (out.toString().isEmpty()) throw new OpenSSLException("Error while extracting private key from PKCS12.");
		
		/** destroy openssl instance */
		proc.destroy();
		
		return out.toByteArray();
	}
	
	/**
	 * Loads .p12 {@link File} data
	 * 
	 * @param p12Data pkcs12 byte array
	 * @param password password which protects pkcs12
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws OpenSSLException
	 */
	public static String getP12data(byte[] p12Data, String password) throws IOException, InterruptedException, OpenSSLException{ 
		
		/** get temporary PKCS12 file */
		File tmpP12File = writeBytesToTempFile(p12Data, FilePattern.P12);
		
		String[] command = {
				"openssl",
				"pkcs12",
				"-in", tmpP12File.getAbsolutePath(),
				"-info",
				"-nodes",
				"-password", "pass:"+password
				};

		/** execute command */
		Process proc = Runtime.getRuntime().exec(command);
		proc.waitFor();
		
		/** get output of pkcs12 information command as input stream */
		InputStream in = proc.getInputStream();
		
		/** prepare collection of output into a byte array */
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		/** write command output into byte stream */
		writeInputToOutput(in, out);
		
		if (out.toString().isEmpty()) throw new OpenSSLException("Could not read input stream.");
		
		/** destroy openssl instance */
		proc.destroy();
		
		/** delete temporary files from disk */
		System.out.println(tmpP12File.delete() ? "P12 file deleted" : "P12 file delete failed ");
		
		return out.toString();		
	}	
}
