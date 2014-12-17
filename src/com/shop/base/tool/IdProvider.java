/**
 * 说明：
 * IdProvider.java 2012-12-7 上午3:43:30
 */
package com.shop.base.tool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.inspur.hsf.service.rpc.bootstrap.ServiceConfig;
import com.inspur.hsf.service.rpc.bootstrap.ServiceReference;

/**
 * 说明：获取BSP业务流水号工具类    
 * 
 * @author pengzhu IdProvider.java 2012-12-7
 */
public class IdProvider {

	private static Log log = LogFactory.getLog(IdProvider.class);

	private static String PubIdTableProvider = "org.loushang.bsp.id.domain.IPubIdTableProvider";

	private static ServiceReference getPubIdTableProvider() {
		ServiceConfig serviceConfig = new ServiceConfig();
		serviceConfig.setServiceName(PubIdTableProvider);
		return new ServiceReference(serviceConfig);
	}

	/**
	 * 获取流水号
	 * 
	 * @param id
	 * @return
	 */
	public static int nextIntValue(String id) {
		Object[] parms = { id };
		Integer i = 0;
		try {
			i = (Integer) getPubIdTableProvider().invoke("nextIntValue", parms);
		} catch (Throwable e) {
			if (log.isErrorEnabled()) {
				log.error("获取业务流水号[" + id + "]出错" + e.getMessage()
						+ e.getCause());
			}
			throw new RuntimeException("获取业务流水号[" + id + "]出错", e);
		}
		return i;
	}

	/**
	 * 获取流水号
	 * 
	 * @param id
	 * @return
	 */
	public static long nextLongValue(String id) {
		Object[] parms = { id };
		Long l = 0L;
		try {
			l = (Long) getPubIdTableProvider().invoke("nextLongValue", parms);
		} catch (Throwable e) {
			if (log.isErrorEnabled()) {
				log.error("获取业务流水号[" + id + "]出错" + e.getMessage()
						+ e.getCause());
			}
			throw new RuntimeException("获取业务流水号[" + id + "]出错", e);
		}
		return l;
	}

	/**
	 * 获取流水号
	 * 
	 * @param id
	 * @return
	 */
	public static String nextStringValue(String id) {
		String idValue = "";
		Object[] parms = { id };
		try {
			idValue = (String) getPubIdTableProvider().invoke(
					"nextStringValue", parms);
		} catch (Throwable e) {
			if (log.isErrorEnabled()) {
				log.error("获取业务流水号[" + id + "]出错" + e.getMessage()
						+ e.getCause());
			}
			throw new RuntimeException("获取业务流水号[" + id + "]出错", e);
		}
		return idValue;
	}

	/**
	 * 获取流水号
	 * 
	 * @param id
	 * @param organId
	 * @return
	 */
	public static String nextStringValueByOrganId(String id, String organId) {
		String idValue = "";
		Object[] parms = { id, organId };
		try {
			idValue = (String) getPubIdTableProvider().invoke(
					"nextStringValueByOrganId", parms);
		} catch (Throwable e) {
			if (log.isErrorEnabled()) {
				log.error("获取组织[" + organId + "]业务流水号[" + id + "]出错"
						+ e.getMessage() + e.getCause());
			}
			throw new RuntimeException("获取[" + organId + "]业务流水号[" + id
					+ "]出错", e);
		}
		return idValue;
	}

	/**
	 * 获取流水号
	 * 
	 * @param id
	 * @param n
	 * @return
	 */
	public static int[] nextIntValues(String id, int n) {
		int[] idValues;
		Object[] parms = { id, n };
		try {
			idValues = (int[]) getPubIdTableProvider().invoke("nextIntValues",
					parms);
		} catch (Throwable e) {
			if (log.isErrorEnabled()) {
				log.error("获取业务流水号[" + id + "]出错" + e.getMessage()
						+ e.getCause());
			}
			throw new RuntimeException("获取业务流水号[" + id + "]出错", e);
		}
		return idValues;
	}

	/**
	 * 获取流水号
	 * 
	 * @param id
	 * @param n
	 * @return
	 */
	public static long[] nextLongValues(String id, int n) {
		long[] idValues;
		Object[] parms = { id, n };
		try {
			idValues = (long[]) getPubIdTableProvider().invoke(
					"nextLongValues", parms);
		} catch (Throwable e) {
			if (log.isErrorEnabled()) {
				log.error("获取业务流水号[" + id + "]出错" + e.getMessage()
						+ e.getCause());
			}
			throw new RuntimeException("获取业务流水号[" + id + "]出错", e);
		}
		return idValues;
	}

	/**
	 * 获取流水号
	 * 
	 * @param id
	 * @param n
	 * @return
	 */
	public static String[] nextStringValues(String id, int n) {
		String[] idValues;
		Object[] parms = { id, n };
		try {
			idValues = (String[]) getPubIdTableProvider().invoke(
					"nextStringValues", parms);
		} catch (Throwable e) {
			if (log.isErrorEnabled()) {
				log.error("获取业务流水号[" + id + "]出错" + e.getMessage()
						+ e.getCause());
			}
			throw new RuntimeException("获取业务流水号[" + id + "]出错", e);
		}
		return idValues;
	}

	/**
	 * 获取流水号
	 * 
	 * @param id
	 * @param organId
	 * @param n
	 * @return
	 */
	public static String[] nextStringValuesByOrganId(String id,
			String organId, int n) {
		String[] idValues;
		Object[] parms = { id, organId, n };
		try {
			idValues = (String[]) getPubIdTableProvider().invoke(
					"nextStringValueByOrganIds", parms);
		} catch (Throwable e) {
			if (log.isErrorEnabled()) {
				log.error("获取组织["+organId+"]业务流水号[" + id + "]出错" + e.getMessage()
						+ e.getCause());
			}
			throw new RuntimeException("获取组织["+organId+"]业务流水号[" + id + "]出错", e);
		}
		return idValues;
	}

	/*
	 * public abstract int nextIntValue(String paramString);
	 * 
	 * public abstract long nextLongValue(String paramString);
	 * 
	 * public abstract String nextStringValue(String paramString);
	 * 
	 * public abstract String nextStringValueByOrganId(String paramString1,
	 * String paramString2);
	 * 
	 * public abstract int[] nextIntValues(String paramString, int paramInt);
	 * 
	 * public abstract long[] nextLongValues(String paramString, int paramInt);
	 * 
	 * public abstract String[] nextStringValues(String paramString, int
	 * paramInt);
	 * 
	 * public abstract String[] nextStringValueByOrganIds(String paramString1,
	 * String paramString2, int paramInt);
	 * 
	 */
}
