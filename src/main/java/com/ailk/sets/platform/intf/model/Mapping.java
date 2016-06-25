package com.ailk.sets.platform.intf.model;

import java.io.Serializable;

/**
 * 常用的映射模型对象
 * @author 毕希研
 *
 */
public class Mapping implements Serializable {

	private static final long serialVersionUID = -659593307232476247L;

	private String key;
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
