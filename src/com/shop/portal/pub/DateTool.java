package com.shop.portal.pub;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 说明：日期相关的工具类
 * 
 * @author pengzhu DateTool.java 2012-7-12
 */
public final class DateTool {

	/**
     * 获取当前月份,格式 "YYYYMM"
     * 
     * @return
     */
    public static String getCurMonth() {
        Calendar calendar = Calendar.getInstance();
        StringBuffer month = new StringBuffer();
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH) + 1;

        month.append(y);
        if (m < 10) {
        	month.append("0");
        }
        month.append(m);

        return month.toString();
    }
	
    /**
     * 获取当前日期,格式 "YYYYMMDD"
     * 
     * @return
     */
    public static String getToday() {
        Calendar calendar = Calendar.getInstance();
        StringBuffer today = new StringBuffer();
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH) + 1;
        int d = calendar.get(Calendar.DAY_OF_MONTH);

        today.append(y);
        if (m < 10) {
            today.append("0");
        }
        today.append(m);
        if (d < 10) {
            today.append("0");
        }
        today.append(d);

        return today.toString();
    }

    /**
     * 获取当前时间,格式"hh:mm:ss"
     * 
     * @return
     */
    public static String getCurrentTime() {
        StringBuffer result = new StringBuffer();
        Calendar calendar = Calendar.getInstance();
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int m = calendar.get(Calendar.MINUTE);
        int s = calendar.get(Calendar.SECOND);
        if (h < 10) {
            result.append("0");
        }
        result.append(h);
        result.append(":");
        if (m < 10) {
            result.append("0");
        }
        result.append(m);
        result.append(":");
        if (s < 10) {
            result.append("0");
        }
        result.append(s);
        return result.toString();
    }

    /**
     * 根据模式返回相应的系统时间字符串
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
     * 获取输入日期的前 n天 (n为负整数)或后n天(n为正整数)。 getBeforeOrNextDay("20120720",1) 返回
     * 20120721 getBeforeOrNextDay("20120720",-1) 返回 20120719
     * 
     * @param day
     * @param n
     * @return
     */
    public static String getBeforeOrNextDay(String day, int n) {
        if (day == null || "".equals(day) || day.length() != 8) {
            throw new RuntimeException("由于缺少必要的参数，获取输入日期的前 n天 或后n天出错：day=" + day + ";n=" + n);
        }
        try {
            String sYear = day.substring(0, 4);
            int year = Integer.parseInt(sYear);
            String sMonth = day.substring(4, 6);
            int month = Integer.parseInt(sMonth);
            String sDay = day.substring(6, 8);
            int dday = Integer.parseInt(sDay);
            Calendar cal = Calendar.getInstance();
            cal.set(year, month - 1, dday);
            cal.add(Calendar.DATE, n);
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            return df.format(cal.getTime());
        } catch (Exception e) {
            throw new RuntimeException("进行日期运算时输入得参数不符合系统规格." + e);
        }
    }

    /**
     * 获取输入月份的前 n月 (n为负整数)或后n月(n为正整数)。
     * 
     * @param month yyyyMM
     * @param n
     * @return
     */
    public static String getBeforeOrNextMonth(String month, int n) {
        if (month == null || "".equals(month) || month.length() != 6) {
            throw new RuntimeException("由于缺少必要的参数，系统无法进行制定的月份换算.");
        }
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
            cal.setTime(sdf.parse(month));
            cal.add(Calendar.MARCH, n);
            return sdf.format(cal.getTime());
        } catch (Exception e) {
            throw new RuntimeException("进行月份运算时输入得参数不符合系统规格." + e);
        }
    }

    /**
     * 返回星期: 星期天:0 星期陆:6
     * 
     * @param date 20120723
     * @return
     */
    public static int getWeekday(String date) {
        if (date == null || date.length() != 8) {
            throw new RuntimeException("由于缺少必要的参数，返回星期时出错：date=" + date);
        }
        String sYear = date.substring(0, 4);
        int year = Integer.parseInt(sYear);
        String sMonth = date.substring(4, 6);
        int mon = Integer.parseInt(sMonth);
        String sDay = date.substring(6, 8);
        int dday = Integer.parseInt(sDay);
        Calendar cal = Calendar.getInstance();
        cal.set(year, mon - 1, dday);
        int weekday = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return weekday;
    }

    /**
     * 得到 农历
     * 
     * @param date 20120723
     * @return
     */
    public static Lunar getLunar(String date) {
        Date d = getDate(date);
        Lunar lunar = new Lunar();
        lunar.Lunar1(d);
        return lunar;
    }

    private static Date getDate(String date) {
        if (date == null || date.length() != 8) {
            throw new RuntimeException("由于缺少必要的参数，系统获取农历时出错.date=" + date);
        }
        String sYear = date.substring(0, 4);
        int year = Integer.parseInt(sYear);
        String sMonth = date.substring(4, 6);
        int mon = Integer.parseInt(sMonth);
        String sDay = date.substring(6, 8);
        int dday = Integer.parseInt(sDay);
        Calendar cal = Calendar.getInstance();
        cal.set(year, mon - 1, dday);
        return cal.getTime();
    }

    /**
     * 得到2个日期之间间隔的天数
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static int differ(String date1, String date2) {
        try {
            Date date11 = dateFormatter.parse(date1);
            Date date22 = dateFormatter.parse(date2);
            int day = differ(date11, date22);
            return day;
        } catch (Exception e) {
            throw new RuntimeException("日期格式错误！");
        }
    }

    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");

    private static int differ(Date date1, Date date2) {
        //long day = date1.getTime() / (24*60*60*1000) - date2.getTime() / (24*60*60*1000); 
        long day = date2.getTime() / 86400000 - date1.getTime() / 86400000; //用立即数，减少乘法计算的开销
        return (int) day;
    }

    /**
     * 功能：取同期 date为要转换的日期
     */
    public static String getSameDate(String date) {
        if (date == null || "".equals(date)) {
            throw new RuntimeException("由于缺少必要的参数，系统无法进行制定的日期换算.");
        }
        try {
            String sYear = date.substring(0, 4);
            int year = Integer.parseInt(sYear);
            return String.valueOf(year - 1) + date.substring(4, date.length());
        } catch (Exception e) {
            throw new RuntimeException("进行日期运算时输入得参数不符合系统规格." + e);
        }
    }
    
    /**
	 * 获取输入 周的开始日期
	 * 
	 * @param week
	 *            like 200412
	 * @return 8位日期 like 20040330
	 */
	public static String getWeekBeginDate(String week) {
		if (week == null || "".equals(week) || week.length() < 5) {
			throw new RuntimeException("由于缺少必要的参数，系统无法进行制定的周换算.");
		}
		try {
			Calendar cal = Calendar.getInstance();
			cal.setFirstDayOfWeek(Calendar.MONDAY);   // 设置一个星期的第一天为星期1，默认是星期日 
			cal.set(Calendar.YEAR, Integer.parseInt(week.substring(0, 4)));
			cal.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(week.substring(4,
					week.length())));
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			return df.format(cal.getTime());
		} catch (Exception e) {
			throw new RuntimeException("进行周运算时输入得参数不符合系统规格." + e);
		}
	}
	/**
	 * 获取输入 周的结束日期
	 * 
	 * @param week
	 *            like 200412
	 * @return 8位日期 like 20040330
	 */
	public static String getWeekEndDate(String week) {
		if (week == null || "".equals(week) || week.length() < 5) {
			throw new RuntimeException("由于缺少必要的参数，系统无法进行制定的周换算.");
		}
		try {
			Calendar cal = Calendar.getInstance();
			cal.setFirstDayOfWeek(Calendar.MONDAY);   // 设置一个星期的第一天为星期1，默认是星期日 
			cal.set(Calendar.YEAR, Integer.parseInt(week.substring(0, 4)));			
			cal.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(week.substring(4,
					week.length())));
			cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			return df.format(cal.getTime());
		} catch (Exception e) {
			throw new RuntimeException("进行周运算时输入得参数不符合系统规格." + e);
		}
	}
	
	/**
	 * 获取自然月的第一天日期 "YYYYMMDD"
	 * @param date1:"201201" 或者 "20120102"
	 * @return
	 */
	public static String getFirstDayOfMonth(String date1) {
		if (date1 == null || "".equals(date1) || date1.length() < 6
				|| date1.length() == 7 || date1.length() > 8) {
			throw new RuntimeException("传入输入日期有误！");
		}
		try {
			String month1 = date1.substring(0, 6);
			return month1+"01";
		} catch (Exception e) {
			throw new RuntimeException("进行日期运算时输入得参数不符合系统规格." + e);
		}
	}

	/**
	 * 获取自然月的最后一天日期 "YYYYMMDD"
	 * 
	 * @param date1
	 *            :"201201" 或者 "20120102"
	 * @return
	 */
	public static String getLastDayOfMonth(String date1) {
		if (date1 == null || "".equals(date1) || date1.length() < 6
				|| date1.length() == 7 || date1.length() > 8) {
			throw new RuntimeException("传入输入日期有误！");
		}
		try {
			String year1 = date1.substring(0, 4);
			String month1 = date1.substring(4, 6);
			int year = Integer.valueOf(year1).intValue();
			int month = Integer.valueOf(month1).intValue() - 1;
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month);
			if ((year % 400 == 0) || (year % 100 != 0 && year % 4 == 0)) { // 闰年
				cal.set(Calendar.DAY_OF_MONTH,
						cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			} else if (month == 1) {// 非闰年2月
				cal.set(Calendar.DAY_OF_MONTH,
						cal.getLeastMaximum(Calendar.DAY_OF_MONTH));
			} else {
				cal.set(Calendar.DAY_OF_MONTH,
						cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			}
			return new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
		} catch (Exception e) {
			throw new RuntimeException("进行日期运算时输入得参数不符合系统规格." + e);
		}
	}

    /**
     * 获取当前月份 返回6位 格式 "yyyyMM"
     * 
     * @return
     */
    public static String getNowMonth() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMM");
        Date today = new Date();
        return sf.format(today);

    }

    /**
     * @param 获取当前系统时间 返回 yyyyMMddHHmmss
     * @return
     */
    public static String getCurrentDayTime() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        return df.format(date);
    }

    /**
	 * 获取输入日期的下一天 返回 8位 like 20050101
	 * 
	 * @param today
	 * @return
	 */
	public static String getNextDay(String day) {
		return getNextDay(day, 1);
	}

	/**
	 * 获取输入日期的下 n 天 返回 8位 like 20050101
	 * 
	 * @param day
	 * @param n
	 * @return
	 */
	public static String getNextDay(String day, int n) {
		if (day == null || "".equals(day) || day.length() != 8) {
			throw new RuntimeException("由于缺少必要的参数，系统无法进行制定的日期换算.");
		}
		try {
			String sYear = day.substring(0, 4);
			int year = Integer.parseInt(sYear);
			String sMonth = day.substring(4, 6);
			int month = Integer.parseInt(sMonth);
			String sDay = day.substring(6, 8);
			int dday = Integer.parseInt(sDay);
			Calendar cal = Calendar.getInstance();
			cal.set(year, month - 1, dday);
			cal.add(Calendar.DATE, n);
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			return df.format(cal.getTime());
		} catch (Exception e) {
			throw new RuntimeException("进行日期运算时输入得参数不符合系统规格." + e);
		}
	}
}
