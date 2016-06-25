package com.ailk.sets.grade.intf;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class LoadWordResponse extends BaseResponse {

	public static class PartInfo implements Serializable {
		private String partName; // 部分名称
		private int questions; // 题数

		public String getPartName() {
			return partName;
		}

		public void setPartName(String partName) {
			this.partName = partName;
		}

		public int getQuestions() {
			return questions;
		}

		public void setQuestions(int questions) {
			this.questions = questions;
		}

		@Override
		public String toString() {
			return "PartInfo [partName=" + partName + ", questions="
					+ questions + "]";
		}
	}

	private int qbId; // 题库id
	private String paperName; // 试卷名称
	private int errors; // 错误数
	private List<PartInfo> partInfos; // 部分信息
	private String filename; // 错误文件名

	public int getQbId() {
		return qbId;
	}

	public void setQbId(int qbId) {
		this.qbId = qbId;
	}

	public String getPaperName() {
		return paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

	public int getErrors() {
		return errors;
	}

	public void setErrors(int errors) {
		this.errors = errors;
	}

	public String getFilename() {
		return filename;
	}

	public List<PartInfo> getPartInfos() {
		return partInfos;
	}

	public void setPartInfos(List<PartInfo> partInfos) {
		this.partInfos = partInfos;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
	public String toString() {
		return "LoadWordResponse [qbId=" + qbId + ", paperName=" + paperName
				+ ", errors=" + errors + ", partInfos=" + partInfos
				+ ", filename=" + filename + "]";
	}

}
