package com.ailk.sets.platform.service.local;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ailk.sets.platform.intf.common.PaperPartSeqEnum;
import com.ailk.sets.platform.intf.empl.domain.QbQuestion;

/**
 * 题目到试卷部分映射关系处理
 * @author panyl
 *
 */
public interface IQbQuestionToPartProcess {
	/**
	 * 获取试卷分类信息
	 * @param questionIds
	 * @return
	 */
	public Map<PaperPartSeqEnum, List<QbQuestion>> getPaperPartQuestions(Collection<Long> questionIds);
	/**
	 * 获取校招试卷题目分类信息
	 * @param questionIds
	 * @return
	 */
	public Map<PaperPartSeqEnum, List<QbQuestion>> getCampusPaperPartQuestions(Collection<Long> questionIds);
}
