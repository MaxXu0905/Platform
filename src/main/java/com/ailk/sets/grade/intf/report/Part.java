package com.ailk.sets.grade.intf.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("serial")
public class Part implements Serializable {

	private int anchor; // 锚点
	private String partName; // 部分名称
	private List<PartItem> partItems; // 题信息列表

	public Part(int anchor) {
		this.anchor = anchor;
		partName = IReportConfig.ITEM_NAMES[anchor];
		partItems = new ArrayList<PartItem>();
	}

	public int getAnchor() {
		return anchor;
	}

	public void setAnchor(int anchor) {
		this.anchor = anchor;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public List<PartItem> getPartItems() {
		return partItems;
	}

	public void setPartItems(List<PartItem> partItems) {
		this.partItems = partItems;
	}

	public void sort() {
		if (partItems == null || partItems.isEmpty())
			return;

		switch (anchor) {
		case IReportConfig.REPORT_COLUMN_TECH_ESSAY:
		case IReportConfig.REPORT_COLUMN_INTELLIGENCE:
			// 按类型排序
			Collections.sort(partItems, new Comparator<PartItem>() {
				@Override
				public int compare(PartItem o1, PartItem o2) {
					return o1.getQuestionType() - o2.getQuestionType();
				}
			});
			break;
		case IReportConfig.REPORT_COLUMN_PROGRAM:
			// 按语言排序
			Collections.sort(partItems, new Comparator<PartItem>() {
				@Override
				public int compare(PartItem o1, PartItem o2) {
					if (o1.getMode() == null)
						return 1;
					else
						return o1.getMode().compareTo(o2.getMode());
				}
			});
			break;
		case IReportConfig.REPORT_COLUMN_BASIC:
		case IReportConfig.REPORT_COLUMN_QUALITY:
			// 不需排序
			break;
		}
	}

}
