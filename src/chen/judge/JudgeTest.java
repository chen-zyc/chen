package chen.judge;

import org.junit.Assert;
import org.junit.Test;

public class JudgeTest {

	public static void main(String[] args) {
//		testEquals();
//		testHasNull();
		testEqualsFields();
	}
	
	public static void testEquals() {
		System.out.println(Judge.equals(null, null)); // true
		System.out.println(Judge.equals("aa", "aa")); // true
		System.out.println(Judge.equals(new String("a"), new String("a"))); // true
		System.out.println(Judge.equals(new Judge(), new Judge())); // false
	}
	
	public static void testHasNull() {
		System.out.println(Judge.hasNull("a", null)); // true
		System.out.println(Judge.hasNull(null, null)); // true
		System.out.println(Judge.hasNull("a", "a")); // false
	}
	
	public static void testEqualsFields() {
		// 类的类型一样的情况
		TestObject1 obj1 = new TestObject1(1, "aa", 3.0f, 5.9, true);
		TestObject1 obj2 = new TestObject1(1, "aa", 3.0f, 5.9, true);
		TestObject1 obj3 = new TestObject1(1, "awa", 3.3f, 5.9, true);
		
		try {
			System.out.println("比较obj1与obj2：" + Judge.equalsFields(obj1, obj2));
			
			System.out.println("比较obj1与obj3：" + Judge.equalsFields(obj1, obj3));
			
			System.out.println("比较obj2与obj3：" + Judge.equalsFields(obj2, obj3));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 类的类型不一样的情况
		TestObject2 obj4 = new TestObject2(3, "hello");
		try {
			System.out.println("比较obj1与obj4：" + Judge.equalsFields(obj1, obj4));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testIsPowerOfTwo() {
		int[] yesNums = new int[] { 0, 2, 2 << 1, 2 << 2, 2 << 3, 2 << 10, 2 << 20 };
		int[] noNums = new int[] { 3, 6, 7, 10 };
		for (int n : yesNums) {
			Assert.assertEquals("test [" + n + "]", true, Judge.isPowerOfTwo(n));
		}
		for (int n : noNums) {
			Assert.assertEquals("test [" + n + "]", false, Judge.isPowerOfTwo(n));
		}
	}
	
	@Test
	public void testIsMoney() {
		String[] tstr = {"+1,234.56", "-1,2,3.5", "1234", "0.3"};
		String[] fstr = {"$123", ""};
		for (String s : tstr) {
			Assert.assertTrue(s + " should true", Judge.isMoney(s));
		}
		for (String s : fstr) {
			Assert.assertFalse(s + " should false", Judge.isMoney(s));
		}
	}
	
	@Test
	public void testContainsCN() {
		class T {
			String str;
			boolean result;

			public T(String str, boolean result) {
				this.str = str;
				this.result = result;
			}
		}
		T[] tests = new T[]{
				new T("abc", false),
				new T("", false),
				new T("a中文b", true),
				new T("你好", true),
		};
		for (T t : tests) {
			boolean ret = Judge.containsCN(t.str);
			Assert.assertEquals("E: " + t.str, t.result, ret);
		}
	}
}
