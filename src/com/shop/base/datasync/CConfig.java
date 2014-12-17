package com.shop.base.datasync;

import java.util.ArrayList;
import java.util.List;

public class CConfig {
	
	
	
	/**
	 * @param isSingleTool
	 * @param timeOut
	 * @param transTimeOut
	 * @param isSingleLog
	 * @param maxSelectItem
	 * @param taskID
	 * @param srcDSName
	 * @param srcType
	 * @param srcJdbcDriverClassName
	 * @param srcJdbcURL
	 * @param srcUserName
	 * @param srcPassWord
	 * @param desDSName
	 * @param desType
	 * @param desJdbcDriverClassName
	 * @param desJdbcURL
	 * @param desUserName
	 * @param desPassWord
	 * @param pipeLineSQLCount
	 * @param desDBSQLCount
	 * @param logDir
	 */
	public CConfig(String ConfigDir) {
		
		FileUtil ObjConfigFile=new FileUtil();
		try
		{
		
		
		//GetPrivateProfileString("d:\\1.txt", "sec1","key1", "NULL");
		IsSingleTool = ObjConfigFile.GetPrivateProfileString(ConfigDir, "OVERALL", "IsSingleTool", "1");
		TimeOut = ObjConfigFile.GetPrivateProfileString(ConfigDir, "OVERALL", "TimeOut", "0");
		TransTimeOut = ObjConfigFile.GetPrivateProfileString(ConfigDir, "OVERALL", "TransTimeOut", "0");
		IsSingleLog = ObjConfigFile.GetPrivateProfileString(ConfigDir, "OVERALL", "IsSingleLog", "1");
		MaxSelectItem = ObjConfigFile.GetPrivateProfileString(ConfigDir, "OVERALL", "MaxSelectItem", "0");
		TaskID = ObjConfigFile.GetPrivateProfileString(ConfigDir, "OVERALL", "TaskID", "00001");
		
		
		SrcIsUseV3 = ObjConfigFile.GetPrivateProfileString(ConfigDir, "SrcDBC", "SrcIsUseV3", "0");
		SrcDSName = ObjConfigFile.GetPrivateProfileString(ConfigDir, "SrcDBC", "SrcDSName", "v3src");
		SrcType = ObjConfigFile.GetPrivateProfileString(ConfigDir, "SrcDBC", "SrcType", "ORACLE");
		SrcJdbcDriverClassName = ObjConfigFile.GetPrivateProfileString(ConfigDir, "SrcDBC", "SrcJdbcDriverClassName", "jdbc.Driver");
		SrcJdbcURL = ObjConfigFile.GetPrivateProfileString(ConfigDir, "SrcDBC", "SrcJdbcURL", null);
		SrcUserName = ObjConfigFile.GetPrivateProfileString(ConfigDir, "SrcDBC", "SrcUserName", "v3");
		SrcPassWord = ObjConfigFile.GetPrivateProfileString(ConfigDir, "SrcDBC", "SrcPassWord", "v3");
		
		
		DesIsUseV3 = ObjConfigFile.GetPrivateProfileString(ConfigDir, "DesDBC", "DesIsUseV3", "0");
		DesDSName = ObjConfigFile.GetPrivateProfileString(ConfigDir, "DesDBC", "DesDSName", "v3des");
		DesType = ObjConfigFile.GetPrivateProfileString(ConfigDir, "DesDBC", "DesType", "DB2");
		DesJdbcDriverClassName = ObjConfigFile.GetPrivateProfileString(ConfigDir, "DesDBC", "DesJdbcDriverClassName", "jdbc.Driver");
		DesJdbcURL = ObjConfigFile.GetPrivateProfileString(ConfigDir, "DesDBC", "DesJdbcURL", null);
		DesUserName = ObjConfigFile.GetPrivateProfileString(ConfigDir, "DesDBC", "DesUserName", "v3");
		DesPassWord = ObjConfigFile.GetPrivateProfileString(ConfigDir, "DesDBC", "DesPassWord", "v3");
		
		GetTimeStampSQL=ObjConfigFile.GetPrivateProfileString(ConfigDir, "PipeLineSQL", "GetTimeStampSQL", null);
		
		TimeStampType = ObjConfigFile.GetPrivateProfileString(ConfigDir, "Log", "TimeStampType", "DATE");
		
		PipeLineSQLCount = ObjConfigFile.GetPrivateProfileString(ConfigDir, "Log", "PipeLineSQLCount", "1");
		DesDBSQLCount = ObjConfigFile.GetPrivateProfileString(ConfigDir, "Log", "DesDBSQLCount", "1");
		LogDir = ObjConfigFile.GetPrivateProfileString(ConfigDir, "Log", "LogDir", "/opt/log/log4j/");
		
		DesDBCCount=ObjConfigFile.GetPrivateProfileString(ConfigDir, "Log", "DesDBCCount", "0");
		
		
		
		
		DesDBCLOG.DesDSName=ObjConfigFile.GetPrivateProfileString(ConfigDir, "DesDBCLOG", "DesDSName", null);
		DesDBCLOG.DesIsUseV3=ObjConfigFile.GetPrivateProfileString(ConfigDir, "DesDBCLOG", "DesIsUseV3", null);
		DesDBCLOG.DesJdbcDriverClassName=ObjConfigFile.GetPrivateProfileString(ConfigDir, "DesDBCLOG", "DesJdbcDriverClassName", null);
		DesDBCLOG.DesType=ObjConfigFile.GetPrivateProfileString(ConfigDir, "DesDBCLOG", "DesType", null);
		DesDBCLOG.DesJdbcURL=ObjConfigFile.GetPrivateProfileString(ConfigDir, "DesDBCLOG", "DesJdbcURL", null);
		DesDBCLOG.DesUserName=ObjConfigFile.GetPrivateProfileString(ConfigDir, "DesDBCLOG", "DesUserName", null);
		DesDBCLOG.DesPassWord=ObjConfigFile.GetPrivateProfileString(ConfigDir, "DesDBCLOG", "DesPassWord", null);
		
		
		
		}
		
		catch(Exception e)
		{
			
		System.out.println("读取配置文件失败 ，"+e.getMessage());
		}
		
		try
		{
		for(int i=1 ;i<=Integer.parseInt(this.DesDBSQLCount);i++)
		{
			
			String strtemp = ObjConfigFile.GetPrivateProfileString(ConfigDir, "DesDBSQL", "SyncSql"+Integer.toString(i), null);
			this.DesDBSQL.add(strtemp);
			
			
		}
		
		for(int i=1 ;i<=Integer.parseInt(this.PipeLineSQLCount);i++)
		{
			
			PipeLineSQLStruct objtemp=new PipeLineSQLStruct();
			objtemp.SelectSql=ObjConfigFile.GetPrivateProfileString(ConfigDir, "PipeLineSQL", "SelectSql"+Integer.toString(i), null);
			objtemp.DropSql=ObjConfigFile.GetPrivateProfileString(ConfigDir, "PipeLineSQL", "DropSql"+Integer.toString(i), null);
			objtemp.CreateSql=ObjConfigFile.GetPrivateProfileString(ConfigDir, "PipeLineSQL", "CreateSql"+Integer.toString(i), null);
			objtemp.InsertSqlFSrc=ObjConfigFile.GetPrivateProfileString(ConfigDir, "PipeLineSQL", "InsertSqlFSrc"+Integer.toString(i), null);
			
			
			objtemp.SyncSqlDelete=ObjConfigFile.GetPrivateProfileString(ConfigDir, "PipeLineSQL", "SyncSqlDelete"+Integer.toString(i), null);
			objtemp.SyncSqlInsert=ObjConfigFile.GetPrivateProfileString(ConfigDir, "PipeLineSQL", "SyncSqlInsert"+Integer.toString(i), null);
			objtemp.TaskID=ObjConfigFile.GetPrivateProfileString(ConfigDir, "PipeLineSQL", "TaskID"+Integer.toString(i), null);
			//objtemp.InsertSqlFSrc=ObjConfigFile.GetPrivateProfileString(ConfigDir, "PipeLineSQL", "InsertSqlFSrc"+Integer.toString(i), null);
			System.out.println("PipeLineSQL 取得 TaskID "+objtemp.TaskID);
			
			
			//String strtemp = ObjConfigFile.GetPrivateProfileString(ConfigDir, "DesDBSQL", "SyncSql"+Integer.toString(i), null);
			this.PipeLineSQL.add(objtemp);
			
			
		}
		
		
		for(int i=1 ;i<=Integer.parseInt(this.DesDBCCount);i++)
		{
			
			DesDbInfoC objtemp=new DesDbInfoC();
			objtemp.DesDSName=ObjConfigFile.GetPrivateProfileString(ConfigDir, "DesDBC"+Integer.toString(i), "DesDSName", null);
			objtemp.DesIsUseV3=ObjConfigFile.GetPrivateProfileString(ConfigDir, "DesDBC"+Integer.toString(i), "DesIsUseV3", null);
			objtemp.DesJdbcDriverClassName=ObjConfigFile.GetPrivateProfileString(ConfigDir, "DesDBC"+Integer.toString(i), "DesJdbcDriverClassName", null);
			objtemp.DesType=ObjConfigFile.GetPrivateProfileString(ConfigDir, "DesDBC"+Integer.toString(i), "DesType", null);
			objtemp.DesJdbcURL=ObjConfigFile.GetPrivateProfileString(ConfigDir, "DesDBC"+Integer.toString(i), "DesJdbcURL", null);
			objtemp.DesUserName=ObjConfigFile.GetPrivateProfileString(ConfigDir, "DesDBC"+Integer.toString(i), "DesUserName", null);
			objtemp.DesPassWord=ObjConfigFile.GetPrivateProfileString(ConfigDir, "DesDBC"+Integer.toString(i), "DesPassWord", null);
			
			objtemp.TaskID=ObjConfigFile.GetPrivateProfileString(ConfigDir, "DesDBC"+Integer.toString(i), "TaskID", null);
			//String strtemp = ObjConfigFile.GetPrivateProfileString(ConfigDir, "DesDBSQL", "SyncSql"+Integer.toString(i), null);
			this.ObjDesDbInfo.add(objtemp);
			System.out.println("读取DesDBC"+Integer.toString(i)+" URL为  "+ObjDesDbInfo.get(i-1).DesJdbcURL +" TaskID :"+ ObjDesDbInfo.get(i-1).TaskID );
			
			
		}
		
		
		
		}
		catch(Exception e )
		{
			
			System.out.println("读取配置文件失败1，"+e.getMessage());
		}
	}


