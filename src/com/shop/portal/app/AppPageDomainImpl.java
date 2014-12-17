package com.shop.portal.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lc.v6.jdbc.mybatis.V6SqlSessionUtil;
import com.shop.base.domain.BaseDomainImpl;

/**
 * 功能:本类为应用中心Domain
 * 
 * @author 孔繁博
 */
public class AppPageDomainImpl extends BaseDomainImpl implements	IAppPageDomain {

	private static Log log = LogFactory.getLog(AppPageDomainImpl.class);
	
	protected void initDomain() {
		// TODO Auto-generated method stub
		
	}
	public List isExistCollect(Map map) {
		// TODO Auto-generated method stub
	    return V6SqlSessionUtil.getSqlSession().selectList("AppPortalDomainImpl.isExistCollect",map);
	}
	public int insertCollect(Map map) {
		return V6SqlSessionUtil.getSqlSession().insert("AppPortalDomainImpl.insertCollect",map);
	}
	public List selectCollect(Map map) {
		return V6SqlSessionUtil.getSqlSession().selectList("AppPortalDomainImpl.selectCollect",map);
		
	}
	public List select(Map map) {
		return V6SqlSessionUtil.getSqlSession().selectList("AppPortalDomainImpl.selectCollect",map);
		
	}
	public int insertRecord(Map map) {
		return V6SqlSessionUtil.getSqlSession().insert("AppPortalDomainImpl.insertRecord",map);
	}
	public List selectRecord(Map map) {
		return V6SqlSessionUtil.getSqlSession().selectList("AppPortalDomainImpl.selectRecord",map);
	}
	public int delCollect(Map map) {
		return V6SqlSessionUtil.getSqlSession().delete("AppPortalDomainImpl.delCollect",map);
	}
	public int delRecord(Map map) {
		return V6SqlSessionUtil.getSqlSession().delete("AppPortalDomainImpl.delRecord",map);
	}
	public List getStruTreeRoot(Map map) {
		List list=new ArrayList();
		try {
			list=V6SqlSessionUtil.getSqlSession().selectList("AppPortalDomainImpl.getStruRoot",map);
		} catch (Exception e) {
			log.error("高速服务执行出错",e);
		}
		return list;	
	}
	public List getSubStruList(Map map) {
		List list=new ArrayList();
		try {
			list=V6SqlSessionUtil.getSqlSession().selectList("AppPortalDomainImpl.getSubStru",map);
		} catch (Exception e) {
			log.error("高速服务执行出错",e);
		}
		return list;
	}
	public Map queryMenuBaike(Map map) {
		
		Map resultMap=V6SqlSessionUtil.getSqlSession().selectOne("AppPortalDomainImpl.getMenuBaike", map);
		
		return resultMap;
	}
	public int deleteMenuBaike(Map map) {
		
		int n=V6SqlSessionUtil.getSqlSession().delete("AppPortalDomainImpl.delMenuBaike", map);
		
		return n;
	}
	public int insertMenuBaike(Map map) {
		
		int n=V6SqlSessionUtil.getSqlSession().insert("AppPortalDomainImpl.insertMenuBaike", map);
		
		return n;
	}
	public int updateCollectMenuOrder(Map map) {

		int n=V6SqlSessionUtil.getSqlSession().insert("AppPortalDomainImpl.updateCollectMenuOrder", map);
		
		return n;
	}
}

   
