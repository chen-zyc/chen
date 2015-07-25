package chen.algorithm.random;

import java.util.Arrays;
import java.util.Random;

public class RandomTest {

	/**
	 * 随机分布a中的元素
	 * 
	 * @param a
	 */
	public static void randomInPlace(int[] a) {
		Random random = new Random();
		for (int i = 0; i < a.length; i++) {
			// [i, length)之间的随机数
			int n = random.nextInt(a.length - i) + i;
			int temp = a[i];
			a[i] = a[n];
			a[n] = temp;
		}
	}
	
	public static void testRandomInPlace() {
		int[] a = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		for (int i = 0; i < 9; i++) {
			randomInPlace(a);
			System.out.println(Arrays.toString(a));
		}
	}

	public static void main(String[] args) {
		// printRandomTest();
		testRandomInPlace();
	}

	public static void printRandomTest() {
		Random random = new Random();
		for (int i = 0; i < 5; i++) {
			// [i, 5)之间的随机数
			int n = random.nextInt(5 - i) + i;
			System.out.println("[" + i + ", 5)之间：" + n);
		}
	}
}
