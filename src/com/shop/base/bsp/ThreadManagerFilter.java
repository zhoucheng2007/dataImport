package com.shop.base.bsp;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * SecurityEnforceFilter 类
 * 
 * @author
 */
public class ThreadManagerFilter implements Filter {

	private static Log log = LogFactory.getLog(ThreadManagerFilter.class);
	/**
	 * 方法 destroy 重写Filter的destroy方法
	 */
	public void destroy() {

	}

	/**
	 * 方法 init 重写Filter的init方法
	 */
	public void init(FilterConfig config) throws ServletException {
		ThreadManager.initThreadManager();
	}

	/**
	 * 方法 doFilter 重写Filter的doFilter方法
	 */
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {	
		
		// request，response 处理
		HttpServletRequest request = getRequest(req);
		HttpServletResponse response = getResponse(resp);
		//String requestUrl = RequestTool.getRequestUrl(request);
		//response.setHeader("Expires", "Mon,12 May 2001 00:20:00 GMT");

		try {
			beforeAccess(request);
			chain.doFilter(request, response);
		} catch(PermitException pe) {
			log.error("ThreadManagerFilter-doFilter 功能当前访问量超过最大限制，请稍后再试！");
			forwardErrorPage(request, response, pe);			
		} finally {
			try {
				afterAccess(request);
			} catch (Exception e){
				log.error("ThreadManagerFilter-doFilter 22 error", e);
			}			
		}
	}

	protected void beforeAccess(HttpServletRequest request) {
		ThreadManager.checkThreadLimit(request);
	}

	protected void afterAccess(HttpServletRequest request) {
		ThreadManager.realseThreadLimit(request);
	}
	

	/**
	 * 转向错误页面
	 * @param request
	 * @param response
	 * @param throwable
	 * @throws IOException
	 * @throws ServletException
	 */
	private void forwardErrorPage(HttpServletRequest request,
			HttpServletResponse response, Throwable throwable)
			throws IOException, ServletException {
		request.setAttribute("THREAD_PERMIT_EXCEPTION", throwable);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/bsp/threadWarn.jsp");
		dispatcher.forward(request, response);
	}
	
	protected HttpServletRequest getRequest(ServletRequest request) {
		HttpServletRequest result = (HttpServletRequest) request;
		return result;
	}

	/**
	 * 得到HTTP响应对象
	 */
	protected HttpServletResponse getResponse(ServletResponse response) {
		return (HttpServletResponse) response;
	}
}
