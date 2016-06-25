package com.ailk.sets.grade.intf;



@SuppressWarnings("serial")
public class GetGroupResponse extends BaseResponse {

	private LoadGroup group;


	public LoadGroup getGroup() {
		return group;
	}

	public void setGroup(LoadGroup group) {
		this.group = group;
	}

}
