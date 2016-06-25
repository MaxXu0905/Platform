package com.ailk.sets.platform.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.domain.CandidateTestMonitor;
import com.ailk.sets.platform.exception.PFDaoException;
import com.ailk.sets.platform.intf.domain.CompanyRecruitActivity;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;
import com.ailk.sets.platform.intf.empl.domain.EmployerOperationLog;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.intf.model.param.GetReportParam;

/**
 * @author 毕希研
 * 
 */
public interface ICandidateTestDao extends IBaseDao<CandidateTest> {

	/**
	 * 获取考试状态
	 * 
	 * @param testId
	 * @param passport
	 * @return
	 */
	public CandidateTest getCandidateTest(long testId, String passport);

	/**
	 * 登录校验考试状态表
	 * 
	 * @param testId
	 * @param passport
	 * @return
	 */
	public CandidateTest loginCandidateTest(long testId, String passport);

	/**
	 * 获取应聘人考试
	 * 
	 * @param testId
	 * @return
	 */
	public CandidateTest getCandidateTest(long testId);

	/**
	 * 获取已通过的数量
	 * 
	 * @param positionId
	 * @return
	 * @throws PFDaoException
	 */
	public long getCountByState(int employerId,int positionId, int state) ;

	/**
	 * 设置推荐结果
	 * 
	 * @param testId
	 * @param choose
	 * @return
	 * @throws PFDaoException
	 */
	public boolean setTestResult(long testId, int choose);

	/**
	 * 保存测试监控
	 * 
	 * @param candidateTestMonitor
	 * @throws PFDaoException
	 */
	public void saveTestMonitor(CandidateTestMonitor candidateTestMonitor);

	/**
	 * 获取某次邀请的视频地址列表
	 * 
	 * @param testId
	 * @param isAbnormal
	 * @return
	 */
	public List<String> getTestMonitor(long testId, int isAbnormal, Page page);

	/**
	 * 更新应聘者照片
	 * 
	 * @param testId
	 * @param url
	 */
	public void updateCandidatePic(long testId, String url);

	/**
	 * 获取试卷实例id
	 * 
	 * @param testId
	 * @return
	 * @throws PFDaoException
	 */
	public int getPaperInstId(long testId);

	/**
	 * 保存一个candidateTest
	 * 
	 * @param candidateTest
	 * @throws PFDaoException
	 */
	public void saveCandidateTest(CandidateTest candidateTest);

	/**
	 * 更新中断次数+1
	 * 
	 * @throws PFDaoException
	 */
	public void updateBreakTimes(long testId);

	/**
	 * 更新切换次数+1
	 * 
	 * @param testId
	 */
	public void updateSwitchTimes(long testId);

	/**
	 * 更新刷新次数+1
	 * 
	 * @param testId
	 */
	public void updateFreshTimes(long testId);

	/**
	 * 标记问题
	 * 
	 * @param testId
	 * @param qId
	 * @return
	 */
	public boolean markQuestion(long testId, int partSeq, long qId);

	/**
	 * 取消标记问题
	 * 
	 * @param testId
	 * @param qId
	 * @return
	 */
	public boolean unMarkQuestion(long testId, int partSeq, long qId);

	/**
	 * 是否标记过问题
	 * 
	 * @param testId
	 * @param partSeq
	 * @param qId
	 * @return
	 */
	public boolean isMarkedTheQuestion(long testId, int partSeq, long qId);

	/**
	 * 获取参加测评活动的数量
	 * 
	 * @param activityId
	 * @return
	 */
	public List<CandidateTest> getByActivityId(
			CompanyRecruitActivity companyRecruitActivity);

	/**
	 * 更新candidateTestlog 包括答题者ip，浏览器，操作系统等等
	 * 
	 * @param testId
	 * @param log
	 */
	public void updateCandidateTestLog(long testId, EmployerOperationLog log);

	/**
	 * 获得宣讲会的考试人数
	 * 
	 * @param positionId
	 * @return
	 */
	public Map<Integer, Long> getTestCount(int positionId, String passport);
	
	/**
	 * 获取具备的列表
	 * @return
	 */
	public List<CandidateTest> getReadyList();
	
	/**
	 * 获取职位相关的已出报告测评
	 * @param positionId
	 * @return
	 */
	public List<CandidateTest> getByPositionId(int positionId);

	/**
	 * 根据条件导出测评报告 - 根据条件获得CandidateTest List
	 * @param param
	 * @return
	 */
	public List<CandidateTest> getCandidateTestList(GetReportParam param);
}
