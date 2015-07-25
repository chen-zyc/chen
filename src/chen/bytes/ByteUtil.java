package chen.bytes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import chen.judge.Judge;
import chen.string.StringUtil;

/**
 * 字节辅助类
 * @author zhangyuchen
 *
 */
public class ByteUtil {

	/** 字节单位 */
	public static enum ByteUnit {
		B(0), 
		KB(1), // 2 ^ 10 = 1024
		MB(2), // 2 ^ 20 = 1048576
		GB(3), // 2 ^ 30 = 1073741824
		TB(4)  // 2 ^ 40 = 1099511627776
		;

		/** 是否能够达到该单位级别的标记 */
		public final long	mask;
		/** 单位的顺序，从小到大依次为0,1,2... */
		public final int order;

		private ByteUnit(int order) {
			this.order = order;
			this.mask = 1L << (order * 10);
		}
	}
	
	/**
	 * 字节数和字节单位的封装，如1GB
	 * @author zhangyuchen
	 *
	 */
	public static class Byter{
		public final Long num;
		public final ByteUnit unit;
		
		public Byter(Long num, ByteUnit unit) {
			this.num = num;
			if (unit == null) {
				this.unit = ByteUnit.B;
			} else {
				this.unit = unit;
			}
		}

		@Override
		public String toString() {
			if (num == null) {
				return "0B";
			}
			return num + "" + unit.name();
		}
		
	}
	
	/**
	 * 单位集合，从小到大
	 */
	private static ByteUnit[] units = new ByteUnit[] {ByteUnit.B, ByteUnit.KB, ByteUnit.MB, ByteUnit.GB, ByteUnit.TB};
	
	/*================ 转换相关 =========================*/
	
	/**
	 * 将以src为单位的n转换为以dest为单位 <br>
	 * <pre>
	 * 算法：
	 * 		B(0)	KB(1)	MB(2)	src		
	 * B(0) 0		<<10	<<20			
	 * KB(1)>>10	0		<<10			
	 * MB(2)>>20	>>10	0				
	 * dest									
	 * src的下标减去dest的下标，在乘以10，就是左移或右移的位数，如果小于0则是右移，否则左移
	 * convert(3089L, ByteUnit.B, ByteUnit.KB) => 3KB
	 * convert(5098L, ByteUnit.KB, ByteUnit.MB) => 4M
	 * </pre>
	 * @param n
	 * @param src
	 * @param dest
	 * @return
	 */
	public static long convert(long n, ByteUnit src, ByteUnit dest) {
		if (src == null || dest == null || src.equals(dest)) {
			return n;
		}
		int diff = src.order - dest.order; // 差值
		// 没有差值，不转换
		if (diff == 0) {
			return n;
		}
		// 左移
		if (diff > 0) {
			return n << (diff * 10);
		}
		// 右移
		if (diff < 0) {
			return n >> (-diff * 10);
		}
		return 0;
	}
	
	/**
	 * 将n转换为最合适的单位表示 <br>
	 * convertSuitably(4L << 40, ByteUnit.KB) => 4096TB
	 * @param n
	 * @param currentUnit 当前单位
	 * @return
	 */
	public static String convertSuitably(long n, ByteUnit currentUnit) {
		if (n <= 0 || currentUnit == null) {
			return "0B";
		}
		// 从最大的开始遍历
		for (int end = currentUnit.order, start = units.length - 1; start > end; start--) {
			ByteUnit dest = units[start];
			long convert = convert(n, currentUnit, dest);
			if(convert > 0) {
				return convert + dest.name();
			}
		}
		// 没有找到合适，就只能是自己了
		return n + currentUnit.name();
	}
	
	
	/**
	 * 【重载】将n转换为最合适的单位表示，n的默认单位是B
	 * @param n
	 * @return
	 */
	public static String convertSuitably(long n) {
		return convertSuitably(n, ByteUnit.B);
	}
	
	/** 解析字节表示的正则表达式 */
	private static Pattern byterPat = Pattern.compile("(\\d+)\\s*([a-zA-Z]*)");
	/**
	 * 将字符串转换为字节表示<br>
	 * 2Tb => 2TB，1GB => 1GB，44mb => 44MB，23 KB => 23KB，32B => 32B，45 => 45B，23 ab => 23B，KB => 0B
	 * @param s
	 * @return
	 */
	public static Byter valueOf(String s) {
		if(Judge.empty(s)){
			return new Byter(0L, ByteUnit.B);
		}
		Matcher m = byterPat.matcher(s);
		Long num = 0L;
		ByteUnit unit = null;
		if (m.find()) {
			int count = m.groupCount();
			if (count >= 1) {
				num = StringUtil.convertLong(m.group(1), 0L);
			}
			if (count >= 2) {
				String unitStr = m.group(2).toUpperCase();
				try {
					unit = ByteUnit.valueOf(unitStr);
				} catch (Exception e) {}
			}
		}
		return new Byter(num, unit);
	}
	
	/**
	 * 将Byter转换为字节B
	 * @param byter
	 * @return
	 */
	public static Long bytes(Byter byter) {
		return convert(byter.num, byter.unit, ByteUnit.B);
	}
	
}
