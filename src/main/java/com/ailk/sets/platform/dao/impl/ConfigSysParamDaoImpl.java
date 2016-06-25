package com.ailk.sets.platform.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IConfigSysParamDao;
import com.ailk.sets.platform.domain.ConfigSysParameters;
import com.ailk.sets.platform.exception.PFDaoException;

@Repository
public class ConfigSysParamDaoImpl extends BaseDaoImpl<ConfigSysParameters>
		implements IConfigSysParamDao {

	public ConfigSysParameters getConfigSysParameters(String name) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ConfigSysParameters.class);
		criteria.add(Restrictions.eq("paramEname", name));
		return (ConfigSysParameters) criteria.uniqueResult();
	}

	public ConfigSysParameters getConfigSysParametersByOpenSession(String name) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session
					.createCriteria(ConfigSysParameters.class);
			criteria.add(Restrictions.eq("paramEname", name));
			return (ConfigSysParameters) criteria.uniqueResult();
		} finally {
			session.close();
		}

	}

	public int getIntValueByName(String name, int defaultValue) {
		ConfigSysParameters param = getConfigSysParametersByOpenSession(name);
		if (param != null) {
			String value = param.getParamValue();
			if (StringUtils.isNotEmpty(value)) {
				int temp = Integer.valueOf(value);
				if (temp > 0) {
					return temp;
				}
			}
		}
		return defaultValue;
	}

	@Override
	public String getConfigParamValue(String name) throws PFDaoException {
		ConfigSysParameters csp = getConfigSysParameters(name);
		if (csp != null)
			return csp.getParamValue();
		else
			throw new PFDaoException(
					"can not find param value , param name is " + name);
	}

}
