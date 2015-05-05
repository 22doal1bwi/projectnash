package de.projectnash.application.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import de.projectnash.entities.Certificate;
import de.projectnash.entities.User;

/**
 * This class provides all methods that represents standardized mechanisms in
 * {@link Certificate} context.
 * 
 * @author Silvio D'Alessandro, Alexander Dobler
 * TODO: implement revokeCRT method
 *
 */
public class CertificateUtility {

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
	 * @author Alexander Dobler
	 */
	private static InputStream getCommandOutput(String... command)
			throws IOException {
		Process proc = Runtime.getRuntime().exec(command);
		return proc.getInputStream();
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
	private static File writeBytesToTempFile(byte[] binaryFileObject,
			FilePattern pattern) throws IOException {
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
	 */
	public static byte[] generatePrivateKey() throws IOException {

		// openssl genrsa 2048
		String[] command = {
				"openssl",
				"genrsa", Constants.KEY_BIT_LENGTH
				};

		/** get output of key generation command */
		InputStream in = getCommandOutput(command);
		/** prepare collection of output into a byte array */
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		/** write command output into byte stream */
		writeInputToOutput(in, out);

		return out.toByteArray();
	}

	/**
	 * Method which generates a certificate signing request (.csr file)
	 * 
	 * @param user
	 * @return request as byte array
	 * @throws IOException
	 * 
	 * @author Alexander Dobler, Silvio D'Alessandro
	 * TODO: add email address to csr
	 */
	public static byte[] generateCSR(User user, byte[] privateKey) throws IOException {

		/** get a temporary key file */
		File tmpKeyFile = writeBytesToTempFile(privateKey, FilePattern.KEY);		
		// openssl req -new -key privateKey.pem
		String[] command = {
				"openssl",
				"req",
				"-new",
				"-subj",
				"/C=" + user.getCountryName()+
				"/ST=" + user.getState() +
				"/L=" + user.getLocalityName() +
				"/O=" + user.getOrganizationName() +
				"/OU="+ user.getOrganzationalUnit() +
				"/CN=" + user.getCommonName() +
				"/emailAddress=" + user.getEmailAddress(),
				"-key",	tmpKeyFile.getAbsolutePath()
				};

		/** get output of key generation command as input stream */
		InputStream in = getCommandOutput(command);
		/** prepare collection of output into a byte array */
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		/** write command output into byte stream */
		writeInputToOutput(in, out);

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

		// openssl req -text -verify -in
		String[] command = {
				"openssl",
				"req",
				"-text",
				"-verify",
				"-in", csrFilePath
				};

		/** get output of key generation command */
		InputStream in = getCommandOutput(command);
		/** prepare collection of output into an byte array */
		OutputStream out = new ByteArrayOutputStream();
		/** write command output into byte stream */
		writeInputToOutput(in, out);

		return out.toString();
	}

	/**
	 * Method which generates a certificate (.crt file)
	 * 
	 * @param csrFilePath
	 * @return certificate as byte array
	 * @throws IOException
	 * @author Alexander Dobler, Tobias Burger
	 */
	public static byte[] generateCRT(byte[] csrData) throws IOException {

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

		/** get output of crt generation command as input stream */
		InputStream in = getCommandOutput(command);
		/** prepare collection of output into a byte array */
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		/** write command output into byte stream */
		writeInputToOutput(in, out);

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
		
		//openssl x509 -in cert.pem -noout -text
		String[] command = {
				"openssl",
				"x509",
				"-in", writeBytesToTempFile(crtData, FilePattern.CRT).getAbsolutePath(),
				"-noout",
				kindOfData			
				};

		/** get output of key generation command */
		InputStream in = getCommandOutput(command);
		/** prepare collection of output into an byte array */
		OutputStream out = new ByteArrayOutputStream();
		/** write command output into byte stream */
		writeInputToOutput(in, out);

		return out.toString();
	}
	
}
