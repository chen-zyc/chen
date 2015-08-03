package threads.test;

import threads.BossMessage;
import threads.Chain;
import threads.ThreadBoss;

public class ThreadBossTest {
	
	public static void main(String[] args) {
		ThreadBossTest t = new ThreadBossTest();
		t.testChain();
	}

	private class T extends Thread {

		private Chain<BossMessage>	chain;

		public T(Chain<BossMessage> c) {
			this.chain = c;
		}

		@Override
		public void run() {
			for (int i = 0; i < 10; i++) {
				BossMessage msg = new BossMessage();
				msg.toBoos = this.getName() + ": " + i;
				this.chain.put(msg);
			}
			
			BossMessage msg = new BossMessage();
			msg.over = true;
			this.chain.put(msg);
		}

	}

	public void testChain() {
		ThreadBoss boss = new ThreadBoss();
		
		for (int i = 0; i < 10; i++) {
			Chain<BossMessage> c = boss.newChain(5);
			T t = new T(c);
			boss.add(t, c, null);
		}
		boss.monitor();
	}

}
