package com.campers.camfp.repository;

import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.campers.camfp.entity.Board;
import com.campers.camfp.entity.Member;

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
			
			Member member = Member.builder().id("userID" + i).build();
			
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
	
	// select test
	@Transactional
	@Test
	public void testSelect() {
		
		Long bno = 25L;
		
//		Optional<Board> result = boardRepository.findById(bno);
//		
//		Board board = result.get();
//		
//		System.out.println(board.getTitle());
		
		Board board = boardRepository.getOne(bno);
		
		System.out.println(board);
	}
	
	// delete test
	@Test
	public void testDelete() {
		
		Long bno = 4L;
		
		boardRepository.deleteById(bno);
	}
	
	// update test
	@Test
	public void testUpdate() {
		
		int count = (int)((Math.random() * 5000) + 1);
		int heart = (int)((Math.random() * 1000) + 1);
		
		Member member = Member.builder().id("userID2").build();
		
		Board board = Board.builder().bno(6L)
									 .title("Update Title")
									 .content("Update Content")
									 .member(member)
									 .category("서울특별시")
									 .count(count)
									 .heart(heart)
									 .build();
				
		
		boardRepository.save(board);
		
	}
	
	// page test
	@Transactional
	@Test
	public void testPage() {
		
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<Board> result = boardRepository.findAll(pageable);
		
		System.out.println(result);
		
		System.out.println("----------------Page Test------------------");
		
		System.out.println("Total pages : " + result.getTotalPages());

		System.out.println("---------------------?----------------------");
	
		for(Board board : result.getContent()) {
			System.out.println(board);
		}
	}
	
	@Transactional
	@Test
	public void testSort() {
		
		Sort sort = Sort.by("bno").descending();
		
		Pageable pageable = PageRequest.of(0, 10, sort);
		
		Page<Board> result = boardRepository.findAll(pageable);
		
		result.get().forEach(board -> {
			System.out.println(board);
		});
		
	}
	
}
