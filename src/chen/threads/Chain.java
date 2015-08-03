package threads;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Chain<T> {

	private BlockingQueue<T>	queue;

	public Chain(int size) {
		this.queue = new ArrayBlockingQueue<T>(size);
	}

	public Exception put(T e) {
		try {
			this.queue.put(e);
		} catch (InterruptedException e1) {
			return e1;
		}
		return null;
	}

	public T take() {
		try {
			return this.queue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Exception put(T e, long timeout, TimeUnit unit) {
		try {
			this.queue.offer(e, timeout, unit);
		} catch (InterruptedException e1) {
			return e1;
		}
		return null;
	}

	public T take(long timeout, TimeUnit unit) {
		try {
			return this.queue.poll(timeout, unit);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
