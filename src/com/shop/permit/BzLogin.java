package com.shop.permit;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shop.base.batis.SqlClient;

/**
 * Servlet
 */
public class BzLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Log logger = LogFactory.getLog(BzLogin.class);

	/**
	 * @see HttpServlet
	 */

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		String userId = request.getParameter("userId");
		String v6pass = request.getParameter("pass");
		// String flag = request.getParameter("flag");
		logger.debug(v6pass+"  "+userId);
		
		
		
		
		// System.out.println("flag"+flag);
		List list = SqlClient
				.commonSelect("SELECT PASSWORD FROM PUB_USERS WHERE USER_ID='"
						+ userId.toUpperCase() + "'");
		String strResult = null;
		
		strResult=list.get(0)+",";
	    logger.debug(strResult+" bzpass1");
		strResult = strResult.substring(10,strResult.length()-2);
		logger.debug(strResult+" bzpass2 ");
		if (v6pass.equals(strResult)) {
			
			response.sendRedirect("http://bz-tec.sd-tobacco.com.cn/v6/j_bsp_security_check/dl?j_username="
					+ userId + "&j_password=" + v6pass);
			
		} else {
		
			PrintWriter out =  response.getWriter();
			out.write("<script> alert('密码错误！');");
			out.write("window.close();</script>");
		}
		// TODO Auto-generated method stub

		// super.service(request, response);
	}

}
