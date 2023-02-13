package com.campers.camfp.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.campers.camfp.entity.board.Board;
import com.campers.camfp.entity.board.Reply;
import com.campers.camfp.repository.board.ReplyRepository;

@SpringBootTest
public class ReplyRepositoryTest {

	@Autowired
	private ReplyRepository replyRepository;
	
	
	// 오류나서 다시 해야함.. 02/13 test 안됨.
	@Test
	public void insertBoardReplys() {
		
		IntStream.rangeClosed(1, 100).forEach(i -> {
			
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
}
