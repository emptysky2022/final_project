package com.campers.camfp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.campers.camfp.dto.MemberDTO;
import com.campers.camfp.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/sample")
@Log4j2
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService; // Service 생성 필요
	
	// membership -> 짧은거로 바꾸길 희망하심!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//	@GetMapping("/")
//	public String index() {
//		return "redirect:/sample/membership";
//	}
	// login 화면으로 넘어가려면 GetMapping도 만들어줘야 함
	@GetMapping("/login")
	public void login() {
		
	}
	
	@GetMapping("/membership")
	public void index1() {
		
	}
	
	// 회원가입 후 login 화면으로 넘어갈거야
	@PostMapping("/login")
	public String register(MemberDTO memberDTO) {
		
		log.info("memberDTO : " + memberDTO);
		
		String id = memberService.register(memberDTO);
		
		return "redirect:/sample/login";
		
	}
	
	
}