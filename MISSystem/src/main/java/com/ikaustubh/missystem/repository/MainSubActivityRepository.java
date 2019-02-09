package com.ikaustubh.missystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ikaustubh.missystem.entities.MainSubActivityEntity;
import com.ikaustubh.missystem.services.MainSubActivityService;

public interface MainSubActivityRepository extends JpaRepository<MainSubActivityEntity, Long>, MainSubActivityService {
}
