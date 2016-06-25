package com.ailk.sets.platform.service.paperpart.process;

import java.util.List;

import com.ailk.sets.platform.intf.common.PaperPartSeqEnum;
import com.ailk.sets.platform.intf.domain.paper.Paper;
import com.ailk.sets.platform.intf.empl.domain.PaperQuestionToSkills;
import com.ailk.sets.platform.intf.empl.domain.PaperSet;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;

/**
 * 试卷部分处理
 * @author panyl
 *
 */
public interface IPaperPartProcess {
	/**
	 * @param paperSet
	 * @param selfQbQuestions  自定义题目集合
	 * @return
	 */
	public void processPaperParts(PaperSet paperSet,List<QbQuestion> selfQbQuestions);
	
	public void processTestPaperPart(List<PaperQuestionToSkills> questions, Paper paper, PaperPartSeqEnum partSeq);
	/**
	 * 获取自定义题目时长
	 * @param selfQbQuestions
	 * @return
	 */
	public int getSelfQuestionsTime(List<QbQuestion> selfQbQuestions);
	/**
	 * 获取自定义题目
	 * @param selfQbQuestions
	 * @return
	 */
	public List<PaperQuestionToSkills> getSelfPaperQuestions(List<QbQuestion> selfQbQuestions);
	
	public boolean needCreateTestQuestion(Paper paper);
}
