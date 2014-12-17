package com.blogzhou.common;

public class WebConstants {
    public static final String PARAM_SITE_DISPLAY_NAME_DEFAULT = "\u5E7F\u4E1C\u7701\u5730\u65B9\u7A0E\u52A1\u5C40";
    public static final String PARAM_SITE_NAME = "siteName";
    public static final String PARAM_SITE_DEFAULT = "gd";
    public static final String PARAM_SITE_FJ = "fj";
    public static final String PARAM_SITE_FZ = "fz";
    public static final String PARAM_SITE_PT = "pt";
    public static final String PARAM_SITE_SM = "sm";
    public static final String PARAM_SITE_QZ = "qz";
    public static final String PARAM_SITE_ZZ = "zz";
    public static final String PARAM_SITE_NP = "np";
    public static final String PARAM_SITE_LY = "ly";
    public static final String PARAM_SITE_CODE = "siteCode";
    public static final String PARAM_STYLE_NAME = "styleName";
    public static final String PARAM_PORTLET_STYLE_NAME = "portletStyleName";
    public static final String PARAM_STYLE_DEFAULT = "blue";
    public static final String PARAM_CATEGORY_ID = "categoryId";
    public static final String PARAM_CATEGORY_CODE = "categoryCode";
    public static final String PARAM_CATEGORY = "category";
    public static final String PARAM_TYPE = "type";
    public static final int PARAM_CATEGORY_ID_DEFAULT = 0;
    public static final String PARAM_WEBAPP_NAME = "webappName";
    public static final String PARAM_WEBAPP_DEFAULT = "portal";
    public static final String ATTRIBUTE_WEB_EXCEPTION = "fs_web_exception";
    public static final String PARAM_CONTROL_COMMAND_NAME = "cmd";
    public static final String URL_LOGIN = "login";
    public static final String URL_LOGOUT = "logout";
    public static final String URL_SB = "sb";
    public static final String URL_ZS = "zs";
    public static final String URL_SWSXBL = "swsxbl";
    public static final String URL_UM = "um";
    public static final String URL_YWYD = "ywyd";
    public static final String URL_YWYDSH = "ywydsh";
    public static final String URL_SSXXFB = "ssxxfb";
    public static final String URL_SSXXFBMGR = "ssxxfbMgr";
    public static final String URL_CKTS = "ckts";
    public static final String URL_SBBQUERY = "sbbQuery";
    public static final String URL_NORMALQUERY = "normalQuery";
    public static final String URL_CKTSSWJG = "cktsswjg";
    public static final String URL_ZZSFPXZ = "zzsfpxz";
    public static final String URL_ZZSFPCX = "zzsfpcx";
    public static final String URL_ZZSFPLXRZ = "zzsfplxrz";
    public static final String URL_ZZSFPLXRZ_ISDOWNLOAD = "zzsfplxrz_isdownload";
    public static final String URL_ZZSFPZXRZ = "zzsfpzxrz";
    public static final String URL_ZZSFPRZZXXG = "zzsfprzzxxg";
    public static final String URL_ZZSYBNSRSB = "zzsybnsrsb";
    public static final String ERROR = "err";
    public static final String CONTENT_TYPE = "text/html; charset=GBK";
    public static final String PSID = "psid";
    public static final String LOGIN_USER = "login_user";
    public static final String PERMISSIONS = "permissions";
    public static final String CAT_NAME_SQHD = "SQHD";
    public static final String CAT_NAME_SJGG = "SJGG";
    public static final String CAT_NAME_DFGG = "DFGG";
    public static final String CAT_NAME_XZZX = "XZZX";
    public static final String CAT_NAME_JYJS = "JYJS";
    public static final String CAT_NAME_GZCX = "GZCX";
    public static final String WSSW_CATEGORY_LIST[][] = {
        {
            "\u7A0E\u4F01\u4E92\u52A8", "SQHD"
        }, {
            "\u4E0B\u8F7D\u4E2D\u5FC3", "XZZX"
        }
    };
    public static final String WSSW_CONTENT_LIST[][] = {
        {
            "\u7701\u5C40\u516C\u544A", "SJGG"
        }, {
            "\u5730\u65B9\u516C\u544A", "DFGG"
        }
    };
    public static final Object URL_CKTSSBYS = "CKTSSBYS";
    public static final Object URL_CKTSFKWJ = "CKTSFKWJ";
    public static final Object URL_CKTSDZSB = "CKTSDZSB";
    public static final Object URL_CKTSSBYSJG = "CKTSSBYSJG";
    public static final String JSP_FILENAME = "JSP_FILENAME";
    
	/**
	 * 网报配置项的名称，用于在foresee.properties中进行查找
	 */
	public static final String FSTAX_CONFIG_ITEM_NAME = "fstax";
	
	

	/**
	 * Web页间属性传递：控制器处理结果
	 * 
	 * @see com.foresee.fstax.web.sb.SbControllerResult
	 */
	public static final String ATTRIBUTE_CONTROLLER_RESULT = "controllerResult";

	/**
	 * Web页间属性传递：需要传递给用户的说明信息
	 */
	public static final String ATTRIBUTE_USER_MESSAGE = "message";

	/**
	 * Web页间属性传递：凭证序号（申报流水号）
	 */
	public static final String ATTRIBUTE_SB_PZXH = "pzxh";

	/**
	 * Web页间属性传递：实际应交纳税额合计
	 */
	public static final String ATTRIBUTE_SB_YZSF = "yzsf";

	/**
	 * Web页间属性传递：滞纳金
	 */
	public static final String ATTRIBUTE_SB_ZNJ = "znj";

	/**
	 * Web页间属性传递：那个模块在扣款
	 */
	public static final String ATTRIBUTE_SB_FROMWHATMODULE = "fromwhatmodule";

	/**
	 * Web页间属性传递：合计计算字段（实际应交纳税额合计）
	 */
	public static final String ATTRIBUTE_SB_SUMFIELD = "sum_field";

	/**
	 * Web页间属性传递：控制器花费总时间
	 */
	public static final String ATTRIBUTE_TIME_OF_CONTROLLER = "timeOfController";

	// =================== session 中使用的变量 ===================
	/**
	 * session中保存的基础用户数据：User基本信息(CMS)
	 */
	public static final String SESSION_ATTRIBUTE_USER = "user";
	
	/**
	 * session中保存的纳税人缴费人对照关系
	 */
	public static final String SESSION_ATTRIBUTE_NSRJFRRELATION = "nsrjfr_relationmap";
	
	/**
	 * session中保存的纳税人征期日历
	 */
	public static final String SESSION_ATTRIBUTE_TAXLEVYCALENDAR = "taxlevycalendar";
	
	/**
	 * session中保存的基础数据：取纳税人基本信息(征管)
	 */
	public static final String SESSION_ATTRIBUTE_LOGININFO = "logininfo";
	/**
	 * session中保存的基础数据：取社保户基本信息(征管)
	 */
	public static final String SESSION_ATTRIBUTE_SBFININFO = "sbfinfo";
	/**
	 * session中保存的基础数据：取工会经费缴费人基本信息(征管)
	 */
	public static final String SESSION_ATTRIBUTE_GHJFINFO = "ghjfinfo";
	
	/**
	 * session中保存的基础数据：取纳税人基本信息(征管，自然人)
	 */
	public static final String SESSION_ATTRIBUTE_LOGININFO_ZRR = "logininfo_zrr";
	/**
	 * session中保存的基础数据：税种专项申报或综合申报
	 */
	public static final String SESSION_ATTRIBUTE_ZHZX = "zhzx";
	/**
	 * 用户权限
	 */
	public static final String SESSION_ATTRIBUTE_PERMISSIONS = "permissions";

	/**
	 * session中保存的基础数据：取得全部税种品目核定信息
	 */
	public static final String SESSION_ATTRIBUTE_QBSZ = "qbsz";
	
	/**
	 * session中保存的基础数据：营业税的申报表数据
	 */
	public static final String SESSION_ATTRIBUTE_YYSXMLVO = "yys_xml";
	
	
	/**
	 * session中保存的基础数据：营业税的申报表数据
	 */
	public static final String SESSION_ATTRIBUTE_YYSXMLVO_JS = "yys_js_xml";

	/**
	 * session中保存的基础数据：取得全部税种品目鉴定信息
	 */
	public static final String SESSION_ATTRIBUTE_SZJD_INFO = "szjd_info";
	
	/**
	 * session中保存的基础数据：银行帐号信息
	 */
	public static final String SESSION_ATTRIBUTE_YHZH_INFO = "yhzh_info";
	/**
	 * session中保存的基础数据：银行帐号信息（Map）
	 */
	public static final String SESSION_ATTRIBUTE_YHZH_INFO_MAP = "yhzh_info_map";
	/**
	 * session中保存的基础数据：发票准购信息
	 */
	public static final String SESSION_ATTRIBUTE_FPZG_INFO = "fpzg_info";
	/**
	 * session中保存的基础数据：缴费人基本信息(社保费)
	 */
	public static final String SESSION_ATTRIBUTE_JFRJBXX_SBF= "jfr_sbf_info";
	/**
	 * session中保存的基础数据：缴费人基本信息(工会经费)
	 */
	public static final String SESSION_ATTRIBUTE_JFRJBXX_GHJF = "jfr_ghjf_info";
	/**
	 * session中保存的基础数据：本期未申报信息
	 */
	public static final String SESSION_ATTRIBUTE_BQWSB_INFO = "bqwsb_info";
	/**
	 * session中保存的基础数据：本期已申报信息
	 */
	public static final String SESSION_ATTRIBUTE_BQYSB_INFO = "bqysb_info";
	
	/**
	 * session中保存的基础数据：取得纳税人各税种成功申报的最后所属期
	 */
	public static final String SESSION_ATTRIBUTE_ZHSSQ = "zhssq";

	/**
	 * session中保存的基础数据：取得跨区核定的管理机关代码
	 */
	public static final String SESSION_ATTRIBUTE_KQHDGLJGDM = "kqhdgljgdm";

	/**
	 * session中保存的基础数据：取得纳税人开户行信息
	 */
	public static final String SESSION_ATTRIBUTE_NSRKHHXX = "nsrkhhxx";

	/**
	 * session中保存的基础数据：取得社保户开户行信息，用于
	 */
	public static final String SESSION_ATTRIBUTE_SBHKHHXX_SKY = "sbhkhhxx_sky";
	/**
	 * session中保存的基础数据：取得工会经费户开户行信息，用于
	 */
	public static final String SESSION_ATTRIBUTE_GHJFHKHHXX_SKY = "ghjfhkhhxx_sky";
	/**
	 * session中保存的基础数据：取得纳税人开户行信息，用于
	 */
	public static final String SESSION_ATTRIBUTE_NSRKHHXX_SKY = "nsrkhhxx_sky";

	/**
	 * session中保存的基础数据：取得纳税人 汇总申报授权查询
	 */
	public static final String SESSION_ATTRIBUTE_NSRHZSBSQCX = "nsrhzsbsqcx";

	/**
	 * session中保存的基础数据：取得纳税人 委托代征核定信息查询
	 */
	public static final String SESSION_ATTRIBUTE_NSRWTDZXXCX = "nsrwtdzxxcx";

	/**
	 * session中保存的基础数据：取得纳税人 房产登记资料 信息查询
	 */
	public static final String SESSION_ATTRIBUTE_NSRFCDJZLXXCX = "nsrfcdjzlxxcx";

