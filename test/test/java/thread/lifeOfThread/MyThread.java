package test.java.thread.lifeOfThread;

public class MyThread extends Thread {

	@Override
	public void run() {
		System.out.println("Æô¶¯Ïß³Ì"+Thread.currentThread().getName());
	}
}
