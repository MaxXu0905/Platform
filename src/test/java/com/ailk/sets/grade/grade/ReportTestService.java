package com.ailk.sets.grade.grade;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.service.intf.IReportSaveService;
import com.ailk.sets.platform.dao.interfaces.ICandidateTestDao;
import com.ailk.sets.platform.dao.interfaces.IPositionLogDao;
import com.ailk.sets.platform.domain.PositionLog;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;

@Transactional(rollbackFor = Exception.class)
@Service
public class ReportTestService implements IReportTestService {

	@Autowired
	private ICandidateTestDao candidateTestDao;

	@Autowired
	private IReportSaveService reportService;

	@Autowired
	private IPositionLogDao positionLogDao;

	public void saveReport(long testId) throws Exception {
		CandidateTest candidateTest = (CandidateTest) candidateTestDao
				.getEntity(testId);
		if (candidateTest == null)
			return;

		reportService.saveReport(candidateTest);
	}

	@Override
	public void save() {
		PositionLog positionLog = new PositionLog();
		positionLog.setLogTime(new Timestamp(System.currentTimeMillis()));

		positionLogDao.save(positionLog);
	}

}
