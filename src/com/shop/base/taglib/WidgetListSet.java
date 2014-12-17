package com.shop.base.taglib;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 功能：<web:widgets/>标签的实现类
 * @author 郑斌
 * @date 2012-12-7 下午2:05:04
 */
public class WidgetListSet  extends TagSupport{
	
	//私有变量
	private String s = "";
	//私有变量
	private String w = "";
	//初始化变量值
	public void setS(String value){
	    this.s = value;
	}
	public void setW(String value){
	    this.w = value;
	}
	
	public int doEndTag() throws JspTagException {
		ScreenWidgetMapping parent = (ScreenWidgetMapping)this.getParent();
		String screenUrl = parent.getScreenUrl();
		if("default".equals(s)){
			//默认对应的widgets
			parent.setDefaultWidgets(w);
		}
		if(screenUrl.equals(s)){
			//本screen对应的widgets
			parent.setSelfWidgets(w);
		}
	    return EVAL_PAGE;
	}
}
