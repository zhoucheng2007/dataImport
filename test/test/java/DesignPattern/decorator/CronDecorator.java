package test.java.DesignPattern.decorator;

public class CronDecorator extends AbstractBread {

	public CronDecorator(IBread bread) {
		super(bread);
	}

	public void paint() {
		System.out.println("������ʻƵ���ɫ��......");
	}
	@Override
	public void kneadFlour() {
		this.paint();
		super.kneadFlour();
	}
	
	@Override
	public void process() {
		System.out.println("���׿�ʼ");
		super.process();
	}
	
}
