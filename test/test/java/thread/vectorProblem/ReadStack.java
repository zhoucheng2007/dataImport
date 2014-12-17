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
		System.out.println("���߳�"+Thread.currentThread().getName()+"��ʼ������");
		while(count<10) {
			synchronized (myStack) {
				if (myStack.empty()) {
					try {
						//��ʱ�̷߳����ڵȴ��̳߳���
						myStack.wait();
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println(e.getMessage());
					}
				}
				System.out.println("���߳�"+Thread.currentThread().getName()+"��ʼ������"+myStack.pop());
				count++;
				//�������߳�ִ��Notify�������߳̿��ܻᱻ�ͷų���
				if (myStack.empty()||count==10) {
					myStack.notify();
				} 
			}
		}
	}
}
