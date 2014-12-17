package com.shop.base.idwithoutbsp;

import org.loushang.waf.ComponentFactory;

/**
 * 功能：与BSP无关的最大号帮助类
 * @author 郑斌
 * @date 2012-11-9 下午5:18:19dd
 */
public class IncrementIdHelper {
	
	private static IIncrementDomain incrementDomain = (IIncrementDomain) ComponentFactory.getBean("incrementDomain");

	/**
	 * 功能：获取一下个int值
	 * @author 郑斌
	 * @date 2012-11-9 下午5:26:53
	 * @param id
	 * @return
	 */
	public static int nextIntValue(String id) {
		return incrementDomain.nextIntValue(id);
		}

	/**
	 * 功能：获取下一个string值
	 * @author 郑斌
	 * @date 2012-11-9 下午5:27:06
	 * @param id
	 * @return
	 */
	public static String nextStringValue(String id) {
		return incrementDomain.nextStringValue(id);
	}

	/**
	 * 功能：获取下i个int值
	 * @author 郑斌
	 * @date 2012-11-9 下午5:27:10
	 * @param id
	 * @param i
	 * @return
	 */
	public static int[] nextIntValues(String id, int i) {
		return incrementDomain.nextIntValues(id, i);
	}

	/**
	 * 功能：获取下i个String值
	 * @author 郑斌
	 * @date 2012-11-9 下午5:27:14
	 * @param id
	 * @param i
	 * @return
	 */
	public static String[] nextStringValues(String id, int i) {
		return incrementDomain.nextStringValues(id, i);
	}

	}
