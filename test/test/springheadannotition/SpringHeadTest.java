package test.springheadannotition;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration( "classpath:spring-beantest.xml")
public class SpringHeadTest {

	@Autowired
	private BeanSmaple beanSmaple;
	
	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-beantest.xml");
	
	@Test
	public void think() {
		//属性注入
		BeanSmaple zhoucheng=(BeanSmaple) context.getBean("zhoucheng");
		System.out.println(zhoucheng.getUrl());

	}
	@Test
	public void thank() {

		//构造函数注入
		BeanCase feng=(BeanCase) context.getBean("feng");
		System.out.println(feng.getName()+feng.getJdbcUrl());
	}
	
}
