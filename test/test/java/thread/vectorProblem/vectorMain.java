/**
 * 
 */
package test.java.thread.vectorProblem;

import java.util.Stack;

import org.junit.Test;

/**
 * @author feng
 *
 */
public class vectorMain {

	/**
	 * 
	 */
	public vectorMain() {
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * ∑Ω∑®1 ºÃ≥–Thread¿‡
	 * 
	 */
	@Test
	public  void methed1() {
		Stack<Integer> myStack=new Stack<Integer>();
		myStack.setSize(3);
		myStack.removeAllElements();
		Thread writeThread=new Thread(new WriteStack(myStack));
		writeThread.setName("writeThread");
		Thread readThread=new Thread(new ReadStack(myStack));
		writeThread.setName("readThread");
		writeThread.start();
		readThread.start();
	}
	
}
