package com.shop.base.check;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.loushang.bsp.service.GetBspInfo;
import org.loushang.bsp.share.organization.OrganFactory;

import com.shop.base.cache.CacheClient;


/**
 * @title:V6配置文件检查工具
 * @description:
 * @author sunfs
 * @mail:sunfs@inspur.com
 * @date:2013-3-23
 */
public class CheckServlet extends HttpServlet{  
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3847271805128526071L;
	
	private static final String OK="ok";
	private static final String ERROR="error";
	private static final String CHECK_INFO="check.info"; //检查过程是否出错
	private static final String RTF_JAR_MD5="rtf.jar.md5";
	private static final String LOG4J_INFO="log4j.info";
	private static final String RTF_JAR_INFO = "rtf.jar.info";
	
	private static final String SERVICE_INFO="service.info";
	private static final String SERVICE_REG_HOST="service.reg.host";
	private static final String SERVICE_REG_PORT="service.reg.port";
	private static final String SERVICE_REG_PRODUCT_MODEL="service.reg.product.model";
	private static final String SERVICE_SERVICE_PROTOCOL="service.service.protocol";
	private static final String SERVICE_SERVICE_HOST="service.host";
	private static final String SERVICE_SERVICE_PORT="service.port";
	
	private static final String SAML_INFO="saml.info";
	private static final String IDP_MD5="saml.idp.md5";
	private static final String IDP_HOST="saml.idp.host";
	private static final String SP_HOST="saml.sp.host";
	
	private static final String  INVOKE_URL_INFO="invoke.url.info";
	private static final String  INVOKE_URL_RESULT="invoke.url.result";
	
	
	
	
	
