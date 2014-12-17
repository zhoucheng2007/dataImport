/**
 * 说明：
 * RequestTool.java 2014-5-29 下午12:56:31
 */
package com.shop.base.bsp;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/** 说明：
 * 
 * @author pengzhu
 * RequestTool.java 2014-5-29
 */
public class RequestTool {
	/**
	 * 通过代理服务包括反向代理req的ip为代理服务器ip获取此方法来获取真实ip
	 * 
	 * @param req
	 * @return
	 */
	public static String getRemoteIp(HttpServletRequest request) {
		//查看是否有代理头信息
		String ip = request.getHeader("x-forwarded-for"); //有直接返回ip
		//代理配置将代理头信息设为unknown 根据其他头来获取真实ip
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0
				|| "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0
				|| "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 获取cookie值方法
	 */
	public static String getCookie(HttpServletRequest request, String cookieID){
		Cookie[] cookies = request.getCookies();
		for (int i = 0; cookies != null && i < cookies.length; i++) {
			if (cookies[i].getName().equals(cookieID)) {
				return cookies[i].getValue();
			}
		}
		return null;
	}

	/**
	 * 得到请求的地址
	 */
	public static String getRequestUrl(HttpServletRequest request) {
		String servletPath = request.getServletPath();
		String pathInfo = request.getPathInfo();
		if (servletPath == null)
			servletPath = "";
		if (pathInfo == null)
			pathInfo = "";
		servletPath += pathInfo;
		if (servletPath.startsWith("/"))
			servletPath = servletPath.substring(1);
		return servletPath;
	}
	
	/**
	 * 拆分以分号分隔的Url字符串为数组方法
	 */
	public static String[] splitUrls(String allUrl){
		String[] urls = null;
		if (allUrl != null) {
			urls = allUrl.split(";");
		} else {
			urls = new String[0];
		}
		return urls;
	}
}
