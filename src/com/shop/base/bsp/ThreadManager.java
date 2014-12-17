package com.shop.base.bsp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shop.base.bsp.GlobalProperties_v6;
import com.shop.base.bsp.RequestTool;


/** 说明：线程控制类
 * 
 * @author pengzhu
 * ThreadManager.java 2014-5-29
 */
public class ThreadManager {
	
	/**
	 * 日志
	 */
	private static Log log = LogFactory.getLog(ThreadManager.class);
	
	private static final String limitThreadUrlKey = "limitThreadUrl";
	private static final String urlThreadNumbersCtrlKey = "maxUrlThreadNumber";
	
	//限制最大访问线程数Url
	private static String[] limitThreadUrl ;
	//单Url最大访问线程数
	private static int maxUrlThreadNumber ;

	//初始化时间点，控制每分钟只能重载一次
	private static long initTimeMillis = 0;
	
	//
	private static Map<String,Integer> urlThreadNumbers = new HashMap<String,Integer>();
	
	/**
	 * 
	 */
	public static void initThreadManager(){
		//初始化时间点，控制每分钟只能重载一次
		long its = ( System.currentTimeMillis() - initTimeMillis ) / 1000 ;
		if(its < 60){
			throw new RuntimeException("每分钟只能修改一次并发控制参数，请不要频繁刷新！");
		}
		initTimeMillis = System.currentTimeMillis();
		//正常加载
		try{
			//读取配置的参数
			maxUrlThreadNumber = getInitNumber(GlobalProperties_v6.getString(urlThreadNumbersCtrlKey));
			limitThreadUrl = RequestTool.splitUrls(GlobalProperties_v6.getString(limitThreadUrlKey));

			//
			if(maxUrlThreadNumber < 0){
				//单Url最大访问线程数不控制，直接删除所有计数
				synchronized (urlThreadNumbersCtrlKey) {
					urlThreadNumbers.clear();
				}
			}else{
				//控制，保留控制Url的计数，删除不控制Url的计数
				synchronized (urlThreadNumbersCtrlKey) {
					Map<String,Integer> urlTNS = new HashMap<String,Integer>();
					urlTNS.putAll(urlThreadNumbers);
					for (int i = 0; i < limitThreadUrl.length; i++) {
						if(!urlThreadNumbers.containsKey(limitThreadUrl[i])){
							urlThreadNumbers.put(limitThreadUrl[i], 0);
						}else{
							urlTNS.remove(limitThreadUrl[i]);
						}
					}
					if(!urlTNS.isEmpty()){
						Iterator<String> iterator = urlTNS.keySet().iterator();
						while(iterator.hasNext()){
							urlThreadNumbers.remove(iterator.next());
						}
					}
				}
			}
		} catch(Exception e){
			log.error("ThreadManager读取配置的参数出错：", e);
		}
		if(log.isInfoEnabled()){
			log.info("ThreadManager读取配置的参数：");
			log.info("单Url最大访问线程数："+maxUrlThreadNumber);
			log.info("限制最大访问线程数Url："+Arrays.toString(limitThreadUrl));
			log.info("ThreadManager当前记录：");
			//log.info("最大Session数："+OnlineManager.getSessionCount());
			log.info("单Url最大访问线程数："+urlThreadNumbers);
		}
	}
	
	public static void reInitThreadManager(){
		GlobalProperties_v6.reload();
		//
		initThreadManager();
	}
	/**
	 * 
	 * @param request
	 */
	public static void checkThreadLimit(HttpServletRequest request) {
		//单Url最大访问线程数
		if(maxUrlThreadNumber > -1){
			String requestUrl = RequestTool.getRequestUrl(request);
			synchronized (urlThreadNumbersCtrlKey) {
				if(urlThreadNumbers.containsKey(requestUrl)){
					Integer number = urlThreadNumbers.get(requestUrl);
					if(log.isDebugEnabled()) log.debug("checkThreadLimit："+requestUrl+"当前访问线程数["+number+"]，单Url最大访问线程数["+maxUrlThreadNumber+"]。");
					if(number >= maxUrlThreadNumber && maxUrlThreadNumber > -1){//增加maxUrlThreadNumber > -1判断，防止被动态修改成不控制
						if(log.isDebugEnabled()) log.debug("checkThreadLimit：不能再访问该URL。");
						throw new PermitException("4130","功能当前访问量超过最大限制，请稍后再试！",number+"/"+maxUrlThreadNumber);
					} else {
						if(log.isDebugEnabled()) {
							log.debug("checkThreadLimit：允许访问该URL。");
						}
						urlThreadNumbers.put(requestUrl, number+1 ) ;
						request.setAttribute(urlThreadNumbersCtrlKey, requestUrl);//放入当前requestUrl防止某些中间件处理Forward或Redirect后URL变化引起控制失效问题
					}
				} else {
					//不包含，不控制
					//log.debug("checkThreadLimit 不包含,不控制 requestUrl="+requestUrl);
				}
			}
		} else {
			//参数设置小于0，不控制
			//log.debug("checkThreadLimit 参数设置小于0，不控制");
		}
	}
	
	/**
	 * @param request
	 */
	public static void realseThreadLimit(HttpServletRequest request) {
		//单Url最大访问线程数
		if(request.getAttribute(urlThreadNumbersCtrlKey)!=null){
			String requestUrl = (String)request.getAttribute(urlThreadNumbersCtrlKey);//取放入Session中的requestUrl防止某些中间件处理Forward或Redirect后URL变化引起控制失效问题
			synchronized (urlThreadNumbersCtrlKey) {
				if(urlThreadNumbers.containsKey(requestUrl)){
					Integer number = urlThreadNumbers.get(requestUrl);
					if(log.isDebugEnabled()) log.debug("realseThreadLimit："+requestUrl+"当前访问线程数["+number+"]，单Url最大访问线程数["+maxUrlThreadNumber+"]。");
					urlThreadNumbers.put(requestUrl, number-1 ) ;
				}else{
					//不包含，不控制
					//log.debug("realseThreadLimit 不包含,不控制 requestUrl="+requestUrl);
				}
			}
		}else{
			//request中没有控制参数，表明开始访问Url时没有控制，退出访问Url时也不用控制
			//log.debug("realseThreadLimit 参数设置小于0，不控制");
		}
	}
	
	/**
	 * @param key
	 * @return
	 */
	public static Object getMonitor(String key) {
		if(key == null)
			return "key == null";
		else if(key.equals("maxUrlThreadNumber"))
			return maxUrlThreadNumber;
		else if(key.equals("limitThreadUrl"))
			return Arrays.toString(limitThreadUrl);
		else if(key.equals("urlThreadNumbers"))
			return urlThreadNumbers;
		else
			return "没有"+key+"的值";
	}
	
	/**
	 * @param value
	 * @return
	 */
	private static int getInitNumber(String value) {
		int number;
		if (value != null) {
			try {
				number = Integer.parseInt(value);
			} catch (NumberFormatException nfe) {
				number = -1;
				log.error("getInitNumber-error", nfe);
			}
		} else {
			number = -1;
		}
		return number;
	}
}
