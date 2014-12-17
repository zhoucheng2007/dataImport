package com.shop.portal.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.shop.base.bsp.V6BspInfo;
import com.shop.base.cmd.BaseCommandImpl;
import com.shop.base.hsf.HsfUtil;

import javax.xml.namespace.QName;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import sun.misc.BASE64Encoder;
/**
 * 功能:本类为应用中心Command
 * 
 * @author 孔繁博
 */
public class AppPageInitCmd extends BaseCommandImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(AppPageInitCmd.class);

	public IAppPageService getAppPageService() {
		return appPageService;
	}

	public void setAppPageService(IAppPageService appPageService) {
		this.appPageService = appPageService;
	}
	private IAppPageService appPageService = null;
	//业务管家
		public void addBusinMsg(HttpServletRequest req, HttpServletResponse rep,
				IErrorHandler errorHandler, IMessageHandler messageHandler,
				ViewHelper viewHelper) {

			GetBspInfo bsp = new GetBspInfo();
			String businId=req.getParameter("business");
			String title=req.getParameter("title");
			String content=req.getParameter("content");
			String msgType=req.getParameter("msgtype");//0：微博，1：私信
			String fromId=bsp.getBspInfo().getUserId();
			String fromName=bsp.getBspInfo().getUserName();
			Map paramMap = new HashMap();
			if(msgType.equals("1")){
				//根据业务取出接收人
				String toId="";
				paramMap.put("BUSINESS_ID", businId);
				List list =V6SqlSessionUtil.getSqlSession()
						.selectList("portalApp.queryBusin", paramMap);//如果是私信 需要touser
		        for(int i=0;i<list.size();i++){
		        	Map listMap=(Map)list.get(i);
		        	toId=(String)listMap.get("USER_ID");
				try {
				HsfUtil.invokeHsfService(
						" Map com.v6.base.message.IMessageService",
						"sendMessage", new Object[] { title,  content,  fromId,  fromName,  toId });
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		    }
			}else{
				
			}
		}
		// 获取业务消息
		public void queryBusimsg(HttpServletRequest req, HttpServletResponse rep,
				IErrorHandler errorHandler, IMessageHandler messageHandler,
				ViewHelper viewHelper) {
			GetBspInfo bsp = new GetBspInfo();
			BspInfo bspInfo = GetBspInfo.getBspInfo();
			String userId =bspInfo.getUserId();
			int msgs = 0;
			try {
				msgs = Integer.valueOf(HsfUtil.invokeHsfService(
						"com.v6.base.putmsg.IBasePutMsg", "getPutMsgNCount",
						new Object[] { userId }).toString());
				JSONObject menuObj = new JSONObject();
				menuObj.put("number", msgs);
				PrintWriter out = rep.getWriter();
				out.write(menuObj.toString());
				out.close();
			} catch (Exception e) {
				log.error("获取业务消息出错",e);
			} catch (Throwable e) {
				log.error("获取业务消息出错",e);
			}
		}
		//获取公司通告
		public void queryComAnnouncement(HttpServletRequest req, HttpServletResponse rep,
				IErrorHandler errorHandler, IMessageHandler messageHandler,
				ViewHelper viewHelper) {
			int annNum = 0;
			try {
				String userId=V6BspInfo.getUserId();
				String comId=V6BspInfo.getComId();
				Map map = new HashMap();
				map.put("USER_ID", userId);
				map.put("COM_ID", comId);
				ServiceConfig address = new ServiceConfig();
				address.setServiceName("com.v6.base.announcementl.IAnnService");
				ServiceReference reference = new ServiceReference(address);	
				annNum = Integer.valueOf((reference.invoke("queryNoReadAnnListSize", new Object[]{map}).toString()));
				JSONObject menuObj = new JSONObject();
				menuObj.put("number", annNum);
				PrintWriter out = rep.getWriter();
				out.write(menuObj.toString());
				out.close();
			} catch (Exception e) {
				log.error("获取公司通告出错",e);
			} catch (Throwable e) {
				log.error("获取公司通告出错",e);
			}
		}
		
		//为用户行为分析采集数据
		public void queryForUba(HttpServletRequest req, HttpServletResponse rep,
				IErrorHandler errorHandler, IMessageHandler messageHandler,
				ViewHelper viewHelper) {		      
			 try {
				String comId=V6BspInfo.getComId();
				String userId=V6BspInfo.getUserId();
				String comUser = userId+"||"+comId;
					  
				PrintWriter out = rep.getWriter();
				out.write( new BASE64Encoder().encode(comUser.getBytes()));
				out.close(); 
			 } catch (Exception e) {
					log.error("获取公司，用户信息失败",e);
			 }
		}
	// 应用中心页面
	public String queryPageInit(HttpServletRequest req,
			HttpServletResponse rep, IErrorHandler errorHandler,
			IMessageHandler messageHandler, ViewHelper viewHelper) {
		req.setAttribute("v6_app_code", req.getParameter("v6_app_code"));
		req.setAttribute("v6_app_type", req.getParameter("v6_app_type"));
		req.setAttribute("v6_app_icon", req.getParameter("v6_app_icon"));
		req.setAttribute("v6_app_text", req.getParameter("v6_app_text"));
		req.setAttribute("v6_app_url", req.getParameter("v6_app_url"));
		req.setAttribute("v6_app_id", req.getParameter("v6_app_id"));
		req.setAttribute("menu_type_id", req.getParameter("menu_type_id"));
	// 青海资金监管切换职位切换菜单
		if (log.isDebugEnabled()) {
			log.debug("v6_app_url" +  req.getParameter("v6_app_url"));
		}
		       String appurl=req.getParameter("v6_app_url");
		       String  appcgs=null;
		       if(appurl!=null&&appurl.indexOf("appcgs")!=-1){
		    	    appcgs=appurl.substring(appurl.indexOf("appcgs=")+7, appurl.length());	
		 
		   	if (log.isDebugEnabled()) {
				log.debug(" appcgs" + appcgs);
			}
				if (appcgs.equals(req.getParameter("v6_app_id"))) {
					GetBspInfo bsp = new GetBspInfo();
					String userId = bsp.getBspInfo().getUserId();
					Map paramMap = new HashMap();
					if (log.isDebugEnabled()) {
						log.debug(" paramMap" + paramMap);
					}
					Map ssomap = V6SqlSessionUtil.getSqlSession()
							.selectOne("AppPortalDomainImpl.queryApp", paramMap);
					String app_url = (String) ssomap.get("APP_URL");
					if (log.isDebugEnabled()) {
						log.debug("======app_url=======" + app_url);
					}
					int num = app_url.indexOf("@:");
					if (num != -1) {
						app_url = app_url.substring(2, app_url.length());
					}
					String[] strs = app_url.split(";");
					String menuXml = "";
					for (int i = 0; i < strs.length; i++) {
						int n = strs[i].indexOf("=");
						String key = strs[i].substring(0, n);
						String value = strs[i].substring(n + 1, strs[i].length());
						if (key.equals("menuxml")) {
							menuXml = value;
						}
					}
					String	endPoint = menuXml.split("\\|")[0].toString();
					String  nameSpace= menuXml.split("\\|")[1].toString();
					String value=null;
					try {
						Object[] param;
						param = new Object[] { (String) ssomap.get("APP_USER_ID") };
						if (log.isDebugEnabled()) {
							log.debug("======param========" + (String) ssomap.get("APP_USER_ID"));
						}
						value =AppPageInitCmd.invoke(endPoint, "getPosition", param,nameSpace);
						if (log.isDebugEnabled()) {
							log.debug("======value========" + value);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						// 根据 xml字符串构造一个 Document 对象
						Document doc = DocumentHelper.parseText(value); // 将字符串转为XML
						Element rootElt = doc.getRootElement(); // 获取根节点DATASETS
						if (log.isDebugEnabled()) {
							log.debug("======rootElt========" + rootElt);
						}
						Iterator iter = rootElt.elementIterator("POSITION"); // 获取根节点下的子节点BACKLOG
						if (log.isDebugEnabled()) {
							log.debug("======iter========" + iter);
						}
						List objList =new ArrayList(); 
						if (log.isDebugEnabled()) {
							log.debug("======objList========" + objList);
						}
						while (iter.hasNext()) {
							Map map = new HashMap();
							Element recordEle = (Element) iter.next();
							if (log.isDebugEnabled()) {
								log.debug("======recordEle========" + recordEle);
							}
							map.put("POSITION_NAME",recordEle.elementTextTrim("POSITION_NAME"));
							map.put("POSITION_VALUE",recordEle.elementTextTrim("POSITION_VALUE"));
							if (log.isDebugEnabled()) {
								log.debug("======map========" + map);
							}
							objList.add(map);
							if (log.isDebugEnabled()) {
								log.debug("======objList========" + objList);
							}
						}
					
						if (log.isDebugEnabled()) {
							log.debug("======list========" + objList);
						}
						req.setAttribute("position", objList);
					} catch (DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						log.debug("======QhPwsMainCmd====queryPageInit===Exception==" + e);
					}
					
				} }
		return null;
	}

	// 获取消息
	public void queryMsg(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper) {
		GetBspInfo bsp = new GetBspInfo();
		BspInfo bspInfo = GetBspInfo.getBspInfo();
		String empOrganId =bspInfo.getEmployeeOrganId();
		int msgs = 0;
		try {
			msgs = Integer.valueOf(HsfUtil.invokeHsfService(
					"com.v6.base.notice.notice.INoticeService", "getMessageCount",
					new Object[] { empOrganId }).toString());
			JSONObject menuObj = new JSONObject();
			menuObj.put("msgs", msgs);

			PrintWriter out = rep.getWriter();
			out.write(menuObj.toString());
			out.close();
		} catch (Exception e) {
			log.error("获取消息出错",e);
		} catch (Throwable e) {
			log.error("获取消息出错",e);
		}
	}

	// 获取待办
	public void queryDaiban(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper) {
		GetBspInfo bsp = new GetBspInfo();
		String userId = bsp.getBspInfo().getUserId();
		int msgs = 0;
		try {
			msgs = Integer.valueOf(HsfUtil.invokeHsfService(
					"org.loushang.bwf.api.bizWfQueryService",
					"getDaiBanTaskCountByUserId", new Object[] { userId })
					.toString());
		} catch (NumberFormatException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (Throwable e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		JSONObject menuObj = new JSONObject();
		try {
			menuObj.put("daiban", msgs);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			PrintWriter out = rep.getWriter();
			out.write(menuObj.toString());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 获取短消息
	public void getShortMsg(HttpServletRequest req, HttpServletResponse rep,
				IErrorHandler errorHandler, IMessageHandler messageHandler,
				ViewHelper viewHelper) {
			GetBspInfo bsp = new GetBspInfo();
			String employeeId = bsp.getBspInfo().getEmployeeOrganId();
			String today=DateTool.getToday();
			String fromTime=today+"000000";
			String toTime=today+"235959";
			List msgList=new ArrayList();
			try {
				msgList = (List)(HsfUtil.invokeHsfService(
						"com.v6.base.mail.IPersonMailService",
						"getNewMails", new Object[] { fromTime,toTime,employeeId,"0","62",9999}));
				
			} catch (NumberFormatException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (Throwable e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			RepSendJson.sendJson(msgList, rep);
	}
	//
	public void setShortMsgReaded(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper) {
		
		GetBspInfo bsp = new GetBspInfo();
		String employeeId = bsp.getBspInfo().getEmployeeOrganId();
		String mailId=(String)req.getParameter("mailId");
		try {
			HsfUtil.invokeHsfService(
					"com.v6.base.mail.IPersonMailService",
					"setReaded", new Object[] {employeeId,mailId});
			
		} catch (NumberFormatException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (Throwable e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			PrintWriter out = rep.getWriter();
			out.write("sucess");
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

	// 获取模块数据
	public void queryModule(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper) {
		String appCode = req.getParameter("appCode");
		Map paramMap = new HashMap();
		paramMap.put("APP_CODE", appCode);
		List list = V6SqlSessionUtil.getSqlSession()
				.selectList("AppPortalDomainImpl.queryModule", paramMap);
		JSONArray jsonArr = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject menuObj = new JSONObject();
			Map map = (Map) list.get(i);
			try {
				menuObj.put("MODULE_CODE", (String) map.get("MODULE_CODE"));
				menuObj.put("MODULE_NAME", (String) map.get("MODULE_NAME"));
				menuObj.put("MODULE_URL", (String) map.get("URI")
						+ (String) map.get("URL"));
			} catch (JSONException e) {
				log.error("获取模块数据出错",e);
			}
			jsonArr.put(menuObj);
		}
		try {
			PrintWriter out = rep.getWriter();
			out.write(jsonArr.toString());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 收藏菜单
	public void insertCollect(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper) {
		// String ssoType = (String) req.getParameter("ssoType");
		GetBspInfo bsp = new GetBspInfo();
		String userId = bsp.getBspInfo().getUserId();
		Map map = new HashMap();
		map.put("USER_ID", userId);
		map.put("MENU_ID", (String) req.getParameter("menuId"));
		map.put("MENU_URL", (String) req.getParameter("menuUrl"));
		map.put("MENU_NAME", (String) req.getParameter("menuName"));
		map.put("APP_TYPE", (String) req.getParameter("appType"));
		// 查询收藏菜单是否已存在
		List list = getAppPageService().isExistCollect(map);
		if (list.size() == 0) {
			int i = getAppPageService().insertCollect(map);
		}
	}

	// 访问记录
	public void insertRecord(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper) {
		// String ssoType = (String) req.getParameter("ssoType");
		GetBspInfo bsp = new GetBspInfo();
		String userId = bsp.getBspInfo().getUserId();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		Map map = new HashMap();
		map.put("USER_ID", userId);
		map.put("MENU_ID", (String) req.getParameter("menuId"));
		map.put("MENU_URL", (String) req.getParameter("menuUrl"));
		map.put("MENU_NAME", (String) req.getParameter("menuName"));
		map.put("APP_TYPE", (String) req.getParameter("appType"));
		map.put("CREATE_TIME", df.format(new Date()));
		int i = getAppPageService().insertRecord(map);
	}

	// 获取菜单数据
	public List queryRecord(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper) {
		GetBspInfo bsp = new GetBspInfo();
		String userId = bsp.getBspInfo().getUserId();
		Map map = new HashMap();
		map.put("USER_ID", userId);
		List list = getAppPageService().selectRecord(map);
		return list;
	}

	// 删除菜单
	public void delCollect(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper) {
		// String ssoType = (String) req.getParameter("ssoType");
		GetBspInfo bsp = new GetBspInfo();
		String userId = bsp.getBspInfo().getUserId();
		Map map = new HashMap();
		map.put("USER_ID", userId);
		map.put("MENU_ID", (String) req.getParameter("menuId"));
		int i = getAppPageService().delCollect(map);
	}

	// 获取菜单数据
	public void queryMenuList(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper) {
		if (log.isDebugEnabled()) {
			log.debug("queryMenuList");
		}
		GetBspInfo bsp = new GetBspInfo();
		String userId = bsp.getBspInfo().getUserId();
		String appCode = req.getParameter("appCode");
		if (log.isDebugEnabled()) {
			log.debug("appCode" + appCode + "userId" + userId);
		}
		Map paramMap = new HashMap();
		paramMap.put("APP_CODE", appCode);
		paramMap.put("USER_ID", userId);
		if (log.isDebugEnabled()) {
			log.debug(" paramMap" + paramMap);
		}
		Map map = V6SqlSessionUtil.getSqlSession()
				.selectOne("AppPortalDomainImpl.queryApp", paramMap);
		String app_url = (String) map.get("APP_URL");
		if (log.isDebugEnabled()) {
			log.debug("======app_url=======" + app_url);
		}
		int num = app_url.indexOf("@:");
		if (num != -1) {
			app_url = app_url.substring(2, app_url.length());
		}
		String[] strs = app_url.split(";");
		String menuXml = "";
		String IsAuthenNew = "";
		String webService = "";
		String isJson="";	
		String encoding="GB2312";
		for (int i = 0; i < strs.length; i++) {
			int n = strs[i].indexOf("=");
			String key = strs[i].substring(0, n);
			String value = strs[i].substring(n + 1, strs[i].length());
			if (key.equals("menuxml")) {
				menuXml = value;
			}
			if (key.equals("IsAuthenNew")) {
				IsAuthenNew = value;
			}
			if (key.equals("isWebService")) {
				webService = value;
			}
			if (key.equals("isJson")) {
				isJson = value;
			}
			if (key.equals("Encoding")) {
				encoding = value;
			}
		}
		String uri = (String) map.get("URI");// http://10.10.10.67/d3
		String type = "1";//loushang3
		String menuJson = "";	

		// 如果是web服务方式
		if (webService.equals("true")) {
			type = "2";
			if (log.isDebugEnabled()) {
				log.debug("======webService=======" + webService);
			}
		}
		// loushang3 loushang5
		if (type.equals("1")) {
			try {
				String myIp = req.getHeader("host");
				if (log.isDebugEnabled()) {
					log.debug("======myIp========" + myIp);
				}
				menuJson = getMenuJsonForLc(userId, menuXml, uri, appCode,
						myIp, IsAuthenNew,isJson,encoding);
			} catch (Exception e) {
				log.error("获取菜单数据出错", e);
			}
		} 
		//web服务方式
		else if (type.equals("2")) {
			// 调用服务代码
			String	endPoint = menuXml.split("\\|")[0].toString();
			String  nameSpace= menuXml.split("\\|")[1].toString();
			String  methodName = menuXml.split("\\|")[2].toString();
			userId = (String) map.get("APP_USER_ID");
			if (log.isDebugEnabled()) {
				log.debug("======userId========" + userId + "==" + nameSpace
						+ "===" + endPoint);
			}
			
			if (log.isDebugEnabled()) {
				log.debug("======app_code========" + appCode);
			}
		String  appcgs=app_url.substring(app_url.indexOf("appcgs=")+7, app_url.length());		
			if(appcgs.equals(appCode)){
			// 为资金监管设置，他们需要当前用户和所属职位，如果第一次没有传职位时获取默认职位菜单---孔繁博20130925
			String positionId =req.getParameter("positionId");
			if(positionId==null){
				positionId="";
			}
			try {
				Object[] param;
				param = new Object[] { userId, positionId };
				menuJson = menuXml2Json(
						AppPageInitCmd.invoke(endPoint, methodName, param, nameSpace),
						uri);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}else{
				try {
					if("true".equals(isJson)){
						menuJson = AppPageInitCmd.invoke(endPoint, methodName, userId, nameSpace);
					}else{
					menuJson = menuXml2Json(
							AppPageInitCmd.invoke(endPoint, methodName, userId, nameSpace),
							uri);}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		// rep.setContentType("text/plain");
		try {
			PrintWriter out = rep.getWriter();
			out.write(menuJson);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}}

	// 获取用户收藏菜单
	public void queryUserCollectMenu(HttpServletRequest req,
			HttpServletResponse rep, IErrorHandler errorHandler,
			IMessageHandler messageHandler, ViewHelper viewHelper) {
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -10);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String tenDayBeforeNow=df.format(cal.getTime());//获取十天之前的日期

		Map map = new HashMap();
		GetBspInfo bsp = new GetBspInfo();
		String userId = bsp.getBspInfo().getUserId();
		map.put("USER_ID", userId);
		map.put("CREATE_TIME", tenDayBeforeNow);	
		List list = (List) getAppPageService().selectCollect(map);
		List listRecord = (List) getAppPageService().selectRecord(map);
		JSONObject menuObj = new JSONObject();
		JSONObject rowsObj = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		JSONObject objHead = new JSONObject();
		try {
			objHead.put("id", "COLLECT");
			objHead.put("text", "我的收藏");
			objHead.put("url", "/");
			objHead.put("isLeaf", "false");
			objHead.put("appType", "");
			objHead.put("childOuter", "false");
			objHead.put("drag", "false");
		} catch (JSONException e1) {
			log.error("出错了",e1);
		}
		JSONArray childArr = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			Map temp = (Map) list.get(i);
			String MENU_ID = (String) temp.get("MENU_ID");
			String MENU_URL = (String) temp.get("MENU_URL");
			String MENU_NAME = (String) temp.get("MENU_NAME");
			String APP_TYPE = (String) temp.get("APP_TYPE");
			JSONObject obj = new JSONObject();
			try {
				obj.put("id", MENU_ID);
				obj.put("text", MENU_NAME);
				obj.put("url", MENU_URL);
				obj.put("isLeaf", "true");
				obj.put("appType", APP_TYPE);
				childArr.put(obj);
			} catch (JSONException e) {
				log.error("出错了",e);
			}
		}
		try {
			objHead.put("children", childArr);
			jsonArr.put(objHead);
			objHead = new JSONObject();
			childArr = new JSONArray();
			try {
				objHead.put("id", "RECORD");
				objHead.put("text", "我的历史");
				objHead.put("url", "/");
				objHead.put("isLeaf", "false");
				objHead.put("appType", "");
				objHead.put("drag", "false");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for (int i = 0; i < listRecord.size(); i++) {
				Map temp = (Map) listRecord.get(i);
				String MENU_ID = (String) temp.get("MENU_ID");
				String MENU_URL = (String) temp.get("MENU_URL");
				String MENU_NAME = (String) temp.get("MENU_NAME");
				String APP_TYPE = (String) temp.get("APP_TYPE");
				JSONObject obj = new JSONObject();
				try {
					obj.put("id", MENU_ID);
					obj.put("text", MENU_NAME);
					obj.put("url", MENU_URL);
					obj.put("isLeaf", "true");
					obj.put("appType", APP_TYPE);
					obj.put("drag", "false");
					childArr.put(obj);
				} catch (JSONException e) {
					log.error("出错了",e);
				}
			}
			objHead.put("children", childArr);
			jsonArr.put(objHead);
			rowsObj.put("rows", jsonArr);
			menuObj.put("menu", rowsObj);

		} catch (JSONException e) {
			log.error("出错了",e);
		}
		try {
			PrintWriter out = rep.getWriter();
			out.write(menuObj.toString());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 获取用户收藏菜单
		public void updateCollectMenuOrder(HttpServletRequest req,
				HttpServletResponse rep, IErrorHandler errorHandler,
				IMessageHandler messageHandler, ViewHelper viewHelper) {
			
			String ids=req.getParameter("ids");
			
			
			GetBspInfo bsp = new GetBspInfo();
			String userId = bsp.getBspInfo().getUserId();
			
			String[] idArr=ids.split(",");
			
			Map map=new HashMap();
			map.put("idArr", idArr);
			map.put("userId", userId);
			
			
			getAppPageService().updateCollectMenuOrder(map);
		}

	// 获取用户使用记录
	public void queryUserRecordMenu(HttpServletRequest req,
			HttpServletResponse rep, IErrorHandler errorHandler,
			IMessageHandler messageHandler, ViewHelper viewHelper) {

		Map map = new HashMap();
		GetBspInfo bsp = new GetBspInfo();
		String userId = bsp.getBspInfo().getUserId();
		map.put("USER_ID", userId);
		List list = (List) getAppPageService().selectRecord(map);
		JSONObject menuObj = new JSONObject();
		JSONObject rowsObj = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			Map temp = (Map) list.get(i);
			String MENU_ID = (String) temp.get("MENU_ID");
			String MENU_URL = (String) temp.get("MENU_URL");
			String MENU_NAME = (String) temp.get("MENU_NAME");
			String APP_TYPE = (String) temp.get("APP_TYPE");
			JSONObject obj = new JSONObject();
			try {
				obj.put("id", MENU_ID);
				obj.put("text", MENU_NAME);
				obj.put("url", MENU_URL);
				obj.put("isLeaf", "true");
				obj.put("appType", APP_TYPE);
				jsonArr.put(obj);
			} catch (JSONException e) {
				log.error("出错了",e);
			}
		}
		try {

			rowsObj.put("rows", jsonArr);
			menuObj.put("menu", rowsObj);

		} catch (JSONException e) {
			log.error("出错了",e);
		}
		try {
			PrintWriter out = rep.getWriter();
			out.write(menuObj.toString());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 这是从loushang3框架的系统中获取菜单的方式
	 * 
	 * @param userId
	 * @param appUrl
	 * @return
	 * @throws IOException
	 */
	public String getMenuJsonForLc(String userId, String menuxml, String uri,
			String appCode, String myIp,String IsAuthenNew,String isJson,String encoding) {
		HttpClient httpClient = new HttpClient();
		String jsonStr = "";
		GetMethod getMethod = new GetMethod("http://" + myIp
				+ "/portal/AuthenService" + "?USERID=" + userId + "&APP="
				+ appCode + "&RESOURCE=" + menuxml + "&HTTPCLIENT=1&IsAuthenNew="+IsAuthenNew);
		if(log.isDebugEnabled()){
			log.debug("访问的url为："+"http://" + myIp
				+ "/portal/AuthenService" + "?USERID=" + userId + "&APP="
				+ appCode + "&RESOURCE=" + menuxml + "&HTTPCLIENT=1&IsAuthenNew="+IsAuthenNew+"&"+isJson+"&"+encoding);
		}
		try {
			// 去掉自动跳转
			getMethod.setFollowRedirects(false);
			httpClient.executeMethod(getMethod);
			// 请求超时设置
			httpClient.getParams().setParameter("http.socket.timeout",
					new Integer(60000 * 10));
			Header locationHeader = getMethod.getResponseHeader("location");
			int num = 1;
			// 跳转判断限制为10次防止出现死循环的页面
			while (locationHeader != null && locationHeader.getValue() != null
					&& num < 10) {
				getMethod.releaseConnection();
				getMethod = new GetMethod(locationHeader.getValue());
				getMethod.setFollowRedirects(false);
				httpClient.executeMethod(getMethod);
				locationHeader = getMethod.getResponseHeader("location");
				num++;
			}
			byte[] responseBody = getMethod.getResponseBody();
			// loushang5
			if(isJson.equals("true")){
				String responseB = new String(responseBody, "UTF-8");	
				responseB=responseB.replaceAll("jsp/", uri+"jsp/");
				jsonStr=responseB;
			}else{
			// 字符编码转换
			String responseB = new String(responseBody, encoding);
			jsonStr = menuXml2Json(responseB, uri);}

		} catch (Exception e) {
			log.error("getMenuJsonForLc===出错了，", e);
		} finally {
			getMethod.releaseConnection();
		}

		return jsonStr;
	}

	/**
	 * 将menuxml.cmd返回的xml转换为json
	 * 
	 * @param xml
	 * @param appUrl
	 * @return
	 */
	public static String menuXml2Json(String xml, String appUrl) {
		JSONArray rowsArr = new JSONArray();
		try {
			Document doc = DocumentHelper.parseText(xml);
			List nodeList = doc.selectNodes("//xmlmenu/menu");
			Iterator iter = nodeList.iterator();
			while (iter.hasNext()) {
				JSONObject temObj = new JSONObject();
				Element element = (Element) iter.next();
				String text = element.attributeValue("text");
				String link = element.attributeValue("link");
				if (link == null) {
					link = "";
				}
				String url = element.attributeValue("url");

				if (url == null) {
					url = "";
				} else
					url = url.replace("|", "&");

				String menuId = element.attributeValue("id");
				String targetframe = element.attributeValue("targetframe");
				if (targetframe == null) {
					targetframe = "";
				}
				if (targetframe.equals("_tree")) {
					appUrl = appUrl + "/jsp/split.jsp?";
					temObj.put("link", appUrl + link);
					temObj.put("url", appUrl + url);
				} else {
					if (url.indexOf("http") < 0) {
						temObj.put("link", appUrl + "/" + link);
						temObj.put("url", appUrl + "/" + url);
					} else {
						temObj.put("link", link);
						temObj.put("url", url);
					}
				}
				temObj.put("text", text);
				temObj.put("id", menuId);
				temObj.put("targetframe", targetframe);
				// System.out.print(temObj);
				boolean haveSon = getChildJson(temObj, element, appUrl);
				if (haveSon) {
					temObj.put("isLeaf", "false");
				} else {
					temObj.put("isLeaf", "true");
				}
				rowsArr.put(temObj);

			}

		} catch (Exception e) {
			log.error("出错了", e);
		}

		return rowsArr.toString();}

	// 将xml转为json
	public static boolean getChildJson(JSONObject parentObj, Element elem,
			String appUrl) throws JSONException {
		List nodeList = elem.selectNodes("menu");
		Iterator iter = nodeList.iterator();
		JSONArray childArr = new JSONArray();
		boolean haveSon=false;
		while (iter.hasNext()) {
			JSONObject temObj = new JSONObject();
			Element element = (Element) iter.next();
			String text = element.attributeValue("text");
			if (text == null) {
				text = "";
			}
			String link = element.attributeValue("link");
			if (link == null) {
				link = "";
			}
			String url = element.attributeValue("url");
			if (url == null) {
				url = "";
			}
			String menuId = element.attributeValue("id");
			if (menuId == null) {
				menuId = "";
			}
			String targetframe = element.attributeValue("targetframe");
			temObj.put("id", menuId);
			temObj.put("text", text);
			if (targetframe == null) {
				targetframe = "";
			}
			if (log.isDebugEnabled()) {
				log.debug("======text========" +text+"======link========" +link+"======url========" +url+"======menuId========" +menuId);
			}
			if (targetframe.equals("_tree")) {
				appUrl = appUrl + "/jsp/split.jsp?";
				temObj.put("link", appUrl + link);
				temObj.put("url", appUrl + url);
			} else {
				if (url.indexOf("http") < 0) {
					temObj.put("link", appUrl + "/" + link);
					temObj.put("url", appUrl + "/" + url);
					if (log.isDebugEnabled()) {
						log.debug("======url1========" +url);
					}
				} else {
					temObj.put("link", link);
					temObj.put("url", url);
					if (log.isDebugEnabled()) {
						log.debug("======url2========" +url);
					}
				}
			}
			temObj.put("id", menuId);
			temObj.put("targetframe", targetframe);
			boolean isHaveSon=getChildJson(temObj, element, appUrl);
			if (isHaveSon) {
				temObj.put("isLeaf", "false");
			} else {
				temObj.put("isLeaf", "true");
			}
			childArr.put(temObj);
			haveSon=true;
		}
		parentObj.put("children", childArr);
		return haveSon;
	}
	

	// 菜单百科更新
	public String menuBaikeUpdate(HttpServletRequest req, HttpServletResponse rep,
			IErrorHandler errorHandler, IMessageHandler messageHandler,
			ViewHelper viewHelper) {
		
		String menuId=(String)req.getParameter("menuId");
		
		String content=(String)req.getParameter("content");
		
		V6BspInfo v6bspInfo=new V6BspInfo();
		String userId=v6bspInfo.getUserId();
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String updateTime=df.format(new Date());
		
		Map parmMap=new HashMap();
		parmMap.put("menuId", menuId);
		parmMap.put("content", content);
		parmMap.put("updateMan", userId);
		parmMap.put("updateTime", updateTime);
		
		int n=getAppPageService().updateMenuBaike(parmMap);
		
		try {
			PrintWriter out = rep.getWriter();
			out.write("1");
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		return "null";
		
	}
	
	// 菜单百科查询
	public void menuBaikeQuery(HttpServletRequest req, HttpServletResponse rep,
				IErrorHandler errorHandler, IMessageHandler messageHandler,
				ViewHelper viewHelper) {
			
			String menuId=(String)req.getParameter("menuId");

			
			Map parmMap=new HashMap();
			parmMap.put("menuId", menuId);

			
			Map resultMap=getAppPageService().queryMenuBaike(parmMap);
			
			String str="";
			if(resultMap!=null){
				str=JsonUtil.object2json(resultMap);
			}
			
			
			try {
				PrintWriter out = rep.getWriter();
				out.write(str);
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	}
public static String invoke(String url, String method, Object[] param,
			String Qname) throws Exception {
		String xml = null;
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		EndpointReference targetEPR = new EndpointReference(url);
		options.setTo(targetEPR);
        QName opAddEntry = new QName(Qname, method);
        Object[] opAddEntryArgs = new Object[] { param };
        Class[] classes = new Class[] { String.class };
        xml = (String) serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs,
				classes)[0];
        return xml.replace("&", "&amp;");
	}
	public static String invoke(String url, String method, String param ,String namespace) throws Exception {

		String xml = null;
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		EndpointReference targetEPR = new EndpointReference(url);
		options.setTo(targetEPR);
		QName opAddEntry = new QName(namespace, method);
		Object[] opAddEntryArgs = new Object[] { param };
		Class[] classes = new Class[] { String.class };
		xml = (String) serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs,classes)[0];
		String menuJson = menuXml2Json(xml, "http://");
	    return xml.replace("&", "&amp;");
		}
}