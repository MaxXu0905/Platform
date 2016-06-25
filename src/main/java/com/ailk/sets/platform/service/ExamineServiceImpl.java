package com.ailk.sets.platform.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.dao.intf.ICandidateTestPartDao;
import com.ailk.sets.grade.dao.intf.ICandidateTestQuestionDao;
import com.ailk.sets.grade.intf.GetQidsResponse;
import com.ailk.sets.grade.intf.GetQidsResponse.QInfo;
import com.ailk.sets.grade.intf.IGradeService;
import com.ailk.sets.grade.intf.report.GetReportSummaryResponse;
import com.ailk.sets.platform.common.ConfigSysParam;
import com.ailk.sets.platform.dao.interfaces.ICandidateTestDao;
import com.ailk.sets.platform.dao.interfaces.IConfigDao;
import com.ailk.sets.platform.dao.interfaces.IConfigSysParamDao;
import com.ailk.sets.platform.dao.interfaces.IInvitationDao;
import com.ailk.sets.platform.dao.interfaces.IPaperDao;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionDao;
import com.ailk.sets.platform.dao.interfaces.IReportDao;
import com.ailk.sets.platform.domain.paper.CandidateTestPart;
import com.ailk.sets.platform.domain.paper.CandidateTestPartId;
import com.ailk.sets.platform.domain.paper.CandidateTestQuestion;
import com.ailk.sets.platform.domain.paper.CandidateTestQuestionId;
import com.ailk.sets.platform.intf.cand.domain.CandidateExamInfo;
import com.ailk.sets.platform.intf.cand.domain.ExamineTimeInfo;
import com.ailk.sets.platform.intf.cand.domain.Invitation;
import com.ailk.sets.platform.intf.cand.domain.PaperData;
import com.ailk.sets.platform.intf.cand.domain.PaperMarkData;
import com.ailk.sets.platform.intf.cand.domain.PaperPartData;
import com.ailk.sets.platform.intf.cand.domain.PaperQuestionInfo;
import com.ailk.sets.platform.intf.cand.domain.QuestionExt;
import com.ailk.sets.platform.intf.cand.service.IExamineService;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.common.FuncBaseResponse;
import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.common.PFResponseData;
import com.ailk.sets.platform.intf.common.PaperCreateResult;
import com.ailk.sets.platform.intf.common.PaperInstancePartStateEnum;
import com.ailk.sets.platform.intf.common.PaperPartSeqEnum;
import com.ailk.sets.platform.intf.common.PaperPartTimerType;
import com.ailk.sets.platform.intf.empl.domain.CandidateReport;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;
import com.ailk.sets.platform.intf.empl.domain.ConfigCodeName;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;
import com.ailk.sets.platform.intf.empl.service.IOutCallService;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.candidateReport.CandReportAndInfo;
import com.ailk.sets.platform.intf.model.monitor.MonitorInfo;
import com.ailk.sets.platform.service.local.IPaperService;
import com.ailk.sets.platform.util.PaperCreateUtils;

/**
 * 试卷服务
 * 
 * @author panyl
 * 
 */
@Transactional(rollbackFor = Exception.class)
public class ExamineServiceImpl implements IExamineService {

	@Autowired
	private IConfigSysParamDao configSysParamDao;

	@Autowired
	private IPaperDao paperDao;

	@Autowired
	private ICandidateTestDao candidateTestDao;

	@Autowired
	private IQbQuestionDao qbQuestionDao;

	@Autowired
	private IGradeService gradeService;

	@Autowired
	private IPaperService paperServiceImpl;
	
	@Autowired
	private IConfigDao configDao;
	@Autowired
	private IInvitationDao invitationDao;
	@Autowired
	private ICandidateTestPartDao candidateTestPartDao;
	@Autowired
	private ICandidateTestQuestionDao candidateTestQuestionDao;
	
	@Autowired
	private IOutCallService outCallService;
	
	@Autowired
	private IReportDao reportDao;
	
	Logger logger = LoggerFactory.getLogger(ExamineServiceImpl.class);

	/**
	 * 获取剩余时间
	 */
	@Override
	public ExamineTimeInfo getExamineTimeLeft(long testId, int partSeq) {
		Invitation invitation = invitationDao.getEntity(testId);
		if (invitation == null) {
			logger.error("not found invitation for id {}", testId);
			return null;
		}
		CandidateTestPartId id = new CandidateTestPartId();
		id.setTestId(testId);
		id.setPartSeq(partSeq);
		CandidateTestPart paperInstancePart = candidateTestPartDao.getEntity(id);
		Date date = paperInstancePart.getBeginTime();
		if (date == null) {
			logger.error(
					"the startTime is null for id {} , partSeq {}, please check",
					testId, partSeq);
			return null;
		}
		int seconds = paperInstancePart.getSuggestTime();
		Calendar end = Calendar.getInstance();
		end.setTime(date);
		end.set(Calendar.SECOND, end.get(Calendar.SECOND) + seconds);

		Calendar now = Calendar.getInstance();
		ExamineTimeInfo info = new ExamineTimeInfo();
		String timeLeft = getSubFormatTime(now, end);
		info.setTimeLeft(timeLeft);
		return info;
	}

