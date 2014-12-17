package com.shop.portal.app;

import java.util.List;
import java.util.Map;
/**
 * 功能:本类为应用中心Service
 * 
 * @author 孔繁博
 */
public interface IAppPageService {	
	
	public List isExistCollect(Map map);	
	public int insertCollect(Map map);
	public List selectCollect(Map map);
	public int insertRecord(Map map);
	public List selectRecord(Map map);
	public int delCollect(Map map); 
	public List getStruTreeRoot(Map map);
	public List getSubStruList(Map map);
	public int updateMenuBaike(Map map);
	public Map queryMenuBaike(Map map);
	public Map updateCollectMenuOrder(Map map);
}