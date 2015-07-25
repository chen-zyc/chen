package chen.system;

import java.io.IOException;
import java.util.Scanner;

import chen.windows.cmd.CmdUtil;

public class ProcessUtil {

	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(System.in);

		String path = "D:/参数一键升级/M6-2014-08-27/startup.bat";
		System.out.println("exe " + path);
		Process proc = CmdUtil.batWithWindow(path);
		proc.getErrorStream().close();
		proc.getInputStream().close();
//		CmdUtil.releaseCache(proc);

		int pid = in.nextInt();
		CmdUtil.taskkill(pid + "");
		proc.getOutputStream().write("exit\r\n".getBytes());
		System.out.println("over");
	}
}
