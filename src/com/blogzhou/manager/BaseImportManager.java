package com.blogzhou.manager;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;

import com.blogzhou.entity.PubStru;

public  abstract  class BaseImportManager {
	
	   protected String getCellValue(HSSFCell cell){  		   	   
	        String value = null;  
			   if(cell==null){
				   return value;
			   }
	        switch(cell.getCellType())  
	        {  
	            case HSSFCell.CELL_TYPE_STRING://�ַ�  
	                value = cell.getRichStringCellValue().getString();  
	                break;  
	            case HSSFCell.CELL_TYPE_NUMERIC://����  
	                long dd = (long)cell.getNumericCellValue();  
	                value = dd+"";  
	                break;  
	            case HSSFCell.CELL_TYPE_BLANK:  
	                value = "";  
	                break;     
	            case HSSFCell.CELL_TYPE_FORMULA:  
	                value = String.valueOf(cell.getCellFormula());  
	                break;  
	            case HSSFCell.CELL_TYPE_BOOLEAN://boolean��ֵ  
	                value = String.valueOf(cell.getBooleanCellValue());  
	                break;  
	            case HSSFCell.CELL_TYPE_ERROR:  
	                value = String.valueOf(cell.getErrorCellValue());  
	                break;  
	            default:  
	                break;  
	        }  
	        //System.out.println(value);
	        return value;  
	    }  
	
	  public abstract List<PubStru> importEmployeeByPoi();
	   
}
