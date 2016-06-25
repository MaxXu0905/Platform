package com.ailk.sets.grade.jdbc;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "config_template")
public class ConfigTemplate implements Serializable, Cloneable {

	@EmbeddedId
	private ConfigTemplatePK configTemplatePK;

	@Column(name = "template_name", nullable = false)
	private String templateName;

	@Column(name = "content", nullable = false)
	private String content;

	public ConfigTemplatePK getConfigTemplatePK() {
		return configTemplatePK;
	}

	public void setConfigTemplatePK(ConfigTemplatePK configTemplatePK) {
		this.configTemplatePK = configTemplatePK;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
