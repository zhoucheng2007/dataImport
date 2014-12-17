package com.shop.base.gis;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.axis.encoding.Base64;
import org.loushang.util.IErrorHandler;
import org.loushang.util.IMessageHandler;
import org.loushang.waf.mvc.ViewHelper;

import com.shop.base.autocomplete.tools.AutoCompleteTools;
import com.shop.base.bsp.V6BspInfo;
import com.shop.base.cmd.BaseCommandImpl;
import com.shop.base.rule.RuleUtil;

public class GisWebserviceCall extends BaseCommandImpl {
	
	private static Log log = LogFactory.getLog(GisWebserviceCall.class);
	
	/**
	 * 查询图形
	 * 
	 * @param req
	 * @param rep
	 * @param errorHandler
	 * @param messageHandler
	 * @param viewHelper
	 * @return
	 */
	public String queryGeometry(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper) {
		if(log.isDebugEnabled()){
			log.debug("GisWebserviceCall---queryGeometry---start");
		}
		Map jsonMap = new HashMap();
		String objId = req.getParameter("objId");
		String layerType = req.getParameter("layerType");//point 点,polyline 线,polygon 面
		String layerName = req.getParameter("layerName");
		SimpleDateFormat responsedftime = new SimpleDateFormat("yyyyMMddHHmmss");
		String currTime = responsedftime.format(new Date());
		String userId = V6BspInfo.getUserId();
		String comId = V6BspInfo.getComId();
		String where = " OBJ_ID IN ( "+objId+" ) ";
				
		jsonMap.put("layerName", layerName);
		jsonMap.put("layerType", layerType);
		jsonMap.put("operateType", "query");
		jsonMap.put("time", currTime);
		jsonMap.put("userId", userId);
		jsonMap.put("where",where);
		jsonMap.put("returnGeometry","true");
		
		String jsonStr = AutoCompleteTools.map2json(jsonMap);
		log.debug("jsonStr="+jsonStr);
		try {
			RPCServiceClient serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			String gisAppAddr = RuleUtil.getRuleValue("GIS_APP_ADDR",comId);
			if(gisAppAddr==null||"".equals(gisAppAddr)){
				gisAppAddr = RuleUtil.getRuleValue("GIS_APP_ADDR","00");
			}
			log.debug("gisAppAddr="+gisAppAddr);
			EndpointReference targetEPR = new EndpointReference(
	        		gisAppAddr+"/gis/services/FeatureIDUQService");
	        options.setTo(targetEPR);
	        //第二个参数为方法名
	        QName namespace = new QName("http://loushang.ws", "doLargeQuery");
	        //方法的参数值集合 
	        Object[] param = new Object[] {jsonStr};
	        //参数类型集合
	        Class[] paramType = new Class[] { String.class };
	        Object[] b = serviceClient.invokeBlocking(namespace, param, paramType);
	        log.debug("queryResult="+(String)b[0]);       
	        rep.setContentType("application/json");
		    PrintWriter out = rep.getWriter();
		    out.write((String)b[0]);
		    out.close();		    
		}catch (Exception e) {
			// TODO Auto-generated catch block
			if(log.isInfoEnabled()){
	    		log.info("查询图形出错！e.printStackTrace()="+e);
	        }
			e.printStackTrace();
		}
	
		return null;
	}
	
