package com.ailk.sets.platform.service.local.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.dao.interfaces.ICandidateTestDao;
import com.ailk.sets.platform.intf.cand.domain.Invitation;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;
import com.ailk.sets.platform.intf.model.invatition.InvitationState;
import com.ailk.sets.platform.service.local.IInvitationStateService;
@Transactional(rollbackFor = Exception.class)
@Service
public class InvitationStateServiceImpl implements IInvitationStateService {
	private Logger logger = LoggerFactory.getLogger(InvitationStateServiceImpl.class);
	@Autowired
	private ICandidateTestDao candidateTestDaoImpl;
	public int getInvitationState(Invitation invitation){
		logger.debug("getInvitationState the test {}  ", invitation.getTestId());
		CandidateTest candidateTest = candidateTestDaoImpl.getCandidateTest(invitation.getTestId());
		Integer inviState = invitation.getInvitationState();
		Integer testState = candidateTest.getTestState();
		logger.debug("candidateTest is {} ,testState is {} ", candidateTest.getTestId(),candidateTest.getTestState());
        logger.debug("invitationState is {} for testId {} ", inviState,invitation.getTestId());
//		if(inviState == InvitationState.INVITATION_STATE_NORMAL){
			if(candidateTest == null || candidateTest.getBeginTime() == null){
				if(invitation.getExpDate().before(new Date())){
					return InvitationState.EXPIRED;//已过期
				}
				return  InvitationState.NOT_ANSWERED;//未答题
			}
			if(testState == 1  && candidateTest.getBeginTime() != null){
				return InvitationState.ANSWERING;//正在答题
			}
//		}
        if(testState == InvitationState.INVITATION_STATE_FINISHED1 || testState == InvitationState.INVITATION_STATE_FINISHED2){
			return InvitationState.ANSWER_FINISHED;//答题完毕
		}
		if(testState == InvitationState.INVITATION_STATE_REPORT1 || testState == InvitationState.INVITATION_STATE_REPORT2){
			  if(candidateTest != null){
				  if(candidateTest.getTestResult() == InvitationState.CANDIDATE_TEST_RESULT0){
					  return InvitationState.WAIT_PROCESS;//待处理
				  }
				if (candidateTest.getTestResult() == InvitationState.CANDIDATE_TEST_RESULT1
						|| candidateTest.getTestResult() == InvitationState.CANDIDATE_TEST_RESULT3) {////0726 版本，因为界面改动大，所以临时将复试状态加入到已推荐
					  return InvitationState.RECOMMEND;//已推荐
				  }
				  if(candidateTest.getTestResult() == InvitationState.CANDIDATE_TEST_RESULT2){
					  return InvitationState.WEED_OUT;//已淘汰
				  }
			  }
		}
	  logger.error("not known state for testId {}", invitation.getTestId());
	  return InvitationState.NOT_ANSWERED;//未答题
	}
}
