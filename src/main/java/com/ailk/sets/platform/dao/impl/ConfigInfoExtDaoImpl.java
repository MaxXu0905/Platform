package com.ailk.sets.platform.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.dao.BaseDaoImpl;
import com.ailk.sets.platform.dao.interfaces.IConfigInfoExtDao;
import com.ailk.sets.platform.domain.PositionInfoExt;
import com.ailk.sets.platform.domain.PositionInfoExtId;
import com.ailk.sets.platform.intf.common.Constants;
import com.ailk.sets.platform.intf.empl.domain.ConfigInfoExt;

@Repository
public class ConfigInfoExtDaoImpl extends BaseDaoImpl<ConfigInfoExt> implements IConfigInfoExtDao {

	@SuppressWarnings("unchecked")
	public void saveInfo(int employerId,int positionId, Map<String, Integer> infoSeq)  {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(ConfigInfoExt.class);
		List<ConfigInfoExt> list = criteria.list();
		Query query = session.createQuery("delete PositionInfoExt where id.positionId = " + positionId + " and id.employerId = " + employerId);
		query.executeUpdate();
		for (ConfigInfoExt cie : list) {
			PositionInfoExt companyInfoExt = new PositionInfoExt();
			if (!infoSeq.containsKey(cie.getInfoId()))
				continue;
			// 保存对象
			try {
				BeanUtils.copyProperties(companyInfoExt, cie);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			companyInfoExt.setMandatory(1);
			PositionInfoExtId id = new PositionInfoExtId();
			id.setEmployerId(employerId);
			id.setInfoId(cie.getInfoId());
			id.setPositionId(positionId);
			companyInfoExt.setId(id);
//			if (infoSeq.get(cie.getInfoId()) != null)
			companyInfoExt.setSeq(infoSeq.get(cie.getInfoId()));
			session.save(companyInfoExt);
		}
	}

	@Override
	public List<ConfigInfoExt> getMandatoryInfoExt() {
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery("from ConfigInfoExt where isMandatory = 1 order by seq ");
		return q.list();
	}
}
