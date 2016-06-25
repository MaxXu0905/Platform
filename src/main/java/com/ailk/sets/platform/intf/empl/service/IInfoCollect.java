package com.ailk.sets.platform.intf.empl.service;

import java.util.Collection;
import java.util.List;

import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.empl.domain.ConfigInfoExtEx;
import com.ailk.sets.platform.intf.empl.domain.PositionInfoConfig;
import com.ailk.sets.platform.intf.exception.PFServiceException;

/**
 * 信息采集服务
 * 
 * @author 毕希研
 * 
 */
public interface IInfoCollect {

	/**
	 * 获取信息字段
	 * 
	 * @param employerId
	 * @return
	 * @throws PFServiceException
	 */
	public Collection<ConfigInfoExtEx> getInfoExt(int employerId,Integer positionId) throws PFServiceException;
	

	/**
	 * 获取二维码图片
	 * 
	 * @param employerId
	 * @return
	 * @throws PFServiceException
	 */
	public String getQRCodePicUrl(int employerId) throws PFServiceException;
	
	/**
	 * 保存职位常规信息
	 * @param employerId
	 * @param infoSeq
	 * @return
	 * @throws PFServiceException
	 */
	public PFResponse saveInfo(int employerId, int positionId, List<PositionInfoConfig> configInfo) throws PFServiceException;
}
