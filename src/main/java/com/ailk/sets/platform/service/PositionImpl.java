package com.ailk.sets.platform.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ailk.sets.grade.dao.impl.PositionInfoExtDaoImpl;
import com.ailk.sets.platform.dao.impl.PositionLevelDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IActivityRecruitAddressDao;
import com.ailk.sets.platform.dao.interfaces.ICandidateInfoDao;
import com.ailk.sets.platform.dao.interfaces.ICandidateTestDao;
import com.ailk.sets.platform.dao.interfaces.ICompanyDao;
import com.ailk.sets.platform.dao.interfaces.IConfigDao;
import com.ailk.sets.platform.dao.interfaces.IEmployerAuthDao;
import com.ailk.sets.platform.dao.interfaces.IEmployerDao;
import com.ailk.sets.platform.dao.interfaces.IEmployerTrialDao;
import com.ailk.sets.platform.dao.interfaces.IInviteDao;
import com.ailk.sets.platform.dao.interfaces.IPaperDao;
import com.ailk.sets.platform.dao.interfaces.IPositionDao;
import com.ailk.sets.platform.dao.interfaces.IPositionLogDao;
import com.ailk.sets.platform.dao.interfaces.IPositionRelationDao;
import com.ailk.sets.platform.dao.interfaces.IPositionSeriesDao;
import com.ailk.sets.platform.dao.interfaces.IReportDao;
import com.ailk.sets.platform.dao.interfaces.ISchoolReportDao;
import com.ailk.sets.platform.domain.EmployerAuthorization;
import com.ailk.sets.platform.domain.EmployerAuthorizationId;
import com.ailk.sets.platform.domain.EmployerPosHistory;
import com.ailk.sets.platform.domain.PositionInfoExt;
import com.ailk.sets.platform.domain.PositionLog;
import com.ailk.sets.platform.domain.PositionRelation;
import com.ailk.sets.platform.exception.PFDaoException;
import com.ailk.sets.platform.intf.cand.domain.Employer;
import com.ailk.sets.platform.intf.cand.domain.Invitation;
import com.ailk.sets.platform.intf.common.ConfigCodeType;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.common.FuncBaseResponse;
import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.domain.ActivityAddressByColleege;
import com.ailk.sets.platform.intf.domain.ActivityRecruitAddress;
import com.ailk.sets.platform.intf.domain.Candidate;
import com.ailk.sets.platform.intf.domain.Company;
import com.ailk.sets.platform.intf.domain.PositionActivityAddressInfo;
import com.ailk.sets.platform.intf.domain.PositionLevelInfo;
import com.ailk.sets.platform.intf.domain.PositionOutInfo;
import com.ailk.sets.platform.intf.domain.PositionQuickInfo;
import com.ailk.sets.platform.intf.domain.PositionSeries;
import com.ailk.sets.platform.intf.domain.PositionSeriesCustom;
import com.ailk.sets.platform.intf.domain.PositionSeriesLevel;
import com.ailk.sets.platform.intf.domain.paper.Paper;
import com.ailk.sets.platform.intf.empl.domain.CandidateReport;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;
import com.ailk.sets.platform.intf.empl.domain.ConfigCodeName;
import com.ailk.sets.platform.intf.empl.domain.ConfigInfoExtEx;
import com.ailk.sets.platform.intf.empl.domain.EmployerAuthorizationIntf;
import com.ailk.sets.platform.intf.empl.domain.EmployerTrialApply;
import com.ailk.sets.platform.intf.empl.domain.PaperOutInfo;
import com.ailk.sets.platform.intf.empl.domain.PaperQuestionTypeInfo;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.empl.domain.PositionEx;
import com.ailk.sets.platform.intf.empl.domain.PositionInfoConfig;
import com.ailk.sets.platform.intf.empl.domain.PositionInitInfo;
import com.ailk.sets.platform.intf.empl.domain.PositionSet;
import com.ailk.sets.platform.intf.empl.domain.PositionTestTypeInfo;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;
import com.ailk.sets.platform.intf.empl.service.IInfoCollect;
import com.ailk.sets.platform.intf.empl.service.IInvite;
import com.ailk.sets.platform.intf.empl.service.IPaper;
import com.ailk.sets.platform.intf.empl.service.IPosition;
import com.ailk.sets.platform.intf.empl.service.IReport;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.intf.model.Question;
import com.ailk.sets.platform.intf.model.condition.Interval;
import com.ailk.sets.platform.intf.model.invatition.InvitationState;
import com.ailk.sets.platform.intf.model.param.GetInvitationInfoParam;
import com.ailk.sets.platform.intf.model.param.GetReportParam;
import com.ailk.sets.platform.intf.model.param.RandomQuestionParam;
import com.ailk.sets.platform.intf.model.position.PaperCountInfo;
import com.ailk.sets.platform.intf.model.position.PosMessage;
import com.ailk.sets.platform.intf.model.position.PosResponse;
import com.ailk.sets.platform.intf.model.position.PositionInfo;
import com.ailk.sets.platform.intf.model.position.PositionPaperInfo;
import com.ailk.sets.platform.intf.model.position.PositionStatistics;
import com.ailk.sets.platform.intf.school.domain.SchoolCandidateReport;
import com.ailk.sets.platform.service.category.Category;
import com.ailk.sets.platform.service.category.CategoryFactory;
import com.ailk.sets.platform.service.local.IPositionService;
import com.ailk.sets.platform.util.PrettyTimeMaker;
import com.alibaba.dubbo.common.utils.StringUtils;

