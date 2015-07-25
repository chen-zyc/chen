package chen.system.info;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import chen.judge.Judge;

public class ComputerInfoAix extends AbstractComputerInfo {
	private static Pattern	macPattern			= Pattern.compile("((([0-9,A-F,a-f]{2}:){5})[0-9,A-F,a-f]{2})");

	private Pattern			cpuPattern			= Pattern.compile("[0-9A-Fa-f]{9}");

	private Pattern			diskSnPattern		= Pattern.compile("[0-9,A-F,a-f]{8}");

	private Pattern			baseBoardSnPattern	= Pattern.compile("[0-9,A-Z,a-z]{12}");

	public String getCpuSN() {
		String cpuSN = getCpuSNInternal();
		if (Judge.empty(cpuSN)) {
			return "0000000000000000";
		}
		return cpuSN;
	}

	public String[] getMacAddresses() {
		List<String> macAddress = getPhysicalAddresses();
		if (macAddress.size() == 0) {
			return DEFAULT_MAC_ADDRESS;
		}
		return macAddress.toArray(new String[macAddress.size()]);
	}

	public String[] getDiskSN() {
		List<String> diskSNs = getDiskSNInternal();
		if (diskSNs.size() == 0) {
			return DEFAULT_DISK_SN;
		}
		return diskSNs.toArray(new String[diskSNs.size()]);
	}

	public String getBaseBoardSN() {
		String baseBoardSN = getBaseBoardSNInternal();
		if (Judge.empty(baseBoardSN)) {
			return "00000000000";
		}
		return baseBoardSN;
	}

	public int getDiskSize() {
		return getDiskSizeInternal();
	}

	public int getMemorySize() {
		return getMemorySizeInternal();
	}

	private String getCpuSNInternal() {
		String cpuSN = "";
		InputStream is = null;
		StringBuffer buffer = new StringBuffer();
		try {
			String[] cmdArr = { "uname", "-Mu" };
			ProcessBuilder builder = new ProcessBuilder(cmdArr);
			builder.redirectErrorStream(true);
			Process proc = builder.start();
			is = proc.getInputStream();
			byte[] data = new byte[1024];
			int count;
			while ((count = is.read(data)) >= 0) {
				buffer.append(new String(data, 0, count));
			}
			Matcher matcher = this.cpuPattern.matcher(buffer.toString());
			if (matcher.find()) cpuSN = matcher.group();
		} catch (Exception localException) {
			if (is != null) try {
				is.close();
			} catch (IOException localIOException1) {}
		} finally {
			if (is != null) try {
				is.close();
			} catch (IOException localIOException2) {}

		}
		return cpuSN;
	}

	private List<String> getPhysicalAddresses() {
		List<String> resultStr = new ArrayList<String>();
		String line = "netstat -v";
		InputStreamReader is = null;
		BufferedReader br = null;
		try {
			Process proc = Runtime.getRuntime().exec(line);
			is = new InputStreamReader(proc.getInputStream());
			br = new BufferedReader(is);
			while ((line = br.readLine()) != null)
				if ((line.contains("Hardware Address")) || (line.contains("硬件地址"))) {
					Matcher matcher = macPattern.matcher(line);
					if (matcher.find()) resultStr.add(matcher.group());
				}
		} catch (Exception localException) {
			if (is != null) try {
				is.close();
			} catch (IOException localIOException1) {}
			if (br != null) try {
				br.close();
			} catch (IOException localIOException2) {}
		} finally {
			if (is != null) try {
				is.close();
			} catch (IOException localIOException3) {}
			if (br != null) try {
				br.close();
			} catch (IOException localIOException4) {}

		}
		return resultStr;
	}

