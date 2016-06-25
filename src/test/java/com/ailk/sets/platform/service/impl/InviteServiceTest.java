package com.ailk.sets.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ailk.sets.platform.intf.cand.domain.Invitation;
import com.ailk.sets.platform.intf.cand.domain.InvitationForMulti;
import com.ailk.sets.platform.intf.cand.domain.InvitationTimeInfo;
import com.ailk.sets.platform.intf.domain.InvitationOutCandidate;
import com.ailk.sets.platform.intf.domain.InvitationOutEmployer;
import com.ailk.sets.platform.intf.domain.InvitationOutInfo;
import com.ailk.sets.platform.intf.empl.domain.InvitationMail;
import com.ailk.sets.platform.intf.empl.domain.InvitationMailForMultiPos;
import com.ailk.sets.platform.intf.empl.service.IInvite;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.intf.model.invatition.InvitationInfo;
import com.ailk.sets.platform.intf.model.invatition.InviteResult;
import com.ailk.sets.platform.intf.model.invatition.OnlineExamReqResult;
import com.ailk.sets.platform.intf.model.param.GetInvitationInfoParam;

public class InviteServiceTest {
    static ApplicationContext context;
    static IInvite inviteService ;
    static{
        PropertyConfigurator.configure(InviteServiceTest.class.getResource("/log4j.properties"));
        context = new ClassPathXmlApplicationContext(new String[] { "/spring/localbean.xml","/spring/beans.xml" });
        inviteService = (IInvite)context.getBean("invite");
    }
    
	/**
	 * 发送邮件邀请
	 * @throws PFServiceException
	 */
	@Test
	public void testInvite() throws PFServiceException
	{
        Invitation invitation = new Invitation();
        invitation.setCandidateEmail("panyl@asiainfo.com");
        invitation.setCandidateName("潘永雷332");
        invitation.setEmployerId(100000114);
        invitation.setPositionId(1441);
       /* invitation.setValidDate("2014-07-28");
//        invitation.setBeginDate("2014-07-28 11:25");
        invitation.setSelfContext("sdfds注意的范德萨发第三方的撒范德萨");
        invitation.setCanWithOutCamera(1);
        invitation.setTestPositionName("校招测评1");*/
        inviteService.invite(invitation);
	}
	
	/**
	 * 发送邮件邀请
	 * @throws PFServiceException
	 */
	@Test
	public void testInviteMulti() throws PFServiceException
	{
		List<Integer> positionIds = new ArrayList();
		positionIds.add(989);
		positionIds.add(990);
        InvitationForMulti invitation = new InvitationForMulti();
        invitation.setCandidateEmail("lipan3@asiainfo.com");
        invitation.setCandidateName("李攀3");
        invitation.setEmployerId(100000120);
//        invitation.setMultiPositionIds(positionIds);
        
        List<Integer> ids1 = new ArrayList<Integer>();
        ids1.add(1373);
        ids1.add(1369);
        List<Integer> ids2 = new ArrayList<Integer>();
        ids2.add(1367);
        ids2.add(1366);
        List<Integer> ids3 = new ArrayList<Integer>();
        ids3.add(1365);
        ids3.add(1364);
        
        invitation.setMustAnswerPositionIds(ids1);
        invitation.setOptionalAnswerPositionIds(ids2);
        invitation.setAddOnPositionIds(ids3);
        invitation.setValidDate("2014-10-28");
        invitation.setBeginDate("2014-01-28 11:25");
        invitation.setSelfContext("sdfds注意的范德萨发第三方的撒范德萨");
        invitation.setCanWithOutCamera(1);
        invitation.setTestPositionName("校招测评1");
        Object o = inviteService.inviteForMulti(invitation);
        System.out.println(o);
	}
	
	@Test
	public void getInvitationTimeInfo(){
		InvitationTimeInfo info = inviteService.getInvitationTimeInfo(100000000002372L);
		System.out.println(info);
	}
	/**
	 * 重发发送邮件邀请
	 * @throws PFServiceException
	 */
	@Test
	public void testReInvite() throws PFServiceException
	{
	    Invitation invitation = new Invitation();
	    invitation.setEmployerId(100000069);
	    invitation.setPositionId(1162);
	    invitation.setTestId(2098L);
//	    invitation.setValidDate("2014/8/1");
//	    invitation.setCanWithOutCamera(1);
//	    invitation.setTestPositionName("你妹！！！");
	    inviteService.reInvite(invitation,true);
	}
	
