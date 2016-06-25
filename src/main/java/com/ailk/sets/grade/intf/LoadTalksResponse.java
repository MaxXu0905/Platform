package com.ailk.sets.grade.intf;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class LoadTalksResponse extends BaseResponse {

	private boolean success;
	private List<TalkResult> results;
	
	public LoadTalksResponse() {
		success = true;
		results = new ArrayList<TalkResult>();
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<TalkResult> getResults() {
		return results;
	}

	public void setResults(List<TalkResult> results) {
		this.results = results;
	}

}
