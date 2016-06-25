package com.ailk.sets.platform.service;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.mail.MessagingException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ailk.sets.grade.dao.intf.ICandidateInfoExtDao;
import com.ailk.sets.grade.intf.BaseResponse;
import com.ailk.sets.grade.intf.GetCandidateInfoResponse;
import com.ailk.sets.grade.intf.GetCandidateInfoResponse.UserInfo;
import com.ailk.sets.grade.intf.GetQInfoResponse;
import com.ailk.sets.grade.intf.IGradeService;
import com.ailk.sets.grade.intf.report.GetReportResponse;
import com.ailk.sets.grade.intf.report.GetReportSummaryResponse;
import com.ailk.sets.grade.intf.report.OverallItem;
import com.ailk.sets.platform.common.ConfigSysParam;
import com.ailk.sets.platform.common.MailSenderInfo;
import com.ailk.sets.platform.dao.impl.QbDifficultyLevelDaoImpl;
import com.ailk.sets.platform.dao.impl.QbQuestionSkillDaoImpl;
import com.ailk.sets.platform.dao.impl.QbSkillDaoImpl;
import com.ailk.sets.platform.dao.interfaces.ICandidateDao;
import com.ailk.sets.platform.dao.interfaces.ICandidateInfoDao;
import com.ailk.sets.platform.dao.interfaces.ICandidateTestDao;
import com.ailk.sets.platform.dao.interfaces.ICompanyDao;
import com.ailk.sets.platform.dao.interfaces.ICompanyEmailserverDao;
import com.ailk.sets.platform.dao.interfaces.IConfigDao;
import com.ailk.sets.platform.dao.interfaces.IConfigSysParamDao;
import com.ailk.sets.platform.dao.interfaces.IEmployerAuthDao;
import com.ailk.sets.platform.dao.interfaces.IEmployerDao;
import com.ailk.sets.platform.dao.interfaces.IEmployerTrialDao;
import com.ailk.sets.platform.dao.interfaces.IInviteDao;
import com.ailk.sets.platform.dao.interfaces.IPaperDao;
import com.ailk.sets.platform.dao.interfaces.IPaperQuestionDao;
import com.ailk.sets.platform.dao.interfaces.IPaperSkillDao;
import com.ailk.sets.platform.dao.interfaces.IPositionDao;
import com.ailk.sets.platform.dao.interfaces.IPositionSeriesDao;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionDao;
import com.ailk.sets.platform.dao.interfaces.IReportDao;
import com.ailk.sets.platform.dao.interfaces.ISchoolReportDao;
import com.ailk.sets.platform.domain.PaperSkill;
import com.ailk.sets.platform.domain.PassEmailEntity;
import com.ailk.sets.platform.domain.QbDifficultyLevel;
import com.ailk.sets.platform.domain.QbQuestionSkill;
import com.ailk.sets.platform.domain.ReportEntity;
import com.ailk.sets.platform.domain.paper.PaperQuestion;
import com.ailk.sets.platform.exception.PFDaoException;
import com.ailk.sets.platform.intf.cand.domain.CandidateInfoExt;
import com.ailk.sets.platform.intf.cand.domain.Employer;
import com.ailk.sets.platform.intf.cand.domain.Invitation;
import com.ailk.sets.platform.intf.common.ConfigCodeType;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.common.FuncBaseResponse;
import com.ailk.sets.platform.intf.common.OutResponse;
import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.common.PFResponseData;
import com.ailk.sets.platform.intf.domain.Candidate;
import com.ailk.sets.platform.intf.domain.Company;
import com.ailk.sets.platform.intf.domain.PositionSeries;
import com.ailk.sets.platform.intf.domain.paper.Paper;
import com.ailk.sets.platform.intf.empl.domain.CandidateReport;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;
import com.ailk.sets.platform.intf.empl.domain.ConfigReport;
import com.ailk.sets.platform.intf.empl.domain.ConfigReportPart;
import com.ailk.sets.platform.intf.empl.domain.EmployerTrialApply;
import com.ailk.sets.platform.intf.empl.domain.GetQInfoResponseModel;
import com.ailk.sets.platform.intf.empl.domain.PaperEssayModelInfo;
import com.ailk.sets.platform.intf.empl.domain.PaperInteModelInfo;
import com.ailk.sets.platform.intf.empl.domain.PaperModel;
import com.ailk.sets.platform.intf.empl.domain.PaperModelPart;
import com.ailk.sets.platform.intf.empl.domain.PaperObjectModelInfo;
import com.ailk.sets.platform.intf.empl.domain.PaperSubjectModelInfo;
import com.ailk.sets.platform.intf.empl.domain.PaperSubjectModelQuestion;
import com.ailk.sets.platform.intf.empl.domain.PaperVideoModelInfo;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;
import com.ailk.sets.platform.intf.empl.service.IOutCallService;
import com.ailk.sets.platform.intf.empl.service.IReport;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.Mapping;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.intf.model.candidateReport.CandReportAndInfo;
import com.ailk.sets.platform.intf.model.candidateReport.PositionGroupReport;
import com.ailk.sets.platform.intf.model.param.GetReportParam;
import com.ailk.sets.platform.intf.model.position.PositionStatistics;
import com.ailk.sets.platform.intf.model.qb.QbSkill;
import com.ailk.sets.platform.intf.model.wx.HttpClientUtil;
import com.ailk.sets.platform.intf.model.wx.OutCallConstants;
import com.ailk.sets.platform.intf.model.wx.WXCommunicator;
import com.ailk.sets.platform.intf.model.wx.WXInterfaceUrl;
import com.ailk.sets.platform.intf.model.wx.msg.Article;
import com.ailk.sets.platform.intf.model.wx.msg.TextPicMsg;
import com.ailk.sets.platform.intf.school.domain.SchoolCandidateReport;
import com.ailk.sets.platform.service.local.IQbQuestionService;
import com.ailk.sets.platform.util.DateUtils;
import com.ailk.sets.platform.util.PFUtils;
import com.ailk.sets.platform.util.PrettyTimeMaker;
import com.ailk.sets.platform.util.SimpleMailSender;
import com.ailk.sets.platform.util.TemplateHost;

@Transactional(rollbackFor = Exception.class)
public class ReportImpl implements IReport {

	@Autowired
	private ICandidateDao candidateDaoImpl;

	@Autowired
	private IConfigSysParamDao configSysParamDao;

	@Autowired
	private IEmployerDao employerDaoImpl;

