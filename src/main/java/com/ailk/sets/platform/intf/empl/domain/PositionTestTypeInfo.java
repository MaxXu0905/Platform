package com.ailk.sets.platform.intf.empl.domain;

import java.io.Serializable;
import java.util.List;

import com.ailk.sets.platform.intf.domain.ReportStatusCountInfo;

public class PositionTestTypeInfo implements Serializable{
	private static final long serialVersionUID = -4939499741528488264L;
    private Integer testType;//测评类型  1社招  2校招
    private int positionNum; //测评数量
    private Integer positionId;// 当positionNum为1时，辞职为这个的测评id
//    private List<ReportStatusCountInfo> reportStatusCountInfo;  //报表统计信息

    
	@Override
	public String toString() {
		return "PositionTestTypeInfo [testType=" + testType + ", positionNum=" + positionNum + ", positionId="
				+ positionId + ", reportStatusCountInfo=" + null + "]";
	}

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public Integer getTestType() {
		return testType;
	}

	public void setTestType(Integer testType) {
		this.testType = testType;
	}

	public int getPositionNum() {
		return positionNum;
	}

	public void setPositionNum(int positionNum) {
		this.positionNum = positionNum;
	}

	/*public List<ReportStatusCountInfo> getReportStatusCountInfo() {
		return reportStatusCountInfo;
	}

	public void setReportStatusCountInfo(List<ReportStatusCountInfo> reportStatusCountInfo) {
		this.reportStatusCountInfo = reportStatusCountInfo;
	}*/
	
}