@Transactional(rollbackFor = Exception.class)
public class PositionImpl implements IPosition {
	private static Logger logger = LoggerFactory.getLogger(PositionImpl.class);
	@Autowired
	private CategoryFactory categoryFactory;
	@Autowired
	private ICandidateInfoDao candidateInfoDao;
	@Autowired
	private IReportDao reportDao;
	@Autowired
	private IPositionDao positionDao;
	@Autowired
	private IInviteDao inviteDao;
	@Autowired
	private IPaperDao paperDao;
	@Autowired
	IPositionService positionService;
	@Autowired
	private ICandidateTestDao candidateTestDao;
	@Autowired
	private IConfigDao configDao;
	@Autowired
	private IEmployerAuthDao employerAuthDaoImpl;
	@Autowired
	private IEmployerDao employerDaoImpl;
	@Autowired
	private IPositionSeriesDao positionSeriesDaoImpl;
	@Autowired
	private PositionLevelDaoImpl positionLevelDaoImpl;
	@Autowired
	private IInfoCollect infoCollect;
	@Autowired
	private IPositionSeriesDao seriesDao;
	@Autowired
	private IPositionLogDao positionLogDao;
	@Autowired
	private IInvite invite;
	@Autowired
	private ISchoolReportDao schoolReportDao;
	@Autowired
	private IPaper paperImpl;
	@Autowired
	private IEmployerAuthDao employerAuthDao;
	@Autowired
	private ICompanyDao companyDao;
	@Autowired
	private PositionInfoExtDaoImpl positionInfoExtDaoImpl;
	
	@Autowired
	private IEmployerTrialDao employerTrialDao;
	
	@Autowired
	private IPositionRelationDao positionRelationDao;
	
	@Autowired
	private IActivityRecruitAddressDao activityRecruitAddressDao;
	@Autowired
	private IReport reportImpl;

	public List<Question> getCommonHistory(List<EmployerPosHistory> list) {
		return null;
	}

	public List<PositionInfo> getPositionInfo(int employerId, Page page) throws PFServiceException {

		List<PositionInfo> result = new ArrayList<PositionInfo>();
		List<Integer> list = employerAuthDaoImpl.getGrantedId(employerId);
		// list.add(employerId);
		for (Integer id : list)
			logger.debug("getPositionInfo grantedPositionIds is " + id);
		List<PositionLog> logs = positionDao.getPositionLog(employerId, list, page);
		if (CollectionUtils.isEmpty(logs))
			return null;
		for (PositionLog pl : logs) {
			logger.debug("log id is " + pl.getLogId() + ",position id is " + pl.getPositionId());
			Map<Integer, Employer> employerMap = new HashMap<Integer, Employer>();
			Map<Integer, Company> companyMap = new HashMap<Integer, Company>();
			PositionInfo posInfo = getPositionInfo(pl.getPositionId(), employerId, null);
			if (employerId != pl.getEmployerId()) {
				if (!employerMap.containsKey(pl.getEmployerId()))
					employerMap.put(pl.getEmployerId(), employerDaoImpl.getEntity(pl.getEmployerId()));
				
				int companyId = employerMap.get(pl.getEmployerId()).getCompanyId();
				if(!companyMap.containsKey(companyId)){
					companyMap.put(companyId, companyDao.getEntity(companyId));
				}
				posInfo.setEmployerId(pl.getEmployerId());
				posInfo.setEmployerName(employerMap.get(pl.getEmployerId()).getEmployerName());
				posInfo.setCompanyName(companyMap.get(companyId).getCompanyName());
			}
			if (null != posInfo) {
				result.add(posInfo);
			}
		}
		return result;
	}

	/*public List<PositionInfo> getPositionInfo(List<Integer> positionIds, int employerId) throws PFServiceException {
		List<PositionInfo> result = new ArrayList<PositionInfo>();
		Map<Integer, Employer> employerMap = new HashMap<Integer, Employer>();
		for (Integer id : positionIds) {
			PositionInfo posInfo = getPositionInfo(id, employerId, null);
			if (!employerMap.containsKey(employerId))
				employerMap.put(employerId, employerDaoImpl.getEntity(employerId));
			posInfo.setEmployerId(employerId);
			posInfo.setEmployerName(employerMap.get(employerId).getEmployerName());
			result.add(posInfo);
		}
		return result;
	}*/

	@Override
	public Position getPosition(long testId) {
		return positionDao.getPositionByInvitation(testId);
	}

	@Override
	public PositionInfo getPositionInfo(int positionId, int employerId, Integer posNumber) throws PFServiceException {
		return getPositionInfo(positionId, employerId, posNumber, null);
	}

