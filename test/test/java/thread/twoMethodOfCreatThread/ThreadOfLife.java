/**
 * 
 */
package test.java.thread.twoMethodOfCreatThread;

import org.junit.Test;

/**
 * @author feng
 *
 */
public class ThreadOfLife {

	/**
	 * 
	 */
	public ThreadOfLife() {
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * ����1 �̳�Thread��
	 * 
	 */
	@Test
	public  void methed1() {
		MyThread th1=new MyThread();

		th1.start();
		System.out.println("shuc");
	}
	
}
