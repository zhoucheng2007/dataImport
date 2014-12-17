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

public class OraginationImportManager extends BaseImportManager {

	public OraginationImportManager() {
		
	}

	@Override
	public List<PubStru> importEmployeeByPoi()   {   
		
			String path="department.xls";
			
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
	            //����ñ�������еĹ���?i��ʾ�������� getNumberOfSheets��ʾ����������    
	            for(int i = 0; i < hwb.getNumberOfSheets(); i++) {   
	                sheet = hwb.getSheetAt(i);   
	                //����������е���,j��ʾ���� getPhysicalNumberOfRows�е�����   
	                for(int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {   
	                    row = sheet.getRow(j);   
	                    pubstru = new PubStru();   
	                     String orgid=getCellValue(row.getCell(1));
	                    //�˷�������getCellValue(HSSFCell cell)�Խ����4����ݽ����жϣ�������Ӧ�Ĵ���   
	                    if(orgid != null && !"".equals(orgid)) {   
	                    	pubstru.setOrganId(orgid);   
	                    }   
	                    
	                    pubstru.setPubStruType("00");  
	                    String parentId=getCellValue(row.getCell(4));

	                    if(parentId != null && !"".equals(parentId)){    
		                    pubstru.setParentId(getCellValue(row.getCell(4)));    
	                    }else{
	                    	Map<String,String> map=new HashMap<String,String>();
	                    	map.put("�ϲ����̲�ר���(��˾)", "113601");
	                    	map.put("���������̲�ר���(��˾)", "113602");
	                    	map.put("Ƽ�����̲�ר���(��˾)", "113603");
	                    	map.put("�Ž����̲�ר���(��˾)", "113604");
	                    	map.put("�������̲�ר���(��˾)", "113605");
	                    	map.put("ӥ̶���̲�ר���(��˾)", "113606");
	                    	map.put("�������̲�ר���(��˾)", "113607");
	                    	map.put("�������̲�ר���(��˾)", "113608");
	                    	map.put("�˴����̲�ר���(��˾)", "113609");
	                    	map.put("�������̲�ר���(��˾)", "113610");
	                    	map.put("�������̲�ר���(��˾)", "113611");
	                    	map.put("����ʡ�̲�ר���(��˾)", "113600");
	                    	map.put("������Ҷ�����������ι�˾", "143607");
	                    	map.put("����ɽ��ͳ������", "888801");
		                    String parentName=getCellValue(row.getCell(0));
	                    	parentId=map.get(parentName);
		                    pubstru.setParentId(parentId);    
	                    }

	                    //Ĭ��Ϊ0����ʼ����ʱ��
	                    pubstru.setStruLevel(0);
	                    //��ʼ��Ĭ��Ϊ��
	                    pubstru.setStruPath("rootId#1");
	                    pubstru.setCtime(new Date());
	                    pubstru.setStruName(getCellValue(row.getCell(2)));
	                    pubstru.setStruCode(getCellValue(row.getCell(1)));

	                    String s="";
	                    /**
	                     * ��װ���
	                     */
	                    if(j<10){
	                    	s="S000000000000000000"+j;	                    	
	                    }else if(j<100){
	                    	 s="S00000000000000000"+j;	
	                    }else if(j<1000){
	                    	 s="S0000000000000000"+j;	
	                    }else if(j<10000){
	                    	s="S000000000000000"+j;	
	                    }
	                    pubstru.setStruId(s);
	                    //Ĭ��0������Ҷ�ӽڵ�
	                    char c='0';
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
