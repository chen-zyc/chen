package chen.algorithm.sort;

import org.junit.Assert;
import org.junit.Test;

public class HeapSortTest {

	@Test
	public void testSort() {
		class T {
			int[]	arr;
			int[]	result;

			T(int[] arr, int[] res) {
				this.arr = arr;
				this.result = res;
			}
		}

		T[] tests = new T[] { 
				new T(new int[] { 1, 2, 3, 4, 5, 6 }, new int[] { 1, 2, 3, 4, 5, 6 }), 
				new T(new int[] { 6, 5, 4, 3, 2, 1 }, new int[] { 1, 2, 3, 4, 5, 6 }), 
				new T(new int[] { 1, 3, 2, 6, 5, 4 }, new int[] { 1, 2, 3, 4, 5, 6 }), 
				new T(new int[] { 6, 6, 4, 3, 2, 2 }, new int[] { 2, 2, 3, 4, 6, 6 }), 
				new T(new int[] { 1 }, new int[] { 1 }), 
				new T(new int[] {}, new int[] {}), 
		};

		for (T t : tests) {
			HeapSort.sort(t.arr);
			Assert.assertArrayEquals(t.arr, t.result);
		}
	}
}