	@Override
	public PositionInfo getPositionInfo(int positionId, int employerId, Integer posNumber, String activityPassport)
			throws PFServiceException {
		try {
			PositionInfo posInfo = new PositionInfo();
			Position pos = positionDao.getEntity(positionId);
			PositionEx posEx = new PositionEx();
			if (pos == null) {
				logger.error("can not find position " + pos);
				return null;
			}
			if (posNumber == null || posNumber == 0) {
				posNumber = Constants.POS_MSG_NUM_SCHOOL;
				if (pos.getTestType() == Constants.TEST_TYPE_CLUB)
					posNumber = Constants.POS_MSG_NUM_CLOB;
			}

			PropertyUtils.copyProperties(posEx, pos);
			posEx.setLevelString(configDao.getConfigCodeName(ConfigCodeType.POSITION_LEVEL, posEx.getLevel() + ""));

			// 职位序列
			PositionSeries series = seriesDao.getEntity(pos.getSeriesId());
			if (null != series) {
				posEx.setpLString(configDao.getConfigCodeName(ConfigCodeType.PROGRAM_LANGUAGE,
						series.getPositionLanguage() + ""));
			}
			posEx.setPublishDateDesc(PrettyTimeMaker.format(pos.getPublishDate()));
			posInfo.setPosition(posEx);
			posInfo.setTestType(pos.getTestType());
			/*
			 * posInfo.setBrandNew(positionDao.isPositionNew(employerId,
			 * positionId));
			 */
			
			// add by lipan 2014年10月24日 如果有职位意向常规信息，那么加入常规信息code 和 value
			List<PositionInfoExt> infoExts = positionInfoExtDaoImpl.getList(employerId, positionId);
			boolean hasPositionIntent = false;
			for (PositionInfoExt infoExt : infoExts)
            {
			    if (infoExt.getId().getInfoId().equals(ConfigCodeType.INTENTION_POSITION))
                {
			        hasPositionIntent = true;
                }
            }
			if (hasPositionIntent)
            {
			    List<ConfigCodeName> codes = configDao.getConfigCode(ConfigCodeType.INTENTION_POSITION);
			    posInfo.setPositionIntents(codes);
            }
			
			// 统计各个状态的报告数量
			setStatistics(posInfo, employerId, positionId);

			GetReportParam param = new GetReportParam(positionId, "0", new Page(posNumber, 1), new Interval("0", "100"));
			// 设置活动passport
			param.setPassport(activityPassport);
			param.setEmployerId(employerId);
			if (employerId != pos.getEmployerId()) {//授权的测评
				logger.debug("the positionId is authed ,positionId {} , authed EmployerId {} ",positionId,employerId);
				Employer employer = employerDaoImpl.getEntity(pos.getEmployerId());
				posInfo.setEmployerName(employer.getEmployerName());
				posInfo.setCompanyName(companyDao.getEntity(employer.getCompanyId()).getCompanyName());
			}
			
			List<CandidateReport> list = reportDao.getReport(param);
			posInfo.setPosMessage(getMsgs(list, false));
			return posInfo;
		} catch (Exception e) {
			logger.error("call getPositionInfo error ", e);
			throw new PFServiceException(e.getMessage());
		}
	}

	public PositionStatistics setStatistics(PositionStatistics ps, int employerId, int positionId) {
		if (ps != null) {
			ps.setReportNum(paperDao.getReportUnreadCount(employerId,positionId));
			ps.setInvitationFailNum(inviteDao.getInvitationFailedNumber(employerId, positionId));
			long chosenNum = candidateTestDao.getCountByState(employerId, positionId, FuncBaseResponse.CONFIRMED);
			chosenNum += candidateTestDao.getCountByState(employerId, positionId,
					InvitationState.CANDIDATE_TEST_RESULT3);
			ps.setChosenNum(chosenNum);
			ps.setInvitatedNum(inviteDao.getInvitationNumber(employerId, positionId));
			ps.setTodoNum(candidateTestDao.getCountByState(employerId, positionId, FuncBaseResponse.TOBECONFIRMED));
			ps.setEliminationNum(candidateTestDao.getCountByState(employerId, positionId, FuncBaseResponse.PASSED));
		}
		return ps;
	}

	private List<PosMessage> getMsgs(List<CandidateReport> list, boolean sample) throws PFDaoException {
		List<PosMessage> result = new ArrayList<PosMessage>();
		if (!CollectionUtils.isEmpty(list))
			for (CandidateReport candidateReport : list) {
				PosMessage posMessage = new PosMessage();
				Candidate candidate = candidateInfoDao.getCandidateById(candidateReport.getCandidateId());
				posMessage.setCandidateName(candidate.getCandidateName());
				posMessage.setScore(candidateReport.getGetScore());
				posMessage.setTestId(candidateReport.getTestId());
				CandidateTest candidateTest = candidateTestDao.getCandidateTest(candidateReport.getTestId());
				posMessage.setPicUrl(candidateTest.getCandidatePic());
				posMessage.setTestId(candidateReport.getTestId());
				posMessage.setReportState(candidateReport.getReportState());
				if (sample)
					posMessage.setPassport(candidateReport.getReportPassport());
				result.add(posMessage);
			}
		return result;
	}

	@Override
	public PFResponse delInvitationFailedLog(int employerId, int positionId) throws PFServiceException {
		PFResponse pfResponse = new PFResponse();
		try {
			positionDao.delPositionLogByState(positionId, 1);
			pfResponse.setCode(FuncBaseResponse.SUCCESS);
		} catch (Exception e) {
			logger.error("error call delInvitationFailedLog", e);
			throw new PFServiceException(e.getMessage());
		}
		return pfResponse;
	}

	@Override
	public Question getRandomQuestion(RandomQuestionParam param) throws PFServiceException {
		Category categoryInstance = categoryFactory.getInstance(param.getCategory());
		if (categoryInstance == null)
			throw new PFServiceException("category " + param.getCategory() + " is not supported now ");
		else {
			return categoryInstance.getRandom(param);
		}
	}

	@Override
	public PositionSet getPositionInfo(int positionId) throws PFServiceException {
		try {
			PositionSet positionSet = new PositionSet();
			Position position = positionDao.getEntity(positionId);
			positionSet.setPosition(position);
			long inviteNum = invite.getInvitationNum(position.getEmployerId(), positionId);
			if (inviteNum > 0) {
				position.setEditable(0);
			} else {
				position.setEditable(1);// 可以编辑
			}

			Collection<ConfigInfoExtEx> coll = infoCollect.getInfoExt(position.getEmployerId(), positionId);
			positionSet.setPositionConfigInfo(coll);
			// 授权信息
			List<EmployerAuthorization> auths = employerAuthDao.getEmployerAuthorizations(position.getEmployerId(),
					positionId);
			List<EmployerAuthorizationIntf> employerAuths = new ArrayList<EmployerAuthorizationIntf>();
			for (EmployerAuthorization auth : auths) {
				EmployerAuthorizationIntf intf = new EmployerAuthorizationIntf();
				intf.setEmailGranted(employerDaoImpl.getEntity(auth.getId().getEmployerGranted()).getEmployerAcct());
				employerAuths.add(intf);
			}
			position.setEmployerAuths(employerAuths);
			return positionSet;
		} catch (Exception e) {
			logger.error("error call getPositionInfo ", e);
			throw new PFServiceException(e.getMessage());
		}
	}