	/**
	 * 获取剩余时间
	 */
	public int getExamineTimeLeftSecond(long testId, int partSeq) {
		Invitation invitation = invitationDao.getEntity(testId);
		if (invitation == null) {
			logger.error("not found invitation for id {}", testId);
			return 0;
		}
		long paperInstId = invitation.getTestId();
		CandidateTestPartId id = new CandidateTestPartId();
		id.setTestId(paperInstId);
		id.setPartSeq(partSeq);
		CandidateTestPart paperInstancePart = candidateTestPartDao.getEntity(id);
		Date date = paperInstancePart.getBeginTime();
		if (date == null) {
			logger.error(
					"the startTime is null for id {} , partSeq {}, please check",
					testId, partSeq);
			return 0;
		}
		int seconds = paperInstancePart.getSuggestTime();
		Calendar end = Calendar.getInstance();
		end.setTime(date);
		end.set(Calendar.SECOND, end.get(Calendar.SECOND) + seconds);

		Calendar now = Calendar.getInstance();
		long time1 = end.getTimeInMillis() - now.getTimeInMillis();
		return (int) (time1 / 1000);
	}

	private static String getSubFormatTime(Calendar now, Calendar end) {
		if (end.after(now)) {
			long passed = end.getTimeInMillis() - now.getTimeInMillis();
			int hour = (int) (passed / (1000 * 60 * 60));
			int min = (int) ((passed % (1000 * 60 * 60)) / (1000 * 60));
			int second = (int) (((passed % (1000 * 60 * 60)) % (1000 * 60)) / 1000);
			String result = (hour <= 9 ? "0" + hour : hour + "");
			result += ":" + (min <= 9 ? "0" + min : min + "");
			result += ":" + (second <= 9 ? "0" + second : second + "");
			return result;
		}
		return "00:00:00";
	}

	@Override
	public boolean markQuestion(long testId, int partSeq, long qId) {
		return candidateTestDao.markQuestion(testId, partSeq, qId);
	}

	/**
	 * 取消标记问题
	 * 
	 * @param testId
	 * @param qId
	 * @return
	 */
	@Override
	public boolean unMarkQuestion(long testId, int partSeq, long qId) {
		return candidateTestDao.unMarkQuestion(testId, partSeq, qId);
	}

	@Override
	public boolean markOrUnMarkQuestin(long testId, int partSeq, long qId) {
		if (candidateTestDao.isMarkedTheQuestion(testId, partSeq, qId)) {// 已经标记则取消标记
			logger.debug(
					"cancel the partSeq {}, qId {} because is been marked",
					partSeq, qId);
			return unMarkQuestion(testId, partSeq, qId);
		}
		logger.debug("mark the partSeq {}, qId {} ", partSeq, qId);
		return markQuestion(testId, partSeq, qId);// 没有标记则标记
	}

	/**
	 * 更新部分状态
	 * 
	 * @param testId
	 * @param partSeq
	 * @param status
	 * @return
	 */
	public PFResponse updatePaperInstancePartStatus(long testId, int partSeq,
			int status) {
		logger.debug(
				"updatePaperInstancePartStatus for partSeq {}, status {}, testId "
						+ testId, partSeq, status);
		PFResponse res = new PFResponse();
		Invitation info = invitationDao.getEntity(testId);
		if (info == null) {
			logger.error("not found invitation for id {}", testId);
			res.setCode(FuncBaseResponse.FAILED);
			res.setMessage("not found Invitation for " + testId);
			return res;
		}

		long paperInstId = info.getTestId();
		CandidateTestPartId id = new CandidateTestPartId(paperInstId, partSeq);
		CandidateTestPart part = candidateTestPartDao.getEntity(id);
		if (part != null) {
			part.setPartState(status);
			res.setCode(FuncBaseResponse.SUCCESS);
			if (status == PaperInstancePartStateEnum.ANSWERING.getValue()) {
				if (part.getBeginTime() == null) {
					part.setBeginTime(new Date());
				} else {
					logger.debug(
							"the paper part has start at time {} for partseq {} in testId "
									+ testId, part.getBeginTime(), partSeq);
				}
			}
			if (status == PaperInstancePartStateEnum.COMMIT_MANUAL.getValue()) {// 提交部分，需要将答题标记记录到下一部分
				part.setEndTime(new Date());
				List<CandidateTestPart> partInfos = candidateTestPartDao.getList(testId);/*qbNoramlDaoImpl
						.getNoramlListByHql(
								"from CandidateTestPart where id.testId = "
										+ testId, CandidateTestPart.class);*/
				int length = partInfos.size();
				CandidateTestPart nextPart = null;
				int partIndex = -1;
				for (int i = 0; i < length; i++) {
					if (partSeq == partInfos.get(i).getId().getPartSeq()) {
						if (i < (length - 1)) {
							nextPart = partInfos.get(i + 1);
							partIndex = i + 2;// 从1开始
							break;
						}
						logger.debug(" {} is the last partSeq for testId {} ",
								partSeq, testId);
					}
				}
				if (nextPart != null) {
					logger.debug("enter the {} part", nextPart.getId()
							.getPartSeq());
					updatePartSeqAndQuesitonId(testId, nextPart.getId()
							.getPartSeq(), -1, partIndex, -1);
				}
			}
			candidateTestPartDao.saveOrUpdate(part);
			return res;
		}
		res.setCode(FuncBaseResponse.FAILED);
		res.setMessage("not found part for paperInstId " + paperInstId
				+ ", partSeq " + partSeq);
		return res;
	}

