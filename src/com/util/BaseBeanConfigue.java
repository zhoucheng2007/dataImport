package com.util;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class BaseBeanConfigue implements IBeanConfigue {

    private static Log log;
    private String mappingResources[];
	
	
    public String[] getMappingResources()
    {
        return mappingResources;
    }
    
    public void setMappingResources(String strings[])
    {
        mappingResources = strings;
    }


	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadXmlConfig(XmlWebApplicationContext ctx) {
        String pre = "";
        if(ctx instanceof WebApplicationContext)
            pre = "/WEB-INF";
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)ctx.getBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        beanDefinitionReader.setResourceLoader(ctx);
        beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(ctx));
        for(int i = 0; i < mappingResources.length; i++)
        {
             try
            {
                String res = getMappingResources()[i];
                if(!res.startsWith("/"))
                    res = "/" + res;
                if(!res.startsWith(pre))
                    res = pre + res;
                beanDefinitionReader.loadBeanDefinitions(ctx.getResource(res));
            }
            catch(Exception e)
            {         
            	}
            }

	}

}
