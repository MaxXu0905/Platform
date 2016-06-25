package com.ailk.sets.platform.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.domain.paper.CandidateTestPart;
import com.ailk.sets.platform.domain.paper.PaperPart;
import com.ailk.sets.platform.exception.PFDaoException;
import com.ailk.sets.platform.intf.domain.paper.Paper;
import com.ailk.sets.platform.intf.empl.domain.PaperInfo;

public interface IPaperDao extends IBaseDao<Paper> {
	public long getReportUnreadCount(int employerId,int positionId);
	
	/**
	 * 更新试卷实例问题视频文件
	 * @param paperInstId
	 * @param questionId
	 * @param url
	 * @throws PFDaoException 
	 */
	public void updatePaperInstanceQuesUrl(long paperInstId, int questionId, String url);
	
	/**
	 * 根据试卷id获取paperpart
	 * @param paperId
	 * @return
	 * @throws PFDaoException
	 */
	public List<PaperPart> getPaperPart(int paperId);
	
	/**
	 * 获取正在答题的部分
	 * @return
	 * @throws PFDaoException
	 */
	public CandidateTestPart getExamingPart(long paperInstId);
	/**
	 * 根据职位类别id获取试卷个数
	 * @param seriesId
	 * @return
	 */
	public int getCountPaperBySeriesId(int seriesId);
	/**
	 * 获取试卷信息
	 * @param seriesId
	 * @param level
	 * @param employerId
	 * @return
	 */
	public List<PaperInfo> getPaperInfo(int seriesId, int level, int employerId, int testType) throws PFDaoException;
	
	public PaperInfo getPaperInfoFromPaper(Paper paper);
	
	/**
	 * 获取预制试卷
	 * @return
	 */
	public List<Paper> getPrebuiltPaper();
	
	/**
     * 获得完成该试卷的大致时间（大致时间是指所有题正确完成时间平均值的总和）
     * @param paperId
     * @return
     */
	public Double getPaperTotalTime(Integer paperId);
	/**
	 * 
	 * @param qbId
	 * @return
	 */
	public Map<String, List<Long>> getPaperQuestionTypeInfoByQbId(int qbId);
} 