	@Override
	public PaperData getPaperData(long testId, String passCode) {
		logger.debug("getPaperData for testId {}, passCode {}  ", testId,
				passCode);
		try {
			Invitation info = invitationDao.getEntity(testId);
			if (info == null) {
				logger.error("not found testId for id {}", testId);
				return null;
			}
			List<PaperPartData> partDatas = new ArrayList<PaperPartData>();
			List<CandidateTestPart> partInfos = candidateTestPartDao.getAllCandidateTestPartByTestId(testId);/*qbNoramlDaoImpl.getNoramlListByHql(
					"from CandidateTestPart where id.testId = " + testId,
					CandidateTestPart.class)*/;
			Map<Integer, PaperPartData> data = changeListToMap(partInfos);
			GetQidsResponse resp = gradeService.getQids(testId);
	
			List<PaperMarkData> marks = getPaperMarkDatas(testId);
	
			/*PaperPartData part = data.get(PaperPartSeqEnum.TEST_OBJECT.getValue());
			if (part != null) {
				List<PaperQuestionInfo> list = new ArrayList<PaperQuestionInfo>();
				PaperQuestionInfo qInfo = new PaperQuestionInfo();
				List<CandidateTestQuestion> sampleChoice = candidateTestQuestionDao.getListByPart(testId, PaperPartSeqEnum.TEST_OBJECT.getValue());
				qInfo.setQuestionId(sampleChoice.get(0).getId().getQuestionId());
				list.add(qInfo);
				part.setQuestionIds(list);
				partDatas.add(part);
			}
	
			part = data.get(PaperPartSeqEnum.TEST_SUBJECT.getValue());
			if (part != null) {
				List<PaperQuestionInfo> list = new ArrayList<PaperQuestionInfo>();
				PaperQuestionInfo qInfo = new PaperQuestionInfo();
				List<CandidateTestQuestion> sampleSubject = candidateTestQuestionDao.getListByPart(testId, PaperPartSeqEnum.TEST_SUBJECT.getValue());
				qInfo.setQuestionId(sampleSubject.get(0).getId().getQuestionId());
				list.add(qInfo);
				part.setQuestionIds(list);
				partDatas.add(part);
			}
	
			part = data.get(PaperPartSeqEnum.TEST_INTERVIEW.getValue());
			if (part != null) {
				List<PaperQuestionInfo> list = new ArrayList<PaperQuestionInfo>();
				List<CandidateTestQuestion> questions = candidateTestQuestionDao.getListByPart(testId,  PaperPartSeqEnum.TEST_INTERVIEW.getValue());
				for (CandidateTestQuestion q : questions) {
					PaperQuestionInfo qInfo = new PaperQuestionInfo();
					qInfo.setQuestionId(q.getId().getQuestionId());
					list.add(qInfo);
				}
				part.setQuestionIds(list);
				partDatas.add(part);
			}*/
	
			PaperPartData part = data.get(PaperPartSeqEnum.OBJECT.getValue());
			if (part != null) {
				PaperMarkData paperMark = getPaperMarkDataByPartSeq(marks,
						PaperPartSeqEnum.OBJECT.getValue());
				part.setQuestionIds(getPaperQuestionInfos(resp.getChoices(),
						paperMark));
				partDatas.add(part);
			}
	
			part = data.get(PaperPartSeqEnum.SUBJECT.getValue());
			if (part != null) {
				PaperMarkData paperMark = getPaperMarkDataByPartSeq(marks,
						PaperPartSeqEnum.SUBJECT.getValue());
				List<CandidateTestQuestion> subjects =  candidateTestQuestionDao.getListByPart(testId, PaperPartSeqEnum.SUBJECT.getValue());
				part.setQuestionIds(getPaperQuestionInfosByTestQuestions(subjects,
						paperMark));
				partDatas.add(part);
			}
	
			part = data.get(PaperPartSeqEnum.ESSAY.getValue());
			if (part != null) {
				PaperMarkData paperMark = getPaperMarkDataByPartSeq(marks,
						PaperPartSeqEnum.ESSAY.getValue());
				List<CandidateTestQuestion> extras =  candidateTestQuestionDao.getListByPart(testId, PaperPartSeqEnum.ESSAY.getValue());
				part.setQuestionIds(getPaperQuestionInfosByTestQuestions(extras,
						paperMark));
				partDatas.add(part);
			}
			
			part = data.get(PaperPartSeqEnum.EXTRA.getValue());
			if (part != null) {
				PaperMarkData paperMark = getPaperMarkDataByPartSeq(marks,
						PaperPartSeqEnum.EXTRA.getValue());
				List<CandidateTestQuestion> extras =  candidateTestQuestionDao.getListByPart(testId, PaperPartSeqEnum.EXTRA.getValue());
				part.setQuestionIds(getPaperQuestionInfosByTestQuestions(extras,
						paperMark));
				partDatas.add(part);
			}
	
			part = data.get(PaperPartSeqEnum.INTEVEIW.getValue());
			if (part != null) {
				List<CandidateTestQuestion> questions =  candidateTestQuestionDao.getListByPart(testId, PaperPartSeqEnum.INTEVEIW.getValue());
				List<PaperQuestionInfo> list = new ArrayList<PaperQuestionInfo>();
				for (CandidateTestQuestion q : questions) {
					PaperQuestionInfo qInfo = new PaperQuestionInfo();
					qInfo.setQuestionId(q.getId().getQuestionId());
					qInfo.setSuggestTime(q.getSuggestTime());
					if (q.getBeginTime() != null) {
						logger.debug(
								"the question {} in paper {} is beginAnswered at "
										+ q.getBeginTime(), q.getId()
										.getQuestionId(), testId);
						int leftSend = PaperCreateUtils.getBetweenTimeForSencond(
								q.getBeginTime(), q.getSuggestTime(), new Date());
						logger.debug("left second is {}, for questionId {}",
								leftSend, q.getId().getQuestionId());
						qInfo.setSuggestTime(leftSend);
					}
					list.add(qInfo);
				}
				part.setQuestionIds(list);
				partDatas.add(part);
			}
	
			
			PaperData paperData = new PaperData();
			
			// add by Lipan 考试约需时长。。。。。。。 2014年09月20日15:46:17
			CandidateTest candidateTest = candidateTestDao.getEntity(testId);
			Double avgTime = paperDao.getPaperTotalTime(candidateTest.getPaperId());
			avgTime = Math.ceil(avgTime/60);
			paperData.setTotalTime(avgTime.intValue());
			paperData.setTimerType(candidateTestPartDao.getCandidateTestTimerType(testId));//试卷计时类型
			paperData.setPartDatas(partDatas);
			return paperData;
		} catch (Exception e) {
			logger.error("error get paper part data ", e);
			throw new RuntimeException(e);
		}
	}

