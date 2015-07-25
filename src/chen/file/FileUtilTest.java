package chen.file;

import java.io.File;

import chen.bytes.ByteUtil;
import chen.bytes.ByteUtil.ByteUnit;
import chen.string.StringUtil;

public class FileUtilTest {

	public static void main(String[] args) {
//		testGetDiskRatio();
//		testGetFileSize();
//		testGetRoot();
//		testSameRoot();
//		testCreateFile();
//		testCreateDir();
//		testCreateTmpFile();
//		testCopyFolder();
		benchmarkCopyFile();
		benchmarkCopyFile2();
		benchmarkCopyFile3();
	}

	public static void testGetDiskRatio() {
		String[] testPath = new String[] { "A://", "B://", "C://", "D://", "E://", "F://", "D:/DA包" };
		for (String path : testPath) {
			Double ratio = FileUtil.getDiskRatio(path);
			System.out.println(path + "使用率：" + StringUtil.float_percent(ratio));
		}
	}
	
	public static void testGetFileSize() {
		Long size = FileUtil.getFileSize(new File("D:/DA包/M5-20140724"));
		System.out.println(size);
		System.out.println(ByteUtil.convertSuitably(size, ByteUnit.B));
	}
	
	public static void testGetRoot() {
		File root = FileUtil.getRoot(new File("FileUtil.java"));
		System.out.println(root);
	}
	
	public static void testSameRoot() {
		File f1 = new File("D:/DA包/M5-20140724");
		File f2 = new File("D:/DA包/M6-2014-08-21");
		File f3 = new File("D:/DA包2"); // 不存在
		File f4 = new File("D:/DA包3"); // 不存在
		System.out.println(FileUtil.sameRoot(f1, f2)); // true
		System.out.println(FileUtil.sameRoot(f1, null)); // false
		System.out.println(FileUtil.sameRoot(null, null)); // false
		System.out.println(FileUtil.sameRoot(f1, f3)); // false
		System.out.println(FileUtil.sameRoot(f3, f3)); // true
		System.out.println(FileUtil.sameRoot(f3, f4)); // true
	}
	
	public static void testCreateFile() {
//		System.out.println(FileUtil.createFile("D://temp/dir1/dir11/f1.txt")); // true
//		System.out.println(FileUtil.createFile("D://temp/dir1/dir11/f1.txt")); // false
		System.out.println(FileUtil.createFile("D://temp/dir1/dir12")); // true
		System.out.println(FileUtil.createFile("D://temp/dir1/dir13/")); // true
	}
	
	public static void testCreateDir() {
//		System.out.println(FileUtil.createDir("D://temp/dir1/dir13")); // true
		System.out.println(FileUtil.createDir("D://temp/dir1/dir13/f1.txt")); // true
	}

	public static void testCreateTmpFile() {
		File tmp = null;
		String prefix = "tmp";
		String suffix = ".txt";
		String dirName = "D:/temp";
		
		tmp = FileUtil.createTmpFile(prefix, suffix, dirName, true);
		System.out.println(tmp);
		
		tmp = FileUtil.createTmpFile(prefix, suffix, dirName, false);
		System.out.println(tmp);
		
//		tmp = FileUtil.createTmpFile(prefix, suffix, null, true);
//		tmp.deleteOnExit();
//		System.out.println(tmp);
	}
	
	public static void testCopyFolder() {
		String old = "D://temp/old";
		String neww = "D://temp/neww";
		FileUtil.copyFolder(old, neww);
	}
	
	private static int times = 5;
	private static String srcFile = "F:\\DNA\\bsd2.xls";
	public static void benchmarkCopyFile() {
		long start = System.currentTimeMillis();
		File src = new File(srcFile);
		for (int i = 0; i < times; i++) {
			File dest = new File("F:\\DNA\\stream" + i + ".xls");
			FileUtil.copyFile(src, dest);
		}
		long end = System.currentTimeMillis();
		System.out.println("benchmarkCopyFile():" + (end - start) + "ms");
	}

	public static void benchmarkCopyFile2() {
		long start = System.currentTimeMillis();
		File src = new File(srcFile);
		for (int i = 0; i < times; i++) {
			File dest = new File("F:\\DNA\\channel" + i + ".xls");
			FileUtil.copyFileChannel(src, dest);
		}
		long end = System.currentTimeMillis();
		System.out.println("benchmarkCopyFile2():" + (end - start) + "ms");
	}
	
	public static void benchmarkCopyFile3() {
		long start = System.currentTimeMillis();
		File src = new File(srcFile);
		for (int i = 0; i < times; i++) {
			File dest = new File("F:\\DNA\\channel" + i + ".xls");
			FileUtil.copyFileChannelOnce(src, dest);
		}
		long end = System.currentTimeMillis();
		System.out.println("benchmarkCopyFile3():" + (end - start) + "ms");
	}
}
