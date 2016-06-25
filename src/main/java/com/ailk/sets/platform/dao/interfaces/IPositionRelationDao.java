package com.ailk.sets.platform.dao.interfaces;

import java.util.List;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.domain.PositionRelation;

public interface IPositionRelationDao  extends IBaseDao<PositionRelation>{

	public List<PositionRelation> getPositionRelationByPositionGroupId(int positionGroupId);
}
