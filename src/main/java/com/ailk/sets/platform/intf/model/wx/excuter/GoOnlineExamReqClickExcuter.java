package com.ailk.sets.platform.intf.model.wx.excuter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ailk.sets.platform.intf.cand.service.IWXCandService;
import com.ailk.sets.platform.intf.empl.service.IWXEmplService;
import com.ailk.sets.platform.intf.model.wx.IWXExcuter;

public class GoOnlineExamReqClickExcuter extends AbstractExcuter implements IWXExcuter {
	private Logger logger = LoggerFactory.getLogger(GoOnlineExamReqClickExcuter.class);

	@Override
	protected String execute(IWXEmplService wxEmplService) {
	    return null;
	}

	@Override
	protected String execute(IWXCandService wxCandService) {
		return null;
	}

}
