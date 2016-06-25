package com.ailk.sets.grade.grade.config;

import java.util.List;

public class ExtraConf {

	private String methodName;
	private String className;
	private String include;
	private String exclude;
	private List<ParamElement> params;

	/**
	 * 初始化后设置值
	 */
	public void postInit() {
		if (params == null)
			return;

		for (ParamElement param : params) {
			param.toType();
		}
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getInclude() {
		return include;
	}

	public void setInclude(String include) {
		this.include = include;
	}

	public String getExclude() {
		return exclude;
	}

	public void setExclude(String exclude) {
		this.exclude = exclude;
	}

	public List<ParamElement> getParams() {
		return params;
	}

	public void setParams(List<ParamElement> params) {
		this.params = params;
	}

}
