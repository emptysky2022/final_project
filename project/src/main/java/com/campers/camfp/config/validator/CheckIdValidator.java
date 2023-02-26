package com.campers.camfp.config.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.campers.camfp.dto.member.MemberDTO;
import com.campers.camfp.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CheckIdValidator extends AbstractValidator<MemberDTO>{
	
	private final MemberRepository memberRepository;
	
	@Override
	protected void doValidate(MemberDTO memberDTO, Errors errors) {
//		if(memberRepository.existsById(memberDTO.getId())) {
//			//중복인경우 
			errors.rejectValue("id","아이디 중복 오류", "이미 사용 중인 아이디입니다.");
		}
	}

//}