	private PaperMarkData getPaperMarkDataByPartSeq(List<PaperMarkData> list,
			int partSeq) {
		if (list == null) {
			logger.debug("not any mark data for the partseq {}", partSeq);
			return null;
		}
		for (PaperMarkData mark : list) {
			if (mark.getPartSeq() == partSeq) {
				return mark;
			}
		}
		return null;

	}

	private List<PaperQuestionInfo> getPaperQuestionInfos(List<QInfo> qInfos,
			PaperMarkData paperMark) {
		List<PaperQuestionInfo> infos = new ArrayList<PaperQuestionInfo>();
		for (QInfo qInfo : qInfos) {
			PaperQuestionInfo info = new PaperQuestionInfo();
			info.setQuestionId(qInfo.getQid());
			if (qInfo.isAnswered()) {
				info.setAnswered(1);
			}
			if (paperMark != null && paperMark.getQuestionIds() != null
					&& paperMark.getQuestionIds().contains(qInfo.getQid())) {
				info.setMarked(1);
			}
			infos.add(info);
		}
		return infos;
	}
	private List<PaperQuestionInfo> getPaperQuestionInfosByTestQuestions(List<CandidateTestQuestion> testQusetions,
			PaperMarkData paperMark) {
		List<PaperQuestionInfo> infos = new ArrayList<PaperQuestionInfo>();
		for (CandidateTestQuestion q : testQusetions) {
			PaperQuestionInfo info = new PaperQuestionInfo();
			info.setQuestionId(q.getId().getQuestionId());
			if (q.getAnswerFlag() != null && q.getAnswerFlag() == 1) {
				info.setAnswered(1);
			}
			if (paperMark != null && paperMark.getQuestionIds() != null
					&& paperMark.getQuestionIds().contains(q.getId().getQuestionId())) {
				info.setMarked(1);
			}
			infos.add(info);
		}
		return infos;
	}
	private Map<Integer, PaperPartData> changeListToMap(
			List<CandidateTestPart> partInfos) {
		Map<Integer, PaperPartData> map = new HashMap<Integer, PaperPartData>();
		for (CandidateTestPart part : partInfos) {
			PaperPartData data = new PaperPartData();
			data.setPartSeq(part.getId().getPartSeq());
			if (part.getId().getPartSeq() == PaperPartSeqEnum.TEST_OBJECT
					.getValue()) {
				ConfigCodeName config = configDao.getConfigCode(
						Constants.CONFIG_PAPER_PART, part.getId().getPartSeq()
								+ "");
				data.setPartDesc(config.getCodeDesc());
			} else if (part.getId().getPartSeq() == PaperPartSeqEnum.TEST_SUBJECT
					.getValue()) {
				ConfigCodeName config = configDao.getConfigCode(
						Constants.CONFIG_PAPER_PART, part.getId().getPartSeq()
								+ "");
				data.setPartDesc(config.getCodeDesc());
			} else {
				data.setPartDesc(part.getPartDesc());
			}

			data.setSuggestTime(part.getSuggestTime());
			data.setTimerType(part.getTimerType());
			map.put(part.getId().getPartSeq(), data);
		}
		return map;
	}

