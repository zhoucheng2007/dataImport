package com.blogzhou.manager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

import com.blogzhou.entity.PubStru;
/**
 * �˳������ܿ�Sql���ɹ�����
 * mailto: zhoucheng2007@gmail.com
 */

public class LogisticsEmployerImportManager extends BaseImportManager {

	public LogisticsEmployerImportManager() {
		// TODO Auto-generated constructor stub
	}

	@Test
	public void importEmployeeByPoiOfLogistics() {		
		String path="C:\\Users\\zc\\Desktop\\������¼\\20150421\\�����ܿ�Import.xls";  
        InputStream fis=null;
		try {
			fis = new FileInputStream(path);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} 		          
        try {   
            HSSFWorkbook hwb = new HSSFWorkbook(fis);   
            HSSFSheet sheet = hwb.getSheetAt(0);   
            HSSFRow row = null;   
            for(int i = 0; i < hwb.getNumberOfSheets(); i++) {   
                sheet = hwb.getSheetAt(i);   
                for(int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {   
                    row = sheet.getRow(j);    
                     String username=getCellValue(row.getCell(0));
                     String userid=getCellValue(row.getCell(1));
                     String model="1";
                     if("1".equals(model)){//��ӡ����
                         String sql="INSERT INTO PUB_USER_IMPORT_TEMP(USER_ID, USER_NAME) VALUES ('"+userid+"', '"+username+"');";
                         System.out.println(sql);
                     }else if("2".equals(model)){
                         String sqlsso="INSERT INTO PUB_USER_SSO (USER_ID, APP_CODE, APP_USER_ID, CERTIFICATE) VALUES ('"+username+"', '10277', '"+userid+"', null);";
                         System.out.println(j+" "+sqlsso);                    	 
                     }

                }   

            }   
        } catch (IOException e) {   
            e.printStackTrace();   
        }  
	}

	@Override
	public List<PubStru> importEmployeeByPoi() {
		// TODO Auto-generated method stub
		return null;
	}

}
