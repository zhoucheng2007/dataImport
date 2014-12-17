package com.shop.base.selectquerytree;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.MidiDevice.Info;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.loushang.bsp.service.OrganProvider;
import org.loushang.util.IErrorHandler;
import org.loushang.util.IMessageHandler;
import org.loushang.waf.mvc.MultiCommand;
import org.loushang.waf.mvc.ViewHelper;

import com.shop.base.batis.SqlClient;
import com.shop.base.cmd.BaseCommandImpl;
import com.shop.base.tool.PubConstants;

/**
 * 功能：带下拉框，和确定，清除，取消按钮的树形下拉组件后台
 * 
 * @author 贾林敬
 * @date 2013-6-4 下午14:59:36
 */
public class SelectQueryTreeCmd extends BaseCommandImpl {
	private static Log log = LogFactory.getLog(SelectQueryTreeCmd.class);
	/**
	 * 功能：公用查询条件，树形下拉框组织数据
	 * 
	 * @author 贾林敬
	 * @date 2013-6-4  下午14:59:36
	 * @param req
	 * @param rep
	 * @param errorHandler
	 * @param messageHandler
	 * @param viewHelper
	 * @return
	 * @throws JSONException IOException
	 */
	//组织厂家（商品拥有者）树
	public void getBrandOwner(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper) throws IOException, JSONException{
	 String type=(String)req.getParameter("type");
	 String json="";
	 if("root".equals(type)){
		String plmBrandownerInfo = "SELECT BRDOWNER_NAME,BRDOWNER_ID FROM PLM_BRANDOWNER ";
		List<Map> plmBrandownerInfoList = SqlClient.commonSelect(plmBrandownerInfo);      //品牌拥有者的List
		JSONArray brandOwnerList=new JSONArray();                                         //初始化要输出大浏览器的json数组
		try {
		for(int i=0,l=plmBrandownerInfoList.size();i<l;i++){
			JSONObject brandOwnerObj = new JSONObject();
			Map brandOwnerMap=plmBrandownerInfoList.get(i);
				brandOwnerObj.put("id",brandOwnerMap.get("BRDOWNER_ID"));
				brandOwnerObj.put("name",brandOwnerMap.get("BRDOWNER_NAME"));
				brandOwnerObj.put("type","brandowner");
				brandOwnerList.put(brandOwnerObj);
			}
		PrintWriter out = rep.getWriter();
        out.write(brandOwnerList.toString());
        if (log.isDebugEnabled()) {
			log.debug("getBrandOwner JSONObject:" + brandOwnerList.toString());
		}
        out.close();	
		} catch (JSONException e) {
			if (log.isDebugEnabled()) {
				log.debug("getBrandOwner JSONObject Error:" + e.getMessage());
			}
			e.printStackTrace();
		}
	 }	
	 
	}
	//品牌树
	public void getBrand (HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper) throws IOException, JSONException{
	 String type=(String)req.getParameter("type");
	 String sql= (String)req.getParameter("sql");
	 String json="";
	 if("root".equals(type)){
		String brandownerInfo = "SELECT BRDOWNER_NAME,BRDOWNER_ID FROM PLM_BRANDOWNER ";
		List<Map> brandownerInfoList = SqlClient.commonSelect(brandownerInfo);          //品牌有着者的List
		JSONArray brandOwnerList=new JSONArray();                                       //初始化要输出到浏览器的品牌拥有者的json数组
		for(int i=0,l=brandownerInfoList.size();i<l;i++){
			JSONObject brandOwnerObj = new JSONObject();
			Map brandOwnerMap=brandownerInfoList.get(i);
				brandOwnerObj.put("id",brandOwnerMap.get("BRDOWNER_ID"));
				brandOwnerObj.put("name",brandOwnerMap.get("BRDOWNER_NAME"));
				brandOwnerObj.put("isParent","true");
				brandOwnerObj.put("type","brandowner");
				brandOwnerList.put(brandOwnerObj);
			}
		PrintWriter out = rep.getWriter();
        out.write(brandOwnerList.toString());
        out.close();	
        if (log.isDebugEnabled()) {
			log.debug("getBrandOwner JSONObject:" + brandOwnerList.toString());
		}	
	}else if("brandowner".equals(type)){
		String brandownerId =req.getParameter("id");
		String brand = "SELECT BRAND_ID,BRAND_NAME FROM PLM_BRAND A,PLM_BRANDOWNER B WHERE A.BRDOWNER_ID =B.BRDOWNER_ID AND B.BRDOWNER_ID='"+brandownerId+"'";
		List<Map> brandInfoList = SqlClient.commonSelect(brand);
		JSONArray brandList=new JSONArray();                         //初始化输出到浏览器的品牌的json数组
		for(int i=0,l=brandInfoList.size();i<l;i++){
			JSONObject brandObj = new JSONObject();
			Map brandMap=brandInfoList.get(i);
			brandObj.put("id",brandMap.get("BRAND_ID"));
			brandObj.put("name",brandMap.get("BRAND_NAME"));
			brandObj.put("type","brand");
			brandList.put(brandObj);
			}
		PrintWriter out = rep.getWriter();
        out.write(brandList.toString());
        out.close();	
        if (log.isDebugEnabled()) {
        	
			log.debug("getBrandOwner JSONObject:" + brandList.toString());
		}
		
	}
	
   }
	//规格（商品）树
	public void getItem (HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper) throws IOException, JSONException{
	 String type=(String)req.getParameter("type");
	 String json="";
	 if("root".equals(type)){
		String brandownerInfo = "SELECT BRDOWNER_NAME,BRDOWNER_ID FROM PLM_BRANDOWNER ";
		List<Map> brandownerInfoList = SqlClient.commonSelect(brandownerInfo);
		JSONArray brandOwnerList=new JSONArray();                                            //初始化输出到浏览器的品牌拥有者的数组
		for(int i=0,l=brandownerInfoList.size();i<l;i++){
			JSONObject brandOwnerObj = new JSONObject();
			Map brandOwnerMap=brandownerInfoList.get(i);
				brandOwnerObj.put("id",brandOwnerMap.get("BRDOWNER_ID"));
				brandOwnerObj.put("name",brandOwnerMap.get("BRDOWNER_NAME"));
				brandOwnerObj.put("isParent","true");
				brandOwnerObj.put("type","brandowner");
				brandOwnerList.put(brandOwnerObj);
			}
		PrintWriter out = rep.getWriter();
        out.write(brandOwnerList.toString());
        out.close();	
        if (log.isDebugEnabled()) {
			log.debug("getBrandOwner JSONObject:" + brandOwnerList.toString());
		}	
	}else if("brandowner".equals(type)){
		String brandownerId =req.getParameter("id");
		String brand = "SELECT BRAND_ID,BRAND_NAME FROM PLM_BRAND A,PLM_BRANDOWNER B WHERE A.BRDOWNER_ID =B.BRDOWNER_ID AND B.BRDOWNER_ID='"+brandownerId+"'";
		List<Map> brandInfoList = SqlClient.commonSelect(brand);
		JSONArray brandList=new JSONArray();                     //初始化输出到浏览器的品牌的数组
		for(int i=0,l=brandInfoList.size();i<l;i++){
			JSONObject brandObj = new JSONObject();
			Map brandMap=brandInfoList.get(i);
			brandObj.put("id",brandMap.get("BRAND_ID"));
			brandObj.put("name",brandMap.get("BRAND_NAME"));
			brandObj.put("isParent","true");
			brandObj.put("type","brand");
			brandList.put(brandObj);
			}
		PrintWriter out = rep.getWriter();
        out.write(brandList.toString());
        out.close();	
        if (log.isDebugEnabled()) {
			log.debug("getBrandOwner JSONObject:" + brandList.toString());
		}	
	}else if("brand".equals(type)){
		String brandId =req.getParameter("id");
		String item = "SELECT ITEM_ID,ITEM_NAME FROM PLM_BRAND A,PLM_ITEM B WHERE A.BRAND_ID =B.BRAND_ID AND A.BRAND_ID='"+brandId+"'";
		List<Map> itemInfoList = SqlClient.commonSelect(item);
		JSONArray itemList = new JSONArray();                         //初始化输出到浏览器的商品的数组
		for(int i=0,l=itemInfoList.size();i<l;i++){
			JSONObject itemObj = new JSONObject();
			Map itemMap=itemInfoList.get(i);
			itemObj.put("id",itemMap.get("ITEM_ID"));
			itemObj.put("name",itemMap.get("ITEM_NAME"));
			itemObj.put("type","item");
			itemList.put(itemObj);
			}
		PrintWriter out = rep.getWriter();
        out.write(itemList.toString());
        out.close();	
        if (log.isDebugEnabled()) {
			log.debug("getBrandOwner JSONObject:" + itemList.toString());
		}
		
	}
	
   }
	
