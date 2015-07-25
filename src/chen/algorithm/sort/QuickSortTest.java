package chen.algorithm.sort;

import org.junit.Assert;
import org.junit.Test;

public class QuickSortTest {

	@Test
	public void testSort() {
		T[] tests = new T[] { 
				new T(new int[] { 1, 2, 3, 4, 5 }, new int[] { 1, 2, 3, 4, 5 }), 
				new T(new int[] { 5, 4, 3, 2, 1 }, new int[] { 1, 2, 3, 4, 5 }), 
				new T(new int[] { 2, 2, 1, 6, 5 }, new int[] { 1, 2, 2, 5, 6 }), 
				new T(new int[] { 1 }, new int[] { 1 }), 
				new T(new int[] {}, new int[] {}), 
		};
		
		for (T t : tests) {
			QuickSort.sort(t.A);
			Assert.assertArrayEquals(t.A, t.expect);
		}
		
		// 随机化的快速排序
		for (T t : tests) {
			QuickSort.randomizedSort(t.A);
			Assert.assertArrayEquals(t.A, t.expect);
		}
	}

	class T {
		int[]	A;
		int[]	expect;

		public T(int[] A, int[] expect) {
			this.A = A;
			this.expect = expect;
		}
	}
}
