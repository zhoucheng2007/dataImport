package com.shop.base.batis;

import java.util.List;

/**
 * bastis通用服务接口类
 * 
 * @author sunfs
 * 
 */
public interface IBatisService {
	
	/**
	 * 通用查询接口
	 * @param sql
	 * @return
	 */
	public  List commonSelect(String sql);
	
	
}
