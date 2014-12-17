package com.blogzhou.common.code.exception;

import org.apache.log4j.Logger;

import com.blogzhou.common.exception.BlogzhouException;

public class CodeException extends BlogzhouException {

	public CodeException() {
	}

	/**
	 * 序列化版本兼容性标识
	 */
	static final long serialVersionUID = 147293465160476725L;

	/**
	 * 日志记录对象
	 */
	private static Logger logger = Logger.getLogger(CodeException.class);

	/**
	 * 根据异常id,扩展异常信息，原始异常构造项目的自定义异常 本函数为快捷构造函数，正规定义的异常应使用本构造函数
	 * 
	 * @param id 异常id
	 * @param extmsg 扩展异常信息，该信息将会纪录到系统日志中
	 * @param ex 原始异常信息
	 */
	public CodeException(String id, String extmsg, Exception ex) {
		super(id, extmsg, ex);
	}

	/**
	 * 构造包含系统信息，用户信息和异常ID的异常类 本函数为完整构造函数，一般无需调用本函数进行构造
	 * 
	 * @param id 异常ID
	 * @param sysmsg 系统信息
	 * @param ensysmsg 英文系统信息
	 * @param usermsg 用户信息
	 * @param enusermsg 英文用户信息
	 * @param extmsg 扩展信息,子异常类可以自定义该字段内容
	 * @param ex 已有异常
	 */
	public CodeException(String id, String sysmsg, String ensysmsg,
			String usermsg, String enusermsg, String extmsg, Exception ex) {
		super(id, sysmsg, ensysmsg, usermsg, enusermsg, extmsg, ex);
	}

}
