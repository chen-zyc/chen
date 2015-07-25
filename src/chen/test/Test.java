package chen.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Test {

	public static void main(String[] args) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy", Locale.US);
		Date d = new Date();
		String s = sdf.format(d);
		System.out.println(s);
	}
}
