package chen.util;


public class Base64 {
	private static final char[]	intToBase64		= { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };

	private static final char[]	intToAltBase64	= { '!', '"', '#', '$', '%', '&', '\'', '(', ')', ',', '-', '.', ':', ';', '<', '>', '@', '[', ']', '^', '`', '_', '{', '|', '}', '~', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '?' };

	private static final byte[]	base64ToInt		= { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51 };

	private static final byte[]	altBase64ToInt	= { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, -1, 62, 9, 10, 11, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 12, 13, 14, -1, 15, 63, 16, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 17, -1, 18, 19, 21, 20, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 22, 23, 24, 25 };

	public static String byteArrayToBase64(byte[] a) {
		return byteArrayToBase64(a, false);
	}

	static String byteArrayToAltBase64(byte[] a) {
		return byteArrayToBase64(a, true);
	}

	public static String byteArrayToBase64(byte[] a, boolean alternate) {
		if (a == null) {
			return null;
		}
		if (a.length == 0) {
			return "";
		}
		int aLen = a.length;
		int numFullGroups = aLen / 3;
		int numBytesInPartialGroup = aLen - 3 * numFullGroups;
		int resultLen = 4 * ((aLen + 2) / 3);
		StringBuffer result = new StringBuffer(resultLen);
		char[] intToAlpha = alternate ? intToAltBase64 : intToBase64;

		int inCursor = 0;
		for (int i = 0; i < numFullGroups; i++) {
			int byte0 = a[(inCursor++)] & 0xFF;
			int byte1 = a[(inCursor++)] & 0xFF;
			int byte2 = a[(inCursor++)] & 0xFF;
			result.append(intToAlpha[(byte0 >> 2)]);
			result.append(intToAlpha[(byte0 << 4 & 0x3F | byte1 >> 4)]);
			result.append(intToAlpha[(byte1 << 2 & 0x3F | byte2 >> 6)]);
			result.append(intToAlpha[(byte2 & 0x3F)]);
		}

		if (numBytesInPartialGroup != 0) {
			int byte0 = a[(inCursor++)] & 0xFF;
			result.append(intToAlpha[(byte0 >> 2)]);
			if (numBytesInPartialGroup == 1) {
				result.append(intToAlpha[(byte0 << 4 & 0x3F)]);
				result.append("==");
			} else {
				int byte1 = a[(inCursor++)] & 0xFF;
				result.append(intToAlpha[(byte0 << 4 & 0x3F | byte1 >> 4)]);
				result.append(intToAlpha[(byte1 << 2 & 0x3F)]);
				result.append('=');
			}

		}

		return result.toString();
	}

	public static byte[] base64ToByteArray(String s) {
		return base64ToByteArray(s, false);
	}

	static byte[] altBase64ToByteArray(String s) {
		return base64ToByteArray(s, true);
	}

	public static byte[] base64ToByteArray(String s, boolean alternate) {
		if (s == null) {
			return null;
		}
		if (s.length() == 0) {
			return new byte[0];
		}
		s = s.replaceAll("\n", "");
		s = s.replaceAll("\r", "");
		// s = StringUtils.replace(s, "\n", "");
		// s = StringUtils.replace(s, "\r", "");

		byte[] alphaToInt = alternate ? altBase64ToInt : base64ToInt;
		int sLen = s.length();
		int numGroups = sLen / 4;
		if (4 * numGroups != sLen) throw new IllegalArgumentException("String length must be a multiple of four.");
		int missingBytesInLastGroup = 0;
		int numFullGroups = numGroups;
		if (sLen != 0) {
			if (s.charAt(sLen - 1) == '=') {
				missingBytesInLastGroup++;
				numFullGroups--;
			}
			if (s.charAt(sLen - 2) == '=') missingBytesInLastGroup++;
		}
		byte[] result = new byte[3 * numGroups - missingBytesInLastGroup];

		int inCursor = 0;
		int outCursor = 0;
		for (int i = 0; i < numFullGroups; i++) {
			int ch0 = base64toInt(s.charAt(inCursor++), alphaToInt);
			int ch1 = base64toInt(s.charAt(inCursor++), alphaToInt);
			int ch2 = base64toInt(s.charAt(inCursor++), alphaToInt);
			int ch3 = base64toInt(s.charAt(inCursor++), alphaToInt);
			result[(outCursor++)] = (byte) (ch0 << 2 | ch1 >> 4);
			result[(outCursor++)] = (byte) (ch1 << 4 | ch2 >> 2);
			result[(outCursor++)] = (byte) (ch2 << 6 | ch3);
		}

		if (missingBytesInLastGroup != 0) {
			int ch0 = base64toInt(s.charAt(inCursor++), alphaToInt);
			int ch1 = base64toInt(s.charAt(inCursor++), alphaToInt);
			result[(outCursor++)] = (byte) (ch0 << 2 | ch1 >> 4);

			if (missingBytesInLastGroup == 1) {
				int ch2 = base64toInt(s.charAt(inCursor++), alphaToInt);
				result[(outCursor++)] = (byte) (ch1 << 4 | ch2 >> 2);
			}

		}

		return result;
	}

