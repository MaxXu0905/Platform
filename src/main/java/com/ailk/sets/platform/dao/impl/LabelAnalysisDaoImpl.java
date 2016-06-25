package com.ailk.sets.platform.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.domain.skilllabel.QbSkillSentenceParse;
import com.ailk.sets.platform.domain.skilllabel.QbSkillSentenceSep;
import com.ailk.sets.platform.intf.domain.skilllabel.QbSkillDegree;
import com.ailk.sets.platform.intf.domain.skilllabel.QbSkillSubjectView;

/**
 * 
 * @author panyl
 * 
 */
@Repository
public class LabelAnalysisDaoImpl {
	private Logger logger = LoggerFactory.getLogger(LabelAnalysisDaoImpl.class);
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	public List<QbSkillSubjectView> getQbSkills(String programLanguage) {
		Session session =null;
			session = sessionFactory.getCurrentSession();
			return session.createSQLQuery("select * from qb_skill_subject_view " +
					"where program_language ='"+programLanguage+"' order by length(skill_name) desc").addEntity(QbSkillSubjectView.class).list();
	}

	public List<QbSkillDegree> getQbSkillDegrees() {
		Session session =null;
			session = sessionFactory.getCurrentSession();
			return session.createSQLQuery("select * from QB_SKILL_DEGREE").addEntity(QbSkillDegree.class).list();
	}
	

	/**
	 * 获取标签语句解析信息
	 * @return
	 */
	public List<QbSkillSentenceParse> getQbSkillSentenceParses(){
		Session session =null;
			session = sessionFactory.getCurrentSession();
			return session.createSQLQuery("select * from QB_SKILL_SENTENCE_PARSE").addEntity(QbSkillSentenceParse.class).list();
	}
	
	/**
	 * 获取可以与语句解析相组合的标点符号信息
	 * @return
	 */
	public List<QbSkillSentenceSep> getQbSkillSentenceSeps(){
		Session session =null;
			session = sessionFactory.getCurrentSession();
			return session.createSQLQuery("select * from QB_SKILL_SENTENCE_SEP").addEntity(QbSkillSentenceSep.class).list();
	}
}