	/**
	 * session中保存的基础数据：取得纳税人 土地登记资料 信息查询
	 */
	public static final String SESSION_ATTRIBUTE_NSRTDDJZLXXCX = "nsrtddjzlxxcx";

	/**
	 * session中保存的基础数据：缴费单位缴费项目核定
	 */
	public static final String SESSION_ATTRIBUTE_SBGL_JFDWJFXMHD = "sbgl_jfdwjfxmhd";

	/**
	 * session中保存的基础数据：核定最低参保基数
	 */
	public static final String SESSION_ATTRIBUTE_SBGL_HDMINCBJS = "sbgl_hdmincbjs";

	/**
	 * session中保存的基础数据：单位社保号
	 */
	public static final String SESSION_ATTRIBUTE_SBGL_DWSBH = "sbgl_dwsbh";

	/**
	 * session中保存的基础数据：单位社保号
	 */
	public static final String SESSION_ATTRIBUTE_SBGL_ALL_DWSBH = "sbgl_all_dwsbh";

	/**
	 * session中保存是否正在写初始化数据的标志
	 */
	public static final String SESSION_ATTRIBUTE_SBGL_STATUS = "sbgl_status";

	/**
	 * session中保存的基础数据：取作为代理人的纳税人基本信息
	 */
	public static final String SESSION_ATTRIBUTE_LOGININFO_DEPUTY = "logininfo_deputy";

	/**
	 * session中保存的基础数据：取得为代理人的全部税种品目核定信息
	 */
	public static final String SESSION_ATTRIBUTE_QBSZ_DEPUTY = "qbsz_deputy";

	/**
	 * session中保存的基础数据：取得为代理人的纳税人开户行信息
	 */
	public static final String SESSION_ATTRIBUTE_NSRKHHXX_DEPUTY = "nsrkhhxx_deputy";

	// =======================使用于各种情形的扣款======================
	/**
	 * 缴款方式代码: 表示是通往网上系统缴款
	 */
	public static  final String DECLARE_TYPE_KK_PAYTYPECODE = "2";

	/**
	 * 用于保存（欠税）扣款预处理对象的request中key值
	 */
	public static final String DECLARE_TYPE_KK_PREPROCESS = "PreprocessInputObj";

	/**
	 * 用于保存扣款预处理结果Root的request中key值
	 */
	public static final String DECLARE_TYPE_KK_PREPROCESS_RESULTROOT = "PreprocessResultRoot";
	
	/**
	 * 用于保存欠税扣款预处理结果Root的request中key值
	 */
	public static final String DECLARE_TYPE_KK_PREPROCESS_RESULTROOT_NOTAX = "NoTaxPreprocessResultRoot";
	
	/**
	 * 用于保存未清缴税款查询结果中，空申报序号时的自定义申报序号key值
	 */
	public static final String DECLARE_TYPE_NOTAX_SELF_DEFINE_SBXH = "NoTax_self_define_sbxh";
	
	/**
	 * 用于保存欠税扣款预处理结果Root的request中key值
	 */
	public static final String DECLARE_TYPE_KK_DATA_SRC = "PreprocessResultRoot_SrcData";
	
	/**
	 * 用于保存欠税扣款预处理结果Root的request中key值
	 */
	public static final String DECLARE_TYPE_KK_DATA_SRC_SB_PAGE = "PreprocessResultRoot_SrcData_SbPage";
	
	/**
	 * 用于保存欠税扣款预处理结果Root的request中key值
	 */
	public static final String DECLARE_TYPE_KK_DATA_SRC_WQJSF_PAGE = "PreprocessResultRoot_SrcData_QqjsfPage";
	
	/**
	 * 区别调用扣款预处理、欠税扣款预处理接口的标志位，在request中含有此属性，则标识调用欠税扣款预处理接口
	 */
	public static final String DECLARE_TYPE_KK_NOTAXPAY = "page_notaxpay";
	
	/**
	 * 记录欠税扣款预处理前，查询相应申报序号以前交易状态的key
	 */
	public static final String DECLARE_TYPE_KK_STATE_KEY = "kk_state_key";
	
	/**
	 * 保存未清缴税费查询结果数据的key
	 */
	public static final String DECLARE_TYPE_WQJSF_QUERYRESULT = "wqjsf_queryResult";
	
	/**
	 * 保存当前扣款是否向省直属欠税扣款key
	 */
	public static final String DECLARE_TYPE_WQJSF_ISSZS_FLAG = "wqjsf_isSzs_flag";
	
	/**
	 * 保存涉税文书处理情况查询的key
	 */
	public static final String DECLARE_TYPE_WSCLZTCX_QUERYRESULT = "wsclztcx_queryResult";

	/**
	 * 扣款预处理,并将预处理结果显示给纳税人
	 */
	public static final String DECLARE_TYPE_KK_DETAIL = "kk_detail";

	/**
	 * 扣款预处理,并将预处理结果显示给纳税人
	 */
	public static final String DECLARE_TYPE_KK_DETAILOBJ = "kk_detailObj";

	/**
	 * 将扣款信息组包并发往国库。<BR>
	 * 包括综合申报、营业税申报、未清缴税款扣款，均使用同一个接口到国库扣款
	 */
	public static final String DECLARE_TYPE_KK_TJ = "kk_tj";
	
	/**
	 * 国库返回的扣款成功与否的Request的属性key。<BR>
	 * 值为：扣款成功，扣款失败
	 */
	public static final String DECLARE_TYPE_KK_RESULT = "kk_result";
	
	/**
	 * 银行端缴款_银行端查询缴税凭证<BR>
	 * 
	 */
	public static final String DECLARE_TYPE_YHDJK_CX = "yhdjk_cx";
	
	/**
	 * 银行端缴款_银行端查询缴税凭证明细<BR>
	 * 
	 */
	public static final String DECLARE_TYPE_YHDJK_MXCX = "yhdjk_mxcx";
	
	

	//==================== 税源登记=====================
	public static final String DECLARE_TYPE_SYDJ="sydj";
	/**
	 * 不动产登记查询
	 */
	public static final String DECLARE_TYPE_SYDJ_BDCDJCX="sydjbdcdjcx";
	// =================== 营业税申报 ===================
	/**
	 * 申报类型：营业税申报
	 */
	public static final String DECLARE_TYPE_YYSSB = "yyssb";

	/**
	 * 申报类型：营业税申报_初始化
	 */
	public static final String DECLARE_TYPE_YYSSB_INIT = DECLARE_TYPE_YYSSB
			+ "_init";

	/**
	 * 营业税各个附表之间切换计算
	 */
	public static final String DECLARE_TYPE_YYSSB_SB_TAB = DECLARE_TYPE_YYSSB
	+ "_sb_tab";
	
	public static final String DECLARE_TYPE_YYSSB_JS_TAB = DECLARE_TYPE_YYSSB
	+ "_js_tab";
	
	/**
	 * 申报类型：营业税申报_提交
	 */
	public static final String DECLARE_TYPE_YYSSB_TJ = DECLARE_TYPE_YYSSB
			+ "_tj";

	//=================== 基金费申报 ===================
	/**
	 * 申报类型：基金费申报
	 */
//	=================== 防洪保安资金申报 ===================
	/**
	 * 申报类型：防洪保安资金申报
	 */
	public static final String DECLARE_TYPE_FHBAZJSB="fhbazjsb";
	/**
	 * 申报类型：防洪保安资金_初始化
	 */
	public static final String DECLARE_TYPE_FHBAZJSB_INIT=DECLARE_TYPE_FHBAZJSB+"_init";
	/**
	 * 申报类型：防洪保安资金_计税
	 */
	public static final String DECLARE_TYPE_FHBAZJSB_JS=DECLARE_TYPE_FHBAZJSB+"_js";
	/**
	 * 申报类型：防洪保安资金_提交
	 */
	public static final String DECLARE_TYPE_FHBAZJSB_TJ=DECLARE_TYPE_FHBAZJSB+"_tj";

	
	// =================== 综合申报 ===================
	/**
	 * 申报类型：综合申报
	 */
	public static final String DECLARE_TYPE_ZHSB = "zhsb";

	/**
	 * 申报类型：综合申报_申报税种
	 */
	public static final String DECLARE_TYPE_ZHSB_SBSZ = DECLARE_TYPE_ZHSB
			+ "_sbsz";

	/**
	 * 申报类型：综合申报_税务机关
	 */
	public static final String DECLARE_TYPE_ZHSB_SWJG = DECLARE_TYPE_ZHSB
			+ "_swjg";

	/**
	 * 申报类型：综合申报_初始化
	 */
	public static final String DECLARE_TYPE_ZHSB_INIT = DECLARE_TYPE_ZHSB
			+ "_init";


	/**
	 * 申报类型：综合申报_计税
	 */
	public static final String DECLARE_TYPE_ZHSB_JS = DECLARE_TYPE_ZHSB + "_js";

	/**
	 * 申报类型：综合申报_提交
	 */
	public static final String DECLARE_TYPE_ZHSB_TJ = DECLARE_TYPE_ZHSB + "_tj";

//	 =================== 零申报 ===================
	/**
	 * 申报类型：零申报
	 */
	
	public static final String DECLARE_TYPE_LSB="lsb";
	
	/**
	 * 申报类型：零申报初始化
	 */
	public static final String DECLARE_TYPE_LSB_INIT=DECLARE_TYPE_LSB+"_init";
	
	/**
	 * 申报类型：零申报提交
	 */
	public static final String DECLARE_TYPE_LSB_TJ=DECLARE_TYPE_LSB+"_tj";

	// =================== 非核定税种申报 ===================
	/**
	 * 申报类型：非核定税种申报
	 */
	public static final String DECLARE_TYPE_FHDSB = "fhdsb";

	/**
	 * 申报类型：非核定税种申报
	 */
	public static final String DECLARE_TYPE_FHDSB_SBSZ = DECLARE_TYPE_FHDSB
			+ "_sbsz";

	/**
	 * 申报类型：非核定税种申报_初始化
	 */
	public static final String DECLARE_TYPE_FHDSB_INIT = DECLARE_TYPE_FHDSB
			+ "_init";

	/**
	 * 申报类型：非核定税种申报_提交
	 */
	public static final String DECLARE_TYPE_FHDSB_TJ = DECLARE_TYPE_FHDSB
			+ "_tj";

	/**
	 * 申报类型：非核定税种申报_税务机关
	 */
	public static final String DECLARE_TYPE_FHDSB_SWJG = DECLARE_TYPE_FHDSB
			+ "_swjg";

	/**
	 * 申报类型：非核定税种申报_记税
	 */
	public static final String DECLARE_TYPE_FHDSB_JS = DECLARE_TYPE_FHDSB
			+ "_js";

