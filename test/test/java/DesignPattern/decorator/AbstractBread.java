package test.java.DesignPattern.decorator;

public  abstract class AbstractBread implements IBread {

	/**
	 * bread Ϊʲô��final
	 */
	private final IBread bread;
	
	public AbstractBread(IBread bread) {
		// TODO Auto-generated constructor stub
		this.bread=bread;
	}

	public void prepair() {
		// TODO Auto-generated method stub
		//System.out.println("׼����ۡ�ˮ�����ͷ�......");
		this.bread.prepair();
	}


	public void kneadFlour() {
		// TODO Auto-generated method stub
		//System.out.println("����......");
		this.bread.kneadFlour();
	}


	public void steamed() {
		// TODO Auto-generated method stub
		//System.out.println("����ͷ....���������ͷ��¯�ˣ�.");
		this.bread.steamed();
	}

	public void process() {
		// TODO Auto-generated method stub
		prepair();
		kneadFlour();
		steamed();
	}

}