	/**
	 * 凸包计算
	 * 
	 * @param req
	 * @param rep
	 * @param errorHandler
	 * @param messageHandler
	 * @param viewHelper
	 * @return
	 */
	public String calculateGeometry(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper) {
		if(log.isDebugEnabled()){
			log.debug("GisWebserviceCall---calculateGeometry---start");
		}
		String jsonStr = req.getParameter("json");
		String comId = V6BspInfo.getComId();
		log.debug("jsonStr="+jsonStr);
		try {
			RPCServiceClient serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			String gisAppAddr = RuleUtil.getRuleValue("GIS_APP_ADDR",comId);
			if(gisAppAddr==null||"".equals(gisAppAddr)){
				gisAppAddr = RuleUtil.getRuleValue("GIS_APP_ADDR","00");
			}
			log.debug("gisAppAddr="+gisAppAddr);
			EndpointReference targetEPR = new EndpointReference(
	        		gisAppAddr+"/gis/services/ConvexHullService");
	        options.setTo(targetEPR);
	        //第二个参数为方法名
	        QName namespace = new QName("http://loushang.ws", "convexHull");
	        //方法的参数值集合 
	        Object[] param = new Object[] {jsonStr};
	        //参数类型集合
	        Class[] paramType = new Class[] { String.class };
	        Object[] b = serviceClient.invokeBlocking(namespace, param, paramType);
	        log.debug("calculateGeometryResult="+(String)b[0]);       
	        rep.setContentType("application/json");
		    PrintWriter out = rep.getWriter();
		    out.write((String)b[0]);
		    out.close();		    
		}catch (Exception e) {
			// TODO Auto-generated catch block
			if(log.isInfoEnabled()){
	    		log.info("凸包计算生成图形出错！e.printStackTrace()="+e);
	        }
			e.printStackTrace();
		}
	
		return null;
	}
	/**
	 * 保存图形
	 * 
	 * @param req
	 * @param rep
	 * @param errorHandler
	 * @param messageHandler
	 * @param viewHelper
	 * @return
	 */
	public String saveGeometry(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper) {
		if(log.isDebugEnabled()){
			log.debug("GisWebserviceCall---saveGeometry---start");
		}
		Map jsonMap = new HashMap();
		SimpleDateFormat responsedftime = new SimpleDateFormat("yyyyMMddHHmmss");
		String currTime = responsedftime.format(new Date());
		String graJson = req.getParameter("graJson");
		String objId = req.getParameter("objId");
		String objName = req.getParameter("objName");
		String layerType = req.getParameter("layerType");
		String layerName = req.getParameter("layerName");
		String opt = req.getParameter("opt");
		String comId = V6BspInfo.getComId();
		String userId = V6BspInfo.getUserId();
		log.debug("graJson="+graJson);		
		try {
			JSONObject obj = new JSONObject(graJson);
			JSONObject geometry = obj.getJSONObject("geometry");
			JSONArray rings = geometry.getJSONArray("rings");
			String[] str = new String[rings.length()];
			List infoList = new ArrayList();				
			for(int i=0;i<rings.length();i++){
				Map geometryMap = new HashMap();
				JSONArray lineList = (JSONArray)rings.get(i);
				str[i] = "";
				for(int j=0;j<lineList.length();j++){
					String x = ((JSONArray)lineList.get(j)).get(0).toString();
					String y = ((JSONArray)lineList.get(j)).get(1).toString();
					String point = x+","+y+";";
					str[i] += point;
				}
				str[i] = str[i].substring(0, str[i].length()-1);
				log.debug("str("+i+")="+str[i]);
				geometryMap.put("POINTS", str[i]);
				geometryMap.put("OBJ_ID", objId);
				geometryMap.put("OBJ_CODE", objId);
				geometryMap.put("OBJ_NAME", objName);
				geometryMap.put("COM_ID", comId);		
				geometryMap.put("UPDATETIME", currTime);
				infoList.add(geometryMap);
			}
			
			jsonMap.put("layerName", layerName);
			jsonMap.put("layerType", layerType);
			if("0".equals(opt)){
				jsonMap.put("operateType", "insert");
			}else{
				jsonMap.put("operateType", "update");
			}
			jsonMap.put("time", currTime);
			jsonMap.put("userId", userId);
			jsonMap.put("infoList",infoList);
			
			String jsonStr = AutoCompleteTools.map2json(jsonMap);
			log.debug("jsonStr="+jsonStr);
			
			RPCServiceClient serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			String gisAppAddr = RuleUtil.getRuleValue("GIS_APP_ADDR",comId);
			if(gisAppAddr==null||"".equals(gisAppAddr)){
				gisAppAddr = RuleUtil.getRuleValue("GIS_APP_ADDR","00");
			}
			log.debug("gisAppAddr="+gisAppAddr);
			EndpointReference targetEPR = new EndpointReference(
	        		gisAppAddr+"/gis/services/FeatureIDUQService");
	        options.setTo(targetEPR);
	        //第二个参数为方法名
	        QName namespace = new QName("http://loushang.ws", "doFeatureIDUQ");
	        //方法的参数值集合 
	        Object[] param = new Object[] {jsonStr};
	        //参数类型集合
	        Class[] paramType = new Class[] { String.class };
	        Object[] b = serviceClient.invokeBlocking(namespace, param, paramType);
	        log.debug("saveResult="+(String)b[0]);
	        rep.setContentType("application/json");
		    PrintWriter out = rep.getWriter();
		    out.write((String)b[0]);
		    out.close();		    
		}catch (Exception e) {
			// TODO Auto-generated catch block
			if(log.isInfoEnabled()){
	    		log.info("保存图形出错！e.printStackTrace()="+e);
	        }
			e.printStackTrace();
		}
	
		return null;
	}
	
