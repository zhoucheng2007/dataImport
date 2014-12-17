package com.blogzhou.common;

import java.io.FileReader;
import java.io.StringReader;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

import com.blogzhou.common.code.CodeManager;
import com.blogzhou.common.configuration.ConfigurationRegister;
import com.blogzhou.common.eaivo.Properties;
import com.blogzhou.common.eaivo.Root;
import com.blogzhou.common.exception.WebException;
import com.blogzhou.common.web.session.LoginInfo;

/**
 * @author Thinking Wu
 * @title 工具基础类
 * 		由于江西代码中 toolkit 对象代码过长,
 * 		现将原有的一些代码分离到该对象中,常用的方法及新增的方法置于Toolkit中
 * @date 2010-9-16
 * @version LastChangedRevision:
 */
public class ToolkitBase {
	/**
	 * 当前类自己的logger
	 */
	protected static Logger logger = Logger.getLogger(ToolkitBase.class);
	
	protected static boolean isDebug = true;

	/**
	 * 日期格式化工具
	 */
	protected static final DateFormat dateFormater = new SimpleDateFormat(
			"yyyy-MM-dd");

	/**
	 * 扣缴义务人密码派发:查询数据上限,大小单位（记录行数）
	 */
	protected static long queryDataUpperLimit;

	/**
	 * 日期格式化工具，只有年月
	 */
	protected static final DateFormat dateFormaterYM = new SimpleDateFormat(
			"yyyy-MM");

	/**
	 * <p>
	 * 根据滞纳天数获取滞纳金额
	 * </p>
	 * 滞纳金 = 滞纳天数 * (本次实纳金额*5/10000)
	 * 
	 * @param taxdue
	 *            本次实纳金额
	 * @param dateLimit
	 *            缴款期限
	 * @return 滞纳金额
	 */
	public float getDelayMoney(float taxdue, Date dateLimit) {
		float delaymoney = 0;
		// SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
		// Date date2 = ft.parse("2008/3/7");

		float delayDays = DateToolkit.getDifferenceDays(dateLimit, new Date());
		delaymoney = delayDays * (taxdue * 5 / 10000);

		return delaymoney;
	}

	/**
	 * 获取 纳税年度
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 纳税年度(第一个元素为“纳税年度起”，第二个元素为“纳税年度止”)
	 * @throws WebException
	 *             异常
	 */
	public static String[] getNsnd(HttpServletRequest request) {
		String[] nsnd = new String[2]; // 纳税年度
		int year = Calendar.getInstance().get(Calendar.YEAR);
		nsnd = new String[] { year + "-01-01", year + "-12-31" };
		return nsnd;
	}

	/**
	 * 获取 上一个纳税年度
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 纳税年度(第一个元素为“纳税年度起”，第二个元素为“纳税年度止”)
	 * @throws WebException
	 *             异常
	 */
	public static String[] getNsndLastYear(HttpServletRequest request) {
		String[] nsnd = new String[2]; // 纳税年度
		int year = Calendar.getInstance().get(Calendar.YEAR) - 1;
		nsnd = new String[] { year + "-01-01", year + "-12-31" };
		return nsnd;
	}

	/**
	 * 取 当前登陆用户的纳税人编码
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 纳税人编码
	 */
	public static String getNsrbm(HttpServletRequest request) {
		LoginInfo logininfo = getNsrjbxx(request);
		return logininfo.getNsrbm();
	}
	
	/**
	 * 取 当前登陆用户的编码
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param taxflag(1税、2社保费、3工会经费)
	 * 			  String
	 * @return 编码
	 */
	public static String getNsrbm(HttpServletRequest request,String taxflag) {
		String payerid = "";
		LoginInfo logininfo = getNsrjbxx(request);
		if("1".equals(taxflag)){
			payerid = logininfo.getNsrbm();
		} else if("2".equals(taxflag)){
			payerid = logininfo.getSbfbm();
		} else if("3".equals(taxflag)){
			payerid = logininfo.getGhjfbm();
		} else{
			payerid = logininfo.getNsrnbm();
		}
		return payerid;
	}


