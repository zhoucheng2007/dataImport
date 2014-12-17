/**
 * 
 */
package com.shop.base.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.loushang.demo.dept.data.Dept;
import org.loushang.waf.ComponentFactory;

import com.inspur.hsf.service.rpc.notify.Notification;
import com.inspur.hsf.service.rpc.notify.NotificationListener;
import com.inspur.hsf.service.rpc.notify.SimpleNotification;

/**
 * @title:缓存工具客户端类
 * @description:单例，注册前端缓存监听内容，推送缓存内容至前端组件
 * @author sunfs
 * @date:2012-06-05
 */
public class CacheClient {
	
	private static ICacheService cacheService=null;
	
//	private static CacheClient cacheClientUtil=null;
	
	private static Map<String,Map> cacheDataMap = new HashMap<String,Map>();
	
	private static Log log = LogFactory.getLog(CacheClient.class);
	
	
	//本地注册的listenerMap
	private static Map<String,NotificationListener> listenerMap = new HashMap<String,NotificationListener>(); //监听器注册中心
	
	public CacheClient(){
		
	}

	
	/**
	 * 1、注册字典数据的sqlId,该sqlId定义在CacheServerSqlMap.xml文件中
	 * 这是扩展缓存定义的第一步
	 */
	private  static void init(){
		log.debug("CacheClient.init!");
		//构造类的唯一实例
//		if(cacheClientUtil==null){
//			cacheClientUtil=new CacheClient();
//		}
		
	}
	
	
	
	/**
	 * 注册缓存监听器
	 * @param cacheKey
	 */
	private static void registerCache(final String cacheKey) {
		init();
//		NotificationListener listener2 = new NotificationListener() {
//		public void handleNotification(Notification notification) {
//			HashMap newMap = (HashMap) ((SimpleNotification) notification)
//					.getData();
//			Map dataMap = cacheDataMap.get(cacheKey);
//			//如果该字典数据未被缓存，则不处理
//			if(dataMap!=null){
//				updateCacheMap(dataMap, newMap);
//			}
//		}
//
//		};
		NotificationListener listener2= new CacheClient().new CacheNotificationListener(cacheKey);
		getCacheService().registerListener(cacheKey,listener2);
		//记录该listener,应用关闭时移除
		listenerMap.put(cacheKey, listener2);
	}
	
	//清空缓存数据，用于bases的意外中断
	public static void clearCache(){
		cacheDataMap.clear();
	}
	
	/**
	 * 注册缓存监听器
	 * @param cacheKey
	 */
	private static void registerObjectCache(final String cacheKey) {
		init();
		NotificationListener listener2 = new NotificationListener() {
		public void handleNotification(Notification notification) {
			HashMap newMap = (HashMap) ((SimpleNotification) notification)
					.getData();
			Map dataMap = cacheDataMap.get(cacheKey);
			//如果该字典数据未被缓存，则不处理
			if(dataMap!=null){
				updateCacheMap(dataMap, newMap);
			}
		}

		};
		getCacheService().registerListener(cacheKey,listener2);
		//记录该listener,应用关闭时移除
		listenerMap.put(cacheKey, listener2);
	}
	
	
	
