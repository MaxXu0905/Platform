package com.ailk.sets.platform.intf.cand.service;

import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.domain.MsgCandidateInfo;

public interface IAppService {
	/**
	 * 保存短信内容  应聘者信息
	 * @param candidateInfo
	 * @return
	 */
	public PFResponse saveMsgCandidateInfo(MsgCandidateInfo candidateInfo);
	/**
	 * 更新心跳状态
	 * @param status
	 * @return
	 */
	public PFResponse udpateAppHeartBeat(int status);
}
