package com.shop.base.cmd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.loushang.util.IErrorHandler;
import org.loushang.util.IMessageHandler;
import org.loushang.waf.mvc.ViewHelper;

import com.shop.base.batis.SqlClient;

public class PubSqlCommand extends BaseCommandImpl {
	/***
	 * 通用查询
	 * @param sql 查询的完整sql语句
	 * @return 结果集，list中的每个元素是一个map,map中以大写的列名为map的键，列的值为map的值
	 */
	public String commonSelect(
        HttpServletRequest req,
        HttpServletResponse rep,
        IErrorHandler errorHandler,
        IMessageHandler messageHandler,
        ViewHelper viewHelper) {		
    	String  sql=req.getParameter("sql");
    	
    	req.setAttribute("result", SqlClient.commonSelect(sql));
    	return null;		
	}
	
	
	public String existsResult(
            HttpServletRequest req,
            HttpServletResponse rep,
            IErrorHandler errorHandler,
            IMessageHandler messageHandler,
            ViewHelper viewHelper) {		
        	String  sql=req.getParameter("sql");
        	
        	req.setAttribute("result", SqlClient.existsResult(sql));
        	return null;		
	}
}
