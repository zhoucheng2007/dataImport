package com.blogzhou.common.code;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.blogzhou.common.code.exception.CodeException;
import com.blogzhou.common.code.exception.CodeExceptionDefine;


public class CodeConfigItem {

	/**
	 * 当前类自己的logger
	 */
	private static Logger logger = Logger.getLogger(CodeConfigItem.class);

	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 描述
	 */
	private String description;
	
	/**
	 * 表名称
	 */
	private String tableName;
	
	/**
	 * 映射别名，用逗号分割多个别名
	 */
	private String alias;
	
	/**
	 * 字段：编码
	 */
	private String fieldCode;
	
	/**
	 * 字段：值
	 */
	private String fieldValue;
	
	/**
	 * 过滤条件
	 */
	private String filter;
	
	/**
	 * 解析配置文件，获取配置值对象
	 * @param codeNode 配置文件节点，CODE
	 * @return 解析配置文件得到的配置值对象，null表示解析失败
	 */
	public static CodeConfigItem parseConfig(Element codeNode) throws CodeException {
		if (codeNode == null) {
			CodeException ex = new CodeException(CodeExceptionDefine.FSTAX_COMMON_CODE_PARAMETER_ILLEGAL,
					"Specify config item CODE is null", null);
			ex.log(logger);
			throw ex;
		}
		
		/* 读取配置项各个属性 */
		CodeConfigItem item = new CodeConfigItem();
		item.setName(codeNode.attributeValue("name"));
		item.setDescription(codeNode.attributeValue("description"));
		item.setTableName(codeNode.attributeValue("table-name"));
		item.setAlias(codeNode.attributeValue("alias"));
		item.setFieldCode(codeNode.attributeValue("field-code"));
		item.setFieldValue(codeNode.attributeValue("field-value"));
		item.setFilter(codeNode.attributeValue("filter"));

		/* 检查必填项 */
		String msg = "";
		if (item.getName() == null || item.getName().length() == 0) {
			msg += " name";
		}
		if (item.getTableName() == null || item.getTableName().length() == 0) {
			msg += " table-name";
		}
		if (item.getFieldCode() == null || item.getFieldCode().length() == 0) {
			msg += " field-code";
		}
		if (item.getFieldValue() == null || item.getFieldValue().length() == 0) {
			msg += " field-value";
		}
		if (msg.length() > 0) {
			CodeException ex = new CodeException(CodeExceptionDefine.FSTAX_COMMON_CODE_CONF_ILLEGAL,
					"Config item [" + codeNode.asXML() + "] cann't be empty:" + msg, null);
			ex.log(logger);
			throw ex;
		}
		
		return item;
	}

	/**
	 * @return Returns the alias.
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias The alias to set.
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Returns the fieldCode.
	 */
	public String getFieldCode() {
		return fieldCode;
	}

	/**
	 * @param fieldCode The fieldCode to set.
	 */
	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	/**
	 * @return Returns the filter.
	 */
	public String getFilter() {
		return filter;
	}

	/**
	 * @param filter The filter to set.
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * @return Returns the fieldValue.
	 */
	public String getFieldValue() {
		return fieldValue;
	}

	/**
	 * @param fieldValue The fieldValue to set.
	 */
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the tableName.
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName The tableName to set.
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	
}
