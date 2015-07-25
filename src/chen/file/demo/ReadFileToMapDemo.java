package chen.file.demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import chen.file.FileUtil;
import chen.judge.Judge;
import chen.print.Print;
import chen.string.StringUtil;

public class ReadFileToMapDemo {
	
	public static void main(String[] args) {
		Map<String, Long> map = new ReadFileToMapDemo().parseFile();
		Print.print(map, 30);
	}

	/**
	 * 读取文件内容到Map中，文件参考/files/demo/file_demo/table_record_size.txt
	 * @param f
	 * @return
	 */
	public Map<String, Long> parseFile() {
		final Map<String, Long> ret = new HashMap<String, Long>();
		// 回调
		FileUtil.ReadFileCallBack callback = new FileUtil.ReadFileCallBack() {
			
			public boolean call(String line) {
				parseLine(line, ret);
				return true;
			}
		};
		
		String path = System.getProperty("user.dir");
		String filePath = "/files/demo/file_demo/table_record_size.txt";
		// jar包中的情况
		InputStream in = this.getClass().getResourceAsStream(filePath);
		if (in == null) {
			try {
				// 试着读取文件
				in = new FileInputStream(path + filePath);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		if (in == null) {
			throw new RuntimeException("找不到文件：" + filePath);
		}
		FileUtil.readFile(in, callback);
		
		return ret;
	}
	
	
	/*==========================================*/
	/**
	 * 解析每一行
	 * @param line
	 * @param ret
	 */
	private void parseLine(String line, Map<String, Long> ret) {
		if (Judge.trimEmpty(line)) {
			return;
		}
		String text = line.trim();
		// 注释跳过
		if (text.startsWith("#")) {
			return;
		}
		// 按分号分割
		String[] parts = line.split(":");
		if (parts.length < 2) {
			return;
		}
		// 不能为空
		if (Judge.trimEmpty(parts[0]) || Judge.trimEmpty(parts[1])) {
			return;
		}
		// 解析数字部分
		Long bytes = StringUtil.convertLong(StringUtil.allNumberFromStr(parts[1]), -1L);
		if (bytes <= 0) {
			return;
		}
		ret.put(parts[0].trim(), bytes);
	}
	
}