	// =================== 个税申报 ===================
	/**
	 * 申报类型：申报_上传文件
	 */
	public static final String DECLARE_TYPE_SB_UPLOAD_FILE = "sb_upload_file";
	// ================================================
	// =================== 社保费：add by hualei ===================
	// ================================================
	/**
	 * 申报类型：社保费征收
	 */
	public static final String DECLARE_TYPE_SBF = "sbfsb";
	/**
	 * 申报类型：社保费征收INIT
	 */
	public static final String DECLARE_TYPE_SBF_INIT = DECLARE_TYPE_SBF+"_init";
	/**
	 * 申报类型：社保费征收JS
	 */
	public static final String DECLARE_TYPE_SBF_JS=DECLARE_TYPE_SBF+"_js";
	/**
	 * 申报类型：社保费征收TJ
	 */
	public static final String DECLARE_TYPE_SBF_TJ=DECLARE_TYPE_SBF+"_tj";
	/**
	 * 申报类型：养老保险
	 */
	public static final String DECLARE_TYPE_SBF_YANGLAO=DECLARE_TYPE_SBF+"_yanglao";
	/**
	 * 申报类型：医疗保险
	 */
	public static final String DECLARE_TYPE_SBF_YILIAO=DECLARE_TYPE_SBF+"_yiliao";
	/**
	 * 申报类型：工伤保险
	 */
	public static final String DECLARE_TYPE_SBF_GONGSHANG=DECLARE_TYPE_SBF+"_gongshang";
	/**
	 * 申报类型：生育保险
	 */
	public static final String DECLARE_TYPE_SBF_SHENGYU=DECLARE_TYPE_SBF+"_shengyu";
	/**
	 * 申报类型：失业保险
	 */
	public static final String DECLARE_TYPE_SBF_SHIYE=DECLARE_TYPE_SBF+"_shiye";
	/**
	 * 申报类型：基金类征收
	 */
    public static final String DECLARE_TYPE_JJ="jjsb";
    /**
	 * 申报类型：基金类征收init
	 */
	public static final String DECLARE_TYPE_JJ_INIT=DECLARE_TYPE_JJ+"_init";
	/**
	 * 申报类型：基金类征收js
	 */
	public static final String DECLARE_TYPE_JJ_JS=DECLARE_TYPE_JJ+"_js";
	/**
	 * 申报类型：基金类征收tj
	 */
	public static final String DECLARE_TYPE_JJ_TJ=DECLARE_TYPE_JJ+"_tj";
	
	// ================================================
	// =================== 企业所得税 ===================
	// ================================================
	/**
	 * 申报类型：核定征收企业所得税
	 */
	public static final String DECLARE_TYPE_QYSDS = "qysds";

	// =================== 核定征收企业所得税纳税申报预缴 ===================
	/**
	 * 申报类型：核定征收企业所得税纳税申报预缴_初始化
	 */
	public static final String DECLARE_TYPE_QYSDS_NSSBYJ = DECLARE_TYPE_QYSDS
			+ "_nssbyj";

	/**
	 * 申报类型：核定征收企业所得税纳税申报预缴_初始化
	 */
	public static final String DECLARE_TYPE_QYSDS_NSSBYJ_INIT = DECLARE_TYPE_QYSDS_NSSBYJ
			+ "_init";

	/**
	 * 申报类型：核定征收企业所得税纳税申报预缴_计税
	 */
	public static final String DECLARE_TYPE_QYSDS_NSSBYJ_JS = DECLARE_TYPE_QYSDS_NSSBYJ
			+ "_js";

	/**
	 * 申报类型：核定征收企业所得税纳税申报预缴_提交
	 */
	public static final String DECLARE_TYPE_QYSDS_NSSBYJ_TJ = DECLARE_TYPE_QYSDS_NSSBYJ
			+ "_tj";

	// =================== 核定征收企业所得税年度汇算清缴 ===================
	/**
	 * 申报类型：核定征收企业所得税年度汇算清缴
	 */
	public static final String DECLARE_TYPE_QYSDS_NDHSQJ = DECLARE_TYPE_QYSDS
			+ "_ndhsqj";

	/**
	 * 申报类型：核定征收企业所得税年度汇算清缴_初始化
	 */
	public static final String DECLARE_TYPE_QYSDS_NDHSQJ_INIT = DECLARE_TYPE_QYSDS_NDHSQJ
			+ "_init";

	/**
	 * 申报类型：核定征收企业所得税年度汇算清缴_计税
	 */
	public static final String DECLARE_TYPE_QYSDS_NDHSQJ_JS = DECLARE_TYPE_QYSDS_NDHSQJ
			+ "_js";

	/**
	 * 申报类型：核定征收企业所得税年度汇算清缴_提交
	 */
	public static final String DECLARE_TYPE_QYSDS_NDHSQJ_TJ = DECLARE_TYPE_QYSDS_NDHSQJ
			+ "_tj";

	// =================== 企业所得税月（季）申报表 ===================
	/**
	 * 申报类型：企业所得税月（季）申报表
	 */
	public static final String DECLARE_TYPE_QYSDS_YJSBB = DECLARE_TYPE_QYSDS
			+ "_yjsbb";

	/**
	 * 申报类型：企业所得税月（季）申报表_初始化
	 */
	public static final String DECLARE_TYPE_QYSDS_YJSBB_INIT = DECLARE_TYPE_QYSDS_YJSBB
			+ "_init";

	/**
	 * 申报类型：企业所得税月（季）申报表_计税
	 */
	public static final String DECLARE_TYPE_QYSDS_YJSBB_JS = DECLARE_TYPE_QYSDS_YJSBB
			+ "_js";

	/**
	 * 申报类型：企业所得税月（季）申报表_提交
	 */
	public static final String DECLARE_TYPE_QYSDS_YJSBB_TJ = DECLARE_TYPE_QYSDS_YJSBB
			+ "_tj";

	/**
	 * 申报类型：企业所得税月（季）申报表
	 */
	public static final String DECLARE_TYPE_QYSDS_YJSBB_B = DECLARE_TYPE_QYSDS_YJSBB
			+ "_newb";

	/**
	 * 申报类型：企业所得税月（季）申报表_初始化(新版B表）
	 */
	public static final String DECLARE_TYPE_QYSDS_YJSBB_B_INIT = DECLARE_TYPE_QYSDS_YJSBB_B
			+ "_init";

	/**
	 * 申报类型：企业所得税月（季）申报表_初始化(新版B表）_计税
	 */
	public static final String DECLARE_TYPE_QYSDS_YJSBB_B_JS = DECLARE_TYPE_QYSDS_YJSBB_B
			+ "_js";

	/**
	 * 申报类型：企业所得税月（季）申报表_初始化(新版B表）_提交
	 */
	public static final String DECLARE_TYPE_QYSDS_YJSBB_B_TJ = DECLARE_TYPE_QYSDS_YJSBB_B
			+ "_tj";

	// =================== 企业所得税月（季）申报表 _ New A ===================
	/**
	 * 申报类型：企业所得税月（季）申报表
	 */
	public static final String DECLARE_TYPE_QYSDS_NEWA = DECLARE_TYPE_QYSDS_YJSBB
			+ "_newa";

	/**
	 * 申报类型：企业所得税月（季）申报表_初始化
	 */
	public static final String DECLARE_TYPE_QYSDS_NEWA_INIT = DECLARE_TYPE_QYSDS_NEWA
			+ "_init";

	/**
	 * 申报类型：企业所得税月（季）申报表_计税
	 */
	public static final String DECLARE_TYPE_QYSDS_NEWA_JS = DECLARE_TYPE_QYSDS_NEWA
			+ "_js";

	/**
	 * 申报类型：企业所得税月（季）申报表_提交
	 */
	public static final String DECLARE_TYPE_QYSDS_NEWA_TJ = DECLARE_TYPE_QYSDS_NEWA
			+ "_tj";
	/**
	 * 申报类型：企业所得税月（季）申报表_导入Excel文件
	 */

	public static final String FILEIMPORT = DECLARE_TYPE_QYSDS_NEWA+"_excelimport";
	// =================== 企业所得税年度纳税申报表 ===================
	/**
	 * 申报类型：企业所得税年度纳税申报表
	 */
	public static final String DECLARE_TYPE_QYSDS_NDNSSBB = DECLARE_TYPE_QYSDS
			+ "_ndnssbb";

	/**
	 * 申报类型：企业所得税年度纳税申报表_初始化
	 */
	public static final String DECLARE_TYPE_QYSDS_NDNSSBB_INIT = DECLARE_TYPE_QYSDS_NDNSSBB
			+ "_init";

	/**
	 * 申报类型：企业所得税年度纳税申报表_计税
	 */
	public static final String DECLARE_TYPE_QYSDS_NDNSSBB_JS = DECLARE_TYPE_QYSDS_NDNSSBB
			+ "_js";

	/**
	 * 申报类型：企业所得税年度纳税申报表_提交
	 */
	public static final String DECLARE_TYPE_QYSDS_NDNSSBB_TJ = DECLARE_TYPE_QYSDS_NDNSSBB
			+ "_tj";

	/**
	 * 申报类型：企业所得税b类年度纳税申报表_初始化
	 */
	public static final String DECLARE_TYPE_QYSDS_NDSBB_INIT ="qysds_ndsbb_newb_init";

	/**
	 * 申报类型：企业所得税b类年度纳税申报表_计税
	 */
	public static final String DECLARE_TYPE_QYSDS_NDSBB_JS = "qysds_ndsbb_newb_js";

	/**
	 * 申报类型：企业所得税b类年度纳税申报表_提交
	 */
	public static final String DECLARE_TYPE_QYSDS_NDSBB_TJ = "qysds_ndsbb_newb_tj";
	
	// ================================================
	// =================== 涉外企业所得税 ===================
	// ================================================
	/**
	 * 申报类型：外商投资企业和外国企业所得税季
	 */
	public static final String DECLARE_TYPE_SWQYSDS = "swqysds";

	// =================== 外商投资企业和外国企业所得税季(月)度申报表（A类）===================
	/**
	 * 申报类型：外商投资企业和外国企业所得税季(月)度申报表（A类）
	 */
	public static final String DECLARE_TYPE_SWQYSDS_A_YJSB = DECLARE_TYPE_SWQYSDS
			+ "_a_yjsb";

	/**
	 * 申报类型：外商投资企业和外国企业所得税季(月)度申报表（A类）_初始化
	 */
	public static final String DECLARE_TYPE_SWQYSDS_A_YJSB_INIT = DECLARE_TYPE_SWQYSDS_A_YJSB
			+ "_init";

	/**
	 * 申报类型：外商投资企业和外国企业所得税季(月)度申报表（A类）_计税
	 */
	public static final String DECLARE_TYPE_SWQYSDS_A_YJSB_JS = DECLARE_TYPE_SWQYSDS_A_YJSB
			+ "_js";

	/**
	 * 申报类型：外商投资企业和外国企业所得税季(月)度申报表（A类）_提交
	 */
	public static final String DECLARE_TYPE_SWQYSDS_A_YJSB_TJ = DECLARE_TYPE_SWQYSDS_A_YJSB
			+ "_tj";

	// =================== 外商投资企业和外国企业季(月)度所得税申报表（B类）===================
	/**
	 * 申报类型：外商投资企业和外国企业季(月)度所得税申报表（B类）
	 */
	public static final String DECLARE_TYPE_SWQYSDS_B_YJSB = DECLARE_TYPE_SWQYSDS
			+ "_b_yjsb";

	/**
	 * 申报类型：外商投资企业和外国企业季(月)度所得税申报表（B类）_初始化
	 */
	public static final String DECLARE_TYPE_SWQYSDS_B_YJSB_INIT = DECLARE_TYPE_SWQYSDS_B_YJSB
			+ "_init";

	/**
	 * 申报类型：外商投资企业和外国企业季(月)度所得税申报表（B类）_计税
	 */
	public static final String DECLARE_TYPE_SWQYSDS_B_YJSB_JS = DECLARE_TYPE_SWQYSDS_B_YJSB
			+ "_js";

