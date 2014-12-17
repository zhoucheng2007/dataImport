package com.shop.base.cloudsmemory;

import java.util.Map;

/**
 * 云记忆业务层接口
 * @author weijingjie
 * 2014-1-3
 */
public interface ICloudsMemoryDomain{
	/**
	 * 持久化session信息
	 * @param paramMap
	 */
	public void saveSession(Map paramMap);
	/**
	 * 获取已经保存的session信息
	 * @param paramMap
	 * @return Map
	 */
	public Map getSession(Map paramMap);
}
