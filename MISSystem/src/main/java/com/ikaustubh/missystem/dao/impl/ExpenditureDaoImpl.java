package com.ikaustubh.missystem.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ikaustubh.missystem.dao.ExpenditureDao;
import com.ikaustubh.missystem.dto.FMRReportFilterDTO;
import com.ikaustubh.missystem.entities.ActivityEntity;
import com.ikaustubh.missystem.entities.BudgetDistributeEntity;
import com.ikaustubh.missystem.entities.ExpenditureEntity;
import com.ikaustubh.missystem.entities.MainProgramHeadEntity;
import com.ikaustubh.missystem.wrapper.ExpenditureWrapper;

@Repository
public class ExpenditureDaoImpl implements ExpenditureDao {

	Logger logger = LoggerFactory.getLogger(ExpenditureDaoImpl.class);
	
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
			returnValMap.put(budgetDistributeEntity.getActivityEntity().getRid(), expenditureWrapper);
		}
		return returnValMap;
	}

	@Override
	@SuppressWarnings("deprecation")
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

	public void getDivisionReportData(FMRReportFilterDTO fmrReportDTO ) {
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BudgetDistributeEntity> findBudgetList(String unitName, String year) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria1 = session.createCriteria(BudgetDistributeEntity.class);
		criteria1.add(Restrictions.eq("year", year));
		Criteria criteria2 = criteria1.createCriteria("unitEntity");
		criteria2.add(Restrictions.eq("name", unitName));
		return criteria1.list();
	}
	
	@Override
	public void saveOrUpdate(ExpenditureEntity entity) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActivityEntity> getAllParentActivity(long mainHeadPrmRid, String financialYear) {
		Session session = entityManager.unwrap(Session.class);
		/*Criteria criteria = session.createCriteria(MainProgramHeadEntity.class);
		criteria.add(Restrictions.eq("id", mainHeadPrmRid));
		criteria.add(Restrictions.eq("mainProgramHeadEntity.mainActivity.rid", (long) 0));
		Criteria fyCriteria = criteria.createCriteria("financialYearEntity");
		fyCriteria.add(Restrictions.eq("year", financialYear));
		
		List<MainProgramHeadEntity> mnPrgHaList=fyCriteria.list();
		if(mnPrgHaList.size() > 0) {
			return mnPrgHaList.get(0).getActivityEntities();
		}*/
		Criteria activityCA = session.createCriteria(ActivityEntity.class);
		activityCA.add(Restrictions.eq("mainActivity.rid", (long) 0));
		activityCA.add(Restrictions.eq("mainProgramHeadEntity.id", mainHeadPrmRid));
		Criteria mphCA = activityCA.createCriteria("mainProgramHeadEntity");
		Criteria fyCriteria = mphCA.createCriteria("financialYearEntity");
		fyCriteria.add(Restrictions.eq("year", financialYear));
		
		return fyCriteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExpenditureEntity> getAllExpenditureUsingFilter(FMRReportFilterDTO fmrReportFilterDTO,
			List<Long> activityRidList) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(ExpenditureEntity.class);
		criteria.add(Restrictions.eq("year", fmrReportFilterDTO.getYear()));
		criteria.add( Restrictions.eq("reportingMonth", fmrReportFilterDTO.getReportingMonth()));
		criteria.add(Restrictions.eq("unitEntity.rid", fmrReportFilterDTO.getUnitRid()));
		criteria.add(Restrictions.in("activityEntity.rid", activityRidList));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BudgetDistributeEntity> getAllBudgetSharedUsingFilter(FMRReportFilterDTO fmrReportFilterDTO,
			List<Long> activityRidList) {
		Session session = entityManager.unwrap(Session.class);
		Criteria budgetCriteria = session.createCriteria(BudgetDistributeEntity.class);
		budgetCriteria.add(Restrictions.eq("year", fmrReportFilterDTO.getYear()));
		budgetCriteria.add(Restrictions.eq("unitEntity.rid", fmrReportFilterDTO.getUnitRid()));
		budgetCriteria.add(Restrictions.in("activityEntity.rid", activityRidList));
		return budgetCriteria.list();
	}

}
