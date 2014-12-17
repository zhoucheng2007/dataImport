package com.shop.portal.pub;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.logging.Log;

public class Lunar
{

    private static Log log;
    int monCyl;
    int dayCyl;
    int yearCyl;
    int year;
    int month;
    int day;
    int i;
    boolean isLeap;
    int lunarInfo[] = {
        19416, 19168, 42352, 21717, 53856, 55632, 0x16554, 22176, 39632, 21970, 
        19168, 42422, 42192, 53840, 0x1d255, 46400, 54944, 44450, 38320, 0x14977, 
        18800, 42160, 46261, 27216, 27968, 0x1ab54, 11104, 38256, 21234, 18800, 
        25958, 54432, 59984, 28309, 23248, 11104, 0x186e3, 37600, 0x1c8d7, 51536, 
        54432, 0x1d8a6, 46416, 22176, 0x1a5b4, 9680, 37584, 53938, 43344, 46423, 
        27808, 46416, 0x15355, 19872, 42448, 0x14573, 21200, 43432, 59728, 27296, 
        44710, 43856, 19296, 43748, 42352, 21088, 62051, 55632, 23383, 22176, 
        38608, 19925, 19152, 42192, 54484, 53840, 54616, 46400, 46496, 0x195a6, 
        38320, 18864, 43380, 42160, 45690, 27216, 27968, 44870, 43872, 38256, 
        19189, 18800, 25776, 29859, 59984, 27480, 21952, 43872, 38613, 37600, 
        51552, 55636, 54432, 55888, 30034, 22176, 43959, 9680, 37584, 51893, 
        43344, 46240, 47780, 44368, 21977, 19360, 42416, 0x15176, 21168, 43312, 
        31060, 27296, 44368, 23378, 19296, 42726, 42208, 53856, 60005, 54576, 
        23200, 30371, 38608, 19415, 19152, 42192, 0x1d0b6, 53840, 54560, 56645, 
        46496, 22224, 21938, 18864, 42359, 42160, 43600, 0x1b255, 27936, 44448
    };
    int solarMonth[] = {
        31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 
        30, 31
    };
    String Gan[] = {
        "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"
    };
    String Zhi[] = {
        "子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", 
        "戌", "亥"
    };
    String Animals[] = {
        "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", 
        "狗", "猪"
    };
    int sTermInfo[] = {
        0, 21208, 42467, 63836, 0x14d59, 0x1a206, 0x1f763, 0x24d89, 0x2a45d, 0x2fbdf, 
        0x353d8, 0x3ac35, 0x404af, 0x45d25, 0x4b553, 0x50d19, 0x56446, 0x5bac6, 0x61087, 0x6658a, 
        0x6b9db, 0x70d90, 0x760cc, 0x7b3b6
    };
    String nStr1[] = {
        "日", "一", "二", "三", "四", "五", "六", "七", "八", "九", 
        "十"
    };
    String nStr2[] = {
        "初", "十", "廿", "卅", "　"
    };
    String monthNong[] = {
        "正", "正", "二", "三", "四", "五", "六", "七", "八", "九", 
        "十", "十一", "十二"
    };
    String yearName[] = {
        "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"
    };
    static Class class$0; /* synthetic field */

    public Lunar()
    {
        i = 0;
    }

    public String cDay(int d)
    {
        String s;
        switch(d)
        {
        case 1: // '\001'
            s = monthNong[getMonth()] + "月";
            break;

        case 10: // '\n'
            s = "初十";
            break;

        case 20: // '\024'
            s = "二十";
            break;

        case 30: // '\036'
            s = "三十";
            break;

        default:
            s = nStr2[d / 10];
            s = s + nStr1[d % 10];
            break;
        }
        return s;
    }

    public String cyclical(int num)
    {
        return Gan[num % 10] + Zhi[num % 12];
    }

    public String cYear(int y)
    {
        String s;
        int d;
        for(s = " "; y > 0; s = yearName[d] + s)
        {
            d = y % 10;
            y = (y - d) / 10;
        }

        return s;
    }

