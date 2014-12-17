package com.blogzhou.common.eaivo;

public class EaiVoConstants {

	/**
	 * 申报文件Head节点，交易名称：申报
	 */
	public static final String HEAD_JYMC_SB = "sbHandler";

	/**
	 * 申报文件Head节点，交易名称：查询
	 */
	public static final String HEAD_JYMC_CX = "queryHandler";

	/**
	 * 申报文件Head节点，交易名称：作废
	 */
	public static final String HEAD_JYMC_ZF = "zfHandler";

	/**
	 * 申报文件Head节点，发起方名称：默认值
	 */
	public static final String HEAD_FQF_DEFAULT = "fqf-Name";

	/**
	 * 节点名称：根结点
	 */
	public static final String NODE_NAME_ROOT = "rootVo";

	/**
	 * 节点名称：根结点->blhName属性
	 */
	public static final String NODE_NAME_BLH = "blhName";

    /**
     * 节点名称：根结点->class属性
     */
    public static final String NODE_NAME_CLASS = "class";

    /**
     * 节点名称：根结点->sid属性
     */
    public static final String NODE_NAME_SID = "sid";

    /**
     * 节点名称：根结点->sid属性
     */
    public static final String NODE_NAME_ACTION = "action";
    
    /**
     * 节点名称：根结点->eai调用时间属性
     */
    public static final String NODE_NAME_EAI_TIME = "eai-time";
    
	/**
	 * 节点名称：head节点
	 */
	public static final String NODE_NAME_HEAD = "head";

	/**
	 * 节点名称：head节点->jyxh节点
	 */
	public static final String NODE_NAME_JYXH = "jyxh";

	/**
	 * 节点名称：head节点->fqf节点
	 */
	public static final String NODE_NAME_FQF = "fqf";

	/**
	 * 节点名称：head节点->jymc节点
	 */
	public static final String NODE_NAME_JYMC = "jymc";

	/**
	 * 节点名称：head节点->repcode节点
	 */
	public static final String NODE_NAME_REPCODE = "repcode";
	
	
	public static final String NODE_NAME_RETURNMESSAGE = "returnMessage";
	
	

	/**
	 * 节点名称：bizInfo节点
	 */
	public static final String NODE_NAME_BIZINFO = "bizInfo";

	/**
	 * 节点名称：bizInfo节点->jhbs
	 */
	public static final String NODE_NAME_JHBS = "jhbs";

	/**
	 * 节点名称：vo节点
	 */
	public static final String NODE_NAME_VO = "vo";

	/**
	 * 节点名称：arrayList结点
	 */
	public static final String NODE_NAME_ARRAYLIST = "arrayList";

	/**
	 * 节点名称：linkedList结点
	 */
	public static final String NODE_NAME_LINKEDLIST = "linkedList";

	/**
	 * 节点名称：properties结点
	 */
	public static final String NODE_NAME_PROPERTIES = "properties";

	/**
	 * 节点名称：cell结点
	 */
	public static final String NODE_NAME_CELL = "cell";

	/**
	 * 节点名称：cell结点->name属性
	 */
	public static final String NODE_NAME_NAME = "name";

	/**
	 * 节点名称：vo结点->setMethod属性
	 */
	public static final String NODE_NAME_SETMETHOD = "setMethod";

	/**
	 * 节点名称：cell结点->value属性
	 */
	public static final String NODE_NAME_VALUE = "value";

	/**
	 * 常量类
	 */
	private EaiVoConstants() {
	}
}
