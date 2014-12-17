package com.shop.base.taglib;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.loushang.bsp.service.GetBspInfo;
import org.loushang.waf.ComponentFactory;
import org.loushang.waf.mvc.ConstantsSmart;


/**
 * 功能：<web:screenWidget />标签的实现
 * @author 郑斌
 * @date 2012-12-7 下午1:48:41
 */
public class ScreenWidgetMapping extends TagSupport{
	
	private static Log log = LogFactory.getLog(WidgetListSet.class);

	private ServletRequest request = null;
	private String screenUrl = "";
	private String[] widgetsArr = new String[]{"",""};
	// 创建bean
	private static IUserWidgetDomain userWidgetDomain = (IUserWidgetDomain) ComponentFactory
				.getBean("userWidgetDomain");
	
	/**
	 * 开始标签处理
	 */
	public int doStartTag() throws JspTagException {
		
		log.debug("ScreenWidgetMapping.doStartTag()");
		
		ServletRequest request = getRequest();
		
		//获取当前screen的路径
		screenUrl = (String)request.getAttribute(ConstantsSmart.SCREEN_URL);
		int sIndex = screenUrl.indexOf("/"+ConstantsSmart.SCREEN+"/");
		if(sIndex != -1){
			screenUrl = screenUrl.substring(sIndex+ConstantsSmart.SCREEN.length()+2);
		}
		return this.EVAL_BODY_INCLUDE;
	}

	/**
	 * 结束标签处理
	 */
	public int doEndTag() throws JspTagException {
		log.debug("ScreenWidgetMapping.doEndTag() start");
		String widgetJson = "";
		if(!widgetsArr[1].equals("")){
			//如果设置了本screen对应的widgets
			widgetJson = widgetsArr[1];
		}else if(!widgetsArr[0].equals("")){
			//如果设置了默认的widgets对应
			widgetJson = widgetsArr[0];
		}else{
			//如果默认的与本页面的widget对应均没有设置
			throw new RuntimeException("请设置默认的widget列表（<web:widgets s=”default” w=”[{path:”xx.jsp”,name:”x”},{…},{…}]”/>）！");
		}
		//获取当前用户
		String userId = GetBspInfo.getBspInfo((HttpServletRequest)request).getUserId();
		//获取用户设置的widgets列表
		log.debug("ScreenWidgetMapping.doEndTag() a");
		String userSetWidgets = userWidgetDomain.getUserSetWidgets(userId,screenUrl);
		log.debug("ScreenWidgetMapping.doEndTag() b");
		if(userSetWidgets == null || "".equals(userSetWidgets)){
			userSetWidgets = "{}";
		}
		//组织本页面实际的widgets显示顺序列表
		log.debug("ScreenWidgetMapping.doEndTag() c");
		Map map = getUserWidgetsArr(widgetJson,userSetWidgets);
		log.debug("ScreenWidgetMapping.doEndTag() d");
		String[] userWidgetSeqArr = (String[])map.get(ConstantsWidgets.WIDGET_SEQ_ARR);
		JSONArray selWS = (JSONArray)map.get(ConstantsWidgets.WIDGET_SEQ_SEL);
		JSONArray userWS = (JSONArray)map.get(ConstantsWidgets.WIDGET_USER);
		
		request.setAttribute(ConstantsWidgets.WIDGET_SEQ_SEL,selWS.toString());
		request.setAttribute(ConstantsWidgets.WIDGET_USER,userWS.toString());
		//request.setAttribute(ConstantsWidgets.WIDGET_USER,userSetWidgets.toString());
		request.setAttribute(ConstantsWidgets.WIDGET_SEQ_ARR, userWidgetSeqArr);
		request.setAttribute(ConstantsWidgets.SCREEN_URL, screenUrl);
		
		log.debug("ScreenWidgetMapping.doEndTag() finish");
	  return this.EVAL_PAGE;
	}
	
