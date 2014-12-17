package com.v6.portal.pub;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.text.DecimalFormat;

public class HelpTool {
	public static final BigDecimal ZERO = new BigDecimal(0);

	public static String setScale(Object o) {
		return setScale(o, 2);
	}

	public static String setScale(Object o, int precision) {
		BigDecimal temp = null;
		if (o == null) {
			temp = ZERO;
		} else if (o instanceof BigDecimal) {
			temp = (BigDecimal) o;
		} else {
			try {
				temp = new BigDecimal(o.toString());
			} catch (Throwable t) {
				return String.valueOf(o);
			}
		}
		try {
			temp = temp.setScale(precision, BigDecimal.ROUND_HALF_UP);
		} catch (Throwable t) {
		}
		return String.valueOf(temp);
	}

	public static String trimnull(Object o) {
		if (o == null || "null".equals(String.valueOf(o))) {
			return "";
		}
		if (o instanceof String) {
			return (String) o;
		}
		return String.valueOf(o);
	}

	public static String trimzero(Object o) {
		if (o == null)
			return "0";
		String s = (o instanceof String) ? (String) o : o.toString();
		int idx = s.lastIndexOf(".");
		if (idx == -1) {
			return s;
		}
		int pos;
		for (pos = s.length() - 1; pos >= 0 && pos >= idx && (s.charAt(pos) == '0' || s.charAt(pos) == '.'); pos--)
			;
		if (pos == -1) {
			return "0";
		} else {
			return s.substring(0, pos + 1);
		}
	}

	/**
	 * 注锟解：要锟斤拷list锟斤拷锟揭伙拷锟絤ap锟斤拷锟斤拷荼锟斤拷锟斤拷锟饺拷摹锟�锟斤拷锟斤拷锟斤拷锟絣ist锟叫ｏ拷map值为BigDecimal锟斤拷Integer值锟斤拷锟斤拷式锟斤拷锟斤拷锟斤拷锟斤拷千锟斤拷位锟斤拷
	 * 值为0锟侥伙拷为锟斤拷锟街凤拷锟斤拷锟斤拷map锟叫碉拷BigDecimal锟斤拷Integer值锟斤拷转锟斤拷为String锟斤拷锟酵★拷
	 * 
	 * @param list
	 * @return
	 */
	public static List formatDecimal(List list) {

		int lengh = list.size();

		List newlist = new ArrayList();

		for (int i = 0; i < lengh; i++) {
			// 锟斤拷锟斤拷锟斤拷值
			Object[] a = ((Map) list.get(i)).keySet().toArray();

			Map map = (Map) list.get(i);
			Map newMap = new HashMap();
			for (int j = 0; j < a.length; j++) {
				Object p = map.get(a[j]);

				if (p != null) {

					if (p.getClass().equals(BigDecimal.class)) {
						p = formatDecimal((BigDecimal) p);
					} else if (p.getClass().equals(Integer.class)) {
						p = formatInteger((Integer) p);
					} else if (p.getClass().equals(String.class)) {
						p = trimRight((String) p);
					}

				} else {
					p = "";
				}

				newMap.put(a[j], p);
			}
			newlist.add(newMap);
		}

		return newlist;
	}

	/**
	 * 锟窖达拷锟斤拷锟街凤拷锟斤拷锟斤拷目崭锟饺ワ拷锟斤拷锟�锟斤拷千锟斤拷位锟侥凤拷锟斤拷使锟矫★拷
	 * 
	 * @param a
	 * @return
	 */
	public static String trimRight(String a) {

		if ("".equals(a)) {
			return a;
		}

		int len = 0;

		for (int i = a.length() - 1; i >= 0; i--) {

			if (a.charAt(i) == ' ') {
				len++;
			} else {
				break;
			}

		}// end for

		return a.substring(0, a.length() - len);
	}

	/**
	 * 
	 * 
	 * BigDecimal值锟斤拷式锟斤拷锟斤拷锟斤拷锟斤拷千锟斤拷位锟斤拷0转锟斤拷为锟斤拷锟斤拷锟斤拷
	 * 
	 * @param b
	 * @return
	 */
	public static String formatDecimal(BigDecimal b) {
		String a = null;

		if (b == null || b.doubleValue() == 0) {
			a = "";
		} else {

			StringBuffer c = new StringBuffer("#,##0");
			if (b.scale() > 0) {
				c.append(".");
			}
			for (int i = 0; i < b.scale(); i++) {
				c.append("0");
			}
			DecimalFormat nf = new DecimalFormat(c.toString());
			a = nf.format(b.doubleValue());
		}

		return a;
	}

