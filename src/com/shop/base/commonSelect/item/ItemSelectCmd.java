package com.shop.base.commonSelect.item;

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
import org.loushang.waf.mvc.MultiCommand;
import org.loushang.waf.mvc.ViewHelper;

import com.shop.base.batis.SqlClient;

public class ItemSelectCmd extends MultiCommand {
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(ItemSelectCmd.class);

	/**
	 * 取自定义查询类型
	 * 
	 * @param req
	 * @param rep
	 * @param errorHandler
	 * @param messageHandler
	 * @param viewHelper
	 * @return
	 **/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String forItemType(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper) {
		String comId = req.getParameter("comId");
		if(log.isDebugEnabled()){
			log.debug("进入取自定义类型");
		}
		// 先从session中取商品类型List 如果取不到则从表中取
		List typeKindList = (List) req.getSession().getAttribute("custTagTypeKindList");
		String typeKinds = req.getSession().getAttribute("custTagTypeKinds") == null ? ""
				: (String) req.getSession().getAttribute("custTagTypeKinds");
		if (typeKindList == null) {
			typeKindList = getTypeKindList(comId);// 取查询商品大类类型
			for (int i = 0; i < typeKindList.size(); i++) {
				Map tkMap = (Map) typeKindList.get(i);
				String typeKind = (String) tkMap.get("INDEX_ID");
				// tkMap.put("CUST_TYPE_LIST", getCustTypeList(comId,typeKind));
				typeKinds = typeKinds + typeKind + ",";
			}
			if (typeKinds.length() > 0) {
				typeKinds = typeKinds.substring(0, typeKinds.length() - 1);
			}
			req.getSession().setAttribute("custTagTypeKindList", typeKindList);
		}
		//
		req.setAttribute("comId", comId);
		//品牌拥有者
		List brandOwnerListData = getDataList("PLM_BRANDOWNER");
		req.setAttribute("brandOwnerListData", brandOwnerListData);
		//价类
		List kindListData = getDataList("PLM_ITEM_PRICE");
		req.setAttribute("kindListData", kindListData);
		//产地类别
		List plmItemYieldlyTypeListData = getDataList("PLM_ITEM_YIELDLY_TYPE");
		req.setAttribute("plmItemYieldlyTypeListData", plmItemYieldlyTypeListData);
		//商品类别
		List plmItemKindListData = getDataList("PLM_ITEM_KIND");
		req.setAttribute("plmItemKindListData", plmItemKindListData);
		//产品类型
		List plmItemProductTypeListData = getDataList("PLM_ITEM_PRODUCT_TYPE");
		req.setAttribute("plmItemProductTypeListData", plmItemProductTypeListData);
		
		return "itemForward.itemtype";
	}

