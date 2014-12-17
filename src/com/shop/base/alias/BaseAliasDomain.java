package com.shop.base.alias;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lc.v6.jdbc.mybatis.V6SqlSessionUtil;
import com.shop.base.domain.BaseDomainImpl;

/**
 * 别名业务层实现
 * @author weijingjie
 * 2013-12-30
 */
public class BaseAliasDomain extends BaseDomainImpl implements IBaseAliasDomain {

	public Map<String, String> initialAliasCache() {
		
		Map<String,String> rtnMap=new HashMap<String,String>();
		try{
			List<Map<String,String>> rtnList=V6SqlSessionUtil.
			getSqlSession().selectList("com.v6.base.alias.BaseAliasDomain.getAllAlias");
			for(Map<String,String> map:rtnList ){
				rtnMap.put(map.get("ID"), map.get("VALUE"));
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("initialAliasCache error,database error");
		}
		if(rtnMap.isEmpty()){
			System.out.println("initialAliasCache rtnMap is null");
		}
		return rtnMap;
	}

	@Override
	protected void initDomain() {
		// TODO Auto-generated method stub
		
	}

}
