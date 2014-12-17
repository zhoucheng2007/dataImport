//package com.v6.base.datasync;
package com.shop.base.datasync;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class LogDbOper 
{
  public String SYNCINDEXID;
  
 public void GeneralValue()
 {
	 UUID iuuid=UUID.randomUUID();
	 SYNCINDEXID=iuuid.toString();
	
	 
 }
 public void DBOper(String TASKID,String SYNCRUNID,String LASTRUNTIME,String Status,String ERRORTYPE,String ERRORDESP,Connection dbcon,String iBK1,String iBK2,String dBK1,String dBK2)
 {
	 try 
	 {
		dbcon.commit();
	
		Statement dbstmt=dbcon.createStatement();
		System.out.println("insert into TableSyncIndex ( SYNCINDEXID,TASKID,LASTRUNTIME,STATUS,BK1,BK2,ERRORTYPE,ERRORDESP,SYNCRUNID) values('"+SYNCINDEXID+"','"+TASKID+"','"+LASTRUNTIME+"','"+Status+"','"+iBK1+"','"+iBK2+"','"+ERRORTYPE+"','"+ERRORDESP+"','"+SYNCRUNID+"')");
		dbstmt.executeUpdate("insert into TableSyncIndex ( SYNCINDEXID,TASKID,LASTRUNTIME,STATUS,BK1,BK2,ERRORTYPE,ERRORDESP,SYNCRUNID) values('"+SYNCINDEXID+"','"+TASKID+"','"+LASTRUNTIME+"','"+Status+"','"+iBK1+"','"+iBK2+"','"+ERRORTYPE+"','"+ERRORDESP+"','"+SYNCRUNID+"')" );
	//	System.out.println("insert into TableSyncDetail ( SYNCDETAILID,SYNCINDEXID,ERRORTYPE,ERRORDESP,BK1,BK2) values('"+SYNCDETAILID+"','"+SYNCINDEXID+"','"+ERRORTYPE+"','"+ERRORDESP+"','"+dBK1+"','"+dBK2+"')" );
	//	dbstmt.executeUpdate("insert into TableSyncDetail ( SYNCDETAILID,SYNCINDEXID,ERRORTYPE,ERRORDESP,BK1,BK2) values('"+SYNCDETAILID+"','"+SYNCINDEXID+"','"+ERRORTYPE+"','"+ERRORDESP+"','"+dBK1+"','"+dBK2+"')" );
	    dbcon.commit();
	    dbstmt.close();
	    
	 } 
	 catch (SQLException e)
	 {
		e.printStackTrace();
	 }
	 
	 
	 
 }
 
 
 public void DBRunOp(String SYNCRUN_ID,String LASTRUNTIME,String Status,String COM_ID,String USER_ID,Connection dbcon,String BK1,String BK2)
 {
	 try 
	 {
		dbcon.commit();
	
		Statement dbstmt=dbcon.createStatement();
		System.out.println("insert into TABLESYNCRUN ( SYNCRUN_ID,RUN_TIME,STATUS,BK1,BK2,COM_ID,USER_ID) values('"+SYNCRUN_ID+"','"+LASTRUNTIME+"','"+Status+"','"+BK1+"','"+BK2+"','"+COM_ID+"','"+USER_ID+"')");
		dbstmt.executeUpdate("insert into TABLESYNCRUN ( SYNCRUN_ID,RUN_TIME,STATUS,BK1,BK2,COM_ID,USER_ID) values('"+SYNCRUN_ID+"','"+LASTRUNTIME+"','"+Status+"','"+BK1+"','"+BK2+"','"+COM_ID+"','"+USER_ID+"')" );
	//	System.out.println("insert into TableSyncDetail ( SYNCDETAILID,SYNCINDEXID,ERRORTYPE,ERRORDESP,BK1,BK2) values('"+SYNCDETAILID+"','"+SYNCINDEXID+"','"+ERRORTYPE+"','"+ERRORDESP+"','"+dBK1+"','"+dBK2+"')" );
	//	dbstmt.executeUpdate("insert into TableSyncDetail ( SYNCDETAILID,SYNCINDEXID,ERRORTYPE,ERRORDESP,BK1,BK2) values('"+SYNCDETAILID+"','"+SYNCINDEXID+"','"+ERRORTYPE+"','"+ERRORDESP+"','"+dBK1+"','"+dBK2+"')" );
	    dbcon.commit();
	    dbstmt.close();
	    
	 } 
	 catch (SQLException e)
	 {
		e.printStackTrace();
	 }
	 
	 
	 
 }
 
 public int DBLockOp(String taskId,Connection dbcon)
 {
	 try 
	 {
		dbcon.commit();
	    dbcon.setAutoCommit(true);
	    
		Statement dbstmt=dbcon.createStatement();
		System.out.println("update tablesynctime set taskstatus='1'  where taskId='"+taskId+"' and taskstatus='0'"  );
		int ilock=dbstmt.executeUpdate("update tablesynctime set taskstatus='1'  where taskId='"+taskId+"' and taskstatus='0'" );
	    System.out.println("ilock "+Integer.toString(ilock));
		dbstmt.close();
	    if(ilock==0)
	    {
	    	 dbcon.setAutoCommit(false);
	    	 return 0;
	    }
	    else if(ilock==1)
	    {
	    	 dbcon.setAutoCommit(false);
	    	 return 1;
	    }

	    return 2;
	 } 
	 catch (SQLException e)
	 {
		 e.printStackTrace();
		 System.out.println("DBLockOp ERROR");
		 try
		 {
			dbcon.setAutoCommit(false);
	   	} 
		 catch (SQLException e1) 
	   	{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
		 return 3;
	 }
	 
	 
	 
 }
 
 
 public int DBUnLockOp(String taskId,Connection dbcon)
 {
	 try 
	 {
		dbcon.commit();
	    dbcon.setAutoCommit(true);
	    
		Statement dbstmt=dbcon.createStatement();
		System.out.println("update set taskstatus='0' from synctimetable where taskId='"+taskId+"' and taststatus='1'" );
		int ilock=dbstmt.executeUpdate("update tablesynctime set taskstatus='0'  where taskId='"+taskId+"' and taskstatus='1'" );
	    dbstmt.close();
	    if(ilock==0)
	    {
	    	 dbcon.setAutoCommit(false);
	    	 return 0;
	    }
	    else if(ilock==1)
	    {
	    	 dbcon.setAutoCommit(false);
	    	 return 1;
	    }

	    return 2;
	 } 
	 catch (SQLException e)
	 {
		 try
		 {
			dbcon.setAutoCommit(false);
	   	} 
		 catch (SQLException e1) 
	   	{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
		 return 3;
	 }
	 
	 
	 
 }
 
 
 
 
 
 
}



