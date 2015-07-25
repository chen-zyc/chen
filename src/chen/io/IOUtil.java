package chen.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class IOUtil {

	public static byte[] getBytes(InputStream is) {
		if (is == null) {
			return null;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] bytes = new byte[1024];
		int len = -1;
		try {
			while ((len = is.read(bytes)) != -1) {
				out.write(bytes, 0, len);
			}
			out.flush();
			return out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
