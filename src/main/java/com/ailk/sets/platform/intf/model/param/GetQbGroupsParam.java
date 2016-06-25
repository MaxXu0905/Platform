package com.ailk.sets.platform.intf.model.param;

import java.io.Serializable;

import com.ailk.sets.platform.intf.model.Page;

@SuppressWarnings("serial")
public class GetQbGroupsParam implements Serializable {

	private Integer qbId;
	private Page page;
	private SearchCondition searchCondition;

	public Integer getQbId() {
		return qbId;
	}

	public void setQbId(Integer qbId) {
		this.qbId = qbId;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public SearchCondition getSearchCondition() {
		return searchCondition;
	}

	public void setSearchCondition(SearchCondition searchCondition) {
		this.searchCondition = searchCondition;
	}

	@Override
	public String toString() {
		return "GetQbGroupsParam [qbId=" + qbId + ", page=" + page + ", searchCondition=" + searchCondition + "]";
	}

}
