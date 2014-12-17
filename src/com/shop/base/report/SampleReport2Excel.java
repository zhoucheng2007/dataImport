package com.shop.base.report;

// 生成Excel的类 
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.loushang.bsp.service.GetBspInfo;

import com.shop.base.tool.DateTool;

/**
 * 功能：将简单样式的报表导出为excel
 * 
 * @author 孙振开发，郑斌改造移植
 * @date 2012-11-8 下午3:48:25
 */
public class SampleReport2Excel extends HttpServlet{

	private static Log log = LogFactory.getLog(SampleReport2Excel.class);
	
	/**
	 * 功能：将报表导出为excel
	 * @author 郑斌
	 * @date 2012-11-27 上午9:14:41
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void exportdata(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		if (log.isInfoEnabled()) {
			log.info("SampleReport2Excel - exportdata - start");
		}
		// 获取报表的json数据
		String reportJson = req.getParameter("sReportJsonHidden");
		if(reportJson==null||reportJson.trim().equals("")){
		   reportJson = (String) req.getAttribute("sReportJsonHidden");	
		}
		if (log.isDebugEnabled()) {
			log.debug("reportJson:" + reportJson);
		}
		if (reportJson == null) {
			log.error("SampleReport2Excel - exportdata:reportJson==null!");
			reportJson = "";
		}
		//取用户信息
		String userName = GetBspInfo.getBspInfo(req).getUserName();
		
		try {
			// 先创建工作簿对象
			HSSFWorkbook book = new HSSFWorkbook();
			// 创建工作表对象并命名
			HSSFSheet sheet = book.createSheet();
			JSONObject jsonObj = new JSONObject(reportJson);
			String tableStr = jsonObj.getString("tableStr");
			String titleStr = jsonObj.getString("titleStr");
			int fixedTrNum = jsonObj.getInt("fixedTrNum");//固定行
			int fixedTdNum = jsonObj.getInt("fixedTdNum");//固定列
			int wrapText = jsonObj.getInt("wrapText");//自动换行
			sheet.createFreezePane(fixedTdNum, fixedTrNum+2);//默认固定表格的表头和标题
			String parmsStr = getParmsDesc(jsonObj.getString("parmsStr"));
			book.setSheetName(0, URLDecoder.decode(titleStr, "utf-8"));
            //book.setSheetName(0, URLDecoder.decode(titleStr, "utf-8"), (short) 1);poi3.5的jar包方法参数个数有变化
			JSONArray jsonArr = null;
			int maxCol = 0;
			try {
				jsonArr = new JSONArray(tableStr);
				int row = 2;
				for (int i = 0; i < jsonArr.length(); i++) {
					JSONArray tdArr = jsonArr.getJSONArray(i);
					int col = 0;
					int dimColumn=1;
					int lastTrHeight=300;
					for (int j = 0; j < tdArr.length(); j++) {
						JSONObject tdObj = tdArr.getJSONObject(j);
						String text = tdObj.getString("text");
						String type = tdObj.getString("type");
						dimColumn = tdObj.getInt("dimColumn");
						int rowSpan = 1;
						try {
							rowSpan = tdObj.getInt("rowSpan");
						} catch (Exception e) {
							//
						}
						int colSpan = 1;
						try {
							colSpan = tdObj.getInt("colSpan");
						} catch (Exception e) {
							//
						}
						//把站位后的单元格空开
						while (getCellValue(sheet, row, col).equals("space")) {
							col = col + 1;
						}
						//单元格占位
						for(int m=0;m<colSpan;m++){
						  for (int n = 1; n < rowSpan; n++) {
							  setCellValue(sheet, row + n, col+m,1, "space", null);
						  }
						}
						Region region = new Region(row, (short) col, row
								+ rowSpan - 1, (short) (col + colSpan - 1));
						sheet.addMergedRegion(region);
						setRegionStyle(getCellStyle(book, "",wrapText), region, sheet);

						HSSFCellStyle style = getCellStyle(book, "",wrapText);
						if (type.equals("td")) {
							style = getCellStyle(book, "td",wrapText);
						} else {
							style = getCellStyle(book, "th",wrapText);		
							//获取此列的宽度
							
							int thWidth = tdObj.getInt("thWidth");
							int excelCellWidth=thWidth/7*256;
							if(excelCellWidth<256*256&&excelCellWidth>0){
							setColumnWidth(sheet,col,excelCellWidth ,"th");
							}else {
							setColumnWidth(sheet,col,255 ,"th");
							}
						}
						
						//是否自动换行
						if(wrapText==1){
						  int excelCellWidth=sheet.getColumnWidth(col);
						  int trHeight = 300;
						  int length=text.length();
						  int height=(int)(length*10*80/excelCellWidth)*trHeight;
						  if(lastTrHeight<height){
							  lastTrHeight=height;	
						  }
						}

						setCellValue(sheet, row, col,dimColumn, text, style);

						col = col + colSpan;

						// 保存一个最大的col
						if (col > maxCol) {
							maxCol = col;
						}
					}
					//是否自动换行
					if(wrapText==1){
				         setHeight(sheet,row,lastTrHeight);
					}
					row++;
				}
				//写入制表信息
//				HSSFCellStyle style = getCellStyle(book, "markInfo");
//				setCellValue(sheet, row, 0, "制表人：", style);
//				setCellValue(sheet, row, 1, userName, style);
//				setCellValue(sheet, row, maxCol - 2 < 2 ? 2 : maxCol - 2,
//						"制表日期：", style);
//				setCellValue(sheet, row, maxCol - 1 < 3 ? 3 : maxCol - 1,DateTool.getToday(), style);
				
				HSSFCellStyle markInfoLeftstyle = getCellStyle(book, "markInfoLeft",0);
				setCellValue(sheet, row, 0,1, "制表人："+userName, markInfoLeftstyle);
				HSSFCellStyle markInfoRightstyle = getCellStyle(book, "markInfoRight",0);
				setCellValue(sheet, row, maxCol - 1 < 3 ? 3 : maxCol - 1,1,"制表日期："+DateTool.getToday(), markInfoRightstyle);

			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			Region region1 = new Region(0, (short) 0, 0, (short) (maxCol - 1));
			sheet.addMergedRegion(region1);
			setRegionStyle(getCellStyle(book, "",wrapText), region1, sheet);
			Region region2 = new Region(1, (short) 0, 1, (short) (maxCol - 1));
			sheet.addMergedRegion(region2);
			setRegionStyle(getCellStyle(book, "",wrapText), region2, sheet);

			HSSFCellStyle styleTitle = getCellStyle(book, "title",wrapText);
			setCellValue(sheet, 0, 0,1, titleStr, styleTitle);
			HSSFCellStyle styleParms = getCellStyle(book, "parms",wrapText);
			setCellValue(sheet, 1, 0,1, parmsStr, styleParms);

			//解决中文文件名乱码问题
			String fileName = "";
			if(req.getHeader("user-agent").indexOf("MSIE") != -1) {
				fileName = java.net.URLEncoder.encode(titleStr,"utf-8") + ".xls"; 
			}else{
				fileName = new String(titleStr.getBytes("utf-8"),"iso-8859-1")+ ".xls"; 
			} 

			ServletOutputStream servletoutputstream = resp.getOutputStream();
			resp.setHeader("Content-disposition", "attachment; filename="
					+ fileName);
			resp.setDateHeader("Expires", 0);
			resp.setContentType("application/vnd.ms-excel;charset=utf-8");
			book.write(servletoutputstream);
			servletoutputstream.flush();
		} catch (Exception e) {
			log.error("报表导出错误：" + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		if (log.isInfoEnabled()) {
			log.info("SampleReport2Excel - exportdata - end");
		}
	}
	//设置每一列的宽度
	public static void setColumnWidth(HSSFSheet sheet,int colindex,int width,String type){
		if("th".equals(type)){
		   sheet.setColumnWidth(colindex, width); 
		}
	}
	//设置每一行的高度
	public static void setHeight(HSSFSheet sheet,int row,int height){
		HSSFRow hssfRow = sheet.getRow(row);
		hssfRow.setHeight((short) height);
	}

	// 读取单元格内容
	public static String getCellValue(HSSFSheet sheet, int row, int col) {
		String cellStr = "";
		HSSFRow hssfRow = sheet.getRow(row);
	//	hssfRow.setHeight((short) 2);
		if (hssfRow != null) {// 如果为空，不处理
	//		hssfRow.setHeight((short) 300);
			HSSFCell cell = hssfRow.getCell((short) col);
			if (cell == null) {// 单元格为空设置cellStr为空串
				cellStr = "";
			} else if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {// 对布尔值的处理
				cellStr = String.valueOf(cell.getBooleanCellValue());
			} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {// 对数字值的处理
				cellStr = cell.getNumericCellValue() + "";
			} else {// 其余按照字符串处理
				cellStr = cell.getStringCellValue();
			}
		}
		return cellStr;
	}

	// 设置单元格内容
	public static void setCellValue(HSSFSheet sheet, int row, int col,int dimColumn,
			String str, HSSFCellStyle style)
			throws UnsupportedEncodingException {
		HSSFRow hssfRow = null;
		hssfRow = sheet.getRow(row);
		if (hssfRow == null) {
			hssfRow = sheet.createRow(row);
		}
		HSSFCell hssfCell = hssfRow.createCell((short) col);
//		hssfCell.setEncoding(HSSFCell.ENCODING_UTF_16);        poi3.5的jar包不需要设置字符编码
		Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()&&dimColumn==0) {
			Double num = Double.parseDouble(str);
			hssfCell.setCellValue(num);
		} else {
			hssfCell.setCellValue(URLDecoder.decode(str.replaceAll("%", "%25"),
					"utf-8"));
		}
		if (style != null) {
			hssfCell.setCellStyle(style);
		}

	}

	// 样式
	public static HSSFCellStyle getCellStyle(HSSFWorkbook wb, String type,int wrapText) {
		HSSFCellStyle style = wb.createCellStyle();
		// 设置文字对齐方式
		if (type.equals("parms")) {
			style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		} else if(type.equals("markInfoLeft")){
			style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		} else if(type.equals("markInfoRight")){
			style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		}else{
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		}
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		//是否自动换行
		if(wrapText==1){
		   style.setWrapText(true);
		}
		// 设置字体
		if (type.equals("title")) {
			HSSFFont titleFont = wb.createFont();
			titleFont.setFontHeightInPoints((short) 15);
			titleFont.setBoldweight((short) 800);
			style.setFont(titleFont);
		}
		// 设置边框宽度
		if (!type.startsWith("markInfo")) {
			style.setBorderTop((short) 1);
			style.setBorderRight((short) 1);
			style.setBorderBottom((short) 1);
			style.setBorderLeft((short) 1);
		}
		// 设置背景颜色
		if (type.equals("th")) {
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		}
		return style;
	}

	// 处理合并单元格后的边框
	private static void setRegionStyle(HSSFCellStyle cs, Region region,
			HSSFSheet sheet) {

		for (int i = region.getRowFrom(); i <= region.getRowTo(); i++) {

			HSSFRow row = sheet.getRow(i);
			if (row == null)
				row = sheet.createRow(i);
			for (int j = region.getColumnFrom(); j <= region.getColumnTo(); j++) {
				HSSFCell cell = row.getCell((short) j);
				if (cell == null) {
					cell = row.createCell((short) j);
					cell.setCellValue("");
				}
				cell.setCellStyle(cs);
			}
		}
	}

	/**
	 * 功能：获取表格上方显示的属性内容
	 * @date 2012-11-8 下午4:31:12
	 * @param parmsStr
	 * @return
	 */
	private static String getParmsDesc(String parmsStr) {
		
		return parmsStr;
	}
	
	//处理get请求
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		exportdata(req,resp);
	}
	//处理post请求
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		exportdata(req,resp);
	}
	
}