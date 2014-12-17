package com.shop.base.bwf.taskexecution.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.v6.base.bwf.constant.BWfConstants;

import com.shop.base.bwf.api.BwfUtil;
import com.shop.base.bwf.taskexecution.domain.IBwfTaskExecution;
import com.shop.base.bwf.tasklist.taskinfo.data.BwfDaiBanTask;
import com.shop.base.bwf.tasklist.taskinfo.data.BwfYiBanTask;
import com.shop.base.bwf.tasklist.taskinfo.domain.IBwfDaiBanTaskDomain;
import com.shop.base.bwf.tasklist.taskinfo.domain.IBwfYiBanTaskDomain;
import com.shop.base.bwf.util.TimeUtils;
import com.shop.base.bwf.util.UuidUtil;
import com.shop.base.bwf.util.bsp.BspUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.loushang.next.core.BusinessException;

public class BwfTaskExecution implements IBwfTaskExecution{

	// 待办实例Domain
	private  IBwfDaiBanTaskDomain bwfDaiBanTaskDomain;
	// 已办实例Domain
	private  IBwfYiBanTaskDomain bwfYiBanTaskDomain;
	private static Log log = LogFactory.getLog(BwfTaskExecution.class);
	/*
	public Map submit(String url, 
			String actualUserId, String actualOrganId, String actualOrganName,
			String nextUrl, String nextOrganId, String nextOrganName, String nextOrganType,
			String subject, int taskCount,
			int limitTime,String limitUnit) {
		Map resultInfo = new HashMap();
		BwfYiBanTask yiBanTask = null;
		if(url != null && !"".equals(url)){
			
			 // 1.完成登录用户的功能对应的任务
			 
			resultInfo.put("yiBanTaskId", completeCurrentTask(actualUserId, actualOrganId,
					actualOrganName, url, taskCount, yiBanTask, null));
		}
		if(nextUrl != null && !"".equals(nextUrl)){

			 // 2.处理下一功能的任务
	
			
			resultInfo.put("daiBanTaskId", forwardNextTask(nextUrl, nextOrganId, 
					nextOrganName, nextOrganType, limitTime, limitUnit, 
					taskCount, yiBanTask, null, subject));
		}
		return resultInfo;
	}
	*/
	/*
	public Map submitWithTaskList(String url, String actualUserId, 
			String actualOrganId, String actualOrganName, int taskCount,
			List nextTaskList) {
		Map resultInfo = new HashMap();
		BwfYiBanTask yiBanTask = null;
		if(url != null && !"".equals(url)){

			 //1.完成登录用户的功能对应的任务

			resultInfo.put("yiBanTaskId", completeCurrentTask(actualUserId, actualOrganId,
					actualOrganName, url, taskCount, yiBanTask, null));
		}
		if(nextTaskList != null && nextTaskList.size()>0){
	
			 // 2.处理下一功能的任务

			resultInfo.put("daiBanTaskIdList", forwardNextWithBatchTask(nextTaskList, 
					yiBanTask, false));
		}
		
		return resultInfo;
	}
	*/
	
	public Map submitWithPrimaryKey(String url, String primaryKey,
			String actualUserId, String actualOrganId, String actualOrganName, 
			String nextUrl, String nextOrganId, String nextOrganName, String nextOrganType,
			String subject, String nextPrimaryKey, 
			int limitTime,String limitUnit) {
		Map resultInfo = new HashMap();
		BwfYiBanTask yiBanTask = null;
		if(url != null && !"".equals(url)){
			
			 // 1.完成登录用户的功能对应的任务
			resultInfo.put("yiBanTaskId", 
					completeCurrentTask(actualUserId, actualOrganId,actualOrganName, url, 0, yiBanTask, primaryKey)
			);
		}
		if(nextUrl != null && !"".equals(nextUrl)){

			 // 2.处理下一功能的任务
			resultInfo.put("daiBanTaskId", 
				forwardNextTask(nextUrl, nextOrganId, nextOrganName, nextOrganType, limitTime, limitUnit, 0, yiBanTask, nextPrimaryKey, subject)
			);
		}
		return resultInfo;
	}
	