	//全局的消息容器
	private  Map msgMap = new HashMap();	
	private static Log log = LogFactory.getLog(CheckServlet.class);
	
		
	@Override
	public void service(ServletRequest req, ServletResponse resp)
			throws ServletException, IOException {
		String param = req.getParameter("genparam");
    	//如果param为空，代表请求是从界面入口传入，遍历各个应用，进行检查，并输出文本信息
    	if(param==null){
    		PrintWriter out = resp.getWriter();	
    		
    		out.println("<html>\r\n");
    		out.println("<head>\r\n");
    		out.println("<title>V6平台检查工具</title>\r\n");
    		out.println("</head>\r\n");
    		out.println("<script language=\"javascript\">\r\n");
    		out.println("	function showMessage(id){\r\n");
    		out.println("		var message = document.getElementById(id);\r\n");
    		out.println("		if(message.style.display == \"block\")\r\n");
    		out.println("			message.style.display = \"none\";\r\n");
    		out.println("		else\r\n");
    		out.println("			message.style.display = \"block\";\r\n");
    		out.println("	}\r\n");
    		out.println("</script>\r\n");
    		
	        out.println("<body>\r\n");  
	        out.println("<h3>使用说明：点击组件标题可以查看详细的检查结果</h3>\r\n");
    		
	        
    		msgMap.clear();
    		Map map = new HashMap();
        	InputStream in =getClass().getResourceAsStream("/check.properties");
        	Properties prop = new Properties();
        	try {
    			prop.load(in);
    			String apache_url= prop.getProperty("apache_url");
    			if(apache_url.endsWith("/")) apache_url=apache_url+"/";
    			String srr_url= prop.getProperty("srr_url");
    			String bspc_url= prop.getProperty("bspc_url");
    			String bsps_url= prop.getProperty("bsps_url");
    			String v6c_urls= prop.getProperty("v6c_urls");
    			String v6s_urls= prop.getProperty("v6s_urls");
    			
    			in.close();
    			
    			//检查apache是否启动
    			Map apacheMap=invokeUrl(apache_url);
    			printApache(out,apacheMap);
    			//检查srr信息
    			Map srrMap = getAppInfo(srr_url+"/check?genparam=internal");
    			printSrr(out, srrMap);
    			
    			//检查bsp前端
				Map bspcMap = getAppInfo(bspc_url+"/check?genparam=internal");
    			printBspc(out, bspcMap,srrMap);
    			
    			
    			
    			Map bspsMap = getAppInfo(bsps_url+"/check?genparam=internal");
    			printComponent(out, bspsMap,srrMap,"bsps","bsp后端：");	
    			
    			
    			String[] urlcs =v6c_urls.split(",");
    			checUrls(out, apache_url, srrMap, urlcs);
    			
    			checUrls(out, apache_url, srrMap, v6s_urls.split("/"));
    			
    			
        	}
        	catch(Exception e){
        		out.println("检查工具入口类出错！");
        		e.printStackTrace(out);
        	}
        	
    		
    		
    		
	    	 
	       
//	        //检查rtf.jar
//	    	printRtf(out);
//	    	//检查日志
//	    	checkLog(out);
//	    	//检查高速服务配置service.properties	    	
//	    	checkHsfConfig(out);
	    	//检查bsp.properties
	    	
	          
	        out.println("</body>\r\n");  
	        out.println("<html>\r\n");  
	        out.close();
    	}// end if(param==null){
    	//如果param不为空，则表示程序传入，检查本应用，并输出格式化信息
    	else if (param.equals("internal")){
    		PrintWriter out= resp.getWriter();
			Map map =checkApp();
			out.print(MapUtils.MapToString(map));	    	
    	}
    	//清除V6缓存入口
    	else if(param.equals("clearv6cache")){
    		PrintWriter out = resp.getWriter();	
    		InputStream in =getClass().getResourceAsStream("/check.properties");
        	Properties prop = new Properties();
        	try {
    			prop.load(in);
    			String apache_url= prop.getProperty("apache_url");
    			if(!apache_url.endsWith("/")) apache_url = apache_url+"/";
    			String v6c_urls= prop.getProperty("v6c_urls");
    			String[] urls =v6c_urls.split(",");
    			for (int i = 0; i < urls.length; i++) {
					String url  = urls[i];
					if(!url.startsWith("http://")){
						url=apache_url+url;
					}
					url = url+"/genparam=clearv6cacheinternal";
					Map  urlMap = invokeUrl(url);
					String info=(String)urlMap.get(INVOKE_URL_RESULT);
					String strResult = (String)urlMap.get(INVOKE_URL_RESULT);
					if(OK.equals(info)){
						out.println(url+"清除缓存成功");
					}
					else{
						out.println("清除缓存时出现错误"+url+"\r\n"+strResult);
					}
					                 
				}
        	}
        	catch (Exception e){
        		out.println("清除缓存时出现错误!");
        		e.printStackTrace(out);
        		
        	}
    	}
    	//清除缓存的实际执行类
    	else if(param.equals("clearv6cacheinternal")){
    		CacheClient.clearCache();
    	}
	}

	private void checUrls(PrintWriter out, String apache_url, Map srrMap,
			String[] urlcs) {
		for (int i = 0; i < urlcs.length; i++) {
			String urlId="";
			String url  = urlcs[i];
			if(!url.startsWith("http://")){
				urlId=url;
				url=apache_url+url;
				
			}
			else{
				String[] ids = url.split("/");
				if(ids.length>0) urlId = ids[ids.length-1];
				if(urlId==null) urlId = ids[ids.length-2];
			}
			
			
			Map v6cMap = getAppInfo(url+"/genparam=internal");
			printComponent(out, v6cMap,srrMap,urlId,urlId);	
		}
	}
	
	//检查Srr的信息
	private Map printApache(PrintWriter out, Map map) {
		boolean isOk = true;
		//取srr信息		
		
		
		
		
		out.println("<h1  onclick=\"showMessage('apache')\">Apache: </h1>\r\n");
		out.println("<div style=\"display:none;\" id=\"apache\" >\r\n");
		//out.println("检查的url:"+srr_url+"/check?genparam=internal");
		
		if(!OK.equals(map.get(CHECK_INFO))){
			isOk = false;
			String errorInfo = (String)map.get(INVOKE_URL_RESULT);
			out.println(errorInfo);
		}
		
		
		
		
		out.println("</div>\r\n");
		if(isOk){
			out.println("<h2 onclick=\"showMessage('apache')\">正常！</h2>");
		}
		else{
			out.println("<h2 onclick=\"showMessage('apache')\"><font color=\"red\">存在问题！（点击查看详细信息）</font></h2>");
		}
		
		return map;
	}
	private void printh1(PrintWriter out,String title,String clickId){
		out.println("<h1");
		if(clickId!=null) out.print("  onclick=\"showMessage('"+clickId+"')\"");
		out.print(">"+title+" </h1>\r\n");
	}
	
