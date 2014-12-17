package com.shop.base.commonSelect.cust;


import org.loushang.waf.mvc.MultiCommand;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.loushang.waf.mvc.ViewHelper;

import com.shop.base.batis.SqlClient;
import com.shop.base.bsp.V6BspInfo;
import com.shop.base.tool.PubConstants;


/**
 * 客户通用选择条件
 * @author Kroos
 *
 */
public class CustomSelectCmd extends MultiCommand{
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(CustomSelectCmd.class);
	
	
	/**
	 * 取自定义查询类型
	 * @param req
	 * @param rep
	 * @param errorHandler
	 * @param messageHandler
	 * @param viewHelper
	 * @return
	 */	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String forCustType(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper){
		String comId = V6BspInfo.getComId();
		//先从session中取客户类型List 如果取不到则从表中取
		List typeKindList = (List)req.getSession().getAttribute("custTagTypeKindList");		
		String typeKinds = req.getSession().getAttribute("custTagTypeKinds")==null?"":(String)req.getSession().getAttribute("custTagTypeKinds");
		if(typeKindList==null){
			typeKindList = getTypeKindList(comId);//取查询客户类型
			for(int i=0;i<typeKindList.size();i++){
				Map tkMap = (Map)typeKindList.get(i);
				String typeKind = (String)tkMap.get("TYPE_KIND");				
				tkMap.put("CUST_TYPE_LIST", getCustTypeList(comId,typeKind));
				typeKinds = typeKinds + typeKind + ",";
			}
			if(typeKinds.length()>0){
				typeKinds = typeKinds.substring(0,typeKinds.length()-1);
			}
			req.getSession().setAttribute("custTagTypeKindList", typeKindList);
			req.getSession().setAttribute("custTagTypeKinds", typeKinds);			
		}
		req.setAttribute("comId", comId);		
		return "ccsForward.custtype";
	}
	
