package com.blogzhou.common.eaivo;

import java.util.List;



import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.blogzhou.common.eaivo.exception.EaiVoException;
import com.blogzhou.common.eaivo.exception.EaiVoExceptionDefine;

public class LinkedVO extends ListVO {
	/**
	 * 序列化版本标识ID
	 */
	private static final long serialVersionUID = 1247669553734686454L;

	/**
	 * 当前类自己的logger
	 */
	private static Logger logger = Logger.getLogger(LinkedVO.class);

    /**
     * 空构造
     * @param root
     */
    protected LinkedVO(Root root) {
        super(root);
    }
    
	/**
	 * 缺省构造
	 */
	protected LinkedVO(Root root, String name, String setMethod) {
		super(root, name, setMethod);
	}

	/**
	 * 增加VO
	 * @param vo VO节点
	 */
	private void addVo(VO vo) {
		lstVo.add(vo);
	}

    /**
     * 根据XML报文构造ListVO对象
     * @param parse XML解析器
     * @return ListVO对象
     */
    public static ListVO parse(Root root, XMLParse parse) {
        LinkedVO lstRet = new LinkedVO(root);
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
			exEai.log(logger);
			throw exEai;
		}
		LinkedVO lstRet = new LinkedVO(root, name, setMethod);

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
		if (sb == null) {
			sb = new StringBuffer();
		}

		/* 输出头 */
		sb.append("<linkedList");
		if (getName() != null) {
			sb.append(" name=\"").append(getName()).append("\"");
		}
		if (getSetMethod() != null) {
			sb.append(" setMethod=\"").append(getSetMethod()).append("\"");
		}
		sb.append(">");

		/* 输出VO */
		super.toString(sb, false);

		/* 输出结束 */
		sb.append("</linkedList>");

		return sb;
	}
}
