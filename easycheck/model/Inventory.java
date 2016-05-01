package com.thy.easycheck.model;

import org.joda.time.DateTime;

public class Inventory {

	private String mAircraftCode;
	private DateTime mScanDate;
	private String mTagId;
	
	//Constructor
	public Inventory(String aircraftCode, DateTime scanDate, String tagId) {
		super();
		this.mAircraftCode = aircraftCode;
		this.mScanDate = scanDate;
		this.mTagId = tagId;
	}
	
	
	//Getters
	public String getAircraftCode() {
		return mAircraftCode;
	}

	public DateTime getScanDate() {
		return mScanDate;
	}

	public String getTagId() {
		return mTagId;
	}
	
	
	//Setters
	public void setAircraftCode(String aircraftCode) {
		this.mAircraftCode = aircraftCode;
	}
	
	public void setScanDate(DateTime scanDate) {
		this.mScanDate = scanDate;
	}
	
	public void setTagId(String tagId) {
		this.mTagId = tagId;
	}
}
