package com.ikaustubh.missystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ikaustubh.missystem.entities.MainProgramHeadEntity;

/**
 * @author Dnyaneshwar Chavan
 * @since 07-Oct-2018
 *
 */
@Repository("mainProgramHeadRepository")
public interface MainProgramHeadRepository extends JpaRepository<MainProgramHeadEntity, Long>{
	

	
}
