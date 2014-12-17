/**
 * 说明：
 * PermitException.java 2014-5-30 上午1:36:27
 */
package com.shop.base.bsp;

/** 说明：
 * 
 * @author pengzhu
 * PermitException.java 2014-5-30
 */
public class PermitException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1081215653375286809L;
	private static String SPACE = " ";
	private String code = null;
	private String note = null;
	
	private PermitException() {
	}

	private PermitException(String message) {
		super(message);
	}

	private PermitException(Throwable cause) {
		super(cause);
	}

	private PermitException(String message, Throwable cause) {
		super(message, cause);
	}

	public PermitException(String code, String message) {
		this(message);
		//
		this.code = code;
	}

	public PermitException(String code, String message, Throwable cause) {
		this(message, cause);
		//
		this.code = code;
	}

	public PermitException(String code, String message, String note) {
		this(message);
		//
		this.code = code;
		this.note = note;
	}

	public PermitException(String code, String message, String note, Throwable cause) {
		this(message, cause);
		//
		this.code = code;
		this.note = note;
	}
	
	public String getCode(){
		return this.code;
	}
	
	public String getNote(){
		return this.note;
	}
	
	public String toString(){
		return getCode() + SPACE + getMessage() + SPACE + getNote();
	}
	
}
