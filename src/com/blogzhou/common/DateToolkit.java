package com.blogzhou.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p> 申报日期相关计算工具类 </p>
 * @author <a href="mailto:zhoucheng2007@gmail.com">zhoucheng</a>
 */
public class DateToolkit {
    /**
     * 当前类自己的logger
     */
    private static Logger logger = LoggerFactory.getLogger(DateToolkit.class);

    /**
     * 日期格式化工具
     */
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 工具类，无需构造
     */
    private DateToolkit() {
    }

    /**
     * 在指定的基准日期上增加一天
     * @param date 基准日期
     * @return 增加一天后的日期
     */
    public static Date incDay(Date date) {
        return addDay(date, 1);
    }

    /**
     * 在指定的基准日期上增加（或减少）指定的天数
     * @param date 基准日期
     * @param days 天数（负数则为减少）
     * @return 增加一天后的日期
     */
    public static Date addDay(Date date, int days) {
        Calendar calArg = Calendar.getInstance();
        calArg.setTime(date);
        calArg.add(Calendar.DATE, days);
        return calArg.getTime();
    }

    /**
     * 根据日期，按照指定的纳税期限产生上一次的所属时期起止
     * @param nsqxdm 纳税期限代码（按月、季、年等）：20月，30季，40年，41半年，50次（视同月处理）
     * @param date 日期，格式必须为yyyy-MM-dd，如2006-12-05
     * @return 包含该日期的所属时期的上一个所属时期：0所属时期起，1所属时期止
     */
    public static Date[] genLastSSSQ(String nsqxdm, String date) {
        try {
            return genLastSSSQ(nsqxdm, dateFormat.parse(date));
        } catch (ParseException ex) {
            logger.warn("指定的日期非法", ex);
        }
        return null;
    }

    /**
     * 根据日期，按照指定的纳税期限产生上一次的所属时期起止
     * @param nsqxdm 纳税期限代码（按月、季、年等）：20月，30季，40年，41半年，50次（视同月处理）
     * @param date 日期，格式必须为yyyy-MM-dd，如2006-12-05
     * @return 包含该日期的所属时期的上一个所属时期：0所属时期起，1所属时期止
     */
    public static Date[] genLastSSSQ(String nsqxdm, Date date) {
        Date[] thisPeriod = genSSSQ(nsqxdm, date);
        return genSSSQ(nsqxdm, addDay(thisPeriod[0], -1));
    }

    /**
     * 根据日期，按照指定的纳税期限产生所属时期起止
     * @param nsqxdm 纳税期限代码（按月、季、年等）：20月，30季，40年，41半年，50次（视同月处理）
     * @param date 日期
     * @return 包含该日期的所属时期：0所属时期起，1所属时期止
     */
    public static Date[] genSSSQ(String nsqxdm, String date) {
        try {
            return genSSSQ(nsqxdm, dateFormat.parse(date));
        } catch (ParseException ex) {
            logger.warn("指定的日期非法", ex);
        }
        return null;
    }

    /**
     * 根据日期，按照指定的纳税期限产生所属时期起止
     * @param nsqxdm 纳税期限代码（按月、季、年等）：20月，30季，40年，41半年，50次（视同月处理）
     * @param date 日期
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
        calEnd.set(Calendar.DAY_OF_MONTH, calEnd.getActualMaximum(Calendar.DAY_OF_MONTH));
        return new Date[] { calStart.getTime(), calEnd.getTime() };
    }

    /**
     * 若给定的上次成功申报时期参数为上期之前的期，就加1期返回；接口返回的期 + 1期 >= 本期，则为重复申报，返回接口返回的数据
     * @param nsqxdm nsqxdm 纳税期限代码（按月、季、年等）：20月，30季，40年，41半年，50次（视同月处理）
     * @param lastSuccessPeriodStart 上次成功申报所属期起
     * @param lastSuccessPeriodEnd 上次成功申报所属期止
     * @return 本次申报所属期
     */
    public static Date[] genDeclareSSSQ(String nsqxdm, Date lastSuccessPeriodStart, Date lastSuccessPeriodEnd) {
        Date today = new Date();
        Date[] lastPeriod = genLastSSSQ(nsqxdm, today);
        if (lastSuccessPeriodEnd.compareTo(lastPeriod[0]) < 0) { // 最后成功申报时期在上期之前，说明非重复申报，增加一期并返回
            return genSSSQ(nsqxdm, incDay(lastSuccessPeriodEnd));
        } else {
            return genSSSQ(nsqxdm, lastSuccessPeriodEnd);
        }
    }

    /**
     * 若给定的上次成功申报时期参数为上期之前的期，就加1期返回；接口返回的期 + 1期 >= 本期，则为重复申报，返回接口返回的数据
     * @param nsqxdm nsqxdm 纳税期限代码（按月、季、年等）：20月，30季，40年，41半年，50次（视同月处理）
     * @param lastSuccessPeriodStart 上次成功申报所属期起
     * @param lastSuccessPeriodEnd 上次成功申报所属期止
     * @return 本次申报所属期
     */
    public static Date[] genDeclareSSSQ(String nsqxdm, String lastSuccessPeriodStart, String lastSuccessPeriodEnd) {
        if (lastSuccessPeriodStart == null || lastSuccessPeriodEnd == null) {
            return genLastSSSQ(nsqxdm, new Date());
        } else {
            try {
                return genDeclareSSSQ(nsqxdm, dateFormat.parse(lastSuccessPeriodStart), dateFormat
                        .parse(lastSuccessPeriodEnd));
            } catch (ParseException ex) {
                logger.warn("指定的日期非法", ex);
            }
        }
        return null;
    }
}
