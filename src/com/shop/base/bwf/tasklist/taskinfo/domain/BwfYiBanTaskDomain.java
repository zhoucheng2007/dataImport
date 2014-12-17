package com.shop.base.bwf.tasklist.taskinfo.domain;

import java.util.Map;

import com.lc.v6.jdbc.mybatis.V6SqlSessionUtil;
import com.shop.base.bwf.tasklist.taskinfo.data.BwfYiBanTask;
import com.shop.base.domain.BaseDomainImpl;

/**
 * 已办任务Domain层实例
 * 
 * @author 浪潮软件 BPM产品组
 * Modify by: weijingjie
 * Modified:  改造成spring容器下轻量级工作流，可以通过L3(V6)的事务管理控制
 */
public class BwfYiBanTaskDomain extends BaseDomainImpl implements IBwfYiBanTaskDomain  {

	public void insertYiBanTask(BwfYiBanTask yiBanTask) {
		V6SqlSessionUtil.getSqlSession()
		.insert("com.v6.base.bwf.tasklist.taskinfo.domain.BwfYiBanTaskDomain.insertYiBanTask",yiBanTask);
	}


	@Override
	protected void initDomain() {
		// TODO Auto-generated method stub
		
	}
	
}
