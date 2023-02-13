package com.campers.camfp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.campers.camfp.dto.BoardDTO;
import com.campers.camfp.entity.Member;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@RequiredArgsConstructor
public class BoardServiceTest {
	
	@Autowired
	private BoardService boardService;

	@Test // Board Entity - regDate - insertable=false로 바꾸고 테스트해야함..개귀차너
	public void testRegister() {
		
		int count = (int)((Math.random() * 5000) + 1);
		int heart = (int)((Math.random() * 1000) + 1);
		
		Member member = Member.builder().mno(5L).build();
		
		BoardDTO boardDTO = BoardDTO.builder()
											  .title("Service Test")
											  .content("Service Test")
											  .mno(member.getMno())
											  .category("경기도")
											  .count(count)
											  .heart(heart)
											  .build();
		
		boardService.register(boardDTO);
		// test -> updateDate까지 표시됨..
	}
	
	
}
