package com.shop.base.alias;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.loushang.waf.ComponentFactory;

/**
 * 别名提供者类
 * @author weijingjie
 * 2013-12-30
 */
public class BaseAliasProvider {
	
	private static Log log=LogFactory.getLog(BaseAliasProvider.class);
	private static Map<String, String> aliasMap;
	
	/**
	 * 获取别名值，以便对不同项目展示不同信息
	 * @param name 如果数据库或者缓存中没有记录时，直接在页面上展示的内容
	 * @param id   对应BASE_ALIAS的ID字段值
	 * @return
	 */
	public static String getAlias(String name,String id){	
		String rtnAlias="";
		if(null!=aliasMap){
			rtnAlias=aliasMap.get(id);
			if(null==rtnAlias){
				rtnAlias=name;
				if(log.isDebugEnabled()){
					log.debug(id+" 的别名未找到，返回默认name");
				}
			}
		}else{
			rtnAlias=name;
			if(log.isDebugEnabled()){
				log.debug(id+" 的别名未找到，返回默认name");
			}
		}
		return rtnAlias;
	}

	/**
	 * 初始化缓存
	 */
	public static void initialAlias(){
		Object obj=ComponentFactory.getBean("baseAliasDomain");
		if(null!=obj){
			IBaseAliasDomain bean=(IBaseAliasDomain)obj;
			aliasMap=new HashMap<String, String>(bean.initialAliasCache());	
		}else{
			System.out.println("无法获取baseAliasDomain,别名缓存初始化失败");
		}
		
	}
}
