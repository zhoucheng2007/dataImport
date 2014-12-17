package test.hessian.client;

import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;

public class HessianTest {
	public static void main(String[] args) throws MalformedURLException {
        String url = "http://localhost:8187/dataImport/hessianService";  
        
        HessianProxyFactory factory = new HessianProxyFactory();  
        BasicAPI basic = (BasicAPI)factory.create(BasicAPI.class, url);  
        System.out.println(basic.sayHello("John")); 
	}
}
