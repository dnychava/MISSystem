package com.ikaustubh.missystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ikaustubh.missystem.dao.ExpenditureDao;
import com.ikaustubh.missystem.entities.ExpenditureEntity;

public interface ExpenditureRepository extends JpaRepository<ExpenditureEntity, Long>, ExpenditureDao {
	
}
