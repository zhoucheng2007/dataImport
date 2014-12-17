package com.shop.base.tool;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * 功能：生成尽量不重复的随机码
 * @author 郑斌
 * @date 2012-10-26 下午1:18:34
 */
public class RandomIdGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println(getRandomUUID());
		//System.out.println(getRandomStr(2));
		System.out.println(getRandomUUID(20));
	}
	
	/**
	 * 功能：生成32位的UUID，16进制的数字串
	 * @author 郑斌
	 * @date 2012-10-26 下午1:20:19
	 * @return
	 */
	public static String getRandomUUID(){
		String s = UUID.randomUUID().toString();
		return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
	}

	
	/**
	 * 功能：按输入位数生成随机码，位数必须大于18位，全由数字组成
	 * @author 郑斌
	 * @date 2012-10-26 下午1:20:19
	 * @param n	位数
	 * @return
	 */
	public static String getRandomUUID(int n){
		String rtnStr = "";
		if(n<18){
			throw new RuntimeException("所需位数必须在18位以上！");
		}else{
			//到毫秒的当前时间字符串
			String timeStr = getCurrTime("yyMMddHHmmssSSS");
			String randomStr = getRandomStr(n-15);
			rtnStr = timeStr + randomStr;
		}
		
		return rtnStr;
	}
	
	/**
	 * 功能：获取n位随机数
	 * @author 郑斌
	 * @date 2012-10-26 下午1:29:31
	 * @param n
	 * @return
	 */
	private static String getRandomStr(int n){
		//输入位数必须大于0
		if(n>0){
			StringBuffer sb = new StringBuffer(n);
			for(int i=0; i<n;i++){
				sb.append((int)(Math.random()*10));
			}
			return sb.toString();
		}else{
			return "";
		}
	}
	/**
	 * 功能：按需要格式获取当前时间
	 * @author 郑斌
	 * @date 2012-10-26 下午1:46:22
	 * @param pattern
	 * @return
	 */
	private static String getCurrTime(String pattern) {
        Calendar c = Calendar.getInstance();
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(d);
    }
}

