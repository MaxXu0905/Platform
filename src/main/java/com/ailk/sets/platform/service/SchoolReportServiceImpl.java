package com.ailk.sets.platform.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ailk.sets.grade.dao.intf.ICandidateInfoExtDao;
import com.ailk.sets.platform.dao.interfaces.ICandidateTestDao;
import com.ailk.sets.platform.dao.interfaces.ICompanyRecruitActivityDao;
import com.ailk.sets.platform.dao.interfaces.IPositionDao;
import com.ailk.sets.platform.dao.interfaces.ISchoolReportDao;
import com.ailk.sets.platform.intf.cand.domain.CandidateInfoExt;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.common.FuncBaseResponse;
import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.domain.CompanyRecruitActivity;
import com.ailk.sets.platform.intf.domain.ReportStatusCountInfo;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.empl.service.IReport;
import com.ailk.sets.platform.intf.empl.service.ISchoolReportService;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.intf.model.candidateReport.PositionGroupReport;
import com.ailk.sets.platform.intf.model.param.GetReportParam;
import com.ailk.sets.platform.intf.school.domain.ReportSkillsScoreInfo;
import com.ailk.sets.platform.intf.school.domain.SchoolCandidateReport;
import com.ailk.sets.platform.intf.school.domain.SchoolReportCondition;
import com.ailk.sets.platform.intf.school.domain.SchoolReportInfo;
import com.alibaba.dubbo.common.utils.StringUtils;

@Transactional(rollbackFor = Exception.class)
public class SchoolReportServiceImpl implements ISchoolReportService{
    private Logger logger = LoggerFactory.getLogger(SchoolReportServiceImpl.class);
	@Autowired
	private ISchoolReportDao schoolReportDao;
	
	@Autowired
	private ICandidateTestDao candidateTestDao;
	
	@Autowired
	private  ICandidateInfoExtDao candidateInfoExtDao;
	@Autowired
	private IReport report;
	@Autowired
	private IPositionDao positionDao;
	@Autowired
	private ICompanyRecruitActivityDao activityDao;
	@Override
	public List<?> getSchoolReportList(SchoolReportCondition condition) {
		logger.debug("the SchoolReportCondition is {}", condition.toString());
		Integer positionId = condition.getPositionId();
		boolean group = false;
		if(positionId != null){
			Position position = positionDao.getEntity(positionId);
			if(position.getGroupFlag() == Constants.GROUP_FLAG_PARENT)
				group = true;
		}else{
			Integer activityId = condition.getActivityId();
			CompanyRecruitActivity activity = activityDao.getEntity(activityId);
		    Position position = positionDao.getEntity(activity.getPositionId());
		    if(position.getGroupFlag() == Constants.GROUP_FLAG_PARENT)
		    	group = true;
			
		}
		if(group){
			GetReportParam param = new GetReportParam();
			param.setEmployerId(condition.getEmployerId());
			param.setPositionId(condition.getPositionId());
			param.setActivityId(condition.getActivityId());
			param.setPage(condition.getPage());
			param.setInputKey(condition.getSearchTxt());
			param.setPositionIntent(condition.getIntentPos());
			List<PositionGroupReport>  groupReports = report.getReportInMem(param);
			return groupReports;
		}
		return schoolReportDao.getSchoolReportList(condition.getEmployerId(),condition.getPositionId(),condition.getActivityId(), condition.getPage(), condition.getSearchTxt(),condition.getTestResult(),condition.getIntentPos());
	}
	
	 /**
     * 获取测评各个科目分数
     * @param testId
     * @return
     */
	public List<ReportSkillsScoreInfo>  getReportSkillsScoreInfo(long testId){
		return schoolReportDao.getReportSkillsScoreInfo(testId);
	}
	
	
	/**
	 * 获取报告总数
	 * @return
	 */
	public long getCountReportList(SchoolReportCondition condition){
		logger.debug("the getCountReportList begin for condition {}", condition);
		return schoolReportDao.getCountReportList(condition.getEmployerId(), condition.getPositionId(),condition.getActivityId(), condition.getPage(), condition.getSearchTxt(),condition.getTestResult(),condition.getIntentPos());
	}
	
