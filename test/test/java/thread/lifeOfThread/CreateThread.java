/**
 * 
 */
package test.java.thread.lifeOfThread;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author feng
 *
 */
public class CreateThread {

	/**
	 * 
	 */
	public CreateThread() {
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * ����1 �̳�Thread��
	 * 
	 */
	@Test
	public  void methed1() {
		System.out.println("���߳̿�ʼ");
		MyThread th1=new MyThread();
		th1.setName("thread1");
		MyThread th2=new MyThread();
		th2.setName("thread2");
		th1.start();
		th2.start();
		System.out.println("���߳̽���");
		
	}
	
	/**
	 * ����1 ʵ��Runnable�ӿ�
	 * 
	 */
	@Test
	public  void methed2() {
		System.out.println("���߳̿�ʼ");
		MyRunnable run1=new MyRunnable();
		Thread th1=new Thread(run1);
		th1.setName("thread1");
		MyRunnable run2=new MyRunnable();
		Thread th2=new Thread(run2);
		th2.setName("thread2");
		th1.start();
		th2.start();
		System.out.println("���߳̽���");
		assertEquals("1","1");
	}
}
