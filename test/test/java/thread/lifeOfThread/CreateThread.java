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
	 * 方法1 继承Thread类
	 * 
	 */
	@Test
	public  void methed1() {
		System.out.println("主线程开始");
		MyThread th1=new MyThread();
		th1.setName("thread1");
		MyThread th2=new MyThread();
		th2.setName("thread2");
		th1.start();
		th2.start();
		System.out.println("主线程结束");
		
	}
	
	/**
	 * 方法1 实现Runnable接口
	 * 
	 */
	@Test
	public  void methed2() {
		System.out.println("主线程开始");
		MyRunnable run1=new MyRunnable();
		Thread th1=new Thread(run1);
		th1.setName("thread1");
		MyRunnable run2=new MyRunnable();
		Thread th2=new Thread(run2);
		th2.setName("thread2");
		th1.start();
		th2.start();
		System.out.println("主线程结束");
		assertEquals("1","1");
	}
}
