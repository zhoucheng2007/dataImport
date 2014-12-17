package com.shop.base.taglib;

/**
 * 功能：用户自定义widget相关接口
 * @author 郑斌
 * @date 2012-12-11 下午1:24:26
 */
public interface IUserWidgetDomain {
	
	/**
	 * 功能：获取用户自定义的widget列表
	 * @author 郑斌
	 * @date 2012-12-11 下午1:24:44
	 * @param userId
	 * @param screenPath
	 * @return
	 */
	public String getUserSetWidgets(String userId,String screenPath);
	
	/**
	 * 功能：插入用户自定义的widget设置
	 * @author 郑斌
	 * @date 2012-12-14 上午10:32:35
	 * @param userId
	 * @param screenPath
	 * @param widgets
	 * @return
	 */
	public int insertUserSet(String userId,String screenPath,String widgets); 
	
	/**
	 * 功能：修改用户自定义的widget设置
	 * @author 郑斌
	 * @date 2012-12-14 上午10:32:35
	 * @param userId
	 * @param screenPath
	 * @param widgets
	 * @return
	 */
	public int updateUserSet(String userId,String screenPath,String widgets); 

}
