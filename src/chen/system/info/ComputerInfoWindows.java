package chen.system.info;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import chen.judge.Judge;

public class ComputerInfoWindows extends AbstractComputerInfo {
	private static Pattern	macPattern	= Pattern.compile("((([0-9,A-F,a-f]{2}-){5})[0-9,A-F,a-f]{2})");

	public static void main(String[] args) {
		Long start = Long.valueOf(System.currentTimeMillis());
		ComputerInfo info = new ComputerInfo();
		System.out.println("cpu序列号：" + info.getCpuSN());
		for (String mac : info.getMacAddresses()) {
			System.out.println("mac地址：" + mac);
		}
		for (String diskSN : info.getDiskSN()) {
			System.out.println("磁盘序列号：" + diskSN);
		}
		System.out.println("主板序列号：" + info.getBaseBoardSN());
		System.out.println("磁盘大小：" + info.getDiskSize());
		System.out.println("内存大小：" + info.getMemorySize());
		Long end = Long.valueOf(System.currentTimeMillis());
		System.out.println("用时:" + (end.longValue() - start.longValue()));
	}

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

	/**
	 * 获取CPU序列号
	 * @return
	 */
	private String getCpuSNInternal() {
		String serial = "";
		Scanner sc = null;
		try {
			String[] cmdArr = { "wmic", "cpu", "get", "ProcessorId" };
			ProcessBuilder builder = new ProcessBuilder(cmdArr);
			builder.redirectErrorStream(true);
			Process process = builder.start();

			process.getOutputStream().close();
			sc = new Scanner(process.getInputStream());
			sc.next();
			serial = sc.next();
		} catch (Exception localException) {} finally {
			if (sc != null) {
				sc.close();
			}
		}
		return serial;
	}

	/**
	 * 获取物理地址
	 * @return
	 */
	private List<String> getPhysicalAddresses() {
		List<String> resultStr = new ArrayList<String>();
		String line = "ipconfig /all";
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
		BufferedReader br = null;
		try {
			File file = File.createTempFile("disksn", ".vbs");
			file.deleteOnExit();
			FileWriter fw = new FileWriter(file);

			String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\nSet colItems = objWMIService.ExecQuery _ \n   (\"SELECT * FROM Win32_PhysicalMedia\",,48) \nFor Each objItem in colItems \n    Wscript.Echo objItem.SerialNumber \n    exit for  ' do the first cpu only! \nNext \n";

			fw.write(vbs);
			fw.close();
			Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				resultStr.add(line.trim());
			}
		} catch (Exception localException) {
			if (br != null) try {
				br.close();
			} catch (IOException localIOException1) {}
		} finally {
			if (br != null) try {
				br.close();
			} catch (IOException localIOException2) {}

		}
		return resultStr;
	}

	private String getBaseBoardSNInternal() {
		String baseBoardSN = "";
		BufferedReader br = null;
		try {
			File file = File.createTempFile("realhowto", ".vbs");
			file.deleteOnExit();
			FileWriter fw = new FileWriter(file);

			String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\nSet colItems = objWMIService.ExecQuery _ \n   (\"Select * from Win32_BaseBoard\",,48) \nFor Each objItem in colItems \n    Wscript.Echo objItem.SerialNumber \nNext\n";

			fw.write(vbs);
			fw.close();
			Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				baseBoardSN = baseBoardSN + line;
			}
		} catch (Exception localException) {
			if (br != null) try {
				br.close();
			} catch (IOException localIOException1) {}
		} finally {
			if (br != null) try {
				br.close();
			} catch (IOException localIOException2) {}

		}
		return baseBoardSN.trim();
	}
}
