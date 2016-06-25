package com.ailk.sets.platform.intf.domain;

import java.io.Serializable;

public class MsgInfo implements Serializable{

	private static final long serialVersionUID = 1811752916170546932L;
	private String phone;
	private String context;
	private String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	@Override
	public String toString() {
		return "MsgInfo [phone=" + phone + ", context=" + context + ",id="+id +"]";
	}
	

}