	@Autowired
	private IConfigDao configDao;

	@Autowired
	private IPositionDao positionDao;

	@Autowired
	private IInviteDao inviteDao;

	@Autowired
	private IPaperDao paperDao;

	@Autowired
	private ICandidateTestDao candidateTestDao;

	@Autowired
	private IReportDao reportDao;

	@Autowired
	private IGradeService gradeService;

	@Autowired
	private IInviteDao inviteDaoImpl;

	@Autowired
	private ICompanyEmailserverDao companyEmailserverDaoImpl;

	@Autowired
	private IQbQuestionService qbQuestionService;

	@Autowired
	private QbSkillDaoImpl qbSkillDaoImpl;

	@Autowired
	private ICandidateTestDao candidateTestDaoImpl;

	@Autowired
	private ICandidateInfoDao candidateInfoDao;

	@Autowired
	private QbDifficultyLevelDaoImpl qbDifficultyLevelDaoImpl;

	@Autowired
	private ICandidateDao candidateDao;

	@Autowired
	private IEmployerAuthDao employerAuthDao;

	@Autowired
	private IPositionSeriesDao seriesDao;

	@Autowired
	private IQbQuestionDao questionDao;

	@Autowired
	private QbQuestionSkillDaoImpl qbQuestionSkillDao;

	@Autowired
	private IPaperSkillDao paperSkillDao;
	@Autowired
	private IPaperQuestionDao paperQuestionDao;
	
    @Autowired
	private IOutCallService outCallService;
    @Autowired
    private IEmployerTrialDao employerTrialDao;
    @Autowired
    private ICompanyDao companyDao;
    
    @Autowired
    private ISchoolReportDao schoolReportDao;
    @Autowired
    private ICandidateInfoExtDao candidateInfoExtDao;
    

	private Logger logger = LoggerFactory.getLogger(ReportImpl.class);

	public PositionStatistics setTestResult(int employerId, int positionId,
			long testId, int choose) throws PFServiceException {
		PositionStatistics statistics = new PositionStatistics();
		long time1 = System.currentTimeMillis();
		try {
			CandidateReport cr = reportDao.getEntity(testId);
			Position position = positionDao.getEntity(positionId);
			Invitation invitaion = inviteDao.getEntity(testId);
			if(position.getEmployerId() != employerId &&  invitaion.getEmployerId() != employerId){//既不是创建人也不是邀请人，则不允许操作
				logger.error("no power for testId {} of employerId {} ", testId, employerId);
				statistics.setCode(FuncBaseResponse.FAILED);
				statistics.setMessage("权限错误");
				return statistics;
			}
			// add by lipan 14/9/11 变更报告状态之前现验证一下报告当前状态 
			CandidateTest candidateTestDb = candidateTestDao.getEntity(testId);
			// 数据库中的报告状态非待定状态都是不合法的请求操作。报告状态有：0-待定; 1-通过; 2-淘汰;3-复试
			if (candidateTestDb.getTestResult()!=0)
            {
			    logger.error("fcking error ！报告不存在或者报告已被处理");
                statistics.setCode(FuncBaseResponse.SUCCESS);
                statistics.setReportStatus(candidateTestDb.getTestResult()); // 0-待定; 1-通过; 2-淘汰;3-复试
                statistics.setMessage("当前报告已被处理");
                return statistics;
            }
			
			boolean result = candidateTestDao.setTestResult(testId, choose);
			if (result)
				statistics.setCode(FuncBaseResponse.SUCCESS);
			else
				statistics.setCode(FuncBaseResponse.FAILED);
			
			try {
				int noticeStatus=0;
				if (choose == 1) {
//					sendChooseEmail(employerId, positionId, testId, cr);
//					sendChooseWxMessage(employerId, positionId, testId, cr);
					noticeStatus= 4; //推荐
				} else if (choose == 2) {
					sendPassEmail(employerId, positionId, testId, cr);
//					sendPassWXMessage(testId);
					noticeStatus= 5; //淘汰
				}
				//测试状态发生变更时通知第三方，modify by zengjie 14/8/18
				if(noticeStatus>0){
					outCallService.updateMrReportStatus(testId,noticeStatus);
				}
			} catch (MessagingException e) {
				logger.error("send choose or pass email error ", e);
			}
			long time2 = System.currentTimeMillis();
			logger.debug("waste {} time for send mail and wx for testId {} ", (time2 - time1) , testId);
			positionDao.delPositionLog(positionId, cr.getTestId());
			statistics.setChosenNum(candidateTestDao.getCountByState(employerId,
					positionId, FuncBaseResponse.CONFIRMED));
			statistics.setInvitatedNum(inviteDao.getInvitationNumber(
					employerId, positionId));
			statistics.setInvitationFailNum(inviteDao
					.getInvitationFailedNumber(employerId, positionId));
			statistics.setEliminationNum(candidateTestDao.getCountByState(employerId,
					positionId, FuncBaseResponse.PASSED));
			statistics.setReportNum(paperDao.getReportUnreadCount(employerId,positionId));
			statistics.setTodoNum(candidateTestDao.getCountByState(employerId,positionId,
					FuncBaseResponse.TOBECONFIRMED));
			long time3 = System.currentTimeMillis();
			logger.debug("waste {} time for countInfo for testId {} ", (time3 - time2) , testId);
			return statistics;
		} catch (Exception e) {
			logger.error("set test result error ", e);
			throw new PFServiceException(e.getMessage());
		}
	}

	@Override
	public OutResponse setTestResultOnly(int employerId, long testId, int choose) {
		logger.debug("setTestResultOnly for testId {}, choose {} , employerId  " + employerId, testId, choose);
		OutResponse statistics = new OutResponse();
		//boolean result = candidateTestDao.setTestResult(testId, choose);
	    //1.获取positionId
		CandidateTest test = candidateTestDao.getCandidateTest((int) testId);
		if(null!=test){
			int positionId = test.getPositionId();
			try {
				PFResponse rsp =this.setTestResult(employerId, positionId, testId, choose);
				if(null!=rsp){
					statistics.setStatus(OutCallConstants.OUT_STATUS_SUCCESS);
					statistics.setMessage("success");
				}else{
					statistics.setStatus(OutCallConstants.OUT_STATUS_ERROR);
					statistics.setMessage("error");
				}
			} catch (Exception e) {
				statistics.setStatus(OutCallConstants.OUT_STATUS_ERROR);
				statistics.setMessage("error");
				logger.error("setTestResultOnly error:"+e.getMessage());
			}
		}else{
			statistics.setStatus(OutCallConstants.OUT_STATUS_ERROR);
		}
		return statistics;
	}
	
