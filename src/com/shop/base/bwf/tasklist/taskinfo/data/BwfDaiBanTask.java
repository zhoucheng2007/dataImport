package com.shop.base.bwf.tasklist.taskinfo.data;

import org.apache.commons.beanutils.BeanUtils;
import org.loushang.next.data.StatefulDatabean;

/**
 * 物理表待办任务
 * 
 * @author 浪潮软件 BPM产品组
 */
public class BwfDaiBanTask extends StatefulDatabean {

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
	 * 域 流程发起人ID
	 */
	private String procCreatorId;
	
	/**
	 * 域 流程发起人名称
	 */
	private String procCreatorName;

	/**
	 * 域 前一环节名称，不同环节间用“，”隔开
	 */
	private String preActDefNames;

	/**
	 * 域 前一环节处理人组织名称(同一环节间用“，”隔开，不同环节间用“；”隔开)
	 */
	private String preOrganNames;

	/**
	 * 域 业务主键
	 */
	private String primaryKey;

	/**
	 * 域 流程类型定义Id
	 */
	private String processType;

	/**
	 * 域 是否可见
	 */
	private String isVisible;
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
	 * 域  环节限时
	 */
	private String actLimitTime;
	/**
	 * 域  环节预警
	 */
	private String actWarnTime;
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
	 */
	public void setActDefName(String actDefName) {
		this.actDefName = actDefName;
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
	 */
	public void setProcCreateTime(String procCreateTime) {
		this.procCreateTime = procCreateTime;
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
	 * 属性 PreActDefNames 的get方法
	 * 
	 * @return String
	 */
	public String getPreActDefNames() {
		return preActDefNames;
	}

	/**
	 * 属性 PreActDefNames 的set方法
	 * 
	 */
	public void setPreActDefNames(String preActDefNames) {
		this.preActDefNames = preActDefNames;
	}

	/**
	 * 属性 PreOrganNames 的get方法
	 * 
	 * @return String
	 */
	public String getPreOrganNames() {
		return preOrganNames;
	}

	/**
	 * 属性 PreOrganNames 的set方法
	 * 
	 */
	public void setPreOrganNames(String preOrganNames) {
		this.preOrganNames = preOrganNames;
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
	 */
	public void setProcessType(String processType) {
		this.processType = processType;
	}

	/**
	 * 属性 IsVisible 的get方法
	 * 
	 * @return String
	 */
	public String getIsVisible() {
		return isVisible;
	}

	/**
	 * 属性 IsVisible 的set方法
	 * 
	 */
	public void setIsVisible(String isVisible) {
		this.isVisible = isVisible;
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
	 * 属性 actLimitTime 的get方法
	 */
	public String getActLimitTime(){
		return actLimitTime;
	}
	/**
	 * 属性 actLimitTime 的set方法
	 */
	public void setActLimitTime(String actLimitTime){
		this.actLimitTime = actLimitTime;
	}
	
	/**
	 * 属性 actWarnTime 的get方法
	 */
	public String getActWarnTime(){
		return actWarnTime;
	}
	/**
	 * 属性 actWarnTime 的get方法
	 */
	public void setActWarnTime(String actWarnTime){
		this.actWarnTime = actWarnTime;
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
	 * 属性 appCode 的get方法
	 */
	public String getAppCode() {
		return appCode;
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
	
	public String toString(){
		try {
			return BeanUtils.describe(this).toString();
		} catch (Exception e) {
			
		}
		return super.toString();
	}
}
