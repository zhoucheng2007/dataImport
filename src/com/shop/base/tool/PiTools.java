package com.shop.base.tool;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.shop.base.service.BaseServiceImpl;


public class PiTools   {
	/*
	 *  getUm
	 *  @parms@ String 
	 *  01: 支,02,盒,03,条,04:件,05:箱，06:万支
	 * 根据传入的参数返回对应的plm_item 的计量单位名
	 */
public  static String getUm(String umname){ 
	 if("03".equals(umname)) return "T_SIZE";
	 if("07".equals(umname)) return "BZT_SIZE";
	 if("06".equals(umname)) return "W_SIZE";
	 if("05".equals(umname)) return "X_SIZE";
	 if("04".equals(umname)) return "J_SIZE";
	 if("02".equals(umname)) return "H_SIZE";
	 if("01".equals(umname)) return "1"; // 如果计量单位是 1 需要单独处理
	return umname;
 }

public  static String getUmName(String umId){ 
	 if("03".equals(umId)) return "条";
	 if("07".equals(umId)) return "标准条";
	 if("06".equals(umId)) return "万支";
	 if("05".equals(umId)) return "箱";
	 if("04".equals(umId)) return "件";
	 if("02".equals(umId)) return "盒";
	 if("01".equals(umId)) return "支"; // 如果计量单位是 1 需要单独处理
	return "条";
}
public static String getUmField(String umname){
	return getUm( umname);
	
}



}
