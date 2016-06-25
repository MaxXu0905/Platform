package com.ailk.sets.platform.intf.empl.service;

import java.util.List;

import com.ailk.sets.platform.intf.domain.DegreeToSkills;
import com.ailk.sets.platform.intf.domain.paper.Paper;
import com.ailk.sets.platform.intf.empl.domain.PaperInfo;
import com.ailk.sets.platform.intf.empl.domain.PaperQuestionTypeInfo;
import com.ailk.sets.platform.intf.empl.domain.PaperSet;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.PaperInitInfo;
import com.ailk.sets.platform.intf.model.PaperResponse;
import com.ailk.sets.platform.intf.model.qb.QbBaseModelInfo;
/**
 * 试卷接口
 * @author panyl
 *
 */
public interface IPaper {
	/**
	 * 获取试卷接口
	 * @param seriesId
	 * @param level
	 * @param employerId
	 * @return
	 */
	public List<PaperInfo> getPaperInfo(int seriesId, int level, int employerId, int testType) throws Exception;
	
	/**
	 * 获取某用户可以看到的题库列表,生成试卷时使用
	 * @param employerId
	 * @return
	 */
	public List<QbBaseModelInfo> getQbBases(int employerId, int seriesId,int levelId) throws PFServiceException;
	
	
	/**
	 * 获取进入试卷的显示的初始化信息
	 * @param employerId
	 * @return
	 */
	public  PaperInitInfo getPaperInitInfo(int employerId, int seriesId,int level) throws PFServiceException;
	
	/**
	 * 
	 * @param paperSet
	 * @return
	 * @throws PFServiceException
	 */
	public PaperResponse createPaper(PaperSet paperSet) throws PFServiceException;
	
	/**
	 * 
	 * @param paperSet
	 * @return
	 * @throws PFServiceException
	 */
	public PaperResponse createCampusPaper(PaperSet paperSet) throws PFServiceException;
	
	
	
	/**
	 * 根据题库id创建试卷
	 * @param qbId
	 * @param paperSet
	 * @return
	 * @throws PFServiceException
	 */
	public PaperResponse createPaperByQbId(int qbId, Paper paper) throws PFServiceException;
	
	
	/**
	 * 根据题库id创建校招试卷
	 * @param qbId
	 * @param paperSet
	 * @return
	 * @throws PFServiceException
	 */
	public PaperResponse createCampusPaperByQbId(int qbId, Paper paper) throws PFServiceException;
	
	/**
	 * @param paper
	 * @return
	 * @throws PFServiceException
	 */
	public List<DegreeToSkills> analysisSkills(Paper paper) throws PFServiceException;
	
	/**
	 * 获取职位试卷统计信息
	 * @param positionId
	 * @return
	 * @throws PFServiceException
	 */
	public 	List<PaperQuestionTypeInfo> getPaperQuestionTypeInfo(int positionId)  throws PFServiceException;
	
	/**
	 * 获取预制试卷 
	 * @return
	 */
	public List<Paper> getPrebuiltPaper();
}
