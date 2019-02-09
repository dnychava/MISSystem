package com.ikaustubh.missystem.services;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

import com.ikaustubh.missystem.entities.ExpenditureEntity;
import com.ikaustubh.missystem.wrapper.ExpenditureWrapper;

public interface ExpenditureService {

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

}
