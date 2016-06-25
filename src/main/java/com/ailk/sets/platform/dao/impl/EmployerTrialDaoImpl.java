package com.ailk.sets.platform.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IEmployerTrialDao;
import com.ailk.sets.platform.intf.cand.domain.Employer;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.common.FuncBaseResponse;
import com.ailk.sets.platform.intf.domain.Company;
import com.ailk.sets.platform.intf.domain.CompanyDomainName;
import com.ailk.sets.platform.intf.empl.domain.EmployerCompanyInfo;
import com.ailk.sets.platform.intf.empl.domain.EmployerTrialApply;

@Repository
public class EmployerTrialDaoImpl extends BaseDaoImpl<EmployerTrialApply>
		implements IEmployerTrialDao {
	private Logger logger = LoggerFactory.getLogger(EmployerTrialDaoImpl.class);

	@Override
	public Employer getEmployerByEmail(String email) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Employer.class);
		criteria.add(Restrictions.eq("employerAcct", email));
		Employer em = (Employer) criteria.uniqueResult();
		return em;
	}

	@Override
	public EmployerTrialApply getEmployerTrialByEmail(String email) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EmployerTrialApply.class);
		criteria.add(Restrictions.eq("userEmail", email));
		EmployerTrialApply em = (EmployerTrialApply) criteria.uniqueResult();
		return em;
	}

	@Override
	public EmployerTrialApply getEmployerTrialByKey(String key) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(EmployerTrialApply.class);
		criteria.add(Restrictions.eq("activationKey", key));
		EmployerTrialApply em = (EmployerTrialApply) criteria.uniqueResult();
		return em;
	}

	public void initCompanyInfoExt(Employer employer) {
		String initConfigSql = "INSERT INTO position_info_ext(employer_id,info_id,info_name,seq,data_type,value_type,value_sql,value_length,verify_exp,mandatory) "
				+ " select  "
				+ employer.getEmployerId()
				+ ",info_id,info_name,@row\\:= @row + 1 as seq_new,data_type,value_type,value_sql,value_length,verify_exp, 1 from config_info_ext  , (SELECT @row\\:= 0) r where is_default = 1 order by seq ";
		Session session = sessionFactory.getCurrentSession();
		session.createSQLQuery(initConfigSql).executeUpdate();
	}

	/**
	 * 获取账号公司信息
	 * 
	 * @param employerId
	 * @return
	 */
	public EmployerCompanyInfo getEmployerCompanyInfo(Integer employerId) {
		EmployerCompanyInfo info = new EmployerCompanyInfo();
		Session session = sessionFactory.getCurrentSession();
		session.get(Employer.class, employerId);
		Employer em = (Employer) session.get(Employer.class, employerId);
		String email = em.getEmployerAcct();
		if (em.getCompanyId() == null) {// 没有公司
			String emailSuffix = email.substring(email.indexOf("@") + 1);
			CompanyDomainName cdn = (CompanyDomainName) session.get(
					CompanyDomainName.class, emailSuffix);
			if (cdn == null) {
				info.setCode(FuncBaseResponse.COMPANYNOTFOUND);
			} else {
				info.setCode(FuncBaseResponse.COMPANYFROMDOMAIN);
				info.setCompanyName(cdn.getCompanyName());
			}
		} else {
			Company c = (Company) session.get(Company.class, em.getCompanyId());
			info.setCode(FuncBaseResponse.SUCCESS);
			info.setCompanyName(c.getCompanyName());
		}
		return info;
	}

	@Override
	public Employer getNormalEmployerByEmail(String email) {
		Employer employer = getEmployerByEmail(email);
		if(employer == null){
			return null;
		}
		if(employer.getAcctType() == null || employer.getAcctType() != Constants.SAMPLE_ACCT_TYPE){
			return employer;
		}
		logger.info("has sample employer for email {} ", email);
		return null;
	}
}
