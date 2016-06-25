package com.ailk.sets.platform.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IInvitationDao;
import com.ailk.sets.platform.dao.interfaces.IReportDao;
import com.ailk.sets.platform.intf.common.FuncBaseResponse;
import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.common.PaperPartSeqEnum;
import com.ailk.sets.platform.intf.empl.domain.CandidateReport;
import com.ailk.sets.platform.intf.empl.domain.ConfigReport;
import com.ailk.sets.platform.intf.empl.domain.ConfigReportPart;
import com.ailk.sets.platform.intf.empl.domain.PaperGrade;
import com.ailk.sets.platform.intf.empl.domain.PaperInstancePartInfo;
import com.ailk.sets.platform.intf.empl.domain.Position;
import com.ailk.sets.platform.intf.empl.service.IPosition;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.intf.model.condition.Interval;
import com.ailk.sets.platform.intf.model.invatition.InvitationState;
import com.ailk.sets.platform.intf.model.param.GetReportParam;
import com.ailk.sets.platform.intf.school.domain.SchoolCandidateReport;
import com.ailk.sets.platform.util.DateUtils;
import com.ailk.sets.platform.util.PaperCreateUtils;

@Repository
public class ReportDaoImpl extends BaseDaoImpl<CandidateReport> implements
		IReportDao {

	private Logger logger = LoggerFactory.getLogger(ReportDaoImpl.class);
	@Autowired
	private IInvitationDao invitationDao;
	@SuppressWarnings("unchecked")
	public List<PaperGrade> getPaperGrade(int paperInstId) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Position.class);
		criteria.add(Restrictions.eq("id.paperInstId", paperInstId));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PaperInstancePartInfo> getPaperInstancePartInfo(int id, int type) {
		Session session = null;
		List<PaperInstancePartInfo> list = new ArrayList<PaperInstancePartInfo>();
		session = sessionFactory.getCurrentSession();
		String sql;
		sql = "select part_seq,suggest_time,part_points,question_num,part_desc from  paper_part where paper_id  = "
				+ id;

		Query query = session.createSQLQuery(sql);
		List<Object[]> results = query.list();
		for (Object[] os : results) {
			PaperInstancePartInfo info = new PaperInstancePartInfo();
			info.setPartSeq((Integer) os[0]);
			info.setPartName((String) os[4]);
			info.setSuggestTime((Integer) os[1]);
			info.setPartPoints((Integer) os[2]);
			info.setQuestionNum((Integer) os[3]);
			list.add(info);
		}
		return list;
	}

	@Override
	public int getTestResult(long testId) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		String hql = "select testResult from CandidateTest where testId = "
				+ testId;
		Query query = session.createQuery(hql);
		return (Integer) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<ConfigReport> getConfigReport() {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ConfigReport.class);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<ConfigReportPart> getConfigReportPart() {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ConfigReportPart.class);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<CandidateReport> getReportByCandidate(int candidateId) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(CandidateReport.class);
		criteria.add(Restrictions.eq("candidateId", candidateId));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CandidateReport> getReport(GetReportParam param) {
	    
	    Session session = null;
        session = sessionFactory.getCurrentSession();

        String testResult = param.getTestResult();
        if(testResult.equals(InvitationState.CANDIDATE_TEST_RESULT1+"")){//0726 版本，因为界面改动大，所以临时将复试状态加入到已推荐
            testResult = testResult + "," + InvitationState.CANDIDATE_TEST_RESULT3;
        }
        
      // add by lipan 2014年7月11日16:05:22 如果为校招，并且使用passport定位到某个活动
      StringBuffer candidateTestParam = new StringBuffer();
      if (!StringUtils.isBlank(param.getPassport())) {
          candidateTestParam.append(" and test.passport='" + param.getPassport()+"'");
      }
      // add by lipan 2014年10月24日 增加报告查询条件
      if (StringUtils.isNotBlank(param.getCommitPaperFromDate()))// 交卷起点时间
        {
          candidateTestParam.append(" and test.end_time>='"+param.getCommitPaperFromDate()+" 00:00'");
        }
      if (StringUtils.isNotBlank(param.getCommitPaperToDate()))// 交卷终点时间
      {
            candidateTestParam.append(" and test.end_time<='"+param.getCommitPaperToDate()+" 23:59'");
      }
        
        StringBuilder candTable = new StringBuilder(
                "select * from (select b.candidate_id,b.candidate_name,b.candidate_email," +
                "max(case a.info_id when 'PHONE' then a.value else 0 end) as PHONE," +
                "sum(case a.info_id when 'INTENTION_POSITION' then a.value else 0 end) as INTENTION_POSITION " +
                "from candidate_info_ext a right join candidate b on a.candidate_id = b.candidate_id group by b.candidate_id ) c where 1=1");
        if (StringUtils.isNotEmpty(param.getInputKey())) {
            candTable
                    .append(" and ((c.candidate_name  like :searchTxt or c.candidate_email like :searchTxt or c.PHONE  like :searchTxt) "
                            + "or ((c.candidate_name  like :searchTxt or c.candidate_email like :searchTxt) and c.PHONE is null)) ");
        }
        if (StringUtils.isNotEmpty(param.getPositionIntent())) {
            candTable.append(" and c.INTENTION_POSITION= '" + param.getPositionIntent() + "' ");
        }

//        String sql = "select report.test_id,report.get_score, d.candidate_id, d.candidate_name, d.candidate_email, d.PHONE "
        String sql = "select report.* "
                + " from candidate_report report,candidate_test test, ("
                + candTable.toString()
                + ") d "
                + "  where report.candidate_id = d.candidate_id "
//                + "  and test.test_id = report.test_id  and report.test_id in ";
                + "  and test.test_id = report.test_id  and test.position_id= "+param.getPositionId()
                + candidateTestParam.toString();
                if (StringUtils.isNotBlank(param.getScore().getMax()))
                {
                    sql += " and get_score<="+param.getScore().getMax();
                }
                if (StringUtils.isNotBlank(param.getScore().getMin()))
                {
                    sql += " and get_score>="+param.getScore().getMin();
                }
//        String testIdIn = "( select test_id  from invitation  t3 , company_recruit_activity t4  "
//                    + "where  t3.position_id = t4.position_id  and t3.passport = t4.passcode and t4.activity_id = "
//                    + activityId + ")";
//        }else if(positionId != null){//按测评过滤
//            testIdIn=   "( select test_id  from invitation  t3  where  t3.position_id  = "+ positionId ;
//            testIdIn+= invitationDaoImpl.getInvitationFilterSqlByEmployer(positionId, employerId);
//            testIdIn+= ")";
//        }
//        sql += testIdIn;
        sql += " and test.test_result in ( " + testResult +")";
        
        sql += " order by report.get_score desc";
        logger.debug("the query report sql is {} ", sql);
        Query query = session.createSQLQuery(sql).addEntity(CandidateReport.class);
        if (StringUtils.isNotEmpty(param.getInputKey())) {
            query.setParameter("searchTxt", "%" + param.getInputKey() + "%");
        }
        query.setFirstResult((param.getPage().getRequestPage() - 1) * param.getPage().getPageSize());
        query.setMaxResults(param.getPage().getPageSize());
//        query.setResultTransformer(new ResultTransformer() {
//            @Override
//            public Object transformTuple(Object[] tuple, String[] aliases) {
//                CandidateReport report = new CandidateReport();
//                report.setTestId(((BigInteger) tuple[0]).longValue());
//                report.setGetScore(((BigDecimal) tuple[1]).doubleValue());
//                report.setCandidateName((String) tuple[3]);
//                report.setCandidateEmail((String) tuple[4]);
//                if (tuple[5] != null)
//                    report.setCandidatePhone(new BigDecimal(tuple[5] + "")
//                            .toString());
//                return report;
//            }
//
//            @SuppressWarnings("rawtypes")
//            @Override
//            public List transformList(List collection) {
//                return collection;
//            }
//        });
        
	    // TODO 
//		Session session = null;
//		session = sessionFactory.getCurrentSession();
//
//		// add by lipan 2014年7月11日16:05:22 如果为校招，并且使用passport定位到某个活动
//		StringBuffer candidateTestParam = new StringBuffer();
//		if (!StringUtils.isBlank(param.getPassport())) {
//		    candidateTestParam.append(" and passport='" + param.getPassport()+"'");
//		}
//		
//		// add by lipan 2014年10月24日 增加报告查询条件
//		if (StringUtils.isNotBlank(param.getCommitPaperFromDate()))// 交卷起点时间
//        {
//		    candidateTestParam.append(" and endTime>="+DateUtils.getTimestamp(param.getCommitPaperFromDate(), DateUtils.DATE_FORMAT_2));
//        }
//		if (StringUtils.isNotBlank(param.getCommitPaperToDate()))// 交卷终点时间
//		{
//            candidateTestParam.append(" and endTime<="+DateUtils.getTimestamp(param.getCommitPaperToDate(), DateUtils.DATE_FORMAT_2));
//		}
//		
//		
//		String testResult = param.getTestResult();
//		if(testResult.equals(InvitationState.CANDIDATE_TEST_RESULT1+"")){//0726 版本，因为界面改动大，所以临时将复试状态加入到已推荐
//			testResult = testResult + "," + InvitationState.CANDIDATE_TEST_RESULT3;
//		}
//		String hql = "from CandidateReport t,CandidateInfoExt e  where testId in (select testId from CandidateTest where t.positionId = "
//				+ param.getPositionId()
//				+ candidateTestParam.toString()
//				+ " and testResult in ("
//				+ testResult
//				+ ")) and getScore >= "
//				+ param.getScore().getMin()
//				+ " and getScore <= "
//				+ param.getScore().getMax()
//				+ " t.candidateId=e.candidateId "
//				;
//		
//		hql += invitationDao.getCandidateTestFilterByEmployer(param.getPositionId(), param.getEmployerId());
//		hql += " order by getScore desc,reportDate desc";
//		Query query = session.createQuery(hql);
//		query.setFirstResult((param.getPage().getRequestPage() - 1)
//				* param.getPage().getPageSize());
//		query.setMaxResults(param.getPage().getPageSize());
		return query.list();
	}

	@Override
	public void setReportState(long testId, int state) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("update CandidateReport set reportState = "
						+ state + " where testId=" + testId);
		query.executeUpdate();
	}

	/**
	 * 获取题目汇总信息
	 * 
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Integer> getCountQuestionNumber(String inStr) {
		String sql = "select count(*) , s.skill_id, q.degree from qb_question q, qb_question_skill s "
				+ "where  s.question_id = q.question_id "
				+ " and  q.question_id in  "
				+ inStr
				+ " and q.question_type in "
				+ PaperCreateUtils
						.getQuestionTypesInStr(PaperPartSeqEnum.OBJECT)
				+ " and  q.is_sample = 0  group by s.skill_id,q.degree ";// 应要求试卷模型
																			// 去掉编程题
		logger.debug("the count sql is {} ", sql);
		Session session = sessionFactory.getCurrentSession();
		List<Object[]> list = (List<Object[]>) session.createSQLQuery(sql)
				.list();
		Map<String, Integer> result = new HashMap<String, Integer>();
		for (Object[] o : list) {
			int number = ((BigInteger) o[0]).intValue();
			String key = (String) o[1] + "_" + (Integer) o[2];// skill_degree作为key
			result.put(key, number);
		}
		return result;
	}

	/**
	 * 获取题目汇总信息
	 * 
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Integer> getCountQuestionNumberOfSubject(String inStr) {
		String sql = "select count(*) , s.skill_id, q.degree from qb_question q, qb_question_skill s "
				+ "where  s.question_id = q.question_id "
				+ " and q.question_id in  "
				+ inStr
				+ " and q.question_type in "
				+ PaperCreateUtils
						.getQuestionTypesInStr(PaperPartSeqEnum.SUBJECT)
				+ " and  q.is_sample = 0  group by s.skill_id ";// 应要求试卷模型 去掉编程题
		logger.debug("the count subject sql is {} ", sql);
		Session session = sessionFactory.getCurrentSession();
		List<Object[]> list = (List<Object[]>) session.createSQLQuery(sql)
				.list();
		Map<String, Integer> result = new HashMap<String, Integer>();
		for (Object[] o : list) {
			int number = ((BigInteger) o[0]).intValue();
			String key = (String) o[1];// skillid
			result.put(key, number);
		}
		return result;
	}

	public static void main(String[] args)
    {
	    Page page = new Page();
        page.setPageSize(5);
        page.setRequestPage(2);
        
	    GetReportParam param = new GetReportParam();
        param.setEmployerId(100000069);
        param.setPage(page);
        param.setTestResult("0");
        param.setPassport("MaTTSHtWoG");
        param.setScore(new Interval("1", "100"));
        
        String passportParam = "";
	    if (!StringUtils.isBlank(param.getPassport())) {
            passportParam = " and passport='" + param.getPassport()+"'";
        }
        String hql = "from CandidateReport where testId in (select testId from CandidateTest where positionId = "
                + param.getPositionId()
                + passportParam
                + " and testResult in ("
                + param.getTestResult()
                + ")) and getScore >= "
                + param.getScore().getMin()
                + " and getScore <= "
                + param.getScore().getMax()
                + " order by getScore desc,reportDate desc";
        System.out.println(hql);
    }

	@Override
	public List<CandidateReport> getCandidateReportOfSample(int positionId) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		String hql = "from CandidateReport where sample = 1 and testId in (select testId from CandidateTest where positionId = "
				+ positionId
				+ ") ";
		Query query = session.createQuery(hql);
		query.setMaxResults(1);
		return query.list();
	
	}

	@Override
	public List<CandidateReport> getCandidateReportsLargerThanScore(double score, int positionId) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		String hql = "from CandidateReport where getScore  > "+ score + " and testId in (select testId from CandidateTest where positionId = "
				+ positionId
				+ ") ";
		Query query = session.createQuery(hql);
		return query.list();
	}
}