	/**
	 * 如果推荐，则向职位的招聘人发送一封邮件
	 * 
	 * @param employerId
	 * @param positionId
	 * @param testId
	 * @param cr
	 * @throws MessagingException
	 */
	private void sendChooseEmail(int employerId, int positionId, long testId,
			CandidateReport cr) throws MessagingException {
		try {
			Company company = inviteDaoImpl.getCompanyInfo(employerId);
			Position position = positionDao.getEntity(positionId);
			ReportEntity reportEntity = new ReportEntity();
			
			reportEntity.setLevel(configDao.getConfigCodeName(
					ConfigCodeType.POSITION_LEVEL, position.getLevel() + ""));
			PositionSeries series = seriesDao.getEntity(position.getSeriesId());
			if(series != null)
			{
			    reportEntity.setSeriesName(series.getSeriesName());
			}
			GetCandidateInfoResponse gcr = gradeService.getCandidateInfo(
					cr.getCandidateId(), position.getEmployerId(),
					position.getPositionId());
			GetReportSummaryResponse grsr = gradeService.getReportSummary(cr
					.getTestId());
			reportEntity.setItems(grsr.getItems());
			reportEntity.setCandidateEmail(gcr.getEmail());
			reportEntity.setCandidateName(gcr.getName());
			
			// add by lipan 2014年7月22日15:30:18 testpositionName如果为空则为  programeLanguage + level + "工程师"...
			CandidateTest candidateTest = candidateTestDaoImpl.getEntity(testId);
			if(StringUtils.isBlank(candidateTest.getTestPositionName()))
			{
			    reportEntity.setTestPositionName(reportEntity.getPosition());
			}else
			{
			    reportEntity.setTestPositionName(candidateTest.getTestPositionName());
			}
			GetReportResponse grr = gradeService.getReport(cr.getTestId());
			if (grr.getErrorCode() == BaseResponse.SUCCESS) {
				reportEntity.setScore(grr.getSummary().getScore());
				
				// add by lipan 2014年7月21日11:05:37  是否有头像 
				if(StringUtils.isBlank(candidateTest.getCandidatePic())) // 没有头像
				{
		            reportEntity.setHasPortrait(Constants.NEGATIVE);
				}else
				{
				    reportEntity.setHasPortrait(Constants.POSITIVE);
				    reportEntity.setPortrait(candidateTest.getCandidatePic());
				}

				MailSenderInfo mailSenderInfo = MailSenderInfo
						.getMailSenderInfo(companyEmailserverDaoImpl.getEntity(
								company.getCompanyId(), "companyId"));
				if (employerId == position.getEmployerId())
					mailSenderInfo.setSubject(reportEntity.getSubject());
				else {
					Employer em = employerDaoImpl.getEntity(employerId);
					reportEntity.setHrName(em.getEmployerName());
					mailSenderInfo.setSubject(reportEntity
							.getRecommendSubject());
				}
				reportEntity.setReportUrl(configSysParamDao
						.getConfigSysParameters(ConfigSysParam.REPORTURL)
						.getParamValue()
						+ "/"
						+ positionId
						+ "/"
						+ testId
						+ "/"
						+ cr.getReportPassport());
				Employer emSelf = employerDaoImpl.getEntity(position
						.getEmployerId());
				reportEntity.setEmployerName(emSelf.getEmployerName());
				TemplateHost templateHost = new TemplateHost();
				VelocityContext context = templateHost.getContext();
				context.put("entity", reportEntity);
				mailSenderInfo.setContent(templateHost
						.makeFileString(TemplateHost.VM_PUBLICTEMPLATE));

				mailSenderInfo.setToAddress(emSelf.getEmployerAcct());
				SimpleMailSender.sendHtmlMail(mailSenderInfo);
			}
		} catch (Exception e) {
			throw new MessagingException("异常", e);
		}
	}

	/**
	 * 推荐后发送微信消息
	 * 
	 * @throws Exception
	 * 
	 */
	private void sendChooseWxMessage(int employerId, int positionId,
			long testId, CandidateReport cr) throws Exception {
		Position position = positionDao.getEntity(positionId);
		Employer employer = employerDaoImpl.getEntity(employerId);
		Employer emSelf = employerDaoImpl.getEntity(position.getEmployerId());
		if (StringUtils.isNotEmpty(emSelf.getOpenId())) {

			CandidateTest candidateTest = candidateTestDao.getEntity(cr
					.getTestId());
			Candidate candidate = candidateDao.getEntity(candidateTest
					.getCandidateId());
			WXCommunicator wxCommunicator = new WXCommunicator(
					HttpClientUtil.getHttpClient());
			String token = wxCommunicator
					.getAccessToken(
							configSysParamDao
									.getConfigParamValue(ConfigSysParam.EMPLOYERAPPID),
							configSysParamDao
									.getConfigParamValue(ConfigSysParam.EMPLOYERAPPSECRET));
			String title = "";
			String content = configSysParamDao
					.getConfigParamValue(ConfigSysParam.EMPLOYEEHANDLERCONTENT);
			ReportEntity reportEntity = new ReportEntity();
			reportEntity.setLevel(configDao.getConfigCodeName(
					ConfigCodeType.POSITION_LEVEL, position.getLevel() + ""));
			PositionSeries series = seriesDao.getEntity(position.getSeriesId());
			if(series != null)
            {
                reportEntity.setSeriesName(series.getSeriesName());
            }
			
			// add by lipan 2014年7月22日15:30:18 testpositionName如果为空则为  seriesName + level + "工程师"...
            if(StringUtils.isBlank(candidateTest.getTestPositionName()))
            {
                reportEntity.setTestPositionName(reportEntity.getPosition());
            }else
            {
                reportEntity.setTestPositionName(candidateTest.getTestPositionName());
            }
			
			if (employerId == position.getEmployerId()) {
				title = configSysParamDao
						.getConfigParamValue(ConfigSysParam.EMPLOYEERECOMMENDTITLE);
				title = MessageFormat.format(title,
						candidate.getCandidateName(),
						reportEntity.getTestPositionName());
			} else {
				title = configSysParamDao
						.getConfigParamValue(ConfigSysParam.EMPLOYEERECOMMENDTITLEHR);
				title = MessageFormat.format(title, employer.getEmployerName(),
						candidate.getCandidateName(),
						reportEntity.getTestPositionName());
			}

			GetReportSummaryResponse grsr = gradeService.getReportSummary(cr
					.getTestId());
			StringBuilder scoreDetail = new StringBuilder();
			for (OverallItem item : grsr.getItems()) {
				scoreDetail.append(item.getName());
				scoreDetail.append("：");
				scoreDetail.append((item.getScore() == null) ? "未打" : item
						.getScore().toString());
				scoreDetail.append("分\n");
				logger.debug("item name is " + item.getName() + ":"
						+ " score is " + item.getScore());
				logger.debug("but my score replace is " + item.getScore() == null ? "未打"
						: item.getScore() + "分\n");
			}
			content = MessageFormat.format(content, cr.getGetScore(), scoreDetail.toString());

			String url = configSysParamDao
					.getConfigParamValue(ConfigSysParam.SCHOOLREPORTURL)
					+ "?testId="
					+ candidateTest.getTestId()
					+ "&employerId="
					+ emSelf.getEmployerId() + "&openId=" + emSelf.getOpenId();
			;

			TextPicMsg textPicMsg = new TextPicMsg(emSelf.getOpenId());
			textPicMsg.addArticle(new Article(title, content, url,
					candidateTest.getCandidatePic()));
			String sendMsg = PFUtils.toJson(textPicMsg);
			sendMsg = sendMsg.replace(Constants.COMMON_BR, "\n");
			logger.debug("send choose message is " + sendMsg);
			wxCommunicator.sendMessage(sendMsg, token);
		}
	}

