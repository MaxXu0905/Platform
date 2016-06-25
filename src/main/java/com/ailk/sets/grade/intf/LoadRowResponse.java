package com.ailk.sets.grade.intf;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class LoadRowResponse extends LoadRow {

	private int sheetType; // 表格类型
	private int errorType; // 错误类型
	private String cause; // 原因
	private Integer suggestTime; // 建议答题时长（秒）
	private String origQbName; // 原题库名
	private Long origQuestionId; // 原题ID
	private LoadRow origRow; // 原题记录

	public int getSheetType() {
		return sheetType;
	}

	public void setSheetType(int sheetType) {
		this.sheetType = sheetType;
	}

	public int getErrorType() {
		return errorType;
	}

	public void setErrorType(int errorType) {
		this.errorType = errorType;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public Integer getSuggestTime() {
		return suggestTime;
	}

	public void setSuggestTime(Integer suggestTime) {
		this.suggestTime = suggestTime;
	}

	public String getOrigQbName() {
		return origQbName;
	}

	public void setOrigQbName(String origQbName) {
		this.origQbName = origQbName;
	}

	public Long getOrigQuestionId() {
		return origQuestionId;
	}

	public void setOrigQuestionId(Long origQuestionId) {
		this.origQuestionId = origQuestionId;
	}

	public LoadRow getOrigRow() {
		return origRow;
	}

	public void setOrigRow(LoadRow origRow) {
		this.origRow = origRow;
	}

	public List<Object> getObjectColumns(int sheetType, String group) {
		List<Object> objs = new ArrayList<Object>();

		switch (sheetType) {
		case LoadConst.SHEET_TYPE_TECH_CHOICE:
			objs.add(skill);
			objs.add(title);
			for (int i = 0; i < 5; i++) {
				if (options != null && i < options.size())
					objs.add(options.get(i));
				else
					objs.add(null);
			}
			objs.add(correctOptions);
			if (suggestSeconds != null)
				objs.add(Double.parseDouble(suggestSeconds));
			else
				objs.add(null);
			objs.add(level);
			objs.add(cause);
			break;
		case LoadConst.SHEET_TYPE_PROGRAM:
			objs.add(title);
			objs.add(refAnswer);
			if (suggestMinutes != null)
				objs.add(Double.parseDouble(suggestMinutes));
			else
				objs.add(null);
			objs.add(mode);
			objs.add(level);
			objs.add(cause);
			break;
		case LoadConst.SHEET_TYPE_TECH_ESSAY:
		case LoadConst.SHEET_TYPE_INTEL_ESSAY:
			objs.add(title);
			objs.add(refAnswer);
			if (suggestMinutes != null)
				objs.add(Double.parseDouble(suggestMinutes));
			else
				objs.add(null);
			objs.add(level);
			objs.add(cause);
			break;
		case LoadConst.SHEET_TYPE_INTEL_CHOICE:
			objs.add(title);
			for (int i = 0; i < 5; i++) {
				if (options != null && i < options.size())
					objs.add(options.get(i));
				else
					objs.add(null);
			}
			objs.add(correctOptions);
			objs.add(explainReqired);
			objs.add(refExplain);
			if (suggestSeconds != null)
				objs.add(Double.parseDouble(suggestSeconds));
			else
				objs.add(null);
			objs.add(level);
			objs.add(cause);
			break;
		case LoadConst.SHEET_TYPE_INTERVIEW:
			objs.add(group);
			objs.add(title);
			if (suggestMinutes != null)
				objs.add(Double.parseDouble(suggestMinutes));
			else
				objs.add(null);
			objs.add(cause);
			break;
		default:
			return null;
		}

		return objs;
	}

}
