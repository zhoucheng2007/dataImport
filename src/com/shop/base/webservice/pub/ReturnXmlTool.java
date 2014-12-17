package com.shop.base.webservice.pub;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.shop.base.tool.DateTool;

/**
 * 组织返回Xml结果帮助类
 * 
 * @author linqy
 * 
 */
public class ReturnXmlTool {
	private static Log log = LogFactory.getLog(ReturnXmlTool.class);
	private static Random r = new Random();
	/**
	 * 执行成功
	 */
	public final static String SUCCESS = "000";
	/**
	 * 用户不存在
	 */
	public final static String USER_NOTEXIST = "100";
	/**
	 * 用户已停用
	 */
	public final static String USER_HASSTOPPED = "101";
	/**
	 * 帐户名密码不正确
	 */
	public final static String USERNAME_PWD_ERROR = "102";
	/**
	 * 调用接口名错误
	 */
	public final static String SERIVCE_NOTEXIST = "103";
	/**
	 * 应用系统错误
	 */
	public final static String APPLICATION_ERROR = "104";
	/**
	 * 参数不合法
	 */
	public final static String XML_ILLEGAL = "200";
	/**
	 * xml参数格式不正确
	 */
	public final static String PARSE_XML_FAILED = "201";
	/**
	 * 请求超时
	 */
	public final static String TIME_OUT = "202";
	/**
	 * 返回xml组织错误
	 */
	public final static String RETURN_XML_FAILED = "203";
	/**
	 * 没有创建权限
	 */
	public final static String NO_CRT_PERMIT = "300";
	/**
	 * 没有检索权限
	 */
	public final static String NO_QUERY_PERMIT = "301";
	/**
	 * 没有修改权限
	 */
	public final static String NO_UPDATE_PERMIT = "302";
	/**
	 * 没有删除权限
	 */
	public final static String NO_DEL_PERMIT = "303";
	/**
	 * 违反数值值域约束规则
	 */
	public final static String NUMERICAL_ILLEGAL = "400";
	/**
	 * 违反字符值域约束规则
	 */
	public final static String CHARACTER_ILLEGAL = "401";
	/**
	 * 违反列完整性约束规则
	 */
	public final static String COL_INTEGRITY_ILLEGAL = "402";
	/**
	 * 违反总体完整性约束规则
	 */
	public final static String INTEGRITY_ILLEGAL = "403";
	/**
	 * 违反关键键一致性约束规则
	 */
	public final static String KEY_CONFORMITY_ILLEGAL = "404";
	/**
	 * 违反包含依赖约束规则
	 */
	public final static String INCLUDING_DEPENDENCE_ILLEGAL = "405";
	/**
	 * 违反显式函数依赖约束规则
	 */
	public final static String EXPLICIT_FUNCTION_DEPENDENCY_ILLEGAL = "406";
	/**
	 * 违反隐式函数依赖约束规则
	 */
	public final static String IMPLICIT_FUNCTION_DEPENDENCY_ILLEGAL = "407";
	/**
	 * CRUD权限参数不合法
	 */
	public final static String CRUD_PERMIT_ILLEGAL = "399";
	/**
	 * 写入任务失败
	 */
	public final static String WRITE_TASK_FAILED = "500";
	/**
	 * 执行操作数据库失败
	 */
	public final static String DATABASE_FAILED = "510";
	/**
	 * 失败
	 */
	public final static String FAILED = "999";

	/**
	 * 获得公共返回部分XML
	 * 
	 * @param code
	 *            返回码
	 * @param msg
	 *            中文描述
	 * @return
	 */
	public static String getTrasStr(String code, String msg) {
		StringBuffer sb = new StringBuffer();
		String tranTime = getCurTime();
		if (msg == null)
			msg = "";
		msg = (msg == "" ? getMsg(code) : (getMsg(code) + "::" + msg));
		try {
			tranTime = getCurTime();
		} catch (Exception e) {

			log.debug("获取系统时间出错！ ");
			e.printStackTrace();
		}
		sb.append("<TRANSER>\n");
		sb.append("\t<PUB");
		sb.append(" REC_ACK='");
		sb.append(code);
		sb.append("'");
		sb.append(" MSG='");
		sb.append(msg);
		sb.append("'");
		sb.append(" TRANSER_TIME='");
		sb.append(tranTime);
		sb.append("'");
		sb.append("/>\n");
		sb.append("\t</TRANSER>");
		return sb.toString();
	}

