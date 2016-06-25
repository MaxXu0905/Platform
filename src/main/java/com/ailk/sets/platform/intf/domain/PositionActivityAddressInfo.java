package com.ailk.sets.platform.intf.domain;

import java.io.Serializable;
import java.util.List;

public class PositionActivityAddressInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ActivityRecruitAddress>  unTestAddresses;
	private List<ActivityRecruitAddress>  testedAddresses;
	
	public List<ActivityRecruitAddress> getUnTestAddresses() {
		return unTestAddresses;
	}
	public void setUnTestAddresses(List<ActivityRecruitAddress> unTestAddresses) {
		this.unTestAddresses = unTestAddresses;
	}
	public List<ActivityRecruitAddress> getTestedAddresses() {
		return testedAddresses;
	}
	public void setTestedAddresses(List<ActivityRecruitAddress> testedAddresses) {
		this.testedAddresses = testedAddresses;
	}
	
}
