package com.ikaustubh.missystem.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ikaustubh.missystem.dto.FMRReportFilterDTO;
import com.ikaustubh.missystem.entities.ActivityEntity;
import com.ikaustubh.missystem.entities.BudgetDistributeEntity;
import com.ikaustubh.missystem.entities.ExpenditureEntity;
import com.ikaustubh.missystem.repository.ExpenditureRepository;
import com.ikaustubh.missystem.services.ExpenditureService;
import com.ikaustubh.missystem.wrapper.ExpenditureWrapper;

@Service
public class ExpenditureServiceImpl implements ExpenditureService {
	
	@Autowired
	private ExpenditureRepository expenditueRepo;

	@Override
	public Set<ExpenditureEntity> findByUnitRidAndSubActivityRids(long unitRid, Set<Long> subActivityRids) {
		return findByUnitRidAndSubActivityRids(unitRid, null, subActivityRids);
	}

	@Override
	public Set<ExpenditureEntity> findByUnitRidAndSubActivityRids(long unitRid, String year,
			Set<Long> subActivityRids) {
		return findByUnitRidAndSubActivityRids(unitRid, year, null, subActivityRids);
	}

	@Transactional(isolation=Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, readOnly=true)
	@Override
	public Set<ExpenditureEntity> findByUnitRidAndSubActivityRids(long unitRid, String year,
			LocalDateTime reportingMonth, Set<Long> subActivityRids) {
		return expenditueRepo.findByUnitRidAndSubActivityRids( unitRid, year, reportingMonth, subActivityRids);
	}

	@Override
	public Map<Long, ExpenditureWrapper> getPrograssiveAmtAndBudget(long unitRid, String year,
			Set<Long> subActivityRids, boolean isFeatchBudgetData) {
		return getPrograssiveAmtAndBudget(unitRid, year, subActivityRids, isFeatchBudgetData, -1);
	}

	@Transactional(isolation=Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, readOnly=true)
	@Override
	public Map<Long, ExpenditureWrapper> getPrograssiveAmtAndBudget(long unitRid, String year,
			Set<Long> subActivityRids, boolean isFeatchBudgetData, int reportingMonth) {
		return expenditueRepo.getPrograssiveAmtAndBudget(unitRid, year, subActivityRids, isFeatchBudgetData, reportingMonth);
	}

	@Transactional(isolation=Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, readOnly=true)
	@Override
	public ExpenditureEntity findByUnitRidAndSubActivityRids(long unitRid, String year, long subActivityRid) {
		return expenditueRepo.findByUnitRidAndSubActivityRids(unitRid, year, subActivityRid);
	}

	@Transactional(isolation=Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, readOnly=true)
	@Override
	public List<BudgetDistributeEntity> findBudgetList(String unitName, String year) {
		
		return expenditueRepo.findBudgetList(unitName, year);
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW, readOnly=false)
	public List<ExpenditureEntity> saveAll(List<ExpenditureEntity> entities){
		return expenditueRepo.saveAll(entities);
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW, readOnly=false)
	@Override
	public void saveOrUpdate(List<ExpenditureEntity> expenditureEntities) {
	    for (ExpenditureEntity expenditureEntity : expenditureEntities) {
			
		}
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW, readOnly=true)
	@Override
	public List<ActivityEntity> getAllParentActivity(long mainHeadPrmRid, String financialYear) {
		return expenditueRepo.getAllParentActivity(mainHeadPrmRid, financialYear);
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW, readOnly=true)
	@Override
	public List<ExpenditureEntity> getAllExpenditureUsingFilter(FMRReportFilterDTO fmrReportFilterDTO,
			List<Long> activityRidList) {
		return expenditueRepo.getAllExpenditureUsingFilter(fmrReportFilterDTO, activityRidList);
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW, readOnly=true)
	@Override
	public List<BudgetDistributeEntity> getAllBudgetSharedUsingFilter(FMRReportFilterDTO fmrReportFilterDTO,
			List<Long> activityRidList) {
		return expenditueRepo.getAllBudgetSharedUsingFilter(fmrReportFilterDTO, activityRidList);
	}

	
	
	
	
	

}
