package com.ailk.sets.platform.intf.cand.domain;

import java.io.Serializable;
import java.util.List;

import com.ailk.sets.platform.intf.model.Mapping;

public class InfoNeeded implements Serializable {

	private static final long serialVersionUID = 2638497460745424689L;
	private String infoId;
	private String infoName;
	private Integer valueLength;
	private String verifyExp;
	private String valueType;
	private List<Mapping> range;
	private String value;
	private String realValue;

	private Object  extendInfo;//只有地区时才有此值

	public String getRealValue() {
		return realValue;
	}

	public void setRealValue(String realValue) {
		this.realValue = realValue;
	}
	public Object getExtendInfo() {
		return extendInfo;
	}

	public void setExtendInfo(Object extendInfo) {
		this.extendInfo = extendInfo;
	}

	public String getInfoName() {
		return infoName;
	}

	public void setInfoName(String infoName) {
		this.infoName = infoName;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public Integer getValueLength() {
		return valueLength;
	}

	public void setValueLength(Integer valueLength) {
		this.valueLength = valueLength;
	}

	public String getVerifyExp() {
		return verifyExp;
	}

	public void setVerifyExp(String verifyExp) {
		this.verifyExp = verifyExp;
	}

	public List<Mapping> getRange() {
		return range;
	}

	public void setRange(List<Mapping> range) {
		this.range = range;
	}
}
