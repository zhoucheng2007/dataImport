package com.shop.base.tool;

import org.apache.catalina.tribes.util.UUIDGenerator;


/**
 * 说明：UUID相关的工具类
 * 
 * @author sunshw
 * @date 2012-9-25
 */
public class UUIDTool {
	
	/**
	 * 获取30位长度的内码
	 *
	 * @return
	 */
	public static String getInnerId() {
		return getInnerIdByLength(30);
	}
	/**
	 * 获取指定长度的uuid,建议不少于10位
	 *
	 * @param length
	 * @return
	 */
	public static String getInnerIdByLength(int length) {
		UUIDGenerator uuid = new UUIDGenerator();
		return uuid.getNextSeqId(length).toString();
	}
	
}
