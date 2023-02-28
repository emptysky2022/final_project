package com.campers.camfp.service;

import java.util.stream.IntStream;

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
		
		Member member = Member.builder().mno(1L).build();
		IntStream.rangeClosed(1, 100).forEach(i -> {
			
		BoardDTO boardDTO = BoardDTO.builder()
											  .title("Service Test")
											  .content("Service Test")
											  .mno(1L)
											  .category("경기도")
											  .count(count)
											  .heart(heart)
											  .build();
		
		boardService.register(boardDTO);
		});
		// test -> updateDate까지 표시됨..
	}
	
	
}
