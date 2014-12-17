package com.shop.base.alias;

import java.util.Map;
/**
 * 别名业务层接口
 * @author BlueSirius
 * 2013-12-30
 */
public interface IBaseAliasDomain {
	/**
	 * 初始化别名信息到java缓存
	 * @return map
	 * 		key id
	 * 		value value
	 */
	public Map<String, String> initialAliasCache();
}
