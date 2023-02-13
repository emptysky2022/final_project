package com.campers.camfp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.campers.camfp.dto.member.MemberDTO;
import com.campers.camfp.service.member.MemberService;

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
	
	@GetMapping({"/signup"})
	public void index1() {
		
	}
	
	@GetMapping("/modifymember")
	public void modify(String id, Model model) {
		
		log.info("id : " + id);
		
		MemberDTO memberDTO = memberService.get(id);
		model.addAttribute("memberDTO", memberDTO);
	}
	
	// 회원가입 후 login 화면으로 넘어갈거야
	@PostMapping("/login")
	public String register(MemberDTO memberDTO) {
		
		log.info("memberDTO : " + memberDTO);
		
		Long mno = memberService.register(memberDTO);
		
		return "redirect:/sample/login";
		
	}
	
	@PostMapping("/readmember")
	public void get(String id, Model model) {
		
		log.info("id : " + id);
		
		MemberDTO memberDTO = memberService.get(id);
		model.addAttribute("memberDTO", memberDTO);
		log.info("memberDTO : " + memberDTO);
		
	}
	
	// 수정
	@PostMapping("/modifymember")
	public String modify(MemberDTO memberDTO, RedirectAttributes redirectAttributes) {
		
		log.info("post modify------------------");
		log.info("memberDTO : " + memberDTO);
		
		memberService.modify(memberDTO);
		
		redirectAttributes.addAttribute("id", memberDTO.getId());
		
		return "redirect:/sample/readmember";
	}
	
}