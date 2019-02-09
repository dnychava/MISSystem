package com.ikaustubh.missystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ikaustubh.missystem.entities.ExpenditureEntity;
import com.ikaustubh.missystem.services.ExpenditureService;

public interface ExpenditureRepository extends JpaRepository<ExpenditureEntity, Long>, ExpenditureService {
	
}
