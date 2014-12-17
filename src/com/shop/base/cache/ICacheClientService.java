package com.shop.base.cache;

/**
 * 客户端缓存服务
 * @author sunfs
 *
 */
public interface ICacheClientService {
	
	//清空前端缓存，用于bases的异外中断
	public void clearCache();

}
