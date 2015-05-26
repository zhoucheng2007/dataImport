package com.blogzhou.manager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

import com.blogzhou.entity.PubStru;
/**
 * ����ϵͳԱ��Sql����
 * �Լ�д�Ĺ�����
 * ����9:52:54
 * mailto: zhoucheng2007@gmail.com
 */

public class SecurityEmployerImportManager extends BaseImportManager {

	public SecurityEmployerImportManager() {
		// TODO Auto-generated constructor stub
	}

	
	public void importSSO() throws Exception {		
		String path="C:\\Users\\zc\\Desktop\\������¼\\20150520\\NC�������ϵͳ�û�����.xls";  
        InputStream fis=null;
        FileOutputStream fos=null;
        fos=new FileOutputStream("C:\\Users\\zc\\Desktop\\������¼\\20150520\\NC�������ϵͳ�û�����.sql");

			fis = new FileInputStream(path);
  
            HSSFWorkbook hwb = new HSSFWorkbook(fis);   
            HSSFSheet sheet = hwb.getSheetAt(0);   
            HSSFRow row = null;   
            StringBuffer sqlBuffer=new StringBuffer();
            for(int i = 0; i < hwb.getNumberOfSheets(); i++) {   
                sheet = hwb.getSheetAt(i);  
                int userAppId=12719;
                for(int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {   
                    row = sheet.getRow(j);    
                     String username=getCellValue(row.getCell(0));
                     String userid=getCellValue(row.getCell(1));
                     String organ_name=getCellValue(row.getCell(2));
                     //String sql="INSERT INTO PUB_USER_IMPORT_TEMP (USER_ID, USER_NAME,ORGAN_NAME) VALUES ('"+userid+"', '"+username+"', '"+organ_name+"');";
                     //String sqlsso="INSERT INTO PUB_USER_SSO (USER_ID, APP_CODE, APP_USER_ID, CERTIFICATE) VALUES ('"+username+"', '10271', '"+userid+"', null);";
                     userAppId++;
                     String sqlStore="INSERT INTO `v6ac`.`ac_user_app` (`user_app_id`, `app_id`, `version_id`, `uid`, `display_order`, `ctime`, `favourite`, `in_use`, `need_user_grant`, `mtime`, `ucount`, `is_user_granted`) VALUES ('"+userAppId+"', '10306', '981d26869b8b40519d36a40998955a8d', '"+userid+"', '0', '1427186328', '1', '1', '0', NULL, '0', '0');";
                     sqlBuffer.append(sqlStore+"\n");
                     
                     //System.out.println(sqlsso);
                }   

            }   
        fos.write(sqlBuffer.toString().getBytes());
        fos.flush();
        fos.close();
	}
	
	@Test
	public void importStoreSql()  throws Exception {
		String path="C:\\Users\\zc\\Desktop\\������¼\\20150526\\�����û�ӳ��.xls";  
        InputStream fis=null;
        FileOutputStream fos=null;
        fos=new FileOutputStream("C:\\Users\\zc\\Desktop\\������¼\\20150526\\NC�������ϵͳ�̵궩��.sql");

			fis = new FileInputStream(path);
  
            HSSFWorkbook hwb = new HSSFWorkbook(fis);   
            HSSFSheet sheet = hwb.getSheetAt(0);   
            HSSFRow row = null;   
            StringBuffer sqlBuffer=new StringBuffer();
            for(int i = 0; i < hwb.getNumberOfSheets(); i++) {   
                sheet = hwb.getSheetAt(i);  
                int userAppId=12719;
                for(int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {   
                    row = sheet.getRow(j);    
                     String username=getCellValue(row.getCell(1));
                     String userid=getCellValue(row.getCell(0));
                     String organ_name=getCellValue(row.getCell(2));
                     //String sql="INSERT INTO PUB_USER_IMPORT_TEMP (USER_ID, USER_NAME,ORGAN_NAME) VALUES ('"+userid+"', '"+username+"', '"+organ_name+"');";
                     //String sqlsso="INSERT INTO PUB_USER_SSO (USER_ID, APP_CODE, APP_USER_ID, CERTIFICATE) VALUES ('"+username+"', '10271', '"+userid+"', null);";
                     userAppId++;
                     String sqlStore="INSERT INTO `v6ac`.`ac_user_app` (`user_app_id`, `app_id`, `version_id`, `uid`, `display_order`, `ctime`, `favourite`, `in_use`, `need_user_grant`, `mtime`, `ucount`, `is_user_granted`) VALUES ('"+userAppId+"', '10306', '981d26869b8b40519d36a40998955a8d', '"+userid+"', '0', '1427186328', '1', '1', '0', NULL, '0', '0');";
                     sqlBuffer.append(sqlStore+"\n");
                     
                     //System.out.println(sqlsso);
                }   

            }   
        fos.write(sqlBuffer.toString().getBytes());
        fos.flush();
        fos.close();
	}

	@Override
	public List<PubStru> importEmployeeByPoi() {
		// TODO Auto-generated method stub
		return null;
	}
	


}
