package chen.algorithm.sort;

/**
 * 插入排序 from 《算法导论》
 * 
 * @author zhangyuchen
 * 
 */
public class InsertionSort {

	/**
	 * 插入排序，排序范围为[start, end) <br/>
	 * 不断获取后面未排序的元素，插入到前面已排序的序列中
	 * 
	 * @param A
	 * @param start
	 * @param end
	 */
	public static void sort(int[] A, int start, int end) {
		if (A == null || A.length <= 0 || start < 0 || start >= A.length || end < 0 || end > A.length || start > end) {
			throw new IllegalArgumentException("数组不能为空，且开始位置和结束位置在有效范围内，且开始位置不能大于结束位置");
		}
		for (int j = start + 1; j < end; j++) {
			int key = A[j];
			// insert A[j] into the sorted sequence A[1..j-1]
			int i = j - 1;
			while (i >= start && A[i] > key) {
				A[i + 1] = A[i]; // 后移
				i--;
			}
			A[i + 1] = key;
		}
	}

}