	/**
	 * 自定义查询
	 * 
	 * @param req
	 * @param rep
	 * @param errorHandler
	 * @param messageHandler
	 * @param viewHelper
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String getSelectedItemType(HttpServletRequest req,
			HttpServletResponse rep, IErrorHandler errorHandler,
			IMessageHandler messageHandler, ViewHelper viewHelper) {
		String comId = req.getParameter("comId");
		String plmTypeName = "";
		String typeKindName = "";
		String kind = req.getParameter("kind"); // 取价类
		if(log.isDebugEnabled()){
			log.debug("===价类===" + kind);
		}
		String YIELDLY_TYPE = req.getParameter("YIELDLY_TYPE"); // 取产地类别
		if(log.isDebugEnabled()) {
			log.debug("===产地类别===" + YIELDLY_TYPE);
		}
		String ITEM_KIND = req.getParameter("ITEM_KIND"); // 取商品类别
		if(log.isDebugEnabled()) {
			log.debug("====商品类别===" + ITEM_KIND);
		}
		String PRODUCT_TYPE = req.getParameter("PRODUCT_TYPE"); // 取产品类型
		if(log.isDebugEnabled()) {
			log.debug("====产品类型===" + PRODUCT_TYPE);
		}
		String itemId = req.getParameter("itemId");
		String itemName = req.getParameter("itemName");
		String brandId = req.getParameter("brandId");
		// String brandOwnerList = req.getParameter("brandOwnerList");
		String brandName = req.getParameter("brandName");
		String isTall = req.getParameter("isTall");
		String isKey = req.getParameter("isKey");
		String PUHMin = req.getParameter("PUHMin");
		String PUHMax = req.getParameter("PUHMax");
		String TRNMin = req.getParameter("TRNMin");
		String TRNMax = req.getParameter("TRNMax");
		String TRADEMin = req.getParameter("TRADEMin");
		String TRADEMax = req.getParameter("TRADEMax");
		String RETAILMin = req.getParameter("RETAILMin");
		String RETAILMax = req.getParameter("RETAILMax");
		String Min1 = req.getParameter("Min1");
		String Max1 = req.getParameter("Max1");

		if(log.isDebugEnabled()) {
			log.debug("获取到的所有的页面条件---yp--isTall=" + isTall + " isKey=" + isKey
					+ " PUHMin=" + PUHMin + " PUHMax=" + PUHMax + " TRNMin="
					+ TRNMin + " TRNMax=" + TRNMax + " TRADEMin=" + TRADEMin
					+ " TRADEMax=" + TRADEMax + " RETAILMin=" + RETAILMin
					+ " RETAILMax=" + RETAILMax + " Min1=" + Min1 + " Max1=" + Max1
					+ "brandOwnerList");
		}
				

		String[] kindArr = kind.split(",");
		String[] yieldlyTypeArr = YIELDLY_TYPE.split(",");
		String[] itemKindArr = ITEM_KIND.split(",");
		String[] productTypeArr = PRODUCT_TYPE.split(",");
		String itemFilterCondition = "";
		List<String> tableList = new ArrayList<String>();
		tableList.add("PLM_ITEM PI");
		tableList.add("PLM_ITEM_COM PIC");
		tableList.add("PLM_BRAND PLB");
		// tableList.add("PLM_BRAND_BEFORE_N PBBN");
		String whereSql = "";
		if (kind != null && !"".equals(kind) && !"null".equals(kind)) {
			typeKindName = "价类";
			if(log.isDebugEnabled()) {
				log.debug("===kind===" + kind);
			}
			for (int i = 0; i < kindArr.length; i++) {
				if ("1".equals(kindArr[i])) {
					plmTypeName = plmTypeName + "一类  ";
				} else if ("2".equals(kindArr[i])) {
					plmTypeName = plmTypeName + "二类 ";
				} else if ("3".equals(kindArr[i])) {
					plmTypeName = plmTypeName + "三类 ";
				} else if ("4".equals(kindArr[i])) {
					plmTypeName = plmTypeName + "四类 ";
				} else if ("5".equals(kindArr[i])) {
					plmTypeName = plmTypeName + "五类 ";
				} else if ("6".equals(kindArr[i])) {
					plmTypeName = plmTypeName + "无价类 ";
				}
			}

			// -------------------组织每个类型选中的内容------------------------
			String itemTypeSelected = getItemTypeSelected(comId, typeKindName,
					plmTypeName);
			itemFilterCondition = itemFilterCondition + itemTypeSelected;
			if(log.isDebugEnabled()) {
				log.debug("====itemTypeSelected==" + itemTypeSelected);
				log.debug("====itemFilterCondition===" + itemFilterCondition);
			}			
		}
		// -------------------组织每个类型选中的内容------------------------
		if (YIELDLY_TYPE != null && !"".equals(YIELDLY_TYPE)
				&& !"null".equals(YIELDLY_TYPE)) {
			if(log.isDebugEnabled()) {
				log.debug("进入产地");
			}
			typeKindName = "产地";
			plmTypeName = "";
			for (int i = 0; i < yieldlyTypeArr.length; i++) {
				if ("0".equals(yieldlyTypeArr[i])) {
					plmTypeName = "省内 ";
				} else if ("1".equals(yieldlyTypeArr[i])) {
					plmTypeName = plmTypeName + "省外 ";
				} else if ("3".equals(yieldlyTypeArr[i])) {
					plmTypeName = plmTypeName + "国外 ";
				}
			}
			if(log.isDebugEnabled()) {
				log.debug("======" + plmTypeName);
			}
			String itemTypeSelected = getItemTypeSelected(comId, typeKindName,
					plmTypeName);
			itemFilterCondition = itemFilterCondition + itemTypeSelected;
		}
		if(log.isDebugEnabled()) {
			log.debug("====itemFilterCondition22==" + itemFilterCondition);
		}
		if (ITEM_KIND != null && !"".equals(ITEM_KIND)
				&& !"null".equals(ITEM_KIND)) {
			typeKindName = "商品类别";
			plmTypeName = "";
			for (int i = 0; i < itemKindArr.length; i++) {
				if ("1".equals(itemKindArr[i])) {
					plmTypeName = "商品  ";
				} else if ("2".equals(itemKindArr[i])) {
					plmTypeName = plmTypeName + "促销品  ";
				} else if ("3".equals(itemKindArr[i])) {
					plmTypeName = plmTypeName + "样品  ";
				} else if ("4".equals(itemKindArr[i])) {
					plmTypeName = plmTypeName + "罚没烟  ";
				} else if ("5".equals(itemKindArr[i])) {
					plmTypeName = plmTypeName + "其他  ";
				}
			}
			String itemTypeSelected = getItemTypeSelected(comId, typeKindName,
					plmTypeName);
			itemFilterCondition = itemFilterCondition + itemTypeSelected;
		}
		if (PRODUCT_TYPE != null && !"".equals(PRODUCT_TYPE)
				&& !"null".equals(PRODUCT_TYPE)) {
			typeKindName = "产品类型";
			plmTypeName = "";
			for (int i = 0; i < productTypeArr.length; i++) {
				if ("10".equals(productTypeArr[i])) {
					plmTypeName = "烤烟型  ";
				} else if ("20".equals(productTypeArr[i])) {
					plmTypeName = plmTypeName + "混合型  ";
				} else if ("30".equals(productTypeArr[i])) {
					plmTypeName = plmTypeName + "外香型  ";
				} else if ("40".equals(productTypeArr[i])) {
					plmTypeName = plmTypeName + "雪茄型  ";
				} else if ("50".equals(productTypeArr[i])) {
					plmTypeName = plmTypeName + "雪茄烟  ";
				} else if ("51".equals(productTypeArr[i])) {
					plmTypeName = plmTypeName + "手卷雪茄  ";
				}
			}
			String itemTypeSelected = getItemTypeSelected(comId, typeKindName,
					plmTypeName);
			itemFilterCondition = itemFilterCondition + itemTypeSelected;
		}
		if(log.isDebugEnabled()) {
			log.debug("====itemFilterCondition23==" + itemFilterCondition);
		}
		if (itemId != null && !"".equals(itemId)) {
			typeKindName = "商品";
			plmTypeName = " " + itemName;
			whereSql = whereSql + " AND PI.ITEM_ID IN (" + itemId + ")";
			String itemTypeSelected = getItemTypeSelected(comId, typeKindName,
					plmTypeName);
			itemFilterCondition = itemFilterCondition + itemTypeSelected;
		}
		String brandownerList = req.getParameter("BROWNDERown"); // 品牌拥有者
		if(log.isDebugEnabled()) {
			log.debug("brandownerList" + brandownerList);
		}
		String inner = "";
		if (null != brandownerList && !"".equals(brandownerList)
				&& !"null".equals(brandownerList)) {
			String[] tempIner = brandownerList.split(",");
			StringBuffer innertype = new StringBuffer();
			for (int i = 0; i < tempIner.length - 1; ++i) {
				innertype.append("'");
				innertype.append(tempIner[i]);
				innertype.append("'");
				innertype.append(",");
			}
			innertype.append("'");
			innertype.append(tempIner[(tempIner.length - 1)]);
			innertype.append("'");
			inner = innertype.toString();
		}
		if(log.isDebugEnabled()) {
			log.debug("ssssssssssss" + inner);
		}
		String brandNames = "";
		if (inner != null && !"".equals(inner) && !"null".equals(inner)) {
			StringBuffer sb2 = new StringBuffer();
			sb2.append("SELECT BRDOWNER_NAME FROM PLM_BRANDOWNER WHERE BRDOWNER_ID IN ("
					+ inner + ")");
			if(log.isDebugEnabled()) {
				log.debug("sb2................." + sb2);
			}
			List brandNameList = SqlClient.commonSelect(sb2.toString());
			if(log.isDebugEnabled()) {
				log.debug("===brandNameList===" + brandNameList);
			}
			for (int m = 0; m < brandNameList.size(); m++) {
				Map map3 = new HashMap();
				map3 = (Map) brandNameList.get(m);
				brandName = (String) map3.get("BRDOWNER_NAME");
				log.debug("===brandName===" + brandName);
				brandNames += brandName + " ";
			}
			// plmTypeName = plmTypeName + "品牌:" + brandNames;
		}
		if(log.isDebugEnabled()) {
			log.debug("brandNames" + brandNames);
			log.debug("inner" + inner);
		}
		
		if (brandownerList != null && !"".equals(brandownerList)
				&& !"null".equals(brandownerList)) {
			typeKindName = "品牌拥有者";
			plmTypeName = " " + brandNames;
			whereSql = whereSql + " AND PI.BRDOWNER_ID IN (" + inner + ")";
			String itemTypeSelected = getItemTypeSelected(comId, typeKindName,
					plmTypeName);
			itemFilterCondition = itemFilterCondition + itemTypeSelected;
		}
		if (brandId != null && !"".equals(brandId)) {
			typeKindName = "品牌";
			plmTypeName = " " + brandName;
			whereSql = whereSql + " AND PLB.BRAND_ID IN (" + brandId + ")";
			String itemTypeSelected = getItemTypeSelected(comId, typeKindName,
					plmTypeName);
			itemFilterCondition = itemFilterCondition + itemTypeSelected;
		}
		if (isTall != null && !"".equals(isTall)) {
			typeKindName = "高价烟";
			plmTypeName = "";
			if ("0".equals(isTall)) {
				plmTypeName = " 否";
				whereSql = whereSql + " AND PI.IS_TALL ='" + isTall + "'";
			}
			if ("1".equals(isTall)) {
				plmTypeName = " 是";
				whereSql = whereSql + " AND PI.IS_TALL ='" + isTall + "'";
			}
			String itemTypeSelected = getItemTypeSelected(comId, typeKindName,
					plmTypeName);
			itemFilterCondition = itemFilterCondition + itemTypeSelected;
		}

		if (isKey != null && !"".equals(isKey)) {
			typeKindName = "重点品牌";
			plmTypeName = "";
			if ("0".equals(isKey)) {
				plmTypeName = " 否";
				// whereSql = whereSql + " AND PBBN.SEQ_TYPE!='05'";
			}
			if ("1".equals(isKey)) {
				plmTypeName = " 是";
				// whereSql = whereSql + " AND PBBN.SEQ_TYPE='05'";
			}
			String itemTypeSelected = getItemTypeSelected(comId, typeKindName, plmTypeName);
			itemFilterCondition = itemFilterCondition + itemTypeSelected;
		}

		if ((PUHMin != null && !"".equals(PUHMin))
				|| (PUHMax != null && !"".equals(PUHMax))) {
			typeKindName = "工商调拨价";
			plmTypeName = "";
			if (PUHMin != null && !"".equals(PUHMin)) {
				plmTypeName = plmTypeName + " 最小值：" + PUHMin + "  ";
				whereSql = whereSql + " AND PIC.PRICE_PUH >= " + PUHMin;
			}
			if (PUHMax != null && !"".equals(PUHMax)) {
				plmTypeName = plmTypeName + " 最大值：" + PUHMax + "  ";
				whereSql = whereSql + " AND PIC.PRICE_PUH <= " + PUHMax;
			}
			String itemTypeSelected = getItemTypeSelected(comId, typeKindName,
					plmTypeName);
			itemFilterCondition = itemFilterCondition + itemTypeSelected;
		}

		if ((TRNMin != null && !"".equals(TRNMin))
				|| (TRNMax != null && !"".equals(TRNMax))) {
			typeKindName = "商商调拨价";
			plmTypeName = "";
			if (TRNMin != null && !"".equals(TRNMin)) {
				plmTypeName = plmTypeName + " 最小值：" + TRNMin + "  ";
				whereSql = whereSql + " AND PIC.PRICE_TRN >= " + TRNMin;
			}
			if (TRNMax != null && !"".equals(TRNMax)) {
				plmTypeName = plmTypeName + " 最大值：" + TRNMax + "  ";
				whereSql = whereSql + " AND PIC.PRICE_TRN <= " + TRNMax;
			}
			String itemTypeSelected = getItemTypeSelected(comId, typeKindName,
					plmTypeName);
			itemFilterCondition = itemFilterCondition + itemTypeSelected;
		}

		if ((TRADEMin != null && !"".equals(TRADEMin))
				|| (TRADEMax != null && !"".equals(TRADEMax))) {
			typeKindName = "批发价格";
			plmTypeName = "";
			if (TRADEMin != null && !"".equals(TRADEMin)) {
				plmTypeName = plmTypeName + " 最小值：" + TRADEMin + "  ";
				whereSql = whereSql + " AND PIC.PRICE_TRADE >= " + TRADEMin;
			}
			if (TRADEMax != null && !"".equals(TRADEMax)) {
				plmTypeName = plmTypeName + " 最大值：" + TRADEMax + "  ";
				whereSql = whereSql + " AND PIC.PRICE_TRADE <= " + TRADEMax;
			}
			String itemTypeSelected = getItemTypeSelected(comId, typeKindName,
					plmTypeName);
			itemFilterCondition = itemFilterCondition + itemTypeSelected;
		}

		if ((RETAILMin != null && !"".equals(RETAILMin))
				|| (RETAILMax != null && !"".equals(RETAILMax))) {
			typeKindName = "建议零售价";
			plmTypeName = "";
			if (RETAILMin != null && !"".equals(RETAILMin)) {
				plmTypeName = plmTypeName + " 最小值：" + RETAILMin + "  ";
				whereSql = whereSql + " AND PIC.PRICE_RETAIL >= " + RETAILMin;
			}
			if (RETAILMax != null && !"".equals(RETAILMax)) {
				plmTypeName = plmTypeName + " 最大值：" + RETAILMax + "  ";
				whereSql = whereSql + " AND PIC.PRICE_RETAIL <= " + RETAILMax;
			}
			String itemTypeSelected = getItemTypeSelected(comId, typeKindName,
					plmTypeName);
			itemFilterCondition = itemFilterCondition + itemTypeSelected;
		}

		if ((Min1 != null && !"".equals(Min1))
				|| (Max1 != null && !"".equals(Max1))) {
			typeKindName = "焦油含量";
			plmTypeName = "";
			if (Min1 != null && !"".equals(Min1)) {
				plmTypeName = plmTypeName + " 最小值：" + Min1 + "  ";
				whereSql = whereSql + " AND PI.TAR_CONT >= " + Min1;
			}
			if (Max1 != null && !"".equals(Max1)) {
				plmTypeName = plmTypeName + " 最大值：" + Max1 + "  ";
				whereSql = whereSql + " AND PI.TAR_CONT <= " + Max1;
			}
			String itemTypeSelected = getItemTypeSelected(comId, typeKindName,
					plmTypeName);
			itemFilterCondition = itemFilterCondition + itemTypeSelected;
		}
		if(log.isDebugEnabled()) {
			log.debug("====itemFilterCondition24afa==" + itemFilterCondition);
		}
		String a = "";
		if (kind != null && !"".equals(kind) && !"null".equals(kind)) {
			for (int i = 0; i < kindArr.length; i++) {
				if ("1".equals(kindArr[i])) { // 一类
					a = a + "'1',";
				} else if ("2".equals(kindArr[i])) { // 二类
					a = a + "'2',";
				} else if ("3".equals(kindArr[i])) { // 三类
					a = a + "'3',";
				} else if ("4".equals(kindArr[i])) { // 四类
					a = a + "'4',";
				} else if ("5".equals(kindArr[i])) { // 五类
					a = a + "'5',";
				} else if ("6".equals(kindArr[i])) { // 无价类
					a = a + "'6',";
				}
			}

			a = a.substring(0, a.length() - 1);
			whereSql = whereSql + " AND PI.KIND IN (" + a + ")";
		}
		if (YIELDLY_TYPE != null && !"".equals(YIELDLY_TYPE)
				&& !"null".equals(YIELDLY_TYPE)) {
			String b = "";
			for (int i = 0; i < yieldlyTypeArr.length; i++) {
				if ("0".equals(yieldlyTypeArr[i])) { // 省内
					b = b + "'0',";
				} else if ("1".equals(yieldlyTypeArr[i])) { // 省外
					b = b + "'1',";
				} else if ("3".equals(yieldlyTypeArr[i])) { // 国外
					b = b + "'3',";
				}
			}
			b = b.substring(0, b.length() - 1);
			whereSql = whereSql + " AND PI.YIELDLY_TYPE IN (" + b + ")";
		}
		if (ITEM_KIND != null && !"".equals(ITEM_KIND)
				&& !"null".equals(ITEM_KIND)) {
			String c = "";
			for (int i = 0; i < itemKindArr.length; i++) {
				if(log.isDebugEnabled()) {
					log.debug("itemKindArr[i]" + itemKindArr[i]);
				}
				if ("1".equals(itemKindArr[i])) { // 烤烟
					c = c + "'1',";
				} else if ("2".equals(itemKindArr[i])) { // 混合型
					c = c + "'2',";
				} else if ("3".equals(itemKindArr[i])) { // 外香型
					c = c + "'3',";
				} else if ("4".equals(itemKindArr[i])) { // 雪茄型
					c = c + "'4',";
				} else if ("5".equals(itemKindArr[i])) { // 雪茄烟
					c = c + "'5',";
				}
			}
			c = c.substring(0, c.length() - 1);
			whereSql = whereSql + " AND PI.ITEM_KIND IN (" + c + ")";
		}
		if (PRODUCT_TYPE != null && !"".equals(PRODUCT_TYPE)
				&& !"null".equals(PRODUCT_TYPE)) {
			String c = "";
			for (int i = 0; i < productTypeArr.length; i++) {
				if ("10".equals(productTypeArr[i])) { // 烤烟
					c = c + "'10',";
				} else if ("20".equals(productTypeArr[i])) { // 混合型
					c = c + "'20',";
				} else if ("30".equals(productTypeArr[i])) { // 外香型
					c = c + "'30',";
				} else if ("40".equals(productTypeArr[i])) { // 雪茄型
					c = c + "'40',";
				} else if ("50".equals(productTypeArr[i])) { // 雪茄烟
					c = c + "'50',";
				} else if ("51".equals(productTypeArr[i])) { // 手卷雪茄
					c = c + "'51',";
				}
			}
			c = c.substring(0, c.length() - 1);
			whereSql = whereSql + " AND PI.PRODUCT_TYPE IN (" + c + ")";
		}
		if(log.isDebugEnabled()) {
			log.debug("====itemFilterCondition25==" + itemFilterCondition);
		}
		Map<String, String> rtnMap = new HashMap<String, String>();
		rtnMap.put("itemFilterCondition", itemFilterCondition);
		rtnMap.put("itemFilterSqlSearch", getItemFilterSql(comId, tableList, whereSql, isKey));
		try {
			JSONObject json = new JSONObject(rtnMap == null ? new HashMap() : rtnMap);
			if(log.isDebugEnabled()) {
				log.debug("getSelectedCustType=" + json);
			}
			rep.setContentType("application/json");
			PrintWriter out = rep.getWriter();
			out.write(json.toString());
			out.close();
		} catch (Exception e) {
			log.error("TagTestCmd----foritemFilterCondition---e.printStackTrace()=", e);
		}
		return "null";
	}

//	/**
//	 * 根据TYPE_KIND取类型信息
//	 * 
//	 * @param comId
//	 * @param typeKind
//	 * @return
//	 */
//	@SuppressWarnings("rawtypes")
//	private Map getTypeKindInfoMap(String comId, String typeKind) {
//		StringBuffer sb = new StringBuffer();
//		sb.append("SELECT TYPE_KIND_NAME,REF_FIELD FROM CRM_TYPE_KIND ");
//		sb.append("WHERE COM_ID = '").append(comId).append("' ");
//		sb.append("AND TYPE_KIND = '").append(typeKind).append("' ");
//		List typeKindList = SqlClient.commonSelect(sb.toString());
//		Map typeKindInfoMap = new HashMap();
//		if (typeKindList != null && typeKindList.size() > 0) {
//			typeKindInfoMap = (Map) typeKindList.get(0);
//		}
//		return typeKindInfoMap;
//	}

