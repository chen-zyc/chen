package chen.util;


public class INITest {

	public static void main(String[] args) {
		String path = System.getProperty("user.dir");
		String file = path + "/files/demo/initest.ini";

		INI ini = new INI();
		String err = ini.parse(file, "GB2312");
		if (err != null && err.length() > 0) {
			System.err.println(err);
			return;
		}

		String s = ini.Str("appname");
		if (!"beeapi".equals(s)) {
			err("appname:" + s);
		}

		int i = ini.Int("httpport");
		if (8080 != i) {
			err("httpport:" + i);
		}

		i = ini.Int("mysqlport");
		if (3600 != i) {
			err("mysqlport:" + i);
		}

		float f = ini.Float("PI");
		if (Math.abs(3.1415976 - f) > 0.0001) {
			err("PI:" + f);
		}

		s = ini.Str("runmode");
		if (!"dev".equals(s)) {
			err("runmode:" + s);
		}

		boolean b = ini.Bool("autorender");
		if (false != b) {
			err("autorender:" + b);
		}

		b = ini.Bool("copyrequestbody");
		if (true != b) {
			err("copyrequestbody:" + b);
		}

		s = ini.Str("demo::key1");
		if (!"asta".equals(s)) {
			err("demo::key1:" + s);
		}

		s = ini.Str("demo::key2");
		if (!"xie".equals(s)) {
			err("demo::key2:" + s);
		}

		b = ini.Bool("demo::CaseInsensitive");
		if (true != b) {
			err("demo::CaseInsensitive:" + b);
		}

		s = ini.Str("demo::peers");
		if (!"one;two;three".equals(s)) {
			err("demo::peers:" + s);
		}
		
		s = ini.Str("demo::cn");
		if (!"汉字".equals(s)) {
			err("demo::cn:" + s);
		}
		
		System.out.println("OK");
	}

	private static void err(String err) {
		System.err.println(err);
		System.exit(1);
	}
}
