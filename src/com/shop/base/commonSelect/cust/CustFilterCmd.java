package com.shop.base.commonSelect.cust;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.loushang.bsp.service.OrganProvider;
import org.loushang.util.IErrorHandler;
import org.loushang.util.IMessageHandler;
import org.loushang.waf.ComponentFactory;
import org.loushang.waf.mvc.ViewHelper;

import com.shop.base.batis.SqlClient;
import com.shop.base.bsp.V6BspInfo;
import com.shop.base.cmd.BaseCommandImpl;
import com.v6.crm.service.hsf.custfilter.ICustFilterHsfService;
/**
 * 漏斗相关
 * @author Kroos
 *
 */
public class CustFilterCmd extends BaseCommandImpl{
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(CustFilterCmd.class);
	
	private static ICustFilterHsfService custFilterService = null;
	
	/**
	 * 取用户对应的漏斗
	 * @param req
	 * @param rep
	 * @param errorHandler
	 * @param messageHandler
	 * @param viewHelper
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String getCustFilter(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper){
		String comId = V6BspInfo.getComId();//取登陆公司的漏斗
		String userId = V6BspInfo.getUserId();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT FILTER_ID,FILTER_NAME FROM CRM_FILTER ");
		sb.append("WHERE COM_ID = '").append(comId).append("' ");		
		sb.append("AND USER_ID = '").append(userId).append("' ");
		if(log.isDebugEnabled()){
			log.debug("取登陆用户漏斗SQL:"+sb.toString());
		}
		List filterList = SqlClient.commonSelect(sb.toString());
		sb = new StringBuffer();
		sb.append("<option value=''>请选择</option>");
		if (filterList != null && !filterList.isEmpty()) {
			for (int i = 0; i < filterList.size(); i++) {
				Map filterMap = (Map)filterList.get(i);				
				sb.append("<option value='").append((String)filterMap.get("FILTER_ID") + "'>");
				sb.append((String)filterMap.get("FILTER_NAME")).append("</option>");
			}
		}
		try {
			PrintWriter out = rep.getWriter();
			out.print(sb.toString());
			out.flush();
		} catch (IOException e) {
			log.debug("PrintWriter 失败");
		}
		return "null";
	}
	/**
	 * 取选择漏斗的筛选条件
	 */
	@SuppressWarnings("rawtypes")
	public String getCustFilterCondition(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper){
		String comId = V6BspInfo.getComId();//取登陆公司的漏斗
		String filterId = req.getParameter("filterId");
		Map paraMap = new HashMap();
		paraMap.put("COM_ID", comId);
		paraMap.put("FILTER_ID", filterId);		
		String custFilterSqlSearch = getCustFilterService().getFilterSql(paraMap);
		if(log.isDebugEnabled()){
			log.debug("客户漏斗SQL:"+custFilterSqlSearch);
		}
		List filterInfoList = getFilterInfoList(comId,filterId);		
		String itemName = "";
		String beginDate = "";
		String endDate = "";
		String itemInfo = "";
		if(filterInfoList!=null && filterInfoList.size()>0){
			Map filterInfoMap = (Map)filterInfoList.get(0);
			itemName = (String)filterInfoMap.get("ITEM_NAME");
			beginDate = (String)filterInfoMap.get("BEGIN_DATE");
			endDate = (String)filterInfoMap.get("END_DATE");
			String begin = beginDate.substring(0,4)+"年"+beginDate.substring(4,6)+"月";
			String end = endDate.substring(0,4)+"年"+endDate.substring(4,6)+"月";
			itemInfo = itemName + " " + begin + "至" + end;
		}
		List filterConditionList = getFilterConditionList(comId,filterId);//漏斗条件			
		String custFilterCondition = "";
		for(int i=0;i<filterConditionList.size();i++){
			Map filterConditionMap = (Map)filterConditionList.get(i);
			String ctrKind = (String)filterConditionMap.get("CONTROL_KIND");
			String indexId = (String)filterConditionMap.get("INDEX_ID");			
			String indexName = (String)filterConditionMap.get("INDEX_NAME");			
			String indexValue = (String)filterConditionMap.get("INDEX_VALUE");
			String typeKind = (String)filterConditionMap.get("TYPE_KIND");
			Map map = getMapByJsonStr(indexValue);
			String condition = "";//每个类型的结果 
			if(ctrKind.equals("1")){
				if(indexId.equals("CO_CUST.SALE_DEPT_ID")){//取营销部
					condition = getSaleorgCondition("营销部",map);
				}else if(indexId.equals("CO_CUST.SLSMAN_ID")){//取客户经理
					condition = getSlsmanCondition("客户经理",map);
				}else{//取其他查询类型
					condition = getCustTypeCondition(comId,typeKind,indexName,map);
				}				
			}else{
				condition = getWebFieldCondistion(itemInfo+indexName,map);
			}			
			custFilterCondition = custFilterCondition + Css.orgCustTypeCondition(condition);
		}
		log.debug("custFilterCondition="+custFilterCondition);
		//最终结果 转换为json
		Map<String, String> rtnMap = new HashMap<String, String>();
		rtnMap.put("custFilterCondition", custFilterCondition);
		rtnMap.put("custFilterSqlSearch", custFilterSqlSearch);
		try{
        	JSONObject json = new JSONObject(rtnMap==null?new HashMap():rtnMap);
            rep.setContentType("application/json");
            PrintWriter out = rep.getWriter();
            out.write(json.toString());
            out.close();
        }
        catch(Exception e){
        	if(log.isInfoEnabled()){
            	log.info("TagTestCmd----forCustFilterCondition---e.printStackTrace()="+e);
            }
            e.printStackTrace();
        }
        return "null";
	}
	/**
	 * 取选择漏斗筛选商品
	 * @param comId
	 * @param filterId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private List getFilterInfoList(String comId,String filterId){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT CF.ITEM_ID,PI.ITEM_NAME,CF.BEGIN_DATE,CF.END_DATE ");
		sb.append("FROM CRM_FILTER CF,PLM_ITEM PI ");
		sb.append("WHERE CF.COM_ID = '").append(comId).append("' ");
		sb.append("AND CF.FILTER_ID = '").append(filterId).append("' ");
		sb.append("AND CF.ITEM_ID = PI.ITEM_ID ");
		if(log.isDebugEnabled()){
			log.debug("取选择漏斗筛选商品SQL:"+sb.toString());
		}
		return SqlClient.commonSelect(sb.toString());
	}
	/**
	 * 取选择漏斗筛选
	 * @param comId
	 * @param filterId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private List getFilterConditionList(String comId,String filterId){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT CF.CONTROL_KIND,CF.INDEX_ID,CF.INDEX_VALUE,TK.TYPE_KIND,");
		sb.append("CASE WHEN CF.CONTROL_KIND = '1' THEN TK.TYPE_KIND_NAME ELSE WF.FIELD_NAME END INDEX_NAME ");
		sb.append("FROM (");
		sb.append(" SELECT INDEX_ID,INDEX_VALUE,CONTROL_KIND ");
		sb.append(" FROM CRM_FILTER_CONDITION ");
		sb.append(" WHERE FILTER_ID = '").append(filterId).append("' ");
		sb.append(") CF LEFT JOIN (");
		sb.append(" SELECT TYPE_KIND,REF_FIELD,TYPE_KIND_NAME FROM CRM_TYPE_KIND ");
		sb.append(" WHERE COM_ID = '").append(comId).append("' ");
		sb.append(") TK ON CF.INDEX_ID = TK.REF_FIELD LEFT JOIN (");
		sb.append(" SELECT KEY_NAME,FIELD_NAME ");
		sb.append(" FROM CRM_WEB_FIELD ");
		sb.append(" WHERE COM_ID = '").append(comId).append("' ");
		sb.append(" AND MENU_ID='custfilter' ");
		sb.append(") WF ON CF.INDEX_ID = WF.KEY_NAME ");
		sb.append("ORDER BY CF.CONTROL_KIND ASC ");
		if(log.isDebugEnabled()){
			log.debug("取选择漏斗筛选条件SQL:"+sb.toString());
		}
		List filterConditionList = SqlClient.commonSelect(sb.toString());
		return filterConditionList;
	}
	/**
	 * 取客户漏斗中的营销部信息
	 * @param infoMap
	 * @return
	 */
	private String getSaleorgCondition(String indexName,Map infoMap){
		StringBuffer sb = new StringBuffer();
		sb.append(Css.orgTypeKindCondition(indexName));//分类的名称
		for(Iterator it = infoMap.entrySet().iterator(); it.hasNext();){
			Map.Entry attrentry = (Map.Entry) (it.next());
			String selKey = (String) attrentry.getKey();	
			if("ALL".equals(selKey)||selKey==null||"".equals(selKey)){
				
			}else{
				sb.append(OrganProvider.getOrganNameByOrganId(selKey)).append(",");
			}
			
		}
		if(sb.length()>0){
			return sb.substring(0,sb.length()-1);
		}else{
			return sb.toString();
		}		
	}
	/**
	 * 取客户漏斗中的客户经理信息
	 * @param infoMap
	 * @return
	 */
	private String getSlsmanCondition(String indexName,Map infoMap){
		StringBuffer sb = new StringBuffer();
		sb.append(Css.orgTypeKindCondition(indexName));//分类的名称
		for(Iterator it = infoMap.entrySet().iterator(); it.hasNext();){
			Map.Entry attrentry = (Map.Entry) (it.next());
			String selKey = (String) attrentry.getKey();			
			sb.append(OrganProvider.getPositionNameByPositionCode(selKey)).append(",");
		}
		if(sb.length()>0){
			return sb.substring(0,sb.length()-1);
		}else{
			return sb.toString();
		}
	}
	/**
	 * 取客户漏斗中的客户类型
	 * @param indexName
	 * @param infoMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private String getCustTypeCondition(String comId,String typeKind,String indexName,Map infoMap){
		StringBuffer sb = new StringBuffer();
		if(infoMap.get("ALL")==null || ((String)infoMap.get("ALL")).equals("")){//全选的 不显示			
			sb.append(Css.orgTypeKindCondition(indexName));//分类的名称				
			for(Iterator it = infoMap.entrySet().iterator(); it.hasNext();){
				Map.Entry attrentry = (Map.Entry) (it.next());
				String selKey = (String) attrentry.getKey();
				//组织页面显示内容
				StringBuffer in = new StringBuffer();
				in.append("SELECT CUST_TYPE_NAME FROM CRM_CUST_TYPE ");
				in.append("WHERE COM_ID = '").append(comId).append("' ");
				in.append("AND TYPE_KIND = '").append(typeKind).append("' ");
				in.append("AND CUST_TYPE = '").append(selKey).append("' ");
				List custTypeNameList = SqlClient.commonSelect(in.toString());
				String custTypeName = "";
				if(custTypeNameList!=null && custTypeNameList.size()>0){
					Map custTypeMap = (Map)custTypeNameList.get(0);
					custTypeName = (String)custTypeMap.get("CUST_TYPE_NAME");
				}
				sb.append(custTypeName).append(",");					
			}					
		}
		if(sb.length()>0){
			return sb.substring(0,sb.length()-1);
		}else{
			return sb.toString();
		}
	}
	/**
	 * 取客户漏斗中的客户类型
	 * @param indexName
	 * @param infoMap
	 * @return
	 */
	private String getWebFieldCondistion(String indexName,Map infoMap){
		StringBuffer sb = new StringBuffer();
		String check=(String)infoMap.get("Check");//是否选择了
		String raido=(String)infoMap.get("Raido");//是范围还是名次 0:范围 1:名次
		//范围
		String min=(String)infoMap.get("Min");//最小值
		String max=(String)infoMap.get("Max");//最大值		
		//名次
		String isTop=(String)infoMap.get("Select");//是前几名还是后几名 1:前 2:后
		String rank=(String)infoMap.get("Rank");//排名
		if(check!=null && check.equals("1")){//选择了
			sb.append(Css.orgTypeKindCondition(indexName));//分类的名称	
			if(raido.equals("0")){
				sb.append("在"+min+"--"+max+"之间");
			}else{
				if(isTop.equals("1")){
					sb.append("前"+rank+"名");
				}else{
					sb.append("后"+rank+"名");
				}				
			}
		}
		return sb.toString();
	}
	/**
	 * 将格式为{"name":"12342","wu":"999","peng":"gogogo"}的字符串重新转换为Map对象
	 * @param str
	 * @return
	 */
	private static Map<String ,String> getMapByJsonStr(String str){
		Map<String ,String> map = new HashMap<String ,String>();
		if(null==str||"".equals(str)||"null".equals(str)||!str.endsWith("\"}"))
			return null;
		String sb = str.substring(2, str.length()-2);
		String[] name =  sb.split("\\\",\\\"");
		String[] nn =null;
		for(int i= 0;i<name.length; i++){
			 nn = name[i].split("\\\":\\\"");
			 map.put(nn[0], nn[1]);
		}
		return map;
	}
	
	private static ICustFilterHsfService getCustFilterService(){
		if(custFilterService==null){
			Object bean = ComponentFactory.getBean("pubCustFilterService");
			custFilterService = (ICustFilterHsfService)bean;
		}		
		return custFilterService;
	}
}
