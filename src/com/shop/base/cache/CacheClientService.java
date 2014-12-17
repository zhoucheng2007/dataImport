/**
 * 
 */
package com.shop.base.cache;

import com.shop.base.service.BaseServiceImpl;

/**
 * 客户端服务发布
 * @author sunfs
 *
 */
public class CacheClientService extends BaseServiceImpl implements
		ICacheClientService {

	/* (non-Javadoc)
	 * @see org.loushang.util.domain.BaseService#initService()
	 */
	@Override
	protected void initService() {
		// TODO Auto-generated method stub

	}

	public void clearCache() {
		CacheClient.clearCache();
		
	}

}
