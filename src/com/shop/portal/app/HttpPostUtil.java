package com.shop.portal.app;

import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;


public class HttpPostUtil {
	public static String post(HttpClient httpClient, String url, NameValuePair[] data) {

		PostMethod post = null;
		GetMethod get = null;
		String rtn = null;
		try{
			byte[] responseBody = null;
			post = new PostMethod(url);
//			post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8"); 

			post.setRequestBody(data);
			int statusCode = httpClient.executeMethod(post);
			if (isRedirect(statusCode)) {
				Header header = post.getResponseHeader("location");
				String location = header.getValue();
				if(location == null || location.equals("")){
					location = "/";
				}
				get = new GetMethod(location);
				httpClient.executeMethod(get);
				responseBody = get.getResponseBody();
			}else{
				responseBody = post.getResponseBody();
			}
			rtn = new String(responseBody,"UTF-8");
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			if(post != null)
				post.releaseConnection();
			if(get != null)
				get.releaseConnection();
		}
		return rtn;
	}
	private static boolean isRedirect(int statusCode){
		if(statusCode == HttpStatus.SC_MOVED_PERMANENTLY
				|| statusCode == HttpStatus.SC_MOVED_TEMPORARILY
				|| statusCode == HttpStatus.SC_SEE_OTHER
				|| statusCode == HttpStatus.SC_TEMPORARY_REDIRECT)
			return true;
		return false;
	}
	public static String postzuo(HttpClient httpClient, String url, NameValuePair[] data) {

		PostMethod post = null;
		GetMethod get = null;
		String rtn = null;
		try{
			byte[] responseBody = null;
			//post = new PostMethod(url);
//			post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8"); 

			//post.setRequestBody(data);
			//post.addRequestHeader("Cookie", headerValue)
			//post.setRequestHeader("content-length", "55");
		//	int statusCode = httpClient.executeMethod(post);
			//if (isRedirect(statusCode)) {
			    
				Header header = post.getResponseHeader("location");
				String location = url;
				if(location == null || location.equals("")){
					location = "/";
				}
				get = new GetMethod(location);
			
				get.setFollowRedirects(true);
				httpClient.executeMethod(get);
				responseBody = get.getResponseBody();
//			}else{
//				responseBody = post.getResponseBody();
//			}
			rtn = new String(responseBody,"UTF-8");
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			if(post != null)
				post.releaseConnection();
			if(get != null)
				get.releaseConnection();
		}
		return rtn;
	}
	
}
