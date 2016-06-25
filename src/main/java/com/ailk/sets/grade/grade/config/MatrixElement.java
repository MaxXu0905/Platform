package com.ailk.sets.grade.grade.config;

import java.io.Serializable;
import java.util.List;

/**
 * 配置文件中的文件结构
 * 
 * @author xugq
 * 
 */
@SuppressWarnings("serial")
public class MatrixElement implements Serializable {

	private Boolean console;
	private List<MatrixFile> files;

	public Boolean isConsole() {
		return console;
	}

	public void setConsole(Boolean console) {
		this.console = console;
	}

	public List<MatrixFile> getFiles() {
		return files;
	}

	public void setFiles(List<MatrixFile> files) {
		this.files = files;
	}

}
