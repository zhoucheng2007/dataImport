package com.shop.base.linkselect;

// 导入java类
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.loushang.bsp.service.OrganProvider;
import org.loushang.util.IErrorHandler;
import org.loushang.util.IMessageHandler;
import org.loushang.waf.mvc.ViewHelper;

import com.shop.base.cmd.BaseCommandImpl;
import com.shop.base.tool.PubConstants;


/**
 * 功能：新版本订单帮助类
 * 
 * @author lijian
 * 
 */
public class LinkSelectCmd extends BaseCommandImpl {

	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(LinkSelectCmd.class);


	private static BigDecimal ZERO = new BigDecimal("0");

	/**
	 * 获取公司下级营销中心
	 * 
	 * @param req
	 * @param rep
	 * @param errorHandler
	 * @param messageHandler
	 * @param viewHelper
	 * @return
	 */
	public String getSaleCenter(HttpServletRequest req,
			HttpServletResponse rep, IErrorHandler errorHandler,
			IMessageHandler messageHandler, ViewHelper viewHelper) {
		String comId = req.getParameter("COM_ID");
		if (log.isDebugEnabled())
			log.debug("zsl LinkSelectCmd getSaleCenter comId is" + comId);

		List<String> organList = OrganProvider.getSubOrganIdList(
				PubConstants.STRU_TYPE_SALE, comId,
				PubConstants.ORG_TYPE_DEPT_SALE_CENTER);
		// 记住查询条件
		String orgIdSearch = (String) req.getSession().getAttribute(
				"sale_center_idSearch");
		orgIdSearch = orgIdSearch == null ? "" : orgIdSearch;

		StringBuffer sb = new StringBuffer();
		String isAll = req.getParameter("IS_ALL");
		if (isAll == null || !isAll.equals("0"))
			sb.append("<option value=''>请选择</option>");

		if (organList != null && !organList.isEmpty()) {
			for (int i = 0; i < organList.size(); i++) {
				String organCode = organList.get(i);
				String organName = OrganProvider
						.getOrganNameByOrganId(organCode);
				sb.append("<option value='" + organCode + "' ");
				sb.append(orgIdSearch.equals(organCode) ? "selected='selected' "
						: "");
				sb.append(">").append(organName).append("</option>");
			}
		}

		try {
			PrintWriter out = rep.getWriter();
			out.print(sb.toString());
			out.flush();
		} catch (IOException e) {
			log.debug("PrintWriter 失败");
		}

		return null;
	}
	/**
	 * 根据公司取下级营销部
	 * @param req
	 * @param rep
	 * @param errorHandler
	 * @param messageHandler
	 * @param viewHelper
	 * @return
	 */
	public String getSaleDeptByCom(HttpServletRequest req,HttpServletResponse rep, IErrorHandler errorHandler,
			IMessageHandler messageHandler, ViewHelper viewHelper){
		String comId = req.getParameter("COM_ID");
		if (log.isDebugEnabled())
			log.debug("zsl LinkSelectCmd getSaleDeptByCom comId is" + comId);

		List<String> organList = OrganProvider.getSubOrganIdList(PubConstants.STRU_TYPE_SALE, comId,PubConstants.ORG_TYPE_DEPT_SALE_DEPT);
		// 记住查询条件
		String orgIdSearch = (String) req.getSession().getAttribute("sale_dept_idSearch");
		orgIdSearch = orgIdSearch == null ? "" : orgIdSearch;

		StringBuffer sb = new StringBuffer();
		String isAll = req.getParameter("IS_ALL");
		if (isAll == null || !isAll.equals("0"))
			sb.append("<option value=''>请选择</option>");

		if (organList != null && !organList.isEmpty()) {
			for (int i = 0; i < organList.size(); i++) {
				String organCode = organList.get(i);
				String organName = OrganProvider.getOrganNameByOrganId(organCode);
				sb.append("<option value='" + organCode + "' ");
				sb.append(orgIdSearch.equals(organCode) ? "selected='selected' " : "");
				sb.append(">").append(organName).append("</option>");
			}
		}
		try {
			PrintWriter out = rep.getWriter();
			out.print(sb.toString());
			out.flush();
		} catch (IOException e) {
			log.debug("PrintWriter 失败");
		}
		return null;
	}
	/**
	 * 获取营销中心下的营销部
	 * 
	 * @param req
	 * @param rep
	 * @param errorHandler
	 * @param messageHandler
	 * @param viewHelper
	 * @return
	 */
	public String getSaleDept(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper) {
		String saleCenterId = req.getParameter("SALE_CENTER_ID");
		if (log.isDebugEnabled())
			log.debug("getSaleDept saleCenterId==" + saleCenterId);
		List<String> organList = OrganProvider.getSubOrganIdList(
				PubConstants.STRU_TYPE_SALE, saleCenterId,
				PubConstants.ORG_TYPE_DEPT_SALE_DEPT);

		// 记住查询条件
		String orgIdSearch = (String) req.getSession().getAttribute(
				"sale_dept_idSearch");
		orgIdSearch = orgIdSearch == null ? "" : orgIdSearch;

		StringBuffer sb = new StringBuffer();
		String isAll = req.getParameter("IS_ALL");
		if (isAll == null || !isAll.equals("0"))
			sb.append("<option value=''>请选择</option>");

		if (organList != null && !organList.isEmpty()) {
			for (int i = 0; i < organList.size(); i++) {
				String organCode = organList.get(i);
				String organName = OrganProvider
						.getOrganNameByOrganId(organCode);
				sb.append("<option value='" + organCode + "' ");
				sb.append(orgIdSearch.equals(organCode) ? "selected='selected' "
						: "");
				sb.append(">").append(organName).append("</option>");
			}
		}

		try {
			PrintWriter out = rep.getWriter();
			out.print(sb.toString());
			out.flush();
		} catch (IOException e) {
			log.debug("PrintWriter 失败");
		}

		return null;
	}

	/**
	 * 获取营销部下的客户经理岗位
	 * 
	 * @param req
	 * @param rep
	 * @param errorHandler
	 * @param messageHandler
	 * @param viewHelper
	 * @return
	 */
	public String getSlsman(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper) {
		String saleDeptId = req.getParameter("SALE_DEPT_ID");
		if (log.isDebugEnabled())
			log.debug("getSlsman saleDeptId==" + saleDeptId);

		List<String> organList = OrganProvider.getSubPositionCodeList(
				PubConstants.STRU_TYPE_SALE, saleDeptId,
				PubConstants.ORG_TYPE_POST_SLSMAN);// 从销售组织取客户经理

		// 记住查询条件
		String orgIdSearch = (String) req.getSession().getAttribute(
				"slsman_idSearch");
		orgIdSearch = orgIdSearch == null ? "" : orgIdSearch;

		StringBuffer sb = new StringBuffer();
		String isAll = req.getParameter("IS_ALL");
		if (isAll == null || !isAll.equals("0"))
			sb.append("<option value=''>请选择</option>");

		if (organList != null && !organList.isEmpty()) {
			for (int i = 0; i < organList.size(); i++) {
				String organCode = organList.get(i);
				String organName = OrganProvider
						.getPositionNameByPositionCode(organCode);
				sb.append("<option value='" + organCode + "' ");
				sb.append(orgIdSearch.equals(organCode) ? "selected='selected' "
						: "");
				sb.append(">").append(organName).append("</option>");
			}
		}

		try {
			PrintWriter out = rep.getWriter();
			out.print(sb.toString());
			out.flush();
		} catch (IOException e) {
			log.debug("PrintWriter 失败");
		}

		return null;
	}

}
