package com.shop.base.rule;

public class RuleCacheUpdateServiceImpl 
	implements IRuleCacheUpdateService{
	
	public void addRuleCache(String comId, String ruleId, String refId,
			String value) {
		RuleUtil.addRuleCache(comId, ruleId, refId, value);
		
	}
	public void deleteRuleCache(String comId, String ruleId, String refId) {
		RuleUtil.deleteRuleCache(comId, ruleId, refId);
		
	}
	public void clearAllRuleCache(){
		RuleUtil.clearAllRuleCache();
	}
}
