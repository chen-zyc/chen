package chen.algorithm.分治;

import chen.judge.Judge;

/**
 * 《算法导论》，4.2 矩阵相乘的Strassen算法
 * =================== 未完成 ======================
 * @author zhangyuchen
 *
 */
@SuppressWarnings("unused")
public class SquareMatrixMultiply {

	public static int[][] multiply(int[][] A, int[][] B) {
		check(A, B);
		
		return null;
	}
	
	private static int[][] _multiply(int[][] A, int[][] B, int x, int y, int len) {
		// TODO
		
		return null;
	}
	
	/**
	 * 从B复制到A，复制区域为[x, x+len), [y, y+len)
	 * @param A
	 * @param B
	 * @param x
	 * @param y
	 * @param len
	 */
	private static void assign(int[][] A, int[][] B, int x, int y, int len) {
		for (int i = x, n = x + len; i < n; i++) {
			for (int j = y, m = y + len; j < m; j++) {
				A[i][j] = B[i][j];
			}
		}
	}
	
	private static void check(int[][] A, int[][] B) {
		if (A == null || B == null) {
			throw new IllegalArgumentException("二维数组不能为空");
		}
		// 行数
		if (A.length == 0 || !Judge.isPowerOfTwo(A.length) || B.length == 0 || !Judge.isPowerOfTwo(B.length)) {
			throw new IllegalArgumentException("数组长度不能为0，且必须是2的幂");
		}
		if (A.length != B.length) {
			throw new IllegalArgumentException("两个数组的长度需相等");
		}
		// 列数
		for (int[] row : A) {
			if (row == null || row.length == 0 || !Judge.isPowerOfTwo(row.length)) {
				throw new IllegalArgumentException("数组每一列的长度都不能为空，且必须是2的幂");
			}
		}
		for (int[] row : B) {
			if (row == null || row.length == 0 || !Judge.isPowerOfTwo(row.length)) {
				throw new IllegalArgumentException("数组每一列的长度都不能为空，且必须是2的幂");
			}
		}
	}
	
}
