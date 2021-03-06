package com.shop.base.bwf.taskexecution.domain;

import java.util.List;
import java.util.Map;


public interface IBwfTaskExecution {

	/**
	 * 指定任务数和限时时间(批量业务数据 1条待办任务)
	 * 
	 * @param url
	 * 			当前待办任务对应的URL
	 * @param actualUserId
	 *            实际处理人用户ID
	 * @param actualOrganId
	 *            实际处理人组织内码
	 * @param actualOrganName
	 *            实际处理人组织名称
	 * @param nextUrl
	 * 			下一待办任务对应的URL
	 * @param nextOrganId
	 * 			下一待办任务的处理人ID
	 * @param nextOrganName
	 * 			下一待办任务的处理人名称
	 * @param nextOrganType
	 * 			组织类型（可设置的值：1(单位),2(部门),6(岗位),8(员工)）
	 * @param subject
	 * 			下一待办任务的标题
	 * @param taskCount
	 * 			下一待办任务的任务数
	 * @param limitTime
	 * 			下一待办任务的限时时间
	 * @param limitUnit
	 * 			下一待办任务的限时时间单位（可设置的值：N(自然日),D(工作日),h(小时),M(月),Y(年)）
	 * @return Map key分别为
	 * 		<ol>
	 * 		<li><code>yiBanTaskId<code>--已办任务的Id
	 *  	<li><code>daiBanTaskId<code>--待办任务的Id
	 *  	</ol> 
	 
	public Map submit(String url, 
			String actualUserId, String actualOrganId, String actualOrganName,
			String nextUrl, String nextOrganId, String nextOrganName, String nextOrganType,
			String subject, int taskCount,
			int limitTime,String limitUnit);
	*/
	/**
	 * 生成批量待办任务(批量业务数据 1条待办任务)
	 * 
	 * @param url
	 * 			当前待办任务对应的URL
	 * @param actualUserId
	 *            实际处理人用户ID
	 * @param actualOrganId
	 *            实际处理人组织内码
	 * @param actualOrganName
	 *            实际处理人组织名称
	 * @param taskCount
	 * 			下一待办任务的任务数
	 * @param nextTaskList
	 *            下一环节列表(包括参与者),类型为List,内容为Map,key分别为
	 *            <ol>
	 *            <li><code>url<code>--下一待办任务对应的URL
	 *            <li><code>organId<code>--下一待办任务的处理人ID
	 *            <li><code>organName<code>--下一待办任务的处理人名称
	 *            <li><code>organType<code>--组织类型（可设置的值：1(单位),2(部门),6(岗位),8(员工)）
	 *            <li><code>subject<code>--下一待办任务的标题
	 *            <li><code>taskCount<code>--下一待办任务的任务数
	 *            <li><code>limitTime<code>--下一待办任务的限时时间
	 *            <li><code>limitUnit<code>--下一待办任务的限时时间单位（可设置的值：N(自然日),D(工作日),h(小时),M(月),Y(年)）
	 *            <li><code>warnTime<code>--下一待办任务的预警时间
	 *            </ol>
	 * @return Map key分别为
	 * 		<ol>
	 * 		<li><code>yiBanTaskId<code>--已办任务的Id
	 *  	<li><code>daiBanTaskId<code>--待办任务的Id
	 *  	</ol> 
	 
	public Map submitWithTaskList(String url, String actualUserId, 
			String actualOrganId, String actualOrganName, int taskCount,
			List nextTaskList);
	*/	
	/**
	 * 指定业务主键和限时时间(1条业务数据 1条待办任务)
	 * 
	 * @param url
	 * 			当前待办任务对应的URL
	 * @param actualUserId
	 *            实际处理人用户ID
	 * @param actualOrganId
	 *            实际处理人组织内码
	 * @param actualOrganName
	 *            实际处理人组织名称
	 * @param primaryKey
	 * 			当前待办任务的业务主键
	 * @param nextUrl
	 * 			下一待办任务对应的URL
	 * @param nextOrganId
	 * 			下一待办任务的处理人ID
	 * @param nextOrganName
	 * 			下一待办任务的处理人名称
	 * @param nextOrganType
	 * 			组织类型（可设置的值：1(单位),2(部门),6(岗位),8(员工)）
	 * @param subject
	 * 			下一待办任务的标题
	 * @param nextPrimaryKey
	 * 			下一待办任务的业务主键
	 * @param limitTime
	 * 			下一待办任务的限时时间
	 * @param limitUnit
	 * 			下一待办任务的限时时间单位（可设置的值：N(自然日),D(工作日),h(小时),M(月),Y(年)）
	 * @return Map key分别为
	 * 		<ol>
	 * 		<li><code>yiBanTaskId<code>--已办任务的Id
	 *  	<li><code>daiBanTaskId<code>--待办任务的Id
	 *  	</ol> 
	 */
	public Map submitWithPrimaryKey(String url, String primaryKey,
			String actualUserId, String actualOrganId, String actualOrganName, 
			String nextUrl, String nextOrganId, String nextOrganName, String nextOrganType,
			String subject, String nextPrimaryKey, 
			int limitTime,String limitUnit);

	/**
	 * 批量生成待办任务(1条业务数据 1条待办任务)
	 * 
	 * @param url
	 * 			当前待办任务对应的URL
	 * @param primaryKey
	 * 			当前待办任务的业务主键
	 * @param actualUserId
	 *            实际处理人用户ID
	 * @param actualOrganId
	 *            实际处理人组织内码
	 * @param actualOrganName
	 *            实际处理人组织名称
	 * @param nextTaskList
	 *            下一环节列表(包括参与者),类型为List,内容为Map,key分别为
	 *            <ol>
	 *            <li><code>url<code>--下一待办任务对应的URL
	 *            <li><code>organId<code>--下一待办任务的处理人ID
	 *            <li><code>organName<code>--下一待办任务的处理人名称
	 *            <li><code>organType<code>--组织类型（可设置的值：1(单位),2(部门),6(岗位),8(员工)）
	 *            <li><code>subject<code>--下一待办任务的标题
	 *            <li><code>primaryKey<code>--下一待办任务的业务主键
	 *            <li><code>limitTime<code>--下一待办任务的限时时间
	 *            <li><code>limitUnit<code>--下一待办任务的限时时间单位（可设置的值：N(自然日),D(工作日),h(小时),M(月),Y(年)）
	 *            <li><code>warnTime<code>--下一待办任务的预警时间
	 *            </ol>
	 * @return Map key分别为
	 * 		<ol>
	 * 		<li><code>yiBanTaskId<code>--已办任务的Id
	 *  	<li><code>daiBanTaskId<code>--待办任务的Id
	 *  	</ol> 
	 */	
	public Map submitWithPrimaryKeyAndTaskList(String url, String primaryKey,
			String actualUserId, String actualOrganId, String actualOrganName,
			List nextTaskList);
}
