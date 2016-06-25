package com.ailk.sets.platform.dao.interfaces;

import java.util.List;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.intf.domain.InvitationCopyto;

public interface IInvitationCopytoDao extends IBaseDao<InvitationCopyto> {
	public List<InvitationCopyto> getInvitationCopytosByTestId(long testId);

}