    public int getDay()
    {
        return day;
    }

    public int getDayCyl()
    {
        return dayCyl;
    }

    public boolean getIsLeap()
    {
        return isLeap;
    }

    public String getLunar(int year, int month, int day)
    {
        int SY = year;
        int SM = month;
        int SD = day;
        int sy = (SY - 4) % 12;
        Calendar cl = Calendar.getInstance();
        cl.set(SY, SM - 1, SD);
        Date sDObj = cl.getTime();
        Lunar1(sDObj);
        String s = cDay(getDay());
        return s;
    }

    public int getMonCyl()
    {
        return monCyl;
    }

    public int getMonth()
    {
        return month;
    }

    public int getYear()
    {
        return year;
    }

    public int getYearCyl()
    {
        return yearCyl;
    }

    public int leapDays(int y)
    {
        if(leapMonth(y) != 0)
            return (lunarInfo[y - 1900] & 0x10000) != 0 ? 30 : 29;
        else
            return 0;
    }

    public int leapMonth(int y)
    {
        if(y < 1900)
            throw new RuntimeException("工作日历年份不能低于1901年。");
        if(y > 2049)
            throw new RuntimeException("工作日历年份不能高于2049年。");
        else
            return lunarInfo[y - 1900] & 0xf;
    }

    public void Lunar1(Date objDate)
    {
        int leap = 0;
        int temp = 0;
        Calendar cl = Calendar.getInstance();
        cl.set(1900, 0, 31);
        Date baseDate = cl.getTime();
        int offset = (int)((objDate.getTime() - baseDate.getTime()) / 0x5265c00L);
        dayCyl = offset + 40;
        monCyl = 14;
        int i;
        for(i = 1900; i < 2050 && offset > 0; i++)
        {
            temp = lYearDays(i);
            offset -= temp;
            monCyl += 12;
        }

        if(offset < 0)
        {
            offset += temp;
            i--;
            monCyl -= 12;
        }
        year = i;
        yearCyl = i - 1864;
        leap = leapMonth(i);
        isLeap = false;
        for(i = 1; i < 13 && offset > 0; i++)
        {
            if(leap > 0 && i == leap + 1 && !isLeap)
            {
                i--;
                isLeap = true;
                temp = leapDays(year);
            } else
            {
                temp = monthDays(year, i);
            }
            if(isLeap && i == leap + 1)
                isLeap = false;
            offset -= temp;
            if(!isLeap)
                monCyl++;
        }

        if(offset == 0 && leap > 0 && i == leap + 1)
            if(isLeap)
            {
                isLeap = false;
            } else
            {
                isLeap = true;
                i--;
                monCyl--;
            }
        if(offset < 0)
        {
            offset += temp;
            i--;
            monCyl--;
        }
        month = i;
        day = offset + 1;
    }

    public int lYearDays(int y)
    {
        if(y - 1900 < 0 || y - 1900 >= lunarInfo.length)
            return 0;
        int li = lunarInfo[y - 1900];
        int sum = 348;
        for(int i = 32768; i > 8; i >>= 1)
            sum += (li & i) != 0 ? 1 : 0;

        return sum + leapDays(y);
    }

    public int monthDays(int y, int m)
    {
        return (lunarInfo[y - 1900] & 0x10000 >> m) != 0 ? 30 : 29;
    }

    
    
    
    
    
    //--新增加内容----------------给定日期获取： 闰四月初五-------------------------------------------begin
    
    private static Date TheDate = null;   
    private int cYear;   
    private int cMonth;   
    private int cDay;   
    private int cHour;   
    //天干   
    private String tgString = "甲乙丙丁戊己庚辛壬癸";   
    //地支   
    private String dzString = "子丑寅卯辰巳午未申酉戌亥";   
    //中文数字   
    private String numString = "一二三四五六七八九十";   
    //阴历中的月称   
    private String monString = "正二三四五六七八九十冬腊";   
    //星期的中文   
    private String weekString = "日一二三四五六";   
    //生肖   
    private String sx = "鼠牛虎兔龙蛇马羊猴鸡狗猪";   
    private SimpleDateFormat chineseDateFormat = new SimpleDateFormat(   
            "yyyy年MM月dd日");   
    
