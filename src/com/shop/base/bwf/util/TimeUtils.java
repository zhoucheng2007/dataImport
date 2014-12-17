package com.shop.base.bwf.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.loushang.bwf.constant.BWfConstants;
import org.loushang.bwf.util.bsp.BspAddDate;
import org.loushang.next.i18n.ResourceBundle;

/**
 * 格式化时间工具类
 */
public class TimeUtils {

	/**
	 * 取得当前时间
	 * 
	 * @return
	 */
	public static String getCurrentDateTime() {
		//SimpleDateFormat是线程不安全的，这里使用局部变量
		return new SimpleDateFormat("yyyyMMdd HH:mm:ss").
				format(Calendar.getInstance().getTime());
	}

	/**
	 * 取得当前日期
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		return new SimpleDateFormat("yyyyMMdd HH:mm:ss").
				format(Calendar.getInstance().getTime());
	}

	/**
	 * 格式化时间
	 * 
	 * @param time
	 * @return String
	 */
	public static String formatTime(long time) {
		return new SimpleDateFormat("yyyyMMdd HH:mm:ss").
				format(new Date(time));
	}

	/**
	 * 转换成毫秒时间
	 * 
	 * @param time
	 * @return long
	 */
	public static long parseLong(String time) {
		long returnTime = 0L;
		if (time != null && !"".equals(time)) {
			try {
				Date d = new SimpleDateFormat("yyyyMMdd HH:mm:ss").
						parse(time);
				returnTime = d.getTime();
			} catch (ParseException e) {
			}
		}

		return returnTime;
	}

	/**
	 * 计算时间差,将开始时间,比如"20000101 01:01:01",结束时间,比如"20000102 01:01:01"间的时间差计算出来,
	 * 并用毫秒表示
	 * 
	 * @param processEnd
	 * @return long
	 */
	public static long calcTimeDiff(String sStartTime, String sEndTime) {
		return parseLong(sEndTime) - parseLong(sStartTime);
	}

	/**
	 * 将毫秒表示的时间差转换成用"天、时、分、秒"表示的字符串
	 * 
	 * @param differTime
	 * @return String
	 */
	public static String convertMillsTimeDiffToDisplayTimeStr(
			long millsTimeDiffer) {
		// 天、时、分、秒
		long day = 0L, hour = 0L, minute = 0L, second = 0L;
		// 是否有天、时
		boolean hasDay = false, hasHour = false;

		// 除以1000转换成秒
		millsTimeDiffer = millsTimeDiffer / 1000;

		/**
		 * 天:一天中的秒数：86400=24 * 60 * 60
		 */
		day = millsTimeDiffer / 86400;
		/**
		 * 时:一小时的秒数:3600=60 * 60
		 */
		hour = millsTimeDiffer / 3600 - day * 24;
		/**
		 * 分:一天中的分数：1440=24*60
		 */
		minute = millsTimeDiffer / 60 - day * 1440 - hour * 60;
		/**
		 * 秒 一天中的秒数：86400=24 * 60 * 60 一小时的秒数:3600=60 * 60
		 */
		second = millsTimeDiffer - day * 86400 - hour * 3600 - minute * 60;

		StringBuffer sb = new StringBuffer();

		if (day > 0) {
			sb.append(day);
			sb.append(ResourceBundle.getPackageBundle(BWfConstants.BWF_COMMON)
					.getLocaleMsg("BWF.COMMON.006", "天"));
			hasDay = true;
		}
		if (hour > 0 || (hour == 0 && hasDay)) {
			sb.append(hour);
			sb.append(ResourceBundle.getPackageBundle(BWfConstants.BWF_COMMON)
					.getLocaleMsg("BWF.COMMON.007", "小时"));
			hasHour = true;
		}
		if (minute > 0 || (minute == 0 && hasHour)) {
			sb.append(minute);
			sb.append(ResourceBundle.getPackageBundle(BWfConstants.BWF_COMMON)
					.getLocaleMsg("BWF.COMMON.008", "分"));
		}
		if (second >= 0) {
			sb.append(second);
			sb.append(ResourceBundle.getPackageBundle(BWfConstants.BWF_COMMON)
					.getLocaleMsg("BWF.COMMON.009", "秒"));
		}

		return sb.toString();
	}

