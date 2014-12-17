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

import com.blogzhou.entity.PubStru;

public class EmployerImportManager extends BaseImportManager {

	public EmployerImportManager() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<PubStru> importEmployeeByPoi() {
		
		String path="employee.xls";
		
		//������   
        InputStream fis=null;
		try {
			fis = new FileInputStream(path);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} 
		
        List<PubStru> infos = new ArrayList<PubStru>();   
        PubStru pubstru = null;   
           
        try {   
            //����Excel����   
            HSSFWorkbook hwb = new HSSFWorkbook(fis);   
            //�õ���һ�����   
            HSSFSheet sheet = hwb.getSheetAt(0);   
            HSSFRow row = null;   
            //���ڸ�ʽ��   
            DateFormat ft = new SimpleDateFormat("yyyy-MM-dd");   
            //Ա����ID��ʼ��š�
            int temp=1743;
            //����ñ�������еĹ���?i��ʾ�������� getNumberOfSheets��ʾ����������    
            for(int i = 0; i < hwb.getNumberOfSheets(); i++) {   
                sheet = hwb.getSheetAt(i);   
                //����������е���,j��ʾ���� getPhysicalNumberOfRows�е�����   
                for(int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {   
                    row = sheet.getRow(j);   
                    pubstru = new PubStru();   
                     String orgid=getCellValue(row.getCell(4));
                    //�˷�������getCellValue(HSSFCell cell)�Խ����4����ݽ����жϣ�������Ӧ�Ĵ���   
                    if(orgid != null && !"".equals(orgid)) {   
                    	pubstru.setOrganId(orgid);   
                    }   
                    
                    pubstru.setPubStruType("00");  
                    String parentId=getCellValue(row.getCell(3));

                    if(parentId != null && !"".equals(parentId)){    
	                    pubstru.setParentId(getCellValue(row.getCell(3)));    
                    }

                    
                    //Ĭ��Ϊ0����ʼ����ʱ��
                    pubstru.setStruLevel(0);
                    //��ʼ��Ĭ��Ϊ��
                    pubstru.setStruPath("rootId#1");
                    pubstru.setCtime(new Date());
                    pubstru.setStruName(getCellValue(row.getCell(5)));
                    pubstru.setStruCode(getCellValue(row.getCell(4)));

                    String s="";
                    /**
                     * ��װ���
                     */
                    temp=temp+1;
                    if(temp<10){
                    	s="S000000000000000000"+temp;	                    	
                    }else if(temp<100){
                    	 s="S00000000000000000"+temp;	
                    }else if(temp<1000){
                    	 s="S0000000000000000"+temp;	
                    }else if(temp<10000){
                    	s="S000000000000000"+temp;	
                    }else if(temp<100000){
                    	s="S00000000000000"+temp;	
                    }
                    pubstru.setStruId(s);
                    //��Ҷ�ӽڵ�
                    char c='1';
                    pubstru.setIsLeaf(c);
                    //Ĭ��ʹ��
                    char u='1';
                    pubstru.setInUse(u);
                    infos.add(pubstru);   
	                System.out.println("the ("+j+") elements was :"+pubstru.getStruName());
                }   

            }   
        } catch (IOException e) {   
            e.printStackTrace();   
        }   
        return infos;   
	}

}