	/**
	 * 
	 * 
	 * BigDecimal值锟斤拷式锟斤拷锟斤拷锟斤拷锟斤拷千锟斤拷位锟斤拷0转锟斤拷为锟斤拷0.00锟斤拷锟斤拷
	 * 
	 * @param b
	 * @return
	 */
	public static String formatDecimal0(BigDecimal b) {
		String a = null;

		if (b == null || b.doubleValue() == 0) {
			a = "0.00";
		} else {

			StringBuffer c = new StringBuffer("#,##0");
			if (b.scale() > 0) {
				c.append(".");
			}
			for (int i = 0; i < b.scale(); i++) {
				c.append("0");
			}
			DecimalFormat nf = new DecimalFormat(c.toString());
			a = nf.format(b.doubleValue());
		}

		return a;
	}

	/**
	 * Integer值锟斤拷式锟斤拷锟斤拷锟斤拷锟斤拷千锟斤拷位锟斤拷0转锟斤拷为锟斤拷锟斤拷锟斤拷
	 * 
	 * @param b
	 * @return
	 */
	public static String formatInteger(Integer b) {
		String a = null;

		if (b == null || b.doubleValue() == 0) {
			a = "";
		} else {

			StringBuffer c = new StringBuffer("#,###");
			DecimalFormat nf = new DecimalFormat(c.toString());
			a = nf.format(b.doubleValue());
		}

		return a;
	}

	/**
	 * 锟节碉拷锟斤拷MappingTool锟斤拷bean锟斤拷值锟斤拷时锟斤拷锟斤拷要锟斤拷String锟斤拷锟斤拷转锟斤拷锟斤拷 int 锟斤拷 BigDecimal 锟酵的诧拷锟斤拷
	 * 锟斤拷锟节撅拷锟斤拷锟斤拷锟斤拷NumberFormatException锟斤拷锟斤拷 锟斤拷锟斤拷锟节达拷锟结供转锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷默锟斤拷值锟斤拷0
	 * 
	 * @param argStr
	 * @return
	 */
	public static int toInt(String argStr) {
		return toInt(argStr, 0);
	}

	public static int toInt(String argStr, int defaultValue) {
		int num = 0;
		if (argStr == null || argStr.trim().equalsIgnoreCase(""))
			return 0;
		try {
			num = Integer.parseInt(argStr);
		} catch (Exception e) {
			num = defaultValue;
		}
		return num;
	}

	/**
	 * 转锟斤拷锟斤拷BigDecimal锟斤拷默锟斤拷值锟斤拷0
	 * 
	 * @param argStr
	 * @return
	 */
	public static BigDecimal toBigDecimal(String argStr) {
		return toBigDecimal(argStr, ZERO);
	}

	public static BigDecimal toBigDecimal(String argStr, BigDecimal defaultValue) {
		BigDecimal bd = null;
		if (argStr == null)
			return defaultValue != null ? defaultValue : ZERO;
		try {
			bd = new BigDecimal(argStr);
		} catch (Exception e) {
			bd = defaultValue;
		}
		return bd;
	}

	/**
	 * 锟斤拷锟斤拷8位锟侥凤拷锟斤拷锟斤拷锟斤拷锟斤拷锟街凤拷锟界：20031225
	 */
	public static String getCurrDate() {
		Calendar c = Calendar.getInstance();
		Date d = c.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(d);
	}

	/**
	 * 锟斤拷锟斤拷8位锟侥凤拷锟斤拷锟斤拷时锟斤拷锟街凤拷锟界：23595900
	 */
	public static String getCurrTime() {
		Calendar c = Calendar.getInstance();
		Date d = c.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss00");
		return sdf.format(d);
	}

	/**
	 * 锟斤拷锟侥Ｊ斤拷锟斤拷锟斤拷锟接︼拷锟较低呈憋拷锟斤拷址锟�
	 * 
	 * @param date
	 * @param deltaDay
	 * @return
	 */
	public static String getCurrTime(String pattern) {
		Calendar c = Calendar.getInstance();
		Date d = c.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(d);
	}

