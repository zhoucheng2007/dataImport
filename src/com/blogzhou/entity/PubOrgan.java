package com.blogzhou.entity;

// Generated 2014-9-24 15:31:13 by Hibernate Tools 4.0.0

import java.util.Date;

/**
 * PubOrgan generated by hbm2java
 */
public class PubOrgan implements java.io.Serializable {

	private String organId;
	private String pubOrganType;
	private String organCode;
	private String organName;
	private String shortName;
	private char inUse;
	private Date ctime;

	public PubOrgan() {
	}	

	public String getPubOrganType() {
		return pubOrganType;
	}



	public void setPubOrganType(String pubOrganType) {
		this.pubOrganType = pubOrganType;
	}



	public String getOrganId() {
		return this.organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getOrganCode() {
		return this.organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	public String getOrganName() {
		return this.organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public char getInUse() {
		return this.inUse;
	}

	public void setInUse(char inUse) {
		this.inUse = inUse;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

}