package com.shop.base.commonSelect.cust;

public class Css {
	/**
	 * 组织页面返回结果样式 - 分类类型
	 * @param typeKindName
	 * @return
	 */
	public static String orgTypeKindCondition(String typeKindName){		
		return "<div class='unitBox' style='word-break:break-all;'><span class='unitHead'>&nbsp;&nbsp;&nbsp;&nbsp;" + typeKindName + ":</span><span class='unitCount'>";
	}
	/**
	 * 组织页面返回结果样式 - 分类明细
	 * @param typeKindName
	 * @return
	 */
	public static String orgCustTypeCondition(String condition){
		return condition + "</span></div>";
	}
}
