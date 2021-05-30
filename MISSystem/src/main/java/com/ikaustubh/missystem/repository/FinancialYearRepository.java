package com.ikaustubh.missystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ikaustubh.missystem.entities.FinancialYearEntity;

/**
 * 
 * @author Dnyaneshwar
 * @since 7-Nov-2018
 */
@Repository
public interface FinancialYearRepository
		extends JpaRepository<FinancialYearEntity, Long> {

}
