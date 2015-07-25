package chen.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * 翻译自beego的conf/ini.go
 * @author zhangyuchen
 *
 */
public class INI {

	final String				DEFAULT_SECTION	= "default";
	final String				bNumComment		= "#";			// number signal
	final String				bSemComment		= ";";			// semicolon
																// signal
	final String				bEmpty			= "";
	final String				bEqual			= "=";			// equal signal
	final String				bDQuote			= "\"";		// quote signal
	final String				sectionStart	= "[";			// section start
																// signal
	final String				sectionEnd		= "]";			// section end
																// signal
	final String				lineBreak		= "\n";

	private IniConfigContainer	cfg				= null;

	public String parse(String fileName, String charset) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(fileName);
		} catch (FileNotFoundException e1) {
			return e1.getMessage();
		}
		BufferedReader r = new BufferedReader(new InputStreamReader(fis, Charset.forName(charset)));
		String err = parse(r);
		if (err != null) {
			return err;
		}
		try {
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean Bool(String key) {
		return Boolean.valueOf(getData(key));
	}

	public boolean DefaultBool(String key, boolean defaultval) {
		String v = getData(key);
		if (v.length() <= 0) {
			return defaultval;
		}
		return Boolean.valueOf(v);
	}

	public int Int(String key) {
		return Integer.valueOf(getData(key));
	}

	public int DefaultInt(String key, int defaultval) {
		try {
			return Int(key);
		} catch (NumberFormatException e) {
			return defaultval;
		}
	}

	public float Float(String key) {
		return Float.valueOf(getData(key));
	}

	public float DefaultFloat(String key, float defaultval) {
		try {
			return Float(key);
		} catch (NumberFormatException e) {
			return defaultval;
		}
	}

	public String Str(String key) {
		return getData(key);
	}

	public String DefaultString(String key, String defaultval) {
		String v = Str(key);
		if (v.length() <= 0) {
			return defaultval;
		}
		return v;
	}

	public Map<String, String> getSection(String section) {
		if (cfg == null || cfg.data == null) {
			return null;
		}
		return cfg.data.get(section);
	}

	
	private String parse(BufferedReader r) {
		cfg = new IniConfigContainer();
		cfg.data = new HashMap<String, Map<String, String>>();
		cfg.sectionComment = new HashMap<String, String>();
		cfg.keyComment = new HashMap<String, String>();
		cfg.lock = new ReentrantReadWriteLock();

		WriteLock wlock = cfg.lock.writeLock();
		wlock.lock();
		try {
			StringBuilder comment = new StringBuilder();
			// check the BOM
			char[] bom = new char[3];
			boolean hasBom = false;
			r.read(bom);
			if (bom[0] == 239 && bom[1] == 187 && bom[2] == 191) {
				hasBom = true;
			}

			String section = DEFAULT_SECTION;
			String line = r.readLine();
			if (!hasBom) {
				line = new String(bom) + line;
			}
			while (line != null) {
				if (line.equals(bEmpty)) {
					line = r.readLine();
					continue;
				}
				line = line.trim();

				String bComment = null;
				if (line.startsWith(bNumComment)) {
					bComment = bNumComment;
				} else if (line.startsWith(bSemComment)) {
					bComment = bSemComment;
				}

				if (bComment != null) {
					line = line.substring(bComment.length());
					line = line.trim();
					comment.append(line).append('\n');
					line = r.readLine();
					continue;
				}

				if (line.startsWith(sectionStart) && line.endsWith(sectionEnd)) {
					section = line.substring(sectionStart.length(), line.length() - sectionEnd.length());
					if (comment.length() > 0) {
						cfg.sectionComment.put(section, comment.toString());
						comment = new StringBuilder();
					}
					if (!cfg.data.containsKey(section)) {
						cfg.data.put(section, new HashMap<String, String>());
					}
					line = r.readLine();
					continue;
				}

				if (!cfg.data.containsKey(section)) {
					cfg.data.put(section, new HashMap<String, String>());
				}

				String[] kv = line.split(bEqual, 2);
				if (kv.length < 2) {
					return "read the content error: \"" + line + "\", should key = val";
				}
				String key = kv[0].trim();
				String value = kv[1].trim();
				if (value.startsWith(bDQuote)) {
					value = value.substring(bDQuote.length());
					if (value.endsWith(bDQuote)) {
						value = value.substring(0, value.length() - bDQuote.length());
					}
				}

				cfg.data.get(section).put(key, value);
				if (comment.length() > 0) {
					cfg.keyComment.put(section + "." + key, comment.toString());
					comment = new StringBuilder();
				}

				line = r.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		} finally {
			wlock.unlock();
		}

		return null;
	}

	private String getData(String key) {
		if (key == null || key.length() <= 0) {
			return "";
		}
		if (cfg == null) {
			return "";
		}

		ReadLock rlock = cfg.lock.readLock();
		rlock.lock();
		try {
			String section, k;
			String[] kv = key.toLowerCase().split("::", 2);
			if (kv.length >= 2) {
				section = kv[0];
				k = kv[1];
			} else {
				section = DEFAULT_SECTION;
				k = kv[0];
			}
			Map<String, String> v = cfg.data.get(section);
			if (v != null) {
				String vv = v.get(k);
				if (vv != null) {
					return vv;
				}
			}
		} finally {
			rlock.unlock();
		}

		return "";
	}

	private class IniConfigContainer {
		/** section => key:val */
		Map<String, Map<String, String>>	data;
		/** section:comment */
		Map<String, String>					sectionComment;
		/** section.key:comment */
		Map<String, String>					keyComment;
		ReentrantReadWriteLock				lock;
	}

}
