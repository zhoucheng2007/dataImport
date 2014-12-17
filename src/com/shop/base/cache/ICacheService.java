package com.shop.base.cache;

import java.util.Map;

import com.inspur.hsf.service.rpc.notify.NotificationListener;

/**
 * 缓存服务器接口类
 * 
 * @author sunfs
 * 
 */
public interface ICacheService {
	
	public static final String CACHE_DELETE_FLAG="v6delete"; //缓存删除标志
	
	/**
	 * 注册字典缓存监听器
	 * @param listener
	 */
	public void registerListener(String cacheKey,NotificationListener listener) ;

	/**
	 * 注销字典缓存监听器
	 * @param listener
	 */
	public void unRegeisterListener(String cacheKey,NotificationListener listener) ;

	/**
	 * 更新字典缓存数据
	 * @param map
	 */
	public void updateCache(String cacheKey,Map dataMap) ;
	
	

	/**
	 * 取字典初始化数据
	 * @param map
	 */
	public Map getInitData(String cacheKey) ;
	
	/**
	 * 取list类型的字典数据
	 * 如下拉列表
	 * @param cacheKey 下拉列表缓存唯一标识
	 * @param listKey  单个下拉列表的标识
	 * @return
	 */
	public  Map getInitListData(String cacheKey,String listKey);
	
	public  Map getInitObjectData(String cacheKey,String listKey);

	public Map getInitData(String cacheKey, Object conditionObj);

	

	//public void updateListCache(String cacheKey, String listKey, Map map);
	
	
}
