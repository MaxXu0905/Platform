package com.ailk.sets.grade.intf;

import java.io.Serializable;

/**
 * 编程题、附加题提交文件项
 * 
 * @author xugq
 * 
 */
public class CommitFile implements Serializable {

	private static final long serialVersionUID = 1L;

	private String filename;
	private String content;
	

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


}
