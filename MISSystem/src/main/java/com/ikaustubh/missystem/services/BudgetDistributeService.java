package com.ikaustubh.missystem.services;

import java.util.Set;

import com.ikaustubh.missystem.entities.BudgetDistributeEntity;

public interface BudgetDistributeService {
	
	public Set<BudgetDistributeEntity> findByUnitRidAndSubActivityRids(long unitRid, String year, Set<Long> subActivityRids );

}
