package com.blogzhou.common.eaivo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.blogzhou.common.configuration.ConfigurationRegister;
import com.blogzhou.common.eaivo.exception.EaiVoException;
import com.blogzhou.common.eaivo.exception.EaiVoExceptionDefine;

public class Root implements Serializable {

	   /**
     * 序列化版本标识ID
     */
    private static final long serialVersionUID = 1232787234263859723L;

    /**
     * 配置文件名称
     */
    private static final String CONFIG_NAME = "fstax";

    /**
     * 配置项名称－－是否使用客户化（高速）XML报文解析方式
     */
    private static final String CONFIG_ITEM = "delegate.xml.customParse";

    /**
     * 当前类自己的logger
     */
    private static Logger logger = Logger.getLogger(Root.class);

    /**
     * 随机数发生器
     */
    private static Random rand = new Random();

    /**
     * 是否使用客户化（高速）XML报文解析方式
     */
    private static boolean isCustomParse = false;
    static {
        initConfig();
    }

    /**
     * 属性：sid
     */
    private String sid;

    /**
     * 属性：action
     */
    private String action;

    /**
     * 属性：blhName
     */
    private String blhName;

    /**
     * 属性：className
     */
    private String className;

    /**
     * 属性：EAI调用消耗时间
     */
    private long timeOfEAI;

    /**
     * 申报文件Head节点
     */
    private Head head;

    /**
     * 申报文件BizInfo节点
     */
    private String bizInfo;

    /**
     * 申报文件VO节点列表
     */
    private List lstVo;

    /**
     * 申报文件arrayList节点列表
     */
    private List lstListVo;

    /**
     * 申报文件linkedList节点列表
     */
    private List lstLinkedVo;

    /**
     * 申报文件properties节点
     */
    private Properties properties;

    /**
     * Cells元素存储
     */
    private Map mapCells;

    /**
     * 构造Root对象
     * @param blhName 核心接口BLH名称
     * @param className 核心接口值对象名称
     * @return 申报文件数据对象根节点
     */
    public static Root getInstance(String blhName, String className) {
        return new Root(null, blhName, className);
    }

    /**
     * 构造Root对象
     * @param sid 服务ID
     * @param blhName 核心接口BLH名称
     * @param className 核心接口值对象名称
     * @return 申报文件数据对象根节点
     */
    public static Root getInstance(String sid, String blhName, String className) {
        return new Root(sid, blhName, className);
    }
    
    /**
     * 构造Root对象 
     * @param sid 服务ID
     * @param repcode 返回代码 
     * @param returnMessage 返回信息
     * @return
     */
    public static Root newInstance(String sid,String repcode,String returnMessage){
		Root root = Root.getInstance(sid, sid, "");
		root.setSID(sid);
		root.getHead().setRepcode(repcode);
		root.getHead().setReturnMessage(returnMessage);
		return root;
	}

    /**
     * 根据XML报文构造Root对象
     * @param xml 
     * @return 申报文件数据对象根节点
     */
    public static Root parse(String xml) throws EaiVoException {
        if (isCustomParse) {
            return parse(new XMLParse(xml));
        } else {
            try {
                return parse(new SAXReader().read(new StringReader(xml)));
            } catch (DocumentException ex) {
                EaiVoException exEai = new EaiVoException(EaiVoExceptionDefine.FSTAX_EAIVO_XML_ILLEGAL, "指定的XML文件格式非法，无法进行解析：\n" + xml, null);
                exEai.log(logger);
                throw exEai;
            }
        }
    }

