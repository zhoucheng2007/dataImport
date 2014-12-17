package com.shop.base.bwf.tasklist.taskinfo.data;

import org.apache.commons.beanutils.BeanUtils;
import org.loushang.next.data.StatefulDatabean;

/**
 * 物理表已办任务
 * 
 * @author 浪潮软件 BPM产品组
 */
public class BwfYiBanTask extends StatefulDatabean {

	/**
	 * 域 用户任务内码
	 */
	private String id;

	/**
	 * 域 参与者组织内码
	 */
	private String organId;

	/**
	 * 域 参与者组织内码
	 */
	private String organName;

	/**
	 * 域 用户任务创建时间
	 */
	private String createTime;

	/**
	 * 域 用户任务完成时间
	 */
	private String endTime;

	/**
	 * 域 环节实例Id
	 */
	private String activityId;

	/**
	 * 域 环节定义唯一Id
	 */
	private String actDefUniqueId;

	/**
	 * 域 环节定义Id
	 */
	private String actDefId;

	/**
	 * 域 环节名称
	 */
	private String actDefName;

	/**
	 * 域 限时时间
	 */
	private String actLimitTime;
	
	/**
	 * 域 预警时间
	 */
	private String actWarnTime;

	/**
	 * 域 流程实例Id
	 */
	private String processId;

	/**
	 * 域 流程定义唯一Id
	 */
	private String procDefUniqueId;

	/**
	 * 域 流程定义Id
	 */
	private String procDefId;

	/**
	 * 域 流程定义名称
	 */
	private String procDefName;

	/**
	 * 域 流程创建时间
	 */
	private String procCreateTime;

	/**
	 * 域 流程办结时间
	 */
	private String procEndTime;
	
	/**
	 * 域 流程发起人ID
	 */
	private String procCreatorId;
	
	/**
	 * 域 流程发起人名称
	 */
	private String procCreatorName;

	/**
	 * 域 流程是否办结（0：流程正在办理；1：流程已经办结）
	 */
	private String isProcEnd;
	
	/**
	 * 域 当前环节名称，不同环节间用“，”隔开
	 */
	private String activeActDefNames;

	/**
	 * 域 当前环节处理人组织名称(同一环节间用“，”隔开，不同环节间用“；”隔开)
	 */
	private String activeOrganNames;

	/**
	 * 域 业务主键
	 */
	private String primaryKey;

	/**
	 * 域 流程类型定义Id
	 */
	private String processType;
	/**
	 * 域 系统全局层次标题
	 */
	private String sysSubject;
	/**
	 * 域 流程定义层次标题
	 */
	private String procSubject;
	/**
	 * 域 业务类型层次标题
	 */
	private String typeSubject;

	/**
	 * 域  任务标题
	 */
	private String subject;
	
	/**
	 * 域 任务来源
	 */
	private String taskSource;
	/**
	 * 域 任务数
	 */
	private int taskCount;

	/**
	 * 域  业务应用编码
	 */
	private String appCode;
	/**
	 * 域  业务处理页面相对路径
	 */
	private String relativePath;
	/**
	 * 域  各工作流内部使用的用户任务ID
	 */
	private String internalId;
	/**
	 * 当前已办的用户ID
	 */
	private String actualUserId;
	
	/**
	 * YiBanTask构造函数
	 */
	public BwfYiBanTask() {
		super();
	}

	/**
	 * 属性 Id 的get方法
	 * 
	 * @return String
	 */
	public String getId() {
		return id;
	}

	/**
	 * 属性 Id 的set方法
	 * 
	 * @return void
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 属性 OrganId 的get方法
	 * 
	 * @return String
	 */
	public String getOrganId() {
		return organId;
	}

	/**
	 * 属性 OrganId 的set方法
	 * 
	 * @return void
	 */
	public void setOrganId(String organId) {
		this.organId = organId;
	}