	/**
	 * 发送淘汰邮件
	 * 
	 * @param employerId
	 * @param positionId
	 * @param testId
	 * @param cr
	 * @throws MessagingException
	 */
	private void sendPassEmail(int employerId, int positionId, long testId,
			CandidateReport cr) throws MessagingException {
		Company company = inviteDaoImpl.getCompanyInfo(employerId);
		Position position = positionDao.getEntity(positionId);
		Candidate candidate = candidateDaoImpl.getEntity(cr.getCandidateId());
		CandidateTest candidateTest = candidateTestDao.getEntity(cr
                .getTestId());
		PassEmailEntity passEmailEntity = getPassEmailEntity(company, position , candidateTest);
		passEmailEntity.setCandidateName(candidate.getCandidateName());
		TemplateHost templateHost = new TemplateHost();
		VelocityContext context = templateHost.getContext();
		context.put("entity", passEmailEntity);
		MailSenderInfo mailSenderInfo = MailSenderInfo
				.getMailSenderInfo(companyEmailserverDaoImpl.getEntity(
						company.getCompanyId(), "companyId"));
		mailSenderInfo.setSubject(passEmailEntity.getSubject());
		mailSenderInfo.setContent(templateHost
				.makeFileString(TemplateHost.VM_PUBLICTEMPLATE));

		mailSenderInfo.setToAddress(candidate.getCandidateEmail());
		SimpleMailSender.sendHtmlMail(mailSenderInfo);
	}

	
	/**
	 * 获得淘汰信息
	 * @param company
	 * @param position
	 * @param candidateTest
	 * @return
	 */
	private PassEmailEntity getPassEmailEntity(Company company,
			Position position ,CandidateTest candidateTest) {
		PassEmailEntity passEmailEntity = new PassEmailEntity();
		Employer positionEmployer = employerDaoImpl.getEntity(position.getEmployerId());
		Company positionCompany = companyDao.getEntity(positionEmployer.getCompanyId());
		if(positionCompany != null)
		{
		    passEmailEntity.setCompanyName(positionCompany.getCompanyName());
		}
		passEmailEntity.setLevel(configDao.getConfigCodeName(
				ConfigCodeType.POSITION_LEVEL, position.getLevel() + ""));
		PositionSeries series = seriesDao.getEntity(position.getSeriesId());
		if(series != null)
        {
		    passEmailEntity.setSeriesName(series.getSeriesName());
        }
        
        // add by lipan 2014年7月22日15:30:18 testpositionName如果为空则为  seriesName + level + "工程师"...
        if(StringUtils.isBlank(candidateTest.getTestPositionName()))
        {
            passEmailEntity.setTestPositionName(passEmailEntity.getPosition());
        }else
        {
            passEmailEntity.setTestPositionName(candidateTest.getTestPositionName());
        }
		return passEmailEntity;
	}

	/**
	 * 发送淘汰微信
	 * @param testId
	 * @throws PFDaoException
	 * @throws UnsupportedEncodingException
	 */
	private void sendPassWXMessage(long testId) throws PFDaoException,
			UnsupportedEncodingException {
		WXCommunicator wxCommunicator = new WXCommunicator(
				HttpClientUtil.getHttpClient());
		Candidate candidate = candidateDaoImpl.getByTestId(testId);
		CandidateTest candidateTest = candidateTestDaoImpl.getEntity(testId);
		if (StringUtils.isNotEmpty(candidate.getOpenId())) {
			Position position = positionDao.getEntity(candidateTest
					.getPositionId());
			Company company = inviteDaoImpl.getCompanyInfo(position
					.getEmployerId());
			PassEmailEntity passEmailEntity = getPassEmailEntity(company,
					position ,candidateTest);
			String token = wxCommunicator
					.getAccessToken(
							configSysParamDao
									.getConfigParamValue(ConfigSysParam.EMPLOYEEAPPID),
							configSysParamDao
									.getConfigParamValue(ConfigSysParam.EMPLOYEEAPPSECRET));
			String text = configSysParamDao
					.getConfigParamValue(ConfigSysParam.EMPLOYERPASS);
			String companyName = "";
			if(company != null)
			{
			    companyName = company.getCompanyName();
			}
			text = MessageFormat.format(text,companyName ,
			        passEmailEntity.getTestPositionName());
			logger.debug("pass text is " + text);
			wxCommunicator.sendMessage(
					WXInterfaceUrl.getSendText(candidate.getOpenId(), text),
					token);
		}
	}

	@Override
	public int getTestResult(long testId) {
		return reportDao.getTestResult(testId);
	}

	public List<ConfigReport> getConfigReport() {
		return null;
	}

	public List<ConfigReportPart> getConfigReportPart() {
		return null;
	}

	public CandidateReport getCandidateReport(long testId) {
		return reportDao.getEntity(testId);
	}

	public List<CandidateReport> getReportByCandidate(int candidateId) {
		return reportDao.getReportByCandidate(candidateId);
	}

