
package com.blogzhou.common.eaivo;

import java.io.Serializable;

import org.dom4j.Element;

public class Head implements Serializable {

	/**
	 * 序列化版本标识ID
	 */
	private static final long serialVersionUID = 1937593574835598659L;

	/**
	 * 交易序号
	 */
	private String jyxh;

	/**
	 * 返回状态码
	 */
	private String repcode;

	private String returnMessage;

	/**
	 * @return 交易状态码.
	 */
	public String getRepcode() {
		return repcode;
	}

	/**
	 * @param repcode
	 *            交易状态码.
	 */
	public void setRepcode(String repcode) {
		this.repcode = repcode;
	}

	/**
	 * 根据XML报文构造Head对象
	 * 
	 * @param parse
	 *            XML解析器
	 * @return Head对象
	 */
	public static Head parse(XMLParse parse) {
		Head head = new Head();
		while (parse.next()) {
			String tmpNode = parse.getCurrentNode();
			boolean isTextNode = parse.isTextNode();
			if (tmpNode.equals(EaiVoConstants.NODE_NAME_HEAD)) {
				break;
			}
			if (tmpNode.equals(EaiVoConstants.NODE_NAME_RETURNMESSAGE)&& isTextNode) {
				head.setReturnMessage(parse.getCurrentValue());
			} else if (tmpNode.equals(EaiVoConstants.NODE_NAME_REPCODE)&& isTextNode) {
				head.setRepcode(parse.getCurrentValue());
			}else if (tmpNode.equals(EaiVoConstants.NODE_NAME_JYXH)&& isTextNode) {
				head.setRepcode(parse.getCurrentValue());
			}
		}
		return head;
	}
 
	/**
	 * 根据XML报文构造Head对象
	 * 
	 * @return Head对象
	 */
	public static Head parse(Element elem) {
		Head head = new Head();
		head.setRepcode(elem.valueOf(EaiVoConstants.NODE_NAME_REPCODE));
		head.setReturnMessage(elem.valueOf(EaiVoConstants.NODE_NAME_RETURNMESSAGE));
		head.setJyxh(elem.valueOf(EaiVoConstants.NODE_NAME_JYXH));
		return head;
	}

	/**
	 * 输出为XML
	 * 
	 * @param sb
	 *            StringBuffer
	 * @return StringBuffer
	 */
	public StringBuffer toString(StringBuffer sb) {
		if (sb == null) {
			sb = new StringBuffer();
		}
		sb.append("<head>");
		if (repcode != null) {
			sb.append("<repcode>" + repcode + "</repcode>");
		}
		if (returnMessage != null) {
			sb.append("<returnMessage>" + returnMessage + "</returnMessage>");
		}
		sb.append("</head>");
		return sb;
	}

	/**
	 * 输出为XML
	 */
	public String toString() {
		return toString(null).toString();
	}

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	public String getJyxh() {
		return jyxh;
	}

	public void setJyxh(String jyxh) {
		this.jyxh = jyxh;
	}
}