	/**
	 * 申报类型：外商投资企业和外国企业季(月)度所得税申报表（B类）_提交
	 */
	public static final String DECLARE_TYPE_SWQYSDS_B_YJSB_TJ = DECLARE_TYPE_SWQYSDS_B_YJSB
			+ "_tj";

	// =================== 外商投资企业和外国企业所得税年度申报表（A类）===================
	/**
	 * 申报类型：外商投资企业和外国企业所得税年度申报表（A类）
	 */
	public static final String DECLARE_TYPE_SWQYSDS_A_NDSB = DECLARE_TYPE_SWQYSDS
			+ "_a_ndsb";

	/**
	 * 申报类型：外商投资企业和外国企业所得税年度申报表（A类）_初始化
	 */
	public static final String DECLARE_TYPE_SWQYSDS_A_NDSB_INIT = DECLARE_TYPE_SWQYSDS_A_NDSB
			+ "_init";

	/**
	 * 申报类型：外商投资企业和外国企业所得税年度申报表（A类）_计税
	 */
	public static final String DECLARE_TYPE_SWQYSDS_A_NDSB_JS = DECLARE_TYPE_SWQYSDS_A_NDSB
			+ "_js";

	/**
	 * 申报类型：外商投资企业和外国企业所得税年度申报表（A类）_提交
	 */
	public static final String DECLARE_TYPE_SWQYSDS_A_NDSB_TJ = DECLARE_TYPE_SWQYSDS_A_NDSB
			+ "_tj";

	// =================== 外商投资企业和外国企业所得税年度申报表（B类）===================
	/**
	 * 申报类型：外商投资企业和外国企业所得税年度申报表（B类）
	 */
	public static final String DECLARE_TYPE_SWQYSDS_B_NDSB = DECLARE_TYPE_SWQYSDS
			+ "_b_ndsb";

	/**
	 * 申报类型：外商投资企业和外国企业所得税年度申报表（B类）_初始化
	 */
	public static final String DECLARE_TYPE_SWQYSDS_B_NDSB_INIT = DECLARE_TYPE_SWQYSDS_B_NDSB
			+ "_init";

	/**
	 * 申报类型：外商投资企业和外国企业所得税年度申报表（B类）_计税
	 */
	public static final String DECLARE_TYPE_SWQYSDS_B_NDSB_JS = DECLARE_TYPE_SWQYSDS_B_NDSB
			+ "_js";

	/**
	 * 申报类型：外商投资企业和外国企业所得税年度申报表（B类）_提交
	 */
	public static final String DECLARE_TYPE_SWQYSDS_B_NDSB_TJ = DECLARE_TYPE_SWQYSDS_B_NDSB
			+ "_tj";

	// =============================================================
	// =================== 个人所得税代扣代缴汇总申报 ===================
	// =============================================================
	/**
	 * 申报类型：个人所得税代扣代缴明细
	 */
	public static final String DECLARE_TYPE_DKDJMX = "gsdkdjmx";

	/**
	 * 申报类型：个人所得税代扣代缴明细_初始化
	 */
	public static final String DECLARE_TYPE_DKDJMX_INIT = DECLARE_TYPE_DKDJMX
			+ "_init";
	
	/**
	 * 申报类型：个人所得税代扣代缴明细_表单申报
	 */
	public static final String DECLARE_TYPE_DKDJMX_BDSB = DECLARE_TYPE_DKDJMX
			+ "_bdsb";

	/**
	 * 申报类型：个人所得税代扣代缴明细_到汇总
	 */
	public static final String DECLARE_TYPE_DKDJMX_TOHZ = DECLARE_TYPE_DKDJMX
			+ "_tohz";

	/**
	 * 申报类型：个人所得税代扣代缴明细零申报
	 */
	public static final String DECLARE_TYPE_DKDJMX_LSB = DECLARE_TYPE_DKDJMX
			+ "_lsb";
	
	/**
	 * 申报类型：个人所得税代扣代缴明细_到汇总
	 */
	public static final String DECLARE_TYPE_DKDJMX_CG = DECLARE_TYPE_DKDJMX
			+ "_cg";

	/**
	 * 申报类型：个人所得税代扣代缴明细_到汇总
	 */
	public static final String DECLARE_TYPE_DKDJMX_UPLOAD = DECLARE_TYPE_DKDJMX
			+ "_upload";
	
	/**
	 * 申报类型：个人所得税代扣代缴明细申报——new
	 * @author ruanshuqin
	 */
	public static final String DECLARE_TYPE_DKDJMX_UPLOAD_NEW = DECLARE_TYPE_DKDJMX+"_upload_new";
	
	/**
	 * 申报类型：个人所得税代扣代缴明细申报--tohz——new
	 * @author ruanshuqin
	 */
	public static final String DECLARE_TYPE_DKDJMX_UPLOAD_NEW_JS = DECLARE_TYPE_DKDJMX+"_upload_new_js";
	/**
	 * 申报类型：个人所得税代扣代缴明细申报取上月数据申报
	 */
	public static final String DECLARE_TYPE_DKDJMX_GETLASTMONGTHDATA = DECLARE_TYPE_DKDJMX+"_get_lastdata";
	
	/**
	 * 申报类型：个人所得税代扣代缴明细从龙山系统导入
	 */
	public static final String DECLARE_TYPE_DKDJMX_LSDR = DECLARE_TYPE_DKDJMX+"_lsdr";
	
	/**
	 * 申报类型：个人所得税代扣代缴明细_导入数据
	 */
	public static final String DECLARE_TYPE_DKDJMX_DR = DECLARE_TYPE_DKDJMX
			+ "_dr";

	/**
	 * 申报类型：个人所得税代扣代缴明细_取消
	 */
	public static final String DECLARE_TYPE_DKDJMX_CANCEL = DECLARE_TYPE_DKDJMX
			+ "_cancel";
	
	// =============================================================
	// =================== 个人所得税代扣代缴汇总申报 ===================
	// =============================================================
	/**
	 * 申报类型：个人所得税代扣代缴汇总申报
	 */
	public static final String DECLARE_TYPE_DKDJHZ = "dkdjhz";
	
	
	/**
	 * 申报类型：个人所得税代扣代缴汇总申报_计税
	 */
	public static final String DECLARE_TYPE_DKDJHZ_JS = DECLARE_TYPE_DKDJHZ
			+ "_js";

	/**
	 * 申报类型：个人所得税代扣代缴汇总申报_初始化
	 */
	public static final String DECLARE_TYPE_DKDJHZ_INIT = DECLARE_TYPE_DKDJHZ
			+ "_init";

	/**
	 * 申报类型：个人所得税代扣代缴汇总申报_提交
	 */
	public static final String DECLARE_TYPE_DKDJHZ_TJ = DECLARE_TYPE_DKDJHZ
			+ "_tj";

	/**
	 * 申报类型：个人所得税代扣代缴汇总申报_扣款信息
	 */
	public static final String DECLARE_TYPE_DKDJHZ_KKXX = DECLARE_TYPE_DKDJHZ
			+ "_kkxx";

	/**
	 * 申报类型：个人所得税代扣代缴汇总申报_扣款成功
	 */
	public static final String DECLARE_TYPE_DKDJHZ_KKCG = DECLARE_TYPE_DKDJHZ
			+ "_kkcg";

	// =================== 个人所得税代扣代缴补录明细 ===================
	/**
	 * 申报类型：个人所得税代扣代缴补录明细
	 */
	public static final String DECLARE_TYPE_DKDJBLMX = "grsds_dkdjblmx";

	/**
	 * 申报类型：个人所得税代扣代缴补录明细_初始化
	 */
	public static final String DECLARE_TYPE_DKDJBLMX_INIT = DECLARE_TYPE_DKDJBLMX
			+ "_init";

	/**
	 * 申报类型：个人所得税代扣代缴补录明细_大数据导入
	 */
	public static final String DECLARE_TYPE_DKDJBLMX_UPLOAD_NEW = DECLARE_TYPE_DKDJBLMX
			+ "_upload_new";
	//====================烟草税纳税申报======================
	/**
	 * 烟草税：烟草税申报表
	 */
	public static final String DECLARE_TYPE_YYSNSSB = "yys_nssb";
	/**
	 * 烟草税：烟草申报_init
	 */
	public static final String DECLARE_TYPE_YYSNSSB_INIT = DECLARE_TYPE_YYSNSSB + "_init";
	/**
	 * 烟草税：烟草税申报计算
	 */
	public static final String DECLARE_TYPE_YYSNSSB_SB_JS = DECLARE_TYPE_YYSNSSB+"_sb_js";
	/**
	 * 烟草税：烟草税申报提交
	 */
	public static final String DECLARE_TYPE_YYSNSSB_SB_TJ = DECLARE_TYPE_YYSNSSB+"_sb_tj";
	//====================资源税申报表=========================
	/**
	 * 资源税：资源税申报表
	 */
	public static final String DECLARE_TYPE_ZYSSBB = "zys_sbb";
	/**
	 * 资源税：资源税申报_init
	 */
	public static final String DECLARE_TYPE_ZYSSBB_INIT = DECLARE_TYPE_ZYSSBB+"_init";
	/**
	 * 资源税：资源税申报_js
	 */
	public static final String DECLARE_TYPE_ZYSSBB_JS = DECLARE_TYPE_ZYSSBB+"_js";
	/**
	 * 资源税：资源税申报_tj
	 */
	public static final String DECLARE_TYPE_ZYSSBB_TJ = DECLARE_TYPE_ZYSSBB+"_tj";
	//====================土地增值税纳税申报表（一）=========================
	/**
	 * 土地增值税：土地增值税申报表（一）
	 */
	public static final String DECLARE_TYPE_TDZZYSBYIXF = "tdzzsxf_sb";
	/**
	 * 土地增值税：土地增值税申报表（一）_init
	 */
	public static final String DECLARE_TYPE_TDZZYSBYIXF_INIT = DECLARE_TYPE_TDZZYSBYIXF+"_init";
	/**
	 * 土地增值税：土地增值税申报表（一）_js
	 */
	public static final String DECLARE_TYPE_TDZZYSBYIXF_JS = DECLARE_TYPE_TDZZYSBYIXF+"_js";
	/**
	 * 土地增值税：土地增值税申报表（一）_tj
	 */
	public static final String DECLARE_TYPE_TDZZYSBYIXF_TJ = DECLARE_TYPE_TDZZYSBYIXF+"_tj";
	// =================== 个人所得税人员登记 ===================
	/**
	 * 个税： 人员登记
	 */
	public static final String DECLARE_TYPE_GS_RYDJ = "gs_rydj";

	/**
	 * 个税： 人员登记_init
	 */
	public static final String DECLARE_TYPE_GS_RYDJ_INIT = DECLARE_TYPE_GS_RYDJ
			+ "_init";

	/**
	 * 个税： 人员登记_init_selectdata
	 */
	public static final String DECLARE_TYPE_GS_RYDJ_INIT_SELECTDATA = DECLARE_TYPE_GS_RYDJ
			+ "_init_selectdata";

	/**
	 * 个税： 人员登记_js
	 */
	public static final String DECLARE_TYPE_GS_RYDJ_JS = DECLARE_TYPE_GS_RYDJ
			+ "_js";

	/**
	 * 个税： 人员登记_tj
	 */
	public static final String DECLARE_TYPE_GS_RYDJ_TJ = DECLARE_TYPE_GS_RYDJ
			+ "_tj";