	/**
	 * 获取 申报日期
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 申报日期
	 */
	public static String getSbrq(HttpServletRequest request) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(new Date());
	}

	/**
	 * 查询基础数据zspmdm ：对应表T_DM_GY_ZSPM CodeManager.getCodeTable("zspmdm");
	 * 返回的是map(pm*sz, mc),例如查"09"的： key.indexOf("*09")
	 * 
	 * @param zsxmdm
	 *            查询条件：征收项目代码
	 * @return HashMap(pmdm,mc)
	 * @throws WebException
	 */
	public static HashMap getZspmdm(String zsxmdm) throws WebException {
		String key;
		HashMap pmmap = new HashMap();
		Map map = CodeManager.getCodeTable("taxitemfilter");
		if (map != null) {
			Iterator iter = (Iterator) map.keySet().iterator();
			while (iter.hasNext()) {
				key = (String) iter.next();
				if (key.startsWith(zsxmdm + "*")) {
					// pmmap.put(key.substring(0, key.indexOf("*")),
					// map.get(key));
					String value = (String) map.get(key);
					if (value != null && value.length() > 0) {
						String[] zspmdm = value.split("@");
						pmmap.put(zspmdm[0], zspmdm[1]);
					}
				}
			}
		}
		logger.debug("取得征收项目" + zsxmdm + "对应的征收品目：" + pmmap);
		return pmmap;
	}

	/**
	 * 查询基础数据隶属关系lsgxdm ：对应表T_DM_GY_LSGX CodeManager.getCodeTable("lsgxdm");
	 * 返回的是map(lsgxdm, mc)
	 * 
	 * @param lsgxdm
	 *            查询条件：征收项目代码
	 * @return HashMap(lsgxdm,mc)
	 * @throws WebException
	 */
	public static HashMap getLsgx() throws WebException {
		HashMap map = (HashMap) CodeManager.getCodeTable("lsgxdm");
		return map;
	}

	/**
	 * 查询基础数据 注册类型 djzclxdm ：对应表T_DM_GY_DJZCLX
	 * CodeManager.getCodeTable("djzclxdm"); 返回的是map(djzclxdm, mc)
	 * 
	 * @return HashMap(djzclxdm,mc)
	 * @throws WebException
	 */
	public static HashMap getDjzclx() throws WebException {
		HashMap map = (HashMap) CodeManager.getCodeTable("djzclxdm");
		return map;
	}

	/**
	 * 查询基础数据 国籍地区代码 gjdm ：对应表T_DM_GY_DJZCLX CodeManager.getCodeTable("gjdm");
	 * 返回的是map(gjdm, mc)
	 * 
	 * @return HashMap(gjdm,mc)
	 * @throws WebException
	 */
	public static HashMap getGjdq() throws WebException {
		HashMap map = (HashMap) CodeManager.getCodeTable("gjdm");
		return map;
	}

	/**
	 * 查询基础数据 行业(中小类)代码 hydldm ：对应表T_DM_GY_HYZXL
	 * CodeManager.getCodeTable("hydldm"); 返回的是map(hydldm, mc)
	 * 
	 * @return HashMap(hydldm,mc)
	 * @throws WebException
	 */
	public static HashMap getHydl() throws WebException {
		HashMap map = (HashMap) CodeManager.getCodeTable("hyzxldm");
		return map;
	}

	/**
	 * 查询基础数据 行业(大类)代码 hydldm ：对应表T_DM_GY_HYDL
	 * CodeManager.getCodeTable("hydldm"); 返回的是map(hydldm, mc)
	 * 
	 * @return HashMap(hydldm,mc)
	 * @throws WebException
	 */
	public static HashMap getHydldm() throws WebException {
		HashMap map = (HashMap) CodeManager.getCodeTable("hydldm");
		return map;
	}

	/**
	 * 查询基础数据 行业（中小类）代码 hydldm ：对应表T_DM_GY_HYZXL
	 * CodeManager.getCodeTable("hyzxldm"); 返回的是map(hyzxldm, mc)
	 * 
	 * @return HashMap(hyzxldm,mc)
	 * @throws WebException
	 */
	public static HashMap getHyzxl() throws WebException {
		HashMap map = (HashMap) CodeManager.getCodeTable("hyzxldm");
		return map;
	}

	/**
	 * 查询基础数据 户籍类型（如城镇户）代码 hjlxdm ：对应表T_DM_SF_HJLX
	 * CodeManager.getCodeTable("hjlxdm"); 返回的是map(hjlxdm, mc)
	 * 
	 * @return HashMap(hjlxdm,mc)
	 * @throws WebException
	 */
	public static HashMap getHjlx() throws WebException {
		HashMap map = (HashMap) CodeManager.getCodeTable("hjlxdm");
		return map;
	}

	/**
	 * 查询基础数据 人员状态代码 ryztdm ：对应表T_DM_SF_RYZT CodeManager.getCodeTable("ryztdm");
	 * 返回的是map(ryztdm, mc)
	 * 
	 * @return HashMap(ryztdm,mc)
	 * @throws WebException
	 */
	public static HashMap getRzzt() throws WebException {
		HashMap map = (HashMap) CodeManager.getCodeTable("ryztdm");
		return map;
	}

	/**
	 * 查询基础数据 用工方式代码 ygfsdm ：对应表T_DM_SF_YGXS CodeManager.getCodeTable("ygxsdm");
	 * 返回的是map(ygxsdm, mc)
	 * 
	 * @return HashMap(ygxsdm,mc)
	 * @throws WebException
	 */
	public static HashMap getYgfs() throws WebException {
		HashMap map = (HashMap) CodeManager.getCodeTable("ygxsdm");
		return map;
	}

	/**
	 * 查询基础数据 职务代码 zwdm ：对应表T_DM_GS_ZW CodeManager.getCodeTable("zwdm");
	 * 返回的是map(zwdm, mc)
	 * 
	 * @return HashMap(zwdm,mc)
	 * @throws WebException
	 */
	public static HashMap getZwdm() throws WebException {
		HashMap map = (HashMap) CodeManager.getCodeTable("zwdm");
		return map;
	}

	/**
	 * 查询基础数据 人员类别代码 rylbdm ：对应表T_DM_SF_RYLB CodeManager.getCodeTable("rylbdm");
	 * 返回的是map(rylbdm, mc)
	 * 
	 * @return HashMap(rylbdm,mc)
	 * @throws WebException
	 */
	public static HashMap getZydm() throws WebException {
		HashMap map = (HashMap) CodeManager.getCodeTable("rylbdm");
		return map;
	}

	/**
	 * 查询基础数据 "身份证明类别代码" sfzmlbdm ：对应表T_DM_GS_ZW
	 * CodeManager.getCodeTable("sfzmlbdm"); 返回的是map(sfzmlbdm, mc)
	 * 
	 * @return HashMap(sfzmlbdm,mc)
	 * @throws WebException
	 */
	public static HashMap getSfzmlb() throws WebException {
		HashMap map = (HashMap) CodeManager.getCodeTable("sfzmlbdm");
		return map;
	}

	/**
	 * 查询国标行业
	 * 
	 * @return
	 * @throws WebException
	 */
	public static HashMap getGbhy() throws WebException {
		HashMap map = (HashMap) CodeManager.getCodeTable("gbtradecode");
		return map;
	}

	/**
	 * 查询币种代码
	 * 
	 * @return
	 * @throws WebException
	 */
	public static HashMap getBzdm() throws WebException {
		HashMap map = (HashMap) CodeManager.getCodeTable("moneytypecode");
		return map;
	}

	/**
	 * 查询证照类型代码
	 * 
	 * @return
	 * @throws WebException
	 */
	public static HashMap getZzlxdm() throws WebException {
		// HashMap map = (HashMap) CodeManager.getCodeTable("certtypecode");
		HashMap map = (HashMap) CodeManager.getCodeTable("certtypecode");

		// HashMap mapRet=new TreeMap(map);

		return map;
	}

	/**
	 * 查询批准机关类型代码
	 * 
	 * @return
	 * @throws WebException
	 */
	public static HashMap getPzjgTypedm() throws WebException {
		// HashMap map = (HashMap) CodeManager.getCodeTable("certtypecode");
		HashMap map = (HashMap) CodeManager.getCodeTable("issueorgtypecode");

		// HashMap mapRet=new TreeMap(map);

		return map;
	}

	/**
	 * 查询批准机关代码
	 * 
	 * @return
	 * @throws WebException
	 */
	public static HashMap getPzjgdm() throws WebException {
		// HashMap map = (HashMap) CodeManager.getCodeTable("certtypecode");
		HashMap map = (HashMap) CodeManager.getCodeTable("issueorgcode");

		// HashMap mapRet=new TreeMap(map);

		return map;
	}

	/**
	 * 查询固定资产折旧方式代码
	 * 
	 * @return
	 * @throws WebException
	 */
	public static HashMap getGdzczjdm() throws WebException {
		HashMap map = (HashMap) CodeManager.getCodeTable("depreciationcode");
		return map;
	}

	/*
	 * 查询单位性质代码
	 */
	public static HashMap getDwxzdm() throws WebException {
		HashMap map = (HashMap) CodeManager
				.getCodeTable("enterprisecharactercode");
		return map;
	}

	/**
	 * 查询单位经济性质代码
	 * 
	 * @return
	 * @throws WebException
	 */
	public static HashMap getJjlxdm() throws WebException {
		HashMap map = (HashMap) CodeManager.getCodeTable("econkindcode");
		return map;
	}

	/**
	 * 此方法是为 委托代缴所写,所以在返回的map中需要除掉: 房产税07、城市房地产税31、土地使用税14、土地增值税15、印花税09、
	 * 车船使用税08、车船牌照税30、个人所得税06 查询基础数据 征收项目（税种）代码 zsxmdm ：对应表T_DM_GY_ZSXM
	 * CodeManager.getCodeTable("zsxmdm"); 返回的是map(zsxmdm, mc)
	 * 
	 * @return HashMap(zsxmdm,mc)
	 * @throws WebException
	 */
	public static HashMap getZsxm() throws WebException {
		HashMap map = (HashMap) CodeManager.getCodeTable("zsxmdm");
		if (map != null) {
			map.remove("07");// 房产税07
			map.remove("31");// 城市房地产税31
			map.remove("14");// 土地使用税14
			map.remove("15");// 土地增值税15
			map.remove("09");// 印花税09
			map.remove("08");// 车船使用税08
			map.remove("30");// 车船牌照税30
			map.remove("06");// 个人所得税06
		}
		return map;
	}

	/**
	 * 查询基础数据 征收品目（税目）代码 zspmdm ：对应表T_DM_GY_ZSPM
	 * CodeManager.getCodeTable("zspmdm"); 返回的是map(zspmdm, mc)
	 * 
	 * @return HashMap(zspmdm,mc)
	 * @throws WebException
	 */
	public static HashMap getZhpm() throws WebException {
		HashMap map = (HashMap) CodeManager.getCodeTable("zspmdm");
		return map;
	}

	/**
	 * 取得 所有的国籍地区代码
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 所有的国籍地区
	 */
	public static HashMap getGjdm() throws WebException {
		HashMap map = (HashMap) CodeManager.getCodeTable("gjdm");
		return map;
	}

	/**
	 * 取得 所有的经营项目代码
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 所有的经营项目
	 */
	public static HashMap getJyxm() throws WebException {
		HashMap map = (HashMap) CodeManager.getCodeTable("jyxmdm");
		return map;
	}

	/**
	 * 获取组织机构类型代码
	 * 
	 * @return
	 * @throws WebException
	 */
	public static HashMap getZzjglx() throws WebException {
		HashMap map = (HashMap) CodeManager.getCodeTable("unifycodecertcode");
		return map;
	}

	/**
	 * 获取机关行业代码
	 * 
	 * @return
	 * @throws WebException
	 */
	public static HashMap getJghydm() throws WebException {
		HashMap map = (HashMap) CodeManager.getCodeTable("issueorgtypecode");
		return map;
	}

	/**
	 * 获取行业对应机构代码
	 * 
	 * @return
	 * @throws WebException
	 */
	public static HashMap getHydyjgdm() throws WebException {
		HashMap map = (HashMap) CodeManager.getCodeTable("issueorgcode");
		return map;
	}

	/**
	 * 获取核算形式代码
	 * 
	 * @return
	 * @throws WebException
	 */
	public static HashMap getHsxsdm() throws WebException {
		HashMap map = (HashMap) CodeManager.getCodeTable("accountingmodecode");

		return map;
	}

	/**
	 * 获取隶属关系代码
	 * 
	 * @return
	 * @throws WebException
	 */
	public static HashMap getLsgxdm() throws WebException {
		HashMap map = (HashMap) CodeManager.getCodeTable("subjectrelationcode");
		return map;
	}

	/**
	 * 获取扣缴义务人注册类型
	 * 
	 * @return
	 * @throws WebException
	 */
	public static HashMap getKjywrzclxdm() throws WebException {
		HashMap map = (HashMap) CodeManager.getCodeTable("regtypecode");
		return map;
	}

	/**
	 * 获取项目用途代码
	 * 
	 * @return
	 * @throws WebException
	 */
	public static Map getXmytdm() throws WebException {
		Map map = (Map) CodeManager.getCodeTable("buildingpurposecode");
		return map;
	}

	/**
	 * 获取建筑类型代码
	 * 
	 * @return
	 * @throws WebException
	 */
	public static Map getJzlxdm() throws WebException {
		Map map = (Map) CodeManager.getCodeTable("constructiontypecode");
		return map;
	}

	/**
	 * 获取房产用途代码
	 * 
	 * @return
	 * @throws WebException
	 */
	public static Map getFcytdm() throws WebException {
		Map map = (Map) CodeManager.getCodeTable("realtytypecode");
		return map;
	}

	/**
	 * 身份证明类型代码
	 */
	public static HashMap getSfzmlxdm() throws WebException {
		HashMap map = (HashMap) CodeManager.getCodeTable("idtypecode");
		return map;
	}

	/**
	 * 行政许可代码
	 * 
	 * @return
	 * @throws WebException
	 */
	public static Map getXzxkdm() throws WebException {
		Map map = CodeManager.getCodeTable("licenceitemcode");
		return map;
	}

	

	/**
	 * 查询未补录明细的汇总信息
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 全部税种品目核定信息
	 */
	public static String getWblmxhzxx(HttpServletRequest request)
			throws Exception {
		LoginInfo logininfo = getNsrjbxx(request);
		BuilderXml builder = new BuilderXml();
		builder.builderHeader("QDgetgshzxxBLH",
				"gov.gdlt.taxcore.taxevent.zshs.sb.qd.QDgetgshzxxReqEvent",
				"queryHandler", null);
		HashMap map = new HashMap();
		map.put("nsrbm", logininfo.getNsrbm());

		Calendar cd = Calendar.getInstance();
		cd.add(Calendar.YEAR, -1);
		cd.set(Calendar.MONTH, 0);
		cd.set(Calendar.DAY_OF_MONTH, 1);
		Date date = cd.getTime();
		Calendar cd1 = Calendar.getInstance();
		cd1.add(Calendar.YEAR, 1);
		cd1.set(Calendar.MONTH, 0);
		cd1.set(Calendar.DAY_OF_MONTH, 1);
		cd1.add(Calendar.DAY_OF_MONTH, -1);
		Date date1 = cd1.getTime();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		map.put("sdqq", sd.format(date));
		map.put("sdqz", sd.format(date1));
		builder.addCell(map);
		DirectorXml director = new DirectorXml(builder);
		String xml = director.construct();

		request.setAttribute("xml", xml);
		if (logger.isDebugEnabled()) {
			logger.debug("input：" + xml);
		}
		ISbCtrlHelper sbCtrlHelper = new ZhsbSelectSwjgCtrlHelper();
		ControllerResult controllerResult = sbCtrlHelper.execute(null, request);
		String xmlFromEAI = (String) controllerResult.getData();
		if (logger.isDebugEnabled()) {
			logger.debug("未补录明细的汇总信息情况：" + xmlFromEAI);
		}
		return xmlFromEAI;
	}

	// /**
	// * 未清缴税费情况查询 sstx中使用
	// * @param request HttpServletRequest
	// * @return 全部税种品目核定信息
	// */
	// public static String getWqjsfqk(HttpServletRequest request) throws
	// Exception {
	// LoginInfo logininfo = getNsrjbxx(request);
	// BuilderXml builder = new BuilderXml();
	// builder.builderHeader("QDSBwqjsfqkcxBLH",
	// "gov.gdlt.taxcore.taxevent.zshs.sb.qd.QDwqjsfqkcxReqEvent",
	// "queryHandler", "query");
	// HashMap map = new HashMap();
	// map.put("nsrbm", logininfo.getNsrbm());
	// builder.addCell(map);
	// DirectorXml director = new DirectorXml(builder);
	// String xml = director.construct();
	//
	// request.setAttribute("xml", xml);
	// ISbCtrlHelper sbCtrlHelper = new ZhsbSelectSwjgCtrlHelper();
	// ControllerResult controllerResult = sbCtrlHelper.execute(null,
	// request);
	// String xmlFromEAI = (String) controllerResult.getData();
	// if (logger.isDebugEnabled()) {
	// logger.debug(" 未清缴税费情况查询：" + xmlFromEAI);
	// }
	// return xmlFromEAI;
	// }

	/**
	 * 未清缴税费情况查询 sstx中使用
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 全部税种品目核定信息
	 */
	public static Root queryWqjsfqk(String nsrbm) throws Exception {
		Root xmlvo = Root.getInstance(SidConstants.COMMON, "QDSBwqjsfqkcxBLH",
				"gov.gdlt.taxcore.taxevent.zshs.sb.qd.QDwqjsfqkcxReqEvent");
		xmlvo.setHead("queryHandler");
		xmlvo.setBizInfo("query");
		Properties prop = xmlvo.getProperties();
		prop.putCell("nsrbm", nsrbm);
		try {
			xmlvo = invoke(xmlvo);
		} catch (Exception e) {
			logger.info(" 未清缴税费情况查询");
		}
		return xmlvo;

	}

	/**
	 * 取 纳税人基本信息
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return LoginInfo 缴费人基本信息
	 */
	public static LoginInfo getNsrjbxx(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Object obj = session
		.getAttribute(TaxWebConstants.SESSION_ATTRIBUTE_LOGININFO);
		if (obj != null && obj instanceof LoginInfo) {
			return (LoginInfo) obj;
		} else {
			return new LoginInfo();
		}
	}
	
	/**
	 * 取 缴费人-社保费基本信息
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return JfrInfo 纳税人基本信息
	 */
	public static JfrInfo getJfrjbxx_ghjf(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Object obj = session
		.getAttribute(TaxWebConstants.SESSION_ATTRIBUTE_GHJFINFO);
		if (obj != null && obj instanceof JfrInfo) {
			return (JfrInfo) obj;
		} else {
			return new JfrInfo();
		}
	}
	/**
	 * 取 缴费人-工会经费基本信息
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return JfrInfo 缴费人基本信息
	 */
	public static JfrInfo getJfrjbxx_sbf(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Object obj = session
				.getAttribute(TaxWebConstants.SESSION_ATTRIBUTE_SBFININFO);
		if (obj != null && obj instanceof JfrInfo) {
			return (JfrInfo) obj;
		} else {
			return new JfrInfo();
		}
	}

	/**
	 * 取 User基本信息
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return LoginInfo 纳税人基本信息
	 */
	public static User getUserInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Object obj = session
				.getAttribute(TaxWebConstants.SESSION_ATTRIBUTE_USER);
		if (obj != null && obj instanceof User) {
			return (User) obj;
		} else {
			return new User();
		}
	}

	/**
	 * 取 作为代理人的纳税人基本信息
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return LoginInfo 纳税人基本信息
	 */
	public static LoginInfo getNsrjbxx_deputy(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Object obj = session
				.getAttribute(TaxWebConstants.SESSION_ATTRIBUTE_LOGININFO_DEPUTY);
		if (obj != null && obj instanceof LoginInfo) {
			return (LoginInfo) obj;
		} else {
			return new LoginInfo();
		}
	}

	/**
	 * 取 纳税人 所有的税种信息
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 全部税种品目核定信息
	 */
	public static String getNsrSzxx(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Object obj = session
				.getAttribute(TaxWebConstants.SESSION_ATTRIBUTE_QBSZ);
		if (obj != null) {
			return obj.toString();
		}
		return "";
	}

	/**
	 * 取 纳税人 所有的税种信息， 选择了跨区管理机关后需要重新取
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param gljgdm
	 *            管理机关代码
	 * @return 全部税种品目核定信息
	 * @throws BackendServiceException
	 */
	public static String getNsrSzxx(HttpServletRequest request, String gljgdm)
			throws BackendServiceException {
		Root xmlvo = null;
		Properties prop = null;
		Date today = new Date();
		Date[] periods = DateToolkit.genLastSSSQ("20", today);
		LoginInfo loginInfo = getNsrjbxx(request);

		/* 取得全部税种品目核定信息 begin */
		xmlvo = Root.getInstance(SidConstants.COMMON, "QDgetszpmhdxxBLH",
				"gov.gdlt.taxcore.taxevent.zshs.sb.qd.QDgetszpmhdxxReqEvent");
		xmlvo.setHead("queryHandler");
		xmlvo.setBizInfo("getallhdxx");
		prop = xmlvo.getProperties();
		prop.putCell("nsrbm", loginInfo.getNsrbm());
		prop.putCell("gljgdm", gljgdm);
		prop.putCell("ssqq", dateFormater.format(periods[0]));
		prop.putCell("ssqz", dateFormater.format(periods[1]));
		prop.putCell("kqbj", "1"); // 跨区标志：0为本区， 1为跨区
		xmlvo = invoke(xmlvo);
		// 去掉跨区的核定信息中nsqxdm<>50的
		// ListVO lstTemp = (ListVO) xmlvo.getArrayLists().get(0);
		// List lstVo = null;
		// if (lstTemp != null) {
		// lstVo = ((ListVO) xmlvo.getArrayLists().get(0)).getVos();
		// for (int i = lstVo.size() - 1; i >= 0; i--) {
		// VO vo = (VO) lstVo.get(i);
		// if (!"50".equals(vo.getCell("nsqxdm")))
		// lstVo.remove(vo);
		// }
		// }
		if (logger.isDebugEnabled())
			logger.debug("取跨区管理机关：" + gljgdm + "的核定税种：" + xmlvo.toString());
		return xmlvo.toString();
	}

	/**
	 * 取 取得跨区核定的管理机关代码
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 全部税种品目核定信息
	 */
	public static String getKqhdgljgdm(HttpServletRequest request)
			throws BackendServiceException {
		if (queryHaveKqgljg(request)) {
			HttpSession session = request.getSession();
			Object obj = session
					.getAttribute(TaxWebConstants.SESSION_ATTRIBUTE_KQHDGLJGDM);
			if (obj == null) {
				/* 取得跨区核定的管理机关代码 begin */
				Root xmlvo = Root
						.getInstance(SidConstants.COMMON, "QDSBqdkqhdgljgBLH",
								"gov.gdlt.taxcore.taxevent.zshs.sb.qd.QDqdkqhdgljgReqEvent");
				xmlvo.setHead("queryHandler");
				xmlvo.setBizInfo("getkqhdzsjg");
				xmlvo.getProperties().putCell("nsrbm", getNsrbm(request));
				obj = invoke(xmlvo).toString();
				session.setAttribute(
						TaxWebConstants.SESSION_ATTRIBUTE_KQHDGLJGDM, obj);
				/* 取得跨区核定的管理机关代码 end */
			}
			return obj.toString();
		} else {
			return "";
		}
	}

	/**
	 * 判断是否需要调跨区管理机关的接口,读配置文件判断，少调跨区的接口
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return true 需要调跨区接口 false 不需要
	 */
	public static boolean queryHaveKqgljg(HttpServletRequest request) {
		LoginInfo loginInfo = getNsrjbxx(request);
		int res = 0;
		String kqgljg = "";
		if (loginInfo.getGljgdm().length() > 5) {
			kqgljg = "kqgljgbz." + loginInfo.getGljgdm().substring(0, 5);
		}
		try {
			ConfigurationRegister conf = ConfigurationRegister.getInstance();
			res = conf.getConfiguration("revenue_cfg").getInt(kqgljg);
		} catch (Exception e) {
		}
		if (logger.isDebugEnabled()) {
			logger.debug("读取纳税人：" + loginInfo.getNsrbm() + "的跨区管理机关的配置是"
					+ String.valueOf(res));
		}
		if (res == 1)
			return true;
		else
			return false;
	}

	/**
	 * 判断某税务机关是否系统自动派号，读取配置文件revenue_cfg.properties 1：手工派号， 0：系统派号
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return false 手工派号 true 系统派号
	 */
	public static boolean IsSbglAutoDispatch(HttpServletRequest request) {
		LoginInfo loginInfo = getNsrjbxx(request);
		int res = 0;
		String dispatch = "";
		if (loginInfo.getGljgdm().length() > 5) {
			dispatch = "sbglhanddispatch."
					+ loginInfo.getGljgdm().substring(0, 5);
		}
		try {
			ConfigurationRegister conf = ConfigurationRegister.getInstance();
			res = conf.getConfiguration("revenue_cfg").getInt(dispatch);
		} catch (Exception e) {
		}
		if (logger.isDebugEnabled()) {
			logger.debug("读取纳税人：" + loginInfo.getNsrbm() + "所在税务机关'"
					+ loginInfo.getGljgmc() + loginInfo.getGljgdm()
					+ "'的手工派号配置为:" + String.valueOf(res));
		}
		if (res == 1)
			return false;
		else
			return true;
	}

	/**
	 * 判断是否存在跨区机关
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isKqjg(HttpServletRequest request)
			throws BackendServiceException {
		boolean flag = false;
		String xmlKqjg = getKqhdgljgdm(request);
		ToolXmltoList xmltolist = new ToolXmltoList();
		xmltolist.setXpath2("arrayList/vo");
		List qxList = null;
		try {
			qxList = xmltolist.xmltoList(xmlKqjg);
		} catch (WebException e) {
			e.printStackTrace();
		}
		if (qxList != null && qxList.size() > 1) {// 存在一条以上记录,则有跨区机关
			flag = true;
		}
		return flag;
	}

	/**
	 * 取 取得纳税人各税种成功申报的最后所属期 这个不能放到session中，每次要取最新的
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 全部税种品目核定信息
	 * @deprecated 应该使用queryGszssq(String nsrbm)取代本函数调用
	 */
	public static String getGszssq(HttpServletRequest request) throws Exception {
		return queryGszssq(getNsrjbxx(request).getNsrbm()).toString();
		// LoginInfo logininfo = getNsrjbxx(request);
		// /* 取得纳税人各税种成功申报的最后所属期 begin */
		// BuilderXml builder = new BuilderXml();
		// builder.builderHeader("QDgetzhssqBLH",
		// "gov.gdlt.taxcore.taxevent.zshs.sb.qd.QDgetzhssqReqEvent",
		// "queryHandler", "getzhssq");
		// HashMap map = new HashMap();
		// map.put("nsrbm", logininfo.getNsrbm());
		// builder.addCell(map);
		// DirectorXml director = new DirectorXml(builder);
		// String xml = director.construct();
		// if (xml.indexOf("rootVo") > 0)
		// xml = xml.substring(xml.indexOf("rootVo") - 2);
		// // //request.setAttribute("xml", xml);
		// // //ISbCtrlHelper sbCtrlHelper = new ZhsbSelectSwjgCtrlHelper();
		// // //ControllerResult controllerResult = sbCtrlHelper.execute(null,
		// request);
		// // //这个不需要代码转名称直接传xml过去可减少时间
		// if (logger.isDebugEnabled())
		// logger.debug("取得纳税人各税种成功申报的最后所属期时组装的xml：" + xml);
		// Root xmlvo = Root.parse(xml);
		// xmlvo.setSID(SidConstants.COMMON);
		// ZhsbSelectSwjgCtrlHelper sbCtrlHelper = new
		// ZhsbSelectSwjgCtrlHelper();
		// ControllerResult result = sbCtrlHelper.excecute(xmlvo);
		// String xmlFromEAI = (String) result.getData();
		// if (logger.isDebugEnabled()) {
		// logger.debug(" 取得纳税人各税种成功申报的最后所属期：" + xmlFromEAI);
		// }
		// /* 取得纳税人各税种成功申报的最后所属期 end */
		// return xmlvo.toString();
	}

	/**
	 * 取 取得纳税人各税种成功申报的最后所属期 这个不能放到session中，每次要取最新的
	 * 
	 * @param nsrbm
	 *            纳税人编码
	 * @return 全部税种品目核定信息
	 */
	public static Root queryGszssq(String nsrbm) throws BackendServiceException {
		Root xmlvo = Root.getInstance(SidConstants.COMMON, "QDgetzhssqBLH",
				"gov.gdlt.taxcore.taxevent.zshs.sb.qd.QDgetzhssqReqEvent");
		xmlvo.setBizInfo("getzhssq");
		xmlvo.getProperties().putCell("nsrbm", nsrbm);
		return invoke(xmlvo);
	}

	/**
	 * 取得生产经营个人税收信息
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param nsgrnbm
	 *            企业纳税人内码
	 * @param fpszbl
	 *            分配所占比例
	 * @param qcwjsdseje
	 *            期初未缴所得税额
	 * @param scjysdje
	 *            生产经营所得额
	 * @param jsje
	 *            应纳税所得额(计税金额)
	 * @param yinseje
	 *            已纳税额
	 * @param djje
	 *            抵缴金额
	 * @return生产经营个人税收信息
	 * 
	 */
	// public static String getSbscjygrss(HttpServletRequest request ,String
	// nsgrnbm,String fpszbl,String qcwjsdseje,String scjysdje,String
	// jsje,String yinseje,String djje){
	// LoginInfo logininfo = getNsrjbxx(request);
	/* 取得投资者的信息 */
	/*
	 * BuilderXml builder = new BuilderXml();
	 * builder.builderHeader("GSyjyjsbBLH","gov.gdlt.taxcore.taxevent.zshs.sb.gs54.GSscjygssubmitReqEvent","sbHandler","interact");
	 * HashMap map=new HashMap(); map.put("nsgrnbm",nsgrnbm);
	 * map.put("fpszbl",fpszbl); map.put("qcwjsdseje",qcwjsdseje);
	 * map.put("scjysdje",scjysdje); map.put("jsje",jsje);
	 * map.put("yinseje",yinseje); map.put("djje",djje); builder.addCell(map);
	 * DirectorXml director = new DirectorXml(builder); String xml =
	 * director.construct(); request.setAttribute("xml", xml); ISbCtrlHelper
	 * sbCtrlHelper = new ZhsbSelectSwjgCtrlHelper();
	 */
	// ControllerResult controllerResult = sbCtrlHelper.execute(null,
	// request);
	// String xmlFromEAI = (String) controllerResult.getData();
	// return xmlFromEAI;
	// }
	/**
	 * 取 个税汇总申报授权查询 信息
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 个税汇总申报授权查询信息
	 * @deprecated 建议使用getSbhzsqcx(String nsrbm)取代本函数调用
	 */
	public static String getSbhzsqcx(HttpServletRequest request)
			throws Exception {
		LoginInfo logininfo = getNsrjbxx(request);
		return getSbhzsqcx(logininfo.getNsrbm()).toString();

	}

	/**
	 * 取 个税汇总申报授权查询 信息
	 * 
	 * @param nsrbm
	 *            纳税人编码
	 * @return 个税汇总申报授权查询信息
	 */
	public static Root getSbhzsqcx(String nsrbm) throws BackendServiceException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String ssqq = "";
		String ssqz = "";
		Date date = new Date();
		try {
			date = sdf.parse(sdf.format(new Date()).substring(0, 7) + "-01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1);
		ssqq = sdf.format(cal.getTime());
		cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		ssqz = sdf.format(cal.getTime());

		Root xmlvo = Root.getInstance(SidConstants.COMMON, "QDgetgshzsqBLH",
				"gov.gdlt.taxcore.taxevent.zshs.sb.qd.QDgetgshzsqReqEvent");
		xmlvo.getProperties().putCell("sdqq", ssqq);
		xmlvo.getProperties().putCell("sdqz", ssqz);
		xmlvo.getProperties().putCell("nsrbm", nsrbm);
		return invoke(xmlvo);
	}

	/**
	 * 取 取得纳税人开户行信息 网上实时扣款使用的银行帐户是采用“帐户用途”为“1--扣税款帐号”还是“4--ETS扣款帐号”或其他？
	 * 4--ETS扣款帐号 8－－efs
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 取得纳税人开户行信息, 只返回 <cell name="zhbj" value="4 || 8"/> 的
	 */
	public static Map getNsrkhhxx(HttpServletRequest request) {

		HttpSession session = request.getSession();
		Root xmlvo = (Root) session
				.getAttribute(TaxWebConstants.SESSION_ATTRIBUTE_NSRKHHXX);
		if (xmlvo == null || xmlvo.getArrayLists().size() == 0) {
			return new HashMap();
		}
		List vos = ((ListVO) xmlvo.getArrayLists().get(0)).getVos();
		if (vos == null || vos.size() == 0) {
			return new HashMap();
		}
		// 取zhbj==4或者8的开户行
		VO resultVO = null;
		Map tmpMap = null;
		for (int i = 0; i < vos.size(); i++) {
			VO vo = (VO) vos.get(i);
			tmpMap = vo.getMap();
			if ("4".equals((String) tmpMap.get("zhbj"))
					|| "8".equals((String) tmpMap.get("zhbj")))
				resultVO = vo;
		}
		if (resultVO == null)
			return new HashMap();
		else
			return resultVO.getMap();
	}

	/**
	 * 检查接口类，如申报情况查询接口，检查返回是否有查询结果
	 * 
	 * @param xmlvo
	 * @param interfaceName
	 * @return true 空结果，flase：有查询结果
	 * @throws WebException
	 */
	public static boolean checkQueryIsNull(Root rtnXmlvo, String interfaceName)
			throws WebException {
		String eMsg = null;
		//WebException fex = null;
		if ("1".equals(rtnXmlvo.getHead().getRepcode().trim())) {
			// 判断是否返回结果的内容为空
			ListVO listTemp = rtnXmlvo.getFirstArrayList();
			List listVo = null;
			if (listTemp == null) {
				eMsg = "调用[" + interfaceName + "]返回信息中，getFirstArrayList为null";
				logger.error(eMsg);
				return true;
				// fex = new WebException(interfaceName, eMsg);
				// throw fex;
			} else {
				listVo = listTemp.getVos(); // 获取vo的集合
				if (0 == listVo.size()) {
					eMsg = "调用[" + interfaceName + "]返回信息中，没有具体的VO数据项";
					logger.error(eMsg);
					return true;
				}
			}
			return false;
		} else if("2".equals(rtnXmlvo.getHead().getRepcode().trim())){
			return true;
		} else {
			// 记录异常情况，并抛出
			checkReturnRootException(rtnXmlvo, interfaceName);
			return false;
		}
	}

	/**
	 * 处理返回的Rootxml异常情况，并抛出异常信息
	 * 
	 * @param xmlvo
	 *            调用接口的返回内容
	 * @param interfaceName
	 *            此类中，使用调用的接口名称
	 * @return
	 * @throws WebException
	 */
	public static void checkReturnRootException(Root xmlvo, String interfaceName)
			throws WebException {
		String eMsg = null;
		WebException fex = null;
		
		//TODO:测试期间暂时屏蔽
		if(isDebug)
			return;
		
		if ("1".equals(xmlvo.getHead().getRepcode().trim())) {
			// 判断是否返回结果的内容为空
			return;
		} else if ("0".equals(xmlvo.getHead().getRepcode().trim())) {
			eMsg = "调用[" + interfaceName + "]返回代码为[0]，异常信息：["
					+ xmlvo.getHead().getReturnMessage() + "]";
			logger.error(eMsg);
		} else if ("-1".equals(xmlvo.getHead().getRepcode().trim())) {
			eMsg = "调用[" + interfaceName + "]返回代码为-1，请联系相关人员，征管系统异常";
			logger.error(eMsg);
		} else {
			eMsg = "调用[" + interfaceName + "]返回代码为["
					+ xmlvo.getHead().getRepcode() + "]，请联系相关人员，征管系统严重异常";
			logger.error(eMsg);
		}
		fex = new WebException(interfaceName, eMsg);
		throw fex;
	}

	/**
	 * 取得”电子缴款书编号”
	 * 
	 * @param count
	 * @return
	 */
	public static List getMultiElectricTaxId(int count)
			throws WebException {
		List ids = new ArrayList();

		String sid = "EleTaxWordCommandServId"; // 接口中的sid

		// 三方协议接口输入Root
		Root xmlvo = Root.getInstance(sid, "", "");
		Properties prop = xmlvo.getProperties();
		prop.putCell("qty", Integer.toString(count));

		if (logger.isDebugEnabled()) {
			logger.debug("构造的取得电子缴款书编号输入Root为：" + xmlvo);
		}

		Root rtnRoot = invoke(xmlvo);

		// 检查返回是否数据异常
		if (checkQueryIsNull(rtnRoot, "<取得电子缴款书编号接口>")) {
			throw new WebException("<取得电子缴款书编号接口>",
					"征管接口返回数据异常，取得电子缴款书编号列表为空");
		}

		if (logger.isDebugEnabled())
			logger.debug("从接口中获取的电子缴款书编号信息：" + rtnRoot);

		// ArrayList在本接口中仅返回一个ArrayList，再获取唯一的vo
		List vos = rtnRoot.getFirstArrayList().getVos();
		for (int i = 0; i < vos.size(); i++) {
			VO vo = (VO) vos.get(i);
			ids.add(vo.getCell("word"));
		}

		return ids;
	}

	/**
	 * 只取得一个”电子缴款书编号”
	 * 
	 * @return String类型的电子缴款书编号
	 */
	public static String getElectricTaxId() throws WebException {
		return (String) getMultiElectricTaxId(1).get(0);
	}

	/**
	 * 组织扣款预处理接口报文，发往征管并获取结果信息
	 * 
	 * @param preprocessInputObj
	 *            扣款预处理接口输入对象
	 * @return String 调用接口的返回结果
	 */
	public static Root getPreprocessResultForNormal(Object preprocessInputObj)
			throws WebException {
		PreprocessInput pre = (PreprocessInput) preprocessInputObj;

		Root xmlvo = Root.getInstance(pre.getSid(), "", "");
		Properties prop = xmlvo.getProperties();

		// 判断是否省直属扣款
		if (!pre.isSzs()) {
			prop.putCell("taxpayerid", pre.getNsrbm());
		} else {
			prop.putCell("taxpayerid", pre.getSzs_nsrbm());
		}
		prop.putCell("payTypeCode", pre.getPayTypeCode());
		prop.putCell("declareDataId", pre.getDeclareDataId());
		prop.putCell("thisPayCount", pre.getPayCount());

		long time1 = System.currentTimeMillis();
		Root result = invoke(xmlvo);
		long time2 = System.currentTimeMillis();

		// 记录日志
		LogKkPack.log(pre.getNsrbm(), "扣款预处理接口", pre.getDeclareDataId(), xmlvo
				.toString(), result.toString(), time2 - time1);

		// 检查返回是否数据异常
		if (checkQueryIsNull(result, "<扣款预处理接口返回>")) {
			throw new WebException("<扣款预处理接口返回>", "征管接口返回数据异常，扣款预处理结果数据为空");
		}

		return result;
	}

	/**
	 * 解析扣款预处理的返回结果，并构造对象TaxDueDetail
	 * 
	 * @param respXmlVo
	 *            扣款预处理返回结果
	 * @param logininfo
	 *            纳税人登陆的基本信息
	 * @param bankInfo
	 *            纳税人相关的银行账号信息
	 * @return TaxDueDetail 解析后扣款预处理的结果数据
	 */
	public static TaxDueDetail getTaxDueDetail(Root respXmlVo,
			LoginInfo logininfo, BankAccountInfoForSky bankInfo)
			throws WebException {
		if (logger.isDebugEnabled()) {
			logger.debug("扣款预处理接口的返回结果是：" + respXmlVo.toString());
		}

		// 在“缴税费情况信息”页面中显示的所有信息对象
		TaxDueDetail detail = new TaxDueDetail();

		// 设置纳税人信息
		detail.setNsrbm(logininfo.getNsrbm());
		detail.setNsrmc(logininfo.getNsrmc());

		// 设置银行信息
		// detail.setBankName(bankInfo.getBankname()); // 征管的银行名称
		detail.setBankName(bankInfo.getGenbankname()); // 征管的银行名称
		detail.setBankAccount(bankInfo.getPaymentaccount());

		// 设置每一个税种税目条目
		// ArrayList在本接口中仅返回一个ArrayList
		List listVo = respXmlVo.getFirstArrayList().getVos(); // 获取vo的集合

		List detailSubs = null;
		String declaredate = respXmlVo.getProperties().getCell("declaredate");
		for (int i = 0; i < listVo.size(); i++) {
			VO vo = (VO) listVo.get(i);

			// 如果需要，则建立数组链表
			if (null == detailSubs) {
				detailSubs = new ArrayList();
			}

			// 申报明细序号
			String levydetaildataid = null;

			// 是否滞纳金，"1"，表示是滞纳金，否则是一种税目
			String isDelayMoney = vo.getCell("parenttype");
			if (isDelayMoney.trim().equals("1")) {
				// 申报明细序号
				levydetaildataid = vo.getCell("parentpointer");
			} else {
				levydetaildataid = vo.getCell("levydetaildataid");
			}

			// 创建或取出TaxDueDetailSub对象
			TaxDueDetailSub item = null;
			boolean findItem = false;
			for (int j = 0; j < detailSubs.size(); j++) {
				item = (TaxDueDetailSub) detailSubs.get(j);
				if (item.getDeclaredetaildataid().equals(levydetaildataid)) {
					findItem = true;
					break;
				}
			}

			if (!findItem) {
				// 初始化一个新对象，并填写滞纳金内容
				item = new TaxDueDetailSub();
				item.setDeclaredetaildataid(levydetaildataid);
				detailSubs.add(item);
			}

			if (isDelayMoney.trim().equals("1")) {
				// 表示是滞纳金，此时需要取出滞纳天数、滞纳金额，并保存到响应的税目中
				item.setDelayday(vo.getCell("laterdates"));// 保存滞纳天数
				item.setDelaymoney(Double.parseDouble(vo.getCell("taxdue")));
			} else {
				// 是某一个税目，不是滞纳金，则设置如下属性
				if (logger.isDebugEnabled()) {
					logger.debug("levydetaildataid: " + levydetaildataid
							+ "; taxtypecode: " + vo.getCell("taxtypecode")
							+ "; taxitemcode: " + vo.getCell("taxitemcode"));
				}
				item.setTaxtypecode(vo.getCell("taxtypecode"));
				item.setTaxitemcode(vo.getCell("taxitemcode"));
				item.setTaxtermbegin(vo.getCell("taxtermbegin"));
				item.setTaxtermend(vo.getCell("taxtermend"));
				item.setDeclaredate(declaredate);
				item.setPaymentterm(vo.getCell("paymentterm"));
				item.setTaxdue(Double.parseDouble(vo.getCell("taxdue")));
				item.setTaxfinal(Double.parseDouble(vo.getCell("taxfinal")));
			}
		}

		// 将明细信息，添加到TaxDueDetail对象
		detail.setTaxItems(detailSubs);

		return detail;
	}

	

	/**
	 * 征收类型代码 （税款类型）-名称
	 * 
	 * @param levytypecode
	 * @return
	 */
	public static String getLevyTypeCodeName(String levytypecode) {
		Map map = CodeManager.getCodeTable("levytypecode");
		String name = (String) map.get(levytypecode);
		if (name == null)
			name = "非法类型代码：" + levytypecode;
		return name;
		/*
		 * int code = 0; try { code = Integer.parseInt(levytypecode.trim()); }
		 * catch (Exception e) { return "非法类型代码：" + levytypecode; } String name =
		 * null; switch (code) { case 110: name = "正常申报"; break; case 113: name =
		 * "代扣代缴"; break; case 230: name = "汇算清缴"; break; default: name =
		 * "非法类型代码：" + levytypecode; } return name;
		 */
	}

	/**
	 * 申报类型 代码--名称转化
	 */
	public static String getDeclareMethodCodeName(String declaremethodcode) {
		int code = 0;
		try {
			code = Integer.parseInt(declaremethodcode.trim());
		} catch (Exception e) {
			return "非法类型代码：" + declaremethodcode;
		}
		String name = null;
		switch (code) {
		case 10:
			name = "上门申报";
			break;
		case 20:
			name = "邮寄申报";
			break;
		case 30:
			name = "电话申报";
			break;
		case 40:
			name = "网上申报";
			break;
		case 50:
			name = "批量扣税";
			break;
		case 60:
			name = "银行网点申报";
			break;
		case 90:
			name = "其他";
			break;
		default:
			name = "非法类型代码：" + declaremethodcode;
		}
		return name;
	}

	/**
	 * 根据缴款方式代码，获取缴款方式名称
	 * 
	 * 空或0：传统数据来源 1：pos机缴款 2：sky税库银缴款
	 * 
	 * @param paymenttypecode
	 * @return
	 */
	public static String getPaymentTypeName(String paymenttypecode) {
		if (paymenttypecode.length() == 0) {
			return "传统方式缴款";
		}
		int type = 0;
		try {
			type = Integer.parseInt(paymenttypecode);
		} catch (Exception e) {
			return "非法类型代码：" + paymenttypecode;
		}
		String name = null;
		switch (type) {
		case 0:
			name = "传统方式缴款";
			break;
		case 1:
			name = "pos机缴款";
			break;
		case 2:
			name = "税库银扣款";
			break;
		default:
			name = "非法类型代码：" + type;
		}
		return name;
	}

	/**
	 * TODO:云南地税新增方法
	 * 根据计算机编码和税费类型，获取银行账号信息
	 * 
	 * @param p_nsrbm
	 * @param p_sflxdm,01：税 02：社保费 03：工会经费
	 * @return
	 * @throws WebException
	 */
	public static BankAccountInfoForSky getBankInfo(String p_nsrbm,String p_sflxdm)
			throws WebException {
		String sid = "ThreeProtocolQueryServId"; // 接口中的sid

		// 三方协议接口输入Root
		Root xmlvo = Root.getInstance(sid, "", "");
		Properties prop = xmlvo.getProperties();
		prop.putCell("payerid", p_nsrbm);
		prop.putCell("payertypecode", p_sflxdm);

		if (logger.isDebugEnabled()) {
			logger.debug("发往三方协议接口的输入：" + xmlvo);
		}

		Root rtnRoot = invoke(xmlvo);

		// 临时取消对返回的异常检查
		if (checkQueryIsNull(rtnRoot, "三方协议查询接口")) {
			return null;
		}

		if (logger.isDebugEnabled())
			logger.debug("从接口中获取的纳税人开户行信息：" + rtnRoot);

		Properties proMap = rtnRoot.getProperties();
		String nsrglm = proMap.getCell("payerid");
		
		VO vo = (VO) rtnRoot.getFirstArrayList().getVos().get(0);

		// 取rootVo中的数据，保存到bankInfo中
		BankAccountInfoForSky bankInfo = new BankAccountInfoForSky();
		bankInfo.setWritedate(vo.getCell("writedate"));//签订时间
		bankInfo.setBankcode(vo.getCell("bankcode"));//银行代码
		bankInfo.setBankname(vo.getCell("bankname"));//银行名称
		bankInfo.setPaymentaccount(vo.getCell("bankaccountno"));//银行帐号
		bankInfo.setTaxpayerid(nsrglm);
	
		return bankInfo;
	}
	
	/**
	 * 根据管理代码获取银行账号信息
	 * 
	 * @param p_nsrbm
	 * @return
	 * @throws WebException
	 */
	public static BankAccountInfoForSky getBankInfo(String p_nsrbm)
			throws WebException {
		String sid = "ThreeProtocolQueryServId"; // 接口中的sid

		// 三方协议接口输入Root
		Root xmlvo = Root.getInstance(sid, "", "");
		Properties prop = xmlvo.getProperties();
		prop.putCell("taxpayerid", p_nsrbm);
		
		if (logger.isDebugEnabled()) {
			logger.debug("发往三方协议接口的输入：" + xmlvo);
		}

		Root rtnRoot = invoke(xmlvo);

		// 临时取消对返回的异常检查
		if (checkQueryIsNull(rtnRoot, "三方协议查询接口")) {
			return null;
		}

		if (logger.isDebugEnabled())
			logger.debug("从接口中获取的纳税人开户行信息：" + rtnRoot);

		Properties proMap = rtnRoot.getProperties();
		String nsrglm = proMap.getCell("taxpayerid");
		//String taxregcode = proMap.getCell("nsrsbh");
		// ArrayList在本接口中仅返回一个ArrayList，再获取唯一的vo
		VO vo = (VO) rtnRoot.getFirstArrayList().getVos().get(0);

		// 取rootVo中的数据，保存到bankInfo中
		BankAccountInfoForSky bankInfo = new BankAccountInfoForSky();
		bankInfo.setWritedate(vo.getCell("writedate"));//签订时间
		bankInfo.setBankcode(vo.getCell("bankcode"));//银行代码
		bankInfo.setBankname(vo.getCell("bankname"));//银行名称
		bankInfo.setPaymentaccount(vo.getCell("bankaccountno"));//银行帐号
		bankInfo.setTaxpayerid(nsrglm);
		/*****
		bankInfo.setProtocolstatus(vo.getCell("protocolstatus"));
		bankInfo.setCanceldate(vo.getCell("canceldate"));
		bankInfo.setReckbankno(vo.getCell("reckbankno"));
		bankInfo.setSkybankcode(vo.getCell("skybankcode"));
		bankInfo.setGenbankname(vo.getCell("genbankname"));
		bankInfo.setBanktypecode(vo.getCell("banktypecode"));
		bankInfo.setSkyclearingbankcode(vo.getCell("skyclearingbankcode"));
		bankInfo.setProtocolnum(vo.getCell("protocolnum"));
		bankInfo.setPayunitename(vo.getCell("payunitename")); // 缴款人名称
		
		bankInfo.setTaxregcode(taxregcode);
		***/
		return bankInfo;
	}

	/**
	 * 
	 * 获取扣款征收接口的结果
	 * 
	 * @param taxpayerid
	 *            纳税人管理码
	 * @param levydataid
	 *            征收暂存序号
	 * @param skybillno
	 *            电子缴税付款凭证字号
	 * @param resultflag
	 *            扣款结果标志
	 * @return Root 返回的Root
	 * @throws WebException
	 */
	public static Root getKkZsResult(String taxpayerid, String levydataid,
			String skybillno, String resultflag, BankAccountInfoForSky bankInfo)
			throws WebException {

		String sid = "DeductFundCommandServId"; // 接口中的sid

		// 扣款征收接口输入Root
		Root xmlvo = Root.getInstance(sid, "", "");
		Properties prop = xmlvo.getProperties();
		prop.putCell("taxpayerid", taxpayerid);
		prop.putCell("paymenttypecode",
				TaxWebConstants.DECLARE_TYPE_KK_PAYTYPECODE);// 缴款方式代码
		prop.putCell("levydataid", levydataid);
		prop.putCell("skybillno", skybillno);
		prop.putCell("resultflag", resultflag);

		// // 填写扣款银行的相关信息--应该是这个
		// prop.putCell("bankcode", bankInfo.getSkybankcode());
		// prop.putCell("bankname", bankInfo.getGenbankname());

		prop.putCell("bankcode", bankInfo.getBankcode()); // table中字段为7，sky的代码>7位
		prop.putCell("bankname", bankInfo.getGenbankname()); // 使用sky的银行名称
		prop.putCell("bankaccount", bankInfo.getPaymentaccount());

		long time1 = System.currentTimeMillis();
		// 欠税扣款征收返回
		Root rtnRoot = invoke(xmlvo);
		long time2 = System.currentTimeMillis();

		// 临时取消对返回的异常检查
		checkReturnRootException(rtnRoot, "扣款征收接口");

		if (logger.isDebugEnabled())
			logger.debug("扣款征收接口返回信息：" + rtnRoot);

		// 记录日志
		LogKkPack.log(taxpayerid, "扣款征收接口", levydataid, xmlvo.toString(),
				rtnRoot.toString(), time2 - time1);

		return rtnRoot;
	}

	/**
	 * 获取纳税人所有的开户信息
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return list 纳税人开户信息
	 */
	public static List getAllNsrkhhxx(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Object obj = session
				.getAttribute(TaxWebConstants.SESSION_ATTRIBUTE_NSRKHHXX);
		String nsrkhhxx = "";
		if (obj != null)
			nsrkhhxx = obj.toString();
		ToolXmltoList xmltoList = new ToolXmltoList();
		List list = new ArrayList();
		try {
			list = xmltoList.xmltoList(nsrkhhxx);
		} catch (Exception e) {
			if (logger.isDebugEnabled())
				logger.debug("取得纳税人开户行信息异常：" + e.toString());
		}
		return list;
	}

	/**
	 * 委托代征核定信息查询
	 * 
	 * @param request
	 * @return
	 */
	public static String getNsrwtdzcx(HttpServletRequest request)
			throws BackendServiceException {
		HttpSession session = request.getSession();
		Object obj = session
				.getAttribute(TaxWebConstants.SESSION_ATTRIBUTE_NSRWTDZXXCX);
		if (obj == null) {
			/* 委托代征核定信息查询 begin */
			Date[] periods = DateToolkit.genLastSSSQ("20", new Date());
			LoginInfo info = getNsrjbxx(request);
			Root xmlvo = Root.getInstance(SidConstants.COMMON, "QDwtdzcxBLH",
					"gov.gdlt.taxcore.taxevent.zshs.sb.qd.QDwtdzcxReqEvent");
			xmlvo.setHead("queryHandler");
			xmlvo.setBizInfo("getwtdzcx");
			Properties prop = xmlvo.getProperties();
			prop.putCell("nsrbm", info.getNsrbm());
			prop.putCell("gljgdm", info.getGljgdm());
			prop.putCell("ssqs", dateFormater.format(periods[0]));
			prop.putCell("ssqz", dateFormater.format(periods[1]));
			obj = invoke(xmlvo).toString();
			session.setAttribute(TaxWebConstants.SESSION_ATTRIBUTE_NSRWTDZXXCX,
					obj);
			/* 委托代征核定信息查询 end */
		}
		return obj.toString();
	}

	/**
	 * 查询申报期限和缴款期限, 这个不是基础数据，是在综合申报中读一行数据，调一个EAI，取申报期限和缴款期限 即： 综合申报有多少行就调这个方法多少次 ，
	 * 在综合申报的init页面调用
	 * 
	 * @param request
	 * @param nsrbm
	 *            纳税人编码
	 * @param nsrnbm
	 *            纳税人内部码
	 * @param ssrqs
	 *            所属日期始
	 * @param ssrqz
	 *            所属日期止
	 * @param gljgdm
	 *            管理机关代码
	 * @param zsxmdm
	 *            征收项目代码
	 * @param zspmdm
	 *            征收品目代码
	 * @return 申报期限和缴款期限xml格式
	 * @throws Exception
	 * @depared 应使用queryZhsbsbqx(LoginInfo info, String ssrqs, String ssrqz,
	 *          String[] zsxmdm, String[] zspmdm)来进行查询
	 */
	public static String getZhsbsbqx(String nsrbm, String ssrqs, String ssrqz,
			String zsxmdm, String nsqxdm) throws Exception {
		String sbqxDate = "";
		Root xmlvo = Root.getInstance("TaxTypeTermServId", "", "");
		Properties prop = xmlvo.getProperties();
		prop.putCell("taxpayerid", nsrbm);
		ListVO lstVo = xmlvo.addArrayList("", "");
		VO vo = lstVo.addVo();
		vo.putCell("taxTypeCode", zsxmdm);
		vo.putCell("declareTermCode", nsqxdm);// 纳税期限代码
		vo.putCell("startDate", ssrqs);
		vo.putCell("endDate", ssrqz);
		xmlvo = invoke(xmlvo);
		if (xmlvo != null && xmlvo.getFirstArrayList() != null
				&& ((ListVO) xmlvo.getFirstArrayList()).getVos().size() > 0) {
			List sbqxlstVo = ((ListVO) xmlvo.getArrayLists().get(0)).getVos();
			Object[] vos = sbqxlstVo.toArray();
			for (int i = 0; i < vos.length; i++) {
				VO sbqxvo = (VO) vos[i];
				sbqxDate = sbqxvo.getCell("limittime");
				break;
			}
		}
		return sbqxDate;
	}

	/**
	 * 查询申报期限和缴款期限, 这个不是基础数据，是在综合申报中读一行数据，调一个EAI，取申报期限和缴款期限 即： 综合申报有多少行就调这个方法多少次 ，
	 * 在综合申报的init页面调用
	 * 
	 * @param request
	 * @param info
	 *            纳税人基本信息，获取纳税人编码,纳税人内部码,管理机关代码
	 * @param ssrqs
	 *            所属日期始
	 * @param ssrqz
	 *            所属日期止
	 * @param zsxmdm
	 *            征收项目代码
	 * @param zspmdm
	 *            征收品目代码
	 * @return 申报期限和缴款期限xml格式
	 * @throws BackendServiceException
	 *             获取失败
	 */
	public static Root queryZhsbsbqx(LoginInfo info, String ssrqs,
			String ssrqz, String[] zsxmdm, String[] zspmdm)
			throws BackendServiceException {
		Root xmlvo = Root.getInstance(SidConstants.COMMON, "SBEAIzhcxBLH",
				"gov.gdlt.taxcore.taxevent.zshs.sb.dqd.QDEaiCXReqEvent");
		xmlvo.setBizInfo("estQuerySBJKQX");
		Properties prop = xmlvo.getProperties();
		prop.putCell("nsrbm", info.getNsrbm());
		prop.putCell("nsrnbm", info.getNsrnbm());
		prop.putCell("gljgdm", info.getGljgdm());

		/* 将各税种品目信息设置到 VOs 中 */
		// prop.putCell("ssrqs", ssrqs);
		// prop.putCell("ssrqz", ssrqz);
		// prop.putCell("zsxmdm", zsxmdm);
		// prop.putCell("zspmdm", zspmdm);
		return invoke(xmlvo);
	}

	/**
	 * 查询申报期限和缴款期限, 这个不是基础数据，调一个EAI，取申报期限和缴款期限 一次取多个申报期限
	 * 
	 * @param request
	 * @param nsrbm
	 *            纳税人编码
	 * @param nsrnbm
	 *            纳税人内部码
	 * @param ssrqs
	 *            所属日期始
	 * @param ssrqz
	 *            所属日期止
	 * @param gljgdm
	 *            管理机关代码
	 * @param zsxmdm
	 *            征收项目代码
	 * @param zspmdm
	 *            征收品目代码
	 * @return List 申报期限和缴款期限xml格式
	 * @throws Exception
	 */
	public static Root querysbqxList(HttpServletRequest request, String nsrbm,
			String gljgdm, List list) throws Exception {
		BuilderXml builder = new BuilderXml();
		builder.builderHeaderhasArrayList("QDgetgszsbjkqxBLH",
				"gov.gdlt.taxcore.taxevent.zshs.sb.dqd.QDEaiCXReqEvent",
				"queryHandler", "estQuerySBJKQX",
				"gov.gdlt.taxcore.taxevent.zshs.sb.dqd.QDEaiCXJGSbqxJkqxVO",
				"setGszpmList");
		HashMap map = new HashMap();
		map.put("nsrbm", nsrbm);
		map.put("gljgdm", gljgdm);
		builder.addCell(map);
		// 构造arrayList中的vo
		for (int i = 0; i < list.size(); i++) {
			HashMap maptmp = (HashMap) list.get(i);
			HashMap mapvo = new HashMap();
			mapvo.put("ssrqs", maptmp.get("ssqq"));
			mapvo.put("ssrqz", maptmp.get("ssqz"));
			mapvo.put("zsxmdm", maptmp.get("zsxmdm"));
			mapvo.put("zspmdm", maptmp.get("zspmdm"));
			builder.addArrayListVoCell(mapvo);
		}
		DirectorXml director = new DirectorXml(builder);
		String xml = director.construct(true);

		if (xml.indexOf("rootVo") > 0)
			xml = xml.substring(xml.indexOf("rootVo") - 2);

		Root xmlvo = Root.parse(xml);
		xmlvo.setSID(SidConstants.COMMON);
		return invoke(xmlvo);
	}

	

	

	

	
	/**
	 * 取得全部税种品目核定信息
	 * 
	 * @param nsrbm
	 * @return
	 * @throws WebException
	 */
	public static Root queryNsrSz(LoginInfo loginInfo, Date sssq_q, Date sssq_z)
			throws WebException {
		Root xmlvo = null;
		Properties prop = null;

		xmlvo = Root.getInstance(SidConstants.COMMON, "QDgetszpmhdxxBLH",
				"gov.gdlt.taxcore.taxevent.zshs.sb.qd.QDgetszpmhdxxReqEvent");
		xmlvo.setHead("queryHandler");
		xmlvo.setBizInfo("getallhdxx");
		prop = xmlvo.getProperties();
		prop.putCell("nsrbm", loginInfo.getNsrbm());
		prop.putCell("gljgdm", loginInfo.getGljgdm());
		prop.putCell("ssqq", dateFormater.format(sssq_q));
		prop.putCell("ssqz", dateFormater.format(sssq_z));
		prop.putCell("kqbj", "0");

		// xmlvo = invoke(xmlvo); // TODO
		// 模拟返回数据,在生产环境中要放开上面的一行代码
		String xmlData = readXmlFile("Z:/hero/trunk/engineering/doc/9_reference/接口/东莞接口/取得全部税种品目核定信息-first-output.xml");
		try {
			xmlvo = Root.parse(xmlData);
		} catch (EaiVoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return xmlvo;
	}

	/**
	 * 取得纳税人开户行信息
	 * 
	 * @param loginInfo
	 * @return
	 */
	public static Root queryNsrYh(LoginInfo loginInfo) {
		Root xmlvo = null;
		Properties prop = null;
		xmlvo = Root.getInstance(SidConstants.COMMON, "QDgetnsrkhhxxBLH",
				"gov.gdlt.taxcore.taxevent.swgl.dj.qd.QDgetnsrkhhxxReqEvent");
		xmlvo.setHead("queryHandler");
		xmlvo.setBizInfo("");
		prop = xmlvo.getProperties();
		prop.putCell("nsrbm", loginInfo.getNsrbm());
		// xmlvo = invoke(xmlvo); // TODO
		// 模拟返回数据,在生产环境中要放开上面的一行代码
		String xmlData = readXmlFile("Z:/hero/trunk/engineering/doc/9_reference/接口/东莞接口/取得纳税人开户行信息-first-output.xml");
		try {
			xmlvo = Root.parse(xmlData);
		} catch (EaiVoException e) {
			e.printStackTrace();
		}
		return xmlvo;
	}

	

	/**
	 * 取得系统当前日期,如果能取得数据库时间那是最好的
	 * 
	 * @return String 格式yyyy-mm-dd 的系统日期
	 */
	public static String getCurrTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String today = sdf.format(date);
		return today;
	}

	/**
	 * 获取系统当前日期的月份
	 * 
	 * @return
	 */
	public static String getCurrMonth() {
		Calendar CD = Calendar.getInstance();
		int month = CD.get(Calendar.MONTH) + 1;
		return month + "";
	}

	/**
	 * 获取系统当前日期的年份
	 * 
	 * @return
	 */
	public static String getCurrYear() {
		Calendar cd = Calendar.getInstance();
		return cd.get(Calendar.YEAR) + "";
	}

	/**
	 * 获取系统当前日期的日
	 * 
	 * @return
	 */
	public static String getCurrDate() {
		Calendar cd = Calendar.getInstance();
		return cd.get(Calendar.DATE) + "";
	}

	/**
	 * 取得给定日期所在月份的最后一天
	 * 
	 * @param year
	 *            int 年
	 * @param month
	 *            int 月
	 * @return String
	 * @throws ParseException
	 */
	public static String getCurrMonthEnd(String inputDate, String format)
			throws ParseException {
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		inputDate = inputDate.substring(0, 7) + "-01";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		date = df.parse(inputDate);
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		date = calendar.getTime();
		SimpleDateFormat df2 = new SimpleDateFormat(format);
		return df2.format(date);
	}

	/**
	 * 取得当前日期上月份的最后一天
	 * 
	 * @param year
	 *            int 年
	 * @param month
	 *            int 月
	 * @return String
	 */
	public static String getMonthEnd() {
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.set(calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH) + 1, 1);
		calendar.add(Calendar.MONTH, -1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		date = calendar.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);// calendar.get(Calendar.YEAR) + "-" +
		// (calendar.get(Calendar.MONTH)+1) + "-"
		// +calendar.get(calendar.DAY_OF_MONTH);
	}

	/**
	 * 取得当前日期上一个月份的第一天
	 * 
	 * @return String
	 */
	public static String getMonthBegin() {
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.set(calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH) + 1, 1);
		calendar.add(Calendar.MONTH, -2);
		date = calendar.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);// calendar.get(Calendar.YEAR) + "-" +
		// (calendar.get(Calendar.MONTH)+1) + "-" +"01";
	}

	/**
	 * 在给定的日期上增加一个月
	 * 
	 * @param date
	 *            yyyy-mm-dd格式的日期
	 * @return
	 */
	public static String getAddMonth(String date) {
		Calendar calendar = Calendar.getInstance();
		Date d1 = new Date();
		if (!date.equals("") && date.length() == 10) {
			calendar.set(Integer.parseInt(date.substring(0, 4)), Integer
					.parseInt(date.substring(5, 7)) - 1, Integer.parseInt(date
					.substring(8, 10)));
			calendar.add(Calendar.MONTH, 1);
			d1 = calendar.getTime();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			return df.format(d1);// calendar.get(Calendar.YEAR) + "-" +
			// (calendar.get(Calendar.MONTH)+1) + "-"
			// +calendar.get(Calendar.DATE);
		}
		return "";

	}

	/**
	 * 在给定的日期上减去一个月
	 * 
	 * @param date
	 *            yyyy-mm-dd格式的日期
	 * @return
	 */
	public static String getMinMonth(String date) {
		Calendar calendar = Calendar.getInstance();
		Date d1 = new Date();
		if (!date.equals("") && date.length() == 10) {
			calendar.set(Integer.parseInt(date.substring(0, 4)), Integer
					.parseInt(date.substring(5, 7)) - 1, Integer.parseInt(date
					.substring(8, 10)));
			calendar.add(Calendar.MONTH, -1);
			d1 = calendar.getTime();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			// df.format(d1);
			return df.format(d1);// calendar.get(Calendar.YEAR) + "-" +
			// (calendar.get(Calendar.MONTH)+1) + "-"
			// +calendar.get(Calendar.DATE);
		}
		return "";

	}

	/**
	 * 在给定的日期上减去一个月
	 * 
	 * @param date
	 *            yyyy-mm-dd格式的日期
	 * @return 按照指定的日期格式返回
	 * @throws ParseException
	 */
	public static String getMinMonth(String date, String inputFormat,
			String outputFormat) throws ParseException {
		Date d1 = null;
		String ss = "";
		if (!date.equals("") && date.length() >= 8 && date.length() <= 10) {
			SimpleDateFormat df = new SimpleDateFormat(inputFormat);
			d1 = df.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(d1);
			calendar.add(Calendar.MONTH, -1);
			d1 = calendar.getTime();
			DateFormat df2 = new SimpleDateFormat(outputFormat);
			ss = df2.format(d1);
		}
		return ss;
	}

	/**
	 * 比较两个日期 输入格式必须为YYYY-MM-DD
	 * 
	 * @param data1
	 * @param data2
	 * @return data1比data2早时返回true,等于data2或者晚时则 返回false
	 * @throws ParseException
	 */
	public static boolean compareDate(String data1, String data2)
			throws ParseException {
		boolean flag = false;
		if (!data1.equals("") && data1.length() >= 8 && data1.length() <= 10
				&& !data2.equals("") && data2.length() >= 8
				&& data2.length() <= 10) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date d1 = df.parse(data1);
			Date d2 = df.parse(data2);
			// System.out.println(d1.toString());
			if (d1.before(d2))
				flag = true;
			else
				flag = false;
		}
		return flag;
	}

	/**
	 * 比较两个日期月份 输入格式必须为YYYY-MM-DD
	 * 
	 * @param data1
	 *            2006-8-15
	 * @param data2
	 * @return data1所在的月比data2所在的月 早 时返回true,等于data2或者晚时返回false
	 * @throws ParseException
	 */
	public static boolean compareMonth(String data1, String data2)
			throws ParseException {
		boolean flag = false;
		if (!data1.equals("") && data1.length() >= 8 && data1.length() <= 10
				&& !data2.equals("") && data2.length() >= 8
				&& data2.length() <= 10) {
			DateFormat df = new SimpleDateFormat("yyyy-MM");
			Date d1 = df.parse(data1);
			Date d2 = df.parse(data2);
			if (d1.before(d2))
				flag = true;
			else
				flag = false;
		}
		return flag;
	}

	/**
	 * 比较 两个日期之间相差的月数 data1-data2
	 * 
	 * @param data1
	 *            输入格式 yyyy-mm-dd
	 * @param data2
	 * @return 0代表两个日期所在月相等,1代表data1-data2=1 即data1比data2晚一个月,-1相反
	 * @throws ParseException
	 */
	public static int CompareMonthNum(String data1, String data2)
			throws ParseException {

		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = df1.parse(data1);
		Date d2 = df2.parse(data2);
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();

		// calendar.set(Integer.parseInt(s1[0]),Integer.parseInt(s1[1]),Integer.parseInt(s1[2]));
		// calendar2.set(Integer.parseInt(s2[0]),Integer.parseInt(s2[1]),Integer.parseInt(s2[2]));

		calendar1.setTime(d1);
		calendar2.setTime(d2);
		int year = calendar1.get(Calendar.YEAR) - calendar2.get(Calendar.YEAR);
		int month = calendar1.get(Calendar.MONTH)
				- calendar2.get(Calendar.MONTH);

		return year * 12 + month;
	}

	public static String[] parseString(String fromString) {
		String[] toString;
		toString = fromString.split(",");
		return toString;
	}

	/**
	 * 获取纳税人 征收品目信息
	 * 
	 * @param request
	 * @return
	 * @throws WebException
	 */
	public static List getZhpm(HttpServletRequest request)
			throws WebException {
		List list = new ArrayList();
		ISbCtrlHelper sbCtrlHelper;
		ControllerResult controllerResult;
		// String xmlFromEAI;
		sbCtrlHelper = new QueryZhpmzmCtrlHelper();
		controllerResult = sbCtrlHelper.execute(null, request);
		list = (List) controllerResult.getData();
		// list = (List)request.getAttribute("queryResult");
		return list;
	}

	/**
	 * 获取纳税人 征收品目信息
	 * 
	 * @param request
	 * @return
	 * @throws WebException
	 */
	public static HashMap getZhpmzm() throws WebException {
		HashMap map = (HashMap) CodeManager.getCodeTable("zspmzmdm");
		return map;
	}

	/**
	 * 取得某税种某所属期申报状态 可以判断 是否重复申报 取得某税种某所属期申报状态-first-input.xml 若有数据返回就是重复申报
	 * 
	 * @param request
	 * @param ssqq 所属期
	 * @param ssqz 所属期
	 * @param szdm 税种代码
	 * @param zspmdm
	 * @return
	 * @throws Exception
	 */
	public static boolean getszssqZt(String nsrbm, String ssqq, String ssqz,
			String szdm, String zspmdm) throws Exception {
		Root xmlvo = Root.getInstance("DeclareStatusQueryServId", "", "");
		Properties prop = xmlvo.getProperties();
		prop.putCell("taxpayerid", nsrbm);
		ListVO lstVo = xmlvo.addArrayList("", "");
		VO vo = lstVo.addVo();
		vo.putCell("taxTypeCode", szdm);
		vo.putCell("taxItemCode", zspmdm);
		vo.putCell("taxTermBegin", ssqq);
		vo.putCell("taxTermEnd", ssqz);

		try {
			xmlvo = invoke(xmlvo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (xmlvo != null && xmlvo.getFirstArrayList() != null) {
			// 判断declarestatuscode是否为1
			List lstVos2 = ((ListVO) xmlvo.getArrayLists().get(0)).getVos();
			VO vo2 = (VO) lstVos2.get(0);
			if ("1".equals(vo2.getCell("declarestatuscode")))
				return true;
			else
				return false;
		} else {
			return false;
		}
	}

	/**
	 * 取得某税种某所属期申报状态
	 * 
	 * @param request
	 * @param List
	 *            包括多条 所属期、szdm 税种代码、zspmdm
	 * @return
	 * @throws Exception
	 */
	public static Root queryszssqZt(HttpServletRequest request, List list)
			throws Exception {
		// 判断是否重复申报
		// 调接口：取得某税种某所属期申报状态-first-input.xml 若有数据返回就是重复申报
		DirectorXml director;
		BuilderXml builder;
		builder = new BuilderXml();
		builder.builderHeaderhasArrayList(
						"QDgetsbztBLH",
						"gov.gdlt.taxcore.taxevent.zshs.sb.qd.QDgetsbztReqEvent",
						"queryHandler", null,
						" gov.gdlt.taxcore.taxevent.zshs.sb.qd.QDsbztVO",
						"setSzpmList");
		HashMap mappro = new HashMap();
		LoginInfo logininfo = getNsrjbxx(request);
		mappro.put("nsrbm", logininfo.getNsrbm());
		builder.addCell(mappro);
		HashMap maptmp;
		for (int i = 0; i < list.size(); i++) {
			maptmp = (HashMap) list.get(i);
			HashMap mapvo = new HashMap();
			mapvo.put("ssqq", maptmp.get("ssqq"));
			mapvo.put("ssqz", maptmp.get("ssqz"));
			mapvo.put("szdm", maptmp.get("zsxmdm"));
			mapvo.put("zspmdm", maptmp.get("zspmdm"));
			builder.addArrayListVoCell(mapvo);
		}
		director = new DirectorXml(builder);
		String xml = director.construct(true);
		if (xml.indexOf("rootVo") > 0)
			xml = xml.substring(xml.indexOf("rootVo") - 2);
		if (logger.isDebugEnabled())
			logger.debug("取得某税种某所属期申报状态,调eai的xml：" + xml);
		Root xmlvo = Root.parse(xml);
		xmlvo.setSID(SidConstants.COMMON);
		return invoke(xmlvo);
	}

	/**
	 * 计算所属期时需要加、减月份 例如传2006-06-30 减1个月 返回2006－05－31
	 * 
	 * @param rq
	 * @param addMonth
	 * @return
	 */
	public static String getMinusMonth(String rq, int addMonth)
			throws WebException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		try {
			Date date = sdf.parse(rq);
			cal.setTime(date);
			cal.add(Calendar.MONTH, addMonth);
			rq = sdf.format(cal.getTime());
		} catch (Exception e) {
			throw new WebException(
					WebExceptionDefine.FSTAX_MVC_XML_SSYW_FAIL, "取所属期异常",
					e);
		}
		return rq;

	}

	/**
	 * 计算所属期时需要加、减日期 例如传2006-06-01 减1天 返回2006－05－31
	 * 
	 * @param rq
	 * @param addMonth
	 * @return
	 */
	public static String getMinusDay(String rq, int day)
			throws WebException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		try {
			Date date = sdf.parse(rq);
			cal.setTime(date);
			cal.add(Calendar.DATE, day);
			rq = sdf.format(cal.getTime());
		} catch (Exception e) {
			throw new WebException(
					WebExceptionDefine.FSTAX_MVC_XML_SSYW_FAIL, "取所属期异常",
					e);
		}
		return rq;

	}



	
	/**
	 * 根据zspm,zsxm,nsqxdm 查询全部税种品目中对应的核定税种起始日期： hdqsrq
	 * 
	 * @throws EaiVoException
	 * 
	 */
	public static String gethdqsrq(HttpServletRequest request, String zsxmdm,
			String zspmdm, String nsqxdm) {
		String xmlString = getNsrSzxx(request);
		String hdqsrq = "";
		try {
			Root xmlvo = Root.parse(xmlString);
			List lstVos = ((ListVO) xmlvo.getArrayLists().get(0)).getVos();
			for (int i = 0; i < lstVos.size(); i++) {
				VO vo = (VO) lstVos.get(i);
				if (vo.getCell("szdm").equals(zsxmdm)
						&& vo.getCell("zspmdm").equals(zspmdm)
						&& vo.getCell("nsqxdm").equals(nsqxdm)) {
					hdqsrq = vo.getCell("hdqsrq");
					break;
				}
			}
		} catch (EaiVoException e) {
		}
		return hdqsrq;
	}


	/**
	 * 获取当月的月初和月末日期
	 * @return  String[] 月初;月末
	 */
	public static String[] getBeginAndEndDate() {

		String[] arryDate = new String[2];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			String startdate = sdf.format(new Date()).substring(0, 7) + "-01";
			arryDate[0] = startdate;
			date = sdf.parse(startdate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.DAY_OF_YEAR, -1);
			String enddate = sdf.format(cal.getTime());
			arryDate[1] = enddate;
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return arryDate;
	}
	/**
	 * 取得纳税人当前时间的所属时期起/止
	 * @param nsqxdm 纳税期限代码 
	 * 		01 按月 02 按季 03 按年 04 按次 10 按半年
	 * @return 所属时期起/止
	 */
	public  static String[] getSssq(String nsqxdm){
		return getLastPeriod(nsqxdm);
	}

	/**
	 * 根据纳税期限产生上一期的所属期
	 * 
	 * @param nsqxdm
	 * [云南的纳税期限代码(征期类型 lev_levydatetype)
	 *            按月  01,02,03,19
	 *            按季  04,05,06,20
	 *            按年  09,10,11,15,16,17,18
	 *            按半年 07,08
	 *            按次 12,13,14
	 * ]
	 * [陕西的纳税期限代码
	 *            01 按月  04 按次 05 06 11 12 
	 *            02 按季  07
	 *            03 按年  10
	 *            08 按半年 
	 *            
	 *            
	 *            	01	15	按月（每月终了后10）                 
					02	15	按季（每季终了后15日）                 
					03	45	按年（每年终了后45）                    
					04		按次申报                   
					05	10	车船税申报期限（3月/9月/的10号前）                   
					06	10	房产税申报期限（4月/10月的全月）
					07	15	按季（每季终了后10日）                 
					08	10	按半年(1月/7月)
					10	31	按年（每年终了后1月份全月）
					11	15	按月（每月终了后15日）
					12	15	按月（每月终了后7日）
	 * ]
	 * @return ssq[] 0：ssqq 1：ssqz
	 */
	public static String[] getLastPeriod(String nsqxdm) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String[] ssq = new String[2];
		Date date;
		try {
			if (nsqxdm == null || nsqxdm.trim().equals("")) {
				nsqxdm = "01";
			}
			// String nsqxDmLx = nsqxdm.substring(0, 1);
			String nsqxDmLx = nsqxdm;
			// String logicDmLX = nsqxdm.substring(1, 5);
			if ("04".equals(nsqxDmLx)||"05".equals(nsqxDmLx)||"06".equals(nsqxDmLx)||"20".equals(nsqxDmLx)) {
				// 是按季报,默认上季 : 季初10天 Q0101_10; 季后10日 Q0001_10;2008-01-01
				date = sdf
						.parse(sdf.format(new Date()).substring(0, 7) + "-01");
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.MONTH, -3);
				date = cal.getTime();
				String tempdate = sdf.format(date);
				if (cal.get(Calendar.MONTH) < 3) {
					ssq[0] = tempdate.substring(0, 4) + "-01-01";
					ssq[1] = tempdate.substring(0, 4) + "-03-31";
				} else if (cal.get(Calendar.MONTH) < 6) {
					ssq[0] = tempdate.substring(0, 4) + "-04-01";
					ssq[1] = tempdate.substring(0, 4) + "-06-30";
				} else if (cal.get(Calendar.MONTH) < 9) {
					ssq[0] = tempdate.substring(0, 4) + "-07-01";
					ssq[1] = tempdate.substring(0, 4) + "-09-30";
				} else {
					ssq[0] = tempdate.substring(0, 4) + "-10-01";
					ssq[1] = tempdate.substring(0, 4) + "-12-31";
				}
			} else if ("09".equals(nsqxDmLx)
					||"10".equals(nsqxDmLx)
					||"11".equals(nsqxDmLx)
					||"15".equals(nsqxDmLx)
					||"16".equals(nsqxDmLx)
					||"17".equals(nsqxDmLx)
					||"18".equals(nsqxDmLx)) {
				// 年报:
				date = sdf
						.parse(sdf.format(new Date()).substring(0, 7) + "-01");
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.MONTH, -12);
				date = cal.getTime();
				String tempdate = sdf.format(date);
				ssq[0] = tempdate.substring(0, 4) + "-01-01";
				ssq[1] = tempdate.substring(0, 4) + "-12-31";
			} else if ("07".equals(nsqxDmLx)||"08".equals(nsqxDmLx)) {
				// 半年
				date = sdf
						.parse(sdf.format(new Date()).substring(0, 7) + "-01");
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.MONTH, -6);
				date = cal.getTime();
				String tempdate = sdf.format(date);
				if (cal.get(Calendar.MONTH) < 6) { // getMonth时，6月返回5
					ssq[0] = tempdate.substring(0, 4) + "-01-01";
					ssq[1] = tempdate.substring(0, 4) + "-06-30";
				} else {
					ssq[0] = tempdate.substring(0, 4) + "-07-01";
					ssq[1] = tempdate.substring(0, 4) + "-12-31";
				}
			} else {
				// 按月，默认上月(按次与按月的所期一致)
				date = sdf
						.parse(sdf.format(new Date()).substring(0, 7) + "-01");
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.MONTH, -1);
				ssq[0] = sdf.format(cal.getTime());
				cal.setTime(date);
				cal.add(Calendar.DATE, -1);
				ssq[1] = sdf.format(cal.getTime());
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ssq;
	}
	
	/**
	 * 根据纳税期限征收期限起，levydatebegin
	 * 
	 * @param nsqxdm
	 *	02	按月申报缴纳，于次月10日内
	 *	03	按月申报缴纳，于次月15日内
	 *	05	按季申报缴纳，于本季度终了后10日内
	 *	06	按季申报缴纳，于本季度终了后15日内
	 *	07	按半年申报缴纳，于本期120日内
	 *	08	按半年申报缴纳，于期满后10日内
	 *	09	按年申报缴纳，于次年45日内
	 *	10	按年申报缴纳，于本年120日内
	 *	11	按年申报缴纳，于次年90日内
	 *	12	按次申报缴纳，于次月15日内
	 *	13	按次申报缴纳（定额加发票）
	 *	14	按次申报缴纳，于次月10日内
	 *	15	按年申报缴纳，于次年120日内
	 *	16	按年申报缴纳，于本年1日至151日内
	 *	17	按年申报缴纳，于次年1日至150日内
	 *	18	按年申报缴纳，于本年1日至360日内
	 *	19	按月申报缴纳，于当月1日至30日内
	 *	20	按季申报缴纳，于本季度1日至60日内
	 *	23	按半年申报缴纳，于期满后15日内
	 * @return ssq[] 0：ssqq 1：ssqz
	 */
	public static String getLevyDateBegin(String nsqxdm) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		String levyDateBegin ="";
		try {
			if (nsqxdm == null || nsqxdm.trim().equals("")) {
				nsqxdm = "03";
			}
			String nsqxDmLx = nsqxdm;
			if ("04".equals(nsqxDmLx)||"05".equals(nsqxDmLx)||"06".equals(nsqxDmLx)||"20".equals(nsqxDmLx)) {
				// 是按季报,默认上季 : 季初10天 Q0101_10; 季后10日 Q0001_10;2008-01-01
				date = sdf.parse(sdf.format(new Date()).substring(0, 7) + "-01");
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				date = cal.getTime();
				String tempdate = sdf.format(date);
				if (cal.get(Calendar.MONTH) < 3) {
					levyDateBegin = tempdate.substring(0, 4) + "-01-01";
				} else if (cal.get(Calendar.MONTH) < 6) {
					levyDateBegin = tempdate.substring(0, 4) + "-04-01";
				} else if (cal.get(Calendar.MONTH) < 9) {
					levyDateBegin = tempdate.substring(0, 4) + "-07-01";
				} else {
					levyDateBegin = tempdate.substring(0, 4) + "-10-01";
				}
			} else if ("09".equals(nsqxDmLx)||"10".equals(nsqxDmLx)||"11".equals(nsqxDmLx)||"15".equals(nsqxDmLx)||"16".equals(nsqxDmLx)||"17".equals(nsqxDmLx)||"18".equals(nsqxDmLx)) {
				// 年报:
				date = sdf.parse(sdf.format(new Date()).substring(0, 7) + "-01");
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				date = cal.getTime();
				String tempdate = sdf.format(date);
				levyDateBegin = tempdate.substring(0, 4) + "-01-01";
			} else if ("07".equals(nsqxDmLx)||"08".equals(nsqxDmLx)||"23".equals(nsqxDmLx)) {// 半年
				date = sdf.parse(sdf.format(new Date()).substring(0, 7) + "-01");
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				date = cal.getTime();
				String tempdate = sdf.format(date);
				if (cal.get(Calendar.MONTH) < 6) { // getMonth时，6月返回5
					levyDateBegin = tempdate.substring(0, 4) + "-01-01";
				} else {
					levyDateBegin = tempdate.substring(0, 4) + "-07-01";
				}
			} else{
				// 按月，默认上月(按次与按月的所期一致)
				date = sdf.parse(sdf.format(new Date()).substring(0, 7) + "-01");
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				levyDateBegin = sdf.format(cal.getTime());
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return levyDateBegin;
	}
	/**
	 * 根据纳税期限产生上一期的所属期
	 * 
	 * @param nsqxdm
	 * @return ssq[] 0：ssqq 1：ssqz
	 */
	public static String[] getLastPeriod_old(String nsqxdm) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String[] ssq = new String[2];
		Date date;
		try {
			if (nsqxdm == null || nsqxdm.trim().equals("")) {
				nsqxdm = "M3123";
			}
			String nsqxDmLx = nsqxdm.substring(0, 1);
			// String logicDmLX = nsqxdm.substring(1, 5);
			if ("Q".equals(nsqxDmLx)) {
				// 是按季报,默认上季 : 季初10天 Q0101_10; 季后10日 Q0001_10;2008-01-01
				date = sdf
						.parse(sdf.format(new Date()).substring(0, 7) + "-01");
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.MONTH, -3);
				date = cal.getTime();
				String tempdate = sdf.format(date);
				if (cal.get(Calendar.MONTH) < 3) {
					ssq[0] = tempdate.substring(0, 4) + "-01-01";
					ssq[1] = tempdate.substring(0, 4) + "-03-31";
				} else if (cal.get(Calendar.MONTH) < 6) {
					ssq[0] = tempdate.substring(0, 4) + "-04-01";
					ssq[1] = tempdate.substring(0, 4) + "-06-30";
				} else if (cal.get(Calendar.MONTH) < 9) {
					ssq[0] = tempdate.substring(0, 4) + "-07-01";
					ssq[1] = tempdate.substring(0, 4) + "-09-30";
				} else {
					ssq[0] = tempdate.substring(0, 4) + "-10-01";
					ssq[1] = tempdate.substring(0, 4) + "-12-31";
				}
			} else if ("Y".equals(nsqxDmLx)) {
				// 年报:
				date = sdf
						.parse(sdf.format(new Date()).substring(0, 7) + "-01");
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.MONTH, -12);
				date = cal.getTime();
				String tempdate = sdf.format(date);
				ssq[0] = tempdate.substring(0, 4) + "-01-01";
				ssq[1] = tempdate.substring(0, 4) + "-12-31";
			} else if ("H".equals(nsqxDmLx)) {
				// 半年
				date = sdf
						.parse(sdf.format(new Date()).substring(0, 7) + "-01");
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.MONTH, -6);
				date = cal.getTime();
				String tempdate = sdf.format(date);
				if (cal.get(Calendar.MONTH) < 6) { // getMonth时，6月返回5
					ssq[0] = tempdate.substring(0, 4) + "-01-01";
					ssq[1] = tempdate.substring(0, 4) + "-06-30";
				} else {
					ssq[0] = tempdate.substring(0, 4) + "-07-01";
					ssq[1] = tempdate.substring(0, 4) + "-12-31";
				}
			} else {
				// 是按月报，默认上月 :M0001_10
				date = sdf
						.parse(sdf.format(new Date()).substring(0, 7) + "-01");
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.MONTH, -1);
				ssq[0] = sdf.format(cal.getTime());
				cal.setTime(date);
				cal.add(Calendar.DATE, -1);
				ssq[1] = sdf.format(cal.getTime());
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ssq;
	}

	/**
	 * 根据日期，按照指定的纳税期限产生所属时期起止
	 * 
	 * @param nsqxdm
	 *            纳税期限代码（按月、季、年等）：20月，30季，40年，41半年，50次（视同月处理）
	 * @param date
	 *            日期, 不一定是月初，只要在某期间内的一个日期
	 * @return 包含该日期的所属时期：0所属时期起，1所属时期止
	 */
	public static Date[] genSSSQ(String nsqxdm, Date date) {
		if (nsqxdm == null) {
			logger.warn("纳税期限为null，无法处理");
			return null;
		}
		if (date == null) {
			logger.warn("指定日期为null，无法处理");
			return null;
		}

		Calendar calArg = Calendar.getInstance();
		calArg.setTime(date);
		calArg.set(Calendar.DAY_OF_MONTH, 1);
		Calendar calStart = (Calendar) calArg.clone();
		Calendar calEnd = (Calendar) calArg.clone();

		/* 分类处理 */
		if (nsqxdm.equals("20") || nsqxdm.equals("50")) { // 按月或按次申报
			// nothing to do
		} else if (nsqxdm.equals("30")) { // 按季申报
			switch (calArg.get(Calendar.MONTH)) {
			case 0:
			case 1:
			case 2:
				calStart.set(Calendar.MONTH, 0);
				calEnd.set(Calendar.MONTH, 2);
				break;
			case 3:
			case 4:
			case 5:
				calStart.set(Calendar.MONTH, 3);
				calEnd.set(Calendar.MONTH, 5);
				break;
			case 6:
			case 7:
			case 8:
				calStart.set(Calendar.MONTH, 6);
				calEnd.set(Calendar.MONTH, 8);
				break;
			default:
				calStart.set(Calendar.MONTH, 9);
				calEnd.set(Calendar.MONTH, 11);
				break;
			}
		} else if (nsqxdm.equals("41")) { // 按半年申报
			if (calArg.get(Calendar.MONTH) < 6) {
				calStart.set(Calendar.MONTH, 0);
				calEnd.set(Calendar.MONTH, 5);
			} else {
				calStart.set(Calendar.MONTH, 6);
				calEnd.set(Calendar.MONTH, 11);
			}
		} else if (nsqxdm.equals("40")) { // 按年申报
			calStart.set(Calendar.MONTH, 0);
			calEnd.set(Calendar.MONTH, 11);
		} else {
			logger.warn("指定纳税期限无法识别：" + nsqxdm);
			return null;
		}

		calStart.set(Calendar.DAY_OF_MONTH, 1);
		calEnd.set(Calendar.DAY_OF_MONTH, calEnd
				.getActualMaximum(Calendar.DAY_OF_MONTH));
		return new Date[] { calStart.getTime(), calEnd.getTime() };
	}

	/**
	 * 对 金额 进行格式化 例如： format(value,"0.00");
	 * 
	 * @param value
	 *            值
	 * @param format
	 *            格式化文本
	 * @param valueType
	 *            值的类型(datetime, currency, decimal, bool)
	 * @return 格式化后的值
	 * @throws XbtTagException
	 *             异常
	 */
	public static String format(String value, String format) {
		if (value != null && format != null && value.trim().length() != 0) {
			try {
				int innerCode = 200400; // 标签：WriteTag.java第636行也写死了
				// if ("decimal".equalsIgnoreCase(valueType)) {
				Double tempValue = null;
				try {
					tempValue = new Double(value);
				} catch (NumberFormatException e) {
					logger.warn("value can not be parsed to java.lang.Double, "
							+ "the value is \"" + value
							+ "\", the format is \"" + format + "\""
							+ ", the valueType is decimal. will return "
							+ "un-formatted-value \"" + value + "\"", e);
				}
				if (tempValue != null) {
					value = new DataFormatHelper().format(tempValue, innerCode,
							format);
				}
				// }
			} catch (Exception e) {
				logger.warn(
						"an error occured while formating value, the value is \""
								+ value + "\", the format is \"" + format
								+ "\""
								+ ", the valueType is decimal. will return "
								+ "un-formatted-value \"" + value + "\"", e);
				value = "0.00";
			}
		} else
			value = "0.00";

		// 去掉小数后面的0，保留2位小数，例如0.00050 变成0.0005 将0.2000 变成 0.20
		if (value.indexOf(".") > 0 && (value.indexOf(".") + 3) < value.length()) {
			while (value.endsWith("0")
					&& (value.indexOf(".") + 2) < value.length()) {
				value = value.substring(0, value.length() - 1);
			}
		}

		return value;
	}

	/**
	 * 房产登记资料查询
	 * 
	 * @param String
	 *            gljgdm 管理机关代码
	 * @param request
	 *            HttpServletRequest
	 * @return 登记资料查询结果
	 */
	/*
	 * public static String getFcdjzlcx(HttpServletRequest request, String
	 * gljgdm) throws Exception { HttpSession session = request.getSession();
	 * Object obj =
	 * session.getAttribute(TaxWebConstants.SESSION_ATTRIBUTE_NSRFCDJZLXXCX);
	 * if(obj == null ){ LoginInfo logininfo = getNsrjbxx(request);
	 * BuilderXml builder = new BuilderXml();
	 * builder.builderHeader("QDfcdjcxBLH",
	 * "gov.gdlt.taxcore.taxevent.swgl.dj.qd.QDfcdjcxReqEvent", "queryHandler",
	 * "getfcdjxx"); HashMap map = new HashMap(); map.put("nsrbm",
	 * logininfo.getNsrbm()); map.put("gljgdm", gljgdm); builder.addCell(map);
	 * DirectorXml director = new DirectorXml(builder); String xml =
	 * director.construct(); request.setAttribute("xml", xml); ISbCtrlHelper
	 * sbCtrlHelper = new ZhsbSelectSwjgCtrlHelper(); ControllerResult
	 * controllerResult = sbCtrlHelper.execute(null, request); String xmlFromEAI =
	 * (String) controllerResult.getData(); if (logger.isDebugEnabled()) {
	 * logger.debug(" 房产登记资料查询：" + xmlFromEAI); }
	 * session.setAttribute(TaxWebConstants.SESSION_ATTRIBUTE_NSRFCDJZLXXCX,
	 * xmlFromEAI); } return obj.toString(); }
	 */

	/**
	 * 房产登记资料查询
	 * 
	 * @param String
	 *            gljgdm 管理机关代码
	 * @param request
	 *            HttpServletRequest
	 * @return 登记资料查询结果
	 */
	public static String getFcdjzlcx(HttpServletRequest request, String gljgdm)
			throws Exception {
		HttpSession session = request.getSession();
		Object obj = session
				.getAttribute(TaxWebConstants.SESSION_ATTRIBUTE_NSRFCDJZLXXCX);
		if (obj == null) {
			Root xmlvo = Root.getInstance(SidConstants.COMMON, "QDfcdjcxBLH",
					"gov.gdlt.taxcore.taxevent.swgl.dj.qd.QDfcdjcxReqEvent");
			xmlvo.setHead("queryHandler");
			xmlvo.setBizInfo("getfcdjxx");
			LoginInfo logininfo = getNsrjbxx(request);
			Properties prop = xmlvo.getProperties();
			prop.putCell("nsrbm", logininfo.getNsrbm());
			prop.putCell("gljgdm", gljgdm);
			obj = invoke(xmlvo).toString();
			session.setAttribute(
					TaxWebConstants.SESSION_ATTRIBUTE_NSRFCDJZLXXCX, obj
							.toString());
		}
		return obj.toString();
	}

	/**
	 * 土地登记资料查询
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 登记资料查询结果
	 */
	public static String getTddjzlcx(HttpServletRequest request, String gljgdm)
			throws Exception {
		HttpSession session = request.getSession();
		Object obj = session
				.getAttribute(TaxWebConstants.SESSION_ATTRIBUTE_NSRTDDJZLXXCX);
		if (obj == null) {
			Root xmlvo = Root.getInstance(SidConstants.COMMON, "QDtddjcxBLH",
					"gov.gdlt.taxcore.taxevent.swgl.dj.qd.QDfcdjcxReqEvent");
			xmlvo.setHead("queryHandler");
			xmlvo.setBizInfo("gettddjxx");
			LoginInfo logininfo = getNsrjbxx(request);
			Properties prop = xmlvo.getProperties();
			prop.putCell("nsrbm", logininfo.getNsrbm());
			prop.putCell("gljgdm", gljgdm);
			obj = invoke(xmlvo);
			/*
			 * LoginInfo logininfo = getNsrjbxx(request); BuilderXml
			 * builder = new BuilderXml(); builder.builderHeader("QDtddjcxBLH",
			 * "gov.gdlt.taxcore.taxevent.swgl.dj.qd.QDfcdjcxReqEvent",
			 * "queryHandler", "gettddjxx"); HashMap map = new HashMap();
			 * map.put("nsrbm", logininfo.getNsrbm()); map.put("gljgdm",
			 * gljgdm); builder.addCell(map); DirectorXml director = new
			 * DirectorXml(builder); String xml = director.construct();
			 * request.setAttribute("xml", xml); ISbCtrlHelper sbCtrlHelper =
			 * new TdsysTjCtrlHelper(); ControllerResult controllerResult =
			 * sbCtrlHelper.execute(null, request); String xmlFromEAI = (String)
			 * controllerResult.getData(); if (logger.isDebugEnabled()) {
			 * logger.debug(" 土地登记资料查询：" + xmlFromEAI); }
			 */
			session.setAttribute(
					TaxWebConstants.SESSION_ATTRIBUTE_NSRTDDJZLXXCX, obj
							.toString());
		}
		return obj.toString();
	}

	/**
	 * 获取扣款结果
	 * 
	 * @param request
	 * @param pzxh
	 *            申报流水后
	 * @return 扣款结果
	 * @throws Exception
	 */
	public static String getKK(HttpServletRequest request, String pzxh)
			throws Exception {
		LoginInfo logininfo = getNsrjbxx(request);
		BuilderXml builder = new BuilderXml();
		builder.builderHeader("QDprocessKKBLH",
				"gov.gdlt.taxcore.taxevent.zshs.zs.dqd.QDprocessKKReqEvent",
				"queryHandler", "null");
		HashMap map = new HashMap();
		map.put("nsrbm", logininfo.getNsrbm());
		map.put("jkfsdm", logininfo.getSkjkfs());
		map.put("pzxh", pzxh);
		builder.addCell(map);
		DirectorXml director = new DirectorXml(builder);
		String xml = director.construct();
		request.setAttribute("xml", xml);
		ISbCtrlHelper sbCtrlHelper = new ZhsbSelectSwjgCtrlHelper();
		ControllerResult controllerResult = sbCtrlHelper.execute(null, request);
		String xmlFromEAI = (String) controllerResult.getData();
		if (logger.isDebugEnabled()) {
			logger.debug("扣款返回结果：" + xmlFromEAI);
		}
		return xmlFromEAI;
	}

	/**
	 * 纳税核定查询
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 纳税核定查询
	 */
	public static String getNshdcx(HttpServletRequest request) throws Exception {
		LoginInfo logininfo = getNsrjbxx(request);
		BuilderXml builder = new BuilderXml();
		builder.builderHeader("QDgetszpmhdxxBLH",
				"gov.gdlt.taxcore.taxevent.zshs.sb.qd.QDgetszpmhdxxReqEvent",
				"queryHandler", "nshdcx");
		HashMap map = new HashMap();
		map.put("nsrbm", logininfo.getNsrbm());
		builder.addCell(map);
		DirectorXml director = new DirectorXml(builder);
		String xml = director.construct();

		request.setAttribute("xml", xml);
		NshdcxCtrlHelper sbCtrlHelper = new NshdcxCtrlHelper();
		ControllerResult controllerResult = sbCtrlHelper.execute(null, request);
		String xmlFromEAI = (String) controllerResult.getData();
		if (logger.isDebugEnabled()) {
			logger.debug(" 纳税核定查询：" + xmlFromEAI);
		}
		return xmlFromEAI;
	}

	/**
	 * 税务人员信息查询-first-output.xml
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 全部税种品目核定信息
	 */
	public static String querySwry(HttpServletRequest request, String userid)
			throws Exception {
		BuilderXml builder = new BuilderXml();
		builder.builderHeader("QDswryxxcxBLH",
				"gov.gdlt.taxcore.taxevent.zshs.sb.qd.QDswryxxcxReqEvent",
				"queryHandler", "getswryxx");
		HashMap map = new HashMap();
		map.put("userid", userid);
		builder.addCell(map);
		DirectorXml director = new DirectorXml(builder);
		String xml = director.construct();

		request.setAttribute("xml", xml);
		ISbCtrlHelper sbCtrlHelper = new ZhsbSelectSwjgCtrlHelper();
		ControllerResult controllerResult = sbCtrlHelper.execute(null, request);
		String xmlFromEAI = (String) controllerResult.getData();
		if (logger.isDebugEnabled()) {
			logger.debug(" 税务人员信息查询:" + xmlFromEAI);
		}
		return xmlFromEAI;
	}

	public static TreeMap hashMaptoTreeMap(HashMap hm) {
		TreeMap tm = new TreeMap();
		if (hm != null) {
			Iterator iter = (Iterator) hm.keySet().iterator();
			String key = "";
			while (iter.hasNext()) {
				key = (String) iter.next();
				tm.put(key, hm.get(key));
			}
		}
		return tm;
	}

	/**
	 * 根据纳税人区域获取经营项目名称(没用到了）
	 * 
	 * @param request
	 * @param gljgdm
	 * @return
	 * @throws WebException
	 */
	public static List getJyxm(HttpServletRequest request, String gljgdm)
			throws WebException {
		List list = new ArrayList();
		/*
		 * ISbCtrlHelper sbCtrlHelper; ControllerResult controllerResult;
		 * request.setAttribute("gljgdm", gljgdm);
		 * //request.setAttribute("jyxmdm",jyxmdm); sbCtrlHelper = new
		 * FpqjsbQueryJyxmCtrlHelper(); controllerResult =
		 * sbCtrlHelper.execute(null, request); list = (List)
		 * controllerResult.getData(); //if(list.size()>0){ // jyxmmc=
		 * ((TDmGyJyxm)list.get(0)).getMc(); //}
		 */
		return list;
	}

	/**
	 * 把经营项目名称放到hashmap中（没用到了）
	 * 
	 * @param jyxmList
	 * @return
	 * @throws WebException
	 */
	public static HashMap getJyxmmc(List jyxmList) throws WebException {

		HashMap map = new HashMap();
		/*
		 * for (int i = 0; i < jyxmList.size(); i++) { TDmGyJyxm jyxm =
		 * ((TDmGyJyxm) jyxmList.get(i)); map.put(jyxm.getJyxmDm(),
		 * jyxm.getMc()); }
		 */
		return map;

	}

	/**
	 * 调用后台服务
	 * 
	 * @param xmlvo
	 *            请求数据文件
	 * @return 业务处理结果
	 */
	public static Root invoke(Root xmlvo) throws BackendServiceException {
		Root retXmlvo = null;
		try {
			retXmlvo = DelegateManager.invoke(xmlvo);
			if (logger.isInfoEnabled()) {
				logger.info("调用征管输入的参数报文：" + xmlvo);
				logger.info("调用征管输出的报文：" + retXmlvo);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			BackendServiceException exBs = new BackendServiceException(
					WebExceptionDefine.FSTAX_MVC_SERVICE_INVOKE_FAILED,
					"调用核心服务SID:" + xmlvo.getSID() + "失败，核心系统返回异常，可能正在维护中。");
			exBs.log(logger);
			exBs.setEaiRepcode("-1");
			exBs.setEaiRepmessage("调用核心服务SID:" + xmlvo.getSID()
					+ "失败，核心系统返回异常，可能正在维护中。");
			throw exBs;
		}

		String repcode = retXmlvo.getHead().getRepcode();
		if (repcode == null || repcode.length() == 0) {
			repcode = "";
			retXmlvo.getHead().setRepcode(repcode);
			return retXmlvo;
		} else if ("0".equals(repcode)||"1".equals(repcode) || "2".equals(repcode)) {
			return retXmlvo;
		} else {
			BackendServiceException exBs = null;
			if (repcode.equals("88017")) {
				exBs = new BackendServiceException(
						WebExceptionDefine.FSTAX_MVC_SERVICE_INVOKE_FAILED,
						"核心服务调用失败，核心系统可能正在维护中。");
			} else {
				exBs = new BackendServiceException(
						WebExceptionDefine.FSTAX_MVC_SERVICE_INVOKE_FAILED,
						"系统通信异常。");
			}
			exBs.setEaiRepcode(repcode);
			exBs.setEaiRepmessage(retXmlvo.getHead().getReturnMessage());
			exBs.log(logger);
		}
		return retXmlvo;
	}
	
	/**
	 * 社保取缴费单位所有社保号以及社保号码对应得登记状态 单位所有社保号信息查询
	 * 
	 * @param String
	 *            gljgdm 纳税人编码
	 * @param request
	 *            HttpServletRequest
	 * @return 登记资料查询结果
	 */
	public static String getDwsbhzt(HttpServletRequest request)
			throws Exception {
		HttpSession session = request.getSession();
		Object obj = session
				.getAttribute(TaxWebConstants.SESSION_ATTRIBUTE_NSRDWSYSBHXXCX);
		if (obj == null) {
			Root xmlvo = Root
					.getInstance(SidConstants.COMMON, "QDSFgetJfdwxxBLH",
							"gov.gdlt.taxcore.taxevent.sfgl.sfdj.qd.QDgetJfdwxxReqEvent");
			xmlvo.setHead("queryHandler");
			xmlvo.setBizInfo("getJfdwxx");
			LoginInfo logininfo = getNsrjbxx(request);
			Properties prop = xmlvo.getProperties();
			prop.putCell("nsrbm", logininfo.getNsrbm());
			obj = invoke(xmlvo).toString();
			session.setAttribute(
					TaxWebConstants.SESSION_ATTRIBUTE_NSRDWSYSBHXXCX, obj
							.toString());
		}
		return obj.toString();
	}

	/**
	 * 取两天相差的天数， 即date1 - date2
	 * 
	 * @param date1
	 * @param date2
	 * @return 相差的天数
	 */
	public static long dayMinus(Date date1, Date date2) {
		long dayInt = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		/** 去掉时、分、秒 * */
		try {
			date1 = sdf.parse(sdf.format(date1));
			date2 = sdf.parse(sdf.format(date2));
			long l1 = date1.getTime();
			long l2 = date2.getTime();
			dayInt = (l1 - l2) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			dayInt = 0;
		}
		return dayInt;
	}

	/**
	 * 获取EAI的平均响应时间
	 * 
	 * @return -1:EAI不可用，0:查询失败，>0:EAI的平均响应时间
	 */
	public static long getTimeOfEAI() {
		try {
			Root root = invoke(Root.getInstance("foresee.eai.time",
					"FstaxTimeOfEAI", null));
			return Long.parseLong(root.getProperties().getCell("eai-time"));
		} catch (Exception ex) {
			return 0;
		}
	}

	/**
	 * 根据身份证 号码判断 性别
	 * 
	 * @param sfzhm
	 * @return
	 */
	public static String getGender(String sfzhm) {
		// 国标规定15位身份证号码 最后一位,18位身份证号码倒数第二位, 单数 男，双数女,男:1, 女:0
		int flag = 0;
		// 有些身份证号码最后一位是X，代表罗马数字的10，下面一句会抛异常
		String xb = "";
		if (sfzhm.length() == 18) {
			xb = sfzhm.substring(sfzhm.length() - 2, sfzhm.length() - 1);
		} else if (sfzhm.length() == 15) {
			xb = sfzhm.substring(sfzhm.length() - 1);
		}
		// System.out.println("sfzhm="+sfzhm+"xb="+xb);
		try {
			flag = Integer.parseInt(xb);
		} catch (Exception e) {
			return "1";
		}
		if (flag % 2 == 0) {
			return "1";
		} else
			return "0";
	}

	/**
	 * 把申报明细（如,0101@0@1200@0@20061234,0402*0*1200*0)转化为hashmap
	 * 
	 * @param sbmx
	 * @return
	 */
	public static HashMap spiltSbxx(String sbmx) {
		// sbmx=",";
		HashMap map = new HashMap();
		sbmx = sbmx.substring(sbmx.indexOf(",") + 1);
		if (sbmx.equals("") || sbmx == null)
			return map;
		/*
		 * if(sbmx.endsWith("@")){ sbmx=sbmx+" "; }
		 */
		String[] sbString = sbmx.split(",", -1);
		if (sbString != null && sbString.length > 0) {
			for (int i = 0; i < sbString.length; i++) {
				String zspm = "";
				String gzxj = "0";
				String jfgz = "0";
				String sbcs = "0";
				String pzxh = "";
				//String xh = "";
				String str = sbString[i];
				String[] mx = str.split("@", -1);
				if (mx[0] != null)
					zspm = mx[0];
				if (mx[1] != null)
					gzxj = mx[1];
				if (mx[2] != null)
					jfgz = mx[2];
				if (mx[3] != null)
					sbcs = mx[3];
				if (mx[4] != null)
					pzxh = mx[4];
				// if(mx[5]!=null)
				// xh=mx[5];
				if (gzxj.equals("")) {
					gzxj = "0";
				}
				if (jfgz.equals("")) {
					jfgz = "0";
				}
				if (sbcs.equals("")) {
					sbcs = "0";
				}

				map.put("gzxj", gzxj);// 工资薪金不分品目，在网页中也分不了
				map.put(zspm + "*jfgz", jfgz);
				map.put(zspm + "*sbcs", sbcs);
				map.put(zspm + "*pzxh", pzxh);
				// map.put(zspm+"*xh",xh);
			}

		}
		return map;
	}

	/**
	 * 扣缴义务人密码派发:查询数据上限,大小单位（记录行数）
	 */
	public static long queryDataUpperLimit() throws WebException {
		if (queryDataUpperLimit == 0) {
			try {
				ConfigurationRegister conf = ConfigurationRegister
						.getInstance();
				queryDataUpperLimit = conf.getConfiguration("fstax").getInt(
						"wsgs.queryDataUpperLimit");
			} catch (Exception e) {
				queryDataUpperLimit = 1000;
			}
			if (queryDataUpperLimit == 0)
				queryDataUpperLimit = 1000;
			if (logger.isDebugEnabled())
				logger.debug("读取扣缴义务人密码派发:查询数据上限：" + queryDataUpperLimit
						+ " 条记录 ");
		}
		if (logger.isDebugEnabled())
			logger.debug("扣缴义务人密码派发:查询数据上限：" + queryDataUpperLimit + " 条记录 ");
		return queryDataUpperLimit;
	}

	public static String getSid(String xml) {
		String result = "";
		SAXReader sax = new SAXReader();
		try {
			Document doc = sax.read(new StringReader(xml));
			XPath x = doc.createXPath("instance");
			Element e = (Element) x.selectSingleNode(doc);
			result = e.attributeValue("sid");

			// System.out.println("result is"+result);
		} catch (Exception e) {
			result = "";
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 判断是否有某菜单的权限
	 * 
	 * @param gljgdm
	 *            管理机关代码
	 * @param limitmenu
	 *            菜单标志
	 * @return true 有权限 false 没权限
	 */
	public static boolean queryLimitMenu(String gljgdm, String limitmenu) {
		int res = 0;
		String tempStr = "";
		if (gljgdm.length() > 5) {
			tempStr = limitmenu + "." + gljgdm.substring(0, 5);
		}
		try {
			ConfigurationRegister conf = ConfigurationRegister.getInstance();
			res = conf.getConfiguration("revenue_cfg").getInt(tempStr);
		} catch (Exception e) {
		}
		if (res == 1)
			return true;
		else
			return false;
	}

	/**
	 * 根据nsqxdm取jsp中日前的validate
	 * 
	 * @param nsqxdm
	 * @return
	 */
	public static String[] queryDateValidate(String nsqxdm) {
		String date_validateq = "";
		String date_validatez = "";
		if (nsqxdm == null || nsqxdm.trim().equals(""))
			nsqxdm = "M32432";
		String nsqxDmLx = nsqxdm.substring(0, 1);
		if ("M".equals(nsqxDmLx)) {
			date_validateq = "date_sbq_m";
			date_validatez = "date_sbz_m";
		} else if ("Q".equals(nsqxDmLx)) {
			date_validateq = "date_sbq_q";
			date_validatez = "date_sbz_q";
		} else if ("Y".equals(nsqxDmLx)) {
			date_validateq = "date_sbq_y";
			date_validatez = "date_sbz_y";
		} else if ("H".equals(nsqxDmLx)) {
			date_validateq = "date_sbq_h";
			date_validatez = "date_sbz_h";
		} else {
			date_validateq = "date_sbq_c";
			date_validatez = "date_sbz_c";
		}
		return new String[] { date_validateq, date_validatez };
	}

	public static String getRtn(String xml) {
		String repcode = "";
		try {
			SAXReader sax = new SAXReader();
			Document doc = sax.read(new StringReader(xml));

			// Document doc = sax.read(new StringReader(xml));
			XPath x = doc.createXPath("//rootVo//head//returnMessage");
			Element e = (Element) x.selectSingleNode(doc);
			repcode = e.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return repcode;

	}

	/**
	 * 返回代码 1；提交成功；0：提交失败
	 * 
	 * @param xml
	 * @return
	 */
	public static String getRetuCode(String xml) {
		String repcode = "";
		try {
			SAXReader sax = new SAXReader();
			Document doc = sax.read(new StringReader(xml));
			XPath x = doc.createXPath("//rootVo//head//repcode");
			Element e = (Element) x.selectSingleNode(doc);
			repcode = e.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return repcode;

	}


	public static Root getWjzId(String nsrbm) {
		Root xmlvo = null;
		try {
			xmlvo = Root.getInstance("OutCertInfoQueryServId_query", "", "");
			xmlvo.getProperties().putCell("taxpayerid", nsrbm);
			xmlvo = invoke(xmlvo);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return xmlvo;
	}

	/**
	 * 获取外经证明细
	 * 
	 * @param outcertinfo
	 * @return
	 */
	public static Root getWjzMx(String outcertinfo) {
		Root xmlvo = null;
		try {
			xmlvo = Root.getInstance("OutCertInfoQueryServId_querydetail", "",
					"");
			xmlvo.getProperties().putCell("outCertInfo", outcertinfo);
			xmlvo = invoke(xmlvo);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return xmlvo;

	}

	public static Root getCommon(String sid, String nsrbm) {
		Root xmlvo = null;
		try {
			xmlvo = Root.getInstance(sid, "", "");
			xmlvo.getProperties().putCell("taxpayerid", nsrbm);
			xmlvo = invoke(xmlvo);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return xmlvo;

	}

	/**
	 * 测试启动函数
	 * 
	 * @param args
	 *            这里不使用
	 * @throws ParseException
	 *             测试失败
	 */
	public static void main(String[] args) throws ParseException {
		// System.out.println("CompareMonthNum()=" +
		// CompareMonthNum("2006-08-12", "2006-08-31"));
		// System.out.println("Get time of EAI: " + getTimeOfEAI());
		// System.out.println(format("0.2032", "0.00"));
		// System.out.println("CompareMonthNum()=" +
		// CompareMonthNum("2006-08-12", "2006-08-31"));

		/*
		 * String[] aaa; aaa = parseString("0100, 0101, 0102, 0201,
		 * 0300, 0301, 0400, 0401, 0500, 0600, 0700, 0800, 0801, 0802, 0803,
		 * 0900, 1000, 9900,xxxx"); for(int i = 0;i<aaa.length;i++){
		 * System.out.println("parseString()"+ i +"="+aaa[i]); }
		 */
		/*
		 * Calendar cd = Calendar.getInstance(); cd.add(Calendar.YEAR,-1);
		 * cd.set(Calendar.MONTH,0); cd.set(Calendar.DAY_OF_MONTH,1); Date date =
		 * cd.getTime(); Calendar cd1 = Calendar.getInstance();
		 * cd1.add(Calendar.YEAR,1); cd1.set(Calendar.MONTH,0);
		 * cd1.set(Calendar.DAY_OF_MONTH,1); cd1.add(Calendar.DAY_OF_MONTH,-1);
		 * Date date1 = cd1.getTime(); SimpleDateFormat sd = new
		 * SimpleDateFormat("yyyy-MM-dd"); System.out.println(sd.format(date));
		 * System.out.println(sd.format(date1));
		 */
	}

	/**
	 * 读取XML文件,转换成字符串
	 * 
	 * @param xmlFileName
	 * @return
	 */
	public static String readXmlFile(String xmlFileName) {
		StringBuffer xml = new StringBuffer();
		try {
			FileReader reader = new FileReader(xmlFileName);
			char[] c = new char[255];
			try {
				int i = reader.read(c);
				while (i != -1) {
					xml.append(c, 0, i);
					i = reader.read(c);
				}
			} finally {
				reader.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return xml.toString();
	}

	/**
	 * 从seq_table中获取不动产项目id id命名规则:d+2位管理码+"N"(代表网报)+7位id值
	 * 
	 */
	public static String getBdcxmid(String nsrbm) {
		String id = "";
		Connection conn = null;
		try {
			// 要从门户的seq_table中获取
			conn = ConnectionManager.getConnection("foresee");
			conn.setAutoCommit(false);
			int tempId = Sequence.getNextID(conn,
					TaxWebConstants.DECLARE_SEQ_BDC).intValue();
			tempId = 10000000 + tempId;
			StringBuffer sb = new StringBuffer("");
			sb.append("d");
			sb.append(nsrbm.substring(0, 2));
			sb.append("N");
			sb.append(String.valueOf(tempId).substring(1));
			id = sb.toString();
		} catch (Exception e) {
			try {
				if (conn != null)
					conn.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			ConnectionManager.closeConnection(conn);
		}
		return id;
	}


	/**
	 * 取得存储在SessionStore中的权限信息
	 * 
	 * @return 权限信息
	 * @throws TaxException
	 *             抛出异常
	 */
	public static PermissionSet getPermissions(HttpServletRequest request)
			throws Exception {
		User info = getUserInfo(request);

		if (info == null) {
			logger.warn("没有找到用户对象,用户可能没有正常登录!");
			throw new Exception("没有找到用户对象,用户可能没有正常登录!");
		}

		PermissionSet permissions = null;
		/*
		 * if
		 * (session.getAttribute(TaxWebConstants.SESSION_ATTRIBUTE_PERMISSIONS) !=
		 * null) { 当权限信息已经存储在sessionstore中时直接取用 permissions = (PermissionSet)
		 * session .getAttribute(TaxWebConstants.SESSION_ATTRIBUTE_PERMISSIONS); }
		 * else { try { 否则得到权限信息并存入到sessionstore中 SecurityManagerFactory factory =
		 * SecurityManagerFactory
		 * .getInstance(SecurityManagerFactory.DS_TYPE_DBMS,
		 * SecurityManagerFactory.PERM_POLICY_SIMPLE); UserManager userManager =
		 * factory.getUserManager(); permissions =
		 * userManager.getPermissionsByUserId(info.getUserId());
		 * session.setAttribute(TaxWebConstants.SESSION_ATTRIBUTE_PERMISSIONS,
		 * permissions); } catch (SecurityException e) {
		 * logger.info(e.getMessage()); throw new Exception(e); } }
		 */
		return permissions;
	}

	/**
	 * 重点税源（获得企业基本信息ID）
	 * 
	 * @param nsrbm
	 * @return
	 * @throws WebException
	 */
	public static String getEnterpriserBasicInfoId(String nsrbm)
			throws WebException {
		Map nsrMap = null;
		String tmpStr = "";
		Root xmlvo = Root.getInstance("BasicInfoQueryServId", "", "");
		xmlvo.getProperties().putCell("taxpayerid", nsrbm);
		xmlvo = invoke(xmlvo); // TODO
		nsrMap = xmlvo.getProperties().getMap();
		if (nsrMap != null && nsrMap.size() > 0)
			tmpStr = (String) nsrMap.get("enterpriserbasicinfoid");
		return tmpStr;
	}

	/**
	 * 重点税源（获得企业基本信息）
	 * 
	 * @param nsrbm
	 * @return map
	 * @throws WebException
	 */
	public static Map getEnterpriserBasicInfo(String nsrbm)
			throws WebException {
		Map nsrMap = null;
		Root xmlvo = Root.getInstance("BasicInfoQueryServId", "", "");
		xmlvo.getProperties().putCell("taxpayerid", nsrbm);
		xmlvo = invoke(xmlvo); // TODO
		nsrMap = xmlvo.getProperties().getMap();
		if (nsrMap != null && nsrMap.size() > 0)
			return nsrMap;
		return null;
	}

	
}
