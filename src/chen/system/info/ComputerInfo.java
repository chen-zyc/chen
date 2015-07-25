package chen.system.info;

public class ComputerInfo extends AbstractComputerInfo {
	private static String			systemName	= System.getProperty("os.name");
	private AbstractComputerInfo	computerInfo;

	public ComputerInfo() {
		if (systemName.toUpperCase().indexOf("WINDOWS") > -1)
			this.computerInfo = new ComputerInfoWindows();
		else if (systemName.toUpperCase().indexOf("AIX") > -1)
			this.computerInfo = new ComputerInfoAix();
		else if (systemName.toUpperCase().indexOf("LINUX") > -1)
			this.computerInfo = new ComputerInfoLinux();
		else
			throw new RuntimeException("目前暂不支持在【" + systemName + "】操作系统上进行许可授权!");
	}

	public String getSystemName() {
		return systemName;
	}

	public String getCpuSN() {
		return this.computerInfo.getCpuSN();
	}

	public String[] getMacAddresses() {
		return this.computerInfo.getMacAddresses();
	}

	public String[] getDiskSN() {
		return this.computerInfo.getDiskSN();
	}

	public String getBaseBoardSN() {
		return this.computerInfo.getBaseBoardSN();
	}

	public int getDiskSize() {
		return this.computerInfo.getDiskSize();
	}

	public int getMemorySize() {
		return this.computerInfo.getMemorySize();
	}
}
