package chen.task;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class TaskUtil {

	/** timer */
	private Timer					timer	= new Timer(true);
	/** 任务集合，键为任务ID */
	private Map<Integer, TimerTask>	tasks	= new HashMap<Integer, TimerTask>();
	/** 最大任务键值 */
	private Integer					max		= 0;


	/**
	 * 添加任务
	 * 
	 * @param task
	 *            任务
	 * @param delay
	 *            延时
	 * @param period
	 *            间隔
	 * @return 任务ID
	 */
	public Integer add(TimerTask task, long delay, long period) {
		tasks.put(++max, task);
		timer.schedule(task, delay, period);
		return max;
	}

	/**
	 * 设置任务，若taskId处已有任务，则取消旧的任务，添加新的任务
	 * 
	 * @param taskId
	 * @param task
	 * @param delay
	 * @param period
	 */
	public void set(Integer taskId, TimerTask task, long delay, long period) {
		TimerTask old = tasks.get(taskId);
		if (old != null) {
			old.cancel();
			tasks.remove(taskId);
		}
		tasks.put(taskId, task);
		timer.schedule(task, delay, period);
	}

	/**
	 * 取消任务，当所有任务都取消后，销毁Timer
	 * 
	 * @param taskId
	 */
	public void cancel(Integer taskId) {
		TimerTask task = tasks.get(taskId);
		if (task != null) {
			task.cancel();
			tasks.remove(taskId);
		}
	}

	/**
	 * 取消所有任务
	 */
	public void cancelAll() {
		for (Integer key : tasks.keySet()) {
			cancel(key);
		}
	}
}
