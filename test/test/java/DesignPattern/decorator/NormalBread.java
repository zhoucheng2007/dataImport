package test.java.DesignPattern.decorator;

public class NormalBread implements IBread {

	public NormalBread() {
		// TODO Auto-generated constructor stub
	}

	public void prepair() {
		// TODO Auto-generated method stub
		System.out.println("准备面粉、水及发酵粉......");
	}


	public void kneadFlour() {
		// TODO Auto-generated method stub
		System.out.println("和面......");
	}


	public void steamed() {
		// TODO Auto-generated method stub
		System.out.println("蒸馒头....香喷彭的馒头出炉了！.");
	}

	public void process() {
		// TODO Auto-generated method stub
		prepair();
		kneadFlour();
		steamed();
	}

}