	/**
	 * 个税： 人员登记_excel导入
	 */
	public static final String DECLARE_TYPE_GS_RYDJ_EXCEL_INIT = DECLARE_TYPE_GS_RYDJ
			+ "_excel_init";

	// =================== 个人所得税人员申报 ===================
	/**
	 * 个税： 人员申报
	 */
	public static final String DECLARE_TYPE_GN_GW_SB = "gs_gn_gw_sb";

	/**
	 * 个税： 人员申报_init
	 */
	public static final String DECLARE_TYPE_GN_GW_SB_INIT = DECLARE_TYPE_GN_GW_SB
			+ "_init";

	/**
	 * 个税： 人员申报_js
	 */
	public static final String DECLARE_TYPE_GN_GW_SB_JS = DECLARE_TYPE_GN_GW_SB
			+ "_js";

	/**
	 * 个税： 人员申报_tj
	 */
	public static final String DECLARE_TYPE_GN_GW_SB_TJ = DECLARE_TYPE_GN_GW_SB
			+ "_tj";

	/**
	 * 个税： 人员申报_excel导入
	 */
	public static final String DECLARE_TYPE_GN_GW_SB_EXCEL_INIT = DECLARE_TYPE_GN_GW_SB
			+ "_excel_init";

	// =============================================================
	// =================== 生产、经营所得投资者个人所得税 ================
	// =============================================================
	/**
	 * 申报类型：生产、经营所得投资者个人所得税
	 */
	public static final String DECLARE_TYPE_SCJYSD = "scjysd";

	// =================== 生产、经营所得投资者个人所得税月（季）申报 ===================
	/**
	 * 申报类型：生产、经营所得投资者个人所得税月（季）申报
	 */
	public static final String DECLARE_TYPE_SCJYSD_YJSB = DECLARE_TYPE_SCJYSD
			+ "_yjsb";

	/**
	 * 申报类型：生产、经营所得投资者个人所得税月（季）申报_初始化
	 */
	public static final String DECLARE_TYPE_SCJYSD_YJSB_INIT = DECLARE_TYPE_SCJYSD_YJSB
			+ "_init";

	/**
	 * 申报类型：生产、经营所得投资者个人所得税月（季）申报_计税
	 */
	public static final String DECLARE_TYPE_SCJYSD_YJSB_JS = DECLARE_TYPE_SCJYSD_YJSB
			+ "_js";

	/**
	 * 申报类型：生产、经营所得投资者个人所得税月（季）申报_提交
	 */
	public static final String DECLARE_TYPE_SCJYSD_YJSB_TJ = DECLARE_TYPE_SCJYSD_YJSB
			+ "_tj";

	// =================== 生产、经营所得投资者个人所得税年度汇算清缴 ===================
	/**
	 * 申报类型：生产、经营所得投资者个人所得税年度汇算清缴
	 */
	public static final String DECLARE_TYPE_SCJYSD_NDSB = DECLARE_TYPE_SCJYSD
			+ "_ndsb";

	/**
	 * 申报类型：生产、经营所得投资者个人所得税年度汇算清缴_初始化
	 */
	public static final String DECLARE_TYPE_SCJYSD_NDSB_INIT = DECLARE_TYPE_SCJYSD_NDSB
			+ "_init";

	/**
	 * 申报类型：生产、经营所得投资者个人所得税年度汇算清缴_计税
	 */
	public static final String DECLARE_TYPE_SCJYSD_NDSB_JS = DECLARE_TYPE_SCJYSD_NDSB
			+ "_js";

	/**
	 * 申报类型：生产、经营所得投资者个人所得税年度汇算清缴_提交
	 */
	public static final String DECLARE_TYPE_SCJYSD_NDSB_TJ = DECLARE_TYPE_SCJYSD_NDSB
			+ "_tj";

	// =================== 房产税纳税申报 ===================
	/**
	 * 申报类型：房产税纳税申报
	 */
	public static final String DECLARE_TYPE_FCS = "fcs";

	/**
	 * 申报类型：房产税纳税申报_税务机关
	 */
	public static final String DECLARE_TYPE_FCS_SWJG = DECLARE_TYPE_FCS
			+ "_swjg";

	/**
	 * 申报类型：房产税纳税申报_初始化
	 */
	public static final String DECLARE_TYPE_FCS_INIT = DECLARE_TYPE_FCS
			+ "_init";

	/**
	 * 申报类型：房产税纳税申报_计税
	 */
	public static final String DECLARE_TYPE_FCS_JS = DECLARE_TYPE_FCS + "_js";

	/**
	 * 申报类型：房产税纳税申报_提交
	 */
	public static final String DECLARE_TYPE_FCS_TJ = DECLARE_TYPE_FCS + "_tj";

	// =================== 土地使用税纳税申报 ===================
	/**
	 * 申报类型：土地使用税纳税申报
	 */
	public static final String DECLARE_TYPE_TDSYS = "tdsys";

	/**
	 * 申报类型：土地使用税纳税申报_税务机关
	 */
	public static final String DECLARE_TYPE_TDSYS_SWJG = DECLARE_TYPE_TDSYS
			+ "_swjg";

	/**
	 * 申报类型：土地使用税纳税申报_初始化
	 */
	public static final String DECLARE_TYPE_TDSYS_INIT = DECLARE_TYPE_TDSYS
			+ "_init";

	/**
	 * 申报类型：土地使用税纳税申报_计税
	 */
	public static final String DECLARE_TYPE_TDSYS_JS = DECLARE_TYPE_TDSYS
			+ "_js";

	/**
	 * 申报类型：土地使用税纳税申报_提交
	 */
	public static final String DECLARE_TYPE_TDSYS_TJ = DECLARE_TYPE_TDSYS
			+ "_tj";
	
    //==================== 重新申报 =====================
	public static final String DECLARE_TYPE_CXSB_INIT="cxsb_init";
	public static final String DECLARE_TYPE_CXSB_FORWARD="cxsb_forward";
	public static final String DECLARE_TYPE_CXSB_DELETE="cxsb_delete";
	
    // ==================== 非居民企业所得税季报 =====================
	public static final String DECLARE_TYPE_FJMQYSDSJB="fjmqysds_jb";
	public static final String DECLARE_TYPE_FJMQYSDSJB_INIT=DECLARE_TYPE_FJMQYSDSJB+"_init";
	public static final String DECLARE_TYPE_FJMQYSDSJB_JS=DECLARE_TYPE_FJMQYSDSJB+"_js";
	public static final String DECLARE_TYPE_FJMQYSDSJB_TJ=DECLARE_TYPE_FJMQYSDSJB+"_tj";
	
	// ==================== 非居民企业所得税年报 =====================
	public static final String DECLARE_TYPE_FJMQYSDSNB="fjmqysds_nb";
	public static final String DECLARE_TYPE_FJMQYSDSNB_INIT=DECLARE_TYPE_FJMQYSDSNB+"_init";
	public static final String DECLARE_TYPE_FJMQYSDSNB_JS=DECLARE_TYPE_FJMQYSDSNB+"_js";
	public static final String DECLARE_TYPE_FJMQYSDSNB_TJ=DECLARE_TYPE_FJMQYSDSNB+"_tj";
	
    // ==================== 企业所得税扣缴报告表 =====================
	public static final String DECLARE_TYPE_QYSDSKJ="qysdskj";
	public static final String DECLARE_TYPE_QYSDSKJ_INIT=DECLARE_TYPE_QYSDSKJ+"_init";
	public static final String DECLARE_TYPE_QYSDSKJ_JS=DECLARE_TYPE_QYSDSKJ+"_js";
	public static final String DECLARE_TYPE_QYSDSKJ_TJ=DECLARE_TYPE_QYSDSKJ+"_tj";
	
//	 ==================== 减税免税申报 =====================
	public static final String DECLARE_TYPE_JSMSSB="jsmssb";
	public static final String DECLARE_TYPE_JSMSSB_INIT=DECLARE_TYPE_JSMSSB+"_init";
	public static final String DECLARE_TYPE_JSMSSB_JS=DECLARE_TYPE_JSMSSB+"_js";
	public static final String DECLARE_TYPE_JSMSSB_TJ=DECLARE_TYPE_JSMSSB+"_tj";
	
	
//	 ==================== 减税免税企业所得税专项申报 =====================
	public static final String DECLARE_TYPE_JSMSSB_QYSDS=DECLARE_TYPE_JSMSSB+"_qysds";
	public static final String DECLARE_TYPE_JSMSSB_QYSDS_INIT=DECLARE_TYPE_JSMSSB_QYSDS+"_init";
	public static final String DECLARE_TYPE_JSMSSB_QYSDS_JS=DECLARE_TYPE_JSMSSB_QYSDS+"_js";
	public static final String DECLARE_TYPE_JSMSSB_QYSDS_TJ=DECLARE_TYPE_JSMSSB_QYSDS+"_tj";
	
//	 ==================== 减税免税个人所得税专项申报 =====================
	public static final String DECLARE_TYPE_JSMSSB_GRSDS=DECLARE_TYPE_JSMSSB+"_grsds";
	public static final String DECLARE_TYPE_JSMSSB_GRSDS_INIT=DECLARE_TYPE_JSMSSB_GRSDS+"_init";
	public static final String DECLARE_TYPE_JSMSSB_GRSDS_JS=DECLARE_TYPE_JSMSSB_GRSDS+"_js";
	public static final String DECLARE_TYPE_JSMSSB_GRSDS_TJ=DECLARE_TYPE_JSMSSB_GRSDS+"_tj";
	
	// ==================== 委托代征汇总申报 =====================
	/**
	 * 申报类型：委托代征汇总申报_管理机关选择
	 */
	public static final String DECLARE_TYPE_WTDZ = "wtdz";

	/**
	 * 申报类型：委托代征汇总申报_管理机关选择
	 */
	public static final String DECLARE_TYPE_WTDZ_XZGLJG = DECLARE_TYPE_WTDZ
			+ "_gljg";

	/**
	 * 申报类型：委托代征汇总申报_初始化
	 */
	public static final String DECLARE_TYPE_WTDZ_INIT = DECLARE_TYPE_WTDZ
			+ "_init";

	/**
	 * 申报类型：委托代征汇总申报_计税
	 */
	public static final String DECLARE_TYPE_WTDZ_JS = DECLARE_TYPE_WTDZ + "_js";

	/**
	 * 申报类型：委托代征汇总申报_提交
	 */
	public static final String DECLARE_TYPE_WTDZ_TJ = DECLARE_TYPE_WTDZ + "_tj";

	// ==================== 代扣代缴汇总申报 =====================
	/**
	 * 申报类型：代扣代缴汇总申报_管理机关选择
	 */
	public static final String DECLARE_TYPE_DKDJ_GLJGXZ = "sbdkdjhz_swjgxz";

	/**
	 * 申报类型：代扣代缴汇总申报_初始化
	 */
	public static final String DECLARE_TYPE_DKDJ_INIT = "sbdkdjhz_init";

	/**
	 * 申报类型：代扣代缴汇总申报_计税
	 */
	public static final String DECLARE_TYPE_DKDJ_JS = "sbdkdjhz_js";

	/**
	 * 申报类型：代扣代缴汇总申报_提交
	 */
	public static final String DECLARE_TYPE_DKDJ_TJ = "sbdkdjhz_result";

	// ====================== 初始化 ========================================

