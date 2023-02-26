package com.campers.camfp.service.member;

import com.campers.camfp.dto.member.MemberDTO;
import com.campers.camfp.entity.member.Member;

public interface MemberService {
	
//	Long register(MemberDTO memberDTO);
//	
//	MemberDTO get(String mno);
//	
//	void remove(Long mno);
//	
	//id, 닉네임 중복검사
	public int idCheck(String id);
	public int nickCheck(String nickname);
	void  deleteByMno(Long mno);
	
	//회원가입
	void signup(MemberDTO memberDTO);
	//회원정보수정
	public void modify(MemberDTO memberDTO, Member member);
	
	
	default Member dtoToEntity(MemberDTO memberDTO) {
		Member member = Member.builder().mno(memberDTO.getMno())
										.id(memberDTO.getId())
										.pw(memberDTO.getPw())
										.nickname(memberDTO.getNickname())
										.profileImg(memberDTO.getProfileImg())
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
		MemberDTO memberDTO = MemberDTO.builder().mno(member.getMno())
												 .id(member.getId())
												 .pw(member.getPw())
												 .nickname(member.getNickname())
												 .profileImg(member.getProfileImg())
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
