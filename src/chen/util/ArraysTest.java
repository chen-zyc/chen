package chen.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ArraysTest {

	@Test
	public void testJoin() {
		class T {
			String		seg;
			String[]	ele;
			String		want;

			public T(String seg, String[] ele, String want) {
				this.seg = seg;
				this.ele = ele;
				this.want = want;
			}
		}

		T[] tests = new T[] { 
				new T(",", new String[] { "1", "2", "3" }, "1,2,3"), 
				new T("", new String[] { "1", "2", "3" }, "123"), 
				new T(",", new String[] { "1" }, "1"), 
		};
		
		for (T t : tests) {
			String ret = Arrays.join(t.seg, t.ele);
			Assert.assertEquals(t.want, ret);
		}
		
	}
	
	public static void testDistinct() {
		List<String> list = new ArrayList<String>();
		list.add("aaa");
		list.add("aaa");
		list.add("bbb");
		list.add("aaa");
		list.add("bbb");

		Arrays.distinct(list);
		System.out.println(java.util.Arrays.toString(list.toArray()));
		
		String[] arr = new String[]{
				"a", "b", "a", "b", "b"
		};
		
		System.out.println(java.util.Arrays.toString(Arrays.distinct(arr, new String[0])));
		
	}
	
	
	public static void main(String[] args) {
		testDistinct();
	}
	
}
