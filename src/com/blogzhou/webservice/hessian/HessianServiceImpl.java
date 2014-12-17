package com.blogzhou.webservice.hessian;

public class HessianServiceImpl implements HessianService{

	@Override
	public String sayHello(String username) {
		return "Hello " + username; 
	}

	@Override
	public HessianModel getHessianModel(String username, String password) {
		return new HessianModel(username, password);  
	}

}
