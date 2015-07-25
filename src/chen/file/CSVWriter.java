package chen.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import chen.judge.Judge;
import chen.util.Arrays;

public class CSVWriter {

	public static String	endFlag	= "\r\n";
	private Writer		writer;

	public CSVWriter(String filePath) {
		if (Judge.empty(filePath)) {
			throw new IllegalArgumentException();
		}
		File f = FileUtil.createFile(filePath);
		if (f == null) {
			throw new IllegalArgumentException("cannt create filePath");
		}
		try {
//			writer = new FileWriter(f);
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "GB2312"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public CSVWriter write(String[] row) {
		if (writer == null) {
			throw new RuntimeException("writer is null");
		}
		try {
			if (Judge.empty(row)) {
				writer.write(endFlag);
			} else {
				// TODO 如果文本中出现','，则达不到目的，应该将含有','的问题用双引号括起来或者所有的row的元素都用双引号括起来
				writer.write(Arrays.join(",", row) + endFlag);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	public void close() {
		if (writer != null) {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