	public PaperModel getPaperModel(int positionId) throws Exception {
		Position p = positionDao.getEntity(positionId);
		if (p == null || p.getPaperId() == null) {
			logger.error(
					"not found position or paperId is null for positionId {}",
					positionId);
			return null;
		}
		PaperModel model  = getPaperModelByPaperId(p.getPaperId());
		/*if(model != null){
			model.setName(p.getPositionName());
			model.setModifyDate(p.getModifyDate());
		}*/
		return model;
	}
	/**
	 * 获取试卷模板构成
	 * 
	 * @param paperId
	 * @return
	 */
	public PaperModel getPaperModelByPaperId(int paperId) throws Exception{
		List<PaperSkill> paperSkills = paperSkillDao.getPaperSkills(paperId);
		List<PaperQuestion> paperQuestions = paperQuestionDao
				.getNormalPaperQuestions(paperId);
		if (paperQuestions.size() == 0) {
			logger.error(
					"not found paperQuestions for paperId  {},please check ....",
					paperId);
			return null;
		}
		logger.debug("paper skill size is {} for paperId {} ",
				paperSkills.size(), paperId);
		logger.debug("paper questions size is {} for  paperId {} ", paperQuestions.size(), paperId);
		PaperModel model = new PaperModel();
		Paper paper = paperDao.getEntity(paperId);
		model.setModifyDate(paper.getModifyDate());
		model.setName(paper.getPaperName());
		int totalTime = 0;
		int totalNum = 0;

		if (paperSkills.size() > 0) {
			PaperObjectModelInfo objects = model.getObjects();
			if (objects == null) {
				objects = new PaperObjectModelInfo();
				model.setObjects(objects);
			}
			List<QbSkill> skills = new ArrayList<QbSkill>();
			List<List<Integer>> difficulties = new ArrayList<List<Integer>>();
			objects.setSkills(skills);
			objects.setDifficulties(difficulties);
			// Map<String,QbSkill> skills = new HashMap<String, QbSkill>();
			for (PaperSkill paperSkill : paperSkills) {
				QbSkill qbSkill = qbSkillDaoImpl.getEntity(paperSkill.getId().getSkillId());
				logger.debug("qbSkill is {} " , qbSkill.toString());
				qbSkill.setPrebuilt(Constants.PREBUILT_SYS);
				skills.add(qbSkill);
				// 初始化为0 0 0
				List<Integer> three = new ArrayList<Integer>();
				for (int i = 0; i < Constants.DIFFICULTY_LEVEL.length; i++)
					three.add(0);

				difficulties.add(three);
			}
		}
		for (PaperQuestion paperQuestion : paperQuestions) {
			long questionId = paperQuestion.getId().getQuestionId();
			QbQuestion qbQuestion = questionDao.getEntity(questionId);
			totalTime += qbQuestion.getSuggestTime();
			totalNum++;
			String questionType = qbQuestion.getQuestionType();
			Integer category = qbQuestion.getCategory();
			Integer prebuilt = qbQuestion.getPrebuilt();
			int degree = qbQuestion.getDegree();
			if (category == Constants.CATEGORY_SKILL) {// 技能选择题，技能编程题
				boolean foundSkill = false;
				if (questionType.equals(Constants.QUESTION_TYPE_NAME_S_CHOICE)
						|| questionType
								.equals(Constants.QUESTION_TYPE_NAME_M_CHOICE)
						|| questionType
								.equals(Constants.QUESTION_TYPE_NAME_S_CHOICE_PLUS)
						|| questionType
								.equals(Constants.QUESTION_TYPE_NAME_M_CHOICE_PLUS)) {
				
					PaperObjectModelInfo objects = model.getObjects();
					if (objects == null) {
						objects = new PaperObjectModelInfo();
						model.setObjects(objects);
					}
					if (prebuilt == Constants.PREBUILT_SELF) {
						putQuestionToSelfQuestions(qbQuestion, model.getObjects());
					}
					List<String> skillIds = qbQuestionSkillDao
							.getSkillIds(questionId);
					logger.debug("the skillIds is {} for qId  {} ", skillIds,questionId);
					for (String skillId : skillIds) {
						QbSkill qbSkill = new QbSkill();
						qbSkill.setSkillId(skillId);
						List<QbSkill> skills = model.getObjects().getSkills();
						List<List<Integer>> difficulties = model.getObjects().getDifficulties();
						if(skills == null){
							skills = new ArrayList<QbSkill>();
							difficulties = new ArrayList<List<Integer>>();
							model.getObjects().setSkills(skills);
							model.getObjects().setDifficulties(difficulties);
						}
						int index = skills.indexOf(qbSkill);
						if (index != -1 || prebuilt == Constants.PREBUILT_SELF) {
							if (qbQuestion.getPrebuilt() == Constants.PREBUILT_SELF) {
								if (index == -1) {
									logger.debug(
											"init self skill for questionId {} , skillId {} ",
											questionId, skillId);
									QbSkill selfSkill = qbSkillDaoImpl
											.getEntity(skillId);
									selfSkill
											.setPrebuilt(Constants.PREBUILT_SELF);
									skills.add(selfSkill);
									// 初始化为0 0 0
									List<Integer> three = new ArrayList<Integer>();
									for (int i = 0; i < Constants.DIFFICULTY_LEVEL.length; i++)
										three.add(0);

									difficulties.add(three);
									index = skills.size() - 1;
								}
							}
							List<Integer> diffs = difficulties.get(index);
							for (int i = 0; i < Constants.DIFFICULTY_LEVEL.length; i++) {
								String diffLevel = Constants.DIFFICULTY_LEVEL[i];
								QbDifficultyLevel level = qbDifficultyLevelDaoImpl
										.getDifficultyLevel(diffLevel,
												paper.getLevel());
								if (level != null) {
									if (prebuilt == Constants.PREBUILT_SELF) {
										if (degree <= ((i + 1) * 2)) {// <=2对应低难度
																		// <=4对应中难度
																		// <6对应高难度
											int number = diffs.get(i);
											number++;
											diffs.set(i, number);
											foundSkill = true;
											logger.debug(
													"found self skill {} for the question {} ",
													skillId, questionId);
											break;
										}
									} else if (degree >= level
											.getDifficultyLow()
											&& degree <= level
													.getDifficultyHigh()) {
										int number = diffs.get(i);
										number++;
										diffs.set(i, number);
										foundSkill = true;
										logger.debug(
												"found skill {} for the question {} ",
												skillId, questionId);
										break;
									}
								} else {
									logger.warn(
											"not found any qbdifficultylevel for diff {} , level {} ",
											diffLevel, paper.getLevel());
								}
							}
						}
						if (foundSkill) {
							break;
						}else{
							logger.warn("not found any diff for qId {} ",questionId);
						}
					}
				}
				else if (questionType.equals(Constants.QUESTION_TYPE_NAME_PROGRAM)
						|| questionType
								.equals(Constants.QUESTION_TYPE_NAME_TEXT)) {
					PaperSubjectModelInfo subjects = model.getSubjects();
					if(subjects == null){
						subjects = new PaperSubjectModelInfo();
						model.setSubjects(subjects);
					}
					List<PaperSubjectModelQuestion> questions = subjects
							.getQuestions();
					if (questions == null) {
						questions = new ArrayList<PaperSubjectModelQuestion>();
						subjects.setQuestions(questions);
					}
					boolean foundProgram = false;
					for (PaperSubjectModelQuestion question : questions) {
						if (question.getProgramLauguage().equals(
								qbQuestion.getProgramLanguage())) {
							question.setQuestionNum(question.getQuestionNum() + 1);
							foundProgram = true;
							break;
						}
					}
					if (foundProgram == false) {
						PaperSubjectModelQuestion question = new PaperSubjectModelQuestion();
						question.setProgramLauguage(qbQuestion
								.getProgramLanguage());
						question.setQuestionNum(1);
						questions.add(question);
					}

					if (prebuilt == Constants.PREBUILT_SELF) {
						putQuestionToSelfQuestions(qbQuestion, subjects);
					}
				}
				else if(questionType.equals(Constants.QUESTION_TYPE_NAME_ESSAY)){
					if (prebuilt == Constants.PREBUILT_SELF) {
						PaperEssayModelInfo essays = model.getEssays();
						if(essays == null){
						    essays = new PaperEssayModelInfo();
                            model.setEssays(essays);
						}
						putQuestionToSelfQuestions(qbQuestion, essays);
					}
				}else{
					logger.warn("not support questionType for categry 1 qId {} ", qbQuestion.getQuestionId());
				}
			} else if (category == Constants.CATEGORY_INTE) {// 智力
				PaperInteModelInfo intellige = model.getIntellige();
				if(intellige == null){
				    intellige = new PaperInteModelInfo();
				    model.setIntellige(intellige);
				}
				putQuestionToSelfQuestions(qbQuestion, intellige);
			} else if (category == Constants.CATEGORY_INTER) {// 面试题
				if (questionType.equals(Constants.QUESTION_TYPE_NAME_INTERVIEW)) {
					PaperVideoModelInfo videos = model.getVideos();
					if(videos == null){
						 videos = new PaperVideoModelInfo();
                         model.setVideos(videos);
					}
					putQuestionToSelfQuestions(qbQuestion, videos);
				}
			}else{
				logger.warn("not support categry for qId {} ", qbQuestion.getQuestionId());
			}
		}
		model.setTotalNum(totalNum);
		model.setTotalTime(totalTime);
		if(model.getIntellige() != null && model.getIntellige().getSelfQuestions() != null){//智力题按 选择问答排序
			java.util.Collections.sort(model.getIntellige().getSelfQuestions(), new Comparator<GetQInfoResponseModel>() {
				@Override
				public int compare(GetQInfoResponseModel o1, GetQInfoResponseModel o2) {
					return o1.getType() - o2.getType();
				}
			});
		}
		
		if(model.getSubjects() != null && model.getSubjects().getSelfQuestions() != null){//编程题按语言排序
			java.util.Collections.sort(model.getSubjects().getSelfQuestions(), new Comparator<GetQInfoResponseModel>() {
				@Override
				public int compare(GetQInfoResponseModel o1, GetQInfoResponseModel o2) {
					 return o1.getProgramLanguage().compareTo(o2.getProgramLanguage());
				}
			});
		}
		
		return model;
	}
	private void putQuestionToSelfQuestions(QbQuestion question,
			PaperModelPart part) throws Exception {
		List<GetQInfoResponseModel> questions = part.getSelfQuestions();
		if (questions == null) {
			questions = new ArrayList<GetQInfoResponseModel>();
			part.setSelfQuestions(questions);
		}
		GetQInfoResponseModel model = new GetQInfoResponseModel();
		String type = question.getQuestionType();
		if (!type.equals(Constants.QUESTION_TYPE_NAME_INTERVIEW)
				&& !type.equals(Constants.QUESTION_TYPE_NAME_GROUP)) {
			GetQInfoResponse res = gradeService.getQInfo(question
					.getQuestionId());
			PropertyUtils.copyProperties(model, res);
			List<QbQuestionSkill> skills = qbQuestionSkillDao.getSkills(question.getQuestionId());
			for(QbQuestionSkill skill : skills){
				if(skill.getSkillSeq() == 1){//取一个seq为1的技能名称即可
					QbSkill qbSkill = qbSkillDaoImpl.getEntity(skill.getId().getSkillId());
					model.setSkillName(qbSkill.getSkillName());
					break;
				}
			}
			if (type.equals(Constants.QUESTION_TYPE_NAME_PROGRAM) || type.equals(Constants.QUESTION_TYPE_NAME_TEXT)) {//编程题增加语言
				model.setProgramLanguage(question.getProgramLanguage());
			}
		} else {
			model.setTitle(question.getQuestionDesc());
		}

		model.setSuggestTime(question.getSuggestTime());
		questions.add(model);
	}


