package com.campers.camfp.config.oauth.provider;

import java.util.Map;


public class GoogleUserInfo implements OAuth2UserInfo{

	private Map<String, Object> attributes;
	
	public GoogleUserInfo(Map<String, Object> attributes) {
		this.attributes=attributes;

	}
	
	@Override
	public String getEmail() {
		return (String)attributes.get("email");
	}

	@Override
	public String getName() {
		return (String)attributes.get("name");
	}

	@Override
	public String getNickname() {
		return (String)attributes.get("name");
	}

	@Override
	public String getProfileImg() {
		return (String)attributes.get("picture");
	}

	@Override
	public String getProviderId() {
		return (String)attributes.get("sub");
	}

	@Override
	public String getProvider() {
		return "google";
	}

	//@Override
	//public int getAge() {
		//return 0;
	//}

	@Override
	public String getPhone() {
		return null;
	}

//	@Override
//	public boolean getGender() {
//		return false;
//	}

}
