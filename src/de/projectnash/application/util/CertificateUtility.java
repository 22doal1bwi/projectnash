package de.projectnash.application.util;

import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import de.projectnash.entities.Certificate;
import de.projectnash.exceptions.OpenSSLException;

/**
 * This class provides all methods that represents standardized mechanisms in
 * {@link Certificate} context.
 * 
 * @author Silvio D'Alessandro, Alexander Dobler
 * TODO: implement revokeCRT method
 *
 */
public class CertificateUtility {
	
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
		P12("pkcs12_", ".p12")
		;

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
	private static void writeInputToOutput(InputStream in, OutputStream out)
			throws IOException {
		int b;
		while ((b = in.read()) != -1) {
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
	 * @return file object
	 * @throws IOException
	 * @author Alexander Dobler
	 */
	private static File writeBytesToTempFile(byte[] binaryFileObject, FilePattern pattern) throws IOException {
		/**
		 * create a empty file matching the pattern in the default temporary
		 * directory
		 */
		File tmp_file = File.createTempFile(pattern.prefix, pattern.suffix);
		/** write into file using fos in try with resources */
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
	public static byte[] generateCSR(String countryName, String state,
			String localityName, String organizationName, String organizationalUnit,
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
		
		if (out.toString().isEmpty()) throw new OpenSSLException("Problem in CSR generation method!");
		
		/** destroy openssl instance */
		proc.destroy();
		
		return out.toByteArray();
	}

	/**
	 * Shows output of a CSR file check
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

		/** get simpleCert root files */
		File rootKeyFile = new File("root_key.pem");
		File rootCertFile = new File("root_cert.pem");

		File tmpCsrFile = writeBytesToTempFile(csrData, FilePattern.CSR);
		// openssl x509 -req -in userRequest.csr -CA rootCert.pem -CAkey
		// rootKey.pem -CAcreateserial -days 730 -sha512
		String[] command = {
				"openssl",
				"x509",
				"-req",
				"-in", tmpCsrFile.getAbsolutePath(),
				"-CA", rootCertFile.getAbsolutePath(),
				"-CAkey", rootKeyFile.getAbsolutePath(),
				"-CAcreateserial",
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
		
		if (out.toString().isEmpty()) throw new OpenSSLException("Root files not found.");
		
		/** destroy openssl instance */
		proc.destroy();
		
		return out.toByteArray();
	}
	
	/**
	 * Method which generates a certificate (.p12 file)
	 * @param crtData
	 * @param privateKey
	 * @return A byte array that contains the .p12 data
	 * @throws IOException
	 * @throws InterruptedException
	 * @author Alexander Dobler, Jonathan Schlotz, Silvio D'Alessandro
	 * @throws OpenSSLException 
	 */
	public static byte[] generatePKCS12(byte[] crtData, byte[] privateKey) throws IOException, InterruptedException, OpenSSLException{
		
		File rootCertFile = new File("root_cert.pem");
		
		File tmpCrtFile = writeBytesToTempFile(crtData, FilePattern.CRT);
		File tmpKeyFile = writeBytesToTempFile(privateKey, FilePattern.KEY);
		Path tmpP12File = Files.createTempFile(FilePattern.P12.prefix, FilePattern.P12.suffix);
		
		String[] command = {
				"openssl",
				"pkcs12",
				"-export",
				"-inkey", tmpKeyFile.getAbsolutePath(),
				"-in", tmpCrtFile.getAbsolutePath(),
				"-certfile", rootCertFile.getAbsolutePath(),
				"-out", tmpP12File.toString(),
				"-password", "pass:"
				};
		
		Process proc = Runtime.getRuntime().exec(command);
		proc.waitFor();
		
		FileInputStream fileInputStream = new FileInputStream (tmpP12File.toFile());
		
		/** prepare collection of output into a byte array */
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		writeInputToOutput(fileInputStream, out);
		/** destroy openssl instance */
		
		if (out.toString().isEmpty()) throw new OpenSSLException("Could not read input stream.");
		
		/** destroy openssl instance */
		proc.destroy();
		
		return out.toByteArray();
	}
	
	/**
	 * 
	 * Method which shows output of CRT file depending on the kind of data you want to get
	 * 
	 * @param crtData
	 * @param kindOfData "-text" (for all data) or "-dates" (for initialization and expiration dates) or "-subject" (for user information)
	 * @return
	 * @throws IOException
	 * @author Marius Boepple, Jonathan Schlotz
	 */
	public static String getCRTdata(byte[] crtData, String kindOfData) throws IOException{
		
		String[] command = {
				"openssl",
				"x509",
				"-in", writeBytesToTempFile(crtData, FilePattern.CRT).getAbsolutePath(),
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
		
		return out.toString();
	}
	
}
