package com.ailk.sets.platform.intf.empl.domain;

import java.io.Serializable;
import java.util.List;

import com.ailk.sets.platform.intf.domain.PositionLevelInfo;
import com.ailk.sets.platform.intf.domain.PositionSeries;
/**
 * 测评创建初始化信息
 * @author panyl
 *
 */
public class PositionInitInfo implements Serializable {
	
	private static final long serialVersionUID = 1557883495748064087L;
	private List<PositionSeries> positionSeries;
	private List<PositionLevelInfo>  positionLevels;
	
	public List<PositionSeries> getPositionSeries() {
		return positionSeries;
	}
	public void setPositionSeries(List<PositionSeries> positionSeries) {
		this.positionSeries = positionSeries;
	}
	public List<PositionLevelInfo> getPositionLevels() {
		return positionLevels;
	}
	public void setPositionLevels(List<PositionLevelInfo> positionLevels) {
		this.positionLevels = positionLevels;
	}
}
