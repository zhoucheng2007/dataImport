package com.blogzhou.webservice.service;

import java.rmi.RemoteException;

import javax.xml.namespace.QName;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;

import com.blogzhou.common.configuration.ConfigurationRegister;

/**
 * 当前类自己的logger
 */
/**
 * 短信平台接口类
 *
 */

public class SmsWebserviceManager{
	/**
	 * 当前类自己的logger
	 */
	protected static Logger logger = Logger.getLogger(SmsWebserviceManager.class);
	
	private static String TargetEndpointAddress = null;
	
	private static String TargetNamespace = null;
	
	private static Configuration configuration;
	
	static{
		/**
		 * 初始化短信平台配置
		 */
		init();
	}
	
    /**
     * 调用征管的远程服务接口
     * @param sid 服务ID
     * @param requestXML 请求报文
     * @return 返回的报文
     * @throws TaxWebserviceException
     */
	public String doService(String requestXML) throws TaxWebserviceException {
		logger.debug("call Remote method [doService]......");

		try {
			RPCServiceClient serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			EndpointReference targetEPR = new EndpointReference(TargetEndpointAddress);
			QName qname=new QName(TargetNamespace,"getMenus");
	        String[] parameters = new String[]{requestXML};
	        Class[] classes = new Class[] { String.class };
	        String responseXML = (String) serviceClient.invokeBlocking(qname, parameters, classes)[0];        
	        return responseXML;
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new TaxWebserviceException(TaxWebserviceException.FSTAX_INIT_FAIL,"调用短信平台接口出现异常:"+e.getMessage());
		}

	}

	/**
	 * 初始化Webservice配置
	 */
	public static void init(){
		try {
			if(configuration==null){
				 ConfigurationRegister conf = ConfigurationRegister.getInstance();
				 configuration = conf.getConfiguration("fstax");
			}
			TargetEndpointAddress = configuration.getString("sms.TargetEndpointAddress");
			TargetNamespace = configuration.getString("sms.TargetNamespace");
		} catch (Exception e) {
			e.printStackTrace();
			TargetEndpointAddress = null;
			TargetNamespace = null;
		}
	}
	
	
	/**
	 * @param args
	 * @throws TaxWebserviceException 
	 * @throws ServiceException 
	 * @throws RemoteException 
	 * @throws CustomException 
	 */
	public static void main(String[] args) throws TaxWebserviceException, RemoteException {
	    SmsWebserviceManager service = new SmsWebserviceManager();
		String requestXML = "admin";
		String responseXML = service.doService(requestXML);
		System.out.println("[requestXML ]:\n"+requestXML);
		System.out.println("[responseXML]:\n"+responseXML);
		
		
	}
}
