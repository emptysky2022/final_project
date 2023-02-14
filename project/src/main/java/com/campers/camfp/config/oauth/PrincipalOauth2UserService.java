package com.campers.camfp.config.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.campers.camfp.config.auth.PrincipalDetails;
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
		
		String provider =userRequest.getClientRegistration().getClientId(); //google
		String nickname = oauth2User.getAttribute("name");
		String id = oauth2User.getAttribute("email");
		String profileImg=oauth2User.getAttribute("picture");
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
					.name(nickname)
					.build();
			memberRepository.save(memberEntity);
		}
		return new PrincipalDetails(memberEntity, oauth2User.getAttributes());
	}
}
