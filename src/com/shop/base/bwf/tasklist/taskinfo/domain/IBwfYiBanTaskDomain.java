package com.shop.base.bwf.tasklist.taskinfo.domain;

import java.util.Map;

import com.shop.base.bwf.tasklist.taskinfo.data.BwfYiBanTask;

import org.loushang.persistent.util.Page;

/**
 * 已办任务Domain层接口
 * 
 * @author 浪潮软件 BPM产品组
 * Modify by: weijingjie
 * Modified:  改造成spring容器下轻量级工作流，可以通过L3(V6)的事务管理控制
 */
public interface IBwfYiBanTaskDomain {
	/**
	 * 插入已办任务
	 * 
	 * @param yiBanInfo
	 */
	public void insertYiBanTask(BwfYiBanTask yiBanTask);

}
