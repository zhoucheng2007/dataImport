package com.shop.base.rule;

import java.util.List;
import java.util.Map;

import org.loushang.util.Page;

public interface IRuleService {
	
	/**
	 * 删除	业务规则	一条记录
	 * @param  RuleId	 
	 */	
    public void deleteRule(Map paraMap);

	/**
	 * 取得	业务规则	一条明细
	 * @param  paraMap	
	 * @return
	 */
    public Map getRule(Map paraMap);

    /**
     * 查询	业务规则	一页记录
     * @param rowSelection
     * @param map
     * @return
     */
    public Page getAllRule(Map map);

    
    /**
     * 保存规则列表
     * @param ruleList
     */
    public void saveRules(List ruleList);
    
    /**
     * 取业务规则的值
     * @param ruleId
     * @param comId
     * @param rulefile 业务规则文件名称
     * @return
     */
    public String getRuleValue(String ruleId,String comId,String rulefile);
    /**
     * 取业务规则的值(规则对象)
     * @param ruleId
     * @param comId
     * @param rulefile 业务规则文件名称
     * @return
     */
    public String getRuleObjectValue(String ruleId,String comId,String refId);
}
