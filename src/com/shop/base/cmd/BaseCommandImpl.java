package com.shop.base.cmd;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.loushang.waf.mvc.MultiCommandSmart;
import org.loushang.waf.mvc.ServletCommandContext;

import com.shop.base.cache.CacheClient;

/**
 * @author v3
 */
public class BaseCommandImpl extends MultiCommandSmart {
	
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
	 * @param listKey
	 */
	public void setListCache(String dictName,String cacheKey,String listKey){
		HttpServletRequest req = ServletCommandContext.getRequest();
		Map cacheMap = CacheClient.getListCacheValue(cacheKey, listKey);
		req.setAttribute(dictName, cacheMap);
	}
	
	/**
	 * 更新缓存
	 * @param cacheKey
	 * @param id
	 * @param value
	 */
	public void updateCache(String cacheKey,String id,String value){
		CacheClient.updateCache(cacheKey, id, value);
	}

	/**
	 * 更新下拉列表缓存
	 * @param cacheKey
	 * @param id
	 * @param value
	 */
	public void updateListCache(String cacheKey,String listKey,String id,String value){
		CacheClient.updateCache(cacheKey+"/"+listKey, id, value);
	}
	
	
	/**
	 * 删除缓存
	 * @param cacheKey
	 * @param id
	 * @param value
	 */
	public void deleteCache(String cacheKey,String id){
		CacheClient.deleteCache(cacheKey, id);
	}
	
	/**
	 * 删除下拉列表缓存
	 * @param cacheKey
	 * @param id
	 * @param value
	 */
	public void deleteListCache(String cacheKey,String listKey,String id){
		CacheClient.deleteCache(cacheKey+"/"+listKey,id);
	}
	

}
