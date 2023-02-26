package com.campers.camfp.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.campers.camfp.entity.member.Member;
import com.campers.camfp.repository.member.MemberRepository;


//시큐리티 설정에서 loginProcessingUrl("/login");
// /login요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어있는 loadUserByUsername함수가 실행

@Service
public class PrincipalDetailsService implements UserDetailsService{

	@Autowired
	private MemberRepository memberRepository;
	
	//username이 로그인의 name의 username과 같아야됨 바꾸려면 SecurityConfig에서 바꺼야됨
	//시큐리티 session = Authentication(내부에 UserDetails가 들어가고)
	//시큐리티 session(Authentication(내부에 UserDetails가 들어가고))내부에 들어감
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findById(username);
		if(member !=null) {
			return new PrincipalDetails(member);
		}
		
		return null; 
	}

}
