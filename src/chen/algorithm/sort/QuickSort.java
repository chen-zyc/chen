package chen.algorithm.sort;

import chen.util.Rand;


public class QuickSort {

	/**
	 * 快速排序，对于已经对于已经排好序的数组，效率为n^2
	 * @param A
	 */
	public static void sort(int[] A) {
		quickSort(A, 0, A.length - 1);
	}

	/**
	 * 快速排序的随机化版本
	 * @param A
	 */
	public static void randomizedSort(int[] A) {
		randomizedQuickSort(A, 0, A.length - 1);
	}
	
	/**
	 * 使用快速排序算法排序A[p..r]
	 * @param A
	 * @param p
	 * @param r
	 */
	private static void quickSort(int[] A, int p, int r) {
		if (p < r) {
			int q = partition(A, p, r);
			quickSort(A, p, q - 1);
			quickSort(A, q + 1, r);
		}
	}
	
	private static void randomizedQuickSort(int[] A, int p, int r) {
		if (p < r) {
			int q = randomizedPartition(A, p, r);
			randomizedQuickSort(A, p, q - 1);
			randomizedQuickSort(A, q + 1, r);
		}
	}

	/**
	 * 分类，将数组中大于A[r]的数据和小于A[r]的数据分开，返回最后A[r]的索引
	 * @param A
	 * @param p
	 * @param r
	 * @return
	 */
	private static int partition(int[] A, int p, int r) {
		int x = A[r]; // 主元，最中间的那个
		int i = p - 1; // 比x小的数中最接近x的
		for (int j = p; j < r; j++) {
			if (A[j] <= x) {
				i++;
				exchange(A, i, j);
			}
		}
		exchange(A, i + 1, r);
		return i + 1;
	}
	
	/**
	 * 先随机化，然后选出主元
	 * @param A
	 * @param p
	 * @param r
	 * @return
	 */
	private static int randomizedPartition(int[] A, int p, int r) {
		// [p, r]间的随机数
		int i = Rand.range2(p, r);
		// 使A[i]成为主元
		exchange(A, i, r);
		// 分类
		return partition(A, p, r);
	}
	
	private static void exchange(int[] A, int i, int j) {
		int temp = A[i];
		A[i] = A[j];
		A[j] = temp;
	}
	
}
