package chen.algorithm.sort;

import java.util.Arrays;

import org.junit.Test;

import chen.judge.Judge;

public class InsertionSortTest {
	
	@Test
	public void testSort() {
		int[] arr = new int[] { 5, 2, 4, 6, 1, 3 };

		// 0 - length
		int[] arr1 = Arrays.copyOf(arr, arr.length);
		InsertionSort.sort(arr1, 0, arr1.length);
		if (Judge.equals(arr1, new int[] { 1, 2, 3, 4, 5, 6 })) {
			System.err.println("sort(0, length)出错，本该是1-6，实际是" + Arrays.toString(arr1));
		}

		// 验证是否出错
		class ErrCheck {
			int[]	arr;
			int		start;
			int		end;
			String	msg;

			public ErrCheck(int[] arr, int start, int end, String msg) {
				this.arr = arr;
				this.start = start;
				this.end = end;
				this.msg = msg;
			}
		}
		ErrCheck[] arr2 = new ErrCheck[] { 
				new ErrCheck(null, 0, 0, "null数组未检测出来"), 
				new ErrCheck(new int[] {}, 0, 0, "空数组未检测出来"), 
				new ErrCheck(new int[] { 1 }, -1, 1, "开始位置小于零未检测出来"), 
				new ErrCheck(new int[] { 1 }, 4, 1, "开始位置大于数组长度未检测出来"), 
				new ErrCheck(new int[] { 1 }, 0, -1, "结束位置小于零未检测出来"), 
				new ErrCheck(new int[] { 1 }, 0, 4, "结束位置大于数组长度未检测出来"), 
				new ErrCheck(new int[] { 1, 2, 3 }, 1, 0, "开始位置大于结束位置未检测出来")
		};
		for(ErrCheck ec:arr2){
			boolean hasErr = false;
			try {
				InsertionSort.sort(ec.arr, ec.start, ec.end);
			} catch (IllegalArgumentException e) {
				hasErr = true;
			}
			if (!hasErr) {
				System.err.println(ec.msg);
			}
		}
		
		// 0 - 中间位置
		int[] arr3 = Arrays.copyOf(arr, arr.length);
		InsertionSort.sort(arr3, 0, 3);
		if (Judge.equals(arr3, new int[] { 2, 4, 5, 6, 1, 3 })) {
			System.err.println("sort(0, 3)出错，本该是[2, 4, 5, 6, 1, 3]，实际是" + Arrays.toString(arr3));
		}

		// 中间位置 - length
		int[] arr4 = Arrays.copyOf(arr, arr.length);
		InsertionSort.sort(arr4, 3, arr4.length);
		if (Judge.equals(arr3, new int[] { 5, 2, 4, 1, 3, 6 })) {
			System.err.println("sort(0, 3)出错，本该是[5, 2, 4, 1, 3, 6]，实际是" + Arrays.toString(arr3));
		}

		// 中间位置 - 中间位置
		int[] arr5 = Arrays.copyOf(arr, arr.length);
		InsertionSort.sort(arr5, 2, 5);
		if (Judge.equals(arr3, new int[] { 5, 2, 1, 4, 6, 3 })) {
			System.err.println("sort(0, 3)出错，本该是[5, 2, 4, 1, 3, 6]，实际是" + Arrays.toString(arr3));
		}

		System.out.println("测试完毕");
	}

}
