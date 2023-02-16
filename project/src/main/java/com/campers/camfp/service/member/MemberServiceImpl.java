package com.campers.camfp.service.member;

import org.springframework.stereotype.Service;

import com.campers.camfp.dto.member.MemberDTO;
import com.campers.camfp.entity.member.Member;
import com.campers.camfp.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService {
	
	private final MemberRepository memberRepository;

	@Override
	public Long register(MemberDTO memberDTO) {
		
		log.info("dto -----------------------" + memberDTO);
		
		Member member = dtoToEntity(memberDTO);
		
		memberRepository.save(member);
		
		return member.getMno();
	}

	@Override
	public MemberDTO get(String id) {
		
		Object result = memberRepository.getMemberById(id);
		log.info(result);
		
		return entityToDTO((Member) result);
	}

	
	public void remove(Long mno) {

		memberRepository.deleteById(mno);
		
	}

	@Override
	public void modify(MemberDTO memberDTO) {
		
		Member member = memberRepository.findById(memberDTO.getMno()).get();
		
		if(member != null) {
			member.change(memberDTO.getPw(),
							memberDTO.getNickname(),
							memberDTO.getProfileImg(),
							memberDTO.getName(),
							memberDTO.getAge(),
							memberDTO.getAddress(),
							memberDTO.getPhone(),
							memberDTO.getIntroduce());
		}
		
	}

}
