package com.ikaustubh.missystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ikaustubh.missystem.entities.ActivityEntity;

public interface ActivityRepo extends JpaRepository<ActivityEntity, Long> {
	
	//public List<ActivityEntity> findAllOrderByRidAsc();
}
