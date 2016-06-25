package com.ailk.sets.platform.service.local;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.dao.intf.ICandidateTestPartDao;
import com.ailk.sets.grade.dao.intf.ICandidateTestQuestionDao;
import com.ailk.sets.platform.dao.impl.QbNormalDaoImpl;
import com.ailk.sets.platform.dao.interfaces.ICandidateTestDao;
import com.ailk.sets.platform.dao.interfaces.IInvitationDao;
import com.ailk.sets.platform.domain.InvitationStateEnum;
import com.ailk.sets.platform.domain.paper.CandidateTestPart;
import com.ailk.sets.platform.domain.paper.CandidateTestPartId;
import com.ailk.sets.platform.domain.paper.CandidateTestQuestion;
import com.ailk.sets.platform.intf.cand.domain.Invitation;
import com.ailk.sets.platform.intf.cand.service.IExamineService;
import com.ailk.sets.platform.intf.common.PaperInstancePartStateEnum;
import com.ailk.sets.platform.intf.common.PaperPartSeqEnum;
import com.ailk.sets.platform.intf.common.PaperPartTimerType;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;
import com.ailk.sets.platform.util.PaperCreateUtils;

@Transactional(rollbackFor = Exception.class)
@Service
public class PaperHanderServiceImpl implements IPaperHanderService {

	@Autowired
	private QbNormalDaoImpl normalDaoImpl;

	@Autowired
	private IExamineService examineService;
	
	@Autowired
	private ICandidateTestPartDao candidateTestPartDao;
	
	@Autowired
	private ICandidateTestDao candidateTestDao;
	@Autowired
	private ICandidateTestQuestionDao candidateTestQuestionDao;
	@Autowired
	private IInvitationDao inviteDaoImp;

	private Logger logger = LoggerFactory.getLogger(PaperHanderServiceImpl.class);

	@Override
	public List<CandidateTestPartId> getNeededHandInCandidateTestPart() {
		/*List<CandidateTestPart> list = normalDaoImpl
				.getNoramlListBySqlWithJvmDate(
						// 延迟5分钟提交 300秒 只取校招的，校招按部分计时
						"select * from candidate_test_part"
								+ " where  test_id in (select  test_id  from candidate_test where position_id in  ( select position_id   from position  where test_type = 2)) and "
								+ " DATE_ADD(begin_time,INTERVAL suggest_time+300 SECOND) < ? and part_state =  "
								+  PaperInstancePartStateEnum.ANSWERING.getValue(), CandidateTestPart.class,
						 Arrays.asList(new Date()));*/
		
		List<CandidateTestPart> list = normalDaoImpl
				.getNoramlListBySqlWithJvmDate(
						// 延迟1800分钟提交  按部分计时
						"select * from candidate_test_part"
								+ " where timer_type = " + PaperPartTimerType.PART.getValue() + " and "
								+ " DATE_ADD(begin_time,INTERVAL suggest_time+1800 SECOND) < ? and part_state =  "
								+  PaperInstancePartStateEnum.ANSWERING.getValue(), CandidateTestPart.class,
						 Arrays.asList(new Date()));
		List<CandidateTestPartId> result = new ArrayList<CandidateTestPartId>();
		for (CandidateTestPart part : list) {
			result.add(new CandidateTestPartId(part.getId().getTestId(), part.getId().getPartSeq()));
		}
		return result;
	}

