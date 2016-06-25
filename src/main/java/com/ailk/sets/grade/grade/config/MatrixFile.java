package com.ailk.sets.grade.grade.config;

import java.io.Serializable;

/**
 * 配置文件中的文件结构
 * 
 * @author xugq
 * 
 */
public class MatrixFile implements Serializable {

	private static final long serialVersionUID = 1L;

	private String mode;
	private String filename;

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
