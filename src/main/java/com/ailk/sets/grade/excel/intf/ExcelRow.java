package com.ailk.sets.grade.excel.intf;

import java.util.List;

import com.ailk.sets.grade.excel.ExcelColumn;
import com.ailk.sets.grade.excel.ExcelConf;
import com.ailk.sets.grade.intf.LoadRow;

public class ExcelRow {

	private int sheetType; // 表格类型
	private int rowNum; // 行号
	private int errorType; // 错误类型
	private String cause; // 原因
	private int serialNo; // 错误序列号
	private Integer suggsetTime; // 建议答题时长
	private String origQbName; // 原题库名
	private Long origQuestionId; // 原题ID
	private LoadRow origRow; // 原题记录
	private List<ExcelColumn> columns; // 列数组
	private ExcelConf conf; // excel的一行

	public int getSheetType() {
		return sheetType;
	}

	public void setSheetType(int sheetType) {
		this.sheetType = sheetType;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
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

	public int getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	public Integer getSuggsetTime() {
		return suggsetTime;
	}

	public void setSuggsetTime(Integer suggsetTime) {
		this.suggsetTime = suggsetTime;
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

	public List<ExcelColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<ExcelColumn> columns) {
		this.columns = columns;
	}

	public ExcelConf getConf() {
		return conf;
	}

	public void setConf(ExcelConf conf) {
		this.conf = conf;
	}

}
