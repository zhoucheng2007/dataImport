package com.shop.base.putmsg;

import java.util.Map;
/*
 * 1.插入的 业务 信息 如果是客户信息调用 addPutMsgCust
 *              如果是组织信息调用 addPutMsgOrg
 */
public interface IBasePutMsg {
 //COM_ID,CUST_TYPE,CUST_ID,REF_TYPE,REF_NUM,MSG,BIZ_POINT,NOTE
  public void addPutMsg(Map map); //插入业务消息 
  public Map addPutMsgWithRetrun(Map map); //插入业务消息 
  public void addPutMsgCust(Map map); //插入客户业务消息 
  public void addPutMsgOrg(Map map); //插入组织业务消息 
  public void calcPutMsg(Map map); //计算将消息 插入消息 池 
  /*
   * 传入当前用户的userId,
   * 返回状态为未读取的系统内的业务消息
   */
  public String getPutMsgNCount(String userId);//获取未读业务消息 
  
  /*
   * 业务组件传入具体接受人的信息 
   * ORGAN_ID	目标对象编码 为人力资源数的员工id
   * RECEIVER_NUM	接受人号码 为null 时从人员队伍信息中取
   */
  public void addPutMsgbyOrgId(Map map);
}
