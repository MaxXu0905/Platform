package com.ailk.sets.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.dao.interfaces.IPaperDao;
import com.ailk.sets.platform.intf.cand.service.IPaperInstance;
import com.ailk.sets.platform.intf.common.FuncBaseResponse;
import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.exception.PFServiceException;

@Transactional(rollbackFor = Exception.class)
public class PaperInstanceImpl implements IPaperInstance {

	@Autowired
	private IPaperDao paperDao;

	@Override
	public PFResponse updatePaperInstanceQuesUrl(long testId, int questionId, String url) throws PFServiceException {
		PFResponse pfResponse = new PFResponse();
		try {
			paperDao.updatePaperInstanceQuesUrl(testId, questionId, url);
			pfResponse.setCode(FuncBaseResponse.SUCCESS);
			return pfResponse;
		} catch (Exception e) {
			throw new PFServiceException(e);
		}
	}
}
