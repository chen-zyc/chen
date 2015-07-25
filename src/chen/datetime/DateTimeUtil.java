package chen.datetime;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

	/*==================== 显示相关 ============================*/
	
	/**
	 * 以HH:mm:ss的格式输出当前时间 <br>
	 * time() => 14:27:49
	 * @return
	 */
	public static String time() {
		return new SimpleDateFormat("HH:mm:ss").format(new Date());
	}
	
	/**
	 * 以yyyy:MM:dd的格式输出当前日期<br>
	 * date() => 2014/08/27
	 * @return
	 */
	public static String date() {
		return new SimpleDateFormat("yyyy/MM/dd").format(new Date());
	}
	
	/**
	 * 以yyyy/MM/dd HH:mm:ss的格式输出当前日期和时间<br>
	 * dateTime() => 2014/08/27 14:32:27
	 * @return
	 */
	public static String dateTime() {
		return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
	}
	
	/**
	 * 以自定义格式输出当前时间<br>
	 * format("HH:mm:ssa") => 14:36:48下午
	 * @param format
	 * @return
	 */
	public static String format(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}
}
