package chen.windows.registry;

import java.util.prefs.Preferences;

import ca.beq.util.win32.registry.RegistryKey;
import ca.beq.util.win32.registry.RegistryValue;
import ca.beq.util.win32.registry.RootKey;

/**
 * 首选项工具类
 * @author zhangyuchen
 *
 */
public class RegistryUtil {

	/*=============== java自带Preferences读写注册表 ===================
	 * 注意，如果写入不了，有可能是权限问题
	 * 机器上还需要安装了JDK */
	
	/**
	 * 向HKEY_LOCAL_MACHINE\Software\JavaSoft\prefs下添加节点，并添加键值对
	 * @param node
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean machineSet(String node, String key, String value) {
		Preferences pre = Preferences.systemRoot().node(node);
		pre.put(key, value);
		return machineGet(node, key, null) != null;
	}

	/**
	 * 从HKEY_LOCAL_MACHINE\Software\JavaSoft\prefs中获取值
	 * @param node
	 * @param key
	 * @param def 默认值
	 * @return
	 */
	public static String machineGet(String node, String key, String def) {
		Preferences pre = Preferences.systemRoot().node(node);
		return pre.get(key, def);
	}
	
	/**
	 * 向HKEY_LOCAL_USER\Software\JavaSoft\prefs下添加节点，并添加键值对<br>
	 * 这个不需要管理员权限好像也可以写进去
	 * @param node
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean userSet(String node, String key, String value) {
		Preferences pre = Preferences.userRoot().node(node);
		pre.put(key, value);
		return userGet(node, key, null) != null;
	}

	/**
	 * 从HKEY_LOCAL_USER\Software\JavaSoft\prefs中获取值
	 * @param node
	 * @param key
	 * @param def
	 * @return
	 */
	public static String userGet(String node, String key, String def) {
		Preferences pre = Preferences.userRoot().node(node);
		return pre.get(key, def);
	}
	
	
	
	/*=============== 使用jRegistryKey.jar ===================
	 * 注意，需要将jRegistryKey.dll放入到jre/bin/下 */
	
	/**
	 * 获取用户注册表信息
	 * @param node
	 * @param key
	 * @return
	 */
	public static String getUserRegistryValue(String node, String key) {
		RegistryKey registryKey = new RegistryKey(RootKey.HKEY_CURRENT_USER, "SYSTEM\\CurrentControlSet\\Services\\" + node);
		if (registryKey.exists() && registryKey.hasValue(key)) {
			RegistryValue value = registryKey.getValue(key);
			return value.getStringValue();
		}
		return null;
	}
	
	/**
	 * 设置用户注册表信息<br>
	 * 这个不需要管理员权限也可以写进去
	 * @param node
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean setUserRegistry(String node, String key, String value) {
		try {
			RegistryKey registryKey = new RegistryKey(RootKey.HKEY_CURRENT_USER, "SYSTEM\\CurrentControlSet\\Services\\" + node);
			if (!registryKey.exists()) {
				registryKey.create();
			}
			registryKey.setValue(new RegistryValue(key, value));
			return getUserRegistryValue(node, key) != null;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 获取系统注册表信息
	 * @param node
	 * @param key
	 * @return
	 */
	public static String getMachineRegistryValue(String node, String key) {
		RegistryKey registryKey = new RegistryKey(RootKey.HKEY_LOCAL_MACHINE, "SYSTEM\\CurrentControlSet\\Services\\" + node);
		if (registryKey.exists() && registryKey.hasValue(key)) {
			RegistryValue value = registryKey.getValue(key);
			return value.getStringValue();
		}
		return null;
	}
	
	/**
	 * 设置系统注册表信息<br>
	 * 这个需要管理员权限
	 * @param node
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean setMachineRegistry(String node, String key, String value) {
		try {
			RegistryKey registryKey = new RegistryKey(RootKey.HKEY_LOCAL_MACHINE, "SYSTEM\\CurrentControlSet\\Services\\" + node);
			if (!registryKey.exists()) {
				registryKey.create();
			}
			registryKey.setValue(new RegistryValue(key, value));
			return getMachineRegistryValue(node, key) != null;
		} catch (Exception e) {
			return false;
		}
	}
}
