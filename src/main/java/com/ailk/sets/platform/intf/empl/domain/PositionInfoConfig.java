package com.ailk.sets.platform.intf.empl.domain;

import java.io.Serializable;
/**
 * 传递的职位常规信息
 * @author panyl
 *
 */
public class PositionInfoConfig implements Serializable {
    
	private static final long serialVersionUID = -6141347602493490287L;
	private String infoId;
    private Integer seq;//seq 不需要前台传
    public String getInfoId() {
		return infoId;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	
	@Override
	public String toString() {
		return "PositionInfoConfig [infoId=" + infoId + ", seq=" + seq + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((infoId == null) ? 0 : infoId.hashCode());
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
		PositionInfoConfig other = (PositionInfoConfig) obj;
		if (infoId == null) {
			if (other.infoId != null)
				return false;
		} else if (!infoId.equals(other.infoId))
			return false;
		return true;
	}
	
	
}
