package com.shop.base.alias;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 别名监听器，只用来实现启动加载别名缓存
 * @author weijingjie
 * 2013-12-30
 */
public class BaseAliasListener implements ServletContextListener{

	public void contextDestroyed(ServletContextEvent arg0) {

	}

	public void contextInitialized(ServletContextEvent arg0) {
			//加载别名缓存
			BaseAliasProvider.initialAlias();
			System.out.println("BASE_ALIAS cache has initialized");
	}
}
