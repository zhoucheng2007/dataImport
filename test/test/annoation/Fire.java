package test.annoation;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class Fire {
	public static void processInjectionBasedOnCurrentContext(Object target) {
		Assert.notNull(target, "Target object must not be null");
		WebApplicationContext cc = ContextLoader.getCurrentWebApplicationContext();
		if (cc != null) {
			AutowiredAnnotationBeanPostProcessor bpp = new AutowiredAnnotationBeanPostProcessor();
			bpp.setBeanFactory(cc.getAutowireCapableBeanFactory());
			bpp.processInjection(target);
		}
		else {
			
		}
	}
}
