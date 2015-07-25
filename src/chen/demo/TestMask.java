package chen.demo;

import org.junit.Assert;
import org.junit.Test;


public class TestMask {

	public static void main(String[] args) {
		new TestMask().test();
	}
	
	@Test
	public void test() {
		FileManager fm = new FileManager();
		assertFm(fm, 0, false, false);
		
		fm.setCanRead(true);
		assertFm(fm, 1, true, false);
		
		fm.setCanWrite(true);
		assertFm(fm, 0x11, true, true);
		
		fm.setCanRead(false);
		assertFm(fm, 0x10, false, true);
		
		fm.setCanWrite(false);
		assertFm(fm, 0, false, false);
		
		fm.setCanRead(false);
		assertFm(fm, 0, false, false);
		
		fm.reverse();
		assertFm(fm, 0x11, true, true);
		
		fm.setCanRead(false);
		fm.reverse();
		assertFm(fm, 0x01, true, false);
		
	}
	
	private void assertFm(FileManager fm, int flag, boolean canRead, boolean canWrite) {
		Assert.assertEquals(fm.mPrivateFlags, flag);
		Assert.assertEquals(fm.canRead(), canRead);
		Assert.assertEquals(fm.canWrite(), canWrite);
	}

	class FileManager {
		private static final int	CAN_READ_MASK	= 0x00000001;
		private static final int	CAN_WRITE_MASK	= 0x00000010;

		private int mPrivateFlags;

		public void setCanRead(boolean canRead) {
			if (canRead) {
				mPrivateFlags |= CAN_READ_MASK; // 最后一位置为1
			} else {
				mPrivateFlags &= ~CAN_READ_MASK; // 最后一位置为0
			}
		}
		
		public boolean canRead() {
			return (mPrivateFlags & CAN_READ_MASK) == CAN_READ_MASK;
		}
		
		public void setCanWrite(boolean canWrite) {
			if (canWrite) {
				mPrivateFlags |= CAN_WRITE_MASK;
			} else {
				mPrivateFlags &= ~CAN_WRITE_MASK;
			}
		}
		
		public boolean canWrite() {
			return (mPrivateFlags & CAN_WRITE_MASK) == CAN_WRITE_MASK;
		}
		
		/**
		 * 反转，将可读变为不可读，不可读变为可读，写同理
		 */
		public void reverse() {
			mPrivateFlags ^= CAN_READ_MASK;
			mPrivateFlags ^= CAN_WRITE_MASK;
		}

		@Override
		public String toString() {
			return "FileManager [mPrivateFlags=" + Integer.toHexString(mPrivateFlags) + "]," + "canRead:" + canRead() + ", canWrite:" + canWrite();
		}
		
	}
}
