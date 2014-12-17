package com.shop.base.bwf.util.bsp;

import java.util.Map;

import org.loushang.bsp.share.organization.bean.OrganView;
import org.loushang.bsp.share.organization.provider.IOrganProvider;
import org.loushang.bsp.share.permit.security.provider.ISecurityProvider;

import com.inspur.hsf.config.ServiceFactory;



public class BspUtil {
	private static BspUtil bspUtil = null;
	public static BspUtil getInstance() {
		if (bspUtil == null) {
			bspUtil = new BspUtil();
		}
		return bspUtil;
	}

	private IOrganProvider organProvider=(IOrganProvider)ServiceFactory.getService("bspOrganProvider");
	private ISecurityProvider securityProvider=(ISecurityProvider)ServiceFactory.getService("bspSecurityProvider");
	
	public Map getFunctionInfo(String functionCode){
		return 	securityProvider.getFunctionInfo(functionCode);
	}
	public String getFunctionCodeByUrl(String url){
		return 	securityProvider.getFunctionCodeByUrl(url);
	}


	/**
	 * 获取对应organId所属公司的organId
	 * @param organId
	 * @param organType
	 */
	public String getCorporationOrganId(String organId, String organType){
		if(organType!=null && !"".equals(organType)){
			//对于类型为岗位的organId，获取对应的单位organId
			if("6".equals(organType)){
				return organProvider.getCorpOfPosition(organId);
			}
			OrganView[] OrganView = organProvider.getCorporationOfEmployee(organId);
			if(OrganView!=null && OrganView.length>0){
				return OrganView[0].getOrganId();
			}
		}
		return null;
	}
	
}
