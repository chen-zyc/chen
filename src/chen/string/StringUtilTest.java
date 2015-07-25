package chen.string;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilTest {

	class T1 {
		String	s;
		String	result;

		public T1(String s, String result) {
			this.s = s;
			this.result = result;
		}
	}
	
	@Test
	public void testDeleteWhitespace() {
		T1[] tests = new T1[]{
				new T1("abc", "abc"),
				new T1(" abc", "abc"),
				new T1("a bc", "abc"),
				new T1("abc ", "abc"),
				new T1("abc\n", "abc"),
				new T1("a, bc", "a,bc"),
		};
		for (T1 t : tests) {
			String ret = StringUtil.deleteWhitespace(t.s);
			Assert.assertEquals("Err:" + t.s + ", want " + t.result, t.result, ret);
		}
	}



}
