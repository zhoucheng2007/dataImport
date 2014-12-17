package test.java.thread.vectorProblem;

import java.util.Stack;

public class ReadStack implements Runnable {

	Stack<Integer> myStack;
	int count=0;
	public ReadStack(Stack<Integer> myStack) {
		this.myStack=myStack;
	}
	
	public void run() {
		try {
			Thread.sleep(50);
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("子线程"+Thread.currentThread().getName()+"开始读数据");
		while(count<10) {
			synchronized (myStack) {
				if (myStack.empty()) {
					try {
						//此时线程放至在等待线程池中
						myStack.wait();
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println(e.getMessage());
					}
				}
				System.out.println("子线程"+Thread.currentThread().getName()+"开始读数据"+myStack.pop());
				count++;
				//当另外线程执行Notify方法后，线程可能会被释放出来
				if (myStack.empty()||count==10) {
					myStack.notify();
				} 
			}
		}
	}
}
