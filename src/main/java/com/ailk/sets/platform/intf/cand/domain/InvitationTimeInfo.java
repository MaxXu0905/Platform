package com.ailk.sets.platform.intf.cand.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import com.ailk.sets.platform.intf.common.PFResponse;

public class InvitationTimeInfo extends PFResponse  implements Serializable{
	private static final long serialVersionUID = -6418422474722028649L;
private Timestamp effDate; //生效时间
private Timestamp expDate; //失效时间
private Long secToEffDate;//距离生效时间的时间间隔  秒
private Long secToExpDate;//距离失效时间的时间间隔  秒


public Timestamp getEffDate() {
	return effDate;
}
public void setEffDate(Timestamp effDate) {
	this.effDate = effDate;
}
public Timestamp getExpDate() {
	return expDate;
}
public void setExpDate(Timestamp expDate) {
	this.expDate = expDate;
}
public Long getSecToEffDate() {
	return secToEffDate;
}
public void setSecToEffDate(Long secToEffDate) {
	this.secToEffDate = secToEffDate;
}
public Long getSecToExpDate() {
	return secToExpDate;
}
public void setSecToExpDate(Long secToExpDate) {
	this.secToExpDate = secToExpDate;
}



}
