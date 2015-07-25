package chen.system.info;

import com.sun.management.OperatingSystemMXBean;

import java.io.File;
import java.lang.management.ManagementFactory;

@SuppressWarnings("restriction")
public abstract class AbstractComputerInfo {
	public static final String		WINDOWS					= "WINDOWS";
	public static final String		LINUX					= "LINUX";
	public static final String		AIX						= "AIX";
	public static final String		DEFAULT_CPU_SN			= "0000000000000000";
	public static final String[]	DEFAULT_MAC_ADDRESS		= { "00:00:00:00:00:00" };

	public static final String[]	DEFAULT_DISK_SN			= { "00000000" };
	public static final String		DEFAULT_BASE_BOARD_SN	= "00000000000";
	public static final int			DEFAULT_DISK_SIZE		= 0;
	public static final int			DEFAULT_MEMORY_SIZE		= 0;

	public abstract String getCpuSN();

	public abstract String[] getMacAddresses();

	public abstract String[] getDiskSN();

	public abstract String getBaseBoardSN();

	public int getDiskSize() {
		return getDiskSizeInternal();
	}

	public int getMemorySize() {
		return getMemorySizeInternal();
	}

	private int getDiskSizeInternal() {
		int diskSize = 0;
		try {
			File[] roots = File.listRoots();
			for (File file : roots)
				diskSize = (int) (diskSize + file.getTotalSpace() / 1024L / 1024L / 1024L);
		} catch (Exception localException) {}
		return diskSize;
	}

	private int getMemorySizeInternal() {
		Long memorySize = new Long(0L);
		try {
			OperatingSystemMXBean osmb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
			memorySize = Long.valueOf(osmb.getTotalPhysicalMemorySize() / 1024L / 1024L);
		} catch (Exception localException) {}
		return memorySize.intValue();
	}
}
