/**
 * 
 */
package com.shop.base.cache;

/**
 * @title:缓存常量，定义字典唯一标识符
 * @author sunfs
 *
 */
public interface CacheConstants {
	
	//------------------计量单位字典接口--------------------------------//	
	public static  final   String       UMDICT_CACHE_KEY="umdict.cache.key"; //计量单位字典
	//base_dict数据字典，单个字典
	public static  final   String       BASE_DICT_CACHE_KEY="mofreflist.dict.cache.key";
	public static  final   String       USERTEST_CACHE_KEY="usercachetest.cache.key";
	
	//银行字典标识符
	public static  final   String       BANK_CACHE_KEY="bankdict.cache.key";
	
	//原因码字典
	public static  final   String       REASON_CACHE_KEY="reason.dict.cache.key";	
	
	//商品字典
	public static  final   String       ITEM_CACHE_KEY="item.dict.cache.key";
	
	//商品分类字典
	public static  final   String       ITEM_TYPE_CACHE_KEY="itemtype.dict.cache.key";
	
	//品牌系列字典
	public static  final   String       BRAND_CACHE_KEY="brand.dict.cache.key";
	
	
	//品牌拥有者字典
	public static  final   String       MANUFACTURER_CACHE_KEY="manufacturer.dict.cache.key";
	
	//价格类型字典
	public static  final   String       PRI_TYPE_CACHE_KEY="price.type.dict.cache.key";
	
	//订货域字典
	public static  final   String       ORDER_DOMAIN_CACHE_KEY="order.domain.dict.cache.key";
	
	//订货周期字典
	public static  final   String       ORDER_PERIODS_CACHE_KEY="order.periods.dict.cache.key";
	
	//订货周期类型字典
	public static  final   String       ORDER_PERIODS_TYPE_CACHE_KEY="order.periods.type.dict.cache.key";
	
	//crm客户分类字典
	public static  final   String       CUST_TYPE_CACHE_KEY="cust.type.dict.cache.key";
	
	//客户分组类别字典
	public static  final   String       GROUP_TYPE_CACHE_KEY="group.type.dict.cache.key";
	
	//商品价位分类字典
	public static  final   String       BRAND_TYPE_KIND_CACHE_KEY="brand.type.kind.dict.cache.key";
	
	//策略规则库指标表字典
	public static  final   String       MARKET_INDEX_CACHE_KEY="market.index.dict.cache.key";
	
	//分配方案组表字典
	public static  final   String       PUT_MODE_GROUP_CACHE_KEY="put.mode.group.dict.cache.key";
	
	//分配方案表字典
	public static  final   String       PUB_MODE_CACHE_KEY="put.mode.dict.cache.key";
	
	//促销品销售限量字典
	public static  final   String       SALA_LIMIT_COM_CACHE_KEY="sala.limit.com.dict.cache.key";
	
	//指标项字典
	public static  final   String       INDEX_ITEM_CACHE_KEY="index.item.dict.cache.key";
	
	
	//公式字典
	public static  final   String       EXPRE_CACHE_KEY="expre.dict.cache.key";
	
	
	//配送域字典
	public static  final   String       DIST_RIGION_CACHE_KEY="dist.rigion.dict.cache.key";
	
	//配送路线字典
	public static  final   String       DIST_RUT_CACHE_KEY="dist.rut.dict.cache.key";
	//零售户字典
	public static  final   String       CUST_CACHE_KEY="cust.dict.cache.key";
	
	//运维平台项目
	public static final String  ITSM_CMDB_RELATION_TYPE_KEY = "itsm.cmdb.reation.type.key";

	public static final String  ITSM_CTI_KEY = "itsm.cti.key";
	
	public static final String  ITSM_PROJECT_KEY = "itsm.project.key";
	
	public static final String  ITSM_VENDOR_KEY = "itsm.vendor.key";
	
	public static final String  ITSM_REPORTER_KEY = "itsm.reporter.key";
	
	

