package com.shop.base.workday;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.loushang.waf.ComponentFactory;

import com.shop.base.batis.SqlClient;
import com.shop.base.tool.DateTool;

public class Workday {
	private static Log log = LogFactory.getLog(Workday.class);
	private static IWorkdayService workdayService;
	
	private static IWorkdayService getWorkdayService(){
		if(workdayService==null){
			Object bean = ComponentFactory.getBean("workdayService");
			workdayService = (IWorkdayService)bean;
		}
		
		return workdayService;
	}
	/**
	 * 取两天之间的工作日，从PUB_ORGAN_WORKDAY取数
	 * @param comId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static String getWorkday(String comId,String beginDate,String endDate){
		if(beginDate.compareTo(endDate)>0){
			throw new RuntimeException("开始日期不能大于结束日期！");
		}
		//return getWorkdayService().getWorkday(comId,beginDate,endDate);
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT COUNT(1) WORKDAY ");
		sb.append("FROM PUB_ORGAN_WORKDAY ");
		sb.append("WHERE ORGAN_ID = '").append(comId).append("' ");
		sb.append("AND SOLAR_DATE BETWEEN '").append(beginDate).append("' AND '").append(endDate).append("' ");
		sb.append("AND IS_WORKDAY = '1' ");
		if(log.isDebugEnabled()){
			log.debug("取工作日SQL=" + sb.toString());
		}
		List rList = SqlClient.commonSelect(sb.toString());
		String workday = "0";
		if(rList!=null && rList.size()>0){
			Map map = (Map)rList.get(0);
			workday = map.get("WORKDAY").toString();
		}
		return workday;
	}
	/**
	 * 取两个月之间的工作日，从PUB_ORGAN_WORKDAY取数
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static String getWorkdayOfMonth(String comId,String beginMonth,String endMonth){
		String beginDate = beginMonth + "01";
		String endDate = DateTool.getLastDayOfMonth(endMonth);
		return getWorkday(comId,beginDate,endDate);
	}
}
