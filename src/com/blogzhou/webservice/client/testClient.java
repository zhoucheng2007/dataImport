package com.blogzhou.webservice.client;

public class testClient {

	public static void main(String[] args) {
		UserService_Service uss=new UserService_Service();
		UserService userService=uss.getUserServicePort();
		System.out.println(userService.getMenus("admin"));
	}

}
