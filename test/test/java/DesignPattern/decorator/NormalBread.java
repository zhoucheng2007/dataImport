package test.java.DesignPattern.decorator;

public class NormalBread implements IBread {

	public NormalBread() {
		// TODO Auto-generated constructor stub
	}

	public void prepair() {
		// TODO Auto-generated method stub
		System.out.println("׼����ۡ�ˮ�����ͷ�......");
	}


	public void kneadFlour() {
		// TODO Auto-generated method stub
		System.out.println("����......");
	}


	public void steamed() {
		// TODO Auto-generated method stub
		System.out.println("����ͷ....���������ͷ��¯�ˣ�.");
	}

	public void process() {
		// TODO Auto-generated method stub
		prepair();
		kneadFlour();
		steamed();
	}

}
