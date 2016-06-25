package com.ailk.sets.platform.intf.empl.domain;

import java.io.Serializable;
import java.util.Collection;

import com.ailk.sets.platform.intf.domain.paper.Paper;

/**
 * 职位创建接口信息
 * @author panyl
 *
 */
public class PaperSet implements Serializable {
	private static final long serialVersionUID = -6390269715143803817L;
	private Paper paper; // 试卷相关信息
	private Collection<Long> questionIds; // 自定义题目id
	
	

	public Collection<Long> getQuestionIds() {
		return questionIds;
	}

	public void setQuestionIds(Collection<Long> questionIds) {
		this.questionIds = questionIds;
	}

	public Paper getPaper() {
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	@Override
	public String toString() {
		return "PaperSet [paper=" + paper + ", questionIds=" + questionIds + "]";
	}

}