	@SuppressWarnings("unused")
	private boolean isRightInterGroup(List<QbQuestion> questions, String[] ids) {
		for (QbQuestion question : questions) {
			boolean hasQues = false;
			for (String id : ids) {
				if (id.equals(question.getQuestionId() + "")) {
					hasQues = true;
					break;
				}
			}
			if (!hasQues)
				return false;
		}
		return true;
	}

	@Override
	public PFResponse ownPosition(int employerId, int positionId) throws PFServiceException {
		PFResponse pfResponse = new PFResponse();
		try {
			logger.debug("ownPosition called employerId {}, positionId {} ", employerId, positionId);
			Position position = positionDao.getEntity(positionId);
			if (position == null || position.getEmployerId() != employerId) {
				List<Integer> list = employerAuthDaoImpl.getGrantedId(employerId);
				logger.debug("the authed positionIds is {} ", list);
				if(list.contains(positionId) || position.getPreBuilt() == Constants.PREBUILT_SYS){//有授权 或者预制
					pfResponse.setCode(FuncBaseResponse.SUCCESS);
				}else{
					pfResponse.setCode(FuncBaseResponse.FAILED);
				}
			} else
				pfResponse.setCode(FuncBaseResponse.SUCCESS);
			return pfResponse;
		} catch (Exception e) {
			logger.error("error call ownPosition", e);
			throw new PFServiceException(e.getMessage());
		}
	}

	@Override
	public PFResponse ownPositionSelf(int employerId, int positionId) throws PFServiceException {
		PFResponse pfResponse = new PFResponse();
		try {
			logger.debug("ownPositionSelf called employerId {}, positionId {} ", employerId, positionId);
			Position position = positionDao.getEntity(positionId);
			if (position == null || position.getEmployerId() != employerId) {
				pfResponse.setCode(FuncBaseResponse.FAILED);
			} else
				pfResponse.setCode(FuncBaseResponse.SUCCESS);
			return pfResponse;
		} catch (Exception e) {
			logger.error("error call ownPosition", e);
			throw new PFServiceException(e.getMessage());
		}
	}
	/**
	 * 获取创建测评的初始化信息
	 * 
	 * @param employerId
	 * @return
	 * @throws PFServiceException
	 */
	public PositionInitInfo getPositionInitInfo(int employerId) throws PFServiceException {
		PositionInitInfo initInfo = new PositionInitInfo();
		initInfo.setPositionSeries(positionSeriesDaoImpl.getPositionSeries(employerId));

		List<ConfigCodeName> codes = configDao.getConfigCode(ConfigCodeType.POSITION_LEVEL);
		List<PositionLevelInfo> levels = new ArrayList<PositionLevelInfo>();
		for (ConfigCodeName code : codes) {
			PositionLevelInfo info = new PositionLevelInfo(Integer.valueOf(code.getId().getCodeId()),
					code.getCodeName());
			levels.add(info);
		}
		initInfo.setPositionLevels(levels);
		return initInfo;
	}

	/**
	 * 创建职位
	 * 
	 * @param position
	 * @return
	 * @throws PFServiceException
	 */
	public PosResponse createPosition(Position position) throws PFServiceException {
		logger.debug("create position for paperId {} , employerId {} , positionId " + position.getPositionId(),
				position.getPaperId(), position.getEmployerId());
		if(position.getPaperId() == null){
			logger.error("create position paperId not be null ");
			throw new PFServiceException("create position paperId not be null ");
		}
		// Integer oldPositionId = position.getPositionId();
		PosResponse res = new PosResponse();
		position.setPreBuilt(Constants.PREBUILT_SELF);
		position.setPositionState(Constants.POSITION_STATE_NORMAL);
		if (position.getPositionId() == null)
			position.setPublishDate(new Timestamp(System.currentTimeMillis()));
		processPositionSeries(position);

		position.setModifyDate(new Timestamp(System.currentTimeMillis()));
		positionDao.saveOrUpdate(position);
		infoCollect.saveInfo(position.getEmployerId(), position.getPositionId(), position.getConfigInfo());

		PositionLog log = positionLogDao.getPositionLogByPosIdAndState(position.getPositionId(), 0);
		if (log == null) {// 新测评
			log = new PositionLog();
			log.setEmployerId(position.getEmployerId());
			log.setPositionState(0);
			log.setStateId(position.getPositionId().longValue());
			log.setPositionId(position.getPositionId());
		}
		log.setLogTime(new Timestamp(System.currentTimeMillis()));
		positionLogDao.saveOrUpdate(log);
		employerAuthDao.deleteAuth(position.getPositionId());
		List<EmployerAuthorizationIntf> employerAuths = position.getEmployerAuths();
		if (employerAuths != null) {
			for (EmployerAuthorizationIntf auth : employerAuths) {
				auth.setEmployerId(position.getEmployerId());
				auth.setPositionGranted(position.getPositionId());
				addEmployerAuthorization(auth);
			}
		}

		res.setCode(FuncBaseResponse.SUCCESS);
		res.setPositionId(position.getPositionId());
		return res;
	}

