package com.shop.base.datasync;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DataSyncAdapter extends DataSync{
	private static Log log=LogFactory.getLog(DataSyncAdapter.class);
	private static String log4jPath=DataSyncAdapter.class.getResource("/log4j.properties").getFile();
	private static String dataSyncConfigPath=DataSyncAdapter.class.getResource("/DataSync.conf").getFile();
	/**
	 * 原始方法
	 * @param taskIdList
	 */
	public static void  dataSync(List<String> taskIdList){
		if(log.isInfoEnabled()){
			log.info("DataSyncAdapter sync task "+taskIdList );
		}
		DataSync(dataSyncConfigPath, log4jPath, "", "","","", taskIdList, null, null);
	}
	
	/**
	 * 添加记录 comId与userId,另一个重载方法为原始方法保留的
	 * @param taskIdList
	 * @param userId
	 * @param comId
	 */
	public static void  dataSync(List<String> taskIdList,String comId,String userId){
		if(log.isInfoEnabled()){
			log.info("DataSyncAdapter sync task "+taskIdList );
		}
		DataSync(dataSyncConfigPath, log4jPath, "", "",comId, userId,taskIdList, null, null);
	}
	

	/**
	 * 汇总数据同步的方法
	 * @param srcDBURL  源数据库URL
	 * @param srcDBUsername  源数据库账号
	 * @param srcDBPassword  源数据库密码
	 * @param desDBURL  目标数据库URL
	 * @param desDBUsername  目标数据库账号
	 * @param desDBPassword  目标数据库密码
	 * @param comId  公司
	 * @param userId  用户
	 * @param plSqlStructList 
	 * 
	 * 		public class PipeLineSQLStruct{
	 *		    public String SelectSql; //在源数据库执行的查询语句
	 *		    public String DropSql;//在目标库执行的drop临时表语句
	 *		    public String CreateSql;//在目标库执行的创建临时表语句
	 *		    public String InsertSqlFSrc; ;//在目标库执行的插入临时表数据语句
	 *		    public String SyncSqlDelete;//在目标库上执行的Delete语句
	 *		    public String SyncSqlInsert;//在目标库上执行的Insert语句
	 *		    public String TaskID;//任务编号
	 *		}
	 *
	 * @return
     *   
     *   
     *   public class CDataSyncSumResult {
     *       public String TaskID;//任务编号
     *       public int ResultFlag;//结果标志
     *       public String Desp;//描述
     *   }
     *   ResultFlag=0 此TaskID的表同步成功。
     *   ResultFlag=1 此TaskID的表同步失败，SQL执行错误，Desp为错误描述。
     *   ResultFlag=2 此TaskID的表同步失败，java执行错误，Desp为错误描述。
     *   如果DataSyncSum返回 null，那么执行出错。
     *   如果DataSyncSum返回 非null，则为 List<CDataSyncSumResult>  对象， 执行成功，
	 */
	public static List<CDataSyncSumResult> dataSyncSum(
			String srcDBURL,String srcDBUsername,String srcDBPassword,
			String desDBURL,String desDBUsername,String desDBPassword,
			String comId,String userId,List<PipeLineSQLStruct> plSqlStructList
	){

		if(log.isInfoEnabled()){
			StringBuilder sb=new StringBuilder();
			try{
				for(PipeLineSQLStruct plsqls:plSqlStructList){
					sb.append(plsqls.TaskID).append(",");
				}
			}catch(Exception e){
				log.error("plSqlStructList 结构错误,无法解析");
			}
			log.info("DataSyncAdapter dataSyncSum task "+sb );
		}
		return DataSyncSum(log4jPath, 
					srcDBURL, srcDBUsername, srcDBPassword, 
					desDBURL, desDBUsername, desDBPassword, 
					comId, userId, plSqlStructList);
	}
	
	public static void  dataSync(List<String> taskIdList,PipeLineSQLStruct o_PLSQLS, List<String> l_DesSQLS){
		if(log.isInfoEnabled()){
			log.info("DataSyncAdapter sync task "+taskIdList );
		}
		DataSync(dataSyncConfigPath, log4jPath, "", "","","", taskIdList, o_PLSQLS, l_DesSQLS);
	}
}
