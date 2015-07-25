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

public class ComputerInfoLinux extends AbstractComputerInfo {
	private static Pattern	macPattern		= Pattern.compile("((([0-9,A-F,a-f]{2}:){5})[0-9,A-F,a-f]{2})");

	private Pattern			cpuPattern		= Pattern.compile("([0-9,A-F,a-f]{2}\\s){7}[0-9,A-F,a-f]{2}");

	private Pattern			diskSnPattern	= Pattern.compile("([0-9,A-F,a-f]{8}-)([0-9,A-F,a-f]{4}-){3}[0-9,A-F,a-f]{8}");

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

	private String getCpuSNInternal() {
		String cpuSN = "";
		InputStream is = null;
		StringBuffer buffer = new StringBuffer();
		try {
			String[] cmdArr = { "sudo", "dmidecode", "-t", "4", "|", "grep", "ID" };
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
		String line = "ifconfig -a";
		Pattern pattern = macPattern;
		InputStreamReader is = null;
		BufferedReader br = null;
		try {
			Process proc = Runtime.getRuntime().exec(line);
			is = new InputStreamReader(proc.getInputStream());
			br = new BufferedReader(is);
			while ((line = br.readLine()) != null)
				if ((line.contains("物理地址")) || (line.contains("Physical Address")) || ((line.contains("Ethernet")) && (line.contains("HWaddr")))) {
					Matcher matcher = pattern.matcher(line);
					if (matcher.find()) {
						String str = matcher.group();
						int index = line.indexOf(str);
						if (index + str.length() < line.length() - 1) {
							char chr = line.charAt(index + str.length());
							if (chr == '-') ;
						} else {
							resultStr.add(str);
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
		return resultStr;
	}

	private List<String> getDiskSNInternal() {
		List<String> resultStr = new ArrayList<String>();
		InputStream is = null;
		StringBuffer buffer = new StringBuffer();
		try {
			String[] cmdArr = { "ls", "-l", "/dev/disk/by-uuid" };
			ProcessBuilder builder = new ProcessBuilder(cmdArr);
			builder.redirectErrorStream(true);
			Process proc = builder.start();
			is = proc.getInputStream();
			byte[] data = new byte[1024];
			int count;
			while ((count = is.read(data)) >= 0) {
				buffer.append(new String(data, 0, count));
			}
			Matcher matcher = this.diskSnPattern.matcher(buffer.toString());
			while (matcher.find())
				resultStr.add(matcher.group());
		} catch (Exception e) {
			e.printStackTrace();

			if (is != null) try {
				is.close();
			} catch (IOException localIOException1) {}
		} finally {
			if (is != null) try {
				is.close();
			} catch (IOException localIOException2) {}

		}
		return resultStr;
	}

	private String getBaseBoardSNInternal() {
		String baseBoardSN = "";
		String line = "";
		InputStreamReader is = null;
		BufferedReader br = null;
		try {
			String[] cmdArr = { "sudo", "dmidecode", "-s", "baseboard-serial-number" };
			ProcessBuilder builder = new ProcessBuilder(cmdArr);
			builder.redirectErrorStream(true);
			Process process = builder.start();

			is = new InputStreamReader(process.getInputStream());
			br = new BufferedReader(is);
			while ((line = br.readLine()) != null) {
				baseBoardSN = line;
			}

			if ("None".equals(baseBoardSN)) baseBoardSN = "";
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
		return baseBoardSN.trim();
	}
}
