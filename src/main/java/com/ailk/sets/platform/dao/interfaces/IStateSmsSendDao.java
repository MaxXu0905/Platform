package com.ailk.sets.platform.dao.interfaces;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.domain.StateSmsSend;

public interface IStateSmsSendDao extends IBaseDao<StateSmsSend> {

	/**
	 * 获取最后一次发送的短信
	 * @param employerId
	 * @param phone
	 * @return
	 */
	public StateSmsSend getLastStateSmsSend(int employerId, String phone, int type);
}
