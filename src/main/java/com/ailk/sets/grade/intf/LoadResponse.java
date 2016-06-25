package com.ailk.sets.grade.intf;

import java.util.ArrayList;
import java.util.List;

import com.ailk.sets.platform.intf.empl.domain.PaperQuestionTypeInfo;

@SuppressWarnings("serial")
public class LoadResponse extends BaseResponse {

	private Integer qbId; // 题库ID
	private int similarityErrors; // 相似度
	private int timeErrors; // 时间调整
	private int formatErrors; // 格式错误
	private List<LoadGroup> groups; // 分组

	private List<PaperQuestionTypeInfo> typeInfos;//统计信息
	
	public List<PaperQuestionTypeInfo> getTypeInfos() {
		return typeInfos;
	}

	public void setTypeInfos(List<PaperQuestionTypeInfo> typeInfos) {
		this.typeInfos = typeInfos;
	}

	public LoadResponse() {
		similarityErrors = 0;
		timeErrors = 0;
		formatErrors = 0;
		groups = new ArrayList<LoadGroup>();
	}

	public Integer getQbId() {
		return qbId;
	}

	public void setQbId(Integer qbId) {
		this.qbId = qbId;
	}

	public int getSimilarityErrors() {
		return similarityErrors;
	}

	public void setSimilarityErrors(int similarityErrors) {
		this.similarityErrors = similarityErrors;
	}

	public int getTimeErrors() {
		return timeErrors;
	}

	public void setTimeErrors(int timeErrors) {
		this.timeErrors = timeErrors;
	}

	public int getFormatErrors() {
		return formatErrors;
	}

	public void setFormatErrors(int formatErrors) {
		this.formatErrors = formatErrors;
	}

	public List<LoadGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<LoadGroup> groups) {
		this.groups = groups;
	}

}
