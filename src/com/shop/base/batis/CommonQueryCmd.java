/**
 * 说明：
 * SqlQueryCmd.java 2012-12-3 上午5:51:03
 */
package com.shop.base.batis;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.loushang.util.IErrorHandler;
import org.loushang.util.IMessageHandler;
import org.loushang.waf.mvc.ViewHelper;
import org.mybatis.spring.SqlSessionTemplate;

import com.lc.v6.jdbc.mybatis.V6SqlSessionUtil;
import com.shop.base.cmd.BaseCommandImpl;

/** 说明：
 * 
 * @author pengzhu
 * SqlQueryCmd.java 2012-12-3
 */
public class CommonQueryCmd extends BaseCommandImpl{

	private static Log log = LogFactory.getLog(CommonQueryCmd.class);
	
	/**
	 * 通用sql查询
	 * @param req
	 * @param rep
	 * @param errorHandler
	 * @param messageHandler
	 * @param viewHelper
	 * @return
	 * @throws IOException 
	 */
	public String query(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper) throws IOException {
		
		String sql = req.getParameter("sql");
		SqlSessionTemplate sqlSession = V6SqlSessionUtil.getSqlSession();
		List list =sqlSession.selectList("common.query",sql);
		String flag = "0";
		if((list!=null) && (list.size()>0)){
			flag = "1";
		}
		
		
		PrintWriter out = rep.getWriter();
		out.print(flag);
		out.flush();
		
		
		return "null";
	}
	
	/**
	 * 记账用校验
	 * 
	 * @param req
	 * @param rep
	 * @author 匡志勇
	 * @date 20130107
	 * @param messageHandler
	 * @param viewHelper
	 * @return
	 */
	public String query1(HttpServletRequest req, HttpServletResponse rep, IErrorHandler errorHandler, IMessageHandler messageHandler, ViewHelper viewHelper)
	  {
	    String sql = req.getParameter("Sql");
	    
	    SqlSessionTemplate sqlSession = V6SqlSessionUtil.getSqlSession();
	    String returnStr = (String)sqlSession.selectOne("common.query1", sql);
	    System.out.println("returnStr=="+returnStr);
	    try
	    {
	      PrintWriter out = rep.getWriter();
	      out.print(returnStr);
	      out.flush();
	    } catch (IOException e) {
	      log.error("CommonQueryCmd.query1--PrintWriter 出错");
	    }

	    return "null";
	  }
	
	/**
	 * 日结用校验
	 * 
	 * @author 匡志勇
	 * @date 20130107
	 * @param errorHandler
	 * @param messageHandler
	 * @param viewHelper
	 * @return
	 */
	public String query2(HttpServletRequest req, HttpServletResponse rep, IErrorHandler errorHandler, IMessageHandler messageHandler, ViewHelper viewHelper)
	  {
	    String sql = req.getParameter("Sql");
	    if(log.isDebugEnabled()){
	    }
	    String returnStr="0";
	    SqlSessionTemplate sqlSession = V6SqlSessionUtil.getSqlSession();
	    List returnList = (List)sqlSession.selectList("common.query2", sql);
	    if(returnList!=null&&returnList.size()>0){
	    	Map tmpMap = (Map)returnList.get(0);
	    	String count1=String.valueOf(returnList.size());
	    	for(int i =0;i<returnList.size();i++){
	    		Map tmpMap1 = (Map)returnList.get(i);
	    		String balDate=(String)tmpMap1.get("BAL_DATE");
	    		count1=count1+","+balDate;
	    	}
	    	returnStr=count1;
	    }
	    try
	    {
	      PrintWriter out = rep.getWriter();
	      out.print(returnStr);
	      out.flush();
	    } catch (IOException e) {
	      log.error("CommonQueryCmd.query2--PrintWriter 出错");
	    }

	    return "null";
	  }
	}
