/**
 *  Copyright (c) 2012-2014 http://www.eryansky.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.zc.exception;

/**
 * Service层异�? 继承自BaseException.
 * 
 */
@SuppressWarnings("serial")
public class ServiceException extends BaseException {

	private Integer code;
	private Object obj;

	public ServiceException() {
		super();
	}

	/**
	 * 
	 * @param code
	 *            状�?�?
	 * @param message
	 *            消息
	 * @param obj
	 *            其它数据
	 */
	public ServiceException(Integer code, String message, Object obj) {
		super(message);
		this.code = code;
		this.obj = obj;
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 
	 * @param code
	 *            状�?�?@see com.eryansky.common.model.Result
	 * @param message
	 *            消息描述
	 * @param obj
	 *            其它信息
	 * @param cause
	 *            异常�?
	 */
	public ServiceException(Integer code, String message, Object obj,
			Throwable cause) {
		super(message, cause);
		this.code = code;
		this.obj = obj;
	}

	public Integer getCode() {
		return code;
	}

	public Object getObj() {
		return obj;
	}

}