	/**
	 * 
	 * @param testId
	 * @param type
	 *            1:按部分获取剩余时间 2:按题目获取剩余时间
	 * @return
	 * @throws Exception
	 */
	public CandidateExamInfo getCandidateExamInfo(long testId, int type)
			throws Exception {
		CandidateTest exam = candidateTestDao.getCandidateTest(testId);
		if (exam == null) {
			logger.error("not found invitation for id {}", testId);
			return null;
		}
		CandidateExamInfo info = new CandidateExamInfo();
		PropertyUtils.copyProperties(info, exam);
		int timerType = candidateTestPartDao.getCandidateTestTimerType(testId);
		// 按题目单独计时，需要leftTime为当前题目的剩余时间
		if (timerType == PaperPartTimerType.QUESTION.getValue()) {
			Long qId = info.getQuestionId();
			if (qId != null) {
				QuestionExt ext = getQuestionExt(testId, qId);
				info.setTimeLeft(ext.getLeftTime() + "");
			}
		} else if(timerType == PaperPartTimerType.PART.getValue()) {
			if (StringUtils.isNotEmpty(info.getPartSeq())) {
				int left = getExamineTimeLeftSecond(testId,
						Integer.valueOf(info.getPartSeq()));
				if (left != 0) {
					logger.debug("the timeLeft is  {} for partSeq {} ", left,
							info.getPartSeq());
					if (left <= 0) {
						left = 0;
					}
					info.setTimeLeft(left + "");
				} else {
					logger.debug(
							"not found any time info for id {}, partSeq {}",
							testId, info.getPartSeq());
				}
			}
		}else{
			logger.error("not support the timerType now {} ", timerType);
		}
		return info;
	}

	/**
	 * 获取答题信息，用于定位到部分以及题目, 按部分计时
	 * 
	 * @param testId
	 * @return
	 */
	public CandidateExamInfo getCandidateExamInfoForPart(long testId)
			throws Exception {
		CandidateTest exam = candidateTestDao.getCandidateTest(testId);;
		if (exam == null) {
			logger.error("not found invitation for id {}", testId);
			return null;
		}
		CandidateExamInfo info = new CandidateExamInfo();
		PropertyUtils.copyProperties(info, exam);
		if (StringUtils.isNotEmpty(info.getPartSeq())) {
			int left = getExamineTimeLeftSecond(testId,
					Integer.valueOf(info.getPartSeq()));
			if (left != 0) {
				logger.debug("the timeLeft is  {} for partSeq {} ", left,
						info.getPartSeq());
				if (left <= 0) {
					left = 0;
				}
				info.setTimeLeft(left + "");
			} else {
				logger.debug("not found any time info for id {}, partSeq {}",
						testId, info.getPartSeq());
			}
		}
		return info;
	}

	/**
	 * 交卷
	 * 
	 * @param testId
	 * @param partSeq
	 * @param status
	 * @return
	 */
	public PFResponse updatePaperInstance(long testId, int status) {
		logger.debug("updatePaperInstance  for testId {}, status {}", testId,
				status);
		PFResponse res = new PFResponse();
		CandidateTest test = candidateTestDao.getCandidateTest(testId);;
		if (test != null) {
			test.setTestState(status);
			test.setEndTime(new Timestamp(System.currentTimeMillis()));
			candidateTestDao.saveOrUpdate(test);
			//status :2:前台交卷，3：自动交卷，交卷时调用状态变更回调，modify by zengjie 14/8/18
			if(PaperInstancePartStateEnum.COMMIT_MANUAL.getValue()==status || PaperInstancePartStateEnum.COMMIT_AUTO.getValue()==status){
				//2:答题完毕
				outCallService.updateMrReportStatus(test, 2);
			}
			
		} else {
			logger.error(" not found CandidateTest for id {}", testId);
		}
		res.setCode(FuncBaseResponse.SUCCESS);
		return res;
	}

	public List<PaperMarkData> getPaperMarkDatas(long testId) {
		logger.debug("getPaperMarkDatas the testId {}", testId);
		CandidateTest exam = candidateTestDao.getCandidateTest(testId);;
		if (exam == null) {
			logger.error(" not found invitation for id {}", testId);
			return null;
		}
		String marks = exam.getQuestionMark();
		if (StringUtils.isNotEmpty(marks)) {
			Map<Integer, PaperMarkData> map = new HashMap<Integer, PaperMarkData>();
			String[] strArr = marks.split("\\|");
			for (String partSeqQId : strArr) {
				String[] partSeqQIds = partSeqQId.split("_");
				if (partSeqQIds.length != 2) {
					logger.error(
							"not right format data mark for testId {}, the str is {}",
							testId, partSeqQId);
					continue;
				}
				int partSeq = Integer.valueOf(partSeqQIds[0]);
				long questionId = Long.valueOf(partSeqQIds[1]);
				PaperMarkData data = map.get(partSeq);
				if (data == null) {
					data = new PaperMarkData();
					data.setPartSeq(partSeq);
					map.put(partSeq, data);
				}
				List<Long> quesIds = data.getQuestionIds();
				if (quesIds == null) {
					quesIds = new ArrayList<Long>();
					data.setQuestionIds(quesIds);
				}
				quesIds.add(questionId);
			}
			return new ArrayList<PaperMarkData>(map.values());
		}
		return new ArrayList<PaperMarkData>();
	}

