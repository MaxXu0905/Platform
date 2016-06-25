package com.ailk.sets.platform.service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
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
import com.ailk.sets.platform.common.ConfigSysParam;
import com.ailk.sets.platform.dao.interfaces.ICandidateDao;
import com.ailk.sets.platform.dao.interfaces.ICandidateInfoDao;
import com.ailk.sets.platform.dao.interfaces.ICandidateTestDao;
import com.ailk.sets.platform.dao.interfaces.ICompanyRecruitActivityDao;
import com.ailk.sets.platform.dao.interfaces.IConfigSysParamDao;
import com.ailk.sets.platform.dao.interfaces.IInviteDao;
import com.ailk.sets.platform.dao.interfaces.IPaperDao;
import com.ailk.sets.platform.dao.interfaces.IPaperPartDao;
import com.ailk.sets.platform.dao.interfaces.IPositionDao;
import com.ailk.sets.platform.dao.interfaces.IPositionRelationDao;
import com.ailk.sets.platform.dao.interfaces.ISchoolExamDao;
import com.ailk.sets.platform.domain.ConfigSysParameters;
import com.ailk.sets.platform.domain.PositionRelation;
import com.ailk.sets.platform.domain.paper.CandidateTestPart;
import com.ailk.sets.platform.domain.paper.CandidateTestQuestion;
import com.ailk.sets.platform.domain.paper.CandidateTestQuestionId;
import com.ailk.sets.platform.domain.paper.PaperPart;
import com.ailk.sets.platform.intf.cand.domain.CandidateExamInfo;
import com.ailk.sets.platform.intf.cand.domain.Invitation;
import com.ailk.sets.platform.intf.cand.domain.PaperPartData;
import com.ailk.sets.platform.intf.cand.domain.PaperQuestionInfo;
import com.ailk.sets.platform.intf.cand.domain.SchoolExamCondition;
import com.ailk.sets.platform.intf.cand.domain.SchoolPaperData;
import com.ailk.sets.platform.intf.cand.domain.SchoolPaperSkillCount;
import com.ailk.sets.platform.intf.cand.service.IExamineService;
import com.ailk.sets.platform.intf.cand.service.ISchoolExamService;
import com.ailk.sets.platform.intf.cand.service.ISchoolInfoService;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.common.PaperPartSeqEnum;
import com.ailk.sets.platform.intf.domain.Candidate;
import com.ailk.sets.platform.intf.domain.CompanyRecruitActivity;
import com.ailk.sets.platform.intf.domain.paper.Paper;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;
import com.ailk.sets.platform.intf.empl.domain.PaperModel;
import com.ailk.sets.platform.intf.empl.domain.PaperObjectModelInfo;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.empl.service.IPosition;
import com.ailk.sets.platform.intf.empl.service.IReport;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.candidateTest.CandidateInfo;
import com.ailk.sets.platform.intf.model.invatition.SchoolInvitationInfo;
import com.ailk.sets.platform.intf.model.qb.QbSkill;
import com.ailk.sets.platform.service.instancepart.process.PaperInstanceProcessSchool;
import com.ailk.sets.platform.service.local.IQbQuestionService;
import com.ailk.sets.platform.util.PaperCreateUtils;

@Transactional(rollbackFor = Exception.class)
public class SchoolExamServiceImpl implements ISchoolExamService {
	@Autowired
	private ICandidateTestDao candidateTestDao;
	@Autowired
	ISchoolInfoService schoolInfoService;
	@Autowired
	private IInviteDao inviteDaoImpl;
	@Autowired
	private ICandidateInfoDao candidateInfoDao;
	@Autowired
	private ICandidateTestDao candidateTestDaoImpl;
	@Autowired
	private IConfigSysParamDao configSysParamDao;
	@Autowired
	private ICandidateDao candidateDao;
	@Autowired
	IQbQuestionService qbQuestionService;
	@Autowired
	private IGradeService gradeService;
	@Autowired
	private IExamineService examineService;
	@Autowired
	private ISchoolExamDao schoolExamDao;
	@Autowired
	private IPositionDao positionDao;
	@Autowired
	private ICompanyRecruitActivityDao companyRecruitActivityDao;

	@Autowired
	private PaperInstanceProcessSchool schoolProcess;
	@Autowired
	private ICandidateTestQuestionDao candidateTestQuestionDao;
	@Autowired
	private IPaperPartDao paperPartDao;
	@Autowired
	private IPaperDao paperDao;
	
