package chen.util;


public class Rand {

	/**
	 * 生成[start, end)范围内的随机数
	 * @param start
	 * @param end
	 * @return
	 */
	public static int range(int start, int end) {
		return (int) (Math.random() * (end-start) + start);
	}
	
	/**
	 * 生成[start, end]范围内的随机数
	 * @param start
	 * @param end
	 * @return
	 */
	public static int range2(int start, int end) {
		return range(start, end + 1);
	}
	
}
