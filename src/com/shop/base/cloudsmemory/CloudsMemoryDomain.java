package com.shop.base.cloudsmemory;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lc.v6.jdbc.mybatis.V6SqlSessionUtil;
import com.shop.base.domain.BaseDomainImpl;

/**
 * 云记忆业务层实现
 * @author weijingjie
 * 2014-1-3
 */
public class CloudsMemoryDomain extends BaseDomainImpl 
	implements ICloudsMemoryDomain{

	Log log=LogFactory.getLog(CloudsMemoryDomain.class);
	
	public Map getSession(Map paramMap){
		//获取当前访问序号
		int dbseq=updateSeq(paramMap);
		Map rtnMap=new HashMap();
		if(0==dbseq){
			//还未记录过session信息
			rtnMap.put("cloudSeq", dbseq);
		}else{
			//已经记录过session信息
			rtnMap.put("cloudSeq", dbseq);
			Map tempMap=(Map)V6SqlSessionUtil.getSqlSession()
				.selectOne("com.v6.base.cloudsmemory.CloudsMemoryDomain.getSession", paramMap);
			rtnMap.put("settingObj",null!=tempMap?(byte[])tempMap.get("SETTINGOBJ"):null);
		}
		return rtnMap;
	}
	
	public void saveSession(Map paramMap){		
		int sessionSeq=
				null!=paramMap.get("cloudSeq")
				?Integer.parseInt(paramMap.get("cloudSeq").toString())
				:0;
		int dbseq=getSeq(paramMap);
		
		if(0==sessionSeq){
			V6SqlSessionUtil.getSqlSession()
			.selectOne("com.v6.base.cloudsmemory.CloudsMemoryDomain.insertSession", paramMap);
			if(log.isDebugEnabled()){
				log.debug("insertSession success");
			}
		}else if (sessionSeq>=dbseq){
			V6SqlSessionUtil.getSqlSession()
			.selectOne("com.v6.base.cloudsmemory.CloudsMemoryDomain.updateSession", paramMap);
			if(log.isDebugEnabled()){
				log.debug("updateSession success");
			}
		}	
	}
	
	/**
	 * @param  map
			   key userId
			   key appCode
			   key objType
	 * @return int
	 */
	private int getSeq(Map paramMap){
		Map rtnMap=(Map)V6SqlSessionUtil.getSqlSession()
		.selectOne("com.v6.base.cloudsmemory.CloudsMemoryDomain.getSeq", paramMap);
		int seq=0;
		if(null!=rtnMap){
			Object seqObj=rtnMap.get("SEQ");
			seq=Integer.parseInt(seqObj.toString());
		}
		return seq;
	}
	private int updateSeq(Map paramMap){
		V6SqlSessionUtil.getSqlSession()
		.update("com.v6.base.cloudsmemory.CloudsMemoryDomain.updateSeq", paramMap);
		int seq=getSeq(paramMap);
		return seq;
	}
	
	@Override
	protected void initDomain() {	
	}

}
