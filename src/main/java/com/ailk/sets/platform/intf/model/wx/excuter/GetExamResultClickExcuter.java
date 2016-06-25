package com.ailk.sets.platform.intf.model.wx.excuter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ailk.sets.platform.intf.cand.service.IWXCandService;
import com.ailk.sets.platform.intf.empl.service.IWXEmplService;
import com.ailk.sets.platform.intf.model.wx.Constant;
import com.ailk.sets.platform.intf.model.wx.IWXExcuter;
import com.ailk.sets.platform.intf.model.wx.resp.RespText;
import com.ailk.sets.platform.intf.model.wx.util.JaxbUtil;

public class GetExamResultClickExcuter extends AbstractExcuter implements IWXExcuter {
	private Logger logger = LoggerFactory.getLogger(GetExamResultClickExcuter.class);

	@Override
	protected String execute(IWXEmplService wxEmplService) {
		try {
			String url = "http://www.101test.com/campus/wx/index/gh_df877ca37d29";
			String content = "你好，欢迎使用百一测评校招微信答题平台 ,<a href=\"" + url + "\">点击开始考试</a>";
			content = URLEncoder.encode(content,"UTF-8");
			logger.debug("CLICK BUTTON RETURN CONTENT"+content);
			
			RespText respText = new RespText();
			respText.setFromUserName(docWraper.getTextUnderRoot(Constant.TOUSERNAME_TAG));
			respText.setToUserName(docWraper.getTextUnderRoot(Constant.FROMUSERNAME_TAG));
			respText.setCreateTime(new Date().getTime() + "");
			respText.setMsgType(Constant.MSGTYPE_TEXT);
			respText.setContent(content);
			logger.debug(JaxbUtil.marshal(respText, RespText.class).replace("&lt;", "<").replace("&gt;", ">").replace("&quot;", "\"").replace("\n","<br>"));
			return JaxbUtil.marshal(respText, RespText.class).replace("&lt;", "<").replace("&gt;", ">").replace("&quot;", "\"").replace("\n","<br>");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected String execute(IWXCandService wxCandService) {
		// TODO Auto-generated method stub
		return null;
	}

}
