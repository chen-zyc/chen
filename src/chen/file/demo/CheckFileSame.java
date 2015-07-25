package chen.file.demo;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import chen.file.FileUtil;

public class CheckFileSame {

	public static void main(String[] args) {
		File f1 = new File("D:/参数导入测试/参数包_一键升级自用20140923-230522/mattermaterials");
		File f2 = new File("D:/参数导入测试/参数包_M6参数升级包20140922-174607/mattermaterials");
		List<File> f1children = Arrays.asList(f1.listFiles());
		List<File> f2children = Arrays.asList(f2.listFiles());
		for (File ch1 : f1children) {
			File ch2 = null;
			for (File t : f2children) {
				if (t.getName().equals(ch1.getName())) {
					ch2 = t;
					break;
				}
			}
			if (ch2 == null) {
				System.out.println("single:" + ch1.getName());
				continue;
			}
			String s1 = FileUtil.readFile(ch1).replaceAll("\\s+", "");
			String s2 = FileUtil.readFile(ch2).replaceAll("\\s+", "");
			System.out.println(ch1.getName() + "的结果：" + (s1.equals(s2)));
		}

	}
}
