package com.shop.base.cloudsmemory;

/**
 * 云记忆枚举类
 * @author weijingjie
 * 2014-1-3
 */
public enum CloudsMemoryEnum {
	
	//记忆的信息类型
	//标准的记忆信息"1"
	OBJ_TYPE_URL("1"),
	//是否使用云记忆
	USE_CLOUDMEM("1"),
	NO_USE_CLOUDMEM("0");
	
	
	
	public String info; 
	private CloudsMemoryEnum(String info){
		this.info=info;
	}
}
