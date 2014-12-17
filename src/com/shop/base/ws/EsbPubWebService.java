/**
 * 
 */
package com.shop.base.ws;

/**
 * @title:北京Esb公共Web服务
 * @description:v6组件公共web服务，发布出去，供其他系统调用
 * @author sunfs
 * @mail:sunfs@inspur.com
 * @date:2013-7-13
 */
public class EsbPubWebService  implements IEsbPubWebService{
	public String execute(String inXml){		
		return "out\r\n"+inXml;
	}
}
