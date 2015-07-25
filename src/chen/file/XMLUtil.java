package chen.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import chen.file.XMLUtil.Callback.Ret;

public class XMLUtil {

	private SAXReader reader = new SAXReader();
	private InputStream input = null;
	private Document doc = null;
	
	/**
	 * 读取xml文件并解析成Document
	 * @param f
	 * @return 若解析出错返回false
	 */
	public boolean setFile(File f) {
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			input = new FileInputStream(f);
			doc = reader.read(input);
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 遍历nodePath指定的节点列表，对于每个节点的每个props指定的属性都会调用call
	 * @param nodePath
	 * @param props
	 * @param call
	 */
	public void visit(String nodePath, List<String> props, Callback call) {
		if (doc == null) {
			return;
		}
		
		List<?> nodes = doc.selectNodes(nodePath);
		if (nodes != null && nodes.size() > 0) {
			for (int i = 0; i < nodes.size(); i++) {
				Node node = (Node) nodes.get(i);
				for (String prop : props) {
					String value = node.valueOf("@" + prop);
					Ret ret = call.call(i, prop, value);
					if (Ret.BREAK.equals(ret)) {
						break;
					}
					if (Ret.GOON.equals(ret)) {
						continue;
					}
					if (Ret.PRINT.equals(ret)) {
						printNode(node);
						break;
					}
				}
			}
		}
	}
	
	public void printNode(Node node) {
		System.out.println(node.asXML());
	}
	
	public static interface Callback {
		enum Ret {
			/** 跳过该节点 */
			BREAK,
			/** 继续 */
			GOON,
			/** 打印节点 */
			PRINT
		}
		
		/**
		 * 对每个节点的每个属性调用方法
		 * @param nodeIndex 节点索引
		 * @param prop 属性名
		 * @param value 属性值
		 * @return
		 */
		Ret call(int nodeIndex, String prop, String value);
	}
}
