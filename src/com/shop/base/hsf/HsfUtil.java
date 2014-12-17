package com.shop.base.hsf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.inspur.hsf.service.rpc.bootstrap.ServiceConfig;
import com.inspur.hsf.service.rpc.bootstrap.ServiceReference;

/**
 * @title:高速服务框架工具类
 * @description:
 * @author sunfs
 * @mail:sunfs@inspur.com
 * @date:2012-11-30
 */
public class HsfUtil {
	private static Log log = LogFactory.getLog(HsfUtil.class);
	/**
	 * 取得高速服务的实例
	 * @param serviceName 高速服务名称，在v6中是服务的接口类
	 * @return 高速服务实例<br>
	 * @例子：<br>
	 * 配置文件中配置如下高速服务：<br>
	  &lt;bean id="v6cacheclient.netty" class="com.inspur.hsf.config.spring.proxy.SpringProviderBean"><br>
	 		&lt;property name="interfaceName">&lt;value>com.v6.base.cache.ICacheClientService&lt;/value>&lt;/property><br>
	 		&lt;property name="target">&lt;ref bean="v6CacheClientService"/>&lt;/property><br>
	  &lt;/bean><br>
	 *Java代码中调用方式如下：<br>
	 *ICacheClientService cacheClientService=(ICacheClientService)HsfUtil.getHsfInstance("com.v6.base.cache.ICacheClientService");<br>
	 *cacheClientService.clearCache();<br>
	 */
	public static Object getHsfInstance(String  serviceName){
		ServiceConfig config = new ServiceConfig();
		config.setServiceName(serviceName);
		ServiceReference reference = new ServiceReference(config);
		return reference.get(serviceName);
	}
	
	/**
	 * 调用高速服务
	 * @param serviceName 高速服务名称，在v6中是服务的接口类
	 * @param methodName 方法名
	 * @param args 参数
	 * @return 高速服务执行结果<br>
	 * @例子：<br>
	 * 配置文件中配置如下高速服务：<br>
	  &lt;bean id="v6cacheclient.netty" class="com.inspur.hsf.config.spring.proxy.SpringProviderBean"><br>
	 		&lt;property name="interfaceName">&lt;value>com.v6.base.cache.ICacheClientService&lt;/value>&lt;/property><br>
	 		&lt;property name="target">&lt;ref bean="v6CacheClientService"/>&lt;/property><br>
	  &lt;/bean><br>
	 *Java代码中调用方式如下：<br>
	 *HsfUtil.invokeHsfService("com.v6.base.cache.ICacheClientService","clearCache",null);<br>
	 * @throws Throwable 
	 *
	 */
	public static Object invokeHsfService(String  serviceName,String methodName,Object[] args) throws Throwable{
		ServiceConfig serviceConfig = new ServiceConfig();
		serviceConfig.setServiceName(serviceName);
		ServiceReference reference = new ServiceReference(serviceConfig);
		return reference.invoke(methodName, args);
		
	}
	public static void main(String[] args) {
		
	}

}
