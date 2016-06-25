package com.ailk.sets.platform.intf.model.qb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class QbProLangInfo implements Serializable {

	private static final long serialVersionUID = 2288119572769824163L;
	private List<String> chartName;
	private List<List<Integer>> numMapping;

	public QbProLangInfo() {
		this.chartName = new ArrayList<String>();
		this.numMapping = new ArrayList<List<Integer>>();
	}

	public void addProgramName(String name) {
		if (StringUtils.isEmpty(name))
			return;
		for (String sname : chartName)
			if (name.equals(sname))
				return;
		chartName.add(name);
		numMapping.add(Arrays.asList(0, 0, 0));
	}

	public void addStatistics(String name, int diff) {
		int index = -1;
		for (int i = 0; i < chartName.size(); i++)
			if (name.equals(chartName.get(i))) {
				index = i;
				break;
			}
		if (index == -1)
			return;
		List<Integer> list = numMapping.get(index);
		if (diff == 2) {
			list.set(0, list.get(0) + 1);
		} else if (diff == 4) {
			list.set(1, list.get(1) + 1);
		} else if (diff == 6) {
			list.set(2, list.get(2) + 1);
		}
	}

	public List<String> getChartName() {
		return chartName;
	}

	public void setChartName(List<String> chartName) {
		this.chartName = chartName;
	}

	public List<List<Integer>> getNumMapping() {
		return numMapping;
	}

	public void setNumMapping(List<List<Integer>> numMapping) {
		this.numMapping = numMapping;
	}

}
