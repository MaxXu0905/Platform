package com.ailk.sets.platform.intf.model.wx.excuter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ailk.sets.platform.intf.cand.domain.Employer;
import com.ailk.sets.platform.intf.cand.service.IWXCandService;
import com.ailk.sets.platform.intf.domain.Candidate;
import com.ailk.sets.platform.intf.empl.service.IWXEmplService;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.wx.Constant;
import com.ailk.sets.platform.intf.model.wx.IWXExcuter;

/**
 * 扫描带参数二维码处理器
 * @author 毕希研
 *
 */
public class SubscribeExcuter extends AbstractExcuter implements IWXExcuter {
	private Logger logger = LoggerFactory.getLogger(SubscribeExcuter.class);

	protected String execute(IWXEmplService wxEmplService) {
		try {
			String param = docWraper.getTextUnderRoot(Constant.EVENT_EVENTKEY_TAG).split("qrscene_")[1];
			String openId = docWraper.getTextUnderRoot(Constant.FROMUSERNAME_TAG);
			Employer employer = wxEmplService.bindingEmployer(Integer.parseInt(param), openId);
			String url = wxEmplService.getServiceBaseUrl() + "/sets/" + "wx/wxLogin/" + docWraper.getTextUnderRoot(Constant.FROMUSERNAME_TAG);
	        String content = "<a href='" + url + "#wechat_webview_type=1'>猛戳这里登录百一系统~</a>";
			wxEmplService.sendScanwelcome(openId, employer.getEmployerName() ,content);
			return employer.toString();
		} catch (Exception e) {
			logger.error("call SubscribeExcuter execute IWXEmplService error", e);
		}
		return null;
	}

	protected String execute(IWXCandService wxCandService) {
		try {
			String param = docWraper.getTextUnderRoot(Constant.EVENT_EVENTKEY_TAG).split("qrscene_")[1];
			String openId = docWraper.getTextUnderRoot(Constant.FROMUSERNAME_TAG);
			
			Candidate candidate = wxCandService.bindingCandidate(Integer.parseInt(param), openId);
			wxCandService.sendScanwelcome(openId ,candidate.getCandidateName());
			return candidate.toString();
		} catch (PFServiceException e) {
			logger.error("call SubscribeExcuter execute IWXCandService error", e);
		}
		return null;
	}
}