	/**
	 * 组织每个类型选择的商品分类
	 * 
	 * @param comId
	 * @param typeKind
	 * @param custTypes
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private String getItemTypeSelected(String comId, String typeKindName,
			String plmTypeName) {
		String itemTypeSelected = orgTypeKindCondition(typeKindName);
		itemTypeSelected = itemTypeSelected + plmTypeName;
		return orgCustTypeCondition(itemTypeSelected);
	}

	/**
	 * 组织返回的SQL
	 * 
	 * @param comId
	 * @param tableList
	 * @param whereSql
	 * @return
	 */
	private String getItemFilterSql(String comId, List<String> tableList,
			String whereSql, String isKey) {
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
	 * 取使用的商品类型
	 * 
	 * @param comId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private List getTypeKindList(String comId) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT PFC.INDEX_ID,PFC.INDEX_VALUE,PFC.CONTROL_KIND,CWF.KEY_NAME,CWF.FIELD_NAME ");
		sb.append("FROM PLM_FILTER_CONDITION PFC,CRM_WEB_FIELD CWF ");
		sb.append("WHERE ");
		sb.append("PFC.INDEX_ID = CWF.KEY_NAME ");
		sb.append("AND CWF.COM_ID = '").append(comId).append("' ");
		return SqlClient.commonSelect(sb.toString());
	}

