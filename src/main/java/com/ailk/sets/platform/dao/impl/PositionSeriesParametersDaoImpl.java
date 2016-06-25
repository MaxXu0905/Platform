package com.ailk.sets.platform.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ailk.sets.platform.domain.PositionSeriesParameters;
import com.ailk.sets.platform.domain.PositionSeriesParametersId;

@Repository
public class PositionSeriesParametersDaoImpl {
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	public PositionSeriesParameters getPositionSeriesParameters(PositionSeriesParametersId id){
		Session session = sessionFactory.getCurrentSession();
		return (PositionSeriesParameters)session.get(PositionSeriesParameters.class, id);
	}
}
