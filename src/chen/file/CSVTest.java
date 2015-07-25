package chen.file;

public class CSVTest {

	public static void main(String[] args) {
		testWrite();
	}

	public static void testWrite() {
		String filePath = "D://temp/csvtest/data.csv";
		CSVWriter writer = new CSVWriter(filePath);
		String[][] rows = new String[][] { 
				new String[] { "1", "2", "3" }, 
				new String[] { "一", "二", "仨" }, 
		};
		for (String[] row : rows) {
			writer.write(row);
		}
		writer.close();
	}

}
