package chen.reflect;

import java.util.HashMap;
import java.util.Map;

public class ProxyUtilTest {

	public static void main(String[] args) {
		testProxy();
	}
	
	public static void testProxy() {
		Map<String, String> map = new HashMap<String, String>();
		CommonInvocationHandler handler = new CommonInvocationHandler(map);
		
		@SuppressWarnings("unchecked")
		Map<String, String> mapProxy = ProxyUtil.proxy(map, handler, Map.class);
		
		mapProxy.put("key1", "val1");
		mapProxy.put("key2", "val2");
		mapProxy.get("key1");
		mapProxy.remove("key2");
	}
}
