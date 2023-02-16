package com.campers.camfp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.campers.camfp.dto.board.BoardDTO;
import com.campers.camfp.entity.member.Member;
import com.campers.camfp.service.board.BoardService;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@RequiredArgsConstructor
public class BoardServiceTest {
	
	@Autowired
	private BoardService boardService;

	@Test
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
