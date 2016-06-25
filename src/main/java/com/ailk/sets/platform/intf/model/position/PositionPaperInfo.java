package com.ailk.sets.platform.intf.model.position;

import java.io.Serializable;
import java.util.List;

import com.ailk.sets.platform.intf.cand.domain.CandidateInfoExt;
import com.ailk.sets.platform.intf.domain.ActivityRecruitAddress;
import com.ailk.sets.platform.intf.empl.domain.ConfigCodeName;
import com.ailk.sets.platform.intf.empl.domain.PaperQuestionTypeInfo;
import com.ailk.sets.platform.intf.empl.domain.Position;

/**
 * 测评信息 以测评组形式展现的测评信息
 * @author panyl
 *
 */
public class PositionPaperInfo extends PositionStatistics implements Serializable {

	private static final long serialVersionUID = -6390269715143803817L;
	private Position position;
	private Integer employerId;
	private String employerName;
//    private Integer testType; // 评测类型 1社会 2校园
	
	private String companyName; //创建测评人的公司名称，授权测评时需要显示公司名称
	
	private List<PaperCountInfo> paperInfos;//试卷统计信息

	private List<ActivityRecruitAddress> unTestAddresses;//未测试的宣讲会地址
	
 	// add by lipan 2014年10月24日 添加职位意向列表(如果常规信息有的话就显示)
 	private List<ConfigCodeName> positionIntents;
 	
 	private Integer reportCandidateNumber;//报告用户数
 	
 	
    public Integer getReportCandidateNumber() {
		return reportCandidateNumber;
	}

	public void setReportCandidateNumber(Integer reportCandidateNumber) {
		this.reportCandidateNumber = reportCandidateNumber;
	}

	public List<PaperCountInfo> getPaperInfos() {
		return paperInfos;
	}

	public void setPaperInfos(List<PaperCountInfo> paperInfos) {
		this.paperInfos = paperInfos;
	}

	public List<ConfigCodeName> getPositionIntents()
    {
        return positionIntents;
    }

    public void setPositionIntents(List<ConfigCodeName> positionIntents)
    {
        this.positionIntents = positionIntents;
    }

    public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}





	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}


	public Integer getEmployerId() {
		return employerId;
	}

	public void setEmployerId(Integer employerId) {
		this.employerId = employerId;
	}

	public String getEmployerName() {
		return employerName;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}

	public List<ActivityRecruitAddress> getUnTestAddresses() {
		return unTestAddresses;
	}

	public void setUnTestAddresses(List<ActivityRecruitAddress> unTestAddresses) {
		this.unTestAddresses = unTestAddresses;
	}
	
	

}
