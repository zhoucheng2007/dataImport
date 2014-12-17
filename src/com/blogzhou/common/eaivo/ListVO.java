package com.blogzhou.common.eaivo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blogzhou.common.eaivo.exception.EaiVoException;
import com.blogzhou.common.eaivo.exception.EaiVoExceptionDefine;

public class ListVO  implements Serializable{
	/**
	 * 序列化版本标识ID
	 */
	private static final long serialVersionUID = 2487657713296648832L;

	/**
	 * 当前类自己的logger
	 */
	private static Logger logger = LoggerFactory.getLogger(ListVO.class);

	/**
	 * 核心系统中VO的名称
	 */
	private String name;

	/**
	 * 核心系统中set方法的名称
	 */
	private String setMethod;

	/**
	 * VO节点列表
	 */
	protected List lstVo;

	/**
	 * Root元素
	 */
	protected Root root;

	/**
	 * 私有构造
	 */
    protected ListVO(Root root) {
		this.root = root;
		lstVo = new ArrayList();
	}

	/**
	 * 缺省构造
	 */
	protected ListVO(Root root, String name, String setMethod) {
		this(root);
		this.name = name;
		this.setMethod = setMethod;
	}

	/**
	 * 增加VO
	 * @param vo VO节点
	 */
	private void addVo(VO vo) {
		lstVo.add(vo);
	}

	/**
	 * 增加VO
	 * return VO节点
	 */
	public VO addVo() {
		return addVo(null, null);
	}

    /**
     * 获取vo列表
     * @return vo列表，元素为VO
     */
    public List getVos() {
        return lstVo;
    }
    
    /**
     * 获取第一个vo
     * @return 第一个vo子节点，如果没有则返回null
     */
    public VO getFirstVo() {
        if (lstVo == null || lstVo.size() == 0) {
            return null;
        }
        return (VO) lstVo.get(0);
    }

	/**
	 * 增加VO
	 * @param name 核心系统的VO名称
	 * @param setMethod 核心系统VO调用方法
	 * @return VO节点
	 */
	public VO addVo(String name, String setMethod) {
		VO vo = null;
		if (name == null || setMethod == null) {
			vo = new VO(root);
		} else {
			vo = new VO(root, name, setMethod);
		}
		
		addVo(vo);
		return vo;
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
     * 根据XML报文构造ListVO对象
     * @param parse XML解析器
     * @return ListVO对象
     */
    public static ListVO parse(Root root, XMLParse parse) {
        ListVO lstRet = new ListVO(root);
        while (parse.next()) {
            String temp = parse.getCurrentNode();
            if (parse.isAttribute()) {
                if (temp.equals(EaiVoConstants.NODE_NAME_NAME)) {
                    lstRet.setName(parse.getCurrentValue());
                } else if (temp.equals(EaiVoConstants.NODE_NAME_SETMETHOD)) {
                    lstRet.setSetMethod(parse.getCurrentValue());
                }
            } else if (temp.equals(EaiVoConstants.NODE_NAME_VO)) {
                lstRet.addVo((VO) VO.parse(root, parse));
            } else {
                break;
            }
        }
        return lstRet;
    }
    
    /**
	 * 根据XML报文构造vo对象
	 * @param xml 
	 * @return 申报文件数据对象vo节点
	 */
	public static ListVO parse(Root root, Element elem) throws EaiVoException {
		String name = elem.attributeValue(EaiVoConstants.NODE_NAME_NAME);
		String setMethod = elem.attributeValue(EaiVoConstants.NODE_NAME_SETMETHOD);
		if (name == null && setMethod == null) {
			EaiVoException exEai = new EaiVoException(EaiVoExceptionDefine.FSTAX_EAIVO_XML_ILLEGAL_ARRAYLIST,
					"指定的XML文件格式非法，arrayList节点必须包含属性" + EaiVoConstants.NODE_NAME_NAME + "和"
							+ EaiVoConstants.NODE_NAME_SETMETHOD + "未找到", null);
			throw exEai;
		}
		ListVO lstRet = new ListVO(root, name, setMethod);

		List lst = elem.elements(EaiVoConstants.NODE_NAME_VO);
		if (lst != null && lst.size() > 0) {
			for (int i = 0; i < lst.size(); i++) {
				Element tmp = (Element) lst.get(i);
				lstRet.addVo((VO) VO.parse(root, tmp));
			}
		}

		return lstRet;
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
	 * @param sb StringBuffer
	 * @return StringBuffer
	 */
	protected StringBuffer toString(StringBuffer sb, boolean isTagInclude) {
		if (sb == null) {
			sb = new StringBuffer();
		}

		/* 输出头 */
		if (isTagInclude) {
			sb.append("<arrayList");
			if (name != null) {
				sb.append(" name=\"").append(name).append("\"");
			}
			if (setMethod != null) {
				sb.append(" setMethod=\"").append(setMethod).append("\"");
			}
			sb.append(">");
		}

		/* 输出元素 */
		for (int cnt = 0; cnt < lstVo.size(); cnt++) {
			((VO) lstVo.get(cnt)).toString(sb);
		}

		/* 输出结束 */
		if (isTagInclude) {
			sb.append("</arrayList>");
		}

		return sb;
	}

	/**
	 * 输出为XML
	 */
	public String toString() {
		return toString(null).toString();
	}
}
