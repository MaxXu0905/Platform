package com.ailk.sets.platform.intf.model.wx.excuter;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ailk.sets.platform.intf.cand.service.IWXCandService;
import com.ailk.sets.platform.intf.domain.Candidate;
import com.ailk.sets.platform.intf.empl.service.IWXEmplService;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.wx.Constant;
import com.ailk.sets.platform.intf.model.wx.IWXExcuter;


public class TextExcuter extends AbstractExcuter implements IWXExcuter{
	private Logger logger = LoggerFactory.getLogger(AbstractExcuter.class);
    
	@Autowired

	@Override
	protected String execute(IWXEmplService wxEmplService) {
		return null;
	}

	@Override
	protected String execute(IWXCandService wxCandService) {
		try {
			String param = docWraper.getTextUnderRoot(Constant.MSGTYPE_CONTENT_TAG);
			String openId = docWraper.getTextUnderRoot(Constant.FROMUSERNAME_TAG);
			if(param != null)
			{
			    if (param.indexOf("+")!=-1)
                {
    				String infos[] = param.split("\\+");
    				if(infos.length < 2)
    				{
    					//输入信息格式不正确提示 -->"您输入的格式不正确，请按照姓名+邮箱(例如：小明+xiaoming@qq.com)绑定";
    					String text = wxCandService.bindFailed(openId);
    					wxCandService.sendFailedMessage(openId);
    					return text;
    				}
    				else
    				{
    					//输入标准格式后执行操作
    					String username = infos[0];
    					String email = infos[1];
    					Candidate candidate = (Candidate) wxCandService.bindingHandCandidate(username, email,openId);
    					wxCandService.sendSuccessMessage(openId, candidate);
    					return candidate.toString();
    				}
                }
			    
			    //
			    if(param.indexOf("#")!=-1)
                {
			        logger.debug("在线申测...用户输入"+param.trim());
                    // 格式2 口令#姓名#邮箱
                    String onlineReqStr[] = param.split("\\#");
                    if (onlineReqStr.length < 3)
                    {
                        wxCandService.onlineReqFailed(openId,"");
                    }else
                    {
                        //输入标准格式后执行操作
                        String passport = StringUtils.trim(onlineReqStr[0]);
                        String username = StringUtils.trim(onlineReqStr[1]);
                        String email = StringUtils.trim(onlineReqStr[2]);
                        wxCandService.onlineReqSave(openId ,passport,username,email);
                    }
                    return "";
                }
			}
			
		} catch (PFServiceException e) {
			logger.error("call SubscribeExcuter execute IWXCandService error", e);
		}
		return null;
	}

	public static void main(String[] args)
    {
	    String param = "demo#李攀#ablipan@163.com";
	    if(param != null)
        {
            if (param.indexOf("+")!=-1)
            {
                String infos[] = param.split("\\+");
                if(infos.length < 2)
                {
                    //输入信息格式不正确提示 -->"您输入的格式不正确，请按照姓名+邮箱(例如：小明+xiaoming@qq.com)绑定";
                    System.out.println("绑定错误");
                }
                else
                {
                    //输入标准格式后执行操作
                    String username = infos[0];
                    String email = infos[1];
                }
            }else if(param.indexOf("#")!=-1)
            {
                // 格式2 口令#姓名#邮箱
                String onlineReqStr[] = param.split("\\#");
                if (onlineReqStr.length < 3)
                {
                    System.out.println("申请错误");
                }else
                {
                    //输入标准格式后执行操作
                    String passport = onlineReqStr[0];
                    String username = onlineReqStr[1];
                    String email = onlineReqStr[2];
                    System.out.println(passport);
                }
            }
        }
    }
}
