package com.campers.camfp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.campers.camfp.config.auth.PrincipalDetails;
import com.campers.camfp.config.validator.CheckIdValidator;
import com.campers.camfp.config.validator.CheckNicknameValidator;
import com.campers.camfp.dto.member.MemberDTO;
import com.campers.camfp.entity.member.Member;
import com.campers.camfp.repository.member.MemberRepository;
import com.campers.camfp.service.member.MemberService;
@Controller //view를 리턴하겟다
public class IndexController {
	
	//중복체크 유효성 검사
	private CheckIdValidator checkIdValidator;
	private CheckNicknameValidator checkNicknameValidator ;
	//커스텀 유효성 검증
	@InitBinder
	public void validatorBinder(WebDataBinder binder) {
		binder.addValidators(checkIdValidator);
		binder.addValidators(checkNicknameValidator);
	}
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	MemberService memberService;
	
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
	public @ResponseBody String member(@AuthenticationPrincipal PrincipalDetails principalDetails) {//이게 사용자 정보 들고있음
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
	
	@Secured("ROLE_MEMBER")
	@GetMapping("/readmember")
	public String readMember(@AuthenticationPrincipal PrincipalDetails principalDetails,Model model) {
		model.addAttribute("member",principalDetails.getMember());
		System.out.println("introduce : "+principalDetails.getMember().getIntroduce());
		return "readmember";
	}
	
	@Secured("ROLE_MEMBER")
	@GetMapping("/modifymember")
	public String modify(@AuthenticationPrincipal PrincipalDetails principalDetails,Model model,@ModelAttribute MemberDTO memberDTO) {
		model.addAttribute("member",principalDetails.getMember());
		System.out.println("사용자 정보 : "+principalDetails.getMember());
		
		return "modifymember";
	}
	
	@PostMapping("/modifyProc")
	public String modifyProc(@ModelAttribute MemberDTO memberDTO,@AuthenticationPrincipal PrincipalDetails principalDetails){//@AuthenticationPrincipal PrincipalDetails principalDetails) {
		System.out.println("member는? " +memberDTO);
		System.out.println("member의 주소" + memberDTO.getIntroduce());
		memberService.modify(memberDTO,principalDetails.getMember());
		
		// 세션 등록
				// 1. config에 Bean등록한 AuthenticationManager를 DI한 후 새로운 토큰을 발행해서 authentication 객체를 만든다.
				//Authentication authentication = authenticate(new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword()));
				// 2. 새로 생성한 authentication 객체를 SecurityContextHolder안에 SecurityContext안에 set한다.
				//SecurityContextHolder.getContext().setAuthentication(authentication);
        return "redirect:/login";
				//return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
			
//		memberDTO = memberDTO.builder()
//							.grade("ROLE_MEMBER")
//							.address(memberDTO.getAddress())
//							.age(memberDTO.getAge())
//							.gender(memberDTO.getGender())
//							.introduce(memberDTO.getIntroduce())
//							.name(memberDTO.getName())
//							.nickname(memberDTO.getNickname())
//							//.id(principalDetails.getMember().getId())
//							.phone(memberDTO.getPhone())
//							//.profileImg(principalDetails.getMember().getProfileImg())
//							.pw("dyddnsdlsms tlsdlek")
//							.build();
		//memberRepository.save(memberDTO);
		//principalDetails.setMember(member);
//		System.out.println("member는? " +memberDTO);
//		return "redirect:/";
//	}

//	@GetMapping("/signupProc")
//	public String signupForm(Model model) {
//		model.addAttribute("MemberDTO", new MemberDTO());
//		return "signup";
	}

	@GetMapping("/signup")
	public String signup(MemberDTO memberDTO) {
		return "signup";
	}
	
	@PostMapping("/signup")
	public String signupProc(@Valid @ModelAttribute MemberDTO memberDTO,BindingResult result, RedirectAttributes attributes,Model model) {
		
		
		if(result.hasErrors()) {
			//회원가입 실패시 입력값 유지
			model.addAttribute("memberDTO" ,memberDTO);
			
			//유효성 검사를 통과하지 못 한 필드와 메세지 핸들링
			Map<String, String> errorMap= new HashMap<>();
			
			for(FieldError error : result.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
				System.out.println("회원가입 실패 ! error message : "+error.getDefaultMessage());
			}
			//model에 담아 view resolve
			for(String key : errorMap.keySet()) {
				model.addAttribute(key, errorMap.get(key));
			}
			
			return "redirect:/signup";
			
		}
		memberService.signup(memberDTO);
		System.out.println("회원가입 성공!");
		return "redirect:/login"; 
	}
	
	@PostMapping("/idCheck")
	@ResponseBody
	public int idCheck(@RequestParam("id") String id) {
		int cnt = memberService.idCheck(id);
		
		return cnt;
		
	}
	
	@PostMapping("/nickCheck")
	@ResponseBody
	public int nickCheck(@RequestParam("nickname") String nickname) {
		int cnt = memberService.nickCheck(nickname);
		
		return cnt;
	}
	@Secured("ROLE_MEMBER")
	@GetMapping("/memberDelete")
	public String deleteMember(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		System.out.println("getID : "+principalDetails.getMember().getMno());
		memberService.deleteByMno(principalDetails.getMember().getMno());
		
		return "redirect:/logout";
	}
	
	@Secured("ROLE_MEMBER")
	@GetMapping("/info")
	public @ResponseBody String info() {
		return "개인정보";
	}
	
	
//	@GetMapping("/sample/read")
//	public void readMember(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
//		Member member = principalDetails.getMember();
//		MemberDTO memberDTO = MemberDTO.builder()
//				.mno(member.getMno())
//				.profileImg(member.getProfileImg())
//				.name(member.getName())
//				.nickname(member.getNickname())
//				.age(member.getAge())
//				.pw(member.getPw())
//				.build();
//		
//		model.addAttribute("memberDTO", memberDTO);
//	}
}