	private void printh2(PrintWriter out,String title,String clickId){
		out.println("<h2");
		if(clickId!=null) out.print("  onclick=\"showMessage('"+clickId+"')\"");
		out.print(">"+title+" </h2>\r\n");
		
		
	}
	
	private void errorh2(PrintWriter out,String title,String clickId){
		out.println("<h2");
		if(clickId!=null) out.print("  onclick=\"showMessage('"+clickId+"')\"");
		out.print("><font color=\"red\">"+title+" </font></h2>\r\n");
	}
	//检查Srr的信息
	private Map printSrr(PrintWriter out, Map map) {
		boolean isOk = true;
		//取srr信息		
		
		
		if(!OK.equals(map.get(CHECK_INFO))){
			isOk = false;
		}
		
		out.println("<h1  onclick=\"showMessage('srr')\">Srr高速服务: </h1>\r\n");
		out.println("<div style=\"display:none;\" id=\"srr\" >\r\n");
		//out.println("检查的url:"+srr_url+"/check?genparam=internal");
		isOk = printLog(out, isOk, map);
		
		//rtf.jar
		isOk = printRtfJar(out, map, isOk,null);
		//service.properties
		
		isOk = printSeriveProp(out, map, isOk,null);
		
		
		
		out.println("</div>\r\n");
		if(isOk){
			out.println("<h2 onclick=\"showMessage('srr')\">正常！（点击查看详细信息）</h2>");
		}
		else{
			out.println("<h2 onclick=\"showMessage('srr')\"><font color=\"red\">存在问题！（点击查看详细信息）</font></h2>");
		}
		
		return map;
	}

	private boolean printSeriveProp(PrintWriter out, Map map, boolean isOk,Map srrMap) {
		out.println("检查service.properties:<br>");
		String serviceInfo=(String)map.get(SERVICE_INFO);
		if(!OK.equals(serviceInfo)){
			isOk=false;
			out.println("<font color=\"red\">存在问题</p></font>\r\n");
			out.println("<br>");
			out.println(serviceInfo);
			out.println("<br>");
		}
		else{
			out.println(serviceInfo);
			out.println("<br>");
			String regHost = (String)map.get(SERVICE_REG_HOST);
			if(regHost==null || regHost.startsWith("0.0")){
				isOk=false;
				out.println("<font color=\"red\">存在问题</p></font>\r\n");
				out.println("<br>");
				out.println("高速服务未定义服务注册中心！");
				out.println("<br>");
			}
			else{
				if(srrMap==null){
					out.println("正常");
					out.println("<br>");
				}
				else{
					String regPort = (String)map.get(SERVICE_REG_PORT);
					if(regHost.equals(srrMap.get(SERVICE_REG_HOST)) && regPort.equals(srrMap.get(SERVICE_REG_PORT))){
						out.println("正常");
						out.println("<br>");
					}
					else{
						out.println("<font color=\"red\">存在问题</p></font>\r\n");
						out.println("<br>");
						out.println("服务注册中心配置和Srr不一致！");
						out.println("<br>");
					}
				}
				
			}
		}
		return isOk;
	}