	/**
	 * 查询邮件邀请信息
	 * @throws PFServiceException
	 */
	@Test
	public void getMailInfo() throws PFServiceException
	{
	    InvitationMail mailContent = inviteService.getMailContent(100000114, 1441);
	    System.out.println(mailContent.toString());
	}
	
	
	@Test
	public void getMailInfos() throws PFServiceException
	{
		List<Integer> positionIds = new ArrayList();
		positionIds.add(1142);
		positionIds.add(1149);
		InvitationMailForMultiPos mailContent = inviteService.getMailContents(100000067, positionIds);
	    System.out.println(mailContent.toString());
	}
	
	/**
	 * 测试
	 */
	@Test
	public void certify()
	{
	    try
        {
            inviteService.certify(2079, "DiFfLmVvrE");
        } catch (PFServiceException e)
        {
            e.printStackTrace();
        }
	}
	
	@Test
	public void inviteByOutInvitation() throws Exception{
		InvitationOutInfo outInfo = new InvitationOutInfo();
		outInfo.setEmployerId(100000037);
		outInfo.setRealPositionName("test123");
		outInfo.setTestWithoutCamera(1);
		
		List<InvitationOutCandidate> candidateList = new ArrayList<InvitationOutCandidate>();
		InvitationOutCandidate candidate = new InvitationOutCandidate();
		candidate.setEmail("panyl@asiainfo.com");
		candidate.setName("panyl");
		candidate.setId(1);
		candidateList.add(candidate);
		InvitationOutCandidate candidate2 = new InvitationOutCandidate();
		candidate2.setEmail("2469600569@qq.com");
		candidate2.setName("panyl2");
		candidate2.setId(2);
		candidateList.add(candidate2);
		outInfo.setCandidateList(candidateList);

		List<InvitationOutEmployer> employers = new ArrayList<InvitationOutEmployer>();
		InvitationOutEmployer outEmployer = new InvitationOutEmployer();
		outEmployer.setEmail("panyl@asiainfo.com");
		outEmployer.setName("潘永雷");
		employers.add(outEmployer);
		InvitationOutEmployer outEmployer2 = new InvitationOutEmployer();
		outEmployer2.setEmail("2469600569@asiainfo.com");
		outEmployer2.setName("潘永雷22");
		employers.add(outEmployer2);
		
		
		outInfo.setEmployers(employers);
//		outInfo.setEmailtitle(emailtitle);
		outInfo.setEmailContent("hhhhhhhhhhhhhhh");
		
		List<InviteResult>  results = inviteService.inviteByOutInvitation(1,1337, 2, outInfo);
	    System.out.println(results);
	}
	
	@Test
	public void inviteBySampleInvitation() throws PFServiceException
	{
        Invitation invitation = new Invitation();
        invitation.setCandidateEmail("panyl@asiainfo.com");
        invitation.setCandidateName("panyl");
        invitation.setEmployerEmail("2469600569@qq.com");
        invitation.setPositionId(1245);
//        invitation.setValidDate("2014/07/28");
        invitation.setCanWithOutCamera(1);
        invitation.setTestPositionName("校招测评1");
        InviteResult res = inviteService.inviteBySampleInvitation(invitation);
        System.out.println(res);
	}
	
	   /**
     * 发送邮件邀请
     * @throws PFServiceException
     */
    @Test
    public void testOnlineExamReq() throws PFServiceException
    {
        Invitation invitation = new Invitation();
        invitation.setCandidateEmail("lipan3@asiainfo.com");
        invitation.setCandidateName("李攀");
        invitation.setPositionId(1367);
        OnlineExamReqResult result = inviteService.onlineExamReqInvite(invitation);
        System.out.println(result.getStatus());
    }
    @Test
    public void  getFailedInvitations() throws Exception{
    	GetInvitationInfoParam param = new GetInvitationInfoParam();
    	
    	Page page = new Page();
    	param.setPage(page);
    	param.setEmployerId(100000114);
        param.setPositionId(1441);
        List<InvitationInfo> list = inviteService.getFailedInvitations(param);
        System.out.println(list.size());
    }
	
}
