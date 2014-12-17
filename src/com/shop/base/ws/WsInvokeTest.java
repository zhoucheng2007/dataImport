package com.shop.base.ws;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;


public class WsInvokeTest {
	
	public static void main(String[] args) {
		try {
			RPCServiceClient serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			//订单中心Web服务调用测试
			EndpointReference targetEPR = new EndpointReference(
					"http://10.15.9.158/pi/services/Ws_co");
			options.setTo(targetEPR);
			//第二个参数为方法名
			QName namespace = new QName("http://loushang.ws", "execute");
			//方法的参数值集合
			Object[] param = new Object[] { "inputxml" };
			//参数类型集合
			Class[] paramType = new Class[] { String.class };
			Object[] b = serviceClient.invokeBlocking(namespace, param, paramType);
			System.out.println(b[0]);
			
//			//联信服务测试
//			EndpointReference targetEPR1= new EndpointReference(
//			"http://10.15.27.105/htjs/services/ebankService");
//			options.setTo(targetEPR1);
//			//第二个参数为方法名
//			QName namespace1 = new QName("http://service.htjs.com", "getCustPayType");
//			//方法的参数值集合
//			Object[] param1 = new Object[] { "011005310" };
//			//参数类型集合
//			Class[] paramType1 = new Class[] { String.class };
//			Object[] b1 = serviceClient.invokeBlocking(namespace1, param1, paramType1);
//			System.out.println(b1[0]);
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