	/**
	 * 跟踪答题到哪部分哪一题
	 * 
	 * @param testId
	 * @param partSeq
	 * @param qId
	 * @return
	 */
	public PFResponse updatePartSeqAndQuesitonId(long testId, int partSeq,
			long qId, int partIndex, int questionIndex) {
		logger.debug(
				"updatePartSeqAndQuesitonId the testId {}, the qId is {}, partSeq is "
						+ partSeq, testId, qId);
		CandidateTest exam = candidateTestDao.getCandidateTest(testId);;
		PFResponse res = new PFResponse();
		if (exam == null) {
			logger.error(" not found invitation for id {}", testId);
			res.setCode(FuncBaseResponse.FAILED);
			res.setMessage("not found Invitation for " + testId);
			return res;
		}
		exam.setPartSeq(partSeq + "");
		exam.setPartIndex(partIndex);
		if (qId == -1) {
			exam.setQuestionId(null);
			exam.setQuestionIndex(null);
		} else {
			exam.setQuestionId(qId);
			exam.setQuestionIndex(questionIndex);
		}
		if(exam.getBeginTime() == null){
			exam.setBeginTime(new Timestamp(System.currentTimeMillis()));
			logger.warn("the exam begin time is null for testId {} ", exam.getTestId());
		}
		candidateTestDao.saveOrUpdate(exam);
		res.setCode(FuncBaseResponse.SUCCESS);
		return res;
	}

	/**
	 * 更新答题时间
	 * 
	 * @param testId
	 * @param qId
	 * @param answerTime
	 * @return
	 */
	public PFResponse addTimeToPaperInstanceQuestion(long testId, long qId,
			int answerTime) {
		logger.debug(
				"addTimeToPaperInstanceQuestion the testId {}, the qId is {} for answerTime "
						+ answerTime, testId, qId);
		Invitation info = invitationDao.getEntity(testId);
		PFResponse res = new PFResponse();
		if (info == null) {
			logger.error(" not found invitation for id {}", testId);
			res.setCode(FuncBaseResponse.FAILED);
			res.setMessage("not found Invitation for " + testId);
			return res;
		}

		CandidateTestQuestionId id = new CandidateTestQuestionId(
				info.getTestId(), qId);
		CandidateTestQuestion ques = candidateTestQuestionDao.get(id);
		int oldTime = ques.getAnswerTime() == null ? 0 : ques.getAnswerTime();
		ques.setAnswerTime(oldTime + answerTime);
		candidateTestQuestionDao.saveOrUpdate(ques);
		res.setCode(FuncBaseResponse.SUCCESS);
		return res;
	}

	public QbQuestion getQuestionById(long qId) {
		logger.debug("getQuestionType the  the qId is {} ", qId);
		QbQuestion q = qbQuestionDao.getEntity(qId);
		if (q == null) {
			logger.error(" not found question for id {}", qId);
			return null;
		}
		return q;
	}

	/**
	 * 获取面试题剩余时间信息
	 * 
	 * @param testId
	 * @param qId
	 * @return
	 */
	@Override
	public QuestionExt getQuestionExt(long testId, long qId) {
		CandidateTestQuestion q = candidateTestQuestionDao.get(
				new CandidateTestQuestionId(testId, qId));
		QuestionExt ext = new QuestionExt();
		ext.setLeftTime(q.getSuggestTime());
		if (q.getBeginTime() != null) {
			logger.debug(
					"get  the question {} in paper {} is beginAnswered at "
							+ q.getBeginTime(), q.getId().getQuestionId(),
					testId);
			int leftSend = PaperCreateUtils.getBetweenTimeForSencond(
					q.getBeginTime(), q.getSuggestTime(), new Date());
			logger.debug("get  left second is {}, for questionId {}", leftSend,
					q.getId().getQuestionId());
			if (leftSend <= 0) {
				leftSend = 0;
			}
			ext.setLeftTime(leftSend);
		}
		return ext;
	}

	/**
	 * 更新试卷题目信息 (面试题)
	 * 
	 * @param testId
	 * @param partSeq
	 * @param qId
	 * @param type
	 *            1开始答题 2结束答题
	 * @return
	 */
	public PFResponse updatePaperInstanceQuestionInfo(long testId, int partSeq,
			long qId, int type) {
		logger.debug("updateInterviewInfo  for testId {}, qId {}", testId, qId);
		PFResponse res = new PFResponse();
//		Invitation info = invitationDao.getEntity(testId);
//		int paperInstId = info.getTestId();
		CandidateTestQuestionId id = new CandidateTestQuestionId(testId,
				qId);
		CandidateTestQuestion ques = candidateTestQuestionDao.get(id);
		if (type == 1) {
			logger.debug("begin to answer the question {} for testId {} ", qId,
					testId);
			if (ques.getBeginTime() == null) {
				ques.setBeginTime(new Timestamp(System.currentTimeMillis()));
			} else {
				logger.debug("the questionId " + qId
						+ " in paperInstId {} has begined at time {}",
						testId, ques.getBeginTime());
			}
		}
		if (type == 2) {
			logger.debug(" end to answer the question {} for testId {} ", qId,
					testId);
			ques.setEndTime(new Timestamp(System.currentTimeMillis()));
			if(ques.getPartSeq() == PaperPartSeqEnum.INTEVEIW.getValue()){//面试题提交答题时间时更新answerFlag
				ques.setAnswerFlag(1);
			}
		}
		candidateTestQuestionDao.saveOrUpdate(ques);
		return res;
	}

