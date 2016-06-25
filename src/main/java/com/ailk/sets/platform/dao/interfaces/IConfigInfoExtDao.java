package com.ailk.sets.platform.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.exception.PFDaoException;
import com.ailk.sets.platform.intf.empl.domain.ConfigInfoExt;

/**
 * 未来考虑加入到缓存中 常规信息配置表访问dao，属于系统初始化录入的配置信息，对应于常规信息录入控件等
 * 
 * @author 毕希研
 * 
 */
public interface IConfigInfoExtDao extends IBaseDao<ConfigInfoExt>{

	/**
	 * @param employerId
	 * @param infoSeq
	 * @throws PFDaoException
	 */
	public void saveInfo(int employerId, int positionId,Map<String, Integer> infoSeq) ;
	
	/**
	 * 获取所有必选的常规信息
	 * @return
	 */
	public List<ConfigInfoExt> getMandatoryInfoExt();
}
