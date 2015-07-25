package chen.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Arrays {

	/**
	 * 每个ele之间使用seg连接，如join(",", "a", "b") == "a,b"
	 * @param seg
	 * @param ele
	 * @return
	 */
	public static String join(String seg, String... ele) {
		StringBuilder ret = new StringBuilder("");
		if (ele == null || ele.length == 0) {
			return ret.toString();
		}
		if (ele.length == 1) {
			ret.append(ele[0]);
			return ret.toString();
		}

		for (int i = 0; i < ele.length - 1; i++) {
			ret.append(ele[i] + seg);
		}
		ret.append(ele[ele.length - 1]);

		return ret.toString();
	}
	
	/**
	 * 去重
	 * @param list
	 */
	public static <T> void distinct(List<T> list) {
		if (list == null || list.size() < 2) {
			return;
		}

		HashSet<T> set = new HashSet<T>();
		for (Iterator<T> it = list.iterator(); it.hasNext();) {
			T t = it.next();
			if (set.contains(t)) {
				it.remove();
			} else {
				set.add(t);
			}
		}
	}
	
	/**
	 * 去重
	 * @param arr
	 * @param emptyArr
	 * @return
	 */
	public static <T> T[] distinct(T[] arr, T[] emptyArr) {
		if (arr == null || arr.length < 2) {
			return arr;
		}

		List<T> list = new ArrayList<T>();
		for (T t : arr) {
			list.add(t);
		}

		distinct(list);
		return list.toArray(emptyArr);
	}
	
}
