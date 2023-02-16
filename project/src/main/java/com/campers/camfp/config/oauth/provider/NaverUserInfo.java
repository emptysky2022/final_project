package com.campers.camfp.config.oauth.provider;

import java.util.Map;


public class NaverUserInfo implements OAuth2UserInfo{

	private Map<String, Object> attributes;
	
	public NaverUserInfo(Map<String, Object> attributes) {
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
		return (String)attributes.get("nickname");
	}
	
	
	public boolean getGender() {
	return (boolean)attributes.get("gender");
	}
	
	public String getPhone() {
		return (String)attributes.get("mobile");
	}

	@Override
	public String getProfileImg() {
		return (String)attributes.get("profile_image");
	}

	@Override
	public String getProviderId() {
		return (String)attributes.get("id");
	}

	@Override
	public String getProvider() {
		return "naver";
	}


}