	@Override
	public List<CandReportAndInfo> getReport(GetReportParam param)
			throws PFServiceException {
		try {
			List<CandReportAndInfo> result = new ArrayList<CandReportAndInfo>();
			List<CandidateReport> list = reportDao.getReport(param);
			if (!CollectionUtils.isEmpty(list)) {
				for (CandidateReport cr : list) {
					CandReportAndInfo candReportAndInfo = new CandReportAndInfo();
					CandidateTest candidateTest = candidateTestDao
							.getCandidateTest(cr.getTestId());
					PropertyUtils.copyProperties(candReportAndInfo, cr);
					candReportAndInfo.setReportDateDesc(PrettyTimeMaker
							.format(cr.getReportDate()));
					candReportAndInfo.setCandidatePic(candidateTest
							.getCandidatePic());
					GetCandidateInfoResponse gcr = gradeService
							.getCandidateInfo(cr.getCandidateId(),
									param.getEmployerId(),
									param.getPositionId());
					GetReportSummaryResponse grsr = gradeService
							.getReportSummary(cr.getTestId());
					candReportAndInfo.setItems(grsr.getItems());
					List<OverallItem> items = candReportAndInfo.getItems();  
					if(items != null){
						int index =-1;
						for(int i = 0; i<items.size();i++){
							if("面试得分".equals(items.get(i).getName())){
								index = i;
								break;
							}
						}
						if(index != -1){
							items.remove(index);
						}
					}
					candReportAndInfo.setCandidateEmail(gcr.getEmail());
					candReportAndInfo.setCandidateName(gcr.getName());
					List<Mapping> mappings = new ArrayList<Mapping>();
					for (UserInfo userInfo : gcr.getInfos()) {
						Mapping mapping = new Mapping();
						mapping.setKey(userInfo.getKey());
						mapping.setValue(userInfo.getValue());
						mappings.add(mapping);
					}
					candReportAndInfo.setInfo(mappings);
					result.add(candReportAndInfo);
				}
			}
			return result;
		} catch (Exception e) {
			logger.error("call report service getReport error ", e);
			throw new PFServiceException(e.getMessage());
		}
	}