	private void processPositionSeries(Position position) {
		if (StringUtils.isNotEmpty(position.getSeriesName())) {
			logger.debug("need create positon series for name {} ", position.getSeriesName());
			List<PositionSeries> result = positionSeriesDaoImpl.getPositionSeriesByName(position.getSeriesName(),
					position.getEmployerId());
			if (CollectionUtils.isEmpty(result)) {
				PositionSeries series = new PositionSeries();

				series.setSeriesName(position.getSeriesName());
				series.setParentId(90);
				series.setSeriesType(3);
				series.setPrebuilt(0);
				series.setCreateBy(position.getEmployerId());
				series.setCreateDate(new Date());
				positionSeriesDaoImpl.saveOrUpdate(series);
				position.setSeriesId(series.getSeriesId());
			} else {
				position.setSeriesId(result.get(0).getSeriesId());
				logger.debug("the positon series has exist {}, size is {}", result.get(0).getSeriesId(), result.size());
			}

		}
	}

	@Override
	public List<PositionSeriesCustom> getPositionSeriesCustom(int employerId) {
		Map<Integer, PositionSeriesCustom> seriesToCustom = new TreeMap<Integer, PositionSeriesCustom>();
		List<PositionSeriesCustom> result = new ArrayList<PositionSeriesCustom>();
		List<Paper> papers = paperDao.getPrebuiltPaper();
		for (Paper paper : papers) {
			if (paper.getSeriesId() == Constants.POSITION_SEREIS_ID_SCHOOL) {
				continue;
			}
			PositionSeriesCustom custom = seriesToCustom.get(paper.getSeriesId());
			if (custom == null) {
				custom = new PositionSeriesCustom();
				custom.setSeriesId(paper.getSeriesId());
				custom.setSeriesName(positionSeriesDaoImpl.getEntity(paper.getSeriesId()).getSeriesName());
				seriesToCustom.put(paper.getSeriesId(), custom);
			}

			List<PositionSeriesLevel> levelPapers = custom.getLevelPapers();
			if (levelPapers == null) {
				levelPapers = new ArrayList<PositionSeriesLevel>();
				custom.setLevelPapers(levelPapers);
			}
			PositionSeriesLevel levelPaper = new PositionSeriesLevel();
			int level = paper.getLevel();
			levelPaper.setLevelId(level);
			int index = levelPapers.indexOf(levelPaper);
			if (index == -1) {// 不包含某种级别的试卷
				levelPaper.setPaperId(paper.getPaperId());
				levelPaper.setLevelName(configDao.getConfigCodeName(ConfigCodeType.POSITION_LEVEL, level + ""));
				levelPapers.add(levelPaper);
			} else {
				logger.info("the paper level {} has contained, so not add the paper {} ", level, paper.getPaperId());
			}
		}
		org.apache.commons.collections.CollectionUtils.addAll(result, seriesToCustom.values().iterator());
		List<Position> hasCustomedPositions = positionDao.getCustomedPaperPositions(employerId);
		for (PositionSeriesCustom custom : result) {
			for (PositionSeriesLevel level : custom.getLevelPapers()) {
				for (Position position : hasCustomedPositions) {
					if (position.getSeriesId().equals(custom.getSeriesId())
							&& position.getLevel() == level.getLevelId()) {
						level.setCustomed(1);
						break;
					}
				}
			}

		}
		return result;
	}

	@Override
	public PFResponse createPositionByCustom(int employerId, PositionQuickInfo quickInfo) throws PFServiceException {
		logger.debug("createPositionByCustom for employerId {}, paperIds {} ", employerId, quickInfo);
		PFResponse pf = new PFResponse();
		List<Integer> paperIds = quickInfo.getPaperIds();
		for (int paperId : paperIds) {
			Paper paper = paperDao.getEntity(paperId);
			Position position = new Position();
			position.setPaperId(paperId);
			position.setEmployerId(employerId);
			position.setSeriesId(paper.getSeriesId());
			position.setLevel(paper.getLevel());
			position.setTestType(paper.getTestType());
			PositionSeries series = positionSeriesDaoImpl.getEntity(paper.getSeriesId());
			String levelName = configDao.getConfigCodeName(ConfigCodeType.POSITION_LEVEL, paper.getLevel() + "");
			position.setPositionName(series.getSeriesName() + levelName + "工程师");
			List<PositionInfoExt> configExts = candidateInfoDao.getCandConfigInfoExts(employerId, null);
			List<PositionInfoConfig> configInfo = new ArrayList<PositionInfoConfig>();
			for (PositionInfoExt configExt : configExts) {
				PositionInfoConfig config = new PositionInfoConfig();
				config.setInfoId(configExt.getId().getInfoId());
				config.setSeq(configExt.getSeq());
				configInfo.add(config);
			}
			position.setConfigInfo(configInfo);
			position.setEmployerAuths(quickInfo.getEmployerAuths());
			createPosition(position);
		}
		pf.setCode(FuncBaseResponse.SUCCESS);
		return pf;
	}

	@Override
	public List<PositionOutInfo> getPositionOutInfos(int employerId) {
		logger.debug("getPositionOutInfos for employerId {} ", employerId);
		List<PositionOutInfo> outInfos = positionDao.getPositionOutInfos(employerId);
		logger.debug("getPositionOutInfos for employerId {}, outInfo size is {} ", employerId, outInfos.size());
		return outInfos;
	}

	@Override
	public List<PositionInfo> getPositionInfoOfSample() throws PFServiceException {
		try {
			List<PositionInfo> results = new ArrayList<PositionInfo>();
			List<Position> positions = positionDao.getPositionsOfSample();
			for (Position position : positions) {
				PositionInfo info = new PositionInfo();
				info.setInvitatedNum(inviteDao.getInvitationNumber(null, position.getPositionId()));
				info.setPosition(position);
				List<PaperQuestionTypeInfo> questionTypes = paperImpl
						.getPaperQuestionTypeInfo(position.getPositionId());
				info.setPaperQuestionTypes(questionTypes);
				List<CandidateReport> list = reportDao.getCandidateReportOfSample(position.getPositionId());
				info.setPosMessage(getMsgs(list, true));
				results.add(info);
			}
			return results;
		} catch (Exception e) {
			logger.error("getPositionInfoOfSample error ", e);
			throw new PFServiceException(e);

		}

	}