	/**
	 * 查询零售户
	 * 
	 * @param req
	 * @param rep
	 * @param errorHandler
	 * @param messageHandler
	 * @param viewHelper
	 * @return
	 */
	public String queryCust(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper) {
		if(log.isDebugEnabled()){
			log.debug("GisWebserviceCall---queryCust---start");
		}
		String jsonStr = req.getParameter("custId");
		String comId = V6BspInfo.getComId();
		
		log.debug("jsonStr="+jsonStr);
		try {
			RPCServiceClient serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			String gisAppAddr = RuleUtil.getRuleValue("GIS_APP_ADDR",comId);
			if(gisAppAddr==null||"".equals(gisAppAddr)){
				gisAppAddr = RuleUtil.getRuleValue("GIS_APP_ADDR","00");
			}
			log.debug("gisAppAddr="+gisAppAddr);
			EndpointReference targetEPR = new EndpointReference(
	        		gisAppAddr+"/gis/services/CustLocService");
	        options.setTo(targetEPR);
	        //第二个参数为方法名
	        QName namespace = new QName("http://loushang.ws", "getCustLoc");
	        //方法的参数值集合 
	        Object[] param = new Object[] {jsonStr};
	        //参数类型集合
	        Class[] paramType = new Class[] { String.class };
	        Object[] b = serviceClient.invokeBlocking(namespace, param, paramType);
	        log.debug("queryResult="+(String)b[0]);       
	        rep.setContentType("application/json");
		    PrintWriter out = rep.getWriter();
		    out.write((String)b[0]);
		    out.close();		    
		}catch (Exception e) {
			// TODO Auto-generated catch block
			if(log.isInfoEnabled()){
	    		log.info("查询零售户出错！e.printStackTrace()="+e);
	        }
			e.printStackTrace();
		}
	
		return null;
	}
	
