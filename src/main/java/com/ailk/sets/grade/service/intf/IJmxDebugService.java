package com.ailk.sets.grade.service.intf;

public interface IJmxDebugService {

	/**
	 * 保留临时文件
	 */
	public void enableKeepTempFiles();

	/**
	 * 删除临时文件
	 */
	public void disableKeepTempFiles();

}
