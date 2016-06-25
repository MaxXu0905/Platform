package com.ailk.sets.grade.grade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.dao.intf.ICandidateReportDao;
import com.ailk.sets.platform.intf.empl.domain.CandidateReport;

@Transactional(rollbackFor = Exception.class)
@Service
public class ReportConverter implements IReportConverter {

	@Autowired
	private ICandidateReportDao candidateReportDao;

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "/spring/beans.xml", "/spring/localbean.xml" });
		context.start();

		ReportConverter instance = context.getBean(ReportConverter.class);

		try {
			instance.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void execute() throws Exception {
		List<CandidateReport> candidateReports = candidateReportDao.getList();
		for (CandidateReport candidateReport : candidateReports) {
			String content = candidateReport.getContent();
			content = content.replace("paperPartInfos", "testPartInfos");

			candidateReport.setContent(content);
			candidateReportDao.update(candidateReport);

		}
	}

}