    public static Root parse(XMLParse parse) throws EaiVoException {
        Root root = null;
        String tmpNode = null;
        boolean isStartTag = false;
        /* 根结点 */
        parse.next();
        if (!parse.getCurrentNode().equals(EaiVoConstants.NODE_NAME_ROOT)) {
            EaiVoException exEai = new EaiVoException(EaiVoExceptionDefine.FSTAX_EAIVO_XML_ILLEGAL_ROOT,
                    "指定的XML文件格式非法，根结点名称必须为" + EaiVoConstants.NODE_NAME_ROOT + ":\n" + parse.getXML(), null);
            exEai.log(logger);
            throw exEai;
        }
        root = Root.getInstance(null, null);

        /* 根属性 */
        parse.next();
        while (parse.isAttribute()) {
            tmpNode = parse.getCurrentNode();
            if (tmpNode.equals(EaiVoConstants.NODE_NAME_SID)) {
                root.sid = parse.getCurrentValue();
            } else if (tmpNode.equals(EaiVoConstants.NODE_NAME_BLH)) {
                root.blhName = parse.getCurrentValue();
            } else if (tmpNode.equals(EaiVoConstants.NODE_NAME_CLASS)) {
                root.className = parse.getCurrentValue();
            } else if (tmpNode.equals(EaiVoConstants.NODE_NAME_ACTION)) {
                root.action = parse.getCurrentValue();
            } else if (tmpNode.equals(EaiVoConstants.NODE_NAME_EAI_TIME)) {
                try {
                    root.timeOfEAI = Long.parseLong(parse.getCurrentValue());
                } catch (NumberFormatException ex) {
                    // Nothing to do.
                }
            }
            parse.next();
        }

        /* 子节点 */
        do {
            tmpNode = parse.getCurrentNode();
            isStartTag = !parse.isEndTag();
            if (tmpNode.equals(EaiVoConstants.NODE_NAME_ROOT)) {
                break;
            }

           if (tmpNode.equals(EaiVoConstants.NODE_NAME_HEAD) && isStartTag) { // 解析head节点
                root.setHead(Head.parse(parse));
            }
   
             if (tmpNode.equals(EaiVoConstants.NODE_NAME_PROPERTIES) && isStartTag) { // 解析properties节点
                root.setProperties(Properties.parse(root, parse));
            } else if (tmpNode.equals(EaiVoConstants.NODE_NAME_VO) && isStartTag) { // 解析vo节点
                root.addVo((VO) VO.parse(root, parse));
            } else if (tmpNode.equals(EaiVoConstants.NODE_NAME_ARRAYLIST) && isStartTag) { // 解析arrayList节点
                root.addArrayList(ListVO.parse(root, parse));
            } else if (tmpNode.equals(EaiVoConstants.NODE_NAME_LINKEDLIST) && isStartTag) { // 解析linkedList节点
                root.addLinkedList((LinkedVO) LinkedVO.parse(root, parse));
            }
        } while (parse.next());

        /* 根结点结束 */
        if (!parse.getCurrentNode().equals(EaiVoConstants.NODE_NAME_ROOT)) {
            EaiVoException exEai = new EaiVoException(EaiVoExceptionDefine.FSTAX_EAIVO_XML_ILLEGAL_ROOT,
                    "指定的XML文件格式非法，没有正常结束：\n" + parse.getXML(), null);
            exEai.log(logger);
            throw exEai;
        }

        return root;
    }

    /**
     * 根据XML报文构造Root对象
     * @param xml 
     * @return 申报文件数据对象根节点
     */
    public static Root parse(Document doc) throws EaiVoException {
        /* 计算临时变量 */
        Root root = null;
        Element elem = null;
        List lst = null;
        String tmp = null;

        /* 解析根结点 */
        Element elemRoot = doc.getRootElement();
        if (!elemRoot.getName().equals(EaiVoConstants.NODE_NAME_ROOT)) {
            EaiVoException exEai = new EaiVoException(EaiVoExceptionDefine.FSTAX_EAIVO_XML_ILLEGAL_ROOT,
                    "指定的XML文件格式非法，根结点名称必须为" + EaiVoConstants.NODE_NAME_ROOT, null);
            exEai.log(logger);
            throw exEai;
        }
        root = Root.getInstance(elemRoot.attributeValue(EaiVoConstants.NODE_NAME_SID), elemRoot
                .attributeValue(EaiVoConstants.NODE_NAME_BLH), elemRoot
                .attributeValue(EaiVoConstants.NODE_NAME_CLASS));
        root.setAction(elemRoot.attributeValue(EaiVoConstants.NODE_NAME_ACTION));
        tmp = elemRoot.attributeValue(EaiVoConstants.NODE_NAME_EAI_TIME);
        if (tmp != null && tmp.length() > 0) {
            try {
                root.setTimeOfEAI(Long.parseLong(tmp));
            } catch (NumberFormatException ex) {
                // Nothing to do.
            }
        }

        /* 解析head节点 */
        elem = elemRoot.element(EaiVoConstants.NODE_NAME_HEAD);
        if (elem == null) {
            EaiVoException exEai = new EaiVoException(EaiVoExceptionDefine.FSTAX_EAIVO_XML_ILLEGAL_HEAD,
                    "指定的XML文件格式非法，节点" + EaiVoConstants.NODE_NAME_ROOT + "未找到", null);
            exEai.log(logger);
            throw exEai;
            
        }
        root.setHead(Head.parse(elem));

        /* 解析bizInfo节点 */
        elem = elemRoot.element(EaiVoConstants.NODE_NAME_BIZINFO);
        if (elem != null) {
            root.setBizInfo(elem.valueOf(EaiVoConstants.NODE_NAME_JHBS));
        }

        /* 解析vo节点 */
        lst = elemRoot.elements(EaiVoConstants.NODE_NAME_VO);
        if (lst != null && lst.size() > 0) {
            for (int i = 0; i < lst.size(); i++) {
                elem = (Element) lst.get(i);
                root.addVo((VO) VO.parse(root, elem));
            }
        }

        /* 解析arrayList节点 */
        lst = elemRoot.elements(EaiVoConstants.NODE_NAME_ARRAYLIST);
        if (lst != null && lst.size() > 0) {
            for (int i = 0; i < lst.size(); i++) {
                elem = (Element) lst.get(i);
                root.addArrayList(ListVO.parse(root, elem));
            }
        }

        /* 解析linkedList节点 */
        lst = elemRoot.elements(EaiVoConstants.NODE_NAME_LINKEDLIST);
        if (lst != null && lst.size() > 0) {
            for (int i = 0; i < lst.size(); i++) {
                elem = (Element) lst.get(i);
                root.addLinkedList((LinkedVO) LinkedVO.parse(root, elem));
            }
        }

        /* 解析properties节点 */
        elem = elemRoot.element(EaiVoConstants.NODE_NAME_PROPERTIES);
        if (elem != null) {
            root.setProperties(Properties.parse(root, elem));
        }

        return root;
    }

