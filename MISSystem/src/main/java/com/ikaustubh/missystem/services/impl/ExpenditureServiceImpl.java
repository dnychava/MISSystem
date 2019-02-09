package com.ikaustubh.missystem.services.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ikaustubh.missystem.entities.BudgetDistributeEntity;
import com.ikaustubh.missystem.entities.ExpenditureEntity;
import com.ikaustubh.missystem.services.ExpenditureService;
import com.ikaustubh.missystem.wrapper.ExpenditureWrapper;

@Repository
@Transactional
public class ExpenditureServiceImpl implements ExpenditureService {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public Set<ExpenditureEntity> findByUnitRidAndSubActivityRids(long unitRid, Set<Long> subActivityRids) {
		return findByUnitRidAndSubActivityRids(unitRid, null, subActivityRids);
	}

	@Override
	public Set<ExpenditureEntity> findByUnitRidAndSubActivityRids(long unitRid, String year,
			Set<Long> subActivityRids) {
		return findByUnitRidAndSubActivityRids(unitRid, year, null, subActivityRids);
	}

	@Override
	@SuppressWarnings("deprecation")
	@Transactional(readOnly = true)
	public Set<ExpenditureEntity> findByUnitRidAndSubActivityRids(long unitRid, String year,
			LocalDateTime reportingMonth, Set<Long> subActivityRids) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(ExpenditureEntity.class);
		criteria.add(Restrictions.eq("unitEntity.rid", unitRid));
		criteria.add(Restrictions.in("mainSubActivityEntity.rid", subActivityRids));
		if (year != null) {
			criteria.add(Restrictions.eq("year", year));
		}
		if (reportingMonth != null) {
			criteria.add(Restrictions.eq("reportingMonth", reportingMonth.getMonthValue()));
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		@SuppressWarnings("unchecked")
		Set<ExpenditureEntity> returnVal = new HashSet<ExpenditureEntity>(criteria.list());
		return returnVal;
	}

	@Override
	@Transactional(readOnly = true)
	public Map<Long, ExpenditureWrapper> getPrograssiveAmtAndBudget(long unitRid, String year,
			Set<Long> subActivityRids, boolean isFeatchBudgetData) {
		return getPrograssiveAmtAndBudget(unitRid, year, subActivityRids, isFeatchBudgetData, -1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Long, ExpenditureWrapper> getPrograssiveAmtAndBudget(long unitRid, String year,
			Set<Long> subActivityRids, boolean isFeatchBudgetData, int reportingMonth) {
		Map<Long, ExpenditureWrapper> returnValMap = new HashMap<Long, ExpenditureWrapper>();
		Map<Long, ExpenditureWrapper> budgetDataMap = null;
		if (isFeatchBudgetData) {
			budgetDataMap = getAllBudgetBySubActivities(unitRid, year, subActivityRids);
		}
		/*
		 * create a native query for calculate the PrograssiveAmt in Expenditure Table
		 */
		StringBuilder nativeQry = new StringBuilder();
		nativeQry.append(
				" select EXPENDITURE__MAIN_SUB_ACTIVITY_RID, sum(EXPENDITURE_TOT_AMT_IN_LAKH) as prograssiveAmtInLakh ");
		nativeQry.append(" from EXPENDITURE ");
		nativeQry.append(" where EXPENDITURE__UNIT_RID = :unitRid ");
		nativeQry.append(" and EXPENDITURE__MAIN_SUB_ACTIVITY_RID in( :subActivityRids ) ");
		nativeQry.append(" and EXPENDITURE_YEAR = :year ");
		if (reportingMonth > 0) {
			nativeQry.append(" and EXPENDITURE_REPORTING_MONTH between 04 and :month ");
		}
		nativeQry.append(" group by EXPENDITURE__MAIN_SUB_ACTIVITY_RID asc");

		Query query = entityManager.createNativeQuery(nativeQry.toString());
		query.setParameter("unitRid", unitRid);
		query.setParameter("subActivityRids", subActivityRids);
		query.setParameter("year", year);
		if (reportingMonth > 0) {
			query.setParameter("month", reportingMonth);
		}
		List<Object[]> resultList = query.getResultList();

		for (Object[] object : resultList) {
			ExpenditureWrapper expenditureWrapper = new ExpenditureWrapper();
			long subActivityRid = ((BigInteger) object[0]).longValue();
			BigDecimal prograssiveAmtInLack = (BigDecimal) object[1];
			if (isFeatchBudgetData) {
				expenditureWrapper.setBudgetAmtInLakh(budgetDataMap.get(subActivityRid).getBudgetAmtInLakh());
			}
			expenditureWrapper.setProgressiveAmtInLakh(prograssiveAmtInLack);
			returnValMap.put(subActivityRid, expenditureWrapper);
		}
		budgetDataMap = null;
		return returnValMap;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	private Map<Long, ExpenditureWrapper> getAllBudgetBySubActivities(long unitRid, String year,
			Set<Long> subActivityRids) {

		Map<Long, ExpenditureWrapper> returnValMap = new HashMap<Long, ExpenditureWrapper>();
		Session session = entityManager.unwrap(Session.class);
		Criteria criteriaBudget = session.createCriteria(BudgetDistributeEntity.class);
		criteriaBudget.add(Restrictions.eq("unitEntity.rid", unitRid));
		criteriaBudget.add(Restrictions.in("mainSubActivityEntity.rid", subActivityRids));
		criteriaBudget.add(Restrictions.eq("year", year));
		criteriaBudget.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<BudgetDistributeEntity> budgetDis = criteriaBudget.list();
		for (BudgetDistributeEntity budgetDistributeEntity : budgetDis) {
			ExpenditureWrapper expenditureWrapper = new ExpenditureWrapper();
			expenditureWrapper.setBudgetAmtInLakh(budgetDistributeEntity.getAmtInLakh());
			returnValMap.put(budgetDistributeEntity.getMainSubActivityEntity().getRid(), expenditureWrapper);
		}
		return returnValMap;
	}

	@Override
	@SuppressWarnings("deprecation")
	@Transactional(readOnly = true)
	public ExpenditureEntity findByUnitRidAndSubActivityRids(long unitRid, String year, long subActivityRid) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(ExpenditureEntity.class);
		criteria.add(Restrictions.eq("unitEntity.rid", unitRid));
		criteria.add(Restrictions.eq("mainSubActivityEntity.rid", subActivityRid));
		criteria.add(Restrictions.eq("year", year));

		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.property("rid"), "rid");
		projList.add(Projections.property("mainSubActivityEntity.rid"), "mainSubActivityEntity.rid");
		projList.add(Projections.sum("totAmtInLakh"), "totAmtInLakh");
		projList.add(Projections.groupProperty("mainSubActivityEntity.rid"), "mainSubActivityEntity.rid");
		projList.add(Projections.groupProperty("rid"), "rid");
		criteria.setProjection(projList);
		// criteria.setResultTransformer(Transformers.aliasToBean(ExpenditureEntity.class));
		List<Object[]> resultList = criteria.list();
		for (Object[] object : resultList) {
			System.out.println("0=" + object[0] + "1=" + object[1] + "=" + object[2]);
		}
		return null;
	}

}
