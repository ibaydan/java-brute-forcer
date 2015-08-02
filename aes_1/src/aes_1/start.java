/**
 * 
 */
package aes_1;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author ismail
 *
 */
public class start {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String cipher_file = args[0];
		String key_file = args[1];
		String target_file = args[2];

		/*
		 * System.out.printf("Cipher file %s, Key file %s, Target file %s",
		 * cipher_file, key_file, target_file);
		 * 
		 * 
		 * 
		 * 
		 * String running_path = System.getProperty("user.dir"); File file = new
		 * File("."); for (String fileNames : file.list())
		 * System.out.println(fileNames);
		 * 
		 * byte[] test_byte = new byte[] { 72, 101, 108, 108, 111, 32, 116, 104,
		 * 101, 114, 101, 46, 32, 72, 111, 119, 32, 97, 114, 101, 32, 121, 111,
		 * 117, 63, 32, 72, 97, 118, 101, 32, 97, 32, 110, 105, 99, 101, 32,
		 * 100, 97, 121, 46, 72, 101, 108, 108, 111, 32, 116, 104, 101, 114,
		 * 101, 46, 32, 72, 111, 119, 32, 97, 114, 101, 32, 121, 111, 117, 63,
		 * 32, 72, 97, 118, 101, 32, 97, 32, 110, 105, 99, 101, 32, 100, 97,
		 * 121, 46, 72, 101, 108, 108, 111, 32, 116, 104, 101, 114, 101, 46, 32,
		 * 72, 111, 119, 32, 97, 114, 101, 32, 121, 111, 117, 63, 32, 72, 97,
		 * 118, 101, 32, 97, 32, 110, 105, 99, 101, 32, 100, 97, 121, 46 };
		 * a.writeFile("deneme", test_byte); String s =
		 * "Hello there. How are you? Have a nice day.Hello there. How are you? Have a nice day.Hello there. How are you? Have a nice day."
		 * ; byte[] deneme = s.getBytes();
		 */

		start a = new start();

		try {
			// Generate keys

			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128);

			// SecretKey aesKey = kgen.generateKey();
			byte[] key = a.readFile(key_file);
			SecretKey aesKey = new SecretKeySpec(key, "AES");
			// a.writeFile("key", aesKey.getEncoded());

			// Encrypt cipher

			// byte[] encryptedBytes = a.myEncrypt(aesKey, a.readFile("clear"));

			byte[] encryptedBytes = a.readFile(cipher_file);
			byte[] message = a.myDecrypt(aesKey, encryptedBytes);
			a.writeFile(target_file, message);
			// a.writeFile("encrypted", encryptedBytes);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public byte[] readFile(String path) throws IOException {
		File asdf = new File(path);
		byte[] byteArray = new byte[(int) asdf.length()];

		FileInputStream in;
		try {
			in = new FileInputStream(path);
			in.read(byteArray);
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return byteArray;

	}

	public int writeFile(String path, byte[] byteArray) throws IOException {

		FileOutputStream out;
		int byte_count = 0;
		try {
			out = new FileOutputStream(path);
			out.write(byteArray);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 1;

	}

	public byte[] myDecrypt(SecretKey aesKey, byte[] encryptedBytes) {

		byte[] decryptedBytes = null;

		try {
			// Decrypt cipher
			Cipher decryptCipher = Cipher.getInstance(" AES/ECB/PKCS5Padding");

			decryptCipher.init(Cipher.DECRYPT_MODE, aesKey);

			// Decrypt
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			outputStream = new ByteArrayOutputStream();
			ByteArrayInputStream inStream = new ByteArrayInputStream(
					encryptedBytes);
			CipherInputStream cipherInputStream = new CipherInputStream(
					inStream, decryptCipher);
			byte[] buf = new byte[128];
			int bytesRead;
			while ((bytesRead = cipherInputStream.read(buf)) >= 0) {
				outputStream.write(buf, 0, bytesRead);
			}
			decryptedBytes = outputStream.toByteArray();
			System.out.println("Result: "
					+ new String(outputStream.toByteArray()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return decryptedBytes;

	}

	public byte[] myEncrypt(SecretKey aesKey, byte[] clearBytes) {
		byte[] encryptedBytes = null;

		try {

			Cipher encryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			encryptCipher.init(Cipher.ENCRYPT_MODE, aesKey);
			System.out.printf("SecretKey %s\n", aesKey.getEncoded());
			// Encrypt
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			CipherOutputStream cipherOutputStream = new CipherOutputStream(
					outputStream, encryptCipher);
			// cipherOutputStream.write(s.getBytes());
			cipherOutputStream.write(clearBytes);
			cipherOutputStream.flush();
			cipherOutputStream.close();
			encryptedBytes = outputStream.toByteArray();

			System.out
					.printf("Encrypted bytes %s\n", encryptedBytes.toString());

		} catch (Exception ex) {
			ex.printStackTrace();

		}

		return encryptedBytes;
	}

}