	private boolean printRtfJar(PrintWriter out, Map map, boolean isOk,Map srrMap) {
		out.println("检查rtf.jar:<br>");
		String rtfJarMd5=(String)map.get(RTF_JAR_MD5);
		if(!OK.equals(map.get(RTF_JAR_INFO))){
			isOk = false;
			out.println("<font color=\"red\">存在问题</p></font>\r\n");
			out.println("<br>");
			out.println(rtfJarMd5);
			out.println("<br>");
		}
		else{
			if(srrMap==null){
				out.println("正常。md5:");
				out.println(rtfJarMd5);
				out.println("<br>");
			}
			else{
				String v6RtfJarMd5=(String)srrMap.get(RTF_JAR_MD5);
				if(rtfJarMd5.equals(v6RtfJarMd5)){
					out.println("正常。");
					out.println("<br>");
				}
				else{
					isOk=false;
					out.println("<font color=\"red\">rtf.jar与srr中的版本冲突！</font>");
					out.println("<br>");
					
				}
				
			}
			
		}
		return isOk;
	}
	private boolean printLog(PrintWriter out, boolean isOk, Map map) {
		out.println("检查日志：");
		out.println("<br>");
		
		//日志
		String logInfo=(String)map.get(LOG4J_INFO);
		if(OK.equals(logInfo)) {
			out.println("正常</p>\r\n");
			out.println("<br>");
		}
		else{
			isOk =false;
			out.println("<font color=\"red\">存在问题</p></font>\r\n");
			out.println("<br>");
			out.println(logInfo);
			out.println("<br>");
						
		}
		return isOk;
	}
	
	private String getServerIP(String ip) throws UnknownHostException{
		if(ip==null || ip.trim().equals("")) ip="";
		if(ip.equals("localhost")||ip.equals("127.0.0.1")){	        
            InetAddress addr=InetAddress.getLocalHost();  
            ip= addr.getHostAddress();  	          
		}
		return ip;
    }  


	//检查bspc的信息
	private Map printBspc(PrintWriter out, Map map,Map srrMap) {
		//取bsc信息
		boolean isOk = true;
		
		
		if(!OK.equals(map.get(CHECK_INFO))){
			//totalMsg=(String)bspcMap.get(CHECK_INFO);
			isOk=false;
		}
		
		
		out.println("<h1  onclick=\"showMessage('bsp')\">bsp前端:</h1>\r\n");
		out.println("<div style=\"display:none;\" id=\"bsp\" >\r\n");
		//out.println("检查的url:"+bspc_url+"/check?genparam=internal");
		//检查日志
		isOk = printLog(out, isOk, map);
		//rtf.jar
		isOk = printRtfJar(out, map, isOk,srrMap);
		//service.properties
		isOk = printSeriveProp(out, map, isOk,srrMap);
		
		out.println("</div>\r\n");
		if(isOk){
			out.println("<h2 onclick=\"showMessage('bsp')\">正常！（点击查看详细信息）</h2>");
		}
		else{
			out.println("<h2 onclick=\"showMessage('bsp')\"><font color=\"red\">存在问题！（点击查看详细信息）</font></h2>");
		}
		
		return map;
	}
	
