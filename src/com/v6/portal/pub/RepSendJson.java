package com.v6.portal.pub;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;


public class RepSendJson {
	
	public  static void sendJson(Object obj,HttpServletResponse rep){
		
		String jsonString =JsonUtil.object2json(obj);
		
		//rep.setContentType("text/plain");
		try {
			PrintWriter out = rep.getWriter();
			out.write(jsonString);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
public  static void sendJson(Object obj,HttpServletResponse rep,String encoding){
		
		String jsonString =JsonUtil.object2json(obj);
		
		//rep.setContentType("text/plain");
		try {
			
			rep.setContentType("application/json;charset="+encoding); 
			rep.setCharacterEncoding(encoding); 
			 PrintWriter out = rep.getWriter();
			out.write(jsonString);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
