package chen.algorithm.sort;

public class MergeSort {
	
	/**
	 * 归并排序，排序范围[start, end]
	 * @param A
	 * @param start
	 * @param end
	 */
	public static void sort(int[] A, int start, int end) {
		if (start < end) {
			int mid = (start + end) / 2;
			sort(A, start, mid);
			sort(A, mid + 1, end);
			merge(A, start, mid, end);
		}
	}

	/**
	 * 将A分为两个数组[sta, mid],[mid+1, end]，合并这两个数组到A中，每个数组应该保证是非递减的，最后的A也是非递减的
	 * @param A
	 * @param sta
	 * @param mid
	 * @param end
	 */
	public static void merge(int[] A, int sta, int mid, int end) {
		int ll = mid - sta + 1; // 左半部分长度，包括mid
		int rl = end - mid; // 右半部分长度
		int[] L = new int[ll + 1]; // 左数组，最后一位为哨兵
		int[] R = new int[rl + 1]; // 右数组，最后一位为哨兵
		// 填充
		for (int i = 0; i < ll; i++) {
			L[i] = A[sta + i];
		}
		for (int i = 0; i < rl; i++) {
			R[i] = A[mid + i + 1];
		}
		// 哨兵
		L[ll] = Integer.MAX_VALUE;
		R[rl] = Integer.MAX_VALUE;
		// L和R的索引
		int i = 0, j = 0;
		for (int k = sta; k <= end; k++) {
			if (L[i] <= R[j]) {
				A[k] = L[i];
				i++;
			} else {
				A[k] = R[j];
				j++;
			}
		}
	}
}
