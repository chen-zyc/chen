package chen.algorithm.分治;

/**
 * 《算法导论》 4.1 最大子数组问题
 * @author zhangyuchen
 *
 */
public class MaxSubArray {
	
	public static RangeValue FindSubArray(int[] A, int low, int high) {
		if (low == high) {
			return new RangeValue(low, high, A[low]);
		}
		int mid = (low + high) / 2;
		RangeValue leftRange = FindSubArray(A, low, mid);
		RangeValue rightRange = FindSubArray(A, mid + 1, high);
		RangeValue crossRange = FindMaxCrossingSubArray(A, low, mid, high);

		if (leftRange.sum >= rightRange.sum && leftRange.sum >= crossRange.sum) {
			return leftRange;
		}
		if (rightRange.sum >= leftRange.sum && rightRange.sum >= crossRange.sum) {
			return rightRange;
		}
		return crossRange;
	}

	/**
	 * 跨越中点的最大子数组的边界和值，左边范围[low, mid]，右边范围[mid+1, high]
	 * @param A
	 * @param low
	 * @param mid
	 * @param high
	 * @return
	 */
	public static RangeValue FindMaxCrossingSubArray(int[] A, int low, int mid, int high) {
		int maxLeft = mid; // 左边最大边界
		int maxRight = -1; // 右边最大边界

		int leftSum = Integer.MIN_VALUE; // 中点左边最大值
		int sum = 0; // 遍历过程中所有值的和
		for (int i = mid; i >= low; i--) {
			sum += A[i];
			if (sum > leftSum) {
				leftSum = sum;
				maxLeft = i;
			}
		}

		int rightSum = Integer.MIN_VALUE;
		sum = 0;
		for (int i = mid + 1; i <= high; i++) {
			sum += A[i];
			if (sum > rightSum) {
				rightSum = sum;
				maxRight = i;
			}
		}

		return new RangeValue(maxLeft, maxRight, leftSum + rightSum);
	}
	
	/**
	 * 最大子数组的左边界值、右边界值、元素值的和
	 * @author zhangyuchen
	 *
	 */
	public static class RangeValue {
		int	left;
		int	right;
		int	sum;

		public RangeValue(int left, int right, int sum) {
			this.left = left;
			this.right = right;
			this.sum = sum;
		}
	}
}
