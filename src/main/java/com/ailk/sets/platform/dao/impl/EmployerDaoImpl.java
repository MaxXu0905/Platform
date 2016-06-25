package com.ailk.sets.platform.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IEmployerDao;
import com.ailk.sets.platform.intf.cand.domain.Employer;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.common.FuncBaseResponse;
import com.ailk.sets.platform.intf.model.employer.LoginInfo;
import com.ailk.sets.platform.intf.model.employer.RegisterInfo;

/**
 * IEmployerDao接口实现类
 * 
 * @author 毕希研
 * 
 */
@Repository
public class EmployerDaoImpl extends BaseDaoImpl<Employer> implements
		IEmployerDao {

	private Logger logger = LoggerFactory.getLogger(EmployerDaoImpl.class);

	public RegisterInfo register(Employer employer) {
		RegisterInfo ri = new RegisterInfo();
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(Employer.class);
		criteria.add(Restrictions.eq("employerAcct", employer.getEmployerAcct()));
		Employer em = (Employer) criteria.uniqueResult();
		if (em != null) {
			ri.setCode(FuncBaseResponse.ACCTREGISTERED);
			return ri;
		}
		ri.setCode(FuncBaseResponse.SUCCESS);
		session.save(employer);
		ri.setEmployer(employer);
		return ri;
	}

	public LoginInfo certify(String username, String password) {
		LoginInfo loginInfo = new LoginInfo();
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Employer.class);
		criteria.add(Restrictions.eq("employerAcct", username));
		Employer employer = (Employer) criteria.uniqueResult();
		if (employer == null || (employer.getAcctType() != null && employer.getAcctType() == Constants.SAMPLE_ACCT_TYPE)) {//体验,委托,外部账号也认为不存在
			loginInfo.setCode(FuncBaseResponse.ACCTNOTEXIST);
			return loginInfo;
		}

		criteria.add(Restrictions.eq("employerPwd", password));
		employer = (Employer) criteria.uniqueResult();
		if (employer == null) {
			loginInfo.setCode(FuncBaseResponse.PASSWORDERROR);
		} else {
			loginInfo.setCode(FuncBaseResponse.SUCCESS);
			loginInfo.setEmployer(employer);
		}
		return loginInfo;
	}

	@Override
	public void removeOpenId(String openId) {
		Session session = getSession();
		String hql = "update Employer set openId = null where openId = '"
				+ openId + "'";
		Query query = session.createQuery(hql);
		query.executeUpdate();
	}

	public Employer getEmployerByOpenId(String openId, int employerId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Employer.class);
		criteria.add(Restrictions.eq("openId", openId));
		criteria.add(Restrictions.eq("employerId", employerId));
		Employer employer = (Employer) criteria.uniqueResult();
		return employer;
	}

	public Employer getEmployerByOpenId(String openId) {
		Session session = getSession();
		Criteria criteria = session.createCriteria(Employer.class);
		criteria.add(Restrictions.eq("openId", openId));
		List<Employer> list = criteria.list();
		if (!CollectionUtils.isEmpty(list)) {
			if (list.size() > 1) {
				logger.warn(
						"the employer size is more than one for openid {} , please check ",
						openId);
			}
			Employer employer = list.get(0);
			return employer;
		}
		return null;
	}

	public Employer selectEmployerByOpenId(String openId) {
		Session session = getSession();
		String hql = "from Employer where openId = '" + openId + "'";
		Query query = session.createQuery(hql);
		Employer employer = (Employer) query.uniqueResult();
		return employer;

	}

	@Override
	public Employer getEmployerByEmail(String email, Integer acctType) {
		Session session = getSession();
		String hql = "from Employer where employerAcct = '" + email + "'";
		if(acctType != null){
			hql += " and acctType = " + acctType;
		}
		Query query = session.createQuery(hql);
		Employer employer = (Employer) query.uniqueResult();
		return employer;
	}

	@Override
	public Employer getEmployerByToken(String employerToken) {
		Session session = getSession();
		String hql = "from Employer where employerToken = '" + employerToken + "'";
		Query query = session.createQuery(hql);
		Employer employer = (Employer) query.uniqueResult();
		return employer;
	}
}
