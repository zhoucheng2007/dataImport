package com.shop.base.rule;

public interface IRuleCacheUpdateService {
	public void addRuleCache(String comId,String ruleId,String refId,String value);
	public void deleteRuleCache(String comId,String ruleId,String refId);
	public void clearAllRuleCache();
}