	public Map submitWithPrimaryKeyAndTaskList(String url, String primaryKey,
			String actualUserId, String actualOrganId, String actualOrganName,
			List nextTaskList) {
		Map resultInfo = new HashMap();
		BwfYiBanTask yiBanTask = null;
		if(url != null && !"".equals(url)){

			// 1.完成登录用户的功能对应的任务
			resultInfo.put("yiBanTaskId", 
					completeCurrentTask(actualUserId, actualOrganId,actualOrganName, url, 0, yiBanTask, primaryKey));
		}
		if(nextTaskList != null && nextTaskList.size()>0){

			//2.处理下一功能的任务
			resultInfo.put("daiBanTaskIdList", 
					forwardNextWithBatchTask(nextTaskList, yiBanTask, true));
		}	
		return resultInfo;
	}
	
	
	/**
	 * 完成当前functionCode对应的任务
	 * @param actualUserId
	 * @param actualOrganId
	 * @param actualOrganName
	 * @param functionCode
	 * @param taskCount
	 * @param yiBanTask
	 * @return
	 */
	private String  completeCurrentTask(String actualUserId, String actualOrganId,
			String actualOrganName, String url, 
			int taskCount, BwfYiBanTask yiBanTask, String primaryKey){
		//1.完成登录用户的功能对应的任务

		// 登录用户userId不能为空
		if (actualUserId == null || "".equals(actualUserId)) {
			throw new BusinessException("登录用户userId不能为空！");
		}
		if (actualOrganId == null || "".equals(actualOrganId)) {
			throw new BusinessException( "实际处理人组织ID不能为空！");
		}
		if (actualOrganName == null || "".equals(actualOrganName)) {
			throw new BusinessException( "实际处理人组织名称不能为空！");
		}
		//调用BSP接口获取对应的url对应的functionCode
		String functionCode = BspUtil.getInstance().getFunctionCodeByUrl(url);
		if(functionCode==null || "".equals(functionCode)){
			throw new BusinessException( "未找到URL所对应的功能！");
		}
		//1.1获取当前用户有权限处理的指定功能的待办任务
		BwfDaiBanTask daiBanTask = getBwfDaiBanTaskDomain()
									.getDaiBanTask(actualUserId, functionCode, primaryKey);		
		if(daiBanTask == null){
			//针对刚工作流程序刚上线和上线后流程调整的情况，会出现daiban不存在的，这时流程正常走，但不创建新的yiban
			//String[] args = new String[]{actualUserId,functionCode};
			//throw new BusinessException("未找到用户[{0}]对应的功能编码为[{1}]的待办任务，若存在，请确定该用户的数据权限和功能权限是否设置正确！");
			return null;
		}
		/**
		 * 构建已办任务
		 */
		yiBanTask = constructYiBanTaskByDaiBanTask(actualUserId,actualOrganId, actualOrganName, daiBanTask, taskCount);
		//持久化插入已办任务
		getBwfYiBanTaskDomain().insertYiBanTask(yiBanTask);
		//当taskCount==0时为1条数据1条待办任务
		if(taskCount!=0 && daiBanTask.getTaskCount()>taskCount){
			//该待办任务中的任务没有全部办完，更新待办任务数
			getBwfDaiBanTaskDomain().updateTaskCount(daiBanTask.getId(),
					daiBanTask.getTaskCount()-taskCount);
		}else{
			//持久化删除代办任务
			getBwfDaiBanTaskDomain().deleteDaiBanTask(daiBanTask.getId());
		}
		return yiBanTask.getId();
		
	}
	
