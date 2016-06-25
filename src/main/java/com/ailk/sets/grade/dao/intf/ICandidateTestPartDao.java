package com.ailk.sets.grade.dao.intf;

import java.util.List;

import com.ailk.sets.platform.dao.IBaseDao;
import com.ailk.sets.platform.domain.paper.CandidateTestPart;

public interface ICandidateTestPartDao extends IBaseDao<CandidateTestPart> {

	public List<CandidateTestPart> getList(long testId);
	
	public List<CandidateTestPart> getAllCandidateTestPartByTestId(long testId);
	/**
	 * 获取试卷计时类型
	 * @param testId
	 * @return
	 */
	public int getCandidateTestTimerType(long testId);

}