	@Autowired
	private ICandidateTestPartDao candidateTestPartDao;
	@Autowired
	private IReport reportImp;
	@Autowired
	private IPosition positionImp;
	
	@Autowired
	private IPositionRelationDao positionRelationDao;
	Logger logger = LoggerFactory.getLogger(SchoolExamServiceImpl.class);

	@Override
	public SchoolPaperData generatePaper(SchoolExamCondition condition) throws PFServiceException, Exception {
		logger.debug("generatePaper condition is {} ", condition);
		SchoolPaperData data = new SchoolPaperData();
		CompanyRecruitActivity acti  = companyRecruitActivityDao.getEntity(condition.getActivityId());
//		CompanyRecruitActivity acti = qbNoramlDaoImpl.getNormalObject(condition.getActivityId(), CompanyRecruitActivity.class);// schoolInfoService.getCompanyRecruitActivity(condition.getCompanyWeixinId(),
		if (acti == null) {
			throw new PFServiceException("not found  CompanyRecruitActivity for getActivityId  " + condition.getActivityId());
		}
		Candidate candidate = candidateInfoDao.getCandiateByWeixinNo(condition.getWeixinId());
		if (candidate == null) {
			throw new PFServiceException("not found  candidate for Weixin  " + condition.getWeixinId());
		}

		String entry = condition.getPositionEntry();
		Position childPosition = positionDao.getPositionByEntry(entry);
		Invitation oldInvitation = getOneInvitation(candidate.getWeixinNo(), acti.getActivityId(),childPosition.getPositionId());
		if (oldInvitation != null) {
			logger.debug("the condition has created invitation {}, the oldtestId is is {} ", condition.toString(), oldInvitation.getTestId());
			createSchoolPaperDataFromOldData(data, oldInvitation);
			return data;
		}
		ConfigSysParameters csp = configSysParamDao.getConfigSysParameters(ConfigSysParam.EFFDAY);

		int positionId = acti.getPositionId();
		int employerId = ((Position)positionDao.getEntity(positionId)).getEmployerId();
		String email = candidate.getCandidateEmail();
		String name = candidate.getCandidateName();
		String passport = acti.getPasscode();// passcode;

		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		Timestamp inviteDate = new Timestamp(System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(df.parse(df.format(inviteDate)));
		calendar.add(Calendar.DAY_OF_MONTH, Integer.parseInt(csp.getParamValue()) + 1);
		Timestamp effDate = new Timestamp(calendar.getTimeInMillis());

		Invitation invitation = new Invitation();
		invitation.setCandidateEmail(email);
		invitation.setCandidateName(name);
		invitation.setPositionId(childPosition.getPositionId());
		invitation.setPassport(passport);
		invitation.setInvitationState(1);
		invitation.setInvitationDate(inviteDate);
		invitation.setExpDate(effDate);
		invitation.setStateDate(new Date(System.currentTimeMillis()));
		invitation.setEmployerId(employerId);

//		data.setTimerType(acti.getTimerType());

		CandidateTest candidateTest = createSchoolPaperInstance(condition , data, invitation, acti);
		invitation.setTestId(candidateTest.getTestId());
		inviteDaoImpl.saveOrUpdate(invitation);
		data.setTestId(invitation.getTestId());
		data.setPassport(invitation.getPassport());
		return data;
	}

	private CandidateTest createSchoolPaperInstance(SchoolExamCondition condition ,SchoolPaperData data, Invitation invitation, CompanyRecruitActivity acti) throws PFServiceException {
		Position p = positionDao.getEntity(invitation.getPositionId());

		CandidateTest candidateTest = new CandidateTest();
		candidateTest.setCandidateId(candidateDao.getCandidateId(invitation.getCandidateName(), invitation.getCandidateEmail()));
		candidateTest.setPositionId(p.getPositionId());
		candidateTest.setTestState(0);
		candidateTest.setTestResult(0);
		candidateTest.setPaperState(2);
		candidateTest.setBreakTimes(0);
		candidateTest.setElapsedTime(0);
		candidateTest.setSwitchTimes(0);
		candidateTest.setFreshTimes(0);
		candidateTest.setPaperId(p.getPaperId());
		candidateTest.setPassport(invitation.getPassport());
		candidateTest.setTestPositionName(p.getPositionName());
		
		// add by lipan 2014年7月29日15:58:55  添加考试的位置信息
		if (!StringUtils.isBlank(condition.getLocationInfo()))
        {
		    String[] locationArray = condition.getLocationInfo().split("\\|"); // 经度|纬度|精度
		    if (null!=locationArray)
            {
		        candidateTest.setLongitude(locationArray[0]); // 经度
		        candidateTest.setLatitude(locationArray[1]); // 纬度
		        candidateTest.setAccuracy(locationArray[2]); // 精度
            }
        }
		
		candidateTestDaoImpl.saveCandidateTest(candidateTest);
        Paper paper = paperDao.getEntity(p.getPaperId());
		
		List<Long> paperQuestionIds = new ArrayList<Long>();
		List<PaperPart> paperParts = paperPartDao.getPaperPartsByPaperId(p.getPaperId());
		List<PaperPartData> partDatas = new ArrayList<PaperPartData>();
		
		for (PaperPart paperPart : paperParts) {
			if(paperPart.getId().getPartSeq() > 20 ){ //校招不需要创建试答
				continue;
			}
			schoolProcess.processPaperInstancePart(candidateTest.getTestId(), paper,paperPart, paperQuestionIds);
			PaperPartData partData = new PaperPartData();
			partData.setPartSeq(paperPart.getId().getPartSeq());
			partData.setPartDesc(paperPart.getPartDesc());
			partData.setSuggestTime(paperPart.getSuggestTime());
			partData.setQuestionLength(paperPart.getQuestionNum());
			partDatas.add(partData);
		}
		if(paperQuestionIds.size() == 0){
			logger.warn("not found any question for positionId {}", p.getPositionId());
			throw new PFServiceException("not found any question for positionId " +  p.getPositionId());
		}
		/*List<PaperPartData> partDatas = new ArrayList<PaperPartData>();
		PaperPartData partData = new PaperPartData();
		partData.setPartSeq(PaperPartSeqEnum.OBJECT.getValue());
		partData.setPartDesc("选择题");
		partData.setSuggestTime(totalTime);
		partData.setQuestionLength(paperQuestionIds.size());
		partDatas.add(partData);*/
		data.setPartDatas(partDatas);
		logger.debug("begin to call create paperService for school , testId {}", candidateTest.getTestId());
		qbQuestionService.callCreatePaperService(candidateTest.getTestId(), invitation.getPassport(), paperQuestionIds);
		data.setSkillCountInfos(getSkillCountInfos(p.getPositionId()));
		return candidateTest;
	}
	
	private List<SchoolPaperSkillCount> getSkillCountInfos(int positionId){
		List<SchoolPaperSkillCount> skillCountInfos = new ArrayList<SchoolPaperSkillCount>();
		try {
		PaperModel 	model = reportImp.getPaperModel(positionId);
		PaperObjectModelInfo objects = model.getObjects();
		if(objects != null){
			List<QbSkill> skills = objects.getSkills();
			List<List<Integer>> diffs = objects.getDifficulties();
			if(CollectionUtils.isNotEmpty(skills)){
				for(int i = 0; i < skills.size(); i++){
					QbSkill qbSkill = skills.get(i);
					
					SchoolPaperSkillCount info = new SchoolPaperSkillCount();
					info.setSkillId(qbSkill.getSkillId());
					info.setSkillName(qbSkill.getSkillName());
					int index = skillCountInfos.indexOf(info); 
					int questionLength = 0;
					if(index != -1){//已经有此skillName
						logger.debug("the skillName has contained in infos {} ", qbSkill);
						info = skillCountInfos.get(index);
						questionLength = info.getQuestionLength();
					}
					List<Integer> diff = diffs.get(i);
					for(Integer num : diff){
						questionLength += num;
					}
					info.setQuestionLength(questionLength);
					skillCountInfos.add(info);
				}
			}
		}
		} catch (Exception e) {
			logger.error("get paper error ", e);
		}
		return skillCountInfos;
	}

	private void createSchoolPaperDataFromOldData(SchoolPaperData data, Invitation oldinvitation) {
		List<CandidateTestPart> testParts = candidateTestPartDao.getAllCandidateTestPartByTestId(oldinvitation.getTestId());
		List<PaperPartData> partDatas = new ArrayList<PaperPartData>();
		for (CandidateTestPart part : testParts) {
			PaperPartData partData = new PaperPartData();
			partData.setPartSeq(part.getId().getPartSeq());
			partData.setPartDesc(part.getPartDesc());
			partData.setSuggestTime(part.getSuggestTime());
			partData.setQuestionLength(part.getQuestionNum());
			partDatas.add(partData);
		}
		data.setPartDatas(partDatas);
		data.setTestId(oldinvitation.getTestId());
		data.setPassport(oldinvitation.getPassport());
		processSkillCount(data, oldinvitation);
	}

	public SchoolPaperData getPaperByInvitationId(long testId) throws PFServiceException, Exception {
		Invitation invitation = inviteDaoImpl.getEntity(testId);
		SchoolPaperData result = new SchoolPaperData();
		List<CandidateTestPart> testParts = candidateTestPartDao.getAllCandidateTestPartByTestId(testId); 
		List<PaperPartData> partDatas = new ArrayList<PaperPartData>();
		GetQidsResponse resp = gradeService.getQids(testId);
		for (CandidateTestPart part : testParts) {
			PaperPartData partData = new PaperPartData();
			if (part.getId().getPartSeq() == PaperPartSeqEnum.OBJECT.getValue()) {
				List<QInfo> choices = resp.getChoices();
				List<Long> questionIds = new ArrayList<Long>();
				for(QInfo qInfo : choices){
					questionIds.add(qInfo.getQid());
				}
				List<PaperQuestionInfo> list = getPaperQuestionInfos(questionIds, invitation.getTestId());
				partData.setQuestionIds(list);
			} else if(part.getId().getPartSeq() == PaperPartSeqEnum.EXTRA.getValue()){//智力题
				List<CandidateTestQuestion> extras =  candidateTestQuestionDao.getListByPart(testId, PaperPartSeqEnum.EXTRA.getValue());
				List<Long> questionIds = new ArrayList<Long>();
				for(CandidateTestQuestion qInfo : extras){
					questionIds.add(qInfo.getId().getQuestionId());
				}
				List<PaperQuestionInfo> list = getPaperQuestionInfos(questionIds, invitation.getTestId());
				partData.setQuestionIds(list);
			}else if(part.getId().getPartSeq() == PaperPartSeqEnum.ESSAY.getValue()){//智力题
				List<CandidateTestQuestion> essays =  candidateTestQuestionDao.getListByPart(testId, PaperPartSeqEnum.ESSAY.getValue());
				List<Long> questionIds = new ArrayList<Long>();
				for(CandidateTestQuestion qInfo : essays){
					questionIds.add(qInfo.getId().getQuestionId());
				}
				List<PaperQuestionInfo> list = getPaperQuestionInfos(questionIds, invitation.getTestId());
				partData.setQuestionIds(list);
			}
			else {
				logger.warn("found not support part for the schoolInvitation Id {}, please check", testId);
			}
			partData.setPartSeq(part.getId().getPartSeq());
			partData.setPartDesc(part.getPartDesc());
			partData.setSuggestTime(part.getSuggestTime());
			partData.setQuestionLength(part.getQuestionNum());
			partDatas.add(partData);
		}
	/*	List<CompanyRecruitActivity> activities = qbNoramlDaoImpl.getNoramlListByHql("from CompanyRecruitActivity where positionId = " + invitation.getPositionId(), CompanyRecruitActivity.class);
		if (activities != null && activities.size() == 1) {
//			result.setTimerType(activities.get(0).getTimerType());
		} else {
			logger.error("not found or found more than one activity for positionId {}, please check ", invitation.getPositionId());
		}*/
		result.setPartDatas(partDatas);
		CandidateExamInfo info = examineService.getCandidateExamInfo(testId, 1);
		result.setExamInfo(info);
		return result;
	}

	private List<PaperQuestionInfo> getPaperQuestionInfos(List<Long> questionIds, long paperInstId) {
		List<PaperQuestionInfo> infos = new ArrayList<PaperQuestionInfo>();
		for (long questionId : questionIds) {
			PaperQuestionInfo info = new PaperQuestionInfo();
			info.setQuestionId(questionId);
			CandidateTestQuestion q = candidateTestQuestionDao.get(new CandidateTestQuestionId(paperInstId, questionId));
			info.setSuggestTime(q.getSuggestTime());
			if (q.getAnswerFlag() != null && q.getAnswerFlag() == 1) {
				info.setAnswered(1);
			}
			if (q.getBeginTime() != null) {
				logger.debug("the question {} in paper {} is beginAnswered at " + q.getBeginTime(), q.getId().getQuestionId(), paperInstId);
				int leftSend = PaperCreateUtils.getBetweenTimeForSencond(q.getBeginTime(), q.getSuggestTime(), new Date());
				logger.debug("left second is {}, for questionId {}", leftSend, q.getId().getQuestionId());
				info.setSuggestTime(leftSend);
			}

			infos.add(info);
		}
		return infos;
	}

	/*public CandidateInfo getInvitationInfo(String weixinId, int activityId) throws Exception {
		logger.debug("getInvitationInfo the weixinId is {}, activityId is {}", weixinId, activityId);
		Candidate candidate = candidateInfoDao.getCandiateByWeixinNo(weixinId);
		if (candidate == null) {
			logger.debug("not found candidate for weixinId {}", weixinId);
			return null;
		}
		CandidateInfo info = new CandidateInfo();
		Invitation oldInvitation = getOneInvitation(weixinId, activityId,null);
		if (oldInvitation != null) {// 已经生成邀请
			logger.debug("the testId {} has created for weixinId {}, activityId " + activityId);
			CandidateTest ce = candidateTestDao.loginCandidateTest(oldInvitation.getTestId(), oldInvitation.getPassport());
			info.setCandidateTest(ce);
		}
		return info;
	}*/

	public Invitation getOneInvitation(String weixinId, int activityId,Integer childPositionId) throws Exception {
		logger.debug("begin get  Invitation for weixinId {}, activityId {} ", weixinId, activityId);
		long time1 = System.currentTimeMillis();
		Candidate candidate = candidateInfoDao.getCandiateByWeixinNo(weixinId);
		logger.debug("end get  InvitationStep for weixinId {}, activityId {} , time " + (System.currentTimeMillis() - time1), weixinId, activityId);
		if (candidate == null) {
			return null;
		}
		CompanyRecruitActivity act = companyRecruitActivityDao.getEntity(activityId);
		List<Invitation> invitations = schoolExamDao.getInvitations(act, candidate,childPositionId);// qbNoramlDaoImpl.getNoramlListByHql(sql,
																					// Invitation.class);
		logger.debug("end get  Invitation for weixinId {}, activityId {} , time " + (System.currentTimeMillis() - time1), weixinId, activityId);
		if (invitations != null && invitations.size() > 0) {
			logger.debug("the  activityId {}, the oldInvitations size is {} ", activityId, invitations.size());
			return invitations.get(0);
		}
		return null;
	}

	public SchoolInvitationInfo getSchoolInvitationInfo(SchoolExamCondition condition) throws Exception {
		String weixinId = condition.getWeixinId();
		int activityId = condition.getActivityId();
		Position childPosition = positionDao.getPositionByEntry(condition.getPositionEntry());
		Invitation invitation = getOneInvitation(weixinId, activityId,childPosition.getPositionId());
		SchoolInvitationInfo info = new SchoolInvitationInfo();
		CompanyRecruitActivity activity = companyRecruitActivityDao.getEntity(activityId);
		info.setActivity(activity);
		if (invitation != null) {
			CandidateTest exam = candidateTestDao.getCandidateTest(invitation.getTestId(), invitation.getPassport());
			if (exam == null) {
				logger.error("not found candidateExam for testID {}, please check ...", invitation.getTestId());
			}
			info.setInvitation(invitation);
			info.setCandidateTest(exam);
		}
		return info;
	}

	private void processSkillCount(SchoolPaperData data, Invitation invitation) {
		data.setSkillCountInfos(getSkillCountInfos(invitation.getPositionId()));
	}

	@Override
	public Position getPositionExamInfos(String candidateEmail, String candidateName, String positionGroupEntry)  throws Exception{
		Position position =  positionDao.getPositionByEntry(positionGroupEntry);
		
		if(position.getGroupFlag() != Constants.GROUP_FLAG_PARENT){
			logger.debug("the position is not group position for positionGroupEntry {}", positionGroupEntry);
			return position;
		}
		String mcandBase = configSysParamDao.getConfigParamValue(ConfigSysParam.MCAND_INDEX_PAGE);
		List<PositionRelation> relations = positionRelationDao.getPositionRelationByPositionGroupId(position.getPositionId());
		
		List<CompanyRecruitActivity> actis = schoolInfoService.getCompanyRecruitActivitysActive(positionGroupEntry);//宣讲会是基于组的
		if (actis.size() > 1) {
			logger.warn("have more than one activity for positionEntry {},please check ", positionGroupEntry);
		}
		
		for(PositionRelation relation : relations){
			Position childPos = positionDao.getEntity(relation.getId().getPositionId());
			childPos.setExamState(0);
			String url = mcandBase +"/wx/index/" + childPos.getEntry() +"?fromGroupEntry=" + positionGroupEntry;
			childPos.setActivityUrl(url);
			if(StringUtils.isNotEmpty(candidateName) && StringUtils.isNotEmpty(candidateEmail)){
				
			    CompanyRecruitActivity c = actis.get(0);
				Invitation invitation = getOneInvitation(candidateName + "_" + candidateEmail,c.getActivityId(),childPos.getPositionId());
				if(invitation != null){//答过
					CandidateTest test = candidateTestDao.getEntity(invitation.getTestId());
					if(test.getTestState() == 0){//未答过
						childPos.setExamState(0);
					} else if(test.getTestState() != 0 && test.getTestState() != 1){//已经答完
						childPos.setExamState(2);
					}else{//上次答了部分
						childPos.setExamState(1);
					}
				}
			}
			if(relation.getRelation() == Constants.POSITION_RELATION_CHOOSE){
				List<Position> choosedPositions = position.getChoosePositions();
				if(choosedPositions == null){
					choosedPositions = new ArrayList<Position>();
					position.setChoosePositions(choosedPositions);
				}
				choosedPositions.add(childPos);
			}else if(relation.getRelation() == Constants.POSITION_RELATION_MUST){
				List<Position> mustPositions = position.getMustPositions();
				if(mustPositions == null){
					mustPositions = new ArrayList<Position>();
					position.setMustPositions(mustPositions);
				}
				mustPositions.add(childPos);
			}
		}
		List<Position> nextExamPositions = new ArrayList<Position>();
		List<Position> mustPositions = position.getMustPositions();
		List<Position> choosedPositions = position.getChoosePositions();
		int finishedMustNum = 0;
		boolean hasChoosedFinished = false;
		if(CollectionUtils.isNotEmpty(mustPositions)){
			for(Position p : mustPositions){
				if(p.getExamState() == 1){
					logger.debug("the exam for must  is positionId is {} , candidateName {} , candidateEmail {} ", new Object[]{p.getPositionId(), candidateName, candidateEmail});
					nextExamPositions.add(p);
					break;
				}
				if(p.getExamState() == 2){
					finishedMustNum++;
				}
			}
		}
		
		if(nextExamPositions.size() == 0){
			if(CollectionUtils.isNotEmpty(choosedPositions)){
				for(Position p : choosedPositions){
					if(p.getExamState() == 1){
						logger.debug("the exam for choose  is positionId is {} , candidateName {} , candidateEmail {} ", new Object[]{p.getPositionId(), candidateName, candidateEmail});
						nextExamPositions.add(p);
						break;
					}
					if(p.getExamState() == 2){
						hasChoosedFinished = true;
						break;
					}
				}
			}
		}
		
		if(nextExamPositions.size() == 0){
			if(CollectionUtils.isNotEmpty(mustPositions)){
				if(finishedMustNum == mustPositions.size()){
					 logger.debug("finishedMustNum == mustPositions.size() , mustPositions.size() is {} candidateName {} , candidateEmail {} ", new Object[]{mustPositions.size(),candidateName, candidateEmail} );
				}else{
					logger.debug("the exam for must  is first  candidateName {} , candidateEmail {} ", new Object[]{candidateName, candidateEmail});
					nextExamPositions.add(mustPositions.get(finishedMustNum));
				}
				
			}
		}
		
		if(nextExamPositions.size() == 0){
		  if(CollectionUtils.isNotEmpty(choosedPositions)){
				if(hasChoosedFinished){
					logger.debug("have choosed for candidateName {} , candidateEmail {} ", new Object[]{candidateName, candidateEmail});
				}else{
					logger.debug("the exam for choose  is all   candidateName {} , candidateEmail {} ", new Object[]{candidateName, candidateEmail});
					nextExamPositions.addAll(choosedPositions);
				}
			}
		}
		position.setNextExamPositions(nextExamPositions);
		return position;
	}
	
	
}