	//检查bspc的信息
	private Map printComponent(PrintWriter out, Map map,Map srrMap,String id,String title) {
		//取bsc信息
		boolean isOk = true;
		
		
		if(!OK.equals(map.get(CHECK_INFO))){
			//totalMsg=(String)bspcMap.get(CHECK_INFO);
			isOk=false;
		}
		
		
		out.println("<h1  onclick=\"showMessage('"+id+"')\">"+title+"</h1>\r\n");
		out.println("<div style=\"display:none;\" id=\""+id+"\" >\r\n");
		//out.println("检查的url:"+bspc_url+"/check?genparam=internal");
		//检查日志
		isOk = printLog(out, isOk, map);
		//rtf.jar
		isOk = printRtfJar(out, map, isOk,srrMap);
		//service.properties
		isOk = printSeriveProp(out, map, isOk,srrMap);
		
		out.println("</div>\r\n");
		if(isOk){
			out.println("<h2 onclick=\"showMessage('"+id+"')\">正常！（点击查看详细信息）</h2>");
		}
		else{
			out.println("<h2 onclick=\"showMessage('"+id+"')\"><font color=\"red\">存在问题！（点击查看详细信息）</font></h2>");
		}
		
		return map;
	}
	
    
    //检查本应用的信息
    private Map checkApp(){
    	boolean isOk = true;
    	Map map = new HashMap();
    	Map checkLogMap = checkLog();
    	if(!OK.equals(checkLogMap.get(LOG4J_INFO))) isOk = false;
		map.putAll(checkLogMap);
    	Map rtfJarMap = checkRtfJar();
    	if(!OK.equals(rtfJarMap.get(RTF_JAR_INFO))) isOk = false;
		map.putAll(rtfJarMap);
		Map servicePropMap = checkServiceProp();
    	if(!OK.equals(rtfJarMap.get(SERVICE_INFO))) isOk = false;
		map.putAll(servicePropMap);
		
		if(isOk) map.put(CHECK_INFO,OK);
    	return map;
    }
    //检查本应用于的rtf.jar信息
    private Map checkRtfJar(){
    	boolean isOk = true;
    	 Map map = new HashMap();
    	 
    	String md5;
		try {
			InputStream in =getServletContext().getResourceAsStream("WEB-INF/lib/rtf.jar");
			md5 = getFileMD5(in);
			map.put(RTF_JAR_MD5,md5);
		} catch (NoSuchAlgorithmException e) {
			isOk = false;
			map.put(RTF_JAR_MD5, e.getStackTrace().toString());
			//e.printStackTrace();
		} catch (IOException e) {
			isOk = false;
			map.put(RTF_JAR_MD5, e.getStackTrace().toString());
		}
 		    	
		if(isOk) map.put(RTF_JAR_INFO, OK);
    	return map;
    }
    
  //检查本应用的service.properties信息
    private Map checkServiceProp(){
    	boolean isOk = true;
    	Map map = new HashMap();
    	InputStream serviceIn = getClass().getResourceAsStream("/service.properties");
		Properties serviceProp = new Properties();
		try {
			serviceProp.load(serviceIn);
			String regHost = serviceProp.getProperty("registry.host");
			if(regHost==null||regHost.trim().equals("")){
				isOk=false;
				map.put(SERVICE_INFO, "未定义srr主机地址！");
				return map;
			}
			map.put(SERVICE_REG_HOST, getServerIP(regHost));
			String regPort = serviceProp.getProperty("registry.port");
			map.put(SERVICE_REG_PORT, regPort);
			
			String serviceHost = serviceProp.getProperty("service.host");
			map.put(SERVICE_SERVICE_HOST, serviceHost);
			
			String servicePort = serviceProp.getProperty("service.port");
			map.put(SERVICE_SERVICE_PORT, servicePort);
			serviceIn.close();
		} catch (IOException e) {
			isOk = false;
			map.put(SERVICE_INFO, e.getStackTrace().toString());
			
		}
    	if(isOk) map.put(SERVICE_INFO, OK);
    	return map;
    }
    
    //检查saml文件
    private Map checkSamlConfig(){
    	boolean isOk = true;
    	Map map = new HashMap();
    	try {
			String fileName =getClass().getResource("/metadata").getFile();
			File dir = new File(fileName);
			if(!dir.exists() && !dir.isDirectory()){
				isOk=false;
				map.put(SAML_INFO,"文件夹不存在！"+fileName);
				return map;
			}
			File[] files =dir.listFiles();
			boolean isIdp = false;
			boolean isSp= false;
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				String name = file.getName().toLowerCase();
				if(!name.endsWith("metadata.xml")) continue;
				InputStream in = new FileInputStream(file);
				String md5 = getFileMD5(in);
				map.put(name, md5);
				//idp file
				if(name.endsWith("idp-metadata.xml")){
					String idpHost =FileTool.searchFile(file.getAbsolutePath(), "Location");
					map.put(IDP_HOST, idpHost);
				}
				//sp文件
				else{
					String spHost =FileTool.searchFile(file.getAbsolutePath(), "Location");
					map.put(SP_HOST, spHost);
				}
				
			}
		} catch (Exception e) {
			isOk=false;
			map.put(SAML_INFO,e.getStackTrace().toString());
		}
    	
