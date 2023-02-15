package com.campers.camfp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.campers.camfp.config.oauth.PrincipalOauth2UserService;

//1. 코드받음(인증) 2. 엑세스토큰(권한) 3. 사용자정보 가져옴 4. 그 정보를 토대로 자동으로 회원가입진행(이메일, 전화번호, 이름, 아이디, 집주소)를 추가로 받아서 회원가입시켜야됨


@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록이됨
//secured 어노테이션 활성화 = @Secured("ROLE_MEMBER")를 걸면 멤버만 사용할수 있게 바뀜(댓글기능사용하면됨)
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) //preAuthorize 어노테이션 활성화 (secured는 한개 이거는 여러개사용하고싶을떄 사용)
public class SecurityConfig extends WebSecurityConfigurerAdapter{ //이 필터가 기본 필터에 등록이됨

	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;
	
	//해당 메서드의 리턴되는 오브젝트를 IoC로 등록
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/member/**").authenticated() // /member는 로그인이 되어야지만 접근가능함 댓글같은 곳에 달면 될듯합니다.
			//.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
			//.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')")
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll()//그 외에는 전부 허용
		.and() //user나 admin으로 가면 로그인페이지로 자동으로 이동됨
			.formLogin() 
			.loginPage("/sample/login")
//			.usernameParameter("name") //이거로 username변경 
			.loginProcessingUrl("/sample/login")// /login 주소가 호출이되면 시큐리티가 스스로 대신 로그인을 진행해줌
			.defaultSuccessUrl("/")//로그인이 완료되면 메인페이지로감
		.and()
			.oauth2Login()
			.loginPage("/sample/login")
			.userInfoEndpoint()
			.userService(principalOauth2UserService);//구글 로그인이 완료되고 후처리가 필요함 
}

}
