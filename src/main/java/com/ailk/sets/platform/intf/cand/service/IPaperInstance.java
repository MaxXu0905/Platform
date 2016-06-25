package com.ailk.sets.platform.intf.cand.service;

import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.exception.PFServiceException;

public interface IPaperInstance {
	/**
	 * 更新试卷实例视频url
	 * @param testId
	 * @param questionId
	 * @param url
	 * @return
	 * @throws PFServiceException 
	 */
	public PFResponse updatePaperInstanceQuesUrl(long testId, int questionId, String url) throws PFServiceException;
}
