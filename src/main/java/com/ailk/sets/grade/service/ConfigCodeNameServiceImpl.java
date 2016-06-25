package com.ailk.sets.grade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.dao.intf.IConfigCodeNameDao;
import com.ailk.sets.grade.service.intf.IConfigCodeNameService;
import com.ailk.sets.platform.intf.common.ConfigCodeType;
import com.ailk.sets.platform.intf.empl.domain.ConfigCodeName;
import com.ailk.sets.platform.intf.empl.domain.ConfigCodeNameId;

@Transactional(rollbackFor = Exception.class)
@Service
public class ConfigCodeNameServiceImpl implements IConfigCodeNameService {

	private static final String CODE_TYPE_POSITION_LEVEL = "POSITION_LEVEL";

	@Autowired
	private IConfigCodeNameDao configCodeNameDao;

	@Override
	public String getPositionLanguage(String codeId) {
		return getCodeName(ConfigCodeType.PROGRAM_LANGUAGE, codeId);
	}

	@Override
	public String getPositionLevel(String codeId) {
		return getCodeName(CODE_TYPE_POSITION_LEVEL, codeId);
	}

	@Override
	public String getCodeName(String codeType, String codeId) {
		ConfigCodeNameId configCodeNameId = new ConfigCodeNameId();
		configCodeNameId.setCodeType(codeType);
		configCodeNameId.setCodeId(codeId);

		ConfigCodeName configCodeName = configCodeNameDao.get(configCodeNameId);
		if (configCodeName == null)
			return null;

		return configCodeName.getCodeName();
	}

}
