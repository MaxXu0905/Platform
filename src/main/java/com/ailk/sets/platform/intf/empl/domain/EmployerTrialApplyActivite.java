package com.ailk.sets.platform.intf.empl.domain;



public class EmployerTrialApplyActivite implements java.io.Serializable {

	private static final long serialVersionUID = -750264039889686980L;
	private String userName;
	private String userEmail;
	private String url;
	private String path= "activationcontent";
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getPath() {
		return path;
	}


}