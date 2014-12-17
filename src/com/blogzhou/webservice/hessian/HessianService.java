package com.blogzhou.webservice.hessian;

public interface HessianService {
	public String sayHello(String username);  
    public HessianModel getHessianModel(String username, String password);
}
