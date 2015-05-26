package test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.blogzhou.dao.PubOrganDao;
import com.blogzhou.dao.PubStruDao;
import com.blogzhou.dao.PubUsersDao;
import com.blogzhou.entity.PubOrgan;
import com.blogzhou.entity.PubStru;
import com.blogzhou.entity.PubUsers;
import com.util.PinYin4jTool;
/**
 * 浪潮集团用户导入测试用例
 * 上午9:00:56
 * mailto: zhoucheng2007@gmail.com
 */
public class testPubUserImport {
	
	PubUsersDao pubUserDao;
	PubStruDao pubStruDao;
	PubOrganDao pubOgranDao;
	
	@Before
	public void beforTest(){
		pubUserDao=new PubUsersDao();
		pubStruDao=new PubStruDao();
		pubOgranDao=new PubOrganDao();
	}

	@Test
	public void mainMethod() throws ClassNotFoundException, SQLException {
		StringBuffer hql=new StringBuffer("from PubStru where pubStruType='00' and struId<='S0000000000000010209' and struId>='S0000000000000001744'");
		hql.append("and isLeaf='1' and inUse='1'");
		System.out.println(hql.toString());
		List<PubStru> pubStruList=pubStruDao.getByHql(hql.toString());
		System.out.println(pubStruList.size());
		
		List<PubUsers> pubUsersList=new ArrayList<PubUsers>();
		
		HashMap<String, String> userNameMap=new HashMap<String, String>();
		
		StringBuffer orghql=new StringBuffer("from PubStru where pubStruType='00' and struId<'S0000000000000001744'");
		hql.append("and isLeaf='1' and inUse='1'");
		System.out.println(hql.toString());
		List<PubStru> pubStruOrghqlList=pubStruDao.getByHql(orghql.toString());
		
		//pubstru根据organId获取PubStru
		HashMap<String, PubStru> pubStruMap=getPubStruMap(pubStruOrghqlList);
		
		HashMap<String, PubOrgan> pubOrganMap=getPubOrganMap();
		
		
		for (PubStru pubStru:pubStruList){			
			
			PubUsers pubUsers=new PubUsers();
			
			String userName=pubStru.getStruName();
			
			pubUsers.setUserName(userName);
			
			Set<String> stringSet=PinYin4jTool.getPinyin(userName);
			
			String userid=null;			
			for(String s : stringSet){
				userid=s.toString().toUpperCase();
				break;
	        }	        			
			
			for (int i = 0; i < 50; i++) {
				if(userNameMap.containsKey(userid)){
					userid=userid+"9";
				}else{
					break;
				}	
			}
		
			userNameMap.put(userid, userid);	
			pubUsers.setUserid(userid);
						
			String userTypeCode="00";
			pubUsers.setUserTypeCode(userTypeCode);
			pubUsers.setPassword("79b2cf0337180351d2dcc5ee9d625481");
			char c=11;
			pubUsers.setAccountStatus(c);
			char s=0;
			pubUsers.setIsSys(s);
			pubUsers.setCreateTime("20141218 09:49:06");									
			
			String corporationid=getCorporation(pubStru,pubStruMap,pubOrganMap);
			pubUsers.setCorporationid(corporationid);
			pubUsers.setStruId(pubStru.getStruId());
			pubUsers.setDepartmenid(pubStru.getParentId());
			
			pubUsersList.add(pubUsers);
			
			
		}
		pubUserDao.batchSave(pubUsersList);
		//List<PubUsers> list=pubUserDao.getAll();
		//System.out.println(list.size());
	}

	
	@After
	public void tearDown(){
		pubUserDao=null;
		pubStruDao=null;
	}
	
	public HashMap<String, PubOrgan> getPubOrganMap() {
		HashMap<String, PubOrgan> pubOrganMap=new HashMap<String, PubOrgan>();
		
		List<PubOrgan> pubOrganList=pubOgranDao.getByHql("from PubOrgan where pubOrganType like '1%' ");
		
		for(PubOrgan pubOrgan:pubOrganList){
			pubOrganMap.put(pubOrgan.getOrganId(), pubOrgan);
		}
		return pubOrganMap;
	}
	
	public HashMap<String, PubStru> getPubStruMap(List<PubStru> pubStruList) {
		HashMap<String, PubStru> pubStruMap=new HashMap<String, PubStru>();
		for (PubStru pubStru:pubStruList){
			pubStruMap.put(pubStru.getOrganId(), pubStru);
		}
		return pubStruMap;
	}
	//获取pubStru里的单位ID，此方法比较复杂
	public String  getCorporation(PubStru pubStru,HashMap<String, PubStru> pubStruMap,HashMap<String, PubOrgan> pubOrganMap) {
		
		String corpoid=null;
		String parentid=pubStru.getParentId();
		if(parentid==null){
			return pubStru.getOrganId();
		}
		
		//如果当前节点是单位，则返回单位组织机构代码
		if(pubOrganMap.containsKey(parentid)){
			return parentid;
		}
		
		if(pubStruMap.containsKey(parentid)){
			PubStru ps=pubStruMap.get(parentid);
			if(pubOrganMap.containsKey(ps.getParentId())){
				corpoid=ps.getParentId();
			}else {
				corpoid=getCorporation(pubStruMap.get(ps.getParentId()),pubStruMap,pubOrganMap);
			}
		}
		
		return corpoid;
	}		
}
