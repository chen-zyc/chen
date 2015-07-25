package chen.algorithm.sort;


/**
 * 计数排序
 * @author zhangyuchen
 *
 */
public class CountingSort {

	/**
	 * 排序
	 * @param in 元素应>=0 && <=max
	 * @param max in中的最大值
	 * @return
	 */
	public static int[] sort(int[] in, int max) {
		int[] out = new int[in.length]; // 输出数组

		int[] count = new int[max + 1]; // 对in中每个元素的出现次数进行计数
		for (int i = 0; i < in.length; i++) {
			count[in[i]]++;
		}
		// 现在，count[i]表示i在in中出现的次数

		for (int i = 1; i <= max; i++) {
			count[i] += count[i - 1];
		}
		// now，count[i]表示比i小或相等的元素出现的次数，也就是应该在out中的位置

		for (int i = in.length - 1; i >= 0; i--) {
			out[count[in[i]] - 1] = in[i]; // 把in[i]放到正确的位置
			count[in[i]]--; // 这样下次出现相同的元素会放到前面的位置
		}

		return out;
	}
}
