package test.java.DesignPattern.decorator;

public  abstract class AbstractBread implements IBread {

	/**
	 * bread 为什么是final
	 */
	private final IBread bread;
	
	public AbstractBread(IBread bread) {
		// TODO Auto-generated constructor stub
		this.bread=bread;
	}

	public void prepair() {
		// TODO Auto-generated method stub
		//System.out.println("准备面粉、水及发酵粉......");
		this.bread.prepair();
	}


	public void kneadFlour() {
		// TODO Auto-generated method stub
		//System.out.println("和面......");
		this.bread.kneadFlour();
	}


	public void steamed() {
		// TODO Auto-generated method stub
		//System.out.println("蒸馒头....香喷彭的馒头出炉了！.");
		this.bread.steamed();
	}

	public void process() {
		// TODO Auto-generated method stub
		prepair();
		kneadFlour();
		steamed();
	}

}
