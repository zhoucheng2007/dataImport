package test.hessian.server;

import com.caucho.hessian.server.HessianServlet;

public class BasicService extends HessianServlet implements BasicAPI{

	@Override
	public String sayHello(String name) {
			System.out.println("start");
		 return "Hello : " + name;  
	}

}