  //1900年到2050年的阴历推算信息   
    private long[] CalendarData = new long[] { 0x8096d, 0x4ae, 0xa57, 0x50a4d,   
            0xd26, 0xd95, 0x40d55, 0x56a, 0x9ad, 0x2095d, 0x4ae, 0x6149b,   
            0xa4d, 0xd25, 0x51aa5, 0xb54, 0xd6a, 0x212da, 0x95b, 0x70937,   
            0x497, 0xa4b, 0x5164b, 0x6a5, 0x6d4, 0x415b5, 0x2b6, 0x957,   
            0x2092f, 0x497, 0x60c96, 0xd4a, 0xea5, 0x50d69, 0x5ad, 0x2b6,   
            0x3126e, 0x92e, 0x7192d, 0xc95, 0xd4a, 0x61b4a, 0xb55, 0x56a,   
            0x4155b, 0x25d, 0x92d, 0x2192b, 0xa95, 0x71695, 0x6ca, 0xb55,   
            0x50ab5, 0x4da, 0xa5d, 0x30a57, 0x52d, 0x8152a, 0xe95, 0x6aa,   
            0x615aa, 0xab5, 0x4b6, 0x414ae, 0xa57, 0x526, 0x31d26, 0xd95,   
            0x70b55, 0x56a, 0x96d, 0x5095d, 0x4ad, 0xa4d, 0x41a4d, 0xd25,   
            0x81aa5, 0xb54, 0xb5a, 0x612da, 0x95b, 0x49b, 0x41497, 0xa4b,   
            0xa164b, 0x6a5, 0x6d4, 0x615b4, 0xab6, 0x957, 0x5092f, 0x497,   
            0x64b, 0x30d4a, 0xea5, 0x80d65, 0x55c, 0xab6, 0x5126d, 0x92e,   
            0xc96, 0x41a95, 0xd4a, 0xda5, 0x20b55, 0x56a, 0x7155b, 0x25d,   
            0x92d, 0x5192b, 0xa95, 0xb4a, 0x416aa, 0xad5, 0x90ab5, 0x4ba,   
            0xa5b, 0x60a57, 0x52b, 0xa93, 0x40e95, 0x6aa, 0xad5, 0x209b5,   
            0x4b6, 0x614ae, 0xa4e, 0xd26, 0x51d26, 0xd53, 0x5aa, 0x30d6a,   
            0x96d, 0x7095d, 0x4ad, 0xa4d, 0x61a4b, 0xd25, 0xd52, 0x51b54,   
            0xb5a, 0x56d, 0x2095b, 0x49b, 0x71497, 0xa4b, 0xaa5, 0x516a5,   
            0x6d2, 0xada };   
  
    //存放每月一日到每年1月1日的天数，二月都以28天计算   
    private int[] madd = new int[] { 0, 31, 59, 90, 120, 151, 181, 212, 243,   
            273, 304, 334 };   
  
    //位运算，主要用来从十六进制中得到阴历每个月份是大月还是小月   
    private int GetBit(long m, int n) {   
        int r = (int) ((m >> n) & 1);   
        return r;   
    }   
  
