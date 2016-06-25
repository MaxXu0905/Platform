package com.ailk.sets.platform.dao.interfaces;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.domain.PositionLog;

public interface IPositionLogDao extends IBaseDao<PositionLog> {
	public PositionLog getPositionLogByPosIdAndState(int positionId, int positionState);

}
