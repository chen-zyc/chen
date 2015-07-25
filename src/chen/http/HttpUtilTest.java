package chen.http;

import java.io.IOException;

public class HttpUtilTest {
	
	public static void main(String[] args) {
		new HttpUtilTest().test();
	}

	public void test() {
		try {
			String response = HttpUtil.get("http://www.baidu.com");
			System.out.println(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
