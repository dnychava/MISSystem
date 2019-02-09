/**
 * 
 */
package com.ikaustubh.missystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ikaustubh.missystem.entities.RecordStatusEntity;
import com.ikaustubh.missystem.entities.UserInfoEntity;

/**
 * @author Dnyaneshwar Chavan
 * @since 07-Oct-2018
 *
 */
@Repository("recordStatusRepository")
public interface RecordStatusRepository extends JpaRepository<RecordStatusEntity, Long> {
	RecordStatusEntity findByName(String name);
}
