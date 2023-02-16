package com.campers.camfp.config.oauth.provider;

public interface OAuth2UserInfo {

	String getProviderId();
	String getProvider();
	String getEmail(); //id
	String getName(); //이름 
	String getNickname(); //닉네임인데 이름
	String getProfileImg();// 프로필이미지
	//boolean getGender();
	//int getAge();
	String getPhone();
	
}
