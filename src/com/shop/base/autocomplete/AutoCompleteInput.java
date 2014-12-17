
package com.shop.base.autocomplete;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.loushang.util.IErrorHandler;
import org.loushang.util.IMessageHandler;
import org.loushang.waf.mvc.ViewHelper;

import com.lc.v6.jdbc.mybatis.V6SqlSessionUtil;
import com.shop.base.autocomplete.tools.AutoCompleteTools;
import com.shop.base.bsp.V6BspInfo;
import com.shop.base.cmd.BaseCommandImpl;

/**
 * @title: 联想帮助框实现方法
 * @author: kuangzhy
 * @data: 2013-08-26
 * @修改: 
 *
 */
public class AutoCompleteInput extends BaseCommandImpl {
	
	private static final long serialVersionUID = 1L;

	private static Log log = LogFactory.getLog(AutoCompleteInput.class);

	/**
	 * 商品联想帮助框
	 * @param req
	 * @param rep
	 * @param errorHandler
	 * @param messageHandler
	 * @param viewHelper
	 * @throws JSONException
	 */
	public void itemIdAssociate(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper) throws JSONException{
		
		String comId = V6BspInfo.getComId();//公司编码
		String itemId=req.getParameter("q");//页面输入的模糊助记码
		
		try {
			itemId=URLDecoder.decode(itemId,"UTF-8");
		} catch (Exception e) {
			//
		}
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT "); 
		sb.append(" ITEM_ID KEY,ITEM_NAME KEY2,SHORT_CODE||' '||ITEM_ID||' '||ITEM_NAME VALUE FROM ( "); 
		sb.append(" SELECT "); 
		sb.append(" PI.ITEM_ID,PIC.SHORT_CODE,PI.ITEM_NAME  "); 
		sb.append(" FROM "); 
		sb.append(" PLM_ITEM PI,PLM_ITEM_COM PIC  "); 
		sb.append(" WHERE PIC.COM_ID='"+comId+"' ");
		sb.append(" AND PI.ITEM_ID = PIC.ITEM_ID  ");
		sb.append(" AND PI.ITEM_KIND!='2' "); //过滤促销品
		sb.append(" AND PIC.SHORT_CODE LIKE '%"+itemId+"%' ORDER BY PIC.ITEM_ID ASC) "); 
		sb.append(" UNION ");
		sb.append(" SELECT "); 
		sb.append(" ITEM_ID KEY,ITEM_NAME KEY2,SHORT_CODE||' '||ITEM_ID||' '||ITEM_NAME VALUE FROM ( "); 
		sb.append(" SELECT "); 
		sb.append(" PI.ITEM_ID,PIC.SHORT_CODE,PI.ITEM_NAME  "); 
		sb.append(" FROM "); 
		sb.append(" PLM_ITEM PI,PLM_ITEM_COM PIC  "); 
		sb.append(" WHERE PIC.COM_ID='"+comId+"' ");
		sb.append(" AND PI.ITEM_ID = PIC.ITEM_ID  ");
		sb.append(" AND PI.ITEM_KIND!='2' "); //过滤促销品
		sb.append(" AND PI.ITEM_ID LIKE '%"+itemId+"%' ORDER BY PIC.ITEM_ID ASC) "); 
		sb.append(" UNION ");
		sb.append(" SELECT "); 
		sb.append(" ITEM_ID KEY,ITEM_NAME KEY2,SHORT_CODE||' '||ITEM_ID||' '||ITEM_NAME VALUE FROM ( "); 
		sb.append(" SELECT "); 
		sb.append(" PI.ITEM_ID,PIC.SHORT_CODE,PI.ITEM_NAME  "); 
		sb.append(" FROM "); 
		sb.append(" PLM_ITEM PI,PLM_ITEM_COM PIC  "); 
		sb.append(" WHERE PIC.COM_ID='"+comId+"' ");
		sb.append(" AND PI.ITEM_ID = PIC.ITEM_ID  ");
		sb.append(" AND PI.ITEM_KIND!='2' "); //过滤促销品
		sb.append(" AND PI.ITEM_NAME LIKE '%"+itemId+"%' ORDER BY PIC.ITEM_ID ASC) "); 
		if(V6SqlSessionUtil.dbTypeIsDB2()){
			sb.append("FETCH FIRST 15 ROWS ONLY ");
		}else{
			sb.append(" WHERE ROWNUM<=15");
		}
		List resultList=AutoCompleteTools.getReturnList(sb.toString());
		/**
		 * 将list转化为json格式，如果不想用这个方法，可以手动转化，格式要求如下:
		 * [{"KEY":"001","VALUE":"中华"},{"KEY":"002","VALUE":"红塔山"}]
		 */
		String jsonStr=AutoCompleteTools.object2json(resultList);
		try {
			PrintWriter out = rep.getWriter();
			out.write(jsonStr);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通用联想功能实现，借助pub_common_help表
	 * @param req
	 * @param rep
	 * @param errorHandler
	 * @param messageHandler
	 * @param viewHelper
	 * @throws JSONException
	 */
	public void commonHelp(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper) throws JSONException{
		
		String helpId=(String)req.getParameter("helpId");
		String q=(String)req.getParameter("q");
		String limit=(String)req.getParameter("limit");
		
		try {
			q=URLDecoder.decode(q,"UTF-8");
		} catch (Exception e) {
			//
		}
		
		StringBuffer sb=new StringBuffer();
		sb.append(" SELECT * FROM PUB_COMMON_HELP WHERE HELP_ID='").append(helpId).append("'");
		List tempList=AutoCompleteTools.getReturnList(sb.toString());
		StringBuffer sql=new StringBuffer();
		if(tempList!=null&&tempList.size()>0){
			Map configMap=(Map)tempList.get(0);
			sql.append(" SELECT * FROM");
			sql.append(" (SELECT ");
			sql.append(configMap.get("SHOW_FIELD"));
			sql.append(" FROM ");
			sql.append(configMap.get("HELP_TABLE").toString().replaceAll("@", ","));
			sql.append(" WHERE ");
			
			String where=(String)configMap.get("HELP_WHERE");
			String[] wbs=where.split("@");
			if(wbs.length>1){
				for(int i=1;i<wbs.length;i=i+2){
					String key=wbs[i];
					String value=req.getParameter(key);
					value=value.replaceAll("@q@", q);
					where=where.replaceAll("@"+key+"@", value);
				}
			}
			sql.append(where).append(")");
			
			if(V6SqlSessionUtil.dbTypeIsDB2()){
				sql.append("FETCH FIRST ").append(limit).append(" ROWS ONLY ");
			}else{
				sql.append(" WHERE ROWNUM<=").append(limit);
			}
		}
		
		List resultList=AutoCompleteTools.getReturnList(sql.toString());
		
		String jsonStr=AutoCompleteTools.object2json(resultList);
		try {
			PrintWriter out = rep.getWriter();
			out.write(jsonStr);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public void afterPropertiesSet() throws Exception {
		
	}
	//设置界面所用的下拉列表
	private void setCacheDict(){
		
	}
	
}
