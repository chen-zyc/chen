package chen.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.Collection;

import chen.judge.Judge;

public class FileUtil {

	/* ======================= 创建相关 ========================== */

	/**
	 * 创建文件，若文件的先辈目录不存在，则会创建这些目录<br>
	 * createFile("D://temp/dir1/dir11/f1.txt") <br>
	 * createFile("D://temp/dir1/dir12") (创建没有后缀的文件dir12) <br>
	 * 
	 * @param filePath
	 * @return 创建成功或已存在返回File，否则返回null
	 */
	public static File createFile(String filePath) {
		if (Judge.empty(filePath)) {
			return null;
		}
		File f = new File(filePath);
		// 已存在
		if (f.exists()) {
			return f;
		}
		// 创建父目录
		File parent = f.getParentFile();
		if (parent != null && !parent.exists()) {
			if (!parent.mkdirs()) {
				return null;
			}
		}
		// 创建文件
		try {
			if (f.createNewFile()) {
				return f;
			}
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 创建目录 <br>
	 * createDir("D://temp/dir1/dir13") => true <br>
	 * createDir("D://temp/dir1/dir13/f1.txt") => true (文件夹名为f1.txt)
	 * 
	 * @param dirPath
	 * @return 返回创建成功的File或null
	 */
	public static File createDir(String dirPath) {
		if (Judge.empty(dirPath)) {
			return null;
		}
		File dir = new File(dirPath);
		if (dir.exists()) {
			return dir;
		}
		if (dir.mkdirs()) {
			return dir;
		}
		return null;
	}

	/**
	 * 创建临时文件 <br>
	 * createTmpFile("tmp", ".txt", "D:/temp", true) =>
	 * D:\temp\tmp3787707981324435425.txt <br>
	 * createTmpFile("tmp", ".txt", null, true) =>
	 * C:\Users\ZHANGY~1.JIU\AppData\Local\Temp\tmp8275714315687224997.txt <br>
	 * 
	 * @param prefix
	 * @param suffix
	 * @param dirName
	 *            为null或空时使用默认路径
	 * @param deleteOnExit
	 *            退出时是否删除
	 * @return
	 */
	public static File createTmpFile(String prefix, String suffix, String dirName, boolean deleteOnExit) {
		File tmp = null;
		// 没有指定目录，在默认目录下创建
		if (Judge.empty(dirName)) {
			try {
				tmp = File.createTempFile(prefix, suffix);
			} catch (IOException e) {
				return null;
			}
		}
		// 指定了目录
		else {
			File dir = new File(dirName);
			// 不存在则创建
			if (!dir.exists()) {
				if (null == createDir(dirName)) {
					return null;
				}
			}
			try {
				tmp = File.createTempFile(prefix, suffix, dir);
			} catch (IOException e) {
				return null;
			}
		}
		if (tmp != null && deleteOnExit) {
			tmp.deleteOnExit();
		}
		return tmp;
	}

	/* ======================= 复制相关 ========================== */

	/**
	 * 复制文件夹及文件夹的所有后代文件或文件夹
	 * 
	 * @param oldPath
	 * @param newPath
	 */
	public static void copyFolder(String oldPath, String newPath) {
		// 1. 源目录
		File src = new File(oldPath);
		// 1.1 不存在或不是目录则不复制
		if (!src.exists() || !src.isDirectory()) {
			return;
		}
		// 2. 目的目录
		File dest = new File(newPath);
		if (!dest.exists()) {
			dest.mkdirs();
		}
		// 3. 遍历源目录下的所有子文件或子目录
		File[] children = src.listFiles();
		for (File child : children) {
			String newFilePath = dest + File.separator + child.getName();
			// 3.1 复制文件
			if (child.isFile()) {
				copyFile(child, new File(newFilePath));
			}
			// 3.2 复制文件夹
			else if (child.isDirectory()) {
				copyFolder(child.getAbsolutePath(), newFilePath);
			}
		}
	}

	/**
	 * 将文件src复制到dest
	 * 
	 * @param src
	 *            源文件
	 * @param dest
	 *            目的文件
	 */
	public static boolean copyFile(File src, File dest) {
		if (src == null || !src.exists() || !src.isFile() || dest == null) {
			return false;
		}
		try {
			FileInputStream in = new FileInputStream(src);
			FileOutputStream out = new FileOutputStream(dest);
			return copy(in, out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean copyFileChannel(File src, File dest) {
		if (src == null || !src.exists() || !src.isFile() || dest == null) {
			return false;
		}

		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			in = new FileInputStream(src);
			out = new FileOutputStream(dest);
			FileChannel inChan = in.getChannel();
			FileChannel outChan = out.getChannel();
//			long inSize = inChan.size();
//			long copySize = inChan.transferTo(0, inSize, outChan);
//			if (inSize != copySize) {
//				System.out.println("源文件长度:" + inSize + ",实际复制长度:" + copySize);
//			}
			long length = 0;
			while (true) {
				if (inChan.position() == inChan.size()) {
					break;
				}
				if (inChan.size() - inChan.position() < 2097152) {
					length = inChan.size() - inChan.position();
				} else {
					length = 2097152;
				}
				inChan.transferTo(inChan.position(), length, outChan);
				inChan.position(inChan.position() + length);
			}
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	public static boolean copyFileChannelOnce(File src, File dest) {
		if (src == null || !src.exists() || !src.isFile() || dest == null) {
			return false;
		}

		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			in = new FileInputStream(src);
			out = new FileOutputStream(dest);
			FileChannel inChan = in.getChannel();
			FileChannel outChan = out.getChannel();
			long inSize = inChan.size();
			long copySize = inChan.transferTo(0, inSize, outChan);
			if (inSize != copySize) {
				System.out.println("源文件长度:" + inSize + ",实际复制长度:" + copySize);
			}

			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * 复制流，复制结束后会关闭流
	 * 
	 * @param in
	 * @param out
	 * @return
	 */
	public static boolean copy(InputStream in, OutputStream out) {
		byte[] bytes = new byte[2097152];
		int len = -1;
		boolean suc = false;
		try {
			while ((len = in.read(bytes)) != -1) {
				out.write(bytes, 0, len);
			}
			suc = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return suc;
	}

	/* ======================= 删除相关 ========================== */

	/**
	 * 删除文件夹里面的所有文件
	 * 
	 * @param path
	 *            文件夹路径 如 c:/fqf
	 */
	public static void delAllFiles(File path) {
		if (path == null || !path.exists() || !path.isDirectory()) {
			return;
		}
		File[] children = path.listFiles();
		for (File child : children) {
			if (child.isFile()) {
				child.delete();
			} else if (child.isDirectory()) {
				delAllFiles(child); // 先删除文件夹里面的文件
				child.delete(); // 再删除空文件夹
			}
		}
	}

	/* ======================= 读取相关 ========================== */

	/**
	 * 读取文件内容到一个字符串中
	 * 
	 * @param file
	 * @return
	 */
	public static String readFile(File file) {
		if (file == null || !file.exists() || !file.isFile()) {
			return null;
		}
		final StringBuilder str = new StringBuilder();
		readFile(file, new ReadFileCallBack() {

			@Override
			public boolean call(String line) {
				str.append(line + "\n");
				return true;
			}
		});
		return str.toString();
	}

	/**
	 * 读取文件内容，每读取一行都调用call.call()进行处理
	 * 
	 * @param file
	 * @param call
	 */
	public static void readFile(File file, ReadFileCallBack call) {
		if (file == null || !file.exists() || !file.isFile() || call == null) {
			return;
		}
		try {
			InputStream in = new FileInputStream(file);
			readFile(in, call);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取文件内容
	 * 
	 * @param in
	 * @param call
	 */
	public static void readFile(InputStream in, ReadFileCallBack call) {
		if (in == null) {
			return;
		}
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(in));
			String tmp = null;
			while ((tmp = reader.readLine()) != null) {
				if (!call.call(tmp)) {
					return;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 从文件中获得字节数组
	 * 
	 * @param file
	 * @return 字节数组，若文件不存在或抛出异常则返回new byte[0]
	 */
	public static byte[] getBytesFromFile(File file) {
		if (file == null || !file.exists()) {
			return new byte[0];
		}
		// 1. 输入流
		FileInputStream in;
		try {
			in = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			return new byte[0];
		}
		// 2. 输出流
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		// 3. 输入流 => 输出流
		byte[] bytes = new byte[1024];
		int len = -1;
		try {
			while ((len = in.read(bytes)) != -1) {
				out.write(bytes, 0, len);
			}
			out.flush();
			out.close();
			return out.toByteArray();
		} catch (IOException e) {
			return new byte[0];
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取磁盘使用率<br>
	 * 若f为磁盘根目录和f为磁盘根目录下的后代目录，这两种情况下获得的大小相同
	 * 
	 * @param f
	 * @return 30.0 或 0.0
	 */
	public static Double getDiskRatio(File f) {
		if (f != null && f.exists()) {
			long total = f.getTotalSpace();
			long free = f.getFreeSpace();
			double ratio = (1 - free * 1.0 / total) * 100;
			return ratio;
		}
		return 0.0;
	}

	/**
	 * 【重载】获取磁盘使用率
	 * 
	 * @param f
	 * @return 30.0 或 0.0
	 */
	public static Double getDiskRatio(String path) {
		if (Judge.empty(path)) {
			return 0.0;
		}
		return getDiskRatio(new File(path));
	}

	/**
	 * 获取文件大小，若文件是一个目录，则目录下的文件大小也计算在内
	 * 
	 * @param f
	 * @return 字节数
	 */
	public static Long getFileSize(File f) {
		if (f != null && f.exists()) {
			// 文件直接返回大小
			if (f.isFile()) {
				return f.length();
			}
			// 目录则需要递归遍历
			File[] children = f.listFiles();
			long size = 0L;
			for (File child : children) {
				size += getFileSize(child);
			}
			return size;
		}

		return 0L;
	}

	/**
	 * 获取文件根目录 <br>
	 * getRoot(new File("D://DA包")) => D:
	 * 
	 * @param f
	 * @return null 或 File
	 */
	public static File getRoot(File f) {
		if (f == null || !f.exists()) {
			return null;
		}
		// File.getParentFile()是使用f的文件名根据分隔符来截取的，可查看源码
		// 这里也仿照getParentFile()来做
		String path = f.getAbsolutePath();
		String root = path.substring(0, path.indexOf(File.separatorChar));
		if (!Judge.empty(root)) {
			return new File(root);
		}
		return null;
	}

	/* ======================= 写入相关 ========================== */

	/**
	 * 将text写入到文件
	 * 
	 * @param filePath
	 * @param text
	 * @param append
	 * @return
	 */
	public static boolean write(String filePath, String text, boolean append) {
		File f = createFile(filePath);
		return write(f, text, append);
	}

	/**
	 * 将text写入到文件
	 * 
	 * @param f
	 * @param text
	 * @param append
	 * @return
	 */
	public static boolean write(File f, String text, boolean append) {
		if (f == null) {
			return false;
		}
		if (!f.exists()) {
			if (null == createFile(f.getAbsolutePath())) {
				return false;
			}
		}
		FileWriter writer = null;
		try {
			writer = new FileWriter(f, append);
			BufferedWriter bw = new BufferedWriter(writer);
			bw.write(text);
			bw.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * 将集合中的对象写入到文件
	 * 
	 * @param f
	 * @param col
	 * @param append
	 * @return
	 */
	public static <T> boolean write(File f, Collection<T> col, boolean append) {
		if (f == null) {
			return false;
		}
		if (!f.exists()) {
			if (null == createFile(f.getAbsolutePath())) {
				return false;
			}
		}
		FileWriter writer = null;
		try {
			writer = new FileWriter(f, append);
			BufferedWriter bw = new BufferedWriter(writer);
			for (T t : col) {
				bw.write(t.toString() + "\r\n");
			}
			bw.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/* ======================= 命名相关 ========================== */

	/**
	 * 重命名文件
	 * 
	 * @param src
	 *            源文件,如D://chenLog.txt
	 * @param newName
	 *            新名称如chenLog2.txt，该名称不应该包含路径分隔符，否则将会移动文件
	 * @return 若重命名成功则返回新文件(src并未修改)，否则返回null
	 */
	public static File rename(File src, String newName) {
		if (src == null || !src.exists()) {
			return null;
		}
		File parent = src.getParentFile();
		File dest = new File((parent == null ? "" : parent.getAbsolutePath()) + File.separator + newName);
		if (src.renameTo(dest)) {
			return dest;
		}
		return null;
	}

	/* ======================= 判断相关 ========================== */

	/**
	 * 两个文件是否有相同的跟目录<br>
	 * 注意，若两个文件都不存在，也会返回true
	 * 
	 * @param f1
	 * @param f2
	 * @return
	 */
	public static boolean sameRoot(File f1, File f2) {
		if (Judge.hasNull(f1, f2)) {
			return false;
		}
		return Judge.equals(FileUtil.getRoot(f1), FileUtil.getRoot(f2));
	}

	/* ======================= 内部类-回调相关 ========================== */

	/**
	 * 读取文件回调
	 * 
	 * @author zhangyuchen
	 * 
	 */
	public interface ReadFileCallBack {
		/**
		 * 读取文件的每一行都会回调该方法
		 * 
		 * @param line
		 *            读取的一行文本
		 * @return 是否继续读取文件的下一行
		 */
		boolean call(String line);
	}
}
