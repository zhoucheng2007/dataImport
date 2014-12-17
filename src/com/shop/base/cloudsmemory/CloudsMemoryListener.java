package com.shop.base.cloudsmemory;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.loushang.waf.ComponentFactory;

import com.shop.base.rule.RuleUtil;

/**
 * 云记忆监听器 实现
 * @author weijingjie
 * 2014-1-3
 */
public class CloudsMemoryListener implements HttpSessionListener{
	
	private Log log=LogFactory.getLog(CloudsMemoryListener.class);
	
	public void sessionCreated(HttpSessionEvent hsEvent) {
		//由于超时时间比较长，所以直接在类里改写session超时时间，方便验证
		//httpSession.setMaxInactiveInterval(20);
	}
	public void sessionDestroyed(HttpSessionEvent hsEvent) {
		if(CloudsMemoryEnum.USE_CLOUDMEM.info.equals(RuleUtil.getRuleValue("USE_CLOUDMEM", "00", "platformrule"))){
			HttpSession httpSession=hsEvent.getSession();
			saveSession(httpSession);
		}
		
	}
	
	/**
	 * session销毁时,持久化用户操作记录
	 * 找出全部AttributeNames 以"/"开头 以.cmd或者.jsp结尾的atrribute做序列化
	 * @param httpSession
	 */
	private void saveSession(HttpSession httpSession){
		//注销计时
		long startTime = System.currentTimeMillis();
		
		String userId=(String)httpSession.getAttribute("saml.userid");
		if(!"".equals(userId)&&null!=userId){
			String appCode=(String)httpSession.getServletContext().getContextPath();
			Object cloudSeq=httpSession.getAttribute("cloudSeq");
			
			//页面级记忆
			Enumeration enums= httpSession.getAttributeNames();
			//storeMap为需要序列化的信息
			Map storeMap=new HashMap();
			/* session内的记忆结构
			 *   key为url形式的，保存一个页面内全部name以Search结尾的input标签内容。
			 *   key为其他形式的在此扩展说明
			 */
						
			while(null!=enums&&enums.hasMoreElements()){
				String attrName=(String)enums.nextElement();
				if(isUrl(attrName)){
					Map inputMap=(Map)httpSession.getAttribute(attrName);
					//时间字段过滤
					timeSearchFilter(inputMap);
					storeMap.put(attrName, inputMap);
				}
			}
			try{
				//序列化session中的用户操作信息
		        ByteArrayOutputStream out = new ByteArrayOutputStream();
		        ObjectOutputStream outputStream = new ObjectOutputStream(out);
		        outputStream.writeObject(storeMap);
		        byte [] settingObj = out.toByteArray();
		        
		        //组织需要持久化数据
		        Map paramMap=new HashMap();
		        paramMap.put("userId", userId);
		        paramMap.put("appCode", appCode);
		        paramMap.put("objType", CloudsMemoryEnum.OBJ_TYPE_URL.info);
		        paramMap.put("cloudSeq", cloudSeq);
		        paramMap.put("settingObj", settingObj);
		        
		        if(log.isDebugEnabled()){
		        	StringBuilder sb=new StringBuilder();
		        	sb.append("userId ").append(userId).append("|");
		        	sb.append("appCode ").append(appCode).append("|");
		        	sb.append("cloudSeq ").append(cloudSeq).append("|");
		        	
		        	log.debug("持久化对象信息 "+sb.toString());
		        }
		        getCloudsMemoryService().saveSession(paramMap);
		        
		        long endTime = System.currentTimeMillis();
		        if(log.isDebugEnabled()){
		        	log.debug("云记忆保存耗时 "+(endTime-startTime)+"ms");
		        }

		     }catch(Exception e){
		    	 log.error("云记忆保存失败 ",e);
		     }
		}
	}
	
	/**
	 * 判断session中的attribute是不是需要记忆的用户操作信息，
	 * 用户操作信息的key都是操作页面的url
	 * @param attrName
	 * @return false/true
	 */
	private boolean isUrl(String attrName){
		return attrName.startsWith("/")&&(attrName.endsWith(".cmd")||attrName.endsWith(".jsp"));
	}
	
	/**
	 * inputMap key中包含有 (time|date|year|month|week).*Search 这些字段的，不保存
	 * @param inputMap
	 */
	private void timeSearchFilter(Map inputMap){
		Set inputKeySet=new HashSet(inputMap.keySet());
			for(Object key:inputKeySet){
				Pattern regex=Pattern.compile("(time|date|year|month|week).*Search", Pattern.CASE_INSENSITIVE);
				Matcher matcher=regex.matcher(key.toString());
				if(matcher.find()){
					inputMap.remove(key);
				}
			}

	}

	private ICloudsMemoryService getCloudsMemoryService() {
		return (ICloudsMemoryService)ComponentFactory.getBean("cloudsMemoryService");
	}
	
}
