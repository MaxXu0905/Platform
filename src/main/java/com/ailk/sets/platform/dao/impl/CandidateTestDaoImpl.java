package com.ailk.sets.platform.dao.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.ICandidateTestDao;
import com.ailk.sets.platform.dao.interfaces.IInvitationDao;
import com.ailk.sets.platform.domain.CandidateTestMonitor;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.common.FuncBaseResponse;
import com.ailk.sets.platform.intf.common.PFResponse;
import com.ailk.sets.platform.intf.domain.CompanyRecruitActivity;
import com.ailk.sets.platform.intf.empl.domain.CandidateReport;
import com.ailk.sets.platform.intf.empl.domain.CandidateTest;
import com.ailk.sets.platform.intf.empl.domain.EmployerOperationLog;
import com.ailk.sets.platform.intf.empl.service.IPosition;
import com.ailk.sets.platform.intf.exception.PFServiceException;
import com.ailk.sets.platform.intf.model.Page;
import com.ailk.sets.platform.intf.model.invatition.InvitationState;
import com.ailk.sets.platform.intf.model.param.GetReportParam;
import com.ailk.sets.platform.util.PassportGenerator;

@Repository
public class CandidateTestDaoImpl extends BaseDaoImpl<CandidateTest> implements
		ICandidateTestDao {
	private Logger logger = LoggerFactory.getLogger(CandidateTestDaoImpl.class);
	@Autowired
	private IInvitationDao invitationDao;

	@Override
	public boolean setTestResult(long testId, int choose) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("update CandidateTest set testResult=" + choose
						+ " where testId=" + testId);
		int num = query.executeUpdate();
		return (num == 1);
	}

	@Override
	public long getCountByState(int employerId,int positionId, int state)  {
		Session session = sessionFactory.getCurrentSession();
		String sql = "select count(*) from CandidateReport where testId in (select testId from CandidateTest where positionId="
				+ positionId + " and testResult=" + state + ")"  ;
		sql += invitationDao.getCandidateTestFilterByEmployer(positionId, employerId);
		Query query = session
				.createQuery(sql);
		Long result = (Long) query.uniqueResult();
		return result == null ? 0 : result;
	}

	@Override
	public void saveTestMonitor(CandidateTestMonitor candidateTestMonitor) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(candidateTestMonitor);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getTestMonitor(long testId, int isAbnormal, Page page) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		String hql = "select picFile from CandidateTestMonitor where testId="
				+ testId + " and isAbnormal=" + isAbnormal
				+ " order by faceNum desc, createTime desc";
		Query query = session.createQuery(hql);
		query.setFirstResult((page.getRequestPage() - 1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		return query.list();
	}

	@Override
	public void updateCandidatePic(long testId, String url) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "update CandidateTest set candidatePic='" + url
				+ "' where testId=" + testId;
		Query query = session.createQuery(hql);
		query.executeUpdate();
	}

	@Override
	public int getPaperInstId(long testId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "select paperInstId from CandidateTest where testId="
				+ testId;
		Query query = session.createQuery(hql);
		return (Integer) query.uniqueResult();
	}

	@Override
	public void saveCandidateTest(CandidateTest candidateTest) {
		String ticketNew = "";
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(CandidateTest.class);
		criteria.add(Restrictions.eq("testId", candidateTest.getTestId()));
		criteria.add(Restrictions.eq("passport", candidateTest.getPassport()));
		CandidateTest ce = (CandidateTest) criteria.uniqueResult();
		if (ce == null) {
			ticketNew = PassportGenerator
					.getRandomPassport(Constants.SESSIONTICKETLENGTH);
			candidateTest.setSessionTicket(ticketNew);
			session.save(candidateTest);
		} else {
			do {
				ticketNew = PassportGenerator
						.getRandomPassport(Constants.SESSIONTICKETLENGTH);
			} while (ce.getSessionTicket().equals(ticketNew));
			ce.setSessionTicket(ticketNew);
			session.update(ce);
		}
	}

	@Override
	public void updateBreakTimes(long testId) {
		Session session = sessionFactory.getCurrentSession();
		String sql = "update candidate_test set break_times = break_times+1 where test_id ="
				+ testId;
		Query query = session.createSQLQuery(sql);
		query.executeUpdate();
	}

	@Override
	public CandidateTest getCandidateTest(long testId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(CandidateTest.class);
		criteria.add(Restrictions.eq("testId", testId));
		return (CandidateTest) criteria.uniqueResult();
	}
    @Override
	public CandidateTest loginCandidateTest(long testId, String passport) {
		String ticketNew = "";
		Session session = getSession();
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq("testId", testId));
		criteria.add(Restrictions.eq("passport", passport));
		CandidateTest ce = (CandidateTest) criteria.uniqueResult();
		if (ce != null) {
			if (ce.getSessionTicket() != null)
				do {
					ticketNew = PassportGenerator
							.getRandomPassport(Constants.SESSIONTICKETLENGTH);
				} while (ce.getSessionTicket().equals(ticketNew));
			else
				ticketNew = PassportGenerator
						.getRandomPassport(Constants.SESSIONTICKETLENGTH);
			ce.setSessionTicket(ticketNew);
			session.update(ce);
		}
		return ce;
	}
    @Override
	public CandidateTest getCandidateTest(long testId, String passport) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(CandidateTest.class);
		criteria.add(Restrictions.eq("testId", testId));
		criteria.add(Restrictions.eq("passport", passport));
		return (CandidateTest) criteria.uniqueResult();
	}

    public boolean markQuestion(long testId, int partSeq, long qId) {
		Session session = null;
		String key = partSeq + "_" + qId;
		session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(CandidateTest.class);
		criteria.add(Restrictions.eq("testId", testId));
		CandidateTest ce = (CandidateTest) criteria.uniqueResult();
		if (StringUtils.isNotEmpty(ce.getQuestionMark())) {
			String[] strArr = ce.getQuestionMark().split("\\|");
			StringBuilder sb = new StringBuilder();
			for (String str : strArr) {
				if (!str.equals(key + "")) {
					sb.append(str + "|");
				}
			}
			if ((sb.length() - 1) == ce.getQuestionMark().length())// 原来没有标记此题
			{
				sb.append(key);
				ce.setQuestionMark(sb.toString());
			}

		} else {
			ce.setQuestionMark(key + "");
		}
		session.saveOrUpdate(ce);
		return true;
	}

	/**
	 * 取消标记问题
	 * 
	 * @param testId
	 * @param qId
	 * @return
	 */
	public boolean unMarkQuestion(long testId, int partSeq, long qId) {
		Session session = null;
		String key = partSeq + "_" + qId;
		session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(CandidateTest.class);
		criteria.add(Restrictions.eq("testId", testId));
		CandidateTest ce = (CandidateTest) criteria.uniqueResult();
		if (StringUtils.isNotEmpty(ce.getQuestionMark())) {
			String[] strArr = ce.getQuestionMark().split("\\|");
			StringBuilder sb = new StringBuilder();
			for (String str : strArr) {
				if (!str.equals(key + "")) {
					sb.append(str + "|");
				}
			}
			if ((sb.length() - 1) == ce.getQuestionMark().length()) {// 原来没有标记此题目
				logger.warn(
						"not unmark because of the qid {} , the testId {}  is not unmark ",
						key, testId);
				return true;
			} else if (sb.length() > 1) {
				sb.deleteCharAt(sb.length() - 1);
			}

			ce.setQuestionMark(sb.toString());
			session.saveOrUpdate(ce);
			return true;
		}
		logger.warn("not found any mark for the testId {}   ", testId);
		return true;
	}

	public boolean isMarkedTheQuestion(long testId, int partSeq, long qId) {
		Session session = null;
		String key = partSeq + "_" + qId;
		session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(CandidateTest.class);
		criteria.add(Restrictions.eq("testId", testId));
		CandidateTest ce = (CandidateTest) criteria.uniqueResult();
		if (StringUtils.isNotEmpty(ce.getQuestionMark())) {
			String[] strArr = ce.getQuestionMark().split("\\|");
			for (String str : strArr) {
				if (str.equals(key + "")) {
					return true;// 包含
				}
			}
		}
		logger.warn("not found any mark for the testId {}   ", testId);
		return false;
	}

	@Override
	public void updateSwitchTimes(long testId) {
		Session session = getSession();
		String sql = "update candidate_test set switch_times = switch_times+1 where test_id ="
				+ testId;
		Query query = session.createSQLQuery(sql);
		query.executeUpdate();
	}

	@Override
	public void updateFreshTimes(long testId) {
		Session session = getSession();
		String sql = "update candidate_test set fresh_times = fresh_times+1 where test_id ="
				+ testId;
		Query query = session.createSQLQuery(sql);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<CandidateTest> getByActivityId(
			CompanyRecruitActivity companyRecruitActivity) {
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq("positionId",
				companyRecruitActivity.getPositionId()));
		criteria.add(Restrictions.eq("passport",
				companyRecruitActivity.getPasscode()));
		return criteria.list();
	}

	/**
	 * 更新candidateTestlog 包括答题者ip，浏览器，操作系统等等
	 * 
	 * @param testId
	 * @param log
	 */
	@Override
	public void updateCandidateTestLog(long testId, EmployerOperationLog log) {
		Session session = getSession();
		String hql = "update CandidateTest set  loginTime =:loginTime,  clientIp = :clientIp, os = :os, browser=:browser, browserVersion=:browserVersion where testId = :testId";
		Query query = session.createQuery(hql);
		query.setTimestamp("loginTime",
				new Timestamp(System.currentTimeMillis()));
		query.setString("clientIp", log.getClientIp());
		query.setString("os", log.getOs());
		query.setString("browser", log.getBrowser());
		query.setString("browserVersion", log.getBrowserVersion());
		query.setLong("testId", testId);
		int size = query.executeUpdate();
		logger.debug("updateCandidateTestLog size is {}  for testId {} ", size,
				testId);
	}

	/**
	 * 获得宣讲会的考试人数
	 * 
	 * @param positionId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, Long> getTestCount(int positionId, String passport) {
		Map<Integer, Long> map = new HashedMap();
		String hql = "select testResult,count(testId) from CandidateTest where positionId=:positionId and passport=:passport and testState in(34,35) group by testResult";
		Query query = getSession().createQuery(hql);
		query.setInteger("positionId", positionId);
		query.setString("passport", passport);
		List<Object[]> list = query.list();
		if (!CollectionUtils.isEmpty(list)) {
			for (Object[] temp : list) {
				map.put((Integer) temp[0], (Long) temp[1]);
			}
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CandidateTest> getReadyList() {
		Session session = sessionFactory.getCurrentSession();

		return session.createQuery(
				"FROM CandidateTest WHERE testState in (2, 3)").list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CandidateTest> getByPositionId(int positionId) {
		Session session = sessionFactory.getCurrentSession();

		return session
				.createQuery(
						"FROM CandidateTest WHERE positionId=?1 AND testState in (34, 35)")
				.setInteger("1", positionId).list();
	}

    @Override
    public List<CandidateTest> getCandidateTestList(GetReportParam param)
    {
        Session session = null;
        session = sessionFactory.getCurrentSession();

//        String testResult = param.getTestResult();
//        if(testResult.equals(InvitationState.CANDIDATE_TEST_RESULT1+"")){//0726 版本，因为界面改动大，所以临时将复试状态加入到已推荐
//            testResult = testResult + "," + InvitationState.CANDIDATE_TEST_RESULT3;
//        }
        
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
        String sql = "select test.* "
                + " from candidate_report report,candidate_test test, ("
                + candTable.toString()
                + ") d "
                + "  where report.candidate_id = d.candidate_id "
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
        logger.debug("the query report sql is {} ", sql);
        Query query = session.createSQLQuery(sql).addEntity(CandidateTest.class);
        if (StringUtils.isNotEmpty(param.getInputKey())) {
            query.setParameter("searchTxt", "%" + param.getInputKey() + "%");
        }
        return query.list();
    }

}
