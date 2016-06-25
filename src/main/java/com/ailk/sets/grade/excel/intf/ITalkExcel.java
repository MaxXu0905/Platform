package com.ailk.sets.grade.excel.intf;

import java.io.InputStream;

import com.ailk.sets.grade.intf.LoadTalksResponse;

public interface ITalkExcel {

	/**
	 * 从excel获取宣讲会
	 * 
	 * @param in
	 *            输入流
	 * @param isXSSF
	 *            是否为xlsx格式
	 * @return
	 * @throws Exception
	 */
	public LoadTalksResponse loadTalks(InputStream in, boolean isXSSF)
			throws Exception;

}
