package com.ibm.training.bootcamp.rest.sample01.dao;

import java.util.List;
import com.ibm.training.bootcamp.rest.sample01.domain.Developer;

public interface DeveloperDao {
	
	public List<Developer> findAll();
	
	public Developer find(Long devID);
	
	public List<Developer> findByName(String firstname, String lastname);
	
	public boolean addDeveloper(Developer dev);
	
	public boolean delDeveloper(Long devID);
}
