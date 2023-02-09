package com.campers.camfp.service;

import com.campers.camfp.dto.MemberDTO;
import com.campers.camfp.entity.Member;

public interface MemberService {

	String register(MemberDTO memberDTO);
	
	MemberDTO get(String id);
	
	void remove(String id);
	
	void modify(MemberDTO memberDTO);
	
	default Member dtoToEntity(MemberDTO memberDTO) {
		Member member = Member.builder().id(memberDTO.getId())
										.pass(memberDTO.getPass())
										.nickname(memberDTO.getNickname())
										.image(memberDTO.getImage())
										.name(memberDTO.getName())
										.age(memberDTO.getAge())
										.gender(memberDTO.getGender())
										.address(memberDTO.getAddress())
										.phone(memberDTO.getPhone())
										.grade(memberDTO.getGrade())
										.introduce(memberDTO.getIntroduce())
										.build();
		
		return member;
	}
	
	default MemberDTO entityToDTO(Member member) {
		MemberDTO memberDTO = MemberDTO.builder().id(member.getId())
												 .pass(member.getPass())
												 .nickname(member.getNickname())
												 .image(member.getImage())
												 .name(member.getName())
												 .age(member.getAge())
												 .gender(member.getGender())
												 .address(member.getAddress())
												 .phone(member.getPhone())
												 .grade(member.getGrade())
												 .introduce(member.getIntroduce())
												 .build();
		
		return memberDTO;
	}
	
	
	
}
