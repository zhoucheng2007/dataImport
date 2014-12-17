package com.shop.base.idwithoutbsp;

/**
 * 功能：最大号domain接口
 * @author 郑斌
 * @date 2012-11-9 上午11:37:39
 */
public interface IIncrementDomain {

	/**
	 * 功能：获取下一个int值
	 * @author 郑斌
	 * @date 2012-11-9 上午11:49:06
	 * @return
	 */
	public int nextIntValue(String id);

	/**
	 * 功能：获取下一个String值
	 * @author 郑斌
	 * @date 2012-11-9 上午11:49:06
	 * @return
	 */
	public String nextStringValue(String id);
	
	/**
	 * 功能：获取下i个int值
	 * @author 郑斌
	 * @date 2012-11-9 上午11:49:06
	 * @return
	 */
	public int[] nextIntValues(String id,int i);

	/**
	 * 功能：获取下i个String值
	 * @author 郑斌
	 * @date 2012-11-9 上午11:49:06
	 * @return
	 */
	public String[] nextStringValues(String id,int i);
}
