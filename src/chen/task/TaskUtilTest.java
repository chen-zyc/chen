package chen.task;

import java.util.TimerTask;

public class TaskUtilTest {

	public static void main(String[] args) throws InterruptedException {
//		new TaskUtilTest().testAddCancel();
		testSet();
	}

	public void testAddCancel() {
		
		TimerTask task1 = new MyTask("task1");

		TimerTask task2 = new MyTask("task2");

		TimerTask task3 = new MyTask("task3");

		TaskUtil util = new TaskUtil();
		Integer task1Id = util.add(task1, 0, 1000);
		Integer task2Id = util.add(task2, 1000, 1500);
		System.out.println(task1Id + "," + task2Id);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		util.cancel(task1Id);

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		util.cancel(task2Id);

		Integer task3Id = util.add(task3, 1000, 1000);

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		util.cancel(task3Id);
		
		util.cancelAll();
	}

	public static void testSet() throws InterruptedException {
		TimerTask t1 = new TimerTask() {

			@Override
			public void run() {
				System.out.println("task 1");
			}
		};
		TimerTask t2 = new TimerTask() {

			@Override
			public void run() {
				System.out.println("task 2");
			}
		};
		
		TaskUtil util = new TaskUtil();
		int id1 = util.add(t1, 0, 1000);
		Thread.sleep(5000);
		util.set(id1, t2, 1000, 2000);
		
		Thread.sleep(5000);
		util.cancel(id1);
	}
	
	class MyTask extends TimerTask {

		private int	i	= 1;
		private String name;
		
		public MyTask(String name) {
			this.name = name;
		}

		@Override
		public void run() {
			StringBuilder str = new StringBuilder(name);
			for (int j = 0; j < i; j++) {
				str.append(".");
			}
			i++;
			if (i > 3) i = 0;
			System.out.println(str);
		}
		
	}

}