	/**
	 * 锟矫碉拷一锟斤拷时锟斤拷锟斤拷映锟绞憋拷锟斤拷锟街碉拷锟斤拷锟斤拷锟紿Hmmss锟斤拷式锟斤拷6位
	 * 
	 * @param time
	 * @param delay_time
	 * @return
	 */
	public static String getTimeByTime(String time, int delay_time) {
		try {
			Calendar c = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
			c.setTime(sdf.parse(time));
			c.add(Calendar.MINUTE, delay_time);
			return sdf.format(c.getTime());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 锟斤拷锟斤拷8位锟斤拷锟斤拷锟斤拷锟街凤拷锟界：20031225 getDateByDate("20030101", 1) -> 20030102
	 * getDateByDate("20030101", -2) -> 20021230
	 */
	public static String getDateByDate(String date, int deltaDay) {
		try {
			Calendar c = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			c.setTime(sdf.parse(date));
			c.add(Calendar.DATE, deltaDay);
			return sdf.format(c.getTime());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 锟斤拷Map锟斤拷锟斤拷一锟斤拷key锟斤拷一锟斤拷int锟斤拷锟斤拷
	 * 
	 * @param map
	 * @return 锟斤拷 map 锟斤拷锟叫匡拷锟街凤拷时锟斤拷锟斤拷锟截碉拷锟斤拷锟斤拷锟斤拷锟斤拷 "-1" 锟斤拷锟斤拷
	 */
	public static int[] toIntArray(Map map) {
		int array[] = { 0 };
		if (map != null && !map.isEmpty()) {
			Object[] o = map.keySet().toArray();
			array = new int[o.length];
			for (int i = 0; i < o.length; i++) {
				array[i] = Integer.parseInt(String.valueOf(o[i]) != null && !String.valueOf(o[i]).trim().equalsIgnoreCase("") ? String.valueOf(o[i]) : "-1");
			}
			return array;
		}
		return array;
	}

	/**
	 * 锟斤拷Map锟斤拷锟斤拷一锟斤拷key锟斤拷一锟斤拷String锟斤拷锟斤拷
	 * 
	 * @param map
	 * @return
	 */
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

	/**
	 * 锟斤拷锟斤拷锟斤拷锟斤拷锟秸碉拷锟斤拷锟绞撅拷锟斤拷纾�004锟斤拷2锟斤拷2锟斤拷 add by zhangll
	 */
	public static String getDateExpression(String date) {
		String returnDate = null;
		try {
			if (date.length() != 8 || !date.substring(0, 3).equals("200")) {
				returnDate = date;
			} else {
				String year = date.substring(0, 4);
				String month = date.substring(4, 6);
				if (month.substring(0, 1).equals("0")) {
					month = month.substring(1, 2);
				}
				String day = date.substring(6, 8);
				if (day.substring(0, 1).equals("0")) {
					day = day.substring(1, 2);
				}
				returnDate = year + "锟斤拷" + month + "锟斤拷" + day + "锟斤拷";
			}
			return returnDate;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 锟斤拷锟斤拷锟斤拷诩锟斤拷锟绞斤拷锟斤拷锟斤拷锟接︼拷锟斤拷址锟�
	 * 
	 * @param date
	 *            锟斤拷8位锟斤拷锟斤拷锟街凤拷 add by wzq 锟斤拷式锟斤拷 2004.02.04 yyyy.MM.dd 2004/02/04
	 *            yyyy/MM/dd 2002-02-04 yyyy-MM-dd 2002锟斤拷02锟斤拷04锟斤拷 yyyy锟斤拷MM锟斤拷dd锟斤拷
	 */
	public static String formatDate(String date, String pattern) {
		if (date == null)
			return "";
		if (date.length() != 8)
			return date;
		if (pattern == null || pattern.trim().equals(""))
			return date;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date dd = sdf.parse(date);
			sdf = new SimpleDateFormat(pattern);
			return sdf.format(dd);
		} catch (Exception e) {
			return date;
		}
	}

	/**
	 * 锟斤拷锟绞憋拷浞碉拷锟斤拷锟接︼拷母锟绞斤拷址锟�
	 * 
	 * @param time
	 *            锟斤拷8位锟斤拷时锟斤拷锟街凤拷
	 * @param pattern
	 * @return add by wzq pattern:锟斤拷式 12:10:20(1...24小时锟斤拷) hh:mm:ss
	 *         00:10:20(0...23小时锟斤拷) HH:mm:ss 12:10:20 PM(/AM) hh:mm:ss a 00:10:20
	 *         PM(/AM) HH:mm:ss a 12时10锟斤拷20锟斤拷 hh时mm锟斤拷ss锟斤拷 00时10锟斤拷20锟斤拷 HH时mm锟斤拷ss锟斤拷
	 */
	public static String formatTime(String time, String pattern) {
		if (time == null)
			return "";
		time = time.trim();
		if (time.length() > 6)
			time = time.substring(0, 6);
		else {
			for (int i = 0; i < 6 - time.trim().length(); i++) {
				time = time.concat("0");
			}
		}
		if (pattern == null || pattern.trim().equals(""))
			return time;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
			Date dd = sdf.parse(time);
			sdf = new SimpleDateFormat(pattern);
			return sdf.format(dd);
		} catch (Throwable t) {
			return time;
		}
	}

	/**
	 * 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷 region 锟斤拷锟斤拷 转锟斤拷锟斤拷 String
	 * 
	 * @param argRegions
	 * @return
	 */
	public static String regionToString(String[] argStr, String argSplitChar) {
		if (argStr == null || argStr.length == 0)
			return "";
		StringBuffer sb = new StringBuffer();
		if (argSplitChar != null)
			sb.append(argSplitChar).append(String.valueOf(argStr[0])).append(argSplitChar);
		else
			sb.append(String.valueOf(argStr[0]));
		for (int i = 1; i < argStr.length; i++) {
			sb.append(",");
			if (argSplitChar != null)
				sb.append(argSplitChar).append(String.valueOf(argStr[i])).append(argSplitChar);
			else
				sb.append(String.valueOf(argStr[i]));
		}
		return sb.toString();
	}

	/**
	 * List 转锟斤拷锟斤拷 锟截讹拷 String,锟斤拷list 锟斤拷元锟斤拷锟斤拷 锟斤拷锟斤拷 argSplitChar锟斤拷铣锟斤拷囟锟斤拷锟�锟斤拷锟街凤拷 <br>
	 * 然锟斤拷锟斤拷 锟街革拷锟斤拷(锟斤拷锟斤拷compart)锟斤拷铣锟�锟斤拷锟截的帮拷锟斤拷list锟斤拷元锟截碉拷一锟斤拷锟街凤拷 <br>
	 * 锟斤拷锟斤拷,sql锟斤拷锟斤拷锟�in('','') 锟街凤拷
	 * 
	 * @param argSplitChar
	 *            锟斤拷锟街凤拷锟斤拷锟皆拷锟�
	 * @param compart
	 *            锟街革拷锟斤拷,默锟斤拷为 锟斤拷锟斤拷 ,
	 * @author chenyzh
	 * @return
	 */
	public static String listToSpString(List list, String argSplitChar, String compart) {
		if (list == null || list.size() == 0)
			return "";
		StringBuffer sb = new StringBuffer();
		if (argSplitChar != null)
			sb.append(argSplitChar).append(String.valueOf(list.get(0))).append(argSplitChar);
		else
			sb.append(String.valueOf(list.get(0)));
		for (int i = 1; i < list.size(); i++) {
			if (compart != null)
				sb.append(compart);
			else
				sb.append(",");
			if (argSplitChar != null)
				sb.append(argSplitChar).append(String.valueOf(list.get(i))).append(argSplitChar);
			else
				sb.append(String.valueOf(list.get(i)));
		}
		return sb.toString();
	}

	public static String CollectionToSpString(Collection colect, String argSplitChar, String compart) {
		if (colect == null || colect.size() == 0)
			return "";
		Iterator colt = colect.iterator();
		List lit = new ArrayList();
		while (colt.hasNext()) {
			lit.add(colt.next());
		}
		return listToSpString(lit, argSplitChar, compart);
	}

	/**
	 * 锟斤拷锟斤拷转锟斤拷锟斤拷锟截讹拷锟斤拷锟街凤拷,锟轿匡拷 锟斤拷锟斤拷 <br>
	 * listToSpString(List list, String argSplitChar, String compart);
	 * 
	 * @author chenyzh
	 */
	public static String arrayToSpString(String[] argStr, String argSplitChar, String compart) {
		return listToSpString(Arrays.asList(argStr), argSplitChar, compart);
	}

	/**
	 * 锟街凤拷 转锟斤拷锟斤拷锟截讹拷锟斤拷锟街凤拷,锟轿匡拷 锟斤拷锟斤拷 <br>
	 * 
	 * @author chenyzh
	 */
	public static String muliStringToSpString(String argStr, String argSplitChar, String compart) {
		StringTokenizer st = new StringTokenizer(argStr, compart);
		String[] s = new String[st.countTokens()];
		int i = 0;
		while (st.hasMoreTokens()) {
			s[i] = st.nextToken();
			i++;
		}
		return listToSpString(Arrays.asList(s), argSplitChar, compart);
	}

	/**
	 * 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷细锟斤拷锟斤拷锟�*
	 * 
	 * @author chenyzh
	 */
	public static void main(String[] args) {

	}
}