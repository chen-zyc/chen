package chen.print;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import chen.file.FileUtil;

public class Print {

	/**
	 * 输出集合，每一元素占一行
	 * @param col
	 */
	public static <T> void print(Collection<T> col) {
		if (col == null) {
			System.err.println("collection is null");
			return;
		}
		if (col.size() == 0) {
			System.err.println("collection is empty");
			return;
		}
		for (T t : col) {
			System.out.println(t.toString());
		}
	}
	
	/**
	 * 将集合输出到文件中，每个元素占据一行
	 * @param f 文件路径，若不存在则会创建
	 * @param col
	 */
	public static <T> void print(String f, Collection<T> col, boolean append) {
		File file = FileUtil.createFile(f);
		FileUtil.write(file, col, append);
	}
	
	/**
	 * 打印map到控制台
	 * @param map
	 * @param keyLen key的长度 <=0 不指定长度
	 * @param valLen value的长度 <=0 不指定长度
	 */
	public static <K,V> void print(Map<K,V> map, int keyLen, int valLen) {
		if (map == null) {
			System.err.println("map is null");
			return;
		}
		if (map.size() == 0) {
			System.err.println("map is empyt");
			return;
		}
		String len1 = keyLen <= 0 ? "" : keyLen + "";
		String len2 = valLen <= 0 ? "" : valLen + "";
		for (Map.Entry<K, V> entry : map.entrySet()) {
			System.out.println(String.format("%1$" + len1 + "s : %2$" + len2 + "s", entry.getKey(), entry.getValue()));
		}
	}
	
	/**
	 * 【重载】value不指定长度
	 * @param map
	 * @param keyLen
	 */
	public static <K,V> void print(Map<K,V> map, int keyLen) {
		print(map, keyLen, 0);
	}
	
	/**
	 * 【重载】key和value都不指定长度
	 * @param map
	 */
	public static <K,V> void print(Map<K,V> map) {
		print(map, 0, 0);
	}
}
