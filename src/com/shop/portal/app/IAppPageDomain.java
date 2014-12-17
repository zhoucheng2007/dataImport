package com.shop.portal.app;

import java.util.List;
import java.util.Map;

/**
 * 功能:本类为应用中心Domain
 * 
 * @author 孔繁博
 */
public interface IAppPageDomain {
	
	/**
	 * @param map
	 * @return
	 */
	public int insertCollect(Map map);
	public List isExistCollect(Map map);
	public List selectCollect(Map map);
	public int insertRecord(Map map);
	public List selectRecord(Map map);
	public int delCollect(Map map);
	public int delRecord(Map map);
	public List getStruTreeRoot(Map map);
	public List getSubStruList(Map map);
	public Map queryMenuBaike(Map map);
	public int deleteMenuBaike(Map map);
	public int insertMenuBaike(Map map);
	public int updateCollectMenuOrder(Map map);
}
   
