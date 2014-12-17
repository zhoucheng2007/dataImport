package com.shop.base.v6report.saveasexcel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.shop.base.bsp.V6BspInfo;
import com.shop.base.tool.DateTool;

/**
 * excel生成工厂
 * @author sun_zhen
 *
 */
public class ExcelFactory {
	private static Log log = LogFactory.getLog(ExcelFactory.class);
	
	public static Map createExcel(JSONObject reportObj) throws JSONException, UnsupportedEncodingException{
		JSONObject reportInfoObj=reportObj.getJSONObject("reportInfo");
		JSONObject reportHeadObj=reportObj.getJSONObject("tableHead");
		JSONObject reportBodyObj=reportObj.getJSONObject("tableBody");
		JSONObject colHeadFixed=reportObj.getJSONObject("colHeadFixed");
		JSONObject colHeadMerge=reportObj.getJSONObject("colHeadMerge");
		   
		//获取列数
		JSONArray headArr=reportHeadObj.getJSONArray("data");
		JSONArray headRowArr0=headArr.getJSONArray(0);//以表头数据的第一行的length作为报表的列数
		int colNum=headRowArr0.length();
		 
		// 先创建工作簿对象
		HSSFWorkbook book = new HSSFWorkbook();
		// 创建工作表对象并命名
		HSSFSheet sheet = book.createSheet();
		// 创建样式对象
		HSSFCellStyle titleStyle = book.createCellStyle();
		HSSFCellStyle parmsStyle = book.createCellStyle();
		HSSFCellStyle thStyle = book.createCellStyle();
		HSSFCellStyle tdStyle = book.createCellStyle();
		HSSFCellStyle leftBottomStyle = book.createCellStyle();
		HSSFCellStyle rightBottomStyle = book.createCellStyle();
		
		String reportName=reportInfoObj.getString("title");
		book.setSheetName(0, reportName);
		
		//表格的标题
		setCellValue(book,sheet,0,0,reportName,null,"word",null);
		CellRangeAddress region=new CellRangeAddress(0,0,0,colNum-1);
		sheet.addMergedRegion(region);
		setRegionStyle(getCellStyle(book,titleStyle, "title"), region, sheet);
		
		//查询参数
		String parmsStr="";
		if(reportInfoObj.has("parms")){
			parmsStr=reportInfoObj.getString("parms");
		}
		String infoStr="";
		if(reportInfoObj.has("info")){
			infoStr=reportInfoObj.getString("info");
		}
		setCellValue(book,sheet, 1, 0, infoStr, null,"word",null);
		setCellValue(book,sheet, 1, colNum-1,parmsStr, null,"word",null);

		
		//表头
		int tNum=2;//表头从第三行开始
		int headRowNum=headArr.length();
		ArrayList<String> wordOrNum = new ArrayList<String>();//记录一列是文字还是数值
		for(int i=0;i<headRowNum;i++){
			JSONArray headRowArr=headArr.getJSONArray(i);
			int headRowCellNum=headRowArr.length();
			for(int j=0;j<headRowCellNum;j++){
				int lineNum=tNum+i;
				JSONObject cellObj=headRowArr.getJSONObject(j);
				String text="";
				if(cellObj.has("text")){
					text = cellObj.getString("text");
				}
				
				/*从表头文字判断这一列是数值还是字符   开始*/
				if(i==headRowNum-1){
					String key="码,号,证,日期,时间,电话";
					String[] keyArr=key.split(",");
					boolean containKey=false;
					for(int k=0;k<keyArr.length;k++){
						if(text.endsWith(keyArr[k])){
							containKey=true;
							break;
						}
					}
					if(containKey){
						wordOrNum.add("word");
					}else{
						wordOrNum.add("number");
					}
				}
				/*从表头文字判断这一列是数值还是字符   结束*/
				
				if(cellObj.has("disable")){
					continue;
				}
				HSSFCellStyle thStyleInst = getCellStyle(book,thStyle, "th");
				setCellValue(book,sheet,lineNum,j,text,thStyleInst,"word","th");
				int n=1;
				while(j+n<headRowCellNum){//检查右侧相邻的单元格，如果内容一致则合并单元格
					JSONObject nextCellObj=headRowArr.getJSONObject(j+n);
					String nextText=nextCellObj.getString("text");
					if(text.equals(nextText)){
						nextCellObj.put("disable", true);
					}else{
						break;
					}
					n++;
				}
				int m=1;
				while(i+m<headRowNum){
					JSONArray nextHeadRowArr=headArr.getJSONArray(i+m);
					JSONObject nextCellObj=nextHeadRowArr.getJSONObject(j);
					String nextText=nextCellObj.getString("text");
					if(text.equals(nextText)){
						nextCellObj.put("disable", true);
					}else{
						break;
					}
					m++;
				}
				if(n>1||m>1){
					CellRangeAddress headRegion=new CellRangeAddress(lineNum,lineNum+m-1,j,j+n-1);
					sheet.addMergedRegion(headRegion);
					setRegionStyle(getCellStyle(book,thStyle, "th"), headRegion, sheet);
				}
				
			}
		}
		
		Map<Integer, HSSFCellStyle> styleMap = new HashMap();
		//表体
		int bNum=tNum+headRowNum;//表体从这一行开始
		int mergeNum=0;
		try{
			mergeNum=colHeadMerge.getInt("number");
		}catch(Exception e){
			
		}
		JSONArray bodyArr=reportBodyObj.getJSONArray("data");
		int bodyRowNum=bodyArr.length();
		for(int i=0;i<bodyRowNum;i++){
			JSONArray bodyRowArr=bodyArr.getJSONArray(i);
			int cellNum=bodyRowArr.length();//单元格个数
			int lineNum=bNum+i;//行号
			for(int j=0;j<cellNum;j++){
				JSONObject cellObj=bodyRowArr.getJSONObject(j);
				String disable = "";
				if(cellObj.has("disable")){
					disable=String.valueOf(cellObj.get("disable"));
				};
				if("true".equals(disable)){
					continue;
				}
				String text="";
				if(cellObj.has("value")){
					text=cellObj.getString("value");
					if(text.equals("&nbsp;")){
						text="";
					}
				}
				
				String cellType=wordOrNum.get(j);
				//判断是数字还是字符串
				Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
				Matcher isNum = pattern.matcher(text);
				if (isNum.matches()&&cellType.equals("number")) {
					//读取小数点后面的位数
				    String[] fotStr0=text.split("\\.");
				    if(fotStr0.length==2){
				    	int len= fotStr0[1].length();
				    	if(!styleMap.containsKey(len)){
				    		HSSFCellStyle tdStyle0= book.createCellStyle();
					    	styleMap.put(len,tdStyle0);
					    	StringBuffer formatSb = new StringBuffer();
					    	formatSb.append("0.");
							//生成和小数点位数对应的字符串
							for(int m=0;m<len;m++){
								formatSb.append("0");
							}
							String formatStr = formatSb.toString();
							HSSFDataFormat format = book.createDataFormat();
							tdStyle0.setDataFormat(format.getFormat(formatStr)); //  保留小数位数
							
							HSSFCellStyle tdStyleInst = getCellStyle(book,tdStyle0, "td");
							setCellValue(book,sheet,lineNum,j,text,tdStyleInst,cellType,"td");
				    	}else{
				    		HSSFCellStyle tdStyleInst = getCellStyle(book,(HSSFCellStyle)styleMap.get(len), "td");
							setCellValue(book,sheet,lineNum,j,text,tdStyleInst,cellType,"td");	
				    	}
				    }else{
				    	HSSFCellStyle tdStyleInst = getCellStyle(book,tdStyle, "td");
						setCellValue(book,sheet,lineNum,j,text,tdStyleInst,cellType,"td");	
				    }	
				}else{
					HSSFCellStyle tdStyleInst = getCellStyle(book,tdStyle, "td");
					setCellValue(book,sheet,lineNum,j,text,tdStyleInst,cellType,"td");
				}

				if(j<mergeNum){
					int n=1;
					while(j+n<mergeNum){//检查右侧相邻的单元格，如果内容一致则合并单元格
						JSONObject nextCellObj=bodyRowArr.getJSONObject(j+n);
						String nextText=nextCellObj.getString("value");
						if(text.equals(nextText)){
							nextCellObj.put("disable", true);
						}else{
							break;
						}
						n++;
					}
					int m=1;
					while(i+m<bodyRowNum){
						JSONArray nextBodyRowArr=bodyArr.getJSONArray(i+m);
						JSONObject nextCellObj=nextBodyRowArr.getJSONObject(j);
						String nextText=nextCellObj.getString("value");
						if(text.equals(nextText)){
							nextCellObj.put("disable", true);
						}else{
							break;
						}
						m++;
					}
					if(n>1||m>1){
						CellRangeAddress bodyRegion=new CellRangeAddress(lineNum,lineNum+m-1,j,j+n-1);
						sheet.addMergedRegion(bodyRegion);
						setRegionStyle(getCellStyle(book,tdStyle, "td"), bodyRegion, sheet);
					}
				}
			
			}
		}
		//表足
		int zNum=bNum+bodyRowNum;
		String userName="man";
		try {
			userName = V6BspInfo.getUserName();
		} catch (Exception e) {
			//
		}
		setCellValue(book,sheet,zNum,0,"制表人："+userName,null,"word",null);
		rightBottomStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		setCellValue(book,sheet,zNum,colNum-1,"制表日期："+DateTool.getToday(),rightBottomStyle,"word",null);
		
		//设置列宽
		JSONArray widthArr=reportHeadObj.getJSONArray("widthArr");
		for(int i=0;i<widthArr.length();i++){
			int width=widthArr.getInt(i);
			sheet.setColumnWidth(i, width*800);
		}
		
		//设置行列锁定
		int freezeColNum=colHeadFixed.getInt("number");
		sheet.createFreezePane(freezeColNum,2+headRowNum);	
		
		Map map=new HashMap();
		map.put("name", reportName);
		map.put("excel", book);
				
		return map;
	}

