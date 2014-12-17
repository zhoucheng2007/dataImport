package com.shop.base.cloudsmemory;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.loushang.waf.ComponentFactory;

import com.shop.base.rule.RuleUtil;

/**
 * 云记忆信息提供者
 * @author weijingjie
 * 2014-1-3
 */
public class CloudsMemorySessionProvider implements Runnable{
	
	private static Log  log=LogFactory.getLog(CloudsMemorySessionProvider.class);
	
	//private HttpServletRequest req;
	private HttpSession hs;
	
	public CloudsMemorySessionProvider(HttpSession hs){
			this.hs=hs;	
	}
	public void run() {
		//异步线程
		//加载计时
		long startTime = System.currentTimeMillis();
		HttpSession hs=this.hs;
		
		Map paramMap=new HashMap();
		String userId=(String)hs.getAttribute("saml.userid");
		String appCode=(String)hs.getServletContext().getContextPath();
		String objType=CloudsMemoryEnum.OBJ_TYPE_URL.info;
		if(!"".equals(userId)&&null!=userId){
			paramMap.put("userId",userId);
			paramMap.put("appCode",appCode);
			paramMap.put("objType",objType);
			//反序列化
			try{
				Map rtnMap=getCloudsMemoryService().getSession(paramMap);
				hs.setAttribute("cloudSeq", rtnMap.get("cloudSeq"));
				
				byte[] settingObj=null!=rtnMap.get("settingObj")?(byte[])rtnMap.get("settingObj"):null;
				if (null!=settingObj){
					ByteArrayInputStream bains=new ByteArrayInputStream(settingObj);
					ObjectInputStream in=new ObjectInputStream(bains);
					Map<String,Map> settingMap=(Map<String,Map>)in.readObject();
					
					Set urlSet=settingMap.keySet();
					for(Object url:urlSet){
						hs.setAttribute(url.toString(), settingMap.get(url));
					}
				}
		        long endTime = System.currentTimeMillis();
		        if(log.isDebugEnabled()){
		        	log.debug("云记忆载入用户session完成耗时 "+(endTime-startTime)+"ms ,序号 ："+rtnMap.get("cloudSeq"));
		        }
			}catch(Exception e){
				log.warn("用户操作记录载入失败",e);		
			}
		}	
	}

	public static void getSession(HttpSession hs){
			//启动新的线程用于异步加载
			//加载计时
			long startTime = System.currentTimeMillis();
			try{
				if(CloudsMemoryEnum.USE_CLOUDMEM.info
						.equals(RuleUtil.getRuleValue("USE_CLOUDMEM", "00", "platformrule"))
				){
					CloudsMemorySessionProvider sessionProvider=new CloudsMemorySessionProvider(hs);
					Thread newTh=new Thread(sessionProvider);
					newTh.start();
				}
			}catch(Exception e){
				log.warn("云记忆加载线程启动失败",e);
			}
	        long endTime = System.currentTimeMillis();
	        if(log.isDebugEnabled()){
	        	log.debug("主线程运行时间:"+(endTime-startTime)+"ms");
	        }

	}

	private  ICloudsMemoryService getCloudsMemoryService() {
		Object obj=ComponentFactory.getBean("cloudsMemoryService");
		if(null!=obj){
			ICloudsMemoryService bean =(ICloudsMemoryService)obj;
			return bean;
		}else{
			log.error("cloudsMemoryService bean is null");
			return null;
		}
	}
	
}
