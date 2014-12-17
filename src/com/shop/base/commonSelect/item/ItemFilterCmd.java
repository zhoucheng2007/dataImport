package com.shop.base.commonSelect.item;

import java.io.IOException;
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
import com.shop.base.cmd.BaseCommandImpl;

public class ItemFilterCmd extends BaseCommandImpl{
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(ItemFilterCmd.class);
	
	/**
	 * 页面初始化
	 * @param req
	 * @param rep
	 * @param errorHandler
	 * @param messageHandler
	 * @param viewHelper
	 * @return
	 */
	public String pageInit(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper){
		String comId = V6BspInfo.getComId();
		req.setAttribute("comId", comId);
		req.setAttribute("organType", OrganProvider.getOrganTypeByOrganId(comId));
		req.setAttribute("comName", OrganProvider.getOrganNameByOrganId(comId));
		return null;
	}
	/**
	 * 取商品对应的漏斗
	 * @param req
	 * @param rep
	 * @param errorHandler
	 * @param messageHandler
	 * @param viewHelper
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String forItemFilter(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper){
		String comId = req.getParameter("comId");
		String userId = V6BspInfo.getUserId();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT FILTER_ID,FILTER_NAME FROM PLM_FILTER ");
		sb.append("WHERE COM_ID = '").append(comId).append("' ");		
		sb.append("AND USER_ID = '").append(userId).append("' ");		
		List filterList = SqlClient.commonSelect(sb.toString());
		log.debug("商品漏斗下拉框"+filterList);
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
	public String forItemFilterCondition(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
 ViewHelper viewHelper) {
		String comId = req.getParameter("comId");
		String filterId = req.getParameter("filterId");
		List filterConditionList = getFilterConditionList(comId, filterId);// 商品漏斗的条件
		if (log.isDebugEnabled()) {
			log.debug("打出商品漏斗list" + filterConditionList);
		}
		String itemFilterCondition = "";
		String whereSql = "";
		List<String> tableList = new ArrayList<String>();
		tableList.add("PLM_ITEM PI");
		tableList.add("PLM_ITEM_COM PIC");
		tableList.add("PLM_BRAND PLB");
		// tableList.add("PLM_BRAND_BEFORE_N PBBN");
		String isKey = "0";
		for (int i = 0; i < filterConditionList.size(); i++) {
			Map filterConditionMap = (Map) filterConditionList.get(i);
			String typeKindName = (String) filterConditionMap.get("FIELD_NAME");
			String indexId = (String) filterConditionMap.get("INDEX_ID");
			String indexValue = (String) filterConditionMap.get("INDEX_VALUE");
			String ctrlKind = (String) filterConditionMap.get("CONTROL_KIND");
			Map map = getMapByJsonStr(indexValue);
			String plmTypeName = "";
			String condition = "";// 每个类型的结果
			if (map.get("ALL") == null || ((String) map.get("ALL")).equals("")) {// 全选的,不显示
				if (ctrlKind.equals("1")) { // 取多选
					condition = orgTypeKindCondition(typeKindName);// 分类的名称
					if ("价类".equals(typeKindName)) {
						if ((String) map.get("1") != null
								&& !"".equals((String) map.get("1"))) {
							plmTypeName = "一类  "; // 一类
						}
						if ((String) map.get("2") != null
								&& !"".equals((String) map.get("2"))) {
							plmTypeName = plmTypeName + "二类  "; // 二类
						}
						if ((String) map.get("3") != null
								&& !"".equals((String) map.get("3"))) {
							plmTypeName = plmTypeName + "三类  "; // 三类
						}
						if ((String) map.get("4") != null
								&& !"".equals((String) map.get("4"))) {
							plmTypeName = plmTypeName + "四类  "; // 四类
						}
						if ((String) map.get("5") != null
								&& !"".equals((String) map.get("5"))) {
							plmTypeName = plmTypeName + "五类  "; // 五类
						}
						if ((String) map.get("6") != null
								&& !"".equals((String) map.get("6"))) {
							plmTypeName = plmTypeName + "无价类  "; // 无价类
						}
					} else if ("产地".equals(typeKindName)) {
						if ((String) map.get("0") != null
								&& !"".equals((String) map.get("0"))) {
							plmTypeName = "省内  "; // 省内
						}
						if ((String) map.get("1") != null
								&& !"".equals((String) map.get("1"))) {
							plmTypeName = plmTypeName + "省外  "; // 省内
						}
						if ((String) map.get("3") != null
								&& !"".equals((String) map.get("3"))) {
							plmTypeName = plmTypeName + "国外  "; // 国外
						}
					} else if ("商品类别".equals(typeKindName)) {
						if ((String) map.get("1") != null
								&& !"".equals((String) map.get("1"))) {
							plmTypeName = "商品  "; // 烤烟型
						}
						if ((String) map.get("2") != null
								&& !"".equals((String) map.get("2"))) {
							plmTypeName = plmTypeName + "促销品  "; // 混合型
						}
						if ((String) map.get("3") != null
								&& !"".equals((String) map.get("3"))) {
							plmTypeName = plmTypeName + "样品  "; // 外香型
						}
						if ((String) map.get("4") != null
								&& !"".equals((String) map.get("4"))) {
							plmTypeName = plmTypeName + "罚没烟 "; // 雪茄型
						}
						if ((String) map.get("5") != null
								&& !"".equals((String) map.get("5"))) {
							plmTypeName = plmTypeName + "其他  "; // 雪茄烟
						}

					} else if ("产品类型".equals(typeKindName)) {
						if ((String) map.get("10") != null
								&& !"".equals((String) map.get("10"))) {
							plmTypeName = "烤烟型  "; // 烤烟型
						}
						if ((String) map.get("20") != null
								&& !"".equals((String) map.get("20"))) {
							plmTypeName = plmTypeName + "混合型  "; // 混合型
						}
						if ((String) map.get("30") != null
								&& !"".equals((String) map.get("30"))) {
							plmTypeName = plmTypeName + "外香型  "; // 外香型
						}
						if ((String) map.get("40") != null
								&& !"".equals((String) map.get("40"))) {
							plmTypeName = plmTypeName + "雪茄型  "; // 雪茄型
						}
						if ((String) map.get("50") != null
								&& !"".equals((String) map.get("50"))) {
							plmTypeName = plmTypeName + "雪茄烟  "; // 雪茄烟
						}
						if ((String) map.get("51") != null
								&& !"".equals((String) map.get("51"))) {
							plmTypeName = plmTypeName + "手卷雪茄  "; // 手卷雪茄
						}
					}
				} else if (ctrlKind.equals("2")) { // 取文本框
					if ((String) map.get("Check") != null
							&& !"".equals((String) map.get("Check"))
							&& !"null".equals((String) map.get("Check"))) {
						condition = orgTypeKindCondition(typeKindName);// 分类的名称
						String Min = (String) map.get("Min");
						if (log.isDebugEnabled()) {
							log.debug("===Min===" + Min);
						}
						if (Min != null && !"".equals(Min)
								&& !"null".equals(Min)) {
							plmTypeName = "最小值:" + Min; // 最小值
						} else {
							plmTypeName = "最小值:" + "无  ";
						}
						String Max = (String) map.get("Max");
						if (log.isDebugEnabled()) {
							log.debug("==Max===" + Max);
						}
						if (Max != null && !"".equals(Max)
								&& !"null".equals(Max)) {
							plmTypeName = plmTypeName + "最大值:" + Max; // 最大值
						} else {
							plmTypeName = plmTypeName + "最大值:" + "无";
						}
					}
				} else if (ctrlKind.equals("3")) { // 取单选框
					if ((String) map.get("Check") != null
							&& !"".equals((String) map.get("Check"))
							&& !"null".equals((String) map.get("Check"))) {
						condition = orgTypeKindCondition(typeKindName);// 分类的名称
						if ("1".equals((String) map.get("Raido"))
								|| (String) map.get("Raido") == null) { // 默认不是高价烟
							plmTypeName = plmTypeName + "是";
						} else if ("0".equals((String) map.get("Raido"))
								|| (String) map.get("Raido") == null) {
							plmTypeName = plmTypeName + "否";
						}
					}
				} else if (ctrlKind.equals("0")) {
					condition = orgTypeKindCondition(typeKindName);// 分类的名称
					String itemName = "";
					String itemNames = "";
					String itemIds = (String) map.get("itemIds");
					StringBuffer sb = new StringBuffer();
					if (itemIds != null && !"".equals(itemIds)
							&& !"null".equals(itemIds)) {
						sb.append("SELECT ITEM_NAME FROM PLM_ITEM WHERE ITEM_ID IN ("
								+ itemIds + ")");
						List itemNameList = SqlClient.commonSelect(sb
								.toString());
						for (int k = 0; k < itemNameList.size(); k++) {
							Map map2 = new HashMap();
							map2 = (Map) itemNameList.get(k);
							itemName = (String) map2.get("ITEM_NAME");
							itemNames += itemName + " ";
						}
						plmTypeName = "商品:" + itemNames + "  ";
					}

					String brandName = "";
					String brandNames = "";
					String brandIds = (String) map.get("brandIds");
					if (log.isDebugEnabled()) {
						log.debug("===brandIds====" + brandIds);
					}
					StringBuffer sb2 = new StringBuffer();
					if (brandIds != null && !"".equals(brandIds)
							&& !"null".equals(brandIds)) {
						sb2.append("SELECT BRAND_NAME FROM PLM_BRAND WHERE BRAND_ID IN ("
								+ brandIds + ")");
						List brandNameList = SqlClient.commonSelect(sb2
								.toString());
						if (log.isDebugEnabled()) {
							log.debug("===brandNameList===" + brandNameList);
						}
						for (int m = 0; m < brandNameList.size(); m++) {
							Map map3 = new HashMap();
							map3 = (Map) brandNameList.get(m);
							brandName = (String) map3.get("BRAND_NAME");
							if (log.isDebugEnabled()) {
								log.debug("===brandName===" + brandName);
							}
							brandNames += brandName + " ";
						}
						plmTypeName = plmTypeName + "品牌:" + brandNames;
					}
					String brandOwnName = "";
					String brandOwnNames = "";
					String brandowns = (String) map.get("brandowns");
					if (log.isDebugEnabled()) {
						log.debug("===brandowns====" + brandowns);
					}
					StringBuffer sb3 = new StringBuffer();
					sb3.append("SELECT BRDOWNER_NAME FROM PLM_BRANDOWNER WHERE BRDOWNER_ID IN ("
							+ brandowns + ")");
					List brandOwnNameList = SqlClient.commonSelect(sb3
							.toString());
					if (log.isDebugEnabled()) {
						log.debug("===brandNameList===" + brandOwnNameList);
					}
					for (int m = 0; m < brandOwnNameList.size(); m++) {
						Map map3 = new HashMap();
						map3 = (Map) brandOwnNameList.get(m);
						brandOwnName = (String) map3.get("BRDOWNER_NAME");
						if (log.isDebugEnabled()) {
							log.debug("===brandOwnName===" + brandOwnName);
						}
						brandOwnNames += brandOwnName + " ";
					}
					plmTypeName = plmTypeName
							+ "<br/> "
							+ "   <font color='black'>&nbsp;&nbsp;&nbsp;品牌拥有者:</font>"
							+ brandOwnNames;

				}

				// 组织SQL
				if (log.isDebugEnabled()) {
					log.debug("进入guozhiwei");
				}
				// where 限制条件
				if (ctrlKind.equals("1")) { // 多选
					if ("PLM_ITEM.KIND".equals(indexId)) {
						String a = "";
						if ((String) map.get("1") != null
								&& !"".equals((String) map.get("1"))) {
							a = a + "'1',"; // 一类
						}
						if ((String) map.get("2") != null
								&& !"".equals((String) map.get("2"))) {
							a = a + "'2',"; // 二类
						}
						if ((String) map.get("3") != null
								&& !"".equals((String) map.get("3"))) {
							a = a + "'3',"; // 三类
						}
						if ((String) map.get("4") != null
								&& !"".equals((String) map.get("4"))) {
							a = a + "'4',"; // 四类
						}
						if ((String) map.get("5") != null
								&& !"".equals((String) map.get("5"))) {
							a = a + "'5',"; // 五类
						}
						if ((String) map.get("6") != null
								&& !"".equals((String) map.get("6"))) {
							a = a + "'6',"; // 无价类
						}
						a = a.substring(0, a.length() - 1);
						whereSql = whereSql + " AND PI.KIND IN (" + a + ")";
						if (log.isDebugEnabled()) {
							log.debug("===whereSql===" + whereSql);
						}
					} else if ("PLM_ITEM.YIELDLY_TYPE".equals(indexId)) {
						String b = "";
						if ((String) map.get("0") != null
								&& !"".equals((String) map.get("0"))) { // 省内
							b = b + "'0',";
						}
						if ((String) map.get("1") != null
								&& !"".equals((String) map.get("1"))) { // 省外
							b = b + "'1',";
						}
						if ((String) map.get("3") != null
								&& !"".equals((String) map.get("3"))) { // 省外
							b = b + "'3',";
						}
						b = b.substring(0, b.length() - 1);
						whereSql = whereSql + " AND PI.YIELDLY_TYPE IN (" + b + ")";
					} else if ("PLM_ITEM.ITEM_KIND".equals(indexId)) {
						String c = "";
						if ((String) map.get("1") != null
								&& !"".equals((String) map.get("1"))) { // 商品
							c = c + "'1',";
						}
						if ((String) map.get("2") != null
								&& !"".equals((String) map.get("2"))) { // 促销品
							c = c + "'2',";
						}
						if ((String) map.get("3") != null
								&& !"".equals((String) map.get("3"))) { // 样品
							c = c + "'3',";
						}
						if ((String) map.get("4") != null
								&& !"".equals((String) map.get("4"))) { // 罚没烟
							c = c + "'4',";
						}
						if ((String) map.get("5") != null
								&& !"".equals((String) map.get("5"))) { // 其他
							c = c + "'5',";
						}

						c = c.substring(0, c.length() - 1);
						whereSql = whereSql + " AND PI.ITEM_KIND IN (" + c	+ ")";
					} else if ("PLM_ITEM.PRODUCT_TYPE".equals(indexId)) {
						String c = "";
						if ((String) map.get("10") != null
								&& !"".equals((String) map.get("10"))) { // 烤烟型
							c = c + "'10',";
						}
						if ((String) map.get("20") != null
								&& !"".equals((String) map.get("20"))) { // 混合型
							c = c + "'20',";
						}
						if ((String) map.get("30") != null
								&& !"".equals((String) map.get("30"))) { // 外香型
							c = c + "'30',";
						}
						if ((String) map.get("40") != null
								&& !"".equals((String) map.get("40"))) { // 雪茄型
							c = c + "'40',";
						}
						if ((String) map.get("50") != null
								&& !"".equals((String) map.get("50"))) { // 雪茄烟
							c = c + "'50',";
						}
						if ((String) map.get("51") != null
								&& !"".equals((String) map.get("51"))) { // 手卷雪茄
							c = c + "'51',";
						}
						c = c.substring(0, c.length() - 1);
						whereSql = whereSql + " AND PI.PRODUCT_TYPE IN (" + c + ")";
					}

				} else if (ctrlKind.equals("2")) {//文本
					if (log.isDebugEnabled()) {
						log.debug("ctrlKind=2");
					}
					if ("PLM_ITEM_COM.PRICE_RETAIL".equals(indexId)) { // 建议零售价
						String Min = (String) map.get("Min");
						String Max = (String) map.get("Max");
						if (Min != null && !"".equals(Min)
								&& !"null".equals(Min)) {
							whereSql = whereSql + " AND PIC.PRICE_RETAIL >= " + Min;
						}
						if (Min != null && !"".equals(Min)
								&& !"null".equals(Min)) {
							whereSql = whereSql + " AND PIC.PRICE_RETAIL <= " + Max;
						}
					} else if ("PLM_ITEM_COM.PRICE_TRN".equals(indexId)) { // 商商调拨价
						String Min = (String) map.get("Min");
						String Max = (String) map.get("Max");
						if (Min != null && !"".equals(Min)
								&& !"null".equals(Min)) {
							whereSql = whereSql + " AND PIC.PRICE_TRN >= " + Min;
						}
						if (Min != null && !"".equals(Min)
								&& !"null".equals(Min)) {
							whereSql = whereSql + " AND PIC.PRICE_TRN <= " + Max;
						}
					} else if ("PLM_ITEM_COM.PRICE_TRADE".equals(indexId)) { // 批发价格
						String Min = (String) map.get("Min");
						String Max = (String) map.get("Max");
						if (Min != null && !"".equals(Min)
								&& !"null".equals(Min)) {
							whereSql = whereSql + " AND PIC.PRICE_TRADE >= " + Min;
						}
						if (Min != null && !"".equals(Min)
								&& !"null".equals(Min)) {
							whereSql = whereSql + " AND PIC.PRICE_TRADE <= " + Max;
						}
					} else if ("PLM_ITEM_COM.PRICE_PUH".equals(indexId)) { // 工商调拨价
						String Min = (String) map.get("Min");
						String Max = (String) map.get("Max");
						if (Min != null && !"".equals(Min)
								&& !"null".equals(Min)) {
							whereSql = whereSql + " AND PIC.PRICE_PUH >= " + Min;
						}
						if (Min != null && !"".equals(Min)
								&& !"null".equals(Min)) {
							whereSql = whereSql + " AND PIC.PRICE_PUH <= " + Max;
						}
					} else if ("PLM_ITEM.TAR_CONT".equals(indexId)) { // 焦油含量
						String Min = (String) map.get("Min");
						String Max = (String) map.get("Max");
						if (Min != null && !"".equals(Min)
								&& !"null".equals(Min)) {
							whereSql = whereSql + " AND PI.TAR_CONT >= " + Min;
						}
						if (Min != null && !"".equals(Min)
								&& !"null".equals(Min)) {
							whereSql = whereSql + " AND PI.TAR_CONT <= " + Max;
						}
					}
					if (log.isDebugEnabled()) {
						log.debug("进入2结束");
					}
				} else if (ctrlKind.equals("3")) { // 单选
					if (log.isDebugEnabled()) {
						log.debug("进入单选开始");
					}
					if ("PLM_ITEM.IS_TALL".equals(indexId)) {
						String Radio = (String) map.get("Raido");
						if (Radio != null && !"".equals(Radio)
								&& !"null".equals(Radio)) { // 是否高价烟
							whereSql = whereSql + " AND PI.IS_TALL ='" + Radio + "'";
						}
					} else if ("PLM_BRAND_BEFORE_N.SEQ_TYPE".equals(indexId)) { // 是否重点品牌
						if (log.isDebugEnabled()) {
							log.debug("进入重点品牌");
						}
						String Radio = (String) map.get("Raido");
						if (Radio != null && !"".equals(Radio)
								&& !"null".equals(Radio)) {
							if ("1".equals(Radio)) { // 是重点品牌
								isKey = "1";
							} else {
								isKey = "0";// 不是重点品牌
							}
						}
					}
				} else if (ctrlKind.equals("0")) { // 商品与品牌与品牌拥有者
					if (log.isDebugEnabled()) {
						log.debug("ctrlKind=0");
					}
					if ("PLM_ITEM.ITEM_ID".equals(indexId)) { // 商品
						String itemIds = (String) map.get("itemIds");
						if (itemIds != null && !"".equals(itemIds)
								&& !"null".equals(itemIds)) {
							whereSql = whereSql + " AND PI.ITEM_ID " + " IN ("
									+ itemIds + ")";
						}
						String brandIds = (String) map.get("brandIds");
						if (brandIds != null && !"".equals(brandIds)
								&& !"null".equals(brandIds)) { // 品牌
							whereSql = whereSql + " AND PLB.BRAND_ID" + " IN ("
									+ brandIds + ")";
						}
						String brandowns = (String) map.get("brandowns");
						if (brandowns != null && !"".equals(brandowns)
								&& !"null".equals(brandowns)) { // 品牌拥有者
							whereSql = whereSql + " AND PI.BRDOWNER_ID" + " IN ("
									+ brandowns + ")";
						}
					}
				}
			}
			condition = condition + plmTypeName + ",";
			condition = condition.substring(0, condition.length() - 1);
			itemFilterCondition = itemFilterCondition + orgItemTypeCondition(condition);
		} //end of for

		Map<String, String> rtnMap = new HashMap<String, String>();
		rtnMap.put("itemFilterCondition", itemFilterCondition);
		rtnMap.put("itemFilterSqlSearch", getItemFilterSql(comId, tableList, whereSql, isKey)); // 商品查询sql
		try {
			JSONObject json = new JSONObject(rtnMap == null ? new HashMap()	: rtnMap);
			rep.setContentType("application/json");
			PrintWriter out = rep.getWriter();
			out.write(json.toString());
			out.close();
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("ItemFilter-foritemFilterCondition-out-Error=", e);
			}
			//e.printStackTrace();
		}
		return "null";
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
		sb.append("SELECT PFC.INDEX_ID,PFC.INDEX_VALUE,PFC.CONTROL_KIND,CWF.KEY_NAME,CWF.FIELD_NAME ");
		sb.append("FROM PLM_FILTER_CONDITION PFC,CRM_WEB_FIELD CWF ");
		sb.append("WHERE PFC.FILTER_ID = '").append(filterId).append("' ");
		sb.append("AND PFC.INDEX_ID = CWF.KEY_NAME ");
		sb.append("AND CWF.COM_ID = '").append(comId).append("' ");
		if(log.isDebugEnabled()){
			log.debug("取选择漏斗筛选条件SQL:"+sb.toString());
		}
		List filterConditionList = SqlClient.commonSelect(sb.toString());
		return filterConditionList;
	}
	
	
//	/**
//	 * 组织返回的SQL
//	 * @param comId
//	 * @param tableList
//	 * @param whereSql
//	 * @return
//	 */
//	private String getItemFilterSql(String comId,List<String> tableList,String whereSql){
//		StringBuffer sql = new StringBuffer();
//		sql.append("SELECT PI.ITEM_ID FROM ");
//		for(int i=0;i<tableList.size();i++){
//			sql.append(tableList.get(i));
//			if(i<tableList.size()-1){
//				sql.append(",");
//			}
//		}
//		sql.append(" WHERE 1=1 AND PI.ITEM_ID=PIC.ITEM_ID AND PI.BRAND_ID=PLB.BRAND_ID AND PLB.BRAND_ID=PBBN.BRAND_ID AND PIC.COM_ID='"+comId+"'");
//		sql.append(whereSql);
//		return sql.toString();
//	}
	