	private static int base64toInt(char c, byte[] alphaToInt) {
		int result = alphaToInt[c];
		if (result < 0) throw new IllegalArgumentException("Illegal character " + c);
		return result;
	}

//	public static void main(String[] args) {
//		int numRuns = Integer.parseInt(args[0]);
//		int numBytes = Integer.parseInt(args[1]);
//		Random rnd = new Random();
//		for (int i = 0; i < numRuns; i++)
//			for (int j = 0; j < numBytes; j++) {
//				byte[] arr = new byte[j];
//				for (int k = 0; k < j; k++) {
//					arr[k] = (byte) rnd.nextInt();
//				}
//				String s = byteArrayToBase64(arr);
//				byte[] b = base64ToByteArray(s);
//				if (!Arrays.equals(arr, b)) {
//					System.out.println("Dismal failure!");
//				}
//				s = byteArrayToAltBase64(arr);
//				b = altBase64ToByteArray(s);
//				if (!Arrays.equals(arr, b)) System.out.println("Alternate dismal failure!");
//			}
//	}
	
	public static void main(String[] args) {
		String s = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4NCgk8cmVxdWVzdCB1c2VybmFtZT0iaW5uZXJudXNlciIgcGFzc3dvcmQ9ImlubmVybnVzZXJwYXNzIj4NCgkJPHR5cGUgdmFsdWU9IkFDVElWRSIvPg0KCQk8ZGF0YQ0KCQkJdmFsdWU9IiZsdDs/eG1sIHZlcnNpb249JnF1b3Q7MS4wJnF1b3Q7IGVuY29kaW5nPSZxdW90O1VURi04JnF1b3Q7PyZndDsmI3hEOyYjeEE7JiN4OTsmbHQ7SU5GTyYjeEQ7JiN4QTsmI3g5OyYjeDk7dXNlck5hbWU9JnF1b3Q7emhhbmd5dWNoZW4mcXVvdDsmI3hEOyYjeEE7JiN4OTsmI3g5O3VzZXJVbml0PSZxdW90O2ppdXFpJnF1b3Q7JiN4RDsmI3hBOyYjeDk7JiN4OTthY3RpdmVXYXk9JnF1b3Q7RklMRSZxdW90OyYjeEQ7JiN4QTsmI3g5OyYjeDk7Y3B1U2VyaWFsPSZxdW90O0JGRUJGQkZGMDAwMjA2QTcmcXVvdDsmI3hEOyYjeEE7JiN4OTsmI3g5O21hY3M9JnF1b3Q7NDQtMzctRTYtNUYtN0YtQzYsMDAtNTAtNTYtQzAtMDAtMDEsMDAtNTAtNTYtQzAtMDAtMDgmcXVvdDsmI3hEOyYjeEE7JiN4OTsvJmd0OyINCgkJLz4NCgk8L3JlcXVlc3Q+";
		byte[] bytes = base64ToByteArray(s);
		System.out.println(new String(bytes));
	}
}
