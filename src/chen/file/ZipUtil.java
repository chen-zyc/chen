package chen.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import chen.judge.Judge;

/**
 * 压缩与解压缩工具类 <br/>
 * 中文文件名会乱码
 * @author zhangyuchen
 *
 */
public class ZipUtil {

	/*============== 压缩 ========================*/
	
	/**
	 * 将src代表的文件或目录压缩到dest文件
	 * @param src
	 * @param dest 以zip为后缀的文件
	 * @throws Exception
	 */
	public static void zip(File src, File dest) throws Exception {
		if (src == null || !src.exists()) {
			throw new IllegalArgumentException("要压缩的源路径需存在");
		}
		if (dest == null) {
			throw new IllegalArgumentException("要压缩的目的文件名不能为null");
		}
		
		dest = FileUtil.createFile(dest.getAbsolutePath());
		if (dest == null) {
			throw new RuntimeException("无法创建目的文件");
		}
		
		ZipOutputStream zos = null;
		boolean finish = false;
		try {
			zos = new ZipOutputStream(new CheckedOutputStream(new FileOutputStream(dest), new CRC32()));
			zip(src, zos, "");
			finish = true;
		} finally {
			if (zos != null) {
				zos.close();
			}
			// 压缩未完成，删除目的文件
			if (!finish && dest.exists()) {
				dest.delete();
			}
		}
	}
	
	private static void zip(File src, ZipOutputStream zos, String baseDir) throws IOException {
		if (src.isDirectory()) {
			zipDir(src, zos, baseDir);
		} else if (src.isFile()) {
			zipFile(src, zos, baseDir);
		}
	}
	
	private static void zipDir(File src, ZipOutputStream zos, String baseDir) throws IOException {
		File[] children = src.listFiles();
		if (children.length == 0) { // 空目录
			zos.putNextEntry(new ZipEntry(baseDir + src.getName() + File.separator));
			return;
		} else {
			for (File child : children) {
				zip(child, zos, baseDir + src.getName() + File.separator);
			}
		}
	}
	
	private static void zipFile(File src, ZipOutputStream zos, String baseDir) throws IOException {
		ZipEntry entry = new ZipEntry(baseDir + src.getName());
		entry.setSize(src.length()); // 未压缩前的大小
		zos.putNextEntry(entry);
		
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(src);
			byte[] bytes = new byte[1024];
			int len = -1;
			while ((len = fis.read(bytes)) != -1) {
				zos.write(bytes, 0, len);
			}
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
	}
	
	
	/*============= 解压缩 =======================*/
	
	/**
	 * 将zipFile解压到dir
	 * @param zipFile
	 * @param dir
	 * @param entryNames 需要解压的entry名称，若为null或空，则全部解压
	 * @throws Exception
	 */
	public static void unzip(File zipFile, File dir, List<String> entryNames) throws Exception {
		dir = FileUtil.createDir(dir.getAbsolutePath());
		if (dir == null) {
			throw new IllegalArgumentException("cannt create dir:" + dir);
		}
		
		ZipFile zip = new ZipFile(zipFile);
		try {
			Enumeration<? extends ZipEntry> entries = zip.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				// 当entryNames不为空时，若entry不在集合中，则跳过
				if (!Judge.empty(entryNames) && !entryNames.contains(entry.getName())) {
					continue;
				}
				
				if (entry.isDirectory()) {
					// 创建目录
					FileUtil.createDir(dir + File.separator + entry.getName());
				} else {
					File f = new File(dir + File.separator + entry.getName());
					FileUtil.createDir(f.getParent());
					FileUtil.copy(zip.getInputStream(entry), new FileOutputStream(f));
				}
			}
		} finally {
			zip.close();
		}
		
	}
	
}
