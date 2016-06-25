package com.ailk.sets.platform.intf.model.param;

import java.io.Serializable;

import com.ailk.sets.platform.intf.model.Page;

public class GetQbQuestionsParam implements Serializable {
	private static final long serialVersionUID = 6589631434330213450L;
	private Integer qbId;
	private int category;
	private Page page;
	private SearchCondition searchCondition;

	public Integer getQbId() {
		return qbId;
	}

	public void setQbId(Integer qbId) {
		this.qbId = qbId;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
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
		return "GetQbQuestionsParam [qbId=" + qbId + ", category=" + category + ", page=" + page + ", searchCondition="
				+ searchCondition + "]";
	}

}
