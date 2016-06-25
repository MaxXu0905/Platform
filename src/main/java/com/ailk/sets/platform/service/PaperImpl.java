package com.ailk.sets.platform.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.platform.dao.impl.PositionSeriesParametersDaoImpl;
import com.ailk.sets.platform.dao.impl.PositionSkillRecommendDaoImpl;
import com.ailk.sets.platform.dao.impl.QbSkillDegreeDaoImpl;
import com.ailk.sets.platform.dao.impl.StatQuestionByseriesDegreeDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IConfigDao;
import com.ailk.sets.platform.dao.interfaces.IConfigSysParamDao;
import com.ailk.sets.platform.dao.interfaces.IPaperDao;
import com.ailk.sets.platform.dao.interfaces.IPaperSkillDao;
import com.ailk.sets.platform.dao.interfaces.IPositionDao;
import com.ailk.sets.platform.dao.interfaces.IQbBaseDao;
import com.ailk.sets.platform.dao.interfaces.IQbQuestionDao;
import com.ailk.sets.platform.dao.interfaces.IQbSkillDao;
import com.ailk.sets.platform.domain.DegreeToSkillLabels;
import com.ailk.sets.platform.domain.PaperSkill;
import com.ailk.sets.platform.domain.PaperSkillId;
import com.ailk.sets.platform.domain.PositionAnalysisResult;
import com.ailk.sets.platform.domain.PositionSeriesParameters;
import com.ailk.sets.platform.domain.PositionSeriesParametersId;
import com.ailk.sets.platform.domain.PositionSkillRecommend;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.common.FuncBaseResponse;
import com.ailk.sets.platform.intf.common.PaperPartSeqEnum;
import com.ailk.sets.platform.intf.domain.DegreeToSkills;
import com.ailk.sets.platform.intf.domain.paper.Paper;
import com.ailk.sets.platform.intf.domain.skilllabel.QbSkillDegree;
import com.ailk.sets.platform.intf.empl.domain.ConfigCodeName;
import com.ailk.sets.platform.intf.empl.domain.PaperInfo;
import com.ailk.sets.platform.intf.empl.domain.PaperQuestionTypeInfo;
import com.ailk.sets.platform.intf.empl.domain.PaperSet;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;
import com.ailk.sets.platform.intf.empl.service.ILabelAnalysis;
import com.ailk.sets.platform.intf.empl.service.IPaper;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.PaperInitInfo;
import com.ailk.sets.platform.intf.model.PaperResponse;
import com.ailk.sets.platform.intf.model.qb.QbBase;
import com.ailk.sets.platform.intf.model.qb.QbBaseModelInfo;
import com.ailk.sets.platform.intf.model.qb.QbSkill;
import com.ailk.sets.platform.intf.model.qb.QbSkillModelInfo;
import com.ailk.sets.platform.service.local.IQbQuestionToPartProcess;
import com.ailk.sets.platform.service.paperpart.process.IPaperPartProcess;
import com.ailk.sets.platform.service.paperpart.process.PaperPartProcessFactory;
import com.ailk.sets.platform.service.paperpart.process.PaperPartProcessSchool;
import com.ailk.sets.platform.util.PaperCreateUtils;

@Transactional(rollbackFor = Exception.class)
public class PaperImpl implements IPaper {

	private Logger logger = LoggerFactory.getLogger(PaperImpl.class);
	@Autowired
	private IPaperDao paperDao;
	@Autowired
	private IQbBaseDao qbBaseDao;
	@Autowired
	private IQbSkillDao qbSkillDao;
	
	@Autowired
	private IQbQuestionToPartProcess qbQuestionToPartProcess;
	
	@Autowired
	private QbSkillDegreeDaoImpl qbSkillDegreeDaoImpl;
	
	@Autowired
	private IConfigSysParamDao configSysParamDao;
	
