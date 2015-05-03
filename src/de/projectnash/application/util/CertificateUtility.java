package de.projectnash.application.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import de.projectnash.entities.Certificate;

/**
 * This class provides all methods that represents standardized mechanisms in
 * {@link Certificate} context.
 * 
 * @author Silvio D'Alessandro, Alexander Dobler, Tobias Burger, Marius Boepple
 *
 */
public class CertificateUtility {

	/** The bit length of the RSA key */
	public static final String CMD_KEY_BIT_LENGTH = "2048";

	/** Duration of days a certificate is valid */
	public static final String CMD_DAYS_VALID = "730";

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
	public static File writeBytesToTempFile(byte[] binaryFileObject,
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
				"genrsa", CMD_KEY_BIT_LENGTH
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
	 * @param countryName
	 * @param state
	 * @param localityName
	 * @param organizationName
	 * @param organizationalUnit
	 * @param commonName
	 * @param privateKeyFilePath
	 * @return request as byte array
	 * @throws IOException
	 * 
	 * @author Alexander Dobler, Silvio D'Alessandro
	 * 
	 */
	public static byte[] generateCSR(String countryName, String state,
			String localityName, String organizationName,
			String organizationalUnit, String commonName,
			String privateKeyFilePath) throws IOException {

		// openssl req -new -key privateKey.pem
		String[] command = {
				"openssl",
				"req",
				"-new",
				"-subj",
				"/C=" + countryName +
				"/ST=" + state +
				"/L=" + localityName +
				"/O=" + organizationName +
				"/OU="+ organizationalUnit +
				"/CN=" + commonName,
				"-key",	privateKeyFilePath
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
	public static byte[] generateCRT(String csrFilePath) throws IOException {

		/** get simpleCert root files */
		File rootKeyFile = new File("root_key.pem");
		File rootCertFile = new File("root_cert.pem");

		// openssl x509 -req -in userRequest.csr -CA rootCert.pem -CAkey
		// rootKey.pem -CAcreateserial -days 730 -sha512
		String[] command = {
				"openssl",
				"x509",
				"-req",
				"-in", csrFilePath,
				"-CA", rootCertFile.getAbsolutePath(),
				"-CAkey", rootKeyFile.getAbsolutePath(),
				"-CAcreateserial",
				"-days", CMD_DAYS_VALID,
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
}
