package com.ailk.sets.platform.intf.model.wx;

import com.ailk.sets.platform.intf.model.wx.util.DocumentWraper;


public interface IWXExcuter {
	public String execute(IWXService wxService,DocumentWraper docWraper);
}