	@Override
	public PFResponse setReportStateRead(int employerId, int positionId,
			long testId) throws PFServiceException {
		PFResponse pfResponse = new PFResponse();
		try {
			reportDao.setReportState(testId, 1);
			// positionDao.delPositionLog(positionId, testId);
			pfResponse.setCode(FuncBaseResponse.SUCCESS);
		} catch (Exception e) {
			pfResponse.setCode(FuncBaseResponse.FAILED);
			logger.error("call reportDao.setReportState error ", e);
			throw new PFServiceException(e.getMessage());
		}
		return pfResponse;
	}

	@Override
	public PFResponse ownReport(int employerId, long testId)
			throws PFServiceException {
		PFResponse pfResponse = new PFResponse();
		try {
			Invitation invitation = inviteDao.getEntity(testId);
			int realOwner = invitation.getEmployerId().intValue();
			if (employerId != realOwner) {
				logger.warn("report own failed for testId {} , employerId {} ", testId , employerId);
				List<Integer> list = employerAuthDao.getGrantedId(employerId);
				if (!CollectionUtils.isEmpty(list))
					for (Integer id : list)
						if (realOwner == id.intValue()) {
							pfResponse.setCode(FuncBaseResponse.SUCCESS);
							break;
						}
				if (pfResponse.getCode() == null)
					pfResponse.setCode(FuncBaseResponse.FAILED);
			} else {
				pfResponse.setCode(FuncBaseResponse.SUCCESS);
			}
			return pfResponse;
		} catch (Exception e) {
			logger.error("error call ownReport", e);
			throw new PFServiceException(e.getMessage());
		}
	}

	@Override
	public PFResponseData<Integer> getEmployerStatus(long testId, String passport) {
		logger.debug("  getEmployerStatus for testId {}, passport {}", testId, passport);
		PFResponseData<Integer> res = new PFResponseData<Integer>();
		res.setCode(FuncBaseResponse.SUCCESS);
		CandidateReport candidateReport = reportDao.getEntity(testId);
		if(!candidateReport.getReportPassport().equals(passport)){
		    res.setCode(FuncBaseResponse.PARAMETERERR);	
		    res.setMessage("参数错误，权限验证失败");
		    return res;
		}
		Invitation invitation = inviteDaoImpl.getEntity(testId);
		int employerId = invitation.getEmployerId();
		Employer employer = employerDaoImpl.getEntity(employerId);
		if(employer == null){
			logger.debug("the employer can not found for testId {} ", testId);
			res.setData(0);
			return res;
		}
		EmployerTrialApply apply = employerTrialDao.getEmployerTrialByEmail(employer.getEmployerAcct());
		if(apply == null){
			logger.debug("the apply can not found for testId {} ", testId);
			res.setData(0);
			return res;
		}
		if ((employer.getAcctType() != null && employer.getAcctType() == Constants.SAMPLE_ACCT_TYPE)
				|| apply.getActivated() != 1) {
			logger.debug("the employer can not active for testId {} , employerId {}", testId, employer.getEmployerId());
			res.setData(0);
			return res;
		}
		res.setData(1);
		return res;
	}

	@Override
	public List<PositionGroupReport> getReportInMem(GetReportParam param) {
		logger.debug("getReportInMem , the param is {} ", param);
		long time1 = System.currentTimeMillis();
		List<SchoolCandidateReport>  reports =   schoolReportDao.getAllSchoolReportList(param.getEmployerId(), param.getPositionId(),param.getActivityId(), null);
	    long time2 = System.currentTimeMillis();
		logger.debug("getReportInMem all waste for {} " , time2 - time1);
		if (CollectionUtils.isEmpty(reports))
			return new ArrayList<PositionGroupReport>();

		// 过滤begin
		String searchTxt = param.getInputKey();
		String intentPos = param.getPositionIntent();
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
			
			/*double getScore = report.getGetScore();
			if (StringUtils.isNotBlank(param.getScore().getMax())
					&& getScore > Double.valueOf(param.getScore().getMax())) {//超过最大过滤条件
				filterReports.add(report);
				continue;
			}
			if (StringUtils.isNotBlank(param.getScore().getMin())
					&& getScore < Double.valueOf(param.getScore().getMin())) {//小于最小过滤条件
				filterReports.add(report);
				continue;
			}*/
			Timestamp endTime = null;
			if (StringUtils.isNotBlank(param.getCommitPaperFromDate()))// 交卷起点时间
			{
				CandidateTest candidateTest = candidateTestDao.getCandidateTest(report.getTestId());
				endTime = candidateTest.getEndTime();
				Timestamp fromDate = DateUtils.getTimestamp(param.getCommitPaperFromDate() + " 00:00",
						DateUtils.DATE_FORMAT_3);
				if (endTime.before(fromDate)) {
					filterReports.add(report);
					continue;
				}
			}
			if (StringUtils.isNotBlank(param.getCommitPaperToDate()))// 交卷终点时间
			{
				if (endTime == null) {
					CandidateTest candidateTest = candidateTestDao.getCandidateTest(report.getTestId());
					endTime = candidateTest.getEndTime();
				}
				Timestamp toDate = DateUtils.getTimestamp(param.getCommitPaperToDate() + " 23:59",
						DateUtils.DATE_FORMAT_3);
				if (endTime.after(toDate)) {
					filterReports.add(report);
					continue;
				}
			}
			
		}
		long time3 = System.currentTimeMillis();
		logger.debug("filter filterReports size is {}, the reportSize is {}, waste  {} " , new Object[]{ filterReports.size(), reports.size(),time3 - time2});
		reports.removeAll(filterReports);

