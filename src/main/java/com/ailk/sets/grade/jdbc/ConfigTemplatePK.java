package com.ailk.sets.grade.jdbc;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class ConfigTemplatePK implements Serializable, Cloneable {

	@Column(name = "test_type", nullable = false)
	private int testType;

	@Column(name = "template_id", nullable = false)
	private String templateId;

	public int getTestType() {
		return testType;
	}

	public void setTestType(int testType) {
		this.testType = testType;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((templateId == null) ? 0 : templateId.hashCode());
		result = prime * result + testType;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConfigTemplatePK other = (ConfigTemplatePK) obj;
		if (templateId == null) {
			if (other.templateId != null)
				return false;
		} else if (!templateId.equals(other.templateId))
			return false;
		if (testType != other.testType)
			return false;
		return true;
	}

}
