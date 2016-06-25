package com.ailk.sets.platform.intf.model.position;

import java.io.Serializable;
import java.util.List;

import com.ailk.sets.platform.intf.cand.domain.CandidateInfoExt;
import com.ailk.sets.platform.intf.empl.domain.ConfigCodeName;
import com.ailk.sets.platform.intf.empl.domain.PaperQuestionTypeInfo;
import com.ailk.sets.platform.intf.empl.domain.Position;

/**
 * 职位信息
 * 
 * @author 毕希研
 * 
 */
public class PositionInfo extends PositionStatistics implements Serializable {

	private static final long serialVersionUID = -6390269715143803817L;
	private Position position;
	private Boolean brandNew;// 是否是新创建职位
	private Integer employerId;
	private String employerName;
    private Integer testType; // 评测类型 1社会 2校园
	private List<PosMessage> posMessage;
	
	private String companyName; //创建测评人的公司名称，授权测评时需要显示公司名称
	
	//sample测评列表会加载paperQuestionTypes
 	private List<PaperQuestionTypeInfo> paperQuestionTypes; //试卷题目信息

 	// add by lipan 2014年10月24日 添加职位意向列表(如果常规信息有的话就显示)
 	private List<ConfigCodeName> positionIntents;
 	

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

	public List<PaperQuestionTypeInfo> getPaperQuestionTypes() {
		return paperQuestionTypes;
	}

	public void setPaperQuestionTypes(List<PaperQuestionTypeInfo> paperQuestionTypes) {
		this.paperQuestionTypes = paperQuestionTypes;
	}


	public Integer getTestType() {
		return testType;
	}

	public void setTestType(Integer testType) {
		this.testType = testType;
	}

	public List<PosMessage> getPosMessage() {
		return posMessage;
	}

	public void setPosMessage(List<PosMessage> posMessage) {
		this.posMessage = posMessage;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Boolean getBrandNew() {
		return brandNew;
	}

	public void setBrandNew(Boolean brandNew) {
		this.brandNew = brandNew;
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

}
