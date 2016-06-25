package com.ailk.sets.platform.intf.model;

import java.io.Serializable;

/**
 * 分页对象
 * 
 * @author 毕希研
 * 
 */
@SuppressWarnings("serial")
public class Page implements Serializable {

	private int pageSize;
	private int requestPage;

	public Page() {
		pageSize = 5;
		requestPage = 1;
	}

	/**
	 * @param pageSize
	 * @param requestPage
	 */
	public Page(int pageSize, int requestPage) {
		this.pageSize = pageSize;
		this.requestPage = requestPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getRequestPage() {
		return requestPage;
	}

	public void setRequestPage(int requestPage) {
		this.requestPage = requestPage;
	}
	
	public int getFirstRow() {
		return (requestPage - 1) * pageSize;
	}
	
	public int getLastRow() {
		return requestPage * pageSize;
	}

	@Override
	public String toString() {
		return "Page [pageSize=" + pageSize + ", requestPage=" + requestPage
				+ "]";
	}

}