	/**
	 * 申报类型：个人所得税代扣代缴汇总申报_初始化
	 */
	public static final String DECLARE_TYPE_GRSDSDKDJHZ_INIT = "grsdsdkdjhz_init";

	/**
	 * 申报类型：生产、经营所得投资者个人所得税月（季）申报_初始化
	 */
	public static final String DECLARE_TYPE_SCJYSDTZZGRSDSYJ_INIT = "scjysdtzzgrsdsyj_init";

	// ====================== 计税 ========================================

	/**
	 * 申报类型：个人所得税代扣代缴汇总申报_计税
	 */
	public static final String DECLARE_TYPE_GRSDSDKDJHZ_JS = "grsdsdkdjhz_js";

	/**
	 * 申报类型：生产、经营所得投资者个人所得税月（季）申报_计税
	 */
	public static final String DECLARE_TYPE_SCJYSDTZZGRSDSYJ_JS = "scjysdtzzgrsdsyj_js";

	// ====================== 提交计税结果 =================================

	/**
	 * 申报类型：个人所得税代扣代缴汇总申报_提交
	 */
	public static final String DECLARE_TYPE_GRSDSDKDJHZ_TJ = "grsdsdkdjhz_tj";

	/**
	 * 申报类型：生产、经营所得投资者个人所得税月（季）申报_提交
	 */
	public static final String DECLARE_TYPE_SCJYSDTZZGRSDSYJ_TJ = "scjysdtzzgrsdsyj_tj";


	// ====================================================
	// ====================== 申报表查询打印=================
	// ====================================================
	/**
	 * 申报打印：申报打印类型与日期
	 */
	public static final String DECLARE_TYPE_SBBCXDY = "sbbcxdy";	

	/**
	 * 申报打印：申报打印类型与日期选择查询
	 */
	public static final String DECLARE_TYPE_SBBCXDY_INIT = DECLARE_TYPE_SBBCXDY + 
		"_init";


	/**
	 * 申报打印：申报打印类型与日期查询结果显示
	 */
	public static final String DECLARE_TYPE_SBBCXDY_RESULT = DECLARE_TYPE_SBBCXDY + 
		"_result";		

	/**
	 * 申报打印：申报打印报表明细打印
	 */
	public static final String DECLARE_TYPE_SBBCXDY_PRINT = DECLARE_TYPE_SBBCXDY + 
		"_print";
	/**
	 * 申报打印：打印通知单
	 */
	public static final String DECLARE_TYPE_SBBCXDY_DYTZD = DECLARE_TYPE_SBBCXDY + 
		"_dytzd";
	
	
	/**
	 * 从征管接口查询所有申报成功情况，init；
	 */
	public static final String DECLARE_TYPE_SBBCXDY_INIT_FROMINTERFACE = DECLARE_TYPE_SBBCXDY+"_init_all";
	/**
	 * 申报表查询打印，从征管接口查询所有申报成功情况，包括网报以及其它类型申报成功的信息。
	 */
	public static final String DECLARE_TYPE_SBBCXDY_RESULT_FROMINTERFACE = DECLARE_TYPE_SBBCXDY+"_result_all";
	
	/**
	 * 电子缴款凭证打印：查询
	 */
	public static final String DECLARE_TYPE_DZJKPZ_QUERY = "dzjkpz_query";
	public static final String DECLARE_TYPE_DZJKPZ_PRINT = "dzjkpz_print";
	
	// ====================================================
	// ====================== 涉税查询 ======================
	// ====================================================
	/**
	 * 查询类型：纳税户历史申报征收情况
	 */
	public static final String DECLARE_TYPE_SBQKCX = "sbqkcx";

	/**
	 * 查询类型：申报表详细信息
	 */
	public static final String DECLARE_TYPE_SBBXXXXCX = "sbbxxxxcx";

	/**
	 * 查询类型：申报表作废
	 */
	public static final String DECLARE_TYPE_SBBZF_QUERY = "sbbzf_query";

	/**
	 * 查询类型：申报表作废结果
	 */
	public static final String DECLARE_TYPE_SBBZF_RESULT= "sbbzf_result";
	
	/**
	 * 查询类型：申报情况_对应税票
	 */
	public static final String DECLARE_TYPE_SBQKDYSP = "sbqkdysp";

	/**
	 * 查询类型：网上申报情况
	 */
	public static final String DECLARE_TYPE_WSSBQK = "wssbqk";

	/**
	 * 查询类型：网上申报情况_申报表内容
	 */
	public static final String DECLARE_TYPE_WSSBQK_SBBNR = "wssbqk_sbbnr";

	/**
	 * 查询类型：网上申报情况_对应税票
	 */
	public static final String DECLARE_TYPE_WSSBQK_DYSP = "wssbqk_dysp";

	/**
	 * 查询类型：未申报情况
	 */
	public static final String DECLARE_TYPE_WSBQK = "wsbqk";

	/**
	 * 查询类型：涉税文书处理情况查询
	 */	
	public static final String DECLARE_TYPE_WSCLZTCX = "wsclztcx";
	/**
	 * 查询类型：涉税文书处理情况查询
	 */	
	public static final String DECLARE_TYPE_WBSCWBBCX = "wbscwbbcx";

	/**
	 * 查询类型：ETS扣款情况（包括所有的缴款扣款方式）
	 */
	public static final String DECLARE_TYPE_ETSKKQK = "etskkqk";

	/**
	 * 查询类型：ETS扣款情况（包括所有的缴款扣款方式）_明细
	 */
	public static final String DECLARE_TYPE_ETSKKQK_MX = "etskkqk_mx";

	/**
	 * 查询类型：涉税提醒
	 */
	public static final String DECLARE_TYPE_SSTX = "sstx_init";

	/**
	 * 查询类型：未清缴税费情况
	 */
	public static final String DECLARE_TYPE_WQJSFQK = "wqjsfqk";
	/**
	 * 查询类型：未清缴税费情况（总局个税，可实时扣款）
	 */
	public static final String DECLARE_TYPE_WQJSFQK_GS = "wqjsfqk_gs";
	
	/**
	 * 查询类型：纳税人基本信息查询_登记信息查询
	 */
	public static final String DECLARE_TYPE_NSRJBXXCX_DJ = "nsrjbxxcx_dj";

	/**
	 * 查询类型： 个税扣缴义务人个人基本信息
	 */
	public static final String DECLARE_TYPE_GSGRXXCX = "gsgrxxcx";

	/**
	 * 查询类型：纳税人基本信息查询_申报信息查询
	 */
	public static final String DECLARE_TYPE_NSRJBXXCX_SB = "nsrjbxxcx_sb";

	/**
	 * 查询类型：纳税核定查询
	 */
	public static final String DECLARE_TYPE_NSHDCX = "nshdcx";

	/**
	 * 查询类型：多渠道申报方式统计（税务人员）
	 */
	public static final String DECLARE_TYPE_DQDSBFSTJ = "dqdsbfstj";

	/**
	 * 查询类型：个税明细申报查询
	 */

	public static final String DECLARE_TYPE_GSMX_SBCX = "gsmx_sbcx";
	
	/**
	 ×查询类型：建筑安装、不动产项目征收情况查询
	 */
	public static final String DECLARE_TYPE_JBXMZHCX = "jbxmzhcx";

	// ====================================================
	// ====================== 发票抽奖 ======================
	// ====================================================
	/**
	 * 发票抽奖登记(这里最主要的是和我们将要传过来的CMD参数进行对比 然后就根据不同的参数在工厂中实现不同助手类)
	 * 我们的cmd传过来的参数必须是我们的这里申明的字符串是一样的
	 */
	public static final String DECLARE_TYPE_FPCJ = "fpcj";

	/**
	 * 发票抽奖登记
	 */
	public static final String DECLARE_TYPE_FPCJ_DJ = DECLARE_TYPE_FPCJ + "_dj";

	/**
	 * 发票抽奖登记结果显示页面
	 */
	public static final String DECLARE_TYPE_FPCJ_DJ_DONE = DECLARE_TYPE_FPCJ
			+ "_dj_done";

	/**
	 * 发票抽奖查询（发票种类代码 和 发票号码）
	 */
	public static final String DECLARE_TYPE_CJCXONE = DECLARE_TYPE_FPCJ
			+ "cxone";

	/**
	 * 发票抽奖查询（电话 和 证件）
	 */
	public static final String DECLARE_TYPE_CJCXTWO = DECLARE_TYPE_FPCJ
			+ "cxtwo";

	/**
	 * 发票抽奖查询（管理机关 和 抽奖期号）
	 */
	public static final String DECLARE_TYPE_FPCJ_CX_CJ_RESULT = DECLARE_TYPE_FPCJ
			+ "_cx_cj_result";

	/**
	 * 发票抽奖验证
	 */
	public static final String DECLARE_TYPE_CJCHECK = DECLARE_TYPE_FPCJ
			+ "check";

	/**
	 * 行数限制，发票抽奖登记页面
	 */
	public static final int ROW_NUM_FPCJ_DJ = 10;

	// ====================================================
	// ====================== 发票管理 ======================
	// ====================================================
	/**
	 * 发票管理： 发票缴销登记
	 */
	public static final String DECLARE_TYPE_FPGL_FPJX = "fpjx";

	/**
	 * 发票管理： 发票缴销登记-初始化
	 */
	public static final String DECLARE_TYPE_FPGL_FPJX_INIT = "fpjx_init";

	/**
	 * 发票管理： 发票缴销登记-提交
	 */
	public static final String DECLARE_TYPE_FPGL_FPJX_SUBMIT= "fpjx_submit";
	
	/**
	 * 发票管理： 发票作废登记-初始化
	 */
	public static final String DECLARE_TYPE_FPGL_FPZF_INIT = "fpzf_init";
	
	/**
	 * 发票管理： 发票作废登记-保存
	 */
	public static final String DECLARE_TYPE_FPGL_FPZF_SUBMIT = "fpzf_submit";
	

	/**
	 * 发票管理： 发票验销-导入
	 */
	public static final String DECLARE_TYPE_FPGL_FPYX_DR = DECLARE_TYPE_FPGL_FPJX
			+ "_dr";

	/**
	 * 发票管理： 发票验销-上一步
	 */
	public static final String DECLARE_TYPE_FPGL_FPYX_BACK = DECLARE_TYPE_FPGL_FPJX
			+ "_back";

	/**
	 * 发票管理： 发票验销
	 */
	public static final String DECLARE_TYPE_FPGL_FPYX_JS = DECLARE_TYPE_FPGL_FPJX
			+ "_js";

	/**
	 * 发票管理：发票核定
	 */
	public static final String DECLARE_TYPE_FPGL_FPHDCX = "fphdcx";

	/**
	 * 发票管理：发票核定查询-初始化
	 */
	public static final String DECLARE_TYPE_FPGL_FPHDCX_INIT = DECLARE_TYPE_FPGL_FPHDCX
			+ "_init";

	/**
	 * 发票管理：发票核定查询-提交
	 */
	public static final String DECLARE_TYPE_FPGL_FPHDCX_TJ = DECLARE_TYPE_FPGL_FPHDCX
			+ "_tj";

	/**
	 * 发票管理：发票领购情况查询
	 */
	public static final String DECLARE_TYPE_FPGL_FPQKCX_INIT = "fpqkcx_init";

	/**
	 * 发票管理：发票领购情况查询
	 */
	public static final String DECLARE_TYPE_FPGL_FPQKCX_TJ = "fpqkcx_tj";

