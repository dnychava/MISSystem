package com.ikaustubh.missystem.services.impl;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ikaustubh.missystem.entities.BudgetDistributeEntity;
import com.ikaustubh.missystem.services.BudgetDistributeService;

@Repository
@Transactional(readOnly = true)
public class BudgetDistributeServiceImpl implements BudgetDistributeService {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public Set<BudgetDistributeEntity> findByUnitRidAndSubActivityRids(long unitRid, String year,
			Set<Long> subActivityRids) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(BudgetDistributeEntity.class);
		criteria.add(Restrictions.eq("unitEntity.rid", unitRid));
		criteria.add(Restrictions.in("mainSubActivityEntity.rid", subActivityRids));
		criteria.add(Restrictions.eq("year", year));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		@SuppressWarnings("unchecked")
		Set<BudgetDistributeEntity> returnVal = new HashSet<BudgetDistributeEntity>(criteria.list());
		return returnVal;
	}
}
