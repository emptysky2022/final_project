package com.campers.camfp.service;

import org.springframework.stereotype.Service;

import com.campers.camfp.dto.MemberDTO;
import com.campers.camfp.entity.Member;
import com.campers.camfp.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService {
	
	private final MemberRepository memberRepository;

	@Override
	public String register(MemberDTO memberDTO) {
		
		log.info("dto -----------------------" + memberDTO);
		
		Member member = dtoToEntity(memberDTO);
		
		memberRepository.save(member);
		
		return member.getId();
	}

	@Override
	public MemberDTO get(String id) {
		
		Object result = memberRepository.getMemberById(id);
		log.info(result);
		
		return entityToDTO((Member) result);
	}

	@Override
	public void remove(String id) {

		memberRepository.deleteById(id);
		
	}

	@Override
	public void modify(MemberDTO memberDTO) {
		
		Member member = memberRepository.findById(memberDTO.getId()).get();
		
		if(member != null) {
			member.change(memberDTO.getPass(),
							memberDTO.getNickname(),
							memberDTO.getImage(),
							memberDTO.getName(),
							memberDTO.getAge(),
							memberDTO.getAddress(),
							memberDTO.getPhone(),
							memberDTO.getIntroduce());
		}
		
	}

}
