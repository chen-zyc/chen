package chen.windows.cmd;

import java.util.Timer;
import java.util.TimerTask;

import chen.string.StringUtil;

public class CmdUtilTest {

	public static void main(String[] args) {
		testGetCpuRatioForWindows();
	}
	
	public static void testGetCpuRatioForWindows() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				double ratio = CmdUtil.getCpuRatioForWindows();
				System.out.println("当前CUP使用率：" + StringUtil.float_percent(ratio));
			}
		}, 0, 1000);
	}
}
