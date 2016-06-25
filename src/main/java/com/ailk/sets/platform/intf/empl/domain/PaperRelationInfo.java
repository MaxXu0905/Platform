package com.ailk.sets.platform.intf.empl.domain;

import java.io.Serializable;
import java.util.List;

public class PaperRelationInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6281366895574630197L;
	private List<Integer> choosePaperIds;// 选答试卷id
	private List<Integer> mustPaperIds;// 必答试卷id
	private Integer chooseNum;// 选择数 必须小于choosePaperIds个数 暂时没用用

	public List<Integer> getChoosePaperIds() {
		return choosePaperIds;
	}

	public void setChoosePaperIds(List<Integer> choosePaperIds) {
		this.choosePaperIds = choosePaperIds;
	}

	public List<Integer> getMustPaperIds() {
		return mustPaperIds;
	}

	public void setMustPaperIds(List<Integer> mustPaperIds) {
		this.mustPaperIds = mustPaperIds;
	}

	public Integer getChooseNum() {
		return chooseNum;
	}

	public void setChooseNum(Integer chooseNum) {
		this.chooseNum = chooseNum;
	}

	@Override
	public String toString() {
		return "PaperRelationInfo [choosePaperIds=" + choosePaperIds + ", mustPaperIds=" + mustPaperIds
				+ ", chooseNum=" + chooseNum + "]";
	}
	
	

}
