package com.shop.base.workday;

public interface IWorkdayService {
	/**
	 * 取开始结束日期之前的工作日
	 * @param comId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public String getWorkday(String comId,String beginDate,String endDate);
}
