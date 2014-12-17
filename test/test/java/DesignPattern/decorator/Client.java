package test.java.DesignPattern.decorator;

public class Client {

	public Client() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("\n==开始装饰馒头！！！！！！'");
		IBread normalBread=new NormalBread();
		
		normalBread=new CronDecorator(normalBread);
		
		normalBread=new SweetDecorator(normalBread);
		

		
		normalBread.process();
		
		System.out.println("\n==开始装饰结束！！！！！！'");
	}

}
