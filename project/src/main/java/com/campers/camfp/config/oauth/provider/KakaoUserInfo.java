package com.campers.camfp.config.oauth.provider;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo{

	private Map<String, Object> attributes;
	private Map<String, Object> email;
	private Object id;
	
	
	public KakaoUserInfo(Map<String, Object> attributes, Map<String, Object> email, Object id) {
		this.attributes=attributes;
		this.email=email;
		this.id=id;
	}
	
	@Override
	public String getProviderId() {
		return (String)id.toString();	
	}

	@Override
	public String getProvider() {
		return "kakao";
	}

	@Override
	public String getEmail() {
		return (String)email.get("email");
	}

	@Override
	public String getName() {
		return (String)attributes.get("nickname");
	}

	@Override
	public String getNickname() {
		return (String)attributes.get("nickname");
	}

	@Override
	public String getProfileImg() {
		return (String)attributes.get("profile_image");
	}

	@Override
	public String getPhone() {
		return null;
	}

}
