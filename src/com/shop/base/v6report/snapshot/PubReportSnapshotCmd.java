package com.shop.base.v6report.snapshot;

// 导入java类
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.loushang.bsp.service.OrganProvider;
import org.loushang.util.IErrorHandler;
import org.loushang.util.IMessageHandler;
import org.loushang.waf.mvc.ViewHelper;

import com.shop.base.bsp.V6BspInfo;
import com.shop.base.cmd.BaseCommandImpl;
import com.shop.base.tool.DateTool;
import com.shop.base.tool.UUIDTool;

import sun.misc.BASE64Encoder;


/**
 * 功能：将报表分享给团队空间
 * 
 * @author 孙振
 * 2013/7/22
 * 
 */
public class PubReportSnapshotCmd extends BaseCommandImpl {

	private static Log log = LogFactory.getLog(PubReportSnapshotCmd.class);
	
	IPubReportSnapshotService pubReportSnapshotService=null;
	
	public IPubReportSnapshotService getPubReportSnapshotService() {
		return pubReportSnapshotService;
	}

	public void setPubReportSnapshotService(
			IPubReportSnapshotService pubReportSnapshotService) {
		this.pubReportSnapshotService = pubReportSnapshotService;
	}

	/**
	 * 保存报表快照
	 * 
	 * @param req
	 * @param rep
	 * @param errorHandler
	 * @param messageHandler
	 * @param viewHelper
	 * @return
	 */
	public String saveReportSnapshot(HttpServletRequest req,
			HttpServletResponse rep, IErrorHandler errorHandler,
			IMessageHandler messageHandler, ViewHelper viewHelper) {
		V6BspInfo bspInfo=new V6BspInfo();
		String option=(String)req.getParameter("option");
		String userId=bspInfo.getUserId();
		String snapShotId=UUIDTool.getInnerId();
		String geneTime=DateTool.getCurrentDayTime();
		String note=(String)req.getParameter("note");
		
		Map map=new HashMap();
		map.put("option", option);
		map.put("userId", userId);
		map.put("snapShotId", snapShotId);
		map.put("geneTime", geneTime);
		map.put("note", note);
		
		getPubReportSnapshotService().insertReportSnapshot(map);
		
    	try {
			PrintWriter out = rep.getWriter();
			out.print(snapShotId);
			out.flush();
		} catch (Exception e) {
			//
		}
    	
		return "null";
	}
	
	/**
	 * 获取用户所在的团队
	 * @param req
	 * @param rep
	 * @param errorHandler
	 * @param messageHandler
	 * @param viewHelper
	 * @return
	 */
	public String getUserIdforSns(HttpServletRequest req,
			HttpServletResponse rep, IErrorHandler errorHandler,
			IMessageHandler messageHandler, ViewHelper viewHelper) {
		
		
		V6BspInfo bspInfo=new V6BspInfo();
		String userId=bspInfo.getUserId();
		String snsUserId="123456789_"+userId+"@1234567890";
		snsUserId=new BASE64Encoder().encode(snsUserId.getBytes());

		try {
			PrintWriter out = rep.getWriter();
			out.print(snsUserId);
			out.flush();
		} catch (Exception e) {
			//
		}
		
		return "null";
		
		
	}

}
