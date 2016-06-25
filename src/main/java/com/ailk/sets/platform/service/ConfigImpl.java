package com.ailk.sets.platform.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.dao.interfaces.IConfigDao;
import com.ailk.sets.platform.intf.empl.domain.ConfigCodeName;
import com.ailk.sets.platform.intf.empl.service.IConfig;
import com.ailk.sets.platform.intf.exception.PFServiceException;

/**
 * 系统配置服务实现类
 * 
 * @author 毕希研
 * 
 */
@Transactional(rollbackFor = Exception.class)
public class ConfigImpl implements IConfig {

	@Autowired
	private IConfigDao configDao;
	private Logger logger = LoggerFactory.getLogger(CandidateInfoServiceImpl.class);

	public List<ConfigCodeName> getConfigCode(String codeType) throws PFServiceException {

		try {
			return configDao.getConfigCode(codeType);
		} catch (Exception e) {
			logger.error("call getConfigCode error ", e);
			throw new PFServiceException(e.getMessage());
		}
	}
}
