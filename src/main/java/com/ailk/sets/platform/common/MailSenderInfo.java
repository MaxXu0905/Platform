package com.ailk.sets.platform.common;

import java.util.Properties;

import com.ailk.sets.platform.domain.CompanyEmailserver;

/**
 * @author : 
 * @create_time : 2014年8月1日 上午10:15:10
 * @desc : 邮件发送实体类
 * @update_person:
 * @update_time :
 * @update_desc :
 *
 */
public class MailSenderInfo {
	private String host;
	private String port = "25";
	private String address;
	private String toAddress;
	private String userName;
	private String password;
	private boolean validate = false;
	private String subject;
	private String content;
	private String bounceAddr;
	// 附件
	private String[] attachFileNames;

	public static MailSenderInfo getMailSenderInfo(CompanyEmailserver companyEmailserver) {
		MailSenderInfo mailInfo = new MailSenderInfo();

		mailInfo.setHost(companyEmailserver.getEmailServer());
		mailInfo.setPort(companyEmailserver.getEmailServerPort());
		mailInfo.setValidate(true);
		mailInfo.setUserName(companyEmailserver.getEmailAcct());
		mailInfo.setPassword(companyEmailserver.getEmailPwd());
		mailInfo.setAddress(companyEmailserver.getCompanyEmail());
		return mailInfo;
	}

	public Properties getProperties() {
		Properties p = new Properties();
		p.put("mail.smtp.host", this.host);
		p.put("mail.smtp.port", this.port);
		p.put("mail.smtp.auth", validate ? "true" : "false");
//		if (StringUtils.isNotEmpty(bounceAddr))
//			p.put("mail.smtp.from", bounceAddr);
		return p;
	}

	public String getBounceAddr() {
		return bounceAddr;
	}

	public void setBounceAddr(String bounceAddr) {
		this.bounceAddr = bounceAddr;
	}

	public boolean isValidate() {
		return validate;
	}

	public String getAddress() {
		return address;
	}

	public String getToAddress() {
		return toAddress;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getContent() {
		return content;
	}

	public String getSubject() {
		return subject;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String[] getAttachFileNames() {
		return attachFileNames;
	}

	public void setAttachFileNames(String[] attachFileNames) {
		this.attachFileNames = attachFileNames;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
