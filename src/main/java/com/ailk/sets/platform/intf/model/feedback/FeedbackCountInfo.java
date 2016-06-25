package com.ailk.sets.platform.intf.model.feedback;

import java.util.List;

import com.ailk.sets.platform.intf.model.Mapping;


public class FeedbackCountInfo implements java.io.Serializable {

	private static final long serialVersionUID = 6400044776207444923L;
	private Integer praiseNum;//赞数
    private Integer negNum;//踩数
	private Integer commentNum;//评论内容
    private List<Mapping> feedItems;
	
    public List<Mapping> getFeedItems() {
		return feedItems;
	}

	public void setFeedItems(List<Mapping> feedItems) {
		this.feedItems = feedItems;
	}

	public Integer getPraiseNum() {
		return praiseNum;
	}

	public void setPraiseNum(Integer praiseNum) {
		this.praiseNum = praiseNum;
	}

	public Integer getNegNum() {
		return negNum;
	}

	public void setNegNum(Integer negNum) {
		this.negNum = negNum;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}
	
}