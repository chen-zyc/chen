package chen.datetime;

import java.util.Timer;
import java.util.TimerTask;


public class DateTimeUtilTest {

	public static void main(String[] args) {
//		testTime();
//		testDate();
//		testDateTime();
		testFormat();
	}
	
	public static void testTime() {
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				System.out.println(DateTimeUtil.time());
			}
		}, 0, 1000);
	}
	
	public static void testDate() {
		System.out.println(DateTimeUtil.date());
	}
	
	public static void testDateTime() {
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				System.out.println(DateTimeUtil.dateTime());
			}
		}, 0, 1000);
	}
	
	public static void testFormat() {
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				System.out.println(DateTimeUtil.format("HH:mm:ssa"));
			}
		}, 0, 1000);
	}
}