	@Autowired
	private ILabelAnalysis label;
	@Autowired
	private PaperPartProcessFactory paperPartProcessFactory;
	@Autowired
	private IConfigDao configDao;
	@Autowired
	private PositionSeriesParametersDaoImpl positionSeriesParametersDaoImpl;
	@Autowired
	private StatQuestionByseriesDegreeDaoImpl statQuestionByseriesDegreeDaoImpl;
	@Autowired
	private PositionSkillRecommendDaoImpl positionSkillRecommendDaoImpl;
	@Autowired
	private IPaperSkillDao paperSkillDao;
	@Autowired
	private IPositionDao positionDao;
	@Autowired
	private PaperPartProcessSchool paperPartProcessSchool;
	@Autowired
	private IQbQuestionDao qbQuestionDao;
	@Override
	public List<PaperInfo> getPaperInfo(int seriesId, int level, int employerId, int testType) throws Exception {
		logger.debug("getPaperInfo  the seriesId {}, level {}, employerId " + employerId +", testType " + testType, seriesId, level);
		return paperDao.getPaperInfo(seriesId, level, employerId,testType);
	}
	@Override
	public 	List<PaperQuestionTypeInfo> getPaperQuestionTypeInfo(int positionId)  throws PFServiceException{
		logger.debug("getPaperQuestionTypeInfo for positionId {} ", positionId);
		Position pos = positionDao.getEntity(positionId);
		Paper paper = paperDao.getEntity(pos.getPaperId());
		PaperInfo info = paperDao.getPaperInfoFromPaper(paper);
		return info.getTypeInfos();
	}

	/**
	 * 获取某用户可以看到的题库列表,创建试卷时使用
	 * 
	 * @param employerId
	 * @return
	 */
	public List<QbBaseModelInfo> getQbBases(int employerId, int seriesId,int levelId) throws PFServiceException {
		logger.debug("getQbBases for employerId {} , seriesId {} ", employerId, seriesId);
		try {
			List<QbSkillDegree> degrees = qbSkillDegreeDaoImpl.getQbSkillDegrees();
			List<QbBaseModelInfo> list = new ArrayList<QbBaseModelInfo>();
			List<QbBase> qbBases = qbBaseDao.getQbBases(employerId);
			for (QbBase qbBase : qbBases) {
				QbBaseModelInfo modelInfo = new QbBaseModelInfo();
				PropertyUtils.copyProperties(modelInfo, qbBase);
				List<QbSkill> qbSkills = qbSkillDao.getQbSkillsOfLowLevel(qbBase.getQbId());
				List<QbSkillModelInfo> skills = new ArrayList<QbSkillModelInfo>();
				modelInfo.setSkills(skills);
				for (QbSkill qbSkill : qbSkills) {
					QbSkillModelInfo skill = new QbSkillModelInfo();
					PropertyUtils.copyProperties(skill, qbSkill);
					if(qbBase.getPrebuilt() == Constants.PREBUILT_SELF){//自定义题库 
						if(qbQuestionDao.hasQuestionOfSelfSkill(skill.getSkillId())){
							skills.add(skill);
						}else{
							logger.debug("not have any quesiton for skill {} ", skill.getSkillId());
						}
						continue;
					}
					for(QbSkillDegree degree : degrees){
						QbSkillDegree qbDegree = new QbSkillDegree(degree.getDegreeId());
						int number = statQuestionByseriesDegreeDaoImpl.getObjectQuestionNumber(seriesId, levelId, qbSkill.getSkillId(), degree.getDegreeId());
					    if(number < degree.getQuestionLeast()){
					    	logger.debug("has not enough qestions for skillId {} degreeeId {}  number " + number +",serisId " + seriesId, qbSkill.getSkillId(),degree.getDegreeId());
					        continue;
					    }
					    if(number < degree.getQuestionMost()){
					    	qbDegree.setQuestionMost(number);// 最多选number道题
					    	logger.debug("has not most qestions for skillId {} degreeeId {} number " + number +",serisId " + seriesId , qbSkill.getSkillId(),degree.getDegreeId());
					    }else{
					    	qbDegree.setQuestionMost(degree.getQuestionMost());
					    }
					    List<QbSkillDegree> degreeToQuestionNum = skill.getDegreeToQuestionNum();
					    if(degreeToQuestionNum == null){
					    	degreeToQuestionNum = new ArrayList<QbSkillDegree>();
					    	skill.setDegreeToQuestionNum(degreeToQuestionNum);
					    }
					    qbDegree.setQuestionLeast(degree.getQuestionLeast());
					    qbDegree.setDegreeName(degree.getDegreeName());
					    degreeToQuestionNum.add(qbDegree);
					}
					if(skill.getDegreeToQuestionNum() != null){//精通 熟悉 掌握 了解都没有题的过滤掉
						skills.add(skill);
					}
				}
				
				if(skills.size() ==  0 ){
					logger.info("not have enough quesitons for qbId {} ", qbBase.getQbId());
					if(modelInfo.getPrebuilt() != Constants.PREBUILT_SYS){
						 list.add(modelInfo);
					}
				}else{
				    list.add(modelInfo);
				}
			}
			logger.debug("getQbBases for employerId {} , result size is {} ",employerId, list.size());
			return list;
		} catch (Exception e) {
			logger.error("call getQbBases error  ", e);
			throw new PFServiceException(e.getMessage());
		}
	}

