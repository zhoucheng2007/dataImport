package com.shop.base.cloudsmemory;

import java.util.Map;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.shop.base.service.BaseServiceImpl;

public class CloudsMemoryService extends BaseServiceImpl 
	implements ICloudsMemoryService{

	ICloudsMemoryDomain cloudsMemoryDomain;
	
	public void saveSession(final Map paramMap) {
		getTransactionTemplate().execute(new TransactionCallback() {	
			public Object doInTransaction(TransactionStatus arg0) {
				cloudsMemoryDomain.saveSession(paramMap);
				return null;
			}
		});
	}

	public Map getSession(Map paramMap) {
		//获取session信息时会更新获取的序号
		//减少云记忆对基本业务功能的影响，不在此功能中加事务控制。
		return cloudsMemoryDomain.getSession(paramMap);
	}

	@Override
	protected void initService() {
	}

	public ICloudsMemoryDomain getCloudsMemoryDomain() {
		return cloudsMemoryDomain;
	}

	public void setCloudsMemoryDomain(ICloudsMemoryDomain cloudsMemoryDomain) {
		this.cloudsMemoryDomain = cloudsMemoryDomain;
	}
	
}