    	if(isOk) map.put(SAML_INFO, OK);
    	return map;
    }
    //检查rtf.jar
    private void printRtf(PrintWriter out,Map map){
    	out.println("<head><title>Check rtf.jar</title></head>");  
    	
    	InputStream in =getServletContext().getResourceAsStream("/WEB-INF/lib/rtf.jar");
    	
    	
    	out.println(map.get(RTF_JAR_MD5));
    }
    
    //检查高速服务配置service.properties	    	
	private void checkHsfConfig(PrintWriter out){
		
	}
	
	 //检查modules.properties	    	
	private void checkModulesConfig(PrintWriter out){
		
	}
    
	//检查数据库连接设置	    	
	private void checkDbConfig(PrintWriter out){
		
	}
	
	//检查高速服务实例信息：	    	
	private void checkHsf(PrintWriter out){
		
	}
	//检查saml集成配置：	    	
	private void checkSamlConf(PrintWriter out){
		
	}
    //检查日志
    private Map   checkLog(){
    	 Map map = new HashMap();
    	try {
			log.debug("log debug is ok");
			log.info("log info is ok");
			log.warn("log warn is ok");
			log.error("log error is ok");
			map.put(LOG4J_INFO, OK);
		} catch (Exception e) {
			map.put(LOG4J_INFO,"输出日志时出现错误!\r\n"+e.getStackTrace().toString());

		}
		return map;
    	
    }
    
	/** 
	 * * 获取单个文件的MD5值，用于文件比对 
	 * * @param file 
	 * * @return 
	 * @throws NoSuchAlgorithmException 
	 * @throws IOException 
	 * */
	private String getFileMD5(InputStream in) throws NoSuchAlgorithmException, IOException {

		MessageDigest digest = null;
		byte buffer[] = new byte[1024];
		int len;		
		digest = MessageDigest.getInstance("MD5");
		while ((len = in.read(buffer, 0, 1024)) != -1) {
			digest.update(buffer, 0, len);
		}
		in.close();
		
		BigInteger bigInt = new BigInteger(1, digest.digest());
		return bigInt.toString();
	}
	
	private Map invokeUrl(String url){
		Map map = new HashMap();
		 try {
			// 创建HttpClient实例      
			HttpClient httpclient = new DefaultHttpClient();   
			// 创建Get方法实例      
			HttpGet httpgets = new HttpGet(url);      
   
			HttpResponse httpResponse = httpclient.execute(httpgets);
			
     
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			//请求成功   
			if (statusCode == HttpStatus.SC_OK)  
			{  
			    
				String result = EntityUtils.toString(httpResponse.getEntity());
				map.put(INVOKE_URL_INFO, OK);
				map.put(INVOKE_URL_RESULT, result);
			}  
			//如果请求不成功
			else  
			{  
				map.put(INVOKE_URL_INFO, ERROR);
				map.put(INVOKE_URL_RESULT,"连接无效！错误码："+statusCode+".\r\nurl="+url);
				 
			}
		} catch (Exception e) {
			map.put(INVOKE_URL_INFO, ERROR);
			map.put(INVOKE_URL_RESULT,"调用连接时出现错误！\r\nurl="+url+"\r\n"+e.getStackTrace().toString());
		} 
		return map;
		
	}
	
	/**  
     * 取得应用的检查信息
     * @param args  
     * @throws IOException   
     * @throws ClientProtocolException   
     */  
    private  Map getAppInfo(String url) {   
    	Map map = new HashMap();
		Map  urlMap = invokeUrl(url);
		String info=(String)urlMap.get(INVOKE_URL_RESULT);
		String strResult = (String)urlMap.get(INVOKE_URL_RESULT);
		if(OK.equals(info)){
			if(strResult!=null){
				map = MapUtils.StringToMap(strResult);
				map.put(CHECK_INFO, OK);
				
	        }  
		}
		//如果请求不成功
        else  
        {  
        	map.put(CHECK_INFO, strResult); 
        }  
			
        
        return map;
    }   
    
    

}  
