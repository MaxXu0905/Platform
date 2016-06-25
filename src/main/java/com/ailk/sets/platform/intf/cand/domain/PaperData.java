package com.ailk.sets.platform.intf.cand.domain;

import java.io.Serializable;
import java.util.List;

public class PaperData implements Serializable
{
    private static final long serialVersionUID = 2499175227368653064L;
    private int totalTime; // 大约时间
    private Integer timerType;//试卷计时类型
    private List<PaperPartData> partDatas;

    public List<PaperPartData> getPartDatas()
    {
        return partDatas;
    }

    public void setPartDatas(List<PaperPartData> partDatas)
    {
        this.partDatas = partDatas;
    }

    public int getTotalTime()
    {
        return totalTime;
    }

    public void setTotalTime(int totalTime)
    {
        this.totalTime = totalTime;
    }

	public Integer getTimerType() {
		return timerType;
	}

	public void setTimerType(Integer timerType) {
		this.timerType = timerType;
	}

}
