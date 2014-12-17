package com.shop.base.selfAction;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 功能：处理.scm请求的转向
 * @author 郑斌
 * @date 2012-11-27 上午10:16:08
 */
public class SelfServletDispatcher extends HttpServlet{
	
	private static Log log = LogFactory.getLog(SelfServletDispatcher.class);
			
	//处理get请求
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		forward(req,resp);
	}
	//处理post请求
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		forward(req,resp);
	}

	/**
	 * 功能：将请求转给实际的servlet进行处理
	 * @author 郑斌
	 * @date 2012-11-27 上午11:15:07
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void forward(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String servletPath = req.getServletPath();
		String queryString = req.getQueryString();
		String[] spArr = servletPath.split("/");
		String realServlet = spArr[spArr.length-1].split("\\.")[0];
		RequestDispatcher dispatcher = req.getRequestDispatcher("/"+realServlet);
		if(log.isDebugEnabled()){
			log.debug("servletPath="+servletPath);
			log.debug("queryString="+queryString);
			log.debug("realServlet="+realServlet);
		}
		dispatcher.forward(req, resp);
	}
	
}
