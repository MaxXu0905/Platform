package com.ailk.sets.platform.intf.model.wx.excuter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ailk.sets.platform.intf.cand.service.IWXCandService;
import com.ailk.sets.platform.intf.empl.service.IWXEmplService;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.wx.Constant;
import com.ailk.sets.platform.intf.model.wx.IWXExcuter;

public class UnSubscribeExcuter extends AbstractExcuter implements IWXExcuter {
	private Logger logger = LoggerFactory.getLogger(UnSubscribeExcuter.class);

	@Override
	protected String execute(IWXEmplService wxEmplService) {
		try {
			return wxEmplService.unBindingEmployer(docWraper.getTextUnderRoot(Constant.FROMUSERNAME_TAG)).toString();
		} catch (PFServiceException e) {
			logger.error("call UnSubscribeExcuter execute IWXEmplService error", e);
		}
		return null;
	}

	@Override
	protected String execute(IWXCandService wxCandService) {
		try {
			return wxCandService.unBindingCandidate(docWraper.getTextUnderRoot(Constant.FROMUSERNAME_TAG)).toString();
		} catch (PFServiceException e) {
			logger.error("call UnSubscribeExcuter execute IWXCandService error", e);
		}
		return null;
	}

}
