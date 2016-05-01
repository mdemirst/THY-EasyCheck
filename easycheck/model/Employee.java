package com.thy.easycheck.model;

import org.joda.time.DateTime;

public class Employee {
	
	private String mEmployeeTagId;
	private String mLocation;
	private DateTime mDatetime;
	private String mUsername;
	private String mPassword;
 
	//Constructer
	public Employee(String employeeTagId, String location,
			DateTime datetime, String username, String password) {
		super();
		this.mEmployeeTagId = employeeTagId;
		this.mLocation = location;
		this.mDatetime = datetime;
		this.mUsername = username;
		this.mPassword = password;
	}
	
	//Getters
	public String getEmployeeTagId() {
		return mEmployeeTagId;
	}

	public String getLocation() {
		return mLocation;
	}

	public DateTime getDatetime() {
		return mDatetime;
	}

	public String getUsername() {
		return mUsername;
	}

	public String getPassword() {
		return mPassword;
	}
	
	
	//Setters
	public void setEmployeeTagId(String employeeTagId) {
		this.mEmployeeTagId = employeeTagId;
	}
	
	public void setLocation(String location) {
		this.mLocation = location;
	}
	
	public void setDatetime(DateTime datetime) {
		this.mDatetime = datetime;
	}
	
	public void setUsername(String username) {
		this.mUsername = username;
	}
	
	public void setPassword(String password) {
		this.mPassword = password;
	}
	
	
	

}
