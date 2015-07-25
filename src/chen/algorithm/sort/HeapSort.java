package chen.algorithm.sort;

public class HeapSort {

	/**
	 * 使用堆排序算法排序A
	 * @param A
	 */
	public static void sort(int[] A) {
		// 使用Arr包装A，仅仅为了给A多加几个属性
		Arr arr = new Arr();
		arr.A = A;
		
		buildMaxHeap(arr);
		
		// 拿最后一个和第一个交换
		for (int i = A.length - 1; i > 0; i--) {
			exchange(A, 0, i); // 现在A[i]是最大的，即最大的放在最后
			arr.heapSize --; // 最后一个是最大的，不再计算它
			maxHeapify(arr, 0);
		}
	}
	
	/**
	 * 建最大堆
	 * @param A
	 */
	private static void buildMaxHeap(Arr arr) {
		arr.heapSize = arr.A.length;
		
		// 从第一个非叶子节点开始到最上面的根节点
		for (int i = (arr.heapSize - 1) / 2; i >= 0; i--) {
			maxHeapify(arr, i);
		}
	}
	
	/**
	 * 交换A中第i和第j的元素
	 * @param A
	 * @param i
	 * @param j
	 */
	private static void exchange(int[] A, int i, int j) {
		int temp = A[i];
		A[i] = A[j];
		A[j] = temp;
	}
	
	/**
	 * 维持A中以i为根节点的数，保证该树为最大堆
	 * @param A
	 * @param i
	 */
	private static void maxHeapify(Arr arr, int i) {
		int l = left(i);
		int r = right(i);
		// 找到i和其左右子节点中的最大值
		int largest = i;
		if (l < arr.heapSize && arr.A[l] > arr.A[largest]) {
			largest = l;
		}
		if (r < arr.heapSize && arr.A[r] > arr.A[largest]) {
			largest = r;
		}
		if (largest != i) {
			// 让最大的元素在根节点
			exchange(arr.A, i, largest);
			// 被交换的那个节点所在的树有可能不再保持最大堆的特性
			maxHeapify(arr, largest);
		}
	}
	
	/**
	 * i的左子节点的序号
	 * @param i
	 * @return
	 */
	private static int left(int i) {
		return 2 * i + 1;
	}
	
	/**
	 * i的右子节点的序号
	 * @param i
	 * @return
	 */
	private static int right(int i) {
		return 2 * i + 2;
	}
	
	private static class Arr {
		int[] A;
		int heapSize;
	}
}
