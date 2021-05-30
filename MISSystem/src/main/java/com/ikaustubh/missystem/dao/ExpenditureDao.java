package com.ikaustubh.missystem.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ikaustubh.missystem.dto.FMRReportFilterDTO;
import com.ikaustubh.missystem.entities.ActivityEntity;
import com.ikaustubh.missystem.entities.BudgetDistributeEntity;
import com.ikaustubh.missystem.entities.ExpenditureEntity;
import com.ikaustubh.missystem.wrapper.ExpenditureWrapper;

public interface ExpenditureDao {

	public Set<ExpenditureEntity> findByUnitRidAndSubActivityRids(long unitRid, Set<Long> subActivityRids);

	public Set<ExpenditureEntity> findByUnitRidAndSubActivityRids(long unitRid, String year, Set<Long> subActivityRids);

	public Set<ExpenditureEntity> findByUnitRidAndSubActivityRids(long unitRid, String year,
			LocalDateTime reportingMonth, Set<Long> subActivityRids);

	public Map<Long, ExpenditureWrapper> getPrograssiveAmtAndBudget(long unitRid, String year, Set<Long> subActivityRids,
			boolean isFeatchBudgetData);
	
	public Map<Long, ExpenditureWrapper> getPrograssiveAmtAndBudget(long unitRid, String year, Set<Long> subActivityRids,
			boolean isFeatchBudgetData, int reportingMonth);
	
	public ExpenditureEntity findByUnitRidAndSubActivityRids(long unitRid, String year,
			long subActivityRid);
	
	public List<BudgetDistributeEntity> findBudgetList(String unitName, String year);
	
	public void saveOrUpdate(ExpenditureEntity entity);
	
	/**
	 * 
	 * @param mainHeadPrmRid
	 * @param financialYear
	 * @return
	 * 
	 */
	public List<ActivityEntity> getAllParentActivity(long mainHeadPrmRid, String financialYear);
	
	/**
	 * 
	 * @param fmrReportFilterDTO
	 * @param activityRidList
	 * @return
	 */
	public List<ExpenditureEntity> getAllExpenditureUsingFilter(FMRReportFilterDTO fmrReportFilterDTO, List<Long> activityRidList);
	
	/**
	 * 
	 * @param fmrReportFilterDTO
	 * @param activityRidList
	 * @return
	 */
	public List<BudgetDistributeEntity> getAllBudgetSharedUsingFilter(FMRReportFilterDTO fmrReportFilterDTO, List<Long> activityRidList);

}