	/**
	 * 属性 OrganName 的get方法
	 * 
	 * @return String
	 */
	public String getOrganName() {
		return organName;
	}

	/**
	 * 属性 OrganName 的set方法
	 * 
	 * @return void
	 */
	public void setOrganName(String organName) {
		this.organName = organName;
	}

	/**
	 * 属性 CreateTime 的get方法
	 * 
	 * @return String
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * 属性 CreateTime 的set方法
	 * 
	 * @return void
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * 属性 EndTime 的get方法
	 * 
	 * @return String
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * 属性 EndTime 的set方法
	 * 
	 * @return void
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * 属性 ActivityId 的get方法
	 * 
	 * @return String
	 */
	public String getActivityId() {
		return activityId;
	}

	/**
	 * 属性 ActivityId 的set方法
	 * 
	 * @return void
	 */
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	/**
	 * 属性 ActDefUniqueId 的get方法
	 * 
	 * @return String
	 */
	public String getActDefUniqueId() {
		return actDefUniqueId;
	}

	/**
	 * 属性 ActDefUniqueId 的set方法
	 * 
	 * @return void
	 */
	public void setActDefUniqueId(String actDefUniqueId) {
		this.actDefUniqueId = actDefUniqueId;
	}

	/**
	 * 属性 ActDefId 的get方法
	 * 
	 * @return String
	 */
	public String getActDefId() {
		return actDefId;
	}

	/**
	 * 属性 ActDefId 的set方法
	 * 
	 * @return void
	 */
	public void setActDefId(String actDefId) {
		this.actDefId = actDefId;
	}

	/**
	 * 属性 ActDefName 的get方法
	 * 
	 * @return String
	 */
	public String getActDefName() {
		return actDefName;
	}

	/**
	 * 属性 ActDefName 的set方法
	 * 
	 * @return void
	 */
	public void setActDefName(String actDefName) {
		this.actDefName = actDefName;
	}

	public String getActLimitTime() {
		return actLimitTime;
	}

	public void setActLimitTime(String actLimitTime) {
		this.actLimitTime = actLimitTime;
	}

	public String getActWarnTime() {
		return actWarnTime;
	}

	public void setActWarnTime(String actWarnTime) {
		this.actWarnTime = actWarnTime;
	}
	
	/**
	 * 属性 ProcessId 的get方法
	 * 
	 * @return String
	 */
	public String getProcessId() {
		return processId;
	}

	/**
	 * 属性 ProcessId 的set方法
	 * 
	 * @return void
	 */
	public void setProcessId(String processId) {
		this.processId = processId;
	}

	/**
	 * 属性 ProcDefUniqueId 的get方法
	 * 
	 * @return String
	 */
	public String getProcDefUniqueId() {
		return procDefUniqueId;
	}

	/**
	 * 属性 ProcDefUniqueId 的set方法
	 * 
	 * @return void
	 */
	public void setProcDefUniqueId(String procDefUniqueId) {
		this.procDefUniqueId = procDefUniqueId;
	}

	/**
	 * 属性 ProcDefId 的get方法
	 * 
	 * @return String
	 */
	public String getProcDefId() {
		return procDefId;
	}

	/**
	 * 属性 ProcDefId 的set方法
	 * 
	 * @return void
	 */
	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	/**
	 * 属性 ProcDefName 的get方法
	 * 
	 * @return String
	 */
	public String getProcDefName() {
		return procDefName;
	}

	/**
	 * 属性 ProcDefName 的set方法
	 * 
	 * @return void
	 */
	public void setProcDefName(String procDefName) {
		this.procDefName = procDefName;
	}

	/**
	 * 属性 ProcCreateTime 的get方法
	 * 
	 * @return String
	 */
	public String getProcCreateTime() {
		return procCreateTime;
	}

	/**
	 * 属性 ProcCreateTime 的set方法
	 * 
	 * @return void
	 */
	public void setProcCreateTime(String procCreateTime) {
		this.procCreateTime = procCreateTime;
	}

