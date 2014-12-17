package com.shop.base.datasync;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;


import java.sql.*;




import java.util.*;
import java.util.Date;  

import java.util.List;
public class DataSync
{

	
	//0 成功
	//return 11 没找到目标数据源
	//return !=0 任务失败
	// 12 任务均已执行，但有失败
	// 13 写日志头表失败
	// 14 ClassNotFoundException
	// 15 一般性失败 或 连不上数据库
	public static int DataSync(String DataSyncFileDir,String log4jFileDir,String SrcDBJndi, String DesDBJndi,String Com_ID,String User_ID,List<String> TaskID, PipeLineSQLStruct o_PLSQLS, List <String> l_DesSQLS)
	{
		/*
		String Com_ID="comid_ta";
		String User_ID="user_ta";
		
		List <String> TaskID=new ArrayList<String>();
		TaskID.add("B0000");
		TaskID.add("B0100");
		TaskID.add("B0200");
		TaskID.add("B0300");
		TaskID.add("B0400");
		TaskID.add("B0500");
		TaskID.add("B0600");
		TaskID.add("B1000");
		TaskID.add("B1100");
		TaskID.add("B1200");
		TaskID.add("B1300");
		TaskID.add("B1400");
		TaskID.add("B1500");
		TaskID.add("B1600");
		TaskID.add("B1700");
		TaskID.add("B1800");
		TaskID.add("B1900");
		TaskID.add("B1A00");
		TaskID.add("B1B00");
		TaskID.add("B2000");
		TaskID.add("B2100");
		TaskID.add("B2200");
		TaskID.add("B3000");
		TaskID.add("B4000");
		TaskID.add("B4100");
		TaskID.add("B4200");
		TaskID.add("B4300");
		TaskID.add("B4400");
		TaskID.add("B4500");
		TaskID.add("B5000");
		TaskID.add("B5100");
		*/
		//////////////////////////////////////////////////////////
		int tasknum=0;
		int resflag=0;
		 UUID duuid=UUID.randomUUID();
		 String SYNCTASKRUNID=duuid.toString();
		
		
		
		PropertyConfigurator.configure(log4jFileDir);
		
	
		
		//CMyLog obj1=new CMyLog();
		Logger log=Logger.getLogger(DataSync.class);
		log.warn("这是日志");
		
		
		
		CConfig objCConfig=new CConfig(DataSyncFileDir);
		
		
		
		for(int k=0;k< objCConfig.ObjDesDbInfo.size();k++)
		{
			//if(objCConfig.)
			//{
			//	
			//}
			if(objCConfig.ObjDesDbInfo.get(k).TaskID.equals(TaskID.get(0).substring(3)))
			{
				objCConfig.DesDSName=objCConfig.ObjDesDbInfo.get(k).DesDSName;
				objCConfig.DesIsUseV3=objCConfig.ObjDesDbInfo.get(k).DesIsUseV3;
				objCConfig.DesType=objCConfig.ObjDesDbInfo.get(k).DesType;
				objCConfig.DesJdbcURL=objCConfig.ObjDesDbInfo.get(k).DesJdbcURL;
				objCConfig.DesUserName=objCConfig.ObjDesDbInfo.get(k).DesUserName;
				objCConfig.DesPassWord=objCConfig.ObjDesDbInfo.get(k).DesPassWord;
				System.out.println("找到数据源 "+objCConfig.DesJdbcURL);
				break;
			}
			if(k==(objCConfig.ObjDesDbInfo.size()-1))
			{
				System.out.println("没有找到目标数据源");
				log.warn("没有找到目标数据源");
			    return 11 ;//11
			}
		}
		
		
		
		
		Connection srcconn=null;
		Connection desconn=null;
		Connection logconn=null;
		//Statement srcstmtQuery[]=new Statement[Integer.parseInt(objCConfig.PipeLineSQLCount)];
		
	//	PreparedStatement srcstmtQuery[]=new PreparedStatement[Integer.parseInt(objCConfig.PipeLineSQLCount)];
		  
		  
		 Statement srcstmt=null;
		 Statement desstmt=null;
		 Statement logstmt=null;
		///////////////////////////////////////////////////
		//读取原数据库
	try
	{
		if(objCConfig.SrcIsUseV3.equals("0"))
		{
			Class.forName(objCConfig.SrcJdbcDriverClassName);
			//Class.forName(objCConfig.SrcJdbcDriverClassName);
		    srcconn = DriverManager.getConnection(objCConfig.SrcJdbcURL,objCConfig.SrcUserName,objCConfig.SrcPassWord);
		    
		    System.out.println("连接源数据库成功 连接字符串 "+objCConfig.SrcJdbcURL);
		    log.warn("连接源数据库成功 连接字符串 "+objCConfig.SrcJdbcURL);
		  
		  //  srcconn.setTransactionIsolation(1);
		    
		    srcconn.setAutoCommit(false); 
		    
		    srcconn.commit();
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    log.warn("准备连接目标数据库 连接字符串  "+objCConfig.DesJdbcURL);
	    	Class.forName(objCConfig.DesJdbcDriverClassName);
		    desconn = DriverManager.getConnection(objCConfig.DesJdbcURL,objCConfig.DesUserName,objCConfig.DesPassWord);
		    
		    System.out.println("连接目标数据库成功 连接字符串  "+objCConfig.DesJdbcURL);
		    log.warn("连接目标数据库成功 连接字符串  "+objCConfig.DesJdbcURL);
		    desconn.setAutoCommit(false);
		    
		    desconn.commit();
		    
		    
		    
		    
		    
		    
		    Class.forName(objCConfig.DesDBCLOG.DesJdbcDriverClassName);
			//Class.forName(objCConfig.SrcJdbcDriverClassName);
		    logconn = DriverManager.getConnection(objCConfig.DesDBCLOG.DesJdbcURL,objCConfig.DesDBCLOG.DesUserName,objCConfig.DesDBCLOG.DesPassWord);
		    System.out.println("连接日志数据库成功 连接字符串 "+objCConfig.DesDBCLOG.DesJdbcURL);
		    log.warn("连接日志数据库成功 连接字符串 "+objCConfig.DesDBCLOG.DesJdbcURL);
		  
		  //  srcconn.setTransactionIsolation(1);
		    
		    logconn.setAutoCommit(false); 
		    
		    logconn.commit();
		    
		    
		    
		    LogDbOper objRunLDO=new LogDbOper();
		    objRunLDO.DBRunOp(SYNCTASKRUNID, (new java.text.SimpleDateFormat( "yyyy-MM-dd HH:mm:ss")).format(new Date()), "0", Com_ID, User_ID, logconn, "", "");
		   
		    
		    
		    //////////////////////////////////////////////////////////////////////
		    for(int w=0;w<TaskID.size();w++)
		    {   
		    	System.out.println("taskID 内循环");
		    	
		    	for(int s=0;s<objCConfig.PipeLineSQL.size();s++)
		    		
		    	{
		    	
		    	//	System.out.println("PipeLineSQL 内循环"+ TaskID.get(w).substring(0, 3) + objCConfig.PipeLineSQL.get(s).TaskID );
		    		if(TaskID.get(w).substring(0, 3).equals(objCConfig.PipeLineSQL.get(s).TaskID))
		    			
		    		{
		    	
		    System.out.println("找到了合适的TaskID");
		    tasknum++;
		    ResultSet srcrst=null;
		    PreparedStatement srcstmtQueryt=null;
		    
		    try
		    {
		    
		    LogDbOper objLOCKLDO=new LogDbOper();
		    if(1==objLOCKLDO.DBLockOp( TaskID.get(w), logconn))
		    {
		    	System.out.println("成功加锁");
		    }
		    else
		    {
		    	System.out.println("失败加锁");
		    	LockFailSQLException objLFSE=new LockFailSQLException();
		    	objLFSE.errordesp="其他任务正在运行";
		    	throw objLFSE;
		    }
		    
		    
		    
		    
		    
		    //ResultSet srcrs[]=new ResultSet[Integer.parseInt(objCConfig.PipeLineSQLCount)];
		    
		    srcstmt=srcconn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);//创建SQL命令对象
		    
		    
		    //////////////////////////////////
		   // desstmt=desconn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);//创建SQL命令对象
		    desstmt=desconn.createStatement();//创建SQL命令对象
		    
		    logstmt=logconn.createStatement();//创建SQL命令对象
		    ////////////////////////////////
		    Timestamp nowts=null;
		    //System.out.println("连接源数据库成功 连接字符串 "+);
		    
		    
		   
		    	
		    
		
		    
		    ResultSet rsNowTs=null;
		    ResultSet rsTsLastSync=null;
		   
		    //////////////取时间戳
		    if(objCConfig.SrcType.equals("ORACLE"))
			{
				
		    
		     // rsNowTs=srcstmt.executeQuery("select systimestamp from dual");//oracle
			}
			if(objCConfig.SrcType.equals("DB2"))
			{
				
				ResultSet rsNowTsDB=srcstmt.executeQuery("SELECT current timestamp FROM sysibm.sysdummy1");//返回SQL语句查询结果集(集合)db2
				rsNowTsDB.next();
				nowts=rsNowTsDB.getTimestamp(1) ;
				rsNowTsDB.close();
			}
		    
			// rsNowTs.next();
			//	Timestamp  tsNowTs = rsNowTs.getTimestamp(1);
			
				///////////////////
				//Date now = new Date();
			   String stsNowTs;
			   if(objCConfig.TimeStampType.toUpperCase().equals("DATE"))
			   {
				 stsNowTs=(new java.text.SimpleDateFormat(  
                "yyyyMMdd")).format(new Date()); 
				
				 log.warn("同步格式 yyyyMMdd " + stsNowTs);
				
			   }
			   else if(objCConfig.TimeStampType.toUpperCase().equals("MONTH"))
			   {
				 stsNowTs=(new java.text.SimpleDateFormat(  
                "yyyyMM")).format(new Date());  
				 log.warn("同步格式 yyyyMM " + stsNowTs);
			   }
			   else if(objCConfig.TimeStampType.toUpperCase().equals("YEAR"))
			   {
				 stsNowTs=(new java.text.SimpleDateFormat(  
                "yyyy")).format(new Date()); 
				 log.warn("同步格式 yyyy "+stsNowTs );
			   }
			   else 
			   {//error
				   
				   log.error( "配置文件  TimeStampType 读取出错");
				   Exception e = null; 
				   throw e;
			   }

			log.warn("日志数据库准备执行  "+objCConfig.GetTimeStampSQL.replace("^TASKID^", TaskID.get(w)));
			rsTsLastSync=logstmt.executeQuery(objCConfig.GetTimeStampSQL.replace("^TASKID^", TaskID.get(w)));
			rsTsLastSync.next();
			String  tsLastSync = rsTsLastSync.getTimestamp(1).toString();
			log.warn("获得上次同步时间戳  "+tsLastSync);
			///////////////////
			
			rsTsLastSync.close();
			
			
			//////////////
			
			
				 
				 if(objCConfig.SrcType.equals("ORACLE"))
					{
						
					 log.warn("时间戳变量准备替换  "+ objCConfig.PipeLineSQL.get(s).SelectSql );
					 objCConfig.PipeLineSQL.get(s).SelectSql=objCConfig.PipeLineSQL.get(s).SelectSql.replace("^TIMESTAMP^", tsLastSync  );
					log.warn("时间戳变量替换 结果 "+ objCConfig.PipeLineSQL.get(s).SelectSql );
					}
					if(objCConfig.SrcType.equals("DB2"))
					{
						
						 log.warn("时间戳变量准备替换  "+ objCConfig.PipeLineSQL.get(s).SelectSql );
						 objCConfig.PipeLineSQL.get(s).SelectSql=objCConfig.PipeLineSQL.get(s).SelectSql.replace("^TIMESTAMP^", tsLastSync  );
						log.warn("时间戳变量替换 结果 "+ objCConfig.PipeLineSQL.get(s).SelectSql );
						
						
					  //rsNowTs=srcstmt.executeQuery("SELECT current timestamp FROM sysibm.sysdummy1");//返回SQL语句查询结果集(集合)db2
					}
  
		    if(objCConfig.DesIsUseV3.equals("0"))
			{//目标数据库使用自有连接
		    	
					    	  if(srcconn!=null)
							    {
							    	
										try
										{
											log.warn("源数据库准备执行  "+objCConfig.PipeLineSQL.get(s).SelectSql.trim());
											srcstmtQueryt=srcconn.prepareStatement(objCConfig.PipeLineSQL.get(s).SelectSql.trim(),ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
										   
											srcstmtQueryt.setFetchSize(2000);
											
											//srcrs[i].
										} 
										catch (SQLException e)
										{
											// TODO Auto-generated catch block
										 e.printStackTrace();
										}//创建SQL命令对象
									
							    	
							    }
					    	  
					    	  //srcstmtQuery[k].
					    	 
					    	//srcstmtQuery[i]=srcconn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
					    	srcrst=srcstmtQueryt.executeQuery();
					    	
					    	
					    	log.warn("源数据库执行成功 "+objCConfig.PipeLineSQL.get(s).SelectSql.trim());
					    	
					    
			    	
			    	
			    	
			    	
			    	
			    	
			    	////////////////////////////////////////////////////////
			    	//srcrs[i]=srcstmt.executeQuery(objCConfig.PipeLineSQL.get(i).SelectSql);
			    	try
			    	{
			    		log.warn("目标数据库准备执行   "+objCConfig.PipeLineSQL.get(s).DropSql);
			    	desstmt.executeUpdate(objCConfig.PipeLineSQL.get(s).DropSql);
			    	
			    	log.warn("目标数据库执行成功   "+objCConfig.PipeLineSQL.get(s).DropSql);
			    	}
			    	catch(SQLException err)
			    	{
			    		
			    		
			    	}
			    	log.warn("目标数据库准备执行   "+objCConfig.PipeLineSQL.get(s).CreateSql);
			    	desstmt.executeUpdate(objCConfig.PipeLineSQL.get(s).CreateSql);
			    	log.warn("目标数据库执行成功   "+objCConfig.PipeLineSQL.get(s).CreateSql);
			    	
			    	 PreparedStatement prest = desconn.prepareStatement(objCConfig.PipeLineSQL.get(s).InsertSqlFSrc,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);  
			    	 
			    	 log.warn("目标数据库准备执行  "+objCConfig.PipeLineSQL.get(s).InsertSqlFSrc);
			    	 ResultSetMetaData metaData = srcrst.getMetaData();  
			    	 
			    	// log.warn("positiong 1");
			    	 
		    		 int colum = metaData.getColumnCount();     
		    	     
		    		// log.warn("colum "+colum);
		    		 /////////////////////////////
		    		 int R=0;
		    		
		    		 //System.out.println("position 2");
				    	
		    		 //log.warn("开始循环 ");
		    		  while( srcrst.next() )
		    		   { 
			    	     
			    		// srcrs[i].next();
		    			  /////////////////////
			    		  R++;
		    			  /////////////////////
		    			  String rescol="";
		    			  for(int j=1;j<=colum;j++)
		    			  {
		    				//  log.warn("获取类型 ");
		    				  String typeStr = ""; //类型     //获取列名     
		    	              String columName = metaData.getColumnName(j);     //获取每一列的数据类型     
		    	              int type = metaData.getColumnType(j);     //判断     
		    	          //    log.warn("开始判断类型");
		    	              if(Types.INTEGER == type)     
		    	                {     //typeStr = "Integer";     
		    	            	
		    	            	prest.setInt(j,srcrst.getInt(j));
		    		
		                      	}     
		    	                else if(Types.VARCHAR == type)     
		    	                {     
		    	             	  
		    	                prest.setString(j, srcrst.getString(j));
		    	                }     
		    	              
		    	                else if(Types.CHAR == type)     
		    	                {     
		    	             	  
		    	                prest.setString(j, srcrst.getString(j));
		    	                }  
		    	              
		    	                else if(Types.DATE == type)     
		    	                {     
		    	             	  
		    	                prest.setDate(j, srcrst.getDate(j));
		    	                }  
		    	              
		    	                else if(Types.DECIMAL == type)     
		    	                {     
		    	             	  
		    	                prest.setDouble(j, srcrst.getDouble(j));
		    	                }  
		    	              
		    	                else if(Types.DOUBLE == type)     
		    	                {     
		    	             	  
		    	                prest.setDouble(j, srcrst.getDouble(j));
		    	                }  
		    	              
		    	                else if(Types.FLOAT == type)     
		    	                {     
		    	             	  
		    	                prest.setFloat(j, srcrst.getFloat(j));
		    	                }  
		    	              
		    	                else if(Types.NUMERIC == type)     
		    	                {     
		    	             	  
		    	                prest.setDouble(j, srcrst.getDouble(j));
		    	                }  
		    	              
		    	                else if(Types.NULL == type)     
		    	                {     
		    	             	  
		    	                prest.setNull(j, Types.NULL );
		    	                }  
		    	              
		    	                else if(Types.REAL == type)     
		    	                {     
		    	             	  
		    	                prest.setDouble(j, srcrst.getDouble(j));
		    	                }  
		    	              
		    	                else if(Types.TIME == type)     
		    	                {     
		    	             	  
		    	                prest.setTime(j, srcrst.getTime(j));
		    	                }  
		    	              
		    	                else if(Types.TIMESTAMP == type)     
		    	                {     
		    	             	  
		    	                prest.setTimestamp(j, srcrst.getTimestamp(j));
		    	                }  
		    	              
		    	                else if(Types.BIGINT == type)     
		    	                {     
		    	             	  
		    	                prest.setLong(j, srcrst.getLong(j));
		    	                } 
		    	              
		    	                else if(Types.BLOB == type)     
		    	                {     
		    	             	  
		    	                prest.setBlob(j, srcrst.getBlob(j));
		    	                } 
		    	              
		    	               
		    	
		    			  }
		    			  
		    			//  log.warn("开始插入一条记录");
		    			  prest.addBatch();
		    			//  log.warn("结束插入一条记录");
		    			  ///////////////
		    		
		    			  if(R%2000==0)
		    			  {
		    			  prest.executeBatch(); 
		    			  prest.clearBatch();
		    			  
		    			  }
		    			
		    			  ////////////////////
		    			  
			    	  }  
			    	   
		    		  log.warn("目标数据库执行成功  "+objCConfig.PipeLineSQL.get(s).InsertSqlFSrc);
			    	   
			    		  prest.executeBatch(); 
			    		  
			    		  prest.close();
			    		  
			    		  srcrst.close();
			    		  
			    		  srcstmtQueryt.close();
			    //////////20140126	
			      desstmt.executeUpdate(objCConfig.PipeLineSQL.get(s).SyncSqlDelete);
			      desstmt.executeUpdate(objCConfig.PipeLineSQL.get(s).SyncSqlInsert);
	
			}
			else
			{//目标数据库使用V3连接
				
				
			}
		    
		   
		    PreparedStatement logprest =null;
		    
		    try
		    {
		    String UpdateTSLastSync="update tablesynctime set lastsynctime = ? where taskid='"+TaskID.get(w)+"'";
		    
		    
		    
		    ///////////////////////////////
		    
		   logprest = logconn.prepareStatement(UpdateTSLastSync,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);  
		    
		    //////////////////////////////////////
		    
		    
		    log.warn("日志数据库准备执行   "+UpdateTSLastSync);
		    
		    logprest.setTimestamp(1, nowts);
		    logprest.addBatch();
		    logprest.executeBatch();
		    
		   
		   // logstmt.executeUpdate(UpdateTSLastSync);
		    
		    log.warn("日志数据库执行成功   "+UpdateTSLastSync);
		    
		    
		    }
		    catch(SQLException exc)
		    {
		      exc.printStackTrace();
		    	
		    }
		    finally
		    {
		    	
		    	if(logprest!=null)
		    	{
		    		logprest.close();
		    	}
		    }
		    
		    
		    
		    log.warn("数据库准备提交   ");
		    
		    
		
		    
		    
		    desconn.commit();
		    srcconn.commit();
		    
		    log.warn("数据库提交成功   ");
		    System.out.println("DataSync Success");
		    
		    
		    
		    
		    ///////////////////////////////////////////////
	  	    
		  	   LogDbOper objUNLOCKLDO=new LogDbOper();
			    if(1==objUNLOCKLDO.DBUnLockOp( TaskID.get(w), logconn))
			    {
			    	System.out.println("成功解锁"+TaskID.get(w));
			    }
			    else
			    {
			    	System.out.println("失败解锁或不用解锁"+TaskID.get(w));
			    	resflag=5;
			   
			    }
			    
			    ////////////////////////////////////////////////////
			    
			    
		    
		    
		    
		    LogDbOper ObjLDO=new LogDbOper();
		    ObjLDO.GeneralValue();
		    ObjLDO.DBOper(TaskID.get(w),SYNCTASKRUNID, (new java.text.SimpleDateFormat( "yyyy-MM-dd HH:mm:ss")).format(new Date()), "0", "0", "", logconn, "", "", "", "");
		    
		    
		    		
		    
		    }
		    catch(LockFailSQLException lw)
		    {
		    	lw.printStackTrace();
		    	
		    	resflag=1;
		    	System.out.println("SQLException"+lw.errordesp);
		    	
		    	desconn.rollback();
		    	srcconn.rollback();
		    
		    	    LogDbOper ObjLDO=new LogDbOper();
		    	    ObjLDO.GeneralValue();
			  	    ObjLDO.DBOper(TaskID.get(w),SYNCTASKRUNID, (new java.text.SimpleDateFormat( "yyyy-MM-dd HH:mm:ss")).format(new Date()), "1", "1",lw.errordesp, logconn, "", "", "", "");
			  
		    	
			  	    
			  	    
		    }
		    catch(SQLException e)
		    {
		    	e.printStackTrace();
		    	
		    	resflag=2;
		    	System.out.println("SQLException"+e.getMessage());
		    	
		    	desconn.rollback();
		    	srcconn.rollback();
		    
		    	    LogDbOper ObjLDO=new LogDbOper();
		    	    ObjLDO.GeneralValue();
			  	    ObjLDO.DBOper(TaskID.get(w),SYNCTASKRUNID, (new java.text.SimpleDateFormat( "yyyy-MM-dd HH:mm:ss")).format(new Date()), "1", "1", e.getMessage(), logconn, "", "", "", "");
			  /////////////////////////////////////////
			  	   LogDbOper objUNLOCKLDO=new LogDbOper();
				    if(1==objUNLOCKLDO.DBUnLockOp( TaskID.get(w), logconn))
				    {
				    	System.out.println("成功解锁"+TaskID.get(w));
				    }
				    else
				    {
				    	System.out.println("失败解锁或不用解锁"+TaskID.get(w));
				    	resflag=4;
				   
				    }
		    	//////////////////////////////////////////
		    }
		    catch(Exception e)
		    {
		    	e.printStackTrace();
		    	
		    	resflag=3;
		    	desconn.rollback();
		    	srcconn.rollback();
		    
		    	    LogDbOper ObjLDO=new LogDbOper();
		    	    ObjLDO.GeneralValue();
			  	    ObjLDO.DBOper(TaskID.get(w), SYNCTASKRUNID ,(new java.text.SimpleDateFormat( "yyyy-MM-dd HH:mm:ss")).format(new Date()), "1", "0", e.getMessage(), logconn, "", "", "", "");
			  	// srcconn.commit();
			  	    
			  	    ///////////////////////////////////////////////
			  	    
			  	   LogDbOper objUNLOCKLDO=new LogDbOper();
				    if(1==objUNLOCKLDO.DBUnLockOp( TaskID.get(w), logconn))
				    {
				    	System.out.println("成功解锁"+TaskID.get(w));
				    }
				    else
				    {
				    	System.out.println("失败解锁或不用解锁"+TaskID.get(w));
				    	resflag=4;
				   
				    }
				    
				    ////////////////////////////////////////////////////
		    }
		    finally
		    {
		    	
		    	
		    	  if(srcrst!=null)
				  {
					  srcrst.close();
				  }
		    	  if(srcstmtQueryt!=null)
		    	  {
		    		  srcstmtQueryt.close();
		    	  }
		    }
		    
		    
		    
		    
			//原数据库使用自有连接
		
		    		}
			    }
				///////////////////////////////////////////////////////////////////////////////////////
			}
		    
		
		    ////////////////////////////////////////////////////////////
		}
		else
		{//原数据库使用V3连接
			
			
		}
	
	/*
		/////////////////////////
		if(resflag==0)
		{
			System.out.println("所有任务执行成功 任务数："+Integer.toString(tasknum));
		}
		else
		{
			System.out.println("任务执行不成功 任务数"+Integer.toString(tasknum)+ " "+ Integer.toString(resflag));
		}
		
		
		/////////////////////////////////////////////////////////////////////////////
		 * 
		 */
		
		
		if(1==1)
		{//清理没有释放的ResultSet
			
		}
		
		if(srcstmt!=null)
		{
			srcstmt.close();
		}
		if(desstmt!=null)
		{
			desstmt.close();
		}
		if(logstmt!=null)
		{
			logstmt.close();
		}
		if(srcconn!=null)
		{
			
			srcconn.close();
		}
		if(desconn!=null)
		{
			
			desconn.close();
		}
		
		
		
/////////////////////////
		if(resflag==0)
		{
			System.out.println("所有任务执行成功 任务数："+Integer.toString(tasknum));
			log.warn("所有任务执行成功 任务数："+Integer.toString(tasknum));
			
			return  0;
		}
		else
		{
			System.out.println("任务执行不成功 任务数"+Integer.toString(tasknum)+ " "+ Integer.toString(resflag));
			log.warn("任务执行不成功 任务数"+Integer.toString(tasknum)+ " "+ Integer.toString(resflag));
			
			return  12;
		}
		
		
		/////////////////////////////////////////////////////////////////////////////
		
	}
	catch(SQLException e)
	{
		e.printStackTrace();
		
		 log.warn(" SQLException   "+e.getMessage());
		try
		{
		if(srcstmt!=null)
		{
			srcstmt.close();
		}
		if(desstmt!=null)
		{
			 desstmt.close();
		}
		if(logstmt!=null)
		{
			logstmt.close();
		}
		
		if(srcconn!=null)
		{
			 log.warn("源数据库准备回滚!   ");
			srcconn.rollback();
			 System.out.println("DataSync Fail，src db rollback !");
			 log.warn("源数据库回滚完毕 !  ");
			srcconn.close();
		}
		if(desconn!=null)
		{
			 log.warn("目标数据库准备回滚   ");
			desconn.rollback();
			 System.out.println("DataSync Fail，des db rollback ! ");
			 log.warn("目标数据库回滚完毕   ");
			desconn.close();
		}
		
		if(logconn!=null)
		{
			 log.warn("日志数据库准备回滚   ");
			 logconn.rollback();
			 System.out.println("DataSync Fail，log db rollback ! ");
			 log.warn("日志数据库回滚完毕   ");
			 logconn.close();
		}
		
		
		}
		catch(SQLException ex)
		{
			
		}
	 System.out.println("Excepriont" + e.getMessage());
	 return 13;
	 
	}
		
	 catch (ClassNotFoundException e) 
	 {  
		 // Logger.getLogger(MyLogger.class.getName()).log(Level.SEVERE, null, ex);  
		 return 14;
	 }
	 catch(Exception e)
	 {
		 
		 System.out.println("["+e.getMessage()+"]");
		 return 15;
	 }
		

	}
	
	
	
	

