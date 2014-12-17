package com.shop.base.notice.notice;

import java.util.List;
import java.util.Map;
/**
 * @title: 通知 Service 接口类
 * @author: PC-ZENGWCH
 * @data: 2013-05-27
 * @修改:
 *
 */
public interface INoticeService {
	
	/**
	 * 删除	通知	一条记录
	 */	
    public void delete(String noteId);
    
	/**
	 * 转发	通知	 
	 * @param beanMap
	 * @return
	 */    
    public void forward(Map beanMap);
    
	/**
	 * 发布	通知	一条记录
	 * @param beanMap
	 * @return
	 */    
    public void receive(Map beanMap);

    /**
     * 新增保存并发布	通知	一条记录
     * @param beanMap
     * @return
     */
    public String insert(Map beanMap);
    
    /**
     * 修改保存并发布	通知	一条记录
     * @param beanMap
     * @return
     */
    public void update(Map beanMap);
    
    /**
	 * 根据员工的organId取该员工的消息数目
	 */
    public String getMessageCount(String employeeOrganId);
    
    /**
	 * 给个人发送消息
	 * @param title  消息标题
	 * @param messageContent 消息内容
	 * @param fromOrganId 发送人组织编码
	 * @param fromName 发送人姓名
	 * @param toOrganId  接收人组织编码
	 * @return
	 */
	public void sendMessage(String title,String messageContent,String fromOrganId,String fromName, String toOrganId) ;
		
	
	/**
	 * 给组织发送消息,将消息发送到指定组织下的所有用户
	 * @param title  消息标题
	 * @param messageContent 消息内容
	 * @param fromOrganId 发送人组织编码
	 * @param fromName 发送人姓名
	 * @param toOrganId  接收组织编码
	 * @return
	 */
	public void sendMessageByOrgan(String title,String messageContent,String fromOrganId,String fromName, String toOrganId) ;
	
	/**
	 * 给组织发送消息,将消息发送到指定组织下、指定组织类型的所有用户
	 * @param title  消息标题
	 * @param messageContent 消息内容
	 * @param fromOrganId 发送人组织编码
	 * @param fromName 发送人姓名
	 * @param toOrganId  接收组织编码
	 * @param toOrganType  接收组织类型
	 * @return
	 */
	public void sendMessageByOrganType(String title,String messageContent,String fromOrganId,String fromName, String toOrganId,String toOrganType) ;
	
	/**
	 * 给组织发送消息,将消息发送到指定组织下、指定组织类型的所有用户
	 * @param title  消息标题
	 * @param messageContent 消息内容
	 * @param fromOrganId 发送人组织编码
	 * @param fromName 发送人姓名
	 * @param toOrganId  接收组织编码
	 * @param toOrganType  接收组织类型
	 * @param toStruType  接收组织机构类型
	 * @return
	 */
	public void sendMessageByOrganType(String title, String messageContent,
			String fromOrganId, String fromName, String toOrganId,
			String toOrganType,String toStruType);
	
	/**
     * 查询	消息列表
     * @param conditionMap 查询条件，map的键为大写的字段名
     * @return
     */
    public List queryMessage(Map conditionMap);
    
    /**
     * 将消息设置为已读
     * @param messageId 消息编码
     * @return
     */
    public void setHasRead(String messageId);

    /**
	 * 给组织发送消息,将消息发送到指定组织下、指定岗位类型的所有用户
	 * @param title  消息标题
	 * @param messageContent 消息内容
	 * @param fromOrganId 发送人组织编码
	 * @param fromName 发送人姓名
	 * @param toOrganId  接收组织编码
	 * @param positionType  接收岗位类型
	 * @param toStruType  接收组织机构类型
	 * @return
	 */
    public void sendMessageByPositionType(String title, String messageContent,
			String fromOrganId, String fromName, String toOrganId,
			String positionType,String toStruType) ;
    
    
    
    

}
