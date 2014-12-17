package com.shop.base.webservice.pub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlUtils {

	public static Map Dom2Map(String inXml) {
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(inXml);
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		Map map = new HashMap();
		if (doc == null)
			return map;
		Element root = doc.getRootElement();
		map = Element2Map(root);
		return map;
	}

	public static Map Element2Map(Element e) {
		Map map = new HashMap();
		map.putAll(Dom2MapBaic(e));

		List<Element> list = e.elements();
		if (list.size() > 0){
			for (Element iter : list){
				List eList = (List) map.get(iter.getName());
				if (eList == null){
					eList = new ArrayList();
					eList.add(Element2Map(iter));
					map.put(iter.getName(), eList);
				} else {
					eList.add(Element2Map(iter));
				}
			}
		}
		return map;
	}

	// 将结点的基本属性转成map
	public static Map Dom2MapBaic(Element e) {
		Map map = new HashMap();
		// 基本属性
		List attrList = e.attributes();
		for (Iterator iterator2 = attrList.iterator(); iterator2.hasNext();) {
			Attribute attribute = (Attribute) iterator2.next();
			map.put(attribute.getName(), attribute.getValue());

		}
		String text = e.getText();
		if (!text.equals(""))
			map.put(e.getName(), text);
		return map;
	}

	public Element createXml() {
		// Document doc = DocumentHelper.createDocument();
		// Element e = doc.addElement("GmMail");
		Element e = DocumentHelper.createElement("MSG");

		return e;
	}

	public static void main(String[] args) {
		XmlUtils utils = new XmlUtils();
		// Element doc = utils.createXml();
		// System.out.println(doc.asXML());
		String inXml = utils.getTestString();
		Map list = utils.Dom2Map(inXml);
		System.out.println(list);
		System.out.println(list.size());

	}

	public String getTestString() {
//		return "<BODY COM_ID='0000000163'>"
//				+ "<DATASET COM_ID='0000000163' CO_NUM='50000' BORN_DATE='20130715'>"
//				+ "<DATASET_LINE CO_NUM='50000' ITEM_ID='100000'></DATASET_LINE>"
//				+ "<DATASET_LINE CO_NUM='50000' ITEM_ID='100001'></DATASET_LINE>"
//				+ "</DATASET>"
//				+ "<DATASET COM_ID='0000000164' CO_NUM='50001' BORN_DATE='20130715'>"
//				+ "<DATASET_LINE CO_NUM='50001' ITEM_ID='100000'>qq</DATASET_LINE>"
//				+ "<DATASET_LINE CO_NUM='50001' ITEM_ID='100002'></DATASET_LINE>"
//				+ "</DATASET>"
//				+ "</BODY>";
		String xml = "<BODY CODE='000' MSG='中文描述' TRANS_TIME='yyyymmddhhmmss'><DATASET COM_ID='0000000163' DATE='10'></DATASET></BODY>";
		return xml;
	}

}