	/**
	 * 构建nextFunctionCode对应的待办任务
	 * @param nextFunctionCode
	 * @param nextOrganId
	 * @param nextOrganName
	 * @param nextOrganType
	 * @param limitTime
	 * @param limitUnit
	 * @param taskCount
	 * @param yiBanTask
	 * @param subject
	 * @return
	 */
	private String  forwardNextTask(String nextUrl, String nextOrganId, 
			String nextOrganName, String nextOrganType, int limitTime, String limitUnit,
			int taskCount, BwfYiBanTask yiBanTask, String primaryKey, String subject){
		if(nextOrganId==null || "".equals(nextOrganId)){
			throw new BusinessException("下一待办任务的办理组织ID不能为空！");
		}
		if(nextOrganName==null || "".equals(nextOrganName)){
			throw new BusinessException("下一待办任务的办理组织名称不能为空！");
		}
		if(nextOrganType==null || "".equals(nextOrganType)){
			throw new BusinessException("下一待办任务办理组织类型不能为空！");
		}
		//调用BSP接口获取对应的url对应的nextFunctionCode
		String nextFunctionCode = BspUtil.getInstance().getFunctionCodeByUrl(nextUrl);
		if(nextFunctionCode==null || "".equals(nextFunctionCode)){
			throw new BusinessException("未找到URL所对应的功能！");
		}
		BwfDaiBanTask nextDaiBanTask = null;
		if(taskCount!=0){
			//判断nextOrganId和nextFunctionCode对应的待办任务是否存在
			nextDaiBanTask = getBwfDaiBanTaskDomain().
					getDaiBanTaskByActDefIdAndOrganId(nextFunctionCode, nextOrganId);
		}
		if(nextDaiBanTask != null){
			//nextOrganId和nextFunctionCode对应的待办任务存在，更新待办任务数
			getBwfDaiBanTaskDomain().updateTaskCount(nextDaiBanTask.getId(), 
					nextDaiBanTask.getTaskCount()+taskCount);
		}else{
			//nextOrganId和nextFunctionCode对应的待办任务不存在时，生成待办任务。
			nextDaiBanTask = constructDaiBanTask(nextUrl, nextFunctionCode, nextOrganId, nextOrganName, nextOrganType,
					yiBanTask, taskCount, primaryKey, subject, limitTime, limitUnit, 0);
			//持久化插入待办任务
			getBwfDaiBanTaskDomain().insertDaiBanTask(nextDaiBanTask);
		}
		return nextDaiBanTask.getId();
	}
	private List forwardNextWithBatchTask(List nextTaskList, BwfYiBanTask yiBanTask,
			boolean isSingleTask){
		for(int i=0; i<nextTaskList.size(); i++){
			Map nextTaskInfo = (Map)nextTaskList.get(i);
			if(nextTaskInfo.get("url")==null || "".equals(nextTaskInfo.get("url").toString())){
				throw new BusinessException("下一待办任务对应的URL不能为空!");
			}
			if(nextTaskInfo.get("organId")==null || "".equals(nextTaskInfo.get("organId").toString())){
				throw new BusinessException("下一待办任务的办理组织ID不能为空！");
			}
			if(nextTaskInfo.get("organName")==null || "".equals(nextTaskInfo.get("organName").toString())){
				throw new BusinessException("下一待办任务的办理组织名称不能为空！");
			}
			if(nextTaskInfo.get("organType")==null || "".equals(nextTaskInfo.get("organType").toString())){
				throw new BusinessException("下一待办任务办理组织类型不能为空！");
			}
		}
		//返回待办任务的id信息
		List daiBanTaskIdList = new ArrayList();
		//需要插入的待办任务列表
		List<BwfDaiBanTask> insertDaiBanTaskList = new ArrayList<BwfDaiBanTask>();
		//需要更新的待办任务列表
		List<BwfDaiBanTask> updateDaiBanTaskList = new ArrayList<BwfDaiBanTask>();
		if(isSingleTask){
			for(int i=0; i<nextTaskList.size();i++){
				Map nextTaskMap = (Map)nextTaskList.get(i);
				String url = (String)nextTaskMap.get("url");
				//调用BSP接口获取对应的url对应的nextFunctionCode
				String functionCode = BspUtil.getInstance().getFunctionCodeByUrl(url);
				if(functionCode==null || "".equals(functionCode)){
					throw new BusinessException("未找到URL所对应的功能！");
				}
				BwfDaiBanTask nextDaiBanTask = constructDaiBanTask(url, functionCode, 
						(String)nextTaskMap.get("organId"), (String)nextTaskMap.get("organName"), 
						(String)nextTaskMap.get("organType"),yiBanTask, 0, (String)nextTaskMap.get("primaryKey"), 
						(String)nextTaskMap.get("subject"),(Integer)nextTaskMap.get("limitTime"),
						(String)nextTaskMap.get("limitUnit"),(Integer)nextTaskMap.get("warnTime"));
				insertDaiBanTaskList.add(nextDaiBanTask);
				daiBanTaskIdList.add(nextDaiBanTask.getId());
			}
		}else{
			for(int i=0; i<nextTaskList.size();i++){
				Map nextTaskMap = (Map)nextTaskList.get(i);
				String url = (String)nextTaskMap.get("url");
				//调用BSP接口获取对应的url对应的nextFunctionCode
				String functionCode = BspUtil.getInstance().getFunctionCodeByUrl(url);
				if(functionCode==null || "".equals(functionCode)){
					throw new BusinessException("未找到URL所对应的功能！");
				}
				//2.1判断nextOrganId和nextFunctionCode对应的待办任务是否存在
				BwfDaiBanTask nextDaiBanTask = getBwfDaiBanTaskDomain().
						getDaiBanTaskByActDefIdAndOrganId(functionCode, 
								(String)nextTaskMap.get("organId"));
				if(nextDaiBanTask != null){
					//nextOrganId和nextFunctionCode对应的待办任务存在，更新待办任务数
					nextDaiBanTask.setTaskCount(nextDaiBanTask.getTaskCount()+
							(Integer)nextTaskMap.get("taskCount"));
					updateDaiBanTaskList.add(nextDaiBanTask);
				}else{
					//nextOrganId和nextFunctionCode对应的待办任务不存在时，生成待办任务。
					nextDaiBanTask = constructDaiBanTask(url, functionCode, 
							(String)nextTaskMap.get("organId"), (String)nextTaskMap.get("organName"), 
							(String)nextTaskMap.get("organType"), yiBanTask, 
							(Integer)nextTaskMap.get("taskCount"), (String)nextTaskMap.get("primaryKey"), 
							(String)nextTaskMap.get("subject"),(Integer)nextTaskMap.get("limitTime"),
							(String)nextTaskMap.get("limitUnit"),(Integer)nextTaskMap.get("warnTime"));
					insertDaiBanTaskList.add(nextDaiBanTask);
				}
				daiBanTaskIdList.add(nextDaiBanTask.getId());
			}
		}
		
		if(insertDaiBanTaskList.size()>0){
			//持久化插入待办任务
			getBwfDaiBanTaskDomain().insert(insertDaiBanTaskList);
		}
		if(updateDaiBanTaskList.size()>0){
			//持久化更新待办任务
			getBwfDaiBanTaskDomain().updateTaskCount(updateDaiBanTaskList);
		}
		return daiBanTaskIdList;
	}
	