    //将阳历向阴历转换   
    @SuppressWarnings("deprecation")   
    public void e2c(String time) throws ParseException {   
        TheDate = chineseDateFormat.parse(time);   
        int total, m, n, k;   
        boolean isEnd = false;   
        int tmp = TheDate.getYear();   
        if (tmp < 1900) {   
            tmp += 1900;   
        }   
  
        //计算TheDate到1900年1月30日的总天数，1900年1月31日是“庚子年正月初一”我们以这个时间点来推测   
        total = (int) ((tmp - 1900) * 365/*先以每年365天粗算*/    
                + countLeapYears(1900, tmp)/*再加上其中的闰年2月多出的一天*/  
                + madd[TheDate.getMonth()]/*当前时间月份到元旦的天数*/    
                       + TheDate.getDate()/*载加上当前月份已过天数*/    
                       - 30/*因为1900年1月31日才是正月初一，粗算时多算了30天*/);   
        //判断当前年份是否是闰年，如果为闰年并且二月已过，应再加上2月多的一天，才是准确的总天数   
        if (isLeapYear(tmp) && TheDate.getMonth() > 1)   
            total++;   
        //开始推算已经过了几个阴历年，从1900年开始   
        for (m = 0;; m++) {   
            //检查16进制中信息，当年是否有闰月，有，则为13个月份   
            k = (CalendarData[m] < 0xfff) ? 11 : 12;   
               
            for (n = k; n >= 0; n--) {   
                //如果总天数被减得小于29或30（由16进制中的规律来确定），则推算结束   
                if (total <= 29 + GetBit(CalendarData[m], n)) {   
                    isEnd = true;   
                    break;   
                }   
                //如果不小于29或30，则继续做减   
                total = total - 29 - GetBit(CalendarData[m], n);   
            }   
            if (isEnd)   
                break;   
        }   
        //当前阴历年份   
        cYear = 1900 + m;   
         //当前阴历月份   
         cMonth = k - n + 1;   
         //当前阴历日子   
         cDay = total;   
            
         //如果阴历cYear年有闰月，则确定是闰几月，并精确阴历月份   
         if (k == 12) {   
             //如果cMonth恰巧等于该年闰月，则需要标示当前阴历月份为闰月   
             if (cMonth == Math.floor(CalendarData[m] / 0x10000) + 1){   
                 cMonth = 1 - cMonth;   
             }   
             //如果cMonth大于该年闰月，则表示闰月已过，需要对cMonth减1   
             if (cMonth > Math.floor(CalendarData[m] / 0x10000) + 1){   
                 cMonth--;   
             }   
         }   
         //计算时辰，夜里23点到1点为子时，每两个小时为一个时辰，用“地支”依次类推   
         cHour = (int) Math.floor((TheDate.getHours() + 3) / 2);   
     }   
   
     //输出 : 闰四月初五   。如果需要其他输出内容，请不要修改下面的方法中的内容，自己再添加方法。lilei 20120725
     public String getcDateString() {   
         String tmp = "";   
         //算天干，1900年1月31日是庚子年，庚是天干中的第七位需要对cYear-4再做模运算   
        /* tmp += tgString.charAt((cYear - 4) % 10); // 年干   
         tmp += dzString.charAt((cYear - 4) % 12); // 年支   
         tmp += "年(";   
         //算生肖   
         tmp += sx.charAt((cYear - 4) % 12);   
         tmp += ")";  */ 
         //处理闰月标记之前的闰月，是被处理为负数了   
         if (cMonth < 1) {   
             tmp += "闰";   
             tmp += monString.charAt(-cMonth - 1);   
         } else  
             tmp += monString.charAt(cMonth - 1);   
         tmp += "月";   
         //处理日子   
         tmp += (cDay < 11) ? "初" : ((cDay < 20) ? "十" : ((cDay < 30) ? "廿"  
                 : "卅"));   
         if (cDay % 10 != 0 || cDay == 10)   
             tmp += numString.charAt((cDay - 1) % 10);   
         if (cHour == 13)   
             tmp += "夜";   
         //处理时辰   
         /*tmp += dzString.charAt((cHour - 1) % 12);   
         tmp += "时"; */  
         return tmp;   
     }   
        
     //计算两个年份间的闰年数   
     private int countLeapYears(int s, int e) {   
         int count = 0;   
         for (int i = s; i < e; i++) {   
             if (isLeapYear(i)) {   
                 count++;   
             }   
         }   
         return count;   
     }   
   
     //判断年份是否为闰年   
     private boolean isLeapYear(int year) {   
         if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {   
             return true;   
         } else {   
             return false;   
         }   
     }   
    
   //--新增加内容----------------给定日期获取： 闰四月初五-------------------------------------------end

}
