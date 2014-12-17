package com.blogzhou.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class HelloWorldController implements Controller{

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			System.out.println("---------------is here-----------");           
	        Map<String,Object> map = new HashMap<String,Object>();  
	        map.put("1", "sdf");  
	        map.put("2", "234324");            
	        return new ModelAndView("/welcome","map",map);//传递map参数 
	}

}
