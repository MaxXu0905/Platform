package com.ailk.sets.platform.intf.model.wx.util;

import org.dom4j.Document;

public class DocumentWraper {
	private Document document;

	public DocumentWraper(Document document) {
		this.document = document;
	}

	public String getTextUnderRoot(String elementName) {
		return document.getRootElement().elementText(elementName);
	}
}
