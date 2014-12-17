package test.java.DesignPattern.decorator;

public class CronDecorator extends AbstractBread {

	public CronDecorator(IBread bread) {
		super(bread);
	}

	public void paint() {
		System.out.println("添加柠檬黄的着色剂......");
	}
	@Override
	public void kneadFlour() {
		this.paint();
		super.kneadFlour();
	}
	
	@Override
	public void process() {
		System.out.println("玉米开始");
		super.process();
	}
	
}
