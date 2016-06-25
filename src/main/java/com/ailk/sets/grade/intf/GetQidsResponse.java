package com.ailk.sets.grade.intf;

import java.io.Serializable;
import java.util.List;

/**
 * 获取试卷ID列表应答
 * 
 * @author xugq
 * 
 */
@SuppressWarnings("serial")
public class GetQidsResponse extends BaseResponse {

	public static class QInfo implements Serializable {
		private long qid;
		private boolean answered;

		public long getQid() {
			return qid;
		}

		public void setQid(long qid) {
			this.qid = qid;
		}

		public boolean isAnswered() {
			return answered;
		}

		public void setAnswered(boolean answered) {
			this.answered = answered;
		}
	}

	public List<QInfo> choices; // 选择题列表

	public List<QInfo> getChoices() {
		return choices;
	}

	public void setChoices(List<QInfo> choices) {
		this.choices = choices;
	}

}
