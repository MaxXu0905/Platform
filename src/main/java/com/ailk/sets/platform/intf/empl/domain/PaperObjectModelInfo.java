package com.ailk.sets.platform.intf.empl.domain;

import java.io.Serializable;
import java.util.List;

import com.ailk.sets.platform.intf.model.qb.QbSkill;

public class PaperObjectModelInfo extends PaperModelPart implements Serializable {
	private static final long serialVersionUID = 4216115459449095594L;
	private List<QbSkill> skills;//选择题技能名称
	private List<List<Integer>> difficulties;//选择题题量分布
	public List<QbSkill> getSkills() {
		return skills;
	}
	public void setSkills(List<QbSkill> skills) {
		this.skills = skills;
	}
	public List<List<Integer>> getDifficulties() {
		return difficulties;
	}
	public void setDifficulties(List<List<Integer>> difficulties) {
		this.difficulties = difficulties;
	}
	
}