	/**
	 * 功能：根据用户自定义widget及页面可选widget列表，组织页面widget显示顺序数组
	 *     方法中新建了较多的对象，主要是为了提供大量widgets时的运行性能
	 * @author 郑斌
	 * @date 2012-12-12 上午8:45:15
	 * @param widgetJson	页面标签设置的widget列表  ([{path:"xx1.jsp",name:"x1"},{path:"xx111.jsp",name:"x111"},{path:"tt.jsp",name:"tt"}])
	 * @param selectedWidgets	数据库中保存的用户选择的widget的json串   ({0:xx111.jsp,2:xx1.jsp,3:ZZ.jsp,1:x1.jsp})
	 * @return
	 */
	private Map getUserWidgetsArr(String widgetJson,String selectedWidgets){
		log.debug("ScreenWidgetMapping.getUserWidgetsArr() 1");
		Map rtnMap = new HashMap();
		//可选的Map
		Map<String,Integer> allMap = new HashMap();
		List<String> allList = new LinkedList();
		//存储widget路径与名称的对应
		Map<String,String> allPMMap = new HashMap();
		Map<String,String> usaMap = new HashMap();
		//组织好的widgets顺序数组
		String[] widgetSeqArr = null;
		log.debug("ScreenWidgetMapping.getUserWidgetsArr() 2");
		try {
			log.debug("ScreenWidgetMapping.getUserWidgetsArr() 2 1");
			JSONArray allJa=null;
			try {
				log.debug("widgetJson:"+widgetJson);
				allJa = new JSONArray(widgetJson);
			} catch (Exception e) {
				System.err.println("131test:"+e.getMessage());
				log.debug(e);
				log.debug("",e);
			}
			log.debug("ScreenWidgetMapping.getUserWidgetsArr() 2 2");
			widgetSeqArr = new String[allJa.length()];
			log.debug("ScreenWidgetMapping.getUserWidgetsArr() 2 3");
			JSONObject tmpJo = null;
			log.debug("ScreenWidgetMapping.getUserWidgetsArr() 2 4");
			//初始化allMap、allList、allPMMap
			for(int i = 0; i < allJa.length(); i++){
				log.debug("ScreenWidgetMapping.getUserWidgetsArr() 2 5");
				tmpJo = allJa.getJSONObject(i);
				String widgetPath = tmpJo.getString("path");
				String widgetName = tmpJo.getString("name");
				allMap.put(widgetPath,i);
				allList.add(widgetPath);
				allPMMap.put(widgetPath, widgetName);
			}
			log.debug("ScreenWidgetMapping.getUserWidgetsArr() 3");
			//处理用户已选
			JSONObject jo = new JSONObject(selectedWidgets);
			Iterator it = jo.keys();
			Set<Integer> removeIndex = new HashSet();
			//首先循环处理，用户已选widget，将已选放入相应位置
			log.debug("ScreenWidgetMapping.getUserWidgetsArr() 4");
			while(it.hasNext()){
				log.debug("ScreenWidgetMapping.getUserWidgetsArr() 5");
				String tmpKey = (String)it.next();
				String tmpPath = jo.getString(tmpKey);
				
				//如果用户所选widget是有效的
				if(allMap.containsKey(tmpPath)){ 
					int index = Integer.parseInt(tmpKey);
					if(index < allJa.length()){
						//按位置插入
						widgetSeqArr[index] = tmpPath;
						usaMap.put(tmpPath, tmpKey);
						//usaSet.add(tmpPath);
						//需要剔除的widget下标
						removeIndex.add(allMap.get(tmpPath));
					}
				}
			}
			log.debug("ScreenWidgetMapping.getUserWidgetsArr() 6");
			//将已选择的widget剔除掉，形成新的widgetList，用这个List来填充页面widget显示顺序数组
			List<String> newList = new LinkedList();
			for(int i = 0; i < allList.size(); i++){
				if(!removeIndex.contains(i)){
					newList.add(allList.get(i));
				}
			}
			log.debug("ScreenWidgetMapping.getUserWidgetsArr() 7");
			
			int allSeq = 0;
			//循环widgetSeqArr，将其填充完全
			for(int i = 0;i < widgetSeqArr.length; i++){
				if(widgetSeqArr[i]==null){
					widgetSeqArr[i] = newList.get(allSeq);
					allSeq++;
				}
			}
			log.debug("ScreenWidgetMapping.getUserWidgetsArr() 8");
			rtnMap.put(ConstantsWidgets.WIDGET_SEQ_ARR, widgetSeqArr);
			//所有可选widget的json数组
			JSONArray rtnAll = new JSONArray("[]");
			//用户已选widget的json数组
			JSONArray rtnUser = new JSONArray("[]");
			JSONObject tmpObj = null;
			log.debug("ScreenWidgetMapping.getUserWidgetsArr() 9");
			//循环组织好的widgetSeqArr，组织同时传往页面的json字符串
			for(int i = 0;i < widgetSeqArr.length; i++){
				tmpObj = new JSONObject("{}");
				tmpObj.put("path",widgetSeqArr[i]);
				if(usaMap.containsKey(widgetSeqArr[i])){
					tmpObj.put("seq", usaMap.get(widgetSeqArr[i]));
					rtnUser.put(tmpObj);
				}
				tmpObj.put("name",allPMMap.get(widgetSeqArr[i]));
				rtnAll.put(tmpObj);
			}
			log.debug("ScreenWidgetMapping.getUserWidgetsArr() 10");
			rtnMap.put(ConstantsWidgets.WIDGET_SEQ_SEL, rtnAll);
			rtnMap.put(ConstantsWidgets.WIDGET_USER, rtnUser);
		} catch (Exception e) {
			log.debug("xxxxx",e);
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			throw new RuntimeException("请将标签（<web:widgets/>）的w值设置为正确的json数组！");
		}
		
		return rtnMap;
	}
	
//	/**
//	 * 功能：按显示顺序组装widget数组
//	 * 
//	 * @author 郑斌
//	 * @date 2012-12-7 下午5:28:07
//	 * @param widgetJson	页面标签设置的widget列表  ([{path:"xx1.jsp",name:"x1"},{path:"xx111.jsp",name:"x111"},{path:"tt.jsp",name:"tt"}])
//	 * @param selectedWidgets	数据库中保存的用户选择的widget路径的json串  ([xx111.jsp,x1.jsp,xx1.jsp,ZZ.JSP])
//	 * @return
//	 */
//	private String[] getUserWidgetsArr(String widgetJson,String selectedWidgets){
//		System.out.println("getUserWidgetsArr - start");
//		//可选的set
//		Set<String> allSet = new HashSet();
//		//用户已选set
//		Set userSet = new HashSet();
//		//组织好的widgets顺序数组
//		String[] widgetSeqArr = null;
//		try {
//			JSONArray allJa = new JSONArray(widgetJson);
//			JSONObject jo = null;
//			//初始化allSet
//			for(int i = 0; i < allJa.length(); i++){
//				jo = allJa.getJSONObject(i);
//				String widgetPath = jo.getString("path");
//				allSet.add(widgetPath);
//			}
//			System.out.println("==allSet:"+allSet);
//			//初始化userSet
//			JSONArray userJa = new JSONArray(selectedWidgets);
//			//组织好的widget顺序数组
//			widgetSeqArr = new String[allSet.size()];
//			int seq = 0;
//			//处理用户所选widgets
//			for(int i = 0; i < userJa.length(); i++){
//				String widgetPath = userJa.getString(i);
//				if(allSet.contains(widgetPath)){
//					widgetSeqArr[seq] = widgetPath;
//					seq++;
//					//记录已加入数组的widget
//					userSet.add(widgetPath);
//				}
//			}
//			System.out.println("==userSet:"+userSet);
//			//拼接其余的widgets
//			for(int i = 0; i < allJa.length(); i++){
//				jo = allJa.getJSONObject(i);
//				String widgetPath = jo.getString("path");
//				if(!userSet.contains(widgetPath)){
//					widgetSeqArr[seq] = widgetPath;
//					seq++;
//				}
//			}
//			System.out.println("ok!");
//		} catch (JSONException e) {
//			e.printStackTrace();
//			throw new RuntimeException("请将标签（<web:widgets/>）的w值设置为正确的json数组！");
//		}
//		System.out.println("getUserWidgetsArr - stop");
//		return widgetSeqArr;
//	}

	/**
	 * 功能：获取request对象，供子标签调用
	 * @author 郑斌
	 * @date 2012-12-7 下午2:29:12
	 * @return
	 */
	public ServletRequest getRequest(){
		request = pageContext.getRequest();
		return request;
	}
	
	/**
	 * 功能：获取screen路径，供子标签调用
	 * @author 郑斌
	 * @date 2012-12-7 下午2:29:12
	 * @return
	 */
	public String getScreenUrl(){
		return screenUrl;
	}
	
	/**
	 * 功能：设置默认对应的widgets
	 * @author 郑斌
	 * @date 2012-12-7 下午2:37:57
	 * @param widgets
	 */
	public void setDefaultWidgets(String widgets){
		widgetsArr[0] = widgets;
	}
	
	/**
	 * 功能：设置本screen页对应的widgets
	 * @author 郑斌
	 * @date 2012-12-7 下午2:38:16
	 * @param widgets
	 */
	public void setSelfWidgets(String widgets){
		widgetsArr[1] = widgets;
	}
}
