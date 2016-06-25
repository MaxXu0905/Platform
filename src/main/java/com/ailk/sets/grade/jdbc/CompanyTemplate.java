package com.ailk.sets.grade.jdbc;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "company_template")
public class CompanyTemplate implements Serializable, Cloneable {

	@EmbeddedId
	private CompanyTemplatePK companyTemplatePK;

	@Column(name = "content", nullable = false)
	private String content;

	public CompanyTemplatePK getCompanyTemplatePK() {
		return companyTemplatePK;
	}

	public void setCompanyTemplatePK(CompanyTemplatePK companyTemplatePK) {
		this.companyTemplatePK = companyTemplatePK;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
