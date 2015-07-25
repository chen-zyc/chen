package chen.util;

import org.junit.Assert;
import org.junit.Test;

public class RandTest {

	@Test
	public void testRangeInt() {
		class R {
			int	start;
			int	end;

			public R(int start, int end) {
				this.start = start;
				this.end = end;
			}
		}

		R[] tests = new R[] { new R(0, 1), new R(2, 5), new R(100, 1000), new R(0, 0), new R(5, 5), };

		// test range()
		for (R r : tests) {
			int actual = Rand.range(r.start, r.end);
			if (r.start == r.end) {
				Assert.assertTrue("range[" + r.start + "," + r.end + ") actual is " + actual, actual == r.start);
			} else {
				Assert.assertFalse("range[" + r.start + "," + r.end + ") actual is " + actual, actual < r.start || actual >= r.end);
			}
		}

		// test range2()
		for (R r : tests) {
			int actual = Rand.range(r.start, r.end);
			if (r.start == r.end) {
				Assert.assertTrue("range[" + r.start + "," + r.end + ") actual is " + actual, actual == r.start);
			} else {
				Assert.assertFalse("range[" + r.start + "," + r.end + ") actual is " + actual, actual < r.start || actual > r.end);
			}
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			System.out.print(Rand.range2(0, 5) + ", ");
		}
		System.out.println();
		for (int i = 0; i < 100; i++) {
			System.out.print(Rand.range(0, 5) + ", ");
		}
	}
}
