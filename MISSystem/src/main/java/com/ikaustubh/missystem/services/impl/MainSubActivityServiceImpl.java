package com.ikaustubh.missystem.services.impl;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ikaustubh.missystem.entities.MainSubActivityEntity;
import com.ikaustubh.missystem.services.MainSubActivityService;

@Repository
@Transactional(readOnly = true)
public class MainSubActivityServiceImpl implements MainSubActivityService {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	@SuppressWarnings("unchecked")
	public List<MainSubActivityEntity> findByRidsAndActivityName(Set<String> newCodes, Set<String> activityNames) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(MainSubActivityEntity.class);
		criteria.add(Restrictions.in("newCode", newCodes));
		criteria.add(Restrictions.in("name", activityNames));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		List<MainSubActivityEntity> list = criteria.list();
		return list;
	}
}
