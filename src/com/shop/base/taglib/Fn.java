package com.shop.base.taglib;

import org.loushang.bsp.service.BspInfo;
import org.loushang.bsp.service.GetBspInfo;
import org.loushang.waf.mvc.ServletCommandContext;
import javax.servlet.http.HttpServletRequest;

public class Fn {
	
	/**
	 * BSP权限过滤方法
	 * @param opCode
	 * @return
	 */
	public static String hasPermit(String opCode){
		String styleClass = null;
		BspInfo info = GetBspInfo.getBspInfo();
		boolean isPermit = info.hasPermissionByOperCode(opCode);
		if(isPermit){
			styleClass = "";
		}else{
			styleClass = "noPermit";
		}
		return styleClass;
	}
  
    /**
     * 获取应用root
     */
	public  static String getAppRoot(){
		HttpServletRequest request = ServletCommandContext.getRequest();
	    StringBuffer sb = new StringBuffer();
	    sb.append(request.getScheme());
	    sb.append("://");
	    sb.append(request.getServerName());
	    sb.append(":");
	    sb.append(request.getServerPort());
	    sb.append(request.getContextPath());
	    return sb.toString();
	}
}
