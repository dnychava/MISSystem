package com.ikaustubh.missystem.services;

import java.util.List;
import java.util.Set;

import com.ikaustubh.missystem.entities.MainSubActivityEntity;

public interface MainSubActivityService {

	public List<MainSubActivityEntity> findByRidsAndActivityName(Set<String> newCodes, Set<String> activityNames);

}
