package com.shop.base.tool;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONException;
import org.json.JSONObject;

import com.lc.v6.jdbc.mybatis.V6SqlSessionUtil;


/**
 * @author sunzhen
 * 联想帮助框工具类
 * 用于转换json对象
 * 20140311 
 */

public class JsonConvertTools   {
	private static Log log = LogFactory.getLog(JsonConvertTools.class);
	
	//执行sql
	public static List getReturnList(String sql){
		SqlSession sqlSession = V6SqlSessionUtil.getSqlSession();
		List returnList = sqlSession.selectList("autocompletetoolSql.getListSql",sql);
		return returnList;
	}
	
	public static String list2json(List list) {
		StringBuffer json = new StringBuffer();
		json.append("[");
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object obj = list.get(i);
				json.append(object2json(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		log.debug("json=="+json);
		return json.toString();
	}
	
	
	/**
	 * 下面为转换方法，有新类型可增加
	 * @param obj
	 * @return
	 */
	public static String object2json(Object obj) {
		StringBuffer json = new StringBuffer();
		if (obj == null) {
			json.append("\"\"");
		} else if (obj instanceof String || obj instanceof Integer || obj instanceof Float || obj instanceof Boolean || obj instanceof Short || obj instanceof Double || obj instanceof Long
				|| obj instanceof BigDecimal || obj instanceof BigInteger || obj instanceof Byte) {
			json.append("\"").append(string2json(obj.toString())).append("\"");
		} else if (obj instanceof Object[]) {
			json.append(array2json((Object[]) obj));
		} else if (obj instanceof List) {
			json.append(list2json((List) obj));
		} else if (obj instanceof Map) {
			json.append(map2json((Map) obj));
		} else if (obj instanceof Set) {
			json.append(set2json((Set) obj));
		} else {
			json.append(bean2json(obj));
		}
		return json.toString();
	}

	public static String bean2json(Object bean) {
		StringBuffer json = new StringBuffer();
		json.append("{");
		PropertyDescriptor[] props = null;
		try {
			props = Introspector.getBeanInfo(bean.getClass(), Object.class).getPropertyDescriptors();
		} catch (IntrospectionException e) {
		}
		if (props != null) {
			for (int i = 0; i < props.length; i++) {
				try {
					String name = object2json(props[i].getName());
					String value = object2json(props[i].getReadMethod().invoke(bean, null));
					json.append(name);
					json.append(":");
					json.append(value);
					json.append(",");
				} catch (Exception e) {
				}
			}
			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}
		return json.toString();
	}

	public static String array2json(Object[] array) {
		StringBuffer json = new StringBuffer();
		json.append("[");
		if (array != null && array.length > 0) {
			for (Object obj : array) {
				json.append(object2json(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	public static String map2json(Map map) {
		StringBuffer json = new StringBuffer();
		json.append("{");
		if (map != null && map.size() > 0) {
			String[] keyArr = toStringArray(map);
			for (int i = 0; i < keyArr.length; i++) {
				json.append(object2json(keyArr[i]));
				json.append(":");
				json.append(object2json(map.get(keyArr[i])));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}
		return json.toString();
	}

	public static String set2json(Set set) {
		StringBuffer json = new StringBuffer();
		json.append("[");
		if (set != null && set.size() > 0) {
			Iterator it = set.iterator();
			while (it.hasNext()) {
				Object obj = it.next();
				json.append(object2json(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	public static String string2json(String s) {
		if (s == null)
			return "";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			switch (ch) {
			case '"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '/':
				sb.append("\\/");
				break;
			default:
				if (ch >= '\u0000' && ch <= '\u001F') {
					String ss = Integer.toHexString(ch);
					sb.append("\\u");
					for (int k = 0; k < 4 - ss.length(); k++) {
						sb.append('0');
					}
					sb.append(ss.toUpperCase());
				} else {
					sb.append(ch);
				}
			}
		}
		return sb.toString();
	}
	 public static HashMap getMapByJson(String str){
		 HashMap map = new HashMap();
			try {
				JSONObject obj = new JSONObject(str);
				map.put("id",obj.get("id"));
				map.put("title",obj.get("title"));
				map.put("content",obj.get("content"));
				map.put("creater",obj.get("creater"));
				map.put("handler",obj.get("handler"));
				map.put("begin_time",obj.get("begin_time"));
				map.put("end_time",obj.get("end_time"));
				map.put("task_type",obj.get("task_type"));
				map.put("processing",obj.get("processing"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
			}
			
		return map;
	 }
	 
	 public static String[] toStringArray(Map map) {
			String array[] = null;
			if (map != null && !map.isEmpty()) {
				Object[] o = map.keySet().toArray();
				array = new String[o.length];
				for (int i = 0; i < o.length; i++) {
					array[i] = String.valueOf(o[i]);
				}
				return array;
			}
			return new String[0];
		}
	
	}
	
