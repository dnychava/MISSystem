package com.ikaustubh.missystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ikaustubh.missystem.entities.BudgetDistributeEntity;
import com.ikaustubh.missystem.services.BudgetDistributeService;

/**
 * 
 * @author Dnyaneshwar
 * @since 7-Nov-2018
 */
public interface BudgetDistributeRepository
		extends JpaRepository<BudgetDistributeEntity, Long>, BudgetDistributeService {

}
