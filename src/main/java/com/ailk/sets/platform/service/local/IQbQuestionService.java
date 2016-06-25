package com.ailk.sets.platform.service.local;

import java.util.List;

import com.ailk.sets.grade.intf.BaseResponse;
import com.ailk.sets.platform.intf.common.PaperPartSeqEnum;
import com.ailk.sets.platform.intf.domain.paper.Paper;
import com.ailk.sets.platform.intf.empl.domain.PaperQuestionToSkills;
import com.ailk.sets.platform.intf.exception.PFServiceException;

public interface IQbQuestionService {
	public void createPaperInstanceByPaper(Paper paper,long testId,String inviteCode) throws PFServiceException;


	public void savePaperQuestions(List<PaperQuestionToSkills> questions, Paper paper, PaperPartSeqEnum seq, int partMinutes);
	
	
	public List<PaperQuestionToSkills> getPaperQuestionToSkillsForPaper(int degree, List<String> skillIds,
			PaperPartSeqEnum paperType);
	
	
	public BaseResponse callCreatePaperService(long inviteId,String inviteCode,List<Long> questionIds) throws PFServiceException;


}
