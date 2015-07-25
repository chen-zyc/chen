package chen.system;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import chen.bytes.ByteUtil;
import chen.datetime.DateTimeUtil;
import chen.string.StringUtil;

public class SystemUtilTest {

	public static void main(String[] args) {
		testMemeryRatio();
		testFreeMemorySize();
	}

	public static void testMemeryRatio() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				Double ratio = SystemUtil.memoryRatio();
				String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
				System.out.println(time + ":内存使用率：" + StringUtil.float_percent(ratio));
			}
		}, 0, 1000);
	}

	public static void testFreeMemorySize() {
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				Long free_b = SystemUtil.freeMemorySize();
				System.out.println(DateTimeUtil.time() + ":空闲内存：" + ByteUtil.convertSuitably(free_b));
				
				free_b = SystemUtil.freeMemorySizeRuntime();
				System.out.println(DateTimeUtil.time() + ":空闲内存(Runtime)：" + ByteUtil.convertSuitably(free_b));
			}
		}, 0, 1000);
	}
}