	//Mof_refList字典项键值
	public static final String ENUM_STATUS_RM_CUST_LICENSE_RM = "ENUM_STATUS_RM_CUST_LICENSE_RM"; // 零售户状态
	public static final String ENUM_TYPE_SD_CO_CO_SALE_SD = "ENUM_TYPE_SD_CO_CO_SALE_SD"; // 订单类型
	public static final String ENUM_STATUS_SD_CO_CO_SALE_SD = "ENUM_STATUS_SD_CO_CO_SALE_SD"; // 订单状态
	public static final String ENUM_RM_CUST_ORDER_WAY = "ENUM_RM_CUST_ORDER_WAY"; // 订货方式
	public static final String ENUM_PMT_STATUS_SD_CO_CO_SALE_SD = "ENUM_PMT_STATUS_SD_CO_CO_SALE_SD"; // 收款状态
	public static final String ENUM_SD_ARTRAN_PAY_TYPE = "ENUM_SD_ARTRAN_PAY_TYPE"; // 收款类型
	public static final String ENUM_KIND_SD_ITEM_TOBACCO_ITEM_BASE_SD = "ENUM_KIND_SD_ITEM_TOBACCO_ITEM_BASE_SD"; // 价类
	public static final String ENUM_KIND_SIZE = "8a8d819707c72c9f0107c72e860a0001"; // 规格
	public static final String ENUM_RM_LICENSE_APPLY_BASE_TYPE = "ENUM_RM_LICENSE_APPLY_BASE_TYPE"; // 经营业态
	public static final String ENUM_RM_CUST_SALE_SCOPE = "ENUM_RM_CUST_SALE_SCOPE"; // 经营规模
	public static final String ENUM_RM_CUST_OTHER_WORK_PORT = "ENUM_RM_CUST_OTHER_WORK_PORT"; // 市场类型
	public static final String ENUM_RM_CUST_ORDER_PERIOD = "ENUM_RM_CUST_ORDER_PERIOD"; // 订货周期
	public static final String ENUM_IS_IMPORTED_SD_ITEM_TOBACCO_ITEMPRI = "ENUM_IS_IMPORTED_SD_ITEM_TOBACCO_ITEMPRI"; // 产地
	public static final String ENUM_PAY_TYPE_RM_CUST_SALE_CRM = "ENUM_PAY_TYPE_RM_CUST_SALE_CRM"; // 结算方式
	public static final String ENUM_PAY_TYPE_RM_CUST_LICENSE_RM = "ENUM_PAY_TYPE_RM_CUST_LICENSE_RM"; // 结算方式
	public static final String ENUM_INNER_TYPE_RM_CUST_LICENSE_RM = "ENUM_INNER_TYPE_RM_CUST_LICENSE_RM"; // 统计类型
	public static final String ENUM_RM_CUST_BUSI_STRU = "ENUM_RM_CUST_BUSI_STRU"; // 经营结构
	public static final String ENUM_RM_CUST_OTHER_BUSI_TIME_TYPE = "ENUM_RM_CUST_OTHER_BUSI_TIME_TYPE"; // 营业时间
	public static final String ENUM_RM_CUST_OTHER_INFO_TERMINAL = "ENUM_RM_CUST_OTHER_INFO_TERMINAL"; // 信息终端
	public static final String ENUM_RM_CUST_OTHER_SHOP_FRONT_MARK = "ENUM_RM_CUST_OTHER_SHOP_FRONT_MARK"; // 门头标识

	public static final String RM_CUST_OTHER_AREA_TYPE = "RM_CUST_OTHER_AREA_TYPE"; // 商圈类型
	public static final String ENUM_ITEM_KIND_SD_ITEM_ITEM_BASE_SD = "ENUM_ITEM_KIND_SD_ITEM_ITEM_BASE_SD"; // 商品类别  
	public static final String ENUM_INNER_TYPE_KIND = "ENUM_INNER_TYPE_KIND"; // INNER_TYPE 分类   ：系统内  系统外
}