package com.thy.easycheck.model;

public class PlaneTag {
	
	public static final int TAG_TYPE_LIFEVEST = 0;
	public static final int TAG_TYPE_OXYGEN_MASK = 1;
	
	private String mTagId;
	private String mPnNumber;
	private String mSnNumber;
	private String mExpirationDate;
	private String mAircraftCode;
	private String mType;
	private String mEmployeeTagId;

	
	//Constructer
	public PlaneTag(String pnNumber, String snNumber, String expirationDate,
			String aircraftCode, String type, String employeeTagId) {
		super();
		this.mPnNumber = pnNumber;
		this.mSnNumber = snNumber;
		this.mExpirationDate = expirationDate;
		this.mAircraftCode = aircraftCode;
		this.mType = type;
		this.mEmployeeTagId = employeeTagId;
	}


	public PlaneTag(String tagId, String pnNumber, String snNumber,
			String expirationDate, String aircraftCode, String type,
			String employeeTagId) {
		super();
		this.mTagId = tagId;
		this.mPnNumber = pnNumber;
		this.mSnNumber = snNumber;
		this.mExpirationDate = expirationDate;
		this.mAircraftCode = aircraftCode;
		this.mType = type;
		this.mEmployeeTagId = employeeTagId;
	}


	//Getters
	public String getTagId() {
		return mTagId;
	}

	public String getPnNumber() {
		return mPnNumber;
	}

	public String getSnNumber() {
		return mSnNumber;
	}

	public String getExpirationDate() {
		return mExpirationDate;
	}

	public String getAircraftCode() {
		return mAircraftCode;
	}

	public String getType() {
		return mType;
	}

	public String getEmployeeTagId() {
		return mEmployeeTagId;
	}
	
	
	//Setters
	public void setTagId(String tagId) {
		this.mTagId = tagId;
	}
	
	public void setPnNumber(String pnNumber) {
		this.mPnNumber = pnNumber;
	}
	
	public void setSnNumber(String snNumber) {
		this.mSnNumber = snNumber;
	}
	
	public void setExpirationDate(String expirationDate) {
		this.mExpirationDate = expirationDate;
	}
	
	public void setAircraftCode(String aircraftCode) {
		this.mAircraftCode = aircraftCode;
	}

	public void setType(String type) {
		this.mType = type;
	}
	
	public void setEmployeeTagId(String employeeTagId) {
		this.mEmployeeTagId = employeeTagId;
	}
	
	
	
	
	
	
	

}