	private BwfYiBanTask constructYiBanTaskByDaiBanTask(String actualUserId,String organId, 
			String organName, BwfDaiBanTask daiBanTask, int taskCount){
		BwfYiBanTask yiBanTask = new BwfYiBanTask();
		yiBanTask.setId(UuidUtil.generate());
		yiBanTask.setOrganId(organId);
		yiBanTask.setOrganName(organName);
		yiBanTask.setCreateTime(daiBanTask.getCreateTime());
		yiBanTask.setEndTime(TimeUtils.getCurrentDateTime());
		yiBanTask.setActivityId(daiBanTask.getActivityId());
		yiBanTask.setActDefUniqueId(daiBanTask.getActDefUniqueId());
		yiBanTask.setActDefId(daiBanTask.getActDefId());
		yiBanTask.setActDefName(daiBanTask.getActDefName());
		yiBanTask.setActLimitTime(daiBanTask.getActLimitTime());
		yiBanTask.setActWarnTime(daiBanTask.getActWarnTime());
		yiBanTask.setProcessId(daiBanTask.getProcessId());
		yiBanTask.setProcDefUniqueId(daiBanTask.getProcDefUniqueId());			
		yiBanTask.setProcDefId(daiBanTask.getProcDefId());
		yiBanTask.setProcDefName(daiBanTask.getProcDefName());
		yiBanTask.setProcCreateTime(daiBanTask.getProcCreateTime());
		yiBanTask.setProcCreatorId(daiBanTask.getProcCreatorId());
		yiBanTask.setProcCreatorName(daiBanTask.getProcCreatorName());
		yiBanTask.setIsProcEnd(" ");//''（空字符串代表不区分流程是否办结）
		yiBanTask.setPrimaryKey(daiBanTask.getPrimaryKey());
		yiBanTask.setProcessType(daiBanTask.getProcessType());
		yiBanTask.setSysSubject(daiBanTask.getSysSubject());
		yiBanTask.setProcSubject(daiBanTask.getProcSubject());
		yiBanTask.setTypeSubject(daiBanTask.getTypeSubject());
		yiBanTask.setSubject(daiBanTask.getSubject());
		yiBanTask.setTaskSource(daiBanTask.getTaskSource());
		yiBanTask.setTaskCount(taskCount);
		yiBanTask.setAppCode(daiBanTask.getAppCode());
		yiBanTask.setRelativePath(daiBanTask.getRelativePath());
		yiBanTask.setInternalId(daiBanTask.getInternalId());
		yiBanTask.setActualUserId(actualUserId);
		
		if(log.isDebugEnabled()){
			log.debug("yiBanTask bean = " +yiBanTask.toString());
		}
		return yiBanTask;
	}
	
