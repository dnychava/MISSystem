package com.ikaustubh.missystem.dao.impl;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.ikaustubh.missystem.dao.BudgetDistributeDao;
import com.ikaustubh.missystem.entities.BudgetDistributeEntity;

public class BudgetDistributeDaoImpl implements BudgetDistributeDao {

	@PersistenceContext
	private EntityManager entityManager;

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
