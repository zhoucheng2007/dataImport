package com.shop.portal.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shop.base.service.BaseServiceImpl;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

/**
 * 功能:本类为应用中心ServiceImpl
 * 
 * @author 孔繁博
 */
public class AppPageServiceImpl extends BaseServiceImpl implements IAppPageService {

	IAppPageDomain appPageDomain = null;

	public void setAppPageDomain(IAppPageDomain appPageDomain) {
		this.appPageDomain = appPageDomain;
	}

	public IAppPageDomain getAppPageDomain() {
		return appPageDomain;
	}

	private static Log log = LogFactory.getLog(AppPageServiceImpl.class);

	protected void initService() {
	}

	public int insertCollect(Map map) {
		return getAppPageDomain().insertCollect(map);
	}

	public List isExistCollect(Map map) {
		return getAppPageDomain().isExistCollect(map);
	}

	public List selectCollect(Map map) {
		// TODO Auto-generated method stub
		List list = getAppPageDomain().selectCollect(map);
		return list;
	}

	public int insertRecord(Map map) {
		// getAppPageDomain().delRecord(map);
		return getAppPageDomain().insertRecord(map);
	}

	public List selectRecord(Map map) {
		// TODO Auto-generated method stub
		return getAppPageDomain().selectRecord(map);
	}

	public int delCollect(Map map) {
		getAppPageDomain().delRecord(map);
		return getAppPageDomain().delCollect(map);
	}

	public List getStruTreeRoot(Map map) {
		return getAppPageDomain().getStruTreeRoot(map);
	}

	public List getSubStruList(Map map) {
		List list=new ArrayList();
		String id=(String)map.get("id");
		if(id.equals("rootId")){
			list=getAppPageDomain().getStruTreeRoot(map);
		}else{
			list=getAppPageDomain().getSubStruList(map);
		}
		for(int i=0;i<list.size();i++){
			Map temp=(Map)list.get(i);
			
		}
		return list;
	}
	
	public int updateMenuBaike(final Map map) {
		getTransactionTemplate().execute(
				new TransactionCallbackWithoutResult() {
					protected void doInTransactionWithoutResult(
							TransactionStatus arg0) {
						getAppPageDomain().deleteMenuBaike(map);
						int n=getAppPageDomain().insertMenuBaike(map);
					}
				});
		return 1;
	}

	public Map queryMenuBaike(Map map) {
		
		Map resultMap=getAppPageDomain().queryMenuBaike(map);
		
		return resultMap;
	}

	public Map updateCollectMenuOrder(final Map map) {
		getTransactionTemplate().execute(
				new TransactionCallbackWithoutResult() {
					protected void doInTransactionWithoutResult(
							TransactionStatus arg0) {
						
						String[] idArr=(String[])map.get("idArr");
						String userId=(String)map.get("userId");
						for(int i=0,len=idArr.length;i<len;i++){
							Map map1=new HashMap();
							map1.put("menuId", idArr[i]);
							map1.put("userId", userId);
							map1.put("order", String.valueOf(i));
							getAppPageDomain().updateCollectMenuOrder(map1);
						}
					}
				});
		return map;
	}
	

}
