package com.ailk.sets.platform.intf.empl.service;

import com.ailk.sets.platform.intf.empl.domain.SmsSendResult;
import com.ailk.sets.platform.intf.exception.PFServiceException;

public interface ISystemService {
	/**
	 * 发送短信
	 * @param employerId
	 * @param phone
	 * @return
	 */
	public SmsSendResult sendSms(int employerId, String phone) throws PFServiceException;
}
