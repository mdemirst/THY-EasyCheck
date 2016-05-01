package com.thy.easycheck.model;

public class ThyAircraft {

	private String mAircraftCode;
	private String mAircraftType;
	
	//Constructors
	public ThyAircraft(String aircraftCode, String aircraftType) {
		super();
		this.mAircraftCode = aircraftCode;
		this.mAircraftType = aircraftType;
	}


	
	//Getters
	public String getAircraftCode() {
		return mAircraftCode;
	}

	public String getAircraftType() {
		return mAircraftType;
	}

	
	//Setters
	public void setAircraftName(String aircraftCode) {
		this.mAircraftCode = aircraftCode;
	}
	
	public void setAircraftType(String aircraftType) {
		this.mAircraftType = aircraftType;
	}
	
	
	
	
	
	
}
