package com.shop.base.commonSelect.cust;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
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

import com.shop.base.cmd.BaseCommandImpl;

public class CustTagTool extends BaseCommandImpl{
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(CustTagTool.class);
	/**
	 * 取组织类型
	 * @return
	 */
	public String getOrganTypeByOrganId(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper){
		String comId = req.getParameter("comId")==null?"":req.getParameter("comId");
		String organType = OrganProvider.getOrganTypeByOrganId(comId);
		Map<String, String> rtnMap = new HashMap<String, String>();
		rtnMap.put("organType", organType);
		try{
        	JSONObject json = new JSONObject(rtnMap==null?new HashMap():rtnMap);
            rep.setContentType("application/json");
            PrintWriter out = rep.getWriter();
            out.write(json.toString());
            out.close();
        }
        catch(Exception e){
        	if(log.isInfoEnabled()){
            	log.info("TagTestCmd----getOrganTypeByOrganId---e.printStackTrace()="+e);
            }
            e.printStackTrace();
        }
        return "null";
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
	public String updateSessionInfo(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper){
		String comId = req.getParameter("comId")==null?"":req.getParameter("comId");
		String commonCustId = req.getParameter("commonCustId")==null?"":req.getParameter("commonCustId");
		String commonCustName = req.getParameter("commonCustName")==null?"":req.getParameter("commonCustName");
		String filterId = req.getParameter("filterId")==null?"":req.getParameter("filterId");
		String custFilterDesc = req.getParameter("custFilterDesc")==null?"":req.getParameter("custFilterDesc");
		String custFilterSqlSearch = req.getParameter("custFilterSqlSearch")==null?"":req.getParameter("custFilterSqlSearch");
		String isSelectCustSearch = req.getParameter("isSelectCustSearch")==null?"N":req.getParameter("isSelectCustSearch");
		String custTagCustFilterCss = req.getParameter("custTagCustFilterCss")==null?"":req.getParameter("custTagCustFilterCss");
		req.getSession().setAttribute("custTagComId", comId);
		req.getSession().setAttribute("custTagCommonCustId", commonCustId);
		req.getSession().setAttribute("custTagCommonCustName", commonCustName);
		req.getSession().setAttribute("custTagFilterId", filterId);
		req.getSession().setAttribute("custTagCustFilterDesc", custFilterDesc);
		req.getSession().setAttribute("custTagCustFilterSql", custFilterSqlSearch);
		req.getSession().setAttribute("isSelectCustSearch", isSelectCustSearch);
		req.getSession().setAttribute("custTagCustFilterCss", custTagCustFilterCss);
		try {
			PrintWriter out = rep.getWriter();
			out.print("");
			out.flush();
		} catch (IOException e) {
			log.debug("PrintWriter 失败");
		}
		return "null";
	}
	public String getCustTagCustFilterCss(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper){
		String custTagCustFilterCss = (String)req.getSession().getAttribute("custTagCustFilterCss");
		if(custTagCustFilterCss==null){
			custTagCustFilterCss = "";
		}
		Map<String, String> rtnMap = new HashMap<String, String>();
		rtnMap.put("custTagCustFilterCss", custTagCustFilterCss);
		try{
        	JSONObject json = new JSONObject(rtnMap==null?new HashMap():rtnMap);
            rep.setContentType("application/json");
            PrintWriter out = rep.getWriter();
            out.write(json.toString());
            out.close();
        }
        catch(Exception e){
        	if(log.isInfoEnabled()){
            	log.info("TagTestCmd----getOrganTypeByOrganId---e.printStackTrace()="+e);
            }
            e.printStackTrace();
        }
		return "null";
	}
}
