package com.shop.base.bwf.util;

import org.loushang.persistent.id.UUIDHexGenerator;

/**
 * 随机数生成工具类,调用框架的随机数生成类
 * 
 */
public class UuidUtil {

	// UUID生成器类
	private static UUIDHexGenerator uuid;

	/**
	 * 
	 * 获得用于设置主键的Id
	 * 
	 * @return String
	 */
	public static String generate() {
		if (uuid == null)
			uuid = new UUIDHexGenerator();
		return (String) uuid.generate();
	}
}