	/**
	 * 组织页面返回结果样式 - 分类类型
	 * 
	 * @param typeKindName
	 * @return
	 */
	private String orgTypeKindCondition(String typeKindName) {
		return "<div class='unitBox' style='word-break:break-all;'><span class='unitHead'>&nbsp;&nbsp;&nbsp;&nbsp;"
				+ typeKindName + ":</span><span class='unitCount'>";
	}

	/**
	 * 组织页面返回结果样式 - 分类明细
	 * 
	 * @param typeKindName
	 * @return
	 */
	private String orgCustTypeCondition(String condition) {
		return condition + "</span></div>";
	}
	
	/**
	 * 获取数据List每条数据是Map{id= , name= }
	 * @param dictId : PLM_BRANDOWNER 品牌拥有者, 其他是BASE_DICT.DICT_ID
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private List getDataList(String dictId) {
		List dataList = new ArrayList();
		//品牌拥有者
		if("PLM_BRANDOWNER".equals(dictId)){
			String plmBrandownerInfo = "SELECT BRDOWNER_NAME,BRDOWNER_ID FROM PLM_BRANDOWNER ORDER BY BRDOWNER_ID";
			List plmBrandownerInfoList = SqlClient.commonSelect(plmBrandownerInfo);
			if(null != plmBrandownerInfoList){
				int size = plmBrandownerInfoList.size();
				for (int i = 0; i < size; i++) {
					Map brandOwnerObj = new HashMap();
					Map brandOwnerMap = (Map)plmBrandownerInfoList.get(i);
					brandOwnerObj.put("id", brandOwnerMap.get("BRDOWNER_ID"));
					brandOwnerObj.put("name", brandOwnerMap.get("BRDOWNER_NAME"));
					dataList.add(brandOwnerObj);
				}
			}
		} else {//字典
			String dictSql = "SELECT DICT_KEY, DICT_VALUE FROM BASE_DICT WHERE DICT_ID='"+dictId+"' ORDER BY DICT_KEY";
			List dictList = SqlClient.commonSelect(dictSql);
			if(null != dictList){
				int size = dictList.size();
				for (int i = 0; i < size; i++) {
					Map dictMap = new HashMap();
					Map tempMap = (Map)dictList.get(i);
					dictMap.put("id", tempMap.get("DICT_KEY"));
					dictMap.put("name", tempMap.get("DICT_VALUE"));
					dataList.add(dictMap);
				}
			}
		}
		return dataList;
	}
}
