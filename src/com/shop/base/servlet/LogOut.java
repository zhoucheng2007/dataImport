package com.shop.base.servlet;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 注销
 *
 * @author pengzhu
 *
 */
public class LogOut implements Servlet {

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	public void init(ServletConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	public void service(ServletRequest servletRequest,
			ServletResponse servletResponse) throws ServletException,
			IOException {
		if (!(servletRequest instanceof HttpServletRequest)) {
			throw new ServletException("Only Support HttpServletRequest");
		}

		if (!(servletResponse instanceof HttpServletResponse)) {
			throw new ServletException("Only Support HttpServletResponse");
		}
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
//		HttpServletResponse response = (HttpServletResponse) servletResponse;
//		response.sendRedirect(request.getContextPath()
//				+ SecurityConfig
//						.getString(SecurityConstants.Authen_LoginFormPage));
	}

}
