package com.ikaustubh.missystem.dao;

import java.util.Set;

import com.ikaustubh.missystem.entities.BudgetDistributeEntity;

public interface BudgetDistributeDao {
	
	public Set<BudgetDistributeEntity> findByUnitRidAndSubActivityRids(long unitRid, String year, Set<Long> subActivityRids );

}
