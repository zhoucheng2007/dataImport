package com.shop.base.taglib;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.loushang.bsp.service.GetBspInfo;
import org.loushang.waf.ComponentFactory;

/**
 * 功能：保存用户自定义选的widget
 * @author 郑斌
 * @date 2012-12-14 上午9:39:30
 */
public class UserWidgetSet extends HttpServlet{ 

	/**
	 * 功能：保存方法
	 * @author 郑斌
	 * @date 2012-12-14 上午9:41:46
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void save(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		Log log = LogFactory.getLog(UserWidgetSet.class);
		if (log.isInfoEnabled()) {
			log.info("UserWidgetSet - save - start");
		}
		// 获取报表的json数据
		String userSetJson = req.getParameter("userSet");
		if (log.isDebugEnabled()) {
			log.debug("userSetJson:" + userSetJson);
		}
		//System.out.println("userSet="+userSetJson);
		if (userSetJson == null) {
			log.error("UserWidgetSet - exportdata:reportJson==null!");
			userSetJson = "[]";
		}
		//取用户信息
		String userId = GetBspInfo.getBspInfo(req).getUserId();
		String screenUrl = req.getParameter("sUrl");
		int rtnI = 0;
		try {
			JSONObject userSetJO= new JSONObject("{}");
			JSONArray userSetJA = new JSONArray(userSetJson);
			JSONObject jo = null;
			for(int i = 0;i < userSetJA.length();i++){
				jo = userSetJA.getJSONObject(i);
				String index = jo.getString("seq");
				String path = jo.getString("path");
				userSetJO.put(index, path);
			}
			IUserWidgetDomain userWidgetDomain = (IUserWidgetDomain) ComponentFactory.getBean("userWidgetDomain");
			//获取用户设置的widgets列表
			String userSetWidgets = userWidgetDomain.getUserSetWidgets(userId,screenUrl);
			
			//如果不存在则insert，如果存在则修改（没有先delete后insert，是为了避免再引入事务模版）
			if(userSetWidgets == null){
				rtnI = userWidgetDomain.insertUserSet(userId, screenUrl, userSetJO.toString());
			}
			rtnI = userWidgetDomain.updateUserSet(userId, screenUrl, userSetJO.toString());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resp.setContentType("text/text");
	    PrintWriter out = resp.getWriter();
	    out.print(rtnI);
		if (log.isInfoEnabled()) {
			log.info("UserWidgetSet - save - end");
		}
	}

	//处理get请求
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		save(req,resp);
	}
	//处理post请求
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		save(req,resp);
	}
	
}
