package com.ailk.sets.platform.intf.model;

import java.util.List;

/**
 * 问题接口
 * 
 * @author 毕希研
 * 
 */
public interface Question {
	public Long getId();

	public String getProgramLang();

	public Boolean getBrandNew();

	public Integer getTime();

	public String getaDesc();

	public String getqDesc();

	public String getQuestionType();

	public List<String> getChoices();

	public Integer getRightChoiceNum();

	public Integer getPoint();
}
