package com.campers.camfp.config.oauth;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.campers.camfp.config.auth.PrincipalDetails;
import com.campers.camfp.config.oauth.provider.GoogleUserInfo;
import com.campers.camfp.config.oauth.provider.KakaoUserInfo;
import com.campers.camfp.config.oauth.provider.NaverUserInfo;
import com.campers.camfp.config.oauth.provider.OAuth2UserInfo;
import com.campers.camfp.entity.member.Member;
import com.campers.camfp.repository.member.MemberRepository;


//구글로 부터 받은 userRequest 데이터에 대한 후처리 함수
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{

	
	@Autowired
	private MemberRepository memberRepository;
	
	
	//userRequest는 code를 받아서 accessToken을 응답받은 객체
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User= super.loadUser(userRequest); //회원 프로필 조회
		System.out.println("getClientRegistration : "+userRequest.getClientRegistration()); //RegistrationID로 어떤 OAuth로 로그인했는지 확인가능
		System.out.println("getAccessToken : "+userRequest.getAccessToken().getTokenValue());
		
		// 구글 로그인 버튼 클릭 -> 구글 로그인창 -> 로그인 완료 ->code를 리턴(OAuth-client라이브러리) -> AccessToken요청
		//userRequest정보 -> loadUser함수 호출-> 구글로부터 회원프로필을 받아줌
		System.out.println("getAttributes : "+oAuth2User.getAttributes()); //이 정보만 있으면됨
		// code를 통해 구성한 정보
		System.out.println("userRequest clientRegistration : " + userRequest.getClientRegistration());
		// token을 통해 응답받은 회원정보
		System.out.println("oAuth2User : " + oAuth2User);
	
		return processOAuth2User(userRequest, oAuth2User);
	}
		
		
		private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
		
		OAuth2UserInfo oAuth2UserInfo=null;
		if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			System.out.println("구글 로그인 요청");
			oAuth2UserInfo= new GoogleUserInfo(oAuth2User.getAttributes());
		}else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")){
			System.out.println("네이버 로그인 요청");
			oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
		}else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")){
			System.out.println("카카오 로그인 요청");
			//Object  oAuth2UserInfo2 = oAuth2User.getAttributes().get("id");
			//System.out.println("asdfsadf"+oAuth2UserInfo2);
			oAuth2UserInfo = new KakaoUserInfo((Map) oAuth2User.getAttributes().get("properties"),(Map) oAuth2User.getAttributes().get("kakao_account"), oAuth2User.getAttributes().get("id")); //get("kakao_account")가 필요함 여기서 이메일을 뽑아낼 수 있음
		} else {
			System.out.println("다른 로그인은 지원하지 않습니다");
		}
		
		Optional<Member> memberOptional = 
				memberRepository.findByIdAndNickname(oAuth2UserInfo.getProvider()+"_"+oAuth2UserInfo.getEmail(), oAuth2UserInfo.getProvider()+"_"+oAuth2UserInfo.getProviderId());
		//이미 생성된 가입자인지 확인
		Member member = null;
		if (memberOptional.isPresent()) {
			member = memberOptional.get();
			// member가 존재하면 update 해주기
			//member.setNickname(oAuth2UserInfo.getNickname()); 이거하면 닉네임 변경됨
			memberRepository.save(member);
		} else {
			//여기로 추가정보 입력받아서 회원가입 진행
			member = Member.builder()
								.nickname(oAuth2UserInfo.getProvider()+"_"+oAuth2UserInfo.getProviderId())
								.id(oAuth2UserInfo.getProvider()+"_"+oAuth2UserInfo.getEmail())
								.grade("ROLE_MEMBER")
								.pw("dyd")
								.profileImg(oAuth2UserInfo.getProfileImg())
								.name(oAuth2UserInfo.getName())
								.age(0)
								.phone(oAuth2UserInfo.getPhone())
								.gender(null)
								.build();
			memberRepository.save(member);
		}
		
		return new PrincipalDetails(member, oAuth2User.getAttributes()); 
	}



}
