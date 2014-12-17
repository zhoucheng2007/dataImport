package com.shop.base.webservice.pub;

import java.lang.reflect.Method;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.loushang.waf.ComponentFactory;

import com.shop.base.rule.RuleUtil;

/**
 * 订单服务
 * 
 * @author linqy
 * 
 */
public class V6ServiceTool implements IV6ServiceTool {
	private static Log log = LogFactory.getLog(V6ServiceTool.class);

	/**
	 * 
	 * 根据传入的xml解析参数，验证用户名密码角色类型，返回验证xml结果
	 * 
	 * @param inXml
	 * @return outXml
	 */
	@SuppressWarnings("finally")
	public String execute(String inXml) {
		String code = ReturnXmlTool.SUCCESS;
		String mainXml = "";
		long st = System.currentTimeMillis();

		if (log.isWarnEnabled())
			log.warn("V6ServiceTool-- xmlServer start time=" + st
					+ " 传入的XML字符串是 \n" + inXml);
		Document dom = null;
		Element head = null;
		try {
			dom = DocumentHelper.parseText(inXml);
			Element root = dom.getRootElement();
			head = root.element("HEAD");// head
			// String target = head.elementText("TARGET");
			String serName =head.elementText("SERNAME");// 服务名
			String funName = head.elementText("FUNNAME");// 方法名
			Element body = root.element("BODY");
			if (log.isDebugEnabled())
				log.debug("serName=" + serName + " funName=" + funName);
			System.out.println("LINExxa = " +serName);
			// 调用实际方法
			//Class<? extends Object> c = com.v6.base.webservice.PutMsgService.class;//ComponentFactory.getBean(serName)
					//.getClass();// 获得类
			// 调用实际方法
			Class<? extends Object> c = ComponentFactory.getBean(serName)
								.getClass();// 获得类			
			System.out.println("LINExxa = " );
			Method m = c.getMethod(funName, String.class);// 方法
			Object instance = c.newInstance();
	    	mainXml = (String) m.invoke(instance, body.asXML());
			System.out.println(mainXml.toString());
		} catch (DocumentException e) {
			code = ReturnXmlTool.PARSE_XML_FAILED;
			log.error("V6ServiceTool-- xmlServer error:" +"a1"+ e);
		} catch (SecurityException e) {
			code = ReturnXmlTool.FAILED;
			log.error("V6ServiceTool-- xmlServer error:" +"a2"+ e);
		} catch (NoSuchMethodException e) {
			code = ReturnXmlTool.SERIVCE_NOTEXIST;
			log.error("V6ServiceTool-- xmlServer error:"+"a3" + e);
		} catch (InstantiationException e) {
			code = ReturnXmlTool.FAILED;
			log.error("V6ServiceTool-- xmlServer error:" +"a4"+ e);
		} catch (IllegalAccessException e) {
			code = ReturnXmlTool.FAILED;
			log.error("V6ServiceTool-- xmlServer error:a5" + e);
		} catch (IllegalArgumentException e) {
			code = ReturnXmlTool.FAILED;
			log.error("V6ServiceTool-- xmlServer error:a6" + e);
		} finally {
			if (code == null || code.equals(""))
				code = ReturnXmlTool.FAILED;
			return getRetXml(code, mainXml, head);
		}
	}

	private String getRetXml(String code, String mainXml, Element head) {
		StringBuffer sb = new StringBuffer();
		// 更新时间
		Element timeEl = head.element("DATE");
		head.remove(timeEl);
		String time = ReturnXmlTool.getCurTime();
		timeEl.setText(time);
		head.add(timeEl);

		sb.append("<MSG>").append(head.asXML());
		sb.append("<BODY CODE=\"").append(code).append("\" ");
		sb.append("MSG=\"").append(ReturnXmlTool.getMsg(code)).append("\" ");
		sb.append("TRANS_TIME=\"").append(time).append("\">");
		sb.append(mainXml);
		sb.append("</BODY></MSG>");
		return sb.toString();
	}

	public static String WsInvoke(String inXml, String target, String serName, String funName) {
		String retXml = "";
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("<MSG>");
			sb.append("<HEAD><ID></ID><NAME></NAME><SOURCE></SOURCE>");
			sb.append("<TARGET>").append(target).append("</TARGET>");
			sb.append("<SERNAME>").append(serName).append("</SERNAME>");
			sb.append("<FUNNAME>").append(funName).append("</FUNNAME>");
			sb.append("<MSGTYPE></MSGTYPE><RTCODE></RTCODE><RTDESC></RTDESC>");
			sb.append("<DATE>").append(ReturnXmlTool.getCurTime()).append("</DATE>");
			sb.append("</HEAD>");
			sb.append("<BODY>").append(inXml).append("</BODY>");
			sb.append("</MSG>");
			RPCServiceClient serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			
			//BANK_OTHER_PAY_CENTER_URL  00  http://10.15.27.105/htjs/services/ebankService	电子结算：其它结算中心服务URL，全局参数
			String payCenterUrl = RuleUtil.getRuleValue("BANK_OTHER_PAY_CENTER_URL", "00");
			log.debug("V6ServiceTool.WsInvoke 消息服务URL==" + payCenterUrl);
			
			// 订单中心Web服务调用测试
			EndpointReference targetEPR = new EndpointReference(
					//"http://10.15.9.158/co/services/Ws_co");
					//"http://10.15.27.105/htjs/services/ebankService");
					payCenterUrl);
			options.setTo(targetEPR);
			// 第二个参数为方法名
			//QName namespace = new QName("http://loushang.ws", "execute");
			QName namespace = new QName("http://service.htjs.com", funName);
			// 方法的参数值集合
			log.debug("V6ServiceTool.WsInvoke 服务调用参数： XML=" + sb.toString());
			Object[] param = new Object[] { sb.toString() };
			// 参数类型集合
			Class[] paramType = new Class[] { String.class };
			Object[] b = serviceClient.invokeBlocking(namespace, param,
					paramType);
			retXml = b[0].toString();
		} catch (AxisFault e) {
			log.error("V6ServiceTool.WsInvoke AxisFault e=" + e);
			return "<BODY CODE=\"999\"></BODY>";
		} catch (Exception e) {
			log.error("V6ServiceTool.WsInvoke Exception e=" + e);
			return "<BODY CODE=\"999\"></BODY>";
		}
		try {
			Document dom = DocumentHelper.parseText(retXml);
			Element root = dom.getRootElement();
			Element body = root.element("BODY");
			retXml = body.asXML();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retXml;
	}

	public static void main(String[] args) {
		String inXml = "<MSG><HEAD><ID></ID><NAME></NAME><SOURCE></SOURCE><TARGET>BASE</TARGET><SERNAME>putMsgService</SERNAME><FUNNAME>addPutMsg</FUNNAME><MSGTYPE></MSGTYPE><RTCODE></RTCODE><RTDESC></RTDESC><DATE>20091019153421</DATE></HEAD><BODY CODE='000' MSG='中文描述' TRANS_TIME='yyyymmddhhmmss'>"+
				"<DATASET COM_ID='N10370101' ORGAN_TYPE='63' ORGAN_ID='JNLC63000002'" +
        		" REF_TYPE='00' REF_NUM='00' SEND_STATUS='00' MSG='20140207errrrrhello'" +
        		" BIZ_POINT='20010' NOTE='00' TYPE='ORG' ></DATASET></BODY></MSG>";
		V6ServiceTool to = new V6ServiceTool();
		to.execute(inXml);
	}

}
