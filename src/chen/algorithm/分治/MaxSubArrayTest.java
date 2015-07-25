package chen.algorithm.分治;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import chen.algorithm.分治.MaxSubArray.RangeValue;

public class MaxSubArrayTest {

	@Test
	public void testFindMaxCrossingSubArray() {
		Arg1[] args = new Arg1[]{
				new Arg1(new int[] { 1, -3, 7, 8, -2 }, 0, 2, 4, 2, 3, 15),
				new Arg1(new int[] { 1, 2, 3 }, 0, 1, 2, 0, 2, 6),
				new Arg1(new int[] { 1, 2 }, 0, 0, 1, 0, 1, 3)
		};
		
		for (Arg1 arg : args) {
			RangeValue rv = MaxSubArray.FindMaxCrossingSubArray(arg.A, arg.low, arg.mid, arg.high);
			Assert.assertEquals(arg + "\n\t实际最大左边界为" + rv.left, rv.left, arg.maxLeft);
			Assert.assertEquals(arg + "\n\t实际最大右边界为" + rv.right, rv.right, arg.maxRight);
			Assert.assertEquals(arg + "\n\t实际最大和为" + rv.sum, rv.sum, arg.sum);
		}
	}
	
	@Test
	public void testFindSubArray() {
		Arg1[] args = new Arg1[]{
				new Arg1(new int[] { 1, -3, 7, 8, -2 }, 0, 0, 4, 2, 3, 15),
				new Arg1(new int[] { 1, -2, 3 }, 0, 0, 2, 2, 2, 3),
				new Arg1(new int[] { 1, -2 }, 0, 0, 1, 0, 0, 1),
				new Arg1(new int[] { 1 }, 0, 0, 0, 0, 0, 1),
				new Arg1(new int[] { 13, -3, -25, 20, -3, -16, -23, 18, 20, -7, 12, -5, -22, 15, -4, 7 }, 0, 0, 15, 7, 10, 43),
		};
		
		for (Arg1 arg : args) {
			RangeValue rv = MaxSubArray.FindSubArray(arg.A, arg.low, arg.high);
			Assert.assertEquals(arg + "\n\t实际最大左边界为" + rv.left, rv.left, arg.maxLeft);
			Assert.assertEquals(arg + "\n\t实际最大右边界为" + rv.right, rv.right, arg.maxRight);
			Assert.assertEquals(arg + "\n\t实际最大和为" + rv.sum, rv.sum, arg.sum);
		}
	}
	
	
	private class Arg1 {
		int[]	A;
		int		low;
		int		mid;
		int		high;
		// 返回结果
		int		maxLeft;
		int		maxRight;
		int		sum;

		public Arg1(int[] A, int low, int mid, int high, int maxLeft, int maxRight, int sum) {
			this.A = A;
			this.low = low;
			this.mid = mid;
			this.high = high;
			this.maxLeft = maxLeft;
			this.maxRight = maxRight;
			this.sum = sum;
		}

		@Override
		public String toString() {
			return "Arg1 [A=" + Arrays.toString(A) + ", low=" + low + ", mid=" + mid + ", high=" + high + ", maxLeft=" + maxLeft + ", maxRight=" + maxRight + ", sum=" + sum + "]";
		}
		
	}
}
