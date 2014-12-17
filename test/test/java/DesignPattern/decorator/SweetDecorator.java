package test.java.DesignPattern.decorator;

public class SweetDecorator extends AbstractBread {

	public SweetDecorator(IBread bread) {
		super(bread);
	}

	public void paint() {
		System.out.println("Ìí¼ÓÌğÃÛËØ......");
	}
	@Override
	public void kneadFlour() {
		this.paint();
		super.kneadFlour();
	}

	@Override
	public void process() {
		System.out.println("ÌğÃÛËØ¿ªÊ¼");
		super.process();
	}
	
	
}
