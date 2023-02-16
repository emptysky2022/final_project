package com.campers.camfp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campers.camfp.config.auth.PrincipalDetails;
import com.campers.camfp.entity.member.Member;
import com.campers.camfp.repository.member.MemberRepository;
@Controller //view를 리턴하겟다
public class IndexController {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/test/login")
	public @ResponseBody String testLogin(Authentication authentication, @AuthenticationPrincipal PrincipalDetails userDetails) {
	System.out.println("text/login ==================");
	PrincipalDetails principalDetails =(PrincipalDetails) authentication.getPrincipal();
	System.out.println("authentication : "+principalDetails.getMember());
	
	System.out.println("userDetails : "+userDetails.getUsername());
	
	return "세션 정보 확인하기";
	}
	
	@GetMapping("/test/oauth/login")
	public @ResponseBody String testOAuthLogin(Authentication authentication, @AuthenticationPrincipal OAuth2User oauth) {
	System.out.println("text/oauth/login ==================");
	OAuth2User oauth2User =(OAuth2User) authentication.getPrincipal();
	System.out.println("authentication : "+oauth2User.getAttributes());
	System.out.println("oauth2User : "+oauth.getAttributes());
	
	return "OAuth세션 정보 확인하기";
	}
	
	@GetMapping({"","/"})
	public  String index() {
		return "index";
	}
	
	@GetMapping("/member")
	public @ResponseBody String member(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		System.out.println("principalDetails : "+ principalDetails.getMember());
		return "member";
	}
	
	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin";
	}
	
	@GetMapping("/customer")
	public @ResponseBody String customer() {
		return "customer";
	}
	
	//시큐리티가 주소를 가져감-SecurityConfig파일 생성후 작동안함
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}
	
	@PostMapping("/sample/signupProc")
	public String signupProc(Member member) {
		System.out.println(member);
		member.setGrade("ROLE_MEMBER"); 
		String rawPassword=member.getPw();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		member.setPw(encPassword);
		memberRepository.save(member);
		return "redirect:/"; 
	}
	
	@Secured("ROLE_MEMBER")
	@GetMapping("/info")
	public @ResponseBody String info() {
		return "개인정보";
	}
	
	
}
