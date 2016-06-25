package com.ailk.sets.platform.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IConfigDao;
import com.ailk.sets.platform.intf.empl.domain.ConfigCodeName;

/**
 * IConfigDao接口实现类
 * 
 * @author 毕希研
 * 
 */
@Repository
public class ConfigDaoImpl extends BaseDaoImpl<ConfigCodeName> implements IConfigDao {

	@SuppressWarnings("unchecked")
	public List<ConfigCodeName> getConfigCode(String codeType) {
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq("id.codeType", codeType));
		criteria.addOrder(Order.asc("seq"));
		return criteria.list();
	}

	public String getConfigCodeName(String codeType, String codeId) {
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq("id.codeType", codeType));
		criteria.add(Restrictions.eq("id.codeId", codeId));
		ConfigCodeName ccn = (ConfigCodeName) criteria.uniqueResult();
		if(null != ccn)
		{
		    return ccn.getCodeName();
		}
		return "";
	}

	public ConfigCodeName getConfigCode(String codeType, String codeId) {
		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq("id.codeType", codeType));
		criteria.add(Restrictions.eq("id.codeId", codeId));
		ConfigCodeName ccn = (ConfigCodeName) criteria.uniqueResult();
		return ccn;
	}
	
	public Map<String, String> getConfigCodeMap(String codeType) {
		List<ConfigCodeName> list = getConfigCode(codeType);
		Map<String, String> map = new HashMap<String, String>();
		if (!CollectionUtils.isEmpty(list)) {
			for (ConfigCodeName ccn : list) {
				map.put(ccn.getId().getCodeId(), ccn.getCodeName());
			}
		}
		return map;
	}
}