	private BwfDaiBanTask constructDaiBanTask(String url, String functionCode, String organId,
			String organName, String organType, BwfYiBanTask yiBanTask,int taskCount,
			String primaryKey, String subject, int limitTime,
			String limitUnit, int warnTime){
		//获取功能信息
		Map functionInfo = BspUtil.getInstance().getFunctionInfo(functionCode);
		if (functionInfo == null) {
			throw new BusinessException("未获取到下一功能ID对应的信息！");
		}
		BwfDaiBanTask daiBanTask = new BwfDaiBanTask();
		daiBanTask.setId(UuidUtil.generate());
		daiBanTask.setOrganId(organId);
		daiBanTask.setOrganName(organName);
		//任务创建时间
		String createTime = TimeUtils.getCurrentDateTime();
		daiBanTask.setCreateTime(createTime);
		String corpOrganId = BspUtil.getInstance()
				.getCorporationOrganId(organId, organType);
		//构建限时时间
		if(limitUnit!=null && !"".equals(limitUnit) &&limitTime!=0){
			long millisLimitTime = TimeUtils.getLimitTime(corpOrganId, createTime, limitUnit, limitTime);
			daiBanTask.setActLimitTime(TimeUtils.formatTime(millisLimitTime));
			//构建预警时间
			if(warnTime!=0){
				long millisWarnTime = TimeUtils.getWarnTime(corpOrganId, createTime,
						limitUnit, limitTime, warnTime);
				daiBanTask.setActWarnTime(TimeUtils.formatTime(millisWarnTime));			
			}
		}
		
		daiBanTask.setActDefId(functionCode);
		daiBanTask.setActDefName((String)functionInfo.get("name"));//输入参数functionCode对应的功能名称
		if(yiBanTask != null){
			daiBanTask.setPreActDefNames(yiBanTask.getActDefName());//前一functionCode对应的功能名称			
		}
		daiBanTask.setIsVisible("1");
		daiBanTask.setSubject(subject);
		daiBanTask.setTaskSource("1");//BPM轻量级工作流生成的任务
		daiBanTask.setTaskCount(taskCount);
		daiBanTask.setAppCode((String)functionInfo.get("appCode"));//输入参数functionCode对应功能所在应用编码
		//构建url
		if (url.indexOf("?") > -1) {
			url += "&assignmentId="+daiBanTask.getId()+"&functionCode="+functionCode
					+"&organId="+daiBanTask.getOrganId();
		}else{
			url += "?assignmentId="+daiBanTask.getId()+"&functionCode="+functionCode
					+"&organId="+daiBanTask.getOrganId();
		}
		if(primaryKey!=null && !"".equals(primaryKey)){
			daiBanTask.setPrimaryKey(primaryKey);
			url += "&primaryKey="+primaryKey;
		}
		daiBanTask.setRelativePath(url);//输入参数functionCode对应功能的URL
		daiBanTask.setInternalId(daiBanTask.getId());	
		
		if(log.isDebugEnabled()){
			log.debug("daiBanTask bean = " +daiBanTask.toString());
		}
		return daiBanTask;
	}

	public IBwfDaiBanTaskDomain getBwfDaiBanTaskDomain() {
		return bwfDaiBanTaskDomain;
	}

	public void setBwfDaiBanTaskDomain(IBwfDaiBanTaskDomain bwfDaiBanTaskDomain) {
		this.bwfDaiBanTaskDomain = bwfDaiBanTaskDomain;
	}

	public IBwfYiBanTaskDomain getBwfYiBanTaskDomain() {
		return bwfYiBanTaskDomain;
	}

	public void setBwfYiBanTaskDomain(IBwfYiBanTaskDomain bwfYiBanTaskDomain) {
		this.bwfYiBanTaskDomain = bwfYiBanTaskDomain;
	}

	

}
