package com.campers.camfp.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.campers.camfp.entity.member.Member;

import lombok.Data;

//시큐리티가 /login 주소 요청이 오면 가로채서 로그인을 진행해서 컨트롤러에 /login이 필요없음
//로그인 진행이 완료가 되면 시큐리티 session을 생성 (security contextHolder)
//오브젝트 타입 => Authentication 타입 객체
//Authentication 안에 User정보가 있어야됨.
//User 오브젝트 타입=> UserDetail타입 객체

//security Session =-> Authentication=>UserDetails(PrincipalDetails)
//2
@Data
public class PrincipalDetails implements UserDetails , OAuth2User{

	private Member member; //
	private Map<String, Object> attributes;
	
	//일반로그인
	public PrincipalDetails(Member member) {
		this.member = member;
	}
	
	//OAuth 로그인
	public PrincipalDetails(Member member,Map<String,Object> attributes) {
		this.member = member;
		this.attributes= attributes;
	}
	
	
	public Member getMember() {
		return member;
	}

	

	@Override
	public String getPassword() {
		
		return member.getPw();
	}

	@Override
	public String getUsername() {
		return member.getName();
	}

	//계정이 만료됬는지 확인 true가 만료안됨
	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	//계정이 잠겼는지 true가 잠기지않음
	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	//비밀번호 만료여부  true가 만료안됨
	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	//계정이 활성화 되었는지 
	@Override
	public boolean isEnabled() {

		return true;
	}
	
	//해당 유저의 권한을 리턴하는 곳
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
			
				return member.getGrade();
			}
		});
		
		return collect;
	}
	
	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	//member의 프라이머리키
	@Override
	public String getName() {
		return member.getId()+"";
	}
	


	
}
