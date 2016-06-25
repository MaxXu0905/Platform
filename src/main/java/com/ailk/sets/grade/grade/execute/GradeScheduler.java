package com.ailk.sets.grade.grade.execute;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ailk.sets.grade.grade.common.TraceManager;

@Service
public class GradeScheduler implements IGradeScheduler {

	private static final Logger logger = Logger.getLogger(GradeScheduler.class);

	@Autowired
	private IGradeMain gradeMain;

	@Value("${grade.schedulerEnabled}")
	private boolean schedulerEnabled;

	/**
	 * 判卷
	 */
	@Scheduled(fixedDelay = 1000 * 60, initialDelay = 1000 * 1)
	@Override
	public synchronized void gradeTests() {
		if (!schedulerEnabled)
			return;

		try {
			List<Long> readyList = gradeMain.getReadyList();
			if (readyList == null)
				return;

			for (long testId : readyList) {
				gradeMain.gradeTest(testId);
			}

			logger.info("判卷结束，等待一段时间继续");
		} catch (Exception e) {
			logger.error(TraceManager.getTrace(e));
		}
	}

	/**
	 * 对特定测评判卷
	 * 
	 * @param testId
	 */
	@Override
	public synchronized void gradeTest(long testId) {
		gradeMain.gradeTest(testId);
	}

}