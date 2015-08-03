package threads;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadBoss {

	private ExecutorService								pool;
	private ConcurrentLinkedQueue<Chain<BossMessage>>	readChains	= new ConcurrentLinkedQueue<Chain<BossMessage>>();
	private ConcurrentLinkedQueue<Chain<BossMessage>>	writeChains	= new ConcurrentLinkedQueue<Chain<BossMessage>>();

	public ThreadBoss() {
		this.pool = Executors.newCachedThreadPool();
	}

	/**
	 * 添加线程，read，write是相对于ThreadBoss来说的。
	 * 
	 * @param command
	 * @param readChain
	 * @param writeChain
	 */
	public void add(Runnable command, Chain<BossMessage> readChain, Chain<BossMessage> writeChain) {
		this.pool.execute(command);
		if (readChain != null) {
			readChains.add(readChain);
		}
		if (writeChain != null) {
			writeChains.add(writeChain);
		}
	}

	public void monitor() {
		new Thread() {

			@Override
			public void run() {
				while (true) {
					for (Chain<BossMessage> c : readChains) {
						BossMessage msg = c.take(100, TimeUnit.MILLISECONDS);
						if (msg.over) {
							readChains.remove(c);
						}
						if (msg != null && !empty(msg.toBoos)) {
							System.out.println(msg.toBoos);
						}
					}
					if (readChains.size() == 0) {
						break;
					}
				}
				System.out.println("it is over.");
			}

		}.start();

	}

	public Chain<BossMessage> newChain(int size) {
		return new Chain<BossMessage>(size);
	}

	private boolean empty(String s) {
		return s == null || s.length() == 0;
	}

}
