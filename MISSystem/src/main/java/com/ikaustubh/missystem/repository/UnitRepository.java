package com.ikaustubh.missystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ikaustubh.missystem.entities.BudgetDistributeEntity;
import com.ikaustubh.missystem.entities.UnitEntity;
import com.ikaustubh.missystem.services.BudgetDistributeService;

/**
 * 
 * @author Dnyaneshwar
 * @since 7-Nov-2018
 */
public interface UnitRepository extends JpaRepository<UnitEntity, Long> {

}
