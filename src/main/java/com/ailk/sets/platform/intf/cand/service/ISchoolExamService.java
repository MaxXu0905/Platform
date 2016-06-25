package com.ailk.sets.platform.intf.cand.service;

import java.util.List;

import com.ailk.sets.platform.intf.cand.domain.Invitation;
import com.ailk.sets.platform.intf.cand.domain.SchoolExamCondition;
import com.ailk.sets.platform.intf.cand.domain.SchoolPaperData;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.candidateTest.CandidateInfo;
import com.ailk.sets.platform.intf.model.invatition.SchoolInvitationInfo;

public interface ISchoolExamService {
	public SchoolPaperData generatePaper(SchoolExamCondition condition) throws PFServiceException ,Exception;
	public SchoolPaperData getPaperByInvitationId(long testId) throws PFServiceException ,Exception;
	
//	public CandidateInfo getInvitationInfo(String weixinId,int activityId) throws Exception;
	/**
	 * 获取邀请信息
	 * @param weixinId
	 * @param activityId
	 * @param childPositionId  子测评id  组测评对应的activityId需要传递子测评id才能查询
	 * @return
	 * @throws Exception
	 */
	public Invitation getOneInvitation(String weixinId, int activityId,Integer childPositionId) throws Exception;
	
	public SchoolInvitationInfo getSchoolInvitationInfo(SchoolExamCondition condition) throws Exception;
	/**
	 * 获取某个人对某个测评(组)的考试信息
	 * @param candidateEmail
	 * @param candidateName
	 * @param positionEntry
	 * @return
	 */
	public Position getPositionExamInfos(String candidateEmail,String candidateName, String positionEntry)  throws Exception;
}