	/**
	 * 获得流程、环节组、环节限时时间
	 * 
	 * @param startTime
	 * @param limitUnit
	 * @param limitTime
	 * @return long
	 */
	public static long getLimitTime(String organId, String startTime, String limitUnit,
			int limitTime) {
		BspAddDate bspAddDate = new BspAddDate();
		return bspAddDate.getBspDateTime(organId, limitUnit, new Date(
				parseLong(startTime)), limitTime);
	}

	/**
	 * 计算流程、环节组、环节预警时间
	 * 
	 * @param startTime
	 * @param limitUnit
	 * @param limitTime
	 * @param warnTime
	 * @return long
	 */
	public static long getWarnTime(String organId, String startTime, String limitUnit,
			int limitTime, int warnTime) {
		BspAddDate bspAddDate = new BspAddDate();
		return bspAddDate.getBspDateTime(organId, limitUnit, new Date(
				parseLong(startTime)), limitTime - warnTime);
	}

	/**
	 * 获得流程或环节超时,返回字符串表示,比如"1天1小时0分1秒"
	 * 
	 * @param limiTime
	 * @param limitUnit
	 * @return String
	 */
	public static String getLimitTimeStr(int limiTime, String limitUnit) {
		StringBuffer sb = new StringBuffer();
		sb.append(limiTime);

		// 时间单位
		String limitUnitLabel = "";

		// 年
		if ("Y".equals(limitUnit)) {
			limitUnitLabel = ResourceBundle.getPackageBundle(BWfConstants.BWF_COMMON)
			.getLocaleMsg("BWF.COMMON.010", "年");
		}
		// 月
		else if ("M".equals(limitUnit)) {
			limitUnitLabel = ResourceBundle.getPackageBundle(BWfConstants.BWF_COMMON)
			.getLocaleMsg("BWF.COMMON.011", "月");
		}
		// 日
		else if ("D".equals(limitUnit)) {
			limitUnitLabel = ResourceBundle.getPackageBundle(BWfConstants.BWF_COMMON)
			.getLocaleMsg("BWF.COMMON.006", "天");
		}
		// 时
		else if ("h".equalsIgnoreCase(limitUnit)) {
			limitUnitLabel = ResourceBundle.getPackageBundle(BWfConstants.BWF_COMMON)
			.getLocaleMsg("BWF.COMMON.007", "小时");
		}
		// 分
		else if ("m".equals(limitUnit)) {
			limitUnitLabel = ResourceBundle.getPackageBundle(BWfConstants.BWF_COMMON)
			.getLocaleMsg("BWF.COMMON.008", "分");
		}
		// 秒
		else if ("s".equals(limitUnit)) {
			limitUnitLabel = ResourceBundle.getPackageBundle(BWfConstants.BWF_COMMON)
				.getLocaleMsg("BWF.COMMON.009", "秒");
		}

		sb.append(limitUnitLabel);

		return sb.toString();
	}

	/**
	 * 获得流程或环节超时,返回毫秒形式的表示
	 * 
	 * @param limiTime
	 * @param limitUnit
	 * @return long
	 */
	public static long getLimitTimeInt(int limiTime, String limitUnit) {
		// 毫秒表示的限时
		long millisLimitTime = 0L;

		// 年
		if ("Y".equals(limitUnit)) {
			millisLimitTime = limiTime * 365 * 24 * 60 * 60 * 1000;
		}
		// 月
		else if ("M".equals(limitUnit)) {
			millisLimitTime = limiTime * 30 * 24 * 60 * 60 * 1000;
		}
		// 日
		else if ("D".equals(limitUnit)) {
			millisLimitTime = limiTime * 24 * 60 * 60 * 1000;
		}
		// 时
		else if ("h".equalsIgnoreCase(limitUnit)) {
			millisLimitTime = limiTime * 60 * 60 * 1000;
		}
		// 分
		else if ("m".equals(limitUnit)) {
			millisLimitTime = limiTime * 60 * 1000;
		}
		// 秒
		else if ("s".equals(limitUnit)) {
			millisLimitTime = limiTime * 1000;
		}

		return millisLimitTime;
	}
}
