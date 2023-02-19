package com.campers.camfp.repository;

import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.campers.camfp.entity.board.Board;
import com.campers.camfp.entity.member.Member;
import com.campers.camfp.repository.board.BoardRepository;

@SpringBootTest
public class BoardRepositoryTest {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Test
	public void testClass() {
		System.out.println(boardRepository.getClass().getName());
	}
	
	// insert test
	@Test
	public void testInsert() {
		
		IntStream.rangeClosed(1, 50).forEach(i -> {
			
			int count = (int)((Math.random() * 5000) + 1);
			int heart = (int)((Math.random() * 1000) + 1);
			
			Member member = Member.builder().mno((long) i).build();
			
			Board board = Board.builder().title("Sample Title" + i)
										 
										 .content("Sample Content" + i)
										 .member(member)
										 .category("서울특별시")
										 .count(count)
										 .heart(heart)
										 .build();
			
 			boardRepository.save(board);
										 
		});
	}
}
