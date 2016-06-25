package com.ailk.sets.platform.intf.model.question;

import java.io.Serializable;
import java.util.List;

import com.ailk.sets.platform.intf.empl.domain.QbQuestion;
import com.ailk.sets.platform.intf.model.Question;

/**
 * 面试题模型类
 * 
 * @author 毕希研
 * 
 */
public class Interview extends QbQuestion implements Question, Serializable {
	private static final long serialVersionUID = -8062662380134886902L;
	private Boolean brandNew; // 是否是新的面试题

	public Boolean getBrandNew() {
		return brandNew;
	}

	public void setBrandNew(Boolean brandNew) {
		this.brandNew = brandNew;
	}

	@Override
	public Long getId() {
		return super.getQuestionId();
	}

	@Override
	public String getProgramLang() {
		return super.getProgramLanguage();
	}

	@Override
	public Integer getTime() {
		return super.getSuggestTime();
	}

	@Override
	public String getaDesc() {
		return null;
	}

	@Override
	public String getqDesc() {
		return super.getQuestionDesc();
	}

	@Override
	public List<String> getChoices() {
		return null;
	}

	@Override
	public Integer getRightChoiceNum() {
		return null;
	}
}
