package test.java.thread.twoMethodOfCreatThread;

public class MyThread extends Thread {

	@Override
	public void run() {
		System.out.println("�����߳�"+Thread.currentThread().getName());
	}
}
