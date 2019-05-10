package com.ibm.training.bootcamp.rest.sample01.domain;

import java.sql.Date;

public class Developer {
	
    Long  devID;
	private String firstname;
	private String middlename;
	private String lastname;
	private Date  birthdate;
	private String position;
	

    
	public Developer(Long devID, String firstname, String middlename, String lastname, Date birthdate,
			String position) {
		this.devID = devID;
		this.firstname = firstname;
		this.middlename = middlename;
		this.lastname = lastname;
		this.birthdate = birthdate;
		this.position = position;
	}

	public Developer(Long devID, String firstname, String lastname) {
		this.devID = devID;
		this.firstname = firstname;
		this.lastname = lastname;
	}
	
	public Long getDevID() {
		return devID;
	}


	public void setDevID(long devID) {
		this.devID = devID;
	}


	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
    

}