	public String  IsSingleTool="1";
	 //#是否是单独工具（1默认），还是V3中的包（0）

	 public String TimeOut="0";
	 
	// #工具运行超时时间 TimeOut，0为永不超时。
	 public String TransTimeOut="0";
	 

	 //#事务超时时间，0为永不超时。
	 
	 public String IsSingleLog="1";
	// #受否单独写日志。1 单独写日志 0不单独写日志
	
	 public String MaxSelectItem="0";
	// #从源数据库读取的行数上限 0 无上限
	 public String TaskID="00001";
	 
	 
	 //[SrcDBC]
	 // #源数据库连接
	 
	 public String SrcIsUseV3 ="0";
	 
	 
	  public String SrcDSName ="v3src";
	 
	 //#名字
	 public String  SrcType="ORACLE";
	 // #数据库类型
	 public String  SrcJdbcDriverClassName="jdbc.Driver";
	  
	 // #驱动包名
	 public String  SrcJdbcURL=null;
	 
	//  #连接字符串
	 
	  public String SrcUserName="v3";
	  
	  //#用户名
	  public String SrcPassWord="v3";
	  
	 // #密码
	  
	  //[DesDBC]
	  
	  public String DesIsUseV3 ="0";
	  
	  //#目标数据库连接
	  public String DesDSName ="v3des";
	  //#名字
	  public String DesType="DB2";
	 // #数据库类型
	  public String DesJdbcDriverClassName="jdbc.Driver";
	  
	 // #驱动包名
	  public String DesJdbcURL=null;
	 // #连接字符串
	  public String DesUserName="v3";
	 // #用户名
	  public String DesPassWord="v3";
	 // #密码
	  public String TimeStampType="DATE";
	  
	  public String PipeLineSQLCount="0";
	
	 // public  Map <String,String> PipeLineSQL = new HashMap<String,String,String,String>();

	  public List<PipeLineSQLStruct> PipeLineSQL=new ArrayList<PipeLineSQLStruct>();
	  public List<DesDbInfoC>  ObjDesDbInfo=new ArrayList<DesDbInfoC>();
	  
	  
	  
	 public String  DesDBSQLCount="0";
	 
	 public  List <String> DesDBSQL = new ArrayList<String>();
	 
	 
	// [Log]
	 public String  LogDir="/opt/log/log4j/";
	 
	 public String GetTimeStampSQL=null;
	 
	 public String DesDBCCount="0";
	 
	 public DesDbInfoC DesDBCLOG=new DesDbInfoC();
	 
	 // #日志路径
	 
	//public FileUtil obj=new FileUtil();
	//String key= obj.GetPrivateProfileString("d:\\1.txt", "sec1","key1", "NULL");

}
