package com.shop.base.cloudsmemory;

import java.util.Map;

/**
 * 云记忆事务层
 * @author weijingjie
 * 2014-1-3
 */
public interface ICloudsMemoryService {
	
	/**
	 * 保存session信息
	 * @param paramMap
	 */
	public void saveSession(Map paramMap);
	/**
	 * 获取session信息，无事务控制
	 * @param paramMap
	 * @return Map
	 */
	public Map getSession(Map paramMap);

}
