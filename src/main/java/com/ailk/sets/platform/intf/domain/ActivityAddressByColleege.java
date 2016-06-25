/**
 * author :  lipan
 * filename :  ActivityRecruitAddress.java
 * create_time : 2014年7月1日 下午8:04:54
 */
package com.ailk.sets.platform.intf.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author panyl
 *
 */
public class ActivityAddressByColleege extends ActivityRecruitAddress implements Serializable
{
	    
	    private List<String> addresses;//可以添加多个详细地址
	    private int supportNumber;
	    
    /**
	 * 
	 */
	private static final long serialVersionUID = -1914404566744750943L;

	
	
	public int getSupportNumber() {
		return supportNumber;
	}


	public void setSupportNumber(int supportNumber) {
		this.supportNumber = supportNumber;
	}


	/**
     * 
     */
    public ActivityAddressByColleege()
    {
    	addresses = new ArrayList<String>();
    }


	public List<String> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<String> addresses) {
		this.addresses = addresses;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.getCity() == null) ? 0 : this.getCity().hashCode());
		result = prime * result + ((this.getCollege() == null) ? 0 : this.getCollege().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActivityAddressByColleege other = (ActivityAddressByColleege) obj;
		if (this.getCity() == null) {
			if (other.getCity() != null)
				return false;
		} else if (!this.getCity().equals(other.getCity()))
			return false;
		if (this.getCollege() == null) {
			if (other.getCollege() != null)
				return false;
		} else if (!this.getCollege().equals(other.getCollege()))
			return false;
		return true;
	}

	
}
