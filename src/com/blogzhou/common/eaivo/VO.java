package com.blogzhou.common.eaivo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import com.blogzhou.common.eaivo.exception.EaiVoException;

public class VO extends Properties implements Serializable{
	/**
	 * 序列化版本标识ID
	 */
	private static final long serialVersionUID = 1934765583884552854L;

	/**
	 * 核心系统中VO的名称
	 */
	private String name;

	/**
	 * 核心系统中set方法的名称
	 */
	private String setMethod;

	/**
	 * arrayList
	 */
	private List lstListVo;

	/**
	 * 缺省构造
	 */
	protected VO(Root root) {
		super(root);
		lstListVo = new ArrayList();
	}

	/**
	 * 带参数构造
	 */
	protected VO(Root root, String name, String setMethod) {
		this(root);
		this.name = name;
		this.setMethod = setMethod;
	}

	/**
	 * 增加arrayList对象列表
	 * @param listVo arrayList对象列表
	 */
	private void addList(ListVO listVo) {
		lstListVo.add(listVo);
	}

    /**
     * 增加arrayList对象列表
     * @param name 核心系统对象名称
     * @param setMethod 核心系统调用方法
     */
    public ListVO addList(String name, String setMethod) {
        ListVO listVo = new ListVO(root, name, setMethod);
        addList(listVo);
        return listVo;
    }
    
    /**
     * 获取arrayList
     * @return arrayList
     */
    public List getArrayLists() {
        return lstListVo;
    }

    /**
     * 获取第一个arrayList，一般用于确信只有一个arrayList的情况
     * @return 第一个arrayList，如果没有任何arrayList子节点则返回null
     */
    public ListVO getFirstArrayList() {
        if (lstListVo == null || lstListVo.size() == 0) {
            return null;
        }
        return (ListVO) lstListVo.get(0);
    }

	/**
	 * @return 核心系统中VO的名称.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return 核心系统中set方法的名称.
	 */
	public String getSetMethod() {
		return setMethod;
	}

    /**
     * @param 核心系统中VO的名称.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param 核心系统中set方法的名称.
     */
    public void setSetMethod(String setMethod) {
        this.setMethod = setMethod;
    }

    /**
     * 根据XML报文构造VO对象
     * @param parse XML解析器
     * @return VO对象
     */
    public static Properties parse(Root root, XMLParse parse) {
        VO vo = new VO(root);        
        while (parse.next()) {
            String temp = parse.getCurrentNode();
            if (parse.isAttribute()) {
                if (temp.equals(EaiVoConstants.NODE_NAME_NAME)) {
                    vo.setName(parse.getCurrentValue());
                } else if (temp.equals(EaiVoConstants.NODE_NAME_SETMETHOD)) {
                    vo.setSetMethod(parse.getCurrentValue());
                }
            } else if (temp.equals(EaiVoConstants.NODE_NAME_CELL)) {
                parse.next();
                temp = parse.getCurrentValue();
                parse.next();
                vo.putCell(temp, parse.getCurrentValue());
            } else if (temp.equals(EaiVoConstants.NODE_NAME_ARRAYLIST)) {
                vo.addList(ListVO.parse(root, parse));
            } else {
                break;
            }
        }
        return vo;
    }

	/**
	 * 根据XML报文构造vo对象
	 * @param xml 
	 * @return 申报文件数据对象vo节点
	 */
	public static Properties parse(Root root, Element elem) throws EaiVoException {
		VO vo = new VO(root);
		String name; // 计算临时存储变量
		String value; // 计算临时存储变量
		List lst; // 计算临时存储变量
		Element tmp; // 计算临时存储变量
		
		/* VO 属性 */
		name = elem.attributeValue(EaiVoConstants.NODE_NAME_NAME);
		if (name != null) {
			vo.name = name;
		}
		value = elem.attributeValue(EaiVoConstants.NODE_NAME_SETMETHOD);
		if (value != null) {
			vo.setMethod = value;
		}
		
		/* VO cell 元素 */
		lst = elem.elements(EaiVoConstants.NODE_NAME_CELL);
		if (lst != null && lst.size() > 0) {
			for (int i = 0; i < lst.size(); i++) {
				tmp = (Element) lst.get(i);
				name = tmp.attributeValue(EaiVoConstants.NODE_NAME_NAME);
				value = tmp.attributeValue(EaiVoConstants.NODE_NAME_VALUE);
				if (name != null && value != null) {
					vo.putCell(name, value);
				}
			}
		}

		/* arrayList 元素 */
		lst = elem.elements(EaiVoConstants.NODE_NAME_ARRAYLIST);
		if (lst != null && lst.size() > 0) {
			for (int i = 0; i < lst.size(); i++) {
				tmp = (Element) lst.get(i);
				vo.addList(ListVO.parse(root, tmp));
			}
		}		
		return vo;
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

		/* 输出头 */
		if (isTagInclude) {
			sb.append("<vo");
			if (name != null) {
				sb.append(" name=\"").append(name).append("\"");
			}
			if (setMethod != null) {
				sb.append(" setMethod=\"").append(setMethod).append("\"");
			}
			sb.append(">");
		}

		/* 输出所有的Cell */
		super.toString(sb, false);

		/* 输出所有arayList */
		for (int i = 0; i < lstListVo.size(); i++) {
			((ListVO) lstListVo.get(i)).toString(sb);
		}
		
		/* 输出结束 */
		if (isTagInclude) {
			sb.append("</vo>");
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
