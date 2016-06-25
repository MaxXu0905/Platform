package com.ailk.sets.platform.intf.common;

/**
 * 功能状态码
 * 
 * @author 毕希研
 * 
 */
public class FuncBaseResponse {
	public static final String SUCCESS = "SUCCESS";
	public static final String PARAMETERERR = "PARAMETERERR";
	public static final String EXCEPTION = "EXCEPTION";
	public static final String FAILED = "FAILED";
	public static final String TIMEOUT = "TIMEOUT";
	public static final String NONEEXIST = "NONEEXIST";
	public static final String REPEAT = "REPEAT"; //对记录的重复操作

	// 招聘人账号申请
	public static final String ACCTREGISTERED = "ACCTREGISTERED";
	public static final String ACCTHASACTIVETED = "ACCTHASACTIVETED";// 账号已经激活
	public static final String EMAILNOTSUPPORT = "EMAILNOTSUPPORT";// 不支持此类邮箱
	public static final String SENDMAILERROR = "SENDMAILERROR";
	public static final String SAMPLEMAXNUM = "SAMPLEMAXNUM";//样例邀请超过最大邀请数

	// 公司名称校验
	public static final String COMPANYNOTFOUND = "COMPANYNOTFOUND";// 没有找到公司
	public static final String COMPANYFROMDOMAIN = "COMPANYFROMDOMAIN";// 通过域名找到

	// 招聘人登录
	public static final String ACCTNOTEXIST = "ACCTNOTEXIST";
	public static final String PASSWORDERROR = "PASSWORDERROR";
	public static final String OUTDATE = "OUTDATE";
	public static final String CERTIFYCODEERR = "CERTIFYCODEERR";

	// 应聘人考试验证邀请码
	public static final String INVNONEEXIST = "4";
	public static final String INVOVERTIME = "5";
	public static final String ACCESSDENY = "ACCESSDENY";
	public static final String ACCESSABLE = "ACCESSABLE";
	public static final String FINISHEXAM = "FINISHEXAM";

	// 应聘者结果状态
	public static final int TOBECONFIRMED = 0;// 待处理
	public static final int CONFIRMED = 1;// 已通过
	public static final int PASSED = 2;// 已淘汰
	
	public static final String NOTSELFPOSITION= "NOTSELFPOSITION";
	public static final String SELFGRANTED= "SELFGRANTED";
	
	
	public static final String SSOERROR = "-1";

}
