package chen.bytes;

import chen.bytes.ByteUtil.ByteUnit;
import chen.bytes.ByteUtil.Byter;

public class ByteUtilTest {

	public static void main(String[] args) {
//		testConvert();
//		testConvertSuitably();
//		testValueOf();
		testBytes();
	}

	public static void testConvert() {
		ByteUnit src = ByteUnit.KB;
		ByteUnit dest = ByteUnit.MB;
		long n = 5098L;
		
		long n2 = ByteUtil.convert(n, src, dest);
		System.out.println(n + src.name() + " => " + n2 + dest.name());
	}
	
	public static void testConvertSuitably() {
		long n = 4L << 40;
		ByteUnit cur = ByteUnit.KB;
		System.out.println(n + cur.name() + " => " + ByteUtil.convertSuitably(n, cur));
	}
	
	public static void testValueOf(){
		String[] testStr = new String[] { "2Tb", "1GB", "44mb", "23 KB", "32B", "45", "23 ab", "KB" };
		for (String s : testStr) {
			Byter byter = ByteUtil.valueOf(s);
			System.out.println(s + " => " + byter);
		}
	}
	
	public static void testBytes() {
		Byter byter = new Byter(3L, ByteUnit.GB);
		Long bytes = ByteUtil.bytes(byter);
		System.out.println(bytes);
		
		String s = ByteUtil.convertSuitably(bytes);
		System.out.println(s);
	}
}
