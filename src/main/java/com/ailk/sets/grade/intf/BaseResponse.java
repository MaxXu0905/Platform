package com.ailk.sets.grade.intf;

import java.io.Serializable;

public class BaseResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int SUCCESS = 0; // 成功
	public static final int EABORT = 1; // 异常退出
	public static final int EBADDESC = 2; // 描述错误
	public static final int EBLOCK = 3; // 阻塞
	public static final int EINVAL = 4; // 参数无效
	public static final int ELIMIT = 5; // 超过限制
	public static final int ENOENT = 6; // 没有入口
	public static final int EOS = 7; // 操作系统异常
	public static final int EPERM = 8; // 权限错误
	public static final int EPROTO = 9; // 协议错误
	public static final int ESVCERR = 10; // 服务错误
	public static final int ESVCFAIL = 11; // 服务失败
	public static final int ESYSTEM = 12; // 系统错误
	public static final int ETIME = 13; // 超时
	public static final int ERELEASE = 14; // 版本错误
	public static final int EMATCH = 15; // 匹配错误
	public static final int EDIAGNOSTIC = 16; // 诊断错误
	public static final int EDUPENT = 17; // 重复入口
	public static final int EEXPIRE = 18; // 超时无效
	public static final int EDEVID = 19; // 设备错误
	public static final int EVERIFY = 20; // 获取验证码失败

	private int errorCode; // 错误代码
	private String errorDesc; // 错误描述

	public BaseResponse() {
		errorCode = 0;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
