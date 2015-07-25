package chen.file;

import java.io.File;
import java.util.ArrayList;

import chen.file.XMLUtil.Callback;

public class XMLUtilTest {

	public static void main(String[] args) {
		XMLUtil util = new XMLUtil();
		
		File dir = new File("D:/temp/for_input_0_0/参数包_升级M6中央版参数包20140922-174607/billdefines");
		if (!dir.exists() || !dir.isDirectory()) {
			throw new IllegalArgumentException("illegal dir...");
		}
		
		ArrayList<String> props = new ArrayList<String>();
		props.add("type");
		props.add("maxLength");
		props.add("digitLength");
		props.add("intLength");
		props.add("intLength");
		props.add("maxValue");
		props.add("minValue");
		
		String[] nodePaths = new String[] {
				"/root/masterTable/fields/field",
				"/root/detailTables/detailTable/fields/field"
		};
		
		File[] children = dir.listFiles();
		for (File child:children) {
//			System.out.println("check " + child.getName());
			for (String nodePath : nodePaths) {
//				System.out.println("\t check " + nodePath);
				test(util, child, nodePath, props);
			}
		}
 		
		System.out.println("over.");
	}
	
	public static void test(XMLUtil util, File file, String nodePath, ArrayList<String> props) {
		boolean have = util.setFile(file);
		if (!have) {
			throw new RuntimeException("no file...");
		}
		
		util.visit(nodePath, props, new Callback() {
			
			int nowindex = -1;
			
			@Override
			public Ret call(int nodeIndex, String prop, String value) {
				if (nodeIndex != nowindex) {
					nowindex = nodeIndex;
					if (!prop.equals("type")) {
						throw new IllegalStateException("第一个属于应该是type,但却是" + prop);
					}
					if (value.equals("")) {
						throw new IllegalStateException("类型值为空");
					}
					if (!value.toUpperCase().equals("INT")) {
						return Ret.BREAK;
					}
					return Ret.GOON;
				}
				if (prop.equals("type")) {
					throw new IllegalStateException("非第一个属于不应该是type,但却是" + prop);
				}
				System.out.println(prop + " : " + value);
				if (value.equals("0.0")) {
					return Ret.PRINT;
				}
				return Ret.GOON;
			}
		});
	}
	
}