	/**
	 * 使用服务器端传来的缓存数据更新客户端的缓存数据
	 * @param cacheMap 客户端缓存数据
	 * @param newMap  服务器端传来的缓存数据
	 */
	private static void updateCacheMap(Map cacheMap,Map newMap){
			Iterator mapIterator = newMap.entrySet().iterator();
			String udtkey = null;
			Object udtvalue = null;
			while (mapIterator.hasNext()) {
				Map.Entry mapEntry = (Map.Entry) mapIterator.next();
				udtkey = (String) mapEntry.getKey();
				udtvalue = (String) mapEntry.getValue();
				if (udtvalue.equals("v6delete")) {
					cacheMap.remove(udtkey);
				} else {
					cacheMap.put(udtkey, udtvalue);
				}
			}
		}
	

	
	/**
	 * 注销所有的缓存监听器
	 * @param listener
	 */
	public static void unRegeisterCache() {
		init();
		Set<String> keySet =listenerMap.keySet();
		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			String cacheKey = (String) iterator.next();
			NotificationListener listener2=listenerMap.get(cacheKey);
			getCacheService().unRegeisterListener(cacheKey,listener2);
		}
		
		
	}
	
	
	
	
	//取得缓存服务
	private static ICacheService getCacheService(){
		if(cacheService==null){
			Object bean = ComponentFactory.getBean("v6CacheService");
			if(bean==null){
				log.error("v6CacheService bean is null!");
				//System.err.println("v6CacheService bean is null!");
			}
			cacheService=(ICacheService) bean;
		}
		return cacheService;
	}

	
	
	/**
	 * 取单个缓存数据，用于主键、名称翻译
	 * @param cacheKey  缓存主键
	 * @param id  对象id
	 * @return
	 */
	public static String getCacheValue(String cacheKey,String id){
		init();
		Map dataMap = cacheDataMap.get(cacheKey);
		//如果是第一次取数据,则需要初始化数据，同时在服务器端注册监听器
		if(dataMap==null){
			dataMap = getCacheService().getInitData(cacheKey);
			cacheDataMap.put(cacheKey, dataMap);
			//注册监听器
			registerCache(cacheKey);
		}
		
		return (String)dataMap.get(id);
	}
	
	/**
	 * 取下拉列表的值
	 * @param dictKey 字典键
	 * @param dictId  字典编码
	 * @return
	 */
	public static String getDictValue(String dictKey,String dictId){
		Map map = getListCacheValue(CacheConstants.BASE_DICT_CACHE_KEY,dictKey);
		return (String)map.get(dictId);
		
	}
	
	
	/**
	 * 取单个缓存数据，用于主键、名称翻译
	 * @param cacheKey  缓存主键
	 * @param id  对象id
	 * @return
	 */
	public static String getCacheValue(String cacheKey,String id,Object conditionObj){
		init();
		Map dataMap = cacheDataMap.get(cacheKey);
		//如果是第一次取数据,则需要初始化数据，同时在服务器端注册监听器
		if(dataMap==null){
			dataMap = getCacheService().getInitData(cacheKey,conditionObj);
			cacheDataMap.put(cacheKey, dataMap);
			//注册监听器
			registerCache(cacheKey);
		}
		
		return (String)dataMap.get(id);
	}
	
	
	/**
	 * 取得下拉列表类型的缓存字典数据
	 * @param cacheKey 缓存主键
	 * @param conditionObj  下拉列表的主键
	 * @return
	 */
	public static Map getListCacheValue(String cacheKey,Object conditionObj){
		init();
		log.debug("cacheKey:conditionObj="+cacheKey+":"+conditionObj+".cacheDataMap="+cacheDataMap);
		Map dataMap = cacheDataMap.get(cacheKey+"/"+conditionObj);
		//如果是第一次取数据,则需要初始化数据，同时在服务器端注册监听器
		if(dataMap==null){
			log.debug("first get Data.cacheKey:conditionObj="+cacheKey+":"+conditionObj);
			dataMap = getCacheService().getInitData(cacheKey,conditionObj);
			cacheDataMap.put(cacheKey+"/"+conditionObj, dataMap);
			//注册监听器
			registerCache(cacheKey+"/"+conditionObj);
		}
		
		return dataMap;
	}
	
	
	
	/**
	 * 取某一类缓存的全部数据
	 * @param cacheKey 缓存标识符
	 * @return
	 */
	public static Map getCache(String cacheKey){
		log.debug("starting cacheClient init;");
		init();
		log.debug("end cacheClient init;");
		
		if(getCacheService()==null) return null;
		
		Map dataMap = cacheDataMap.get(cacheKey);
		//如果是第一次取数据,则需要初始化数据
		if(dataMap==null){
			dataMap = getCacheService().getInitData(cacheKey);
			cacheDataMap.put(cacheKey, dataMap);
			//注册监听器
			registerCache(cacheKey);
		}
		
		return dataMap;
	}
	
	//通用的缓存更新方法
	public static void updateCache(String cacheKey,String id,Object value){
		init();
		Map map = new HashMap();
		map.put(id, value);
		getCacheService().updateCache(cacheKey, map);
	}
	
	// 删除缓存通用方法
	public static  void deleteCache(String cacheKey,String id) {
		init();
		Map map = new HashMap();
		map.put(id, ICacheService.CACHE_DELETE_FLAG);
		getCacheService().updateCache(cacheKey, map);
	}
	
	//------------------计量单位字典接口--------------------------------//
	
	
	
	
	//------------------下拉列表字典接口--------------------------------//
	
	//取单个下拉列表数据
	private static Map getObjectDict(String listKey){
		init();
		Map dataMap = cacheDataMap.get(listKey);
		//如果是第一次取数据,则需要初始化数据，同时在服务器端注册监听器
		if(dataMap==null){
			log.debug("getObjectDictValue:"+listKey);
			dataMap = getCacheService().getInitObjectData(CacheConstants.BASE_DICT_CACHE_KEY,listKey);
			cacheDataMap.put(listKey, dataMap);
			//注册监听器
			registerCache(listKey);
		}
		return dataMap;
	}
	
	
	
	// 更新下拉列表缓存
	private static  void updateObjectDict(String listKey,String id,Object value) {
		init();
		Map map = new HashMap();
		map.put(id, value);
		getCacheService().updateCache(listKey, map);
	}
	
	
	//--------------自定义数据测试----------------//
	// 更新缓存
//	private static  void updateUserDictTest(String id,String name) {		
//		updateCache(CacheConstants.USERTEST_CACHE_KEY, id,name);
//	}
//	
//	// 删除缓存
//	private static  void deleteUserDictTest(String id) {
//		deleteCache(CacheConstants.USERTEST_CACHE_KEY, id);
//	}
//	
//	
//	//取单条字典数据
//	private static String getUserTestDictValue(String id){
//		return getCacheValue(CacheConstants.USERTEST_CACHE_KEY,id);
//	}
//	
//	
//	//取整个字典数据
//	private static Map getUserTestDict(){
//		return getCache(CacheConstants.USERTEST_CACHE_KEY);
//	}
	
	class CacheNotificationListener implements NotificationListener,Serializable{
		String cacheKey;
		public CacheNotificationListener(String  key){
			cacheKey = key;
		}

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void handleNotification(Notification notification) {
			HashMap newMap = (HashMap) ((SimpleNotification) notification)
			.getData();
			Map dataMap = cacheDataMap.get(cacheKey);
			//如果该字典数据未被缓存，则不处理
			if(dataMap!=null){
				updateCacheMap(dataMap, newMap);
			}
			
		}
		
	}
	
}


