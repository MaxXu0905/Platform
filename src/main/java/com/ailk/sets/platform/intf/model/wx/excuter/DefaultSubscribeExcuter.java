package com.ailk.sets.platform.intf.model.wx.excuter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ailk.sets.platform.intf.cand.service.IWXCandService;
import com.ailk.sets.platform.intf.empl.service.IWXEmplService;
import com.ailk.sets.platform.intf.model.wx.Constant;
import com.ailk.sets.platform.intf.model.wx.IWXExcuter;

/**
 * 手动关注微信公众号处理器
 * @author 毕希研
 * 
 */
public class DefaultSubscribeExcuter extends AbstractExcuter implements IWXExcuter {

    private Logger logger = LoggerFactory.getLogger(DefaultSubscribeExcuter.class);
    
	@Override
	protected String execute(IWXEmplService wxEmplService) {
	    logger.debug("send welcome to employer ...");
	    String url = wxEmplService.getServiceBaseUrl() + "/sets/" + "wx/wxLogin/" + docWraper.getTextUnderRoot(Constant.FROMUSERNAME_TAG);
	    String content = "<a href='" + url + "#wechat_webview_type=1'>猛戳这里登录百一系统~</a>";
		wxEmplService.sendWelcome(docWraper.getTextUnderRoot(Constant.FROMUSERNAME_TAG) , content);
		return null;
	}

	@Override
	protected String execute(IWXCandService wxCandService) {
        logger.debug("send welcome to candidate ...");
		wxCandService.sendWelcome(docWraper.getTextUnderRoot(Constant.FROMUSERNAME_TAG));
		return null;
	}

}