	/**
	 * 设置测试结果
	 * @param testId
	 * @param choose
	 * @return
	 * @throws PFServiceException
	 */
	public PFResponse updateTestResult(long testId, int choose) throws PFServiceException{
		PFResponse statistics = new PFResponse();
		logger.debug("updateTestResult for testId {}, testResult {} ", testId,choose);
		try {
			boolean result = candidateTestDao.setTestResult(testId, choose);
			if (result)
				statistics.setCode(FuncBaseResponse.SUCCESS);
			else
				statistics.setCode(FuncBaseResponse.FAILED);
		}catch(Exception e){
			logger.error("set test result error ", e);
			throw new PFServiceException(e);
		}
		return statistics;
	}
	
	public SchoolReportInfo getSchoolReportInfo(long testId) throws PFServiceException{
		SchoolReportInfo statistics = new SchoolReportInfo();
		logger.debug("getSchoolReportInfo for testId {}", testId );
		try {
			CandidateTest test = candidateTestDao.getCandidateTest(testId);
			if (test != null){
				statistics.setCode(FuncBaseResponse.SUCCESS);
				statistics.setTestResult(test.getTestResult());
				statistics.setGetScore(schoolReportDao.getGetScore(testId));
			}
			else{
				statistics.setCode(FuncBaseResponse.FAILED);
			}
		}catch(Exception e){
			logger.error("getSchoolReportInfo error ", e);
			throw new PFServiceException(e);
		}
		return statistics;
	}

	/**
	  * 获取报告各个状态数量接口
	  * @param employerId
	  * @param posOrActId
	  * @param type  1测评id  2活动id
	  * @return
	  */
	public List<ReportStatusCountInfo> getSchoolReportStatusCount(int employerId,int posOrActId, int type){
		logger.debug("getSchoolReportStatusCount for employerId {}, posOrActId {} , type " + type,employerId,posOrActId);
	    return schoolReportDao.getSchoolReportStatusCount(employerId,posOrActId, type);
	}

	@Override
	public List<SchoolCandidateReport> getSchoolReportListInMem(SchoolReportCondition condition) {
		logger.debug("getSchoolReportListInMem , the condition is {} ", condition);
		long time1 = System.currentTimeMillis();
		List<SchoolCandidateReport> reports = schoolReportDao.getAllSchoolReportList(condition.getEmployerId(),condition.getPositionId(),condition.getActivityId(),
				condition.getTestResult());
		logger.debug("getAllSchoolReportListByActId waste for {} " , System.currentTimeMillis() - time1);
		if (CollectionUtils.isEmpty(reports))
			return reports;

		// 过滤begin
		String searchTxt = condition.getSearchTxt();
		String intentPos = condition.getIntentPos();
		List<Integer> candidateIds = new ArrayList<Integer>();
		for (SchoolCandidateReport report : reports) {
			candidateIds.add(report.getCandidateId());
		}
		Map<Integer, Map<String, CandidateInfoExt>> candidateMap = candidateInfoExtDao
				.getCandidateInfoExts(candidateIds);
		List<SchoolCandidateReport> filterReports = new ArrayList<SchoolCandidateReport>();
		for (SchoolCandidateReport report : reports) {

			String name = report.getCandidateName();
			String email = report.getCandidateEmail();
			Map<String, CandidateInfoExt> candidateInfoExt = candidateMap.get(report.getCandidateId());

			String phone = "";
			CandidateInfoExt phoneExt = candidateInfoExt.get(Constants.PHONE);
			if (phoneExt != null && StringUtils.isNotEmpty(phoneExt.getValue())) {
				phone = phoneExt.getValue();
				report.setCandidatePhone(phone);
			}
			String intent = "";
			CandidateInfoExt intentExt = candidateInfoExt.get(Constants.INTENTION_POSITION);
			if (intentExt != null && StringUtils.isNotEmpty(intentExt.getValue())) {
				intent = intentExt.getValue();
			}
			if (StringUtils.isNotEmpty(searchTxt)) {
				if (!(name.contains(searchTxt) || email.contains(searchTxt) || phone.contains(searchTxt))) {
					filterReports.add(report);
					continue;
				}
			}
			if (StringUtils.isNotEmpty(intentPos)) {
				if (!intent.equals(intentPos)) {
					filterReports.add(report);
					continue;
				}
			}
		}
		logger.debug("filter filterReports size is {}, the reportSize is {} ", filterReports.size(), reports.size());
		reports.removeAll(filterReports);

		Page page = condition.getPage();
		int fromIndex = page.getFirstRow();
		int toIndex = page.getLastRow();
		if (fromIndex > reports.size())
			fromIndex = reports.size();
		if (toIndex > reports.size())
			toIndex = reports.size();
		return reports.subList(fromIndex, toIndex);
	}
}
