package chen.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyUtil {

	
	/**
	 * 对instance使用handler作为代理 <br>
	 * <pre>
	 * 用法：
	 * Map&lt;String, String&gt; map = new HashMap&lt;String, String&gt;();
	 * CommonInvocationHandler handler = new CommonInvocationHandler(map);
	 * @SuppressWarnings("unchecked")
	 * Map&lt;String, String&gt; mapProxy = ProxyUtil.proxy(map, handler, Map.class);
	 * </pre>
	 * @param instance 被代理的实例
	 * @param handler 代理类
	 * @param clazz 需要转换的类型，这里必须是instance的一个接口
	 * @return 返回T的一个实例
	 */
	public static <T> T proxy(Object instance, InvocationHandler handler, Class<T> clazz) {
		if (instance == null) 
			return null;
		Object obj = Proxy.newProxyInstance(instance.getClass().getClassLoader(), instance.getClass().getInterfaces(), handler);
		return clazz.cast(obj);
	}
	
}
