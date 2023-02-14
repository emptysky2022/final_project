package com.campers.camfp.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import com.campers.camfp.dto.board.ReplyDTO;
import com.campers.camfp.service.board.ReplyService;

@SpringBootTest
public class ReplyServiceTests {
	
	@Autowired
	private ReplyService replyService;
	
	@Test
	public void testGetList() {
		Long bno = 17L;
		
		List<ReplyDTO> replyDTOList = replyService.getListOfBoard(bno);
		
		replyDTOList.forEach(replyDTO -> System.out.println(replyDTO));
	}
	
}