	/**
	 * 发票管理：发票库存查询
	 */
	public static final String DECLARE_TYPE_FPGL_FPKCCX = "fpkccx";

	public static final String DECLARE_TYPE_FPGL_FPKCCX1 = "testfpkccx";

	/**
	 * 发票管理：发票逾期未缴销查询
	 */
	public static final String DECLARE_TYPE_FPGL_FPYQWJXCX = "fpyqwjxcx";
	/**
	 * 不动产可开发票征收记录初始化
	 */
	public static final String DECLARE_TYPE_FPGL_BDCXSXX_INIT="fpgl_bdcxsxx_init";
	/**
	 * 不动产可开发票
	 */
	public static final String DECLARE_TYPE_FPGL_BDCXSXX_TEMP="fpgl_bdcxsxx_temp";
	/**
	 * 不动产可开发票
	 */
	public static final String DECLARE_TYPE_FPGL_BDCXSXX_TJ="fpgl_bdcxsxx_tj";
	/**
	 * 不动产销售信息录入
	 */
	public static final String DECLARE_TYPE_FPGL_BDCXSXX_INSERT="fpgl_bdcxsxx_insert";
	/**
	 * 不动产销售信息查询
	 */
	public static final String DECLARE_TYPE_FPGL_BDCXSXX_CX="fpgl_bdcxsxx_cx";
	/**
	 * 不动产销售信息查询（本地表）
	 */
	public static final String DECLARE_TYPE_FPGL_BDCXSXXCX_INIT="fpgl_bdcxsxxcx_init";
	/**
	 * 不动产销售信息删除
	 */
	public static final String DECLARE_TYPE_FPGL_BDCXSXX_DEL="fpgl_bdcxsxx_del";
	/**
	 * 不动产销售信息删除(NEXT)
	 */
	public static final String DECLARE_TYPE_FPGL_BDCXSXXSC_TEMP="fpgl_bdcxsxxsc_temp";
	// --------------------------------------发票清缴纳税申报
	/**
	 * 申报类型：发票清缴纳税申报
	 */
	public static final String DECLARE_TYPE_FPGL_FPQJSB = "fpqjsb";

	/**
	 * 申报类型：发票清缴纳税申报_申报税种
	 */
	public static final String DECLARE_TYPE_FPGL_FPQJSB_SBSZ = "fpqjsb_sbsz";

	/**
	 * 申报类型：发票清缴纳税申报_初始化
	 */
	public static final String DECLARE_TYPE_FPGL_FPQJSB_INIT = "fpqjsb_init";

	/**
	 * 申报类型：发票清缴纳税申报_计税
	 */
	public static final String DECLARE_TYPE_FPGL_FPQJSB_JS = "fpqjsb_js";

	/**
	 * 申报类型：发票清缴纳税申报_提交
	 */
	public static final String DECLARE_TYPE_FPGL_FPQJSB_TJ = "fpqjsb_tj";

	/**
	 * 个税文件查询－提交
	 */
	public static final String DECLARE_TYPE_GSWJ_CX = "gswj_cx";

	/**
	 * 个税文件删除
	 */
	public static final String DECLARE_TYPE_GSWJ_DEl = "gswj_del";

	/**
	 * 未清缴税款扣款
	 */
	public static final String DECLARE_TYPE_WQJSK_KK = "wqjsk_kk";

	/**
	 * 未清缴税款扣款
	 */
	public static final String DECLARE_TYPE_GS_QUERY_PASSWORD = "gs_query_password";

	/**
	 * 未清缴情况提交
	 */
	public static final String DECLARE_TYPE_WQJSK_TJ = "wqjsk_tj";

	// ====================================================
	// ====================== 社保管理 ======================
	// ====================================================

	/**
	 * session中保存的基础数据：社保取缴费单位所有社保号以及社保号码对应得登记状态 单位所有社保号信息查询
	 */
	public static final String SESSION_ATTRIBUTE_NSRDWSYSBHXXCX = "nsrdwsysbhxxcx";

	/**
	 * 社保管理－初始化数据
	 */
	public static final String DECLARE_TYPE_SBGL_INIT = "sbgl_init";

	/**
	 * 社保增员登记
	 */
	public static final String DECLARE_TYPE_SBGL_ZY = "sbgl_zy";

	/**
	 * 社保增员登记 初始化
	 */
	public static final String DECLARE_TYPE_SBGL_ZY_INIT = DECLARE_TYPE_SBGL_ZY
			+ "dj_init";

	/**
	 * 社保增员登记(通过个人社保号增员)
	 */
	public static final String DECLARE_TYPE_SBGL_ZYBYSBH_INIT = DECLARE_TYPE_SBGL_ZY
			+ "bysbh_init";

	/**
	 * 社保增员登记(通过个人社保号增员方式)
	 */
	public static final String DECLARE_TYPE_SBGL_ZYBYSBH_TJ = DECLARE_TYPE_SBGL_ZY
			+ "bysbh_tj";

	/**
	 * 社保增员登记 校验
	 */
	public static final String DECLARE_TYPE_SBGL_ZY_JY = DECLARE_TYPE_SBGL_ZY
			+ "jy_jy";

	/**
	 * 社保增员登记 EXCEL上传成功 初始化
	 */
	public static final String DECLARE_TYPE_SBGL_ZY_INIT_PAGE = "sbdj_zy_init_page";

	/**
	 * 社保增员登记 EXCEL上传成功 校验
	 */
	public static final String DECLARE_TYPE_SBGL_ZY_JY_PAGE = DECLARE_TYPE_SBGL_ZY
			+ "jy_jy_page";

	/**
	 * 社保增员登记 保存
	 */
	public static final String DECLARE_TYPE_SBGL_ZY_BC = DECLARE_TYPE_SBGL_ZY
			+ "bc_bc";

	/**
	 * 社保增员登记 excel文件上传
	 */
	public static final String DECLARE_TYPE_SBGL_ZYSB_EXCEL = DECLARE_TYPE_SBGL_ZY
			+ "sb_excel";

	/**
	 * 社保增员登记 excel文件上传
	 */
	public static final String DECLARE_TYPE_SBGL_ZYSB_UPLOADEXCEL = DECLARE_TYPE_SBGL_ZY
			+ "sb_uploadexcel";

	/**
	 * 社保减员登记 初始化
	 */
	public static final String DECLARE_TYPE_SBGL_JYSB = "sbgl_jysb";

	/**
	 * 社保减员登记 初始化
	 */
	public static final String DECLARE_TYPE_SBGL_JYSB_INIT = DECLARE_TYPE_SBGL_JYSB
			+ "_init";

	/**
	 * 社保减员登记 提交
	 */
	public static final String DECLARE_TYPE_SBGL_JYSB_TJ = DECLARE_TYPE_SBGL_JYSB
			+ "_tj";

	/**
	 * 社保减员登记 保存
	 */
	public static final String DECLARE_TYPE_SBGL_JYSB_BC = DECLARE_TYPE_SBGL_JYSB
			+ "_bc";

	/**
	 * 社保减员登记 excel文件上传初始化
	 */
	public static final String DECLARE_TYPE_SBGL_JYSB_EXCEL = DECLARE_TYPE_SBGL_JYSB
			+ "_excel";

	/**
	 * 社保减员登记 excel文件上传
	 */
	public static final String DECLARE_TYPE_SBGL_JYSB_UPLOADEXCEL = DECLARE_TYPE_SBGL_JYSB
			+ "_uploadexcel";

	/**
	 * 社保减员登记 excel文件上传 翻页
	 */
	public static final String DECLARE_TYPE_SBGL_JYSB_UPLOADEXCEL_PAGE = DECLARE_TYPE_SBGL_JYSB
			+ "_uploadexcel_page";

	/**
	 * 社保减员登记 在册初始化
	 */
	public static final String DECLARE_TYPE_SBGL_JYSB_ZCINIT = DECLARE_TYPE_SBGL_JYSB
			+ "_zcinit";

	/**
	 * 社保减员登记 在册初始化
	 */
	public static final String DECLARE_TYPE_SBGL_JYSB_EXCELTJ = DECLARE_TYPE_SBGL_JYSB
			+ "_exceltj";

	/**
	 * 社保减员登记 在册查询
	 */
	public static final String DECLARE_TYPE_SBGL_JYSB_ZCCX = DECLARE_TYPE_SBGL_JYSB
			+ "_zccx";

	/**
	 * 社保减员登记 在册提交
	 */
	public static final String DECLARE_TYPE_SBGL_JYSB_ZCTJ = DECLARE_TYPE_SBGL_JYSB
			+ "_zctj";

	/**
	 * 社保管理－本月增减员清单查询
	 */
	public static final String DECLARE_TYPE_SBGL_ZJYQDCX_INIT = "sbgl_zjyqdcx_init";

	/**
	 * 社保管理－作废申报init
	 */
	public static final String DECLARE_TYPE_SBGL_ZFSB_INIT = "sbgl_zfsb_init";

	/**
	 * 社保管理－作废申报
	 */
	public static final String DECLARE_TYPE_SBGL_ZFSB_ZF = "sbgl_zfsb_zf";

	/**
	 * 社保管理－社保费申报情况查询_init
	 */
	public static final String DECLARE_TYPE_SBGL_SBQKCX_INIT = "sbgl_sbqkcx_init";

	/**
	 * 社保管理－社保费申报情况查询
	 */
	public static final String DECLARE_TYPE_SBGL_SBQKCX_lIST = "sbgl_sbqkcx_list";

	/**
	 * 社保管理－社会保险费欠税查询init
	 */
	public static final String DECLARE_TYPE_SBGL_SBQFQKCX_INIT = "sbgl_sbqfqkcx_init";

	/**
	 * 社保管理－社会保险费欠税查询
	 */
	public static final String DECLARE_TYPE_SBGL_SBQFQKCX_lIST = "sbgl_sbqfqkcx_list";

	/**
	 * 社保管理－社会保险费申报缴款个人明细查询Init
	 */
	public static final String DECLARE_TYPE_SBGL_SBGRMXCX_INIT = "sbgl_sbgrmxcx_init";

	/**
	 * 社保管理－社会保险费申报缴款个人明细查询
	 */
	public static final String DECLARE_TYPE_SBGL_SBGRMXCX_lIST = "sbgl_sbgrmxcx_list";

	/**
	 * 社保管理－社会保险费申报（excel文件上传）
	 */
	public static final String DECLARE_TYPE_SBGL_SHBXFSB_EXCEL = "sbgl_shbxfsb_excel";

	/**
	 * 社保申报初始化
	 */
	public static final String DECLARE_TYPE_SBGL_SBSB = "sbgl_sbsb";

	/**
	 * 社保申报初始化
	 */
	public static final String DECLARE_TYPE_SBGL_SBSB_INIT = DECLARE_TYPE_SBGL_SBSB
			+ "_init";

	/**
	 * 社保申报翻页提交
	 */
	public static final String DECLARE_TYPE_SBGL_SBSB_FYTJ = DECLARE_TYPE_SBGL_SBSB
			+ "_fytj";

	/**
	 * 社保申报汇总
	 */
	public static final String DECLARE_TYPE_SBGL_SBSB_HZ = DECLARE_TYPE_SBGL_SBSB
			+ "_hz";

	/**
	 * 社保申报明细提交
	 */
	public static final String DECLARE_TYPE_SBGL_SBSB_MXTJ = DECLARE_TYPE_SBGL_SBSB
			+ "_mxtj";

