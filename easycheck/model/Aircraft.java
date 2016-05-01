package com.thy.easycheck.model;

public class Aircraft {
	
	private String mAircraftId;
	private String mAircraftDefinition;
	private String mImageLocation;
	
	
	//Constructors
	public Aircraft(String aircraftDefinition, String imageLocation) {
		super();
		this.mAircraftDefinition = aircraftDefinition;
		this.mImageLocation = imageLocation;
	}

	public Aircraft(String aircraftId, String aircraftDefinition,
			String imageLocation) {
		super();
		this.mAircraftId = aircraftId;
		this.mAircraftDefinition = aircraftDefinition;
		this.mImageLocation = imageLocation;
	}

	
	//Getters
	public String getAircraftId() {
		return mAircraftId;
	}

	public String getAircraftDefinition() {
		return mAircraftDefinition;
	}

	public String getImageLocation() {
		return mImageLocation;
	}
	
	
	//Setters
	public void setAircraftId(String aircraftId) {
		this.mAircraftId = aircraftId;
	}
	
	public void setAircraftDefinition(String aircraftDefinition) {
		this.mAircraftDefinition = aircraftDefinition;
	}

	public void setImageLocation(String imageLocation) {
		this.mImageLocation = imageLocation;
	}
	
	
	
	
	

}