		List<PositionGroupReport> groupReports = sortReport(reports,param);
		long time4 = System.currentTimeMillis();
		logger.debug("sortReport  groupReports size is {}, the reports is {}, waste  {} " , new Object[]{ groupReports.size(), reports.size(),time4 - time3});

		Page page = param.getPage();
		int fromIndex = page.getFirstRow();
		int toIndex = page.getLastRow();
		if (fromIndex > groupReports.size())
			fromIndex = groupReports.size();
		if (toIndex > groupReports.size())
			toIndex = groupReports.size();
		groupReports = groupReports.subList(fromIndex, toIndex);
		//设置测评名称    前面没有设置  在这设置只设置展现给前台的  防止访问多次数据库
		for(PositionGroupReport report : groupReports){
			List<CandReportAndInfo> candReports = report.getReports();
			for(CandReportAndInfo candReport : candReports){
				candReport.setPositionName(positionDao.getEntity(candReport.getPositionId()).getPositionName());
			}
		}
		return groupReports;
	}

	
	private List<PositionGroupReport>  sortReport(List<SchoolCandidateReport> schoolCandidateReports,GetReportParam param){
		Map<Integer,List<CandReportAndInfo>>  candidateToReports = new HashMap<Integer, List<CandReportAndInfo>>();//人与报告关系
		for(SchoolCandidateReport schoolCandidateReport : schoolCandidateReports){
			int candidateId = schoolCandidateReport.getCandidateId();
			List<CandReportAndInfo> candReportAndInfos = candidateToReports.get(candidateId);
			if(candReportAndInfos == null){
				candReportAndInfos = new ArrayList<CandReportAndInfo>();
				candidateToReports.put(candidateId, candReportAndInfos);
			}
			CandReportAndInfo candReportAndInfo = changeToCandReportAndInfo(schoolCandidateReport);
			candReportAndInfos.add(candReportAndInfo);
		}
		final Integer orderPositionId = param.getOrderByPositionId();
		List<Map.Entry<Integer, List<CandReportAndInfo>>> list = new ArrayList<Map.Entry<Integer, List<CandReportAndInfo>>>(candidateToReports.entrySet());
		if(orderPositionId != null){
		java.util.Collections.sort(list,new Comparator<Map.Entry<Integer, List<CandReportAndInfo>>>() {
			@Override
			public int compare(Entry<Integer, List<CandReportAndInfo>> o1, Entry<Integer, List<CandReportAndInfo>> o2) {
				List<CandReportAndInfo> list1 = o1.getValue();
				List<CandReportAndInfo> list2 = o2.getValue();
				Double maxScore1 = -1D;
				Double maxScore2 = -1D;
				for(CandReportAndInfo report : list1){
					if(report.getPositionId().equals(orderPositionId)){
						if(report.getGetScore() > maxScore1){
							maxScore1 = report.getGetScore();
						}
					}
				}
				
				for(CandReportAndInfo report : list2){
					if(report.getPositionId().equals(orderPositionId)){
						if(report.getGetScore() > maxScore1){
							maxScore2 = report.getGetScore();
						}
					}
				}
				return maxScore2.compareTo(maxScore1) ;
			}
		});
		}
		List<PositionGroupReport>  groupReports = new ArrayList<PositionGroupReport>();
		for(int i = 0; i< list.size() ;i++){
			Map.Entry<Integer, List<CandReportAndInfo>>  entry = list.get(i);
			List<CandReportAndInfo> candReportAndInfos = entry.getValue();
			
			boolean filter = false;
			Integer positionId = param.getFilterScorePositionId();
			if(positionId != null){
			for(CandReportAndInfo candReportAndInfo : candReportAndInfos){
				
				if(positionId.equals(candReportAndInfo.getPositionId())){
					double getScore = candReportAndInfo.getGetScore();
					String max = param.getScore().getMax();
					String min = param.getScore().getMin();
					if(StringUtils.isEmpty(max)){
						max = 100 +"";
					}
					if(StringUtils.isEmpty(min)){
						min = 0 +"";
					}
					
					if (getScore >= Double.valueOf(min) && getScore <= Double.valueOf(max)) {
							filter = true;
							break;
					}
					
				}
			}
			}else{
				filter = true;
			}
			if(!filter){
				continue;
			}
			
			PositionGroupReport report = new PositionGroupReport();
			CandReportAndInfo  candReport = candReportAndInfos.get(0);
			report.setCandidateId(entry.getKey());
			report.setCandidateName(candReport.getCandidateName());
			report.setCandidateEmail(candReport.getCandidateEmail());
			report.setCandidatePhone(candReport.getCandidatePhone());
			report.setReports(candReportAndInfos);
			
			
			groupReports.add(report);
		}
		return groupReports;
	}
	
	private CandReportAndInfo changeToCandReportAndInfo(SchoolCandidateReport schoolCandidateReport) {
		CandReportAndInfo candReportAndInfo = new CandReportAndInfo();
		candReportAndInfo.setCandidateId(schoolCandidateReport.getCandidateId());
		candReportAndInfo.setCandidateName(schoolCandidateReport.getCandidateName());
		candReportAndInfo.setPositionId(schoolCandidateReport.getPositionId());
		candReportAndInfo.setGetScore(schoolCandidateReport.getGetScore());
		candReportAndInfo.setTestId(schoolCandidateReport.getTestId());
		candReportAndInfo.setCandidateEmail(schoolCandidateReport.getCandidateEmail());
		candReportAndInfo.setCandidatePhone(schoolCandidateReport.getCandidatePhone());
		return candReportAndInfo;
	}

	@Override
	public int getReportNumber(int employerId, Integer positionId, Integer activityId) {
		Position position = positionDao.getEntity(positionId);
		List<SchoolCandidateReport>  reports =   schoolReportDao.getAllSchoolReportList(employerId, positionId,activityId, null);
		
		if(position.getGroupFlag() == Constants.GROUP_FLAG_PARENT){
			List<Integer> candidateIds = new ArrayList<Integer>();
			if (!CollectionUtils.isEmpty(reports))
			for (SchoolCandidateReport report : reports) {
				if(!candidateIds.contains(report.getCandidateId()))
				candidateIds.add(report.getCandidateId());
			}
			return candidateIds.size();
		}
		
		return reports.size();
	}
}
