package com.ailk.sets.grade.service;

import org.springframework.stereotype.Service;

import com.ailk.sets.grade.grade.common.DebugConfig;
import com.ailk.sets.grade.service.intf.IJmxDebugService;

@Service
public class JmxDebugService implements IJmxDebugService {

	@Override
	public void enableKeepTempFiles() {
		DebugConfig.setKeepTempFiles(true);
	}

	@Override
	public void disableKeepTempFiles() {
		DebugConfig.setKeepTempFiles(false);
	}

}
