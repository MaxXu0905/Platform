package com.ailk.sets.platform.intf.model.candidateReport;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
/**
 * 测评组报告  以考生为组织单位
 * @author panyl
 *
 */
public class PositionGroupReport implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3760193296916087967L;
	
	private String candidateName;
	private String candidateEmail;
	private String candidatePhone; //微信hr需要显示号码 拨打电话
	private Integer candidateId;
    //报告信息
	private List<CandReportAndInfo>  reports;
	private Map<Integer,List<CandReportAndInfo>> positionToReports; //测评id与报告的关系   排序方便使用
	
	public String getCandidatePhone() {
		return candidatePhone;
	}
	public void setCandidatePhone(String candidatePhone) {
		this.candidatePhone = candidatePhone;
	}
	public String getCandidateName() {
		return candidateName;
	}
	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}
	public String getCandidateEmail() {
		return candidateEmail;
	}
	public void setCandidateEmail(String candidateEmail) {
		this.candidateEmail = candidateEmail;
	}
	public Integer getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(Integer candidateId) {
		this.candidateId = candidateId;
	}
	public List<CandReportAndInfo> getReports() {
		return reports;
	}
	public void setReports(List<CandReportAndInfo> reports) {
		this.reports = reports;
	}
	
	
}
