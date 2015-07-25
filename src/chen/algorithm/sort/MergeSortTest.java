package chen.algorithm.sort;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class MergeSortTest {

	@Test
	public void testMerge() {
		MergeArr[] arrs = new MergeArr[] { 
				new MergeArr(new int[] { 1, 3, 5, 2, 4, 6 }, 0, 2, 5, new int[] { 1, 2, 3, 4, 5, 6 }),
				new MergeArr(new int[] { 1, 3, 5, 2, 4, 6 }, 0, 0, 5, new int[] { 1, 3, 5, 2, 4, 6 }),
				new MergeArr(new int[] { 1, 3, 5, 2, 4, 6 }, 0, 4, 5, new int[] { 1, 3, 5, 2, 4, 6 }),
				new MergeArr(new int[] { 1, 3, 5, 1, 4, 6 }, 0, 2, 5, new int[] { 1, 1, 3, 4, 5, 6 }),
				new MergeArr(new int[] { 1, 3, 5, 1, 4, 6 }, 2, 2, 3, new int[] { 1, 3, 1, 5, 4, 6 }),
		};
		for (MergeArr arr : arrs) {
			int[] copy = Arrays.copyOf(arr.origin, arr.origin.length);
			MergeSort.merge(copy, arr.start, arr.mid, arr.end);
			Assert.assertArrayEquals(
					"归并排序：" + Arrays.toString(arr.origin) + "merge后为" + Arrays.toString(copy) + "，应为" + Arrays.toString(arr.result), 
					copy, arr.result);
		}
	}
	
	@Test
	public void testSort() {
		MergeArr[] arrs = new MergeArr[] { 
				new MergeArr(new int[] { 1, 3, 5, 2, 4, 6 }, 0, 0, 5, new int[] { 1, 2, 3, 4, 5, 6 }),
				new MergeArr(new int[] { 1, 3, 5, 2, 4, 6 }, 2, 0, 4, new int[] { 1, 3, 2, 4, 5, 6 }),
				new MergeArr(new int[] { 1, 3, 5, 2, 4 }, 0, 0, 4, new int[] { 1, 2, 3, 4, 5}),
		};
		for (MergeArr arr : arrs) {
			int[] copy = Arrays.copyOf(arr.origin, arr.origin.length);
			MergeSort.sort(copy, arr.start, arr.end);
			Assert.assertArrayEquals(
					"归并排序：" + Arrays.toString(arr.origin) + "排序后为" + Arrays.toString(copy) + "，应为" + Arrays.toString(arr.result), 
					copy, arr.result);
		}
	}
	
	private class MergeArr {
		int[]	origin;
		int		start, mid, end;
		int[]	result;

		public MergeArr(int[] origin, int start, int mid, int end, int[] result) {
			this.origin = origin;
			this.start = start;
			this.mid = mid;
			this.end = end;
			this.result = result;
		}
	}
}