	/**
	 * 组织返回的SQL
	 * 
	 * @param comId
	 * @param tableList
	 * @param whereSql
	 * @return
	 */
	private String getItemFilterSql(String comId, List<String> tableList, String whereSql, String isKey) {
		String organType = OrganProvider.getOrganTypeByOrganId(comId);
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT PI.ITEM_ID FROM ");
		for (int i = 0; i < tableList.size(); i++) {
			sql.append(tableList.get(i));
			if (i < tableList.size() - 1) {
				sql.append(",");
			}
		}
		if (isKey != null && !"".equals(isKey)) {
			if ("1".equals(isKey)) {				
				sql.append(" LEFT JOIN PLM_BRAND_BEFORE_N PBBN ON PLB.BRAND_ID=PBBN.BRAND_ID AND PBBN.SEQ_TYPE='05' AND PBBN.IS_CURR='1'");				
			} else if ("0".equals(isKey)) {
				//
			}			
		}
		sql.append(" WHERE 1=1");		
		sql.append(" AND PI.ITEM_ID=PIC.ITEM_ID AND PI.BRAND_ID=PLB.BRAND_ID AND PIC.COM_ID='" + comId + "' ");
		sql.append(whereSql);
		return sql.toString();
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
	/**
	 * 组织页面返回结果样式 - 分类类型
	 * @param typeKindName
	 * @return
	 */
	private String orgTypeKindCondition(String typeKindName){		
		return "<div class='unitBox' style='word-break:break-all;'><span class='unitHead'>&nbsp;&nbsp;&nbsp;&nbsp;" + typeKindName + ":</span><span class='unitCount'>";
	}
	/**
	 * 组织页面返回结果样式 - 分类明细
	 * @param typeKindName
	 * @return
	 */
	private String orgItemTypeCondition(String condition){
		return condition + "</span></div>";
	}
	
	/**
	 * 更新session
	 * @param req
	 * @param rep
	 * @param errorHandler
	 * @param messageHandler
	 * @param viewHelper
	 * @return
	 */
	public String updateItemSessionInfo(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper){
		String commonItemIdSearch = req.getParameter("commonItemIdSearch")==null?"":req.getParameter("commonItemIdSearch");
		String commonItemNameSearch = req.getParameter("commonItemNameSearch")==null?"":req.getParameter("commonItemNameSearch");
		String filterId = req.getParameter("filterId")==null?"":req.getParameter("filterId");
		String itemFilterCondition = req.getParameter("itemFilterCondition")==null?"":req.getParameter("itemFilterCondition");
		String itemFilterConditionCss = req.getParameter("itemFilterConditionCss")==null?"":req.getParameter("itemFilterConditionCss");
		String itemFilterSqlSearch = req.getParameter("itemFilterSqlSearch")==null?"":req.getParameter("itemFilterSqlSearch");
		if(log.isDebugEnabled()) {
			log.debug("获取的commonItemIdSearch="+commonItemIdSearch+"  commonItemNameSearch="+commonItemNameSearch+"  filterId="+filterId+"  itemFilterCondition="+itemFilterCondition+"  itemFilterConditionCss"+itemFilterConditionCss);
			log.debug("获取到的条件是---"+itemFilterSqlSearch);
		}		
		req.getSession().setAttribute("commonfilterItemId", commonItemIdSearch);
		req.getSession().setAttribute("commonfilterItemName", commonItemNameSearch);
		req.getSession().setAttribute("itemTagFilterId", filterId);
		req.getSession().setAttribute("itemFilterCondition", itemFilterCondition);
		req.getSession().setAttribute("itemFilterConditionCss", itemFilterConditionCss);
		req.getSession().setAttribute("itemFilterSessionSql", itemFilterSqlSearch);
		try {
			PrintWriter out = rep.getWriter();
			out.print("");
			out.flush();
		} catch (IOException e) {
			log.error("PrintWriter 失败", e);
		}
		return "null";
	}
	
	public String getTagItemFilterCss(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper){
		String itemFilterConditionCss = (String)req.getSession().getAttribute("itemFilterConditionCss");
		if(itemFilterConditionCss==null){
			itemFilterConditionCss = "";
		}
		Map<String, String> rtnMap = new HashMap<String, String>();
		rtnMap.put("itemFilterConditionCss", itemFilterConditionCss);
		try{
        	JSONObject json = new JSONObject(rtnMap==null?new HashMap():rtnMap);
            rep.setContentType("application/json");
            PrintWriter out = rep.getWriter();
            out.write(json.toString());
            out.close();
        }
        catch(Exception e){
            log.error("TagTestCmd----getOrganTypeByOrganId---e.printStackTrace()=", e);
        }
		return "null";
	}
}
