package com.campers.camfp.config.type;

public enum CampingType {
	COMMON("일반"),
	GLAMPING("글램핑"),
	CAR("자동차"),
	CARAVN("카라반");

	final private String name;
	
	public String getName() {
		return name;
	}
	
	private CampingType(String name) {
		this.name = name;
	}
	
	
}
