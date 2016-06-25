package com.ailk.sets.platform.intf.model.question;

import java.io.Serializable;
import java.util.List;

import com.ailk.sets.platform.intf.model.Question;

/**
 * 附加题模型类
 * 
 * @author 毕希研
 * 
 */
public class Extras implements Question, Serializable {
	private static final long serialVersionUID = -7956523905916674102L;

	private Long questionId; // id，仅当isNew是false时才有正确的值
	private List<String> skillIds; // 通过哪些标签选出
	private String programLang; // 编程语言
	private Integer time; // 答题时长
	private String qDesc; // 问题描述
	private String aDesc; // 参考答案描述
	private String questionType; // 问题类型
	private Boolean brandNew; // 是否是新的附加题
	private Integer point;

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public List<String> getSkillIds() {
		return skillIds;
	}

	public void setSkillIds(List<String> skillIds) {
		this.skillIds = skillIds;
	}

	public String getProgramLang() {
		return programLang;
	}

	public void setProgramLang(String programLang) {
		this.programLang = programLang;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public String getqDesc() {
		return qDesc;
	}

	public void setqDesc(String qDesc) {
		this.qDesc = qDesc;
	}

	public String getaDesc() {
		return aDesc;
	}

	public void setaDesc(String aDesc) {
		this.aDesc = aDesc;
	}

	public Boolean getBrandNew() {
		return brandNew;
	}

	public void setBrandNew(Boolean brandNew) {
		this.brandNew = brandNew;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	@Override
	public Long getId() {
		return questionId;
	}

	@Override
	public List<String> getChoices() {
		return null;
	}

	@Override
	public Integer getRightChoiceNum() {
		return null;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

}
