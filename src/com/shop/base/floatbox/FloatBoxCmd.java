package com.shop.base.floatbox;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.loushang.bsp.service.BspInfo;
import org.loushang.bsp.service.OrganProvider;
import org.loushang.bsp.service.SecurityProvider;
import org.loushang.util.IErrorHandler;
import org.loushang.util.IMessageHandler;
import org.loushang.waf.mvc.MultiCommand;
import org.loushang.waf.mvc.ViewHelper;

import com.shop.base.batis.SqlClient;
import com.shop.base.bsp.V6BspInfo;
import com.shop.base.cmd.BaseCommandImpl;
import com.shop.base.tool.PubConstants;

/**
 * 功能：客户、商品、员工的浮动框后台类
 * 
 * @author 郑斌
 * @date 2013-1-8 上午11:18:36
 */
public class FloatBoxCmd extends MultiCommand {

	private static Log log = LogFactory.getLog(FloatBoxCmd.class);
	
	/**
	 * 功能：获取相关信息并转向到浮动框页面
	 * 
	 * @author 郑斌
	 * @date 2013-1-8 上午11:19:26
	 * @param req
	 * @param rep
	 * @param errorHandler
	 * @param messageHandler
	 * @param viewHelper
	 * @return
	 * @throws IOException
	 */
	public String getInfo(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper) throws IOException {
		if(log.isDebugEnabled()){
			log.debug("FloatBoxCmd - getInfo - start!");
		}
		//页面转向
		String forwardStr = "";
		String randomval = req.getParameter("randomval");
		String code = req.getParameter("code");
		String type = req.getParameter("type");
				
		if ("employee".equals(type)) {
			//获取员工信息
			String employeeName = V6BspInfo.getUserName();
			String userId = V6BspInfo.getUserId();
			Map map=SecurityProvider.getUserBaseAttr(userId);
			String mobile=(String) map.get("mobile");
			String comId= V6BspInfo.getComId();
			String comName = OrganProvider.getOrganNameByOrganId(comId);			
			String organId = V6BspInfo.getEmployeeOrganId();
    		String organName = OrganProvider.getOrganNameByOrganId(organId);
			req.setAttribute("employeeName", employeeName);
			req.setAttribute("mobile", mobile);
			req.setAttribute("comName", comName);
			req.setAttribute("organName", organName);
			forwardStr = "floatboxforward.employee";
		} else if ("cust".equals(type)) {
			// 获取客户信息
			String custInfo ="SELECT CUST_ID,CUST_NAME,SALE_DEPT_ID,SLSMAN_ID,ORDER_TEL,BUSI_ADDR,MANAGER FROM CO_CUST WHERE CUST_ID='"+code+"'";
			List<Map> custInfoList = SqlClient.commonSelect(custInfo);
			Map<String,String> custInfoMap = custInfoList.get(0);
			String custName = custInfoMap.get("CUST_NAME");
			String saleDeptId = custInfoMap.get("SALE_DEPT_ID");
			String slsmanId = custInfoMap.get("SLSMAN_ID");
			String orderTel = custInfoMap.get("ORDER_TEL");
			String busiAddr = custInfoMap.get("BUSI_ADDR");
			String manager = custInfoMap.get("MANAGER");
			String saleDeptName = OrganProvider.getOrganNameByOrganId(PubConstants.STRU_TYPE_SALE,saleDeptId);
			String slsmanName = OrganProvider.getPositionNameByPositionCode(slsmanId);
			req.setAttribute("custName", custName);
			req.setAttribute("slsmanId", slsmanId);
			req.setAttribute("slsmanName", slsmanName);
			req.setAttribute("saleDeptId", saleDeptId);
			req.setAttribute("saleDeptName", saleDeptName);
			req.setAttribute("orderTel", orderTel);
			req.setAttribute("busiAddr", busiAddr);
			req.setAttribute("manager", manager);
			forwardStr = "floatboxforward.cust";
		} else if ("item".equals(type)) {
			// 获取商品信息
			String comId= V6BspInfo.getComId();
			String itemInfo = "SELECT PI.ITEM_ID,PI.ITEM_NAME,PI.KIND,PI.BOX_BAR_IMG,T0.DICT_VALUE, PI.TAR_CONT,PIC.PRICE_TRADE,PIC.PRICE_RETAIL,PB.BRDOWNER_NAME,PIC.NET_DATE"
			+" FROM PLM_ITEM_COM PIC,PLM_BRANDOWNER PB,PLM_ITEM PI LEFT JOIN ( SELECT DICT_KEY,DICT_VALUE FROM BASE_DICT WHERE DICT_ID='PLM_ITEM_PRICE') "
			+"T0 ON PI.KIND = T0.DICT_KEY"
			+" WHERE PI.ITEM_ID = PIC.ITEM_ID AND PI.BRDOWNER_ID = PB.BRDOWNER_ID"
			+" AND PIC.COM_ID ='"+comId+"' AND PI.ITEM_ID = '"+code+"'";		
			List<Map> itemInfoList = SqlClient.commonSelect(itemInfo);
			if(itemInfoList.size()>0){
			   Map itemInfoMap = itemInfoList.get(0);
			   String itemName = (String) itemInfoMap.get("ITEM_NAME");//商品名称
			   String kind = (String) itemInfoMap.get("DICT_VALUE");//价类
			   BigDecimal tarCont = (BigDecimal)itemInfoMap.get("TAR_CONT");//焦油含量
			   tarCont = tarCont.setScale(2,BigDecimal.ROUND_HALF_UP);  
			   BigDecimal priceTrade = (BigDecimal) itemInfoMap.get("PRICE_TRADE");//批发价
			   priceTrade = priceTrade.setScale(2,BigDecimal.ROUND_HALF_UP);  
			   BigDecimal priceRetail = (BigDecimal) itemInfoMap.get("PRICE_RETAIL");//建议零售价
			   priceRetail = priceRetail.setScale(2,BigDecimal.ROUND_HALF_UP);  
			   String brdownerName = (String) itemInfoMap.get("BRDOWNER_NAME");//品牌拥有者
			   String netDate = (String) itemInfoMap.get("NET_DATE");//入网时间	   
			   String itemImg = (String) itemInfoMap.get("BOX_BAR_IMG");//商品图片名
			   
			   req.setAttribute("itemImg", itemImg);
			   req.setAttribute("itemName", itemName);
			   req.setAttribute("kind", kind);
			   req.setAttribute("tarCont", tarCont);
			   req.setAttribute("priceTrade", priceTrade);
			   req.setAttribute("priceRetail", priceRetail);
			   req.setAttribute("brdownerName", brdownerName);
			   req.setAttribute("netDate", netDate);			   			   
			}
			
			forwardStr = "floatboxforward.item";
		}
		if(log.isDebugEnabled()){
			log.debug("FloatBoxCmd - getInfo - end!");
		}
		return forwardStr;
	}
}