	// 读取单元格内容
		private static String getCellValue(HSSFSheet sheet, int row, int col) {
			String cellStr = "";
			HSSFRow hssfRow = sheet.getRow(row);
			if (hssfRow != null) {// 如果为空，不处理
				hssfRow.setHeight((short) 300);
				HSSFCell cell = hssfRow.getCell(col);
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
		private static void setCellValue(HSSFWorkbook wb,HSSFSheet sheet, int row, int col,String str,HSSFCellStyle style,String wordOrNum,String type)
				throws UnsupportedEncodingException {
			HSSFDataFormat format = wb.createDataFormat();
			if(str==null){
				str="";
			}
			HSSFRow hssfRow = null;
			hssfRow = sheet.getRow(row);
			if (hssfRow == null) {
				hssfRow = sheet.createRow(row);
			}
			HSSFCell hssfCell = hssfRow.createCell(col);
			Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
			Matcher isNum = pattern.matcher(str);
			
			if (isNum.matches()&&wordOrNum.equals("number")) {
				String forStr = str;
				Double num = Double.parseDouble(str);
				hssfCell.setCellValue(num);	
			} else {
				hssfCell.setCellValue(URLDecoder.decode(str.replaceAll("%", "%25"),"utf-8"));
			}
			if (style != null&&"td".equals(type)) {
				style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			}
			if (style != null) {
				hssfCell.setCellStyle(style);
			}

		}
		
		// 样式
		private static HSSFCellStyle getCellStyle(HSSFWorkbook wb , HSSFCellStyle style, String type) {
			// 设置文字对齐方式
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
			if (type.equals("parms")) {
				style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			} else if(type.equals("markInfoLeft")){
				style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			} else if(type.equals("markInfoRight")){
				style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			}else{
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			}
			if (type.equals("title")) {
				HSSFFont titleFont = wb.createFont();
				titleFont.setFontHeightInPoints((short) 15);
				titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				style.setFont(titleFont);
			}
			// 设置边框宽度
			if (type.startsWith("markInfo")) {
				style.setBorderTop((short) 0);
				style.setBorderRight((short) 0);
				style.setBorderBottom((short) 0);
				style.setBorderLeft((short) 0);
			}else{
				style.setBorderTop((short) 1);
				style.setBorderRight((short) 1);
				style.setBorderBottom((short) 1);
				style.setBorderLeft((short) 1);
			}
			// 设置背景颜色
			if (type.equals("th")) {
				HSSFFont thFont = wb.createFont();
				thFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				style.setFont(thFont);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
			}else{
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style.setFillForegroundColor(HSSFColor.WHITE.index);
			}
			return style;
		}
		
		
		// 处理合并单元格后的边框
		private static void setRegionStyle(HSSFCellStyle cs, CellRangeAddress region,
				HSSFSheet sheet) {
			for (int i = region.getFirstRow(); i <= region.getLastRow(); i++) {
				HSSFRow row = sheet.getRow(i);
				if (row == null)
					row = sheet.createRow(i);
				for (int j = region.getFirstColumn(); j <= region.getLastColumn(); j++) {
					HSSFCell cell = row.getCell(j);
					if (cell == null) {
						cell = row.createCell(j);
						cell.setCellValue("");
					}
					cell.setCellStyle(cs);
				}
			}
		}
		
		
		/**
		 * 测试用例
		 * @param args
		 * @throws Exception
		 */
		public static void main(String[] args) throws Exception {
			
			
			String fileName="c:/data.txt";
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF-8"));  
			String data = br.readLine();
			StringBuffer sb=new StringBuffer();
			while( data!=null){
			      sb.append(data);
			      data = br.readLine();
			}

			JSONObject reportJson=new JSONObject(sb.toString());

			Map map=ExcelFactory.createExcel(reportJson);
			
			String reportName=(String)map.get("name");
			HSSFWorkbook book=(HSSFWorkbook)map.get("excel");
			
			File outfile = new File("c:/"+reportName+".xls");
			FileOutputStream outputStream = new FileOutputStream(outfile);
			book.write(outputStream);
			outputStream.close();
			System.out.print("OK!");
			
			
		}


}
