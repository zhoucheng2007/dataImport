/**
 * 
 */
package com.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * @author feng
 *
 */
public interface IBeanConfigue extends InitializingBean{
	
    public abstract void loadXmlConfig(XmlWebApplicationContext xmlwebapplicationcontext);

}
