package com.ailk.sets.platform.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.ICompanyRecruitActivityDao;
import com.ailk.sets.platform.dao.interfaces.IEmployerAuthDao;
import com.ailk.sets.platform.dao.interfaces.IInvitationDao;
import com.ailk.sets.platform.dao.interfaces.IPositionDao;
import com.ailk.sets.platform.dao.interfaces.IPositionRelationDao;
import com.ailk.sets.platform.dao.interfaces.IQbSkillDao;
import com.ailk.sets.platform.dao.interfaces.ISchoolReportDao;
import com.ailk.sets.platform.domain.PositionRelation;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.domain.CompanyRecruitActivity;
import com.ailk.sets.platform.intf.domain.ReportStatusCountInfo;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.intf.model.invatition.InvitationState;
import com.ailk.sets.platform.intf.school.domain.CandidateTestScore;
import com.ailk.sets.platform.intf.school.domain.ReportSkillsScoreInfo;
import com.ailk.sets.platform.intf.school.domain.SchoolCandidateReport;
import com.alibaba.dubbo.common.utils.StringUtils;

@Repository
public class SchoolReportDaoImpl extends BaseDaoImpl<SchoolCandidateReport>
		implements ISchoolReportDao {

	@Autowired
	private IQbSkillDao qbSkillDao;
	
	@Autowired
	private ICompanyRecruitActivityDao companyRecruitActivityDao;
	
	@Autowired
	private IInvitationDao invitationDaoImpl;
	@Autowired
	private IEmployerAuthDao employerAuthDaoImpl;

	@Autowired
	private IPositionDao positionDao;
	@Autowired
	private IPositionRelationDao positionRelationDao;
	
	@Autowired
	private ICompanyRecruitActivityDao activityDao;
	private Logger logger = LoggerFactory.getLogger(SchoolReportDaoImpl.class);

	@Override
	@SuppressWarnings({ "unchecked", "serial" })
	public List<SchoolCandidateReport> getSchoolReportList(Integer employerId, Integer positionId,Integer activityId, Page page, String searchTxt,
			Integer testResult, String intentPos) {
		if(activityId == null && positionId == null){
			throw new RuntimeException("not right paramter activityId and positionId both null");
		}
		Session session = null;
		session = sessionFactory.getCurrentSession();
		StringBuilder candTable = new StringBuilder(
				"select * from (select b.candidate_id,b.candidate_name,b.candidate_email," +
				"max(case a.info_id when 'PHONE' then a.value else 0 end) as PHONE," +
				"sum(case a.info_id when 'INTENTION_POSITION' then a.value else 0 end) as INTENTION_POSITION " +
				"from candidate_info_ext a right join candidate b on a.candidate_id = b.candidate_id group by b.candidate_id ) c where 1=1");
		if (StringUtils.isNotEmpty(searchTxt)) {
			candTable
					.append(" and ((c.candidate_name  like :searchTxt or c.candidate_email like :searchTxt or c.PHONE  like :searchTxt) "
							+ "or ((c.candidate_name  like :searchTxt or c.candidate_email like :searchTxt) and c.PHONE is null)) ");
		}
		if (StringUtils.isNotEmpty(intentPos)) {
			candTable.append(" and c.INTENTION_POSITION= '" + intentPos + "' ");
		}

		String sql = "select report.test_id,report.get_score, d.candidate_id, d.candidate_name, d.candidate_email, d.PHONE "
				+ " from candidate_report report,candidate_test test, ("
				+ candTable.toString()
				+ ") d "
				+ "  where report.candidate_id = d.candidate_id "
				+ "  and test.test_id = report.test_id  and report.test_id in ";
		
		String testIdIn = "";
		if(activityId != null){//按活动过滤
			testIdIn = "( select test_id  from invitation  t3 , company_recruit_activity t4  "
					+ "where  t3.position_id = t4.position_id  and t3.passport = t4.passcode and t4.activity_id = "
					+ activityId + ")";
		}else if(positionId != null){//按测评过滤
			testIdIn=	"( select test_id  from invitation  t3  where  t3.position_id  = "+ positionId ;
			testIdIn+= invitationDaoImpl.getInvitationFilterSqlByEmployer(positionId, employerId);
			testIdIn+= ")";
		}
		sql += testIdIn;
		if (testResult != null) {
			sql += " and test.test_result = " + testResult;
		}

		sql += " order by report.get_score desc";
		logger.debug("the query report sql is {} ", sql);
		Query query = session.createSQLQuery(sql);
		if (StringUtils.isNotEmpty(searchTxt)) {
			query.setParameter("searchTxt", "%" + searchTxt + "%");
		}
		query.setFirstResult((page.getRequestPage() - 1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		query.setResultTransformer(new ResultTransformer() {
			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				SchoolCandidateReport report = new SchoolCandidateReport();
				report.setTestId(((BigInteger) tuple[0]).longValue());
				report.setGetScore(((BigDecimal) tuple[1]).doubleValue());
				report.setCandidateName((String) tuple[3]);
				report.setCandidateEmail((String) tuple[4]);
				if (tuple[5] != null)
					report.setCandidatePhone(new BigDecimal(tuple[5] + "")
							.toString());
				return report;
			}

			@SuppressWarnings("rawtypes")
			@Override
			public List transformList(List collection) {
				return collection;
			}
		});
		List<SchoolCandidateReport> list = query.list();
		return list;
	}

	/**
	 * 获取测评各个科目分数
	 * 
	 * @param testId
	 * @return
	 */
	@SuppressWarnings({ "serial", "unchecked" })
	public List<ReportSkillsScoreInfo> getReportSkillsScoreInfo(long testId) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		Query q = session
				.createQuery("from CandidateTestScore where id.testId = "
						+ testId);
		q.setResultTransformer(new ResultTransformer() {

			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				CandidateTestScore score = (CandidateTestScore) tuple[0];
				ReportSkillsScoreInfo scoreInfo = new ReportSkillsScoreInfo();
				scoreInfo.setScore(score.getScore());
				String skillId = score.getId().getSkillId();
				scoreInfo.setSkillId(skillId);
				scoreInfo.setSkillName(qbSkillDao.getEntity(skillId)
						.getSkillName());
				return scoreInfo;
			}

			@SuppressWarnings("rawtypes")
			@Override
			public List transformList(List collection) {
				return collection;
			}
		});

		List<ReportSkillsScoreInfo> list = q.list();
		return list;
	}

	/**
	 * 获取报告总数
	 * 
	 * @return
	 */
	public long getCountReportList(Integer employerId, Integer positionId, Integer activityId, Page page, String searchTxt,
			Integer testResult, String intentPos) {
		if(activityId == null && positionId == null){
			throw new RuntimeException("getCountReportList not right paramter activityId and positionId both null");
		}
		Session session = null;
		session = sessionFactory.getCurrentSession();
		StringBuilder candTable = new StringBuilder(
				"select * from (select b.candidate_id,b.candidate_name,b.candidate_email," +
				"sum(case a.info_id when 'PHONE' then a.value else 0 end) as PHONE," +
				"sum(case a.info_id when 'INTENTION_POSITION' then a.value else 0 end) as INTENTION_POSITION " +
				"from candidate_info_ext a right join candidate b on a.candidate_id = b.candidate_id group by b.candidate_id ) c where 1=1");
		if (StringUtils.isNotEmpty(searchTxt)) {
			candTable
					.append(" and ((c.candidate_name  like :searchTxt or c.candidate_email like :searchTxt or c.PHONE  like :searchTxt) "
							+ "or ((c.candidate_name  like :searchTxt or c.candidate_email like :searchTxt) and c.PHONE is null)) ");
		}
		if (StringUtils.isNotEmpty(intentPos)) {
			candTable.append(" and c.INTENTION_POSITION= '" + intentPos + "' ");
		}

		String sql = "select count(*)  "
				+ " from candidate_report report,candidate_test test, ("
				+ candTable.toString()
				+ ") d "
				+ "  where report.candidate_id = d.candidate_id "
				+ "  and test.test_id = report.test_id  and report.test_id in ";
		
		String testIdIn = "";
		if(activityId != null){//按活动过滤
			testIdIn = "( select test_id  from invitation  t3 , company_recruit_activity t4  "
					+ "where  t3.position_id = t4.position_id  and t3.passport = t4.passcode and t4.activity_id = "
					+ activityId + ")";
		}else if(positionId != null){//按测评过滤
			testIdIn=	"( select test_id  from invitation  t3  where  t3.position_id  = "+ positionId ;
			testIdIn+= invitationDaoImpl.getInvitationFilterSqlByEmployer(positionId, employerId);
			testIdIn+= ")";
		}
		sql += testIdIn;
		if (testResult != null) {
			sql += " and test.test_result = " + testResult;
		}


		logger.debug("the query report sql is {} ", sql);
		Query query = session.createSQLQuery(sql);
		if (StringUtils.isNotEmpty(searchTxt)) {
			query.setParameter("searchTxt", "%" + searchTxt + "%");
		}
		long size = ((BigInteger) query.uniqueResult()).longValue();
		logger.debug("the size is {} for activityId {} ", size, activityId);
		return size;
	}

	/**
	 * 获取报告分数
	 * 
	 * @param testId
	 * @return
	 */
	public Double getGetScore(long testId) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		String sql = "select get_score  from candidate_report where test_id = :test_id";
		Query query = session.createSQLQuery(sql);
		query.setParameter("test_id", testId);
		BigDecimal p = ((BigDecimal) query.uniqueResult());
		if (p == null) {
			return null;
		}
		return p.doubleValue();
	}
	 /**
	  * 
	  * @param employerId
	  * @param posOrActId
	  * @param type  1测评id  2活动id
	  * @return
	  */
	@Override
	@SuppressWarnings({ "serial", "unchecked" })
	public List<ReportStatusCountInfo> getSchoolReportStatusCount(int employerId,int posOrActId, int type) {
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		if(type == 2){
			CompanyRecruitActivity act = companyRecruitActivityDao.getEntity(posOrActId);
			String sql = "select test_result , count(*) from  candidate_test "
					+ "where position_id = :positionId "
					+ " and test_state in (:testStates) and passport = :passport group by test_result";
		    query = session.createSQLQuery(sql);
			query.setInteger("positionId", act.getPositionId());
			query.setString("passport", act.getPasscode());
		} else if(type == 1) {
			String sql = "select test_result , count(*) from  candidate_test "
					+ "where position_id = :positionId and test_state in (:testStates)   " ;
			sql += invitationDaoImpl.getCandidateTestFilterSqlByEmployer(posOrActId, employerId);
			sql += " group by test_result ";
			query = session.createSQLQuery(sql);
			query.setInteger("positionId", posOrActId);
//			query.setInteger("employerId", employerId);
//			query.setParameterList("positionIds", list);
		}else{
			throw new RuntimeException("error type for not 1 and 2 ,but " + type);
		}
		
		query.setParameterList("testStates", new Integer[] {
				InvitationState.INVITATION_STATE_REPORT1,
				InvitationState.INVITATION_STATE_REPORT2 });
		query.setResultTransformer(new ReportStatusResultTransformer());
		return query.list();
	}

	class ReportStatusResultTransformer implements ResultTransformer{

		private static final long serialVersionUID = 4081030857099706389L;

		@Override
		public Object transformTuple(Object[] tuple, String[] aliases) {
			ReportStatusCountInfo info = new ReportStatusCountInfo();
			info.setTestResult((Integer) tuple[0]);
			info.setCount(((BigInteger) tuple[1]).intValue());
			return info;
		}

		@SuppressWarnings("rawtypes")
		@Override
		public List transformList(List collection) {
			return collection;
		}
		
	}
	@Override
	public List<SchoolCandidateReport> getAllSchoolReportList(Integer employerId,Integer positionId, Integer activityId, Integer testResult) {
		Session session = getSession();
		String sql = "select report.test_id,report.get_score, report.candidate_id ,invite.candidate_name, invite.candidate_email, invite.position_id"
				+ " from candidate_report report  , invitation invite ";
		if (testResult != null) {
			sql += " , candidate_test test  where report.test_id = test.test_id ";
		} else {
			sql += " where 1 = 1";
		}
		sql += " and report.test_id = invite.test_id and   report.test_id in ";
		
		String testIdIn = "";
		if(activityId != null){//按活动过滤
			
			CompanyRecruitActivity activity = activityDao.getEntity(activityId);
		    Position position = positionDao.getEntity(activity.getPositionId());
		    if(position.getGroupFlag() == Constants.GROUP_FLAG_PARENT){
		    	testIdIn = "( select test_id  from invitation  t3 , company_recruit_activity t4  "
					+ " where  t3.position_id in ( select position_id from position_relation where position_group_id = t4.position_id )  " 
					+ "  and t3.passport = t4.passcode and t4.activity_id = "
						+ activityId + ")";
		    }else{
		    	testIdIn = "( select test_id  from invitation  t3 , company_recruit_activity t4  "
						+ "where  t3.position_id = t4.position_id  and t3.passport = t4.passcode and t4.activity_id = "
						+ activityId + ")";
		    }
		}else if(positionId != null){//按测评过滤
			Position position = positionDao.getEntity(positionId);
			if(position.getGroupFlag() == Constants.GROUP_FLAG_PARENT){
				List<PositionRelation> positionRelations = positionRelationDao.getPositionRelationByPositionGroupId(positionId);
				StringBuffer sb = new StringBuffer();
				sb.append("(");
				for(PositionRelation pr : positionRelations){
					sb.append(pr.getId().getPositionId()).append(",");
				}
				sb.deleteCharAt(sb.length() - 1);
				sb.append(")");
				testIdIn=	"( select test_id  from invitation  t3  where  t3.position_id  in  "+ sb.toString() ;
			}
			else {
				testIdIn=	"( select test_id  from invitation  t3  where  t3.position_id  = "+ positionId ;
			}
			testIdIn+= invitationDaoImpl.getInvitationFilterSqlByEmployer(positionId, employerId);
			testIdIn+= ")";
		}
		sql += testIdIn;
		
		if (testResult != null) {
			sql += " and test.test_result = " + testResult;
		}
		sql += " order by report.get_score desc";
		logger.debug("the query act sql is {} ", sql);
		Query query = session.createSQLQuery(sql);
		query.setResultTransformer(new ResultTransformer() {
			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				SchoolCandidateReport report = new SchoolCandidateReport();
				report.setTestId(((BigInteger) tuple[0]).longValue());
				report.setGetScore(((BigDecimal) tuple[1]).doubleValue());
				report.setCandidateId((Integer) tuple[2]);
				report.setCandidateName((String) tuple[3]);
				report.setCandidateEmail((String) tuple[4]);
				report.setPositionId((Integer)tuple[5]);
				/**
				 * if (tuple[5] != null) report.setCandidatePhone(new
				 * BigDecimal(tuple[5] + "") .toString());
				 */
				return report;
			}

			@SuppressWarnings("rawtypes")
			@Override
			public List transformList(List collection) {
				return collection;
			}
		});
		List<SchoolCandidateReport> list = query.list();
		return list;
	}

	@Override
	public List<ReportStatusCountInfo> getSchoolReportStatusCountByEmployerId(int employerId, int testType) {

		Session session = sessionFactory.getCurrentSession();
		String sql = "select test_result , count(*) from  candidate_test "
				+ "where test_id in" +
				" (select test_id from invitation  where " 
			     	+ " (employer_id = :employerId and position_id in (select position_id from position where test_type = :testType))" //自己邀请的
				    + " or position_id in ( select position_id from position where employer_id = :employerId  and  test_type = :testType )" //自己测评的邀请
			 +  " )  "
				+ " and test_state in (:testStates) group by test_result";
		
		logger.debug("getSchoolReportStatusCountByEmployerId sql is {} ", sql);
		Query query = session.createSQLQuery(sql);
		query.setInteger("employerId", employerId);
		query.setInteger("testType", testType);
		query.setParameterList("testStates", new Integer[] { InvitationState.INVITATION_STATE_REPORT1,
				InvitationState.INVITATION_STATE_REPORT2 });
		query.setResultTransformer(new ReportStatusResultTransformer());
		return query.list();

	}
	
	@Override
	public List<ReportStatusCountInfo> getSchoolReportStatusCountByPositionId(int employerId, int positionId){
		Session session = sessionFactory.getCurrentSession();
		String sql = "select test_result , count(*) from  candidate_test "
				+ "where  position_id =:positionId  "
				+ " and test_state in (:testStates) " ;
		 sql+= invitationDaoImpl.getCandidateTestFilterSqlByEmployer(positionId, employerId);
		 sql+= "group by test_result";
		 logger.debug("getSchoolReportStatusCountByPositionId sql is {} ", sql);
		Query query = session.createSQLQuery(sql);
//		query.setInteger("employerId", employerId);
		query.setInteger("positionId", positionId);
		query.setParameterList("testStates", new Integer[] { InvitationState.INVITATION_STATE_REPORT1,
				InvitationState.INVITATION_STATE_REPORT2 });
		query.setResultTransformer(new ReportStatusResultTransformer());
		return query.list();
	}

}