	/**
	 * @return 流程办结时间
	 */
	public String getProcEndTime() {
		return procEndTime;
	}

	/**
	 * @param procEndTime 流程办结时间
	 */
	public void setProcEndTime(String procEndTime) {
		this.procEndTime = procEndTime;
	}
	
	/**
	 * @return procCreatorId 流程发起人ID
	 */
	public String getProcCreatorId() {
		return procCreatorId;
	}

	/**
	 * @param procCreatorId 流程发起人ID
	 */
	public void setProcCreatorId(String procCreatorId) {
		this.procCreatorId = procCreatorId;
	}

	/**
	 * @return procCreatorName 流程发起人名称
	 */
	public String getProcCreatorName() {
		return procCreatorName;
	}

	/**
	 * @param procCreatorName 流程发起人名称
	 */
	public void setProcCreatorName(String procCreatorName) {
		this.procCreatorName = procCreatorName;
	}

	/**
	 * @return 流程是否办结（0：流程正在办理；1：流程已经办结）
	 */
	public String getIsProcEnd() {
		return isProcEnd;
	}

	/**
	 * @param isProcEnd 流程是否办结（0：流程正在办理；1：流程已经办结）
	 */
	public void setIsProcEnd(String isProcEnd) {
		this.isProcEnd = isProcEnd;
	}

	public String getActiveActDefNames() {
		return activeActDefNames;
	}

	public void setActiveActDefNames(String activeActDefNames) {
		this.activeActDefNames = activeActDefNames;
	}

	public String getActiveOrganNames() {
		return activeOrganNames;
	}

	public void setActiveOrganNames(String activeOrganNames) {
		this.activeOrganNames = activeOrganNames;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	/**
	 * 属性 ProcessType 的get方法
	 * 
	 * @return String
	 */
	public String getProcessType() {
		return processType;
	}

	/**
	 * 属性 ProcessType 的set方法
	 * 
	 * @return void
	 */
	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getSysSubject() {
		return sysSubject;
	}

	public void setSysSubject(String sysSubject) {
		this.sysSubject = sysSubject;
	}

	public String getProcSubject() {
		return procSubject;
	}

	public void setProcSubject(String procSubject) {
		this.procSubject = procSubject;
	}

	public String getTypeSubject() {
		return typeSubject;
	}

	public void setTypeSubject(String typeSubject) {
		this.typeSubject = typeSubject;
	}

	/**
	 * 属性 subject 的get方法
	 */
	public String getSubject() {
		return subject;
	}
	
	/**
	 * 属性 subject 的set方法
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	/**
	 * 属性 appCode 的get方法
	 */
	public String getAppCode() {
		return appCode;
	}
	
	public String getTaskSource() {
		return taskSource;
	}

	public void setTaskSource(String taskSource) {
		this.taskSource = taskSource;
	}

	public int getTaskCount() {
		return taskCount;
	}

	public void setTaskCount(int taskCount) {
		this.taskCount = taskCount;
	}
	
	/**
	 * 属性 appCode 的set方法
	 */
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	
	/**
	 * 属性 relativePath 的get方法
	 */
	public String getRelativePath() {
		return relativePath;
	}
	
	/**
	 * 属性 relativePath 的set方法
	 */
	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}
	
	/**
	 * 属性 internalId 的get方法
	 */
	public String getInternalId() {
		return internalId;
	}
	
	/**
	 * 属性 internalId 的set方法
	 */
	public void setInternalId(String internalId) {
		this.internalId = internalId;
	}
	
	public String getActualUserId() {
		return actualUserId;
	}

	public void setActualUserId(String actualUserId) {
		this.actualUserId = actualUserId;
	}

	public String toString(){
		try {
			return BeanUtils.describe(this).toString();
		} catch (Exception e) {
			
		}
		return super.toString();
	}
}
