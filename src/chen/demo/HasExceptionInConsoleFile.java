package chen.demo;

import java.io.File;

import chen.file.FileUtil;
import chen.file.FileUtil.ReadFileCallBack;
import chen.judge.Judge;

/**
 * 控制台输出文件中是否包含错误信息
 * 
 * @author zhangyuchen
 * 
 */
public class HasExceptionInConsoleFile {

	public static void main(String[] args) {
		
		File file = new File("F:/console.txt");
		System.out.println("开始检查：" + file);
		FileUtil.readFile(file, new ReadFileCallBack() {

			@Override
			public boolean call(String line) {
				if (!Judge.empty(line)) {
					if (line.indexOf("Exception") >= 0) {
						System.out.println(line);
					}
				}
				return true;
			}
		});
		System.out.println("-----------> over <----------------");
		
	}
}
