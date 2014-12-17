package com.blogzhou.common.web.session;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginInfo {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2483858812364094373L;

	/**
	 * 当前类自己的logger
	 */
	private static Logger logger = LoggerFactory.getLogger(LoginInfo.class);

	/************************* 接口中定义的属性 begin *********************/
	/**
	 * 纳税人内部码
	 */
	private String nsrnbm;
	
	/**
	 * 社保户统征代码
	 */
	private String sbfbm;
	
	/**
	 * 工会经费编码
	 */
	private String ghjfbm;

	/**
	 * 纳税人名称
	 */
	private String nsrmc;
	
	/**
	 * 纳税人名称(英文) (新增)
	 */
	private String englishname;
	/**
	 * 税务登记证号
	 */
	private String swdjzh;
	
	/**
	 * 税务登记类型代码（企业类型）(新增)
	 */
	private String taxpayertypecode;
	/**
	 * 注册地址
	 */
	private String zcdz;
	/**
	 * 联系电话
	 */
	private String lxdh;
	/**
	 * 法人代表
	 */
	private String frdb;
	/**
	 * 法人代表的地址 (新增)
	 */
	private String jurpaddress;
	/**
	 * 法人/业主电子信箱 (新增)
	 */
	private String jurpemail;
	/**
	 * 法人证件类型代码 (新增)
	 */
	private String jurpcardtypecode;
	/**
	 * 法人证件号码 (新增)
	 */
	private String jurpid;
	/**
	 * 财务负责人
	 */
	private String cwfzr;

	/**
	 * 财务负责人
	 */
	private String cwfzrTel;
	/**
	 * 财务负责人邮箱 (新增)
	 */
	private String financialprincipalemail;
	/**
	 * 办税员姓名
	 */
	private String glfwCzy;
	/**
	 * 办税人员联系电话
	 */
	private String taxclerktel;
	/**
	 * 办税人员邮箱 (新增)
	 */
	private String taxclerkemail;
	/**
	 * 企业财务制度代码 (新增)
	 */
	private String financesystemcode;
	/**
	 * 会计电算化核算代码 (新增)
	 */
	private String bursaraccountmodecode;
	/**
	 * 记账方式代码 (新增)
	 */
	private String chalkmode;
	/**
	 * 税收管理员名称
	 */
	private String taxofficialname;
	/**
	 * 税收管理员代码
	 */
	private String taxofficialcode;
	/**
	 * 税收管理员电话 (新增)
	 */
	private String taxofficialtel;
	/**
	 * 专管员税务机构代码(新增)
	 */
	private String taxofficialorgcode;
	/**
	 * 预算级次代码(新增)
	 */
	private String budgetlevelcode;
	/**
	 * 登记状态
	 */
	private String djzt;
	/**
	 * 社保户状态 (新增)
	 */
	private String soctaxpayerstatus;
	/**
	 * 纳税人缴费人类型代码,使用3位代码，分别表示税、社保、工会经费；1代表是，0代表否
	 * 例如："101"代表是"纳税人"和"工会经费缴费人"，不是"社保户"
	 */
	private String nsrjfrlxdm;
	/**
	 * 社保户标志 (新增)
	 */
	private String soctaxpayerflag;
	/**
	 * 组织机构代码 (新增)
	 */
	private String taxpayerorgid;
	/**
	 * 经营范围：主营 (新增)
	 */
	private String mainbusidesc;
	/**
	 * 登记注册类型
	 */
	private String regtypecode;

	private String zclxdm;
	/**
	 * 经营方式代码(新增)
	 */
	private String operationdesc;
	/**
	 * 经营行业代码(新增)
	 */
	private String operationtradecode;
	/**
	 * 企业工商登记注册类型代码(新增)
	 */
	private String certtypecode;
	/**
	 * 企业工商营业执照的登记时间
	 */
	private String practicedate;
	/**
	 * 企业国地税属性
	 */
	private String levymngmodecode;
	/**
	 * 生产经营期限起 (新增)
	 */
	private String producedealintermstart;
	/**
	 * 生产经营期限止 (新增)
	 */
	private String producedealintermend;
	/**
	 * 国标行业代码
	 */
	private String hydm;
	/**
	 * 隶属关系
	 */
	private String lsgxdm;
	/**
	 * 管理机关代码
	 */
	private String gljgdm;
	/**
	 * 申报方式
	 */
	private String sbfsdm;
	/**
	 * 核算方式代码  (新增)
	 */
	private String accountingmodecode;
	/**
	 * 是否非居民企业 (新增)
	 */
	private String personflag;
	/**
	 * 企业类别代码 (新增)
	 */
	private String enterprisesortcode;
	/**
	 * 企业所得税征收方式代码
	 */
	private String enptaxlevymethodcode;
	/**
	 * 总分支机构类型代码 (新增)
	 */
	private String zfzjgdm;
	
	/**
	 * 税款缴款方式 (新增)
	 */
	private String skjkfs;

	/**
	 * 社保费缴款方式 (新增)
	 */
	private String sbfjkfs;
	
	/**
	 * 基金缴款方式代码  (新增)
	 */
	private String jjjkfs;
	
	/**
	 * 申请的业务业型代码(开通中获取)
	 */
	private String[] sqywzldms; 
	
	/**
	 * 缴税银行帐号
	 */
	private String yhzh;
	/**
	 * 缴税银行名称
	 */
	private String yhmc;
	
	/************************* 接口中定义的属性 end *********************/
	
	
	
	
	
	
	/************************* 代码对应的名称属性 begin *********************/
	/**
	 * 管理机关名称
	 */
	private String gljgmc;
	/**
	 * 申报方式名称
	 */
	private String sbfsmc;
	/**
	 * 登记注册类型
	 */
	private String zclxmc;
	/************************* 代码对应的名称属性 end *********************/
	
	/************************* 保留属性 开始 *********************/
	/**
	 * 纳税人编码
	 */
	private String nsrbm;

	/**
	 * 用户登陆帐号
	 */
	private String yhdlzh;
	
	
	/************************* 保留属性 end *********************/
	
	/****************************
	 * TODO: 待确认后去除的属性 开始 
	 * **************************/
	/**
	 * 征收机关
	 */
	private String zsjgdm;

	/**
	 * 征收机构名称
	 */
	private String zsjgmc;

	/**
	 * 核算机关
	 */
	private String hsjgdm;

	/**
	 * E-mail
	 */
	private String email;
	
	
	/**
	 * 分项申报标志
	 */
	
	private String fxsbflag;
	/**
	 * 邮编
	 */
	private String yzbm;
	
	/**
	 * 代扣代缴标志
	 */
	private String dkdjbz;


	// 征收方式代码
	private String zsfsdm;

	// 征收标志
	private String zsbz;

	private boolean hdh;// 是否是核定纳税户，false:为鉴定户；true为核定户
	
	private boolean sdh;//双定户，定期定额户

	private boolean BankConferUser = false;// 是否是签了三方协议的用户，true为签订了协议用户

	/**
	 * 企业所得税计算方式代码
	 */
	private String enplevycalmethodcode;
	/**
	 * 是否有申报权限
	 */
	private boolean hasSbPermission = false;
	
	/**
	 * 缺省构造
	 */
	public LoginInfo() {
		
	}
	public LoginInfo(Map map) {
		nsrnbm = (String) map.get("taxpayerid");// 纳税人管理码
		nsrmc = (String) map.get("chinaname");// 纳税人名称（中文）
		englishname = (String) map.get("englishname");// 纳税人名称(英文)
		swdjzh = (String) map.get("taxregcode");// 税务登记证号
		taxpayertypecode = (String) map.get("taxpayertypecode");// 企业类型
		zcdz = (String) map.get("regaddress");// 注册地址
		lxdh = (String) map.get("regaddresstel");// 联系电话
		yzbm = (String) map.get("loginpostnum");
		frdb = (String) map.get("jurpname");// 法人/业主姓名
		jurpaddress = (String) map.get("jurpaddress");// 法人/业主详细地址
		jurpemail = (String) map.get("jurpemail");// 法人/业主电子信箱
		jurpcardtypecode = (String) map.get("jurpcardtypecode");// 法人证件类型代码
		jurpid = (String) map.get("jurpid");// 法人证件类型代码
		cwfzr = (String) map.get("financialprincipalname");// 财务负责人名称
		cwfzrTel = (String) map.get("financialprincipaltelephone");// 财务负责人电话
		financialprincipalemail = (String) map.get("financialprincipalemail");// 财务负责人邮箱
		glfwCzy = (String) map.get("taxclerkname");// 办税员姓名
		taxclerktel = (String) map.get("taxclerktel");// 办税员电话
		taxclerkemail = (String) map.get("taxclerkemail");// 办税员邮箱
		financesystemcode = (String) map.get("financesystemcode");// 企业财务制度代码
		bursaraccountmodecode = (String) map.get("bursaraccountmodecode");// 会计电算化核算代码
		chalkmode = (String) map.get("chalkmode");// 记账方式代码
		taxofficialname = (String) map.get("taxofficialname");// 税收管理员名称
		taxofficialcode = (String) map.get("taxofficialcode");// 税收管理员代码
		taxofficialtel = (String) map.get("taxofficialtel");// 税收管理员电话
		taxofficialorgcode = (String) map.get("taxofficialorgcode");// 专管员税务机构代码
		budgetlevelcode = (String) map.get("budgetlevelcode");// 预算级次代码
		djzt = (String) map.get("taxpayerstatuscode");// 纳税人状态代码
		soctaxpayerstatus = (String) map.get("soctaxpayerstatus");// 社保户状态
		soctaxpayerflag = (String) map.get("soctaxpayerflag");// 社保户标志
		taxpayerorgid = (String) map.get("taxpayerorgid");// 组织机构代码
		mainbusidesc = (String) map.get("mainbusidesc");// 经营范围：主营
		regtypecode = (String) map.get("regtypecode");// 登记注册类型代码
		zclxdm = (String) map.get("regtypecode");// 登记注册类型代码
		operationdesc = (String) map.get("operationdesc");// 经营方式代码
		operationtradecode = (String) map.get("operationtradecode");// 经营行业代码
		certtypecode = (String) map.get("certtypecode");// 企业工商登记注册类型代码
		practicedate = (String) map.get("issuedate");// 企业工商营业执照的登记时间（颁发日期）
		levymngmodecode = (String) map.get("levymngmodecode");// 企业国地税属性
		producedealintermstart = (String) map.get("producedealintermstart");// 生产经营期限起
		producedealintermend = (String) map.get("producedealintermend");// 生产经营期限止
		hydm = (String) map.get("gbtradecode");// 国标行业代码
		lsgxdm = (String) map.get("subjectrelationcode");// 隶属关系代码
		gljgdm = (String) map.get("mngcode");// 管理机构代码
		sbfsdm = (String) map.get("declaremethodcode");// 申报方式代码
		accountingmodecode = (String) map.get("accountingmodecode");// 核算方式代码
		personflag = (String) map.get("personflag");// 是否非居民企业
		enterprisesortcode = (String) map.get("enterprisesortcode");// 企业类别代码
		
		enptaxlevymethodcode = (String) map.get("levymethodcode");// 征收方式代码  enptaxlevymethodcode 江西用到的名称
		
		zsfsdm = enptaxlevymethodcode;// 征收方式代码 
		
		zfzjgdm = (String) map.get("zfzjgdm");// 总分支机构类型代码:01 跨省总机构		02 跨省分支机构		99 本地
		
		nsrbm = (String) map.get("taxpayerid");
		/**	10 查帐征收	20 核定征收	40 定期定额		 */
		
		hdh = (!zsfsdm.equals("10"));
		
		
		sdh=(zsfsdm.equals("40"));//40 定期定额	
		sqywzldms = (String[])map.get("sqywzldms");//申请开通的业务类型代码
		intPermissions(sqywzldms);
	}
	

	
	
	/**
	 * 
	 * 初始化权限
	 *  01	申报征收
		02	涉税数据采集（查询、打印）
		03	一户式查询
		04	综合查询
		05	重点税源直报
		06	服务类
	 * @param sqywzldms
	 */
	private void intPermissions(String[] sqywzldms){
		if(sqywzldms!=null){
			for(int i=0;i<sqywzldms.length;i++){
				if("01".equals(sqywzldms[i]))
					this.hasSbPermission = true;//申报征收的权限
			}
		}
	}

	/**
	 * 取 代扣代缴标志
	 * 
	 * @return
	 */
	public String getDkdjbz() {
		return dkdjbz;
	}

	/**
	 * 设置 代扣代缴标志
	 * 
	 * @param dkdjbz
	 */
	public void setDkdjbz(String dkdjbz) {
		this.dkdjbz = dkdjbz;
	}

	/**
	 * 设置 联系电话
	 * 
	 * @param String
	 *            联系电话
	 */
	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	/**
	 * 取 联系电话
	 * 
	 * @return 联系电话
	 */
	public String getLxdh() {
		return lxdh;
	}

	/**
	 * 设置 登记注册类型
	 * 
	 * @param String
	 *            登记注册类型
	 */
	public void setZclxdm(String zclxdm) {
		this.zclxdm = zclxdm;
	}

	/**
	 * 取 登记注册类型
	 * 
	 * @return 登记注册类型
	 */
	public String getZclxdm() {
		return zclxdm;
	}

	/**
	 * 设置 登记注册类型
	 * 
	 * @param String
	 *            登记注册类型
	 */
	public void setZclxmc(String zclxmc) {
		this.zclxmc = zclxmc;
	}

	/**
	 * 取 登记注册类型
	 * 
	 * @return 登记注册类型
	 */
	public String getZclxmc() {
		return zclxmc;
	}

	/**
	 * 设置 邮编
	 * 
	 * @param String
	 *            邮编
	 */
	public void setYzbm(String yzbm) {
		this.yzbm = yzbm;
	}

	/**
	 * 取 邮编
	 * 
	 * @return 邮编
	 */
	public String getYzbm() {
		return yzbm;
	}

	/**
	 * 设置 税务登记号
	 * 
	 * @param String
	 *            税务登记号
	 */
	public void setSwdjzh(String swdjzh) {
		this.swdjzh = swdjzh;
	}

	/**
	 * 取 税务登记号
	 * 
	 * @return 税务登记号
	 */
	public String getSwdjzh() {
		return swdjzh;
	}

	/**
	 * 设置 申报方式
	 * 
	 * @param String
	 *            申报方式
	 */
	public void setSbfsdm(String sbfsdm) {
		this.sbfsdm = sbfsdm;
	}

	/**
	 * 取 申报方式
	 * 
	 * @return 申报方式
	 */
	public String getSbfsdm() {
		return sbfsdm;
	}

	/**
	 * 取 申报方式
	 * 
	 * @return 申报方式
	 */
	public String getSbfsmc() {
		return sbfsmc;
	}

	/**
	 * 设置 申报方式
	 * 
	 * @param String
	 *            申报方式
	 */
	public void setSbfsmc(String sbfsmc) {
		this.sbfsmc = sbfsmc;
	}

	/**
	 * 设置 隶属关系
	 * 
	 * @param String
	 *            隶属关系
	 */
	public void setLsgxdm(String lsgxdm) {
		this.lsgxdm = lsgxdm;
	}

	/**
	 * 取 隶属关系
	 * 
	 * @return 隶属关系
	 */
	public String getLsgxdm() {
		return lsgxdm;
	}

	/**
	 * 设置 E-mail
	 * 
	 * @param String
	 *            E-mail
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 取 E-mail
	 * 
	 * @return E-mail
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置 法人代表
	 * 
	 * @param String
	 *            法人代表
	 */
	public void setFrdb(String frdb) {
		this.frdb = frdb;
	}

	/**
	 * 取 法人代表
	 * 
	 * @return 法人代表
	 */
	public String getFrdb() {
		return frdb;
	}

	/**
	 * 设置 登记状态
	 * 
	 * @param String
	 *            登记状态
	 */
	public void setDjzt(String djzt) {
		this.djzt = djzt;
	}

	/**
	 * 取 登记状态
	 * 
	 * @return 登记状态
	 */
	public String getDjzt() {
		return djzt;
	}

	/**
	 * 设置 财务负责人
	 * 
	 * @param String
	 *            财务负责人
	 */
	public void setCwfzr(String cwfzr) {
		this.cwfzr = cwfzr;
	}

	/**
	 * 取 财务负责人
	 * 
	 * @return 财务负责人
	 */
	public String getCwfzr() {
		return cwfzr;
	}

	/**
	 * 设置 核算机关
	 * 
	 * @param String
	 *            核算机关
	 */
	public void setHsjgdm(String hsjgdm) {
		this.hsjgdm = hsjgdm;
	}

	/**
	 * 取 核算机关
	 * 
	 * @return 核算机关
	 */
	public String getHsjgdm() {
		return hsjgdm;
	}

	/**
	 * 设置 征收机关
	 * 
	 * @param String
	 *            征收机关
	 */
	public void setZsjgdm(String zsjgdm) {
		this.zsjgdm = zsjgdm;
	}

	/**
	 * 取 征收机关
	 * 
	 * @return 征收机关
	 */
	public String getZsjgdm() {
		return zsjgdm;
	}

	/**
	 * 设置 管理机关代码
	 * 
	 * @param String
	 *            管理机关代码
	 */
	public void setGljgdm(String gljgdm) {
		this.gljgdm = gljgdm;
	}

	/**
	 * 取 管理机关代码
	 * 
	 * @return 管理机关代码
	 */
	public String getGljgdm() {
		return gljgdm;
	}

	/**
	 * 设置 管理机关名称
	 * 
	 * @param String
	 *            管理机关名称
	 */
	public void setGljgmc(String gljgmc) {
		this.gljgmc = gljgmc;
	}

	/**
	 * 取 管理机关名称
	 * 
	 * @return 管理机关名称
	 */
	public String getGljgmc() {
		return gljgmc;
	}

	/**
	 * 设置 纳税人内部码
	 * 
	 * @param String
	 *            纳税人内部码
	 */
	public void setNsrnbm(String nsrnbm) {
		this.nsrnbm = nsrnbm;
	}

	/**
	 * 取 纳税人内部码
	 * 
	 * @return 纳税人内部码
	 */
	public String getNsrnbm() {
		return nsrnbm;
	}

	/**
	 * 设置 纳税人名称
	 * 
	 * @param String
	 *            纳税人名称
	 */
	public void setNsrmc(String nsrmc) {
		this.nsrmc = nsrmc;
	}

	/**
	 * 取 纳税人名称
	 * 
	 * @return 纳税人名称
	 */
	public String getNsrmc() {
		return nsrmc;
	}

	/**
	 * 设置 登陆用户的用户登陆帐号
	 * 
	 * @param String
	 *            yhdlzh 登陆用户的用户登陆帐号
	 */
	public void setYhdlzh(String yhdlzh) {
		this.yhdlzh = yhdlzh;
	}

	/**
	 * 取 当前登陆用户的用户登陆帐号
	 * 
	 * @return 用户登陆帐号
	 */
	public String getYhdlzh() {
		return yhdlzh;
	}

	/**
	 * 设置 注册地址
	 * 
	 * @param String
	 *            注册地址
	 */
	public void setZcdz(String zcdz) {
		this.zcdz = zcdz;
	}

	/**
	 * 取 注册地址
	 * 
	 * @return 注册地址
	 */
	public String getZcdz() {
		return zcdz;
	}

	/**
	 * 设置 行业代码
	 * 
	 * @param String
	 *            行业代码
	 */
	public void setHydm(String hydm) {
		this.hydm = hydm;
	}

	/**
	 * 取 行业代码
	 * 
	 * @return 行业代码
	 */
	public String getHydm() {
		return hydm;
	}

	public String getNsrbm() {
		return nsrbm;
	}

	public void setNsrbm(String nsrbm) {
		this.nsrbm = nsrbm;
	}

	public String getZsfsdm() {
		return zsfsdm;
	}

	public void setZsfsdm(String zsfsdm) {
		this.zsfsdm = zsfsdm;
	}

	public boolean isHdh() {
		return hdh;
	}

	public void setHdh(boolean hdh) {
		this.hdh = hdh;
	}

	public String getZsjgmc() {
		return zsjgmc;
	}

	public void setZsjgmc(String zsjgmc) {
		this.zsjgmc = zsjgmc;
	}

	public String getZsbz() {
		return zsbz;
	}

	public void setZsbz(String zsbz) {
		this.zsbz = zsbz;
	}

	public boolean isBankConferUser() {
		return BankConferUser;
	}

	public void setBankConferUser(boolean bankConferUser) {
		BankConferUser = bankConferUser;
	}

	public String getTaxclerktel() {
		return taxclerktel;
	}

	public void setTaxclerktel(String taxclerktel) {
		this.taxclerktel = taxclerktel;
	}

	public String getCwfzrTel() {
		return cwfzrTel;
	}

	public void setCwfzrTel(String cwfzrTel) {
		this.cwfzrTel = cwfzrTel;
	}

	
	
	public String getPracticedate() {
		return practicedate;
	}

	public void setPracticedate(String practicedate) {
		this.practicedate = practicedate;
	}

	public String getTaxofficialcode() {
		return taxofficialcode;
	}

	public void setTaxofficialcode(String taxofficialcode) {
		this.taxofficialcode = taxofficialcode;
	}

	

	public String getLevyMngModeCode() {
		return levymngmodecode;
	}

	public void setLevyMngModeCode(String levymngmodecode) {
		this.levymngmodecode = levymngmodecode;
	}
	
	public String getTaxofficialname() {
		return taxofficialname;
	}

	public void setTaxofficialname(String taxofficialname) {
		this.taxofficialname = taxofficialname;
	}



	public String getEnplevycalmethodcode() {
		return enplevycalmethodcode;
	}

	public void setEnplevycalmethodcode(String enplevycalmethodcode) {
		this.enplevycalmethodcode = enplevycalmethodcode;
	}

	public String getEnptaxlevymethodcode() {
		return enptaxlevymethodcode;
	}

	public void setEnptaxlevymethodcode(String enptaxlevymethodcode) {
		this.enptaxlevymethodcode = enptaxlevymethodcode;
	}

	public String getGlfwCzy() {

		return glfwCzy;
	}

	public void setGlfwCzy(String glfwCzy) {
		this.glfwCzy = glfwCzy;
	}

	public String getJjjkfs() {
		return jjjkfs;
	}
	public void setJjjkfs(String jjjkfs) {
		this.jjjkfs = jjjkfs;
	}
	public String getSbfjkfs() {
		return sbfjkfs;
	}
	public void setSbfjkfs(String sbfjkfs) {
		this.sbfjkfs = sbfjkfs;
	}
	public String getSkjkfs() {
		return skjkfs;
	}
	public void setSkjkfs(String skjkfs) {
		this.skjkfs = skjkfs;
	}
	public static Logger getLogger() {
		return logger;
	}
	public static void setLogger(Logger logger) {
		LoginInfo.logger = logger;
	}

	public String getAccountingmodecode() {
		return accountingmodecode;
	}
	public void setAccountingmodecode(String accountingmodecode) {
		this.accountingmodecode = accountingmodecode;
	}
	public String getBudgetlevelcode() {
		return budgetlevelcode;
	}
	public void setBudgetlevelcode(String budgetlevelcode) {
		this.budgetlevelcode = budgetlevelcode;
	}
	public String getBursaraccountmodecode() {
		return bursaraccountmodecode;
	}
	public void setBursaraccountmodecode(String bursaraccountmodecode) {
		this.bursaraccountmodecode = bursaraccountmodecode;
	}
	public String getCerttypecode() {
		return certtypecode;
	}
	public void setCerttypecode(String certtypecode) {
		this.certtypecode = certtypecode;
	}
	public String getChalkmode() {
		return chalkmode;
	}
	public void setChalkmode(String chalkmode) {
		this.chalkmode = chalkmode;
	}
	public String getEnglishname() {
		return englishname;
	}
	public void setEnglishname(String englishname) {
		this.englishname = englishname;
	}
	public String getEnterprisesortcode() {
		return enterprisesortcode;
	}
	public void setEnterprisesortcode(String enterprisesortcode) {
		this.enterprisesortcode = enterprisesortcode;
	}
	public String getFinancesystemcode() {
		return financesystemcode;
	}
	public void setFinancesystemcode(String financesystemcode) {
		this.financesystemcode = financesystemcode;
	}
	public String getFinancialprincipalemail() {
		return financialprincipalemail;
	}
	public void setFinancialprincipalemail(String financialprincipalemail) {
		this.financialprincipalemail = financialprincipalemail;
	}
	public String getJurpaddress() {
		return jurpaddress;
	}
	public void setJurpaddress(String jurpaddress) {
		this.jurpaddress = jurpaddress;
	}
	public String getJurpcardtypecode() {
		return jurpcardtypecode;
	}
	public void setJurpcardtypecode(String jurpcardtypecode) {
		this.jurpcardtypecode = jurpcardtypecode;
	}
	public String getJurpemail() {
		return jurpemail;
	}
	public void setJurpemail(String jurpemail) {
		this.jurpemail = jurpemail;
	}
	public String getLevymngmodecode() {
		return levymngmodecode;
	}
	public void setLevymngmodecode(String levymngmodecode) {
		this.levymngmodecode = levymngmodecode;
	}
	public String getMainbusidesc() {
		return mainbusidesc;
	}
	public void setMainbusidesc(String mainbusidesc) {
		this.mainbusidesc = mainbusidesc;
	}
	public String getOperationdesc() {
		return operationdesc;
	}
	public void setOperationdesc(String operationdesc) {
		this.operationdesc = operationdesc;
	}
	public String getOperationtradecode() {
		return operationtradecode;
	}
	public void setOperationtradecode(String operationtradecode) {
		this.operationtradecode = operationtradecode;
	}
	public String getPersonflag() {
		return personflag;
	}
	public void setPersonflag(String personflag) {
		this.personflag = personflag;
	}
	public String getProducedealintermend() {
		return producedealintermend;
	}
	public void setProducedealintermend(String producedealintermend) {
		this.producedealintermend = producedealintermend;
	}
	public String getProducedealintermstart() {
		return producedealintermstart;
	}
	public void setProducedealintermstart(String producedealintermstart) {
		this.producedealintermstart = producedealintermstart;
	}
	public String getSoctaxpayerflag() {
		return soctaxpayerflag;
	}
	public void setSoctaxpayerflag(String soctaxpayerflag) {
		this.soctaxpayerflag = soctaxpayerflag;
	}
	public String getSoctaxpayerstatus() {
		return soctaxpayerstatus;
	}
	public void setSoctaxpayerstatus(String soctaxpayerstatus) {
		this.soctaxpayerstatus = soctaxpayerstatus;
	}
	public String getTaxclerkemail() {
		return taxclerkemail;
	}
	public void setTaxclerkemail(String taxclerkemail) {
		this.taxclerkemail = taxclerkemail;
	}
	public String getTaxofficialorgcode() {
		return taxofficialorgcode;
	}
	public void setTaxofficialorgcode(String taxofficialorgcode) {
		this.taxofficialorgcode = taxofficialorgcode;
	}
	public String getTaxofficialtel() {
		return taxofficialtel;
	}
	public void setTaxofficialtel(String taxofficialtel) {
		this.taxofficialtel = taxofficialtel;
	}
	public String getTaxpayerorgid() {
		return taxpayerorgid;
	}
	public void setTaxpayerorgid(String taxpayerorgid) {
		this.taxpayerorgid = taxpayerorgid;
	}
	public String getTaxpayertypecode() {
		return taxpayertypecode;
	}
	public void setTaxpayertypecode(String taxpayertypecode) {
		this.taxpayertypecode = taxpayertypecode;
	}
	public String getZfzjgdm() {
		return zfzjgdm;
	}
	public void setZfzjgdm(String zfzjgdm) {
		this.zfzjgdm = zfzjgdm;
	}
	public String[] getSqywzldms() {
		return sqywzldms;
	}
	public void setSqywzldms(String[] sqywzldms) {
		this.sqywzldms = sqywzldms;
	}
	
	public String getJurpid() {
		return jurpid;
	}
	public void setJurpid(String jurpid) {
		this.jurpid = jurpid;
	}
	public boolean isHasSbPermission() {
		return hasSbPermission;
	}
	public void setHasSbPermission(boolean hasSbPermission) {
		this.hasSbPermission = hasSbPermission;
	}
	public String getRegtypecode() {
		return regtypecode;
	}
	public void setRegtypecode(String regtypecode) {
		this.regtypecode = regtypecode;
	}
	public boolean isSdh() {
		return sdh;
	}
	public void setSdh(boolean sdh) {
		this.sdh = sdh;
	}
	public String getSbfbm() {
		return sbfbm;
	}
	public void setSbfbm(String sbfbm) {
		this.sbfbm = sbfbm;
	}
	public String getGhjfbm() {
		return ghjfbm;
	}
	public void setGhjfbm(String ghjfbm) {
		this.ghjfbm = ghjfbm;
	}
	public String getNsrjfrlxdm() {
		return nsrjfrlxdm;
	}
	public void setNsrjfrlxdm(String nsrjfrlxdm) {
		this.nsrjfrlxdm = nsrjfrlxdm;
	}
	public String getYhmc() {
		return yhmc;
	}
	public void setYhmc(String yhmc) {
		this.yhmc = yhmc;
	}
	public String getYhzh() {
		return yhzh;
	}
	public void setYhzh(String yhzh) {
		this.yhzh = yhzh;
	}
	public String getFxsbflag() {
		return fxsbflag;
	}
	public void setFxsbflag(String fxsbflag) {
		this.fxsbflag = fxsbflag;
	}
	
	
}
