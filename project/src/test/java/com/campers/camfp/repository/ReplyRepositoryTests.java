package com.campers.camfp.repository;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.campers.camfp.entity.board.Board;
import com.campers.camfp.entity.board.Reply;
import com.campers.camfp.repository.board.ReplyRepository;

@SpringBootTest
public class ReplyRepositoryTests {

	@Autowired
	private ReplyRepository replyRepository;
	
	@Test
	public void insertBoardReplys() {
		
		IntStream.rangeClosed(1, 500).forEach(i -> {
			
			// 임시 게시판 번호
			Long bno = (long)((Math.random() * 50) + 1); 

			
			int heart = (int)((Math.random() * 100) + 1);
			
			Reply reply = Reply.builder().board(Board.builder().bno(bno).build())
										 .replyer("replyer" + i)
										 .content("Reply Test" + i)
										 .heart(heart)
										 .build();
			
			
			replyRepository.save(reply);
			
		});
	}
	
	@Test
	public void testListByBoard() {
		List<Reply> replyList = (List<Reply>) replyRepository.getRepliesByBoard(Board.builder().bno(5L).build());
	
		replyList.forEach(reply -> System.out.println(reply));
	}
	
	
}
