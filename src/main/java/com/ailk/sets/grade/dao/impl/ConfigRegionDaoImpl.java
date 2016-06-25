package com.ailk.sets.grade.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.ailk.sets.grade.dao.intf.IConfigRegionDao;
import com.ailk.sets.grade.intf.report.InterviewInfo.ConfigRegionInfo;
import com.ailk.sets.platform.intf.cand.domain.ConfigRegion;

@Repository
public class ConfigRegionDaoImpl implements IConfigRegionDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	@Cacheable(value = "configRegion")
	public List<ConfigRegionInfo> getConfigRegionInfos() throws Exception {
		Map<Integer, ConfigRegionInfo> regionMap = new HashMap<Integer, ConfigRegionInfo>();
		Session session = sessionFactory.getCurrentSession();

		List<ConfigRegion> configRegions = session.createQuery(
				"FROM ConfigRegion").list();
		for (ConfigRegion configRegion : configRegions) {
			int regionId = configRegion.getRegionId();
			int parentId = configRegion.getParentId();

			if (configRegion.getRegionLevel() == 2) {
				ConfigRegionInfo configRegionInfo = regionMap.get(regionId);
				if (configRegionInfo == null) {
					configRegionInfo = new ConfigRegionInfo();
					configRegionInfo
							.setChildren(new ArrayList<ConfigRegionInfo>());
					regionMap.put(regionId, configRegionInfo);
				}

				configRegionInfo.setRegionId(regionId);
				configRegionInfo.setRegionName(configRegion.getRegionName());
			} else {
				ConfigRegionInfo configRegionInfo = regionMap.get(parentId);
				if (configRegionInfo == null) {
					configRegionInfo = new ConfigRegionInfo();
					configRegionInfo
							.setChildren(new ArrayList<ConfigRegionInfo>());
					regionMap.put(parentId, configRegionInfo);
				}

				List<ConfigRegionInfo> children = configRegionInfo
						.getChildren();
				ConfigRegionInfo child = new ConfigRegionInfo();
				child.setRegionId(regionId);
				child.setRegionName(configRegion.getRegionName());
				children.add(child);
			}
		}

		List<ConfigRegionInfo> result = new ArrayList<ConfigRegionInfo>();
		for (ConfigRegionInfo configRegionInfo : regionMap.values()) {
			result.add(configRegionInfo);
		}

		return result;
	}

	@Override
	@CacheEvict(value = "configRegion", allEntries = true)
	public void evict() {
	}

}
