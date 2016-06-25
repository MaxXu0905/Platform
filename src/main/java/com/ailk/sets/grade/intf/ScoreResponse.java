package com.ailk.sets.grade.intf;


/**
 * 获取打分结果应答
 * 
 * @author xugq
 * 
 */
public class ScoreResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;
	private boolean editable; // 是否允许修改
	private double score; // 得分

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

}
