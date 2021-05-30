package com.ikaustubh.missystem.dao;

import java.util.List;
import java.util.Set;

import com.ikaustubh.missystem.entities.ActivityEntity;

public interface MainSubActivityDao {

	public List<ActivityEntity> findByRidsAndActivityName(Set<String> newCodes, Set<String> activityNames);

}
