package com.ailk.sets.platform.intf.empl.domain;

import com.ailk.sets.grade.intf.GetQInfoResponse;

public class GetQInfoResponseModel extends GetQInfoResponse {
	private static final long serialVersionUID = 5813841226774775816L;
	private Integer suggestTime;
	private String skillName;// 技能名称  选择题前台按技能分类
    private String programLanguage;//编程语言，编程题按语言排序
	
	public String getProgramLanguage() {
		return programLanguage;
	}
	public void setProgramLanguage(String programLanguage) {
		this.programLanguage = programLanguage;
	}
	public String getSkillName() {
		return skillName;
	}
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
	public Integer getSuggestTime() {
		return suggestTime;
	}
	public void setSuggestTime(Integer suggestTime) {
		this.suggestTime = suggestTime;
	}
	
}
