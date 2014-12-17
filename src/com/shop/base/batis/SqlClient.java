package com.shop.base.batis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.loushang.waf.ComponentFactory;

public class SqlClient {
	
	private static Log log = LogFactory.getLog(SqlClient.class);
	
	private static IBatisService batisService=null;
	
	/***
	 * 通用查询
	 * @param sql 查询的完整sql语句
	 * @return 结果集，list中的每个元素是一个map,map中以大写的列名为map的键，列的值为map的值
	 */
	public static List commonSelect(String sql){
		return getBatisService().commonSelect(sql);
	}
	
	
	public static boolean existsResult(String sql){
		return commonSelect(sql).size()>0;
	}
	
	
	//取得缓存服务
	private static IBatisService getBatisService(){
		if(batisService==null){
			Object bean = ComponentFactory.getBean("batisService");
			if(bean==null){
				log.error("batisService bean is null!");
				//System.err.println("v6CacheService bean is null!");
			}
			batisService=(IBatisService) bean;
		}
		return batisService;
	}


}
