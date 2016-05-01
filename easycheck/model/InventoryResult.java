package com.thy.easycheck.model;

import org.joda.time.DateTime;

public class InventoryResult {
	
	private String mAircraftCode;
	private DateTime mDateTime;
	private String mEmployeeTagId;
	private int mTotalSeatCount;
	private int mExpiredCount;
	private int mExpireSoonCount;
	private int mTagFoundCount;
	
	
	//Constructor
	public InventoryResult(String aircraftCode, DateTime dateTime,
			String employeeTagId, int totalSeatCount, int expiredCount,
			int expireSoonCount, int tagFoundCount) {
		super();
		this.mAircraftCode = aircraftCode;
		this.mDateTime = dateTime;
		this.mEmployeeTagId = employeeTagId;
		this.mTotalSeatCount = totalSeatCount;
		this.mExpiredCount = expiredCount;
		this.mExpireSoonCount = expireSoonCount;
		this.mTagFoundCount = tagFoundCount;
	}


	//Getters
	public String getAircraftCode() {
		return mAircraftCode;
	}
	
	public DateTime getDateTime() {
		return mDateTime;
	}

	public String getEmployeeTagId() {
		return mEmployeeTagId;
	}

	public int getTotalSeatCount() {
		return mTotalSeatCount;
	}

	public int getExpiredCount() {
		return mExpiredCount;
	}

	public int getExpireSoonCount() {
		return mExpireSoonCount;
	}

	public int getTagFoundCount() {
		return mTagFoundCount;
	}

	
	//Setters
	public void setAircraftCode(String aircraftCode) {
		this.mAircraftCode = aircraftCode;
	}
	
	public void setDateTime(DateTime dateTime) {
		this.mDateTime = dateTime;
	}
	
	public void setEmployeeTagId(String employeeTagId) {
		this.mEmployeeTagId = employeeTagId;
	}
	
	public void setTotalSeatCount(int totalSeatCount) {
		this.mTotalSeatCount = totalSeatCount;
	}
	
	public void setExpiredCount(int expiredCount) {
		this.mExpiredCount = expiredCount;
	}
	
	public void setExpireSoonCount(int expireSoonCount) {
		this.mExpireSoonCount = expireSoonCount;
	}
	
	public void setTagFoundCount(int tagFoundCount) {
		this.mTagFoundCount = tagFoundCount;
	}
	
	
	
	
	
	
	
	

}
