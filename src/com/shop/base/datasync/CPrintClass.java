package com.shop.base.datasync;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


public class CPrintClass 
{
	public void FPrintResultSet(ResultSet SrcRs)
	{
		int i=0;
		 try {
			ResultSetMetaData metaData=SrcRs.getMetaData();
			 int colum = metaData.getColumnCount();  
			 
			 
			 
			 
			 
			 //////////////////////////////////////
			 
			  while(SrcRs.next())
   		   { 
	    	      /* prest.setString(1, "192.168.1.1");  
	    	       prest.setString(2, "localhost");  
	    	       prest.setString(3, "20081009");  
	    	       prest.setInt(4, 8);  
	    	       prest.setString(5, "11111111");  
	    	       prest.addBatch(); 
	    	       */ 
	    		// srcrs[i].next();
	    		 i++;
   			  
   			  String rescol="";
   			  for(int j=1;j<=colum;j++)
   			  {
   				  String typeStr = ""; //����     //��ȡ����     
   	              String columName = metaData.getColumnName(j);     //��ȡÿһ�е��������     
   	              int type = metaData.getColumnType(j);     //�ж�     
   	            
   	              System.out.println("��"+ i +"�У���"+j+"�У� ���"+SrcRs.getString(j));
   	              
   	               
   	
   			  }
   			  
   			  
   			  //prest.addBatch();
   			  
	    	  } 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 ////////////////////////////////////
		     } 
		 catch (SQLException e) 
		 {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}

}
