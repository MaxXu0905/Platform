package com.ailk.sets.platform.intf.empl.service;

import java.util.List;

import com.ailk.sets.platform.intf.cand.domain.Invitation;
import com.ailk.sets.platform.intf.cand.domain.InvitationForMulti;
import com.ailk.sets.platform.intf.cand.domain.InvitationTimeInfo;
import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.domain.InvitationOutInfo;
import com.ailk.sets.platform.intf.empl.domain.InvitationMail;
import com.ailk.sets.platform.intf.empl.domain.InvitationMailForMultiPos;
import com.ailk.sets.platform.intf.empl.domain.OutInvitationRequest;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.intf.model.candidateTest.CandidateInfo;
import com.ailk.sets.platform.intf.model.invatition.InvitationInfo;
import com.ailk.sets.platform.intf.model.invatition.InvitationValidInfo;
import com.ailk.sets.platform.intf.model.invatition.InviteResult;
import com.ailk.sets.platform.intf.model.invatition.OnlineExamReqResult;
import com.ailk.sets.platform.intf.model.param.GetInvitationInfoParam;
import com.ailk.sets.platform.intf.model.position.PositionStatistics;

/**
 * 邀请服务
 * 
 * @author 毕希研
 * 
 */
public interface IInvite {

	/**
	 * 发送邀请邮件并生成试卷服务
	 * 
	 * @param employerId
	 * @param positionId
	 * @param list
	 * @throws PFServiceException
	 */
	public InviteResult invite(Invitation invitation) throws PFServiceException;

	
	/**
	 * 根据姓名和邮箱删除失败的邀请
	 * @param invitation
	 * @return
	 * @throws PFServiceException
	 */
	public PFResponse deteleByCandidate(Invitation invitation) ;
	
	/**
	 * 发送邀请邮件并生成试卷服务 多个测评
	 * 
	 * @param employerId
	 * @param positionId
	 * @param list
	 * @throws PFServiceException
	 */
	public InviteResult inviteForMulti(InvitationForMulti invitationMulti) throws PFServiceException;
	/**
	 * 获取邮件的标题以及内容    多个测评Id
	 * 
	 * @param employerId
	 * @param positionId
	 * @return
	 * @throws PFServiceException
	 */
	public InvitationMailForMultiPos getMailContents(int employerId, List<Integer> positionIds) throws PFServiceException;
	
	
	/**
	 * 获取邮件的标题以及内容
	 * 
	 * @param employerId
	 * @param positionId
	 * @return
	 * @throws PFServiceException
	 */
	public InvitationMail getMailContent(int employerId, int positionId) throws PFServiceException;

	/**
	 * 验证邀请和通行证
	 * 
	 * @param testId
	 * @param passport
	 * @return
	 * @throws PFServiceException
	 */
	public CandidateInfo certify(long testId, String passport) throws PFServiceException;

	/**
	 * 验证session有效性
	 * 
	 * @param testId
	 * @param passport
	 * @param ticket
	 * @return
	 * @throws PFServiceException
	 */
	public PFResponse certify(long testId, String passport, String ticket) throws PFServiceException;

	/**
	 * 验证访问有效性
	 * 
	 * @param state
	 * @return
	 */
	public PFResponse certifyRequest(long testId, int state) throws PFServiceException;

	/**
	 * 获取邀请失效的列表
	 * 
	 * @param positionId
	 * @param page
	 * @return
	 * @throws PFServiceException
	 */
	public List<InvitationInfo> getInvitationInfo(GetInvitationInfoParam param) throws PFServiceException;

	/**
	 * 重新邀请
	 * 
	 * @param employerId
	 * @param positionId
	 * @param ids
	 * @return
	 * @throws PFServiceException
	 */
	public PositionStatistics reInvite(Invitation invitation, boolean withStatistics) throws PFServiceException;

	/**
	 * 删除失败的邀请
	 * 
	 * @param employerId
	 * @param positionId
	 * @param ids
	 * @return
	 * @throws PFServiceException
	 */
	public PositionStatistics delFailedInvitation(int employerId, int positionId, List<Integer> ids) throws PFServiceException;

	/**
	 * 获取已邀请列表
	 * 
	 * @param positionId
	 * @param page
	 * @return
	 * @throws PFServiceException
	 */
	public List<InvitationValidInfo> getInvitationValidInfo(int employerId, int positionId, Page page) throws PFServiceException;

	/**
	 * 验证邀请和通行证
	 * 
	 * @param testId
	 * @param passport
	 * @return
	 * @throws PFServiceException
	 */
	public CandidateInfo certifyWithWeixin(String openId ,long testId, String passport, String weixinNo) throws PFServiceException;

	/**
	 * 获取职位的全部邀请数(除失败的邀请)
	 * 
	 * @param positionId
	 * @return
	 * @throws PFServiceException
	 */
	public long getInvitationNum(int employerId, int positionId) throws PFServiceException;
	
	/**
	 * 第三方发送邀请接口
	 * @param outInfo
	 * @return
	 */
	public List<InviteResult> inviteByOutInvitation(int type,int positionId,int invalidDays,InvitationOutInfo outInfo) throws PFServiceException;

	/**
	 * 发送样例邀请
	 * @param invitation
	 * @return
	 * @throws PFServiceException
	 */
	public InviteResult inviteBySampleInvitation(Invitation invitation) throws PFServiceException;
	
	/**
	 * 获取邀请的生效时间  失效时间信息  
	 * @param testId
	 * @return
	 */
	public InvitationTimeInfo getInvitationTimeInfo(long testId);


    /**
     * 在线申测生成邀请记录
     * @param invitation
     * @return
     * @throws PFServiceException
     */
    public OnlineExamReqResult onlineExamReqInvite(Invitation invitation) throws PFServiceException;
    
    /**
     * 获取失败的邀请
     * @param employerId
     * @param positionId
     * @return
     */
	public List<InvitationInfo> getFailedInvitations(GetInvitationInfoParam param)  throws PFServiceException;
	
	/**
	 * 外部请求考试
	 * @param invitationReq
	 * @return
	 */
	public InviteResult inviteForNoMailOfOut(OutInvitationRequest invitationReq)  throws PFServiceException;

}
