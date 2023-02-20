package com.campers.camfp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.campers.camfp.dto.board.ReplyDTO;
import com.campers.camfp.service.board.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/replies")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {

	private final ReplyService replyService;
	
	@GetMapping("/{bno}/all")
	public ResponseEntity<List<ReplyDTO>> getList(@PathVariable("bno") Long bno) {
		
		log.info("list-------------");
		log.info("BNO : " + bno);
		
		List<ReplyDTO> replyDTOList = replyService.getListOfBoard(bno);
		
		return new ResponseEntity<>(replyDTOList, HttpStatus.OK);
	}
	
	@PostMapping("/{bno}")
	public ResponseEntity<Long> addReply(@RequestBody ReplyDTO replyDTO) {
		
		log.info("add-----------------");
		log.info("replyDTO : " + replyDTO);
		
		Long replyNum = replyService.replyRegister(replyDTO);
		
		return new ResponseEntity<Long>(replyNum, HttpStatus.OK);
	}
	
	@PutMapping("/{bno}/{replyNum}")
	public ResponseEntity<Long> modifyReply(@PathVariable Long replyNum, @RequestBody ReplyDTO replyDTO) {
		
		log.info("modify---------------");
		log.info("replyDTO : " + replyDTO);
		
		replyService.modify(replyDTO);
		
		return new ResponseEntity<Long>(replyNum, HttpStatus.OK);
	}
	
	
	@DeleteMapping("/{bno}/{replyNum}")
	public ResponseEntity<Long> removeReply(@PathVariable Long replyNum) {
		
		log.info("remove---------------");
		log.info("replyNum : " + replyNum);
		
		replyService.remove(replyNum);
		
		return new ResponseEntity<Long>(replyNum, HttpStatus.OK);
	}
	
	
}