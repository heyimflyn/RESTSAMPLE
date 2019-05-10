package com.ibm.training.bootcamp.rest.sample01.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import com.ibm.training.bootcamp.rest.sample01.dao.DeveloperDao;
import com.ibm.training.bootcamp.rest.sample01.dao.DeveloperJdbc;
import com.ibm.training.bootcamp.rest.sample01.domain.Developer;

public class DeveloperServiceImpl implements DeveloperService{

	DeveloperDao devdao;
	
	public DeveloperServiceImpl() {
		this.devdao = DeveloperJdbc.getInstance();
	}

	@Override
	public List<Developer> findAll() {
		return devdao.findAll();
	}

	@Override
	public Developer find(long devID) {
	 return devdao.find(devID);
	}

	@Override
	public List<Developer> findByName(String firstname, String lastname) {
		return devdao.findByName(firstname, lastname);
	}

	@Override
	public boolean addDeveloper(Developer dev) {
		boolean adddev = false;
		
		if(validate(dev)) {
			devdao.addDeveloper(dev);
		    
			adddev=true;
		}else {
			throw new IllegalArgumentException("Fields cannot be blank");
			
		}return adddev;
	}

	private boolean validate(Developer dev) {
		return !StringUtils.isAnyBlank(dev.getFirstname(), dev.getLastname());
	 }

	@Override
	public void delDeveloper(long devID) {
		devdao.delDeveloper(devID);
	}
	
}
