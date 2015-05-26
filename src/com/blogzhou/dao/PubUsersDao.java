package com.blogzhou.dao;

// Generated 2014-9-24 15:31:15 by Hibernate Tools 4.0.0

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.blogzhou.entity.PubUsers;

/**
 * Home object for domain model class PubStru.
 * @see com.blogzhou.entity.PubStru
 * @author Hibernate Tools
 */
public class PubUsersDao extends BaseDao<PubUsers>{

	private static final Log log = LogFactory.getLog(PubUsersDao.class);

	@Override
	public PubUsers findById(String id) {
		log.debug("getting instance with id: " + id);
		try {
			PubUsers instance = (PubUsers) currentSession.get(PubUsers.class, id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}


	
	/*
	 * 鑾峰彇鎵�湁鐨勭粍缁囨満鏋勫璞�
	 * @ Return 鏁版嵁搴撲腑鎵�湁鐨勫璞�
	 * @ 
	 */
    
	public List<PubUsers> getAll() {
		String hql="from PubUsers";
		try {
			List<PubUsers> results = getByHql(hql);
			return results;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public void batchSave(List<PubUsers> pubUsersList) throws ClassNotFoundException, SQLException{
				
		long begin = System.currentTimeMillis();   
		Class.forName("com.ibm.db2.jcc.DB2Driver");
		//2.鑾峰彇鏁版嵁搴撹繛鎺�
		Connection conn=(Connection) DriverManager.getConnection("jdbc:db2://10.36.98.232:50000/v6db", "db2inst1", "db2inst1"); 
		Statement sta=conn.createStatement();
		int i=0;       
		try {
			//3.閫氳繃Connection瀵硅薄鍒涘缓Statement瀵硅薄
			HashMap<String, String> userNameMap=new HashMap<String, String>();
			userNameMap.put("XIONGYUFENG", "XIONGYUFENG");
			userNameMap.put("WANQUN", "WANQUN");
			userNameMap.put("WANGKAI", "WANGKAI");
			userNameMap.put("YUANQUAN", "YUANQUAN");
			userNameMap.put("JIANGWEN", "JIANGWEN");
			userNameMap.put("LXMTEST1", "LXMTEST1");
			int temp=10;
			conn.setAutoCommit(false);
			for(PubUsers pubUsers:pubUsersList){	
				if(userNameMap.containsKey(pubUsers.getUserid())){
					continue;
				}
				String sql= "INSERT INTO PUB_USERS (USER_ID, USER_TYPE_CODE, USER_NAME, PASSWORD, ACCOUNT_STATUS, IS_SYS, CREATE_TIME, LOCK_TIME, EXPIRED_TIME, PWD_UPT_TIME, PWD_TIME, ID_NUM) VALUES ('"+pubUsers.getUserid()+"', '"+pubUsers.getUserTypeCode()+"', '"+pubUsers.getUserName()+"', '"+pubUsers.getPassword()+"', '11', '0', '"+pubUsers.getCreateTime()+"', null, null, null, null, null)";
				
				String useremployeesql="INSERT INTO PUB_USER_EMPLOYEE (USER_ID, CORPORATION_ID, DEPARTMENT_ID, EMPLOYEE_ID) VALUES ('"+pubUsers.getUserid()+"', '"+pubUsers.getCorporationid()+"', '"+pubUsers.getDepartmenid()+"', '"+pubUsers.getStruId()+"')";
				
				String policysql="INSERT INTO PUB_USER_POLICY (USER_ID, IS_USE_IP, IP_POLICY_VALUE, MAX_SESSION_NUMBER) VALUES ('"+pubUsers.getUserid()+"', '0', '', 1)";
								
                temp=temp+1;
                String s="";
                if(temp<10){
                	s="00000000000000000000000000000"+temp;	                    	
                }else if(temp<100){
                	s="0000000000000000000000000000"+temp;	
                }else if(temp<1000){
                	s="000000000000000000000000000"+temp;	
                }else if(temp<10000){
                	s="00000000000000000000000000"+temp;	
                }else if(temp<100000){
                	s="0000000000000000000000000"+temp;	
                }
				
				String rolesql="INSERT INTO PUB_USER_ROLE (RULE_ID, RULE_TYPE, TARGET, ROLE_ID) VALUES ('"+s+"', '00', '"+pubUsers.getUserid()+"', 'PUBLIC')";
                
				sta.executeUpdate(sql);
				sta.executeUpdate(useremployeesql);
				sta.executeUpdate(policysql);
				sta.executeUpdate(rolesql);
				i++;
	            if(i%100==0){   //姣忎竴鍗冩潯鍒锋柊骞跺啓鍏ユ暟鎹簱  
	            	conn.commit();  
	            } 
			}
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conn.rollback();
		}finally{
			sta.close();
			conn.close();
		}				
        
        long end = System.currentTimeMillis();  
        System.out.println((end-begin)/1000.0);  
	}
	
}
