package com.ailk.sets.platform.dao.interfaces;

import java.util.List;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.intf.domain.PositionSeries;

public interface IPositionSeriesDao extends IBaseDao<PositionSeries> {
	public List<PositionSeries> getPositionSeries(int employerId);
	public List<PositionSeries> getPositionSeriesByName(String seriesName,int employerId);
	public void evict();
}
