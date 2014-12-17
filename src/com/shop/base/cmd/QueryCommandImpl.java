package com.shop.base.cmd;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.loushang.waf.mvc.QueryCommandSupportWithInitSmart;
import org.loushang.waf.mvc.QueryHelper;
import org.loushang.waf.mvc.ServletCommandContext;

import com.shop.base.cache.CacheClient;

/**
 * 翻页初始化查询 Command 基类 
 * @author V6
 */
public abstract class QueryCommandImpl extends QueryCommandSupportWithInitSmart {

	/**
	 * 覆写父类org.loushang.waf.mvc.QueryCommandSupportWithInitSmart中的doQuery方法
	 * V6中可以在此方法实现自己的扩展 
	 */
	public String doQuery(HttpServletRequest request,
			HttpServletResponse response, QueryHelper queryHelper, Map map)
			throws Exception {
		return query(request, response, queryHelper, map);
	}
	
	public abstract String query(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse,
			QueryHelper paramQueryHelper, Map paramMap) throws Exception;
	

	/**
	 * 设置缓存字典
	 * @param dictName  字典名
	 * @param cacheKey  缓存主键
	 */
	public void setCache(String dictName,String cacheKey){
		HttpServletRequest req = ServletCommandContext.getRequest();
		Map cacheMap = CacheClient.getCache(cacheKey);
		req.setAttribute(dictName, cacheMap);
	}
	

	/**
	 * 设置下拉列表缓存
	 * @param dictName
	 * @param cacheKey
	 * @param conditionObj 条件
	 */
	public void setListCache(String dictName,String cacheKey,Object conditionObj){
		HttpServletRequest req = ServletCommandContext.getRequest();
		Map cacheMap = CacheClient.getListCacheValue(cacheKey, conditionObj);
		req.setAttribute(dictName, cacheMap);
	}
	
}
