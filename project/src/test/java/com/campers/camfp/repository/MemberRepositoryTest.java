package com.campers.camfp.repository;

import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.campers.camfp.config.type.GenderType;
import com.campers.camfp.entity.member.Member;
import com.campers.camfp.repository.member.MemberRepository;

@SpringBootTest
public class MemberRepositoryTest {

	@Autowired
	private MemberRepository memberRepository;

	@Test
	public void insertMember() {	// test 확인 완.

		IntStream.rangeClosed(1, 50).forEach(i -> {
			
			GenderType xy = GenderType.MALE;	// i = 2의 배수일 때 남자.
			
			if(i % 2 == 0) {
				xy = GenderType.FEMALE;	
			}					// 여기까지 남자.
			
			Member member = Member.builder().id("userID" + i)
											.pw("1111")
											.nickname("userNick" + i)
											.age(20)
											.profileImg("imageSample" + i)
											.name("userName" + i)
											.gender(xy)
											.address("서울시" + i)
											.phone("010-1111-" + i)
											//.grade(true) -> String으로바꿈
											.introduce("안녕하슈")
											.build();
			
			memberRepository.save(member);
		});
		
	}

	@Test
	public void testSelectUser() { // test 확인 완.

//		String mid = "userID5";		// id를 입력해줌.
		Long mno = 1L;

		Optional<Member> result = memberRepository.findById(mno); // findById로 아이디에 맞는 정보를?? result에 넣어줌.

		System.out.println("------------------------");

		if (result.isPresent()) { // result가 null이 아닐경우 true.
			System.out.println("true");
			Member member = result.get();
			System.out.println(member);
		} else {
			System.out.println("false");
		}
	}

	@Test
	public void testDelete() { // test 확인 완.

//		String mid = "userID5";
		Long mno = 1L;

		memberRepository.deleteById(mno);
	}

	// 02/13 테스트 안됨.
	@Test
	public void testUpdate() {

//		Member member = Member.builder().id("userID6").pw("2222").nickname("upNick4").profileImg("updateImage4")
//				.name("updateName4").age(20).gender(false).address("수정시4").phone("010-4444-0").grade(true)
//				.introduce("수정하슈4").build();
//
//		System.out.println(memberRepository.save(member));

	}
}
