package com.shop.base.bwf.tasklist.taskinfo.domain;

import java.util.List;
import java.util.Map;

import com.shop.base.bwf.tasklist.taskinfo.data.BwfDaiBanTask;

import org.loushang.persistent.util.Page;

/**
 * 待办任务Domain层接口
 * 
 * @author 浪潮软件 BPM产品组
 * Modify by: weijingjie
 * Modified:  改造成spring容器下轻量级工作流，可以通过L3(V6)的事务管理控制
 */
public interface IBwfDaiBanTaskDomain {

	/**
	 * 插入待办任务
	 * 
	 * @param daiBanTask
	 */
	public void insertDaiBanTask(BwfDaiBanTask daiBanTask);

	/**
	 * 根据任务Id删除待办任务
	 * 
	 * @param id
	 */
	public void deleteDaiBanTask(String id);

	/**
	 * 根据环节定义ID和用户organId来获取待办任务
	 * 为轻量级工作流添加
	 * @param actDefId
	 * @param organId
	 * @return BwfDaiBanTask
	 */
	public BwfDaiBanTask getDaiBanTaskByActDefIdAndOrganId(String actDefId, String organId);
	
	/**
	 * 更新当前待办任务的数据
	 * 为轻量级工作流添加
	 * @param assignmentId
	 * @param TaskCount
	 */
	public void updateTaskCount(String assignmentId, int taskCount);
	
	/**
	 * 批量待办任务的数据
	 * 为轻量级工作流添加
	 * @param list
	 */
	public void updateTaskCount(List<BwfDaiBanTask> daiBanTaskList);
	
	/**
	 *  获取指定用户的待办任务(轻量级工作流类型)
	 * @param userId
	 * @param functionCode
	 * @param primaryKey
	 * @return BwfDaiBanTask
	 */
	public BwfDaiBanTask getDaiBanTask(String userId, 
			String functionCode, String primaryKey);
	
	
	/**
	 * 批量插入待办任务
	 * 
	 * @param list<BwfDaiBanTask>
	 */
	public void insert(List<BwfDaiBanTask> list);
	
}
