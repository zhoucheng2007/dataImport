package test.java.thread.vectorProblem;

import java.util.Stack;

public class WriteStack implements Runnable {

	Stack<Integer> myStack;
	int count=0;
	public WriteStack(Stack<Integer> myStack) {
		this.myStack=myStack;
	}
	
	public void run() {
		int count = 0;
		System.out.println("子线程"+Thread.currentThread().getName()+"开始写数据");
		while(count<10) {
			synchronized (myStack) {
				if (myStack.size()==3) {
					try {
						//此时线程放至在等待线程池中
						myStack.wait();
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println(e.getMessage());
					}
				}
				System.out.println("子线程"+Thread.currentThread().getName()+"开始写数据"+myStack.push(count));
				count++;
				//当另外线程执行Notify方法后，线程可能会被释放出来
				if (myStack.size()==3||count==10) {
					myStack.notify();
				} 
			}
		}
	}
}
