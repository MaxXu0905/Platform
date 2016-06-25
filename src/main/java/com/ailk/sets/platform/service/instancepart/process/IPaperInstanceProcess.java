package com.ailk.sets.platform.service.instancepart.process;

import java.util.List;

import com.ailk.sets.platform.domain.paper.PaperPart;
import com.ailk.sets.platform.domain.paper.PaperQuestion;
import com.ailk.sets.platform.intf.domain.paper.Paper;
import com.ailk.sets.platform.intf.empl.domain.PaperQuestionToSkills;

/**
 * 试卷实例部分出题处理器
 * 
 * @author panyl
 * 
 */
public interface IPaperInstanceProcess {
	/**
	 * 获取试卷模板题目
	 * @param paperPart
	 * @return
	 */
	public List<PaperQuestion> getPaperQuestions(PaperPart paperPart);
	/**
	 * 
	 * @param paperPart
	 */
	public void processPaperInstancePart(long testId,Paper paper,PaperPart paperPart,List<Long> paperQuestionIds);
	
	/**
	 * 获取考试题目
	 * @param paperQuestion
	 * @param paperPart
	 * @param paper
	 * @param hasFoundQuestions
	 * @return
	 */
	public PaperQuestionToSkills createQuestionFromQuestion(Paper paper,PaperQuestion paperQuestion, PaperPart paperPart,List<PaperQuestionToSkills> hasFoundQuestions);
	
	/**
	 * 获取题目自己
	 * @param paperQuestion
	 * @param paperPart
	 * @param paper
	 * @param hasFoundQuestions
	 * @return
	 */
	public PaperQuestionToSkills createQuestionFromSelf(Paper paper,PaperQuestion paperQuestion, PaperPart paperPart,List<PaperQuestionToSkills> hasFoundQuestions);
}
