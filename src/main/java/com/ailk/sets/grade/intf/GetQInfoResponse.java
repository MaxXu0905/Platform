package com.ailk.sets.grade.intf;

import java.io.Serializable;
import java.util.List;

/**
 * 获取试题详细信息应答
 * 
 * @author xugq
 * 
 */
public class GetQInfoResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;

	public static class MatrixItem implements Serializable {
		private static final long serialVersionUID = 1L;

		private String filename; // 文件名
		private String template; // 模板内容
		private String content; // 用户内容，如果与template一样，则为空
		private String mode; // 模式

		public String getFilename() {
			return filename;
		}

		public void setFilename(String filename) {
			this.filename = filename;
		}

		public String getTemplate() {
			return template;
		}

		public void setTemplate(String template) {
			this.template = template;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getMode() {
			return mode;
		}

		public void setMode(String mode) {
			this.mode = mode;
		}
	}

	public static class Matrix implements Serializable {
		private static final long serialVersionUID = 1L;

		private boolean console; // 是否支持控制台
		private int samples; // 测试样例个数
		private List<MatrixItem> items; // 列表

		public boolean isConsole() {
			return console;
		}

		public void setConsole(boolean console) {
			this.console = console;
		}

		public int getSamples() {
			return samples;
		}

		public void setSamples(int samples) {
			this.samples = samples;
		}

		public List<MatrixItem> getItems() {
			return items;
		}

		public void setItems(List<MatrixItem> items) {
			this.items = items;
		}
	}

	private String qbName; // 题库
	private String mode; // 标题编辑器
	private String title; // 标题
	private int category; // 归类
	private int type; // 类型
	private Matrix matrix; // 代码矩阵（编程题、附加题有值）
	private List<String> options; // 选项（选择题有值）
	private String optAnswers; // 选择题答案
	private String optDesc; // 选择题答案说明
	private int editType; // 编辑类型

	public String getQbName() {
		return qbName;
	}

	public void setQbName(String qbName) {
		this.qbName = qbName;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Matrix getMatrix() {
		return matrix;
	}

	public void setMatrix(Matrix matrix) {
		this.matrix = matrix;
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	public String getOptAnswers() {
		return optAnswers;
	}

	public void setOptAnswers(String optAnswers) {
		this.optAnswers = optAnswers;
	}

	public String getOptDesc() {
		return optDesc;
	}

	public void setOptDesc(String optDesc) {
		this.optDesc = optDesc;
	}

	public int getEditType() {
		return editType;
	}

	public void setEditType(int editType) {
		this.editType = editType;
	}

}
