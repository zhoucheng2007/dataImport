package com.shop.base.cmd;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.loushang.bsp.service.GetBspInfo;
import org.loushang.util.IErrorHandler;
import org.loushang.util.IMessageHandler;
import org.loushang.waf.mvc.ViewHelper;
import org.openadaptor.util.base64.Base64Encoder;

public abstract class MainPageCommand extends BaseCommandImpl {
	private Log log = LogFactory.getLog(MainPageCommand.class);

	/**
	 * 
	 * @param req
	 * @param rep
	 * @param errorHandler
	 * @param messageHandler
	 * @param viewHelper
	 * @return
	 */
	public String goHomePage(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper) {
		String retn = queryPage(req, rep, errorHandler, messageHandler,
				viewHelper);
		setSomeCookie(req, rep, errorHandler, messageHandler, viewHelper);
		uploadPage(req, rep, errorHandler, messageHandler, viewHelper);
		return retn;
	}

	public abstract String queryPage(HttpServletRequest req,
			HttpServletResponse rep, IErrorHandler errorHandler,
			IMessageHandler messageHandler, ViewHelper viewHelper);

	private final void setSomeCookie(HttpServletRequest req,
			HttpServletResponse rep, IErrorHandler errorHandler,
			IMessageHandler messageHandler, ViewHelper viewHelper) {

		GetBspInfo bsp = new GetBspInfo();
		String userId = bsp.getBspInfo().getUserId();
		String userName = bsp.getBspInfo().getUserName();
		String organId = bsp.getBspInfo().getEmployeeOrganId();

		Base64Encoder base64Encoder = new Base64Encoder();
		String str = userId + "#" + userName + "#" + organId;
		byte[] midbytes = null;
		try {
			midbytes = str.getBytes("UTF8");
		} catch (UnsupportedEncodingException e) {
			log.error("将str转化为byte出错", e);
		}
		String v6u = base64Encoder.encode(midbytes);
		if (log.isDebugEnabled()) {
			log.debug("v6u====" + v6u);
		}
		Cookie cookie = new Cookie("v6u", v6u);
		cookie.setPath("/");

		rep.addCookie(cookie);

	}

	/**
	 * @param req
	 * @param rep
	 * @param errorHandler
	 * @param messageHandler
	 * @param viewHelper
	 * @return
	 */
	public String uploadPage(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper) {

		GetBspInfo bsp = new GetBspInfo();
		String userId = bsp.getBspInfo().getUserId();
		String userName = bsp.getBspInfo().getUserName();
		req.setAttribute("userId", userId);
		req.setAttribute("userName", userName);
		return null;
	}
}
