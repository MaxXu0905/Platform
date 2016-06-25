package com.ailk.sets.platform.intf.model.wx.excuter;

import com.ailk.sets.platform.intf.cand.service.IWXCandService;
import com.ailk.sets.platform.intf.empl.service.IWXEmplService;
import com.ailk.sets.platform.intf.model.wx.Constant;
import com.ailk.sets.platform.intf.model.wx.IWXExcuter;

public class GoLoginClickExcuter extends AbstractExcuter implements IWXExcuter {
	
//	private Logger logger = LoggerFactory.getLogger(GoLoginClickExcuter.class);

	@Override
	protected String execute(IWXEmplService wxEmplService) {
		String openId = docWraper.getTextUnderRoot(Constant.FROMUSERNAME_TAG);
//		String employer = wxEmplService.isBind(openId);
		
//		if(employer=="")
//		{
//			    //用户绑定过回复信息
//				String url = wxEmplService.getServiceBaseUrl() + "/sets/" + "wx/wxLogin/" + docWraper.getTextUnderRoot(Constant.FROMUSERNAME_TAG);
//				String content = "<a href='" + url + "#wechat_webview_type=1'>点击进入百一测评</a>";
//				logger.debug("content go login is "+content);
//				wxEmplService.defaultMessageSend(openId, content);
//				return null;
//		}
//		else{
		String url = wxEmplService.getServiceBaseUrl() + "/sets/" + "wx/wxLogin/" + docWraper.getTextUnderRoot(Constant.FROMUSERNAME_TAG);
		String content = "<a href='" + url + "#wechat_webview_type=1'>猛戳这里登录百一系统~</a>";
		wxEmplService.messageSend(openId, content);
		return null;
//		}
	}

	@Override
	protected String execute(IWXCandService wxCandService) {
		return null;
	}
	

}
