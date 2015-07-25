package chen.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {

	private static final char[]	HEX_DIGITS	= { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String encrypt(String algorithm, String plainText) throws NoSuchAlgorithmException {
		if (plainText == null) {
			return null;
		}
		MessageDigest ins = MessageDigest.getInstance(algorithm);
		ins.update(plainText.getBytes());
		byte[] b = ins.digest();

		return format(b);
	}

	public static String format(byte[] b) {
		StringBuilder s = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			s.append(HEX_DIGITS[(b[i] >> 4) & 0xf]).append(HEX_DIGITS[b[i] & 0xf]);
		}
		return s.toString();
	}

	public static void main(String[] args) {
		try {
			String secret = encrypt("MD5", "1234567");
			System.out.println("secret: " + secret);
			System.out.println(encrypt("sha1", "1234567"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		System.out.println(0x1c);
		System.out.println(0x86);
		System.out.println(0xee);
	}

}
