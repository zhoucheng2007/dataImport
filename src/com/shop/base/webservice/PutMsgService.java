package com.shop.base.webservice;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.loushang.waf.ComponentFactory;
import org.xml.sax.SAXParseException;

import com.shop.base.putmsg.IBasePutMsg;
import com.shop.base.webservice.pub.ReturnXmlTool;
import com.shop.base.webservice.pub.XmlUtils;
/**
 * 结算服务WebService
 * 
 * @author HAOHL
 * 
 */
public class PutMsgService{

	private static Log log = LogFactory.getLog(PutMsgService.class);

	/**
	 * 
	 * 订单结算结果通知
	 * 
	 * @param inXml
	 * @return outXml
	 * @throws SAXParseException
	 */
	public static String addPutMsg(String inXml) throws SAXParseException {

		long beginT = System.currentTimeMillis();
		if (log.isDebugEnabled())
			log.debug("PutMsgService -- addPutMsg --begin:CurTime="
					+ beginT + ",,, inXml=\n" + inXml);
		List headList = new ArrayList();
		Map map =new HashMap();
		try {
			//解析参数XML，得到List
			headList = (List) XmlUtils.Dom2Map(inXml).get("DATASET");
			//map=(Map)XmlUtils.Dom2Map(inXml).get("DATASET");
			log.debug("PutMsgService -- addPutMsg --headList==" + headList.toString());
			map=(Map) headList.get(0);
			log.debug("PutMsgService -- addPutMsg --map==" + map.toString());
		} catch (Exception e) {
			log.error("PutMsgService -- addPutMsg --xml error" + e);
			throw new RuntimeException(ReturnXmlTool.XML_ILLEGAL);
		}
		IBasePutMsg basePutMsg =(IBasePutMsg)ComponentFactory.getBean("basePutMsg");
		if (log.isDebugEnabled()) {
			log.debug("PutMsgService -- addPutMsg-----basePutMsg========" );
		}
		basePutMsg.addPutMsg(map);
		
		if (log.isDebugEnabled()) {
			log.debug("rtnMapxxx========" );
		}
		//List list = new ArrayList();//(List)rtnMap.get("DATASET");
		//list.add("")
		//将List转换成xml并返回
		//String outXml = ReturnXmlTool.getXmlFromList("DATASET", list);

	//	long endT = System.currentTimeMillis();
		//if (log.isInfoEnabled()) {
		//	log.info("PutMsgService -- addPutMsg =totaltime ="
		//			+ (endT - beginT) + "--- outXml==" + outXml);
	//	}
		return "";
	}

	
	public static void main(String[] args) {
		PutMsgService utils = new PutMsgService();
		// Element doc = utils.createXml();
		// System.out.println(doc.asXML());
		String inXml = utils.getTestString();
		//Map list = utils.Dom2Map(inXml);
		//String outXml = utils.checkCoNew(inXml);
		//System.out.println(outXml);
		//System.out.println(outXml.length());
		try {
			addPutMsg(inXml);
		} catch (SAXParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("aaaa/nbbb");
	}
	
	public String getTestString() {
		return "<BODY COM_ID='0000000163'>"
				+"<DATASET COM_ID='N10370101' ORGAN_TYPE='63' ORGAN_ID='JNLC63000002'" +
        		" REF_TYPE='00' REF_NUM='00' SEND_STATUS='00' MSG='20140207hello'" +
        		" BIZ_POINT='20010' NOTE='00' TYPE='ORG' ></DATASET>"
				/*+ "<DATASET COM_ID='0000000163' CO_NUM='50000' BORN_DATE='20130715'>"
				+ "<DATASET_LINE CO_NUM='50000' ITEM_ID='100000'></DATASET_LINE>"
				+ "<DATASET_LINE CO_NUM='50000' ITEM_ID='100001'></DATASET_LINE>"
				+ "</DATASET>"
				+ "<DATASET COM_ID='0000000164' CO_NUM='50001' BORN_DATE='20130715'>"
				+ "<DATASET_LINE CO_NUM='50001' ITEM_ID='100000'>qq</DATASET_LINE>"
				+ "<DATASET_LINE CO_NUM='50001' ITEM_ID='100002'></DATASET_LINE>"
				+ "</DATASET>"*/
				+ "</BODY>";
		//String xml = "<BODY><DATASET COM_ID='11210201(其他属性类似 COM_ID 依次添加)' ></DATASET></BODY>";
		//String xml = "<BODY CODE='000' MSG='中文描述' TRANS_TIME='yyyymmddhhmmss'><DATASET COM_ID='0000000163' DATE='10'></DATASET></BODY>";
		//return xml;
	}
}
