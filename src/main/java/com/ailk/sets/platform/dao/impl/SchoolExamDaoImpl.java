package com.ailk.sets.platform.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.dao.interfaces.IQbSkillDao;
import com.ailk.sets.platform.dao.interfaces.ISchoolExamDao;
import com.ailk.sets.platform.intf.cand.domain.Invitation;
import com.ailk.sets.platform.intf.cand.domain.SchoolPaperSkillCount;
import com.ailk.sets.platform.intf.domain.Candidate;
import com.ailk.sets.platform.intf.domain.CompanyRecruitActivity;
import com.ailk.sets.platform.util.PaperCreateUtils;

@Repository
public class SchoolExamDaoImpl implements ISchoolExamDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Autowired
	private IQbSkillDao qbSkillDao;

	/*@SuppressWarnings("unchecked")
	public List<SchoolPaperSkillCount> getSchoolPaperSkillCounts(
			Invitation invitation) {
		String sql = "select skill_id, count(*) from  qb_question_skill "
				+ "where  skill_id in (select skill_id from paper_skill )  and question_id in (select question_id from candidate_test_question "
				+ "where test_id =  " + invitation.getTestId()
				+ ") group by skill_id";
		List<SchoolPaperSkillCount> schoolPaperSkillCounts = new ArrayList<SchoolPaperSkillCount>();
		Session session = null;
		session = sessionFactory.getCurrentSession();
		List<Object[]> list = (List<Object[]>) session.createSQLQuery(sql)
				.list();
		for (Object[] o : list) {
			SchoolPaperSkillCount skillCount = new SchoolPaperSkillCount();
			skillCount.setSkillId(o[0].toString());
			skillCount.setSkillName(qbSkillDao.getEntity(
					skillCount.getSkillId()).getSkillName());
			skillCount.setQuestionLength(((BigInteger) o[1]).intValue());
			schoolPaperSkillCounts.add(skillCount);
		}
		return schoolPaperSkillCounts;
	}

	@SuppressWarnings("unchecked")
	public List<SchoolPaperSkillCount> getSchoolPaperSkillCounts(
			List<Long> questionIds, int paperId) {
		String sql = "select skill_id, count(*) from  qb_question_skill  "
				+ "where skill_id in (select skill_id from paper_skill )  and question_id in  "
				+ PaperCreateUtils.getLongInStr(questionIds)
				+ " group by skill_id";
		List<SchoolPaperSkillCount> schoolPaperSkillCounts = new ArrayList<SchoolPaperSkillCount>();
		Session session = null;
		session = sessionFactory.getCurrentSession();
		List<Object[]> list = (List<Object[]>) session.createSQLQuery(sql)
				.list();
		for (Object[] o : list) {
			SchoolPaperSkillCount skillCount = new SchoolPaperSkillCount();
			skillCount.setSkillId(o[0].toString());
			// skillCount.setSkillName(o[1].toString());
			skillCount.setSkillName(qbSkillDao.getEntity(
					skillCount.getSkillId()).getSkillName());
			skillCount.setQuestionLength(((BigInteger) o[1]).intValue());
			schoolPaperSkillCounts.add(skillCount);
		}
		return schoolPaperSkillCounts;
	}
*/
	@SuppressWarnings("unchecked")
	public List<Invitation> getInvitations(CompanyRecruitActivity act,
			Candidate candidate,Integer childPositionId) {
		String sql = "from Invitation where positionId = :positionId "
				+ " and candidateName = :candidateName "
				+ " and candidateEmail = :candidateEmail "
				+ " and passport = :passport ";
		Session session = null;
		session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		if(childPositionId != null){//测评组时宣讲会的测评id是组的，需要取某一个子测评的
			query.setParameter("positionId", childPositionId);
		}else{
			query.setParameter("positionId", act.getPositionId());
		}
		
		query.setParameter("candidateName", candidate.getCandidateName());
		query.setParameter("candidateEmail", candidate.getCandidateEmail());
		query.setParameter("passport", act.getPasscode());
		return query.list();
	}
}