	/**
	 * 零售户空间查询
	 * 
	 * @param req
	 * @param rep
	 * @param errorHandler
	 * @param messageHandler
	 * @param viewHelper
	 * @return
	 */
	public String getCustByBuffer(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper) {
		if(log.isDebugEnabled()){
			log.debug("GisWebserviceCall---queryCust---start");
		}
		Map jsonMap = new HashMap();
		Map gpsEpRequestMap = new HashMap();
		String x = req.getParameter("x");
		String y = req.getParameter("y");
		String d = req.getParameter("distance");
		String needGeometry = req.getParameter("needGeometry");
		String offsetType = req.getParameter("offsetType");
		String mobileType = req.getParameter("mobileType");
		//零售户图层
		String layerName = "GIS_CUST_L";
		String comId = V6BspInfo.getComId();
		
		gpsEpRequestMap.put("x", x);
		gpsEpRequestMap.put("y", y);
		gpsEpRequestMap.put("distance", d);
		gpsEpRequestMap.put("offsetType", offsetType);
		gpsEpRequestMap.put("needGeometry", needGeometry);
		gpsEpRequestMap.put("layerName", layerName);
		gpsEpRequestMap.put("mobileType", mobileType);
		jsonMap.put("gpsEpRequest", gpsEpRequestMap);
		
		String jsonStr = AutoCompleteTools.map2json(jsonMap);
		log.debug("jsonStr="+jsonStr);
		try {
			RPCServiceClient serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			String gisAppAddr = RuleUtil.getRuleValue("GIS_APP_ADDR",comId);
			if(gisAppAddr==null||"".equals(gisAppAddr)){
				gisAppAddr = RuleUtil.getRuleValue("GIS_APP_ADDR","00");
			}
			log.debug("gisAppAddr="+gisAppAddr);
			EndpointReference targetEPR = new EndpointReference(
	        		gisAppAddr+"/gis/services/GetCustByBufferService");
	        options.setTo(targetEPR);
	        //第二个参数为方法名
	        QName namespace = new QName("http://loushang.ws", "getCustIdByBuffer");
	        //方法的参数值集合 
	        Object[] param = new Object[] {jsonStr};
	        //参数类型集合
	        Class[] paramType = new Class[] { String.class };
	        Object[] b = serviceClient.invokeBlocking(namespace, param, paramType);
	        log.debug("queryResult="+(String)b[0]);       
	        rep.setContentType("application/json");
		    PrintWriter out = rep.getWriter();
		    out.write((String)b[0]);
		    out.close();		    
		}catch (Exception e) {
			// TODO Auto-generated catch block
			if(log.isInfoEnabled()){
	    		log.info("零售户空间查询出错！e.printStackTrace()="+e);
	        }
			e.printStackTrace();
		}
	
		return null;
	}
	
