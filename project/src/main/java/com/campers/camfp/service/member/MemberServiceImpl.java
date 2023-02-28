package com.campers.camfp.service.member;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campers.camfp.config.auth.PrincipalDetails;
import com.campers.camfp.dto.member.MemberDTO;
import com.campers.camfp.entity.member.Member;
import com.campers.camfp.repository.member.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private PrincipalDetails principalDetails;
	private final MemberRepository memberRepository;

//	@Override
//	public Long register(MemberDTO memberDTO) {
//		
//		log.info("dto -----------------------" + memberDTO);
//		
//		Member member = dtoToEntity(memberDTO);
//		
//		memberRepository.save(member);
//		
//		return member.getMno();
//	}
//
//	@Override
//	public MemberDTO get(String id) {
//		
//		Object result = memberRepository.getMemberById(id);
//		log.info(result);
//		
//		return entityToDTO((Member) result);
//	}
//
//	
//	public void remove(Long mno) {
//
//		memberRepository.deleteById(mno);
//		
//	}

	@Override
	@Transactional
	public void modify(MemberDTO memberDTO,Member info) {
		// 수정시에는 영속성 컨텍스트 Member 오브젝트를 영속화시키고, 영속화된 Member 오브젝트를 수정
        // select 를 해서 Member 오브젝트를 DB 로부터 가져오는 이유는 영속화를 하기 위해서!!
        // 영속화된 오브젝트를 변경하면 자동으로 DB에 update 문을 날려주기 때문
		Member member = memberRepository.findById(info.getId());
		member.change(memberDTO.getNickname(), memberDTO.getName(),
				memberDTO.getPhone(),memberDTO.getIntroduce(),memberDTO.getAge(),memberDTO.getAddress()
				,memberDTO.getProfileImg(),memberDTO.getGender());
			String rawPassword=member.getPw();
			String encPassword = bCryptPasswordEncoder.encode(rawPassword);
			memberDTO.setPw(encPassword);
			memberRepository.save(member);
			
			//세션 등록
			//Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(memberDTO.getId(), memberDTO.getPw()));
	        //SecurityContextHolder.getContext().setAuthentication(authentication);
			
	}

	//중복검사
	@Override
	public int idCheck(String id) {
		int cnt = memberRepository.countById(id);
		
		return cnt;
	}
	
	@Override
	public int nickCheck(String nickname) {
		int nnt = memberRepository.countByNickname(nickname);
		
		return nnt;
	}
	
	


	@Override
	public void signup(MemberDTO memberDTO) {
		
		memberDTO.setGrade("ROLE_MEMBER"); 
		String rawPassword=memberDTO.getPw();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		memberDTO.setPw(encPassword);
		Member member= dtoToEntity(memberDTO);
		memberRepository.save(member);
		System.out.println("asdf"+member);
		
		System.out.println("DB에 회원저장 성공");
		
	}

	@Override
	@Transactional
	public void deleteByMno(Long mno) {
		memberRepository.deleteByMno(mno);
		
	}

}
