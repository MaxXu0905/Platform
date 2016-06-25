package com.ailk.sets.platform.intf.model.candidateReport;

import java.io.Serializable;
import java.util.List;

import com.ailk.sets.grade.intf.report.OverallItem;
import com.ailk.sets.platform.intf.model.Mapping;

public class CandReportAndInfo implements Serializable {
	private static final long serialVersionUID = 6987970798428987303L;
	private Integer reportState;
	private Integer candidateId;
	private String candidatePic;
	private Long testId;
	private Integer paperInstId;
	private Double getScore;
	private String summary;

	private String reportDateDesc;
	private String candidateName;
	private String candidateEmail;
	private String candidatePhone;

	private List<Mapping> info;
	private List<OverallItem> items;
	
	private Integer positionId; //测评id  组测评展现时使用
	private String positionName;//测评名称   组测评展现时使用
	
	private Integer ranking;//排名  达内使用
	

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	public String getCandidatePhone() {
		return candidatePhone;
	}

	public void setCandidatePhone(String candidatePhone) {
		this.candidatePhone = candidatePhone;
	}

	public List<OverallItem> getItems() {
		return items;
	}

	public void setItems(List<OverallItem> items) {
		this.items = items;
	}

	public Integer getReportState() {
		return reportState;
	}

	public void setReportState(Integer reportState) {
		this.reportState = reportState;
	}

	public Integer getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Integer candidateId) {
		this.candidateId = candidateId;
	}

	public String getCandidatePic() {
		return candidatePic;
	}

	public void setCandidatePic(String candidatePic) {
		this.candidatePic = candidatePic;
	}

	public Long getTestId() {
		return testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}

	public Integer getPaperInstId() {
		return paperInstId;
	}

	public void setPaperInstId(Integer paperInstId) {
		this.paperInstId = paperInstId;
	}

	public Double getGetScore() {
		return getScore;
	}

	public void setGetScore(Double getScore) {
		this.getScore = getScore;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getReportDateDesc() {
		return reportDateDesc;
	}

	public void setReportDateDesc(String reportDateDesc) {
		this.reportDateDesc = reportDateDesc;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public List<Mapping> getInfo() {
		return info;
	}

	public void setInfo(List<Mapping> info) {
		this.info = info;
	}

	public String getCandidateEmail() {
		return candidateEmail;
	}

	public void setCandidateEmail(String candidateEmail) {
		this.candidateEmail = candidateEmail;
	}

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

}
