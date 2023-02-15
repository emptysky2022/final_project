package com.campers.camfp.config.oauth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.campers.camfp.config.auth.PrincipalDetails;
import com.campers.camfp.config.oauth.provider.GoogleUserInfo;
import com.campers.camfp.config.oauth.provider.NaverUserInfo;
import com.campers.camfp.config.oauth.provider.OAuth2UserInfo;
import com.campers.camfp.entity.member.Member;
import com.campers.camfp.repository.member.MemberRepository;


//구글로 부터 받은 userRequest 데이터에 대한 후처리 함수
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{

	
	@Autowired
	private MemberRepository memberRepository;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("getClientRegistration : "+userRequest.getClientRegistration()); //RegistrationID로 어떤 OAuth로 로그인했는지 확인가능
		System.out.println("getAccessToken : "+userRequest.getAccessToken().getTokenValue());
		
		//구글의 회원 프로필 조회
		OAuth2User oauth2User= super.loadUser(userRequest);
		// 구글 로그인 버튼 클릭 -> 구글 로그인창 -> 로그인 완료 ->code를 리턴(OAuth-client라이브러리) -> AccessToken요청
		//userRequest정보 -> loadUser함수 호출-> 구글로부터 회원프로필을 받아줌
		System.out.println("getAttributes : "+oauth2User.getAttributes()); //이 정보만 있으면됨
		
		OAuth2UserInfo oAuth2UserInfo=null;
		if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			System.out.println("구글 로그인 요청");
			oAuth2UserInfo= new GoogleUserInfo(oauth2User.getAttributes());
		}else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")){
			System.out.println("네이버 로그인 요청");
			oAuth2UserInfo = new NaverUserInfo((Map)oauth2User.getAttributes().get("response"));
		}else{
			System.out.println("카카오 로그인 요청");
			//oAuth2UserInfo= new KakaoUserInfo(oauth2User.getAttributes());
		}
		
		String name=oAuth2UserInfo.getName();
		//boolean gender=oAuth2UserInfo.getGender();
		//int age=oAuth2UserInfo.getAge();
		String phone=oAuth2UserInfo.getPhone();
		String provider =oAuth2UserInfo.getProvider(); //google
		String nickname = oAuth2UserInfo.getNickname();
		String id = oAuth2UserInfo.getEmail();
		String profileImg=oAuth2UserInfo.getProfileImg();
		String pw ="dyd";
		String grade= "ROLE_MEMBER";
		
		
		Member memberEntity = memberRepository.findByName(nickname);
		
		if(memberEntity==null) {
			memberEntity= Member.builder()
					.nickname(nickname)
					.id(id)
					.grade(grade)
					.pw(pw)
					.profileImg(profileImg)
					.name(name)
					//.age(age)
					.phone(phone)
					//.gender(gender)
					.build();
			memberRepository.save(memberEntity);
		}
		return new PrincipalDetails(memberEntity, oauth2User.getAttributes());
	}
}
