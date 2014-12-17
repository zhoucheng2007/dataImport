package com.shop.base.rule;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.loushang.waf.ComponentFactory;

public class RuleUtil{
	
	private static IRuleService ruleService;
	private static Log log = LogFactory.getLog(RuleUtil.class);

	//规则缓存,按<公司<规则ID<参考对象,编码值>>>保存
	private static Map<String,Map<String,Map<String,String>>> ruleCache=new HashMap<String,Map<String,Map<String,String>>>();

	//取得规则服务
	private static IRuleService getRuleService(){
		if(ruleService==null){
			Object bean = ComponentFactory.getBean("pubRuleService");
			if(bean==null){
				log.error("ruleService bean is null!");
			}
			ruleService = (IRuleService) bean;
		}
		return ruleService;
	}
	
	/**
	 * 取规则的值
	 * @param ruleId 规则类型编码
	 * @param comId  公司编码
	 * @param rulefile 规则文件名称，如zmrule,
	 * @description:默认取该公司的规则值，如果取不到，则取该规则的默认值
	 * @return
	 */
	public static String  getRuleValue(String ruleId,String comId,String rulefile){
		String value;
		try{
			value=ruleCache.get(comId).get(ruleId).get("N");
		}catch(Exception e){
			if(log.isDebugEnabled()){
				log.debug("内存里没有对应的缓存，尝试从规则表里取对应的缓存信息|comId="+comId+"|ruleId="+ruleId+"|refId=N");
			}
			value=getRuleService().getRuleValue(ruleId, comId,rulefile);
			addRuleCache(comId,ruleId,"N",value);
		}
		return value;
	}
	
	/**
	 * 取规则的值
	 * @param ruleId 规则类型编码
	 * @param comId  公司编码
	 * @description:默认取该公司的规则值，如果取不到，则取该规则的默认值
	 * @return
	 */
	public static String  getRuleValue(String ruleId,String comId){
		String value;
		try{
			value=ruleCache.get(comId).get(ruleId).get("N");
		}catch(Exception e){
			if(log.isDebugEnabled()){
				log.debug("内存里没有对应的缓存，尝试从规则表里取对应的缓存信息|comId="+comId+"|ruleId="+ruleId+"|refId=N");
			}
			value=getRuleService().getRuleValue(ruleId, comId,null);
			addRuleCache(comId,ruleId,"N",value);
		}
		return value;
	}
	/**
	 * 取规则对象的值
	 * @param ruleId 规则类型编码
	 * @param comId  公司编码
	 * @param refId  规则对象ID 
	 * @description:默认取该公司的规则对象值，如果取不到，则取全局公司该规则对象的
	 * @return
	 */
	public static String  getRuleObjectValue(String ruleId,String comId,String refId){
		String value;
		try{
			value=ruleCache.get(comId).get(ruleId).get(refId);
		}catch(Exception e){
			if(log.isDebugEnabled()){
				log.debug("内存里没有对应的缓存，尝试从规则表里取对应的缓存信息|comId="+comId+"|ruleId="+ruleId+"|refId="+refId);
			}
			value=getRuleService().getRuleObjectValue(ruleId, comId,refId);
			addRuleCache(comId,ruleId,refId,value);
		}
		return value;
	}
	
	//把获得的值保存到缓存中 <公司<规则ID<参考对象,编码值>>>
	public static void addRuleCache(String comId,String ruleId,String refId,String value){
		try{
			Map<String,String> valueMap=new HashMap<String,String>();
			valueMap.put(refId, value);
			Map<String,Map<String,String>> lineMap=new HashMap<String,Map<String,String>>();
			lineMap.put(ruleId, valueMap);
	
			//更新缓存内容
			if(null!=ruleCache.get(comId)){
				if(null!=ruleCache.get(comId).get(ruleId)){
					ruleCache.get(comId).get(ruleId).put(refId, value);
				}else{
					ruleCache.get(comId).put(ruleId, valueMap);
				}
			}else {
				ruleCache.put(comId,lineMap);
			}	
		}catch(Exception e){
			log.error("记录缓存失败");
		}
	}
	//删除缓存中部分值 <公司<规则ID<参考对象,编码值>>>
	public static void deleteRuleCache(String comId,String ruleId,String refId){
		//更新缓存内容
		if(null!=ruleCache.get(comId)){
			if(null!=ruleCache.get(comId).get(ruleId)){
				ruleCache.get(comId).get(ruleId).clear();
			}
		}
	}
	//清理缓存
	public static void clearAllRuleCache(){
		ruleCache.clear();
	}
}