	/**
	 * <outerTag> <TRANSER> </TRANSER> <tag> </tab> </outerTag>
	 * 
	 * @param outerTag
	 *            最外层的标签
	 * @param transStr
	 *            <TRANSER>标示的字符串,由CustServHelper.getTrasStr(String code,String
	 *            msg)获得
	 * @param mainStr
	 *            <tag>标示的返回字符串 自组织的返回XML
	 * @return
	 */
	public static String getReturnXml(String outerTag, String transStr,
			String mainStr) {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"gb2312\" standalone=\"no\"?>\n");
		sb.append("<");
		sb.append(outerTag.toUpperCase());
		sb.append(">");
		sb.append("\n\t");
		sb.append(transStr);
		sb.append("\n\t");
		sb.append(mainStr);
		sb.append("</");
		sb.append(outerTag.toUpperCase());
		sb.append(">");
		return sb.toString();
	}

	/**
	 * 获得最后返回的xml字符串 SJ
	 * 
	 * @param outerTag
	 * @param code
	 * @param trancmsg
	 * @param mainXml
	 * @return 2009 2009-3-9 8:35:44
	 */
	public static String getReturnXml(String outerTag, String code,
			String trancmsg, String mainXml) {
		return getReturnXml(outerTag, getTrasStr(code, trancmsg), mainXml);
	}
	/**
	 * 功能： 返回YYYYMMDDHHMMSS再加上四位随机数的时间戳
	 * 
	 * @return
	 * @throws RuntimeException
	 */
	public static String getCurTime() throws RuntimeException {
		return DateTool.getCurrentDayTime() + getId(4);
	}

	private static String getId(int length) {
		String val = "";
		if (length <= 10) {
			int randInt = r.nextInt();
			if (randInt < 0)
				randInt = randInt + 1;
			val = String.valueOf(Math.abs(randInt));
		} else if (length <= 19) {
			val = String.valueOf(Math.abs(r.nextLong()));
		} else if (length <= 29) {
			int randInt = r.nextInt();
			if (randInt < 0)
				randInt = randInt + 1;
			val = String.valueOf(Math.abs(r.nextLong()))
					+ String.valueOf(Math.abs(randInt));
		} else {
			val = String.valueOf(Math.abs(r.nextLong()))
					+ String.valueOf(Math.abs(r.nextLong()));
		}
		if (length < val.length()) {
			val = val.substring(0, length);
		}
		return val;
	}

	/**
	 * 根据公共部分返回码,获得返回信息 SJ
	 * 
	 * @param code
	 * @return 2009 2009-3-9 9:06:05
	 */
	public static String getMsg(String code) {
		String note = "";
		if (code.equals(SUCCESS)) {
			note = "执行成功";
		} else if (code.equals(USER_NOTEXIST)) {
			note = "用户不存在";
		} else if (code.equals(USER_HASSTOPPED)) {
			note = "用户已停用";
		} else if (code.equals(USERNAME_PWD_ERROR)) {
			note = "帐户名密码不正确";
		} else if (code.equals(SERIVCE_NOTEXIST)) {
			note = "调用接口名错误";
		} else if (code.equals(APPLICATION_ERROR)) {
			note = "应用系统错误";
		} else if (code.equals(XML_ILLEGAL)) {
			note = "参数不合法";
		} else if (code.equals(PARSE_XML_FAILED)) {
			note = "xml参数格式不正确";
		} else if (code.equals(RETURN_XML_FAILED)) {
			note = "返回xml组织错误";
		} else if (code.equals(TIME_OUT)) {
			note = "请求超时";
		} else if (code.equals(NO_CRT_PERMIT)) {
			note = "没有创建权限";
		} else if (code.equals(NO_QUERY_PERMIT)) {
			note = "没有检索权限";
		} else if (code.equals(NO_UPDATE_PERMIT)) {
			note = "没有修改权限";
		} else if (code.equals(NO_DEL_PERMIT)) {
			note = "没有删除权限";
		} else if (code.equals(NUMERICAL_ILLEGAL)) {
			note = "违反数值值域约束规则";
		} else if (code.equals(CHARACTER_ILLEGAL)) {
			note = "违反字符值域约束规则";
		} else if (code.equals(COL_INTEGRITY_ILLEGAL)) {
			note = "违反列完整性约束规则";
		} else if (code.equals(INTEGRITY_ILLEGAL)) {
			note = "违反总体完整性约束规则";
		} else if (code.equals(KEY_CONFORMITY_ILLEGAL)) {
			note = "违反关键键一致性约束规则";
		} else if (code.equals(INCLUDING_DEPENDENCE_ILLEGAL)) {
			note = "违反包含依赖约束规则";
		} else if (code.equals(EXPLICIT_FUNCTION_DEPENDENCY_ILLEGAL)) {
			note = "违反显式函数依赖约束规则";
		} else if (code.equals(IMPLICIT_FUNCTION_DEPENDENCY_ILLEGAL)) {
			note = "违反隐式函数依赖约束规则";
		} else if (code.equals(CRUD_PERMIT_ILLEGAL)) {
			note = "CRUD权限参数不合法";
		} else if (code.equals(WRITE_TASK_FAILED)) {
			note = "写入任务失败";
		} else if (code.equals(DATABASE_FAILED)) {
			note = "执行操作数据库失败";
		} else if (code.equals(FAILED)) {
			note = "失败";
		}
		return note;
	}

	/**
	 * 将装载有数据Map的List对象转换为一个包含返回信息的完整xml
	 * 
	 * @param isShowData
	 *            是否显示数据节点信息
	 * @param rtnCode
	 *            信息返回节点XSM-信息编号
	 * @param rtnMsg
	 *            信息返回节点XSM-信息中文描述
	 * @param transerTime
	 *            信息返回节点XSM-返回时间
	 * @param dataNodeName
	 *            数据信息节点名称
	 * @param dataParamMap
	 *            数据信息节点名称
	 * @param lineNodeName
	 *            数据行节点名称
	 * @param dataList
	 *            数据List对象
	 * @return String xml
	 */
	public static String getWholeXmlFromList(boolean isShowData,
			String rtnCode, String rtnMsg, String transerTime,
			String dataNodeName, Map<String, String> dataParamMap, String lineNodeName,
			List<Map<String, ?>> dataList) {
		StringBuffer sb = new StringBuffer(512);
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

		// ////////根节点中带返回信息////////
		sb.append("<xsm code=\"").append(rtnCode).append("\"");
		sb.append(" msg=\"").append(rtnMsg).append("\"");
		sb.append(" trans_time=\"").append(transerTime).append("\">");

		// /////////数据信息/////////
		// 若需要显示信息则使用List装载Line行，否则dataList的信息不写入Xml
		if (isShowData) {
			// List to Xml
			sb.append(getXmlFromList(dataNodeName, dataParamMap, lineNodeName,
					dataList));
		} else {
		}
		sb.append("</xsm>");
		return sb.toString();
	}

	/**
	 * 返回只带有提示信息的xml
	 * 
	 * @param rtnCode
	 *            信息返回节点XSM-信息编号
	 * @param rtnMsg
	 *            信息返回节点XSM-信息中文描述
	 * @param transerTime
	 *            信息返回节点XSM-返回时间
	 * @return String xml
	 */
	public static String getRtnMsgXml(String rtnCode, String rtnMsg,
			String transerTime) {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

		// 根节点-带返回信息
		sb.append("<xsm code=\"").append(rtnCode).append("\"");
		sb.append(" msg=\"").append(rtnMsg).append("\"");
		sb.append(" trans_time=\"").append(transerTime).append("\">");

		// 根节点关闭
		sb.append("</xsm>");

		return sb.toString();
	}

	/**
	 * 从List转化为String类型的Xml文件
	 * 
	 * @param dataNodeName
	 *            数据信息节点名称
	 * @param dataParamMap
	 *            数据信息节点名称
	 * @param lineNodeName
	 *            数据行节点名称
	 * @param dataList
	 *            数据List对象
	 * @return String xml
	 */
	public static String getXmlFromList(String dataNodeName, Map dataParamMap,
			String lineNodeName, List dataList) {
		StringBuffer sb = new StringBuffer(512);

		sb.append("<").append(dataNodeName);
		if (null != dataParamMap) {
			Iterator iter = dataParamMap.entrySet().iterator();
			while (iter.hasNext()) {
				Entry entry = (Entry) iter.next();
				String key = (String) entry.getKey();
				String val = entry.getValue() == null ? "" : (entry
						.getValue()).toString().trim();
				sb.append(" ").append(key).append("=\"").append(val)
						.append("\"");
			}
		}
		sb.append(">");

		sb.append(getXmlFromList(lineNodeName, dataList));

		sb.append("</").append(dataNodeName).append(">");

		return sb.toString();
	}

	/**
	 * 从List转化为String类型的Xml文件
	 * 
	 * @param lineNodeName
	 *            数据行节点名称
	 * @param dataList
	 *            数据List对象
	 * @return String xml
	 */
	public static String getXmlFromList(String lineNodeName, List dataList) {
		StringBuffer sb = new StringBuffer(512);

		if (null != dataList) {
			Map dataMap = null;
			// 遍历dataList数据装载xml数据部分
			for (int i = 0; i < dataList.size(); i++) {
				dataMap = (HashMap) dataList.get(i);
				Iterator<Entry<String, Object>> iter = dataMap.entrySet().iterator();
				// 数据行节点
				sb.append("<").append(lineNodeName);
				// 取出Map中的数据构造数据行
				while (iter.hasNext()) {
					Entry entry = iter.next();
					String key = (String) entry.getKey();
					String val = entry.getValue() == null ? ""
							: (entry.getValue()).toString().trim();
					sb.append(" ").append(key).append("=\"").append(val)
							.append("\"");
				}
				sb.append("/>");
			}
		}

		return sb.toString();
	}

}
