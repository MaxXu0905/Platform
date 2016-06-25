package com.ailk.sets.platform.service;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ailk.sets.platform.common.ConfigSysParam;
import com.ailk.sets.platform.common.MailSenderInfo;
import com.ailk.sets.platform.dao.interfaces.ICandidateDao;
import com.ailk.sets.platform.dao.interfaces.ICandidateInfoDao;
import com.ailk.sets.platform.dao.interfaces.ICandidateTestDao;
import com.ailk.sets.platform.dao.interfaces.ICompanyDao;
import com.ailk.sets.platform.dao.interfaces.ICompanyEmailserverDao;
import com.ailk.sets.platform.dao.interfaces.IConfigDao;
import com.ailk.sets.platform.dao.interfaces.IConfigSysParamDao;
import com.ailk.sets.platform.dao.interfaces.IEmployerDao;
import com.ailk.sets.platform.dao.interfaces.IInvitationCopytoDao;
import com.ailk.sets.platform.dao.interfaces.IInviteDao;
import com.ailk.sets.platform.dao.interfaces.IOnlineExamReqDao;
import com.ailk.sets.platform.dao.interfaces.IPaperDao;
import com.ailk.sets.platform.dao.interfaces.IPositionDao;
import com.ailk.sets.platform.dao.interfaces.IPositionRelationDao;
import com.ailk.sets.platform.dao.interfaces.IPositionSeriesDao;
import com.ailk.sets.platform.domain.ConfigSysParameters;
import com.ailk.sets.platform.domain.InvationEntity;
import com.ailk.sets.platform.domain.InvationEntityMulti;
import com.ailk.sets.platform.domain.PositionInfoExt;
import com.ailk.sets.platform.domain.PositionLog;
import com.ailk.sets.platform.domain.PositionRelation;
import com.ailk.sets.platform.domain.paper.PaperPart;
import com.ailk.sets.platform.exception.PFDaoException;
import com.ailk.sets.platform.intf.cand.domain.Employer;
import com.ailk.sets.platform.intf.cand.domain.Invitation;
import com.ailk.sets.platform.intf.cand.domain.InvitationForMulti;
import com.ailk.sets.platform.intf.cand.domain.InvitationTimeInfo;
import com.ailk.sets.platform.intf.common.ConfigCodeType;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.common.FuncBaseResponse;
import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.common.PaperPartSeqEnum;
import com.ailk.sets.platform.intf.domain.Candidate;
import com.ailk.sets.platform.intf.domain.Company;
import com.ailk.sets.platform.intf.domain.InvitationCopyto;
import com.ailk.sets.platform.intf.domain.InvitationCopytoId;
import com.ailk.sets.platform.intf.domain.InvitationOutCandidate;
import com.ailk.sets.platform.intf.domain.InvitationOutEmployer;
import com.ailk.sets.platform.intf.domain.InvitationOutInfo;
import com.ailk.sets.platform.intf.domain.PositionSeries;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;
import com.ailk.sets.platform.intf.empl.domain.EmployerRegistInfo;
import com.ailk.sets.platform.intf.empl.domain.InvitationMail;
import com.ailk.sets.platform.intf.empl.domain.InvitationMailForMultiPos;
import com.ailk.sets.platform.intf.empl.domain.OnlineExamReq;
import com.ailk.sets.platform.intf.empl.domain.OutInvitationRequest;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.empl.domain.PositionInfoConfig;
import com.ailk.sets.platform.intf.empl.service.IEmployerTrial;
import com.ailk.sets.platform.intf.empl.service.IInvite;
import com.ailk.sets.platform.intf.empl.service.IPosition;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.intf.model.candidateTest.CandidateInfo;
import com.ailk.sets.platform.intf.model.invatition.InvitationInfo;
import com.ailk.sets.platform.intf.model.invatition.InvitationValidInfo;
import com.ailk.sets.platform.intf.model.invatition.InviteResult;
import com.ailk.sets.platform.intf.model.invatition.OnlineExamReqResult;
import com.ailk.sets.platform.intf.model.param.GetInvitationInfoParam;
import com.ailk.sets.platform.intf.model.position.PosResponse;
import com.ailk.sets.platform.intf.model.position.PositionStatistics;
import com.ailk.sets.platform.intf.model.wx.HttpClientUtil;
import com.ailk.sets.platform.intf.model.wx.WXCommunicator;
import com.ailk.sets.platform.intf.model.wx.WXInterfaceUrl;
import com.ailk.sets.platform.service.local.IInvitationStateService;
import com.ailk.sets.platform.util.DateUtils;
import com.ailk.sets.platform.util.PassportGenerator;
import com.ailk.sets.platform.util.PrettyTimeMaker;
import com.ailk.sets.platform.util.PropsUtils;
import com.ailk.sets.platform.util.SimpleMailSender;
import com.ailk.sets.platform.util.TemplateHost;

@Transactional(rollbackFor = Exception.class)
public class InviteImpl implements IInvite {
	private Logger logger = LoggerFactory.getLogger(InviteImpl.class);
	@Autowired
	private IPaperDao paperDao;
	@Autowired
	private ICandidateTestDao candidateTestDaoImpl;
	@Autowired
	private IInviteDao inviteDaoImpl;
	@Autowired
	private ICandidateInfoDao candidateInfoDao;
	@Autowired
	private ICandidateDao candidateDao;
	@Autowired
	private IConfigSysParamDao configSysParamDao;
	@Autowired
	private IPositionDao positionDao;
	@Autowired
	private IConfigDao configDao;
	@Autowired
	private IPosition positionImpl;
	@Autowired
	private IInvitationStateService invitationStateService;
	@Autowired
	private ICompanyDao companyDaoImpl;
	@Autowired
	private ICompanyEmailserverDao companyEmailserverDaoImpl;
	@Autowired
	private IEmployerDao employerDaoImpl;
	
	@Autowired
	private IPositionSeriesDao seriesDao;
    @Autowired
	private IInvitationCopytoDao invitationCopytoDao;
    @Autowired
    private IEmployerTrial employerTrail;
    @Autowired
    private IOnlineExamReqDao onlineExamReqDaoImpl;
    
    @Autowired
    private IPositionRelationDao positionRelationDao;
    
	@Override
	public InvitationMail getMailContent(int employerId, int positionId) throws PFServiceException {
		logger.debug("getMailContent for employerId {} , positionId {} ", employerId, positionId);
		try {
			InvitationMail im = new InvitationMail();
			Position position = positionDao.getPosition(positionId);
			if (position.getGroupFlag() == Constants.GROUP_FLAG_PARENT) {
				return getMailContentByPositionGroup(employerId,positionId);
			} else if (position.getGroupFlag() == Constants.GROUP_FLAG_CHILD) {
				logger.warn("can getMailContent for child position , positionId  " + positionId);
				throw new PFServiceException("can getMailContent for child position , positionId  " + positionId);
			}
			Company company = inviteDaoImpl.getCompanyInfo(position.getEmployerId());//显示测评创建人的公司，而不是被委托人(邀请人)
			
			InvationEntity invationEntity = new InvationEntity();
			//　公司名称
			if(company != null){
				invationEntity.setCompanyName(company.getCompanyName());
			}else{
				if(employerId == Constants.SAMPLE_EMPLOYER_ID){
					invationEntity.setCompanyName(companyDaoImpl.getEntity(Constants.SAMPLE_COMPANY_ID).getCompanyName());
				}
			}
			
			// 邮件正文中职位名称
            String positionName = "";
			
			// 校招
           // 社招职位名称
		    positionName = position.getPositionName();
		    invationEntity.setPositionName(positionName);
			/*if(Position.TEST_TYPE_CAMPUS == position.getTestType())
			{
	            // 社招职位名称
			    positionName = position.getPositionName();
			    invationEntity.setPositionName(positionName);
			}else // 社招
			{
			    // 职位序列
			    PositionSeries positionSeries = seriesDao.getEntity(position.getSeriesId());
			    invationEntity.setLevel(configDao.getConfigCodeName(ConfigCodeType.POSITION_LEVEL, position.getLevel() + ""));
			    // 默认名称
			    positionName = positionSeries.getSeriesName() + invationEntity.getLevel() + "工程师";
			    invationEntity.setPositionName(positionName);
			}*/
			// 邮件标题
			im.setSubject(invationEntity.getSubject());
			ConfigSysParameters csp = configSysParamDao.getConfigSysParameters(ConfigSysParam.EFFDAY);
			// 试卷类型集合
			im.setHasInterview(Constants.NEGATIVE); // 默认设置是否有面试题标识为0
			List<PaperPart> paperParts = paperDao.getPaperPart(position.getPaperId());
			// 试卷内容，如：选择题（10道）、编程题（2道）、面试题（1道）
			StringBuffer paperContent = new StringBuffer();
			int totalTime = 0;
			for (PaperPart part : paperParts)
            {
			    if(!StringUtils.isBlank(paperContent.toString()))
			    {
			        paperContent.append("、");
			    }
			    paperContent.append(part.getQuestionNum()+"道"+part.getPartDesc());
			    // 如果有面试题，设置面试题标识为1
                if(part.getId().getPartSeq() == PaperPartSeqEnum.INTEVEIW.getValue())
                {
                    im.setHasInterview(Constants.POSITIVE);
                    invationEntity.setHasInterview(Constants.POSITIVE);
                }
                totalTime += (part.getSuggestTime() == null ? 0 : part.getSuggestTime() );
            }
			invationEntity.setPaperContent(paperContent.toString()); // 试卷内容
			Double avgTime = paperDao.getPaperTotalTime(position.getPaperId());
			im.setAvgTime(avgTime.intValue());
			avgTime = Math.ceil(avgTime/60);
			invationEntity.setTotalTime(avgTime.intValue()); // 大概作答时间
			invationEntity.setPaperSize(paperParts.size());
			invationEntity.setUrl("【考试链接地址】");
			invationEntity.setCandidateName("【考生姓名】");

			TemplateHost templateHost = new TemplateHost();
			VelocityContext context = templateHost.getContext();

			//  邀请时间  - 当前时间
            Timestamp inviteDate = new Timestamp(System.currentTimeMillis());
             // 失效时间  当前时间+6天
            Timestamp expDate = DateUtils.getOffsetDate(inviteDate, Integer.parseInt(csp.getParamValue()));
			
            context.put("entity", invationEntity);
            invationEntity.setExpDate(expDate);
            
            im.setTotalTime(totalTime);
			// add by lipan 2014年7月5日14:25:18
			im.setValidDate(DateUtils.getDateStr(expDate , DateUtils.DATE_FORMAT_3));
			im.setTestPositionName(positionName);
//			im.setCanWithOutCamera(0); //  默认值0
			im.setCurrentDate(DateUtils.getDateStr(inviteDate , DateUtils.DATE_FORMAT_3)); // 当前时间
			im.setContent(templateHost.makeFileString(TemplateHost.VM_INVITATIONDIV));
			return im;
		} catch (Exception e) {
			logger.error("error get mail content , ", e);
			throw new PFServiceException(e.getMessage());
		}
	}

