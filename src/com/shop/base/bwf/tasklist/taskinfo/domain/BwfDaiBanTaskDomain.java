package com.shop.base.bwf.tasklist.taskinfo.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lc.v6.jdbc.mybatis.V6SqlSessionUtil;
import com.shop.base.bwf.tasklist.taskinfo.data.BwfDaiBanTask;
import com.shop.base.domain.BaseDomainImpl;

import org.mybatis.spring.SqlSessionTemplate;

/**
 * 待办任务Domain层实例
 * 
 * @author 浪潮软件 BPM产品组
 * Modify by: weijingjie
 * Modified:  改造成spring容器下轻量级工作流，可以通过L3(V6)的事务管理控制
 */
public class BwfDaiBanTaskDomain extends BaseDomainImpl implements IBwfDaiBanTaskDomain {

	@Override
	protected void initDomain() {
		// TODO Auto-generated method stub
		
	}

	public void insertDaiBanTask(BwfDaiBanTask daiBanTask) {
		V6SqlSessionUtil.getSqlSession()
		.insert("com.v6.base.bwf.tasklist.taskinfo.domain.BwfDaiBanTaskDomain.insertDaiBanTask",daiBanTask);
		//创建待办任务之后，插入任务关系列表，sunfs,20140625
		V6SqlSessionUtil.getSqlSession()
		.delete("com.v6.base.bwf.tasklist.taskinfo.domain.BwfDaiBanTaskDomain.insertTaskUser",daiBanTask.getId());
	}

	public void deleteDaiBanTask(String id) {
		//删除待办任务之前，删除任务关系列表，sunfs,20140625
		V6SqlSessionUtil.getSqlSession()
		.delete("com.v6.base.bwf.tasklist.taskinfo.domain.BwfDaiBanTaskDomain.deleteTaskUser",id);
		
		Map paramMap=new HashMap();
		paramMap.put("id",id);
		V6SqlSessionUtil.getSqlSession()
		.delete("com.v6.base.bwf.tasklist.taskinfo.domain.BwfDaiBanTaskDomain.deleteDaiBanTask",paramMap);
		
	}

	public BwfDaiBanTask getDaiBanTaskByActDefIdAndOrganId(String actDefId,
			String organId) {
		Map paramMap=new HashMap();
		paramMap.put("actDefId",actDefId);
		paramMap.put("organId",organId);
		BwfDaiBanTask daiBanTask=(BwfDaiBanTask)V6SqlSessionUtil.getSqlSession()
			.selectOne("com.v6.base.bwf.tasklist.taskinfo.domain.BwfDaiBanTaskDomain.getDaiBanTaskByActDefIdAndOrganId",paramMap);
		return daiBanTask;
	}

	public void updateTaskCount(String assignmentId, int taskCount) {
		Map paramMap=new HashMap();
		paramMap.put("id",assignmentId);
		paramMap.put("taskCount",taskCount);
		V6SqlSessionUtil.getSqlSession()
		.update("com.v6.base.bwf.tasklist.taskinfo.domain.BwfDaiBanTaskDomain.updateTaskCount",paramMap);	
	}

	public void updateTaskCount(List<BwfDaiBanTask> daiBanTaskList) {
		SqlSessionTemplate sqlSessionTemplate=V6SqlSessionUtil.getSqlSession();
		for(BwfDaiBanTask daiBanTask:daiBanTaskList){
			Map paramMap=new HashMap();
			paramMap.put("id",daiBanTask.getId());
			paramMap.put("taskCount",daiBanTask.getTaskCount());
			sqlSessionTemplate.update("com.v6.base.bwf.tasklist.taskinfo.domain.BwfDaiBanTaskDomain.updateTaskCount",paramMap);
		}
	}

	public BwfDaiBanTask getDaiBanTask(String userId, String functionCode,
			String primaryKey) {
		Map paramMap=new HashMap();
		paramMap.put("userId",userId);
		paramMap.put("functionCode",functionCode);
		paramMap.put("primaryKey",primaryKey);
		BwfDaiBanTask daiBanTask=(BwfDaiBanTask)V6SqlSessionUtil.getSqlSession()
			.selectOne("com.v6.base.bwf.tasklist.taskinfo.domain.BwfDaiBanTaskDomain.getDaiBanTask",paramMap);
		return daiBanTask;
	}

	public void insert(List<BwfDaiBanTask> list) {
		SqlSessionTemplate sqlSessionTemplate=V6SqlSessionUtil.getSqlSession();
		for(BwfDaiBanTask daiBanTask:list){			
			sqlSessionTemplate.insert("com.v6.base.bwf.tasklist.taskinfo.domain.BwfDaiBanTaskDomain.insertDaiBanTask",daiBanTask);
			//创建待办任务之后，插入任务关系列表，sunfs,20140625
			V6SqlSessionUtil.getSqlSession()
			.delete("com.v6.base.bwf.tasklist.taskinfo.domain.BwfDaiBanTaskDomain.insertTaskUser",daiBanTask.getId());
			
		}
		
	}

	
	
}
