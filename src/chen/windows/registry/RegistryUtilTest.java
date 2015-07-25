package chen.windows.registry;

public class RegistryUtilTest {

	public static void main(String[] args) {
//		testSet();
//		testUserSet();
//		testSetUserRegistry();
//		testGetUserRegistryValue();
		testSetRegistry();
	}
	
	public static void testSet() {
		boolean suc = RegistryUtil.machineSet("test", "yy", "haa");
		System.out.println(suc);
	}
	
	public static void testUserSet() {
		boolean suc = RegistryUtil.userSet("test", "yy", "haa");
		System.out.println(suc);
	}
	
	public static void testSetUserRegistry() {
		System.out.println(RegistryUtil.setUserRegistry("test", "zyc", "zzz"));
	}
	
	public static void testGetUserRegistryValue() {
		System.out.println(RegistryUtil.getUserRegistryValue("test", "zyc"));
	}
	
	public static void testSetRegistry() {
		System.out.println(RegistryUtil.setMachineRegistry("test", "zyc", "aaa"));
	}
}
