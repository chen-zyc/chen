package chen.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import chen.io.IOUtil;
import chen.judge.Judge;

public class ImageUtil {

	/**
	 * 这种方式可获得classpath下和网络上的图片
	 * @param imagePath， 示例：chen/ui/images/logo.gif， http://***.jpg
	 * @return
	 */
	public static InputStream getInputStream(String imagePath) {
		if (Judge.empty(imagePath)) {
			return null;
		}
		try {
			URL url = null;
			if (isURL(imagePath)) {
				url = new URL(imagePath);
			} else {
				url = ImageUtil.class.getResource(imagePath);
				if (url == null) {
					url = ImageUtil.class.getClassLoader().getResource(imagePath);
				}
			}
			if (url == null) {
				return null;
			}
			URLConnection conn = url.openConnection();
			return conn.getInputStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] getBytes(String imagePath) {
		InputStream is = getInputStream(imagePath);
		return IOUtil.getBytes(is);
	}
	
	public static void resize(int width, int height) {
		
	}
	
	public static boolean isURL(String url) {
		if (Judge.empty(url)) {
			return false;
		}
		if (url.startsWith("http://")) {
			return true;
		}
		return false;
	}
}