	//返回选中厂家
	public void getBrandOwnerS(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper) throws IOException, JSONException{
	 String sql= (String)req.getParameter("sql");
	 List<Map> sqlInfoList = SqlClient.commonSelect(sql);
	 if (log.isDebugEnabled()) {
			log.debug(" 11111:" + sql.toString());
		}
	 String sqlName="";
	 StringBuffer sb = new StringBuffer();
	 for(int i=0;i<sqlInfoList.size();i++){
		Map<String,String> sqlInfoMap = sqlInfoList.get(i);	
		sb.append(",");
		sb.append(sqlInfoMap.get("BRDOWNER_NAME"));
	 }
	 sqlName=sb.toString();
	 sqlName=sqlName.substring(1);
	 //String selectedName = type;
	 PrintWriter out = rep.getWriter();
     out.write(sqlName.toString());
     if (log.isDebugEnabled()) {
		log.debug("getBrandOwner returnname:" + sqlName.toString());
	 }
     out.close();
	}
	
	//返回选中品牌
	public void getBrandS(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper) throws IOException, JSONException{
	 String sql= (String)req.getParameter("sql");
	 List<Map> sqlInfoList = SqlClient.commonSelect(sql);
	 if (log.isDebugEnabled()) {
			log.debug(" 11111:" + sql.toString());
		}
	 String sqlName="";
	 StringBuffer sb = new StringBuffer();
	 for(int i=0;i<sqlInfoList.size();i++){
		Map<String,String> sqlInfoMap = sqlInfoList.get(i);	
		sb.append(",");
		sb.append(sqlInfoMap.get("BRAND_NAME"));
	 }
	 sqlName=sb.toString();
	 sqlName=sqlName.substring(1);
	 //String selectedName = type;
	 PrintWriter out = rep.getWriter();
     out.write(sqlName.toString());
     if (log.isDebugEnabled()) {
		log.debug("getBrand returnname:" + sqlName.toString());
	 }
     out.close();
	}
	
	//返回选中商品
	public void getItemS(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper) throws IOException, JSONException{
	 String sql= (String)req.getParameter("sql");
	 List<Map> sqlInfoList = SqlClient.commonSelect(sql);
	 String sqlName="";
	 StringBuffer sb = new StringBuffer();
	 for(int i=0;i<sqlInfoList.size();i++){
		Map<String,String> sqlInfoMap = sqlInfoList.get(i);	
		sb.append(",");
		sb.append(sqlInfoMap.get("ITEM_NAME"));
	 }
	 sqlName=sb.toString();
	 sqlName=sqlName.substring(1);
	 //String selectedName = type;
	 PrintWriter out = rep.getWriter();
     out.write(sqlName.toString());
     if (log.isDebugEnabled()) {
		log.debug("getItem returnname:" + sqlName.toString());
	 }
     out.close();
	}
}