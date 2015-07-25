package chen.system;

import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

@SuppressWarnings("restriction")
public class SystemUtil {

	/**
	 * 获取内存使用率 <br />
	 * 有一定的偏差，偏高
	 * 
	 * @return Double 或 null
	 */
	public static Double memoryRatio() {
		try {
			OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
			// 总的物理内存+虚拟内存
			long totalvirtualMemory = osmxb.getTotalSwapSpaceSize();
			// 剩余的物理内存
			long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();
			Double compare = (Double) (1 - freePhysicalMemorySize * 1.0 / totalvirtualMemory) * 100;
			return compare;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 空闲物理内存
	 * @return Long 或 null
	 */
	public static Long freeMemorySize() {
		try {
			OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
			// 剩余的物理内存
			return osmxb.getFreePhysicalMemorySize();
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 空闲虚拟机内存
	 * @return
	 */
	public static Long freeMemorySizeRuntime() {
		return Runtime.getRuntime().freeMemory();
	}

}
