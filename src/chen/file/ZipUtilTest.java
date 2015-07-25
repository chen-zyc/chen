package chen.file;

import java.io.File;
import java.util.Arrays;

public class ZipUtilTest {

	public static void main(String[] args) {
		testZip();
	}
	
	public static void testZip() {
		class T {
			String src;
			String dest;
			String unzip;
			
			public T(String src, String dest, String unzip) {
				this.src = src;
				this.dest = dest;
				this.unzip = unzip;
			}
		}
		
		T[] tests = new T[]{
				new T("D://temp/ziptest/src", "D://temp/ziptest/dest/zip测试.zip", "D://temp/ziptest/src/unzip"),
				new T("D://temp/ziptest/ziptest.txt", "D://temp/ziptest/ziptest.zip", "D://temp/ziptest/txt")
		};
		
		try {
			for (T t : tests) {
				ZipUtil.zip(new File(t.src), new File(t.dest));
				ZipUtil.unzip(new File(t.dest), new File(t.unzip), Arrays.asList("src/dir/java重启.txt".replace('/', File.separatorChar)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
