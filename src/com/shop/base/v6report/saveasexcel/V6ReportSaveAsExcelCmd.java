package com.shop.base.v6report.saveasexcel;

// 导入java类
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.loushang.bsp.service.OrganProvider;
import org.loushang.util.IErrorHandler;
import org.loushang.util.IMessageHandler;
import org.loushang.waf.mvc.ViewHelper;

import com.shop.base.bsp.V6BspInfo;
import com.shop.base.cmd.BaseCommandImpl;
import com.shop.base.tool.DateTool;
import com.shop.base.tool.UUIDTool;



/**
 * 功能：报表插件专用saveAsExcel
 * 
 * @author 孙振
 * 2013/12/20
 * 
 */
public class V6ReportSaveAsExcelCmd extends BaseCommandImpl {

	private static Log log = LogFactory.getLog(V6ReportSaveAsExcelCmd.class);
	

	/**
	 * 导出EXCEL
	 * @param req
	 * @param rep
	 * @param errorHandler
	 * @param messageHandler
	 * @param viewHelper
	 * @return    
	 * @throws JSONException 
	 * @throws IOException 
	 */
	public String saveAsExcel(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper) throws JSONException, IOException {
		
		if (log.isInfoEnabled()) {
			log.info("V6Report2Excel - exportdata - start");
		}
		// 获取报表的json数据
		String reportJson = req.getParameter("sReportJsonHidden");
		
		reportJson=URLDecoder.decode(reportJson, "utf-8");
		
		if (log.isDebugEnabled()) {
			log.debug("reportJson:" + reportJson);
		}
		if (reportJson == null) {
			log.error("V6Report2Excel - exportdata:reportJson==null!");
			reportJson = "";
		}
		
		JSONObject reportObj=new JSONObject(reportJson);
		
		Map excelMap=ExcelFactory.createExcel(reportObj);
		String reportName=(String)excelMap.get("name");
		HSSFWorkbook book=(HSSFWorkbook)excelMap.get("excel");
		
		
		//输出
		String fileName = "";
		if(req.getHeader("user-agent").indexOf("MSIE") != -1 || req.getHeader("user-agent").indexOf("rv:11") !=-1) {
			fileName = java.net.URLEncoder.encode(reportName,"utf-8") + ".xls"; 
		}else{
			fileName = new String(reportName.getBytes("utf-8"),"iso-8859-1")+ ".xls"; 
		} 

		ServletOutputStream servletoutputstream = rep.getOutputStream();
		rep.setHeader("Content-disposition", "attachment; filename="+ fileName);
		rep.setDateHeader("Expires", 0);
		rep.setContentType("application/vnd.ms-excel;charset=utf-8");
		book.write(servletoutputstream);
		servletoutputstream.flush();
		
		if (log.isInfoEnabled()) {
			log.info("V6Report2Excel - exportdata - end");
		}
		
		return "null";
	}
	

	
	
	

}