    /**
     * 私有构造
     * @param sid 服务ID
     * @param blhName 核心接口BLH名称
     * @param className 核心接口值对象名称
     */
    private Root(String sid, String blhName, String className) {
        this.sid = sid;
        this.blhName = blhName;
        this.className = className;
        init();
    }

    /**
     * 初始化方法
     */
    private void init() {
        head = new Head();
        lstVo = new ArrayList();
        lstListVo = new ArrayList();
        lstLinkedVo = new ArrayList();
        mapCells = new HashMap();
        setHead("queryHandler");
    }

    /**
     * 读取初始化配置文件
     */
    private synchronized static void initConfig() {
        try {
            ConfigurationRegister conf = ConfigurationRegister.getInstance();
            isCustomParse = conf.getConfiguration(CONFIG_NAME).getBoolean(CONFIG_ITEM, false);
        } catch (Exception e) {
            //Nothing to do.
        }
        if (isCustomParse) {
            logger.info("XML Parser will using Customized Parser, high speed.");
        } else {
            logger.info("XML Parser will using SAXBuilder, safety.");
        }

    }

    /**
     * 获取SID
     * @return 属性sid
     */
    public String getSID() {
        return sid;
    }

    /**
     * 设置SID
     * @return 属性sid
     */
    public void setSID(String sid) {
        this.sid = sid;
    }

    /**
     * 获取Action
     * @return 属性action
     */
    public String getAction() {
        return action;
    }

    /**
     * 设置Action
     * @return 属性action
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @return Returns the timeOfEAI.
     */
    public long getTimeOfEAI() {
        return timeOfEAI;
    }

    /**
     * @param timeOfEAI The timeOfEAI to set.
     */
    public void setTimeOfEAI(long timeOfEAI) {
        this.timeOfEAI = timeOfEAI;
    }

    /**
     * 设置头信息
     * @param jymc 交易名称
     * @param jyxh 交易序号
     */
    public void setHead(String jymc, String jyxh) {
        //head.setFqf(EaiVoConstants.HEAD_FQF_DEFAULT);
        //head.setJymc(jymc);
        //head.setJyxh(jyxh);
    }

    /**
     * 设置头信息
     * @param jymc 交易名称
     */
    public void setHead(String jymc) {
        //setHead(jymc, String.valueOf(Math.abs(rand.nextLong())));
    }

    /**
     * 设置头信息
     * @param jymc 交易名称
     */
    private void setHead(Head head) {
        this.head = head;
    }

    /**
     * 获取头信息
     * @return head节点信息
     */
    public Head getHead() {
        return head;
    }

    /**
     * 设置业务信息
     * @return 交互标识
     */
    public String getBizInfo() {
    	//return "";
        return bizInfo;
    }

