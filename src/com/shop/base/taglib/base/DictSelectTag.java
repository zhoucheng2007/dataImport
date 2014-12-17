/**
 * 
 */
package com.shop.base.taglib.base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.shop.base.cache.CacheClient;
import com.shop.base.cache.CacheConstants;

/**
 * @title:下拉列表自定义标签
 * @description:数据来源：base_dict表
 * @author sunfs
 * @mail:sunfs@inspur.com
 * @date:2013-1-29
 */
public class DictSelectTag extends TagSupport {
	
	private String dictkey;
	
	private String name;
	
	private String style;
	
	private String id;
	private String bclass;
	
	private String nulldesc;
	private String scroll;
	
	private String onchange;
	
	private String disabled;
	
	private String value;
	
	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getNulldesc() {
		return nulldesc;
	}

	public void setNulldesc(String nulldesc) {
		this.nulldesc = nulldesc;
	}

	public String getScroll() {
		return scroll;
	}

	public void setScroll(String scroll) {
		this.scroll = scroll;
	}

	public String getBclass() {
		return bclass;
	}

	public void setBclass(String bclass) {
		this.bclass = bclass;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	private String property;

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDictkey() {
		return dictkey;
	}

	public void setDictkey(String dictkey) {
		this.dictkey = dictkey;
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			 
            JspWriter out = this.pageContext.getOut();
            
            Map  dictMap =CacheClient.getListCacheValue(CacheConstants.BASE_DICT_CACHE_KEY, dictkey);
 
            if(dictMap==null) dictMap = new HashMap();
           
 
            out.println("<select ");
            
            if(name!=null){
            	out.print(" name=\""+name+"\" ");
            }
            
            if(id!=null){
            	out.print(" id=\""+id+"\" ");
            }
            
            if(property!=null){
            	out.print(" property=\""+property+"\" ");
            }
            
            if(bclass!=null){
            	out.print(" class=\""+bclass+"\" ");
            }
            if(style!=null){
            	out.print(" style=\""+style+"\" ");
            }
            
            if(scroll!=null){
            	out.print(" scroll=\""+scroll+"\" ");
            }
            
            if(onchange!=null){
            	out.print(" onchange=\""+onchange+"\" ");
            }
            
            if(value!=null){
            	out.print(" value=\""+value+"\" ");
            }
            
            if(disabled!=null){
            	out.print(" disabled=\""+disabled+"\" ");
            }
            
 
            out.println(">");
            if(nulldesc!=null && !"".equals(nulldesc.trim())){
            	out.print("<option  value=\"\">"+nulldesc+"</option>");
            }
            Set keySet=dictMap.keySet();
            for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				String theValue = (String)dictMap.get(key);
				out.println("<option  theValue=\""+key+"\"");
				if(key!=null &&key.equals(value)){
					out.print("  selected=\"selected\" ");
				}
				out.print(">");
				out.print(theValue);
				out.println("</option>");
				
			}
            
            out.println("</select>");
            
 
        } catch(Exception e) {
 
            throw new JspException(e.getMessage());
 
        }

		return super.doEndTag();
	}
	
	

}