 	private InvitationMail getMailContentByPositionGroup(int employerId, int positionId) throws PFServiceException {

		logger.debug("getMailContentByPositionGroup for employerId {} , positionId {} ", employerId, positionId);
		try {
			InvitationMail im = new InvitationMail();
			Position groupPos = positionDao.getEntity(positionId);
			List<PositionRelation> positionRelations = positionRelationDao.getPositionRelationByPositionGroupId(positionId);
			List<Integer> mustPositionIds = new ArrayList<Integer>();
			List<Integer> optionalPositionIds = new ArrayList<Integer>();
			for(PositionRelation pr : positionRelations){
				if(pr.getRelation() == Constants.POSITION_RELATION_MUST){
					mustPositionIds.add(pr.getId().getPositionId());
				}else if(pr.getRelation() == Constants.POSITION_RELATION_CHOOSE){
					optionalPositionIds.add(pr.getId().getPositionId());
				}else{
					logger.error("not support relation " + pr.getRelation());
					throw new PFServiceException("not support relation " + pr.getRelation());
				}
			}
			
			InvationEntityMulti invationEntity = new InvationEntityMulti();
			List<InvationEntity> perInvationEntitys = new ArrayList<InvationEntity>();
			invationEntity.setPerInvitationEntitys(perInvationEntitys);
			
			List<InvationEntity> perInvitationEntitys2 = new ArrayList<InvationEntity>();
			invationEntity.setPerInvitationEntitys2(perInvitationEntitys2);
			
			for(int pId : mustPositionIds){
				InvationEntity perInvationEntity = getInvationEntity(employerId, pId, im, invationEntity);
				perInvationEntitys.add(perInvationEntity);
			}
			for(int pId : optionalPositionIds){
				InvationEntity perInvationEntity = getInvationEntity(employerId, pId, im, invationEntity);
				perInvitationEntitys2.add(perInvationEntity);
			}
			
			TemplateHost templateHost = new TemplateHost();
			VelocityContext context = templateHost.getContext();
	        context.put("entity", invationEntity);
			im.setContent(templateHost.makeFileString(TemplateHost.VM_INVITATIONDIVMULTI));
			im.setTestPositionName(groupPos.getPositionName());//组测评清空
			return im;
		} catch (Exception e) {
			logger.error("error get getMailContentByPositionGroup content , ", e);
			throw new PFServiceException(e.getMessage());
		}
	
	}
	
 	
 	private InvationEntity getInvationEntity(int employerId, int positionId,InvitationMail im ,InvationEntityMulti invationEntity ){

		Position position = positionDao.getPosition(positionId);
		Company company = inviteDaoImpl.getCompanyInfo(position.getEmployerId());//显示测评创建人的公司，而不是被委托人(邀请人)
		
		//　公司名称
		if(company != null){
			invationEntity.setCompanyName(company.getCompanyName());
		}else{
			if(employerId == Constants.SAMPLE_EMPLOYER_ID){
				invationEntity.setCompanyName(companyDaoImpl.getEntity(Constants.SAMPLE_COMPANY_ID).getCompanyName());
			}
		}
		
		// 邮件正文中职位名称
        String positionName = "";
        InvationEntity perInvationEntity = new InvationEntity();
        
     // 社招职位名称
	    positionName = position.getPositionName();
	    perInvationEntity.setPositionName(positionName);
	    
		/*// 校招
		if(Position.TEST_TYPE_CAMPUS == position.getTestType())
		{
            // 社招职位名称
		    positionName = position.getPositionName();
		    perInvationEntity.setPositionName(positionName);
		}else // 社招
		{
		    // 职位序列
		    PositionSeries positionSeries = seriesDao.getEntity(position.getSeriesId());
		    invationEntity.setLevel(configDao.getConfigCodeName(ConfigCodeType.POSITION_LEVEL, position.getLevel() + ""));
		    // 默认名称
		    positionName = positionSeries.getSeriesName() + invationEntity.getLevel() + "工程师";
		    perInvationEntity.setPositionName(positionName);
		}*/
		// 邮件标题
		im.setSubject(invationEntity.getSubject());
		ConfigSysParameters csp = configSysParamDao.getConfigSysParameters(ConfigSysParam.EFFDAY);
		// 试卷类型集合
		if (im.getHasInterview() == null || im.getHasInterview() != Constants.POSITIVE) {
			im.setHasInterview(Constants.NEGATIVE); // 默认设置是否有面试题标识为0
		}
		List<PaperPart> paperParts = paperDao.getPaperPart(position.getPaperId());
		// 试卷内容，如：选择题（10道）、编程题（2道）、面试题（1道）
		StringBuffer paperContent = new StringBuffer();
		int totalTime = 0;
		for (PaperPart part : paperParts)
        {
		    if(!StringUtils.isBlank(paperContent.toString()))
		    {
		        paperContent.append("、");
		    }
		    paperContent.append(part.getQuestionNum()+"道"+part.getPartDesc());
		    // 如果有面试题，设置面试题标识为1
            if(part.getId().getPartSeq() == PaperPartSeqEnum.INTEVEIW.getValue())
            {
                im.setHasInterview(Constants.POSITIVE);
                invationEntity.setHasInterview(Constants.POSITIVE);
            }
            totalTime += (part.getSuggestTime() == null ? 0 : part.getSuggestTime() );
        }
		
		perInvationEntity.setPaperContent(paperContent.toString()); // 试卷内容
		Double avgTime = paperDao.getPaperTotalTime(position.getPaperId());
		if(im.getAvgTime() == null || im.getAvgTime().intValue() < avgTime.intValue()){
			im.setAvgTime(avgTime.intValue());
		}
		avgTime = Math.ceil(avgTime/60);
		perInvationEntity.setTotalTime(avgTime.intValue()); // 大概作答时间
		perInvationEntity.setPaperSize(paperParts.size());
		perInvationEntity.setUrl("【考试链接地址】");
//		perInvationEntitys.add(perInvationEntity);
		
		invationEntity.setCandidateName("【考生姓名】");

		

		//  邀请时间  - 当前时间
        Timestamp inviteDate = new Timestamp(System.currentTimeMillis());
         // 失效时间  当前时间+6天
        Timestamp expDate = DateUtils.getOffsetDate(inviteDate, Integer.parseInt(csp.getParamValue()));
        
        invationEntity.setExpDate(expDate);
        
        im.setTotalTime(totalTime);
		// add by lipan 2014年7月5日14:25:18
		im.setValidDate(DateUtils.getDateStr(expDate , DateUtils.DATE_FORMAT_3));
		im.setTestPositionName(positionName);
//		im.setCanWithOutCamera(0); //  默认值0
		im.setCurrentDate(DateUtils.getDateStr(inviteDate , DateUtils.DATE_FORMAT_3)); // 当前时间
		return perInvationEntity;
	
 	}
	@Override
	public InviteResult invite(Invitation invitation) throws PFServiceException {
		logger.debug("invite , invitation info is {} ", invitation);
		try {
			
		    long startTime = System.currentTimeMillis();
		    
		    deteleByCandidate(invitation);
		    
		    // 拷贝..
		    InvitationMail invitationMail = new InvitationMail();
		    PropertyUtils.copyProperties(invitationMail, invitation);
		    
		    Integer positionId = invitationMail.getPositionId();
		    Integer employerId = invitationMail.getEmployerId();
		    
			InviteResult pfResponse = new InviteResult();
//			Company company = inviteDaoImpl.getCompanyInfo(employerId);
			Position position = positionDao.getEntity(positionId);
			if(position.getGroupFlag() == Constants.GROUP_FLAG_PARENT){
			   return inviteForGroupPosition(invitation);
			}else if(position.getGroupFlag() == Constants.GROUP_FLAG_CHILD){
				logger.warn("can not send invitation for child position , positionId  "+ invitation.getPositionId());
				throw new PFServiceException("can not send invitation for child position , positionId  "+ invitation.getPositionId());
			}
			Company company = inviteDaoImpl.getCompanyInfo(position.getEmployerId());//显示测评创建人的公司，而不是被委托人(邀请人)
			InvationEntity invationEntity = new InvationEntity();
			if(company == null){
				logger.debug("the company for employerId {} is null, get with SAMPLE_COMPANY_ID", employerId);
				company = companyDaoImpl.getEntity(Constants.SAMPLE_COMPANY_ID);
			}
			if(company != null)
			invationEntity.setCompanyName(company.getCompanyName());
			
			// 职位名称为自定义名称
			invationEntity.setPositionName(invitationMail.getTestPositionName());
			
			
            // 邮件正文中职位名称
            String positionName = "";
           
            if(StringUtils.isBlank(invitationMail.getTestPositionName()))
            {
                // 社招职位名称
                positionName = position.getPositionName();
                // 默认名称
                invationEntity.setPositionName(positionName);
            }
            
           /* // 校招
            if(Position.TEST_TYPE_CAMPUS == position.getTestType())
            {
                if(StringUtils.isBlank(invitationMail.getTestPositionName()))
                {
                    // 社招职位名称
                    positionName = position.getPositionName();
                    // 默认名称
                    invationEntity.setPositionName(positionName);
                }
            }else // 社招
            {
                if(StringUtils.isBlank(invitationMail.getTestPositionName()))
                {
                    // 职位序列
                    PositionSeries positionSeries = seriesDao.getEntity(position.getSeriesId());
                    invationEntity.setLevel(configDao.getConfigCodeName(ConfigCodeType.POSITION_LEVEL, position.getLevel() + ""));
                    // 邮件名称
                    positionName = positionSeries.getSeriesName() + invationEntity.getLevel() + "工程师";
                    // 默认名称
                    invationEntity.setPositionName(positionName);
                }
            }
          */

            ConfigSysParameters csp = configSysParamDao.getConfigSysParameters(ConfigSysParam.EFFDAY);
			
			//  邀请时间  - 当前时间
			Timestamp inviteDate = new Timestamp(System.currentTimeMillis());
			
	         // 生效时间 - 自定义 / 当前时间+6天
            Timestamp expDate = DateUtils.getTimestamp(invitationMail.getValidDate() , DateUtils.DATE_FORMAT_3);
            if(null == expDate)
            {
                // 当前时间+6天作为失效时间
                expDate = DateUtils.getOffsetDate(inviteDate, Integer.parseInt(csp.getParamValue()));
            }
           
           
            invationEntity.setExpDate(expDate);
            
            String beginDateStr = invitation.getBeginDate();
            if(StringUtils.isNotEmpty(beginDateStr)){
            	 Timestamp effDate = DateUtils.getTimestamp(beginDateStr , DateUtils.DATE_FORMAT_3);
            	 invationEntity.setEffDate(effDate);
            	 invitation.setEffDate(effDate);
            }
            List<PaperPart> paperParts = paperDao.getPaperPart(position.getPaperId());
            invationEntity.setPaperSize(paperParts.size());
			// 试卷内容，如：选择题（10道）、编程题（2道）、面试题（1道）
            StringBuffer paperContent = new StringBuffer();
            for (PaperPart part : paperParts)
            {
                if(!StringUtils.isBlank(paperContent.toString()))
                {
                    paperContent.append("、");
                }
                paperContent.append(part.getPartDesc()+"（"+part.getQuestionNum()+"道）");
                // 如果有面试题，设置面试题标识为1
                if(part.getId().getPartSeq() == PaperPartSeqEnum.INTEVEIW.getValue())
                {
                    invationEntity.setHasInterview(Constants.POSITIVE);
                }
            }
            invationEntity.setPaperContent(paperContent.toString()); // 试卷内容
            Double avgTime = paperDao.getPaperTotalTime(position.getPaperId());
			avgTime = Math.ceil(avgTime/60);
			invationEntity.setTotalTime(avgTime.intValue()); // 大概作答时间
//            invationEntity.setTotalTime(paperDao.getPaperTotalTime(position.getPaperId())); // 大概作答时间
			csp = configSysParamDao.getConfigSysParameters(ConfigSysParam.INVATIONURL);
			String url = csp.getParamValue();

			if (invitation.getTestId() == null) {
				String passport = PassportGenerator.getRandomPassport(Constants.PASSPORTLENGTH);
				invitation.setPassport(passport);
				invitation.setPositionId(positionId);
				
				// 保存应聘人测评信息 
				CandidateTest candidateTest = insertCandidateTest(invitation, invitationMail);
				invitation.setTestId(candidateTest.getTestId());
			}
			invitation.setEmployerId(employerId);
			
			inviteDaoImpl.saveOrUpdate(invitation);
			
			// 发送邀请邮件
			MailSenderInfo mailSenderInfo = MailSenderInfo.getMailSenderInfo(companyEmailserverDaoImpl.getEntity(company.getCompanyId(), "companyId"));
			// 退信地址，暂时先设定为公司邮箱
			// mailSenderInfo.setBounceAddr(employer.getEmployerAcct());
			TemplateHost templateHost = new TemplateHost();
			VelocityContext context = templateHost.getContext();
			  // 邮件标题
          
			Map<String, String> mailExceptionMap = configDao.getConfigCodeMap(ConfigCodeType.MAILEXCEPTION);
			int state = 0;
			// 生成通行证和url
			String invatitionUrl = url + "/" + invitation.getTestId() + "/" + invitation.getPassport();
			invitation.setInvitationUrl(invatitionUrl);
			invationEntity.setUrl(invatitionUrl);
			invationEntity.setCandidateName(invitation.getCandidateName());
			invationEntity.setSelfContext(invitation.getSelfContext());
			pfResponse.setUrl(invatitionUrl);
			String mailSubject = "";
	        mailSubject = invationEntity.getSubject();
			mailSenderInfo.setSubject(mailSubject);
			try {
				mailSenderInfo.setToAddress(invitation.getCandidateEmail());
				context.put("entity", invationEntity);
				String pageString = templateHost.makeFileString(TemplateHost.VM_INVITATIONHTML);
				mailSenderInfo.setContent(pageString);
				SimpleMailSender.sendHtmlMail(mailSenderInfo);
				state = 1;
			} catch (Exception e) {
				logger.error("send email failed", e);
				pfResponse.setCode(FuncBaseResponse.FAILED);
				String excName = e.getClass().getName();
				String errtxt = mailExceptionMap.get(excName);
				if (!StringUtils.isEmpty(errtxt))
					invitation.setInvitationErrtxt(errtxt);
				else
					invitation.setInvitationErrtxt("未知原因");
				pfResponse.setMessage(invitation.getInvitationErrtxt());
				state = 0;
				PositionLog positionLog = new PositionLog();
				positionLog.setEmployerId(employerId);
				positionLog.setLogTime(new Timestamp(System.currentTimeMillis()));
				positionLog.setPositionId(positionId);
				positionLog.setPositionState(1);
				positionLog.setStateId(invitation.getTestId());
				positionDao.savePositionLog(positionLog);
//				sendWXInvitationFailed(employerId, invitation, invationEntity);
			}
			invitation.setInvitationState(state);
			invitation.setInvitationDate(inviteDate);
			 // 失效时间 - 第七天23:59点
            String dateStr = DateUtils.getDateStr(DateUtils.timestamp2Date(expDate) , DateUtils.DATE_FORMAT_3);
			Timestamp intExpDate = DateUtils.getTimestamp(dateStr, DateUtils.DATE_FORMAT_3);
			invitation.setExpDate(intExpDate);
			invitation.setStateDate(new Date(System.currentTimeMillis()));
			inviteDaoImpl.saveOrUpdate(invitation);
			
			
			if (StringUtils.isEmpty(pfResponse.getCode()))
				pfResponse.setCode(FuncBaseResponse.SUCCESS);
			pfResponse.setTestId(invitation.getTestId());
			logger.info("invite cost time is {}", (System.currentTimeMillis() - startTime));
			return pfResponse;
		} catch (Exception e) {
			logger.error("error send invitations.......", e);
			throw new PFServiceException(e.getMessage());
		}
	}

