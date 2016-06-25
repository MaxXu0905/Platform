package com.ailk.sets.platform.intf.model.wx.excuter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ailk.sets.platform.intf.cand.service.IWXCandService;
import com.ailk.sets.platform.intf.empl.service.IWXEmplService;
import com.ailk.sets.platform.intf.model.wx.IWXExcuter;
import com.ailk.sets.platform.intf.model.wx.IWXService;
import com.ailk.sets.platform.intf.model.wx.util.DocumentWraper;

public abstract class AbstractExcuter implements IWXExcuter {
	private Logger logger = LoggerFactory.getLogger(AbstractExcuter.class);
	protected DocumentWraper docWraper;

	@Override
	public String execute(IWXService wxService, DocumentWraper docWraper) {
		logger.debug("prepare call service " + wxService);
		this.docWraper = docWraper;
		if (wxService instanceof IWXCandService)
		{
		    return execute((IWXCandService) wxService);
		}
		else if (wxService instanceof IWXEmplService)
		{
		    return execute((IWXEmplService) wxService);
		}
		else
		{
		    throw new UnsupportedOperationException("not support excuter for service,service is " + wxService);
		}
	}

	protected abstract String execute(IWXEmplService wxEmplService);

	protected abstract String execute(IWXCandService wxCandService);
}