	//0 成功
	//return 11 没找到目标数据源
	//return !=0 任务失败
	// 12 任务均已执行，但有失败
	// 13 写日志头表失败
	// 14 ClassNotFoundException
	// 15 一般性失败 或 连不上数据库
	public static List<CDataSyncSumResult> DataSyncSum(String log4jFileDir,String SrcDBURL,String SrcDBUsername,String SrcDBPassword, String DesDBURL,String DesDBUsername,String DesDBPassword,String Com_ID,String User_ID, List <PipeLineSQLStruct> o_PLSQLS)
	{
		List <CDataSyncSumResult> o_res_CSSR=new ArrayList <CDataSyncSumResult>();
		/*
		String Com_ID="comid_ta";
		String User_ID="user_ta";
		
		List <String> TaskID=new ArrayList<String>();
		TaskID.add("B0000");
		TaskID.add("B0100");
		TaskID.add("B0200");
		TaskID.add("B0300");
		TaskID.add("B0400");
		TaskID.add("B0500");
		TaskID.add("B0600");
		TaskID.add("B1000");
		TaskID.add("B1100");
		TaskID.add("B1200");
		TaskID.add("B1300");
		TaskID.add("B1400");
		TaskID.add("B1500");
		TaskID.add("B1600");
		TaskID.add("B1700");
		TaskID.add("B1800");
		TaskID.add("B1900");
		TaskID.add("B1A00");
		TaskID.add("B1B00");
		TaskID.add("B2000");
		TaskID.add("B2100");
		TaskID.add("B2200");
		TaskID.add("B3000");
		TaskID.add("B4000");
		TaskID.add("B4100");
		TaskID.add("B4200");
		TaskID.add("B4300");
		TaskID.add("B4400");
		TaskID.add("B4500");
		TaskID.add("B5000");
		TaskID.add("B5100");
		*/
		//////////////////////////////////////////////////////////
		int tasknum=0;
		int resflag=0;
		 UUID duuid=UUID.randomUUID();
		 String SYNCTASKRUNID=duuid.toString();
		
		
		
		PropertyConfigurator.configure(log4jFileDir);
		
	
		
		//CMyLog obj1=new CMyLog();
		Logger log=Logger.getLogger(DataSync.class);
		log.warn("这是日志");
		
		
		
		//CConfig objCConfig=new CConfig(DataSyncFileDir);
		
		
		
		
		
		
		Connection srcconn=null;
		Connection desconn=null;
		Connection logconn=null;
	
		  
		 Statement srcstmt=null;
		 Statement desstmt=null;
		 Statement logstmt=null;
		///////////////////////////////////////////////////
		//读取原数据库
	try
	{
		
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Class.forName(objCConfig.SrcJdbcDriverClassName);
		    srcconn = DriverManager.getConnection(SrcDBURL,SrcDBUsername,SrcDBPassword);
		    
		    System.out.println("连接源数据库成功 连接字符串 "+SrcDBURL);
		    log.warn("连接源数据库成功 连接字符串 "+SrcDBURL);
		  
		  //  srcconn.setTransactionIsolation(1);
		    
		    srcconn.setAutoCommit(false); 
		    
		    srcconn.commit();
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    log.warn("准备连接目标数据库 连接字符串  "+DesDBURL);
	    	Class.forName("oracle.jdbc.driver.OracleDriver");
		    desconn = DriverManager.getConnection(DesDBURL,DesDBUsername,DesDBPassword);
		    
		    System.out.println("连接目标数据库成功 连接字符串  "+DesDBURL);
		    log.warn("连接目标数据库成功 连接字符串  "+DesDBURL);
		    desconn.setAutoCommit(false);
		    
		    desconn.commit();
		    
		    
		    
		    
		    log.warn("准备连接日志数据库 连接字符串  "+DesDBURL);
		    
		    Class.forName("oracle.jdbc.driver.OracleDriver");
			
		    logconn = DriverManager.getConnection(DesDBURL,DesDBUsername,DesDBPassword);
		    System.out.println("连接日志数据库成功 连接字符串 "+DesDBURL);
		    log.warn("连接日志数据库成功 连接字符串 "+DesDBURL);
		  
		  //  srcconn.setTransactionIsolation(1);
		    
		    logconn.setAutoCommit(false); 
		    
		    logconn.commit();
		    
		    
		    
		    LogDbOper objRunLDO=new LogDbOper();
		    objRunLDO.DBRunOp(SYNCTASKRUNID, (new java.text.SimpleDateFormat( "yyyy-MM-dd HH:mm:ss")).format(new Date()), "0", Com_ID, User_ID, logconn, "", "");
		   
		    
		    
		    //////////////////////////////////////////////////////////////////////
		  
		    	
		    	for(int s=0;s<o_PLSQLS.size();s++)
		    		
		    	{
		    	
		    	//	System.out.println("PipeLineSQL 内循环"+ TaskID.get(w).substring(0, 3) + objCConfig.PipeLineSQL.get(s).TaskID );
		    		
		    	
		  //  System.out.println("找到了合适的TaskID");
		  //  tasknum++;
		    ResultSet srcrst=null;
		    PreparedStatement srcstmtQueryt=null;
		    
		    try
		    {
		  
		    
		    
		    
		    //ResultSet srcrs[]=new ResultSet[Integer.parseInt(objCConfig.PipeLineSQLCount)];
		    
		    srcstmt=srcconn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);//创建SQL命令对象
		    
		    
		    //////////////////////////////////
		   // desstmt=desconn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);//创建SQL命令对象
		    desstmt=desconn.createStatement();//创建SQL命令对象
		    
		    logstmt=logconn.createStatement();//创建SQL命令对象
		    ////////////////////////////////
		    Timestamp nowts=null;
		    //System.out.println("连接源数据库成功 连接字符串 "+);
		    
		    
		   
		    	
		    
		
		    
		    ResultSet rsNowTs=null;
		    ResultSet rsTsLastSync=null;
		   
		  
			   

			
			
			
			//////////////
			
			
				 
				
  
		  
		    	
					    	  if(srcconn!=null)
							    {
							    	
										try
										{
											log.warn("源数据库准备执行  "+o_PLSQLS.get(s).SelectSql.trim());
											srcstmtQueryt=srcconn.prepareStatement(o_PLSQLS.get(s).SelectSql.trim(),ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
										   
											srcstmtQueryt.setFetchSize(2000);
											
											//srcrs[i].
										} 
										catch (SQLException e)
										{
											// TODO Auto-generated catch block
										 e.printStackTrace();
										}//创建SQL命令对象
									
							    	
							    }
					    	  
					    	  //srcstmtQuery[k].
					    	 
					    	//srcstmtQuery[i]=srcconn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
					    	srcrst=srcstmtQueryt.executeQuery();
					    	
					    	
					    	log.warn("源数据库执行成功 "+o_PLSQLS.get(s).SelectSql.trim());
					    	
					    
			    	
			    	
			    	
			    	
			    	
			    	
			    	////////////////////////////////////////////////////////
			    	//srcrs[i]=srcstmt.executeQuery(objCConfig.PipeLineSQL.get(i).SelectSql);
			    	try
			    	{
			    		log.warn("目标数据库准备执行   "+o_PLSQLS.get(s).DropSql);
			    	desstmt.executeUpdate(o_PLSQLS.get(s).DropSql);
			    	
			    	log.warn("目标数据库执行成功   "+o_PLSQLS.get(s).DropSql);
			    	}
			    	catch(SQLException err)
			    	{
			    		
			    		
			    	}
			    	log.warn("目标数据库准备执行   "+o_PLSQLS.get(s).CreateSql);
			    	desstmt.executeUpdate(o_PLSQLS.get(s).CreateSql);
			    	log.warn("目标数据库执行成功   "+o_PLSQLS.get(s).CreateSql);
			    	
			    	 PreparedStatement prest = desconn.prepareStatement(o_PLSQLS.get(s).InsertSqlFSrc,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);  
			    	 
			    	 log.warn("目标数据库准备执行  "+o_PLSQLS.get(s).InsertSqlFSrc);
			    	 ResultSetMetaData metaData = srcrst.getMetaData();  
			    	 
			    	// log.warn("positiong 1");
			    	 
		    		 int colum = metaData.getColumnCount();     
		    	     
		    		// log.warn("colum "+colum);
		    		 /////////////////////////////
		    		 int R=0;
		    		
		    		 //System.out.println("position 2");
				    	
		    		 //log.warn("开始循环 ");
		    		  while( srcrst.next() )
		    		   { 
			    	     
			    		// srcrs[i].next();
		    			  /////////////////////
			    		  R++;
		    			  /////////////////////
		    			  String rescol="";
		    			  for(int j=1;j<=colum;j++)
		    			  {
		    				//  log.warn("获取类型 ");
		    				  String typeStr = ""; //类型     //获取列名     
		    	              String columName = metaData.getColumnName(j);     //获取每一列的数据类型     
		    	              int type = metaData.getColumnType(j);     //判断     
		    	          //    log.warn("开始判断类型");
		    	              if(Types.INTEGER == type)     
		    	                {     //typeStr = "Integer";     
		    	            	
		    	            	prest.setInt(j,srcrst.getInt(j));
		    		
		                      	}     
		    	                else if(Types.VARCHAR == type)     
		    	                {     
		    	             	  
		    	                prest.setString(j, srcrst.getString(j));
		    	                }     
		    	              
		    	                else if(Types.CHAR == type)     
		    	                {     
		    	             	  
		    	                prest.setString(j, srcrst.getString(j));
		    	                }  
		    	              
		    	                else if(Types.DATE == type)     
		    	                {     
		    	             	  
		    	                prest.setDate(j, srcrst.getDate(j));
		    	                }  
		    	              
		    	                else if(Types.DECIMAL == type)     
		    	                {     
		    	             	  
		    	                prest.setDouble(j, srcrst.getDouble(j));
		    	                }  
		    	              
		    	                else if(Types.DOUBLE == type)     
		    	                {     
		    	             	  
		    	                prest.setDouble(j, srcrst.getDouble(j));
		    	                }  
		    	              
		    	                else if(Types.FLOAT == type)     
		    	                {     
		    	             	  
		    	                prest.setFloat(j, srcrst.getFloat(j));
		    	                }  
		    	              
		    	                else if(Types.NUMERIC == type)     
		    	                {     
		    	             	  
		    	                prest.setDouble(j, srcrst.getDouble(j));
		    	                }  
		    	              
		    	                else if(Types.NULL == type)     
		    	                {     
		    	             	  
		    	                prest.setNull(j, Types.NULL );
		    	                }  
		    	              
		    	                else if(Types.REAL == type)     
		    	                {     
		    	             	  
		    	                prest.setDouble(j, srcrst.getDouble(j));
		    	                }  
		    	              
		    	                else if(Types.TIME == type)     
		    	                {     
		    	             	  
		    	                prest.setTime(j, srcrst.getTime(j));
		    	                }  
		    	              
		    	                else if(Types.TIMESTAMP == type)     
		    	                {     
		    	             	  
		    	                prest.setTimestamp(j, srcrst.getTimestamp(j));
		    	                }  
		    	              
		    	                else if(Types.BIGINT == type)     
		    	                {     
		    	             	  
		    	                prest.setLong(j, srcrst.getLong(j));
		    	                } 
		    	              
		    	                else if(Types.BLOB == type)     
		    	                {     
		    	             	  
		    	                prest.setBlob(j, srcrst.getBlob(j));
		    	                } 
		    	              
		    	               
		    	
		    			  }
		    			  
		    			//  log.warn("开始插入一条记录");
		    			  prest.addBatch();
		    			//  log.warn("结束插入一条记录");
		    			  ///////////////
		    		
		    			  if(R%2000==0)
		    			  {
		    			  prest.executeBatch(); 
		    			  prest.clearBatch();
		    			  
		    			  }
		    			
		    			  ////////////////////
		    			  
			    	  }  
			    	   
		    		  log.warn("目标数据库执行成功  "+o_PLSQLS.get(s).InsertSqlFSrc);
			    	   
			    		  prest.executeBatch(); 
			    		  
			    		  prest.close();
			    		  
			    		  srcrst.close();
			    		  
			    		  srcstmtQueryt.close();
			
	
			
			    		  //////////20140126	
						    //	desstmt.executeUpdate(o_PLSQLS.get(s).SyncSqlDelete);
						    //    desstmt.executeUpdate(o_PLSQLS.get(s).SyncSqlInsert);
		    
		   
		  
		    
		    
		    
		    log.warn("数据库准备提交   ");
		    
		    
		
		    
		    
		    desconn.commit();
		    srcconn.commit();
		    
		    log.warn("数据库提交成功   ");
		    System.out.println("DataSync Success");
		    
		    
		    
		    
		   
			    
			    
		    
		    
		    
		    LogDbOper ObjLDO=new LogDbOper();
		    ObjLDO.GeneralValue();
		    ObjLDO.DBOper(o_PLSQLS.get(s).TaskID,SYNCTASKRUNID, (new java.text.SimpleDateFormat( "yyyy-MM-dd HH:mm:ss")).format(new Date()), "0", "0", "", logconn, "", "", "", "");
		   
		    ////////////////////////////////////////////////////////////////////
		    CDataSyncSumResult o_CSSR=new CDataSyncSumResult();
		    o_CSSR.TaskID=o_PLSQLS.get(s).TaskID;
		    o_CSSR.ResultFlag=0;
		    
		    o_res_CSSR.add(o_CSSR);
		    /////////////////////////////////////////////////////////////
		    
		    		
		    
		    }
		  
		    catch(SQLException e)
		    {
		    	e.printStackTrace();
		    	
		    	resflag=2;
		    	System.out.println("SQLException"+e.getMessage());
		    	
		    	desconn.rollback();
		    	srcconn.rollback();
		    
		    	    LogDbOper ObjLDO=new LogDbOper();
		    	    ObjLDO.GeneralValue();
			  	    ObjLDO.DBOper(o_PLSQLS.get(s).TaskID,SYNCTASKRUNID, (new java.text.SimpleDateFormat( "yyyy-MM-dd HH:mm:ss")).format(new Date()), "1", "1", e.getMessage(), logconn, "", "", "", "");
			  	    
			  	    
			  	    
			  	  ////////////////////////////////////////////////////////////////////
				    CDataSyncSumResult o_CSSR=new CDataSyncSumResult();
				    o_CSSR.TaskID=o_PLSQLS.get(s).TaskID;
				    o_CSSR.ResultFlag=1;
				    o_CSSR.Desp="SQL语句执行错误 ："+e.getMessage();
				    
				    o_res_CSSR.add(o_CSSR);
				    /////////////////////////////////////////////////////////////
			
		    }
		    catch(Exception e)
		    {
		    	e.printStackTrace();
		    	
		    	resflag=3;
		    	desconn.rollback();
		    	srcconn.rollback();
		    
		    	    LogDbOper ObjLDO=new LogDbOper();
		    	    ObjLDO.GeneralValue();
			  	    ObjLDO.DBOper(o_PLSQLS.get(s).TaskID, SYNCTASKRUNID ,(new java.text.SimpleDateFormat( "yyyy-MM-dd HH:mm:ss")).format(new Date()), "1", "0", e.getMessage(), logconn, "", "", "", "");
			 
			    	  ////////////////////////////////////////////////////////////////////
				    CDataSyncSumResult o_CSSR=new CDataSyncSumResult();
				    o_CSSR.TaskID=o_PLSQLS.get(s).TaskID;
				    o_CSSR.ResultFlag=2;
				    o_CSSR.Desp="Java执行错误 ："+e.getMessage();
				    
				    o_res_CSSR.add(o_CSSR);
				    /////////////////////////////////////////////////////////////
			  	    
			  	    
			  	    
			  	    
			  	    
			  	    // srcconn.commit();
			  	    
			  	  
		    }
		    finally
		    {
		    	
		    	
		    	  if(srcrst!=null)
				  {
					  srcrst.close();
				  }
		    	  if(srcstmtQueryt!=null)
		    	  {
		    		  srcstmtQueryt.close();
		    	  }
		    }
		    
		    
		    
		    
			//原数据库使用自有连接
		
		    		}
			    
				///////////////////////////////////////////////////////////////////////////////////////
			
		    
		
		    ////////////////////////////////////////////////////////////
	
	
	
		
		
		if(1==1)
		{//清理没有释放的ResultSet
			
		}
		
		if(srcstmt!=null)
		{
			srcstmt.close();
		}
		if(desstmt!=null)
		{
			desstmt.close();
		}
		if(logstmt!=null)
		{
			logstmt.close();
		}
		if(srcconn!=null)
		{
			
			srcconn.close();
		}
		if(desconn!=null)
		{
			
			desconn.close();
		}
		
		
		
return o_res_CSSR;
		
	}
	catch(SQLException e)
	{
		e.printStackTrace();
		
		 log.warn(" SQLException   "+e.getMessage());
		try
		{
		if(srcstmt!=null)
		{
			srcstmt.close();
		}
		if(desstmt!=null)
		{
			 desstmt.close();
		}
		if(logstmt!=null)
		{
			logstmt.close();
		}
		
		if(srcconn!=null)
		{
			 log.warn("源数据库准备回滚!   ");
			srcconn.rollback();
			 System.out.println("DataSync Fail，src db rollback !");
			 log.warn("源数据库回滚完毕 !  ");
			srcconn.close();
		}
		if(desconn!=null)
		{
			 log.warn("目标数据库准备回滚   ");
			desconn.rollback();
			 System.out.println("DataSync Fail，des db rollback ! ");
			 log.warn("目标数据库回滚完毕   ");
			desconn.close();
		}
		
		if(logconn!=null)
		{
			 log.warn("日志数据库准备回滚   ");
			 logconn.rollback();
			 System.out.println("DataSync Fail，log db rollback ! ");
			 log.warn("日志数据库回滚完毕   ");
			 logconn.close();
		}
		
		
		}
		catch(SQLException ex)
		{
			
		}
	 System.out.println("Excepriont" + e.getMessage());
	 return null;
	 
	}
		
	 catch (ClassNotFoundException e) 
	 {  
		 // Logger.getLogger(MyLogger.class.getName()).log(Level.SEVERE, null, ex);  
		 return null;
	 }
	 catch(Exception e)
	 {
		 
		 System.out.println("["+e.getMessage()+"]");
		 return null;
	 }
		

	}













}