	private void sendWXInvitationFailed(int employerId, Invitation invitation, InvationEntity invationEntity) throws PFDaoException {
		WXCommunicator wxCommunicator = new WXCommunicator(HttpClientUtil.getHttpClient());
		Employer employer = employerDaoImpl.getEntity(employerId);
		if (StringUtils.isNotEmpty(employer.getOpenId())) {
			String token = wxCommunicator.getAccessToken(configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYERAPPID), configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYERAPPSECRET));
			String text = configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYEEFAILED);
			text = MessageFormat.format(text, invationEntity.getPosition(), invitation.getCandidateName(), invitation.getCandidateEmail(), invitation.getInvitationErrtxt());
			logger.debug("token is (" + token + ")");
			logger.debug("tell emloyer " + employerId + " openId(" + employer.getOpenId() + ")invitation failed text is " + text);
			wxCommunicator.sendMessage(WXInterfaceUrl.getSendText(employer.getOpenId(), text), token);
		}
	}

	private CandidateTest insertCandidateTest(Invitation invitation, InvitationMail invitationMail) {
		CandidateTest candidateTest = new CandidateTest();
		// candidateTest.setCandidateId(candidateInfoDao.getCandidateId(invitation.getCandidateName(), invitation.getCandidateEmail()));
		candidateTest.setPositionId(invitationMail.getPositionId());
		candidateTest.setTestState(0);
		candidateTest.setTestResult(0);
		candidateTest.setPaperState(0);
		candidateTest.setBreakTimes(0);
		candidateTest.setElapsedTime(0);
		candidateTest.setSwitchTimes(0);
		candidateTest.setFreshTimes(0);
		candidateTest.setChannelType(invitation.getChannelType());
		candidateTest.setPassport(invitation.getPassport());
		candidateTest.setCandidateId(candidateDao.getCandidateId(invitation.getCandidateName(), invitation.getCandidateEmail()));
		candidateTest.setPaperId(positionDao.getEntity(invitation.getPositionId()).getPaperId());
		// add by lipan 2014年7月5日14:52:43
		candidateTest.setCanWithOutCamera(invitationMail.getCanWithOutCamera()); //是否允许没有摄像头
		candidateTest.setTestPositionName(invitationMail.getTestPositionName()); //测评名称
		
		candidateTestDaoImpl.saveCandidateTest(candidateTest);
		return candidateTest;
	}

	public CandidateInfo certify(long testId, String passport) throws PFServiceException {
		try {
			Invitation invitation = candidateInfoDao.getInvitation(testId);
			CandidateInfo info = new CandidateInfo();
			// 邀请不存在或者同行者不正确
			if (invitation == null || (!invitation.getPassport().equals(passport))) {
				info.setCode(FuncBaseResponse.ACCESSDENY);
				info.setMessage(configDao.getConfigCodeName(ConfigCodeType.INVITE_ERROR, FuncBaseResponse.INVNONEEXIST));
			// 邀请过期的情况
			} else if (invitation.getExpDate().before(new Timestamp(System.currentTimeMillis()))) {
				info.setCode(FuncBaseResponse.ACCESSDENY);
				info.setMessage(configDao.getConfigCodeName(ConfigCodeType.INVITE_ERROR, FuncBaseResponse.INVOVERTIME));
			} else if (invitation.getInvitationState() == 1) {
				CandidateTest candidateTest = candidateTestDaoImpl.loginCandidateTest(testId, passport);
				logger.debug("longin candidate testId is {},testState is {}", candidateTest.getTestId(), candidateTest.getTestState());
				// 已经完成考试
				if (candidateTest.getTestState() > 1) {
					info.setCode(FuncBaseResponse.FINISHEXAM);
					info.setMessage(invitation.getCandidateName());
				} else {
					info.setCode(FuncBaseResponse.ACCESSABLE);
					info.setCandidateName(invitation.getCandidateName());
					info.setCandidateTest(candidateTest);

					// 获取测评主题描述
					int employerId = candidateInfoDao.getEmployerId(testId);
					Company company = inviteDaoImpl.getCompanyInfo(employerId);
					Position position = positionDao.getEntity(invitation.getPositionId());
					InvationEntity invationEntity = new InvationEntity();
					invationEntity.setCompanyName(company.getCompanyName());
					
					// 默认为自定义名称
					if(!StringUtils.isBlank(candidateTest.getTestPositionName()))
					{
					    invationEntity.setPositionName(candidateTest.getTestPositionName());
					}else
					{
					    invationEntity.setPositionName(position.getPositionName());
					}
					
					 // 默认为自定义名称
                    info.setCandidateDesc(invationEntity.getCuzSubjectForCand());
					
                    // 职位序列
                    PositionSeries positionSeries = seriesDao.getEntity(position.getSeriesId());
                    
					// 社招:编程语言和级别  组成候选人描述...
		            if(Position.TEST_TYPE_SOCIETY == position.getTestType())
		            {
					    invationEntity.setSeriesName(positionSeries.getSeriesName());
					    invationEntity.setLevel(configDao.getConfigCodeName(ConfigCodeType.POSITION_LEVEL, position.getLevel() + ""));
					   
					    // 没有自定义名称.....编程语言+级别+”测评“
					    if(StringUtils.isBlank(candidateTest.getTestPositionName()))
					    {
					        // 候选人考试标题
					        info.setCandidateDesc(invationEntity.getSubjectForCand());
					    }
					}
				}
			}
			return info;
		} catch (Exception e) {
			logger.error("error call certify( testId, String passport),", e);
			throw new PFServiceException(e.getMessage());
		}
	}

	public PFResponse certify(long testId, String passport, String ticket) throws PFServiceException {
		try {
			PFResponse pfResponse = new PFResponse();
			Invitation invitation = candidateInfoDao.getInvitation(testId);
			if (invitation.getInvitationState() == 3 || invitation.getInvitationState() == 5) {
				pfResponse.setCode(FuncBaseResponse.ACCESSDENY);
				pfResponse.setMessage(configDao.getConfigCodeName(ConfigCodeType.INVITE_ERROR, invitation.getInvitationState() + ""));
			} else {
				CandidateTest ce = candidateTestDaoImpl.getCandidateTest(testId, passport);
				// if (ce != null) {
				// if (ce.getSessionTicket().equals(ticket))
				// pfResponse.setCode(FuncBaseResponse.ACCESSABLE);
				// else {
				// pfResponse.setCode(FuncBaseResponse.ACCESSDENY);
				// pfResponse.setMessage(configDao.getConfigCodeName(ConfigCodeType.INVITE_ERROR, "6"));
				// }
				// }
				if (ce.getTestState() > 1) {
					pfResponse.setCode(FuncBaseResponse.FINISHEXAM);
					pfResponse.setMessage(invitation.getCandidateName());
				} else
					pfResponse.setCode(FuncBaseResponse.ACCESSABLE);
			}
			return pfResponse;
		} catch (Exception e) {
			logger.error("error certify( testId, String passport, String ticket),", e);
			throw new PFServiceException(e.getMessage());
		}
	}

	@Override
	public PFResponse certifyRequest(long testId, int state) throws PFServiceException {
		PFResponse pfResponse = new PFResponse();
		return pfResponse;
	}

	@Override
	public List<InvitationInfo> getInvitationInfo(GetInvitationInfoParam param) throws PFServiceException {
		try {
			List<InvitationInfo> result = new ArrayList<InvitationInfo>();
			List<Invitation> list = inviteDaoImpl.getInvitaionInfo(param);
			if (!CollectionUtils.isEmpty(list))
				for (Invitation invitation : list) {
					InvitationInfo invitationFailedInfo = new InvitationInfo();
					BeanUtils.copyProperties(invitationFailedInfo, invitation);
					invitationFailedInfo.setInvitationDateDesc(PrettyTimeMaker.format(invitationFailedInfo.getInvitationDate()));
					result.add(invitationFailedInfo);
				}
			return result;
		} catch (IllegalAccessException e) {
			throw new PFServiceException(e);
		} catch (InvocationTargetException e) {
			throw new PFServiceException(e);
		}
	}

	@Override
	public PositionStatistics reInvite(Invitation invitation, boolean withStatistics) throws PFServiceException {
		try {
			Invitation invitationDb = inviteDaoImpl.getEntity(invitation.getTestId());
			
			// 使用前台传递的参数覆盖数据库中查询的对象
			PropsUtils.copyProperties(invitationDb, invitation);
			inviteDaoImpl.clearInvitationErrtxt(invitationDb);
			InviteResult inviteResult = invite(invitationDb);
			PositionStatistics ps = new PositionStatistics();
			PropertyUtils.copyProperties(ps, inviteResult);
			if (withStatistics)
				positionImpl.setStatistics(ps, invitation.getEmployerId(), invitation.getPositionId());
			return ps;
		} catch (Exception e) {
			logger.error("error call reInvite,", e);
			throw new PFServiceException(e.getMessage());
		}
	}

	@Override
	public PositionStatistics delFailedInvitation(int employerId, int positionId, List<Integer> ids) throws PFServiceException {
		PositionStatistics ps = new PositionStatistics();
		try {
			inviteDaoImpl.delInvitations(ids, employerId);
			ps.setCode(FuncBaseResponse.SUCCESS);
		} catch (Exception e) {
			ps.setCode(FuncBaseResponse.FAILED);
			logger.error("error call delFailedInvitation,", e);
			throw new PFServiceException(e.getMessage());
		}
		return positionImpl.setStatistics(ps, employerId, positionId);
	}

	/**
	 * 获取已邀请列表
	 * 
	 * @param positionId
	 * @param page
	 * @return
	 * @throws PFServiceException
	 */
	public List<InvitationValidInfo> getInvitationValidInfo(int employerId, int positionId, Page page) throws PFServiceException {
		try {
			List<InvitationValidInfo> result = new ArrayList<InvitationValidInfo>();
			List<Invitation> list = inviteDaoImpl.getInvitationInfo(employerId, positionId, page);
			if (!CollectionUtils.isEmpty(list))
				for (Invitation invitation : list) {
					InvitationValidInfo invitationValidInfo = new InvitationValidInfo();
					BeanUtils.copyProperties(invitationValidInfo, invitation);
					invitationValidInfo.setInvitationDateDesc(PrettyTimeMaker.format(invitationValidInfo.getInvitationDate()));
					invitationValidInfo.setInvitationState(invitationStateService.getInvitationState(invitation));
					result.add(invitationValidInfo);
				}
			return result;
		} catch (IllegalAccessException e) {
			throw new PFServiceException(e);
		} catch (InvocationTargetException e) {
			throw new PFServiceException(e);
		}
	}

	/**
	 * 微信号验证邀请和通行证
	 * 
	 * @param testId
	 * @param passport
	 * @return
	 * @throws PFServiceException
	 */
	public CandidateInfo certifyWithWeixin(String openId ,long testId, String passport, String weixinNo) throws PFServiceException {
		try {
			Invitation invitation = candidateInfoDao.getInvitation(testId);
			CandidateInfo info = new CandidateInfo();
			if (invitation == null || (!invitation.getPassport().equals(passport))) {
				info.setCode(FuncBaseResponse.ACCESSDENY);
				info.setMessage(configDao.getConfigCodeName(ConfigCodeType.INVITE_ERROR, FuncBaseResponse.NONEEXIST));
			} else if (invitation.getInvitationState() == 1) {
				info.setCode(FuncBaseResponse.ACCESSABLE);

				CandidateTest ce = candidateTestDaoImpl.loginCandidateTest(testId, passport);
				info.setCandidateName(invitation.getCandidateName());
				info.setCandidateTest(ce);

				int employerId = candidateInfoDao.getEmployerId(testId);
				Company company = inviteDaoImpl.getCompanyInfo(employerId);
				Position position = positionDao.getEntity(invitation.getPositionId());
				InvationEntity invationEntity = new InvationEntity();
				if(company != null)
				invationEntity.setCompanyName(company.getCompanyName());
//				invationEntity.setProgrameLanguage(configDao.getConfigCodeName(ConfigCodeType.POSITION_LANGUAGE, seriesDao.getEntity(position.getSeriesId()).getPositionLanguage()));
//				invationEntity.setLevel(configDao.getConfigCodeName(ConfigCodeType.POSITION_LEVEL, position.getLevel() + ""));
				invationEntity.setPositionName(position.getPositionName());
				info.setCandidateDesc(invationEntity.getSubjectForCand());

				CandidateTest candidateTest = new CandidateTest();
				candidateTest.setCandidateId(candidateInfoDao.getCandidateIdWithWeixin(openId , invitation.getCandidateName(), invitation.getCandidateEmail(), weixinNo));
				candidateTest.setPassport(passport);
				candidateTest.setPositionId(invitation.getPositionId());
				candidateTest.setTestId(invitation.getTestId());
				candidateTest.setBeginTime(new Timestamp(System.currentTimeMillis()));
				candidateTest.setTestResult(0);
				candidateTestDaoImpl.saveCandidateTest(candidateTest);
			} else {
				info.setCode(FuncBaseResponse.ACCESSDENY);
				info.setMessage(configDao.getConfigCodeName(ConfigCodeType.INVITE_ERROR, invitation.getInvitationState() + ""));
			}
			return info;
		} catch (Exception e) {
			logger.error("error call certifyWithWeixin,", e);
			throw new PFServiceException(e.getMessage());
		}
	}

	public long getInvitationNum(int employerId, int positionId) throws PFServiceException {
		try {
			long num = inviteDaoImpl.getInvitationNumber(employerId, positionId);
			long failed = inviteDaoImpl.getInvitationFailedNumber(employerId, positionId);
			return (num - failed);
		} catch (Exception e) {
			logger.error("error call getInvitationNum,", e);
			throw new PFServiceException(e.getMessage());
		}
	}
	
	public static void main(String[] args)
    {
	    Timestamp current = DateUtils.getCurrentTimestamp();
	    String dateStr = DateUtils.getDateStr(DateUtils.timestamp2Date(current) , DateUtils.DATE_FORMAT_4);
	    System.out.println(dateStr);
        System.out.println(DateUtils.getTimestamp(dateStr, DateUtils.DATE_FORMAT_3));
//        System.out.println(exp.before(current));
    }

	@Override
	public List<InviteResult> inviteByOutInvitation(int type,int positionId,int invalidDays,InvitationOutInfo outInfo)  throws PFServiceException{
		logger.debug("inviteByOutInvitation for positionId {} , outInfo {} ", positionId, outInfo);
		List<InviteResult> results = new ArrayList<InviteResult>();
		List<InvitationOutCandidate> candidateList = outInfo.getCandidateList();
		Timestamp validTime = new Timestamp(System.currentTimeMillis());
		validTime = DateUtils.getOffsetDate(validTime, invalidDays);
		String valid = DateUtils.getDateStr(validTime,DateUtils.DATE_FORMAT_2);
		for(InvitationOutCandidate candidate : candidateList){
			Invitation invitation = new Invitation();
			invitation.setCandidateEmail(candidate.getEmail());
			invitation.setCandidateName(candidate.getName());
			invitation.setValidDate(valid);
			invitation.setEmployerId(outInfo.getEmployerId());
			invitation.setPositionId(positionId);
			Position position = positionDao.getPosition(positionId);
			if(position.getPreBuilt() == Constants.PREBUILT_SYS){//内置测评不能发送邀请
				Position copy = positionDao.getPositionByCreateFrom(positionId, outInfo.getEmployerId());
				if(copy == null){//没有创建过测评
					copy = new Position();
					try {
						PropertyUtils.copyProperties(copy, position);
						copy.setCreateFrom(positionId);
						copy.setPositionId(null);
						copy.setEmployerId(outInfo.getEmployerId());
						 List<PositionInfoExt>  configs = candidateInfoDao.getCandConfigInfoExts(position.getEmployerId(), positionId);
						 List<PositionInfoConfig> infoExts = new ArrayList<PositionInfoConfig>();
						 for(PositionInfoExt config : configs){
							 PositionInfoConfig infoExt = new PositionInfoConfig();
							 infoExt.setInfoId(config.getId().getInfoId());
							 infoExt.setSeq(config.getSeq());
							 infoExts.add(infoExt);
						 }
						 copy.setConfigInfo(infoExts);
						 PosResponse res = positionImpl.createPosition(copy);
						invitation.setPositionId(res.getPositionId());
					} catch (Exception e) {
						logger.error("error copy ", e);
					}
				}
				
			}
			
			
			invitation.setTestPositionName(outInfo.getRealPositionName());
			invitation.setCanWithOutCamera(outInfo.getTestWithoutCamera());
			invitation.setChannelType(type);
			invitation.setSelfContext(outInfo.getEmailContent());
			InviteResult res = invite(invitation);
			res.setId(candidate.getId());
			results.add(res);
			
			List<InvitationOutEmployer> copyTos = outInfo.getEmployers();
			if(copyTos != null){
				for(InvitationOutEmployer outEmployer : copyTos){
					InvitationCopyto copyTo = new InvitationCopyto();
					InvitationCopytoId id = new InvitationCopytoId();
					id.setEmail(outEmployer.getEmail());
					id.setTestId(res.getTestId());
					copyTo.setId(id);
					copyTo.setName(outEmployer.getName());
					invitationCopytoDao.saveOrUpdate(copyTo);
				}
			}
		}
		return results;
	}

	@Override
	public InviteResult inviteBySampleInvitation(Invitation invitation) throws PFServiceException {
		try{
			InviteResult result = new InviteResult();
			String employerEmail = invitation.getEmployerEmail();
			if(StringUtils.isEmpty(employerEmail)){
				logger.warn("employerEmail can not be null ");
				result.setCode(FuncBaseResponse.FAILED);
				result.setMessage("招聘者邮箱不能为空");
				return result;
			}
			Position position = positionDao.getEntity(invitation.getPositionId());
	        if(position.getSample() != Constants.POSITION_TEST_SAMPLE){
	        	logger.warn("the position {} is not a sample position, please check ", invitation.getPositionId() );
				result.setCode(FuncBaseResponse.FAILED);
				result.setMessage("权限错误，非样例测评发送邀请");
				return result;
	        }
	    
	        EmployerRegistInfo info = new EmployerRegistInfo();
	        info.setEmployerEmail(employerEmail);
	        employerTrail.registEmployerQuickly(info);
	        
	        long invitationNum = inviteDaoImpl.getInvitationNumber(info.getEmployerId(), position.getPositionId());
	        int max = Integer.valueOf(configSysParamDao.getConfigParamValue("SAMPLEMAXNUM"));
	        if(invitationNum >= max){
	        	result.setCode(FuncBaseResponse.SAMPLEMAXNUM);
				result.setMessage("超过最大邀请数");
				return result;
	        }
	        invitation.setEmployerId(info.getEmployerId());
	        result = invite(invitation);
			return result;
		}catch(Exception e){
			logger.error("error to inviteBySampleInvitation ", e);
			throw new PFServiceException(e);
		}
		
	}

	@Override
	public InvitationTimeInfo getInvitationTimeInfo(long testId) {
		Invitation invitation = inviteDaoImpl.getEntity(testId);
		Timestamp effDate = invitation.getEffDate();
		Timestamp expDate = invitation.getExpDate();
		InvitationTimeInfo timeInfo = new InvitationTimeInfo();
		long current = System.currentTimeMillis();
		if(effDate != null){
			timeInfo.setEffDate(effDate);
			long misToEff =  effDate.getTime() - current; //还差多少时间可以考试
			timeInfo.setSecToEffDate(misToEff/1000);
		}
		
		timeInfo.setExpDate(expDate);
		long misToExp =  expDate.getTime() - current;//还剩多少时间考试结束
		timeInfo.setSecToExpDate(misToExp/1000);
		return timeInfo;
	}

	@Override
	public InvitationMailForMultiPos getMailContents(int employerId, List<Integer> positionIds) throws PFServiceException {

		logger.debug("getMailContents for employerId {} , positionIds {} ", employerId, positionIds);
		try {
			InvitationMailForMultiPos im = new InvitationMailForMultiPos();
			InvationEntityMulti invationEntity = new InvationEntityMulti();
			List<InvationEntity> perInvationEntitys = new ArrayList<InvationEntity>();
			invationEntity.setPerInvitationEntitys(perInvationEntitys);
			for(Integer positionId : positionIds){
				Position position = positionDao.getPosition(positionId);
				Company company = inviteDaoImpl.getCompanyInfo(position.getEmployerId());//显示测评创建人的公司，而不是被委托人(邀请人)
				
				//　公司名称
				if(company != null){
					invationEntity.setCompanyName(company.getCompanyName());
				}else{
					if(employerId == Constants.SAMPLE_EMPLOYER_ID){
						invationEntity.setCompanyName(companyDaoImpl.getEntity(Constants.SAMPLE_COMPANY_ID).getCompanyName());
					}
				}
				
				// 邮件正文中职位名称
	            String positionName = "";
	            InvationEntity perInvationEntity = new InvationEntity();
	            // 社招职位名称
			    positionName = position.getPositionName();
			    perInvationEntity.setPositionName(positionName);
				/*// 校招
				if(Position.TEST_TYPE_CAMPUS == position.getTestType())
				{
		            // 社招职位名称
				    positionName = position.getPositionName();
				    perInvationEntity.setPositionName(positionName);
				}else // 社招
				{
				    // 职位序列
				    PositionSeries positionSeries = seriesDao.getEntity(position.getSeriesId());
				    invationEntity.setLevel(configDao.getConfigCodeName(ConfigCodeType.POSITION_LEVEL, position.getLevel() + ""));
				    // 默认名称
				    positionName = positionSeries.getSeriesName() + invationEntity.getLevel() + "工程师";
				    perInvationEntity.setPositionName(positionName);
				}*/
				// 邮件标题
				im.setSubject(invationEntity.getSubject());
				ConfigSysParameters csp = configSysParamDao.getConfigSysParameters(ConfigSysParam.EFFDAY);
				// 试卷类型集合
				if (im.getHasInterview() == null || im.getHasInterview() != Constants.POSITIVE) {
					im.setHasInterview(Constants.NEGATIVE); // 默认设置是否有面试题标识为0
				}
				List<PaperPart> paperParts = paperDao.getPaperPart(position.getPaperId());
				// 试卷内容，如：选择题（10道）、编程题（2道）、面试题（1道）
				StringBuffer paperContent = new StringBuffer();
				int totalTime = 0;
				for (PaperPart part : paperParts)
	            {
				    if(!StringUtils.isBlank(paperContent.toString()))
				    {
				        paperContent.append("、");
				    }
				    paperContent.append(part.getQuestionNum()+"道"+part.getPartDesc());
				    // 如果有面试题，设置面试题标识为1
	                if(part.getId().getPartSeq() == PaperPartSeqEnum.INTEVEIW.getValue())
	                {
	                    im.setHasInterview(Constants.POSITIVE);
	                    invationEntity.setHasInterview(Constants.POSITIVE);
	                }
	                totalTime += (part.getSuggestTime() == null ? 0 : part.getSuggestTime() );
	            }
				
				perInvationEntity.setPaperContent(paperContent.toString()); // 试卷内容
				Double avgTime = paperDao.getPaperTotalTime(position.getPaperId());
				if(im.getAvgTime() == null || im.getAvgTime().intValue() < avgTime.intValue()){
					im.setAvgTime(avgTime.intValue());
				}
				avgTime = Math.ceil(avgTime/60);
				perInvationEntity.setTotalTime(avgTime.intValue()); // 大概作答时间
				perInvationEntity.setPaperSize(paperParts.size());
				perInvationEntity.setUrl("【考试链接地址】");
				perInvationEntitys.add(perInvationEntity);
				
				invationEntity.setCandidateName("【考生姓名】");

				TemplateHost templateHost = new TemplateHost();
				VelocityContext context = templateHost.getContext();

				//  邀请时间  - 当前时间
	            Timestamp inviteDate = new Timestamp(System.currentTimeMillis());
	             // 失效时间  当前时间+6天
	            Timestamp expDate = DateUtils.getOffsetDate(inviteDate, Integer.parseInt(csp.getParamValue()));
				
	            context.put("entity", invationEntity);
	            invationEntity.setExpDate(expDate);
	            
	            im.setTotalTime(totalTime);
				// add by lipan 2014年7月5日14:25:18
				im.setValidDate(DateUtils.getDateStr(expDate , DateUtils.DATE_FORMAT_3));
				im.setTestPositionName(positionName);
//				im.setCanWithOutCamera(0); //  默认值0
				im.setCurrentDate(DateUtils.getDateStr(inviteDate , DateUtils.DATE_FORMAT_3)); // 当前时间
				im.setContent(templateHost.makeFileString(TemplateHost.VM_INVITATIONDIVMULTI2));
			}
			
			return im;
		} catch (Exception e) {
			logger.error("error get mail content , ", e);
			throw new PFServiceException(e.getMessage());
		}
	
	}

	@Override
	public InviteResult inviteForMulti(InvitationForMulti invitationMulti) throws PFServiceException {
		logger.debug("invite , inviteForMulti info is {} ", invitationMulti);
		try {
//			 List<InviteResult> results = new ArrayList<InviteResult>();
			 InviteResult pfResponse = new InviteResult();
			 List<Invitation>  invitations = new ArrayList<Invitation>();
		    long startTime = System.currentTimeMillis();
		    // 拷贝..
		    MailSenderInfo mailSenderInfo = null;
		    TemplateHost templateHost = new TemplateHost();
			VelocityContext context = templateHost.getContext();
			InvationEntityMulti invationEntity = new InvationEntityMulti();
			// 必选
			List<InvationEntity> perInvationEntitys = new ArrayList<InvationEntity>();
			invationEntity.setPerInvitationEntitys(perInvationEntitys);
			// 可选
			List<InvationEntity> perInvationEntitys2 = new ArrayList<InvationEntity>();
			invationEntity.setPerInvitationEntitys2(perInvationEntitys2);
			// 附加
			List<InvationEntity> perInvationEntitys3 = new ArrayList<InvationEntity>();
			invationEntity.setPerInvitationEntitys3(perInvationEntitys3);
			Map<String, String> mailExceptionMap = configDao.getConfigCodeMap(ConfigCodeType.MAILEXCEPTION);
			Timestamp inviteDate = new Timestamp(System.currentTimeMillis());
			// 必答
		    for(Integer positionId : invitationMulti.getMustAnswerPositionIds()){
		    	Invitation invitation = new Invitation();
		    	invitations.add(invitation);
		    	invitation.setPositionId(positionId);
		    	PropertyUtils.copyProperties(invitation, invitationMulti);
		    	
		    	 InvationEntity perInvationEntity  = new InvationEntity();
		    	 perInvationEntitys.add(perInvationEntity);
		    	 
		    	 InvitationMail invitationMail = new InvitationMail();
				   PropertyUtils.copyProperties(invitationMail, invitation);
				    
				    Integer employerId = invitationMail.getEmployerId();
				    
					
//					results.add(pfResponse);
//					Company company = inviteDaoImpl.getCompanyInfo(employerId);
					Position position = positionDao.getEntity(positionId);
					Company company = inviteDaoImpl.getCompanyInfo(position.getEmployerId());//显示测评创建人的公司，而不是被委托人(邀请人)
					
					if(company == null){
						logger.debug("the company for employerId {} is null, get with SAMPLE_COMPANY_ID", employerId);
						company = companyDaoImpl.getEntity(Constants.SAMPLE_COMPANY_ID);
					}
					if(company != null)
					invationEntity.setCompanyName(company.getCompanyName());
					
					// 职位名称为自定义名称
					perInvationEntity.setPositionName(position.getPositionName());
					
		            // 邮件正文中职位名称
		            String positionName = "";
		            
		            if(StringUtils.isBlank(invitationMail.getTestPositionName()))
	                {
	                    // 社招职位名称
	                    positionName = position.getPositionName();
	                    // 默认名称
	                    perInvationEntity.setPositionName(positionName);
	                }
		           /* // 校招
		            if(Position.TEST_TYPE_CAMPUS == position.getTestType())
		            {
		                if(StringUtils.isBlank(invitationMail.getTestPositionName()))
		                {
		                    // 社招职位名称
		                    positionName = position.getPositionName();
		                    // 默认名称
		                    perInvationEntity.setPositionName(positionName);
		                }
		            }else // 社招
		            {
		                if(StringUtils.isBlank(invitationMail.getTestPositionName()))
		                {
		                    // 职位序列
		                    PositionSeries positionSeries = seriesDao.getEntity(position.getSeriesId());
		                    invationEntity.setLevel(configDao.getConfigCodeName(ConfigCodeType.POSITION_LEVEL, position.getLevel() + ""));
		                    // 邮件名称
		                    positionName = positionSeries.getSeriesName() + invationEntity.getLevel() + "工程师";
		                    // 默认名称
		                    perInvationEntity.setPositionName(positionName);
		                }
		            }
		            */

		            ConfigSysParameters csp = configSysParamDao.getConfigSysParameters(ConfigSysParam.EFFDAY);
					
					//  邀请时间  - 当前时间
//					Timestamp inviteDate = new Timestamp(System.currentTimeMillis());
					
			         // 生效时间 - 自定义 / 当前时间+6天
		            Timestamp expDate = DateUtils.getTimestamp(invitationMail.getValidDate() , DateUtils.DATE_FORMAT_3);
		            if(null == expDate)
		            {
		                // 当前时间+6天作为失效时间
		                expDate = DateUtils.getOffsetDate(inviteDate, Integer.parseInt(csp.getParamValue()));
		            }
		           
		           
		            invationEntity.setExpDate(expDate);
		            
		            String beginDateStr = invitation.getBeginDate();
		            if(StringUtils.isNotEmpty(beginDateStr)){
		            	 Timestamp effDate = DateUtils.getTimestamp(beginDateStr , DateUtils.DATE_FORMAT_3);
		            	 invationEntity.setEffDate(effDate);
		            	 invitation.setEffDate(effDate);
		            }
		            List<PaperPart> paperParts = paperDao.getPaperPart(position.getPaperId());
		            perInvationEntity.setPaperSize(paperParts.size());
					// 试卷内容，如：选择题（10道）、编程题（2道）、面试题（1道）
		            StringBuffer paperContent = new StringBuffer();
		            for (PaperPart part : paperParts)
		            {
		                if(!StringUtils.isBlank(paperContent.toString()))
		                {
		                    paperContent.append("、");
		                }
		                paperContent.append(part.getPartDesc()+"（"+part.getQuestionNum()+"道）");
		                // 如果有面试题，设置面试题标识为1
		                if(part.getId().getPartSeq() == PaperPartSeqEnum.INTEVEIW.getValue())
		                {
		                    invationEntity.setHasInterview(Constants.POSITIVE);
		                }
		            }
		            perInvationEntity.setPaperContent(paperContent.toString()); // 试卷内容
		            Double avgTime = paperDao.getPaperTotalTime(position.getPaperId());
					avgTime = Math.ceil(avgTime/60);
					perInvationEntity.setTotalTime(avgTime.intValue()); // 大概作答时间
//		            invationEntity.setTotalTime(paperDao.getPaperTotalTime(position.getPaperId())); // 大概作答时间
					csp = configSysParamDao.getConfigSysParameters(ConfigSysParam.INVATIONURL);
					String url = csp.getParamValue();

					if (invitation.getTestId() == null) {
						String passport = PassportGenerator.getRandomPassport(Constants.PASSPORTLENGTH);
						invitation.setPassport(passport);
						invitation.setPositionId(positionId);
						
						// 保存应聘人测评信息 
						CandidateTest candidateTest = insertCandidateTest(invitation, invitationMail);
						invitation.setTestId(candidateTest.getTestId());
					}
					invitation.setEmployerId(employerId);
					
					inviteDaoImpl.saveOrUpdate(invitation);
					
					// 发送邀请邮件
					if(mailSenderInfo == null)
					 mailSenderInfo = MailSenderInfo.getMailSenderInfo(companyEmailserverDaoImpl.getEntity(company.getCompanyId(), "companyId"));
					// 退信地址，暂时先设定为公司邮箱
					// mailSenderInfo.setBounceAddr(employer.getEmployerAcct());
					
					
					int state = 0;
					// 生成通行证和url
					String invatitionUrl = url + "/" + invitation.getTestId() + "/" + invitation.getPassport();
					invitation.setInvitationUrl(invatitionUrl);
					perInvationEntity.setUrl(invatitionUrl);
					invationEntity.setCandidateName(invitation.getCandidateName());
					invationEntity.setSelfContext(invitation.getSelfContext());
					pfResponse.setUrl(invatitionUrl);
					 // 邮件标题
		            String mailSubject = "";
		            mailSubject = invationEntity.getSubject();
					mailSenderInfo.setSubject(mailSubject);
					
					invitation.setInvitationState(state);
					invitation.setInvitationDate(inviteDate);
					 // 失效时间 - 第七天23:59点
		            String dateStr = DateUtils.getDateStr(DateUtils.timestamp2Date(expDate) , DateUtils.DATE_FORMAT_3);
					Timestamp intExpDate = DateUtils.getTimestamp(dateStr, DateUtils.DATE_FORMAT_3);
					invitation.setExpDate(intExpDate);
					invitation.setStateDate(new Date(System.currentTimeMillis()));
					inviteDaoImpl.saveOrUpdate(invitation);
					
					
					if (StringUtils.isEmpty(pfResponse.getCode()))
						pfResponse.setCode(FuncBaseResponse.SUCCESS);
//					pfResponse.setTestId(invitation.getTestId());
		    }
		    
		    // 选答
		    for(Integer positionId : invitationMulti.getOptionalAnswerPositionIds()){
		        Invitation invitation = new Invitation();
		        invitations.add(invitation);
		        invitation.setPositionId(positionId);
		        PropertyUtils.copyProperties(invitation, invitationMulti);
		        
		        InvationEntity perInvationEntity  = new InvationEntity();
		        perInvationEntitys2.add(perInvationEntity);
		        
		        InvitationMail invitationMail = new InvitationMail();
		        PropertyUtils.copyProperties(invitationMail, invitation);
		        
		        Integer employerId = invitationMail.getEmployerId();
		        
		        
//					results.add(pfResponse);
//					Company company = inviteDaoImpl.getCompanyInfo(employerId);
		        Position position = positionDao.getEntity(positionId);
		        Company company = inviteDaoImpl.getCompanyInfo(position.getEmployerId());//显示测评创建人的公司，而不是被委托人(邀请人)
		        
		        if(company == null){
		            logger.debug("the company for employerId {} is null, get with SAMPLE_COMPANY_ID", employerId);
		            company = companyDaoImpl.getEntity(Constants.SAMPLE_COMPANY_ID);
		        }
		        if(company != null)
		            invationEntity.setCompanyName(company.getCompanyName());
		        
		        // 职位名称为自定义名称
		        perInvationEntity.setPositionName(position.getPositionName());
		        
		        // 邮件正文中职位名称
		        String positionName = "";
		        
		        if(StringUtils.isBlank(invitationMail.getTestPositionName()))
	            {
	                // 社招职位名称
	                positionName = position.getPositionName();
	                // 默认名称
	                perInvationEntity.setPositionName(positionName);
	            }
		        
		        // 校招
		       /* if(Position.TEST_TYPE_CAMPUS == position.getTestType())
		        {
		            if(StringUtils.isBlank(invitationMail.getTestPositionName()))
		            {
		                // 社招职位名称
		                positionName = position.getPositionName();
		                // 默认名称
		                perInvationEntity.setPositionName(positionName);
		            }
		        }else // 社招
		        {
		            if(StringUtils.isBlank(invitationMail.getTestPositionName()))
		            {
		                // 职位序列
		                PositionSeries positionSeries = seriesDao.getEntity(position.getSeriesId());
		                invationEntity.setLevel(configDao.getConfigCodeName(ConfigCodeType.POSITION_LEVEL, position.getLevel() + ""));
		                // 邮件名称
		                positionName = positionSeries.getSeriesName() + invationEntity.getLevel() + "工程师";
		                // 默认名称
		                perInvationEntity.setPositionName(positionName);
		            }
		        }
		        */
		        
		        ConfigSysParameters csp = configSysParamDao.getConfigSysParameters(ConfigSysParam.EFFDAY);
		        
		        //  邀请时间  - 当前时间
//		        inviteDate = new Timestamp(System.currentTimeMillis());
		        
		        // 生效时间 - 自定义 / 当前时间+6天
		        Timestamp expDate = DateUtils.getTimestamp(invitationMail.getValidDate() , DateUtils.DATE_FORMAT_3);
		        if(null == expDate)
		        {
		            // 当前时间+6天作为失效时间
		            expDate = DateUtils.getOffsetDate(inviteDate, Integer.parseInt(csp.getParamValue()));
		        }
		        
		        
		        invationEntity.setExpDate(expDate);
		        
		        String beginDateStr = invitation.getBeginDate();
		        if(StringUtils.isNotEmpty(beginDateStr)){
		            Timestamp effDate = DateUtils.getTimestamp(beginDateStr , DateUtils.DATE_FORMAT_3);
		            invationEntity.setEffDate(effDate);
		            invitation.setEffDate(effDate);
		        }
		        List<PaperPart> paperParts = paperDao.getPaperPart(position.getPaperId());
		        perInvationEntity.setPaperSize(paperParts.size());
		        // 试卷内容，如：选择题（10道）、编程题（2道）、面试题（1道）
		        StringBuffer paperContent = new StringBuffer();
		        for (PaperPart part : paperParts)
		        {
		            if(!StringUtils.isBlank(paperContent.toString()))
		            {
		                paperContent.append("、");
		            }
		            paperContent.append(part.getPartDesc()+"（"+part.getQuestionNum()+"道）");
		            // 如果有面试题，设置面试题标识为1
		            if(part.getId().getPartSeq() == PaperPartSeqEnum.INTEVEIW.getValue())
		            {
		                invationEntity.setHasInterview(Constants.POSITIVE);
		            }
		        }
		        perInvationEntity.setPaperContent(paperContent.toString()); // 试卷内容
		        Double avgTime = paperDao.getPaperTotalTime(position.getPaperId());
		        avgTime = Math.ceil(avgTime/60);
		        perInvationEntity.setTotalTime(avgTime.intValue()); // 大概作答时间
//		            invationEntity.setTotalTime(paperDao.getPaperTotalTime(position.getPaperId())); // 大概作答时间
		        csp = configSysParamDao.getConfigSysParameters(ConfigSysParam.INVATIONURL);
		        String url = csp.getParamValue();
		        
		        if (invitation.getTestId() == null) {
		            String passport = PassportGenerator.getRandomPassport(Constants.PASSPORTLENGTH);
		            invitation.setPassport(passport);
		            invitation.setPositionId(positionId);
		            
		            // 保存应聘人测评信息 
		            CandidateTest candidateTest = insertCandidateTest(invitation, invitationMail);
		            invitation.setTestId(candidateTest.getTestId());
		        }
		        invitation.setEmployerId(employerId);
		        
		        inviteDaoImpl.saveOrUpdate(invitation);
		        
		        // 发送邀请邮件
		        if(mailSenderInfo == null)
		            mailSenderInfo = MailSenderInfo.getMailSenderInfo(companyEmailserverDaoImpl.getEntity(company.getCompanyId(), "companyId"));
		        // 退信地址，暂时先设定为公司邮箱
		        // mailSenderInfo.setBounceAddr(employer.getEmployerAcct());
		        
		        
		        int state = 0;
		        // 生成通行证和url
		        String invatitionUrl = url + "/" + invitation.getTestId() + "/" + invitation.getPassport();
		        invitation.setInvitationUrl(invatitionUrl);
		        perInvationEntity.setUrl(invatitionUrl);
		        invationEntity.setCandidateName(invitation.getCandidateName());
		        invationEntity.setSelfContext(invitation.getSelfContext());
		        pfResponse.setUrl(invatitionUrl);
		        // 邮件标题
		        String mailSubject = "";
		        mailSubject = invationEntity.getSubject();
		        mailSenderInfo.setSubject(mailSubject);
		        
		        invitation.setInvitationState(state);
		        invitation.setInvitationDate(inviteDate);
		        // 失效时间 - 第七天23:59点
		        String dateStr = DateUtils.getDateStr(DateUtils.timestamp2Date(expDate) , DateUtils.DATE_FORMAT_3);
		        Timestamp intExpDate = DateUtils.getTimestamp(dateStr, DateUtils.DATE_FORMAT_3);
		        invitation.setExpDate(intExpDate);
		        invitation.setStateDate(new Date(System.currentTimeMillis()));
		        inviteDaoImpl.saveOrUpdate(invitation);
		        
		        
		        if (StringUtils.isEmpty(pfResponse.getCode()))
		            pfResponse.setCode(FuncBaseResponse.SUCCESS);
//					pfResponse.setTestId(invitation.getTestId());
		    }
		    
		    // 附加
		    for(Integer positionId : invitationMulti.getAddOnPositionIds()){
		        Invitation invitation = new Invitation();
		        invitations.add(invitation);
		        invitation.setPositionId(positionId);
		        PropertyUtils.copyProperties(invitation, invitationMulti);
		        
		        InvationEntity perInvationEntity  = new InvationEntity();
		        perInvationEntitys3.add(perInvationEntity);
		        
		        InvitationMail invitationMail = new InvitationMail();
		        PropertyUtils.copyProperties(invitationMail, invitation);
		        
		        Integer employerId = invitationMail.getEmployerId();
		        
		        
//					results.add(pfResponse);
//					Company company = inviteDaoImpl.getCompanyInfo(employerId);
		        Position position = positionDao.getEntity(positionId);
		        Company company = inviteDaoImpl.getCompanyInfo(position.getEmployerId());//显示测评创建人的公司，而不是被委托人(邀请人)
		        
		        if(company == null){
		            logger.debug("the company for employerId {} is null, get with SAMPLE_COMPANY_ID", employerId);
		            company = companyDaoImpl.getEntity(Constants.SAMPLE_COMPANY_ID);
		        }
		        if(company != null)
		            invationEntity.setCompanyName(company.getCompanyName());
		        
		        // 职位名称为自定义名称
		        perInvationEntity.setPositionName(position.getPositionName());
		        
		        // 邮件正文中职位名称
		        String positionName = "";
		        
		        if(StringUtils.isBlank(invitationMail.getTestPositionName()))
	            {
	                // 社招职位名称
	                positionName = position.getPositionName();
	                // 默认名称
	                perInvationEntity.setPositionName(positionName);
	            }
		        
		        // 校招
		        /*if(Position.TEST_TYPE_CAMPUS == position.getTestType())
		        {
		            if(StringUtils.isBlank(invitationMail.getTestPositionName()))
		            {
		                // 社招职位名称
		                positionName = position.getPositionName();
		                // 默认名称
		                perInvationEntity.setPositionName(positionName);
		            }
		        }else // 社招
		        {
		            if(StringUtils.isBlank(invitationMail.getTestPositionName()))
		            {
		                // 职位序列
		                PositionSeries positionSeries = seriesDao.getEntity(position.getSeriesId());
		                invationEntity.setLevel(configDao.getConfigCodeName(ConfigCodeType.POSITION_LEVEL, position.getLevel() + ""));
		                // 邮件名称
		                positionName = positionSeries.getSeriesName() + invationEntity.getLevel() + "工程师";
		                // 默认名称
		                perInvationEntity.setPositionName(positionName);
		            }
		        }*/
		        
		        
		        ConfigSysParameters csp = configSysParamDao.getConfigSysParameters(ConfigSysParam.EFFDAY);
		        
		        //  邀请时间  - 当前时间
//		        Timestamp inviteDate = new Timestamp(System.currentTimeMillis());
		        
		        // 生效时间 - 自定义 / 当前时间+6天
		        Timestamp expDate = DateUtils.getTimestamp(invitationMail.getValidDate() , DateUtils.DATE_FORMAT_3);
		        if(null == expDate)
		        {
		            // 当前时间+6天作为失效时间
		            expDate = DateUtils.getOffsetDate(inviteDate, Integer.parseInt(csp.getParamValue()));
		        }
		        
		        
		        invationEntity.setExpDate(expDate);
		        
		        String beginDateStr = invitation.getBeginDate();
		        if(StringUtils.isNotEmpty(beginDateStr)){
		            Timestamp effDate = DateUtils.getTimestamp(beginDateStr , DateUtils.DATE_FORMAT_3);
		            invationEntity.setEffDate(effDate);
		            invitation.setEffDate(effDate);
		        }
		        List<PaperPart> paperParts = paperDao.getPaperPart(position.getPaperId());
		        perInvationEntity.setPaperSize(paperParts.size());
		        // 试卷内容，如：选择题（10道）、编程题（2道）、面试题（1道）
		        StringBuffer paperContent = new StringBuffer();
		        for (PaperPart part : paperParts)
		        {
		            if(!StringUtils.isBlank(paperContent.toString()))
		            {
		                paperContent.append("、");
		            }
		            paperContent.append(part.getPartDesc()+"（"+part.getQuestionNum()+"道）");
		            // 如果有面试题，设置面试题标识为1
		            if(part.getId().getPartSeq() == PaperPartSeqEnum.INTEVEIW.getValue())
		            {
		                invationEntity.setHasInterview(Constants.POSITIVE);
		            }
		        }
		        perInvationEntity.setPaperContent(paperContent.toString()); // 试卷内容
		        Double avgTime = paperDao.getPaperTotalTime(position.getPaperId());
		        avgTime = Math.ceil(avgTime/60);
		        perInvationEntity.setTotalTime(avgTime.intValue()); // 大概作答时间
//		            invationEntity.setTotalTime(paperDao.getPaperTotalTime(position.getPaperId())); // 大概作答时间
		        csp = configSysParamDao.getConfigSysParameters(ConfigSysParam.INVATIONURL);
		        String url = csp.getParamValue();
		        
		        if (invitation.getTestId() == null) {
		            String passport = PassportGenerator.getRandomPassport(Constants.PASSPORTLENGTH);
		            invitation.setPassport(passport);
		            invitation.setPositionId(positionId);
		            
		            // 保存应聘人测评信息 
		            CandidateTest candidateTest = insertCandidateTest(invitation, invitationMail);
		            invitation.setTestId(candidateTest.getTestId());
		        }
		        invitation.setEmployerId(employerId);
		        
		        inviteDaoImpl.saveOrUpdate(invitation);
		        
		        // 发送邀请邮件
		        if(mailSenderInfo == null)
		            mailSenderInfo = MailSenderInfo.getMailSenderInfo(companyEmailserverDaoImpl.getEntity(company.getCompanyId(), "companyId"));
		        // 退信地址，暂时先设定为公司邮箱
		        // mailSenderInfo.setBounceAddr(employer.getEmployerAcct());
		        
		        
		        int state = 0;
		        // 生成通行证和url
		        String invatitionUrl = url + "/" + invitation.getTestId() + "/" + invitation.getPassport();
		        invitation.setInvitationUrl(invatitionUrl);
		        perInvationEntity.setUrl(invatitionUrl);
		        invationEntity.setCandidateName(invitation.getCandidateName());
		        invationEntity.setSelfContext(invitation.getSelfContext());
		        pfResponse.setUrl(invatitionUrl);
		        // 邮件标题
		        String mailSubject = "";
		        mailSubject = invationEntity.getSubject();
		        mailSenderInfo.setSubject(mailSubject);
		        
		        invitation.setInvitationState(state);
		        invitation.setInvitationDate(inviteDate);
		        // 失效时间 - 第七天23:59点
		        String dateStr = DateUtils.getDateStr(DateUtils.timestamp2Date(expDate) , DateUtils.DATE_FORMAT_3);
		        Timestamp intExpDate = DateUtils.getTimestamp(dateStr, DateUtils.DATE_FORMAT_3);
		        invitation.setExpDate(intExpDate);
		        invitation.setStateDate(new Date(System.currentTimeMillis()));
		        inviteDaoImpl.saveOrUpdate(invitation);
		        
		        
		        if (StringUtils.isEmpty(pfResponse.getCode()))
		            pfResponse.setCode(FuncBaseResponse.SUCCESS);
//					pfResponse.setTestId(invitation.getTestId());
		    }
		    
		    
		    int state = 0;
			try {
				mailSenderInfo.setToAddress(invitationMulti.getCandidateEmail());
				context.put("entity", invationEntity);
				String pageString = templateHost.makeFileString(TemplateHost.VM_INVITATIONHTMLMULTI);
				mailSenderInfo.setContent(pageString);
				SimpleMailSender.sendHtmlMail(mailSenderInfo);
				 state = 1;
			} catch (Exception e) {
				logger.error("send email failed", e);
				pfResponse.setCode(FuncBaseResponse.FAILED);
				pfResponse.setCandidateEmail(invitationMulti.getCandidateEmail());
				pfResponse.setCandidateName(invitationMulti.getCandidateName());
				for(int i = 0; i< invitations.size(); i++){
//					PFResponse pfResponse = results.get(i);
					Invitation invitation = invitations.get(i);
					String excName = e.getClass().getName();
					String errtxt = mailExceptionMap.get(excName);
					if (!StringUtils.isEmpty(errtxt))
						invitation.setInvitationErrtxt(errtxt);
					else
						invitation.setInvitationErrtxt("未知原因");
					pfResponse.setMessage(invitation.getInvitationErrtxt());
				    state = 0;
					PositionLog positionLog = new PositionLog();
					positionLog.setEmployerId(invitation.getEmployerId());
					positionLog.setLogTime(new Timestamp(System.currentTimeMillis()));
					positionLog.setPositionId(invitation.getPositionId());
					positionLog.setPositionState(1);
					positionLog.setStateId(invitation.getTestId());
					positionDao.savePositionLog(positionLog);
//					sendWXInvitationFailed(invitation.getEmployerId(), invitation, invationEntity);
				}
				
			}
			for(Invitation invitation : invitations){
				invitation.setInvitationState(state);
				inviteDaoImpl.saveOrUpdate(invitation);
			}
			logger.info("invite cost time is {}", (System.currentTimeMillis() - startTime));
		    return pfResponse;
		   
		} catch (Exception e) {
			logger.error("error send invitations.......", e);
			throw new PFServiceException(e.getMessage());
		}
	}
	
	@Override
	public OnlineExamReqResult onlineExamReqInvite(Invitation invitation) throws PFServiceException {
       try {
            Position position = positionDao.getEntity(invitation.getPositionId());
            Timestamp currentTime = DateUtils.getCurrentTimestamp();
            // 1. 用户绑定
            Candidate candidate = candidateDao.getCandidate(invitation.getCandidateName(), invitation.getCandidateEmail(), invitation.getOpenId());
            // 2. 申测去重
            OnlineExamReq examReqDb = onlineExamReqDaoImpl.getOnlineExamReq(candidate.getCandidateId(), position.getPositionId());
            if (null!=examReqDb)
            {
                return new OnlineExamReqResult(OnlineExamReqResult.STATUS_REPEAT,examReqDb.getPassport());
            }
            // 3. 生成 candidate_test 记录
            CandidateTest candidateTest = new CandidateTest();
            candidateTest.setPositionId(invitation.getPositionId());
            candidateTest.setTestState(0);
            candidateTest.setTestResult(0);
            candidateTest.setPaperState(0);
            candidateTest.setBreakTimes(0);
            candidateTest.setElapsedTime(0);
            candidateTest.setSwitchTimes(0);
            candidateTest.setFreshTimes(0);
            candidateTest.setPassport(PassportGenerator.getRandomPassport(Constants.PASSPORTLENGTH)); // 10位passport
            candidateTest.setCandidateId(candidate.getCandidateId());
            candidateTest.setPaperId(position.getPaperId());
//            candidateTest.setCanWithOutCamera(invitationMail.getCanWithOutCamera()); //是否允许没有摄像头
            candidateTest.setTestPositionName(position.getPositionName()); //测评名称
            candidateTestDaoImpl.saveCandidateTest(candidateTest);
            // 4. 生成 invitation 记录
            invitation.setTestId(candidateTest.getTestId());
            invitation.setPassport(candidateTest.getPassport());
            invitation.setEmployerId(position.getEmployerId());
            invitation.setInvitationDate(currentTime);
            // 生效时间：用户打开申测时的自定义时间 / 系统当前时间+6天
            Timestamp expDate = position.getExamEndDate();
            if(null == expDate)
            {
                ConfigSysParameters csp = configSysParamDao.getConfigSysParameters(ConfigSysParam.EFFDAY);
                expDate = DateUtils.getOffsetDate(currentTime, Integer.parseInt(csp.getParamValue()));
            }
            invitation.setExpDate(expDate); // 失效时间
            invitation.setInvitationState(1); // 邀请状态1成功
            invitation.setInvitationDate(currentTime); //状态更新时间
            // 生成通行证和url
            ConfigSysParameters csp = configSysParamDao.getConfigSysParameters(ConfigSysParam.INVATIONURL);
            String invatitionUrl = csp.getParamValue() + "/" + invitation.getTestId() + "/" + invitation.getPassport();
            invitation.setInvitationUrl(invatitionUrl);
            inviteDaoImpl.saveOrUpdate(invitation);
            // 5. 生成 online_exam_req 记录
            OnlineExamReq examReq = new OnlineExamReq();
            examReq.setStatus(1); // 状态正常
            examReq.setPositionId(position.getPositionId());
            examReq.setCandidateId(candidate.getCandidateId()); // candidate id
            examReq.setTestId(candidateTest.getTestId()); // candidate_test id
            examReq.setCreateDate(currentTime); // 
            // 口令去重
            String passport = RandomStringUtils.randomAlphanumeric(8).toLowerCase();
            do
            {
                passport = RandomStringUtils.randomAlphanumeric(8).toLowerCase();
            } while (null!=onlineExamReqDaoImpl.getOnlineExamReqByPassport(passport)); // 如果passport重复那么继续随机..
            examReq.setPassport(passport);
            onlineExamReqDaoImpl.save(examReq);
            return new OnlineExamReqResult(OnlineExamReqResult.STATUS_SUCCESS,examReq.getPassport());
        } catch (Exception e) {
            logger.error("generate onlineExamReqInvite error.......", e);
            throw new PFServiceException(e.getMessage());
        }
	 }
	
	private InviteResult inviteForGroupPosition(Invitation invitation) throws PFServiceException{
		InvitationForMulti invitationMulti = new InvitationForMulti();
		try {
			PropertyUtils.copyProperties(invitationMulti, invitation);
			
			List<PositionRelation> positionRelations = positionRelationDao.getPositionRelationByPositionGroupId(invitation.getPositionId());
			List<Integer> mustPositionIds = new ArrayList<Integer>();
			List<Integer> optionalPositionIds = new ArrayList<Integer>();
			for(PositionRelation pr : positionRelations){
				if(pr.getRelation() == Constants.POSITION_RELATION_MUST){
					mustPositionIds.add(pr.getId().getPositionId());
				}else if(pr.getRelation() == Constants.POSITION_RELATION_CHOOSE){
					optionalPositionIds.add(pr.getId().getPositionId());
				}else{
					logger.error("not support relation " + pr.getRelation());
					throw new PFServiceException("not support relation " + pr.getRelation());
				}
			}
			invitationMulti.setMustAnswerPositionIds(mustPositionIds);
			invitationMulti.setOptionalAnswerPositionIds(optionalPositionIds);
			invitationMulti.setAddOnPositionIds(new ArrayList<Integer>());//防止空指针
			InviteResult result =  inviteForMulti(invitationMulti);
			return result;
		} catch (Exception e) {
			logger.error("error to inviteForGroupPosition ", e);
		    throw new PFServiceException(e);	
		}
	}

	@Override
	public List<InvitationInfo> getFailedInvitations(GetInvitationInfoParam param) throws PFServiceException {
		    param.setInvitationState(0);
			Map<String, Integer> candidateIds = new HashMap<String, Integer>();
			Page oldPage = param.getPage();
			Page newPage = new Page(Integer.MAX_VALUE,1);
			param.setPage(newPage);
			List<Invitation> list = inviteDaoImpl.getInvitaionInfo(param);
			List<InvitationInfo> results = new ArrayList<InvitationInfo>();
			if (!CollectionUtils.isEmpty(list))
				Collections.sort(list, new Comparator<Invitation>() {

					@Override
					public int compare(Invitation o1, Invitation o2) {
						return o2.getExpDate().compareTo(o1.getExpDate());
					}
				});
				for (Invitation invitation : list) {
					if(candidateIds.containsKey(invitation.getCandidateName() +"_" + invitation.getCandidateEmail())){//已经包含
						logger.debug("failed invitations contains more than one for candidateInfo {} ", invitation.getCandidateName() +"_" + invitation.getCandidateEmail());
						continue;
					}
					candidateIds.put(invitation.getCandidateName() +"_" + invitation.getCandidateEmail(), 1);
					
					InvitationInfo invitationFailedInfo = new InvitationInfo();
					try {
						BeanUtils.copyProperties(invitationFailedInfo, invitation);
//						invitationFailedInfo.setTestId(null);
					} catch (IllegalAccessException e) {
						throw new PFServiceException(e);
					} catch (InvocationTargetException e) {
						throw new PFServiceException(e);
					}
					invitationFailedInfo.setInvitationDateDesc(PrettyTimeMaker.format(invitationFailedInfo.getInvitationDate()));
					
					results.add(invitationFailedInfo);
				}
			int fromIndex = oldPage.getFirstRow();
			int toIndex = oldPage.getLastRow();
			if (fromIndex > results.size())
				fromIndex = results.size();
			if (toIndex > results.size())
				toIndex = results.size();
			List<InvitationInfo> subs =  results.subList(fromIndex, toIndex);
			for(InvitationInfo info : subs){
				Long testId = info.getTestId();
				CandidateTest test = candidateTestDaoImpl.getEntity(testId);
				Invitation invitation = inviteDaoImpl.getEntity(testId);
				info.setCanWithOutCamera(test.getCanWithOutCamera());
				info.setTestPositionName(test.getTestPositionName());
				Timestamp effDate = invitation.getEffDate();
				if(effDate != null){
					info.setBeginDate(DateUtils.getMonthStr(effDate.getTime(),  DateUtils.DATE_FORMAT_3));
				}
				Timestamp expDate = invitation.getExpDate();
				info.setValidDate(DateUtils.getMonthStr(expDate.getTime(), DateUtils.DATE_FORMAT_3));
				
				info.setValidTimeLeft((expDate.getTime() - System.currentTimeMillis()) /1000);
				info.setTestId(null);
			}
			return subs;
			  
	}

	@Override
	public PFResponse deteleByCandidate(Invitation invitation)  {
		PFResponse pf = new PFResponse();
		pf.setCode(FuncBaseResponse.SUCCESS);
		 List<Invitation> fails  = inviteDaoImpl.getFailedInvitations(invitation.getEmployerId(), invitation.getPositionId(), invitation.getCandidateName(), invitation.getCandidateEmail());
         if(fails.size() > 0){
         	logger.debug("delete failed invitation size {} for employerId {} , positionId {},candidateName {} , candidateEmail {} ", new Object[]{fails.size(),invitation.getEmployerId(), invitation.getPositionId(), invitation.getCandidateName(), invitation.getCandidateEmail()});
             for(Invitation inv : fails){
             	inviteDaoImpl.delete(inv.getTestId());
             	candidateTestDaoImpl.delete(inv.getTestId());
             }
         }
         return pf;
	}

	@Override
	public InviteResult inviteForNoMailOfOut(OutInvitationRequest invitationReq)  throws PFServiceException {
		try {
			logger.debug("inviteForNoMailOfOut  the invitationReq is {} ", invitationReq);
			InviteResult res = new InviteResult();
			res.setCode(FuncBaseResponse.SUCCESS);
            Position position = positionDao.getEntity(invitationReq.getPaperId());
            Timestamp currentTime = DateUtils.getCurrentTimestamp();
            // 1. 用户绑定
            int   candidateId = candidateDao.getCandidateId(invitationReq.getCandidateName(), invitationReq.getCandidateEmail());
            // 3. 生成 candidate_test 记录
            CandidateTest candidateTest = new CandidateTest();
            candidateTest.setPositionId(invitationReq.getPaperId());
            candidateTest.setTestState(0);
            candidateTest.setTestResult(0);
            candidateTest.setPaperState(0);
            candidateTest.setBreakTimes(0);
            candidateTest.setElapsedTime(0);
            candidateTest.setSwitchTimes(0);
            candidateTest.setFreshTimes(0);
            candidateTest.setPassport(PassportGenerator.getRandomPassport(Constants.PASSPORTLENGTH)); // 10位passport
            candidateTest.setCandidateId(candidateId);
            candidateTest.setPaperId(position.getPaperId());
//            candidateTest.setCanWithOutCamera(invitationMail.getCanWithOutCamera()); //是否允许没有摄像头
            candidateTest.setTestPositionName(position.getPositionName()); //测评名称
            candidateTestDaoImpl.saveCandidateTest(candidateTest);
            // 4. 生成 invitation 记录
            Invitation invitation = new Invitation();
            
            invitation.setPositionId(invitationReq.getPaperId());
            invitation.setCandidateName(invitationReq.getCandidateName());
            invitation.setCandidateEmail(invitationReq.getCandidateEmail());
            invitation.setTestId(candidateTest.getTestId());
            invitation.setPassport(candidateTest.getPassport());
            invitation.setEmployerId(position.getEmployerId());
            invitation.setInvitationDate(currentTime);
            // 生效时间：用户打开申测时的自定义时间 / 系统当前时间+6天
            Timestamp expDate = position.getExamEndDate();
            if(null == expDate)
            {
                ConfigSysParameters csp = configSysParamDao.getConfigSysParameters(ConfigSysParam.EFFDAY);
                expDate = DateUtils.getOffsetDate(currentTime, Integer.parseInt(csp.getParamValue()));
            }
            invitation.setExpDate(expDate); // 失效时间
            invitation.setInvitationState(1); // 邀请状态1成功
            invitation.setInvitationDate(currentTime); //状态更新时间
            // 生成通行证和url
            ConfigSysParameters csp = configSysParamDao.getConfigSysParameters(ConfigSysParam.INVATIONURL);
            String invatitionUrl = csp.getParamValue() + "/" + invitation.getTestId() + "/" + invitation.getPassport();
            if(StringUtils.isNotEmpty(invitationReq.getRedirectUrl())){
            	invatitionUrl += "?redirectUrl=" + invitationReq.getRedirectUrl();
            }
            invitation.setInvitationUrl(invatitionUrl);
            inviteDaoImpl.saveOrUpdate(invitation);
            res.setUrl(invatitionUrl);
            return res;
        } catch (Exception e) {
            logger.error("generate onlineExamReqInvite error.......", e);
            throw new PFServiceException(e.getMessage());
        }
	}
}