	/**
	 * 获取进入试卷的显示的初始化信息
	 * @param employerId
	 * @return
	 */
	public  PaperInitInfo getPaperInitInfo(int employerId, int seriesId,int level) throws PFServiceException{
		logger.debug("getPaperInitInfo for employerId {}, seriesId {} level  " + level, employerId, seriesId);
		try {
			PaperInitInfo initInfo = new PaperInitInfo();
			initInfo.setQbBaseInfos(getQbBases(employerId,seriesId,level));
			initInfo.setDegreeInfos(qbSkillDegreeDaoImpl.getQbSkillDegrees());
			initInfo.setObjectAvgTime(Integer.valueOf(configSysParamDao.getConfigParamValue(Constants.CONFIG_SYS_OBJECT_AVG_TIME)));
			initInfo.setSubjectAvgTime(Integer.valueOf(configSysParamDao.getConfigParamValue(Constants.CONFIG_SYS_SUBECT_AVG_TIME)));
			PositionSeriesParameters pos = positionSeriesParametersDaoImpl.getPositionSeriesParameters(new PositionSeriesParametersId(seriesId,
					Constants.QUESTION_TYPE_NAME_PROGRAM));
			if(pos != null){
				initInfo.setLeastSubjectNum(pos.getQuestionLeast());
				initInfo.setMostSubjectNum(pos.getQuestionMost());
			}else{
				logger.debug("not found PositionSeriesParameters for seriesId {} ",seriesId);
			}
			initInfo.setInitDegreeSkills(getInitDegreeSkills(seriesId, level));
			return initInfo;
		} catch (Exception e) {
			logger.error("call getPaperInitInfo error  ", e);
			throw new PFServiceException(e.getMessage());
		}
	}
	
	
	@Override
	public PaperResponse createPaper(PaperSet paperSet) throws PFServiceException {
		logger.debug("create paper paperSet {} ", paperSet);
		PaperResponse res = new PaperResponse();
		Collection<Long> questionIds = paperSet.getQuestionIds();
		Map<PaperPartSeqEnum, List<QbQuestion>> partQuestions = qbQuestionToPartProcess
				.getPaperPartQuestions(questionIds);
		Paper paper = paperSet.getPaper();
		Timestamp time = new Timestamp(System.currentTimeMillis());
		paper.setCreateDate(time);
		paper.setModifyDate(time);
		paperDao.saveOrUpdate(paper);
		
		createPaperSkills(paper);

		List<ConfigCodeName>  configs = configDao.getConfigCode(Constants.CONFIG_PAPER_PART);
		for(ConfigCodeName config : configs){
			int seq = Integer.valueOf(config.getId().getCodeId());
			PaperPartSeqEnum partSeq = PaperPartSeqEnum.valueOf(seq);
			IPaperPartProcess process = paperPartProcessFactory.getPaperPartProcess(partSeq);
			if(process == null){
				logger.debug("not found process for partSeq {} ", partSeq.getValue());
				continue;
			}
			process.processPaperParts(paperSet, partQuestions.get(partSeq));
		}
		res.setCode(FuncBaseResponse.SUCCESS);
		res.setPaperId(paper.getPaperId());
		return res;
	}
	/**
	 * 
	 * @param paperSet
	 * @return
	 * @throws PFServiceException
	 */
	public PaperResponse createCampusPaper(PaperSet paperSet) throws PFServiceException{

		logger.debug("create createCampusPaper paperSet {} ", paperSet);
		PaperResponse res = new PaperResponse();
		Collection<Long> questionIds = paperSet.getQuestionIds();
		Map<PaperPartSeqEnum, List<QbQuestion>> partQuestions = qbQuestionToPartProcess
				.getCampusPaperPartQuestions(questionIds);
		Paper paper = paperSet.getPaper();
		Timestamp time = new Timestamp(System.currentTimeMillis());
		paper.setCreateDate(time);
		paper.setModifyDate(time);
		paperDao.saveOrUpdate(paper);
		createPaperSkills(paper);
		paperPartProcessSchool.processPaperParts(paperSet, partQuestions.get(PaperPartSeqEnum.OBJECT));
		partQuestions.remove(PaperPartSeqEnum.OBJECT);
		//选择题通过paperPartProcessSchool生成  校招也有智力  问答题， 
		List<ConfigCodeName>  configs = configDao.getConfigCode(Constants.CONFIG_PAPER_PART);
		for(ConfigCodeName config : configs){
			int seq = Integer.valueOf(config.getId().getCodeId());
			PaperPartSeqEnum partSeq = PaperPartSeqEnum.valueOf(seq);
			IPaperPartProcess process = paperPartProcessFactory.getPaperPartProcess(partSeq);
			if(process == null || partQuestions.get(partSeq) == null){
				logger.debug("not found process for partSeq {}  or partQuestions.get(partSeq) is {} ", partSeq.getValue(),partQuestions.get(partSeq));
				continue;
			}
			process.processPaperParts(paperSet, partQuestions.get(partSeq));
		}
		res.setCode(FuncBaseResponse.SUCCESS);
		res.setPaperId(paper.getPaperId());
		return res;
	
	}
	/**
	 * @param paper
	 * @return
	 * @throws PFServiceException
	 */
	@Override
	public List<DegreeToSkills> analysisSkills(Paper paper) throws PFServiceException{
		logger.debug("analysis for seriesId {} skillDesc {} ", paper.getSeriesId(),paper.getSkillDesc());
		PositionAnalysisResult r =  label.analysisSentences(paper.getSeriesId()+"", paper.getSkillDesc());
		List<DegreeToSkills> list = new ArrayList<DegreeToSkills>();
		
		List<DegreeToSkillLabels> views =  r.getDegreeToSkillLableses();
		if(views == null){
			return null;
		}
		for(DegreeToSkillLabels view : views){
			DegreeToSkills degreeToSkill = new DegreeToSkills();
			degreeToSkill.setLabelDegree(view.getLabelDegree());
			degreeToSkill.setSkills(PaperCreateUtils.changePositionSeriesSkillViewToSkills(view.getSkillLabels()));
			list.add(degreeToSkill);
		}
        return list;
	}
	