	@Override
	public PFResponse updateInterrupt(long testId) throws PFServiceException {
		PFResponse pfResponse = new PFResponse();
		try {
			long paperInstId = candidateTestDao.getPaperInstId(testId);
			CandidateTestPart pip = paperDao.getExamingPart(paperInstId);
			if (pip != null) {
				candidateTestDao.updateBreakTimes(testId);
				pfResponse.setCode(FuncBaseResponse.SUCCESS);
			} else
				pfResponse.setCode(FuncBaseResponse.FAILED);
			return pfResponse;
		} catch (Exception e) {
			throw new PFServiceException(e);
		}
	}

	/**
	 * 更新考试状态，用于跟踪到哪一个页面，用于页面还原
	 * 
	 * @param testId
	 * @param status
	 * @return
	 * @throws PFServiceException
	 */
	@Override
	public PFResponse updateCandidateExamStatus(long testId, int status) {
		logger.debug(
				"updateCandidateExamStatus the testId is {} , status is {}",
				testId, status);
		PFResponse pfResponse = new PFResponse();
		CandidateTest exam = candidateTestDao.getCandidateTest(testId);
		if (exam == null) {
			pfResponse.setCode(FuncBaseResponse.FAILED);
			pfResponse.setMessage("not found invitation for id " + testId);
			logger.error("not found invitation for id {}", testId);
			return null;
		}
		logger.debug("updateCandidateExamStatus for testId {} from status {} to status " + status, testId, exam.getSessionState());
		exam.setSessionState(status);
		candidateTestDao.saveOrUpdate(exam);
		pfResponse.setCode(FuncBaseResponse.SUCCESS);
		return pfResponse;
	}

	/**
	 * 根据邀请id获取CandidateExam
	 * 
	 * @param testId
	 * @return
	 */
	@Override
	public CandidateTest getCandidateExamByInviId(long testId) {
		CandidateTest exam = candidateTestDao.getCandidateTest(testId);
		if (exam == null) {
			logger.error("not found invitation for id {}", testId);
			return null;
		}
		return exam;
	}

	/**
	 * 开始考试
	 * 
	 * @param testId
	 * @return
	 */
	public PFResponse startExamPaper(long testId) {
		logger.debug("startExamPaper for testId {} ", testId);
		PFResponse res = new PFResponse();
		CandidateTest info =  candidateTestDao.getCandidateTest(testId);
		if (info == null) {
			logger.error("not found CandidateTest for id {}", testId);
			res.setCode(FuncBaseResponse.FAILED);
			res.setMessage("not found Invitation for " + testId);
			return res;
		}
		if (info.getBeginTime() == null) {
			info.setBeginTime(new Timestamp(System.currentTimeMillis()));
		} else {
			logger.debug("the paper  has start at time {} for testId {}",
					info.getBeginTime(), testId);
		}
		info.setTestState(1);// 设置开始考试状态
		candidateTestDao.saveOrUpdate(info);
		//智库发起的测评需要当状态更改时通知,modify by zengjie 14/8/18
		//status:1答题中
		outCallService.updateMrReportStatus(info, 1);
		res.setCode(FuncBaseResponse.SUCCESS);
		return res;
	}

	public MonitorInfo getMonitorInfo(long testId) throws PFServiceException {
		try {
			MonitorInfo monitorInfo = new MonitorInfo();
			monitorInfo.setConfigSwitchTimes(Integer.parseInt(configSysParamDao
					.getConfigParamValue(ConfigSysParam.ALLOWEDSWITCHTIMES)));
			CandidateTest candidateTest = candidateTestDao.getEntity(testId);
			monitorInfo.setFreshTimes(candidateTest.getFreshTimes());
			monitorInfo.setSwitchTimes(candidateTest.getSwitchTimes());
			return monitorInfo;
		} catch (Exception e) {
			throw new PFServiceException(e);
		}
	}

	/**
	 * 应聘者调用生成试卷实例
	 * 
	 * @param testId
	 * @param inviteCode
	 * @return
	 */
	public PFResponse createPaper(final long testId, final String inviteCode)
			throws PFServiceException {
		PFResponse pfResponse = new PFResponse();
		pfResponse.setCode(FuncBaseResponse.SUCCESS);
		logger.debug("thread create paper start for testId {}", testId);
		try {
			updateCandidateTestPaperState(testId, 1);// 正在生成
			Invitation invitaion = invitationDao.getEntity(testId);
			paperServiceImpl.createPaperInstance(invitaion.getPositionId(),
					testId, inviteCode);
			logger.debug("thread create paper end for testId {}", testId);
			updateCandidateTestPaperState(testId, 2);// 生成成功
		} catch (Exception e) {
			logger.error("error create paperInstance ", e);
			throw new PFServiceException(e);
		}
		return pfResponse;
	}

	/**
	 * 更新生成试卷状态
	 * 
	 * @return
	 */
	public PFResponse updateCandidateTestPaperState(long testId, int paperState) {
		logger.debug("update paperstate testId {}, paperState {} ", testId,
				paperState);
		PFResponse pfResponse = new PFResponse();
		pfResponse.setCode(FuncBaseResponse.SUCCESS);
		CandidateTest candidateTest = candidateTestDao.getCandidateTest(testId);
		candidateTest.setPaperState(paperState);
		candidateTestDao.saveOrUpdate(candidateTest);
		return pfResponse;
	}