	@Override
	public Position getPositionByPositionId(int employerId, int positionId) throws Exception {
		Position position = positionDao.getPosition(positionId);
		if(position.getGroupFlag() == Constants.GROUP_FLAG_PARENT){
			//获取报告数  失败邀请数  邀请数
			position.setReportNum(Long.valueOf(reportImpl.getReportNumber(employerId, positionId, null)));  
			
			GetInvitationInfoParam param = new GetInvitationInfoParam();
			param.setEmployerId(employerId);
			param.setPositionId(positionId);
			param.setInvitationState(0);//失败邀请
			Page newPage = new Page(Integer.MAX_VALUE,1);
			param.setPage(newPage);
			List<Invitation> list = inviteDao.getInvitaionInfo(param);
			Map<String, Integer> faileDcandidateIds = new HashMap<String, Integer>();
			for (Invitation invitation : list) {
				if(faileDcandidateIds.containsKey(invitation.getCandidateName() +"_" + invitation.getCandidateEmail())){//已经包含
					logger.debug("failed invitations contains more than one for candidateInfo {} ", invitation.getCandidateName() +"_" + invitation.getCandidateEmail());
					continue;
				}
				faileDcandidateIds.put(invitation.getCandidateName() +"_" + invitation.getCandidateEmail(), 1);
			}
			position.setInvitationFailNum(Long.valueOf(faileDcandidateIds.size()))	;
			 
			position.setInvitatedNum(invite.getInvitationNum(employerId, positionId));
			
			List<PositionRelation> relations = positionRelationDao.getPositionRelationByPositionGroupId(position.getPositionId());
			for(PositionRelation relation : relations){
				Position childPos = positionDao.getEntity(relation.getId().getPositionId());
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
		}
		return position;
	}

	@Override
	public List<PositionTestTypeInfo> getPositionTestTypeInfo(int employerId) {
		List<Integer> list = employerAuthDaoImpl.getGrantedId(employerId);
		logger.debug("getPositionTestTypeInfo positionIds is {} for employerId {}" ,list, employerId);
		List<PositionTestTypeInfo> infos = positionDao.getPositionTestTypeInfo(employerId,list);
		/*for (PositionTestTypeInfo info : infos) {
			info.setReportStatusCountInfo(schoolReportDao.getSchoolReportStatusCountByEmployerId(employerId,
					info.getTestType()));
		}*/
		return infos;
	}

	@Override
	public List<Position> getPositionByTestType(int employerId, int testType, Page page) {
		List<Position> result = new ArrayList<Position>();
		List<Integer> list = employerAuthDaoImpl.getGrantedId(employerId);
//		list.add(employerId);
        logger.debug("getPositionByTestType positionIds is {} for employerId {}" ,list, employerId);
		List<Position> poses = positionDao.getPosition(employerId, list, page,testType);
		if (CollectionUtils.isEmpty(poses))
			return null;
		for (Position pl : poses) {
			Position position = positionDao.getPosition(pl.getPositionId());
			position.setReportNum(Long.valueOf(reportImpl.getReportNumber(employerId, pl.getPositionId(), null)));
			/*position.setReportStatusCountInfo(schoolReportDao.getSchoolReportStatusCountByPositionId(employerId,
					pl.getPositionId()));*/
			result.add(position);
		}
		return result;
	}

	@Override
	public PFResponse addEmployerAuthorization(EmployerAuthorizationIntf employerAuth) throws PFServiceException {
		logger.debug("addEmployerAuthorization the employerAuth is {} ", employerAuth);
		PFResponse result = new PFResponse();
		try {
			PFResponse res = ownPositionSelf(employerAuth.getEmployerId(), employerAuth.getPositionGranted());
			if (!res.getCode().equals(FuncBaseResponse.SUCCESS)) {
				logger.info("not  its self position {} for employerid {} ", employerAuth.getPositionGranted(),
						employerAuth.getEmployerId());
				result.setCode(FuncBaseResponse.NOTSELFPOSITION);
			}
			Employer employer = employerDaoImpl.getEmployerByEmail(employerAuth.getEmailGranted(), null);
			if (employer == null) {
				result.setCode(FuncBaseResponse.NONEEXIST);// 账号不存在
				logger.debug("the mail not exist {}", employerAuth.getEmailGranted());
				return result;
			}
			if (employer.getEmployerId() == employerAuth.getEmployerId()) {// 不需要授权给自己
				logger.debug("the position is it self {}, positionId {} ", employerAuth.getEmployerId(),
						employerAuth.getPositionGranted());
				result.setCode(FuncBaseResponse.SELFGRANTED);//
				return result;
			}
			EmployerAuthorization auth = new EmployerAuthorization();
			EmployerAuthorizationId id = new EmployerAuthorizationId();
			id.setEmployerGranted(employer.getEmployerId());
			id.setEmployerId(employerAuth.getEmployerId());
			id.setPositionGranted(employerAuth.getPositionGranted());
			auth.setId(id);
			employerAuthDao.save(auth);
			result.setCode(FuncBaseResponse.SUCCESS);
		} catch (Exception e) {
			logger.error("error addEmployerAuthorization ", e);
			throw new PFServiceException(e);
		}
		return result;
	}

	/**
	 * 检验邮箱是否可以授权
	 * 
	 * @param email
	 * @return
	 */
	public PFResponse checkAuthorEmail(int employerId, String email) {
		PFResponse result = new PFResponse();
		
		logger.debug("checkAuthorEmail employeId {} , email {} ", employerId, email);
		String suffixMail = email.substring(email.indexOf("@") + 1);
		if(configDao.getConfigCode("NOT_SUPPORT_EMAIL", suffixMail) != null){
			logger.warn("the checkAuthorEmail for email  {}  not support ", email);
			result.setCode(FuncBaseResponse.EMAILNOTSUPPORT);
			return result;
		}
		
		Employer employer = employerDaoImpl.getEmployerByEmail(email, null);
		if (employer == null) {
			result.setCode(FuncBaseResponse.NONEEXIST);// 账号不存在
			return result;
		}
		if(employer.getAcctType() != null && employer.getAcctType() == Constants.SAMPLE_ACCT_TYPE){//体验或者第三方账号类型也认为是账号不存在
			result.setCode(FuncBaseResponse.NONEEXIST);// 账号不存在
			return result;
		}
		EmployerTrialApply apply = employerTrialDao.getEmployerTrialByEmail(email);
		if(apply != null && apply.getActivated() == 0){//没激活也认为不存在
			result.setCode(FuncBaseResponse.NONEEXIST);// 账号不存在
			return result;
		}
		if(apply == null){
			logger.warn("the employer has not trial info {} ", email);
		}
		if (employer.getEmployerId() == employerId) {// 不需要授权给自己
			result.setCode(FuncBaseResponse.SELFGRANTED);//
			return result;
		}
		result.setCode(FuncBaseResponse.SUCCESS);
		return result;
	}

	@Override
	public List<PositionPaperInfo> getPositionInfoWithPaperCount(int employerId, Page page) throws PFServiceException {

		List<PositionPaperInfo> result = new ArrayList<PositionPaperInfo>();
		List<Integer> list = employerAuthDaoImpl.getGrantedId(employerId);
		// list.add(employerId);
		for (Integer id : list)
			logger.debug("getPositionInfoWithPaperCount grantedPositionIds is " + id);
		List<Position> positions = positionDao.getPosition(employerId, list, page,null);
		if (CollectionUtils.isEmpty(positions))
			return null;
		long times = 0;
		for (Position position : positions) {
			PositionPaperInfo positionInfo = new PositionPaperInfo();
			logger.debug(" position id is " + position.getPositionId());

			PositionEx posEx = new PositionEx();
			try {
				PropertyUtils.copyProperties(posEx, position);
			} catch (Exception e) {
				logger.error("error copy ",e);
				throw new PFServiceException(e);
			}
//			posEx.setLevelString(configDao.getConfigCodeName(ConfigCodeType.POSITION_LEVEL, posEx.getLevel() + ""));

			// 职位序列
//			PositionSeries series = seriesDao.getEntity(pos.getSeriesId());
//			if (null != series) {
//				posEx.setpLString(configDao.getConfigCodeName(ConfigCodeType.PROGRAM_LANGUAGE,
//						series.getPositionLanguage() + ""));
//			}
			posEx.setPublishDateDesc(PrettyTimeMaker.format(position.getPublishDate()));
			positionInfo.setPosition(posEx);
			

			List<PaperCountInfo> paperInfos = new ArrayList<PaperCountInfo>();
			positionInfo.setPaperInfos(paperInfos);
			if(position.getGroupFlag() == Constants.GROUP_FLAG_NORMAL){
				PaperCountInfo paperInfo = changePaperInfoFromPosition(employerId,position);
				paperInfos.add(paperInfo);
				paperInfo.setRelation(Constants.POSITION_RELATION_MUST);
			}else if(position.getGroupFlag() == Constants.GROUP_FLAG_PARENT){
				List<PositionRelation> relations = positionRelationDao.getPositionRelationByPositionGroupId(position.getPositionId());
				Collections.sort(relations, new Comparator<PositionRelation>() {//先必考 后选考

					@Override
					public int compare(PositionRelation o1, PositionRelation o2) {
						return o1.getRelation().compareTo(o2.getRelation());
					}
					
				});
				for(PositionRelation relation : relations){
					Position childPos = positionDao.getEntity(relation.getId().getPositionId());
					PaperCountInfo paperInfo = changePaperInfoFromPosition(employerId, childPos);
					paperInfo.setRelation(relation.getRelation());
					paperInfos.add(paperInfo);
				}
				
			}else{
				logger.error("the position is not normal or parent id {} , group flag {} ", position.getPositionId(), position.getGroupFlag());
			}
			
			//报告数
			long time1 = System.currentTimeMillis();
			List<SchoolCandidateReport>  reports =   schoolReportDao.getAllSchoolReportList(employerId, position.getPositionId(),null, null);
			if (CollectionUtils.isEmpty(reports)){
				positionInfo.setReportCandidateNumber(0);

			}else{
				Map<Integer,Integer> candidateIds =new HashMap<Integer, Integer>();
				for (SchoolCandidateReport report : reports) {
					candidateIds.put(report.getCandidateId(), 1);
				}
				positionInfo.setReportCandidateNumber(candidateIds.size());
			}

			times += (System.currentTimeMillis() - time1);
			
			//未检测信号
			int positionId = position.getPositionId();
			List<ActivityRecruitAddress> addresses = activityRecruitAddressDao.getAllAddressByPositionId(positionId);
			List<ActivityRecruitAddress> unTestAddresses = new ArrayList<ActivityRecruitAddress>();
			
			Map<ActivityAddressByColleege,ActivityAddressByColleege> unTestAddes = new HashMap<ActivityAddressByColleege, ActivityAddressByColleege>();
			for(ActivityRecruitAddress address : addresses){
				if(address.getTotalNum() != null ){ //只判断移动和联通
					continue;
				}
				ActivityAddressByColleege keyAdd = new ActivityAddressByColleege();
				keyAdd.setCollege(address.getCollege());
				keyAdd.setCity(address.getCity());
				if(unTestAddes.get(keyAdd) != null){
					unTestAddes.get(keyAdd).getAddresses().add(address.getAddress());
				}else{
					unTestAddes.put(keyAdd, keyAdd);
					keyAdd.getAddresses().add(address.getAddress());
				}
//				unTestAddresses.add(address);
			}
			unTestAddresses.addAll(unTestAddes.values());
			positionInfo.setUnTestAddresses(unTestAddresses);
			
			Map<Integer, Employer> employerMap = new HashMap<Integer, Employer>();
			Map<Integer, Company> companyMap = new HashMap<Integer, Company>();
			if (employerId != position.getEmployerId()) {
				if (!employerMap.containsKey(position.getEmployerId()))
					employerMap.put(position.getEmployerId(), employerDaoImpl.getEntity(position.getEmployerId()));
				
				int companyId = employerMap.get(position.getEmployerId()).getCompanyId();
				if(!companyMap.containsKey(companyId)){
					companyMap.put(companyId, companyDao.getEntity(companyId));
				}
				positionInfo.setEmployerId(position.getEmployerId());
				positionInfo.setEmployerName(employerMap.get(position.getEmployerId()).getEmployerName());
				positionInfo.setCompanyName(companyMap.get(companyId).getCompanyName());
			}
			if (null != positionInfo) {
				result.add(positionInfo);
			}
		}
		logger.debug("get report number waste time {} for employerId {} for page {}  ", new Object[]{times,employerId,page});
		return result;
	}
	
	
	private PaperCountInfo changePaperInfoFromPosition(Integer employerId, Position position){
		Paper paper = paperDao.getEntity(position.getPaperId());
		PaperCountInfo paperInfo = new PaperCountInfo();
		paperInfo.setPaperId(paper.getPaperId());
		paperInfo.setPaperName(paper.getPaperName());
//		paperInfo.setReportNum(paperDao.getReportUnreadCount(employerId,position.getPositionId()));
		return paperInfo;
	}

	@Override
	public List<ActivityRecruitAddress> getUnTestActivityRecruitAddressesByPositionId(int positionId) {
		
		List<ActivityRecruitAddress> list = activityRecruitAddressDao.getAllAddressByPositionId(positionId);
		if(list == null || list.size() == 0 ){
			return null;
		}
		List<ActivityRecruitAddress> unTestes = new ArrayList<ActivityRecruitAddress>();
		for(ActivityRecruitAddress address : list){
			if(address.getTotalNum() == null){
				unTestes.add(address);
			}
		}
		return unTestes;
	}
	
	@Override
	public List<ActivityRecruitAddress> getActivityRecruitAddressesByPositionId(int positionId) {
		
		List<ActivityRecruitAddress> list = activityRecruitAddressDao.getAllAddressByPositionId(positionId);
		if(list == null || list.size() == 0 ){
			return null;
		}
		return list;
	}

	@Override
	public PositionActivityAddressInfo getPositionActivityAddressInfo(int positionId) {
		List<ActivityRecruitAddress> addresses = activityRecruitAddressDao.getAllAddressByPositionId(positionId);
		if(addresses == null || addresses.size() == 0 ){
			return null;
		}
		PositionActivityAddressInfo info = new PositionActivityAddressInfo();

		List<ActivityRecruitAddress> unTestAddresses = new ArrayList<ActivityRecruitAddress>();
		
		Map<ActivityAddressByColleege,ActivityAddressByColleege> testedAddress = new HashMap<ActivityAddressByColleege, ActivityAddressByColleege>();
		for(ActivityRecruitAddress address : addresses){
			if(address.getTotalNum() != null ){ //只判断移动和联通
				ActivityAddressByColleege keyAdd = new ActivityAddressByColleege();
				keyAdd.setCollege(address.getCollege());
				keyAdd.setCity(address.getCity());
				if(testedAddress.get(keyAdd) != null){
					testedAddress.get(keyAdd).getAddresses().add(address.getAddress());
				}else{
					testedAddress.put(keyAdd, keyAdd);
					keyAdd.getAddresses().add(address.getAddress());
				}
				if(address.getTotalNum() > keyAdd.getSupportNumber()){
					keyAdd.setSupportNumber(address.getTotalNum());
				}
			}else{
				unTestAddresses.add(address);
			}
			
//			unTestAddresses.add(address);
		}
		
		List<ActivityRecruitAddress> testedAddresses = new ArrayList<ActivityRecruitAddress>();
		testedAddresses.addAll(testedAddress.values());
		info.setTestedAddresses(testedAddresses);
		info.setUnTestAddresses(unTestAddresses);
		return info;
	}

    /**
     * 根据用户Id
     * @param employerId
     * @return
     */
	@Override
    public List<PaperOutInfo> getPaperOutInfo(int employerId){
		Page page = new Page();
		page.setPageSize(Integer.MAX_VALUE);
		page.setRequestPage(1);
    	List<Position> positions = positionDao.getPosition(employerId, new ArrayList<Integer>(), page, null);
    	List<PaperOutInfo> papers= new ArrayList<PaperOutInfo>();
    	for(Position position : positions){
    		PaperOutInfo paperInfo = new PaperOutInfo();
    		paperInfo.setPaperId(position.getPositionId());
    		paperInfo.setPaperName(position.getPositionName());
    		paperInfo.setCreateDate(position.getPublishDate());
    		papers.add(paperInfo);
    	}
    	logger.debug("getPaperOutInfo for employerId {} , papers {} ", employerId, papers);
    	return papers;
    }
}
