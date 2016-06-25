package com.ailk.sets.platform.intf.empl.domain;

import java.io.Serializable;
/**
 * 第三方获取token的返回信息
 * @author panyl
 *
 */
public class SSOEmployerRes implements Serializable{
     /**
	 * 
	 */
	private static final long serialVersionUID = 496236568098235902L;
	private int status;
	private int code;
    private EmployerRegistInfo data;
    private String message;
    
    
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public EmployerRegistInfo getData() {
		return data;
	}
	public void setData(EmployerRegistInfo data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "SSOEmployerRes [status=" + status + ", data=" + data + ", message=" + message + "]";
	}
	
	
     
}