	/**
	 * 获取生成试卷状态
	 * 
	 * @param testId
	 * @return
	 */
	@Override
	public PaperCreateResult getCandidateTestPaperState(long testId) {
		logger.debug("getCandidateTestPaperState for testId {} ", testId);
		CandidateTest candidateTest = candidateTestDao.getCandidateTest(testId);
		PaperCreateResult res = new PaperCreateResult();
		res.setCode(candidateTest.getPaperState() + "");
		logger.debug("getCandidateTestPaperState for testId {}, state {}  ",
				testId, candidateTest.getPaperState());
		if(candidateTest.getPaperState() == 2){
			res.setTimerType(candidateTestPartDao.getCandidateTestTimerType(testId));
		}
		return res;
	}

	@Override
	public PaperData getTestPaperData(long testId, String passCode) {
		logger.debug("getTestPaperData for testId {}, passCode {}  ", testId,
				passCode);
		try {
			Invitation info = invitationDao.getEntity(testId);
			if (info == null) {
				logger.error("not found testId for id {}", testId);
				return null;
			}
			List<PaperPartData> partDatas = new ArrayList<PaperPartData>();
			List<CandidateTestPart> partInfos = candidateTestPartDao.getAllCandidateTestPartByTestId(testId);/*qbNoramlDaoImpl.getNoramlListByHql(
					"from CandidateTestPart where id.testId = " + testId,
					CandidateTestPart.class)*/;
			Map<Integer, PaperPartData> data = changeListToMap(partInfos);
			GetQidsResponse resp = gradeService.getQids(testId);
	
			List<PaperMarkData> marks = getPaperMarkDatas(testId);
	
			PaperPartData part = data.get(PaperPartSeqEnum.TEST_OBJECT.getValue());
			if (part != null) {
				List<PaperQuestionInfo> list = new ArrayList<PaperQuestionInfo>();
				PaperQuestionInfo qInfo = new PaperQuestionInfo();
				List<CandidateTestQuestion> sampleChoice = candidateTestQuestionDao.getListByPart(testId, PaperPartSeqEnum.TEST_OBJECT.getValue());
				qInfo.setQuestionId(sampleChoice.get(0).getId().getQuestionId());
				list.add(qInfo);
				part.setQuestionIds(list);
				partDatas.add(part);
			}
	
			part = data.get(PaperPartSeqEnum.TEST_SUBJECT.getValue());
			if (part != null) {
				List<PaperQuestionInfo> list = new ArrayList<PaperQuestionInfo>();
				PaperQuestionInfo qInfo = new PaperQuestionInfo();
				List<CandidateTestQuestion> sampleSubject = candidateTestQuestionDao.getListByPart(testId, PaperPartSeqEnum.TEST_SUBJECT.getValue());
				qInfo.setQuestionId(sampleSubject.get(0).getId().getQuestionId());
				list.add(qInfo);
				part.setQuestionIds(list);
				partDatas.add(part);
			}
	
			part = data.get(PaperPartSeqEnum.TEST_INTERVIEW.getValue());
			if (part != null) {
				List<PaperQuestionInfo> list = new ArrayList<PaperQuestionInfo>();
				List<CandidateTestQuestion> questions = candidateTestQuestionDao.getListByPart(testId,  PaperPartSeqEnum.TEST_INTERVIEW.getValue());
				for (CandidateTestQuestion q : questions) {
					PaperQuestionInfo qInfo = new PaperQuestionInfo();
					qInfo.setQuestionId(q.getId().getQuestionId());
					list.add(qInfo);
				}
				part.setQuestionIds(list);
				partDatas.add(part);
			}
	
			PaperData paperData = new PaperData();
			
			// add by Lipan 考试约需时长。。。。。。。 2014年09月20日15:46:17
            CandidateTest candidateTest = candidateTestDao.getEntity(testId);
            Double avgTime = paperDao.getPaperTotalTime(candidateTest.getPaperId());
            avgTime = Math.ceil(avgTime/60);
            paperData.setTotalTime(avgTime.intValue());
			
			paperData.setPartDatas(partDatas);
			return paperData;
		} catch (Exception e) {
			logger.error("error get test paper part data ", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public PFResponseData<CandReportAndInfo> gradeAndGetRanking(long testId) throws Exception {
		PFResponseData<CandReportAndInfo> report = new PFResponseData<CandReportAndInfo>();
		GetReportSummaryResponse res = gradeService.gradeReport(testId);
		if(res.getErrorCode() == 0){
			report.setCode(FuncBaseResponse.SUCCESS);
			CandReportAndInfo data = new CandReportAndInfo();
			data.setTestId(testId);
			data.setGetScore(res.getScore());
			CandidateTest test = candidateTestDao.getEntity(testId);
			List<CandidateReport> reports = reportDao.getCandidateReportsLargerThanScore(res.getScore(), test.getPositionId());
			data.setRanking(reports.size() + 1);
			report.setData(data);
		}else{
			logger.warn("grade report error for testId {} ", testId);
			report.setCode(FuncBaseResponse.FAILED);
		}
		return report;
	}
}
