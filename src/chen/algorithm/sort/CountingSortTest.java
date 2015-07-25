package chen.algorithm.sort;

import org.junit.Assert;
import org.junit.Test;

public class CountingSortTest {

	class T {
		int[]	in;
		int[]	expected;
		int		max;

		public T(int[] in, int[] expected, int max) {
			this.in = in;
			this.expected = expected;
			this.max = max;
		}
	}

	@Test
	public void test() {
		T[] tests = new T[] { 
				new T(new int[] { 1, 2, 3, 4, 5 }, new int[] { 1, 2, 3, 4, 5 }, 5), 
				new T(new int[] { 5, 4, 3, 2, 1 }, new int[] { 1, 2, 3, 4, 5 }, 5), 
				new T(new int[] { 1, 4, 3, 4, 5 }, new int[] { 1, 3, 4, 4, 5 }, 5), 
				new T(new int[] { 1 }, new int[] { 1 }, 1), 
				new T(new int[] { 1, 1, 1, 1, 1 }, new int[] { 1, 1, 1, 1, 1 }, 1), 
		};
		
		for (T t : tests) {
			int[] out = CountingSort.sort(t.in, t.max);
			Assert.assertArrayEquals(t.expected, out);
		}
	}

}