	public List<DegreeToSkills> getInitDegreeSkills(int seriesId, int levelId){
		 Map<QbSkillDegree,DegreeToSkills> map = new HashMap<QbSkillDegree, DegreeToSkills>();
		 List<DegreeToSkills>  list = new ArrayList<DegreeToSkills>();
		 List<PositionSkillRecommend> recommonds = positionSkillRecommendDaoImpl.getPositionSkillRecommends(seriesId, levelId);
		 for(PositionSkillRecommend rec : recommonds){
			 QbSkillDegree skillDegree = qbSkillDegreeDaoImpl.getQbSkillDegree(rec.getDegreeId());
			 DegreeToSkills degreeToSkills =  map.get(skillDegree);
			 if(degreeToSkills == null){
				 degreeToSkills = new DegreeToSkills();
				 degreeToSkills.setLabelDegree(skillDegree);
				 map.put(skillDegree, degreeToSkills);
				 degreeToSkills.setSkills(new ArrayList<QbSkill>());
			 }
			 QbSkill skill = qbSkillDao.getEntity(rec.getId().getSkillId());
			 QbSkill add = new QbSkill();
			 add.setSkillId(skill.getSkillId());
			 add.setSkillName(skill.getSkillName());
			 degreeToSkills.getSkills().add(add);
		 }
		 CollectionUtils.addAll(list, map.values().iterator());
		 return list;
	}
	private void createPaperSkills(Paper paper){
		List<DegreeToSkills> degreeToSkills =paper.getDegreeToSkills();
        if(CollectionUtils.isEmpty(degreeToSkills)){
			logger.info("the degreeToSkills is null for employerId {} , paperName is {} ", paper.getCreateBy(), paper.getPaperName());
			return;
		}
		List<String> allSkills = new ArrayList<String>();
		for (DegreeToSkills degreeToSkill : degreeToSkills) {
			for (QbSkill skill : degreeToSkill.getSkills()) {
				if (allSkills.contains(skill.getSkillId())) {
					logger.warn("the skill is contained {} ", skill.getSkillName());
					continue;
				}
				allSkills.add(skill.getSkillId());
				PaperSkill pk = new PaperSkill();
				PaperSkillId pkId = new PaperSkillId(paper.getPaperId(), skill.getSkillId());
				pk.setId(pkId);
				pk.setDegreeId(degreeToSkill.getLabelDegree().getDegreeId());
				pk.setSkillSeq(1);
				pk.setQuestionNum(skill.getQuestionNum());
				paperSkillDao.saveOrUpdate(pk);
			}
		}
	}
	@Override
	public PaperResponse createPaperByQbId(int qbId, Paper paper) throws PFServiceException {
		logger.debug("create createPaperByQbId  paper {} for qbId {} ", paper,qbId);
		PaperResponse res = new PaperResponse();
		List<QbQuestion> questions = qbQuestionDao.getListByQbId(qbId);
		Collection<Long> questionIds = new ArrayList<Long>();
		for(QbQuestion qbQuestion : questions){
			if(qbQuestion.getQuestionType().equals(Constants.QUESTION_TYPE_NAME_INTERVIEW)){//面试题需要过滤，需要面试题组即可
				continue;
			}
			questionIds.add(qbQuestion.getQuestionId());
		}
		if(questionIds.size() == 0){
			throw new PFServiceException("not found any quesiton for qbId " + qbId );
		}
		Map<PaperPartSeqEnum, List<QbQuestion>> partQuestions = qbQuestionToPartProcess
				.getPaperPartQuestions(questionIds);
		Timestamp time = new Timestamp(System.currentTimeMillis());
		paper.setCreateDate(time);
		paper.setModifyDate(time);
		paperDao.saveOrUpdate(paper);
		createPaperSkills(paper);
		PaperSet paperSet = new PaperSet();
		paperSet.setPaper(paper);
		List<ConfigCodeName>  configs = configDao.getConfigCode(Constants.CONFIG_PAPER_PART);
		for(ConfigCodeName config : configs){
			int seq = Integer.valueOf(config.getId().getCodeId());
			PaperPartSeqEnum partSeq = PaperPartSeqEnum.valueOf(seq);
			IPaperPartProcess process = paperPartProcessFactory.getPaperPartProcess(partSeq);
			if(process == null){
				logger.debug("not found process for partSeq {} ", partSeq.getValue());
				continue;
			}
			process.processPaperParts(paperSet, partQuestions.get(partSeq));
		}
		res.setCode(FuncBaseResponse.SUCCESS);
		res.setPaperId(paper.getPaperId());
		return res;
	}
	@Override
	public PaperResponse createCampusPaperByQbId(int qbId, Paper paper) throws PFServiceException {

		logger.debug("create createCampusPaperByQbId paper {} ", paper);
		PaperResponse res = new PaperResponse();
		List<QbQuestion> questions = qbQuestionDao.getListByQbId(qbId);
		Collection<Long> questionIds = new ArrayList<Long>();
		for(QbQuestion qbQuestion : questions){
			if(qbQuestion.getQuestionType().equals(Constants.QUESTION_TYPE_NAME_INTERVIEW)){//面试题需要过滤，需要面试题组即可
				continue;
			}
			questionIds.add(qbQuestion.getQuestionId());
		}
		if(questionIds.size() == 0){
			throw new PFServiceException("not found any quesiton for qbId " + qbId );
		}
		
		Map<PaperPartSeqEnum, List<QbQuestion>> partQuestions = qbQuestionToPartProcess
				.getCampusPaperPartQuestions(questionIds);
		Timestamp time = new Timestamp(System.currentTimeMillis());
		paper.setCreateDate(time);
		paper.setModifyDate(time);
		paperDao.saveOrUpdate(paper);
		createPaperSkills(paper);
		PaperSet paperSet = new PaperSet();
		paperSet.setPaper(paper);
		paperPartProcessSchool.processPaperParts(paperSet, partQuestions.get(PaperPartSeqEnum.OBJECT));
		partQuestions.remove(PaperPartSeqEnum.OBJECT);
		//选择题通过paperPartProcessSchool生成  校招也有智力  问答题， 
		List<ConfigCodeName>  configs = configDao.getConfigCode(Constants.CONFIG_PAPER_PART);
		for(ConfigCodeName config : configs){
			int seq = Integer.valueOf(config.getId().getCodeId());
			PaperPartSeqEnum partSeq = PaperPartSeqEnum.valueOf(seq);
			IPaperPartProcess process = paperPartProcessFactory.getPaperPartProcess(partSeq);
			if(process == null || partQuestions.get(partSeq) == null){
				logger.debug("not found process for partSeq {}  or partQuestions.get(partSeq) is {} ", partSeq.getValue(),partQuestions.get(partSeq));
				continue;
			}
			process.processPaperParts(paperSet, partQuestions.get(partSeq));
		}
		
		res.setCode(FuncBaseResponse.SUCCESS);
		res.setPaperId(paper.getPaperId());
		return res;
	}
	@Override
	public List<Paper> getPrebuiltPaper() {
		return paperDao.getPrebuiltPaper();
	}

}