	private List<String> getDiskSNInternal() {
		List<String> resultStr = new ArrayList<String>();
		String line = "";
		InputStreamReader is = null;
		BufferedReader br = null;
		try {
			String[] cmdArr = { "lscfg", "-vpl", "hdisk*" };
			ProcessBuilder builder = new ProcessBuilder(cmdArr);
			builder.redirectErrorStream(true);
			Process process = builder.start();

			is = new InputStreamReader(process.getInputStream());
			br = new BufferedReader(is);
			while ((line = br.readLine()) != null)
				if (line.contains("Serial Number")) {
					Matcher matcher = this.diskSnPattern.matcher(line);
					if (matcher.find()) resultStr.add(matcher.group());
				}
		} catch (Exception e) {
			e.printStackTrace();

			if (is != null) try {
				is.close();
			} catch (IOException localIOException1) {}
			if (br != null) try {
				br.close();
			} catch (IOException localIOException2) {}
		} finally {
			if (is != null) try {
				is.close();
			} catch (IOException localIOException3) {}
			if (br != null) try {
				br.close();
			} catch (IOException localIOException4) {}

		}
		return resultStr;
	}

	private String getBaseBoardSNInternal() {
		String baseBoardSN = "";
		String line = "";
		InputStreamReader is = null;
		BufferedReader br = null;
		try {
			String[] cmdArr = { "lscfg", "-vpl", "sysplanar0" };
			ProcessBuilder builder = new ProcessBuilder(cmdArr);
			builder.redirectErrorStream(true);
			Process process = builder.start();

			is = new InputStreamReader(process.getInputStream());
			br = new BufferedReader(is);
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.startsWith("Serial Number")) {
					Matcher matcher = this.baseBoardSnPattern.matcher(line);
					if (matcher.find()) {
						baseBoardSN = matcher.group();
						break;
					}
				}
			}
		} catch (Exception localException) {
			if (is != null) try {
				is.close();
			} catch (IOException localIOException1) {}
			if (br != null) try {
				br.close();
			} catch (IOException localIOException2) {}
		} finally {
			if (is != null) try {
				is.close();
			} catch (IOException localIOException3) {}
			if (br != null) try {
				br.close();
			} catch (IOException localIOException4) {}

		}
		return baseBoardSN;
	}

	private int getDiskSizeInternal() {
		int diskSize = 0;
		String sn = "";
		String line = "";
		InputStreamReader is = null;
		BufferedReader br = null;
		try {
			String[] cmdArr = { "lscfg", "-vpl", "hdisk*" };
			ProcessBuilder builder = new ProcessBuilder(cmdArr);
			builder.redirectErrorStream(true);
			Process process = builder.start();

			is = new InputStreamReader(process.getInputStream());
			br = new BufferedReader(is);
			while ((line = br.readLine()) != null)
				if (line.contains("磁盘驱动器")) {
					Pattern pattern = Pattern.compile("\\([1-9]\\d*");
					Matcher matcher = pattern.matcher(line);
					if (matcher.find()) {
						sn = matcher.group();
						sn = sn.substring(1);
						diskSize += Integer.valueOf(sn).intValue() / 1024;
					}
				}
		} catch (Exception localException) {
			if (is != null) try {
				is.close();
			} catch (IOException localIOException1) {}
			if (br != null) try {
				br.close();
			} catch (IOException localIOException2) {}
		} finally {
			if (is != null) try {
				is.close();
			} catch (IOException localIOException3) {}
			if (br != null) try {
				br.close();
			} catch (IOException localIOException4) {}

		}
		return diskSize;
	}

	private int getMemorySizeInternal() {
		String memorySize = "";
		String line = "";
		InputStreamReader is = null;
		BufferedReader br = null;
		try {
			String[] cmdArr = { "prtconf" };
			ProcessBuilder builder = new ProcessBuilder(cmdArr);
			builder.redirectErrorStream(true);
			Process process = builder.start();

			is = new InputStreamReader(process.getInputStream());
			br = new BufferedReader(is);
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.startsWith("内存大小：")) {
					Pattern pattern = Pattern.compile("[1-9]\\d*");
					Matcher matcher = pattern.matcher(line);
					if (matcher.find()) {
						memorySize = matcher.group();
						break;
					}
				}
			}
		} catch (Exception localException) {
			if (is != null) try {
				is.close();
			} catch (IOException localIOException1) {}
			if (br != null) try {
				br.close();
			} catch (IOException localIOException2) {}
		} finally {
			if (is != null) try {
				is.close();
			} catch (IOException localIOException3) {}
			if (br != null) try {
				br.close();
			} catch (IOException localIOException4) {}

		}
		return Integer.valueOf(memorySize).intValue();
	}
}
