/*
 * <p>Copyright Foresee Science & Technology Co.</p>
 * @author <a href="mailto:lindahai@foresee.com.cn">Lin Dahai</a>
 * $Id: Properties.java 6101 2006-11-07 08:07:47Z lindahai $
 */
package com.blogzhou.common.eaivo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import com.blogzhou.common.UUID;
import com.blogzhou.common.eaivo.exception.EaiVoException;


public class Properties implements Serializable {

	/**
	 * 序列化版本标识ID
	 */
	private static final long serialVersionUID = 3288649263659465354L;

	/**
	 * CellMap的关键字
	 */
	protected String cellMapKey;

	/**
	 * Root元素
	 */
	protected Root root;
	
	/**
	 * Cells
	 */
	protected Map cells;
	
	/**
	 * 缺省构造
	 */
	protected Properties(Root root) {
		this.root = root;
		cellMapKey = UUID.getUUID();
		cells = new HashMap();
		root.putCellMap(cellMapKey, cells);
	}

	/**
	 * 设置Cell节点
	 * @param name 名称
	 * @param value 值
	 */
	public void putCell(String name, String value) {
		if (value != null && value.equalsIgnoreCase("null"))
			value = "";
		/**
		 *报文空格处理
		 */
		name = name.trim();
		value = value.trim();
		cells.put(name, value);
	}

	/**
	 * 获取Cell节点的值
	 * @param cellName Cell节点名称
	 * @return Cell节点值
	 */
	public String getCell(String cellName) {
		String str = (String) cells.get(cellName);
		if (str != null && str.equalsIgnoreCase("null"))
			str = "";
		return str;
	}

    /**
     * 是否包含某Cell
     * @param cellName 名称
     * @return 是否
     */
    public boolean isContain(String cellName) {
        return cells.containsKey(cellName);
    }    

    /**
     * 获取Cell属性映射表
     * @return Cell属性映射表
     */
    public Map getMap() {
        return cells;
    }
    
    /**
     * 根据XML报文构造Properties对象
     * @param parse XML解析器
     * @return Properties对象
     */
    public static Properties parse(Root root, XMLParse parse) {
        Properties prop = new Properties(root);        
        while (parse.next()) {
            String temp = parse.getCurrentNode();
            if (!temp.equals(EaiVoConstants.NODE_NAME_CELL)) {
                break;                
            }
            parse.next();
            temp = parse.getCurrentValue();
            parse.next();
            prop.putCell(temp, parse.getCurrentValue());
        }
        return prop;
    }

    
	/**
	 * 根据XML报文构造properties对象
	 * @param xml 
	 * @return 申报文件数据对象properties节点
	 */
	public static Properties parse(Root root, Element elem) throws EaiVoException {
		Properties prop = new Properties(root);
		List lst = elem.elements(EaiVoConstants.NODE_NAME_CELL);
		if (lst != null && lst.size() > 0) {
			for (int i = 0; i < lst.size(); i++) {
				Element tmp = (Element) lst.get(i);
				String name = tmp.attributeValue(EaiVoConstants.NODE_NAME_NAME);
				String value = tmp.attributeValue(EaiVoConstants.NODE_NAME_VALUE);
				if (name != null && value != null) {
					prop.putCell(name, value);
				}
			}
		}
		return prop;
	}

	/**
	 * 输出为XML
	 * @param sb StringBuffer
	 * @param isTagInclude 是否包含Tag标签
	 * @return StringBuffer
	 */
	protected StringBuffer toString(StringBuffer sb, boolean isTagInclude) {
		if (sb == null) {
			sb = new StringBuffer();
		}

		if (isTagInclude) {
			sb.append("<properties>");
		}

		Object[] objs = cells.entrySet().toArray();
		for (int i = 0; i < objs.length; i++) {
			Map.Entry entry = (Map.Entry) objs[i];
			sb.append("<cell name=\"");
			sb.append((String) entry.getKey());
			sb.append("\" value=\"");
			sb.append((String) entry.getValue());
			sb.append("\"/>");
		}

		if (isTagInclude) {
			sb.append("</properties>");
		}

		return sb;
	}

	/**
	 * 输出为XML
	 * @param sb StringBuffer
	 * @return StringBuffer
	 */
	public StringBuffer toString(StringBuffer sb) {
		return toString(sb, true);
	}

	/**
	 * 输出为XML
	 */
	public String toString() {
		return toString(null).toString();
	}

}
