package com.ailk.sets.platform.dao.interfaces;

import java.util.List;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.domain.CandidateInfoFromApp;

public interface ICandidateInfoFromAppDao extends IBaseDao<CandidateInfoFromApp> {
	public List<CandidateInfoFromApp> getNeedSendInvitationCandidates() ;
}
