package com.shop.base.listener;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ProxyApplicationListener implements ServletContextListener {
	private static Log log = LogFactory.getLog(ProxyApplicationListener.class);
    public List getListener(){  
		Properties props = new Properties();
	    InputStream in = null;
	    List list=null;
		try {
			 in=this.getClass().getClassLoader().getResourceAsStream("listenersetting.properties");
			 props.load(in);
			 Enumeration en = props.propertyNames();
			 list=new ArrayList();
		     while (en.hasMoreElements()) {
		          String key = (String) en.nextElement();
		          if(key.endsWith("ListenerType")){
		            	String property = props.getProperty (key);
		            	if("ServletContextListener".equals(property)){
		            		String classKey=key.replace("ListenerType", "className");
		            		String classproperty = props.getProperty (classKey);
		            		Class listener  = Class.forName(classproperty);
							BaseApplicationListener obj= (BaseApplicationListener) listener.newInstance();
							list.add(obj); 
		            	}      
				   }
		    }
		} 
		catch (ClassNotFoundException e) {
			log.debug("ClassNotFoundException="+e.getMessage());
		}
		catch (InstantiationException e) {
			log.debug("InstantiationException="+e.getMessage());
		}
		catch (IllegalAccessException e) {
			log.debug("IllegalAccessException="+e.getMessage());
		}catch (IOException e) {
			log.debug("IOException="+e.getMessage());
		}
        finally{
        	 if(null != in){
        	      try{
        	           in.close();
        	      }
        	      catch (IOException e) {
        				log.debug("IOException="+e.getMessage());
        		 }
        	}
        }
	    return list;
	}
	public void contextDestroyed(ServletContextEvent arg0) {	
		List list=getListener();
		BaseApplicationListener base;
        if(null!=list&&list.size()>0){
        	for(int i=0;i<list.size();i++){
        		Object obj=list.get(i);
        		base=(BaseApplicationListener)obj;
        		base.contextDestroyed(arg0);
        	}
        }
	}

	public void contextInitialized(ServletContextEvent arg0) {
		List list=getListener();
		BaseApplicationListener base;
        if(null!=list&&list.size()>0){
        	for(int i=0;i<list.size();i++){
        		Object obj=list.get(i);
        		base=(BaseApplicationListener)obj;
        		base.contextInitialized(arg0);
        	}
        }
		 
	}
}
