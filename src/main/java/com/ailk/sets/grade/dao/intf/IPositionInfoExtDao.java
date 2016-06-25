package com.ailk.sets.grade.dao.intf;

import java.util.List;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.domain.PositionInfoExt;

public interface IPositionInfoExtDao extends IBaseDao<PositionInfoExt> {

	public List<PositionInfoExt> getList(int employerId, int positionId);
	
}
