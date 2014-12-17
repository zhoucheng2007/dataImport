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
		System.out.println("���߳�"+Thread.currentThread().getName()+"��ʼд����");
		while(count<10) {
			synchronized (myStack) {
				if (myStack.size()==3) {
					try {
						//��ʱ�̷߳����ڵȴ��̳߳���
						myStack.wait();
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println(e.getMessage());
					}
				}
				System.out.println("���߳�"+Thread.currentThread().getName()+"��ʼд����"+myStack.push(count));
				count++;
				//�������߳�ִ��Notify�������߳̿��ܻᱻ�ͷų���
				if (myStack.size()==3||count==10) {
					myStack.notify();
				} 
			}
		}
	}
}
