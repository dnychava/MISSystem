package com.ikaustubh.missystem.services.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ikaustubh.missystem.dao.MainSubActivityDao;
import com.ikaustubh.missystem.entities.ActivityEntity;
import com.ikaustubh.missystem.services.MainSubActivityService;

@Service
@Transactional(isolation=Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, readOnly=true)
public class MainSubActivityServiceImpl implements MainSubActivityService {

	@Autowired
	private MainSubActivityDao mainSubActivityDao;
	
	@Transactional(isolation=Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, readOnly=true)
	@Override
	public List<ActivityEntity> findByRidsAndActivityName(Set<String> newCodes, Set<String> activityNames) {
		
		return mainSubActivityDao.findByRidsAndActivityName(newCodes, activityNames);
	}
}
