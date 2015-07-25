package chen.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class CommonInvocationHandler implements InvocationHandler{

	/** 被代理的类实例 */
	private Object proxiedObj;
	
	public CommonInvocationHandler(Object proxiedObj) {
		this.proxiedObj = proxiedObj;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		StringBuilder msg = new StringBuilder();
		msg.append("Class : " + proxiedObj.getClass() + "\n\t")
			.append("Method : " + method.getName() + "\n\t")
			.append("Parameters : " + Arrays.toString(args) + "\n\t");
		System.out.println("CommonInvocationHandler Message : \n\t" + msg);
		
		return method.invoke(proxiedObj, args);
	}

}
