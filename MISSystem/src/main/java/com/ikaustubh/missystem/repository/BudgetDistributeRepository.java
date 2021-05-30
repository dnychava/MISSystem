package com.ikaustubh.missystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ikaustubh.missystem.dao.BudgetDistributeDao;
import com.ikaustubh.missystem.entities.BudgetDistributeEntity;

/**
 * 
 * @author Dnyaneshwar
 * @since 7-Nov-2018
 */
@Repository
public interface BudgetDistributeRepository
		extends JpaRepository<BudgetDistributeEntity, Long>, BudgetDistributeDao {

}
