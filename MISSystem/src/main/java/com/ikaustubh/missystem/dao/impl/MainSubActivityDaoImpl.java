package com.ikaustubh.missystem.dao.impl;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ikaustubh.missystem.dao.MainSubActivityDao;
import com.ikaustubh.missystem.entities.ActivityEntity;
import com.ikaustubh.missystem.services.MainSubActivityService;

@Repository
@Transactional(readOnly = true)
public class MainSubActivityDaoImpl implements MainSubActivityDao {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	@SuppressWarnings("unchecked")
	public List<ActivityEntity> findByRidsAndActivityName(Set<String> newCodes, Set<String> activityNames) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(ActivityEntity.class);
		criteria.add(Restrictions.in("newCode", newCodes));
		criteria.add(Restrictions.in("name", activityNames));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		List<ActivityEntity> list = criteria.list();
		return list;
	}
}
