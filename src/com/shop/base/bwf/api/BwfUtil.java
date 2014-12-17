package com.shop.base.bwf.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.loushang.waf.ComponentFactory;

import com.shop.base.batis.SqlClient;


/**
 * 获得获取工作流适配器实例工具类
 * @author BlueSirius
 *	2013/12/06
 */
public class BwfUtil {

	private static Log log = LogFactory.getLog(BwfUtil.class);
	private static IBizWfTaskAdapter bizWfTaskAdapterBean;
	
	public static IBizWfTaskAdapter getBizWfTaskAdapter(){
		if(null==bizWfTaskAdapterBean){
			Object bean=ComponentFactory.getBean("bizWfTaskAdapter");
			if(bean==null){
				log.error("bizWfTaskAdapterBean bean is null!");
			}
			bizWfTaskAdapterBean=(IBizWfTaskAdapter)bean;
		}
		return bizWfTaskAdapterBean;
	}
	
	
	/**
	 * 此方法负责调用 轻量级工作流中 submitWithPrimaryKey() 指定业务主键(1条业务数据 1条待办任务) 
	 * @param comId
	 * @param processId
	 * @param nodeId
	 * @param primaryKey
	 * @param actualUserId
	 * @param actualOrganId
	 * @param actualOrganName
	 * @param nextUrl
	 * @param nextOrganId
	 * @param nextOrganName
	 * @param nextOrganType
	 * @param subject
	 * @param nextPrimaryKey
	 */
	public static void v6submitWithPrimaryKey(
			String comId,
			String processId,
			String nodeId,
            String primaryKey,
            String actualUserId,
            String actualOrganId,
            String actualOrganName,
            String nextUrl,
            String nextOrganId,
            String nextOrganName,
            String nextOrganType,
            String subject,
            String nextPrimaryKey
            )
	{
		//如果流程未启用，直接返回
		if(!isProcessActive(comId,processId)) return ;
		
		//获取工作流配置信息
		Map<String, String> bwfConfMap=getBWfConfMap(comId, processId, nodeId);
		
		String url=(String)bwfConfMap.get("URL");
		if(null!=url) url=url.trim();
		
		int limitTime=0;
		String limitUnit="";
		String LIMIT_TIME=(String)bwfConfMap.get("LIMIT_TIME");//时限 数据库中存的为 24:H
		if(null!=LIMIT_TIME&&!"".equals(LIMIT_TIME)){
			limitTime=Integer.parseInt(LIMIT_TIME.split(":")[0]);
			limitUnit=LIMIT_TIME.split(":")[1];
		}
		
		//调用V6改造后的工作流接口
		getBizWfTaskAdapter().submitWithPrimaryKeyAndLimitTime(
				url, 
				primaryKey, 
				actualUserId, 
				actualOrganId, 
				actualOrganName,
				nextUrl, 
				nextOrganId, 
				nextOrganName, 
				nextOrganType, 
				subject, 
				nextPrimaryKey, 
				limitTime, 
				limitUnit);

	}
	
	
	/**
	 * 此方法负责调用 轻量级工作流中 submitWithPrimaryKey() 指定业务主键(1条业务数据 1条待办任务) 
	 * 指定起始url,可用于处理起始节点这种特殊的情况
	 * @param url
	 * @param comId
	 * @param processId
	 * @param nodeId
	 * @param primaryKey
	 * @param actualUserId
	 * @param actualOrganId
	 * @param actualOrganName
	 * @param nextUrl
	 * @param nextOrganId
	 * @param nextOrganName
	 * @param nextOrganType
	 * @param subject
	 * @param nextPrimaryKey
	 */
	public static void v6submitWithPrimaryKey(
			String url,
			String comId,
			String processId,
			String nodeId,
            String primaryKey,
            String actualUserId,
            String actualOrganId,
            String actualOrganName,
            String nextUrl,
            String nextOrganId,
            String nextOrganName,
            String nextOrganType,
            String subject,
            String nextPrimaryKey
            )
	{
		//如果流程未启用，直接返回
		if(!isProcessActive(comId,processId)) return ;
		
		//获取工作流配置信息
		Map<String, String> bwfConfMap=getBWfConfMap(comId, processId, nodeId);
		
		int limitTime=0;
		String limitUnit="";
		String LIMIT_TIME=(String)bwfConfMap.get("LIMIT_TIME");//时限 数据库中存的为 24:H
		if(null!=LIMIT_TIME&&!"".equals(LIMIT_TIME)){
			limitTime=Integer.parseInt(LIMIT_TIME.split(":")[0]);
			limitUnit=LIMIT_TIME.split(":")[1];
		}
		
		//调用V6改造后的工作流接口
		getBizWfTaskAdapter().submitWithPrimaryKeyAndLimitTime(
				url, 
				primaryKey, 
				actualUserId, 
				actualOrganId, 
				actualOrganName,
				nextUrl, 
				nextOrganId, 
				nextOrganName, 
				nextOrganType, 
				subject, 
				nextPrimaryKey, 
				limitTime, 
				limitUnit);

	}
	
	/**
	 * 判断某个流程是否启用轻量级工作流
	 * @param comId
	 * @param processId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isProcessActive(String comId,String processId){
		String inUse="0";
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT IN_USE FROM BASE_BWF_CONF WHERE COM_ID='"+comId+"' AND PROCESS_ID='"+processId+"'");
		List<Map<String, String>> ccList=SqlClient.commonSelect(sb.toString()); 
		if(null!=ccList&&ccList.size()>0){
			Map<String, String> confMap=(Map<String, String>)ccList.get(0);
			inUse=(String)confMap.get("IN_USE");
			if(null==inUse) inUse="0";
		}
		if("1".equals(inUse)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 获取某个节点的轻量级工作流配置信息
	 * @param comId     公司编码
	 * @param processId 流程ID
	 * @param nodeId    节点ID
	 * @return
	 */
	public static Map<String, String> getBWfConfMap(String comId,String processId,String nodeId){
		Map<String, String> confMap = new HashMap<String, String>();
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT");
		sb.append(" B.COM_ID,B.PROCESS_ID,B.IN_USE,");
		sb.append(" NODE_ID, ");
		sb.append(" PARENT_ID,");
		sb.append(" ORGAN_TYPE,");
		sb.append(" URL,");
		sb.append(" NEXT_URL,");
		sb.append(" LIMIT_TIME");
		sb.append(" ");
		sb.append(" FROM BASE_BWF_CONF B,BASE_BWF_CONF_LN BL");
		sb.append(" WHERE B.COM_ID=BL.COM_ID");
		sb.append(" AND B.PROCESS_ID=BL.PROCESS_ID");
		sb.append(" AND B.COM_ID='"+comId+"'");
		sb.append(" AND B.PROCESS_ID='"+processId+"'");
		sb.append(" AND NODE_ID='"+nodeId+"'");
        @SuppressWarnings("unchecked")
		List<Map<String, String>> ccList=SqlClient.commonSelect(sb.toString()); 
		if(null!=ccList&&ccList.size()>0){
			confMap=(Map<String, String>)ccList.get(0);
		}
		
		return confMap;
	}
	

}
