package com.ailk.sets.platform.intf.empl.domain;

public class PositionGroupInfo extends Position {
	
	private static final long serialVersionUID = -3454248714228042278L;
	private PaperRelationInfo paperRelationInfo;

	public PaperRelationInfo getPaperRelationInfo() {
		return paperRelationInfo;
	}

	public void setPaperRelationInfo(PaperRelationInfo paperRelationInfo) {
		this.paperRelationInfo = paperRelationInfo;
	}

	@Override
	public String toString() {
		return "PositionGroupInfo [paperRelationInfo=" + paperRelationInfo + "] " + super.toString();
	}
	
	
}
