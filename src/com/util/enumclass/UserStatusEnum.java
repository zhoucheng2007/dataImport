
package com.util.enumclass;

public enum UserStatusEnum {
	/** 正常(0) */ 
	normal(0, "正常"),
	/** 已删除(1) */
	delete(1, "已删除"),
	/** 待审核(2) */
	audit(2, "待审核"), 
	/** 锁定(3) */
	lock(3, "已锁定");

	/**
	 * 值 Integer型
	 */
	private final Integer value;
	/**
	 * 描述 String型
	 */
	private final String description;

	UserStatusEnum(Integer value, String description) {
		this.value = value;
		this.description = description;
	}

	/**
	 * 获取值
	 * @return value
	 */
	public Integer getValue() {
		return value;
	}

	/**
     * 获取描述信息
     * @return description
     */
	public String getDescription() {
		return description;
	}

	public static UserStatusEnum getStatusState(Integer value) {
		if (null == value)
			return null;
		for (UserStatusEnum _enum : UserStatusEnum.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
	
	public static UserStatusEnum getStatusState(String description) {
		if (null == description)
			return null;
		for (UserStatusEnum _enum : UserStatusEnum.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}

}