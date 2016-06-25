package com.ailk.sets.platform.intf.model.wx.excuter;

import com.ailk.sets.platform.intf.model.wx.IWXExcuter;
import com.ailk.sets.platform.intf.model.wx.IWXService;
import com.ailk.sets.platform.intf.model.wx.util.DocumentWraper;

public class DefaultExcuter implements IWXExcuter {

	@Override
	public String execute(IWXService wxService, DocumentWraper docWraper) {
		return "call default Excuter";
	}

}
