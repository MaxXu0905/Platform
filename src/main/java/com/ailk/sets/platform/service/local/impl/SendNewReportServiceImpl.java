/**
 * author :  lipan
 * filename :  SendNewReportServiceImpl.java
 * create_time : 2014年8月14日 下午8:22:26
 */
package com.ailk.sets.platform.service.local.impl;

import java.text.MessageFormat;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.intf.BaseResponse;
import com.ailk.sets.grade.intf.GetCandidateInfoResponse;
import com.ailk.sets.grade.intf.IGradeService;
import com.ailk.sets.grade.intf.report.GetReportResponse;
import com.ailk.sets.grade.intf.report.GetReportSummaryResponse;
import com.ailk.sets.grade.intf.report.OverallItem;
import com.ailk.sets.platform.common.ConfigSysParam;
import com.ailk.sets.platform.common.MailSenderInfo;
import com.ailk.sets.platform.dao.interfaces.ICandidateDao;
import com.ailk.sets.platform.dao.interfaces.ICandidateTestDao;
import com.ailk.sets.platform.dao.interfaces.ICompanyEmailserverDao;
import com.ailk.sets.platform.dao.interfaces.IConfigDao;
import com.ailk.sets.platform.dao.interfaces.IConfigSysParamDao;
import com.ailk.sets.platform.dao.interfaces.IEmployerDao;
import com.ailk.sets.platform.dao.interfaces.IInvitationCopytoDao;
import com.ailk.sets.platform.dao.interfaces.IInvitationDao;
import com.ailk.sets.platform.dao.interfaces.IInviteDao;
import com.ailk.sets.platform.dao.interfaces.IPositionDao;
import com.ailk.sets.platform.dao.interfaces.IPositionSeriesDao;
import com.ailk.sets.platform.domain.ReportEntity;
import com.ailk.sets.platform.exception.PFDaoException;
import com.ailk.sets.platform.intf.cand.domain.Employer;
import com.ailk.sets.platform.intf.cand.domain.Invitation;
import com.ailk.sets.platform.intf.common.ConfigCodeType;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.domain.Candidate;
import com.ailk.sets.platform.intf.domain.Company;
import com.ailk.sets.platform.intf.domain.InvitationCopyto;
import com.ailk.sets.platform.intf.domain.PositionSeries;
import com.ailk.sets.platform.intf.empl.domain.CandidateReport;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.model.wx.HttpClientUtil;
import com.ailk.sets.platform.intf.model.wx.WXCommunicator;
import com.ailk.sets.platform.intf.model.wx.msg.Article;
import com.ailk.sets.platform.intf.model.wx.msg.SimpleMsg;
import com.ailk.sets.platform.intf.model.wx.msg.Text;
import com.ailk.sets.platform.intf.model.wx.msg.TextPicMsg;
import com.ailk.sets.platform.service.local.ISendNewReportService;
import com.ailk.sets.platform.util.SimpleMailSender;
import com.ailk.sets.platform.util.TemplateHost;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author : lipan
 * @create_time : 2014年8月14日 下午8:22:26
 * @desc : 出报告时发送新消息
 * @update_person:
 * @update_time :
 * @update_desc :
 * 
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class SendNewReportServiceImpl implements ISendNewReportService
{
    private Logger logger = LoggerFactory.getLogger(SendNewReportServiceImpl.class);

    @Autowired
    private IConfigSysParamDao configSysParamDao;
    @Autowired
    private IEmployerDao employerDaoImpl;
    @Autowired
    private IPositionDao positionDao;
    @Autowired
    private IConfigDao configDao;
    @Autowired
    private ICandidateTestDao candidateTestDao;
    @Autowired
    private ICandidateDao candidateDao;
    @Autowired
    private IGradeService gradeService;
    @Autowired
    private IPositionSeriesDao seriesDao;
    @Autowired
    private ICompanyEmailserverDao companyEmailserverDaoImpl;
    @Autowired
    private IInviteDao inviteDaoImpl;
    @Autowired
	private IInvitationCopytoDao invitationCopytoDao;
    @Autowired
    private IInvitationDao invitationDao;
    @Override
    public void sendNewReport(CandidateReport candidateReport) throws Exception
    {
        CandidateTest candidateTest = candidateTestDao.getEntity(candidateReport.getTestId());
        Position position = positionDao.getEntity(candidateTest.getPositionId());
        Invitation invitation = invitationDao.getEntity(candidateReport.getTestId());//邀请人
        // 1. 通知考生分数微信 —— 校招
        if (Constants.TEST_TYPE_SCHOOL == position.getTestType()
                && Position.NOTIFY_SCORE_YES == position.getNotifyScore())
        {
            tellScore(candidateTest, candidateReport, position);
        }

        // 2. 通知招聘者新报告微信 —— 社招
        if (Constants.TEST_TYPE_CLUB == position.getTestType())
        {
            tellNewReportWx(invitation.getEmployerId(), position, candidateTest, candidateReport);//给邀请人发微信邮件，而不是测评创建人
        }
        
        // 3. 通知招聘者新报告邮件 —— 社招
        if (Constants.TEST_TYPE_CLUB == position.getTestType())
        {
            tellNewReportEmail(invitation.getEmployerId(), position, candidateTest,
                    candidateReport);
        }
    }

    /**
     * 微信通知应聘者（校招）得分
     * 
     * @throws PFDaoException
     */
    private void tellScore(CandidateTest candidateTest, CandidateReport candidateReport,
            Position position) throws PFDaoException
    {
        logger.debug("tell test " + candidateTest.getTestId() + " got score with Weixin");
        try
        {
            Candidate candidate = candidateDao.getEntity(candidateTest.getCandidateId());
            if (StringUtils.isNotEmpty(candidate.getOpenId()))
            {
                WXCommunicator wxCommunicator = new WXCommunicator(HttpClientUtil.getHttpClient());
                String token = wxCommunicator.getAccessToken(
                        configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYEEAPPID),
                        configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYEEAPPSECRET));
                String content = configSysParamDao
                        .getConfigParamValue(ConfigSysParam.EMPLOYERGETSCORE);

                GetReportSummaryResponse grsr = gradeService.getReportSummary(candidateTest
                        .getTestId());
                StringBuilder scoreDetail = new StringBuilder();
                for (OverallItem item : grsr.getItems())
                {
                    scoreDetail.append(item.getName() + ": ")
                            .append(item.getScore() == null ? "未打" : item.getScore()).append("分\n");
                }
                content = MessageFormat.format(content, candidate.getCandidateName(),
                        position.getPositionName(), candidateReport.getGetScore(),
                        scoreDetail.toString());
                Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
                // 封装消息对象
                SimpleMsg msg = new SimpleMsg(candidate.getOpenId(), SimpleMsg.MSG_TYPE_TEXT,
                        new Text(content));
                wxCommunicator.sendMessage(gson.toJson(msg), token);
            }
        } catch (Exception e)
        {
            throw new PFDaoException(e);
        }
    }

    /**
     * 微信通知招聘者已出报告
     * 
     * @throws Exception
     */
    private void tellNewReportWx(int employerId, Position position, CandidateTest candidateTest,
            CandidateReport candidateReport) throws Exception
    {
        logger.debug("tell employer " + employerId + " has a new report with Weixin");
        Employer employer = employerDaoImpl.getEntity(employerId);
        if (StringUtils.isNotEmpty(employer.getOpenId()))
        {
            Candidate candidate = candidateDao.getEntity(candidateTest.getCandidateId());
            WXCommunicator wxCommunicator = new WXCommunicator(HttpClientUtil.getHttpClient());
            String token = wxCommunicator.getAccessToken(
                    configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYERAPPID),
                    configSysParamDao.getConfigParamValue(ConfigSysParam.EMPLOYERAPPSECRET));
            String title = configSysParamDao
                    .getConfigParamValue(ConfigSysParam.EMPLOYEEHANDLERTITLE);
            String content = configSysParamDao
                    .getConfigParamValue(ConfigSysParam.EMPLOYEEHANDLERCONTENT);

            title = MessageFormat.format(title, candidate.getCandidateName(),
                    candidateTest.getTestPositionName());

            GetReportSummaryResponse grsr = gradeService.getReportSummary(candidateReport
                    .getTestId());
            StringBuilder scoreDetail = new StringBuilder();
            for (OverallItem item : grsr.getItems())
            {
                scoreDetail.append(item.getName());
                scoreDetail.append("：");
                scoreDetail.append((item.getScore() == null) ? "未打" : item.getScore().toString());
                scoreDetail.append("分\n");
                logger.debug("item name is " + item.getName() + ":" + " score is "
                        + item.getScore());
                logger.debug("but my score replace is " + item.getScore() == null ? "未打" : item
                        .getScore() + "分\n");
            }
            content = MessageFormat.format(content, candidateReport.getGetScore(),
                    scoreDetail.toString());

            String url = configSysParamDao.getConfigParamValue(ConfigSysParam.SCHOOLREPORTURL)
                    + "?testId=" + candidateTest.getTestId() + "&employerId="
                    + employer.getEmployerId() + "&openId=" + employer.getOpenId();

            // 图文消息
            TextPicMsg textPicMsg = new TextPicMsg(employer.getOpenId());
            textPicMsg
                    .addArticle(new Article(title, content, url, candidateTest.getCandidatePic()));
            Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
            String sendMsg = gson.toJson(textPicMsg);
            wxCommunicator.sendMessage(sendMsg, token);
        }
    }

    /**
     * 新报告邮件 ——> 招聘者
     * 
     * @param employerId
     * @param position
     * @param candidateTest
     * @param cr
     * @throws MessagingException
     */
    private void tellNewReportEmail(int employerId, Position position, CandidateTest candidateTest,
            CandidateReport cr) throws MessagingException
    {
        logger.debug("tell employer " + employerId + " has a new report with Email");
        try
        {
            Company company = inviteDaoImpl.getCompanyInfo(position.getEmployerId());
            ReportEntity reportEntity = new ReportEntity();

            // 发送新报告模板
            reportEntity.setPath("newReport");

            reportEntity.setLevel(configDao.getConfigCodeName(ConfigCodeType.POSITION_LEVEL,
                    position.getLevel() + ""));
            PositionSeries series = seriesDao.getEntity(position.getSeriesId());
            if (series != null)
            {
                reportEntity.setSeriesName(series.getSeriesName());
            }
            GetCandidateInfoResponse gcr = gradeService.getCandidateInfo(cr.getCandidateId(),
                    position.getEmployerId(), position.getPositionId());
            GetReportSummaryResponse grsr = gradeService.getReportSummary(cr.getTestId());
            reportEntity.setItems(grsr.getItems());
            reportEntity.setCandidateEmail(gcr.getEmail());
            reportEntity.setCandidateName(gcr.getName());

            // add by lipan 2014年7月22日15:30:18 testpositionName如果为空则为
            // seriesName + level + "工程师"...
            if (StringUtils.isBlank(candidateTest.getTestPositionName()))
            {
                reportEntity.setTestPositionName(reportEntity.getPosition());
            } else
            {
                reportEntity.setTestPositionName(candidateTest.getTestPositionName());
            }

            GetReportResponse grr = gradeService.getReport(cr.getTestId());
            if (grr.getErrorCode() == BaseResponse.SUCCESS)
            {
                reportEntity.setScore(grr.getSummary().getScore());

                // add by lipan 2014年7月21日11:05:37 是否有头像
                if (StringUtils.isBlank(candidateTest.getCandidatePic())) // 没有头像
                {
                    reportEntity.setHasPortrait(Constants.NEGATIVE);
                } else
                {
                    reportEntity.setHasPortrait(Constants.POSITIVE);
                    reportEntity.setPortrait(candidateTest.getCandidatePic());
                }

                MailSenderInfo mailSenderInfo = MailSenderInfo
                        .getMailSenderInfo(companyEmailserverDaoImpl.getEntity(
                                company.getCompanyId(), "companyId"));

                mailSenderInfo.setSubject("您收到一份" + reportEntity.getCandidateName() + "【"
                        + reportEntity.getCandidateEmail() + "】的"
                        + candidateTest.getTestPositionName() + "测评报告");
                reportEntity.setReportUrl(configSysParamDao.getConfigSysParameters(
                        ConfigSysParam.REPORTURL).getParamValue()
                        + "/"
                        + position.getPositionId()
                        + "/"
                        + candidateTest.getTestId()
                        + "/"
                        + cr.getReportPassport());
                Employer emSelf = employerDaoImpl.getEntity(employerId);//发邮件给邀请人
                if(StringUtils.isNotEmpty(emSelf.getEmployerName())){
                	 reportEntity.setEmployerName(emSelf.getEmployerName());
                }else{
                	 reportEntity.setEmployerName(emSelf.getEmployerAcct());
                }
               
                TemplateHost templateHost = new TemplateHost();
                VelocityContext context = templateHost.getContext();
                context.put("entity", reportEntity);
                mailSenderInfo.setContent(templateHost
                        .makeFileString(TemplateHost.VM_PUBLICTEMPLATE));

                mailSenderInfo.setToAddress(emSelf.getEmployerAcct());
                SimpleMailSender.sendHtmlMail(mailSenderInfo);
                //给招聘人组发送报告邮件，通过TestId获取招聘人组，modify by zengjie,14/8/15
                List<InvitationCopyto> emplorers = invitationCopytoDao.getInvitationCopytosByTestId(candidateTest.getTestId());
                if(CollectionUtils.isNotEmpty(emplorers)){
                	//循环发送邮件
                	for(InvitationCopyto emplorer : emplorers){
                		mailSenderInfo.setToAddress(emplorer.getId().getEmail());
                		SimpleMailSender.sendHtmlMail(mailSenderInfo);
                	}
                }
            }
        } catch (Exception e)
        {
            throw new MessagingException("给招聘者发送新报告邮件异常...", e);
        }
    }
}