	/**
	 * 自定义查询
	 * @param req
	 * @param rep
	 * @param errorHandler
	 * @param messageHandler
	 * @param viewHelper
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String getSelectedCustType(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper){
		String comId = V6BspInfo.getComId();
		//selectedCustType : JYGM@1,2;SCLX@1,2 
		String selectedCustType = req.getParameter("selectedCustType");		
		if(log.isDebugEnabled()){
			log.debug("自定义查询 选择的客户类型selectedCustType = " + selectedCustType);
		}
		//更新session中复选框的选中状态
		updateSession(req,selectedCustType);
		String[] custTypeArr = selectedCustType.split(";");		
		String custFilterCondition = "";
		List<String> tableList = new ArrayList<String>();
		tableList.add("CO_CUST");
		String whereSql = "";
		for(int i=0;i<custTypeArr.length;i++){
			String typeKind = custTypeArr[i].split("@")[0];//客户类型 JYGM
			String custTypes = custTypeArr[i].split("@")[1];//选择的分类 1,2			
			Map typeKindInfoMap = getTypeKindInfoMap(comId,typeKind);//取客户类型名称、对应字段			
			String typeKindName = (String)typeKindInfoMap.get("TYPE_KIND_NAME");
			String refField = (String)typeKindInfoMap.get("REF_FIELD");

			//-------------------组织每个类型选中的内容------------------------
			String custTypeSelected = getCustTypeSelected(comId,typeKind,typeKindName,custTypes);
			custFilterCondition = custFilterCondition + custTypeSelected;			
			//-------------------组织每个类型选中的内容------------------------
			
			//组织SQL用到的表和WHERE过滤条件
			String refTable = refField.split("\\.")[0];
			if(!tableList.contains(refTable)){
				tableList.add(refTable);
			}
			whereSql = whereSql + " AND " + refField + " IN ('" + custTypes.replaceAll(",", "','") + "') ";
		}			
		Map<String, String> rtnMap = new HashMap<String, String>();
		rtnMap.put("custFilterCondition", custFilterCondition);
		rtnMap.put("custFilterSqlSearch", getCustFilterSql(comId,tableList,whereSql));
		try{
        	JSONObject json = new JSONObject(rtnMap==null?new HashMap():rtnMap);
        	log.debug("getSelectedCustType="+json);
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
	 * 根据选择的客户分类更新session中复选框是否选中
	 * @param req
	 * @param selectedCustType
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void updateSession(HttpServletRequest req,String selectedCustType){
		List typeKindList = (List)req.getSession().getAttribute("custTagTypeKindList");
		//selectedCustType : JYGM@1,2;SCLX@1,2 
		String[] selects = selectedCustType.split(";");
		for(int i=0;i<typeKindList.size();i++){//循环每个客户类型
			Map typeKindMap = (Map)typeKindList.get(i);
			String typeKind = (String)typeKindMap.get("TYPE_KIND");
			List custTypeList = (List)typeKindMap.get("CUST_TYPE_LIST");
			boolean isAllSel = true;
			for(int j=0;j<selects.length;j++){				
				if(selects[j].indexOf(typeKind)!=-1){//需要处理
					String custTypeSel = selects[j].split("@")[1]+",";					
					for(int k=0;k<custTypeList.size();k++){//
						Map custTypeMap = (Map)custTypeList.get(k);
						String custType = (String)custTypeMap.get("CUST_TYPE");
						custTypeMap.put("IS_CHECKED", "0");//先全部设为未选择
						if(custTypeSel.indexOf(custType+",")!=-1){
							custTypeMap.put("IS_CHECKED", "1");
						}
					}
					isAllSel = false;
				}
			}
			if(isAllSel){//全选的				
				for(int k=0;k<custTypeList.size();k++){
					Map custTypeMap = (Map)custTypeList.get(k);
					custTypeMap.put("IS_CHECKED", "1");//全部设为选择
				}				
			}
		}
		req.getSession().setAttribute("custTagTypeKindList", typeKindList);
	}
	/**
	 * 根据TYPE_KIND取类型信息
	 * @param comId
	 * @param typeKind
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Map getTypeKindInfoMap(String comId,String typeKind){
		Map typeKindInfoMap = new HashMap();
		if(typeKind.equals("PERIODS")){
			typeKindInfoMap.put("TYPE_KIND_NAME", "访销周期");
			typeKindInfoMap.put("REF_FIELD", "CSC_CUST.PERIODS_ID");
		}else{
			StringBuffer sb = new StringBuffer();		
			sb.append("SELECT TYPE_KIND_NAME,REF_FIELD FROM CRM_TYPE_KIND ");
			sb.append("WHERE COM_ID = '").append(comId).append("' ");
			sb.append("AND TYPE_KIND = '").append(typeKind).append("' ");
			List typeKindList = SqlClient.commonSelect(sb.toString());		
			if(typeKindList!=null && typeKindList.size()>0){
				typeKindInfoMap = (Map)typeKindList.get(0);			
			}
		}		
		return typeKindInfoMap;
	}
	/**
	 * 组织每个类型选择的客户分类
	 * @param comId
	 * @param typeKind
	 * @param custTypes
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private String getCustTypeSelected(String comId,String typeKind,String typeKindName,String custTypes){
		String custTypeSelected = Css.orgTypeKindCondition(typeKindName);			
		StringBuffer sb = new StringBuffer();
		if(typeKind.equals("PERIODS")){
			sb.append("SELECT PERIODS_ID CUST_TYPE,PERIODS_NAME CUST_TYPE_NAME FROM CSC_ORDER_PERIODS ");
			sb.append("WHERE COM_ID = '").append(comId).append("' ");
			sb.append("AND TYPE_SORT = '01' ");
			sb.append("AND PERIODS_ID IN ('").append(custTypes.replaceAll(",", "','")).append("') ");
		}else{
			sb.append("SELECT CUST_TYPE,CUST_TYPE_NAME FROM CRM_CUST_TYPE ");
			sb.append("WHERE COM_ID = '").append(comId).append("' ");
			sb.append("AND TYPE_KIND = '").append(typeKind).append("' ");
			sb.append("AND CUST_TYPE IN ('").append(custTypes.replaceAll(",", "','")).append("') ");
		}		
		List custTypeList = SqlClient.commonSelect(sb.toString());
		for(int j=0;j<custTypeList.size();j++){
			Map ctMap = (Map)custTypeList.get(j);
			String custTypeName = (String)ctMap.get("CUST_TYPE_NAME");
			custTypeSelected = custTypeSelected + custTypeName + ",";
		}
		if(custTypeSelected.length()>0){
			custTypeSelected = custTypeSelected.substring(0,custTypeSelected.length()-1);
		}		
		return Css.orgCustTypeCondition(custTypeSelected);
	}
	/**
	 * 组织返回的SQL
	 * @param comId
	 * @param tableList
	 * @param whereSql
	 * @return
	 */
	private String getCustFilterSql(String comId,List<String> tableList,String whereSql){
		String organType = OrganProvider.getOrganTypeByOrganId(comId);
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT CUST_ID FROM (");
		sql.append("SELECT CO_CUST.CUST_ID FROM ");
		for(int i=0;i<tableList.size();i++){
			sql.append(tableList.get(i));
			if(i<tableList.size()-1){
				sql.append(",");
			}
		}
		sql.append(" WHERE 1 = 1 ");
		for(int i=0;i<tableList.size();i++){
			String table = tableList.get(i);
			if(!(table.toUpperCase().equals("CO_CUST"))){
				sql.append(" AND CO_CUST.CUST_ID = ").append(table).append(".CUST_ID ");
			}
		}
		if(organType.equals(PubConstants.ORG_TYPE_CORP_CITY)){
			sql.append(" AND CO_CUST.COM_ID = '").append(comId).append("' ");
		}
		sql.append(whereSql);
		sql.append(") CO_CUST ");
		sql.append("WHERE 1 = 1 ");
		return sql.toString();
	}
	/**
	 * 取使用的客户类型
	 * @param comId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private List getTypeKindList(String comId){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT CTK.TYPE_KIND,CTK.TYPE_KIND_NAME ");
		sb.append("FROM CRM_CUST_GROUP CCG,CRM_TYPE_KIND CTK ");
		sb.append("WHERE CCG.COM_ID = CTK.COM_ID AND CCG.TYPE_KIND = CTK.TYPE_KIND ");
		sb.append("AND CCG.GROUP_TYPE = '01' AND CTK.IS_USE = '1' ");
		sb.append("AND CCG.COM_ID = '").append(comId).append("' ");
		List typeKindList = SqlClient.commonSelect(sb.toString());
		//加上订货周期
		Map typeKindMap = new HashMap();
		typeKindMap.put("TYPE_KIND", "PERIODS");
		typeKindMap.put("TYPE_KIND_NAME", "访销周期");
		typeKindList.add(typeKindMap);
		return typeKindList;
	}
	/**
	 * 取客户类型下的分类
	 * @param comId
	 * @param typeKind
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private List getCustTypeList(String comId,String typeKind){
		StringBuffer sb = new StringBuffer();
		if(!typeKind.equals("PERIODS")){
			sb.append("SELECT CUST_TYPE,CUST_TYPE_NAME,'1' IS_CHECKED ");
			sb.append("FROM CRM_CUST_TYPE ");
			sb.append("WHERE COM_ID = '").append(comId).append("' ");
			sb.append("AND TYPE_KIND = '").append(typeKind).append("' ");
			sb.append("AND IS_USE = '1' ");
			sb.append("ORDER BY CUST_TYPE ASC ");
		}else{
			sb.append("SELECT PERIODS_ID CUST_TYPE,PERIODS_NAME CUST_TYPE_NAME,'1' IS_CHECKED ");
			sb.append("FROM CSC_ORDER_PERIODS ");
			sb.append("WHERE COM_ID = '").append(comId).append("' ");
			sb.append("AND TYPE_SORT = '01' ");
			sb.append("ORDER BY PERIODS_ID ASC ");
		}		
		return SqlClient.commonSelect(sb.toString());
	}
	
	
	
	
	
}
