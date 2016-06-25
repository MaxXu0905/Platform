package com.ailk.sets.platform.intf.empl.domain;


public class QbQuestionForExtra {
	private String title;// 题目
	private String code;// 参考答案
	private String subjectCode; // 科目编码
	private Integer suggestTime;//答题时间
	private Integer createBy; //创建人id
	private Integer companyId;//公司id

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public Integer getSuggestTime() {
		return suggestTime;
	}
	public void setSuggestTime(Integer suggestTime) {
		this.suggestTime = suggestTime;
	}
	public Integer getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
}