	@Override
	public void processCandidateTestPart(CandidateTestPartId id) {
		CandidateTestPart currentPart =candidateTestPartDao.getEntity(id);
		if (currentPart.getTimerType() == PaperPartTimerType.PART.getValue()) {//校招按部分计时
			logger.info("update paper_instatnce_part to auto commit , paperInstId is {}, partSeq is {}", currentPart
					.getId().getTestId(), currentPart.getId().getPartSeq());
			currentPart.setPartState(PaperInstancePartStateEnum.COMMIT_AUTO.getValue());// 设置自动提交
			currentPart.setEndTime(new Date());
			CandidateTest candidateTest = candidateTestDao.getCandidateTest(currentPart.getId().getTestId());
			List<CandidateTestPart> instanceParts = candidateTestPartDao.getAllCandidateTestPartByTestId(candidateTest.getTestId());
			PaperCreateUtils.sortPaperInstancePartByPartSeq(instanceParts);
			if (instanceParts.size() > 0) {
				CandidateTestPart last = instanceParts.get(instanceParts.size() - 1);
				if (last.getId().getPartSeq() == currentPart.getId().getPartSeq()) {
					Invitation in = inviteDaoImp.getEntity(candidateTest.getTestId());
					logger.info("auto finished Invitation for id {} , because {} part is the last paperInstancePart",
							in.getTestId(), last.getId().getPartSeq());
					examineService.updatePaperInstance(in.getTestId(), InvitationStateEnum.AUTO_FINISHED.getValue());
				}

			}
		}
		/*if (currentPart.getId().getPartSeq() == PaperPartSeqEnum.INTEVEIW.getValue()
				|| currentPart.getId().getPartSeq() == PaperPartSeqEnum.ESSAY.getValue()) {//校招按部分计时
			// logger.debug("enter interview or extra part for paperInstId {}",
			// currentPart.getId().getPaperInstId());
			List<CandidateTestQuestion> questions = normalDaoImpl
					.getNoramlListBySqlWithJvmDate(
							// 延迟一分钟
							"select * from candidate_test_question where DATE_ADD(begin_time,INTERVAL suggest_time+60 SECOND) < ? and begin_time is not null  and end_time is null and part_seq in( "
									+ PaperPartSeqEnum.INTEVEIW.getValue()
									+ ","
									+ PaperPartSeqEnum.EXTRA.getValue()
									+ ","
									+ PaperPartSeqEnum.ESSAY.getValue()
									+ ")"
									+ " and test_id = "
									+ currentPart.getId().getTestId()
									+ " order by question_seq asc", CandidateTestQuestion.class,
							Arrays.asList(new Date()));
			for (CandidateTestQuestion q : questions) {
				logger.info("need to end the questionId {} in testId {}", q.getId().getQuestionId(), q.getId()
						.getTestId());
				q.setEndTime(new Timestamp(System.currentTimeMillis()));
				candidateTestQuestionDao.saveOrUpdate(q);
			}
		}*/
		candidateTestPartDao.saveOrUpdate(currentPart);
	}

	public List<Long> getDataOutTest() {
		List<Long> testIds = new ArrayList<Long>();
		List<Invitation> invitations = inviteDaoImp.getInvitationOfAnswning();// 正在答题的职位
		if (invitations.size() > 0) {
			for (Invitation in : invitations) {
				Date expDate = in.getExpDate();
				Calendar e = Calendar.getInstance();
				e.setTime(expDate);
				e.set(Calendar.MINUTE, e.get(Calendar.MINUTE) + 2); //延迟两分钟没有交卷的
				if (e.getTime().before(new Date())) {
					testIds.add(in.getTestId());
				}
			}
		}
		return testIds;
	}

	public void processDateOutTest(long testId) {
		logger.info("begin to auto commit the invitation {} because it is timeout ", testId);
		examineService.updatePaperInstance(testId, InvitationStateEnum.AUTO_FINISHED.getValue());
		List<CandidateTestPart> parts = candidateTestPartDao.getAllCandidateTestPartByTestId(testId);
		for(CandidateTestPart part : parts){
			if(part.getPartState().intValue() ==  PaperInstancePartStateEnum.ANSWERING.getValue()){
				logger.debug("auto create part for testId {} , partId {} ", testId, part.getId().getPartSeq());
				part.setPartState(PaperInstancePartStateEnum.COMMIT_AUTO.getValue());// 设置自动提交
				part.setEndTime(new Date());
				candidateTestPartDao.saveOrUpdate(part);
			}
		}
	}
}