    /**
     * 设置业务信息
     * @param jhbs 交互标识
     */
    public void setBizInfo(String jhbs) {
        bizInfo = jhbs;
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
     * 增加VO
     * @param name 核心系统的VO名称
     * @param setMethod 核心系统VO调用方法
     * @return VO节点
     */
    public VO addVo(String name, String setMethod) {
        VO vo = null;
        if (name == null || setMethod == null) {
            vo = new VO(this);
        } else {
            vo = new VO(this, name, setMethod);
        }

        addVo(vo);
        return vo;
    }

    /**
     * 获取vo
     * @return vo
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
     * 增加arrayList
     * @param lst arrayList节点
     */
    private void addArrayList(ListVO lst) {
        lstListVo.add(lst);
    }

    /**
     * 增加arrayList
     * @param name 核心系统的VO名称
     * @param setMethod 核心系统VO调用方法
     * @return arrayList节点
     */
    public ListVO addArrayList(String name, String setMethod) {
        ListVO lst = new ListVO(this, name, setMethod);
        addArrayList(lst);
        return lst;
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
     * 根据唯一的名称获取arrayList
     * @return arrayList，如果没有找到对应名称的arrayList子节点则返回null
     */
    public ListVO getArrayListByName(String name) {
        if (lstListVo == null || lstListVo.size() == 0) {
            return null;
        }
        else{
        	ListVO result=null;
        	for(int i=0;i<lstListVo.size();i++){
        		ListVO lstvo=(ListVO) lstListVo.get(i);
        		if(name.equalsIgnoreCase(lstvo.getName())){
        			result=lstvo;
        			break;
        		}
        	}
        	return result;
        }        
    }

    /**
     * 根据唯一的名称获取VO
     * @return VO,如果该名称对应多个VO,则返回最先找到的VO,如果没有找到对应名称的VO子节点则返回 NULL
     */
    public VO getVOByName(String name) {
        if (lstListVo == null || lstListVo.size() == 0 || name == null || "".equals(name.trim())) {
            return null;
        }
        name = name.trim();
        ListVO lstvo = null;
        List vos = null;
        VO vo = null;
    	for(int i = 0; i < lstListVo.size(); i ++){
    		lstvo = (ListVO) lstListVo.get(i);
    		if (lstvo.getVos() == null || lstvo.getVos().size() == 00) {
    			continue;
    		}
    		vos = lstvo.getVos();
    		for (int j = 0; j < vos.size(); j ++) {
    			vo = (VO) vos.get(j);
    			if (name.equalsIgnoreCase(vo.getName())) {
    				return vo;
    			}
    		}
    	}
        return null;               
    }
    
    /**
     * 增加linkedList
     * @param lst linkedList节点
     */
    private void addLinkedList(LinkedVO lst) {
        lstLinkedVo.add(lst);
    }

    /**
     * 增加linkedList
     * @return linkedList节点
     */
    public LinkedVO addLinkedList(String name, String setMethod) {
        LinkedVO lst = new LinkedVO(this, name, setMethod);
        addLinkedList(lst);
        return lst;
    }

    /**
     * 获取linkedList
     * @return linkedList
     */
    public List getLinkedLists() {
        return lstLinkedVo;
    }

    /**
     * 获取第一个linkedList，一般用于确信只有一个linkedList的情况
     * @return 第一个linkedList，如果没有任何linkedList子节点则返回null
     */
    public LinkedVO getFirstLinkedList() {
        if (lstLinkedVo == null || lstLinkedVo.size() == 0) {
            return null;
        }
        return (LinkedVO) lstLinkedVo.get(0);
    }

    /**
     * 设置申报properties节点
     * @param prop 属性节点
     */
    public void setProperties(Properties prop) {
        if (properties != null) {
            this.mapCells.remove(properties.cellMapKey);
        }
        properties = prop;
    }

    /**
     * 获取Properties
     * @return properties
     */
    public Properties getProperties() {
        if (properties == null) {
            properties = new Properties(this);
        }
        return properties;
    }

    /**
     * @return Returns the blhName.
     */
    public String getBlhName() {
        return blhName;
    }
    
    public void setBlhName(String blhName){
    	this.blhName = blhName;
   }

    /**
     * @return Returns the className.
     */
    public String getClassName() {
        return className;
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
        sb.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
        sb.append("<rootVo");
        if (sid != null) {
            sb.append(" sid=\"").append(sid).append("\"");
        }
        if (blhName != null) {
            sb.append(" blhName=\"").append(blhName).append("\"");
        }

        sb.append(">");

        /* 输出head */
        head.toString(sb);

        /* 输出vo */
        for (int cnt = 0; cnt < lstVo.size(); cnt++) {
            ((VO) lstVo.get(cnt)).toString(sb);
        }

        /* 输出arrayList */
        for (int cnt = 0; cnt < lstListVo.size(); cnt++) {
            ((ListVO) lstListVo.get(cnt)).toString(sb);
        }

        /* 输出linkedList */
        for (int cnt = 0; cnt < lstLinkedVo.size(); cnt++) {
            ((LinkedVO) lstLinkedVo.get(cnt)).toString(sb);
        }

        /* 输出properties */
        if (properties != null) {
            properties.toString(sb);
        }

        /* 输出结束 */
        sb.append("</rootVo>");

        return sb;
    }

    /**
     * 输出为XML
     */
    public String toString() {
        return toString(null).toString();
    }

    /**
     * 输出为Document对象
     * @return Document对象
     * @throws DocumentException 
     */
    public Document toDocument() throws DocumentException  {
        return new SAXReader().read(new StringReader(toString()));
    }

    /**
     * 获取CellMap
     * @param key 关键字
     * @return Map
     */
    protected Map getCellMap(String key) {
        return (Map) mapCells.get(key);
    }

    /**
     * 设置CellMap
     * @param key 关键字
     * @param cellMap Cell表
     */
    protected void putCellMap(String key, Map cellMap) {
        mapCells.put(key, cellMap);
    }

    /**
     * 查找Cell所在的位置
     * @param key 关键字
     * @return Cell所在的位置，元素类型为Map
     */
    public List findCellMap(String key) {
        List lst = new ArrayList();
        Object[] objs = mapCells.entrySet().toArray();
        for (int i = 0; i < objs.length; i++) {
            Map map = (Map) ((Map.Entry) objs[i]).getValue();
            if (map.containsKey(key)) {
                lst.add(map);
            }
        }
        return lst;
    }

    /**
     * 查找Cell的取值
     * @param key 关键字
     * @return Key所对应的值
     */
    public String findCellValue(String key) {
        List lst = findCellMap(key);
        if (lst.size() == 0) {
            return null;
        }
        return (String) ((Map) lst.get(0)).get(key);
    }

    /**
     * 测试启动函数
     * @param args 不作处理
     * @throws Exception 失败
     */
    public static void main(String args[]) throws Exception {
        long timer1 = 0;
        long timer2 = 0;
        long mem = 0;
        String xmlTest = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<rootVo blhName=\"QDzfsbBLH\" eai-time=\"203\">\n<properties>\n<cell name=\"zfjg\" value=\"0\" />\n<cell name=\"sessionID\" value=\"244010500301163467014283\" />\n<cell name=\"channelType\" value=\"CHANEL_EAI\" />\n<cell name=\"repCode\" value=\"0\" />\n<cell name=\"isRowSet\" value=\"false\" />\n<cell name=\"jgdm\" value=\"0\" />\n</properties>\n<head>\n<repcode>0</repcode>\n<jyxh>7497084590810907452</jyxh>\n</head>\n<bizInfo>\n<jhbs>null</jhbs>\n</bizInfo>\n</rootVo>\n";
        String xmlParse1 = "";
        String xmlParse2 = "";
        int testRound = 1;
        Root root = null;
        Runtime rt = Runtime.getRuntime();
        
        if (1 == 1) {
            BufferedReader br = new BufferedReader(new FileReader("D:/test.xml"));
            while ((xmlParse1 = br.readLine()) != null) {
                xmlParse2 += xmlParse1;
            }
            xmlTest = xmlParse2;
        }
        
        try {
            Thread.sleep(10);
        } catch (Exception ex) {
        }

        
        mem = rt.totalMemory() - rt.freeMemory();
        root = Root.parse(new XMLParse(xmlTest));
        mem = (rt.totalMemory() - rt.freeMemory()) - mem;
        timer1 = System.currentTimeMillis();
        for (int i = 0; i < testRound; i++) {
            root = Root.parse(new XMLParse(xmlTest));
        }
        timer1 = System.currentTimeMillis() - timer1;
        xmlParse2 = root.toString();

        System.gc();
        mem = rt.totalMemory() - rt.freeMemory();
        root = Root.parse(new SAXReader().read(new StringReader(xmlTest)));
        mem = (rt.totalMemory() - rt.freeMemory()) - mem;
        timer2 = System.currentTimeMillis();
        for (int i = 0; i < testRound; i++) {
            root = Root.parse(new SAXReader().read(new StringReader(xmlTest)));
        }
        timer2 = System.currentTimeMillis() - timer2;
        xmlParse1 = root.toString();
        System.out.println("\nSAXBuilder parse total " + timer2 + "ms \t Per " + ((timer2 + 0.0) / testRound) + "ms, first time mem= " + mem + "\n");

        System.out.println("\n\nCompare the speed: " + ((timer2 + 0.0) / timer1) + "\t\tCompare the two result: " + xmlParse1.compareTo(xmlParse2));
    }
}