	/**
	 * 保存零售户
	 * 
	 * @param req
	 * @param rep
	 * @param errorHandler
	 * @param messageHandler
	 * @param viewHelper
	 * @return
	 */
	public String saveCust(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper) {
		if(log.isDebugEnabled()){
			log.debug("GisWebserviceCall---queryCust---start");
		}
		Map jsonMap = new HashMap();
		List custList = new ArrayList();
		Map custMap = new HashMap();
		Map gpsEpRequestMap = new HashMap();
		String custId = req.getParameter("custId");
		String x = req.getParameter("x");
		String y = req.getParameter("y");
		String offsetType = req.getParameter("offsetType");
		if("0".equals(offsetType)){
			String baiduXY = getBaiduXY("0",Double.parseDouble(x),Double.parseDouble(y));
			String[] netXY = baiduXY.split(",");
			custMap.put("netX", netXY[0]);
			custMap.put("netY", netXY[1]);
		}else{
			String baiduXY = getBaiduXY("2",Double.parseDouble(x),Double.parseDouble(y));
			String[] netXY = baiduXY.split(",");
			custMap.put("netX", netXY[0]);
			custMap.put("netY", netXY[1]);
		}
		custMap.put("custId", custId);
		custMap.put("x", x);
		custMap.put("y", y);
		custMap.put("offsetType", offsetType);
		custList.add(custMap);
		
		String mobileType = req.getParameter("mobileType");
		SimpleDateFormat currentftime = new SimpleDateFormat("yyyyMMddHHmmss");
		String currentTime = currentftime.format(new Date());
		String comId = V6BspInfo.getComId();
		String userId = V6BspInfo.getUserId();
		gpsEpRequestMap.put("time", currentTime);
		gpsEpRequestMap.put("comId", comId);
		gpsEpRequestMap.put("userId", userId);
		gpsEpRequestMap.put("mobileType", mobileType);
		gpsEpRequestMap.put("custList", custList);
		jsonMap.put("gpsEpRequest", gpsEpRequestMap);
		
		String jsonStr = AutoCompleteTools.map2json(jsonMap);
		log.debug("jsonStr="+jsonStr);
		try {
			RPCServiceClient serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			String gisAppAddr = RuleUtil.getRuleValue("GIS_APP_ADDR",comId);
			if(gisAppAddr==null||"".equals(gisAppAddr)){
				gisAppAddr = RuleUtil.getRuleValue("GIS_APP_ADDR","00");
			}
			log.debug("gisAppAddr="+gisAppAddr);
			EndpointReference targetEPR = new EndpointReference(
	        		gisAppAddr+"/gis/services/CollCustLocService");
	        options.setTo(targetEPR);
	        //第二个参数为方法名
	        QName namespace = new QName("http://loushang.ws", "doCollCustLoc");
	        //方法的参数值集合 
	        Object[] param = new Object[] {jsonStr};
	        //参数类型集合
	        Class[] paramType = new Class[] { String.class };
	        Object[] b = serviceClient.invokeBlocking(namespace, param, paramType);
	        log.debug("queryResult="+(String)b[0]);       
	        rep.setContentType("application/json");
		    PrintWriter out = rep.getWriter();
		    out.write((String)b[0]);
		    out.close();		    
		}catch (Exception e) {
			// TODO Auto-generated catch block
			if(log.isInfoEnabled()){
	    		log.info("保存零售户出错！e.printStackTrace()="+e);
	        }
			e.printStackTrace();
		}
	
		return null;
	}
	/**
	 * 
	*简要说明：gps坐标转换为百度坐标(返回经纬度，中间用英文逗号隔开，即：经度,纬度  )
	*@param type 0：将gps坐标转换为百度坐标        2：将goole或高德坐标转换为百度坐标
	*@param x GPS坐标：经度
	*@param y GPS坐标：纬度
	*@return  String  返回百度经纬度坐标
	 */
	public String getBaiduXY(String type,double x,double y){

		String x1 ="";
		String y1 = "";
		String comId = V6BspInfo.getComId();
		//google 坐标转百度链接   //http://api.map.baidu.com/ag/coord/convert?from=2&to=4&x=116.32715863448607&y=39.990912172420714
		//gps坐标的type=0  google坐标的type=2 baidu坐标的type=4
		String path = RuleUtil.getRuleValue("GIS_BAIDU_API",comId);
		if(path==null||"".equals(path)){
			path = RuleUtil.getRuleValue("GIS_BAIDU_API","00");
		}
		log.debug("getBaiduXY-path="+path);
		if(type.equals("0")){//gps
			path = path+"/ag/coord/convert?from=0&to=4&x="+x+"+&y="+y;
		}else if(type.equals("2")){//谷歌
			path = path+"/ag/coord/convert?from=2&to=4&x="+x+"+&y="+y;
		}
		try{
			//使用http请求获取转换结果
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			InputStream inStream = conn.getInputStream();
			
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1){
				outStream.write(buffer, 0, len);
			}
		
			//得到返回的结果
			String res = outStream.toString();
		
			//处理结果
			JSONObject resObj = new JSONObject(res);
			if (resObj!=null && resObj.has("error")){
				String err = String.valueOf(resObj.getInt("error"));
				if ("0".equals(err)){
					//编码转换
					x1 = new String(Base64.decode(resObj.getString("x")));
					y1 = new String(Base64.decode(resObj.getString("y")));
				}
			}
		
		} catch (Exception e){
			log.error("GpsToBaidu.getBaiduXY---e:"+e);
		}
		if(x1.equals("")) {
			return "";
		}else{
			return x1+","+y1;
		}
		
	}
}
