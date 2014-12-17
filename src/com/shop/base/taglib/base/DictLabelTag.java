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
 * @title:数据字典编码名称转换标签
 * @description:数据来源：base_dict表
 * @author sunfs
 * @mail:sunfs@inspur.com
 * @date:2013-4-11
 */
public class DictLabelTag extends TagSupport {
	
	private String dictkey;
	
	private String dictid;
	
	

	private String name;
	
	private String style;
	
	private String id;
	private String bclass;
	
	

	public String getDictid() {
		return dictid;
	}

	public void setDictid(String dictid) {
		this.dictid = dictid;
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
            
            
            
            out.println("<label ");
            
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
            
            
            
 
            out.println(">");
            String labelText = CacheClient.getCacheValue(dictkey, dictid);
            out.print(labelText);
            out.print("</label>");
            
 
        } catch(Exception e) {
 
            throw new JspException(e.getMessage());
 
        }

		return super.doEndTag();
	}
	
	

}
