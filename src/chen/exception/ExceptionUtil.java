package chen.exception;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {

	/**
	 * 获取异常最初的cause，若e为null，则返回一个新的throwable()，以保证返回值不为空
	 * @param e
	 * @return
	 */
	public static Throwable getFirstCause(Throwable e) {
		if (e == null) {
			return new Throwable();
		}
		Throwable cause = e.getCause();
		while (cause != null) {
			e = cause;
			cause = cause.getCause();
		}
		return e;
	}
	
	/**
	 * 获取最初那个cause的message
	 * @param e
	 * @return
	 */
	public static String getFirstMessage(Throwable e) {
		return getFirstCause(e).getMessage();
	}
	
	/**
	 * 获取错误堆栈详细信息
	 * @param e
	 * @return
	 */
	public static String getStackTrace(Throwable e) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintStream pout = new PrintStream(out);
		e.printStackTrace(pout);
		String ret = new String(out.toByteArray());
		pout.close();
		try {
			out.close();
		} catch (Exception e1) {}
		return ret;
	}
	
	/**
	 * 获取错误堆栈详细信息
	 * @param e
	 * @return
	 */
	public static String getStackTrace2(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		e.printStackTrace(pw);
		pw.flush();
		sw.flush();
		return sw.toString();
	}
}
