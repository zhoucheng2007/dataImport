package test.java.thread.lifeOfThread;

public class MyRunnable  implements Runnable {

	@Override
	public void run() {
		System.out.println("Æô¶¯Ïß³Ì"+Thread.currentThread().getName());
	}
}