	/**
	 * 申报类型：契税申报_初始化
	 * @date 2011-04-05
	 * @author zhoucheng
	 */
	public static final String DECLARE_TYPE_QS = "qs";

	/**
	 * 申报类型：契税申报_初始化
	 * @date 2011-04-05
	 * @author zhoucheng
	 */
	public static final String DECLARE_TYPE_QS_INIT = DECLARE_TYPE_QS+ "_init";
	
	/**
	 * 申报类型：契税申报_下一步
	 * @date 2011-04-07
	 * @author zhoucheng
	 */
	public static final String DECLARE_TYPE_QS_JS = DECLARE_TYPE_QS+ "_js";
	
	/**
	 * 申报类型：契税申报_提交
	 * @date 2011-04-07
	 * @author zhoucheng
	 */
	public static final String DECLARE_TYPE_QS_TJ = DECLARE_TYPE_QS+ "_tj";
	
	/**
	 * 申报类型：城镇土地使用税申报_初始化
	 * @date 2011-04-05
	 * @author zhoucheng
	 */
	public static final String DECLARE_TYPE_CZTDSYS = "cztdsys";

	/**
	 * 申报类型：城镇土地使用税申报_初始化
	 * @date 2011-04-05
	 * @author zhoucheng
	 */
	public static final String DECLARE_TYPE_CZTDSYS_INIT = DECLARE_TYPE_CZTDSYS+ "_init";
	
	/**
	 * 申报类型：城镇土地使用税申报_下一步
	 * @date 2011-04-07
	 * @author zhoucheng
	 */
	public static final String DECLARE_TYPE_CZTDSYS_JS = DECLARE_TYPE_CZTDSYS+ "_js";
	
	/**
	 * 申报类型：城镇土地使用税申报_提交
	 * @date 2011-04-07
	 * @author zhoucheng
	 */
	public static final String DECLARE_TYPE_CZTDSYS_TJ = DECLARE_TYPE_CZTDSYS+ "_tj";
	
	/**
	 * 财务报表Excel上传初始化
	 */
	public static final String DECLARE_TYPE_CWBB_EXCEL_INIT = "cwbb_excel_init";

	/**
	 * 财务报表元数据常量
	 */
	public static final String DECLARE_TYPE_CWBB_EXCEL_ADDYSJ = "cwbb_excel_addysj";

	/**
	 * 财务报表excel上传结果
	 */
	public static final String DECLARE_TYPE_CWBB_RESULT = "cwbb_excel_result";

	public static final String DECLARE_TYPE_CWBB_CX_INIT = "cwbb_cx_init";
	
	public static final String DECLARE_TYPE_CWBB_COMMON_TJ = "cwbb_tj";

	// 管理服务提交
	public static final String DECLARE_TYPE_GLFW_COMMON_TJ = "glfw_tj";
	
	// 管理服务提交
	public static final String DECLARE_TYPE_GLFW_COMMON_TJ_BYROOTVO = "glfw_tj_byrootvo";	

	// 管理服务建筑工程提交
	public static final String DECLARE_TYPE_GLFW_JZGC_TJ = "glfw_jzgc_tj";

	//管理服务建筑工程查询外管证
	public static final String DECLARE_TYPE_GLFW_JZGC_INIT = "glfw_jzgc_init";
	//	管理服务建筑工程查询外管证
	public static final String DECLARE_TYPE_GLFW_JZGCXMCX = "jzgcxmcx";

	// 企业注册
	public static final String DECLARE_TYPE_GLFW_QY_ZC = "glfw_qy_zc";
	//接收总局软件个税汇总申报信息
	public static final String DECLARE_TYPE_GSSB_HZSB = "gssb_hzsb";
	/**
	 * 省属企业认定保存,查询
	 */
	public static final String DECLARE_TYPE_GLFW_SSQYRD_TJ="glfw_ssqyrd_tj";
	public static final String DECLARE_TYPE_GLFW_SSQYRD_CX="glfw_ssqyrd_cx";
	/**
	 * 重点税源模块
	 */
	public static final String DECLARE_TYPE_ZDSY="zdsy";
	public static final String DECLARE_TYPE_ZDSY_XXB_INIT=DECLARE_TYPE_ZDSY+"_xxb_init";
	public static final String DECLARE_TYPE_ZDSY_CPB_INIT=DECLARE_TYPE_ZDSY+"_cpb_init";
	public static final String DECLARE_TYPE_ZDSY_FDC_INIT=DECLARE_TYPE_ZDSY+"_fdc_init";
	public static final String DECLARE_TYPE_ZDSY_SHB_INIT=DECLARE_TYPE_ZDSY+"_shb_init";
	public static final String DECLARE_TYPE_ZDSY_CWB_INIT=DECLARE_TYPE_ZDSY+"_cwb_init";
	/**
	 * 不动产对应的seq table
	 */
	public static final String DECLARE_SEQ_BDC = "SEQ_FSTAX_WSSW_BDC";

	/**
	 * 允许基金费申报的地区代码(市级)
	 */
	public static final String AREA_CODE="AreaCodes";
	
	/**
	 * 允许基金费申报的地区代码(县级)
	 */
	public static final String AREA_CODE_EXT="AreaCodes_ext";
	
	/**
	 * 涉税提醒标志：1为展现涉税提醒页面；0：为不展现涉税提醒页面
	 */
	public static final String FSTAX_SSTX_FLAG="fstax.sstx.flag";
	
	/**
	 * 涉税提醒间隔时间，登录后多长时间展现涉税提醒页面(时间单位为秒)
	 */
	public static final String FSTAX_SSTX_INTERVALTIME="fstax.sstx.IntervalTime";
	
	/**
	 * 常量类，无需构造函数
	 */
	private WebConstants() {
		// TODO 刘：TaxWebConstants常量类
	}
    /**
     * 鉴定信息
     * session中保存的基础数据：鉴定信息
     */
    public static final String SESSION_ATTRIBUTE_QYSDS_JDXX = "qysds_jdxx";
    
    /**
     * 省直属 纳税人基本信息
     * session中保存的基础数据：取纳税人基本信息(征管)
     */
    public static final String SESSION_ATTRIBUTE_SZS_LOGININFO = "szs_logininfo"; 
    /**
     * 省直属 企业认定信息
     * session中保存的基础数据：省直属 企业认定信息
     */
    public static final String SESSION_ATTRIBUTE_SZS_RDINFO = "szs_rdinfo";
    /**
     * 省直属 鉴定信息
     * session中保存的基础数据：省直属 鉴定信息
     */
    public static final String SESSION_ATTRIBUTE_SZS_JDXX = "szs_jdxx";    
    /**
     * 发票兑奖信息所用的pzxh
     * session中保存的基础数据：发票兑奖信息录入时的凭证序号
     */
    public static final String SESSION_ATTRIBUTE_PZXH_FPDJXX = "fpdjxx_pzxh";    
    /**
     * 维护页面模块信息 扣款
     */
    public static final String FSTAX_WEIHU_KK = "WEIHU_KOUKUAN_A";  
    
    //2010-06-11银行端查询解锁
    /**
	 * 查询类型：银行端解锁查询
	 */
	public static final String YHDJS_CX = "yhdjscx";
	
	// add by  hualei 
	/**
	 * 用户登录时我的信息中“税务登记”中信息的查询 2010-08-05
	 */
//	public static final String MYINFO_USER_LOGIN="registinfo";
//	/**
//	 * 用户登录时我的信息中“征管鉴定”中信息的查询 2010-08-09
//	 */
//	public static final String MYINFO_USER_LOGIN_ZGJD="getNsjdInfo";
//	/**
//	 * 用户登录时我的信息中“发票信息”中信息的查询 2010-08-09
//	 */
//	public static final String MYINFO_USER_LOGIN_FGZG="getFpzgInfo";
   //add end!
   
	public static final String MYINFO_TYPE_FPXX = "fpcx"; //add by hgg
	/**
	 * 申报凭证序号
	 */
	public static final String PZXH="pzxh";
	/**
	 * 申报表修改标识
	 */
	public static final String SB_MODIFY="sb_modify";
	/**
	 * 申报凭证序号前缀
	 */
	public static final String PZXH_PREFIX="25301";
	/**
	 * 电子缴款凭证编号前缀
	 */
	public static final String DZJKPZBH_PREFIX="DZPZ";
	/**
	 * 桌面设置 
	 */
	public static final String DECLARE_TYPE_ZMSZ_SAVE = "zmsz_save";
	public static final String DECLARE_TYPE_ZMSZ_LOAD = "zmsz_load";
	public static final String DECLARE_TYPE_ZMSZ_MAIN_LOAD = "zmsz_main_load";
	/**
	 * 综合查询  2010-09-16 hualei
	 */
	public static final String ZHCX_DECLARL="zhcx";
	public static final String ZHCX_DECLARL_SBQKCX=ZHCX_DECLARL+"_sbqkcx";
	public static final String ZHCX_DECLARL_WSBQKCX = ZHCX_DECLARL+"_wsbqkcx";
	public static final String ZHCX_DECLARL_FPCX = ZHCX_DECLARL+"_fpcx";
	public static final String ZHCX_DECLARL_SFTZ = ZHCX_DECLARL+"_sftz";
	public static final String ZHCX_DECLARL_SSWS = ZHCX_DECLARL+"_ssws";	
	/**
	 * 个人独资合伙企业个人所得税申报  2010-11-03 LiuJiaWei
	 */
	public static final String GRDZHHSDS_INIT="grdzhhsds_init";
	public static final String GRDZHHSDS_JS="grdzhhsds_js";
	public static final String GRDZHHSDS_TJ="grdzhhsds_tj";	
	/**
	 * 合伙企业投资者个人所得税汇总纳税申报
	 */
	public static final String HHQYTZZSDS_INIT="hhqytzzsds_init";
	public static final String HHQYTZZSDS_JS="hhqytzzsds_js";
	public static final String HHQYTZZSDS_TJ="hhqytzzsds_tj";
	/**
	 * 车船税纳税申报
	*/
	public static final String CCS_INIT="ccs_init";
	public static final String CCS_JS="ccs_js";
	public static final String CCS_TJ="ccs_tj";
	public static final String CCS_UPLOAD = "ccs_upload";
	public static final String CCS_DJSB = "ccs_djsb";	
	/**
	 * 耕地占用税
	 */
	public static final String DECLARE_TYPE_GDZYS_INIT = "gdzys_init";
	public static final String DECLARE_TYPE_GDZYS_JS = "gdzys_js";
	public static final String DECLARE_TYPE_GDZYS_TJ = "gdzys_tj";	
	/**
	 * 纳税人、自然人区别标志
	 */
	public static final String TAXPAYER_FLAG = "taxpayerflag";	
	public static final String OPERATION = "operation";
	/**
	 * 残保金申报
	 */
	public static final String DECLARE_TYPE_CBJSB_INIT = "cbjsb_init";
	public static final String DECLARE_TYPE_CBJSB_JS = "cbjsb_js";
	public static final String DECLARE_TYPE_CBJSB_TJ = "cbjsb_tj";
	
	/**
	 * 关联关系
	 * 
	 */ 
	public static final String DECLARE_TYPE_GLGX = "glgx";
	public static final String DECLARE_TYPE_GLGX_QUERY = DECLARE_TYPE_GLGX+"_query";
	public static final String DECLARE_TYPE_GLGX_SUBMIT= DECLARE_TYPE_GLGX+"_submit";

}
